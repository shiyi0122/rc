package com.hna.hka.archive.management.appSystem.service;

import com.hna.hka.archive.management.appSystem.model.SysRobotSoftAssetInformation;
import com.hna.hka.archive.management.assetsSystem.model.SysRobotBelarcAdvisor;

import java.util.List;
import java.util.Map;

public interface AppRobotSoftAssetInformationService {
    SysRobotSoftAssetInformation getRobotSoftAssetInformationList(Map<String, Object> search);

    List<SysRobotBelarcAdvisor> getRobotSoftwareVersionList(String robotCode);
}

