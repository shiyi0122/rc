package com.hna.hka.archive.management.system.model;

import lombok.Data;

@Data
public class SysRobotAdvertising {
    private Long advertisingId;

    private String advertisingScenicSpotId;

    private String advertisingUrl;

    private String advertisingValid;

    private String advertisingName;

    private String advertisingOrder;

    private String createTime;

    private String updateTime;

    /**
     * 景区名称
     */
    private String scenicSpotName;

}