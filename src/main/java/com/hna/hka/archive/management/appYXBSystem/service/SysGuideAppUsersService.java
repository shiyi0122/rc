package com.hna.hka.archive.management.appYXBSystem.service;

import com.hna.hka.archive.management.appYXBSystem.model.SysGuideAppUsers;
import com.hna.hka.archive.management.appYXBSystem.model.UserOperationLog;
import com.hna.hka.archive.management.appYXBSystem.model.UserOperationLogDTO;
import com.hna.hka.archive.management.system.util.PageDataResult;

import java.util.List;

public interface SysGuideAppUsersService {

    int addGuideAppUsers(SysGuideAppUsers sysGuideAppUsers);

    int editGuideAppUsers(SysGuideAppUsers sysGuideAppUsers);

    int delGuideAppUsers(Long userId);

    PageDataResult getGuideAppUsersList(Integer pageNum, Integer pageSize, SysGuideAppUsers sysGuideAppUsers);

    List<SysGuideAppUsers> getGuideAppUsersPhone();


    List<String> getGuideAppUsersByClientGtId();


    List<UserOperationLog> getUserOperation(UserOperationLogDTO userOperationLogDTO);
}
