package com.hna.hka.archive.management.appSystem.service;

import com.hna.hka.archive.management.appSystem.model.SysAppLoginLog;
import com.hna.hka.archive.management.system.model.SysScenicSpotBinding;

import java.util.List;

/**
 * @ProjectName: IDEA-CODE
 * @Package: com.hna.hka.archive.management.appSystem.service
 * @ClassName: SysAppLoginLogService
 * @Author: 郭凯
 * @Description:
 * @Date: 2020/11/23 13:46
 * @Version: 1.0
 */
public interface SysAppLoginLogService {

    void insertSysAppLoginLog(SysAppLoginLog sysLoginLog);

    List<SysScenicSpotBinding> selectbindingsList(String loginName);


}
