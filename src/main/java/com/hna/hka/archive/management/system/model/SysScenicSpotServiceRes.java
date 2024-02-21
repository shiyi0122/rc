package com.hna.hka.archive.management.system.model;

import lombok.Data;

@Data
public class SysScenicSpotServiceRes {
    private Long serviceId;

    private String serviceName;

    private String serviceNamePinYin;

    private String serviceType;

    private Long scenicSpotId;

    private String serviceGps;

    private String serviceGpsBaiDu;

    private String serviceIntroduce;

    private String servicePic;

    private String createDate;

    private String updateDate;

    /**
     * 景区名称
     */
    private String scenicSpotName;

}