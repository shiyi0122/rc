package com.hna.hka.archive.management.system.dao;

import com.hna.hka.archive.management.system.model.SysRobotCorpus;
import com.hna.hka.archive.management.system.model.SysRobotCorpusWithBLOBs;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface SysRobotCorpusMapper {
    int deleteByPrimaryKey(Long corpusId);

    int insert(SysRobotCorpusWithBLOBs record);

    int insertSelective(SysRobotCorpusWithBLOBs record);

    SysRobotCorpusWithBLOBs selectByPrimaryKey(Long corpusId);

    int updateByPrimaryKeySelective(SysRobotCorpusWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(SysRobotCorpusWithBLOBs record);

    int updateByPrimaryKey(SysRobotCorpus record);

    List<SysRobotCorpus> getSemanticsList(Map<String, String> search);
}