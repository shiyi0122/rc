package com.hna.hka.archive.management.assetsSystem.controller;

import com.hna.hka.archive.management.assetsSystem.model.SysRobotErrorRecords;
import com.hna.hka.archive.management.assetsSystem.model.SysScenicSpotFenfun;
import com.hna.hka.archive.management.assetsSystem.service.SpotProfitSharingStatisticsService;
import com.hna.hka.archive.management.system.model.SysScenicSpot;
import com.hna.hka.archive.management.system.model.SysScenicSpotCapPrice;
import com.hna.hka.archive.management.system.model.SysUsers;
import com.hna.hka.archive.management.system.util.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2022/4/23 17:52
 * 景区分润统计
 *
 */
@Api(tags = "景区分润统计")
@CrossOrigin
@RestController
@RequestMapping("/system/spotProfitSharingStatistics")
public class SpotProfitSharingStatisticsController extends PublicUtil {

    @Autowired
    SpotProfitSharingStatisticsService spotProfitSharingStatisticsService;

    /**
     * 景区分润统计列表
     * @param
     * @param spotId
     * @param startDate
     * @param endDate
     * @param type
     * @return
     */

    @ApiOperation("景区分润统计列表")
    @GetMapping("spotProfitSharingStatisticeList")
    public PageDataResult spotProfitSharingStatisticeList(Long subjectId , Long spotId,String startDate,String endDate,Long type,Long companyId ,Integer pageNum,Integer pageSize){


        ReturnModel returnModel = new ReturnModel();
        Map<String, Object> search = new HashMap<>();

        search.put("subjectId",subjectId);
        search.put("spotId",spotId);
        search.put("startDate",startDate);
        search.put("endDate",endDate);
        search.put("type",type);
        search.put("companyId",companyId);
        if (ToolUtil.isEmpty(pageNum)){
            search.put("pageNum",1);
        }else{
            search.put("pageNum",pageNum);
        }
        if (ToolUtil.isEmpty(pageSize)){
            search.put("pageSize",10);
        }else{
            search.put("pageSize",pageSize);
        }

        PageDataResult pageDataResult = spotProfitSharingStatisticsService.spotProfitSharingStatisticeList(search);


        return pageDataResult;

    }



    @ApiOperation("编辑分润统计状态")
    @PostMapping("editSpotProfitSharingStatistice")
    public ReturnModel editSpotProfitSharingStatistice(@RequestBody SysScenicSpotFenfun sysScenicSpotFenfun){

        ReturnModel returnModel = new ReturnModel();

        int i = spotProfitSharingStatisticsService.editSpotProfitSharingStatistice(sysScenicSpotFenfun);

        if (i == 1){
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_SUCCESS);
            returnModel.setMsg("修改成功");
        } else {
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("修改失败");
        }
        return returnModel;

    }

    @ApiOperation("添加分润统计测试")
    @PostMapping("addSpotProfitSharingStatistice")
    public ReturnModel addSpotProfitSharingStatistice(@RequestBody SysScenicSpotFenfun sysScenicSpotFenfun){

        ReturnModel returnModel = new ReturnModel();

        int i = spotProfitSharingStatisticsService.spotProfitSharingStatistice();

        if (i == 1){
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_SUCCESS);
            returnModel.setMsg("添加成功");
        } else {

            returnModel.setData(i);
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("添加失败");
        }

        return returnModel;

    }


    /**
     * 导出统计表数据
     */
    @ApiOperation("导出分润统计")
    @GetMapping("/downloadExcel")
    public void  spotProfitSharingStatisticeExcel(HttpServletResponse response, Long subjectId , Long spotId,Long companyId,String startDate,String endDate,Long type) throws Exception {
        List<SysScenicSpotFenfun> sysScenicSpotFenfuns = null;
        Map<String,Object> search = new HashMap<>();
        search.put("subjectId",subjectId);
        search.put("spotId",spotId);
        search.put("startDate",startDate);
        search.put("endDate",endDate);
        search.put("type",type);
        search.put("companyId",companyId);
//        if (ToolUtil.isEmpty(pageNum)){
//            search.put("pageNum",1);
//        }else{
//            search.put("pageNum",pageNum);
//        }
//        if (ToolUtil.isEmpty(pageSize)){
//            search.put("pageSize",10);
//        }else{
//            search.put("pageSize",pageSize);
//        }

        PageDataResult pageDataResult = spotProfitSharingStatisticsService.spotProfitSharingStatisticeExcel(search);
        sysScenicSpotFenfuns = (List<SysScenicSpotFenfun>) pageDataResult.getList();


        String dateTime = DateFormatUtils.format(new Date(), "yyyyMMddHHmm");
        FileUtil.exportExcel(FileUtil.getWorkbook("景区分润统计表","分润统计",SysScenicSpotFenfun.class,sysScenicSpotFenfuns),"分润统计"+ dateTime +".xls",response);
    }


    @ApiOperation("景区分润统计图表数据")
    @GetMapping("spotProfitSharingStatisticeChart")
    public ReturnModel spotProfitSharingStatisticeChart(Long subjectId , Long spotId,String startDate,String endDate,Long type,Long companyId ) {

        ReturnModel returnModel = new ReturnModel();
        Map<Object,Object> map = spotProfitSharingStatisticsService.spotProfitSharingStatisticeChart(subjectId,spotId,startDate,endDate,type,companyId);

        returnModel.setData(map);
        returnModel.setState(Constant.STATE_SUCCESS);
        returnModel.setMsg("获取成功");
        return returnModel;

    }




    @ApiOperation("测试定时统计接口")
    @CrossOrigin
    @GetMapping("/test")
    public void test() {
        spotProfitSharingStatisticsService.spotProfitSharingStatisticeTimedStatistics();


    }








    }
