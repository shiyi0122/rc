package com.hna.hka.archive.management.managerApp.dao;

import com.hna.hka.archive.management.managerApp.model.SysAppRoleResource;

public interface SysAppRoleResourceMapper {
    int deleteByPrimaryKey(Long relationId);

    int insert(SysAppRoleResource record);

    int insertSelective(SysAppRoleResource record);

    SysAppRoleResource selectByPrimaryKey(Long relationId);

    int updateByPrimaryKeySelective(SysAppRoleResource record);

    int updateByPrimaryKey(SysAppRoleResource record);

    void deleteByRoleId(Long roleId);
}