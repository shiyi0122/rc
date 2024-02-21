package com.hna.hka.archive.management.assetsSystem.service;

import com.hna.hka.archive.management.assetsSystem.model.AccountClose;

import java.util.List;

/**
 * @program: rc
 * @description:
 * @author: zhaoxianglong
 * @create: 2021-09-13 14:15
 **/
public interface AccountCloseService {
    List<AccountClose> list(String companyId, String spotId, String startDate, String endDate, Integer type, Integer pageNum, Integer pageSize);

    Integer getCount(String companyId, String spotId, String startDate, String endDate, Integer type);

    Integer update(AccountClose accountClose);

    /**
     * 定时统计结算流水
     */
    void spotAccountCloseTimedStatistics();

    /**
     * 定时统计结算流水
     */
    void spotAccountCloseTimedStatisticsNew();
}
