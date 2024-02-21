package com.hna.hka.archive.management.business.dao;

import com.hna.hka.archive.management.business.model.BusinessOrder;

import java.util.List;
import java.util.Map;

public interface BusinessOrderMapper {
    int deleteByPrimaryKey(Long id);

    int insert(BusinessOrder record);

    int insertSelective(BusinessOrder record);

    BusinessOrder selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BusinessOrder record);

    int updateByPrimaryKey(BusinessOrder record);

    List<BusinessOrder> getOrderList(Map<String, String> search);
}