package com.hna.hka.archive.management.system.service;

import com.hna.hka.archive.management.system.model.SysRobotSpeechProblems;
import com.hna.hka.archive.management.system.util.PageDataResult;

import java.util.List;
import java.util.Map;

/**
 * @ProjectName: IDEA-CODE
 * @Package: com.hna.hka.archive.management.system.service
 * @ClassName: SysRobotSpeechProblemsService
 * @Author: 郭凯
 * @Description: 问题汇总业务层（接口）
 * @Date: 2020/7/24 16:26
 * @Version: 1.0
 */
public interface SysRobotSpeechProblemsService {

    PageDataResult getSpeechProblemsList(Integer pageNum, Integer pageSize, Map<String, String> search);

    List<SysRobotSpeechProblems> getSpeechProblemsExcel(Map<String,String> search);

    int editSpeechProblemsHandleState(SysRobotSpeechProblems sysRobotSpeechProblems);
}
