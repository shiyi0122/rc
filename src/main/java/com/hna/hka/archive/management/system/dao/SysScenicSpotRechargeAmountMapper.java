package com.hna.hka.archive.management.system.dao;

import com.hna.hka.archive.management.system.model.SysScenicSpotRechargeAmount;

import java.util.List;
import java.util.Map;

public interface SysScenicSpotRechargeAmountMapper {
    int deleteByPrimaryKey(Long rechargeId);

    int insert(SysScenicSpotRechargeAmount record);

    int insertSelective(SysScenicSpotRechargeAmount record);

    SysScenicSpotRechargeAmount selectByPrimaryKey(Long rechargeId);

    int updateByPrimaryKeySelective(SysScenicSpotRechargeAmount record);

    int updateByPrimaryKeyWithBLOBs(SysScenicSpotRechargeAmount record);

    int updateByPrimaryKey(SysScenicSpotRechargeAmount record);

    List<SysScenicSpotRechargeAmount> getRechargeAmountList(Map<String, String> search);
}