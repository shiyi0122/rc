package com.hna.hka.archive.management.system.service;

import com.hna.hka.archive.management.system.util.PageDataResult;

import java.util.Map;

/**
 * @ProjectName: IDEA-CODE
 * @Package: com.hna.hka.archive.management.system.service
 * @ClassName: SysDepositRefundLogService
 * @Author: 郭凯
 * @Description: 押金扣款业务层（接口）
 * @Date: 2020/9/9 15:19
 * @Version: 1.0
 */
public interface SysDepositRefundLogService {

    PageDataResult getDepositRefundLogList(Integer pageNum, Integer pageSize, Map<String, String> search);
}
