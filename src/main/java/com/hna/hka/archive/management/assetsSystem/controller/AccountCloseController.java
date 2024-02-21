package com.hna.hka.archive.management.assetsSystem.controller;

import com.hna.hka.archive.management.assetsSystem.model.AccountClose;
import com.hna.hka.archive.management.assetsSystem.service.AccountCloseService;
import com.hna.hka.archive.management.system.util.Constant;
import com.hna.hka.archive.management.system.util.FileUtil;
import com.hna.hka.archive.management.system.util.PublicUtil;
import com.hna.hka.archive.management.system.util.ReturnModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;


/**
 * @program: rc
 * @description: 结账流水
 * @author: zhaoxianglong
 * @create: 2021-09-13 14:10
 **/
@Api(tags = "结账流水")
@RestController
@RequestMapping("/system/account_close")
public class AccountCloseController extends PublicUtil {

    @Autowired
    private AccountCloseService service;

    @ApiOperation("获取结账流水列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "company" , value = "收款单位Id"),
            @ApiImplicitParam(name = "spot" , value = "景区Id"),
            @ApiImplicitParam(name = "beginDate" , value = "开始时间"),
            @ApiImplicitParam(name = "beginDate" , value = "结束时间"),
            @ApiImplicitParam(name = "type" , value = "状态"),
            @ApiImplicitParam(name = "pageNum" , value = "页码"),
            @ApiImplicitParam(name = "pageSize" , value = "每页条数"),
    })
    @CrossOrigin
    @GetMapping(value = "/list")
    public ReturnModel list(String company , String spot , String beginDate , String endDate , Integer type , Integer pageNum , Integer pageSize){
        try{
            List<AccountClose> list = service.list(company , spot , beginDate , endDate , type , pageNum , pageSize);
            return new ReturnModel(list , "获取结账流水列表成功" , Constant.STATE_SUCCESS , null , service.getCount(company , spot , beginDate , endDate , type));
        } catch (Exception e){
            logger.error("获取结账流水列表失败" , e);
            return new ReturnModel(e.getMessage() , "获取结账流水列表失败" , "500" , null);
        }
    }

    @ApiOperation("编辑")
    @CrossOrigin
    @PostMapping("/update")
    public ReturnModel update(@RequestBody AccountClose accountClose){
        try {
            Integer result = service.update(accountClose);
            if (result > 0){
                return new ReturnModel(result  , "结账流水更改成功" , Constant.STATE_SUCCESS , null);
            } else {
                return new ReturnModel("结账流水不存在或已被删除" , "结账流水更改失败" , Constant.STATE_FAILURE , null);
            }
        } catch (Exception  e){
            logger.error("结账流水更改失败" , e);
            return new ReturnModel(e.getMessage() , "结账流水更改失败" , "500" , null);
        }
    }

    @ApiOperation("导出")
    @CrossOrigin
    @GetMapping("/download")
    public void download(String company , String spot , String beginDate , String endDate , Integer type , HttpServletResponse response, HttpServletRequest request){
        try{
            String spot1 = request.getParameter("spot");
            List<AccountClose> list = service.list(company , spot , beginDate , endDate , type , null , null);
            String dateTime = DateFormatUtils.format(new Date(), "yyyyMMddHHmm");
            FileUtil.exportExcel(FileUtil.getWorkbook("结账流水", "结账流水", AccountClose.class, list),"结账流水"+ dateTime +".xls",response);

        } catch (Exception e){
            logger.error("获取结账流水列表失败" , e);
        }
    }

//    @ApiOperation("财务核实结算状态")
//    @CrossOrigin
//    @GetMapping("/updateVerifyStatus")
//    public ReturnModel updateVerifyStatus(Long id,String type,String status){
//
//
//
//
//    }



}
