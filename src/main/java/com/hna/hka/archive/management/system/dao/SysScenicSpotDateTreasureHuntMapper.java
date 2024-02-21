package com.hna.hka.archive.management.system.dao;

import com.hna.hka.archive.management.system.model.SysScenicSpotDateTreasureHunt;

import java.util.List;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2022/3/11 15:23
 */
public interface SysScenicSpotDateTreasureHuntMapper {


    List<SysScenicSpotDateTreasureHunt> getDateTreasureListNew(Map<String, String> search);


    int insertSelective(SysScenicSpotDateTreasureHunt dateTreasureHunt);

    int updateByPrimaryKeySelective(SysScenicSpotDateTreasureHunt datetreasureHunt);

    int deleteByPrimaryKey(Long treasureId);

}
