package com.hna.hka.archive.management.system.dao;

import com.hna.hka.archive.management.system.model.SysRobotMapRes;

import java.util.List;
import java.util.Map;

public interface SysRobotMapResMapper {
    int deleteByPrimaryKey(Long resId);

    int insert(SysRobotMapRes record);

    int insertSelective(SysRobotMapRes record);

    SysRobotMapRes selectByPrimaryKey(Long resId);

    int updateByPrimaryKeySelective(SysRobotMapRes record);

    int updateByPrimaryKey(SysRobotMapRes record);

    List<SysRobotMapRes> getRobotMapResList(Map<String, String> search);

    SysRobotMapRes getSysRobotMapResByScenicSpotId(String scenicSpotId);
}