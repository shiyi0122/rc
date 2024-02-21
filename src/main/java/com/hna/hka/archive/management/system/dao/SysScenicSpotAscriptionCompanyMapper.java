package com.hna.hka.archive.management.system.dao;

import com.hna.hka.archive.management.system.model.SysScenicSpotAscriptionCompany;

import java.util.List;
import java.util.Map;

public interface SysScenicSpotAscriptionCompanyMapper {
    int deleteByPrimaryKey(Long companyId);

    int insert(SysScenicSpotAscriptionCompany record);

    int insertSelective(SysScenicSpotAscriptionCompany record);

    SysScenicSpotAscriptionCompany selectByPrimaryKey(Long companyId);

    int updateByPrimaryKeySelective(SysScenicSpotAscriptionCompany record);

    int updateByPrimaryKey(SysScenicSpotAscriptionCompany record);

    List<SysScenicSpotAscriptionCompany> getScenicSpotBindingList(Map<String, Object> search);

    //模糊查询根据景区名称
    SysScenicSpotAscriptionCompany selectComponyNameById(String ascriptionCompanyName);

}