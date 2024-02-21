package com.hna.hka.archive.management.system.dao;

import com.hna.hka.archive.management.system.model.SysRoleResource;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SysRoleResourceMapper {
    int deleteByPrimaryKey(Long relationId);

    int insert(SysRoleResource record);

    int insertSelective(SysRoleResource record);

    SysRoleResource selectByPrimaryKey(Long relationId);

    int updateByPrimaryKeySelective(SysRoleResource record);

    int updateByPrimaryKey(SysRoleResource record);

    /**
     * 删除此角色所有的权限
     * @param roleId
     * @return
     */
    int deleteByPrimaryKeyRoleId(Long roleId);
}