package com.hna.hka.archive.management.system.service.impl;

import com.hna.hka.archive.management.assetsSystem.dao.RealTimeMapper;
import com.hna.hka.archive.management.system.dao.SysOrderMapper;
import com.hna.hka.archive.management.system.dao.SysScenicSpotMapper;
import com.hna.hka.archive.management.system.model.SysScenicSpot;
import com.hna.hka.archive.management.system.service.RealTimeService;
import com.hna.hka.archive.management.system.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * @program: rc
 * @description:
 * @author: zhaoxianglong
 * @create: 2021-10-29 14:50
 **/
@Service
public class RealTimeServiceImpl implements RealTimeService {

    @Autowired
    RealTimeMapper mapper;
    @Autowired
    SysScenicSpotMapper sysScenicSpotMapper;
    @Autowired
    SysOrderMapper sysOrderMapper;

    @Override
    public List<HashMap> line(Long spotId) {

        return mapper.line(spotId);

    }

    @Override
    public HashMap<String, Object> lineN(String spotId, String startTime, String endTime, String time, String type, String multipeDate) {
        HashMap<String, Object> returnMap = new HashMap<>();

        List<HashMap> spotList = new ArrayList<>();
        HashMap<String, Object> map = new HashMap<>();
        if ("1".equals(type)) {//多景区一天

            if (StringUtils.isEmpty(spotId)) {
                List<String> nameList = new ArrayList<>();
                Double drysMax = 0d;
                Integer jxzMax = 0;
                List<Double> drys = mapper.lineDRYS(null, time);
                map = new HashMap<>();
                map.put("name", "营收(元)");
                map.put("type", "line");
                map.put("yAxislndex", 0);
                map.put("data", drys);
                spotList.add(map);
                nameList.add((String) map.get("name"));

                map = new HashMap<>();
                List<Integer> jxz = mapper.lineJXZ(null, time);
                map.put("type", "line");
                map.put("yAxislndex", 1);
                map.put("name", "在线订单(条)");
                map.put("data", jxz);
                spotList.add(map);
                nameList.add((String) map.get("name"));

                //获取时间
                List<String> dateList = mapper.lineDate(time);
                //收入最大值
                if (drys.size() > 0) {
                    drysMax = Collections.max(drys);
                }
                //条数最大值
                if (jxz.size() > 0) {
                    jxzMax = Collections.max(jxz);
                }

                returnMap.put("lineList", spotList);
                returnMap.put("dateList", dateList);
                returnMap.put("drysMax", drysMax);
                returnMap.put("jxzMax", jxzMax);
                returnMap.put("nameList", nameList);

                //订单总数
                Map<String, Object> search = new HashMap<>();

                search.put("startTime", startTime);
                search.put("endTime", DateUtil.addDay(endTime, 1));

                search.put("orderScenicSpotId", spotId);
                int orderList = sysOrderMapper.getTotle(search);
                returnMap.put("totle", orderList);

                return returnMap;

            } else {
                List<String> nameList = new ArrayList<>();
                Double drysMax = 0d;
                Integer jxzMax = 0;
                String[] spot = spotId.split(",");
                for (String spotId1 : spot) {
                    SysScenicSpot sysScenicSpot = sysScenicSpotMapper.selectByPrimaryKey(Long.parseLong(spotId1));
                    List<Double> drys = mapper.lineDRYS(spotId1, time);
                    map = new HashMap<>();
                    map.put("name", sysScenicSpot.getScenicSpotName() + "-营收(元)");
                    map.put("type", "line");
                    map.put("yAxislndex", 0);
                    map.put("data", drys);
                    spotList.add(map);
                    nameList.add((String) map.get("name"));

                    map = new HashMap<>();
                    List<Integer> jxz = mapper.lineJXZ(spotId1, time);
                    map.put("type", "line");
                    map.put("yAxislndex", 1);
                    map.put("name", sysScenicSpot.getScenicSpotName() + "-在线订单(条)");
                    map.put("data", jxz);
                    nameList.add((String) map.get("name"));

                    //收入最大值
                    Double drysMax1 = Collections.max(drys);

                    //条数最大值
                    Integer jxzMax1 = Collections.max(jxz);
                    if (drysMax1 > drysMax) {
                        drysMax = drysMax1;
                    }
                    if (jxzMax1 > jxzMax) {
                        jxzMax = jxzMax1;
                    }

                    spotList.add(map);
                }
                //获取时间
                List<String> dateList = mapper.lineDate(time);

                returnMap.put("lineList", spotList);
                returnMap.put("dateList", dateList);
                returnMap.put("drysMax", drysMax);
                returnMap.put("jxzMax", jxzMax);
                returnMap.put("nameList", nameList);

                //订单总数
                Map<String, Object> search = new HashMap<>();

                search.put("startTime", startTime);
                search.put("endTime", DateUtil.addDay(endTime, 1));

                search.put("time", time);
                int orderList = 0;
                String[] spot1 = spotId.split(",");
                for (int i = 0; i < spot1.length; i++) {
                    search.put("orderScenicSpotId", spot1[i]);
                    orderList += sysOrderMapper.getTotle(search);
                }
                returnMap.put("totle", orderList);

                return returnMap;
            }

        } else if ("2".equals(type)) {//一景区多天(多选天数)

            try {
                List<String> nameList = new ArrayList<>();
                Double drysMax = 0d;
                Integer jxzMax = 0;
                String[] list = multipeDate.split(",");

                for (String date : list) {

                    List<Double> drys = mapper.lineDRYS(spotId, date);
                    map = new HashMap<>();
                    map.put("name", date + "-营收(元)");
                    map.put("type", "line");
                    map.put("yAxislndex", 0);
                    map.put("data", drys);
                    spotList.add(map);
                    nameList.add((String) map.get("name"));

                    map = new HashMap<>();
                    List<Integer> jxz = mapper.lineJXZ(spotId, date);
                    map.put("name", date + "-在线订单(条)");
                    map.put("type", "line");
                    map.put("yAxislndex", 1);
                    map.put("data", jxz);
                    spotList.add(map);
                    nameList.add((String) map.get("name"));

                    //收入最大值
                    Double drysMax1 = 0d;
                    if (drys.size() > 0) {
                        drysMax1 = Collections.max(drys);
                    }
                    Integer jxzMax1 = 0;
                    if (jxz.size() > 0) {
                        jxzMax1 = Collections.max(jxz);
                    }

                    //条数最大值
                    if (drysMax1 > drysMax) {
                        drysMax = drysMax1;
                    }
                    if (jxzMax1 > jxzMax) {
                        jxzMax = jxzMax1;
                    }

                }
                //获取时间时分秒
                List<String> dateList = mapper.hourMinuteAndSecond();
                returnMap.put("drysMax", drysMax);
                returnMap.put("jxzMax", jxzMax);
                returnMap.put("lineList", spotList);
                returnMap.put("dateList", dateList);
                returnMap.put("nameList", nameList);

                //订单总数
                Map<String, Object> search = new HashMap<>();

                search.put("startTime", startTime);
                search.put("endTime", DateUtil.addDay(endTime, 1));
                search.put("orderScenicSpotId", spotId);
                int orderList = 0;
                for (String date : list) {
                    search.put("time", date);
                    orderList += sysOrderMapper.getTotle(search);
                }
                returnMap.put("totle", orderList);

                return returnMap;
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if ("0".equals(type)) {//一景区多天(时间段)

            try {
                List<String> nameList = new ArrayList<>();
                Double drysMax = 0d;
                Integer jxzMax = 0;
                List<String> list = DateUtil.betweenDays(startTime, endTime);
                for (String date : list) {

                    List<Double> drys = mapper.lineDRYS(spotId, date);
                    map = new HashMap<>();
                    map.put("name", date + "-营收(元)");
                    map.put("type", "line");
                    map.put("yAxislndex", 0);
                    map.put("data", drys);
                    spotList.add(map);
                    nameList.add((String) map.get("name"));

                    map = new HashMap<>();
                    List<Integer> jxz = mapper.lineJXZ(spotId, date);
                    map.put("name", date + "-在线订单(条)");
                    map.put("type", "line");
                    map.put("yAxislndex", 1);
                    map.put("data", jxz);
                    spotList.add(map);
                    nameList.add((String) map.get("name"));

                    //收入最大值
                    Double drysMax1 = 0d;
                    if (drys.size() > 0) {
                        drysMax1 = Collections.max(drys);
                    }
                    Integer jxzMax1 = 0;
                    if (jxz.size() > 0) {
                        jxzMax1 = Collections.max(jxz);
                    }
                    //条数最大值

                    if (drysMax1 > drysMax) {
                        drysMax = drysMax1;
                    }
                    if (jxzMax1 > jxzMax) {
                        jxzMax = jxzMax1;
                    }

                }
                //获取时间时分秒
                List<String> dateList = mapper.hourMinuteAndSecond();
                returnMap.put("drysMax", drysMax);
                returnMap.put("jxzMax", jxzMax);
                returnMap.put("lineList", spotList);
                returnMap.put("dateList", dateList);
                returnMap.put("nameList", nameList);

                //订单总数
                Map<String, Object> search = new HashMap<>();

                search.put("startTime", startTime);
                search.put("endTime", DateUtil.addDay(endTime, 1));
                search.put("orderScenicSpotId", spotId);
                search.put("time", time);
                int orderList = sysOrderMapper.getTotle(search);
                returnMap.put("totle", orderList);

                return returnMap;
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        //订单总数
        Map<String, Object> search = new HashMap<>();
        if (StringUtils.isEmpty(startTime) && StringUtils.isEmpty(endTime)) {
            search.put("startTime", startTime);
            search.put("endTime", DateUtil.addDay(endTime, 1));
        }
        search.put("orderScenicSpotId", spotId);
        search.put("time", time);
        int orderList = sysOrderMapper.getTotle(search);
        returnMap.put("totle", orderList);
        return returnMap;
    }
}
