package com.hna.hka.archive.management.assetsSystem.service;

import com.hna.hka.archive.management.system.util.PageDataResult;

import java.util.Map;

/**
 * @Author zhang
 * @Date 2022/7/19 13:27
 */
public interface TreasureHuntOrderService {
    PageDataResult getTreasureHuntList(Integer pageNum, Integer pageSize, Map<String, Object> search);


}
