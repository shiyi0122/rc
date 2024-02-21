package com.hna.hka.archive.management.system.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hna.hka.archive.management.system.dao.*;
import com.hna.hka.archive.management.system.model.*;
import com.hna.hka.archive.management.system.service.SysUserService;
import com.hna.hka.archive.management.system.util.*;
import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * @ProjectName: IDEA-CODE
 * @Package: com.hna.hka.archive.management.system.service.impl
 * @ClassName: SysUserServiceImpl
 * @Author: 郭凯
 * @Description: 用户管理业务层（实现）
 * @Date: 2020/5/16 14:06
 * @Version: 1.0
 */
@Service
@Transactional
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private SysUsersMapper sysUsersMapper;

    @Autowired
    private SysUsersExamineMapper sysUsersExamineMapper;
    @Autowired
    private SysUsersRoleSpotExamineMapper sysUsersRoleSpotExamineMapper;

    @Autowired
    private SysScenicSpotBindingMapper sysScenicSpotBindingMapper;

    @Autowired
    private SysUsersRoleSpotMapper sysUsersRoleSpotMapper;

    @Autowired
    private SysScenicSpotImgMapper sysScenicSpotImgMapper;

    @Autowired
    private SysScenicSpotMapper sysScenicSpotMapper;


    /**
     * @Author 郭凯
     * @Description 查询用户列表
     * @Date 14:08 2020/5/16
     * @Param [pageNum, pageSize, sysUsers]
     * @return com.hna.hka.archive.management.system.util.PageDataResult
     **/
    @Override
    public PageDataResult getUserList(Integer pageNum, Integer pageSize, SysUsers sysUsers) {
        PageDataResult pageDataResult = new PageDataResult();
        PageHelper.startPage(pageNum, pageSize);
        List<SysUsers> sysUsersList = sysUsersMapper.getUserList(sysUsers);
        if(sysUsersList.size() != 0){
            PageInfo<SysUsers> pageInfo = new PageInfo<>(sysUsersList);
            pageDataResult.setList(pageInfo.getList());
            pageDataResult.setTotals((int) pageInfo.getTotal());
        }
        return pageDataResult;
    }

    /**
     * @Author 郭凯
     * @Description 新增用户
     * @Date 9:14 2020/5/18
     * @Param [sysUsers]
     * @return int
     **/
    @Override
    public int addUser(SysUsers sysUsers) {
        //判断用户账号是否存在
        SysUsers users = sysUsersMapper.getUserByLoginName(sysUsers.getLoginName());
        if (users != null){
            return 2;
        }
        //主键ID
        sysUsers.setUserId(IdUtils.getSeqId());
        // 密码和盐值
        RandomNumberGenerator rr = new SecureRandomNumberGenerator();
        PasswordHelper pp = new PasswordHelper();
        String loginPassword = sysUsers.getPassword();
        sysUsers.setSaltValue(rr.nextBytes().toHex());
        sysUsers.setPassword(pp.getMD5Password(loginPassword,sysUsers.getSaltValue()));
        sysUsers.setUserState("10");
        // 创建时间
        sysUsers.setCreateDate(DateUtil.currentDateTime());
        // 更新时间
        sysUsers.setUpdateDate(DateUtil.currentDateTime());

        sysUsersExamineMapper.insertSelective(sysUsers);
        return sysUsersMapper.insertSelective(sysUsers);
    }

    /**
     * @Author 郭凯
     * @Description 修改用户信息
     * @Date 13:32 2020/5/18
     * @Param [sysUsers]
     * @return int
     **/
    @Override
    public int editUser(SysUsers sysUsers) {
        sysUsers.setUpdateDate(DateUtil.currentDateTime());
        RandomNumberGenerator rr = new SecureRandomNumberGenerator();
        PasswordHelper pp = new PasswordHelper();
        sysUsers.setSaltValue(rr.nextBytes().toHex());
//        sysUsers.setPassword(pp.getMD5Password(sysUsers.getPassword(),sysUsers.getSaltValue()));
        sysUsers.setUpdateDate(DateUtil.currentDateTime());
        return sysUsersMapper.updateByPrimaryKeySelective(sysUsers);
    }

    /**
     * @Author 郭凯
     * @Description 删除用户
     * @Date 13:38 2020/5/18
     * @Param [userId]
     * @return int
     **/
    @Override
    public int delUser(Long userId) {
        SysUsers sysUsers = new SysUsers();
        sysUsers.setUserId(userId);
        sysUsers.setUserState("90");
        sysUsers.setUpdateDate(DateUtil.currentDateTime());
        return sysUsersMapper.updateByPrimaryKeySelective(sysUsers);
    }

    /**
     * @Author 郭凯
     * @Description 查询角色景区
     * @Date 9:25 2020/5/19
     * @Param []
     * @return java.util.List<com.hna.hka.archive.management.system.model.SysScenicSpotBinding>
     **/
    @Override
    public List<SysScenicSpotBinding> getScenicSpotRole(Map<String,String> search) {
        List<SysScenicSpotBinding> scenicSpotBinding = new ArrayList<SysScenicSpotBinding>();
        List<SysScenicSpotImg> sysScenicSpotImg = sysScenicSpotImgMapper.getScenicSpotImgByUserId(search);
        List<SysScenicSpot> SysScenicSpot = sysScenicSpotMapper.getScenicSpotList();
        if (sysScenicSpotImg.size() == SysScenicSpot.size()){
            return scenicSpotBinding;
        }
        List<SysScenicSpotBinding> sysScenicSpotBinding = sysScenicSpotBindingMapper.getScenicSpotRole();

        List<SysScenicSpotBinding> scenicSpots = new ArrayList<SysScenicSpotBinding>();

        //筛选出省份数据
        for (SysScenicSpotBinding spotBinding : sysScenicSpotBinding){
            if (ToolUtil.isEmpty(spotBinding.getScenicSpotPid())){
                scenicSpotBinding.add(spotBinding);
            }
        }
        //删除调用户已有的景区权限
        for (SysScenicSpotImg scenicSpot : sysScenicSpotImg){
            for (SysScenicSpotBinding scenicSpotBindings : sysScenicSpotBinding){
                if (scenicSpot.getScenicSpotId().equals(scenicSpotBindings.getScenicSpotFid())){
                    sysScenicSpotBinding.remove(scenicSpotBindings);
                    break;
                }
            }
        }
        Iterator<SysScenicSpotBinding> iter = sysScenicSpotBinding.iterator();
        while(iter.hasNext()){
            SysScenicSpotBinding s = iter.next();
            if(ToolUtil.isEmpty(s.getScenicSpotPid())){
                iter.remove();
            }
        }
        for (SysScenicSpotBinding scenicSpotBindings : sysScenicSpotBinding){
            scenicSpots.add(scenicSpotBindings);
        }
        for(SysScenicSpotBinding SysScenicSpotBinding : scenicSpotBinding){
            for (SysScenicSpotBinding scenicSpotBindings : sysScenicSpotBinding){
                if (SysScenicSpotBinding.getScenicSpotFid().equals(scenicSpotBindings.getScenicSpotPid())){
                    scenicSpots.add(SysScenicSpotBinding);
                    break;
                }
            }
        }
        return scenicSpots;
    }

    /**
     * @Author 郭凯
     * @Description 新增账号景区角色权限
     * @Date 11:31 2020/5/19
     * @Param [roleId, scenicSpots, userId]
     * @return int
    **/
    @Override
    public int addScenicSpotRole(Long roleId, String scenicSpots, String userId) {
        String[] scenicSpotId = scenicSpots.split(",");
        int i = 0;
        int k= 0;
        for (int j = 0; j < scenicSpotId.length; j++) {
            SysScenicSpotBinding SysScenicSpotBinding = sysScenicSpotBindingMapper.selectByPrimaryKey(Long.parseLong(scenicSpotId[j]));
            if (!ToolUtil.isEmpty(SysScenicSpotBinding.getScenicSpotPid())){
                SysUsersRoleSpot sysUsersRoleSpot = new SysUsersRoleSpot();
                    sysUsersRoleSpot.setId(IdUtils.getSeqId());
                    sysUsersRoleSpot.setUserId(Long.parseLong(userId));
                    sysUsersRoleSpot.setRoleId(roleId);
                    sysUsersRoleSpot.setScenicSpotId(Long.parseLong(scenicSpotId[j]));
                    sysUsersRoleSpot.setCreateDate(DateUtil.currentDateTime());
                    k = sysUsersRoleSpotMapper.insertSelective(sysUsersRoleSpot);
            }

        }
        return k;
    }

    /**
     * zhang
     * @param roleId
     * @param scenicSpots
     * @param userId
     * @return
     */
    @Override
    public int addScenicSpotRoleNew(Long roleId, String scenicSpots, String userId) {

        String[] scenicSpotId = scenicSpots.split(",");
        int i = 0;
        int k= 0;
        for (int j = 0; j < scenicSpotId.length; j++) {
            SysScenicSpotBinding SysScenicSpotBinding = sysScenicSpotBindingMapper.selectByPrimaryKey(Long.parseLong(scenicSpotId[j]));
            if (!ToolUtil.isEmpty(SysScenicSpotBinding.getScenicSpotPid())){
                SysUsersRoleSpot sysUsersRoleSpot = new SysUsersRoleSpot();
                SysUsersRoleSpot roleSpot =   sysUsersRoleSpotExamineMapper.selectRoleAndSpotId(Long.parseLong(userId),roleId,Long.parseLong(scenicSpotId[j]));
                if (StringUtils.isEmpty(roleSpot)){
                    sysUsersRoleSpot.setId(IdUtils.getSeqId());
                    sysUsersRoleSpot.setUserId(Long.parseLong(userId));
                    sysUsersRoleSpot.setRoleId(roleId);
                    sysUsersRoleSpot.setScenicSpotId(Long.parseLong(scenicSpotId[j]));
                    sysUsersRoleSpot.setCreateDate(DateUtil.currentDateTime());
                    k= sysUsersRoleSpotExamineMapper.insertSelective(sysUsersRoleSpot);
                }

            }
            SysUsers sysUser =   sysUsersExamineMapper.selectByPrimaryKey(Long.parseLong(userId));
            if (StringUtils.isEmpty(sysUser)){
                SysUsers sysUsers = sysUsersMapper.selectByPrimaryKey(Long.parseLong(userId));
                sysUsers.setExamineState("0");
                sysUsers.setUpdateDate(DateUtil.currentDateTime());
                i = sysUsersExamineMapper.insertSelective(sysUsers);

            }else{

                i = sysUsersExamineMapper.updateExamineByUserId(Long.parseLong(userId),"0",DateUtil.currentDateTime());
            }

        }
        return k==1 || i==1 ? 1:0;

    }




    /**
     * @Author 郭凯
     * @Description 用户密码重置
     * @Date 16:12 2020/11/19
     * @Param [sysUsers]
     * @return int
    **/
    @Override
    public int resetPassword(SysUsers sysUsers) {
        RandomNumberGenerator rr = new SecureRandomNumberGenerator();
        PasswordHelper pp = new PasswordHelper();
        sysUsers.setSaltValue(rr.nextBytes().toHex());
        sysUsers.setPassword(pp.getMD5Password(sysUsers.getPassword(),sysUsers.getSaltValue()));
        sysUsers.setUpdateDate(DateUtil.currentDateTime());
        return sysUsersMapper.updateByPrimaryKeySelective(sysUsers);
    }

    /**
     * @Author 郭凯
     * @Description 查询权限详情
     * @Date 11:06 2020/11/20
     * @Param [pageNum, pageSize, search]
     * @return java.util.List<com.hna.hka.archive.management.system.model.SysUsersRoleSpot>
    **/
    @Override
    public PageDataResult getUserRoleSpotList(Integer pageNum, Integer pageSize, Map<String, Object> search) {
        PageDataResult pageDataResult = new PageDataResult();
        PageHelper.startPage(pageNum, pageSize);
        List<SysUsersRoleSpot> sysUsersRoleSpotList = sysUsersRoleSpotMapper.getUserRoleSpotList(search);
        if(sysUsersRoleSpotList.size() != 0){
            PageInfo<SysUsersRoleSpot> pageInfo = new PageInfo<>(sysUsersRoleSpotList);
            pageDataResult.setList(pageInfo.getList());
            pageDataResult.setTotals((int) pageInfo.getTotal());
        }
        return pageDataResult;
    }

    /**
     * @Author 郭凯
     * @Description 删除用户权限
     * @Date 11:26 2020/11/20
     * @Param [id]
     * @return int
    **/
    @Override
    public int delUserRoleSpot(Long id) {
               sysUsersRoleSpotExamineMapper.deleteByPrimaryKey(id);
        return sysUsersRoleSpotMapper.deleteByPrimaryKey(id);
    }

    /**
     * @Author 郭凯
     * @Description 用户权限Excel下载数据查询
     * @Date 9:54 2020/12/4
     * @Param [search]
     * @return java.util.List<com.hna.hka.archive.management.system.model.SysUsersRoleSpot>
    **/
    @Override
    public List<SysUsersRoleSpot> getUsersRoleSpotVoExcel(Map<String, Object> search) {
        return sysUsersRoleSpotMapper.getUserRoleSpotList(search);
    }

    /**
     * @Author 郭凯
     * @Description 查询用户信息
     * @Date 15:23 2020/12/8
     * @Param [userId]
     * @return com.hna.hka.archive.management.system.model.SysUsers
    **/
    @Override
    public SysUsers getUsersById(Long userId) {
        return sysUsersMapper.selectByPrimaryKey(userId);
    }

    /**
     * @Author 郭凯
     * @Description 导出用户Excel表数据查询
     * @Date 9:22 2020/12/10
     * @Param [search]
     * @return java.util.List<com.hna.hka.archive.management.system.model.SysUsers>
    **/
    @Override
    public List<SysUsers> getUsersVoExcel(Map<String, Object> search) {
        return sysUsersMapper.getUsersVoExcel(search);
    }

    /**
     * @Author 郭凯
     * @Description 根据用户登录名称查询用户信息
     * @Date 10:37 2020/12/10
     * @Param [loginName]
     * @return com.hna.hka.archive.management.system.model.SysUsers
    **/
    @Override
    public SysUsers getSysUsersByLoginName(String loginName) {
        return sysUsersMapper.getUserByLoginName(loginName);
    }

    /**
     * 审核接口
     * @param sysUsers
     * @return
     */

    @Override
    public int userExamine(SysUsers sysUsers) {

        Map<String, Object> search = new HashMap<>();
        int i = 0;
        if ("1".equals(sysUsers.getUserState())){
            search.put("userId",sysUsers.getUserId());
            List<SysUsersRoleSpot> userRoleSpotList = sysUsersRoleSpotExamineMapper.getUserRoleSpotList(search);
//            int j = sysUsersRoleSpotMapper.deleteByUserId(sysUsers.getUserId());

            for (SysUsersRoleSpot sysUsersRoleSpot : userRoleSpotList) {
                SysUsersRoleSpot userRoleSpot =  sysUsersRoleSpotMapper.selectUserSRoleSpotById(sysUsersRoleSpot.getUserId(),sysUsersRoleSpot.getRoleId(),sysUsersRoleSpot.getScenicSpotId());
                if (StringUtils.isEmpty(userRoleSpot)){
                    i = sysUsersRoleSpotMapper.insertSelective(sysUsersRoleSpot);
                }
            }
            i =  sysUsersExamineMapper.updateExamineByUserId(sysUsers.getUserId(),sysUsers.getUserState(),DateUtil.currentDateTime());

        }else{
             i =  sysUsersExamineMapper.updateExamineByUserId(sysUsers.getUserId(),sysUsers.getUserState(),DateUtil.currentDateTime());
            return 2;
        }



        return i;
    }


    /**
     * 获取审核用户列表
     * @param pageNum
     * @param pageSize
     * @param sysUsers
     * @return
     */
    @Override
    public PageDataResult getUserExamineList(Integer pageNum, Integer pageSize, SysUsers sysUsers) {
        PageDataResult pageDataResult = new PageDataResult();
        PageHelper.startPage(pageNum, pageSize);
//        List<SysUsers> sysUsersList = sysUsersMapper.getUserList(sysUsers);
        List<SysUsers> userList = sysUsersExamineMapper.getUserList(sysUsers);
        if(userList.size() != 0){
            PageInfo<SysUsers> pageInfo = new PageInfo<>(userList);
            pageDataResult.setList(pageInfo.getList());
            pageDataResult.setTotals((int) pageInfo.getTotal());
        }
        return pageDataResult;
    }
}
