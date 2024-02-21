package com.hna.hka.archive.management.system.dao;

import com.hna.hka.archive.management.system.model.SysOrderCurrentLog;

import java.util.List;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2022/12/21 18:10
 */
public interface SysOrderCurrentLogMapper {
    int add(SysOrderCurrentLog sysOrderCurrentLog);

    List<SysOrderCurrentLog> getSysOrderCurrentLogList(Map<String, Object> search);


}
