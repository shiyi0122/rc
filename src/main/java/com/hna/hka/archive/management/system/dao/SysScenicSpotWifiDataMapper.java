package com.hna.hka.archive.management.system.dao;

import com.hna.hka.archive.management.system.model.SysScenicSpotWifiData;

import java.util.HashMap;
import java.util.List;

/**
 * @Author zhang
 * @Date 2022/3/19 11:24
 */
public interface SysScenicSpotWifiDataMapper {


    List<SysScenicSpotWifiData> getScenicSpotWifiData(HashMap<String, String> search);


    Integer getMorningPeopleCounting(String spotId);

    Integer getAfternoonPeopleCounting(String spotId);

    Integer getCurrentPeopleCounting(String spotId);

}
