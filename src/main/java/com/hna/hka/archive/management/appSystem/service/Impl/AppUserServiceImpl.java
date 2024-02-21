package com.hna.hka.archive.management.appSystem.service.Impl;

import com.hna.hka.archive.management.appSystem.service.AppUserService;
import com.hna.hka.archive.management.managerApp.dao.SysAppUsersMapper;
import com.hna.hka.archive.management.managerApp.model.SysAppUsers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @ProjectName: IDEA-CODE
 * @Package: com.hna.hka.archive.management.appSystem.service.Impl
 * @ClassName: AppUserServiceImpl
 * @Author: 郭凯
 * @Description: APP用户管理（业务）
 * @Date: 2020/11/23 13:40
 * @Version: 1.0
 */
@Service
public class AppUserServiceImpl implements AppUserService {

    @Autowired
    private SysAppUsersMapper sysAppUsersMapper;

    /**
     * @Author 郭凯
     * @Description 根据用户名查询当前登录用户
     * @Date 13:42 2020/11/23
     * @Param [loginName]
     * @return com.hna.hka.archive.management.managerApp.model.SysAppUsers
    **/
    @Override
    public SysAppUsers getSysUserByLoginName(String loginName) {
        return sysAppUsersMapper.getSysAppUserByLoginName(loginName);
    }

    /**
     * @Author 郭凯
     * @Description 更新tokenId
     * @Date 13:44 2020/11/23
     * @Param [sysAppUsers]
     * @return void
    **/
    @Override
    public void updateAppUserTokenId(SysAppUsers sysAppUsers) {
        sysAppUsersMapper.updateByPrimaryKeySelective(sysAppUsers);
    }

    /**
     * @Author 郭凯
     * @Description 查询longinTokenId，是否一致
     * @Date 13:51 2020/11/23
     * @Param [longinTokenId]
     * @return com.hna.hka.archive.management.managerApp.model.SysAppUsers
    **/
    @Override
    public SysAppUsers getSysUserBylonginTokenId(String longinTokenId) {
        return sysAppUsersMapper.getSysUserBylonginTokenId(longinTokenId);
    }

    /**
     * @Author 郭凯
     * @Description 更新userClientGtId
     * @Date 17:50 2020/11/23
     * @Param [sysAppUsers]
     * @return int
    **/
    @Override
    public int updateUserClientGtId(SysAppUsers sysAppUsers) {
        return sysAppUsersMapper.updateByPrimaryKeySelective(sysAppUsers);
    }

    /**
     * @Method getAppUsersByScenicIdList
     * @Author 郭凯
     * @Version  1.0
     * @Description 查询景区园长
     * @Return java.util.List<com.hna.hka.archive.management.managerApp.model.SysAppUsers>
     * @Date 2021/7/13 17:22
     */
    @Override
    public List<SysAppUsers> getAppUsersByScenicIdList(Map<String,Object> search) {
        List<SysAppUsers> appUsersList = sysAppUsersMapper.getAppUsersByScenicIdList(search);
        return appUsersList;
    }

    /**
     * 退出登录
     * @param longinTokenId
     * @return
     */
    @Override
    public int loginOut(String longinTokenId) {

        SysAppUsers userBylonginTokenId = sysAppUsersMapper.getSysUserBylonginTokenId(longinTokenId);

        return 1;

    }

    /**
     * 登录成功后保存推送id
     * @param userId
     * @param userClientGtId
     * @return
     */
    @Override
    public int preservePushId(Long userId, String userClientGtId) {

        SysAppUsers sysAppUsers = sysAppUsersMapper.selectByPrimaryKey(userId);
        sysAppUsers.setUserClientGtId(userClientGtId);
        int i = sysAppUsersMapper.updateByPrimaryKeySelective(sysAppUsers);
        return i;

    }
}
