package com.hna.hka.archive.management.business.dao;

import com.hna.hka.archive.management.business.model.BusinessDurationOption;

import java.util.List;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2023/5/19 16:55
 */
public interface BusinessDurationOptionMapper {

    List<BusinessDurationOption> getDurationOptionList(Map<String, String> search);


    int insertSelective(BusinessDurationOption durationOption);

    int updateByPrimaryKeySelective(BusinessDurationOption durationOption);

    int delDurationOption(Long id);

}
