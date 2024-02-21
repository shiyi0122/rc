package com.hna.hka.archive.management.assetsSystem.service;

import com.github.pagehelper.PageInfo;
import com.hna.hka.archive.management.assetsSystem.model.SysRobotQualityInspectionDetail;
import com.hna.hka.archive.management.assetsSystem.model.SysRobotQualityInspectionStandard;

import java.util.HashMap;
import java.util.List;

/**
 * @program: rc
 * @description:
 * @author: zhaoxianglong
 * @create: 2021-09-16 09:58
 **/
public interface SRQISService {
    PageInfo<SysRobotQualityInspectionStandard> list(String name, String beginTime, String endTime, Integer pageNum, Integer pageSize);

    Integer add(SysRobotQualityInspectionStandard standard);

    Integer edit(SysRobotQualityInspectionStandard standard);

    Integer delete(Long id);

    List<SysRobotQualityInspectionDetail> showDetail(Long id);

    Integer addDetail(SysRobotQualityInspectionDetail detail);

    Integer editDetail(SysRobotQualityInspectionDetail detail);

    Integer deleteDetail(Long id, Long standardId);

    HashMap getMsgByRobotCode(String robotCode, Integer type);

    List<SysRobotQualityInspectionStandard> getAll(String type);
}
