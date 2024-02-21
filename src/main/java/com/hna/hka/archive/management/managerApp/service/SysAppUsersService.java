package com.hna.hka.archive.management.managerApp.service;

import com.hna.hka.archive.management.managerApp.model.SysAppUsers;
import com.hna.hka.archive.management.managerApp.model.SysAppUsersSpotRole;
import com.hna.hka.archive.management.system.util.PageDataResult;

import java.util.List;
import java.util.Map;

/**
 * @ProjectName: IDEA-CODE
 * @Package: com.hna.hka.archive.management.managerApp.service
 * @ClassName: SysAppUsersService
 * @Author: 郭凯
 * @Description: 管理者APP用户管理业务层（接口）
 * @Date: 2020/11/3 9:53
 * @Version: 1.0
 */
public interface SysAppUsersService {

    PageDataResult getAppUsersList(Integer pageNum, Integer pageSize, Map<String, String> search);

    int editAppUsers(SysAppUsers sysAppUsers);

    int addAppUser(SysAppUsers sysAppUsers);

    int delAppUsers(Long userId);

    List<SysAppUsersSpotRole> getScenicSpotzTree(Long userId);

    int addRoleScenicSpot(Long roleId, String scenicSpots, String userId);

    int resetPassword(SysAppUsers sysAppUsers);

	List<SysAppUsers> getUsersVoExcel(Map<String, Object> search);

    List<SysAppUsers> getSysAppUsers();

    SysAppUsers getAppUserById(Long userId);



}
