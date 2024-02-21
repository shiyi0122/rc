package com.hna.hka.archive.management.system.dao;

import com.hna.hka.archive.management.system.model.SysRobotCorpusMediaExtend;

import java.util.List;
import java.util.Map;

public interface SysRobotCorpusMediaExtendMapper {
    int deleteByPrimaryKey(Long mediaExtendId);

    int insert(SysRobotCorpusMediaExtend record);

    int insertSelective(SysRobotCorpusMediaExtend record);

    SysRobotCorpusMediaExtend selectByPrimaryKey(Long mediaExtendId);

    int updateByPrimaryKeySelective(SysRobotCorpusMediaExtend record);

    int updateByPrimaryKey(SysRobotCorpusMediaExtend record);

    List<SysRobotCorpusMediaExtend> getViewDetailsList(Map<String, String> search);
}