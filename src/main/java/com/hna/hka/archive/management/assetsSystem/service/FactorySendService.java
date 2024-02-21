package com.hna.hka.archive.management.assetsSystem.service;

import com.hna.hka.archive.management.assetsSystem.model.FactorySend;
import com.hna.hka.archive.management.system.model.SysRobot;

import java.io.IOException;
import java.util.List;

/**
 * @program: rc
 * @description:
 * @author: zhaoxianglong
 * @create: 2021-09-29 10:44
 **/
public interface FactorySendService {
    List<FactorySend> list(Long spotId, String beginDate, String endDate, Long userId, Integer pageNum, Integer pageSize, Integer type, Integer form);

    Integer getCount(Long spotId, String beginDate, String endDate, Long userId, Integer type, Integer form);

    Integer add(FactorySend factorySend);

    String getPId(long applicantId);

    Integer edit(FactorySend factorySend) throws IOException;

    FactorySend detail(Long id);

    List factoryList();

    List spotList();

    List userList();

    List trailList(String robotCode);


    List spotAllList();

    List<FactorySend> list(Long spotId, String beginDate, String endDate, Long userId, Integer type, Integer form);

    int upType(FactorySend factorySend);


    List<SysRobot> getSpotIdByRobotList(String spotId);

}
