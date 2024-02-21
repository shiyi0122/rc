package com.hna.hka.archive.management.system.dao;

import com.hna.hka.archive.management.system.model.SysRobotPad;
import com.hna.hka.archive.management.system.model.SysRobotPadNew;

import java.util.List;
import java.util.Map;

public interface SysRobotPadNewMapper {
    int deleteByPrimaryKey(Long padId);

    int insert(SysRobotPadNew record);

    int insertSelective(SysRobotPadNew record);

    SysRobotPadNew selectByPrimaryKey(Long padId);

    int updateByPrimaryKeySelective(SysRobotPadNew record);

    int updateByPrimaryKey(SysRobotPad record);

    List<SysRobotPadNew> getRobotMapResList(Map<String, String> search);

    SysRobotPadNew getAppPadNumber();

    SysRobotPadNew getAppPadNumberNew(String type);


    List<SysRobotPadNew> getAppPadNumberNewList(String s);

}