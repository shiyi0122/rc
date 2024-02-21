package com.hna.hka.archive.management.system.dao;

import java.util.List;
import java.util.Map;

import com.hna.hka.archive.management.system.model.SysScenicSpotActivity;

public interface SysScenicSpotActivityMapper {
    int deleteByPrimaryKey(Long activityId);

    int insert(SysScenicSpotActivity record);

    int insertSelective(SysScenicSpotActivity record);

    SysScenicSpotActivity selectByPrimaryKey(Long activityId);

    int updateByPrimaryKeySelective(SysScenicSpotActivity record);

    int updateByPrimaryKey(SysScenicSpotActivity record);

	List<SysScenicSpotActivity> getScenicSpotActivityList(Map<String, Object> search);

	SysScenicSpotActivity getScenicSpotActivityByType(String activityType);
}