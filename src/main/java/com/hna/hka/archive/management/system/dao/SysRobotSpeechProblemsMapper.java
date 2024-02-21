package com.hna.hka.archive.management.system.dao;

import com.hna.hka.archive.management.system.model.SysRobotSpeechProblems;

import java.util.List;
import java.util.Map;

public interface SysRobotSpeechProblemsMapper {
    int deleteByPrimaryKey(Long speechProblemsId);

    int insert(SysRobotSpeechProblems record);

    int insertSelective(SysRobotSpeechProblems record);

    SysRobotSpeechProblems selectByPrimaryKey(Long speechProblemsId);

    int updateByPrimaryKeySelective(SysRobotSpeechProblems record);

    int updateByPrimaryKey(SysRobotSpeechProblems record);

    List<SysRobotSpeechProblems> getSpeechProblemsList(Map<String, String> search);
}