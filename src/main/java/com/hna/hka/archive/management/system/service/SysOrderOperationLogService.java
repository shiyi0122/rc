package com.hna.hka.archive.management.system.service;

import com.hna.hka.archive.management.system.model.SysOrderExcel;
import com.hna.hka.archive.management.system.model.SysOrderOperationLog;
import com.hna.hka.archive.management.system.model.SysOrderOperationLogExcel;
import com.hna.hka.archive.management.system.util.PageDataResult;

import java.util.List;
import java.util.Map;

/**
 * @ProjectName: IDEA-CODE
 * @Package: com.hna.hka.archive.management.system.service
 * @ClassName: SysOrderOperationLogService
 * @Author: 郭凯
 * @Description: 订单操作日志业务层(接口)
 * @Date: 2020/6/2 9:20
 * @Version: 1.0
 */
public interface SysOrderOperationLogService {

    /**
     * 订单操作日志列表查询
     * @param pageNum
     * @param pageSize
     * @param search
     * @return
     */
    PageDataResult getOrderOperationLogList(Integer pageNum, Integer pageSize, Map<String, Object> search);

    /**
     * 订单操作日志列表下载
     * @param search
     * @return
     */
    List<SysOrderOperationLogExcel> getOrderOperationLogExcel(Map<String, Object> search);

}
