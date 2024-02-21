package com.hna.hka.archive.management.assetsSystem.controller;

import com.hna.hka.archive.management.assetsSystem.model.SubscriptionInformation;
import com.hna.hka.archive.management.assetsSystem.model.SysScenicSubscriptionInformationTax;
import com.hna.hka.archive.management.assetsSystem.service.SubscriptionInformationService;
import com.hna.hka.archive.management.system.util.Constant;
import com.hna.hka.archive.management.system.util.ReturnModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @program: rc
 * @description: 签约信息
 * @author: zhaoxianglong
 * @create: 2021-09-09 13:54
 **/
@Slf4j
@Api(tags = "签约信息")
@RestController
@RequestMapping("/system/subscriptionInformation")
public class SubscriptionInformationController {

    @Autowired
    SubscriptionInformationService service;

    @ApiOperation("公司列表")
    @ApiImplicitParam(name = "spotId" , value = "景区ID")
    @CrossOrigin
    @GetMapping("/companyList")
    public ReturnModel companyList(String spotId) {
        try {
            List<Map<String, String>> companyList = service.companyList(spotId);
            return new ReturnModel(companyList, "公司信息获取成功", Constant.STATE_SUCCESS, null);
        } catch (Exception e){
            log.error("公司信息获取失败" , e);
            return new ReturnModel(e.getMessage(), "公司信息获取失败", "500", null);
        }
    }

    @ApiOperation("景区列表")
    @ApiImplicitParam(name = "companyId" , value = "公司ID")
    @CrossOrigin
    @GetMapping("/spotList")
    public ReturnModel spotList(Long companyId) {
        try {
            List<Map<String, String>> companyList = service.spotList(companyId);
            return new ReturnModel(companyList, "景区信息获取成功", Constant.STATE_SUCCESS, null);
        } catch (Exception e){
            log.error("景区信息获取失败" , e);
            return new ReturnModel(e.getMessage(), "景区信息获取失败", "500", null);
        }
    }

