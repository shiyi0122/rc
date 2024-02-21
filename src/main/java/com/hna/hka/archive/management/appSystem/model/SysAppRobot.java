package com.hna.hka.archive.management.appSystem.model;

import lombok.Data;

/**
 * @ProjectName: rc
 * @Package: com.hna.hka.archive.management.appSystem.model
 * @ClassName: SysAppRobot
 * @Author: 郭凯
 * @Description: 管理者APP机器人实体
 * @Date: 2021/6/8 13:33
 * @Version: 1.0
 */
@Data
public class SysAppRobot {

    //机器人编号
    private String robotCode;

    //百度坐标
    private String robotGpsBaiDu;

    //84坐标
    private String robotGpsGpgga;

    //腾讯坐标
    private String robotGpsSmallApp;

    //机器人电量
    private String robotPowerState;

    //机器人运行状态
    private String robotRunState;

    //景区ID
    private Long scenicSpotId;

    //景区名称
    private String scenicSpotName;

    //机器人创建时间
    private String createDate;

    //APP端是否接收到推送信息
    private String pushStatus;

    //机器人锁定状态
    private String robotAdminLocking;

    //机器人个推ID
    private String robotCodeCid;

    //机器人SIM卡号
    private String robotCodeSim;

    //机器人故障状态
    private String robotFaultState;

    //机器人ID
    private String robotId;

    //轮询监听状态
    private String robotPollingType;

    //数据是否删除
    private String robotType;

    //pad端软件版本号
    private String clientVersion;

    //机器人备注
    private String robotRemarks;

    private String updateDate;

    private String OnOffStatus;

}
