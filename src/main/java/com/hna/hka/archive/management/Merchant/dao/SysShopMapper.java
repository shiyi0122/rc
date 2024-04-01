package com.hna.hka.archive.management.Merchant.dao;


import com.hna.hka.archive.management.Merchant.model.SysShop;
import com.hna.hka.archive.management.assetsSystem.model.ScenicSpot;
import com.hna.hka.archive.management.system.model.SysOrder;
import com.hna.hka.archive.management.system.model.SysScenicSpotBinding;

import java.util.List;
import java.util.Map;

public interface SysShopMapper {

    int deleteShop(Long shopId);

    int addShop(SysShop record);

    List<SysShop> getShopList(SysShop sysShop);

    int updateShop(SysShop record);

    List<ScenicSpot> getScenicList();

    List<SysScenicSpotBinding> queryScenicSpotList(Map<String, Object> search);

    int updateByOrderNumber(SysOrder sysOrder);
}