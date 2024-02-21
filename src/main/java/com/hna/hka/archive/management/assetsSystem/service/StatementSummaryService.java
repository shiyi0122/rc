package com.hna.hka.archive.management.assetsSystem.service;

import com.hna.hka.archive.management.assetsSystem.model.OrderMoney;
import com.hna.hka.archive.management.assetsSystem.model.SysScenicSpotTargetAmount;
import com.hna.hka.archive.management.system.util.PageDataResult;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2023/2/1 14:50
 */
public interface StatementSummaryService {

    PageDataResult getStatementSummaryList(String subjectId, String companyId, String spotId, String startTime, String endTime, Integer pageNum, Integer pageSize);


    List<OrderMoney> getStatementSummaryExcel(String subjectId, String companyId, String spotId, String startTime, String endTime);

    /**
     * 预览
     * @param subjectId
     * @param companyId
     * @param spotId
     * @param startTime
     * @param endTime
     * @return
     */
    HashMap<String, Object> preview(String subjectId, String companyId, String spotId, String startTime, String endTime);

}
