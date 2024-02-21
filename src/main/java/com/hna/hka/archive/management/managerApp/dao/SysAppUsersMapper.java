package com.hna.hka.archive.management.managerApp.dao;

import com.hna.hka.archive.management.managerApp.model.SysAppUsers;

import java.util.List;
import java.util.Map;

public interface SysAppUsersMapper {
    int deleteByPrimaryKey(Long userId);

    int insert(SysAppUsers record);

    int insertSelective(SysAppUsers record);

    SysAppUsers selectByPrimaryKey(Long userId);

    int updateByPrimaryKeySelective(SysAppUsers record);

    int updateByPrimaryKey(SysAppUsers record);

    List<SysAppUsers> getAppUsersList(Map<String, String> search);

    SysAppUsers getSysAppUserByLoginName(String loginName);

    SysAppUsers getSysUserBylonginTokenId(String longinTokenId);

	List<SysAppUsers> getUsersVoExcel(Map<String, Object> search);

    List<SysAppUsers> getSysAppUsers();

    List<SysAppUsers> getAppUsersByScenicIdList(Map<String,Object> search);

    SysAppUsers getSysAppUserByUserName(String serviceRecordsPersonnel);

}