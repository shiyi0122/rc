package com.hna.hka.archive.management.assetsSystem.dao;

import com.hna.hka.archive.management.assetsSystem.model.SysRobotSoftwareVersion;

import java.util.List;
import java.util.Map;

public interface SysRobotSoftwareVersionMapper {
    int deleteByPrimaryKey(Long softwareVersionId);

    int insert(SysRobotSoftwareVersion record);

    int insertSelective(SysRobotSoftwareVersion record);

    SysRobotSoftwareVersion selectByPrimaryKey(Long softwareVersionId);

    int updateByPrimaryKeySelective(SysRobotSoftwareVersion record);

    int updateByPrimaryKey(SysRobotSoftwareVersion record);

    List<SysRobotSoftwareVersion> getRobotSoftwareVersionList(Map<String, Object> search);
}