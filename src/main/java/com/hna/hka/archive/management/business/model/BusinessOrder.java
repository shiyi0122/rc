package com.hna.hka.archive.management.business.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class BusinessOrder {
    private Long id;

    private Long userId;

    private String orderNumber;

    private BigDecimal orderAmount;

    private String payType;

    private String orderStatus;

    private String invoiceStatus;

    private String orderType;

    private String createTime;

    private String updateTime;

    /**
     * 用户名称
     */
    private String userName;

    /**
     * 用户手机号
     */
    private String phone;

    /**
     * 真实虚拟状态
     */
    private String realType;

    /**
     * 景区名称
     */
    private String scenicSpotName;
}