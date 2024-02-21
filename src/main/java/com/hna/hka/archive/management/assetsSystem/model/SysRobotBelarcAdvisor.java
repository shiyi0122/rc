package com.hna.hka.archive.management.assetsSystem.model;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

@Data
public class SysRobotBelarcAdvisor {
    private Long robotBelarcAdvisorId;

    private Long robotId;


    @Excel(name = "机器人型号",width = 10,orderNum = "2")
    private String robotModel;

    @Excel(name = "主控型号",width = 10,orderNum = "5")
    private String masterControlModel;

    @Excel(name = "主控固件版本号",width = 10,orderNum = "6")
    private String masterControlFirmwareVersion;

    @Excel(name = "主控ID",width = 10,orderNum = "7")
    private String masterControlId;

    @Excel(name = "PAD型号",width = 10,orderNum = "8")
    private String padVersion;

    @Excel(name = "PAD固件版本号",width = 10,orderNum = "9")
    private String padFirmwareVersion;

    @Excel(name = "PAD厂家",width = 10,orderNum = "10")
    private String padManufactor;

    @Excel(name = "MEID",width = 10,orderNum = "11")
    private String meid;

    @Excel(name = "驱动器型号",width = 10,orderNum = "12")
    private String driveModel;

    @Excel(name = "驱动器固件版本号",width = 10,orderNum = "13")
    private String driveFirmwareVersion;

    @Excel(name = "驱动器厂家",width = 10,orderNum = "14")
    private String driverManufacturer;

    @Excel(name = "驱动器ID",width = 10,orderNum = "15")
    private String driveId;

    @Excel(name = "电机型号",width = 10,orderNum = "16")
    private String motorModel;

    @Excel(name = "电机ID",width = 10,orderNum = "18")
    private String motorId;

    @Excel(name = "电机厂家",width = 10,orderNum = "17")
    private String motorManufacturer;

    @Excel(name = "电池型号",width = 10,orderNum = "19")
    private String batteryModel;

    @Excel(name = "电池厂家",width = 10,orderNum = "20")
    private String batteryManufacturer;

    @Excel(name = "电池ID",width = 10,orderNum = "21")
    private String batteryId;

    @Excel(name = "机器人功率",width = 10,orderNum = "22")
    private String robotPower;

    @Excel(name = "充电器型号",width = 10,orderNum = "23")
    private String chargerModel;

    @Excel(name = "充电器厂家",width = 10,orderNum = "24")
    private String chargerManufacturer;

    @Excel(name = "充电器ID",width = 10,orderNum = "25")
    private String chargerId;

    @Excel(name = "测距模块型号",width = 10,orderNum = "26")
    private String rangingModularModel;

    @Excel(name = "测距模块版本号",width = 10,orderNum = "27")
    private String rangingModularVersion;

    @Excel(name = "测距模块厂家",width = 10,orderNum = "28")
    private String rangingModularManufacturer;

    @Excel(name = "测距模块ID",width = 10,orderNum = "29")
    private String rangingModularId;

    @Excel(name = "添加时间",width = 10,orderNum = "30")
    private String createDate;

    private String updateDate;

    //景区ID
    private Long scenicSpotId;

    //景区名称
    @Excel(name = "景区名称",width = 10,orderNum = "1")
    private String scenicSpotName;

    //机器人编号
    @Excel(name = "机器人编号",width = 10,orderNum = "0")
    private String robotCode;

    //机器人ICCID
    @Excel(name = "机器人ICCID",width = 10,orderNum = "4")
    private String robotCodeSim;

    //PAD版本号
    @Excel(name = "PAD版本号",width = 10,orderNum = "3")
    private String clientVersion;

    private String upgradeModule;

    private String upgradedVersion;

}