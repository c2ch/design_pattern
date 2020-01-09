package com.fline.file;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.FileHeader;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: zhengfeng
 * @Date: 2019\4\23 0009 11:59
 * @Description:
 */
@Component
public class ZipUtils {
	private static final Logger log = LoggerFactory.getLogger(ZipUtils.class);

	/**
	 * 压缩不加密
	 * 
	 * @param sourceFileNameList
	 * @param destZipFileName
	 * @return
	 */
	public static File zip(List<String> sourceFileNameList, String destZipFileName) {
		ArrayList<File> sourceFileList = new ArrayList<File>(sourceFileNameList.size());
		for (String sourceFileName : sourceFileNameList) {
			File sourceFile = new File(sourceFileName);
			sourceFileList.add(sourceFile);
		}
		return zip(sourceFileList, destZipFileName);
	}

	/**
	 * 压缩不加密
	 * 
	 * @param sourceFileList
	 * @param destZipFileName
	 * @return
	 */
	public static File zip(ArrayList<File> sourceFileList, String destZipFileName) {
		try {
			ZipFile zipFile = new ZipFile(destZipFileName);
			ZipParameters parameters = new ZipParameters();
			// 设置压缩模式
			parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
			parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
			for (File sourceFile : sourceFileList) {
				if (sourceFile.isDirectory()) {
					zipFile.addFolder(sourceFile, parameters);
				} else {
					zipFile.addFile(sourceFile, parameters);
				}
			}
			return new File(destZipFileName);
		} catch (ZipException e) {
			log.error("zip error:{}", e.getMessage());
		}
		return null;
	}

	/**
	 * 压缩加密
	 * 
	 * @param sourceFileNameList
	 * @param destZipFileName
	 * @param password
	 * @return
	 */
	public static boolean zip(List<String> sourceFileNameList, String destZipFileName, String password) {
		ArrayList<File> sourceFileList = new ArrayList<File>(sourceFileNameList.size());
		for (String sourceFileName : sourceFileNameList) {
			File sourceFile = new File(sourceFileName);
			sourceFileList.add(sourceFile);
		}
		return zip(sourceFileList, destZipFileName, password);
	}

	/**
	 * 压缩加密
	 * 
	 * @param sourceFileList
	 * @param destZipFileName
	 * @param password
	 * @return
	 */
	public static boolean zip(ArrayList<File> sourceFileList, String destZipFileName, String password) {
		try {
			ZipFile zipFile = new ZipFile(destZipFileName);
			ZipParameters parameters = new ZipParameters();
			// 设置压缩模式
			parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
			parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
			// 设置加密标志
			parameters.setEncryptFiles(true);
			// 设置aes加密
			parameters.setEncryptionMethod(Zip4jConstants.ENC_METHOD_AES);
			parameters.setAesKeyStrength(Zip4jConstants.AES_STRENGTH_256);
			// 设置密码
			parameters.setPassword(password);
			for (File sourceFile : sourceFileList) {
				if (sourceFile.isDirectory()) {
					zipFile.addFolder(sourceFile, parameters);
				} else {
					zipFile.addFile(sourceFile, parameters);
				}
			}
			return true;
		} catch (ZipException e) {
			log.error("zip error:{}", e.getMessage());
		}
		return false;
	}

	/**
	 * 解压缩到当前文件夹
	 * 
	 * @param zipFileName
	 * @param password
	 * @return
	 */
	public static List<File> unzip(String zipFileName, String password) {
		ZipFile zipFile = null;
		try {
			zipFile = new ZipFile(zipFileName);
			if (zipFile.isEncrypted()) {
				zipFile.setPassword(password);
			}
			File file = new File(zipFileName);
			String destDir = file.getParent();
			return unzip(zipFileName, destDir, password);
		} catch (ZipException e) {
			log.error("unzip error:{}", e.getMessage());
		}
		return null;
	}

