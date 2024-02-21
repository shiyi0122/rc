package com.hna.hka.archive.management.system.dao;

import com.hna.hka.archive.management.system.model.SysRobotPad;
import com.hna.hka.archive.management.system.model.SysRobotPadNew;

import java.util.List;
import java.util.Map;

public interface SysRobotPadMapper {
    int deleteByPrimaryKey(Long padId);

    int insert(SysRobotPad record);

    int insertSelective(SysRobotPad record);

    SysRobotPad selectByPrimaryKey(Long padId);

    int updateByPrimaryKeySelective(SysRobotPad record);

    int updateByPrimaryKey(SysRobotPad record);

    List<SysRobotPad> getRobotMapResList(Map<String, String> search);

    SysRobotPad getAppPadNumber();
}