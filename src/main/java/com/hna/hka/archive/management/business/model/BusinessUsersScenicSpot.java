package com.hna.hka.archive.management.business.model;

import lombok.Data;

@Data
public class BusinessUsersScenicSpot {
    private Long id;

    private Long userId;

    private Long scenicSpotId;

    private Long contractId;

    private String dividendRatio;

    private String realType;

    private String switchType;

    private String totalNum;

    private String cooperationType;

    private String contractStartTime;

    private String contractEndTime;

    private String createTime;

    private String updateTime;

    /**
     * 用户名称
     */
    private String userName;

    /**
     * 景区名称
     */
    private String scenicSpotName;

    /**
     * 景区收入总金额
     */
    private String totalAmount;

    /**
     * 景区昨天收入金额
     */
    private String amount;

    /**
     * 入账时间
     */
    private String createDate;


    private Long[] scenicSpotIds;

}