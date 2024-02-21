package com.hna.hka.archive.management.appSystem.dao;

import com.hna.hka.archive.management.appSystem.model.AppRobotSoftAssetInformation;
import com.hna.hka.archive.management.assetsSystem.model.SysRobotBelarcAdvisor;
import com.hna.hka.archive.management.assetsSystem.model.SysRobotSoftAssetInformation;
import com.hna.hka.archive.management.system.model.SysRobot;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface AppRobotSoftAssetInformationMapper {
    int deleteByPrimaryKey(Long softAssetInformationId);

    int insert(AppRobotSoftAssetInformation record);

    int insertSelective(AppRobotSoftAssetInformation record);

    AppRobotSoftAssetInformation selectByPrimaryKey(Long softAssetInformationId);

    int updateByPrimaryKeySelective(AppRobotSoftAssetInformation record);

    int updateByPrimaryKey(AppRobotSoftAssetInformation record);

    SysRobotSoftAssetInformation getRobotSoftAssetInformation(Map<String, Object> search);

    com.hna.hka.archive.management.appSystem.model.SysRobotSoftAssetInformation getRobotSoftAssetInformationList(Map<String, Object> search);

    List<SysRobotBelarcAdvisor> appRobotSoftAssetInformationMapper(String robotCode);

    AppRobotSoftAssetInformation getRobotCodeByDetails(String robotCode);

}