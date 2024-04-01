package com.hna.hka.archive.management.Merchant.service.Impl;

import com.github.pagehelper.PageInfo;
import com.hna.hka.archive.management.Merchant.dao.SysShopMapper;
import com.hna.hka.archive.management.Merchant.model.SysShop;
import com.hna.hka.archive.management.Merchant.service.SysShopService;
import com.hna.hka.archive.management.assetsSystem.model.ScenicSpot;
import com.hna.hka.archive.management.system.model.SysOrder;
import com.hna.hka.archive.management.system.model.SysScenicSpotBinding;
import com.hna.hka.archive.management.system.util.DateUtil;
import com.hna.hka.archive.management.system.util.IdUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service("sysShopService")
@Transactional
public class SysShopServiceImpl implements SysShopService {

    @Autowired
    private SysShopMapper sysShopMapper;
//^[-+]?([1-8]?\d(\.\d+)?|90(\.0+)?),\s*[-+]?(180(\.0+)?|((1[0-7]\d)|([1-9]?\d))(\.\d+)?)$

    @Override
    public PageInfo<SysShop> getShopList(SysShop sysShop) {
        List<SysShop> shopList = sysShopMapper.getShopList(sysShop);
        return new PageInfo<>(shopList);
    }

    @Override
    public int addShop(SysShop sysShop) {

        sysShop.setShopId(IdUtils.getSeqId());
        sysShop.setCreateDate(DateUtil.currentDateTime());
        sysShop.setUpdateDate(DateUtil.currentDateTime());
        return sysShopMapper.addShop(sysShop);
    }

    @Override
    public int deleteShop(Long shopId) {
        return sysShopMapper.deleteShop(shopId);
    }

    @Override
    public int updateShop(SysShop sysShop) {
        sysShop.setUpdateDate(DateUtil.currentDateTime());
        return sysShopMapper.updateShop(sysShop);
    }

    @Override
    public List<ScenicSpot> getScenicList() {
        return sysShopMapper.getScenicList();
    }

    @Override
    public List<SysScenicSpotBinding> queryScenicSpotList(Map<String, Object> search) {
        return sysShopMapper.queryScenicSpotList(search);
    }

    @Override
    public int updateByOrderNumber(SysOrder sysOrder) {
        return sysShopMapper.updateByOrderNumber(sysOrder);
    }
}
