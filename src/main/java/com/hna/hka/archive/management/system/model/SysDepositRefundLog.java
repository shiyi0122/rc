package com.hna.hka.archive.management.system.model;

import lombok.Data;

@Data
public class SysDepositRefundLog {
    private Long id;

    private String loginName;

    private String orderNumber;

    private String userPhone;

    private String orderMoney;

    private String depositMoney;

    private String blackListType;

    private String robotCode;

    private String orderStartTime;

    private String orderEndTime;

    private String createDate;

}