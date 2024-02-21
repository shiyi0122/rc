package com.hna.hka.archive.management.appSystem.service;

import com.github.pagehelper.PageInfo;
import com.hna.hka.archive.management.appSystem.model.SubstituteApply;

import java.util.List;
import java.util.Map;

/**
 * @program: rc
 * @description:
 * @author: zhaoxianglong
 * @create: 2021-11-03 10:03
 **/
public interface SubstituteApplyService {
    PageInfo<SubstituteApply> list(String beginDate, String endDate, Long spotId, Long userId, Integer stat, Integer pageNum, Integer pageSize);

    void add(SubstituteApply substituteApply) throws Exception;

    void edit(SubstituteApply substituteApply) throws Exception;

    void updateStat(String applyNumber , Long stat, Long userid, String aggestion, Long factoryId) throws Exception;

    PageInfo<SubstituteApply> sysList(String beginDate, String endDate, Long spotId, Long userId, Long robotId,Integer stat, String faultNumber,Integer pageNum, Integer pageSize);


    PageInfo<SubstituteApply> editSubstituteApply(SubstituteApply substituteApply);

    List<SubstituteApply> getSubstituteApplySystemExcel(Map<String, Object> search);

}
