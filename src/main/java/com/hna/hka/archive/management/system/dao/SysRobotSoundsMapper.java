package com.hna.hka.archive.management.system.dao;

import com.hna.hka.archive.management.system.model.SysRobotSounds;

import java.util.List;
import java.util.Map;

public interface SysRobotSoundsMapper {
    int deleteByPrimaryKey(Long soundsId);

    int insert(SysRobotSounds record);

    int insertSelective(SysRobotSounds record);

    SysRobotSounds selectByPrimaryKey(Long soundsId);

    int updateByPrimaryKeySelective(SysRobotSounds record);

    int updateByPrimaryKey(SysRobotSounds record);

    List<SysRobotSounds> getRobotSoundsList(Map<String, String> search);
}