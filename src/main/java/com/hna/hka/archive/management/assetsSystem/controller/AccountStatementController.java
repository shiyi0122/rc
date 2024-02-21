package com.hna.hka.archive.management.assetsSystem.controller;

import com.gexin.fastjson.JSON;
import com.hna.hka.archive.management.assetsSystem.model.AccountStatement;
import com.hna.hka.archive.management.assetsSystem.service.AccountStatementService;
import com.hna.hka.archive.management.system.model.SysUsers;
import com.hna.hka.archive.management.system.util.Constant;
import com.hna.hka.archive.management.system.util.PublicUtil;
import com.hna.hka.archive.management.system.util.ReturnModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.FileUtils;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
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
 * @program: rc
 * @description: 流水对账单
 * @author: zhaoxianglong
 * @create: 2021-09-09 10:23
 **/
@Api(tags = "流水对账单")
@RestController
@RequestMapping("/system/account_statement")
public class AccountStatementController extends PublicUtil {

    @Autowired
    private AccountStatementService service;


    @ApiOperation("公司列表")
    @ApiImplicitParam(name = "spotId", value = "景区ID")
    @CrossOrigin
    @GetMapping("/companyList")
    public ReturnModel companyList(String spotId) {
        try {
            List<AccountStatement> list = service.companyList(spotId);
            return new ReturnModel(list, "获取公司列表成功", Constant.STATE_SUCCESS, null);
        } catch (Exception e) {
            logger.error("获取公司列表失败", e);
            return new ReturnModel(null, "获取公司列表失败", "500", null);
        }
    }

    @ApiOperation("景区列表")
    @ApiImplicitParam(name = "companyId", value = "景区ID")
    @CrossOrigin
    @GetMapping("/spotList")
    public ReturnModel spotList(Long companyId) {
        try {
            List<AccountStatement> list = service.spotList(companyId);
            return new ReturnModel(list, "获取景区列表成功", Constant.STATE_SUCCESS, null);
        } catch (Exception e) {
            logger.error("获取景区列表失败", e);
            return new ReturnModel(null, "获取景区列表失败", "500", null);
        }
    }

