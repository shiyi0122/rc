package com.hna.hka.archive.management.assetsSystem.service;

import java.util.Map;

/**
 * @Author zhang
 * @Date 2023/2/11 10:01
 */
public interface PeopleCountingService {

    Map<String, Object> getPeopleCountingList(Long spotId, String beginDate, String endDate);

}
