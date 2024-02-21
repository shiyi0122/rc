package com.hna.hka.archive.management.system.dao;

import com.hna.hka.archive.management.system.model.SysScenicSpotTreasureHunt;
import com.hna.hka.archive.management.system.model.SysScenicSpotTreasureHuntRule;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface SysScenicSpotTreasureHuntRuleMapper {
    int insertSelective(SysScenicSpotTreasureHuntRule treasureHuntRule);

    List<SysScenicSpotTreasureHuntRule> getBroadcastList(Long scenicSpotId,String ruleType);

    List<SysScenicSpotTreasureHunt> getTreasureListNew(Map<String,String> search);

    int updateByPrimaryKeySelective( SysScenicSpotTreasureHuntRule sysScenicSpotTreasureHuntRule);

    int deleteByPrimaryKey(@Param("ruleId") Long ruleId);

    SysScenicSpotTreasureHuntRule selectBroadcastRuleBySpotId(Long scenicSpotId,String ruleType);

}
