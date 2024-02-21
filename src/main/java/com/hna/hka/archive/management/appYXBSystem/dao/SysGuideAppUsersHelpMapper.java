package com.hna.hka.archive.management.appYXBSystem.dao;

import com.hna.hka.archive.management.appYXBSystem.model.SysGuideAppUsers;
import com.hna.hka.archive.management.appYXBSystem.model.SysGuideAppUsersHelp;
import com.hna.hka.archive.management.system.model.SysUsers;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysGuideAppUsersHelpMapper {

    int deleteByPrimaryKey(Long helpId);

    int insert(SysGuideAppUsersHelp sysGuideAppUsersHelp);

    int insertSelective(SysGuideAppUsersHelp sysGuideAppUsersHelp);

    int updateByPrimaryKeySelective(SysGuideAppUsersHelp sysGuideAppUsersHelp);


    List<SysGuideAppUsersHelp> getGuideAppUsersHelpList(@Param("sysGuideAppUsersHelp")SysGuideAppUsersHelp sysGuideAppUsersHelp);

}
