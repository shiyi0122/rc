package com.hna.hka.archive.management.system.dao;

import com.hna.hka.archive.management.system.model.SysRobotPadNewBinding;

/**
 * @Author zhang
 * @Date 2023/3/9 19:47
 */
public interface SysRobotPadNewBindingMapper {

    SysRobotPadNewBinding selectByPrimaryKey(Long id);

    int deleteByPrimaryKey(Long id);

    int insertSelective(SysRobotPadNewBinding sysRobotPadNewBinding);

    int updateByPrimaryKeySelective(SysRobotPadNewBinding sysRobotPadNewBinding);

    int deleteByPrimaryKeyNatural(Long padNaturalId);


}
