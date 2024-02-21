package com.hna.hka.archive.management.assetsSystem.controller;

import com.github.pagehelper.PageInfo;
import com.hna.hka.archive.management.assetsSystem.model.EquipmentAttendanceStatistics;
import com.hna.hka.archive.management.assetsSystem.service.EquipmentAttendanceStatisticsService;
import com.hna.hka.archive.management.system.util.FileUtil;
import com.hna.hka.archive.management.system.util.PageDataResult;
import com.hna.hka.archive.management.system.util.PublicUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Api(tags = "设备出勤")
@Controller
@RequestMapping("/system/EquipmentAttendanceStatistics")
public class EquipmentAttendanceStatisticsController extends PublicUtil {

    @Autowired
    private EquipmentAttendanceStatisticsService equipmentAttendanceStatisticsService;

    @ApiOperation("设备出勤查询")
    @RequestMapping("/list")
    @ResponseBody
    public PageDataResult list(EquipmentAttendanceStatistics equipmentAttendanceStatistics,Integer pageNum,Integer pageSize){
        PageDataResult pageDataResult = new PageDataResult();
        try {
            if(null == pageNum) {
                pageNum = 1;
            }
            if(null == pageSize) {
                pageSize = 10;
            }
//            List<Map> mapList = equipmentAttendanceStatisticsService.listNew(equipmentAttendanceStatistics, pageNum, pageSize);
            List<EquipmentAttendanceStatistics> mapList =  equipmentAttendanceStatisticsService.listsN(equipmentAttendanceStatistics, pageNum, pageSize);
            if (mapList.size()>0){
                PageInfo<EquipmentAttendanceStatistics> mapPageInfo = new PageInfo<>(mapList);
                pageDataResult.setData(mapPageInfo.getList());
                pageDataResult.setTotals((int)mapPageInfo.getTotal());
            }

        }catch (Exception e){
            logger.info("设备考勤列表查询失败",e);
        }
        return pageDataResult;
    }



    @ApiOperation("导出数据")
    @RequestMapping("/downloadFile")
    @ResponseBody
    public void downloadFile(HttpServletResponse Response, EquipmentAttendanceStatistics equipmentAttendanceStatistics, Integer pageNum, Integer pageSize){
         PageDataResult pageDataResult = new PageDataResult();
         List<EquipmentAttendanceStatistics> equipmentAttendanceStatisticsList=new ArrayList<>();

         try {
            if(null == pageNum) {
                pageNum = 1;
            }
            if(null == pageSize) {
                pageSize = 10;
            }
            pageDataResult = equipmentAttendanceStatisticsService.list(equipmentAttendanceStatistics, pageNum, pageSize);
            List<?> list = pageDataResult.getList();
            for (int i=0;i<list.size();i++){
                equipmentAttendanceStatisticsList.add((EquipmentAttendanceStatistics) list.get(i));
            }
            String dateTime = DateFormatUtils.format(new Date(), "yyyyMMddHHmm");
            FileUtil.exportExcel(FileUtil.getWorkbook("设备考勤记录","设备考勤列表",EquipmentAttendanceStatistics.class,equipmentAttendanceStatisticsList),"设备考勤记录"+ dateTime +".xls",Response);
        }catch (Exception e){
            logger.info("设备考勤导出失败",e);
        }
    }

    @ApiOperation("设备出勤饼图")
    @RequestMapping("/pieChartList")
    @ResponseBody
    public PageDataResult pieChartList(String type,String startTime,String endTime,String spotId) {
        PageDataResult pageDataResult = new PageDataResult();

       List<Map<String,Object>> list =  equipmentAttendanceStatisticsService.pieChartList(type,startTime,endTime,spotId);

       pageDataResult.setData(list);

       return pageDataResult;
    }

    @ApiOperation("设备出勤柱状图")
    @RequestMapping("/histogramList")
    @ResponseBody
    public PageDataResult histogramList(String type,String startTime,String endTime,String spotId,String sort) {
        PageDataResult pageDataResult = new PageDataResult();



      List<Map<String,Object>> map =  equipmentAttendanceStatisticsService.histogramList(type,startTime,endTime,spotId,sort);

      pageDataResult.setData(map);

        return pageDataResult;
    }


    @ApiOperation("设备出勤柱状图新")
    @RequestMapping("/histogramListN")
    @ResponseBody
    public PageDataResult histogramListNew(String type,String startTime,String endTime,String spotId,String sort) {
        PageDataResult pageDataResult = new PageDataResult();

        List<Map<String,Object>> map =  equipmentAttendanceStatisticsService.histogramListNew(type,startTime,endTime,spotId,sort);

        pageDataResult.setData(map);

        return pageDataResult;
    }




}
