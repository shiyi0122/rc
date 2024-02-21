package com.hna.hka.archive.management.assetsSystem.service;

import com.hna.hka.archive.management.assetsSystem.model.Cost;
import com.hna.hka.archive.management.assetsSystem.model.FixedAssetsResult;
import com.hna.hka.archive.management.assetsSystem.model.SearchRobot;
import com.hna.hka.archive.management.system.util.PageDataResult;

public interface FixedAssetsService {
    FixedAssetsResult list(String startDate,String endDate,String scenicSpotId,Integer pageNum,Integer pageSize);

    PageDataResult report(String startDate,String endDate,String scenicSpotId);
}
