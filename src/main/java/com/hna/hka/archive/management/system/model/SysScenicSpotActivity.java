package com.hna.hka.archive.management.system.model;

import lombok.Data;

@Data
public class SysScenicSpotActivity {
    private Long activityId;

    private String activityName;

    private Long activityScenicSpotId;

    private String activityScenicSpotName;

    private String activityStandard;

    private String activityAmount;

    private String numberCoupons;

    private String activityStartTime;

    private String activityEndTime;

    private String activityType;

    private String claimConditions;

    private String activityFailure;

    private String createDate;

    private String updateDate;

    private String activityUseType;
    
    /**
     * 活动有效期
     */
    private String termOfValidity;
}