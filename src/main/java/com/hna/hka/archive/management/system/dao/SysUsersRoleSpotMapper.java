package com.hna.hka.archive.management.system.dao;

import com.hna.hka.archive.management.system.model.SysUsersRoleSpot;

import java.util.List;
import java.util.Map;

public interface SysUsersRoleSpotMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SysUsersRoleSpot record);

    int insertSelective(SysUsersRoleSpot record);

    SysUsersRoleSpot selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysUsersRoleSpot record);

    int updateByPrimaryKey(SysUsersRoleSpot record);

    int deleteByScenicSpotId(Long scenicSpotId);

    List<SysUsersRoleSpot> getUserRoleSpotList(Map<String, Object> search);

    int deleteByUserId(Long userId);

    SysUsersRoleSpot selectUserSRoleSpotById(Long userId, Long roleId, Long scenicSpotId);

}