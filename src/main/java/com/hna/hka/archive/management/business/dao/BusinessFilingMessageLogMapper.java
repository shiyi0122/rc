package com.hna.hka.archive.management.business.dao;

import com.hna.hka.archive.management.business.model.BusinessFilingMessageLog;

import java.util.List;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2022/10/26 16:26
 */

public interface BusinessFilingMessageLogMapper {
    List<BusinessFilingMessageLog> getFilingMessageLogList(Map<String, Object> search);

}
