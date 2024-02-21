package com.hna.hka.archive.management.system.model;

import lombok.Data;

/**
 * @Author zhang
 * @Date 2023/3/2 15:00
 * 机器人异常状态时间配置
 */

@Data
public class SysRobotUnusualTime {

    private Long id;

    private Long sysScenicSpotId;

    private String orderAbnormalTime;

    private String forbiddenTime;

    private String temporaryLacking;

    private String receivingOrders;

    private String padAppUsee;

    private String switchOnOff;

    private String  chargeStatus;

    private String  saturationLow;

    private String  saturationHigh;

    private String  saturationTime;

    private String createDate;

    private String updateDate;

    private String scenicSpotName;

    private String orderState;

    private String batteryReminder;

    private String onOffStatus;

}
