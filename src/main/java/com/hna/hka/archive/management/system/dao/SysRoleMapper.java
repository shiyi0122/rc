package com.hna.hka.archive.management.system.dao;

import com.hna.hka.archive.management.system.model.SysRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface SysRoleMapper {
    int deleteByPrimaryKey(Long roleId);

    int insert(SysRole record);

    int insertSelective(SysRole record);

    SysRole selectByPrimaryKey(Long roleId);

    int updateByPrimaryKeySelective(SysRole record);

    int updateByPrimaryKey(SysRole record);

    /**
     * 根据登录名查询用户角色信息(以角色字符串返回)
     * @param map
     * @return
     */
    List<SysRole> getRolesByLoginName(Map<String, Object> map);

    /**
     * 根据登录名查询用户的资源权限标识(以角色字符串返回)
     * @param map
     * @return
     */
    List<Map<String, String>> getRoleResPrmsByLoginName(Map<String, Object> map);

    /**
     * 查询角色列表
     * @param sysRole
     * @return
     */
    List<SysRole> getRoleList(@Param("sysRole") SysRole sysRole);

    /**
     * 根据用户获取用户所有角色
     * @param userId
     * @return
     */
    List<SysRole> getUserRole(Long userId);
    /**
     * 根据用户获取用户所有角色
     * @param userId
     * @return
     */
    List<SysRole> getUserExamineRole(Long userId);

}