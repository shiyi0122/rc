package com.hna.hka.archive.management.appSystem.dao;

import com.hna.hka.archive.management.appSystem.model.SysAppRole;

import java.util.List;
import java.util.Map;

/**
 * @ProjectName: IDEA-CODE
 * @Package: com.hna.hka.archive.management.appSystem.dao
 * @ClassName: SysAppRoleMapper
 * @Author: 郭凯
 * @Description:
 * @Date: 2020/11/23 14:45
 * @Version: 1.0
 */
public interface SysAppRoleMapper {

    List<SysAppRole> getRolesByLoginName(Map<String, Object> map);

    List<SysAppRole> getAppUsersList(Map<String, Object> search);

    int insertSelective(SysAppRole record);
}
