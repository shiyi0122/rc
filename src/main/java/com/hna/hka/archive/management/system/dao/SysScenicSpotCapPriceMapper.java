package com.hna.hka.archive.management.system.dao;

import com.hna.hka.archive.management.system.model.SysScenicSpotCapPrice;
import com.hna.hka.archive.management.system.model.SysScenicSpotCapPriceLog;

import java.util.List;
import java.util.Map;

public interface SysScenicSpotCapPriceMapper {
    int deleteByPrimaryKey(Long capPriceId);

    int insert(SysScenicSpotCapPrice record);

    int insertSelective(SysScenicSpotCapPrice record);

    SysScenicSpotCapPrice selectByPrimaryKey(Long capPriceId);

    int updateByPrimaryKeySelective(SysScenicSpotCapPrice record);

    int updateByPrimaryKey(SysScenicSpotCapPrice record);

    SysScenicSpotCapPrice selectCapPriceByScenicSpotId(Long scenicSpotId);

    List<SysScenicSpotCapPrice> getScenicSpotCapPriceList(Map<String, String> search);

    List<SysScenicSpotCapPriceLog> getScenicSpotCapPriceListExcel(Map<String, String> search);
}