package com.hna.hka.archive.management.assetsSystem.dao;

import com.hna.hka.archive.management.assetsSystem.model.SysAppFlowPath;
import com.hna.hka.archive.management.assetsSystem.model.SysAppFlowPathSpot;

import java.util.List;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2023/6/27 16:51
 */
public interface SysAppFlowPathSpotMapper {


    int  insertSelective(SysAppFlowPathSpot sysAppFlowPathSpot);


    int deleteByFlowPathId(Long id);

    List<SysAppFlowPathSpot> selectFlowPathIdByList(Long id);

}
