package com.hna.hka.archive.management.system.service;

import com.hna.hka.archive.management.system.model.SysOrderLog;
import com.hna.hka.archive.management.system.util.PageDataResult;

import java.util.List;
import java.util.Map;

public interface SysOrderLogStoredService {

    PageDataResult getOperationLogRefundOrderList(Integer pageNum, Integer pageSize, Map<String, Object> search);

    int insertOrderLog(SysOrderLog orderLog);

    List<SysOrderLog> getUploadExcelOrderLog(Map<String, Object> search);

}
