package com.hna.hka.archive.management.business.dao;

import com.hna.hka.archive.management.business.model.BusinessUsersRole;

public interface BusinessUsersRoleMapper {
    int deleteByPrimaryKey(Long id);

    int insert(BusinessUsersRole record);

    int insertSelective(BusinessUsersRole record);

    BusinessUsersRole selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BusinessUsersRole record);

    int updateByPrimaryKey(BusinessUsersRole record);

    BusinessUsersRole getBusinessUsersByUserId(Long userId);
}