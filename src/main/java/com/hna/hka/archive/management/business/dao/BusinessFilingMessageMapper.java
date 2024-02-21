package com.hna.hka.archive.management.business.dao;

import com.hna.hka.archive.management.business.model.BusinessFilingMessage;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2022/9/21 16:21
 */
public interface BusinessFilingMessageMapper {


    List<BusinessFilingMessage> getFilingMessageList(Map<String, Object> search);

    BusinessFilingMessage selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BusinessFilingMessage businessFilingMessage);

    int insertSelective(BusinessFilingMessage businessFilingMessage);


    List<BusinessFilingMessage> getSpotNameFilingMessage(@Param("filingSpotName")String filingSpotName);


}
