package com.hna.hka.archive.management.system.dao;

import com.hna.hka.archive.management.system.model.SysLog;
import com.hna.hka.archive.management.system.model.SysLogWithBLOBs;

import java.util.List;
import java.util.Map;

public interface SysLogMapper {
    int deleteByPrimaryKey(Long logId);

    int insert(SysLogWithBLOBs record);

    int insertSelective(SysLogWithBLOBs record);

    SysLogWithBLOBs selectByPrimaryKey(Long logId);

    int updateByPrimaryKeySelective(SysLogWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(SysLogWithBLOBs record);

    int updateByPrimaryKey(SysLog record);

    List<SysLog> getStoredValueAmountLogList(Map<String, String> search);
}