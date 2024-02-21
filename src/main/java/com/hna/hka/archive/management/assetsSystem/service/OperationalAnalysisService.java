package com.hna.hka.archive.management.assetsSystem.service;

import com.hna.hka.archive.management.assetsSystem.model.ScenicSpot;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @program: rc
 * @description:
 * @author: zhaoxianglong
 * @create: 2021-09-27 10:43
 **/
public interface OperationalAnalysisService {
    HashMap<String, ArrayList> timeAnalysis(Integer type, String beginDate, String endDate) throws ParseException;

    List fitList();

    Integer fitAdd(Integer begin, Integer end);

    Integer fitEdit(Long id, Integer begin, Integer end);

    Integer fitDelete(Long id);

    HashMap<String, ArrayList> amountAnalysis(Integer type, String beginDate, String endDate) throws ParseException;

    HashMap<String, List> fitRevenue(Integer type, String beginDate, String endDate,String spotId,String provinceId) throws ParseException;

    List<ScenicSpot>  spotList(String provinceId);


    List<ScenicSpot> provinceList();


}
