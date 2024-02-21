package com.hna.hka.archive.management.appSystem.service;


import com.hna.hka.archive.management.assetsSystem.model.InspectionRecord;
import com.hna.hka.archive.management.appSystem.model.vo.SysAppRobotQualityTesting;
import com.hna.hka.archive.management.assetsSystem.model.ProductionInfo;
import com.hna.hka.archive.management.assetsSystem.model.SysRobotQualityInspectionStandard;

import java.util.List;

public interface AppRobotQualityTestingService {
    int insertQcOrAt(String userId, InspectionRecord inspectionRecord) ;

    InspectionRecord seInspectionDetails(Long id,String robotCode);

    List<InspectionRecord> selectQcOrAts(String robotCode, String startTime, String endTime, Integer type, Integer result,String userId) throws Exception;

    List<SysRobotQualityInspectionStandard> qualityStandard(String type);


    List<ProductionInfo> productionBatch();


    int reInspection(String userId, InspectionRecord inspectionRecord);


}
