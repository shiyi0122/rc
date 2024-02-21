package com.hna.hka.archive.management.system.dao;

import com.hna.hka.archive.management.system.model.SysOrderLog;

import java.util.List;
import java.util.Map;

public interface SysOrderLogStoredMapper {

    List<SysOrderLog> getOperationLogRefundOrderList(Map<String, Object> search);

    int insertSelective(SysOrderLog record);
}
