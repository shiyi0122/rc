package com.hna.hka.archive.management.system.util;

import com.alibaba.excel.metadata.Font;
import com.alibaba.excel.metadata.TableStyle;
import com.alibaba.excel.support.ExcelTypeEnum;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Objects;

/**
 * @ProjectName: rc
 * @Package: com.hna.hka.archive.management.system.util
 * @ClassName: EasyExcel
 * @Author: 郭凯
 * @Description: EasyExcel导出类
 * @Date: 2021/7/28 15:44
 * @Version: 1.0
 */
public class EasyExcel {

    public static final Logger logger = LoggerFactory.getLogger(EasyExcel.class);



    public static OutputStream getOutputStream(HttpServletResponse response, String fileName,
                                         ExcelTypeEnum excelTypeEnum) {
        try {
            // 设置响应输出的头类型
            if (Objects.equals(".xls", excelTypeEnum.getValue())) {
                //导出xls格式
                response.setContentType("application/vnd.ms-excel;charset=GBK");
            } else if (Objects.equals(".xlsx", excelTypeEnum.getValue())) {
                //导出xlsx格式
                response.setContentType(
                        "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=GBK");
            }
            // 设置下载文件名称(注意中文乱码)
            response.addHeader("Content-Disposition",
                    "attachment;filename=" + new String((fileName).getBytes("GB2312"), "ISO8859-1") + excelTypeEnum
                            .getValue());
            response.addHeader("Pragma", "No-cache");
            response.addHeader("Cache-Control", "No-cache");
            response.setCharacterEncoding("utf8");
            return response.getOutputStream();
        } catch (IOException e) {
            logger.error("EasyExcelUtil-->getOutputStream exception:", e);
        }
        return null;
    }

    /**
     * 设置表格样式s
     * @return
     */
    public static TableStyle createTableStyle() {
        TableStyle tableStyle = new TableStyle();
        Font headFont = new Font();
        headFont.setBold(true);
        headFont.setFontHeightInPoints((short) 14);
        headFont.setFontName("楷体");
        tableStyle.setTableHeadFont(headFont);
        tableStyle.setTableHeadBackGroundColor(IndexedColors.LIGHT_GREEN);

        Font contentFont = new Font();
        contentFont.setFontHeightInPoints((short) 12);
        contentFont.setFontName("黑体");
        tableStyle.setTableContentFont(contentFont);
        return tableStyle;
    }

    public static  HorizontalCellStyleStrategy setHorizontalCellStyleStrategy(){
        /*******自定义列标题和内容的样式******/
        // 头的策略
        WriteCellStyle headWriteCellStyle = new WriteCellStyle();
        // 背景设置为红色
        headWriteCellStyle.setFillForegroundColor(IndexedColors.PALE_BLUE.getIndex());
        WriteFont headWriteFont = new WriteFont();
        headWriteFont.setFontHeightInPoints((short)10);
        headWriteCellStyle.setWriteFont(headWriteFont);
        // 内容的策略
        WriteCellStyle contentWriteCellStyle = new WriteCellStyle();
        // 这里需要指定 FillPatternType 为FillPatternType.SOLID_FOREGROUND 不然无法显示背景颜色.头默认了 FillPatternType所以可以不指定
        //  contentWriteCellStyle.setFillPatternType(FillPatternType.SOLID_FOREGROUND);
        // 背景绿色
        //  contentWriteCellStyle.setFillForegroundColor(IndexedColors.GREEN.getIndex());
        WriteFont contentWriteFont = new WriteFont();
        // 字体大小
        contentWriteFont.setFontHeightInPoints((short)10);
        contentWriteCellStyle.setWriteFont(contentWriteFont);

        //设置 自动换行
        contentWriteCellStyle.setWrapped(true);
        //设置 垂直居中
        contentWriteCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        //设置 水平居中
        contentWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);
//        //设置边框样式
//       contentWriteCellStyle.setBorderLeft(BorderStyle.MEDIUM);
        //      contentWriteCellStyle.setBottomBorderColor(IndexedColors.BLUE.getIndex()); 颜色
        //      contentWriteCellStyle.setBorderTop(DASHED);
//        contentWriteCellStyle.setBorderRight(DASHED);
        //       contentWriteCellStyle.setBorderBottom(DASHED);

        // 这个策略是 头是头的样式 内容是内容的样式 其他的策略可以自己实现
        HorizontalCellStyleStrategy horizontalCellStyleStrategy =
                new HorizontalCellStyleStrategy(headWriteCellStyle, contentWriteCellStyle);
        return horizontalCellStyleStrategy;
    }

}
