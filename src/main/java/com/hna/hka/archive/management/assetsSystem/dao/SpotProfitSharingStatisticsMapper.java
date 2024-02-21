package com.hna.hka.archive.management.assetsSystem.dao;

import com.hna.hka.archive.management.assetsSystem.model.SysScenicSpotFenfun;

import java.util.List;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2022/4/23 17:59
 */

public interface SpotProfitSharingStatisticsMapper {


    List<SysScenicSpotFenfun> spotProfitSharingStatistice(Long companyId, Long spotId, String startDate, String endDate, Long type);


    SysScenicSpotFenfun  selectByPrimaryKey(Long fenrunScenicSpotId);

    int insertSelective(SysScenicSpotFenfun sysScenicSpotFenfun);

    int updateByPrimaryKeySelective(SysScenicSpotFenfun sysScenicSpotFenfun);

    int deleteByPrimaryKey(Long fenrunScenicSpotId);

    List<SysScenicSpotFenfun> list(Map<String,Object> search);


    Map<String, Object> getAmountTo(Map<String, Object> search);


    SysScenicSpotFenfun getSpotIdAndDateByOne(long companyId, long spotId, String month);

}
