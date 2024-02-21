package com.hna.hka.archive.management.system.util;

import java.math.BigDecimal;
import java.text.NumberFormat;

/**
 * @ProjectName: rc
 * @Package: com.hna.hka.archive.management.system.util
 * @ClassName: BigDecimalUtil
 * @Author: 郭凯
 * @Description: 精准计算类
 * @Date: 2021/6/17 17:31
 * @Version: 1.0
 */
public class BigDecimalUtil {

    /**
     * @Method
     * @Author 郭凯
     * @Version  1.0
     * @Description 加法
     * @Return
     * @Date 2021/6/17 17:36
     */
    public static String add(String one , String two){

        if (ToolUtil.isEmpty(one) || "0".equals(one) || "0.0".equals(one)){
            return "0";
        }

        if (ToolUtil.isEmpty(two) || "0".equals(two) || "0.0".equals(two)){
            return "0";
        }

        BigDecimal one1=new BigDecimal(one);
        BigDecimal two1=new BigDecimal(two);
        //加法
        BigDecimal result1 = one1.add(two1);
        return result1.toString();
    }

    /**
     * @Method
     * @Author 郭凯
     * @Version  1.0
     * @Description 减法
     * @Return
     * @Date 2021/6/17 17:37
     */
    public static String subtract(String one , String two){

        if (ToolUtil.isEmpty(one)){
            return "0";
        }

        if (ToolUtil.isEmpty(two)){
            return "0";
        }

        BigDecimal one1=new BigDecimal(one);
        BigDecimal two1=new BigDecimal(two);
        //减法
        BigDecimal result2 = one1.subtract(two1);
        return result2.toString();
    }

    /**
     * @Method
     * @Author 郭凯
     * @Version  1.0
     * @Description 乘法
     * @Return
     * @Date 2021/6/17 17:37
     */
    public static String multiply(String one , String two){

        BigDecimal one1=new BigDecimal(one);
        BigDecimal two1=new BigDecimal(two);
        //乘法
        BigDecimal result2 = one1.multiply(two1);
        return result2.toString();
    }

    /**
     * @Method
     * @Author 郭凯
     * @Version  1.0
     * @Description 除法
     * @Return
     * @Date 2021/6/17 17:38
     */
    public static String divide(String one , String two){

        if (ToolUtil.isEmpty(one) || "0".equals(one) || "0.0".equals(one)){
            return "0";
        }

        if (ToolUtil.isEmpty(two) || "0".equals(two) || "0.0".equals(two)){
            return "0";
        }

        BigDecimal one1=new BigDecimal(one);
        BigDecimal two1=new BigDecimal(two);
        //除法
        BigDecimal result2 = one1.divide(two1,2,BigDecimal.ROUND_DOWN);
        return result2.toString();
    }

    /**
     * @Method
     * @Author 郭凯
     * @Version  1.0
     * @Description 计算百分比
     * @Return
     * @Date 2021/7/12 14:57
     */
    public static String calculatePercentage(String one , String two){
        Integer chapterCount = Integer.valueOf(one);//总数
        Integer learnCount = Integer.valueOf(two);;
        // 创建一个数值格式化对象
        NumberFormat numberFormat = NumberFormat.getInstance();
        // 设置精确到小数点后2位
        numberFormat.setMaximumFractionDigits(0);
        String result = numberFormat.format((float) learnCount/ (float)chapterCount* 100);//所占百
        return result;
    }

    public static void main(String[] args) {
        BigDecimal one1=new BigDecimal("26");
        BigDecimal two1=new BigDecimal("0.0");
        //减法
        BigDecimal result2 = one1.subtract(two1);
        System.out.println(result2);
    }

}
