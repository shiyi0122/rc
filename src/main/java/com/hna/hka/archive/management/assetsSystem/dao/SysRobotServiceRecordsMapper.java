package com.hna.hka.archive.management.assetsSystem.dao;

import com.hna.hka.archive.management.assetsSystem.model.SysRobotServiceRecords;

import java.util.List;
import java.util.Map;

public interface SysRobotServiceRecordsMapper {
    int deleteByPrimaryKey(Long serviceRecordsId);

    int insert(SysRobotServiceRecords record);

    int insertSelective(SysRobotServiceRecords record);

    SysRobotServiceRecords selectByPrimaryKey(Long serviceRecordsId);

    int updateByPrimaryKeySelective(SysRobotServiceRecords record);

    int updateByPrimaryKey(SysRobotServiceRecords record);

    List<SysRobotServiceRecords> getRobotServiceRecordsList(Map<String, Object> search);

    SysRobotServiceRecords selectByErrorRecordsModel(String errorRecordsOrderNo);


    int updateByPrimaryKeySelectiveNO(SysRobotServiceRecords sysRobotServiceRecords);

}