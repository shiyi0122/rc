package com.hna.hka.archive.management.assetsSystem.service;

import com.hna.hka.archive.management.assetsSystem.model.SysRobotPeripheral;
import com.hna.hka.archive.management.system.util.ReturnModel;

import java.util.List;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2022/3/1 15:34
 */
public interface SysRobotPeripheralService {
    ReturnModel getRobotPeripheralList(Integer pageNum, Integer pageSize, Map<String, String> search);

    int addRobotPeripheral(SysRobotPeripheral sysRobotPeripheral);

    int editRobotPeripheral(SysRobotPeripheral sysRobotPeripheral);

    int delRobotPeripheral(Long robotPeripheralId);

    List<SysRobotPeripheral> getRobotPeripheralExcelList(Map<String, String> search);

    //导入
    int importExcel(SysRobotPeripheral sysRobotPeripheral);
}
