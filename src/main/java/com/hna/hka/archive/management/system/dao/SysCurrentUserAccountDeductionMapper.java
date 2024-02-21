package com.hna.hka.archive.management.system.dao;

import com.hna.hka.archive.management.system.model.SysCurrentUserAccountDeduction;

import java.util.List;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2022/12/1 16:16
 */
public interface SysCurrentUserAccountDeductionMapper {


    int add(SysCurrentUserAccountDeduction sysCurrentUserAccountDeduction);


    List<SysCurrentUserAccountDeduction> list(Map<String, Object> search);

}
