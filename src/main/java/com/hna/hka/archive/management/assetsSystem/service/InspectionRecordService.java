package com.hna.hka.archive.management.assetsSystem.service;

import com.github.pagehelper.PageInfo;
import com.hna.hka.archive.management.appSystem.model.vo.SysAppRobotQualityTesting;
import com.hna.hka.archive.management.assetsSystem.model.InspectionRecord;
import com.hna.hka.archive.management.assetsSystem.model.InspectionRecordDetail;
import com.hna.hka.archive.management.assetsSystem.model.RobotQualityTesting;

import java.util.List;

/**
 * @program: rc
 * @description:
 * @author: zhaoxianglong
 * @create: 2021-09-18 10:33
 **/
public interface InspectionRecordService {
    PageInfo<InspectionRecord> list(String robotCode, String startTime, String endTime, Integer type, Integer result, Integer pageNum, Integer pageSize) throws Exception;

    Integer add(SysAppRobotQualityTesting sysAppRobotQualityTesting);

    InspectionRecord preview(Long id);

    List<InspectionRecordDetail> showDetail(long inspectionId);


    RobotQualityTesting detailList(Long id);

    Integer delect(Long id);
}
