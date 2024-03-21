package com.hna.hka.archive.management.Merchant.service;

import com.github.pagehelper.PageInfo;
import com.hna.hka.archive.management.Merchant.model.SysShop;
import com.hna.hka.archive.management.Merchant.model.SysShopCommodity;
import com.hna.hka.archive.management.system.model.SysCurrentUserExchange;

import java.util.Map;


public interface SysShopCommodityService {

    PageInfo<SysShopCommodity> getShopCommodityList(SysShopCommodity sysShopCommodity);

    int addShopCommodity(SysShopCommodity sysShopCommodity);

    int deleteShopCommodity(Long commodityId);

    int updateShopCommodity(SysShopCommodity sysShopCommodity);

    SysCurrentUserExchange exchangePrize(Map<String, Object> search);

    SysShop getSysUserId(String userId);


}
