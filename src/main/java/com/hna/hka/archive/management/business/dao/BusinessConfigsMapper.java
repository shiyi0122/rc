package com.hna.hka.archive.management.business.dao;

import com.hna.hka.archive.management.business.model.BusinessConfigs;

import java.util.List;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2023/7/11 13:32
 */
public interface BusinessConfigsMapper {


    int updateByPrimaryKeySelective(BusinessConfigs businessConfigs);


    List<BusinessConfigs> getConfigsList(Map<String, String> search);

}
