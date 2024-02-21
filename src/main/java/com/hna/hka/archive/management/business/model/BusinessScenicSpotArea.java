package com.hna.hka.archive.management.business.model;

import lombok.Data;

@Data
public class BusinessScenicSpotArea {
    private Long id;

    private Long scenicSpotId;

    private Integer provinceId;

    private Integer cityId;

    private Integer regionId;

    private String mergerName;

    private String createTime;

    private String updateTime;
}