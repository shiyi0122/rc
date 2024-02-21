package com.hna.hka.archive.management.assetsSystem.dao;

import com.hna.hka.archive.management.assetsSystem.model.SysRobotErrorRepairUser;

public interface SysRobotErrorRepairUserMapper {
    int deleteByPrimaryKey(Long errorRepairUserId);

    int insert(SysRobotErrorRepairUser record);

    int insertSelective(SysRobotErrorRepairUser record);

    SysRobotErrorRepairUser selectByPrimaryKey(Long errorRepairUserId);

    int updateByPrimaryKeySelective(SysRobotErrorRepairUser record);

    int updateByPrimaryKey(SysRobotErrorRepairUser record);

    SysRobotErrorRepairUser getRobotErrorRepairUser(SysRobotErrorRepairUser sysRobotErrorRepairUser);
}