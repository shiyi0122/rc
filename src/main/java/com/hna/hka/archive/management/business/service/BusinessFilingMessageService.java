package com.hna.hka.archive.management.business.service;

import com.hna.hka.archive.management.business.model.BusinessFilingMessage;
import com.hna.hka.archive.management.business.model.BusinessFilingMessageLog;
import com.hna.hka.archive.management.system.util.PageDataResult;

import java.util.List;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2022/9/21 16:20
 */
public interface BusinessFilingMessageService {
    //报备列表查询
    PageDataResult getFilingMessageList(Integer pageNum, Integer pageSize, Map<String, Object> search);

    //导出
    List<BusinessFilingMessage> uploadExcelFilingMessage(Map<String, Object> search);


    //修改审核结果
    int editFilingMessageResult(BusinessFilingMessage businessFilingMessage);

    //添加报备信息
    int addFilingMessage(BusinessFilingMessage businessFilingMessage);


// 查询审核日志列表
    PageDataResult getFilingMessageLogList(Integer pageNum, Integer pageSize, Long id);
    // 导出审核日志列表
    List<BusinessFilingMessageLog> uploadExcelFilingMessageAuditLog(Map<String, Object> search);


    int  importExcelEnter(BusinessFilingMessage businessFilingMessage);


}
