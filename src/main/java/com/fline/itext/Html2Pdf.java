package com.fline.itext;

import com.lowagie.text.pdf.BaseFont;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class Html2Pdf {
    private static String FONTS_PATH="C:\\Windows\\Fonts\\simsun.ttc";
    //html 转PDF
    public static void html2Pdf(String url, String pdfPath) throws Exception{
        OutputStream os = null;
        try {
            ITextRenderer renderer = new ITextRenderer();
            ITextFontResolver fontResolver = renderer.getFontResolver();
            fontResolver.addFont(FONTS_PATH, BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
            os = new FileOutputStream(pdfPath);
            renderer.setDocument(url);
            renderer.layout();
            renderer.createPDF(os);
            os.flush();
            os.close();
        }catch (Exception e) {
            e.printStackTrace();
            if (os != null) {
                os.close();
            }
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) throws Exception {

        //测试
        String url = "D:\\Users\\User\\Desktop\\a.html";
        url = new File(url).toURI().toURL().toString();
        String path =  "D:\\test.pdf";
        html2Pdf(url, path);
    }

}
