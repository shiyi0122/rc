package com.hna.hka.archive.management.system.service;

import com.hna.hka.archive.management.system.util.PageDataResult;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2022/3/19 11:22
 */
public interface SysScenicSpotWifiDataService {
    PageDataResult getScenicSpotWifiData(Integer pageNum, Integer pageSize, HashMap<String, String> search);


    Map<String, Object> getPeopleCounting(String spotId);

}
