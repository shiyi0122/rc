package com.hna.hka.archive.management.system.dao;

import com.hna.hka.archive.management.system.model.SysCurrentUserAgreementLog;

import java.util.List;
import java.util.Map;

public interface SysCurrentUserAgreementLogMapper {
    int deleteByPrimaryKey(Long agreementId);

    int insert(SysCurrentUserAgreementLog record);

    int insertSelective(SysCurrentUserAgreementLog record);

    SysCurrentUserAgreementLog selectByPrimaryKey(Long agreementId);

    int updateByPrimaryKeySelective(SysCurrentUserAgreementLog record);

    int updateByPrimaryKey(SysCurrentUserAgreementLog record);

    List<SysCurrentUserAgreementLog> getCurrentUserAgreementLogList(Map<String, String> search);
}