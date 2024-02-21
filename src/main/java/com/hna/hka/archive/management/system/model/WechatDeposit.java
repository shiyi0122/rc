package com.hna.hka.archive.management.system.model;

import lombok.Data;

@Data
public class WechatDeposit extends WechatDepositKey {
	private Long depositUserId;

    private String depositMoney;

    private Long depositScenicSpotId;

    private String depositState;

    private String tradeNo;

    private String spbillCreateIp;

    private String returnResultCode;

    private String requestNonceStr;

    private String returnNonceStr;

    private String requestSign;

    private String returnSign;

    private String returnPrepayId;

    private String couponFee;

    private String paymentClient;

    private String createDate;

    private String updateDate;

}