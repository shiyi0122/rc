package com.hna.hka.archive.management.system.service;

import com.hna.hka.archive.management.system.model.SysCurrentUserAccount;
import com.hna.hka.archive.management.system.model.SysLogWithBLOBs;
import com.hna.hka.archive.management.system.util.PageDataResult;

import java.util.List;
import java.util.Map;

/**
 * @ProjectName: IDEA-CODE
 * @Package: com.hna.hka.archive.management.system.service
 * @ClassName: SysCurrentUserAccountService
 * @Author: 郭凯
 * @Description: 储值管理业务层（接口）
 * @Date: 2020/6/23 16:28
 * @Version: 1.0
 */
public interface SysCurrentUserAccountService {

    PageDataResult getCurrentUserAccountList(Integer pageNum, Integer pageSize, Map<String, String> search);

    List<SysCurrentUserAccount> getUploadExcelCurrentUserAccount(Map<String, String> search);

    int editCurrentUserAccount(SysCurrentUserAccount sysCurrentUserAccount);

    SysCurrentUserAccount selectAccountByUserId(Long userId);

    SysCurrentUserAccount getSysCurrentUserAccountById(Long accountId);

    void addSysLog(SysLogWithBLOBs sysLog);
}
