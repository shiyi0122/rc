package com.hna.hka.archive.management.business.service;


import com.hna.hka.archive.management.business.model.BusinessFilingSpot;
import com.hna.hka.archive.management.system.util.PageDataResult;

import java.util.List;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2023/3/15 11:23
 */
public interface BusinessFilingSpotService {

    PageDataResult getFilingSpotList(Integer pageNum, Integer pageSize, Map sarch);


    int addFilingSpot(BusinessFilingSpot businessFilingSpot);


    int editFilingSpot(BusinessFilingSpot businessFilingSpot);

    int delFilingSpot(Long id);
}
