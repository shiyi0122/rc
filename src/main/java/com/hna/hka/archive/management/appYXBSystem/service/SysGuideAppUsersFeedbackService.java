package com.hna.hka.archive.management.appYXBSystem.service;

import com.hna.hka.archive.management.appYXBSystem.model.SysGuideAppUsersFeedback;
import com.hna.hka.archive.management.system.util.PageDataResult;

public interface SysGuideAppUsersFeedbackService {

    int addGuideAppUsersFeedback(SysGuideAppUsersFeedback sysGuideAppUsersFeedback);

    int editGuideAppUsersFeedback(SysGuideAppUsersFeedback sysGuideAppUsersFeedback);

    int delGuideAppUsersFeedback(Long appUserId);

    PageDataResult getGuideAppUsersFeedbackList(Integer pageNum, Integer pageSize, SysGuideAppUsersFeedback sysGuideAppUsersFeedback);

}
