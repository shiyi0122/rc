package com.hna.hka.archive.management.system.dao;

import java.util.List;
import java.util.Map;

import com.hna.hka.archive.management.system.model.SysRobotAudioAndVideo;

public interface SysRobotAudioAndVideoMapper {
    int deleteByPrimaryKey(Long mediaId);

    int insert(SysRobotAudioAndVideo record);

    int insertSelective(SysRobotAudioAndVideo record);

    SysRobotAudioAndVideo selectByPrimaryKey(Long mediaId);

    int updateByPrimaryKeySelective(SysRobotAudioAndVideo record);

    int updateByPrimaryKey(SysRobotAudioAndVideo record);

	List<SysRobotAudioAndVideo> getSemanticResources();

	List<SysRobotAudioAndVideo> getRobotAudioAndVideoList(Map<String, Object> search);
}