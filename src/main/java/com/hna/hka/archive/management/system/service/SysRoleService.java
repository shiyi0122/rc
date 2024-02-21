package com.hna.hka.archive.management.system.service;

import com.hna.hka.archive.management.system.model.SysResource;
import com.hna.hka.archive.management.system.model.SysRole;
import com.hna.hka.archive.management.system.model.SysUsersRoleSpot;
import com.hna.hka.archive.management.system.util.PageDataResult;

import java.util.List;
import java.util.Set;

public interface SysRoleService {

    /**
     * 查询用户角色
     * @param loginName
     * @return
     */
    Set<String> getRoleByLoginName(String loginName);

    /**
     * 根据登录名查询用户的资源权限标识(以角色字符串返回)
     * @return
     */
    Set<String> getStringResPrmsByLoginName(String loginName);

    /**
     *  查询角色列表
     * @Author 郭凯
     * @Description
     * @Date 14:48 2020/5/12
     * @Param
     * @return
    **/
    PageDataResult getRoleList(Integer pageNum, Integer pageSize, SysRole sysRole);

    /**
     * 添加角色
     * @Author 郭凯
     * @Description
     * @Date 16:25 2020/5/12
     * @Param
     * @return
    **/
    int addRole(SysRole sysRole);

    /**
     * 删除角色
     * @param roleId
     * @return
     */
    int delRole(Long roleId);

    /**
     * 修改角色信息
     * @param sysRole
     * @return
     */
    int editRole(SysRole sysRole);

    /**
     * 查询权限设置回显数据
     * @param roleId
     * @return
     */
    List<SysResource> getEchoZtree(Long roleId);

    /**
     * 权限设置提交
     * @param roleId
     * @param resIds
     * @return
     */
    int addRoleResource(Long roleId, String resIds);

    /**
     * 获取权限分配用户
     * @return
     */
    List<SysRole> getScenicSpotRole();

    /**
     * 根据用户获取用户所有角色
     * @param userId
     * @return
     */
    List<SysRole> getUserRole(Long userId);

    List<SysUsersRoleSpot> getUserExamineRole(Long userId);


//    /**
//     * 根据角色id，获取角色下的权限
//     * @param roleId
//     * @return
//     */
//    List<SysResource> getRoleRes(Long roleId);

}
