package com.hna.hka.archive.management.system.dao;

import com.hna.hka.archive.management.system.model.SysRobotVersion;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SysRobotVersionMapper {
    int deleteByPrimaryKey(Long robotVersionId);

    int insert(SysRobotVersion record);

    int insertSelective(SysRobotVersion record);

    SysRobotVersion selectByPrimaryKey(Long robotVersionId);

    int updateByPrimaryKeySelective(SysRobotVersion record);

    int updateByPrimaryKey(SysRobotVersion record);

    SysRobotVersion getSysRobotVersionByRobotId(Long robotId);
}