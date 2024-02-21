package com.hna.hka.archive.management.assetsSystem.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @program: rc
 * @description: 流水对账
 * @author: zhaoxianglong
 * @create: 2021-09-10 15:27
 **/
@ApiModel
@Data
public class AccountStatement {

    @ApiModelProperty(name = "orderNumber" , value = "订单编号")
    private String orderNumber;

    @ApiModelProperty(name = "currentUserPhone" , value = "用户手机号")
    private String currentUserPhone;

    @ApiModelProperty(name = "orderRobotCode" , value = "设备编号")
    private String orderRobotCode;

    @ApiModelProperty(name = "orderScenicSpotName" , value = "景区名称")
    private String orderScenicSpotName;

    @ApiModelProperty(name = "orderStartTime" , value = "开始时间")
    private String orderStartTime;

    @ApiModelProperty(name = "orderEndTime" , value = "结束时间")
    private String orderEndTime;

    @ApiModelProperty(name = "totalTime" , value = "使用分钟")
    private Integer totalTime;

    @ApiModelProperty(name = "actualAmount" , value = "实际计费")
    private BigDecimal actualAmount;

    @ApiModelProperty(name = "dispatchingFee" , value = "调度费")
    private BigDecimal dispatchingFee;

    @ApiModelProperty(name = "orderAmount" , value = "实际支付金额")
    private BigDecimal orderAmount;

    @ApiModelProperty(name = "orderRefundAmount" , value = "退款金额")
    private BigDecimal orderRefundAmount;

    @ApiModelProperty(name = "realIncome" , value = "实际收入金额")
    private BigDecimal realIncome;

    @ApiModelProperty(name = "orderStatus" , value = "支付状态")
    private Integer orderStatus;

    @ApiModelProperty(name = "paymentMethod" , value = "支付方式")
    private Integer paymentMethod;
}
