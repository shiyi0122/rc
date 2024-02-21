package com.hna.hka.archive.management.system.dao;

import com.hna.hka.archive.management.system.model.SysCurrentBlackList;

public interface SysCurrentBlackListMapper {
    int deleteByPrimaryKey(Long blackListId);

    int insert(SysCurrentBlackList record);

    int insertSelective(SysCurrentBlackList record);

    SysCurrentBlackList selectByPrimaryKey(Long blackListId);

    int updateByPrimaryKeySelective(SysCurrentBlackList record);

    int updateByPrimaryKey(SysCurrentBlackList record);

    SysCurrentBlackList getBlacklistByUserId(Long userId);
}