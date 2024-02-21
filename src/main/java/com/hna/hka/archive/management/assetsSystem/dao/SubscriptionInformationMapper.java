package com.hna.hka.archive.management.assetsSystem.dao;

import com.hna.hka.archive.management.assetsSystem.model.OrderMoney;
import com.hna.hka.archive.management.assetsSystem.model.SubscriptionInformation;
import com.hna.hka.archive.management.assetsSystem.model.SysScenicSpotFenfun;
import com.hna.hka.archive.management.assetsSystem.model.SysScenicSubscriptionInformationTax;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @program: rc
 * @description: 签约信息
 * @author: zhaoxianglong
 * @create: 2021-09-09 13:57
 **/
public interface SubscriptionInformationMapper {
    List<Map<String, String>> companyList(String[] spotId) throws Exception;

    List<Map<String, String>> spotList(Long companyId) throws Exception;

    List<SubscriptionInformation> list(Long companyId, String[] spotId, BigDecimal charge, BigDecimal tax, Integer pageNum, Integer pageSize,String beginDate,String endDate) throws Exception;

    Integer add(SubscriptionInformation subscriptionInformation) throws Exception;

    Integer edit(SubscriptionInformation subscriptionInformation) throws Exception;

    Integer delete(Long id) throws Exception;

    List<Map<String, String>> allCompany(@Param("spotId") String spotId) throws Exception;

    List<Map<String, String>> allSpot() throws Exception;

    List<Map<String, String>> allSubject() throws Exception;

    SubscriptionInformation queryById(Long id) throws Exception;

    Integer getCount(Long companyId, String[] spotId, BigDecimal charge, BigDecimal tax);

    List<SubscriptionInformation> selectSpotIdByContract(Long spotId);

    Map selectJxzyCompanyId();


    List<Map<String, String>> companyListAll();

    List<SubscriptionInformation> selectAll();


    List<Map<String, String>> getSpotIdCompanyIdData(Long spotId, Long companyId);

    List<OrderMoney> getStatementSummaryList(String subjectId, String companyId, String spotId);

    List<SysScenicSubscriptionInformationTax> getTaxRateFormula();


    SubscriptionInformation selectCompanyNameById(String cooperation);

}
