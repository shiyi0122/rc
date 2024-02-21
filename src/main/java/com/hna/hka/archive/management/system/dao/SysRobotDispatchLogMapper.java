package com.hna.hka.archive.management.system.dao;

import com.hna.hka.archive.management.system.model.SysRobotDispatchLog;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface SysRobotDispatchLogMapper {
    int deleteByPrimaryKey(Long robotDispatchId);

    int insert(SysRobotDispatchLog record);

    int insertSelective(SysRobotDispatchLog record);

    SysRobotDispatchLog selectByPrimaryKey(Long robotDispatchId);

    int updateByPrimaryKeySelective(SysRobotDispatchLog record);

    int updateByPrimaryKey(SysRobotDispatchLog record);

    /**
     * 机器人调度日志列表查询
     * @param search
     * @return
     */
    List<SysRobotDispatchLog> getOperationLogRobotAlarmList(Map<String, Object> search);

    /**
     * 机器人调度日志列表查询（添加了景区查询）
     * @param search
     * @return
     */
    List<SysRobotDispatchLog> getOperationLogRobotAlarmListNew(Map<String, Object> search);

    List<String> getRobotDispatchLogDate(String scenicSpotName, String startDate, String endDate);

    Integer getRobotDispatchLogInCount(String scenicSpotName, String date);

    Integer getRobotDispatchLogOutCount(String scenicSpotName, String date);

}