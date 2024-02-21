package com.hna.hka.archive.management.system.model;

import lombok.Data;

/**
 * @Author zhang
 * @Date 2022/2/11 15:21
 */

@Data
public class SysScenicSpotRobotOperate {

    private Long operateId;

    private String robotCode;

    private Long scenicSpotId;

    private String startDate;

    private String endDate;

    private String createDate;

    private String updateDate;

    private String scenicSpotName;

}
