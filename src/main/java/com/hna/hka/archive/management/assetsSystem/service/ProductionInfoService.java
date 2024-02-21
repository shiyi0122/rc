package com.hna.hka.archive.management.assetsSystem.service;

import com.github.pagehelper.PageInfo;
import com.hna.hka.archive.management.assetsSystem.model.ProductionInfo;
import com.hna.hka.archive.management.system.model.SysScenicSpotBinding;

/**
 * @program: rc
 * @description:
 * @author: zhaoxianglong
 * @create: 2021-09-17 13:36
 **/
public interface ProductionInfoService {
    PageInfo<ProductionInfo> list(String name, String factory, Integer pageNum, Integer pageSize) throws Exception;

    Integer add(ProductionInfo info);

    Integer edit(ProductionInfo info);

    Integer delete(Long id);

    PageInfo<SysScenicSpotBinding> spotFactoryList();


}
