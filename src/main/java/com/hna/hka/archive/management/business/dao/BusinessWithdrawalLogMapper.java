package com.hna.hka.archive.management.business.dao;

import com.hna.hka.archive.management.business.model.BusinessWithdrawalLog;

import java.util.List;
import java.util.Map;

public interface BusinessWithdrawalLogMapper {
    int deleteByPrimaryKey(Long id);

    int insert(BusinessWithdrawalLog record);

    int insertSelective(BusinessWithdrawalLog record);

    BusinessWithdrawalLog selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BusinessWithdrawalLog record);

    int updateByPrimaryKey(BusinessWithdrawalLog record);

    List<BusinessWithdrawalLog> getWithdrawalLogList(Map<String, String> search);
}