package com.hna.hka.archive.management.system.service;

import com.hna.hka.archive.management.system.model.SysOrderCurrentLog;
import com.hna.hka.archive.management.system.util.PageDataResult;

import java.util.Map;

/**
 * @Author zhang
 * @Date 2022/12/21 18:07
 */
public interface SysOrderCurrentLogService {
    int add(SysOrderCurrentLog sysOrderCurrentLog);


    PageDataResult getSysOrderCurrentLogList(Integer pageNum, Integer pageSize, Map<String, Object> search);

}
