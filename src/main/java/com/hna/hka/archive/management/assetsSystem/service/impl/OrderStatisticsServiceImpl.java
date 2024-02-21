package com.hna.hka.archive.management.assetsSystem.service.impl;

import com.hna.hka.archive.management.assetsSystem.dao.OrderStatisticsMapper;
import com.hna.hka.archive.management.assetsSystem.service.OrderStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: rc
 * @description:
 * @author: zhaoxianglong
 * @create: 2021-09-23 09:03
 **/
@Service
public class OrderStatisticsServiceImpl implements OrderStatisticsService {

    @Autowired
    OrderStatisticsMapper mapper;

    @Override
    public List findData(Long companyId, Long spotId, String beginTime, String endTime) {
        return mapper.findData(companyId , spotId , beginTime , endTime);
    }
}
