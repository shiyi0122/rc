package com.hna.hka.archive.management.business.dao;



import com.hna.hka.archive.management.business.model.BusinessFilingSpot;

import java.util.List;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2023/3/15 11:24
 */
public interface BusinessFilingSpotMapper {

    List<BusinessFilingSpot> getFilingSpotList(Map search);


    int insertSelective(BusinessFilingSpot businessFilingSpot);

    int updateByPrimaryKeySelective(BusinessFilingSpot businessFilingSpot);

    int deleteByPrimaryKey(Long id);
}
