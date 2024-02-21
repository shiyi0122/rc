package com.hna.hka.archive.management.wenYuRiverInterface.model;

import lombok.Data;

/**
 * @ProjectName: rc
 * @Package: com.hna.hka.archive.management.wenYuRiverInterface.model
 * @ClassName: WenYuRiverOrder
 * @Author: 郭凯
 * @Description: 温榆河订单实体
 * @Date: 2021/5/17 17:49
 * @Version: 1.0
 */
@Data
public class WenYuRiverOrder {

    private long orderId;

    private String currentUserPhone;

    private String orderRobotCode;

    private String orderScenicSpotName;

    private String orderStartTime;

    private String orderEndTime;

    private String totalTime;

    private String orderAmount;

    private String actualAmount;

    private String dispatchingFee;

    private String orderRefundAmount;

    private String orderStatus;

    private String realIncome;

    private String orderAndDeductible;

    private String subStatus;

    private String paymentMethod;

    private String subMethod;

    private String orderDiscount;

    private String amountAfterDiscount;

    private String deductibleAmount;
}
