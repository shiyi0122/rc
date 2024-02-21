package com.hna.hka.archive.management.assetsSystem.dao;

import com.hna.hka.archive.management.assetsSystem.model.SysScenicSpotOperationRules;
import io.lettuce.core.dynamic.annotation.Param;

import java.util.List;
import java.util.Map;

public interface SysScenicSpotOperationRulesMapper {
    int deleteByPrimaryKey(Long operationRulesId);

    int insert(SysScenicSpotOperationRules record);

    int insertSelective(SysScenicSpotOperationRules record);

    SysScenicSpotOperationRules selectByPrimaryKey(Long operationRulesId);

    int updateByPrimaryKeySelective(SysScenicSpotOperationRules record);

    int updateByPrimaryKey(SysScenicSpotOperationRules record);

    List<SysScenicSpotOperationRules> getOperationRulesListById(@Param("scenicSpotFId") String scenicSpotFId);

    List<SysScenicSpotOperationRules> getOperationRulesList(Map<String, String> search);

    List<SysScenicSpotOperationRules> getOperationRulesBySpotId(Long scenicSpotId);
}