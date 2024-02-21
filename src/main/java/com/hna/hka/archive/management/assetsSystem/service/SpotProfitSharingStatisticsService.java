package com.hna.hka.archive.management.assetsSystem.service;

import com.hna.hka.archive.management.assetsSystem.model.SysScenicSpotFenfun;
import com.hna.hka.archive.management.system.util.PageDataResult;

import java.util.Map;

/**
 * @Author zhang
 * @Date 2022/4/23 17:55
 * 景区分润统计
 */
public interface SpotProfitSharingStatisticsService {


    Integer spotProfitSharingStatistice();


    PageDataResult spotProfitSharingStatisticeList(Map<String,Object> search);

    int editSpotProfitSharingStatistice(SysScenicSpotFenfun sysScenicSpotFenfun);


    PageDataResult spotProfitSharingStatisticeExcel(Map<String, Object> search);


    Map<Object, Object> spotProfitSharingStatisticeChart(Long subjectId, Long spotId, String startDate, String endDate, Long type, Long companyId);

    /**
     * 每月定时统计相关数据的方法
     */
    void spotProfitSharingStatisticeTimedStatistics();
}
