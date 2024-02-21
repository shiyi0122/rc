package com.hna.hka.archive.management.assetsSystem.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: rc
 * @description:
 * @author: zhaoxianglong
 * @create: 2021-09-25 10:42
 **/
public interface RTStatisticsMapper {
    List<HashMap> timeList(String order, Long companyId, Long spotId, Integer type, String status, Integer dateType, String startDate, String endDate, Integer timeType, Double totalTime, String robotCode, Integer pageNum, Integer pageSize);

    HashMap getTimeCount(Long companyId, Long spotId, Integer type, String status, Integer dateType, String startDate, String endDate, Integer timeType, Double totalTime, String robotCode);

    List<HashMap> companyList(Long spotId);

    List<HashMap> spotList(Long companyId);

    List<HashMap> amountList(Integer type, String robotCode, String order, String payType, Long companyId, Long spotId, String status, Integer dateType, String startDate, String endDate, Integer amountType, Double totalAmount, Integer pageNum, Integer pageSize);

    HashMap getAmountCount(Integer type, String robotCode, String order, String payType, Long companyId, Long spotId, String status, Integer dateType, String startDate, String endDate, Integer amountType, Double totalAmount);

    List<HashMap> spotTimeList(String order, Long companyId, Long spotId, Integer type, String status, Integer dateType, String startDate, String endDate, Integer timeType, Double totalTime, String robotCode, Integer pageNum, Integer pageSize);

    HashMap getSpotTimeCount(Long companyId, Long spotId, Integer type, String status, Integer dateType, String startDate, String endDate, Integer timeType, Double totalTime, String robotCode);

    List<HashMap> spotAmountList(Integer type, String robotCode, String order, String payType, Long companyId, Long spotId, String status, Integer dateType, String startDate, String endDate, Integer amountType, Double totalAmount, Integer pageNum, Integer pageSize);

    HashMap getSpotAmountCount(Integer type, String robotCode, String order, String payType, Long companyId, Long spotId, String status, Integer dateType, String startDate, String endDate, Integer amountType, Double totalAmount);

    List<HashMap> timeChart(Long companyId, Long spotId, Integer dateType, String startDate, String endDate);

    List<HashMap> amountChart(Long companyId, Long spotId, Integer dateType, String startDate, String endDate);

    List<HashMap> spotAmountListNew(Integer type, String robotCode, String order, String payType, Long companyId, Long spotId, String status, Integer dateType, String startDate, String endDate, Integer amountType, Double totalAmount, Integer pageNum, Integer pageSize);

    HashMap getSpotAmountCountNew(Integer type, String robotCode, String order, String payType, Long companyId, Long spotId, String status, Integer dateType, String startDate, String endDate, Integer amountType, Double totalAmount);


    List<Map<String,Object>> spotAmountListNewTow(Integer type, String robotCode, String order, String payType, Long companyId, Long spotId, String status, Integer dateType, String startDate, String endDate, Integer amountType, Double totalAmount, Integer pageNum, Integer pageSize);


    Double getSpotAmountMoney(String scenicSpotId, String startDate, String endDate);


    Double getSpotOperateTime(String scenicSpotId, String startDate, String endDate);


    Integer onlineOrderNumber(String scenicSpotId);

    Integer getSpotAndDateByRobot(Long spotIdN, String startDate, String endDate, Integer dateType);

    List<HashMap> amountByYear(Long spotId, String startDate, String endDate);

    List<HashMap> amountByMonth(Long spotId, String startDate, String endDate);

    List<HashMap> amountByDay(Long spotId, String startDate, String endDate);

    List<HashMap> amountAll(Long spotId, String startDate, String endDate);

    List<HashMap> amountMonthAll(Long spotId, String startDate, String endDate);

    List<HashMap> amountYearAll(Long spotId, String startDate, String endDate);
}