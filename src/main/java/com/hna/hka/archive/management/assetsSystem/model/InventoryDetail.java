package com.hna.hka.archive.management.assetsSystem.model;

import cn.afterturn.easypoi.excel.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("配件进销存")
public class InventoryDetail {

    @ApiModelProperty("主键")
    private Long id;
    @Excel(name = "订单时间" , width = 15 , orderNum = "0")
    @ApiModelProperty("订单时间")
    private String orderTime;
    @Excel(name = "订单编号" , width = 15 , orderNum = "1")
    @ApiModelProperty("订单编号")
    private String orderNumber;
    @Excel(name = "配件编码" , width = 15 , orderNum = "2")
    @ApiModelProperty("配件编码")
    private String goodsCode;
    @Excel(name = "商品数量" , width = 15 , orderNum = "6")
    @ApiModelProperty("商品数量")
    private Long goodsAmount;
    @Excel(name = "单价(元)" , width = 15 , orderNum = "7")
    @ApiModelProperty("单价(元)")
    private Double unitPirce;
    @ApiModelProperty("金额(元)")
    @Excel(name = "金额(元)" , width = 15 , orderNum = "8")
    private Double totalAmount;
    @Excel(name = "备注" , width = 15 , orderNum = "9")
    @ApiModelProperty("备注")
    private String notes;
    @Excel(name = "发货单位" , width = 15 , orderNum = "10")
    @ApiModelProperty("发货单位")
    private String spotName;
    @Excel(name = "电话" , width = 15 , orderNum = "11")
    @ApiModelProperty("电话")
    private String phone;
    @Excel(name = "发货人" , width = 15 , orderNum = "12")
    @ApiModelProperty("发货人")
    private String deliveryMan;
    @Excel(name = "收货人" , width = 15 , orderNum = "14")
    @ApiModelProperty("收货人")
    private String receiver;
    @Excel(name = "商品名称" , width = 15 , orderNum = "3")
    @ApiModelProperty("商品名称")
    private String goodsName;
    @Excel(name = "规格型号" , width = 15 , orderNum = "4")
    @ApiModelProperty("规格型号")
    private String model;
    @Excel(name = "单位" , width = 15 , orderNum = "5")
    @ApiModelProperty("单位")
    private String unit;
    @ApiModelProperty("发货单位")
    private Long spotId;
    @ApiModelProperty("商品id")
    private Long goodsId;
    @ApiModelProperty("创建时间")
    private String createTime;
    @ApiModelProperty("更新时间")
    private String updateTime;
    @ApiModelProperty("类型：1、入库；2、出库")
    private Long type;
    @ApiModelProperty("申请单号")
    private String applyNumber;
    // 出库原因 ：1生产 2配件申请 3故障上报 4损坏上报
    @ApiModelProperty("出入库原因")
    @Excel(name = "出入库原因" , width = 15 , orderNum = "17")
    private String inStockReason ;
    @ApiModelProperty("状态：0、初始状态；1、确认；2、撤销")
    private Long status;
    @ApiModelProperty("收货单位")
    private Long receivingId;
    @Excel(name = "收货单位" , width = 15 , orderNum = "13")
    @ApiModelProperty("收货单位")
    private String receivingName;
    @ApiModelProperty("收货地址")
    private String receivingAddress;
    @ApiModelProperty("收货手机号")
    private String   receivingPhone;
    @ApiModelProperty("出入库类型，出库：1生产、2销售、3售后 ；入库：1采购/2回收")
    @Excel(name = "出入库类型" , width = 15 , orderNum = "17")
    private String  inStockType;
    @ApiModelProperty("快递单号")
    @Excel(name = "快递单号" , width = 15 , orderNum = "15")
    private String  courierNumber;
    @Excel(name = "快递费(元)" , width = 15 , orderNum = "16")
    @ApiModelProperty("快递费(元)")
    private String   expressFee;
    @ApiModelProperty("确认人id")
    private String  confirmedById;
    @ApiModelProperty("保质期内")
    private String warrantyPeriod ;
    @ApiModelProperty("是否运营不当0，不是，1是")
    private String isAnError;
    @ApiModelProperty("签收状态0未签收，1，签收")
    private String  signInState;
    @ApiModelProperty("供货地址类型id")
    private Long spotAddressTypeId;
    @ApiModelProperty("收货地址类型id")
    private Long receivingAddressTypeId;

    @ApiModelProperty("确认人名称")
    @Excel(name = "确认人名称" , width = 15 , orderNum = "14")
    private String  confirmedByName;
    @ApiModelProperty("原始数量,修改时用")
    private Long oldGoodAmount;

    @ApiModelProperty("四级管理使用到的id(发货)")
    private String spotLevelId;

    @ApiModelProperty("四级管理使用到的id(收货)")
    private String receivingLevelId;

    @ApiModelProperty("故障单号")
    private String errorRecordsNo;

}
