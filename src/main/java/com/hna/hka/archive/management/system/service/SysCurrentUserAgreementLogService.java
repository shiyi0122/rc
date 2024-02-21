package com.hna.hka.archive.management.system.service;

import com.hna.hka.archive.management.system.util.PageDataResult;

import java.util.Map;

/**
 * @ProjectName: IDEA-CODE
 * @Package: com.hna.hka.archive.management.system.service
 * @ClassName: SysCurrentUserAgreementLogService
 * @Author: 郭凯
 * @Description: 用户协议签订日志业务层（接口）
 * @Date: 2020/9/9 16:58
 * @Version: 1.0
 */
public interface SysCurrentUserAgreementLogService {

    PageDataResult getCurrentUserAgreementLogList(Integer pageNum, Integer pageSize, Map<String, String> search);
}
