package com.hna.hka.archive.management.appSystem.model;

import lombok.Data;

/**
 * @Author zhang
 * @Date 2023/5/25 18:06
 */
@Data
public class AppRobotGpsDispatchLog {

    private Long gpsId;

    private String robotCode;

    private Long scenicSpotId;

    private String scenicSpotName;

    private String exceedingTime;

    private String reason;

    private String createDate;

    private String updateDate;
}
