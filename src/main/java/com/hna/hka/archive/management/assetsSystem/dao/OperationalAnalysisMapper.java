package com.hna.hka.archive.management.assetsSystem.dao;

import com.hna.hka.archive.management.assetsSystem.model.ScenicSpot;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

/**
 * @program: rc
 * @description:
 * @author: zhaoxianglong
 * @create: 2021-09-27 10:44
 **/
public interface OperationalAnalysisMapper {
    List<HashMap<String, Object>> timeSort(Integer type, String beginDate, String endDate, int analysis);

    List<HashMap<String , Object>> timeList(Integer type, String beginDate, String endDate, Object spotId, int analysis);

    List fitList();

    Integer fitAdd(Long id, Integer begin, Integer end);

    Integer fitEdit(Long id, Integer begin, Integer end);

    Integer fitDelete(Long id);

    List<HashMap<String, Object>> fitRevenue(Integer type, String beginDate, String endDate, int analysis,String spotId,String provinceId);

    List<ScenicSpot>  spotList(@Param("provinceId") Long provinceId);

    List<ScenicSpot> provinceList();
}
