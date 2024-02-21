package com.hna.hka.archive.management.assetsSystem.dao;

import com.hna.hka.archive.management.assetsSystem.model.GoodsStock;

import java.util.HashMap;
import java.util.List;

/**
 * @program: rc
 * @description:
 * @author: zhaoxianglong
 * @create: 2021-10-20 15:35
 **/
public interface GoodsStockMapper {
    List<GoodsStock> list(Long managementId, Long spotId, Integer isDanger);

    Integer add(GoodsStock stock);

    Integer edit(GoodsStock stock);

    Integer delete(Long id);

    List<HashMap> getSpot();

    List<HashMap> getGoods();

    GoodsStock getByGoods(Long managementId , Long spotId);


    GoodsStock getPartIdAndNumberByStock(Long partsManagementId, Double quantity);


    List<GoodsStock> getPartIdAndNumberByStockList(Long partsManagementId, Double quantity);


    GoodsStock listById(String warehouseId);
}
