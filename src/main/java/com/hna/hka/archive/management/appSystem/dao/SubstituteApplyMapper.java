package com.hna.hka.archive.management.appSystem.dao;

import com.hna.hka.archive.management.appSystem.model.SubstituteApply;
import com.hna.hka.archive.management.appSystem.model.SubstituteApplyDetail;

import java.util.List;
import java.util.Map;

/**
 * @program: rc
 * @description:
 * @author: zhaoxianglong
 * @create: 2021-11-03 10:06
 **/
public interface SubstituteApplyMapper {
    List<SubstituteApply> list(String beginDate, String endDate, Long spotId, Long userId, Integer stat);

    void add(SubstituteApply substituteApply);

    void edit(SubstituteApply substituteApply);

    String getGTIdBySpotId(Long applyUserId);

    SubstituteApply getByKey(String applyNumber);

    SubstituteApplyDetail[] getDetailsByKey(String applyNumber);

    String getUserNameById(Long userid);

    List<SubstituteApply> sysList(String beginDate, String endDate, Long spotId, Long userId, Long robotId, Integer stat,String faultNumber);


    List<SubstituteApply> getSubstituteApplySystemExcel(Map<String, Object> search);


}
