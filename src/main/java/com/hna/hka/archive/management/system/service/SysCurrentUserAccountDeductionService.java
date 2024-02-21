package com.hna.hka.archive.management.system.service;

import com.hna.hka.archive.management.system.model.SysCurrentUserAccountDeduction;
import com.hna.hka.archive.management.system.util.PageDataResult;

import java.util.List;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2022/12/1 16:05
 */
public interface SysCurrentUserAccountDeductionService {

    int add(SysCurrentUserAccountDeduction sysCurrentUserAccountDeduction);

    PageDataResult getCurrentUserAccountDeductionList(Integer pageNum, Integer pageSize, Map<String, Object> search);


    List<SysCurrentUserAccountDeduction> uploadExceluserPhoneLog(Map<String, Object> search);



}
