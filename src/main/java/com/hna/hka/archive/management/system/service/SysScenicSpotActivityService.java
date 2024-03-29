package com.hna.hka.archive.management.system.service;

import com.hna.hka.archive.management.system.model.SysScenicSpotActivity;
import com.hna.hka.archive.management.system.util.PageDataResult;

import java.util.List;
import java.util.Map;

public interface SysScenicSpotActivityService {

	PageDataResult getScenicSpotActivityList(Integer pageNum, Integer pageSize, Map<String, Object> search);

	int addScenicSpotActivity(SysScenicSpotActivity sysScenicSpotActivity);

	int delScenicSpotActivity(Long activityId);

	List<SysScenicSpotActivity> getScenicSpotActivity();

	int getActivityFailure(SysScenicSpotActivity sysScenicSpotActivity);

	int editScenicSpotActivity(SysScenicSpotActivity sysScenicSpotActivity);

	void timingEditActivityFailure();


	int addvipCoupon(String userPhone, Long scenicSpotId, Long number);
}
