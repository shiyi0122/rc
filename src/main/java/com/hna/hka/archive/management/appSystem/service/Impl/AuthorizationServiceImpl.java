package com.hna.hka.archive.management.appSystem.service.Impl;

import com.hna.hka.archive.management.appSystem.dao.SysPermissionMapper;
import com.hna.hka.archive.management.appSystem.dao.SysResMapper;
import com.hna.hka.archive.management.appSystem.service.AuthorizationService;
import com.hna.hka.archive.management.system.model.SysResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ProjectName: IDEA-CODE
 * @Package: com.hna.hka.archive.management.appSystem.service.Impl
 * @ClassName: AuthorizationServiceImpl
 * @Author: 郭凯
 * @Description:
 * @Date: 2020/11/23 13:58
 * @Version: 1.0
 */
@Service
public class AuthorizationServiceImpl implements AuthorizationService {

    @Autowired
    private SysResMapper sysResMapper;

    @Autowired
    private SysPermissionMapper sysPermissionMapper;

    @Override
    /**
     * @Author 郭凯
     * @Description 根据登录名查询用户的可访问的菜单信息
     * @Date 14:00 2020/11/23
     * @Param [loginName]
     * @return java.util.List<com.hna.hka.archive.management.system.model.SysResource>
    **/
    public List<SysResource> getMenuByAppLoginName(String loginName) {
        Map<String, Object> map = new HashMap<String, Object>();
        // 用户名
        map.put("loginName", loginName);
        // 用户状态:状态正常
        map.put("status", "10");
        // 角色状态:状态正常
        map.put("roleStatus", "10");
        // 系统权限:状态正常
        map.put("prmsStatus", "10");
        // 系统资源：状态正常
        map.put("resStatus", "10");
        // 资源类型：功能菜单
        map.put("resType", "10");
        return sysResMapper.getMenuByAppLoginName(map);
    }

    @Override
    public List<SysResource> selectroleResourcePrms(String loginName, String resId, String roleId) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("loginName", loginName);
        map.put("resId", resId);
        map.put("roleId", roleId);
        map.put("status", "10");
        map.put("roleStatus", "10");
        map.put("prmsStatus", "10");
        map.put("resStatus", "10");
        // 资源类型：功能菜单
        map.put("resType", "10");
        List<SysResource> resPrmsIdentity = sysPermissionMapper.getRoleResPrmsByLoginName(map);
        return resPrmsIdentity;
    }
}
