package com.hna.hka.archive.management.system.dao;

import com.hna.hka.archive.management.system.model.SysRobotProblemExtend;

import java.util.List;
import java.util.Map;

public interface SysRobotProblemExtendMapper {
    int deleteByPrimaryKey(Long extendId);

    int insert(SysRobotProblemExtend record);

    int insertSelective(SysRobotProblemExtend record);

    SysRobotProblemExtend selectByPrimaryKey(Long extendId);

    int updateByPrimaryKeySelective(SysRobotProblemExtend record);

    int updateByPrimaryKey(SysRobotProblemExtend record);

    List<SysRobotProblemExtend> getSemanticsDetailsList(Map<String, Object> search);
}