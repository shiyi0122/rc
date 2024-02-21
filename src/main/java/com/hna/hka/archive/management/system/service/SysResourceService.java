package com.hna.hka.archive.management.system.service;

import com.hna.hka.archive.management.system.model.SysResource;

import java.util.List;
import java.util.Map;

public interface SysResourceService {

    /**
     * 查询头部菜单栏
     * @return
     */
    List<SysResource> getResourceNav();

    /**
     * 根据顶级菜单查询二三级菜单
     * @param search
     * @return
     */
    List<SysResource> getResourceLeft(Map<String, String> search);

    /**
     * 菜单列表查询
     * @return
     */
    List<SysResource> getResourceList(SysResource sysResource);

    /**
     * 添加菜单栏
     * @param sysResource
     * @return
     */
    int addResource(SysResource sysResource);

    /**
     * 删除菜单栏
     * @param resId
     * @return
     */
    int delResource(Long resId);

    /**
     * 根据ID查询回显对象
     * @param resId
     * @return
     */
    SysResource getResourceById(Long resId);

    /**
     * 菜单栏修改
     * @param sysResource
     * @return
     */
    int updateResource(SysResource sysResource);

    /**
     * 根据角色获取角色下的所有权限
     * @param roleId
     * @return
     */
    List<SysResource> getRoleResourceList(Long roleId);

}
