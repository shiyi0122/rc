package com.hna.hka.archive.management.system.dao;

import com.hna.hka.archive.management.system.model.SysResExcel;
import com.hna.hka.archive.management.system.model.SysScenicSpotServiceRes;

import java.util.List;
import java.util.Map;

public interface SysScenicSpotServiceResMapper {
    int deleteByPrimaryKey(Long serviceId);

    int insert(SysScenicSpotServiceRes record);

    int insertSelective(SysScenicSpotServiceRes record);

    SysScenicSpotServiceRes selectByPrimaryKey(Long serviceId);

    int updateByPrimaryKeySelective(SysScenicSpotServiceRes record);

    int updateByPrimaryKey(SysScenicSpotServiceRes record);

    List<SysScenicSpotServiceRes> getServiceResList(Map<String, String> search);

    List<SysResExcel> getSysResExcel(Map<String,Object> search);

    SysResExcel selSysRes(SysResExcel res);

    int upSysRes(SysResExcel res);

    int addSysRes(SysResExcel res);

    List<SysScenicSpotServiceRes> getScenicSpotServiceResAll();

}