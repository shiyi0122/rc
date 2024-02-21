package com.hna.hka.archive.management.managerApp.dao;

import com.hna.hka.archive.management.managerApp.model.SysRobotAdministratorsVersion;

import java.util.List;
import java.util.Map;

public interface SysRobotAdministratorsVersionMapper {
    int deleteByPrimaryKey(Long versionId);

    int insert(SysRobotAdministratorsVersion record);

    int insertSelective(SysRobotAdministratorsVersion record);

    SysRobotAdministratorsVersion selectByPrimaryKey(Long versionId);

    int updateByPrimaryKeySelective(SysRobotAdministratorsVersion record);

    int updateByPrimaryKey(SysRobotAdministratorsVersion record);

    List<SysRobotAdministratorsVersion> getAdministratorsVersionList(Map<String, Object> search);

    SysRobotAdministratorsVersion getAdministratorsVersion();
}