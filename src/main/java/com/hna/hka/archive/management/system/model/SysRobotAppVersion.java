package com.hna.hka.archive.management.system.model;

import lombok.Data;

@Data
public class SysRobotAppVersion {
    private Long versionId;

    private String versionUrl;

    private String versionNumber;

    private String versionDescription;

    private Long scenicSpotId;

    private String createDate;

    /**
     * 景区名称
     */
    private String scenicSpotName;

}