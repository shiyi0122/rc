package com.hna.hka.archive.management.managerApp.service;

import com.hna.hka.archive.management.managerApp.model.SysManagerAppRes;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @ProjectName: rc
 * @Package: com.hna.hka.archive.management.managerApp.service
 * @ClassName: SysManagerAppResService
 * @Author: 郭凯
 * @Description: 管理者APP菜单管理业务层（接口）
 * @Date: 2021/6/4 10:23
 * @Version: 1.0
 */
public interface SysManagerAppResService {

    List<SysManagerAppRes> getManagerAppResList(SysManagerAppRes managerAppRes);

    int addManagerAppRes(SysManagerAppRes sysResource);

    List<SysManagerAppRes> getEchoAppZtree(Long roleId);

    List<SysManagerAppRes> getAppUserResource(Map<String, Object> search);

    Set<String> getAppUserPermissions(Map<String, Object> search);

    int delManagerAppRes(Long resId);
}
