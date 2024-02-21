package com.hna.hka.archive.management.assetsSystem.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @program: rc
 * @description: 配件进销存单据
 * @author: zhaoxianglong
 * @create: 2021-10-21 15:33
 **/
@Data
@ApiModel("配件进销存单据")
public class Inventory {

    @ApiModelProperty("单号")
    private String orderNumber;

    @ApiModelProperty("发货单位")
    private Long spotId;

    @ApiModelProperty("发货单位")
    private String spotName;

    @ApiModelProperty("收货单位")
    private Long receivingId;

    @ApiModelProperty("收货单位")
    private String receivingName;

    @ApiModelProperty("电话")
    private String phone;

    @ApiModelProperty("订单时间")
    private String orderTime;

    @ApiModelProperty("送货人")
    private String deliveryMan;

    @ApiModelProperty("收货人")
    private String reveiver;

    @ApiModelProperty("类型：1、入库；2、出库")
    private Long type;

    @ApiModelProperty("收货地址")
    private String receivingAddress;

    @ApiModelProperty("收货电话")
    private String  receivingPhone;

    @ApiModelProperty("出入库类型，出库：1生产、2销售、3售后 ；入库：1采购/2回收")
    private String inStockReason;
    @ApiModelProperty("快递单号")
    private String  courierNumber;
    @ApiModelProperty("快递费")
    private String   expressFee;
    @ApiModelProperty("保质期内")
    private String   warrantyPeriod;
    @ApiModelProperty("是否运营不当0，不是，1是")
    private String  isAnError;
    @ApiModelProperty("签收状态0未签收，1，签收")
    private String  signInState;
    @ApiModelProperty("故障单号")
    private Long errorRecordsNo;

    @ApiModelProperty("出库原因类型 0配件申请 1故障申请")
    private String outType;

    @ApiModelProperty("配件申请Id")
    private String accessoriesApplicationId;

    @ApiModelProperty("详情")
    private InventoryDetail[] details;
}
