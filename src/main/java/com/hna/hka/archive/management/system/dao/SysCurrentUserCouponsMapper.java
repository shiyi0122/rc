package com.hna.hka.archive.management.system.dao;

import java.util.List;
import java.util.Map;

import com.hna.hka.archive.management.system.model.SysCurrentUserCoupons;

public interface SysCurrentUserCouponsMapper {
    int deleteByPrimaryKey(Long userCouponsId);

    int insert(SysCurrentUserCoupons record);

    int insertSelective(SysCurrentUserCoupons record);

    SysCurrentUserCoupons selectByPrimaryKey(Long userCouponsId);

    int updateByPrimaryKeySelective(SysCurrentUserCoupons record);

    int updateByPrimaryKey(SysCurrentUserCoupons record);

	SysCurrentUserCoupons getCurrentUserCouponsById(Long currentUserId, Long activityId);

	List<SysCurrentUserCoupons> getCurrentUserCouponsList(Map<String, String> search);
}