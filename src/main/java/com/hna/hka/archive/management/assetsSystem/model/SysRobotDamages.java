package com.hna.hka.archive.management.assetsSystem.model;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

@Data
public class SysRobotDamages {
    private Long damagesId;

    @Excel(name = "机器人编号" ,width = 20,orderNum = "0")
    private String robotCode;

    @Excel(name = "用户手机号" ,width = 20,orderNum = "1")
    private String phone;

    @Excel(name = "订单编号" ,width = 20,orderNum = "2")
    private String orderNumber;

    private String damagesType;

    @Excel(name = "支付状态" ,width = 20,orderNum = "3")
    private String damagesTypeName;

    @Excel(name = "设备使用订单" ,width = 20, orderNum = "4" )
    private String robotOrderNumber;

    private String damagesPhoto;

    private String affectUseType;

    private String administratorsCollection;

    private String scenicSpotId;

    @Excel(name = "定损人" ,width = 20, orderNum = "6" )
    private String lossRater;

    @Excel(name = "定损金额" ,width = 20, orderNum = "7" )
    private String fixedLossAmount;

    @Excel(name = "定损时间" ,width = 20, orderNum = "8" )
    private String lossTime;

    @Excel(name = "故障名称" ,width = 20, orderNum = "9" )
    private String errorRecordsName;

    @Excel(name = "故障描述" ,width = 20, orderNum = "10" )
    private String errorRecordsDescription;

    private String paymentPlatform;

    @Excel(name = "支付平台" ,width = 20, orderNum = "11" )
    private String paymentPlatformName;

    @Excel(name = "实际支付金额" ,width = 20, orderNum = "12" )
    private String actualAmountPaid;

    @Excel(name = "实际收入金额" ,width = 20, orderNum = "13" )
    private String actualIncomeAmount;

    private String createDate;

    private String updateDate;

    @Excel(name = "景区名称" ,width = 20, orderNum = "5" )
    private String scenicSpotName;

    private String startTime;

    private String endTime;
}