    @ApiOperation("列表信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页码"),
            @ApiImplicitParam(name = "pageSize", value = "每页条数"),
            @ApiImplicitParam(name = "company", value = "公司ID"),
            @ApiImplicitParam(name = "spot", value = "景区ID"),
            @ApiImplicitParam(name = "beginDate", value = "开始时间"),
            @ApiImplicitParam(name = "endDate", value = "结束时间"),
            @ApiImplicitParam(name = "paymentType", value = "支付方式"),
            @ApiImplicitParam(name = "orderStatus", value = "支付状态")
    })
    @CrossOrigin
    @GetMapping("/list")
    public ReturnModel list(Integer pageNum, Integer pageSize, Long company, Long spot, String beginDate, String endDate, String paymentType, Integer orderStatus) {

        try {
            List<AccountStatement> list = service.list(company, spot, beginDate, endDate, paymentType, orderStatus, pageNum, pageSize);
            Map<String, Object> count = service.getCount(company, spot, beginDate, endDate, paymentType, orderStatus);
            return new ReturnModel(list, "获取列表信息成功", Constant.STATE_SUCCESS, count.get("sum").toString() , Integer.parseInt(count.get("count").toString()));
        } catch (Exception e) {
            logger.error("获取列表信息失败", e);
            return new ReturnModel(null, "获取列表信息失败", "500", null);
        }
    }

    @ApiOperation("预览")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "company", value = "公司ID"),
            @ApiImplicitParam(name = "spot", value = "景区ID"),
            @ApiImplicitParam(name = "beginDate", value = "开始时间"),
            @ApiImplicitParam(name = "endDate", value = "结束时间"),
            @ApiImplicitParam(name = "paymentType", value = "支付方式"),
            @ApiImplicitParam(name = "paymentStatus", value = "支付状态")
    })
    @CrossOrigin
    @GetMapping("/preview")
    public ReturnModel preview(Long company, Long spot, String beginDate, String endDate, String paymentType,String paymentStatus) {
        HashMap<String, Object> map = service.preview(company, spot, beginDate, endDate, paymentType,paymentStatus);
        return new ReturnModel(map, "预览信息成功", Constant.STATE_SUCCESS, null);
    }

    @ApiOperation("下载")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "company", value = "公司ID"),
            @ApiImplicitParam(name = "spot", value = "景区ID"),
            @ApiImplicitParam(name = "beginDate", value = "开始时间"),
            @ApiImplicitParam(name = "endDate", value = "结束时间"),
            @ApiImplicitParam(name = "paymentType", value = "支付方式")
    })
    @CrossOrigin
    @GetMapping("/download")
    public void download(Long company, Long spot, String beginDate, String endDate, String paymentType,String paymentStatus, HttpServletResponse response) {

        try {
            //解决服务器中jar包无法访问resources下文件的问题
            ClassPathResource resource = new ClassPathResource("static/xls/test.xlsx");
            InputStream in = resource.getInputStream();
            //创建临时文件(空文件)  prefx:test  suffix: .png，该文件会默认创建在你用户的更目录下，具体哪个自己打印出来看看就知道
            File file = File.createTempFile("test", ".xlsx");
            //将获取的流转为文件，在转换过后我们的资源文件就被copy到前面创建的临时文件中了
            FileUtils.copyInputStreamToFile(in, file);



            MultipartFile multipartFile = new MockMultipartFile(file.getName(), new FileInputStream(file));
            InputStream inputStream = multipartFile.getInputStream();
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
            XSSFSheet sheet = workbook.getSheetAt(0);
            HashMap<String, Object> map = service.preview(company, spot, beginDate, endDate, paymentType,paymentStatus);
            sheet.getRow(3).getCell(1).setCellValue(map.get("收款账户").toString());
            sheet.getRow(4).getCell(1).setCellValue(map.get("收款账号").toString());
            sheet.getRow(5).getCell(1).setCellValue(map.get("开户行").toString());
            sheet.getRow(8).getCell(0).setCellValue(map.get("景区名称").toString());
            sheet.getRow(8).getCell(1).setCellValue(map.get("月份").toString());
            sheet.getRow(8).getCell(2).setCellValue(map.get("流水").toString());
            sheet.getRow(8).getCell(3).setCellValue(map.get("手续费").toString());
            sheet.getRow(8).getCell(4).setCellValue(map.get("可分润金额").toString());
            sheet.getRow(8).getCell(5).setCellValue(map.get("分润金额").toString());
            sheet.getRow(9).getCell(5).setCellValue(map.get("分润比例").toString());
            sheet.getRow(10).getCell(5).setCellValue(map.get("税").toString());
            sheet.getRow(11).getCell(5).setCellValue(map.get("结算").toString());
            sheet.getRow(26).getCell(3).setCellValue(map.get("合作主体").toString());
            String fileName = "流水对账单" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
            fileName = URLEncoder.encode(fileName, "UTF8");
            response.setContentType("application/vnd.ms-excel;chartset=utf-8");
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");
            ServletOutputStream out = response.getOutputStream();
            workbook.write(out);
            out.flush();
            out.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @ApiOperation("核实")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "company", value = "公司ID"),
            @ApiImplicitParam(name = "spot", value = "景区ID"),
            @ApiImplicitParam(name = "beginDate", value = "开始时间"),
            @ApiImplicitParam(name = "endDate", value = "结束时间"),
            @ApiImplicitParam(name = "paymentType", value = "支付方式"),
            @ApiImplicitParam(name = "orderStatus", value = "支付状态")
    })
    @CrossOrigin
    @GetMapping("/verify")
    public ReturnModel verify( Long company, Long spot, String beginDate, String endDate, String paymentType, Integer orderStatus) {

        ReturnModel returnModel = new ReturnModel();

        SysUsers sysUser = getSysUser();
        int i = service.verify(company,spot,beginDate,endDate,paymentType,orderStatus,sysUser.getUserId(), sysUser.getUserName());

        if (i>0){
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_SUCCESS);
            returnModel.setMsg("核实成功");
        }else{
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("核实失败");
        }
        return returnModel;
    }
    @ApiOperation("是否核实")
    @CrossOrigin
    @GetMapping("/isVerify")
    public ReturnModel isVerify( Long company, Long spot, String beginDate, String endDate, String paymentType, Integer orderStatus) {

        ReturnModel returnModel = new ReturnModel();

        int i = service.isVerify(company,spot,beginDate,endDate,paymentType,orderStatus);

        returnModel.setData(i);
        returnModel.setMsg("获取成功");
        returnModel.setState(Constant.STATE_SUCCESS);

        return returnModel;
    }




}
