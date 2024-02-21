package com.hna.hka.archive.management.assetsSystem.dao;

import com.hna.hka.archive.management.assetsSystem.model.SysRobotErrorParts;

import java.util.List;

public interface SysRobotErrorPartsMapper {


    int insert(SysRobotErrorParts sysRobotErrorParts);

    //根据故障单号，故障配件
    List<SysRobotErrorParts>  selectErrorPartsByRecordsId(Long errorRecordsId);

  //  List<SysRobotErrorParts> selectErrorPartsByRecordsId(Long errorRecordsId);

}
