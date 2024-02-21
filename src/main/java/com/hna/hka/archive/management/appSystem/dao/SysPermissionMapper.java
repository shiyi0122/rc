package com.hna.hka.archive.management.appSystem.dao;

import com.hna.hka.archive.management.system.model.SysResource;

import java.util.List;
import java.util.Map;

/**
 * @ProjectName: IDEA-CODE
 * @Package: com.hna.hka.archive.management.appSystem.dao
 * @ClassName: SysPermissionMapper
 * @Author: 郭凯
 * @Description:
 * @Date: 2020/11/23 14:51
 * @Version: 1.0
 */
public interface SysPermissionMapper {

    List<SysResource> getRoleResPrmsByLoginName(Map<String, Object> map);
}
