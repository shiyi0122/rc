package com.hna.hka.archive.management.Merchant.dao;


import com.hna.hka.archive.management.Merchant.model.SysShop;
import com.hna.hka.archive.management.Merchant.model.SysShopCommodity;
import com.hna.hka.archive.management.system.model.SysCurrentUserExchange;
import com.hna.hka.archive.management.system.model.SysCurrentUserExchangeLog;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface SysShopCommodityMapper {
    int deleteShopCommodity(Long commodityId);

    int addShopCommodity(SysShopCommodity record);

    List<SysShopCommodity> getShopCommodityList(SysShopCommodity sysShopCommodity);

    int updateShopCommodity(SysShopCommodity record);

    SysCurrentUserExchange exchangePrize(Map<String, Object> search);

    int updateByPrimaryKeySelective(SysCurrentUserExchange exchange);

    SysCurrentUserExchange getExchangePrize(SysCurrentUserExchange exchange);

    int addExchangeLog(SysCurrentUserExchangeLog exchangeLog);

    SysShop getSysUserById(@Param("userId") String userId);

    SysShopCommodity getTypeNum(Long shopId);
}