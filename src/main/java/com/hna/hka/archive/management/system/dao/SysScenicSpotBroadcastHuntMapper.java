package com.hna.hka.archive.management.system.dao;

import com.hna.hka.archive.management.system.model.SysScenicSpotBroadcastHunt;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysScenicSpotBroadcastHuntMapper {
    int insertSelective(SysScenicSpotBroadcastHunt broadcastHunt);

    List<SysScenicSpotBroadcastHunt> getBroadcastList(@Param("sysScenicSpotBroadcastHunt") SysScenicSpotBroadcastHunt sysScenicSpotBroadcastHunt);

    int openOrCloseBroadcastHunt(@Param("switchs") Long switchs,@Param("scenicSpotId") String scenicSpotId);


    List<SysScenicSpotBroadcastHunt> getBroadcastListNew(@Param("sysScenicSpotBroadcastHunt") SysScenicSpotBroadcastHunt sysScenicSpotBroadcastHunt);

    int updateByPrimaryKeySelective( SysScenicSpotBroadcastHunt sysScenicSpotBroadcastHunt);

    int deleteByPrimaryKey(@Param("broadcastId") Long broadcastId);

    int deleteById(Long broadcastId);
}
