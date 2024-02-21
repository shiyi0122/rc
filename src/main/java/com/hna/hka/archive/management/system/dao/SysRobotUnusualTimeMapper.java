package com.hna.hka.archive.management.system.dao;

import com.hna.hka.archive.management.system.model.SysRobotUnusualTime;
import com.hna.hka.archive.management.system.model.SysScenicSpot;

import java.util.List;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2023/3/2 15:05
 */
public interface SysRobotUnusualTimeMapper {
    List<SysRobotUnusualTime> getSysRobotUnusualTimeList(Map<String, String> search);


    int insert(SysRobotUnusualTime sysRobotUnusualTime);

    int update(SysRobotUnusualTime sysRobotUnusualTime);

    SysRobotUnusualTime getSpotIdByUnusualTime(Long scenicSpotId);

    int deleteByPrimaryKey(Long id);

    List<SysScenicSpot> getSysRobotUnusualTimeSpotList();


}
