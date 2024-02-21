package com.hna.hka.archive.management.appSystem.model;

import lombok.Data;

/**
 * @ProjectName: rc
 * @Package: com.hna.hka.archive.management.appSystem.model
 * @ClassName: SysAppOrder
 * @Author: 郭凯
 * @Description: APP订单管理实体类
 * @Date: 2021/6/8 14:31
 * @Version: 1.0
 */
@Data
public class SysAppOrder{

    //是否提交
    private String subStatus;

    private String subStatusName;

    //订单状态
    private String orderStatus;

    private String orderStatusName;

    //机器人编号
    private String orderRobotCode;

    //景区名称
    private String scenicSpotName;

    //用户手机号
    private String currentUserPhone;

    //订单开始时间
    private String orderStartTime;

    //订单结束时间
    private String orderEndTime;

    //调度费
    private String dispatchingFee;

    //支付方式
    private String paymentMethod;

    private String paymentMethodName;

    private String totalTime;

    //订单金额
    private String realIncome;

    private String actualAmount;

    private String orderAmount;

    private String orderId;

    private String orderNumber;

    private String orderRefundAmount;

    //是否退过调度费用
    private  String isDispatchingFee;

}
