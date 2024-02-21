package com.hna.hka.archive.management.assetsSystem.service;

import com.hna.hka.archive.management.assetsSystem.model.AccountStatement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: rc
 * @description: 流水对账单
 * @author: zhaoxianglong
 * @create: 2021-09-09 10:25
 **/
public interface AccountStatementService {
    List<AccountStatement> list(Long company, Long spot, String beginDate, String endDate, String paymentType, Integer orderStatus, Integer pageNum, Integer pageSize) throws Exception;

    Map<String, Object> getCount(Long company, Long spot, String beginDate, String endDate, String paymentType, Integer orderStatus) throws Exception;

    List<AccountStatement> companyList(String spotId) throws Exception;

    List<AccountStatement> spotList(Long companyId) throws Exception;

    HashMap<String, Object> preview(Long company, Long spot, String beginDate, String endDate, String paymentType,String paymentStatus);

    int verify(Long company, Long spot, String beginDate, String endDate, String paymentType, Integer orderStatus,Long userId,String userName);

    int isVerify(Long company, Long spot, String beginDate, String endDate, String paymentType, Integer orderStatus);




//    List<HashMap<String, Object>> previewNew(Long company, Long spot, String beginDate, String endDate, String paymentType,String paymentStatus);

}