    @ApiOperation("列表信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "companyId" , value = "公司Id"),
            @ApiImplicitParam(name = "spotId" , value = "景区id"),
            @ApiImplicitParam(name = "charge" , value = "手续费"),
            @ApiImplicitParam(name = "tax" , value = "税点"),
            @ApiImplicitParam(name = "pageNum" , value = "页码"),
            @ApiImplicitParam(name = "pageSize" , value = "每页条数")
    })
    @CrossOrigin
    @GetMapping("/list")
    public ReturnModel list(Long companyId , String spotId , BigDecimal charge , BigDecimal tax ,String beginDate,String endDate , Integer pageNum , Integer pageSize){
        try {
            List<SubscriptionInformation> list = service.list(companyId , spotId , charge , tax , pageNum , pageSize,beginDate,endDate);
            Integer count = service.getCount(companyId , spotId , charge , tax);
            return new ReturnModel(list, "列表信息获取成功", Constant.STATE_SUCCESS, null , count);
        } catch (Exception e){
            log.error("列表信息获取失败" , e);
            return new ReturnModel(e.getMessage(), "列表信息获取失败", "500", null);
        }
    }

    @ApiOperation("添加签约信息")
    @CrossOrigin
    @PostMapping("/add")
    public ReturnModel add(@RequestBody SubscriptionInformation subscriptionInformation){
        try {
            Integer result = service.add(subscriptionInformation);
            if (result > 0){
                return new ReturnModel(result , "签约信息添加成功" , Constant.STATE_SUCCESS , null);
            } else {
                return new ReturnModel("未知原因", "签约信息添加失败", Constant.STATE_FAILURE, null);
            }
        } catch (Exception e){
            log.error("签约信息添加失败" , e);
            return new ReturnModel(e.getMessage(), "签约信息添加失败", "500", null);
        }
    }


    @ApiOperation("编辑签约信息")
    @CrossOrigin
    @GetMapping("/queryById")
    public ReturnModel queryById(Long id){
        try {
            SubscriptionInformation subscriptionInformation = service.queryById(id);
            if (subscriptionInformation != null ){
                return new ReturnModel(subscriptionInformation , "签约信息获取成功" , Constant.STATE_SUCCESS , null);
            } else {
                return new ReturnModel("未知原因", "签约信息获取失败", Constant.STATE_FAILURE, null);
            }
        } catch (Exception e){
            log.error("签约信息获取失败" , e);
            return new ReturnModel(e.getMessage(), "签约信息获取失败", "500", null);
        }
    }

    @ApiOperation("修改签约信息")
    @CrossOrigin
    @PostMapping("/edit")
    public ReturnModel edit(@RequestBody SubscriptionInformation subscriptionInformation){
        try {
            Integer result = service.edit(subscriptionInformation);
            if (result > 0){
                return new ReturnModel(result , "签约信息修改成功" , Constant.STATE_SUCCESS , null);
            } else {
                return new ReturnModel("当前数据不存在或已被删除", "签约信息修改失败", Constant.STATE_FAILURE, null);
            }
        } catch (Exception e){
            log.error("签约信息修改失败" , e);
            return new ReturnModel(e.getMessage(), "签约信息修改失败", "500", null);
        }
    }

    @ApiOperation("删除签约信息")
    @CrossOrigin
    @PostMapping("/delete")
    public ReturnModel delete(Long id){
        try {
            Integer result = service.delete(id);
            if (result > 0){
                return new ReturnModel(result , "签约信息删除成功" , Constant.STATE_SUCCESS , null);
            } else {
                return new ReturnModel("当前数据不存在或已被删除", "签约信息删除失败", Constant.STATE_FAILURE, null);
            }
        } catch (Exception e){
            log.error("签约信息删除失败" , e);
            return new ReturnModel(e.getMessage(), "签约信息删除失败", "500", null);
        }
    }

    @ApiOperation("新增修改时合作公司列表")
    @CrossOrigin
    @GetMapping("/allCompany")
    public ReturnModel allCompany(String spotId){
        try {
            List<Map<String, String>> companyList = service.allCompany(spotId);
            return new ReturnModel(companyList, "新增修改时合作公司列表获取成功", Constant.STATE_SUCCESS, null);
        } catch (Exception e){
            log.error("新增修改时合作公司列表获取失败" , e);
            return new ReturnModel(e.getMessage(), "新增修改时合作公司列表获取失败", "500", null);
        }
    }

    @ApiOperation("新增修改时景区列表")
    @CrossOrigin
    @GetMapping("/allSpot")
    public ReturnModel allSpot(){
        try {
            List<Map<String, String>> companyList = service.allSpot();
            return new ReturnModel(companyList, "新增修改时景区列表获取成功", Constant.STATE_SUCCESS, null);
        } catch (Exception e){
            log.error("新增修改时景区列表获取失败" , e);
            return new ReturnModel(e.getMessage(), "新增修改时景区列表获取失败", "500", null);
        }
    }

    @ApiOperation("新增修改时合作主体列表")
    @CrossOrigin
    @GetMapping("/allSubject")
    public ReturnModel allSubject(){
        try {
            List<Map<String, String>> companyList = service.allSubject();
            return new ReturnModel(companyList, "新增修改时合作主体列表获取成功", Constant.STATE_SUCCESS, null);
        } catch (Exception e){
            log.error("新增修改时合作主体列表获取失败" , e);
            return new ReturnModel(e.getMessage(), "新增修改时合作主体列表获取失败", "500", null);
        }
    }

    @ApiOperation("签约公司列表")
    @CrossOrigin
    @GetMapping("/companyListAll")
    public ReturnModel companyListAll(){
        try {
            List<Map<String, String>> companyList = service.companyListAll();
            return new ReturnModel(companyList, "签约公司列表获取成功", Constant.STATE_SUCCESS, null);
        } catch (Exception e){
            log.error("签约公司列表获取失败" , e);
            return new ReturnModel(e.getMessage(), "签约公司列表获取失败", "500", null);
        }
    }

    @ApiOperation("根据景区和公司id或者公司相关信息")
    @CrossOrigin
    @GetMapping("/getSpotIdCompanyIdData")
    public ReturnModel getSpotIdCompanyIdData(Long spotId,Long companyId){
        ReturnModel returnModel = new ReturnModel();

        List<Map<String, String>> list = service.getSpotIdCompanyIdData(spotId,companyId);

        if (list.size()>0){
            returnModel.setData(list.get(0));
        }else{
            returnModel.setData("");
        }
        returnModel.setState(Constant.STATE_SUCCESS);
        returnModel.setMsg("获取成功");
        return returnModel;
    }


    @ApiOperation("获取税率公式")
    @CrossOrigin
    @GetMapping("/getTaxRateFormula")
    public ReturnModel getTaxRateFormula(){

        ReturnModel returnModel = new ReturnModel();
        List<SysScenicSubscriptionInformationTax> list =  service.getTaxRateFormula();
        returnModel.setData(list);
        returnModel.setState(Constant.STATE_SUCCESS);
        returnModel.setMsg("获取成功");

        return returnModel;
    }



}
