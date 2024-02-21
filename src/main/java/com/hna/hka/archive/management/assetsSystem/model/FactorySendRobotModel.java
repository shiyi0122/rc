package com.hna.hka.archive.management.assetsSystem.model;

import lombok.Data;

/**
 * @Author zhang
 * @Date 2022/3/31 18:28
 */
@Data
public class FactorySendRobotModel {

    private   Long id;

    private String  applicableModel;

    private String  robotCount;

    private String createDate;

    private Long  robotFactorySendRobotId;


}
