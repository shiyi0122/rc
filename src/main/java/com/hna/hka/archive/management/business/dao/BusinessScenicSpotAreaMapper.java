package com.hna.hka.archive.management.business.dao;

import com.hna.hka.archive.management.business.model.BusinessScenicSpotArea;

public interface BusinessScenicSpotAreaMapper {
    int deleteByPrimaryKey(Long id);

    int insert(BusinessScenicSpotArea record);

    int insertSelective(BusinessScenicSpotArea record);

    BusinessScenicSpotArea selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BusinessScenicSpotArea record);

    int updateByPrimaryKey(BusinessScenicSpotArea record);

    BusinessScenicSpotArea selectAreaScenicSpotId(Long scenicSpotId);

    int  deleteBySpotId(Long scenicSpotId);

}