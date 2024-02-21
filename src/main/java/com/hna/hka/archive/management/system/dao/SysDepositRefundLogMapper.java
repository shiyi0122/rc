package com.hna.hka.archive.management.system.dao;

import com.hna.hka.archive.management.system.model.SysDepositRefundLog;

import java.util.List;
import java.util.Map;

public interface SysDepositRefundLogMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SysDepositRefundLog record);

    int insertSelective(SysDepositRefundLog record);

    SysDepositRefundLog selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysDepositRefundLog record);

    int updateByPrimaryKey(SysDepositRefundLog record);

    List<SysDepositRefundLog> getDepositRefundLogList(Map<String, String> search);
}