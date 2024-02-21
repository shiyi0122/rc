package com.hna.hka.archive.management.business.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class BusinessUsers {
    private Long id;

    private Long imageId;

    private Long licenseAttachId;

    private String unionId;

    private Integer provinceId;

    private Integer cityId;

    private Integer regionId;

    private String phone;

    private String password;

    private String userName;

    private String companyName;

    private String identityCard;

    private Integer integral;

    private String wechat;

    private BigDecimal accountBalance;

    private String wxSalt;

    private String salt;

    private String address;

    private String userType;

    private String userState;

    private String scenicType;

    private String partnerType;

    private String createTime;

    private String updateTime;

    private String  examineType;

    private String  depositCheckType;

    private String contractStartTime;

    private String contractEndTime;

    //是否过期
    private String timeType;
}