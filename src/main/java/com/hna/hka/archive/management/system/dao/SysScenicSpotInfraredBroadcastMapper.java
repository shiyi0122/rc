package com.hna.hka.archive.management.system.dao;

import com.hna.hka.archive.management.system.model.SysScenicSpotInfraredBroadcast;

import java.util.List;
import java.util.Map;

public interface SysScenicSpotInfraredBroadcastMapper {
    int deleteByPrimaryKey(Long infraredId);

    int insert(SysScenicSpotInfraredBroadcast record);

    int insertSelective(SysScenicSpotInfraredBroadcast record);

    SysScenicSpotInfraredBroadcast selectByPrimaryKey(Long infraredId);

    int updateByPrimaryKeySelective(SysScenicSpotInfraredBroadcast record);

    int updateByPrimaryKey(SysScenicSpotInfraredBroadcast record);

    List<SysScenicSpotInfraredBroadcast> getInfraredBroadcastList(Map<String, String> search);
}