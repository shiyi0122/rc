package com.hna.hka.archive.management.assetsSystem.service;

import com.hna.hka.archive.management.assetsSystem.model.SubscriptionInformation;
import com.hna.hka.archive.management.assetsSystem.model.SysScenicSubscriptionInformationTax;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @program: rc
 * @description: 签约信息
 * @author: zhaoxianglong
 * @create: 2021-09-09 13:56
 **/
public interface SubscriptionInformationService {
    List<Map<String, String>> companyList(String spotId) throws Exception;

    List<Map<String, String>> spotList(Long companyId) throws Exception;

    List<SubscriptionInformation> list(Long companyId, String spotId, BigDecimal charge, BigDecimal tax, Integer pageNum, Integer pageSize,String beginDate,String endDate) throws Exception;

    Integer add(SubscriptionInformation subscriptionInformation) throws Exception;

    Integer edit(SubscriptionInformation subscriptionInformation) throws Exception;

    Integer delete(Long id) throws Exception;

    List<Map<String, String>> allCompany(String spotId) throws Exception;

    List<Map<String, String>> allSpot() throws Exception;

    List<Map<String, String>> allSubject() throws Exception;

    SubscriptionInformation queryById(Long id) throws Exception;

    Integer getCount(Long companyId, String spotId, BigDecimal charge, BigDecimal tax) throws Exception;

    List<Map<String, String>> companyListAll();


    List<Map<String, String>> getSpotIdCompanyIdData(Long spotId, Long companyId);


    List<SysScenicSubscriptionInformationTax> getTaxRateFormula();


}
