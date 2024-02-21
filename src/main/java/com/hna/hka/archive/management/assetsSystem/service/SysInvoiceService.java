package com.hna.hka.archive.management.assetsSystem.service;

import com.hna.hka.archive.management.assetsSystem.model.SysInvoice;
import com.hna.hka.archive.management.system.util.PageDataResult;

import java.util.List;
import java.util.Map;

/**
 * @ProjectName: rc
 * @Package: com.hna.hka.archive.management.assetsSystem.service
 * @ClassName: SysInvoiceService
 * @Author: 郭凯
 * @Description: 发票申请记录管理业务层（接口）
 * @Date: 2021/6/25 18:42
 * @Version: 1.0
 */
public interface SysInvoiceService {

    PageDataResult getInvoiceList(Integer pageNum, Integer pageSize, Map<String, Object> search);

    int addInvoice(SysInvoice sysInvoice);

    int editBilling(SysInvoice sysInvoice);

    List<SysInvoice> getInvoiceExcelList(Map<String, Object> search);
}
