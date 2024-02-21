package com.hna.hka.archive.management.assetsSystem.dao;

import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

/**
 * @Author zhang
 * @Date 2022/3/3 13:23
 * 机器人饱和度
 */
public interface RobotOperateSaturationMapper {


    List<HashMap> getOperateSaturationList(Long spotId, Integer pageNum, Integer pageSize);


    String getOperateSaturationCensus(@Param("startDate") String  startDate,@Param("endDate") String endDate,@Param("spotId") Long spotId);

    int  getOperateSaturationListCount(@Param("spotId") Long spotId);

    HashMap getOperateSaturationSettlement(@Param("spotId") Long spotId);
}
