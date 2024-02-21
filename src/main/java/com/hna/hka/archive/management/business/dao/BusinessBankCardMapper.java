package com.hna.hka.archive.management.business.dao;

import com.hna.hka.archive.management.business.model.BusinessBankCard;

import java.util.List;
import java.util.Map;

public interface BusinessBankCardMapper {
    int deleteByPrimaryKey(Long id);

    int insert(BusinessBankCard record);

    int insertSelective(BusinessBankCard record);

    BusinessBankCard selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BusinessBankCard record);

    int updateByPrimaryKey(BusinessBankCard record);

    List<BusinessBankCard> getBankCardList(Map<String, String> search);
}