package com.hna.hka.archive.management.appSystem.dao;

import com.hna.hka.archive.management.appSystem.model.SysRobotAccessoriesApplicationDetail;
import com.hna.hka.archive.management.appSystem.model.SysRobotErrorRecordDetaIl;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author zhang
 * @Date 2023/2/27 16:57
 */
public interface SysRobotAccessoriesApplicationDetailMapper {

    int add(SysRobotAccessoriesApplicationDetail sysRobotAccessoriesApplicationDetail);

    int updateByPrimaryKeySelective(SysRobotAccessoriesApplicationDetail sysRobotAccessoriesApplicationDetail);

    List<SysRobotAccessoriesApplicationDetail> selectByAccessoriesApplicationId(Long accessoriesApplicationId);

    List<SysRobotAccessoriesApplicationDetail> getSpotIdSendOutGoodsList(@Param("userId") Long userId,@Param("scenicSpotId") String scenicSpotId, @Param("isSendOutGoods") String isSendOutGoods);


    SysRobotAccessoriesApplicationDetail selectById(String id);

    int deleteByAccessoriesId(Long accessoriesApplicationId);


    Double getSumAccessoryPrice(Long errorRecordsId);

    List<SysRobotAccessoriesApplicationDetail> selectNullByList(@Param("scenicSpotId") String scenicSpotId, @Param("isSendOutGoods") String isSendOutGoods);


    int insertSys(SysRobotErrorRecordDetaIl sysRobotErrorRecordDetaIl);

    List<SysRobotErrorRecordDetaIl> selectByIds(Long errorRecordsId);
}
