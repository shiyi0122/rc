package com.hna.hka.archive.management.business.dao;

import com.hna.hka.archive.management.business.model.BusinessCooperate;

import java.util.List;
import java.util.Map;

public interface BusinessCooperateMapper {
    int deleteByPrimaryKey(Long id);

    int insert(BusinessCooperate record);

    int insertSelective(BusinessCooperate record);

    BusinessCooperate selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BusinessCooperate record);

    int updateByPrimaryKey(BusinessCooperate record);

    List<BusinessCooperate> getBusinessCooperateList(Map<String, String> search);
}