package com.hna.hka.archive.management.system.service;

import java.util.List;
import java.util.Map;

import com.hna.hka.archive.management.system.model.SysScenicSpotActivity;
import com.hna.hka.archive.management.system.util.PageDataResult;

public interface SysScenicSpotActivityService {

	PageDataResult getScenicSpotActivityList(Integer pageNum, Integer pageSize, Map<String, Object> search);

	int addScenicSpotActivity(SysScenicSpotActivity sysScenicSpotActivity);

	int delScenicSpotActivity(Long activityId);

	List<SysScenicSpotActivity> getScenicSpotActivity();

	int getActivityFailure(SysScenicSpotActivity sysScenicSpotActivity);

	int editScenicSpotActivity(SysScenicSpotActivity sysScenicSpotActivity);

	void timingEditActivityFailure();


}
