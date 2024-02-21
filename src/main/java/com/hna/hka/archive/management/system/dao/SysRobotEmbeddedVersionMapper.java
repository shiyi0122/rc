package com.hna.hka.archive.management.system.dao;

import com.hna.hka.archive.management.system.model.SysRobotEmbeddedVersion;

import java.util.List;
import java.util.Map;

public interface SysRobotEmbeddedVersionMapper {
    int deleteByPrimaryKey(Long embeddedId);

    int insert(SysRobotEmbeddedVersion record);

    int insertSelective(SysRobotEmbeddedVersion record);

    SysRobotEmbeddedVersion selectByPrimaryKey(Long embeddedId);

    int updateByPrimaryKeySelective(SysRobotEmbeddedVersion record);

    int updateByPrimaryKey(SysRobotEmbeddedVersion record);

    List<SysRobotEmbeddedVersion> getRobotEmbeddedVersionList(Map<String, String> search);

	SysRobotEmbeddedVersion getRobotEmbeddedVersionByVersion(String softwareVersion);
}