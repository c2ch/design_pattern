package com.fline.file;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

public class DownLoad {

    public void client2DownOneFile(HttpServletResponse response, String filename, String path ) throws Exception{
        if (filename != null) {
            FileInputStream is = null;
            BufferedInputStream bs = null;
            OutputStream os = null;
            try {
                File file = new File(path);
                if (file.exists()) {
                    //设置Headers
                    response.setHeader("Content-Type","application/octet-stream");
                    //设置下载的文件的名称-该方式已解决中文乱码问题
                    response.setHeader("Content-Disposition","attachment;filename=" +  new String( filename.getBytes("gb2312"), "ISO8859-1" ));
                    is = new FileInputStream(file);
                    bs =new BufferedInputStream(is);
                    os = response.getOutputStream();
                    byte[] buffer = new byte[1024];
                    int len = 0;
                    while((len = bs.read(buffer)) != -1){
                        os.write(buffer,0,len);
                    }
                }else{
                    String error = "下载的文件资源不存在";
                }
            }catch(Exception e){
                e.printStackTrace();
            }finally {
                try{
                    if(is != null){
                        is.close();
                    }
                    if( bs != null ){
                        bs.close();
                    }
                    if( os != null){
                        os.flush();
                        os.close();
                    }
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args){
        FileInputStream is = null;
        BufferedInputStream bs = null;
        OutputStream os = null;

        try {
            File file = new File("D:\\Users\\User\\Desktop\\IMPORT.xlsx");
            if (file.exists()) {
                //设置Headers
                is = new FileInputStream(file);
                bs =new BufferedInputStream(is);
                os = new BufferedOutputStream(new FileOutputStream(new File("D:\\Users\\User\\Desktop\\2.xlsx")));
                byte[] buffer = new byte[1024];
                int len = 0;
                while((len = bs.read(buffer)) != -1){
                    os.write(buffer,0,len);
                }
            }else{
                String error = "下载的文件资源不存在";
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            try{
                if(is != null){
                    is.close();
                }
                if( bs != null ){
                    bs.close();
                }
                if( os != null){
                    os.flush();
                    os.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

}
