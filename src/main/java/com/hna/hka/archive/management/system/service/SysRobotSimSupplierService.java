package com.hna.hka.archive.management.system.service;

import com.hna.hka.archive.management.system.model.SysRobotSimSupplier;
import com.hna.hka.archive.management.system.util.PageDataResult;

import java.util.List;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2023/6/2 10:59
 */
public interface SysRobotSimSupplierService {

    int addSysRobotSimSupplier(SysRobotSimSupplier sysRobotSimSupplier);


    int editSysRobotSimSupplier(SysRobotSimSupplier sysRobotSimSupplier);

    PageDataResult getSysRobotSimSupplierList(Integer pageNum, Integer pageSize, Map<String, Object> search);


    int delSysRobotSimSupplier(Long id);

    List<SysRobotSimSupplier> getSimExcel(Map<String, Object> search);

    SysRobotSimSupplier getSimById(String simCard);
}
