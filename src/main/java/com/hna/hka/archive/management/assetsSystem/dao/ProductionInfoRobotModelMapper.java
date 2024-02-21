package com.hna.hka.archive.management.assetsSystem.dao;

import com.hna.hka.archive.management.assetsSystem.model.ProductionInfo;
import com.hna.hka.archive.management.assetsSystem.model.ProductionInfoRobotModel;

import java.util.List;

/**
 * @Author zhang
 * @Date 2022/3/31 15:49
 */
public interface ProductionInfoRobotModelMapper {
    List<ProductionInfoRobotModel> list(Long  id);

    Integer add(ProductionInfoRobotModel info);

    Integer edit(ProductionInfoRobotModel info);

    Integer delete(Long id);

    Integer deleteRobotProductionInfoId(Long id);


}
