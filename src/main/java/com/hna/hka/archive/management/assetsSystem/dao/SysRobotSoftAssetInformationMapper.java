package com.hna.hka.archive.management.assetsSystem.dao;

import com.hna.hka.archive.management.assetsSystem.model.SysRobotSoftAssetInformation;
import io.lettuce.core.dynamic.annotation.Param;

import java.util.List;
import java.util.Map;

public interface SysRobotSoftAssetInformationMapper {
    int deleteByPrimaryKey(Long softAssetInformationId);

    int insert(SysRobotSoftAssetInformation record);

    int insertSelective(SysRobotSoftAssetInformation record);

    SysRobotSoftAssetInformation selectByPrimaryKey(Long softAssetInformationId);

    int updateByPrimaryKeySelective(SysRobotSoftAssetInformation record);

    int updateByPrimaryKey(SysRobotSoftAssetInformation record);

    List<SysRobotSoftAssetInformation> getRobotSoftAssetInformationList(Map<String, Object> search);

    SysRobotSoftAssetInformation getRobotSoftAssetInformationByRobotId(Long robotId);

    SysRobotSoftAssetInformation getAppRobotSoftAssetInformation(Map<String, Object> search);

    SysRobotSoftAssetInformation getRobotSoftAssetInformationByRobotCode(@Param("robotCode") String robotCode);

    List<SysRobotSoftAssetInformation> getRobotSoftAssetInformationListAll();

    SysRobotSoftAssetInformation selectRobotSoftByRobotCode(String robotCode);
}