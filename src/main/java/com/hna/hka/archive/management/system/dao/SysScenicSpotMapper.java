package com.hna.hka.archive.management.system.dao;

import com.hna.hka.archive.management.appSystem.model.BigPadSpot;
import com.hna.hka.archive.management.assetsSystem.model.ScenicSpot;
import com.hna.hka.archive.management.system.model.SysScenicSpot;
import com.hna.hka.archive.management.system.model.SysScenicSpotAndCap;
import com.hna.hka.archive.management.system.model.SysScenicSpotExpand;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface SysScenicSpotMapper {
    int deleteByPrimaryKey(Long scenicSpotId);

    int insert(SysScenicSpot record);

    int insertSelective(SysScenicSpot record);

    SysScenicSpot selectByPrimaryKey(Long scenicSpotId);

    int updateByPrimaryKeySelective(SysScenicSpot record);

    int updateByPrimaryKey(SysScenicSpot record);

    /**
     * 查询所有景区
     * @return
     */
    List<SysScenicSpot> getScenicSpotList();

    /**
     * 景区列表查询
     * @param searc
     * @return
     */
    List<SysScenicSpot> selectScenicSpotList(Map<String,String> searc);

    /**
     * 景区列表查询，有扩展景区
     * @param searc
     * @return
     */
    List<SysScenicSpotExpand> selectScenicSpotListNew(Map<String,String> searc);


    List<SysScenicSpot> getScenicSpotBillingRulesList(Map<String, Object> search);


    List<SysScenicSpot> getScenicSpotNameList();


    SysScenicSpotAndCap getSysScenicSpotAndCap(Long scenicSpotId);

    List<SysScenicSpotAndCap> getSysScenicSpotAndCapList();

    SysScenicSpot selectScenicNameByName(String scenicSpotName);

    int getScenicSpotSwitchs(long scenicSpotId);

    SysScenicSpot selectByScenicSpotId(Long spotId);

    List<SysScenicSpot> getScenicSpotListPaging(Integer pageNum, Integer pageSize);

    Integer getScenicSpotCount();

    List<SysScenicSpot> getScenicSpotOperated(String i);

    Integer getScenicSpotOperateNum(Integer i);


    List<BigPadSpot> getBigPidSpotDropDownBox();


    List<SysScenicSpot> getScenicSpotListNew( @Param("spotId") String spotId);


    SysScenicSpot getSpotNameById(String scenicSpotName);

    List<SysScenicSpot> getScenicSpotListWords();


    List<SysScenicSpot> getScenicSpotUnusualList();


    List<SysScenicSpot> selectById(ScenicSpot scenicSpot);
}
