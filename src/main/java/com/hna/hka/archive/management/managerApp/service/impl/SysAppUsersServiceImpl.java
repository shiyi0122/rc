package com.hna.hka.archive.management.managerApp.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hna.hka.archive.management.managerApp.dao.SysAppUsersMapper;
import com.hna.hka.archive.management.managerApp.dao.SysAppUsersSpotRoleMapper;
import com.hna.hka.archive.management.managerApp.model.SysAppUsers;
import com.hna.hka.archive.management.managerApp.model.SysAppUsersSpotRole;
import com.hna.hka.archive.management.managerApp.service.SysAppUsersService;
import com.hna.hka.archive.management.system.util.*;
import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @ProjectName: IDEA-CODE
 * @Package: com.hna.hka.archive.management.managerApp.service.impl
 * @ClassName: SysAppUsersServiceImpl
 * @Author: 郭凯
 * @Description: 管理者APP用户管理业务层（实现）
 * @Date: 2020/11/3 9:54
 * @Version: 1.0
 */
@Service
@Transactional
public class SysAppUsersServiceImpl implements SysAppUsersService {

    @Autowired
    private SysAppUsersMapper sysAppUsersMapper;

    @Autowired
    private SysAppUsersSpotRoleMapper sysAppUsersSpotRoleMapper;


    /**
     * @Author 郭凯
     * @Description 管理者APP用户管理列表查询
     * @Date 10:04 2020/11/3
     * @Param [pageNum, pageSize, search]
     * @return com.hna.hka.archive.management.system.util.PageDataResult
    **/
    @Override
    public PageDataResult getAppUsersList(Integer pageNum, Integer pageSize, Map<String, String> search) {
        PageDataResult pageDataResult = new PageDataResult();
        PageHelper.startPage(pageNum, pageSize);
        List<SysAppUsers> sysAppUsersList = sysAppUsersMapper.getAppUsersList(search);
        if (sysAppUsersList.size() != 0){
            PageInfo<SysAppUsers> pageInfo = new PageInfo<>(sysAppUsersList);
            pageDataResult.setList(pageInfo.getList());
            pageDataResult.setTotals((int) pageInfo.getTotal());
        }
        return pageDataResult;
    }

    /**
     * @Author 郭凯
     * @Description APP用户修改
     * @Date 10:57 2020/11/3
     * @Param [sysAppUsers]
     * @return int
    **/
    @Override
    public int editAppUsers(SysAppUsers sysAppUsers) {
        sysAppUsers.setUpdateDate(DateUtil.currentDateTime());
        return sysAppUsersMapper.updateByPrimaryKeySelective(sysAppUsers);
    }

    @Override
    /**
     * @Author 郭凯
     * @Description APP用户新增
     * @Date 13:46 2020/11/3
     * @Param [sysAppUsers]
     * @return int
    **/
    public int addAppUser(SysAppUsers sysAppUsers) {
        sysAppUsers.setUserId(IdUtils.getSeqId());
        RandomNumberGenerator rr = new SecureRandomNumberGenerator();
        PasswordHelper pp = new PasswordHelper();
        sysAppUsers.setSaltValue(rr.nextBytes().toHex());
        sysAppUsers.setPassword(pp.getMD5Password(sysAppUsers.getLoginName(),sysAppUsers.getSaltValue()));
        sysAppUsers.setUserState("10");
        sysAppUsers.setCreateDate(DateUtil.currentDateTime());
        sysAppUsers.setUpdateDate(DateUtil.currentDateTime());
        return sysAppUsersMapper.insertSelective(sysAppUsers);
    }

    /**
     * @Author 郭凯
     * @Description APP用户删除
     * @Date 14:02 2020/11/3
     * @Param [userId]
     * @return int
    **/
    @Override
    public int delAppUsers(Long userId) {
        sysAppUsersSpotRoleMapper.deleteByUserId(userId);
        return sysAppUsersMapper.deleteByPrimaryKey(userId);
    }

    /**
     * @Author 郭凯
     * @Description 查询回显景区
     * @Date 17:38 2020/11/3
     * @Param [userId]
     * @return java.util.List<com.hna.hka.archive.management.managerApp.model.SysAppUsersSpotRole>
    **/
    @Override
    public List<SysAppUsersSpotRole> getScenicSpotzTree(Long userId) {
        return sysAppUsersSpotRoleMapper.getScenicSpotzTree(userId);
    }

    /**
     * @Author 郭凯
     * @Description 新增APP账号景区角色权限
     * @Date 17:48 2020/11/3
     * @Param [roleId, scenicSpots, userId]
     * @return int
    **/
    @Override
    public int addRoleScenicSpot(Long roleId, String scenicSpotId, String userId) {
        sysAppUsersSpotRoleMapper.deleteByUserId(Long.parseLong(userId));
        String[] spotId = scenicSpotId.split(",");
        for (int i = 0; i < spotId.length; i++) {
            SysAppUsersSpotRole sysUsersRoleSpot = new SysAppUsersSpotRole();
            sysUsersRoleSpot.setPrimaryKeyId(IdUtils.getSeqId());
            sysUsersRoleSpot.setPrimaryKeyUid(Long.parseLong(userId));
            sysUsersRoleSpot.setPrimaryKeyPid(Long.parseLong(spotId[i]));
            sysUsersRoleSpot.setPrimaryKeyRid(roleId);
            sysUsersRoleSpot.setCreateTime(DateUtil.currentDateTime());
            sysUsersRoleSpot.setUpdateTime(DateUtil.currentDateTime());
            sysAppUsersSpotRoleMapper.insertSelective(sysUsersRoleSpot);
        }
        return 1;
    }

    /**
     * @Author 郭凯
     * @Description 管理者APP重置密码
     * @Date 11:43 2020/11/4
     * @Param [sysAppUsers]
     * @return int
    **/
    @Override
    public int resetPassword(SysAppUsers sysAppUsers) {
        RandomNumberGenerator rr = new SecureRandomNumberGenerator();
        PasswordHelper pp = new PasswordHelper();
        sysAppUsers.setSaltValue(rr.nextBytes().toHex());
        sysAppUsers.setPassword(pp.getMD5Password(sysAppUsers.getPassword(),sysAppUsers.getSaltValue()));
        sysAppUsers.setUpdateDate(DateUtil.currentDateTime());
        return sysAppUsersMapper.updateByPrimaryKeySelective(sysAppUsers);
    }

    /**
    * @Author 郭凯
    * @Description: 下载Excel表
    * @Title: getUsersVoExcel
    * @date  2021年3月3日 下午2:05:59 
    * @param @param search
    * @param @return
    * @throws
     */
	@Override
	public List<SysAppUsers> getUsersVoExcel(Map<String, Object> search) {
		// TODO Auto-generated method stub
		return sysAppUsersMapper.getUsersVoExcel(search);
	}

    /**
     * @Method getSysAppUsers
     * @Author 郭凯
     * @Version  1.0
     * @Description 查询所有员工
     * @Return java.util.List<com.hna.hka.archive.management.managerApp.model.SysAppUsers>
     * @Date 2021/6/25 14:54
     */
    @Override
    public List<SysAppUsers> getSysAppUsers() {
        return sysAppUsersMapper.getSysAppUsers();
    }

    /**
     * @Method getAppUserById
     * @Author 郭凯
     * @Version  1.0
     * @Description 根据ID查询用户
     * @Return com.hna.hka.archive.management.managerApp.model.SysAppUsers
     * @Date 2021/7/8 17:34
     */
    @Override
    public SysAppUsers getAppUserById(Long userId) {
        return sysAppUsersMapper.selectByPrimaryKey(userId);
    }


}
