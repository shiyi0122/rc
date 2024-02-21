package com.hna.hka.archive.management.appSystem.service.Impl;

import com.hna.hka.archive.management.appSystem.dao.SysAppRoleMapper;
import com.hna.hka.archive.management.appSystem.model.SysAppRole;
import com.hna.hka.archive.management.appSystem.service.SysAppRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @ProjectName: IDEA-CODE
 * @Package: com.hna.hka.archive.management.appSystem.service.Impl
 * @ClassName: SysAppRoleServiceImpl
 * @Author: 郭凯
 * @Description:
 * @Date: 2020/11/23 14:44
 * @Version: 1.0
 */
@Service
public class SysAppRoleServiceImpl implements SysAppRoleService {

    @Autowired
    private SysAppRoleMapper sysAppRoleMapper;

    @Override
    /**
     * @Author 郭凯
     * @Description 根据用户的登录名称查询SysRole
     * @Date 14:45 2020/11/23
     * @Param [map]
     * @return com.hna.hka.archive.management.appSystem.model.SysAppRole
    **/
    public SysAppRole getRolesByLoginName(Map<String, Object> map) {
        List<SysAppRole> sysRoleList = sysAppRoleMapper.getRolesByLoginName(map);
        return sysRoleList.size()!=0?sysRoleList.get(0):null;
    }
}
