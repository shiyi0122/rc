package com.hna.hka.archive.management.assetsSystem.dao;

import com.hna.hka.archive.management.assetsSystem.model.SysRobotPeripheral;

import java.util.List;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2022/3/1 15:37
 */
public interface SysRobotPeripheralMapper {

    List<SysRobotPeripheral> getRobotPeripheralLists(Map<String, String> search);
    List<SysRobotPeripheral> getRobotPeripheralList(Map<String, String> search);

    int insertSelective(SysRobotPeripheral sysRobotPeripheral);

    int updateByPrimaryKeySelective(SysRobotPeripheral sysRobotPeripheral);

    int deleteByPrimaryKey(Long robotPeripheralId);
    List<SysRobotPeripheral> getRobotPeripheralId(SysRobotPeripheral sysRobotPeripheral);
}
