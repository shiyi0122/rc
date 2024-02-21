package com.hna.hka.archive.management.business.dao;

import com.hna.hka.archive.management.business.model.BusinessAuction;

import java.util.List;
import java.util.Map;

public interface BusinessAuctionMapper {
    int deleteByPrimaryKey(Long id);

    int insert(BusinessAuction record);

    int insertSelective(BusinessAuction record);

    BusinessAuction selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BusinessAuction record);

    int updateByPrimaryKeyWithBLOBs(BusinessAuction record);

    int updateByPrimaryKey(BusinessAuction record);

    List<BusinessAuction> getAuctionList(Map<String, String> search);
}