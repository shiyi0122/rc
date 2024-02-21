package com.hna.hka.archive.management.system.dao;

import com.hna.hka.archive.management.system.model.SysScenicSpotWarning;
import com.hna.hka.archive.management.system.model.WarningExcel;

import java.util.List;
import java.util.Map;

public interface SysScenicSpotWarningMapper {
    int deleteByPrimaryKey(Long warningId);

    int insert(SysScenicSpotWarning record);

    int insertSelective(SysScenicSpotWarning record);

    SysScenicSpotWarning selectByPrimaryKey(Long warningId);

    int updateByPrimaryKeySelective(SysScenicSpotWarning record);

    int updateByPrimaryKey(SysScenicSpotWarning record);

    List<SysScenicSpotWarning> getWarningList(Map<String, String> search);

    List<WarningExcel> getWarningExcel(Map<String, Object> search);

    WarningExcel selWarning(WarningExcel warn);

    int addWarningExcel(WarningExcel warn);

    int upWarningExcel(WarningExcel warn);
}