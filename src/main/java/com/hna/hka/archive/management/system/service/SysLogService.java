package com.hna.hka.archive.management.system.service;

import com.hna.hka.archive.management.system.util.PageDataResult;

import java.util.Map;

/**
 * @ProjectName: IDEA-CODE
 * @Package: com.hna.hka.archive.management.system.service
 * @ClassName: SysLogService
 * @Author: 郭凯
 * @Description: 储值金额修改日志业务层（接口）
 * @Date: 2020/12/2 9:44
 * @Version: 1.0
 */
public interface SysLogService {

    PageDataResult getStoredValueAmountLogList(Integer pageNum, Integer pageSize, Map<String, String> search);
}
