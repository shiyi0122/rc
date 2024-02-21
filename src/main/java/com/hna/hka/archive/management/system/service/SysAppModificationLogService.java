package com.hna.hka.archive.management.system.service;

import com.hna.hka.archive.management.system.util.PageDataResult;

import java.util.Map;

/**
 * @ProjectName: IDEA-CODE
 * @Package: com.hna.hka.archive.management.system.service
 * @ClassName: SysAppModificationLogService
 * @Author: 郭凯
 * @Description: APP操作日志业务层（接口）
 * @Date: 2020/9/10 13:26
 * @Version: 1.0
 */
public interface SysAppModificationLogService {

    PageDataResult getAppModificationLogList(Integer pageNum, Integer pageSize, Map<String, Object> search);
}
