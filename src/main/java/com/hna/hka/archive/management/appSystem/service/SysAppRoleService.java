package com.hna.hka.archive.management.appSystem.service;

import com.hna.hka.archive.management.appSystem.model.SysAppRole;

import java.util.Map;

/**
 * @ProjectName: IDEA-CODE
 * @Package: com.hna.hka.archive.management.appSystem.service
 * @ClassName: SysAppRoleService
 * @Author: 郭凯
 * @Description:
 * @Date: 2020/11/23 14:43
 * @Version: 1.0
 */
public interface SysAppRoleService {

    SysAppRole getRolesByLoginName(Map<String, Object> map);
}
