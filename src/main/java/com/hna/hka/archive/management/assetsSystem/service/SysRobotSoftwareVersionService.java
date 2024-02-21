package com.hna.hka.archive.management.assetsSystem.service;

import com.hna.hka.archive.management.assetsSystem.model.SysRobotSoftwareVersion;
import com.hna.hka.archive.management.system.util.PageDataResult;

import java.util.List;
import java.util.Map;

/**
 * @ProjectName: rc
 * @Package: com.hna.hka.archive.management.assetsSystem.service
 * @ClassName: SysRobotSoftwareVersionService
 * @Author: 郭凯
 * @Description: 机器人升级管理业务层（接口）
 * @Date: 2021/6/26 20:13
 * @Version: 1.0
 */
public interface SysRobotSoftwareVersionService {

    PageDataResult getRobotSoftwareVersionList(Integer pageNum, Integer pageSize, Map<String, Object> search);

    List<SysRobotSoftwareVersion> getRobotSoftwareVersionExcel(Map<String, Object> search);
}
