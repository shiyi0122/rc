package com.hna.hka.archive.management.business.dao;

import com.hna.hka.archive.management.business.model.BusinessScenicSpotExpand;

import java.util.List;
import java.util.Map;

public interface BusinessScenicSpotExpandMapper {
    int deleteByPrimaryKey(Long id);

    int insert(BusinessScenicSpotExpand record);

    int insertSelective(BusinessScenicSpotExpand record);

    BusinessScenicSpotExpand selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BusinessScenicSpotExpand record);

    int updateByPrimaryKeyWithBLOBs(BusinessScenicSpotExpand record);

    int updateByPrimaryKey(BusinessScenicSpotExpand record);

    List<BusinessScenicSpotExpand> getScenicSpotExpandList(Map<String, Object> search);

    BusinessScenicSpotExpand selectScenicSpotExpandByScenicId(Long scenicSpotId);

	List<BusinessScenicSpotExpand> getScenicSpot();

    List<BusinessScenicSpotExpand> getScenicSpotExpandListByUserId(Map<String, Object> search);

    //根据景区id修改扩展景区
    int updateByPrimaryKeySelectiveBySpotId(BusinessScenicSpotExpand businessScenicSpotExpand);


    int deleteBySpotId(Long scenicSpotId);


}