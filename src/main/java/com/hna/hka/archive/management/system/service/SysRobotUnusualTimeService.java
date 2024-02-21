package com.hna.hka.archive.management.system.service;

import com.hna.hka.archive.management.system.model.SysRobotUnusualTime;
import com.hna.hka.archive.management.system.model.SysScenicSpot;
import com.hna.hka.archive.management.system.util.PageDataResult;

import java.util.List;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2023/3/2 15:05
 */
public interface SysRobotUnusualTimeService {

    PageDataResult getSysRobotUnusualTimeList(Integer pageNum, Integer pageSize, Map<String, String> search);

    int addSysRobotUnusualTime(SysRobotUnusualTime sysRobotUnusualTime);


    int editSysRobotUnusualTime(SysRobotUnusualTime sysRobotUnusualTime);


    SysRobotUnusualTime getUnusualTime(String robotCode);

    int delSysRobotUnusualTime(SysRobotUnusualTime sysRobotUnusualTime);

    List<SysScenicSpot> getUnusualSpotList();


}
