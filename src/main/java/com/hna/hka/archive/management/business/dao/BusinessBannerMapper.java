package com.hna.hka.archive.management.business.dao;

import com.hna.hka.archive.management.business.model.BusinessBanner;

import java.util.List;
import java.util.Map;

public interface BusinessBannerMapper {
    int deleteByPrimaryKey(Long id);

    int insert(BusinessBanner record);

    int insertSelective(BusinessBanner record);

    BusinessBanner selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BusinessBanner record);

    int updateByPrimaryKey(BusinessBanner record);

    List<BusinessBanner> getBannerList(Map<String, String> search);
}