package com.hna.hka.archive.management.assetsSystem.controller;

import com.alibaba.fastjson.JSON;
import com.hna.hka.archive.management.assetsSystem.service.RTStatisticsService;
import com.hna.hka.archive.management.system.util.Constant;
import com.hna.hka.archive.management.system.util.DateUtil;
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

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: rc
 * @description: 机器人运营时长统计
 * @author: zhaoxianglong
 * @create: 2021-09-25 10:40
 **/
@Api(tags = "机器人运营时长统计")
@CrossOrigin
@RestController
@RequestMapping("/system/robot_time_statistics")
public class RTStatisticsController extends PublicUtil {

    @Autowired
    RTStatisticsService service;


    @ApiOperation("按时间统计运营时长")
    @GetMapping("/timeList")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "order", value = "排序,example: company_ID desc"),
            @ApiImplicitParam(name = "companyId", value = "公司ID"),
            @ApiImplicitParam(name = "spotId", value = "景区ID"),
            @ApiImplicitParam(name = "type", value = "统计方式:1.按景区;2.按机器人"),
            @ApiImplicitParam(name = "status", value = "运营状态:1.已运营,2.测试,3.预运营"),
            @ApiImplicitParam(name = "dateType", value = "日期类型:1.日,2.月,3.年"),
            @ApiImplicitParam(name = "startDate", value = "起始时间"),
            @ApiImplicitParam(name = "endDate", value = "截止时间"),
            @ApiImplicitParam(name = "timeType", value = "时长类型:1.运营时长,2.平均单台运营时长"),
            @ApiImplicitParam(name = "totalTime", value = "运营时长"),
            @ApiImplicitParam(name = "robotCode", value = "机器人编码"),
            @ApiImplicitParam(name = "pageNum", value = "页码"),
            @ApiImplicitParam(name = "pageSize", value = "每页条数")
    })
    public ReturnModel timeList(String order, Long companyId, Long spotId, Integer type, String status, Integer dateType, String startDate, String endDate, Integer timeType, Double totalTime, String robotCode, Integer pageNum, Integer pageSize) {
        try {
            List<HashMap> list = service.timeList(order, companyId, spotId, type, status, dateType, startDate, endDate, timeType, totalTime, robotCode, pageNum, pageSize);
            HashMap count = service.getTimeCount(companyId, spotId, type, status, dateType, startDate, endDate, timeType, totalTime, robotCode);
            return new ReturnModel(list, "success", Constant.STATE_SUCCESS, JSON.toJSONString(count));
        } catch (Exception e) {
            logger.error("error", e);
            return new ReturnModel(e, "error", "500", null);
        }
    }

    @ApiOperation("按时间统计运营流水")
    @GetMapping("/amountList")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "order", value = "排序,example: company_ID desc"),
            @ApiImplicitParam(name = "payType", value = "交易方式:1.微信,2.储值,3.微信+储值,4.押金抵扣"),
            @ApiImplicitParam(name = "companyId", value = "公司ID"),
            @ApiImplicitParam(name = "spotId", value = "景区ID"),
            @ApiImplicitParam(name = "status", value = "运营状态:1.已运营,2.测试,3.预运营"),
            @ApiImplicitParam(name = "dateType", value = "日期类型:1.日,2.月,3.年"),
            @ApiImplicitParam(name = "startDate", value = "起始时间"),
            @ApiImplicitParam(name = "endDate", value = "截止时间"),
            @ApiImplicitParam(name = "amountType", value = "金额类型:1.交易金额,2.平均单台交易金额"),
            @ApiImplicitParam(name = "totalAmount", value = "交易金额"),
            @ApiImplicitParam(name = "pageNum", value = "页码"),
            @ApiImplicitParam(name = "pageSize", value = "每页条数"),
            @ApiImplicitParam(name = "type", value = "统计方式:1.景区;2.机器人"),
            @ApiImplicitParam(name = "robotCode", value = "机器人编码"),

    })
    public ReturnModel amountList(Integer type, String robotCode, String order, String payType, Long companyId, Long spotId, String status, Integer dateType, String startDate, String endDate, Integer amountType, Double totalAmount, Integer pageNum, Integer pageSize) {
        try {
            List<HashMap> list = service.amountList(type, robotCode, order, payType, companyId, spotId, status, dateType, startDate, endDate, amountType, totalAmount, pageNum, pageSize);
            HashMap count = service.getAmountCount(type, robotCode, order, payType, companyId, spotId, status, dateType, startDate, endDate, amountType, totalAmount);
            return new ReturnModel(list, "success", Constant.STATE_SUCCESS, JSON.toJSONString(count));
        } catch (Exception e) {
            logger.error("error", e);
            return new ReturnModel(e, "error", "500", null);
        }
    }


    @ApiOperation("按景区统计运营时长")
    @GetMapping("/spotTimeList")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "order", value = "排序,example: company_ID desc"),
            @ApiImplicitParam(name = "companyId", value = "公司ID"),
            @ApiImplicitParam(name = "spotId", value = "景区ID"),
            @ApiImplicitParam(name = "type", value = "统计方式:1.按景区;2.按机器人"),
            @ApiImplicitParam(name = "status", value = "运营状态:1.已运营,2.测试,3.预运营"),
            @ApiImplicitParam(name = "dateType", value = "日期类型:1.日,2.月,3.年"),
            @ApiImplicitParam(name = "startDate", value = "起始时间"),
            @ApiImplicitParam(name = "endDate", value = "截止时间"),
            @ApiImplicitParam(name = "timeType", value = "时长类型:1.运营时长,2.平均单台运营时长"),
            @ApiImplicitParam(name = "totalTime", value = "运营时长"),
            @ApiImplicitParam(name = "robotCode", value = "机器人编码"),
            @ApiImplicitParam(name = "pageNum", value = "页码"),
            @ApiImplicitParam(name = "pageSize", value = "每页条数")
    })
    public ReturnModel spotTimeList(String order, Long companyId, Long spotId, Integer type, String status, Integer dateType, String startDate, String endDate, Integer timeType, Double totalTime, String robotCode, Integer pageNum, Integer pageSize) {
        try {
            List<HashMap> list = service.spotTimeList(order, companyId, spotId, type, status, dateType, startDate, endDate, timeType, totalTime, robotCode, pageNum, pageSize);
            HashMap count = service.getSpotTimeCount(companyId, spotId, type, status, dateType, startDate, endDate, timeType, totalTime, robotCode);
            return new ReturnModel(list, "success", Constant.STATE_SUCCESS, JSON.toJSONString(count));
        } catch (Exception e) {
            logger.error("error", e);
            return new ReturnModel(e, "error", "500", null);
        }
    }

    @ApiOperation("按景区统计运营流水")
    @GetMapping("/spotAmountList")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "order", value = "排序,example: company_ID desc"),
            @ApiImplicitParam(name = "type", value = "统计方式:1.按景区;2.按机器人"),
            @ApiImplicitParam(name = "RobotCode", value = "统计方式:1.按景区;2.按机器人"),
            @ApiImplicitParam(name = "payType", value = "交易方式:1.微信,2.储值,3.微信+储值,4.押金抵扣"),
            @ApiImplicitParam(name = "companyId", value = "公司ID"),
            @ApiImplicitParam(name = "spotId", value = "景区ID"),
            @ApiImplicitParam(name = "status", value = "运营状态:1.已运营,2.测试,3.预运营"),
            @ApiImplicitParam(name = "dateType", value = "日期类型:1.日,2.月,3.年"),
            @ApiImplicitParam(name = "startDate", value = "起始时间"),
            @ApiImplicitParam(name = "endDate", value = "截止时间"),
            @ApiImplicitParam(name = "amountType", value = "金额类型:1.交易金额,2.平均单台交易金额"),
            @ApiImplicitParam(name = "totalAmount", value = "交易金额"),
            @ApiImplicitParam(name = "pageNum", value = "页码"),
            @ApiImplicitParam(name = "pageSize", value = "每页条数")
    })
    public ReturnModel spotAmountList(Integer type, String robotCode, String order, String payType, Long companyId, Long spotId, String status, Integer dateType, String startDate, String endDate, Integer amountType, Double totalAmount, Integer pageNum, Integer pageSize) {
        try {
            String s = DateUtil.crutDate();
            if (endDate.equals(DateUtil.crutDate())) {

//                List<Map<String,Object>> listTwo  =  service.spotAmountlistNewTow(type , robotCode , order , payType , companyId , spotId , status , dateType , startDate , endDate , amountType , totalAmount , pageNum , pageSize);
                List<HashMap> list = service.spotAmountListNew(type, robotCode, order, payType, companyId, spotId, status, dateType, startDate, endDate, amountType, totalAmount, pageNum, pageSize);
                HashMap count = service.getSpotAmountCountNew(type, robotCode, order, payType, companyId, spotId, status, dateType, startDate, endDate, amountType, totalAmount);
                return new ReturnModel(list, "success", Constant.STATE_SUCCESS, JSON.toJSONString(count));

            } else {
                List<HashMap> list = service.spotAmountList(type, robotCode, order, payType, companyId, spotId, status, dateType, startDate, endDate, amountType, totalAmount, pageNum, pageSize);
                HashMap count = service.getSpotAmountCount(type, robotCode, order, payType, companyId, spotId, status, dateType, startDate, endDate, amountType, totalAmount);

                return new ReturnModel(list, "success", Constant.STATE_SUCCESS, JSON.toJSONString(count));
            }
        } catch (Exception e) {
            logger.error("error", e);
            return new ReturnModel(e, "error", "500", null);
        }
    }

    @ApiOperation("公司列表")
    @GetMapping("/companyList")
    public ReturnModel companyList(Long spotId) {
        try {
            List<HashMap> list = service.companyList(spotId);
            return new ReturnModel(list, "success", Constant.STATE_SUCCESS, null);
        } catch (Exception e) {
            logger.error("error", e);
            return new ReturnModel(e, "error", "500", null);
        }
    }

    @ApiOperation("景区列表")
    @GetMapping("/spotList")
    public ReturnModel spotList(Long companyId) {
        try {
            List<HashMap> list = service.spotList(companyId);
            return new ReturnModel(list, "success", Constant.STATE_SUCCESS, null);
        } catch (Exception e) {
            logger.error("error", e);
            return new ReturnModel(e, "error", "500", null);
        }
    }


    @ApiOperation("按时间统计运营时长多时间多景区报表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "companyId", value = "公司ID"),
            @ApiImplicitParam(name = "spotId", value = "景区ID"),
            @ApiImplicitParam(name = "dateType", value = "日期类型:1.日,2.月,3.年"),
            @ApiImplicitParam(name = "startDate", value = "起始时间"),
            @ApiImplicitParam(name = "endDate", value = "截止时间")
    })
    @GetMapping("/timeChart")
    public ReturnModel timeChart(Long spotId, Long companyId, Integer dateType, String startDate, String endDate) {
        try {
            List<HashMap> list = service.timeChart(companyId, spotId, dateType, startDate, endDate);
            return new ReturnModel(list, "success", Constant.STATE_SUCCESS, null);
        } catch (Exception e) {
            logger.error("error", e);
            return new ReturnModel(e, "error", "500", null);
        }
    }

    @ApiOperation("按时间统计运营流水多时间多景区报表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "companyId", value = "公司ID"),
            @ApiImplicitParam(name = "spotId", value = "景区ID"),
            @ApiImplicitParam(name = "dateType", value = "日期类型:1.日,2.月,3.年"),
            @ApiImplicitParam(name = "startDate", value = "起始时间"),
            @ApiImplicitParam(name = "endDate", value = "截止时间")
    })
    @GetMapping("/amountChart")
    public ReturnModel amountChart(Long spotId, Long companyId, Integer dateType, String startDate, String endDate) {
        try {
            List<HashMap> list = service.amountChart(companyId, spotId, dateType, startDate, endDate);
            return new ReturnModel(list, "success", Constant.STATE_SUCCESS, null);
        } catch (Exception e) {
            logger.error("error", e);
            return new ReturnModel(e, "error", "500", null);
        }
    }


    @ApiOperation("统计营收曲线")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "spotId", value = "景区ID"),
            @ApiImplicitParam(name = "dateType", value = "日期类型:1.日,2.月,3.年"),
            @ApiImplicitParam(name = "startDate", value = "起始时间"),
            @ApiImplicitParam(name = "endDate", value = "截止时间")
    })
    @GetMapping("/amountByTime")
    public ReturnModel amountByTime(Long spotId, Integer dateType, String startDate, String endDate) {
        try {
            List<HashMap> list = service.amountByTime(spotId, dateType, startDate, endDate);
            return new ReturnModel(list, "success", Constant.STATE_SUCCESS, null);
        } catch (Exception e) {
            logger.error("error", e);
            return new ReturnModel(e, "error", "500", null);
        }
    }
}
