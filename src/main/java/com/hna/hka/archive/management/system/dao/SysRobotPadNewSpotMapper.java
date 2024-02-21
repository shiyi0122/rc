package com.hna.hka.archive.management.system.dao;

import com.hna.hka.archive.management.system.model.SysRobotPad;
import com.hna.hka.archive.management.system.model.SysRobotPadNew;
import com.hna.hka.archive.management.system.model.SysRobotPadNewSpot;

import java.util.List;

/**
 * @Author zhang
 * @Date 2023/3/8 13:51
 */
public interface SysRobotPadNewSpotMapper {

    int insertSelective(SysRobotPadNewSpot sysRobotPadNewSpot);


    int deleteByPrimaryKey(Long padId);

    SysRobotPadNewSpot selectBySpotIdType(String spotId, String packageType);

    int updateByPrimaryKeySelective(SysRobotPadNewSpot sysRobotPadNewSpot);

    List<SysRobotPadNew> selectPadIdBySpotId(Long padId);

    int deleteById(Long id);

}
