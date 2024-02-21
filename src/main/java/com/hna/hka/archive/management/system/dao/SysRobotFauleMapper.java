package com.hna.hka.archive.management.system.dao;

import com.hna.hka.archive.management.system.model.SysRobotFaule;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface SysRobotFauleMapper {
    int deleteByPrimaryKey(Long robotAlarmId);

    int insert(SysRobotFaule record);

    int insertSelective(SysRobotFaule record);

    SysRobotFaule selectByPrimaryKey(Long robotAlarmId);

    int updateByPrimaryKeySelective(SysRobotFaule record);

    int updateByPrimaryKey(SysRobotFaule record);

    /**
     * 机器人报警日志列表查询
     * @param search
     * @return
     */
    List<SysRobotFaule> getOperationLogRobotAlarmList(Map<String,Object> search);

    List<SysRobotFaule> getRobotFauleList(Map<String, String> search);
}