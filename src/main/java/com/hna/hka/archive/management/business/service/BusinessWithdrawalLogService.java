package com.hna.hka.archive.management.business.service;

import com.hna.hka.archive.management.business.model.BusinessWithdrawalLog;
import com.hna.hka.archive.management.system.util.PageDataResult;

import java.util.List;
import java.util.Map;

/**
 * @ProjectName: IDEA-CODE
 * @Package: com.hna.hka.archive.management.business.service
 * @ClassName: BusinessWithdrawalLogService
 * @Author: 郭凯
 * @Description: 提现申请管理业务层（接口）
 * @Date: 2020/8/13 10:40
 * @Version: 1.0
 */
public interface BusinessWithdrawalLogService {

    PageDataResult getWithdrawalLogList(Integer pageNum, Integer pageSize, Map<String, String> search);

    int editAdopt(BusinessWithdrawalLog businessWithdrawalLog);

    List<BusinessWithdrawalLog> getWithdrawalLogExcel(Map<String, String> search);
}
