package com.hna.hka.archive.management.assetsSystem.service.impl;

import com.hna.hka.archive.management.assetsSystem.dao.SubscriptionInformationMapper;
import com.hna.hka.archive.management.assetsSystem.model.SubscriptionInformation;
import com.hna.hka.archive.management.assetsSystem.model.SysScenicSubscriptionInformationTax;
import com.hna.hka.archive.management.assetsSystem.service.SubscriptionInformationService;
import com.hna.hka.archive.management.system.util.IdUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @program: rc
 * @description: 签约信息
 * @author: zhaoxianglong
 * @create: 2021-09-09 13:56
 **/
@Service
public class SubscriptionInformationServiceImpl implements SubscriptionInformationService {

    @Autowired
    SubscriptionInformationMapper mapper;

    @Override
    public List<Map<String, String>> companyList(String spotId) throws Exception{
        String[] split = (spotId != null ? spotId.split(",") : null);
        List<Map<String, String>> list = mapper.companyList(split);
        if (list.size()>0){
            return mapper.companyList(split);
        }else{
            List<Map<String, String>> list1 = mapper.companyList(null);
            return list1;
        }

    }

    @Override
    public List<Map<String, String>> spotList(Long companyId)  throws Exception{
        return mapper.spotList(companyId);
    }

    @Override
    public List<SubscriptionInformation> list(Long companyId, String spotId, BigDecimal charge, BigDecimal tax, Integer pageNum, Integer pageSize,String beginDate,String endDate) throws Exception {
        String[] split = (spotId != null ? spotId.split(",") : null);
        return mapper.list(companyId ,  split , charge , tax , pageNum , pageSize,beginDate,endDate);
    }

    @Override
    public Integer add(SubscriptionInformation subscriptionInformation) throws Exception {
        subscriptionInformation.setId(IdUtils.getSeqId());
        return mapper.add(subscriptionInformation);
    }

    @Override
    public Integer edit(SubscriptionInformation subscriptionInformation) throws Exception {
        return mapper.edit(subscriptionInformation);
    }

    @Override
    public Integer delete(Long id) throws Exception {
        return mapper.delete(id);
    }

    @Override
    public List<Map<String, String>> allCompany(String spotId) throws Exception {
        return mapper.allCompany(spotId);
    }

    @Override
    public List<Map<String, String>> allSpot() throws Exception {
        return mapper.allSpot();
    }

    @Override
    public List<Map<String, String>> allSubject() throws Exception {
        return mapper.allSubject();
    }

    @Override
    public SubscriptionInformation queryById(Long id) throws Exception{
        return mapper.queryById(id);
    }

    @Override
    public Integer getCount(Long companyId, String spotId, BigDecimal charge, BigDecimal tax) throws Exception {
        String[] split = (spotId != null ? spotId.split(",") : null);
        return mapper.getCount(companyId , split , charge , tax);
    }

    @Override
    public List<Map<String, String>> companyListAll() {

        List<Map<String, String>> list =  mapper.companyListAll();

        return list;
    }

    @Override
    public List<Map<String, String>> getSpotIdCompanyIdData(Long spotId, Long companyId) {

        List<Map<String, String>> list  = mapper.getSpotIdCompanyIdData(spotId,companyId);

        return list;

    }

    /**
     * 获取利率公式
     * @return
     */
    @Override
    public List<SysScenicSubscriptionInformationTax> getTaxRateFormula() {

        List<SysScenicSubscriptionInformationTax> list = mapper.getTaxRateFormula();
        return list;
    }
}
