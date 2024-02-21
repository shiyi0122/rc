package com.hna.hka.archive.management.business.dao;

import com.hna.hka.archive.management.business.model.BusinessArticle;

import java.util.List;
import java.util.Map;

public interface BusinessArticleMapper {
    int deleteByPrimaryKey(Long id);

    int insert(BusinessArticle record);

    int insertSelective(BusinessArticle record);

    BusinessArticle selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BusinessArticle record);

    int updateByPrimaryKeyWithBLOBs(BusinessArticle record);

    int updateByPrimaryKey(BusinessArticle record);

    List<BusinessArticle> getArticleList(Map<String, String> search);
}