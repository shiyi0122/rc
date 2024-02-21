package com.hna.hka.archive.management.assetsSystem.dao;

import com.hna.hka.archive.management.assetsSystem.model.SysRobotObstacleAvoidanceModule;

import java.util.List;
import java.util.Map;

public interface SysRobotObstacleAvoidanceModuleMapper {
    int deleteByPrimaryKey(Long obstacleAvoidanceModularId);

    int insert(SysRobotObstacleAvoidanceModule record);

    int insertSelective(SysRobotObstacleAvoidanceModule record);

    SysRobotObstacleAvoidanceModule selectByPrimaryKey(Long obstacleAvoidanceModularId);

    int updateByPrimaryKeySelective(SysRobotObstacleAvoidanceModule record);

    int updateByPrimaryKey(SysRobotObstacleAvoidanceModule record);

    List<SysRobotObstacleAvoidanceModule> getRobotObstacleAvoidanceModuleList(Map<String, Object> search);
}