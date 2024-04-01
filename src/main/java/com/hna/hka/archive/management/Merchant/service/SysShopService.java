package com.hna.hka.archive.management.Merchant.service;

import com.github.pagehelper.PageInfo;
import com.hna.hka.archive.management.Merchant.model.SysShop;
import com.hna.hka.archive.management.assetsSystem.model.ScenicSpot;
import com.hna.hka.archive.management.system.model.SysOrder;
import com.hna.hka.archive.management.system.model.SysScenicSpotBinding;

import java.util.List;
import java.util.Map;


public interface SysShopService {
    PageInfo<SysShop> getShopList(SysShop sysShop);

    int addShop(SysShop sysShop);

    int deleteShop(Long shopId);

    int updateShop(SysShop sysShop);

    List<ScenicSpot> getScenicList();

    List<SysScenicSpotBinding> queryScenicSpotList(Map<String, Object> search);

    int updateByOrderNumber(SysOrder sysOrder);
}
