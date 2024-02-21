package com.hna.hka.archive.management.assetsSystem.service;

import com.hna.hka.archive.management.assetsSystem.model.SysRobotPartsManagement;
import com.hna.hka.archive.management.system.util.PageDataResult;

import java.util.List;
import java.util.Map;

/**
 * @ProjectName: rc
 * @Package: com.hna.hka.archive.management.assetsSystem.service
 * @ClassName: SysRobotPartsManagementService
 * @Author: 郭凯
 * @Description: 机器人配件管理业务层（接口）
 * @Date: 2021/5/28 18:04
 * @Version: 1.0
 */
public interface SysRobotPartsManagementService {

    PageDataResult getRobotPartsManagementList(Integer pageNum, Integer pageSize, Map<String, Object> search);

    int addRobotPartsManagement(SysRobotPartsManagement sysRobotPartsManagement);

    int editRobotPartsManagement(SysRobotPartsManagement sysRobotPartsManagement);

    int delRobotPartsManagement(Long partsManagementId);

    List<SysRobotPartsManagement> getRobotPartsManagementExcel(Map<String, Object> search);

    List<SysRobotPartsManagement> getAppAccessoriesApplicationName(Map<String,Object> search);

    SysRobotPartsManagement getAccessoryNameByParts(String partsManagementName);


    Long getAmount(Map<String, Object> search);
}
