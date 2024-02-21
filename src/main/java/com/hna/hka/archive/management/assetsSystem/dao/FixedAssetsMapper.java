package com.hna.hka.archive.management.assetsSystem.dao;

import com.hna.hka.archive.management.assetsSystem.model.Cost;
import org.apache.commons.math3.analysis.function.Cos;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FixedAssetsMapper {

    List<Cost> selDateAndCost(@Param("scenicSpotId") String scenicSpotId);

    Cost selActualCost(@Param("startDate") String startDate,@Param("endDate") String endDate,@Param("scenicSpotId") String scenicSpotId);

    Cost selFlowingWater(Cost cost);

    Cost selGrossProfit(@Param("startDate") String startDate,@Param("endDate") String endDate,@Param("scenicSpotId") String scenicSpotId);

    List<Cost> reportActualCost(@Param("startDate") String startDate,@Param("endDate") String endDate,@Param("scenicSpotId") String scenicSpotId);

    List<Cost> reportGrossProfit(@Param("startDate") String startDate,@Param("endDate") String endDate,@Param("scenicSpotId") String scenicSpotId);
}