package com.hna.hka.archive.management.assetsSystem.dao;

import com.hna.hka.archive.management.assetsSystem.model.SysInvoice;
import io.lettuce.core.dynamic.annotation.Param;

import java.util.List;
import java.util.Map;

public interface SysInvoiceMapper {
    int deleteByPrimaryKey(Long invoiceId);

    int insert(SysInvoice record);

    int insertSelective(SysInvoice record);

    SysInvoice selectByPrimaryKey(Long invoiceId);

    int updateByPrimaryKeySelective(SysInvoice record);

    int updateByPrimaryKey(SysInvoice record);

    List<SysInvoice> getInvoiceList(Map<String, Object> search);

    SysInvoice getInvoiceByNumber(@Param("orderNumber") String orderNumber);
}