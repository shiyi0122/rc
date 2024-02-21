package com.hna.hka.archive.management.assetsSystem.dao;

import com.hna.hka.archive.management.assetsSystem.model.SysAttendanceRules;

import java.util.List;
import java.util.Map;

public interface SysAttendanceRulesMapper {
    int deleteByPrimaryKey(Long attendanceRulesId);

    int insert(SysAttendanceRules record);

    int insertSelective(SysAttendanceRules record);

    SysAttendanceRules selectByPrimaryKey(Long attendanceRulesId);

    int updateByPrimaryKeySelective(SysAttendanceRules record);

    int updateByPrimaryKey(SysAttendanceRules record);

    List<SysAttendanceRules> getAttendanceRulesList(Map<String, Object> search);
}