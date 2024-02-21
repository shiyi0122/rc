package com.hna.hka.archive.management.system.dao;

import com.hna.hka.archive.management.system.model.SysUsersRoleSpot;

import java.util.List;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2022/3/10 15:54
 */
public interface SysUsersRoleSpotExamineMapper {

    int deleteByPrimaryKey(Long id);

    int insert(SysUsersRoleSpot record);

    int insertSelective(SysUsersRoleSpot record);

    SysUsersRoleSpot selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysUsersRoleSpot record);

    int updateByPrimaryKey(SysUsersRoleSpot record);

    int deleteByScenicSpotId(Long scenicSpotId);

    List<SysUsersRoleSpot> getUserRoleSpotList(Map<String, Object> search);


    int deleteByUserId(Long userId);

    SysUsersRoleSpot selectRoleAndSpotId(long userId, Long roleId, long spotId);

}
