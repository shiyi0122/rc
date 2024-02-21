package com.hna.hka.archive.management.system.dao;

import com.hna.hka.archive.management.system.model.SysScenicSpotRandomProblem;
import com.hna.hka.archive.management.system.model.SysScenicSpotRandomProblemWithBLOBs;

import java.util.List;
import java.util.Map;

public interface SysScenicSpotRandomProblemMapper {
    int deleteByPrimaryKey(Long problemId);

    int insert(SysScenicSpotRandomProblemWithBLOBs record);

    int insertSelective(SysScenicSpotRandomProblemWithBLOBs record);

    SysScenicSpotRandomProblemWithBLOBs selectByPrimaryKey(Long problemId);

    int updateByPrimaryKeySelective(SysScenicSpotRandomProblemWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(SysScenicSpotRandomProblemWithBLOBs record);

    int updateByPrimaryKey(SysScenicSpotRandomProblem record);

    List<SysScenicSpotRandomProblemWithBLOBs> getRandomProblemList(Map<String, String> search);
}