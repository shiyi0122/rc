package com.hna.hka.archive.management.Merchant.model;

import cn.afterturn.easypoi.excel.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SysShopWriteOffLog {
    private Long writeOffId;

    @ApiModelProperty("关联商品Id")
    private Long exchangeId;

    @ApiModelProperty("商品名称")
    @Excel(name = "商品名称",width = 20,orderNum = "1")
    private String exchangeName;

    @Excel(name = "商品类型",width = 20,orderNum = "2")
    private String commodityType;

    @ApiModelProperty("景区Id")
    private Long scenicSpotId;

    @ApiModelProperty("景区名称")
    @Excel(name = "景区名称",width = 20,orderNum = "3")
    private String scenicSpotName;

    @ApiModelProperty("商店ID")
    private Long shopId;

    @ApiModelProperty("商店名称")
    @Excel(name = "商店名称",width = 20,orderNum = "4")
    private String shopName;

    @ApiModelProperty("核销日期")
    @Excel(name = "核销日期",width = 20,orderNum = "5")
    private String writeOffDate;

    @ApiModelProperty("核销用户ID")
    private Long writeOffUserId;

    @ApiModelProperty("核销用户手机号")
    @Excel(name = "核销用户手机号",width = 20,orderNum = "6")
    private String writeOffUserPhone;

    @ApiModelProperty("创建时间")
    @Excel(name = "创建时间",width = 20,orderNum = "7")
    private String createDate;

    @ApiModelProperty("更新时间")
    @Excel(name = "更新时间",width = 20,orderNum = "8")
    private String updateDate;




}