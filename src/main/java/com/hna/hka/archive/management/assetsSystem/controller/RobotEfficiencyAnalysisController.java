package com.hna.hka.archive.management.assetsSystem.controller;

import com.hna.hka.archive.management.assetsSystem.model.*;
import com.hna.hka.archive.management.assetsSystem.service.RobotEfficiencyAnalysisService;
import com.hna.hka.archive.management.system.model.SysRobot;
import com.hna.hka.archive.management.system.model.SysUsers;
import com.hna.hka.archive.management.system.util.Constant;
import com.hna.hka.archive.management.system.util.FileUtil;
import com.hna.hka.archive.management.system.util.PageDataResult;
import com.hna.hka.archive.management.system.util.ReturnModel;
import com.rabbitmq.client.Return;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2022/4/13 15:52
 */
@Api(tags = "机器人利用率")
@RequestMapping("/system/efficiencyAnalysis")
@RestController
public class RobotEfficiencyAnalysisController {

    @Autowired
    RobotEfficiencyAnalysisService robotEfficiencyAnalysisService;

    /**
     * 根据景区查询机器人利用率
     */
    @ApiOperation("查询机器人利用率")
    @GetMapping("/getSpotGrossProfitMarginList")
    public ReturnModel getSpotGrossProfitMarginList( Long type,String spotId,String startTime, String endTime, Integer pageNum , Integer pageSize, String sort) {

        ReturnModel returnModel = new ReturnModel();

        List<Map<String ,Object>> mapList = robotEfficiencyAnalysisService.getSpotGrossProfitMarginList(type,spotId,startTime,endTime,pageNum,pageSize,sort);
        Map<String, Object> map = mapList.get(mapList.size()-1);
        mapList.remove(mapList.size()-1);
        Integer total = (Integer)map.get("total");
        if (mapList.size() > 0){
            returnModel.setData(mapList);
            returnModel.setState(Constant.STATE_SUCCESS);
            returnModel.setMsg("success");
//            returnModel.setType(JSON.toJSONString(map));
            returnModel.setTotal(total);
        }


        return returnModel;
    }

    /**
     * 机器人利用率图表
     */

    @ApiOperation("机器人利用率图表")
    @GetMapping("/getSpotGrossProfitMarginChartList")
    public ReturnModel  getSpotGrossProfitMarginChartList(Long type,String spotId,String startTime, String endTime, Integer pageNum , Integer pageSize, String sort) {

        ReturnModel returnModel = new ReturnModel();

        returnModel  = robotEfficiencyAnalysisService.getSpotGrossProfitMarginChartList(type,spotId,startTime,endTime,pageNum,pageSize,sort);


        return returnModel;
    }

    /**
     * @Method uploadExcelRobot
     * @Author
     * @Version  1.0
     * @Description 导出机器人利用率信息Excel表
     * @Return void
     * @Date 2021/5/27 14:31
     */
    @ApiOperation("导出机器人利用率信息")
    @RequestMapping(value = "/uploadExcelSpotGrossProfitMarginChart")
    public void  uploadExcelSpotGrossProfitMarginChart(HttpServletResponse response, @RequestBody SearchRobot searchRobot) throws Exception {


        ReturnModel returnModel = new ReturnModel();


        List<SpotGrossProfitMargin> mapList = robotEfficiencyAnalysisService.getSpotGrossProfitMarginExcel(Long.parseLong(searchRobot.getType()),searchRobot.getSpotId(),searchRobot.getStartTime(),searchRobot.getEndTime(),searchRobot.getPageNum(),searchRobot.getPageSize(),searchRobot.getSort());

        String dateTime = DateFormatUtils.format(new Date(), "yyyyMMddHHmm");
        FileUtil.exportExcel(FileUtil.getWorkbook("机器人利用率信息", "机器人利用率", SpotGrossProfitMargin.class, mapList),"机器人利用率信息"+ dateTime +".xls",response);

//        returnModel.setData(mapList);
//        returnModel.setState(Constant.STATE_SUCCESS);
//        returnModel.setMsg("success");

//        returnModel.setTotal(total);

//        return returnModel;

    }



}
