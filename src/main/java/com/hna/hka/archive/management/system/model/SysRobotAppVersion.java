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

    //0无PAD无更新版本 1有PAD自动更新版本（后台根据此状态来推送给PAD通知更新） 2 景区手动升级
    private String autoUpdateMonitor;

}