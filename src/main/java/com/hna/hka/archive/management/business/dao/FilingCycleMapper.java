package com.hna.hka.archive.management.business.dao;


import com.hna.hka.archive.management.business.model.BusinessFilingCycle;

/**
 * @Author zhang
 * @Date 2022/7/28 14:27
 */
public interface FilingCycleMapper {

    BusinessFilingCycle getCycle();


    int editFilingCycle(Long cycle);

}
