package com.hna.hka.archive.management.system.dao;

import com.hna.hka.archive.management.system.model.SysScenicSpotCapPriceLog;

import java.util.List;
import java.util.Map;

public interface SysScenicSpotCapPriceLogMapper {
    int deleteByPrimaryKey(Long capPriceId);

    int insert(SysScenicSpotCapPriceLog record);

    int insertSelective(SysScenicSpotCapPriceLog record);

    SysScenicSpotCapPriceLog selectByPrimaryKey(Long capPriceId);

    int updateByPrimaryKeySelective(SysScenicSpotCapPriceLog record);

    int updateByPrimaryKey(SysScenicSpotCapPriceLog record);

    List<SysScenicSpotCapPriceLog> getScenicSpotCapPriceLogList(Map<String, Object> search);

    List<SysScenicSpotCapPriceLog> getScenicSpotCapPriceLogExcel(Map<String, String> search);

}