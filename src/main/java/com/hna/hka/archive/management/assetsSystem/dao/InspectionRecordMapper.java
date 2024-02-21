package com.hna.hka.archive.management.assetsSystem.dao;

import com.hna.hka.archive.management.assetsSystem.model.InspectionRecord;
import com.hna.hka.archive.management.assetsSystem.model.InspectionRecordDetail;
import com.hna.hka.archive.management.appSystem.model.vo.SysAppRobotQualityTesting;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @program: rc
 * @description:
 * @author: zhaoxianglong
 * @create: 2021-09-18 10:33
 **/
public interface InspectionRecordMapper {
    List<InspectionRecord> list(String robotCode, String startTime, String endTime, Integer type, Integer result, String userId);

    Integer add(InspectionRecord record);

    InspectionRecord findByKey(Long id);

    List<InspectionRecordDetail> showDetail(long inspectionId);

    Integer addRobotQualityTesting(SysAppRobotQualityTesting sysAppRobotQualityTesting);

    InspectionRecord selectQcOrAts(Long type, Long result);

    InspectionRecordDetail[] lists(long id);

    InspectionRecord selectById(Long id);

    int addDetail(InspectionRecordDetail detail);

    InspectionRecord selectByRobotCode(String robotCode);

    InspectionRecord selectByRobotCodeAndType(String robotCode, Long type);

    int updateByPrimaryKeySelective(InspectionRecord inspectionRecord);

    int  editDetail(InspectionRecordDetail detail);

    Integer delect(Long id);
}
