package com.hna.hka.archive.management.appYXBSystem.dao;

import com.hna.hka.archive.management.appYXBSystem.model.SysGuideAppUsers;
import com.hna.hka.archive.management.appYXBSystem.model.UserOperationLog;
import com.hna.hka.archive.management.appYXBSystem.model.UserOperationLogDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysGuideAppUsersMapper {

    int insert(SysGuideAppUsers sysGuideAppUsers);

    int insertSelective(SysGuideAppUsers sysGuideAppUsers);

    int updateByPrimaryKeySelective(SysGuideAppUsers sysGuideAppUsers);

    SysGuideAppUsers selectAppUsersByPhone(String userPhone);

    int deleteByPrimaryKey(Long userId);

    List<SysGuideAppUsers> getGuideAppUsersList(@Param("sysGuideAppUsers") SysGuideAppUsers sysGuideAppUsers);

    List<SysGuideAppUsers> selectAppPhone();


    List<String> selectAppUsersByClientGtId();


    List<UserOperationLog> selectByUser(UserOperationLogDTO userOperationLogDTO);

    int selectInstall(UserOperationLogDTO userOperationLogDTO);

    int selectByregister(UserOperationLogDTO userOperationLogDTO);

    int selectByOpen(UserOperationLogDTO userOperationLogDTO);
}
