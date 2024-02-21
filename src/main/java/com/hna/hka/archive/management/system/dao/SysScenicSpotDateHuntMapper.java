package com.hna.hka.archive.management.system.dao;

import com.hna.hka.archive.management.system.model.SysScenicSpotDateHunt;

import java.util.List;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2022/3/10 10:12
 */
public interface SysScenicSpotDateHuntMapper {


    int insertSelective(SysScenicSpotDateHunt sysScenicSpotDateHunt);


    int updateByPrimaryKeySelective(SysScenicSpotDateHunt sysScenicSpotDateHunt);

    int deleteByPrimaryKey(Long dateTreasureId);

    List<SysScenicSpotDateHunt> getSpotDateHuntList(Map<String, String> search);

    List<SysScenicSpotDateHunt> getSpotDateHuntIdList(Map<String, Object> search);

}
