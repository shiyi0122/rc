package com.hna.hka.archive.management.appSystem.service;

import com.hna.hka.archive.management.managerApp.model.SysAppUsers;

import java.util.List;
import java.util.Map;

/**
 * @ProjectName: IDEA-CODE
 * @Package: com.hna.hka.archive.management.appSystem.service
 * @ClassName: AppUserService
 * @Author: 郭凯
 * @Description: APP用户管理业务层（接口）
 * @Date: 2020/11/23 13:40
 * @Version: 1.0
 */
public interface AppUserService {

    SysAppUsers getSysUserByLoginName(String loginName);

    void updateAppUserTokenId(SysAppUsers sysAppUsers);

    SysAppUsers getSysUserBylonginTokenId(String longinTokenId);

    int updateUserClientGtId(SysAppUsers sysAppUsers);

    List<SysAppUsers> getAppUsersByScenicIdList(Map<String,Object> search);

    int loginOut(String longinTokenId);


    int preservePushId(Long userId, String userClientGtId);

}
