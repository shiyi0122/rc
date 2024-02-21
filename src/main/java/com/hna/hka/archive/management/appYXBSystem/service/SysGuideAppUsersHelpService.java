package com.hna.hka.archive.management.appYXBSystem.service;

import com.hna.hka.archive.management.appYXBSystem.model.SysGuideAppUsersHelp;
import com.hna.hka.archive.management.system.util.PageDataResult;

public interface SysGuideAppUsersHelpService {

   int addGuideAppUsersHelp(SysGuideAppUsersHelp sysGuideAppUsersHelp);

    int editGuideAppUsersHelp(SysGuideAppUsersHelp sysGuideAppUsersHelp);

    int delGuideAppUsersHelp(Long helpId);

    PageDataResult getGuideAppUsersHelpList(Integer pageNum, Integer pageSize, SysGuideAppUsersHelp sysGuideAppUsersHelp);
}
