package com.hna.hka.archive.management.system.service.impl;

import com.hna.hka.archive.management.system.dao.SysScenicSpotCapPriceMapper;
import com.hna.hka.archive.management.system.model.SysScenicSpotCapPrice;
import com.hna.hka.archive.management.system.service.SysScenicSpotCapPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author zhang
 * @Date 2022/6/29 17:35
 */
@Service
public class SysScenicSpotCapPriceServiceImpl implements SysScenicSpotCapPriceService {

   @Autowired
    SysScenicSpotCapPriceMapper sysScenicSpotCapPriceMapper;

    @Override
    public SysScenicSpotCapPrice queryScenicSpotCapPrice(Long orderScenicSpotId) {

        SysScenicSpotCapPrice sysScenicSpotCapPrice = sysScenicSpotCapPriceMapper.selectCapPriceByScenicSpotId(orderScenicSpotId);

        return sysScenicSpotCapPrice;


    }
}
