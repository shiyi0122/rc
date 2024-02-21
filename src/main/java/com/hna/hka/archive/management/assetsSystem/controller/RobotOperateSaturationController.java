package com.hna.hka.archive.management.assetsSystem.controller;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.hna.hka.archive.management.assetsSystem.model.SysRobotPartsManagement;
import com.hna.hka.archive.management.assetsSystem.service.RobotOperateSaturationService;
import com.hna.hka.archive.management.system.util.Constant;
import com.hna.hka.archive.management.system.util.DateUtil;
import com.hna.hka.archive.management.system.util.PageDataResult;
import com.hna.hka.archive.management.system.util.ReturnModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @Author zhang
 * @Date 2022/3/3 10:59
 * 机器人运营饱和度
 */
@Api(tags = "机器人运营饱和度")
@RequestMapping("/system/operateSaturation")
@RestController
public class RobotOperateSaturationController {

    @Autowired
    RobotOperateSaturationService robotOperateSaturationService;

    /**
     * 根据景区查询机器人饱和度
     */

    @ApiOperation("查询机器人饱和度")
    @GetMapping("/getOperateSaturationList")
    public ReturnModel getOperateSaturationList(Long spotId , Integer pageNum , Integer pageSize) {
        ReturnModel returnModel = new ReturnModel();

        try{
            List<HashMap> list = robotOperateSaturationService.getOperateSaturationList(spotId,pageNum,pageSize);
            HashMap  map =   robotOperateSaturationService.getOperateSaturationSettlement(spotId);
            int i =  robotOperateSaturationService.getOperateSaturationListCount(spotId);
            if (list.size() > 0){
                returnModel.setData(list);
                returnModel.setState(Constant.STATE_SUCCESS);
                returnModel.setMsg("success");
                returnModel.setType(JSON.toJSONString(map));
                returnModel.setTotal(i);
            }
            System.out.println("**************************"+returnModel);
            return returnModel;
        }catch (Exception e){
            e.printStackTrace();
            return new ReturnModel(e , "error" , "500" , null);
        }

    }

    @ApiOperation("机器人饱和度折线图")
    @GetMapping("/getOperateSaturationLineChart")
    public PageDataResult getOperateSaturationLineChart(Long spotId) {
        PageDataResult pageDataResult = new PageDataResult();

        try {
            List<String> list = DateUtil.dateHoursList(new Date());
            List<String> operateSaturationCensus = robotOperateSaturationService.getOperateSaturationCensus(list,spotId);

            pageDataResult.setData(operateSaturationCensus);
            pageDataResult.setList(list);
            return pageDataResult;

        } catch (ParseException parseException) {
            parseException.printStackTrace();
        }
        return pageDataResult;
    }


}
