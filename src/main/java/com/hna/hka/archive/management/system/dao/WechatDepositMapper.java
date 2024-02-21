package com.hna.hka.archive.management.system.dao;

import com.hna.hka.archive.management.system.model.WechatDeposit;
import com.hna.hka.archive.management.system.model.WechatDepositKey;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface WechatDepositMapper {
    int deleteByPrimaryKey(WechatDepositKey key);

    int insert(WechatDeposit record);

    int insertSelective(WechatDeposit record);

    WechatDeposit selectByPrimaryKey(WechatDepositKey key);

    int updateByPrimaryKeySelective(WechatDeposit record);

    int updateByPrimaryKey(WechatDeposit record);

    WechatDeposit getSysDepositByUserId(@Param("currentUserId") Long currentUserId,@Param("depositState") String depositState);

	List<WechatDeposit> getWechatSysDepositLogByUserId(Long currentUserId);

	WechatDeposit getDepositLogByUserId(Long currentUserId);

	WechatDeposit getWechatDepositByOutTradeNo(@Param("outTradeNo")String outTradeNo);
}