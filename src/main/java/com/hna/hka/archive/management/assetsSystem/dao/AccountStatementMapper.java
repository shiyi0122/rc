package com.hna.hka.archive.management.assetsSystem.dao;

import com.hna.hka.archive.management.assetsSystem.model.AccountClose;
import com.hna.hka.archive.management.assetsSystem.model.AccountStatement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: rc
 * @description: 流水对账单
 * @author: zhaoxianglong
 * @create: 2021-09-09 10:27
 **/
public interface AccountStatementMapper {
    List<AccountStatement> list(Long userId, Long company, Long spot, String beginDate, String endDate, String[] paymentType, Integer orderStatus, Integer pageNum, Integer pageSize, Integer beginSize, Integer endSize) throws Exception;

    Map<String, Object> getCount(Long userId, Long company, Long spot, String beginDate, String endDate, String[] paymentType, Integer orderStatus, Integer beginSize, Integer endSize) throws Exception;

    List<AccountStatement> companyList(Long userId, String[] spotId) throws Exception;

    List<AccountStatement> spotList(Long userId, Long companyId) throws Exception;

    HashMap<String, Object> preview(Long userId, Long company, Long spot, String beginDate, String endDate, String[] paymentType, Integer beginSize, Integer endSize,String paymentStatus);

    Double verify(Long userId, Long company, Long spot, String beginDate, String endDate, String[] split, Integer orderStatus,String paymentType ,Integer beginSize, Integer endSize);

    Double verifyN(Long userId, Long company, Long spot, String beginDate, String endDate, String[] split, Integer orderStatus,String paymentType ,Integer beginSize, Integer endSize);


    AccountClose getSpotIdAndCompanyIdAndDateByRunningData(Long spot, Long companyId, String month);

    int  add(AccountClose accountClose);

    int  edit(AccountClose accountClose);




//    List<HashMap<String, Object>> previewNew(Long userId, Long company, Long spot, String beginDate, String endDate, String[] split, Integer beginSize, Integer endSize, String paymentStatus);

}
