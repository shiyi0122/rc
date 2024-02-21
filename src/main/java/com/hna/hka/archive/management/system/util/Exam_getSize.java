package com.hna.hka.archive.management.system.util;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.math.BigDecimal;
import java.math.MathContext;
import java.text.DecimalFormat;

/**
 * @ProjectName: IDEA-CODE
 * @Package: com.hna.hka.archive.management.system.util
 * @ClassName: Exam_getSize
 * @Author: 郭凯
 * @Description: 获取文件大小
 * @Date: 2020/11/16 16:33
 * @Version: 1.0
 */
public class Exam_getSize {

    public static String  getSize(MultipartFile file)
    {
        double result=0;
        String unit="字节";
        long length = file.getSize();
        if(length<1024)
        {
            result= length;
        }
        else if(length<1024*1024)
        {
            result=length/1024.0;
            unit="KB";
        }
        else if(length<1024*1024*1024)
        {
            result=length/1024.0/1024;
            unit="MB";
        }
        else
        {
            result=length/1024.0/1024/1024;
            unit="GB";
        }
        DecimalFormat decimalFormat = new DecimalFormat("###################.###########");
        String format = decimalFormat.format(result);
        BigDecimal bigDecimal=new BigDecimal(format+"",new MathContext(3));
        return bigDecimal+unit;

    }

}
