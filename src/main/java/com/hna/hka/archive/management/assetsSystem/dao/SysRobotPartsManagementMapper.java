package com.hna.hka.archive.management.assetsSystem.dao;

import com.hna.hka.archive.management.assetsSystem.model.SysRobotPartsManagement;
import io.lettuce.core.dynamic.annotation.Param;

import java.util.List;
import java.util.Map;

public interface SysRobotPartsManagementMapper {
    int deleteByPrimaryKey(Long partsManagementId);

    int insert(SysRobotPartsManagement record);

    int insertSelective(SysRobotPartsManagement record);

    SysRobotPartsManagement selectByPrimaryKey(Long partsManagementId);

    int updateByPrimaryKeySelective(SysRobotPartsManagement record);

    int updateByPrimaryKey(SysRobotPartsManagement record);

    List<SysRobotPartsManagement> getRobotPartsManagementList(Map<String, Object> search);

    List<SysRobotPartsManagement> getAppAccessoriesApplicationName(Map<String,Object> search);

    List<SysRobotPartsManagement> accessoriesDetails(Long errorRecordsId);

    List<SysRobotPartsManagement> selectRobotByName(String accessoryName,String accessoriesCode);

    SysRobotPartsManagement getAmount(Map<String, Object> search);
}