package com.hna.hka.archive.management.business.dao;

import com.hna.hka.archive.management.business.model.BusinessMemberLevel;

import java.util.List;
import java.util.Map;

public interface BusinessMemberLevelMapper {
    int deleteByPrimaryKey(Long id);

    int insert(BusinessMemberLevel record);

    int insertSelective(BusinessMemberLevel record);

    BusinessMemberLevel selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BusinessMemberLevel record);

    int updateByPrimaryKey(BusinessMemberLevel record);

    List<BusinessMemberLevel> getMemberLevelList(Map<String, String> search);
}