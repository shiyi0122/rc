package com.hna.hka.archive.management.system.service;

import java.util.Map;

import com.hna.hka.archive.management.system.model.SysCurrentUserCoupons;
import com.hna.hka.archive.management.system.util.PageDataResult;

public interface SysCurrentUserCouponsService {

	int addCurrentUserCoupons(SysCurrentUserCoupons sysCurrentUserCoupons);

	PageDataResult getCurrentUserCouponsList(Integer pageNum, Integer pageSize, Map<String, String> search);

	int delCurrentUserCoupons(Long userCouponsId);

	int insertCurrentUserCoupons(SysCurrentUserCoupons sysCurrentUserCoupons);

}
