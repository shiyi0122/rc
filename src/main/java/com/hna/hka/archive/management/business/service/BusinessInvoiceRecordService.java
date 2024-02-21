package com.hna.hka.archive.management.business.service;

import com.hna.hka.archive.management.system.util.PageDataResult;

import java.util.Map;

/**
 * @ProjectName: IDEA-CODE
 * @Package: com.hna.hka.archive.management.business.service
 * @ClassName: BusinessInvoiceRecordService
 * @Author: 郭凯
 * @Description: 发票管理业务层（接口）
 * @Date: 2020/8/13 9:38
 * @Version: 1.0
 */
public interface BusinessInvoiceRecordService {

    PageDataResult getInvoiceRecordList(Integer pageNum, Integer pageSize, Map<String, String> search);
}
