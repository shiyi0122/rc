package com.hna.hka.archive.management.system.dao;

import com.hna.hka.archive.management.system.model.SysRobotCorpusAudioAndVideo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface SysRobotCorpusAudioAndVideoMapper {
    int deleteByPrimaryKey(Long mediaId);

    int insert(SysRobotCorpusAudioAndVideo record);

    int insertSelective(SysRobotCorpusAudioAndVideo record);

    SysRobotCorpusAudioAndVideo selectByPrimaryKey(Long mediaId);

    int updateByPrimaryKeySelective(SysRobotCorpusAudioAndVideo record);

    int updateByPrimaryKey(SysRobotCorpusAudioAndVideo record);

    List<SysRobotCorpusAudioAndVideo> getCorpusAudioAndVideoList(Map<String, String> search);

}