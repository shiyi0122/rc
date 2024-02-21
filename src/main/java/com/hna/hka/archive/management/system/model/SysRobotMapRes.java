package com.hna.hka.archive.management.system.model;

import lombok.Data;

@Data
public class SysRobotMapRes {
    private Long resId;

    private Long resScenicSpotId;

    private String resUrl;

    private String resType;

    private String resSize;

    private String resVersion;

    private String createDate;

    private String updateDate;

    /**
     * 景区名称
     */
    private String scenicSpotName;

}