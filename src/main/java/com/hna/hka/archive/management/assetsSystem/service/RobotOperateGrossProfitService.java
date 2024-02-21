package com.hna.hka.archive.management.assetsSystem.service;

import com.hna.hka.archive.management.assetsSystem.model.RobotOperateGrossProfit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2022/3/17 18:00
 * 机器人毛利率
 */
public interface RobotOperateGrossProfitService {

    Map<String, Object> getSpotGrossProfitMarginList(Long type, Long spotId,String startTime,String endTime, Integer pageNum, Integer pageSize,String sort);

    Map<Object,Object> getSpotGrossProfitMarginListStatistical(String spotId, String startDate, String endDate, String type);


    HashMap<Object, Object> getSpotGrossProfitMarginListStatisticalDate(String spotId, String startDate, String endDate, String type);


    Map<String, Object> getSpotGrossProfitMarginListStatisticalTotal(Long spotId, String startData, String endData, Long type);


    List<RobotOperateGrossProfit> getSpotGrossProfitMarginExcel(Long type, Long spotId, String startTime, String endTime, String sort);


}
