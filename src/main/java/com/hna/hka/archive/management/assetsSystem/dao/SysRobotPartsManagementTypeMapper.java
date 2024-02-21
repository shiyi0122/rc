package com.hna.hka.archive.management.assetsSystem.dao;

import com.hna.hka.archive.management.assetsSystem.model.SysRobotPartsManagementType;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author zhang
 * @Date 2022/12/11 16:55
 */
public interface SysRobotPartsManagementTypeMapper {
    int insert(SysRobotPartsManagementType sysRobotPartsManagementType);

    int updateByPrimaryKeySelective(SysRobotPartsManagementType sysRobotPartsManagementType);

    List<SysRobotPartsManagementType> list (@Param("partsManagementType") String partsManagementType);

    SysRobotPartsManagementType listOne(Long id);

    int del(Long id);

    SysRobotPartsManagementType getPartsManagementTypeByOne( @Param("accessoriesTypeName") String accessoriesTypeName);

}
