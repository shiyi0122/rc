package com.hna.hka.archive.management.appYXBSystem.service;

import com.hna.hka.archive.management.appYXBSystem.model.SysGuideAppNews;
import com.hna.hka.archive.management.system.util.PageDataResult;

import java.util.Map;

public interface SysGuideAppNewsService {
    PageDataResult getGuideAppNewsList(Integer pageNum, Integer pageSize, Map<String, String> search);

    int editGuideAppNews(SysGuideAppNews sysGuideAppNews);

    int delGuideAppNews(Long guideId);

    int addGuideAppNews(SysGuideAppNews sysGuideAppNews);

}
