package com.hna.hka.archive.management.system.service;

import com.hna.hka.archive.management.managerApp.model.SysRobotAdministratorsVersion;
import com.hna.hka.archive.management.system.util.PageDataResult;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * @ProjectName: IDEA-CODE
 * @Package: com.hna.hka.archive.management.system.service
 * @ClassName: SysRobotAdministratorsVersionService
 * @Author: 郭凯
 * @Description: APP版本管理业务层（实现）
 * @Date: 2020/11/19 9:13
 * @Version: 1.0
 */
public interface SysRobotAdministratorsVersionService {

    PageDataResult getAdministratorsVersionList(Integer pageNum, Integer pageSize, Map<String, Object> search);

    int delAdministratorsVersion(Long versionId);

    int addAdministratorsVersion(MultipartFile file, SysRobotAdministratorsVersion sysRobotAdministratorsVersion);

    SysRobotAdministratorsVersion getAdministratorsVersion();

    int editAdministratorsVersion(SysRobotAdministratorsVersion sysRobotAdministratorsVersion);
}
