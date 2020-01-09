package com.fline.itext;

import org.apache.commons.lang.StringUtils;

import java.io.File;

public class Html2Pdf2 {

    public static void main(String[] args) throws Exception {
        Html2Pdf2 html2Pdf2 = new Html2Pdf2();
        String pdf = html2Pdf2.getPdf("D:\\Users\\User\\Desktop\\a.html");
        System.out.println(pdf);
    }

    /**
     * windows执行文件
     */
    private String windowExePath;
    /**
     * linux执行文件
     */
    private String linuxExePath;


    public String getPdf(String pageUrl) throws Exception {
        String outputPath = "D:\\test.pdf";
        String cmdStr = getCmdStr(pageUrl, outputPath);
        boolean success = excuteCmd(cmdStr);
        if (success) {
            return outputPath;
        } else {
            if(isExistNotCreate(outputPath)){
                return outputPath;
            }else {

                throw new Exception("转化异常！[" + outputPath + "]");
            }
        }
    }

    public static boolean isExistNotCreate(String path) {
        File file = new File(path);
        return file.exists();
    }

    private String getCmdStr(String pageUrl, String outputPath) {
        StringBuilder cmdStr = new StringBuilder();
        String absoultOutputPath = PathUtils.getClassRootPath(outputPath);
        /************************输出文件夹检查************************/
        checkFolderAndCreate(absoultOutputPath);
        String absoultExePath = "";
        if (PathUtils.isWindows()) {//windows系统
            absoultExePath = getWindowExePath();
            absoultOutputPath = PathUtils.getWindowsRightPath(absoultOutputPath);
        } else {//默认linux系统
            absoultExePath = getLinuxExePath();
            //需要给脚本授权
            //cmdStr.append("chmod +x ").append(absoultExePath).append(" && ");
            excuteCmd("chmod +x " + absoultExePath);
        }
        cmdStr.append(absoultExePath).append(" ").append(pageUrl).append(" ").append(absoultOutputPath);
        return cmdStr.toString();
    }

    public String getWindowExePath() {
        if (StringUtils.isBlank(this.windowExePath)) {
            String absoultExePath = PathUtils.getClassRootPath("/plugin/window/wkhtmltopdf/bin/wkhtmltopdf");
            this.windowExePath = PathUtils.getWindowsRightPath(absoultExePath);
        }
        return this.windowExePath;
    }

    public void setWindowExePath(String windowExePath) {
        this.windowExePath = windowExePath;
    }

    public String getLinuxExePath() {
        if (StringUtils.isBlank(this.linuxExePath)) {
            this.linuxExePath = PathUtils.getClassRootPath("/plugin/linux/wkhtmltox/bin/wkhtmltopdf");
        }
        return this.linuxExePath;
    }

    public void setLinuxExePath(String linuxExePath) {
        this.linuxExePath = linuxExePath;
    }


    public static boolean excuteCmd(String cmdStr) {
        // 利用Runtime输出流读取
        Runtime rt = Runtime.getRuntime();
        try {
            System.out.println("Command：" + cmdStr);
            Process p = rt.exec(cmdStr);
            StreamGobbler errorGobbler = new StreamGobbler(p.getErrorStream(),
                    "ERROR");
            // 开启屏幕标准错误流
            errorGobbler.start();
            StreamGobbler outGobbler = new StreamGobbler(p.getInputStream(),
                    "STDOUT");
            // 开启屏幕标准输出流
            outGobbler.start();
            int w = p.waitFor();
            int v = p.exitValue();
            if (w == 0 && v == 0) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }


    public static boolean checkFolderAndCreate(String absoultOutputFilePath){
        int index = absoultOutputFilePath.lastIndexOf("/");
        if(index > 0) {
            String dirPath = absoultOutputFilePath.substring(0,index);
            File file = new File(dirPath);
            int i = 0;
            while (!file.exists()) {
                file.mkdirs();
                if(i++ > 100){
                    return false;
                }
            }
        }
        return true;
    }

}
