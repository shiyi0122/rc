package com.hna.hka.archive.management.appSystem.service.Impl;

import com.hna.hka.archive.management.appSystem.dao.AppRobotSoftAssetInformationMapper;
import com.hna.hka.archive.management.appSystem.model.SysRobotSoftAssetInformation;
import com.hna.hka.archive.management.appSystem.service.AppRobotSoftAssetInformationService;
import com.hna.hka.archive.management.assetsSystem.dao.SysRobotBelarcAdvisorMapper;
import com.hna.hka.archive.management.assetsSystem.dao.SysRobotSoftAssetInformationMapper;
import com.hna.hka.archive.management.assetsSystem.model.SysRobotBelarcAdvisor;
import com.hna.hka.archive.management.system.dao.SysOrderMapper;
import com.hna.hka.archive.management.system.util.ToolUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @ClassName：AppRobotSoftAssetInformationServiceImpl
 * @Author: gouteng
 * @Date: 2022-12-29 17:36
 * @Description: 必须描述类做什么事情, 实现什么功能
 */
@Service
public class AppRobotSoftAssetInformationServiceImpl implements AppRobotSoftAssetInformationService {

    @Autowired
    private AppRobotSoftAssetInformationMapper appRobotSoftAssetInformationMapper;
    @Autowired
    private SysRobotSoftAssetInformationMapper robotSoftAssetInformationMapper;
    @Autowired
    private SysRobotBelarcAdvisorMapper sysRobotBelarcAdvisorMapper;
    @Autowired
    private SysOrderMapper sysOrderMapper;

    @Override
    public SysRobotSoftAssetInformation getRobotSoftAssetInformationList(Map<String, Object> search) {
        SysRobotSoftAssetInformation robotSoftAssetInformationList = appRobotSoftAssetInformationMapper.getRobotSoftAssetInformationList(search);

        Integer type = (Integer) search.get("type");

        //当天运行时间
        Integer dayTime = sysOrderMapper.getRobotCodeByDayTime(robotSoftAssetInformationList.getRobotCode());
        robotSoftAssetInformationList.setDayTime(dayTime);
        //当月运行时间
        Integer monthTime = sysOrderMapper.getRobotCodeByMonthTime(robotSoftAssetInformationList.getRobotCode());
        robotSoftAssetInformationList.setMonthTime(monthTime);

        //当年运行时间
        Integer yearTime = sysOrderMapper.getRobotCodeByYearTime(robotSoftAssetInformationList.getRobotCode());
        robotSoftAssetInformationList.setYearTime(yearTime);

        //累积运行时长
        Integer runTime = sysOrderMapper.getRobotCodeByAccumulateTime(robotSoftAssetInformationList.getRobotCode());
        robotSoftAssetInformationList.setRunTime(runTime);


        return robotSoftAssetInformationList;
    }

    @Override
    public  List<SysRobotBelarcAdvisor> getRobotSoftwareVersionList(String robotCode) {
        List<SysRobotBelarcAdvisor> getRobotSoftwareVersionList = appRobotSoftAssetInformationMapper.appRobotSoftAssetInformationMapper(robotCode);

        for (SysRobotBelarcAdvisor robotBelarcAdvisor:getRobotSoftwareVersionList){
            for (int i=1;i<=3;i++){
                if (i==2){
                    SysRobotBelarcAdvisor robotVersion = sysRobotBelarcAdvisorMapper.getRobotVersion(robotBelarcAdvisor.getRobotCode(), "2");
                    if (ToolUtil.isNotEmpty(robotVersion)){
                        robotBelarcAdvisor.setMasterControlFirmwareVersion(robotVersion.getUpgradedVersion());
                        robotBelarcAdvisor.setUpdateDate(robotVersion.getUpdateDate());
                    }
                }else if (i==3){
                    SysRobotBelarcAdvisor robotVersion = sysRobotBelarcAdvisorMapper.getRobotVersion(robotBelarcAdvisor.getRobotCode(), "3");
                    if (ToolUtil.isNotEmpty(robotVersion)) {
                        robotBelarcAdvisor.setRangingModularVersion(robotVersion.getUpgradedVersion());
                        robotBelarcAdvisor.setUpdateDate(robotVersion.getUpdateDate());
                    }
                }
            }
        }

        return getRobotSoftwareVersionList;
    }
}
