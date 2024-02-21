package com.hna.hka.archive.management.appSystem.service.Impl;

import com.hna.hka.archive.management.appSystem.dao.SysCurrentUserExchangeMapper;
import com.hna.hka.archive.management.appSystem.model.SysCurrentUserExchange;
import com.hna.hka.archive.management.appSystem.service.AppCodeScanCodePrizeCashingService;
import com.hna.hka.archive.management.system.model.SysCurrentUserExchangeLog;
import com.hna.hka.archive.management.system.util.DateUtil;
import com.hna.hka.archive.management.system.util.IdUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2022/4/11 14:49
 */
@Service
public class AppCodeScanCodePrizeCashingServiceImpl implements AppCodeScanCodePrizeCashingService {

    @Autowired
    SysCurrentUserExchangeMapper sysCurrentUserExchangeMapper;

    /**
     * 查询兑奖信息
     * @param: search
     * @description: TODO
     * @author: qushaobei
     * @date: 2021/11/25 0025
     */
    public SysCurrentUserExchange exchangePrize(Map<String, Object> search) {
        return sysCurrentUserExchangeMapper.exchangePrize(search);
    }

    /**
     * 修改兑奖品状态
     * @param exchange
     * @return
     */

    @Override
    public int updateExchangePrizeState(SysCurrentUserExchange exchange) {

        exchange.setExchangeState("1");
        exchange.setShipmentStatus("4");//现场已兑换
        int i = sysCurrentUserExchangeMapper.updateByPrimaryKeySelective(exchange);

        SysCurrentUserExchange exchangePrize = sysCurrentUserExchangeMapper.getExchangePrize(exchange);
        SysCurrentUserExchangeLog exchangeLog = new SysCurrentUserExchangeLog();
        exchangeLog.setExchangeLogId(IdUtils.getSeqId());
        exchangeLog.setUserId(exchangePrize.getUserId());
        //奖品id
        exchangeLog.setPrizeId(exchangePrize.getExchangeId());
        exchangeLog.setScenicSpotId(exchangePrize.getScenicSpotId());
        exchangeLog.setExchangePort("1");
        exchangeLog.setOperationalState("3");
        exchangeLog.setAccountName(exchange.getAccountName());
        exchangeLog.setCreateDate(DateUtil.currentDateTime());
        exchangeLog.setUpdateDate(DateUtil.currentDateTime());
        sysCurrentUserExchangeMapper.addExchangeLog(exchangeLog);
        return i;
    }

    /**
     * 查询奖品列表
     * @param pageNum
     * @param pageSize
     * @param search
     * @return
     */
    @Override
    public List<SysCurrentUserExchange> queryExchangePrizeList(int pageNum, int pageSize, Map<String, Object> search) {


        sysCurrentUserExchangeMapper.queryExchangePrizeList(search);

        return null;
    }

    @Override
    public void timingExchangePrize() {
        List<SysCurrentUserExchange> exchange = sysCurrentUserExchangeMapper.getExchangePrizeList();
        for (int i = 0; i < exchange.size(); i++) {
            if (!DateUtil.isEffectiveDates(exchange.get(i).getStartValidity(), exchange.get(i).getEndValidity())){
                exchange.get(i).setShipmentStatus("5");
                exchange.get(i).setExchangeState("2");
                sysCurrentUserExchangeMapper.updateByPrimaryKeySelective(exchange.get(i));
            }
        }
    }
}
