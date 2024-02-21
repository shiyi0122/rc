package com.hna.hka.archive.management.business.dao;

import com.hna.hka.archive.management.business.model.BusinessDataMaintain;

import java.util.List;
import java.util.Map;

public interface BusinessDataMaintainMapper {
    int deleteByPrimaryKey(Long id);

    int insert(BusinessDataMaintain record);

    int insertSelective(BusinessDataMaintain record);

    BusinessDataMaintain selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BusinessDataMaintain record);

    int updateByPrimaryKey(BusinessDataMaintain record);

    List<BusinessDataMaintain> getDataMaintainList(Map<String, String> search);
}