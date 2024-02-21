package com.hna.hka.archive.management.system.service;

import com.github.pagehelper.PageInfo;
import com.hna.hka.archive.management.system.model.*;
import com.hna.hka.archive.management.system.util.PageDataResult;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

/**
 * @ProjectName: rc
 * @Package: com.hna.hka.archive.management.system.service
 * @ClassName: SysScenicSpotBroadcastHuntService
 * @Author: 郭凯
 * @Description: 寻宝奖品
 * @Date:
 * @Version: 1.0
 */
public interface SysScenicSpotTreasureHuntService {
    /**
     * @Method 查询寻宝及景点列表
     * @Author 郭凯
     * @Version 1.0
     * @Description
     * @Return
     * @Date 2021/12/9 13:00
     */
    PageDataResult getTreasureList(Integer pageNum, Integer pageSize, Map<String, String> search);

    /**
     * @Method 新增寻宝景点
     * @Author 曲绍备
     * @Version 1.0
     * @Description
     * @Return
     * @Date 2021/12/9 18:01
     */
    int addTreasure(SysScenicSpotTreasureHunt treasureHunt, MultipartFile file);

    /**
     * @Method 修改寻宝景点
     * @Author 张
     * @Version 1.0
     * @Description
     * @Return
     */
    int editTreasure(SysScenicSpotTreasureHunt treasureHunt, MultipartFile file);

    /**
     * @Method 删除寻宝景点
     * @Author 张
     * @Version 1.0
     * @Description
     * @Return
     */
    int delTreasure(Long treasureId, Long scenicSpotId);

    /**
     * 创建奖池
     * @param treasureNewJackpot
     * @return
     */
    int addJackpotNew(SysScenicSpotTreasureNewJackpot treasureNewJackpot);

    int updateJackpotNew(SysScenicSpotTreasureNewJackpot treasureNewJackpot);

    PageInfo<SysScenicSpotTreasureNewJackpot> getJackpotNew(SysScenicSpotTreasureNewJackpot treasureNewJackpot);

    int delectJackpotNew(SysScenicSpotTreasureNewJackpot treasureNewJackpot);

    PageInfo<SysCurrentUserIntegral> getUserInteGral(SysCurrentUserIntegral userIntegral);

    PageInfo<SysCurrentUserIntegralLog> getUserInteGralLog(SysCurrentUserIntegralLog userIntegralLog);

    PageInfo<SysCurrentUserAddress> getUserAddress(SysCurrentUserAddress userAddress);

    int editUserAddress(SysCurrentUserAddress userAddress);

    int delectUserAddress(SysCurrentUserAddress userAddress);

    int addUserAddress(SysCurrentUserAddress userAddress);

    int updateUserInteGral(SysCurrentUserIntegral userIntegral);

    PageInfo<SysCurrentUserExchange> getUserExchange(SysCurrentUserExchange userExchange);

    int editUserExchange(MultipartFile file,SysCurrentUserExchange userExchange);

    PageInfo<SysScenicSpotBroadcastHunt> getBroadcastHunt(SysScenicSpotBroadcastHunt broadcastHunt);

    int editBroadcastHunt(SysScenicSpotBroadcastHunt broadcastHunt);

    int addBroadcastHunt(SysScenicSpotBroadcastHunt broadcastHunt);

    int delectBroadcastHunt(SysScenicSpotBroadcastHunt broadcastHunt);

    PageDataResult getBroadcastHuntLog(SysScenicSpotBroadcastHuntLog broadcastHuntLog);

    PageInfo<SysScenicSpotTreasureNewHunt> getTreasureNewHunt(SysScenicSpotTreasureNewHunt treasureNewHunt);

    int addTreasureNewHunt(MultipartFile file, SysScenicSpotTreasureNewHunt treasureNewHunt);

    int editTreasureNewHunt(MultipartFile file, SysScenicSpotTreasureNewHunt treasureNewHunt);

    int delectTreasureNewHunt(SysScenicSpotTreasureNewHunt treasureNewHunt);

    PageInfo<SysCurrentUserIntegralHuntLog> getUserIntegralHuntLog(SysCurrentUserIntegralHuntLog integralHuntLog);

    List<SysScenicSpot> getSpotId(Long spotId);

    List<SysScenicSpotBroadcast> getbroadCast(Long broadcastId,Long scenicSpotId);

    PageInfo<SysCurrentUserExchangeLog> getUserExchangeLog(SysCurrentUserExchangeLog userExchangeLog);

    PageInfo<SysCurrentUserUpdateTreasureLog> getUpdateTreasureLog(SysCurrentUserUpdateTreasureLog userUpdateTreasureLog);

    List<SysCurrentUserExchange> downloadUserExchange(SysCurrentUserExchange userExchange);

    PageInfo<SysOrderDetail> getTreasureHuntDetail(SysOrder sysOrder) throws ParseException;


//
    /**
     * 下载寻宝景点表
     * @param search
     * @return
     */
//    List<SysScenicSpotBroadcastExtendWithBLOBs> getTreasureExcel(Map<String, Object> search);

}
