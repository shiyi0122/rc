package com.hna.hka.archive.management.business.dao;

import com.hna.hka.archive.management.business.model.BusinessFeedBack;

import java.util.List;
import java.util.Map;

public interface BusinessFeedBackMapper {
    int deleteByPrimaryKey(Long id);

    int insert(BusinessFeedBack record);

    int insertSelective(BusinessFeedBack record);

    BusinessFeedBack selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BusinessFeedBack record);

    int updateByPrimaryKey(BusinessFeedBack record);

    List<BusinessFeedBack> getFeedBackList(Map<String, String> search);
}