package com.hna.hka.archive.management.assetsSystem.model;

import lombok.Data;

import java.util.List;

/**
 * @ClassName：OperationAttendance
 * @Author: gouteng
 * @Date: 2022-11-20 21:05
 * @Description: 必须描述类做什么事情, 实现什么功能
 */
@Data
public class OperationAttendance {
//    景区ID
    private Long scenicSpotId;
//    景区名称
    private String scenicSpotName;
//    打卡 根据startDate时间升序
    private List<NumAndTime> timePeriod;
//    当天机器人开启台数
    private Integer startupStatus;
//    当天机器人关闭台数
    private Integer closedState;
//    机器人版本台数
    private List<RobotVersion> robotVersionAndNum;
//    开始时间
    private String startDate;
//    结束时间
    private String endDate;
}
