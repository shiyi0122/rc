package com.hna.hka.archive.management.system.service;

import com.hna.hka.archive.management.system.model.SysCurrentUser;
import com.hna.hka.archive.management.system.model.SysUsers;
import com.hna.hka.archive.management.system.model.WechatSysDepositLog;
import com.hna.hka.archive.management.system.util.PageDataResult;

import java.util.List;
import java.util.Map;

/**
 * @ProjectName: IDEA-CODE
 * @Package: com.hna.hka.archive.management.system.service
 * @ClassName: SysCurrentUserService
 * @Author: 郭凯
 * @Description: 客户管理业务层（接口）
 * @Date: 2020/5/22 15:00
 * @Version: 1.0
 */
public interface SysCurrentUserService {

    /**
     * 查询客户列表
     * @param pageNum
     * @param pageSize
     * @param search
     * @return
     */
    PageDataResult getCurrentUserList(Integer pageNum, Integer pageSize, Map<String,Object> search);

    /**
     * 修改用户是否欠款状态
     * @param currenUser
     * @return
     */
    int updateCurrenUser(SysCurrentUser currenUser);

    /**
     * 查询回显状态
     * @param currentUserId
     * @return
     */
    SysCurrentUser getCurrentUserById(Long currentUserId);

    /**
     * 修改押金缴纳状态
     * @param sysCurrentUser
     * @param sysUsers 
     * @return
     */
    int updateDepositPayState(SysCurrentUser sysCurrentUser, SysUsers sysUsers);

    int saveDeposLog(WechatSysDepositLog chatSysDepositLog);

    List<SysCurrentUser> getCurrentUserExcel(Map<String, Object> search);

    int delBlacklist(Long blackListId);

    SysCurrentUser getCurrentUserByPhone(String phone);

}
