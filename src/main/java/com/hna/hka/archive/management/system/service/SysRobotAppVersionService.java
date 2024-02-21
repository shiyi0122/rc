package com.hna.hka.archive.management.system.service;

import com.hna.hka.archive.management.system.model.SysRobotAppVersion;
import com.hna.hka.archive.management.system.util.PageDataResult;
import org.springframework.web.multipart.MultipartFile;

/**
 * @ProjectName: IDEA-CODE
 * @Package: com.hna.hka.archive.management.system.service
 * @ClassName: SysRobotAppVersionService
 * @Author: 郭凯
 * @Description: PAD版本管理业务层（接口）
 * @Date: 2020/5/29 16:56
 * @Version: 1.0
 */
public interface SysRobotAppVersionService {

    /**
     * PAD版本管理列表查询
     * @param pageNum
     * @param pageSize
     * @param sysRobotAppVersion
     * @return
     */
    PageDataResult getRobotVersionPadList(Integer pageNum, Integer pageSize, SysRobotAppVersion sysRobotAppVersion);

    /**
     * 查询当前版本号
     * @param scenicSpotId
     * @return
     */
    SysRobotAppVersion getAppVersionNumber(Long scenicSpotId);

    /**
     * 新增PAD信息
     * @param sysRobotAppVersion
     * @param file
     * @return
     */
    int addRobotVersionPad(SysRobotAppVersion sysRobotAppVersion, MultipartFile file);

    /**
     * 删除PAD信息
     * @param versionId
     * @return
     */
    int delRobotVersionPad(Long versionId);

    int editRobotVersionPad(SysRobotAppVersion sysRobotAppVersion);
}
