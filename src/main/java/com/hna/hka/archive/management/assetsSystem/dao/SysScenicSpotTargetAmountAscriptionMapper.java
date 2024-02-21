package com.hna.hka.archive.management.assetsSystem.dao;

import com.hna.hka.archive.management.assetsSystem.model.SysScenicSpotTargetAmountAscription;

/**
 * @Author zhang
 * @Date 2022/3/7 16:58
 */
public interface SysScenicSpotTargetAmountAscriptionMapper {

    int insertSelective(SysScenicSpotTargetAmountAscription sysScenicSpotTargetAmountAscription);

    int updateByPrimaryKeySelective(SysScenicSpotTargetAmountAscription sysScenicSpotTargetAmountAscription);

    int deleteByTargetAmountId(Long targetAmountId);

    SysScenicSpotTargetAmountAscription[] getSpotTargetAmountAscriptionByType(Long targetAmountId,String type);
}
