package com.hna.hka.archive.management.assetsSystem.controller;

import com.alibaba.fastjson.JSON;
import com.hna.hka.archive.management.assetsSystem.model.RobotOperateGrossProfit;
import com.hna.hka.archive.management.assetsSystem.model.SearchRobot;
import com.hna.hka.archive.management.assetsSystem.model.SpotGrossProfitMargin;
import com.hna.hka.archive.management.assetsSystem.service.RobotOperateGrossProfitService;
import com.hna.hka.archive.management.assetsSystem.service.RobotOperateSaturationService;
import com.hna.hka.archive.management.system.dao.SysScenicSpotMapper;
import com.hna.hka.archive.management.system.service.SysScenicSpotService;
import com.hna.hka.archive.management.system.util.Constant;
import com.hna.hka.archive.management.system.util.FileUtil;
import com.hna.hka.archive.management.system.util.ReturnModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * @Author zhang
 * @Date 2022/3/3 10:59
 * 机器人毛利率
 */
@Api(tags = "机器人毛利率")
@RequestMapping("/system/operateGrossProfit")
@RestController
public class RobotOperateGrossProfitController {

    @Autowired
    RobotOperateGrossProfitService robotOperateGrossProfitService;





    /**
     * 根据景区查询机器人毛利率
     */

    @ApiOperation("查询机器人毛利率")
    @GetMapping("/getSpotGrossProfitMarginList")
    public ReturnModel getSpotGrossProfitMarginList(Long type, Long spotId, String startTime, String endTime, Integer pageNum , Integer pageSize, String sort) {

        ReturnModel returnModel = new ReturnModel();

        Map<String, Object> mapList = robotOperateGrossProfitService.getSpotGrossProfitMarginList(type,spotId,startTime,endTime,pageNum,pageSize,sort);

        if (mapList.size() > 0){
            returnModel.setData(mapList.get("list"));
            returnModel.setState(Constant.STATE_SUCCESS);
            returnModel.setMsg("success");
//            returnModel.setType(JSON.toJSONString(map));
            returnModel.setTotal((int)mapList.get("total"));
        }


        return returnModel;
    }


    @ApiOperation("查询机器人毛利率统计图")
    @GetMapping("/getSpotGrossProfitMarginListStatistical")
    public ReturnModel getSpotGrossProfitMarginListStatistical(String spotId,String startData,String endData,String type) {

        ReturnModel returnModel = new ReturnModel();
//
        int i = startData.compareTo(endData);
        if (i==0 && StringUtils.isEmpty(spotId)){//时间相等

            //按照景区
            Map<Object,Object> list = robotOperateGrossProfitService.getSpotGrossProfitMarginListStatistical(spotId,startData,endData,type);
            returnModel.setData(list);
            returnModel.setMsg("success");
            returnModel.setState(Constant.STATE_SUCCESS);

            return returnModel;

        }else{//时间不等
            //返回时间段
            HashMap<Object, Object> list = robotOperateGrossProfitService.getSpotGrossProfitMarginListStatisticalDate(spotId,startData,endData,type);
            returnModel.setData(list);
            returnModel.setMsg("success");
            returnModel.setState(Constant.STATE_SUCCESS);

            return returnModel;

        }

    }

    @ApiOperation("查询机器人毛利率合计")
    @GetMapping("/getSpotGrossProfitMarginListStatisticalTotal")
    public ReturnModel getSpotGrossProfitMarginListStatisticalTotal(Long spotId,String startData,String endData,Long type) {

        ReturnModel returnModel = new ReturnModel();
//
        int i = startData.compareTo(endData);

            //按照景区
            Map<String,Object> list = robotOperateGrossProfitService.getSpotGrossProfitMarginListStatisticalTotal(spotId,startData,endData,type);
            returnModel.setData(list);
            returnModel.setMsg("success");
            returnModel.setState(Constant.STATE_SUCCESS);

            return returnModel;
    }

    /**
     * @Method uploadExcelRobot
     * @Author 郭凯
     * @Version  1.0
     * @Description 导出机器人毛利率信息Excel表
     * @Return void
     * @Date 2021/5/27 14:31
     */
    @ApiOperation("导出机器人利用率信息")
    @RequestMapping(value = "/uploadExcelSpotGrossProfitMargin")
    public void  uploadExcelSpotGrossProfitMargin(HttpServletResponse response,@RequestBody RobotOperateGrossProfit robotOperateGrossProfit) throws Exception {


        ReturnModel returnModel = new ReturnModel();


        List<RobotOperateGrossProfit> mapList = robotOperateGrossProfitService.getSpotGrossProfitMarginExcel(robotOperateGrossProfit.getType(),robotOperateGrossProfit.getSpotId(),robotOperateGrossProfit.getStartTime(),robotOperateGrossProfit.getEndTime(),robotOperateGrossProfit.getSort());

        String dateTime = DateFormatUtils.format(new Date(), "yyyyMMddHHmm");
        FileUtil.exportExcel(FileUtil.getWorkbook("机器人毛利率信息", "机器人毛利率", RobotOperateGrossProfit.class, mapList),"机器人利用率信息"+ dateTime +".xls",response);

//        returnModel.setData(mapList);
//        returnModel.setState(Constant.STATE_SUCCESS);
//        returnModel.setMsg("success");

//        returnModel.setTotal(total);

//        return returnModel;

    }











    }
