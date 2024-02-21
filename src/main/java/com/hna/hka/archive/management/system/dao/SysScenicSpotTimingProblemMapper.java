package com.hna.hka.archive.management.system.dao;

import com.hna.hka.archive.management.system.model.SysScenicSpotRandomProblem;
import com.hna.hka.archive.management.system.model.SysScenicSpotRandomProblemWithBLOBs;
import com.hna.hka.archive.management.system.model.SysScenicSpotTimingProblem;
import com.hna.hka.archive.management.system.model.SysScenicSpotTimingProblemWithBLOBs;

import java.util.List;
import java.util.Map;

public interface SysScenicSpotTimingProblemMapper {
    int deleteByPrimaryKey(Long problemId);

    int insert(SysScenicSpotTimingProblemWithBLOBs record);

    int insertSelective(SysScenicSpotTimingProblemWithBLOBs record);

    SysScenicSpotTimingProblemWithBLOBs selectByPrimaryKey(Long problemId);

    int updateByPrimaryKeySelective(SysScenicSpotTimingProblemWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(SysScenicSpotTimingProblemWithBLOBs record);

    int updateByPrimaryKey(SysScenicSpotTimingProblem record);

    List<SysScenicSpotTimingProblemWithBLOBs> getTimingProblemList(Map<String, String> search);
}