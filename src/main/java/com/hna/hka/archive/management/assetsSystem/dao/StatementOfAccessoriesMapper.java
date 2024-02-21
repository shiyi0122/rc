package com.hna.hka.archive.management.assetsSystem.dao;

import com.hna.hka.archive.management.assetsSystem.model.AccountStatement;
import com.hna.hka.archive.management.assetsSystem.model.StatementOfAccessories;
import com.hna.hka.archive.management.assetsSystem.model.SysRobotErrorParts;
import com.hna.hka.archive.management.assetsSystem.model.SysRobotErrorRecords;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2022/4/20 10:19
 * 配件对账单
 */
public interface StatementOfAccessoriesMapper {


    List<StatementOfAccessories> list(Long company, Long spot, String beginDate, String endDate, String paymentType, Long errorRecordsUpkeepCost,Integer beginSize,Integer endSize, Integer pageNum, Integer pageSize);

    Map<String, Object> getCount(Long company, Long spot, String beginDate, String endDate, String paymentType, Long errorRecordsUpkeepCost, Integer beginSize, Integer endSize, Integer pageNum, Integer pageSize);

    List<HashMap<String, Object>> preview(Long company, Long spot, String beginDate, String paymentType, Long errorRecordsUpkeepCost, Integer beginSize, Integer pageNum, Integer pageSize);

    Map<String, Object> calculateTotal(Long company, Long spot, String beginDate, String endDate, String paymentType, Integer orderStatus, Integer beginSize, Integer endSize, Integer pageNum, Integer pageSize);


    StatementOfAccessories selectById(Long errorRecordsId);




    List<StatementOfAccessories> listNew(Long company, Long spot, String beginDate, String paymentType, Long errorRecordsUpkeepCost, String errorRecordsQuality ,Integer pageNum, Integer pageSize);

    Map<String, Object> getCountNew(Long company, Long spot, String beginDate, String paymentType, Long errorRecordsUpkeepCost,String errorRecordsQuality ,Integer pageNum, Integer pageSize);


    List<StatementOfAccessories> listNewNoPage(Long company, Long spot, String beginDate, String paymentType, Long errorRecordsUpkeepCost);

    int  updateByPrimaryKeySelective(StatementOfAccessories statementOfAccessories);


    int  exitSettlementState(Long errorRecordsId, Long sysUserId);

    List<SysRobotErrorRecords> getDateLikeByRobotErrorRecordList(String date);


}
