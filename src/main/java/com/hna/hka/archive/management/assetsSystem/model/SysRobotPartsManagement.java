package com.hna.hka.archive.management.assetsSystem.model;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

@Data
public class SysRobotPartsManagement {
    private Long partsManagementId;

    private String accessoriesType;

    @Excel(name = "配件类型" , width = 10 , orderNum = "0")
    private String accessoriesTypeName;

//    @Excel(name = "故障名称" , width = 10 , orderNum = "1")
    private String faultName;

    @Excel(name = "配件编码" , width = 10 , orderNum = "1")
    private String accessoriesCode;

    @Excel(name = "配件名称" , width = 10 , orderNum = "2")
    private String accessoryName;

    @Excel(name = "配件型号" , width = 10 , orderNum = "3")
    private String accessoryModel;

    @Excel(name = "单位" , width = 10 , orderNum = "4")
    private String unit;

    @Excel(name = "进货价格(元)" , width = 10 , orderNum = "5")
    private Double accessoryPrice;

    @Excel(name = "出货价格(元)" , width = 10 , orderNum = "6")
    private Double accessoryPriceOut;

    @Excel(name = "质保周期" , width = 10 , orderNum = "7")
    private String warrantyPeriod;

    @Excel(name = "适用机型" , width = 10 , orderNum = "8")
    private String applicableModels;

    @Excel(name = "备注" , width = 10 , orderNum = "9")
    private String remarks;

    private String createTime;

    private String updateTime;

    //配件类型id
    private String accessoriesTypeId;

    //品牌
    @Excel(name = "品牌" , width = 10 , orderNum = "10")
    private String  brand;

    //采购渠道
    @Excel(name = "采购渠道" , width = 10 , orderNum = "11")
    private String procurementChannels;


    private String accessoryNumber;

    private String robotWarrantyDeadline;

    private String spotId;

    private Long amount;

}