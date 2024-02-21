package com.hna.hka.archive.management.system.model;

import lombok.Data;

@Data
public class SysCurrentUserCoupons {
    private Long userCouponsId;

    private Long activityId;

    private Long userId;

    private String userCouponsName;

    private Long couponsScenicSpotId;

    private String couponsScenicSpotName;

    private String couponsStandard;

    private String couponsAmount;

    private String couponsStartTime;

    private String couponsEndTime;

    private String couponsType;

    private String type;

    private String couponsFailure;

    private String createDate;

    private String updateDate;
    
    /**
     * 用户手机号
     */
    private String phone;
    
    /**
     * 有效期字段
     */
    private String termOfValidity;

}