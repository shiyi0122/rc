package com.hna.hka.archive.management.system.model;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

@Data
public class SysCurrentUser {
    private Long currentUserId;

    @Excel(name = "客户手机号" , orderNum = "0")
    private String currentUserPhone;

    @Excel(name = "客户OPENID" , orderNum = "2",width = 20)
    private String currentOpenId;

    private String currentSessionKey;

    private String currentThdSession;

    private String depositPayState;

    @Excel(name = "押金金额" ,orderNum = "7")
    private String depositPayAmount;

    private String creditArrearsState;

    private String gpsCoordinates;

    @Excel(name = "缴纳押金时间",orderNum = "8")
    private String depositPayTime;

    @Excel(name = "退还押金时间",orderNum = "9")
    private String returnDepositPayTime;

    private String scenicSpotId;

    private String smallAppMonitorState;

    private String smallPolling;

    private String continuationStatus;
    
    private String payUserId;

    private String paymentChannels;

    @Excel(name = "注册时间",orderNum = "6")
    private String createDate;

    private String updateDate;

    /**
     * 景区名称
     */
    @Excel(name = "景区名称" , orderNum = "1")
    private String scenicSpotName;

    /**
     * 押金缴纳状态名称
     */
    @Excel(name = "押金缴纳状态" , orderNum = "3")
    private String depositPayStateName;

    /**
     * 黑名单状态
     */
    private String blackListType;

    @Excel(name = "黑名单状态",orderNum = "5")
    private String blackListTypeName;

    /**
     * 信用欠费状态
     */
    @Excel(name = "信用欠费状态",orderNum = "4")
    private String creditArrearsStateName;

    @Excel(name = "加入黑名单时间",orderNum = "10")
    private String blackListTypeDate;

    /**
     * 押金订单编号
     */
    private String outTradeNo;
}