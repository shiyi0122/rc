package com.hna.hka.archive.management.system.dao;

import com.hna.hka.archive.management.system.model.SysScenicSpotBroadcastExtend;
import com.hna.hka.archive.management.system.model.SysScenicSpotBroadcastExtendWithBLOBs;

import java.util.List;
import java.util.Map;

public interface SysScenicSpotBroadcastExtendMapper {
    int deleteByPrimaryKey(Long broadcastResId);

    int insert(SysScenicSpotBroadcastExtendWithBLOBs record);

    int insertSelective(SysScenicSpotBroadcastExtendWithBLOBs record);

    SysScenicSpotBroadcastExtendWithBLOBs selectByPrimaryKey(Long broadcastResId);

    int updateByPrimaryKeySelective(SysScenicSpotBroadcastExtendWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(SysScenicSpotBroadcastExtendWithBLOBs record);

    int updateByPrimaryKey(SysScenicSpotBroadcastExtend record);

    List<SysScenicSpotBroadcastExtendWithBLOBs> getBroadcastContentList(Map<String, Object> search);

    List<SysScenicSpotBroadcastExtendWithBLOBs> getBroadcastExcel(Map<String, Object> search);
}