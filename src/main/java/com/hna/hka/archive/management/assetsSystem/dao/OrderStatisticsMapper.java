package com.hna.hka.archive.management.assetsSystem.dao;

import java.util.List;

/**
 * @program: rc
 * @description:
 * @author: zhaoxianglong
 * @create: 2021-09-23 09:04
 **/
public interface OrderStatisticsMapper {
    List findData(Long companyId, Long spotId, String beginTime, String endTime);
}
