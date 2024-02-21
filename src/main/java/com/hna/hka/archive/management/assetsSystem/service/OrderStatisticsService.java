package com.hna.hka.archive.management.assetsSystem.service;

import java.util.List;

/**
 * @program: rc
 * @description:
 * @author: zhaoxianglong
 * @create: 2021-09-23 09:03
 **/
public interface OrderStatisticsService {
    List findData(Long companyId, Long spotId, String beginTime, String endTime);
}
