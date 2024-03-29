package com.hna.hka.archive.management.system.dao;

import com.hna.hka.archive.management.system.model.SysCurrentUser;
import com.hna.hka.archive.management.system.model.SysScenicSpotActivity;

import java.util.List;
import java.util.Map;

public interface SysScenicSpotActivityMapper {
    int deleteByPrimaryKey(Long activityId);

    int insert(SysScenicSpotActivity record);

    int insertSelective(SysScenicSpotActivity record);

    SysScenicSpotActivity selectByPrimaryKey(Long activityId);

    int updateByPrimaryKeySelective(SysScenicSpotActivity record);

    int updateByPrimaryKey(SysScenicSpotActivity record);

	List<SysScenicSpotActivity> getScenicSpotActivityList(Map<String, Object> search);

	SysScenicSpotActivity getScenicSpotActivityByType(String activityType);

    SysCurrentUser selectByPhone(String userPhone);
}