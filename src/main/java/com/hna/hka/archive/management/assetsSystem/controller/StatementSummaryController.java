package com.hna.hka.archive.management.assetsSystem.controller;

import com.hna.hka.archive.management.assetsSystem.model.OrderMoney;
import com.hna.hka.archive.management.assetsSystem.model.SysScenicSpotTargetAmount;
import com.hna.hka.archive.management.assetsSystem.service.StatementSummaryService;
import com.hna.hka.archive.management.system.util.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2023/2/1 14:37
 * 对账单汇总表
 */
@Api(tags = "对账单汇总表")
@RestController
@RequestMapping("/system/statementSummary")
public class StatementSummaryController extends PublicUtil {

    @Autowired
    StatementSummaryService statementSummaryService;


    @ApiOperation("账单汇总列表查询")
    @CrossOrigin
    @GetMapping("/getStatementSummaryList")
    public PageDataResult getStatementSummaryList(String subjectId, String companyId, String spotId, String startTime, String endTime, Integer pageNum, Integer pageSize) {

        PageDataResult pageDataResult = new PageDataResult();

        pageDataResult = statementSummaryService.getStatementSummaryList(subjectId, companyId, spotId, startTime, endTime, pageNum, pageSize);

        return pageDataResult;

    }

    @ApiOperation("导出")
    @RequestMapping("/getStatementSummaryExcel")
    @ResponseBody
    public PageDataResult getStatementSummaryExcel(HttpServletResponse response, String subjectId, String companyId, String spotId, String startTime, String endTime) {
        PageDataResult pageDataResult = new PageDataResult();

        try {

            List<OrderMoney> list = statementSummaryService.getStatementSummaryExcel(subjectId, companyId, spotId, startTime, endTime);
            String dateTime = DateFormatUtils.format(new Date(), "yyyyMMddHHmm");
            FileUtil.exportExcel(FileUtil.getWorkbook("对账单汇总表", "对账单汇总表", OrderMoney.class, list), "对账单汇总" + dateTime, response);

        } catch (Exception e) {
            logger.info("getTargetAmountList", e);
        }
        return pageDataResult;
    }


    @ApiOperation("预览")
    @CrossOrigin
    @GetMapping("/preview")
    public ReturnModel preview(String subjectId, String companyId, String spotId, String startTime, String endTime) {
        HashMap<String, Object> mapList = statementSummaryService.preview(subjectId, companyId, spotId, startTime, endTime);
        return new ReturnModel(mapList, "预览信息成功", Constant.STATE_SUCCESS, null);
    }

