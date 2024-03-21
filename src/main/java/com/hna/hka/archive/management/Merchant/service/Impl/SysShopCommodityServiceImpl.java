package com.hna.hka.archive.management.Merchant.service.Impl;

import com.github.pagehelper.PageInfo;
import com.hna.hka.archive.management.Merchant.dao.SysShopCommodityMapper;
import com.hna.hka.archive.management.Merchant.dao.SysShopWriteOffLogMapper;
import com.hna.hka.archive.management.Merchant.model.SysShop;
import com.hna.hka.archive.management.Merchant.model.SysShopCommodity;
import com.hna.hka.archive.management.Merchant.service.SysShopCommodityService;
import com.hna.hka.archive.management.system.model.SysCurrentUserExchange;
import com.hna.hka.archive.management.system.util.DateUtil;
import com.hna.hka.archive.management.system.util.IdUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service("sysShopCommodityService")
@Transactional
public class SysShopCommodityServiceImpl implements SysShopCommodityService {

    @Autowired
    private SysShopCommodityMapper sysShopCommodityMapper;

    @Autowired
    private SysShopWriteOffLogMapper sysShopWriteOffLogMapper;
    @Override
    public PageInfo<SysShopCommodity> getShopCommodityList(SysShopCommodity sysShopCommodity) {
        List<SysShopCommodity> shopCommodityList = sysShopCommodityMapper.getShopCommodityList(sysShopCommodity);
        return new PageInfo<>(shopCommodityList);
    }

    @Override
    public int addShopCommodity(SysShopCommodity sysShopCommodity) {
        SysShopCommodity typeNum = sysShopCommodityMapper.getTypeNum(sysShopCommodity.getShopId());
        if (typeNum.getCommodityType() == null ||("").equals(typeNum.getCommodityType())) {
            sysShopCommodity.setCommodityType("0");
        } else {
            String commodityType = typeNum.getCommodityType();
            Integer commodityType1 = Integer.parseInt(commodityType) + 1;
            sysShopCommodity.setCommodityType(String.valueOf(commodityType1));
        }
        sysShopCommodity.setCommodityId(IdUtils.getSeqId());
        sysShopCommodity.setCreateDate(DateUtil.currentDateTime());
        sysShopCommodity.setUpdateDate(DateUtil.currentDateTime());
        return sysShopCommodityMapper.addShopCommodity(sysShopCommodity);
    }

    @Override
    public int deleteShopCommodity(Long commodityId) {
        return sysShopCommodityMapper.deleteShopCommodity(commodityId);
    }

    @Override
    public int updateShopCommodity(SysShopCommodity sysShopCommodity) {
        sysShopCommodity.setUpdateDate(DateUtil.currentDateTime());
        return sysShopCommodityMapper.updateShopCommodity(sysShopCommodity);
    }

    @Override
    public SysCurrentUserExchange exchangePrize(Map<String, Object> search) {
        return sysShopCommodityMapper.exchangePrize(search);
    }

    @Override
    public SysShop getSysUserId(String userId) {
        return sysShopCommodityMapper.getSysUserById(userId);
    }

}
