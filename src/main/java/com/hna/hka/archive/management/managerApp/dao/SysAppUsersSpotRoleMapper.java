package com.hna.hka.archive.management.managerApp.dao;

import com.hna.hka.archive.management.managerApp.model.SysAppUsers;
import com.hna.hka.archive.management.managerApp.model.SysAppUsersSpotRole;
import io.lettuce.core.dynamic.annotation.Param;

import java.util.List;

public interface SysAppUsersSpotRoleMapper {
    int deleteByPrimaryKey(Long primaryKeyId);

    int insert(SysAppUsersSpotRole record);

    int insertSelective(SysAppUsersSpotRole record);

    SysAppUsersSpotRole selectByPrimaryKey(Long primaryKeyId);

    int updateByPrimaryKeySelective(SysAppUsersSpotRole record);

    int updateByPrimaryKey(SysAppUsersSpotRole record);

    List<SysAppUsersSpotRole> getScenicSpotzTree(Long userId);

    int deleteByUserId(Long userId);

    SysAppUsersSpotRole getRoleScenicSpot(@Param("roleId") Long roleId,@Param("scenicSpots") String scenicSpots,@Param("userId") String userId);

    List<SysAppUsersSpotRole> getUserIdListBySpotId(Long scenicSpotId);

    List<SysAppUsers> getScenicSpotByUser(@Param("scenicSpotId") Long scenicSpotId);

    List<SysAppUsers> getScenicSpotTypeByUser();


}