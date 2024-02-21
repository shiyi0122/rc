package com.hna.hka.archive.management.appSystem.dao;

import com.hna.hka.archive.management.assetsSystem.model.SysRobotErrorRecords;
import com.hna.hka.archive.management.assetsSystem.model.SysRobotPartsManagement;

import java.util.List;
import java.util.Map;

public interface SysRobotErrorRecordsMapper {
    int deleteByPrimaryKey(Long errorRecordsId);

    int insert(SysRobotErrorRecords record);

    int insertSelective(SysRobotErrorRecords record);

    SysRobotErrorRecords selectByPrimaryKey(Long errorRecordsId);

    int updateByPrimaryKeySelective(SysRobotErrorRecords record);

    int updateByPrimaryKey(SysRobotErrorRecords record);

    List<SysRobotErrorRecords> getRobotErrorRecordList(Map<String, String> search);

    List<SysRobotErrorRecords> getRobotErrorRecordsList(Map<String, Object> search);


    Double getSumPartPrice(Long spotId,String date);


    Double getSumUpkeepCost(Long spotId,String date);

    Double getSpotIdAndTimeLikeByRepairMoney(long spotId, String date);

    Double getSpotIdAndTimeLikeBySpotRepairMoney(long spotId, String last12Months);


    List<SysRobotErrorRecords> getFailureRecord(Map<String, Object> search);

    List<SysRobotErrorRecords> getAppRobotErrorRecords(Map<String, Object> search);





}