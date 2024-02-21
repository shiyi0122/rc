package com.hna.hka.archive.management.assetsSystem.dao;

import com.hna.hka.archive.management.assetsSystem.model.FactorySend;

import java.util.List;

/**
 * @program: rc
 * @description:
 * @author: zhaoxianglong
 * @create: 2021-09-29 10:45
 **/
public interface FactorySendMapper {
    List<FactorySend> list(Long spotId, String beginDate, String endDate, Long userId, Integer pageNum, Integer pageSize, Integer type, Integer form);

    Integer getCount(Long spotId, String beginDate, String endDate, Long userId, Integer type, Integer form);

    Integer add(FactorySend factorySend);

    String getPId(long applicantId);

    Integer edit(FactorySend factorySend);

    FactorySend findByKey(Long id);

    List factoryList();

    List spotList();

    List userList();

    List trailList(String robotCode);

    List spotAllList();

    List<FactorySend> lists(Long spotId, String beginDate, String endDate, Long userId, Integer type, Integer form);



}
