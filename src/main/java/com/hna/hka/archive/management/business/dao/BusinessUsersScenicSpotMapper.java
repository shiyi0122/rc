package com.hna.hka.archive.management.business.dao;

import com.hna.hka.archive.management.business.model.BusinessUsers;
import com.hna.hka.archive.management.business.model.BusinessUsersScenicSpot;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface BusinessUsersScenicSpotMapper {
    int deleteByPrimaryKey(Long id);

    int insert(BusinessUsersScenicSpot record);

    int insertSelective(BusinessUsersScenicSpot record);

    BusinessUsersScenicSpot selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BusinessUsersScenicSpot record);

    int updateByPrimaryKey(BusinessUsersScenicSpot record);

    BusinessUsersScenicSpot getBusinessUsersScenicSpot(@Param("userId") Long userId,@Param("scenicSpotId") Long scenicSpotId);

    List<BusinessUsers> getBusinessUsersScenicSpotsList(Map<String, Object> search);
}