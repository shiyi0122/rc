package com.hna.hka.archive.management.system.dao;

import com.hna.hka.archive.management.system.model.SysRobot;
import com.hna.hka.archive.management.system.model.SysRobotId;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SysRobotIdMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SysRobotId record);

    int insertSelective(SysRobotId record);

    SysRobotId selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysRobotId record);

    int updateByPrimaryKey(SysRobotId record);

    /**
     * 查询机器人ID
     * @return
     */
    SysRobotId getNewSysRobotId();


    SysRobot getByCode(String robotCode);
}
