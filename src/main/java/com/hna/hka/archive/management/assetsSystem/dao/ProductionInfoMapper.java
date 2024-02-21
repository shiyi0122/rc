package com.hna.hka.archive.management.assetsSystem.dao;

import com.hna.hka.archive.management.assetsSystem.model.ProductionInfo;

import java.util.List;

/**
 * @program: rc
 * @description:
 * @author: zhaoxianglong
 * @create: 2021-09-17 13:37
 **/
public interface ProductionInfoMapper {
    List<ProductionInfo> list(String name, String factory);

    Integer add(ProductionInfo info);

    Integer edit(ProductionInfo info);

    Integer delete(Long id);

    List<ProductionInfo> getListAll();


}
