package com.hna.hka.archive.management.system.model;

import lombok.Data;

@Data
public class SysScenicSpotBinding {
    private Long scenicSpotFid;

    private String scenicSpotFname;

    private Long scenicSpotPid;

    private Long scenicSpotSid;

    private Long scenicSpotQid;

    private Integer scenicSpotType;

    private String cityPic;

    private String cityLabel;

    private String scenicSpotSname;

    private String scenicSpotQname;

    private String scenicSpotPname;

    //饱和度
    private String recordReceptionDesk;
    //饱和度范围(低)
    private String saturationLow;
    //饱和度范围(高)
    private String saturationHigh;

    private String gpsUpdateDate;



}