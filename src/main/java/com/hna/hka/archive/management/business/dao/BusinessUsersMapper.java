package com.hna.hka.archive.management.business.dao;

import com.hna.hka.archive.management.business.model.BusinessUsers;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface BusinessUsersMapper {
    int deleteByPrimaryKey(Long id);

    int insert(BusinessUsers record);

    int insertSelective(BusinessUsers record);

    BusinessUsers selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BusinessUsers record);

    int updateByPrimaryKey(BusinessUsers record);

    List<BusinessUsers> getBusinessUsersList(Map<String, String> search);

    int editBusinessUsersFilling(@Param("userId") Long userId,@Param("examineType") Long examineType);

    int editBusinessUsersDeposit(Long userId, Long depositCheckType);

}