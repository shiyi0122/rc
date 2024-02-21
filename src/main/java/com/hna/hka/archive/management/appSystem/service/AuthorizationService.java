package com.hna.hka.archive.management.appSystem.service;

import com.hna.hka.archive.management.system.model.SysResource;

import java.util.List;

/**
 * @ProjectName: IDEA-CODE
 * @Package: com.hna.hka.archive.management.appSystem.service
 * @ClassName: AuthorizationService
 * @Author: 郭凯
 * @Description:
 * @Date: 2020/11/23 13:58
 * @Version: 1.0
 */
public interface AuthorizationService {

    List<SysResource> getMenuByAppLoginName(String loginName);

    List<SysResource> selectroleResourcePrms(String loginName, String resId, String roleId);
}
