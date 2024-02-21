package com.hna.hka.archive.management.system.dao;

import java.util.List;
import java.util.Map;

import com.hna.hka.archive.management.system.model.SysConfigs;

public interface SysConfigsMapper {
    int deleteByPrimaryKey(Long configsId);

    int insert(SysConfigs record);

    int insertSelective(SysConfigs record);

    SysConfigs selectByPrimaryKey(Long configsId);

    int updateByPrimaryKeySelective(SysConfigs record);

    int updateByPrimaryKeyWithBLOBs(SysConfigs record);

    int updateByPrimaryKey(SysConfigs record);

	List<SysConfigs> getConfigsList(Map<String, Object> search);
}