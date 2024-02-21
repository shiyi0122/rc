package com.hna.hka.archive.management.business.service;

import com.hna.hka.archive.management.business.model.BusinessConfigs;
import com.hna.hka.archive.management.system.util.PageDataResult;

import java.util.Map;

/**
 * @Author zhang
 * @Date 2023/7/11 13:31
 */
public interface BusinessConfigsService {
    int editConfigs(BusinessConfigs businessConfigs);


    PageDataResult getConfigsList(Integer pageNum, Integer pageSize, Map<String, String> search);


}
