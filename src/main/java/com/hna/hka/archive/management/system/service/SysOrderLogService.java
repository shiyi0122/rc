package com.hna.hka.archive.management.system.service;

import com.hna.hka.archive.management.system.model.ExternalData.RefundOrderList;
import com.hna.hka.archive.management.system.model.SysOrderLog;
import com.hna.hka.archive.management.system.util.PageDataResult;

import java.util.List;
import java.util.Map;

/**
 * @ProjectName: IDEA-CODE
 * @Package: com.hna.hka.archive.management.system.service
 * @ClassName: SysOrderLogService
 * @Author: 郭凯
 * @Description: 订单退款日志业务层（接口）
 * @Date: 2020/5/29 10:22
 * @Version: 1.0
 */
public interface SysOrderLogService {

    /**
     * 订单退款日志列表查询
     * @param pageNum
     * @param pageSize
     * @param search
     * @return
     */
    PageDataResult getOperationLogRefundOrderList(Integer pageNum, Integer pageSize, Map<String, Object> search);

    /**
     * 下载订单退款日志Excel表
     * @param search
     * @return
     */
    List<SysOrderLog> getUploadExcelOrderLog(Map<String, Object> search);

    int insertOrderLog(SysOrderLog orderLog);

    SysOrderLog selectByOrderLogNumber(String orderNumber);

    List<RefundOrderList> getOrderNumberLogList(String orderNumber);


}
