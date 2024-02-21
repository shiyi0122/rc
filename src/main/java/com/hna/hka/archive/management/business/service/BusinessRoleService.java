package com.hna.hka.archive.management.business.service;

import com.hna.hka.archive.management.system.util.PageDataResult;

import java.util.Map;

/**
 * @ProjectName: IDEA-CODE
 * @Package: com.hna.hka.archive.management.business.service
 * @ClassName: BusinessRoleService
 * @Author: 郭凯
 * @Description: 招商平台角色管理业务层（接口）
 * @Date: 2020/10/12 14:08
 * @Version: 1.0
 */
public interface BusinessRoleService {

    PageDataResult getBusinessRoleList(Integer pageNum, Integer pageSize, Map<String, String> search);

    int delRole(Long roleId);
}
