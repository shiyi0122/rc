package com.hna.hka.archive.management.assetsSystem.dao;

import com.hna.hka.archive.management.assetsSystem.model.SysCurrentUserFeedback;

import java.util.List;
import java.util.Map;

public interface SysCurrentUserFeedbackMapper {
    int deleteByPrimaryKey(Long feedbackId);

    int insert(SysCurrentUserFeedback record);

    int insertSelective(SysCurrentUserFeedback record);

    SysCurrentUserFeedback selectByPrimaryKey(Long feedbackId);

    int updateByPrimaryKeySelective(SysCurrentUserFeedback record);

    int updateByPrimaryKey(SysCurrentUserFeedback record);

    List<SysCurrentUserFeedback> getRobotDamagesAppList(Map<String, String> search);
}