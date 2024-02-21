package com.hna.hka.archive.management.assetsSystem.model;


import cn.afterturn.easypoi.excel.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SysScenicSpotTargetAmount {

    @ApiModelProperty("目标金额主键ID")
    private Long targetAmountId;
    @ApiModelProperty("景区ID")
    private Long scenicSpotId;
    @ApiModelProperty("景区目标金额(元)")
    @Excel(name = "景区目标金额(元)" , width = 50 , orderNum = "11")
    private String targetAmount;
    @ApiModelProperty("机器人目标金额(元)")
    private String robotTargetAmount;
    @ApiModelProperty("目标金额类型 1日目标 2月目标 3年目标")
    private String targetState;
    @ApiModelProperty("创建时间")
    private String createDate;
    @ApiModelProperty("修改时间")
    private String updateDate;
    @ApiModelProperty("日期")
    @Excel(name = "年月" , width = 50 , orderNum = "2")
    private String date;

    @ApiModelProperty("机器人折旧成本(元)")
    @Excel(name = "机器人折旧成本(元)" , width = 50 , orderNum = "9")
    private String robotCost;

    @ApiModelProperty("运营人员成本(元)")
    @Excel(name = "运营人员成本(元)" , width = 50 , orderNum = "3")
    private String  operateCost;


    @ApiModelProperty("景区营销成本(元)")
    @Excel(name = "景区营销成本(元)" , width = 50 , orderNum = "5")
    private String spotMarketCost;

    @ApiModelProperty("租金(元)")
    @Excel(name = "租金成本(元)" , width = 50 , orderNum = "7")
    private String rent;
    @Excel(name = "维护成本(元)" , width = 50 , orderNum = "9")
    @ApiModelProperty("维护成本(元)")
    private String maintainCost;
//    @Excel(name = "合作公司id" , width = 50 , orderNum = "9")
    @ApiModelProperty("合作公司id")
    private String  companyId;

    @ApiModelProperty("机器人出厂成本")
    private String  robotExFactory;


    @ApiModelProperty("机器人成本详情")
    private SysScenicSpotTargetAmountAscription[] robotCostList;
    @ApiModelProperty("运营人员成本详情")
    private SysScenicSpotTargetAmountAscription[] operateCostList;
    @ApiModelProperty("景区营销成本详情")
    private SysScenicSpotTargetAmountAscription[] stopMarketCostList;
    @ApiModelProperty("租金成本详情")
    private SysScenicSpotTargetAmountAscription[] rentList;
    @ApiModelProperty("维护成本详情")
    private SysScenicSpotTargetAmountAscription[] maintainCostList;
    @ApiModelProperty("机器人出厂成本")
    private SysScenicSpotTargetAmountAscription[] robotExFactoryList;


    @Excel(name = "景区名称" , width = 50 , orderNum = "1")
    private String scenicSpotName;
    @Excel(name = "运营人员成本承担方" , width = 50 , orderNum = "4")
    private String undertakerName;

    @Excel(name = "景区营销成本承担方" , width = 50 , orderNum = "6")
    private String spotMarketName;

    @Excel(name = "景区租金成本承担方" , width = 50 , orderNum = "8")
    private String rentName;

    @Excel(name = "景区维养成本承担方" , width = 50 , orderNum = "10")
    private String maintainCostName;
    @Excel(name = "机器人折旧承担方" , width = 50 , orderNum = "11")
    private String robotCostName;
    @Excel(name = "合作公司名称" , width = 50 , orderNum = "9")
    private String companyName;

}