package com.hna.hka.archive.management.assetsSystem.service;

import com.hna.hka.archive.management.assetsSystem.model.ChartReturnClass;
import com.hna.hka.archive.management.assetsSystem.model.SpotGrossProfitMargin;
import com.hna.hka.archive.management.system.util.PageDataResult;
import com.hna.hka.archive.management.system.util.ReturnModel;

import java.util.List;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2022/4/13 15:54
 * 机器人利用率
 */
public interface RobotEfficiencyAnalysisService {

    List<Map<String, Object>> getSpotGrossProfitMarginList(Long type, String spotId,String startTime, String endTime, Integer pageNum, Integer pageSize, String sort);

    ReturnModel getSpotGrossProfitMarginChartList(Long type, String spotId, String startTime, String endTime, Integer pageNum, Integer pageSize, String sort);


    List<SpotGrossProfitMargin> getSpotGrossProfitMarginExcel(Long type, String spotId, String startTime, String endTime, Integer pageNum, Integer pageSize, String sort);

}
