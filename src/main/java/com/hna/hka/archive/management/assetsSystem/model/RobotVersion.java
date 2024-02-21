package com.hna.hka.archive.management.assetsSystem.model;

import lombok.Data;

/**
 * @ClassName：robotVersion
 * @Author: gouteng
 * @Date: 2022-11-22 9:54
 * @Description: 为了返回的数据好看一些,新建实体类只返回两个字段
 */
@Data
public class RobotVersion {
//    机器人版本
    private String clientVersion;
//    机器人版本数量
    private  Integer versionNum;
}
