package com.hna.hka.archive.management.system.dao;

import com.hna.hka.archive.management.system.model.SysResource;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface SysResourceMapper {
    int deleteByPrimaryKey(Long resId);

    int insert(SysResource record);

    int insertSelective(SysResource record);

    SysResource selectByPrimaryKey(Long resId);

    int updateByPrimaryKeySelective(SysResource record);

    int updateByPrimaryKey(SysResource record);

    /**
     * 查询头部菜单栏
     * @return
     */
    List<SysResource> getResourceNav(@Param("scenicSpotId") String scenicSpotId,@Param("userName") String userName);

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
     * 查询父节点数据
     * @param resCode
     * @return
     */
    SysResource selectByPrimaryKeyCode(String resCode);

    /**
     * 查询权限设置回显数据
     * @param roleId
     * @return
     */
    List<SysResource> getEchoZtree(Long roleId);
    /**
     * 根据角色获取角色下的所有权限
     * @param roleId
     * @return
     */
    List<SysResource> getRoleResourceList(@Param("roleId") Long roleId);

}