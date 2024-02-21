package com.hna.hka.archive.management.system.dao;

import com.hna.hka.archive.management.system.model.SysCurrentUserAccount;

import java.util.List;
import java.util.Map;

public interface SysCurrentUserAccountMapper {
    int deleteByPrimaryKey(Long accountId);

    int insert(SysCurrentUserAccount record);

    int insertSelective(SysCurrentUserAccount record);

    SysCurrentUserAccount selectByPrimaryKey(Long accountId);

    int updateByPrimaryKeySelective(SysCurrentUserAccount record);

    int updateByPrimaryKey(SysCurrentUserAccount record);

    List<SysCurrentUserAccount> getCurrentUserAccountList(Map<String, String> search);

    List<SysCurrentUserAccount> getUploadExcelCurrentUserAccount(Map<String, String> search);

    SysCurrentUserAccount selectAccountByUserId(Long userId);

    SysCurrentUserAccount getSysCurrentUserAccountById(Long accountId);
}