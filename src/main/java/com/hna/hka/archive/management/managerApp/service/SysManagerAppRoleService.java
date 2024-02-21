package com.hna.hka.archive.management.managerApp.service;

import com.hna.hka.archive.management.appSystem.model.SysAppRole;
import com.hna.hka.archive.management.system.util.PageDataResult;

import java.util.List;
import java.util.Map;

/**
 * @ProjectName: rc
 * @Package: com.hna.hka.archive.management.managerApp.service
 * @ClassName: SysAppRoleServiceImpl
 * @Author: 郭凯
 * @Description: APP角色管理业务层（接口）
 * @Date: 2021/6/4 15:16
 * @Version: 1.0
 */
public interface SysManagerAppRoleService {

    PageDataResult getAppRoleList(Integer pageNum, Integer pageSize, Map<String, Object> search);

    int addAppRole(SysAppRole sysAppRole);

    int addRoleResource(Long roleId, String resIds);

    List<SysAppRole> getAppRoleLists();
}
