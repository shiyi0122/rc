package com.hna.hka.archive.management.system.util;

import com.itextpdf.text.pdf.BaseFont;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 * @program: rc
 * @description:
 * @author: zhaoxianglong
 * @create: 2021-09-17 17:41
 **/
@Component
public class HtmlUtil {

    private HtmlUtil() {
    }

    public static void file2Pdf(File htmlFile, String pdfFile) {
        try (OutputStream os = new FileOutputStream(pdfFile)) {
            String url = htmlFile.toURI().toURL().toString();
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocument(url);
            // 解决中文支持
            ITextFontResolver fontResolver = renderer.getFontResolver();
            // 获取字体绝对路径，ApplicationContextUtil是我自己写的类
            fontResolver.addFont("src/main/resources/templates/font/simsun.ttc", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            renderer.layout();
            renderer.createPDF(os);
        } catch (Exception e) {
            // 抛出自定义异常
        }
    }

    public static void html2Pdf(String html, String pdfFile) throws IOException {
        String pdfDir = StringUtils.substringBeforeLast(pdfFile, "/");
        File file = new File(pdfDir);
        if (!file.exists()) {
            file.mkdirs();
        }
        File file1 = new File(pdfFile);
        if (!file1.exists()) {
            file1.createNewFile();
        }
        try {
            OutputStream os = new FileOutputStream(file1);
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(html);
            // 解决中文支持
            ITextFontResolver fontResolver = renderer.getFontResolver();

            // 获取字体绝对路径，ApplicationContextUtil是我自己写的类
            fontResolver.addFont("src/main/resources/templates/font/simsun.ttc", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            renderer.layout();
            renderer.createPDF(os);
        } catch (Exception e) {
            // 抛出自定义异常
            e.printStackTrace();
        }
    }

    public static void html2servlet(String targetFileName , String html, HttpServletResponse response) throws IOException {
        try {
//            targetFileName = targetFileName + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
            targetFileName = URLEncoder.encode(targetFileName, "UTF8");
            response.setContentType("application/vnd.ms-excel;chartset=utf-8");
            response.setHeader("Content-Disposition", "attachment;filename=" + targetFileName + ".pdf");
            OutputStream os = response.getOutputStream();
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(html);
            // 解决中文支持
            ITextFontResolver fontResolver = renderer.getFontResolver();

            //解决服务器中jar包无法访问resources下文件的问题
            ClassPathResource resource = new ClassPathResource("static/font/simsun.ttc");
            InputStream in = resource.getInputStream();
            //创建临时文件(空文件)  prefx:test  suffix: .png，该文件会默认创建在你用户的更目录下，具体哪个自己打印出来看看就知道
            File file = File.createTempFile("simsum", ".ttc");
            //将获取的流转为文件，在转换过后我们的资源文件就被copy到前面创建的临时文件中了
            FileUtils.copyInputStreamToFile(in, file);
            String path = file.getPath();

            fontResolver.addFont(path, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);

            // 获取字体绝对路径，ApplicationContextUtil是我自己写的类
//            fontResolver.addFont("src/main/resources/static/font/simsun.ttc", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            renderer.layout();
            renderer.createPDF(os);
        } catch (Exception e) {
            // 抛出自定义异常
            e.printStackTrace();
        }
    }








}
