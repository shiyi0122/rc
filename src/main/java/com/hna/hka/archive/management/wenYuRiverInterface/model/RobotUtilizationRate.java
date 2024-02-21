package com.hna.hka.archive.management.wenYuRiverInterface.model;

import lombok.Data;

/**
 * @ProjectName: rc
 * @Package: com.hna.hka.archive.management.wenYuRiverInterface.model
 * @ClassName: RobotUtilizationRate
 * @Author: 郭凯
 * @Description: 机器人使用率对象
 * @Date: 2021/5/14 15:30
 * @Version: 1.0
 */
@Data
public class RobotUtilizationRate {

    //机器人编号
    private String robotCode;

    //机器人使用人次
    private String numberOfUsers;

    //机器人使用率
    private String utilizationRate;
}
