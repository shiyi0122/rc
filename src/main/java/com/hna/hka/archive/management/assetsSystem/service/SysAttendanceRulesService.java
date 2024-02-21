package com.hna.hka.archive.management.assetsSystem.service;

import com.hna.hka.archive.management.system.util.PageDataResult;

import java.util.Map;

/**
 * @ProjectName: rc
 * @Package: com.hna.hka.archive.management.assetsSystem.service
 * @ClassName: SysAttendanceRulesService
 * @Author: 郭凯
 * @Description: 考勤规则业务层（接口）
 * @Date: 2021/6/3 11:10
 * @Version: 1.0
 */
public interface SysAttendanceRulesService {

    PageDataResult getAttendanceRulesList(Integer pageNum, Integer pageSize, Map<String, Object> search);
}
