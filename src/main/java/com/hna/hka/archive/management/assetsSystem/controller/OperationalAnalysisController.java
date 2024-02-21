package com.hna.hka.archive.management.assetsSystem.controller;

import com.hna.hka.archive.management.assetsSystem.model.ScenicSpot;
import com.hna.hka.archive.management.assetsSystem.service.OperationalAnalysisService;
import com.hna.hka.archive.management.system.util.Constant;
import com.hna.hka.archive.management.system.util.PublicUtil;
import com.hna.hka.archive.management.system.util.ReturnModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @program: rc
 * @description: 运营分析
 * @author: zhaoxianglong
 * @create: 2021-09-27 10:42
 **/
@Api(tags = "运营分析")
@RestController
@CrossOrigin
@RequestMapping("/system/operational_analysis")
public class OperationalAnalysisController extends PublicUtil {

    @Autowired
    OperationalAnalysisService service;


    @ApiOperation("运营时长分析")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type" , value = "统计类型:1.月,2.年"),
            @ApiImplicitParam(name = "beginDate" , value = "起始时间"),
            @ApiImplicitParam(name = "endDate" , value = "截止时间"),
    })
    @GetMapping("/timeAnalysis")
    public ReturnModel timeAnalysis(@NotNull Integer type , String beginDate , String endDate){
        try{
            HashMap<String, ArrayList> map = service.timeAnalysis(type, beginDate, endDate);
            return new ReturnModel(map , "success" , Constant.STATE_SUCCESS , null);
        } catch (Exception e){
            logger.error(e.getMessage() , e);
            return new ReturnModel(e.getMessage() , "error" , "500" , null);
        }
    }

    @ApiOperation("运营流水分析")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type" , value = "统计类型:1.月,2.年"),
            @ApiImplicitParam(name = "beginDate" , value = "起始时间"),
            @ApiImplicitParam(name = "endDate" , value = "截止时间"),
    })
    @GetMapping("/amountAnalysis")
    public ReturnModel amountAnalysis(@NotNull Integer type , String beginDate , String endDate){
        try{
            HashMap<String, ArrayList> map = service.amountAnalysis(type, beginDate, endDate);
            return new ReturnModel(map , "success" , Constant.STATE_SUCCESS , null);
        } catch (Exception e){
            logger.error(e.getMessage() , e);
            return new ReturnModel(e.getMessage() , "error" , "500" , null);
        }
    }


    @ApiOperation("景区统计设置列表")
    @GetMapping("/fitList")
    public ReturnModel fitList(){
        try{
            List list = service.fitList();
            return new ReturnModel(list , "success" , Constant.STATE_SUCCESS , null);
        } catch (Exception e){
            logger.error(e.getMessage() , e);
            return new ReturnModel(e.getMessage() , "error" , "500" , null);
        }
    }


    @ApiOperation("景区统计设置添加")
    @PostMapping("/fitAdd")
    public ReturnModel fitAdd(Integer begin , Integer end){
        try{
            Integer result = service.fitAdd(begin , end);
            return new ReturnModel(result , "success" , Constant.STATE_SUCCESS , null);
        } catch (Exception e){
            logger.error(e.getMessage() , e);
            return new ReturnModel(e.getMessage() , "error" , "500" , null);
        }
    }

    @ApiOperation("景区统计设置修改")
    @PostMapping("/fitEdit")
    public ReturnModel fitEdit(Long id , Integer begin , Integer end){
        try{
            Integer result = service.fitEdit(id , begin , end);
            return new ReturnModel(result , "success" , Constant.STATE_SUCCESS , null);
        } catch (Exception e){
            logger.error(e.getMessage() , e);
            return new ReturnModel(e.getMessage() , "error" , "500" , null);
        }
    }

    @ApiOperation("景区统计设置删除")
    @DeleteMapping("/fitDelete")
    public ReturnModel fitDelete(Long id){
        try{
            Integer result = service.fitDelete(id);
            return new ReturnModel(result , "success" , Constant.STATE_SUCCESS , null);
        } catch (Exception e){
            logger.error(e.getMessage() , e);
            return new ReturnModel(e.getMessage() , "error" , "500" , null);
        }
    }

    /**
     * 张
     * @param type
     * @param beginDate
     * @param endDate
     * @param spotId
     * @param provinceId
     * @return
     */
    @ApiOperation("景区营收")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type" , value = "统计类型:1.月,3.天"),
            @ApiImplicitParam(name = "beginDate" , value = "起始时间"),
            @ApiImplicitParam(name = "endDate" , value = "截止时间"),
            @ApiImplicitParam(name = "spotId" , value = "景区id"),
            @ApiImplicitParam(name = "provinceId" , value = "地区id"),
    })
    @GetMapping("/fitRevenue")
    public ReturnModel fitRevenue(@NotNull Integer type , String beginDate , String endDate,String spotId,String provinceId){
        try{
            if (StringUtils.isEmpty(beginDate)){
                Date date = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
                String format = sdf.format(date);
                beginDate = format;
            }
            if (StringUtils.isEmpty(endDate)){
                Date date = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
                String format = sdf.format(date);
                endDate = format;
            }

            HashMap<String, List> map = service.fitRevenue(type, beginDate, endDate,spotId,provinceId);
            return new ReturnModel(map , "success" , Constant.STATE_SUCCESS , null);
        } catch (Exception e){
            logger.error(e.getMessage() , e);
            return new ReturnModel(e.getMessage() , "error" , "500" , null);
        }
    }

    /**
     * zhang
     * @param provinceId
     * @return
     */
    @ApiOperation("已运营景区列表")
    @GetMapping("/spotList")
    public ReturnModel spotList(String provinceId){
        try{
            List<ScenicSpot> map = service.spotList(provinceId);
            return new ReturnModel(map , "success" , Constant.STATE_SUCCESS , null);
        } catch (Exception e){
            logger.error(e.getMessage() , e);
            return new ReturnModel(e.getMessage() , "error" , "500" , null);
        }
    }

    /**
     * zhang
     * @return
     */
    @ApiOperation("省份列表")
    @GetMapping("/provinceList")
    public ReturnModel provinceList(){
        try{
            List<ScenicSpot> map = service.provinceList();
            return new ReturnModel(map , "success" , Constant.STATE_SUCCESS , null);
        } catch (Exception e){
            logger.error(e.getMessage() , e);
            return new ReturnModel(e.getMessage() , "error" , "500" , null);
        }
    }




}
