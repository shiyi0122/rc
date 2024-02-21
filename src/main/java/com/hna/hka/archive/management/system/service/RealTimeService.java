package com.hna.hka.archive.management.system.service;

import java.util.HashMap;
import java.util.List;

/**
 * @program: rc
 * @description:
 * @author: zhaoxianglong
 * @create: 2021-10-29 14:50
 **/
public interface RealTimeService {
    List<HashMap> line(Long spotId);

    HashMap<String, Object>  lineN(String spotId, String startTime, String endTime, String time, String type,String multipeDate);



}
