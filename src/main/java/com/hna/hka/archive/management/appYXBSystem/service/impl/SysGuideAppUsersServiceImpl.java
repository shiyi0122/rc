package com.hna.hka.archive.management.appYXBSystem.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hna.hka.archive.management.appYXBSystem.dao.SysGuideAppUsersMapper;
import com.hna.hka.archive.management.appYXBSystem.model.SysGuideAppUsers;
import com.hna.hka.archive.management.appYXBSystem.model.UserOperationLog;
import com.hna.hka.archive.management.appYXBSystem.model.UserOperationLogDTO;
import com.hna.hka.archive.management.appYXBSystem.service.SysGuideAppNewsService;
import com.hna.hka.archive.management.appYXBSystem.service.SysGuideAppUsersService;
import com.hna.hka.archive.management.system.model.SysUsers;
import com.hna.hka.archive.management.system.util.DateUtil;
import com.hna.hka.archive.management.system.util.IdUtils;
import com.hna.hka.archive.management.system.util.PageDataResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysGuideAppUsersServiceImpl implements SysGuideAppUsersService {

    @Autowired
    SysGuideAppUsersMapper sysGuideAppUsersMapper;

    /**
     * 添加用户
     * @param sysGuideAppUsers
     * @return
     */
    @Override
    public int addGuideAppUsers(SysGuideAppUsers sysGuideAppUsers) {

        SysGuideAppUsers appUsers= sysGuideAppUsersMapper.selectAppUsersByPhone(sysGuideAppUsers.getUserPhone());
        if (appUsers != null){
            return 2;
        }
        sysGuideAppUsers.setUserId(IdUtils.getSeqId());
        sysGuideAppUsers.setCreateDate(DateUtil.currentDateTime());
        int i = sysGuideAppUsersMapper.insertSelective(sysGuideAppUsers);
        return i;
    }

    /**
     * 修改用户
     * @param sysGuideAppUsers
     * @return
     */
    @Override
    public int editGuideAppUsers(SysGuideAppUsers sysGuideAppUsers) {

        sysGuideAppUsers.setUpdateDate(DateUtil.currentDateTime());
        return sysGuideAppUsersMapper.updateByPrimaryKeySelective(sysGuideAppUsers);
    }

    /**
     * 删除用户
     * @param userId
     * @return
     */
    @Override
    public int delGuideAppUsers(Long userId) {
        return  sysGuideAppUsersMapper.deleteByPrimaryKey(userId);
    }

    /**
     * 用户列表
     * @param pageNum
     * @param pageSize
     * @param sysGuideAppUsers
     * @return
     */
    @Override
    public PageDataResult getGuideAppUsersList(Integer pageNum, Integer pageSize, SysGuideAppUsers sysGuideAppUsers) {
        PageDataResult pageDataResult = new PageDataResult();
        PageHelper.startPage(pageNum, pageSize);
        List<SysGuideAppUsers> appUsersList = sysGuideAppUsersMapper.getGuideAppUsersList(sysGuideAppUsers);
        if(appUsersList.size() != 0){
            PageInfo<SysGuideAppUsers> pageInfo = new PageInfo<>(appUsersList);
            pageDataResult.setList(pageInfo.getList());
            pageDataResult.setTotals((int) pageInfo.getTotal());
        }

        return pageDataResult;
    }

    /**
     * 获取用户手机号
     * @return
     */
    @Override
    public List<SysGuideAppUsers> getGuideAppUsersPhone() {
        List<SysGuideAppUsers> list = sysGuideAppUsersMapper.selectAppPhone();
        return list;
    }

    /**
     * 获取所有用户推送别名
     * @return
     */
    @Override
    public List<String> getGuideAppUsersByClientGtId() {

        List<String> list =  sysGuideAppUsersMapper.selectAppUsersByClientGtId();
        return list;
    }

    @Override
    public List<UserOperationLog> getUserOperation(UserOperationLogDTO userOperationLogDTO) {
        List<UserOperationLog> userOperationLogs = sysGuideAppUsersMapper.selectByUser(userOperationLogDTO);
        //安装installNumber
        int install = sysGuideAppUsersMapper.selectInstall(userOperationLogDTO);
        //注册
        int byregister = sysGuideAppUsersMapper.selectByregister(userOperationLogDTO);
        //打开
        int open = sysGuideAppUsersMapper.selectByOpen(userOperationLogDTO);
        int AllNumber = install + byregister + open;
        userOperationLogs.get(0).setAllNumber(String.valueOf(AllNumber));
        userOperationLogs.get(0).setInstallNumber(String.valueOf(install));
        userOperationLogs.get(0).setRegisterNumber(String.valueOf(byregister));
        userOperationLogs.get(0).setOpenNumber(String.valueOf(open));
        return userOperationLogs;
    }
}