	/**
	 * 根据所给密码解压zip压缩包到指定目录
	 * <p>
	 * 如果指定目录不存在,可以自动创建,不合法的路径将导致异常被抛出
	 *
	 * @param zipFileName zip压缩包绝对路径
	 * @param destDir 指定解压文件夹位置
	 * @param password 密码(可为空)
	 * @return 解压后的文件数组
	 * @throws ZipException
	 */
	public static List<File> unzip(String zipFileName, String destDir, String password) {
		List<File> fileList = new ArrayList<File>();
		try {
			ZipFile zipFile = new ZipFile(zipFileName);
			// System.out.println("-----------" + System.getProperty("sun.jnu.encoding"));
//			System.setProperty("sun.zip.encoding", System.getProperty("sun.jnu.encoding")); //防止文件名中有中文时出错
//			 zipFile.setFileNameCharset("utf-8");
			zipFile.setFileNameCharset(System.getProperty("sun.jnu.encoding"));
			if (!zipFile.isValidZipFile()) {
				throw new ZipException("压缩文件不合法,可能被损坏.");
			}
			if (zipFile.isEncrypted()) {
				zipFile.setPassword(password);
			}
			File destFile = new File(destDir);
			if (!destFile.exists()) {
				destFile.mkdirs();
			}
			zipFile.extractAll(destDir);
			List<FileHeader> fileHeaderList = zipFile.getFileHeaders();
			for (FileHeader fileHeader : fileHeaderList) {
				File file = new File(destDir, fileHeader.getFileName());
				//文件夹的不加
				if(file.isFile()){
					fileList.add(file);
				}
			}
		} catch (ZipException e) {
			log.error("unzip error:{}", e.getMessage());
		}
		return fileList;
	}

	/**
	 * 单文件解压解密
	 * 
	 * @param zipFileName
	 * @param password
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static InputStream singleUnzipToStream(String zipFileName, String password) {
		try {
			ZipFile zipFile = new ZipFile(zipFileName);
			zipFile.setFileNameCharset("UTF-8");
			if (zipFile.isEncrypted()) {
				zipFile.setPassword(password);
			}
			List<FileHeader> fileHeaderList = zipFile.getFileHeaders();
			if (fileHeaderList.size() == 1) {
				return zipFile.getInputStream(fileHeaderList.get(0));
			}
		} catch (ZipException e) {
			log.error("unzip to inputstream error:{}", e.getMessage());
		}
		return null;
	}

	/**
	 * 单文件解压解密
	 * 
	 * @param zipFileName
	 *            压缩包文件
	 * @param originalFileName
	 *            原始文件
	 * @param password
	 * @return
	 */
	public static InputStream singleUnzipToStream(String zipFileName, String originalFileName, String password) {
		try {
			ZipFile zipFile = new ZipFile(zipFileName);
			zipFile.setFileNameCharset("UTF-8");
			if (zipFile.isEncrypted()) {
				zipFile.setPassword(password);
			}
			FileHeader fileHeader = zipFile.getFileHeader(originalFileName);
			return zipFile.getInputStream(fileHeader);
		} catch (ZipException e) {
			log.error("unzip to inputstream error:{}", e.getMessage());
		}
		return null;
	}

	/**
	 * 单文件解压解密
	 * 
	 * @param zipFileName
	 *            压缩包文件
	 * @param originalFileName
	 *            原始文件
	 * @param destFileName
	 *            输出的目标文件
	 * @param password
	 *            密码
	 * @return
	 */
	public static File singleUnzipOutput(String zipFileName, String originalFileName, String destFileName,
			String password) {
		InputStream inputStream = null;
		FileOutputStream fileOutputStream = null;
		File destFile = new File(destFileName);
		try {
			inputStream = singleUnzipToStream(zipFileName, originalFileName, password);
			fileOutputStream = new FileOutputStream(new File(destFileName));
			IOUtils.copy(inputStream, fileOutputStream);
			return destFile;
		} catch (FileNotFoundException e) {
			log.error("unzip output error:{}", e.getMessage());
		} catch (IOException e) {
			log.error("unzip output inputstream error:{}", e.getMessage());
		} finally {
			IOUtils.closeQuietly(fileOutputStream);
			IOUtils.closeQuietly(inputStream);
		}
		return null;
	}

	public static void main(String[] args){
		String srcPath = "D:\\Users\\User\\Desktop\\20190719112.zip";
		String destPath = "D:\\Users\\User\\Desktop\\";
		List<File> files = ZipUtils.unzip(srcPath, destPath, null);
		for(File f : files){
			System.out.println(f.getName());
		}
	}

}
