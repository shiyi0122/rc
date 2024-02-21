package com.hna.hka.archive.management.managerApp.model;

import lombok.Data;

@Data
public class SysRobotAdministratorsVersion {
    private Long versionId;

    private String versionUrl;

    private String versionNumber;

    private String versionDescription;

    private Long scenicSpotId;

    private String createDate;
}