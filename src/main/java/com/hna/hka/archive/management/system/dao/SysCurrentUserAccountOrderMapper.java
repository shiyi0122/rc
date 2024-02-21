package com.hna.hka.archive.management.system.dao;

import com.hna.hka.archive.management.system.model.SysCurrentUserAccountOrder;

import java.util.List;
import java.util.Map;

public interface SysCurrentUserAccountOrderMapper {
    int deleteByPrimaryKey(Long accountOrderId);

    int insert(SysCurrentUserAccountOrder record);

    int insertSelective(SysCurrentUserAccountOrder record);

    SysCurrentUserAccountOrder selectByPrimaryKey(Long accountOrderId);

    int updateByPrimaryKeySelective(SysCurrentUserAccountOrder record);

    int updateByPrimaryKey(SysCurrentUserAccountOrder record);

    List<SysCurrentUserAccountOrder> getCurrentUserAccountOrderList(Map<String, Object> search);

    List<SysCurrentUserAccountOrder> getCurrentUserAccountOrderListByUserId(Long userId);
}