package com.hna.hka.archive.management.system.dao;

import com.hna.hka.archive.management.system.model.SysScenicSpotBroadcast;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysScenicSpotBroadcastMapper {
    int deleteByPrimaryKey(Long broadcastId);

    int insert(SysScenicSpotBroadcast record);

    int insertSelective(SysScenicSpotBroadcast record);

    SysScenicSpotBroadcast selectByPrimaryKey(Long broadcastId);

    int updateByPrimaryKeySelective(SysScenicSpotBroadcast record);

    int updateByPrimaryKey(SysScenicSpotBroadcast record);

    List<SysScenicSpotBroadcast> getBroadcastList(@Param("sysScenicSpotBroadcast") SysScenicSpotBroadcast sysScenicSpotBroadcast);

    List<SysScenicSpotBroadcast> getBroadcastListByScenicSpotId(Long scenicSpotId);

    int getBroadcastSumByScenicSpotId(Long scenicSpotId);

    List<SysScenicSpotBroadcast> getScenicSpotBroadcastAll();


}