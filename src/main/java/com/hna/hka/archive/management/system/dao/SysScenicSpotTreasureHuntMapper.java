package com.hna.hka.archive.management.system.dao;

import com.hna.hka.archive.management.system.model.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface SysScenicSpotTreasureHuntMapper {
    int insertSelective(SysScenicSpotTreasureHunt treasureHunt);

    List<SysScenicSpotTreasureHunt> getTreasureList(@Param("sysScenicSpotTreasureHunt") SysScenicSpotTreasureHunt sysScenicSpotTreasureHunt);

    List<SysScenicSpotTreasureHunt> getTreasureListNew(Map<String,String> search);

    int updateByPrimaryKeySelective( SysScenicSpotTreasureHunt sysScenicSpotTreasureHunt);

    int deleteByPrimaryKey(@Param("treasureId") Long treasureId);

    int addJackpotNew(SysScenicSpotTreasureNewJackpot treasureNewJackpot);

    int updateJackpotNew(SysScenicSpotTreasureNewJackpot treasureNewJackpot);

    List<SysScenicSpotTreasureNewJackpot> getJackpotNew(SysScenicSpotTreasureNewJackpot treasureNewJackpot);

    int delectJackpotNew(SysScenicSpotTreasureNewJackpot treasureNewJackpot);

    List<SysCurrentUserIntegral> getUserInteGral(SysCurrentUserIntegral userIntegral);

    List<SysCurrentUserIntegralLog> getUserInteGralLog(SysCurrentUserIntegralLog userIntegralLog);

    List<SysCurrentUserAddress> getUserAddress(SysCurrentUserAddress userAddress);

    int editUserAddress(SysCurrentUserAddress userAddress);

    int delectUserAddress(SysCurrentUserAddress userAddress);

    int addUserAddress(SysCurrentUserAddress userAddress);

    int updateUserInteGral(SysCurrentUserIntegral userIntegral);

    int insertUserInteGralLog(SysCurrentUserIntegralLog userIntegralLog);

    List<SysCurrentUserExchange> getUserExchange(SysCurrentUserExchange userExchange);

    int editUserExchange(SysCurrentUserExchange userExchange);

    List<SysScenicSpotBroadcastHunt> getBroadcastHunt(SysScenicSpotBroadcastHunt broadcastHunt);

    int editBroadcastHunt(SysScenicSpotBroadcastHunt broadcastHunt);

    int addBroadcastHunt(SysScenicSpotBroadcastHunt broadcastHunt);

    int delectBroadcastHunt(SysScenicSpotBroadcastHunt broadcastHunt);

    List<SysScenicSpotBroadcastHuntLog> getBroadcastHuntLog(SysScenicSpotBroadcastHuntLog broadcastHuntLog);

    List<SysScenicSpotTreasureNewHunt> getTreasureNewHunt(SysScenicSpotTreasureNewHunt treasureNewHunt);

    int addTreasureNewHunt(SysScenicSpotTreasureNewHunt treasureNewHunt);

    int editTreasureNewHunt(SysScenicSpotTreasureNewHunt treasureNewHunt);

    int delectTreasureNewHunt(SysScenicSpotTreasureNewHunt treasureNewHunt);

    List<SysCurrentUserIntegralHuntLog> getUserIntegralHuntLog(SysCurrentUserIntegralHuntLog integralHuntLog);

    SysCurrentUserIntegral getUserByUserId(Long userId);

    List<SysScenicSpot> getSpotId(Long spotId);

    SysCurrentUserExchange getUserExchangeId(Long exchangeId);

    List<SysScenicSpotBroadcast> getbroadCast(Long broadcastId,Long scenicSpotId);

    SysCurrentUser selectByPhone(String userPhone);

    int delectJackpotHunt(Long jackpotId);

    List<SysCurrentUserExchangeLog> getUserExchangeLog(SysCurrentUserExchangeLog userExchangeLog);

    int editBroadcastHuntId(SysScenicSpotBroadcastHunt broadcastHunt);

    SysScenicSpotTreasureNewHunt getTreasureId(Long treasureId);

    int addTreasureNewHuntLog(SysCurrentUserUpdateTreasureLog updateTreasureLog);

    List<SysCurrentUserUpdateTreasureLog> getUpdateTreasureLog(SysCurrentUserUpdateTreasureLog userUpdateTreasureLog);

    List<SysOrderDetail> getTreasureHuntDetail(SysOrder sysOrder);


}
