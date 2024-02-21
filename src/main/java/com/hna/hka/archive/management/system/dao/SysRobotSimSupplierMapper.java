package com.hna.hka.archive.management.system.dao;

import com.hna.hka.archive.management.system.model.SysRobotSimSupplier;

import java.util.List;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2023/6/2 11:02
 */
public interface SysRobotSimSupplierMapper {

    int insertSelective(SysRobotSimSupplier sysRobotSimSupplier);


    int updateByPrimaryKeySelective(SysRobotSimSupplier sysRobotSimSupplier);

    List<SysRobotSimSupplier> getSysRobotSimSupplierList(Map<String, Object> search);


    int deleteByPrimaryKey(Long id);

    SysRobotSimSupplier getSimById(String simCard);
}
