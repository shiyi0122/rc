package com.hna.hka.archive.management.system.service;

import com.hna.hka.archive.management.system.model.SysScenicSpotBinding;
import com.hna.hka.archive.management.system.model.SysUsers;
import com.hna.hka.archive.management.system.model.SysUsersRoleSpot;
import com.hna.hka.archive.management.system.util.PageDataResult;

import java.util.List;
import java.util.Map;

/**
 * @ProjectName: IDEA-CODE
 * @Package: com.hna.hka.archive.management.system.service
 * @ClassName: sysUserService
 * @Author: 郭凯
 * @Description: 用户管理业务层（接口）
 * @Date: 2020/5/16 14:05
 * @Version: 1.0
 */
public interface SysUserService {

    /**
     * 查询用户列表
     * @param pageNum
     * @param pageSize
     * @param sysUsers
     * @return
     */
    PageDataResult getUserList(Integer pageNum, Integer pageSize, SysUsers sysUsers);

    /**
     * 新增用户
     * @param sysUsers
     * @return
     */
    int addUser(SysUsers sysUsers);

    /**
     * 修改用户信息
     * @param sysUsers
     * @return
     */
    int editUser(SysUsers sysUsers);

    /**
     * 删除用户
     * @param userId
     * @return
     */
    int delUser(Long userId);

    /**
     * 查询角色景区
     * @return
     */
    List<SysScenicSpotBinding> getScenicSpotRole(Map<String,String> search);

    /**
     * 新增账号景区角色权限
     * @param roleId
     * @param scenicSpots
     * @param userId
     * @return
     */
    int addScenicSpotRole(Long roleId, String scenicSpots, String userId);

    int resetPassword(SysUsers sysUsers);

    PageDataResult getUserRoleSpotList(Integer pageNum, Integer pageSize, Map<String, Object> search);

    int delUserRoleSpot(Long id);

    List<SysUsersRoleSpot> getUsersRoleSpotVoExcel(Map<String, Object> search);

    SysUsers getUsersById(Long userId);

    List<SysUsers> getUsersVoExcel(Map<String, Object> search);

    SysUsers getSysUsersByLoginName(String loginName);

    int userExamine(SysUsers sysUsers);

    PageDataResult getUserExamineList(Integer pageNum, Integer pageSize, SysUsers sysUsers);
    /**
     * 新增账号景区角色权限
     * @param roleId
     * @param scenicSpots
     * @param userId
     * zhang后添加审核流程
     * @return
     */
    int  addScenicSpotRoleNew(Long roleId, String scenicSpots, String userId);

}
