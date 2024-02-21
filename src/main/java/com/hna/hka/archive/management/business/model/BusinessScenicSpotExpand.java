package com.hna.hka.archive.management.business.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class BusinessScenicSpotExpand {
    private Long id;

    private Long scenicSpotId;

    private Long imageId;

    private String scenicSpotArea;

    private BigDecimal revenueYear;

    private Integer rewardRate;

    private String realData;

    private String createTime;

    private String updateTime;

    private String scenicSpotIntroduce;

    /**
     * 景区名称
     */
    private String scenicSpotName;
    
    /**
     * 省份ID
     */
    private String provinceId;
    
    /**
     * 城市ID
     */
    private String cityId;
    
    /**
     * 区/县ID
     */
    private String regionId;
}