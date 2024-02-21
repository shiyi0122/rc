package com.hna.hka.archive.management.business.service;

import com.hna.hka.archive.management.business.model.BusinessDurationOption;
import com.hna.hka.archive.management.system.util.PageDataResult;

import java.util.Map;

/**
 * @Author zhang
 * @Date 2023/5/19 16:54
 */
public interface BusinessDurationOptionService {


    PageDataResult getDurationOptionList(Integer pageNum, Integer pageSize, Map<String, String> search);

    int addDurationOption(BusinessDurationOption durationOption);

    int editDurationOption(BusinessDurationOption durationOption);

    int delDurationOption(BusinessDurationOption durationOption);


}
