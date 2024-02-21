package com.hna.hka.archive.management.assetsSystem.service;

import com.hna.hka.archive.management.assetsSystem.model.SysRobotPartsManagementType;
import com.hna.hka.archive.management.system.util.PageDataResult;

import java.util.List;

/**
 * @Author zhang
 * @Date 2022/12/11 16:54
 */
public interface SysRobotPartsManagementTypeService {
    int addRobotPartsManagementType(SysRobotPartsManagementType sysRobotPartsManagementType);


    int editRobotPartsManagementType(SysRobotPartsManagementType sysRobotPartsManagementType);

    int delRobotPartsManagementType(Long id);

    PageDataResult list(SysRobotPartsManagementType sysRobotPartsManagementType, Integer pageNum, Integer pageSize);

    List<SysRobotPartsManagementType> getPartsManagementTypeDropDown();


    SysRobotPartsManagementType getPartsManagementTypeByOne(String accessoriesTypeName);

}
