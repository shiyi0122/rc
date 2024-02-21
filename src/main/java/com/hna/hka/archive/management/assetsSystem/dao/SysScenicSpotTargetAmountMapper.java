package com.hna.hka.archive.management.assetsSystem.dao;

import com.hna.hka.archive.management.assetsSystem.model.SumPrice;
import com.hna.hka.archive.management.assetsSystem.model.SysScenicSpotTargetAmount;

import java.util.List;
import java.util.Map;

public interface SysScenicSpotTargetAmountMapper {
    int deleteByPrimaryKey(Long targetAmountId);

    int insert(SysScenicSpotTargetAmount record);

    int insertSelective(SysScenicSpotTargetAmount record);

    SysScenicSpotTargetAmount selectByPrimaryKey(Long targetAmountId);

    int updateByPrimaryKeySelective(SysScenicSpotTargetAmount record);

    int updateByPrimaryKey(SysScenicSpotTargetAmount record);

    List<SysScenicSpotTargetAmount> getTargetAmountList(Map<String, Object> search);

    SysScenicSpotTargetAmount getTargetAmountById(SysScenicSpotTargetAmount sysScenicSpotTargetAmount);

    List<SysScenicSpotTargetAmount> getTargetAmountListExel(Map<String, Object> search);

    Double getTargetAmountGpmList(Long scenicSpotId, String startTime, String endTime);

    SysScenicSpotTargetAmount getSpotIdAndDateByTagret(Long scenicSpotId, String date);


}