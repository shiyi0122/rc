package com.hna.hka.archive.management.appYXBSystem.dao;

import com.hna.hka.archive.management.appYXBSystem.model.SysGuideAppNews;
import com.hna.hka.archive.management.appYXBSystem.model.SysGuideAppUsers;
import com.hna.hka.archive.management.system.model.SysCurrentUserAccount;

import java.util.List;
import java.util.Map;

public interface SysGuideAppNewsMapper {

    List<SysGuideAppNews> getGuideAppNewsList(Map<String, String> search);

    int updateByPrimaryKeySelective(SysGuideAppNews sysGuideAppNews);

    int insertSelective(SysGuideAppNews sysGuideAppNews);

    int deleteByPrimaryKey(Long guideId);


    List<SysGuideAppUsers> getGuideAppNewsListAll();

}
