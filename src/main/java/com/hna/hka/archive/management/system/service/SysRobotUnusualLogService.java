package com.hna.hka.archive.management.system.service;

import com.hna.hka.archive.management.assetsSystem.model.SysRobotErrorRecords;
import com.hna.hka.archive.management.system.model.SysRobotUnusualLog;
import com.hna.hka.archive.management.system.model.SysRobotUnusualTime;
import com.hna.hka.archive.management.system.model.SysUsers;
import com.hna.hka.archive.management.system.util.PageDataResult;

import java.util.List;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2023/3/3 10:14
 */
public interface SysRobotUnusualLogService {


    int addSysRobotUnusualLog(SysRobotUnusualLog sysRobotUnusualLog);


    PageDataResult getSysRobotUnusualLogList(Integer pageNum, Integer pageSize, Map<String, Object> search);

    int editSysRobotUnusualLog(SysRobotUnusualLog sysRobotUnusualLog);

    PageDataResult getAppSysRobotUnusualLogList(Integer pageNum, Integer pageSize, Map<String, Object> search);

    int editSysRobotUnusualLogState(String id, String state, Long userId);

    void timingRobotSpotUnusualLog();

    void timingScenicSpotSaturationLog();

    Boolean ifSysRobotBadge();

    int oneClickProcessing(SysUsers sysUser, Long scenicSpotId,String startTime,String endTime);


    void timingRobotSpotOrderLog();


}

