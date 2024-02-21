package com.hna.hka.archive.management.system.service;

import com.hna.hka.archive.management.system.model.SysRobotEmbeddedVersion;
import com.hna.hka.archive.management.system.util.PageDataResult;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * @ProjectName: IDEA-CODE
 * @Package: com.hna.hka.archive.management.system.service
 * @ClassName: SysRobotEmbeddedVersionService
 * @Author: 郭凯
 * @Description: 嵌入式软件管理业务层（接口）
 * @Date: 2020/9/8 13:44
 * @Version: 1.0
 */
public interface SysRobotEmbeddedVersionService {

    PageDataResult getRobotEmbeddedVersionList(Integer pageNum, Integer pageSize, Map<String, String> search);

    int addRobotEmbeddedVersion(SysRobotEmbeddedVersion sysRobotEmbeddedVersion, MultipartFile file);

    int delRobotEmbeddedVersion(Long embeddedId);

    int editRobotEmbeddedVersion(SysRobotEmbeddedVersion sysRobotEmbeddedVersion, MultipartFile file);
}
