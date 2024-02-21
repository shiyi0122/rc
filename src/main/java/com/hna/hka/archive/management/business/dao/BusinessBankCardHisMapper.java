package com.hna.hka.archive.management.business.dao;

import com.hna.hka.archive.management.business.model.BusinessBankCardHis;

public interface BusinessBankCardHisMapper {
    int deleteByPrimaryKey(Long id);

    int insert(BusinessBankCardHis record);

    int insertSelective(BusinessBankCardHis record);

    BusinessBankCardHis selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BusinessBankCardHis record);

    int updateByPrimaryKey(BusinessBankCardHis record);

	BusinessBankCardHis getBusinessBankCardHisByUserId(Long userId);
}