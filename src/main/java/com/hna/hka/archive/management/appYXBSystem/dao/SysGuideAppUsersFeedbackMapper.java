package com.hna.hka.archive.management.appYXBSystem.dao;

import com.hna.hka.archive.management.appYXBSystem.model.SysGuideAppUsers;
import com.hna.hka.archive.management.appYXBSystem.model.SysGuideAppUsersFeedback;
import com.hna.hka.archive.management.system.model.SysUsers;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysGuideAppUsersFeedbackMapper {

    int deleteByPrimaryKey(Long appUserId);

    int insert(SysGuideAppUsersFeedback sysGuideAppUsersFeedback);

    int insertSelective(SysGuideAppUsersFeedback sysGuideAppUsersFeedback);

    int updateByPrimaryKeySelective(SysGuideAppUsersFeedback sysGuideAppUsersFeedback);

    List<SysGuideAppUsersFeedback> getGuideAppUsersFeedbackList(@Param("sysGuideAppUsersFeedback") SysGuideAppUsersFeedback sysGuideAppUsersFeedback);

}
