package com.hna.hka.archive.management.system.model;

import lombok.Data;

@Data
public class SysScenicSpotRecommendedRoute {
    private Long routeId;

    private Long scenicSpotId;

    private String routeName;

    private String routeNamePinYin;

    private String routeIntroduce;

    private String routeGps;

    private String routeGpsBaiDu;

    private String createDate;

    private String updateDate;

    /**
     * 景区名称
     */
    private String scenicSpotName;
}