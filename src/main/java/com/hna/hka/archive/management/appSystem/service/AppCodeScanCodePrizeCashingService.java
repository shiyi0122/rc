package com.hna.hka.archive.management.appSystem.service;

import com.hna.hka.archive.management.appSystem.model.SysCurrentUserExchange;

import java.util.List;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2022/4/11 14:49
 *
 * 扫码兑奖
 */
public interface AppCodeScanCodePrizeCashingService {
    /**
     * 查询兑奖信息
     * @param: search
     * @description: TODO
     * @author: qushaobei
     * @date: 2021/11/25 0025
     */
    SysCurrentUserExchange exchangePrize(Map<String, Object> search);


    /**
     * 修改奖品状态
     * @param exchange
     * @return
     */
    int updateExchangePrizeState(SysCurrentUserExchange exchange);

    List<SysCurrentUserExchange> queryExchangePrizeList(int pageNum, int pageSize, Map<String, Object> search);

    void timingExchangePrize();
}
