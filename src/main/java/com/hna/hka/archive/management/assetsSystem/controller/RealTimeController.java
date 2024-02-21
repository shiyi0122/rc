package com.hna.hka.archive.management.assetsSystem.controller;

import com.alibaba.excel.event.Order;
import com.hna.hka.archive.management.assetsSystem.model.SearchRobot;
import com.hna.hka.archive.management.system.model.SysOrder;
import com.hna.hka.archive.management.system.service.RealTimeService;
import com.hna.hka.archive.management.system.service.SysOrderService;
import com.hna.hka.archive.management.system.util.Constant;
import com.hna.hka.archive.management.system.util.ReturnModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @program: rc
 * @description: 当日营收曲线
 * @author: zhaoxianglong
 * @create: 2021-10-29 14:39
 **/
@Api(tags = "当日营收曲线")
@RequestMapping("/system/realTime")
@RestController
public class RealTimeController {

    @Autowired
    RealTimeService service;
    @Autowired
    SysOrderService sysOrderService;



    @GetMapping("/line")
    public ReturnModel line(Long spotId){
        try {
            List<HashMap> list = service.line(spotId);
            return new ReturnModel(list , "success" , Constant.STATE_SUCCESS , null);
        } catch (Exception e){
            e.printStackTrace();
            return new ReturnModel(e.getMessage() , "error" , "500" , null);
        }
    }

    @GetMapping("/lineN")
    public ReturnModel lineN(String spotId,String startTime,String endTime,String time,String type,String multipeDate){
        try {
            HashMap<String, Object> returnMap = service.lineN(spotId,startTime,endTime,time,type,multipeDate);
            return new ReturnModel(returnMap , "success" , Constant.STATE_SUCCESS , null);
        } catch (Exception e){
            e.printStackTrace();
            return new ReturnModel(e.getMessage() , "error" , "500" , null);
        }
    }







}
