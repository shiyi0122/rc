package com.hna.hka.archive.management.system.dao;

import com.hna.hka.archive.management.system.model.SysRobotUnusualLog;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2023/3/3 10:20
 */
public interface SysRobotUnusualLogMapper {

    int insert(SysRobotUnusualLog sysRobotUnusualLog);


    List<SysRobotUnusualLog> list(Map<String, Object> search);

    int update(SysRobotUnusualLog sysRobotUnusualLog);

    SysRobotUnusualLog selectById(String id);

    List<SysRobotUnusualLog> backstageUnusualList(Map<String, Object> search);

    SysRobotUnusualLog getRobotCodeByUnusualLog(String robotCode,String type);

    List<SysRobotUnusualLog> listApp(Map<String, Object> search);


    SysRobotUnusualLog getRobotCodeByUnusualLogN(String robotCode);

    Long getTotalNumber();


    int oneClickProcessing(@Param("userId")Long userId,@Param("scenicSpotId")Long scenicSpotId,@Param("startTime") String startTime,@Param("endTime") String endTime, @Param("time") String time);


}
