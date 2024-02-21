package com.hna.hka.archive.management.assetsSystem.service;

import com.hna.hka.archive.management.assetsSystem.model.AccountStatement;
import com.hna.hka.archive.management.assetsSystem.model.CooperativeCompany;
import com.hna.hka.archive.management.assetsSystem.model.StatementOfAccessories;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2022/4/20 10:15
 */
public interface StatementOfAccessoriesService  {


    List<StatementOfAccessories> list(Long company, Long spot, String beginDate, String endDate, String paymentType, Long errorRecordsUpkeepCost, Integer pageNum, Integer pageSize);

    Map<String, Object> getCount(Long company, Long spot, String beginDate, String endDate, String paymentType, Long errorRecordsUpkeepCost, Integer pageNum, Integer pageSize);

    List<HashMap<String, Object>> preview(Long company, Long spot, String beginDate, String paymentType, Long errorRecordsUpkeepCost, Integer pageNum, Integer pageSize);

    Map<String,Object> calculateTotal(Long company, Long spot, String beginDate, String endDate, String paymentType, Integer orderStatus, Integer pageNum, Integer pageSize);

    CooperativeCompany getCompanyId(Long companyId);


    List<StatementOfAccessories> download(Long company, Long spot, String beginDate, String endDate, String paymentType, Long errorRecordsUpkeepCost, Integer pageNum, Integer pageSize);





    List<StatementOfAccessories> listNew(Long company, Long spot, String beginDate, String paymentType, Long errorRecordsUpkeepCost, String errorRecordsQuality,Integer pageNum, Integer pageSize);


    Map<String, Object> getCountNew(Long company, Long spot, String beginDate, String paymentType, Long errorRecordsUpkeepCost,String errorRecordsQuality ,Integer pageNum, Integer pageSize);


    int exitSettlementState(Long company, Long spot, String beginDate, String paymentType, Long errorRecordsUpkeepCost,Long userId);


    int settlementStateState(Long company, Long spot, String beginDate, String paymentType, Long errorRecordsUpkeepCost,Long userId,String userName);


    /**
     * 定时计算景区承担配件费和景区承担维修费(当月1号凌晨执行)
     */

    void timingCalculationSpotPartsMaintenance();


}