    @ApiOperation("下载")
    @CrossOrigin
    @GetMapping("/download")
    public void download(String subjectId , String companyId, String spotId, String startTime, String endTime, HttpServletResponse response) {

        try {
            //解决服务器中jar包无法访问resources下文件的问题
            ClassPathResource resource = new ClassPathResource("static/xls/test1.xlsx");
            InputStream in = resource.getInputStream();
            //创建临时文件(空文件)  prefx:test  suffix: .png，该文件会默认创建在你用户的更目录下，具体哪个自己打印出来看看就知道
            File file = File.createTempFile("test1", ".xlsx");
            //将获取的流转为文件，在转换过后我们的资源文件就被copy到前面创建的临时文件中 了
            FileUtils.copyInputStreamToFile(in, file);

            MultipartFile multipartFile = new MockMultipartFile(file.getName(), new FileInputStream(file));
            InputStream inputStream = multipartFile.getInputStream();
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
            XSSFSheet sheet = workbook.getSheetAt(0);
            HashMap<String, Object> preview = statementSummaryService.preview(subjectId, companyId, spotId, startTime, endTime);
            List<Map<String,Object>> list = (List<Map<String,Object>>)preview.get("list");
            HashMap<String,Object> subtotal = (HashMap<String,Object>) preview.get("subtotal");
            Double shareProportion = (Double) preview.get("shareProportion");
            Double shareAmount = (Double) preview.get("shareAmount");
            int i = 8;
            if (list.size()>0){


//                XSSFCellStyle header = workbook.createCellStyle();
//                header.setFillPattern(FillPatternType.SOLID_FOREGROUND);    //设置填充方案
//                header.setFillForegroundColor(new XSSFColor(new Color(255, 218, 185)));
                sheet.getRow(1).getCell(0).setCellValue(list.get(0).get("收款单位").toString());

                sheet.getRow(3).getCell(1).setCellValue(list.get(0).get("收款单位").toString());
                sheet.getRow(4).getCell(1).setCellValue(list.get(0).get("开户行").toString());
                sheet.getRow(5).getCell(1).setCellValue(list.get(0).get("收款账号").toString());
                for (; i < list.size()+8; i++) {

                    String s = list.get(i - 8).get("税").toString();
                    if ("0.0".equals(s)){
                        double v = Double.parseDouble(s);
                        sheet.createRow(i).createCell(4).setCellValue(String.valueOf(v));
                    }else{
                        sheet.createRow(i).createCell(4).setCellValue(String.valueOf(s));

                    }

                    sheet.getRow(i).createCell(0).setCellValue(list.get(i-8).get("景区名称").toString());
                    sheet.getRow(i).createCell(1).setCellValue(list.get(i-8).get("月份").toString());
                    sheet.getRow(i).createCell(2).setCellValue(list.get(i-8).get("流水").toString());
                    sheet.getRow(i).createCell(3).setCellValue(list.get(i-8).get("手续费").toString());
//                    sheet.createRow(i).createCell(1).setCellValue(list.get(i-8).get("月份").toString());
//                    sheet.createRow(i).createCell(2).setCellValue(list.get(i-8).get("流水").toString());
//                    sheet.createRow(i).createCell(3).setCellValue(list.get(i-8).get("手续费").toString());


                    sheet.getRow(i).createCell(5).setCellValue(list.get(i-8).get("可分成金额").toString());
                }


                sheet.getRow(i).getCell(0).setCellValue("可分成金额小计");
                sheet.addMergedRegion(new CellRangeAddress(i,i,0,1));//合并单元格
                sheet.getRow(i).getCell(2).setCellValue(subtotal.get("流水小计").toString());
                sheet.getRow(i).getCell(3).setCellValue(subtotal.get("手续费小计").toString());
                sheet.getRow(i).getCell(4).setCellValue(subtotal.get("税小计").toString());
                sheet.getRow(i++).getCell(5).setCellValue(subtotal.get("可分成金额小计").toString());

                //分成比例
                sheet.getRow(i).getCell(0).setCellValue("分成比例");
                sheet.addMergedRegion(new CellRangeAddress(i,i,0,1));
                sheet.getRow(i++).getCell(5).setCellValue(shareProportion * 100 + "%");
                //分成金额
//                sheet.getHeader().setCenter(header.getDataFormatString());
                sheet.getRow(i).getCell(0).setCellValue("分成金额");
                sheet.addMergedRegion(new CellRangeAddress(i,i,0,1));
                sheet.addMergedRegion(new CellRangeAddress(i,i,2,4));
                sheet.getRow(i).getCell(5).setCellValue(shareAmount);
                //公司
                sheet.getRow(26).getCell(3).setCellValue((String) list.get(0).get("我方公司"));
//                sheet.addMergedRegion(new CellRangeAddress(26,26,3,5));


                String fileName = "对账单" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
                fileName = URLEncoder.encode(fileName, "UTF8");
                response.setContentType("application/vnd.ms-excel;chartset=utf-8");
                response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");
                ServletOutputStream out = response.getOutputStream();
                workbook.write(out);
                out.flush();
                out.close();
                inputStream.close();

            }else{

//                sheet.getRow(3).getCell(1).setCellValue( list.get(0).get("收款单位").toString());
//                sheet.getRow(4).getCell(1).setCellValue(list.get(0).get("收款账号").toString());
//                sheet.getRow(5).getCell(1).setCellValue(list.get(0).get("开户行").toString());
//                sheet.getRow(8).getCell(0).setCellValue(map.get("景区名称").toString());
//                sheet.getRow(8).getCell(1).setCellValue(map.get("月份").toString());
//                sheet.getRow(8).getCell(2).setCellValue(map.get("流水").toString());
//                sheet.getRow(8).getCell(3).setCellValue(map.get("手续费").toString());
//                sheet.getRow(8).getCell(4).setCellValue(map.get("可分润金额").toString());
//                sheet.getRow(8).getCell(5).setCellValue(map.get("分润金额").toString());
//                sheet.getRow(9).getCell(5).setCellValue(map.get("分润比例").toString());
//                sheet.getRow(10).getCell(5).setCellValue(map.get("税").toString());
//                sheet.getRow(11).getCell(5).setCellValue(map.get("结算").toString());
//                sheet.getRow(26).getCell(3).setCellValue(map.get("合作主体").toString());
//                String fileName = "对账单" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
//                fileName = URLEncoder.encode(fileName, "UTF8");
//                response.setContentType("application/vnd.ms-excel;chartset=utf-8");
//                response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");
//                ServletOutputStream out = response.getOutputStream();
//                workbook.write(out);
//                out.flush();
//                out.close();
//                inputStream.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }




}