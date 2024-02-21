package com.hna.hka.archive.management.assetsSystem.model;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

@Data
public class SysRobotSoftwareVersion {
    private Long softwareVersionId;

    @Excel(name = "机器人编号" ,orderNum = "0" , width = 20)
    private String robotCode;

    private Long scenicSpotId;

    private String upgradeModule;

    @Excel(name = "升级前版本" ,orderNum = "4" , width = 20)
    private String preUpgradeVersion;

    @Excel(name = "升级后版本" ,orderNum = "5" , width = 20)
    private String upgradedVersion;

    @Excel(name = "升级状态" ,replace = {"升级成功_1","升级中_2","升级失败_3"},orderNum = "5" , width = 20)
    private String state;

    @Excel(name = "升级时间" ,orderNum = "6" , width = 20)
    private String createDate;

    private String updateDate;


    @Excel(name = "机器人型号" ,orderNum = "1" , width = 20)
    private String robotModel;

    @Excel(name = "景区名称" ,orderNum = "2" , width = 20)
    private String scenicSpotName;

    @Excel(name = "升级模块" ,replace = {"APP端_1","主控_2","超声_3"},orderNum = "3" , width = 20)
    private String upgradeModuleName;
}