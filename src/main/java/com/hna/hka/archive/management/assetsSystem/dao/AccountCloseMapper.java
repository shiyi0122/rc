package com.hna.hka.archive.management.assetsSystem.dao;

import com.hna.hka.archive.management.assetsSystem.model.AccountClose;

import java.util.List;

/**
 * @program: rc
 * @description:
 * @author: zhaoxianglong
 * @create: 2021-09-13 14:20
 **/
public interface AccountCloseMapper {
    List<AccountClose> list(String companyId, String[] spotId, String startDate, String endDate, Integer type, Integer pageNum, Integer pageSize);

    Integer getCount(String companyId, String[] spotId, String startDate, String endDate, Integer type);

    Integer update(AccountClose accountClose);

    AccountClose getSpotIdAndCompanyIdAndDateByAccountClose(long spotId, Long companyId, String month);

    int add(AccountClose accountClose);

    int edit(AccountClose accountClose);

    /**
     * 根据时间查询结算流水记录
     * @param month
     * @return
     */
    List<AccountClose> getDateByAccountClose(String month);

    AccountClose selectSpotAndCompanyAndDate(Long spot, Long company, String beginDate);


}
