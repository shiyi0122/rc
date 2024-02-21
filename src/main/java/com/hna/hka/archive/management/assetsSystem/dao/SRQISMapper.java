package com.hna.hka.archive.management.assetsSystem.dao;

import com.hna.hka.archive.management.assetsSystem.model.SysRobotQualityInspectionDetail;
import com.hna.hka.archive.management.assetsSystem.model.SysRobotQualityInspectionStandard;
import com.hna.hka.archive.management.system.model.SysScenicSpot;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @program: rc
 * @description:
 * @author: zhaoxianglong
 * @create: 2021-09-16 09:59
 **/
public interface SRQISMapper {
    List<SysRobotQualityInspectionStandard> list(String name, String beginTime, Integer beginSize, String endTime, Integer endSize);

    Integer add(SysRobotQualityInspectionStandard standard);

    Integer edit(SysRobotQualityInspectionStandard standard);

    Integer delete(Long id);

    List<SysRobotQualityInspectionDetail> showDetail(Long id);

    Integer addDetail(SysRobotQualityInspectionDetail detail);

    Integer editDetail(SysRobotQualityInspectionDetail detail);

    Integer deleteDetail(Long id, Long standardId);

    HashMap getMsgByRobotCode(String robotCode, Integer type);

    ArrayList getDetailByRobotCode(String robotCode , Integer type);

    List<SysRobotQualityInspectionStandard> getAll(String type);

    List<SysRobotQualityInspectionStandard> qualityStandard(String type);


    SysRobotQualityInspectionDetail selectById(@Param("SysRobotQualityInspectionDetail") SysRobotQualityInspectionDetail detail);

    SysRobotQualityInspectionDetail selectByPre(@Param("SysRobotQualityInspectionDetail")SysRobotQualityInspectionDetail detail);

    SysRobotQualityInspectionDetail selectByNext(@Param("SysRobotQualityInspectionDetail")SysRobotQualityInspectionDetail detail);

    Integer updateSerial(SysRobotQualityInspectionDetail previousData);

}
