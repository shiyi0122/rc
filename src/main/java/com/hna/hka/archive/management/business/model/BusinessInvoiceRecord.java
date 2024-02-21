package com.hna.hka.archive.management.business.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class BusinessInvoiceRecord {
    private Long id;

    private Long userId;

    private Long invoiceRiseId;

    private Long operatorId;

    private Long addressId;

    private BigDecimal invoiceAmount;

    private String failReasons;

    private String state;

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
     * 发票抬头名称
     */
    private String titleInvoice;

    /**
     * 详细地址
     */
    private String address;
}