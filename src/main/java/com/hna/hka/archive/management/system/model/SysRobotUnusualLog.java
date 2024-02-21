package com.hna.hka.archive.management.system.model;

import lombok.Data;

/**
 * @Author zhang
 * @Date 2023/3/3 10:00
 * 机器人异常状态监控日志
 */
@Data
public class SysRobotUnusualLog {

    private Long id;

    private String robotCode;

    private Long  scenicSpotId;

    private String unusualType;

    private Long  appProcessorId;

    private Long  backstageProcessorId;

    private String  status;

    private String unusualTime;

    private String createDate;

    private String updateDate;

    private String propellingContent;

    //管理者app人员
    private String appProcessorName;
    //后台操作人员
    private String backstageProcessorName;
    //景区名称
    private String scenicSpotName;

    private String startTime;

    private String endTime;
}
