package com.hna.hka.archive.management.system.model;

import lombok.Data;

@Data
public class SysRobotEmbeddedVersion {
	private Long embeddedId;

    private String embeddedVersion;

    private String embeddedDescribe;

    private String embeddedUrl;

    private Long scenicSpotId;

    private String createDate;

    private String updateDate;

    private String pushStatus;

    private String robotVersion;

    private String fileSize;

    private String embeddedType;

    private String softwareVersion;

}