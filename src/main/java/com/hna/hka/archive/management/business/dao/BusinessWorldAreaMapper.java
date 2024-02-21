package com.hna.hka.archive.management.business.dao;

import com.hna.hka.archive.management.business.model.BusinessWorldArea;

import java.util.List;

public interface BusinessWorldAreaMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(BusinessWorldArea record);

    int insertSelective(BusinessWorldArea record);

    BusinessWorldArea selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BusinessWorldArea record);

    int updateByPrimaryKey(BusinessWorldArea record);

    List<BusinessWorldArea> getProvince(Long pid);


}