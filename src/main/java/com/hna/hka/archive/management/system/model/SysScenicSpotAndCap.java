package com.hna.hka.archive.management.system.model;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

import java.io.Serializable;

@Data
public class SysScenicSpotAndCap implements Serializable {


    private Long scenicSpotId;


    @Excel(name = "景区名称",orderNum = "0" ,width = 20)
    private String scenicSpotName;

    @Excel(name = "联系人",orderNum = "1" ,width = 10)
    private String scenicSpotContact;

    @Excel(name = "联系人电话",orderNum = "2" ,width = 10)
    private String scenicSpotPhone;

    @Excel(name = "景区邮箱",orderNum = "3" ,width = 10)
    private String scenicSpotEmail;

    @Excel(name = "景区地址",orderNum = "4" ,width = 20)
    private String scenicSpotAddres;

    @Excel(name = "景区邮编",orderNum = "5" , width = 10)
    private String scenicSpotPostalCode;

    private String scenicSpotRobotTotal;

    private String scenicSpotStatus;

    private String scenicSpotOpenword;

    private String scenicSpotCloseword;

    private String scenicSpotBeyondPrice;

    private String scenicSpotWeekendPrice;

    private String scenicSpotNormalPrice;

    private String scenicSpotWeekendRentPrice;

    private String scenicSpotNormalRentPrice;

    private String scenicSpotWeekendTime;

    private String scenicSpotNormalTime;

    private String scenicSpotRemainingTime;

    private String scenicSpotRentTime;

    private String coordinateRange;

    private String scenicSpotDeposit;

    private String normalCappedPrice;

    private String weekendCappedPrice;

    private String randomBroadcastTime;

    private String robotWakeupWords;

    private String createDate;

    private String updateDate;

    private Long scenicSpotFid;
    
    private String scenicSpotFrequency;

    private String scenicSpotFenceTime;

    private String scenicSpotForbiddenZoneTime;

    private String  workTime;
    private  String closingTime;
    private String  workBroadcast;
    private String  closingBroadcast;
    private String  huntSwitch;
    private String  heat;


    private String hornSwitch;
    private String repeatStatus;
    private String huntQuantity;
    private String lampOpeningTime;
    private String lampClosingTime;
    private String freeTimeSetting;



    @Excel(name = "测试开始时间",orderNum = "8" , width = 10)
    private String testStartTime;

    @Excel(name = "试运营开始时间",orderNum = "9" , width = 10)
    private String trialOperationsTime;

    @Excel(name = "正式运营时间",orderNum = "10" , width = 10)
    private String formalOperationTime;

    @Excel(name = "停止运营时间",orderNum = "11" , width = 10)
    private String stopOperationTime;

    /**
     * 景区状态名称
     */
    @Excel(name = "景区状态",orderNum = "7" , width = 10)
    private String robotWakeupWordsName;

    /**
     * 景区图片地址
     */
    private String scenicSpotUrlName;

    /**
     * 景区区域
     */
    @Excel(name = "景区归属地",orderNum = "6" , width = 10)
    private String scenicSpotRegion;

    private Long companyId;

    private String companyName;

    /**
     * 景区证书
     */
    private Long certificateId;

    /**
     * 归属景区
     */
    private String scenicSpotAscriptionName;

    @Excel(name = "一档工作日封顶时间",orderNum = "1" ,width = 20)
    private String normalCapOneTime;
    @Excel(name = "一档工作日封顶价格",orderNum = "2" ,width = 20)
    private String normalCapOnePrice;
    @Excel(name = "一档工作日封顶单价",orderNum = "3" ,width = 20)
    private String normalCapOneUnitPrice;
    @Excel(name = "二档工作日封顶时间",orderNum = "4" ,width = 20)
    private String normalCapTwoTime;
    @Excel(name = "二档工作日封顶价格",orderNum = "5" ,width = 20)
    private String normalCapTwoPrice;
    @Excel(name = "二档工作日封顶单价",orderNum = "6" ,width = 20)
    private String normalCapTwoUnitPrice;
    @Excel(name = "一档周末封顶时间",orderNum = "7" ,width = 20)
    private String weekendCapOneTime;
    @Excel(name = "一档周末封顶价格",orderNum = "8" ,width = 20)
    private String weekendCapOnePrice;
    @Excel(name = "一档周末封顶单价",orderNum = "9" ,width = 20)
    private String weekendCapOneUnitPrice;
    @Excel(name = "二档周末封顶时间",orderNum = "10" ,width = 20)
    private String weekendCapTwoTime;
    @Excel(name = "二档周末封顶价格",orderNum = "11" ,width = 20)
    private String weekendCapTwoPrice;
    @Excel(name = "二档周末封顶单价",orderNum = "12" ,width = 20)
    private String weekendCapTwoUnitPrice;




}