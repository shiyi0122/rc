package com.hna.hka.archive.management.system.dao;

import com.hna.hka.archive.management.system.model.SysScenicSpotName;

import java.util.List;
import java.util.Map;

public interface SysScenicSpotNameMapper {
    int deleteByPrimaryKey(Long scenicSpotNameId);

    int insert(SysScenicSpotName record);

    int insertSelective(SysScenicSpotName record);

    SysScenicSpotName selectByPrimaryKey(Long scenicSpotNameId);

    int updateByPrimaryKeySelective(SysScenicSpotName record);

    int updateByPrimaryKey(SysScenicSpotName record);

    List<SysScenicSpotName> getScenicSpotNameList(Map<String, Object> search);
}