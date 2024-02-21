package com.hna.hka.archive.management.assetsSystem.dao;

import com.hna.hka.archive.management.assetsSystem.model.FactorySendRobotModel;
import com.hna.hka.archive.management.assetsSystem.model.ProductionInfoRobotModel;

import java.util.List;

/**
 * @Author zhang
 * @Date 2022/3/31 18:26
 * 工厂发货，质检标准 ，机器人型号数量类
 */
public interface FactorySendRobotModelMapper {

    List<FactorySendRobotModel> list(Long  id);

    Integer add(FactorySendRobotModel info);

    Integer edit(FactorySendRobotModel info);

    Integer delete(Long id);

    Integer deleteFactorySendRobotModelId(Long id);

}
