package com.hna.hka.archive.management.assetsSystem.dao;

import com.hna.hka.archive.management.assetsSystem.model.SysOrderExceptionManagement;

import java.util.List;
import java.util.Map;

public interface SysOrderExceptionManagementMapper {
    int deleteByPrimaryKey(Long orderExceptionManagementId);

    int insert(SysOrderExceptionManagement record);

    int insertSelective(SysOrderExceptionManagement record);

    SysOrderExceptionManagement selectByPrimaryKey(Long orderExceptionManagementId);

    int updateByPrimaryKeySelective(SysOrderExceptionManagement record);

    int updateByPrimaryKey(SysOrderExceptionManagement record);

    List<SysOrderExceptionManagement> getOrderExceptionManagementList(Map<String, Object> search);

    List<SysOrderExceptionManagement> getCauseByList(String type);

}