package com.hna.hka.archive.management.assetsSystem.model;


import cn.afterturn.easypoi.excel.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

//导出所需要的
@Data
public class SysScenicSpotTargetAmountExcel {

    @ApiModelProperty("景区目标金额")
    @Excel(name = "景区目标金额" , width = 50 , orderNum = "11")
    private String targetAmount;

    @ApiModelProperty("日期")
    @Excel(name = "年月" , width = 50 , orderNum = "2")
    private String date;

    @ApiModelProperty("运营人员成本")
    @Excel(name = "运营人员成本" , width = 50 , orderNum = "3")
    private String  operateCost;

    @ApiModelProperty("景区营销成本")
    @Excel(name = "景区营销成本" , width = 50 , orderNum = "5")
    private String spotMarketCost;

    @ApiModelProperty("租金")
    @Excel(name = "租金成本" , width = 50 , orderNum = "7")
    private String rent;

    @Excel(name = "维护成本" , width = 50 , orderNum = "9")
    @ApiModelProperty("维护成本")
    private String maintainCost;


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


}