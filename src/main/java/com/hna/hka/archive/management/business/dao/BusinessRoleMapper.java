package com.hna.hka.archive.management.business.dao;

import com.hna.hka.archive.management.business.model.BusinessRole;

import java.util.List;
import java.util.Map;

public interface BusinessRoleMapper {
    int deleteByPrimaryKey(Long roleId);

    int insert(BusinessRole record);

    int insertSelective(BusinessRole record);

    BusinessRole selectByPrimaryKey(Long roleId);

    int updateByPrimaryKeySelective(BusinessRole record);

    int updateByPrimaryKey(BusinessRole record);

    List<BusinessRole> getBusinessRoleList(Map<String, String> search);
}