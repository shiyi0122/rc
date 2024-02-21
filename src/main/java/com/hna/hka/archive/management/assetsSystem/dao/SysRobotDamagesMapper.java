package com.hna.hka.archive.management.assetsSystem.dao;

import com.hna.hka.archive.management.assetsSystem.model.SysRobotDamages;

import java.util.List;
import java.util.Map;

public interface SysRobotDamagesMapper {
    int deleteByPrimaryKey(Long damagesId);

    int insert(SysRobotDamages record);

    int insertSelective(SysRobotDamages record);

    SysRobotDamages selectByPrimaryKey(Long damagesId);

    int updateByPrimaryKeySelective(SysRobotDamages record);

    int updateByPrimaryKey(SysRobotDamages record);

    List<SysRobotDamages> getRobotDamagesAppList(Map<String, String> search);

    List<SysRobotDamages> getRobotDamagesList(Map<String, Object> search);
}