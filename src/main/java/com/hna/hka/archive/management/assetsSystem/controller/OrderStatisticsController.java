package com.hna.hka.archive.management.assetsSystem.controller;

import com.hna.hka.archive.management.assetsSystem.service.OrderStatisticsService;
import com.hna.hka.archive.management.system.util.Constant;
import com.hna.hka.archive.management.system.util.PublicUtil;
import com.hna.hka.archive.management.system.util.ReturnModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @program: rc
 * @description: 订单统计
 * @author: zhaoxianglong
 * @create: 2021-09-23 09:01
 **/
@Api(tags = "订单统计")
@RestController
@RequestMapping("/system/order_statistics")
public class OrderStatisticsController extends PublicUtil {

    @Autowired
    OrderStatisticsService service;

    @ApiOperation("订单统计数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "companyId" , value = "公司ID"),
            @ApiImplicitParam(name = "spotId" , value = "景区ID"),
            @ApiImplicitParam(name = "type" , value = "不传"),
            @ApiImplicitParam(name = "beginTime" , value = "起始时间"),
            @ApiImplicitParam(name = "endTime" , value = "截止时间"),
    })
    @CrossOrigin
    @GetMapping("/findData")
    public ReturnModel findData(Long company , Long spot , String beginTime , String endTime){
        try {
            List list = service.findData(company , spot , beginTime , endTime);
            return new ReturnModel(list , "数据获取成功" , Constant.STATE_SUCCESS , null);
        } catch (Exception e){
            logger.error("获取数据失败" , e);
            return new ReturnModel(e.getMessage() , "获取数据失败" , "500" , null);
        }
    }
}
