package com.hna.hka.archive.management.assetsSystem.model;

import lombok.Data;

/**
 * @Author zhang
 * @Date 2022/3/31 15:40
 * 生产批次中机器人型号和数量
 */

@Data
public class ProductionInfoRobotModel {

      Long id;

      String  applicableModel;

      String  robotCount;

      String createDate;

      Long  robotProductionInfoId;



}
