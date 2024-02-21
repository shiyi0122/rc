package com.hna.hka.archive.management.assetsSystem.service;

import com.github.pagehelper.PageInfo;
import com.hna.hka.archive.management.assetsSystem.model.GoodsStock;
import com.hna.hka.archive.management.system.util.PageDataResult;

import java.util.List;

/**
 * @program: rc
 * @description:
 * @author: zhaoxianglong
 * @create: 2021-10-20 15:34
 **/
public interface GoodsStockService {
    PageInfo<GoodsStock> list(Long managementId, Long spotId, Integer isDanger, Integer pageNum, Integer pageSize);

    Integer add(GoodsStock stock);

    Integer edit(GoodsStock stock);

    Integer delete(Long id);

    List getSpot();

    List getGoods();

    int isGoodsIdSpotId(Long spotId, Long managementId);

    List<GoodsStock> detailList(Long managementId, Long spotId, Integer isDanger);


    int importExcelEnter(GoodsStock goodsStock);

    PageDataResult getGoodsStockList(Long spotId, int pageNum, int pageSize);


}
