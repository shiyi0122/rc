package com.hna.hka.archive.management.business.dao;

import com.hna.hka.archive.management.business.model.BusinessInvoiceRecord;

import java.util.List;
import java.util.Map;

public interface BusinessInvoiceRecordMapper {
    int deleteByPrimaryKey(Long id);

    int insert(BusinessInvoiceRecord record);

    int insertSelective(BusinessInvoiceRecord record);

    BusinessInvoiceRecord selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BusinessInvoiceRecord record);

    int updateByPrimaryKey(BusinessInvoiceRecord record);

    List<BusinessInvoiceRecord> getInvoiceRecordList(Map<String, String> search);
}