package com.hna.hka.archive.management.assetsSystem.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hna.hka.archive.management.assetsSystem.dao.EquipmentAttendanceStatisticsMapper;
import com.hna.hka.archive.management.assetsSystem.model.EquipmentAttendanceStatistics;
import com.hna.hka.archive.management.assetsSystem.model.SpotGrossProfitMargin;
import com.hna.hka.archive.management.assetsSystem.service.EquipmentAttendanceStatisticsService;
import com.hna.hka.archive.management.system.dao.SysOrderMapper;
import com.hna.hka.archive.management.system.dao.SysRobotMapper;
import com.hna.hka.archive.management.system.dao.SysScenicSpotMapper;
import com.hna.hka.archive.management.system.model.SysOrder;
import com.hna.hka.archive.management.system.model.SysScenicSpot;
import com.hna.hka.archive.management.system.util.DateUtil;
import com.hna.hka.archive.management.system.util.PageDataResult;
import org.apache.shiro.crypto.hash.Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.*;

@Service
public class EquipmentAttendanceStatisticsServiceImpl implements EquipmentAttendanceStatisticsService {

    @Autowired
    private EquipmentAttendanceStatisticsMapper equipmentAttendanceStatisticsMapper;
    @Autowired
    private SysScenicSpotMapper sysScenicSpotMapper;

    @Autowired
    private SysOrderMapper sysOrderMapper;
    @Autowired
    private SysRobotMapper sysRobotMapper;



    @Override
    public PageDataResult list(EquipmentAttendanceStatistics equipmentAttendanceStatistics, Integer pageNum, Integer pageSize) {
        PageDataResult pageDataResult=new PageDataResult();
        PageHelper.startPage(pageNum, pageSize);
        Map<String, Object> map = new HashMap<>();

        String startTime=null,endTime=null;
        if (equipmentAttendanceStatistics.getDateType().equals("1")){//按年计算
            startTime = equipmentAttendanceStatistics.getStartTime();
            endTime = equipmentAttendanceStatistics.getEndTime();
            startTime = startTime+"-01"+"-01";
            endTime=endTime+"-12"+"-31";
            equipmentAttendanceStatistics.setStartTime(startTime);
            equipmentAttendanceStatistics.setEndTime(endTime);
        }else if (equipmentAttendanceStatistics.getDateType().equals("2")){
            startTime = equipmentAttendanceStatistics.getStartTime();
            endTime = equipmentAttendanceStatistics.getEndTime();
            startTime=startTime+"-01";
            int monthDay = DateUtil.getMonthDay(endTime);
            endTime=endTime+"-"+monthDay;
            equipmentAttendanceStatistics.setStartTime(startTime);
            equipmentAttendanceStatistics.setEndTime(endTime);

        }

        List<Map> lists = new ArrayList<>();

        List<EquipmentAttendanceStatistics> list = new ArrayList<>();

        List<EquipmentAttendanceStatistics> spotList = equipmentAttendanceStatisticsMapper.spotList(equipmentAttendanceStatistics);
        for (EquipmentAttendanceStatistics spot:spotList){
            equipmentAttendanceStatistics.setScenicSpotId(spot.getScenicSpotId());
            equipmentAttendanceStatistics.setScenicSpotName(spot.getScenicSpotName());


            int equipmentNum=0,equipmentOperatingNum=0;
             list = equipmentAttendanceStatisticsMapper.list(equipmentAttendanceStatistics);

            if (list.size() <= 0){


                continue;
            }

            for (EquipmentAttendanceStatistics attendanceStatistics:list){
                 equipmentOperatingNum+= Integer.parseInt(attendanceStatistics.getEquipmentOperatingNum());

                Long robotCount = sysRobotMapper.getSpotIdByRobotCount(Long.parseLong(spot.getScenicSpotId()));

                equipmentNum += robotCount;
                 attendanceStatistics.setEquipmentNum(robotCount.toString());

            }
            int  days=0,operationDays=0;

            EquipmentAttendanceStatistics attendanceStatistics = list.get(0);
            String s = DateUtil.currentDateTime();
            try {
                attendanceStatistics.setOpeningDays(DateUtil.findDates(attendanceStatistics.getFormalOperationTime(),s));
                days = Integer.parseInt(DateUtil.findDates(startTime, endTime));
                List<String> dates = DateUtil.betweenDays(startTime, endTime);
                for (int i=0;i<dates.size();i++){
                    equipmentAttendanceStatistics.setStartTime(dates.get(i));
                    equipmentAttendanceStatistics.setEndTime(dates.get(i+1));
                    int attendanceDay = equipmentAttendanceStatisticsMapper.selAttendance(equipmentAttendanceStatistics);
                    if (attendanceDay==0){
                        int orderDay = equipmentAttendanceStatisticsMapper.selOrder(equipmentAttendanceStatistics);
                        operationDays+=orderDay;
                    }else {
                        operationDays+=attendanceDay;
                    }
                }

            } catch (ParseException e) {
                e.printStackTrace();
            }
            equipmentAttendanceStatistics.setScenicSpotNameOperatingRate((double)operationDays/days);
            equipmentAttendanceStatistics.setOperationDays(String.valueOf(operationDays));
            equipmentAttendanceStatistics.setEquipmenOperatingRate((double) equipmentOperatingNum/equipmentNum);
            map.put(spot.getScenicSpotId(),equipmentAttendanceStatistics);
            lists.add(map);
        }

        if (list.size() > 0){
            PageInfo<EquipmentAttendanceStatistics> pageInfo = new PageInfo<>(list);
            pageDataResult.setList(pageInfo.getList());
            pageDataResult.setTotals((int) pageInfo.getTotal());
        }


        return pageDataResult;
    }

    @Override
    public List<Map>  listNew(EquipmentAttendanceStatistics equipmentAttendanceStatistics, Integer pageNum, Integer pageSize) {
        PageDataResult pageDataResult=new PageDataResult();
//        PageHelper.startPage(pageNum, pageSize);
        Map<String, Object> map = new HashMap<>();


        String startTime=null,endTime=null;
        if (equipmentAttendanceStatistics.getDateType().equals("1")){//按年计算
            startTime = equipmentAttendanceStatistics.getStartTime();
            endTime = equipmentAttendanceStatistics.getEndTime();
            startTime = startTime+"-01"+"-01";
            endTime=endTime+"-12"+"-31";
            equipmentAttendanceStatistics.setStartTime(startTime);
            equipmentAttendanceStatistics.setEndTime(endTime);
        }else if (equipmentAttendanceStatistics.getDateType().equals("2")){
            startTime = equipmentAttendanceStatistics.getStartTime();
            endTime = equipmentAttendanceStatistics.getEndTime();
            startTime=startTime+"-01";
            int monthDay = DateUtil.getMonthDay(endTime);
            endTime=endTime+"-"+monthDay;
            equipmentAttendanceStatistics.setStartTime(startTime);
            equipmentAttendanceStatistics.setEndTime(endTime);

        }

        List<Map> lists = new ArrayList<>();

        List<EquipmentAttendanceStatistics> list = new ArrayList<>();

        List<EquipmentAttendanceStatistics> spotList = equipmentAttendanceStatisticsMapper.spotList(equipmentAttendanceStatistics);
        for (EquipmentAttendanceStatistics spot:spotList){

            map = new HashMap<>();

            equipmentAttendanceStatistics.setScenicSpotId(spot.getScenicSpotId());
            equipmentAttendanceStatistics.setScenicSpotName(spot.getScenicSpotName());
            int equipmentNum=0,equipmentOperatingNum=0;
            list = equipmentAttendanceStatisticsMapper.list(equipmentAttendanceStatistics);

            if (list.size() <= 0){

                map.put(spot.getScenicSpotId(),null);
                lists.add(map);


                continue;
            }

            for (EquipmentAttendanceStatistics attendanceStatistics:list){
                equipmentOperatingNum+= Integer.parseInt(attendanceStatistics.getEquipmentOperatingNum());

                Long robotCount = sysRobotMapper.getSpotIdByRobotCount(Long.parseLong(spot.getScenicSpotId()));

                equipmentNum += robotCount;
                attendanceStatistics.setEquipmentNum(robotCount.toString());

            }
            int  days=0,operationDays=0;

            EquipmentAttendanceStatistics attendanceStatistics = list.get(0);
            String s = DateUtil.currentDateTime();
            try {

                attendanceStatistics.setOpeningDays(DateUtil.findDates(attendanceStatistics.getCreateDate(),s));
                days = Integer.parseInt(DateUtil.findDates(startTime, endTime));
                List<String> dates = DateUtil.betweenDays(startTime, endTime);
                for (int i=0;i<dates.size();i++){
                    if (i+1==dates.size()){
                        break;
                    }
                    equipmentAttendanceStatistics.setStartTime(dates.get(i));
                    equipmentAttendanceStatistics.setEndTime(dates.get(i+1));
                    int attendanceDay = equipmentAttendanceStatisticsMapper.selAttendance(equipmentAttendanceStatistics);
                    if (attendanceDay==0){
                        int orderDay = equipmentAttendanceStatisticsMapper.selOrder(equipmentAttendanceStatistics);
                        operationDays+=orderDay;
                    }else {
                        operationDays+=attendanceDay;
                    }
                }

            } catch (ParseException e) {
                e.printStackTrace();
            }
            equipmentAttendanceStatistics.setScenicSpotNameOperatingRate((double)operationDays/days);
            equipmentAttendanceStatistics.setOperationDays(String.valueOf(operationDays));
            equipmentAttendanceStatistics.setEquipmenOperatingRate((double) equipmentOperatingNum/equipmentNum);
            map.put(spot.getScenicSpotId(),equipmentAttendanceStatistics);
            lists.add(map);
        }




        return lists;
    }






    @Override
    public List<Map<String, Object>> pieChartList(String type,String startTime,String endTime,String spotId) {

        List<Map<String,Object>> list = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> search = new HashMap<>();
        try {
            if (StringUtils.isEmpty(spotId)){

                Integer i = sysScenicSpotMapper.getScenicSpotOperateNum(1);
                Integer k = sysScenicSpotMapper.getScenicSpotOperateNum(2);
                Integer j = sysScenicSpotMapper.getScenicSpotOperateNum(3);

                map.put("name","已运营");
                map.put("value",i);

                list.add(map);
                map = new HashMap<>();
                map.put("name","预运营");
                map.put("value",k);
                list.add(map);
                map = new HashMap<>();
                map.put("name","测试景区");
                map.put("value",j);
                list.add(map);

                return list;

            }else{
                if ("1".equals(type)){

                    if(startTime.equals(endTime)){
                        startTime = startTime+"-01";
                        endTime = endTime+"-12";
                        List<String> months = DateUtil.betweenMonths(startTime, endTime);
                        int i =0;
                        int j =0;
                        for (String month : months) {
                            search.put("date",month);
                            search.put("spotId",spotId);
                            int orderRobotCount = sysOrderMapper.getOrderRobotCount(search);
                            if (orderRobotCount>0){
                                i++;
                            }else{
                                j++;
                            }
                        }
                        map.put("name","运营月数");
                        map.put("value",i);
                        list.add(map);
                        map= new HashMap<>();
                        map.put("name","未运营月数");
                        map.put("value",j);
                        list.add(map);

                        return list;

                    }else{
                        List<String> years = DateUtil.betweenYears(startTime, endTime);
                        int i =0;
                        int j =0;
                        String yearS;
                        String yearE;
                        for (String year : years) {
                            yearS = year+"-01";
                            yearE = year+"-12";
                            List<String> months = DateUtil.betweenMonths(yearS, yearE);
                            for (String month : months) {
                                search.put("date",month);
                                search.put("spotId",spotId);
                                int orderRobotCount = sysOrderMapper.getOrderRobotCount(search);
                                if (orderRobotCount>0){
                                    i++;
                                }else{
                                    j++;
                                }
                            }

                        }
                        map.put("name","运营月数");
                        map.put("value",i);
                        list.add(map);
                        map= new HashMap<>();
                        map.put("name","未运营月数");
                        map.put("value",j);
                        list.add(map);

                        return  list;

                    }

                }else if ("2".equals(type)){

                    if (startTime.equals(endTime)){
                        startTime=startTime+"-01";
                        int monthDay = DateUtil.getMonthDay(endTime);
                        endTime=endTime+"-"+monthDay;
                        List<String> dateList = DateUtil.betweenDays(startTime, endTime);
                        int i =0;
                        int j =0;
                        for (String s : dateList) {
                            search.put("date",s);
                            search.put("spotId",spotId);
                            int orderRobotCount = sysOrderMapper.getOrderRobotCount(search);
                            if (orderRobotCount > 0){
                                i++;
                            }else{
                                j++;
                            }
                        }

                        map.put("name","运营天数");
                        map.put("value",i);
                        list.add(map);
                        map= new HashMap<>();
                        map.put("name","未运营天数");
                        map.put("value",j);
                        list.add(map);

                        return list ;

                    }else{

                        List<String> months = DateUtil.betweenMonths(startTime, endTime);
                        int i =0;
                        int j =0;
                        String dateS;
                        String dateE;
                        for (String month : months) {
                            dateS = month+"-01";
                            int monthDay = DateUtil.getMonthDay(month);
                            dateE = month+"-"+monthDay;
                            List<String> dateList = DateUtil.betweenDays(dateS, dateE);
                            for (String s : dateList) {
                                search.put("date",s);
                                search.put("spotId",spotId);
                                int orderRobotCount = sysOrderMapper.getOrderRobotCount(search);
                                if (orderRobotCount > 0){
                                    i++;
                                }else{
                                    j++;
                                }
                            }
                        }

                        map.put("name","运营天数");
                        map.put("value",i);
                        list.add(map);
                        map= new HashMap<>();
                        map.put("name","未运营天数");
                        map.put("value",j);
                        list.add(map);

                        return list;
                    }

                }

            }
        }catch (Exception e){

            e.printStackTrace();

        }

        return list;
    }

    @Override
    public List<Map<String, Object>> histogramList(String type, String startTime, String endTime, String spotId,String sort) {

        ArrayList<Map<String,Object>> list = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        DecimalFormat df = new DecimalFormat("0.00");
        try {
            if (StringUtils.isEmpty(spotId)) {
                if ("1".equals(type)) {

                    List<SysScenicSpot> scenicSpotOperated = sysScenicSpotMapper.getScenicSpotOperated("1,3");
                    //园区数量
                    int spotNum = scenicSpotOperated.size();
                    //运营天数
                    double operateDay = 0;

                    //园区运营率
                    double spotOperateRate = 0;
                    //机器人出勤率
                    double robotAttendance = 0;
                    for (SysScenicSpot sysScenicSpot : scenicSpotOperated) {
                        map = new HashMap<>();
                        SysOrder firstOrder = sysOrderMapper.getSpotIdFirstOrder(sysScenicSpot.getScenicSpotId());

                        SysOrder lastOrder = sysOrderMapper.getSpotIdLastOrder(sysScenicSpot.getScenicSpotId());
                        if (StringUtils.isEmpty(firstOrder)){

                            map.put("spotId",sysScenicSpot.getScenicSpotId());
                            map.put("spotName",sysScenicSpot.getScenicSpotName());
                            map.put("time", startTime + "~" + endTime);
                            map.put("spotOperateRate", 0);
                            map.put("robotAttendance", 0);

                            list.add(map);
                            continue;
                        }

                        String firstDate = firstOrder.getCreateDate().substring(0, 10);
                        String lastDate = lastOrder.getCreateDate().substring(0, 10);
                        //运营天数
                        String dates = DateUtil.findDates(firstDate, lastDate);
                        operateDay = Double.parseDouble(dates);

                        if (startTime.equals(endTime)) {
                            int yearSum = new GregorianCalendar().isLeapYear(Integer.parseInt(startTime)) ? 366 : 365;

                            spotOperateRate = (operateDay / (spotNum * yearSum)) * 100;
                            spotOperateRate = Double.parseDouble(df.format(spotOperateRate)) ;

                        } else {
                            List<String> dateList = DateUtil.betweenYears(startTime, endTime);
                            int yearSum = 0;
                            for (String s : dateList) {
                                int i = new GregorianCalendar().isLeapYear(Integer.parseInt(s)) ? 366 : 365;
                                yearSum = yearSum + i;

                            }
                            spotOperateRate = (operateDay / (spotNum * yearSum)) * 100.0;
                            spotOperateRate =  Double.parseDouble(df.format(spotOperateRate));
                        }
                        Long robotCount = sysRobotMapper.getSpotIdByRobotCount(sysScenicSpot.getScenicSpotId());
                        Integer warehouseRobotCount = sysOrderMapper.getRobotWarehouseYearCount(sysScenicSpot.getScenicSpotId(), startTime, endTime);

                        if ( warehouseRobotCount != null && warehouseRobotCount != 0 && robotCount != null && robotCount != 0) {

                            robotAttendance = (warehouseRobotCount / robotCount) * 100.0;
                            robotAttendance = Double.parseDouble(df.format(robotAttendance)) ;
                        }

                        map.put("spotId",sysScenicSpot.getScenicSpotId());
                        map.put("spotName",sysScenicSpot.getScenicSpotName());
                        map.put("time", startTime + "~" + endTime);
                        map.put("spotOperateRate", spotOperateRate);
                        map.put("robotAttendance", robotAttendance);

                        list.add(map);
                    }


                } else if ("2".equals(type)) {

                    List<SysScenicSpot> scenicSpotOperated = sysScenicSpotMapper.getScenicSpotOperated("1,2,3");
                    //园区数量
                    int spotNum = scenicSpotOperated.size();
                    //运营天数
                    double operateDay = 0;

                    //园区运营率
                    double spotOperateRate = 0;
                    double robotAttendance = 0;
                    for (SysScenicSpot sysScenicSpot : scenicSpotOperated) {
                        map = new HashMap<>();
                        SysOrder firstOrder = sysOrderMapper.getSpotIdFirstOrder(sysScenicSpot.getScenicSpotId());

                        SysOrder lastOrder = sysOrderMapper.getSpotIdLastOrder(sysScenicSpot.getScenicSpotId());

                        if (StringUtils.isEmpty(firstOrder)){

                            map.put("spotId",sysScenicSpot.getScenicSpotId());
                            map.put("spotName",sysScenicSpot.getScenicSpotName());
                            map.put("time", startTime + "~" + endTime);
                            map.put("spotOperateRate", 0);
                            map.put("robotAttendance", 0);

                            list.add(map);
                            continue;
                        }

                        String firstDate = firstOrder.getCreateDate().substring(0, 10);
                        String lastDate = lastOrder.getCreateDate().substring(0, 10);
                        //运营天数
                        String dates = DateUtil.findDates(firstDate, lastDate);
                        operateDay = Double.parseDouble(dates);

                        if (startTime.equals(endTime)) {

                            int monthDay = DateUtil.getMonthDay(startTime);
                            spotOperateRate = (operateDay / (spotNum * monthDay)) * 100.0;
                            spotOperateRate = Double.parseDouble(df.format(spotOperateRate)) ;

                        } else {
                            List<String> dateList = DateUtil.betweenMonths(startTime, endTime);
                            int monthSum = 0;
                            for (String s : dateList) {
                                int monthDay = DateUtil.getMonthDay(s);
                                monthSum = monthSum + monthDay;

                            }
                            spotOperateRate = (operateDay / (spotNum * monthSum)) * 100.0;
                            spotOperateRate = Double.parseDouble(df.format(spotOperateRate));
                        }
                        Long robotCount = sysRobotMapper.getSpotIdByRobotCount(sysScenicSpot.getScenicSpotId());
                        Integer warehouseRobotCount = sysOrderMapper.getRobotWarehouseMonthCount(sysScenicSpot.getScenicSpotId(), startTime, endTime);

                        if (warehouseRobotCount != 0 && warehouseRobotCount != null && robotCount != null && robotCount != 0) {

                            robotAttendance = (warehouseRobotCount / robotCount) * 100;
                            robotAttendance = Double.parseDouble(df.format(robotAttendance)) ;
                        }

                        map.put("spotId",sysScenicSpot.getScenicSpotId());
                        map.put("spotName",sysScenicSpot.getScenicSpotName());
                        map.put("time", startTime + "~" + endTime);
                        map.put("spotOperateRate", spotOperateRate);
                        map.put("robotAttendance", robotAttendance);

                        list.add(map);
                    }

                } else {
                    List<SysScenicSpot> scenicSpotOperated = sysScenicSpotMapper.getScenicSpotOperated("1,2,3");
                    //园区数量
                    int spotNum = scenicSpotOperated.size();
                    //运营天数
                    double operateDay = 0;

                    //园区运营率
                    double spotOperateRate = 0;
                    double robotAttendance = 0;
                    for (SysScenicSpot sysScenicSpot : scenicSpotOperated) {
                        map = new HashMap<>();
                        SysOrder firstOrder = sysOrderMapper.getSpotIdFirstOrder(sysScenicSpot.getScenicSpotId());

                        SysOrder lastOrder = sysOrderMapper.getSpotIdLastOrder(sysScenicSpot.getScenicSpotId());
                        if (StringUtils.isEmpty(firstOrder)){

                            map.put("spotId",sysScenicSpot.getScenicSpotId());
                            map.put("spotName",sysScenicSpot.getScenicSpotName());
                            map.put("time", startTime + "~" + endTime);
                            map.put("spotOperateRate", 0);
                            map.put("robotAttendance", 0);

                            list.add(map);
                            continue;
                        }

                        String firstDate = firstOrder.getCreateDate().substring(0, 10);
                        String lastDate = lastOrder.getCreateDate().substring(0, 10);
                        //运营天数
                        String dates = DateUtil.findDates(firstDate, lastDate);
                        operateDay = Double.parseDouble(dates);

                        if (startTime.equals(endTime)) {

                            spotOperateRate = (operateDay / (spotNum * 1)) * 100.0;
                            spotOperateRate = Double.parseDouble(df.format(spotOperateRate));

                        } else {

                            String dateS = DateUtil.findDates(startTime, endTime);
                            int dateC = Integer.parseInt(dateS);


                            spotOperateRate = (operateDay / (spotNum * dateC)) * 100;
                            spotOperateRate = Double.parseDouble(df.format(spotOperateRate));

                        }
                        Long robotCount = sysRobotMapper.getSpotIdByRobotCount(sysScenicSpot.getScenicSpotId());
                        Integer warehouseRobotCount = sysOrderMapper.getRobotWarehouseDayCount(sysScenicSpot.getScenicSpotId(), startTime, endTime);

                        if (warehouseRobotCount != 0 && warehouseRobotCount != null && robotCount != null && robotCount != 0) {

                            robotAttendance = (warehouseRobotCount / robotCount) * 100;

                        }

                        map.put("spotId",sysScenicSpot.getScenicSpotId());
                        map.put("spotName",sysScenicSpot.getScenicSpotName());
                        map.put("time", startTime + "~" + endTime);
                        map.put("spotOperateRate", spotOperateRate);
                        map.put("robotAttendance", robotAttendance);

                        list.add(map);
                    }


                }


            } else {

                if ("1".equals(type)) {

                    List<SysScenicSpot> scenicSpotOperated = sysScenicSpotMapper.getScenicSpotOperated("1,2,3");
                    SysScenicSpot sysScenicSpot = sysScenicSpotMapper.selectByScenicSpotId(Long.parseLong(spotId));

                    //园区数量
                    int spotNum = scenicSpotOperated.size();
                    //运营天数
                    double operateDay = 0;

                    //园区运营率
                    double spotOperateRate = 0;
                    //机器人出勤率
                    double robotAttendance = 0;


                    SysOrder firstOrder = sysOrderMapper.getSpotIdFirstOrder(sysScenicSpot.getScenicSpotId());

                    SysOrder lastOrder = sysOrderMapper.getSpotIdLastOrder(sysScenicSpot.getScenicSpotId());
                    if (StringUtils.isEmpty(firstOrder)){

                        map.put("spotId",sysScenicSpot.getScenicSpotId());
                        map.put("spotName",sysScenicSpot.getScenicSpotName());
                        map.put("time", startTime + "~" + endTime);
                        map.put("spotOperateRate", 0);
                        map.put("robotAttendance", 0);

                        list.add(map);
                    }

                    String firstDate = firstOrder.getCreateDate().substring(0, 10);
                    String lastDate = lastOrder.getCreateDate().substring(0, 10);
                    //运营天数
                    String dates = DateUtil.findDates(firstDate, lastDate);
                    operateDay = Double.parseDouble(dates);

                    if (startTime.equals(endTime)) {
                        int yearSum = new GregorianCalendar().isLeapYear(Integer.parseInt(startTime)) ? 366 : 365;

                        spotOperateRate = (operateDay / (spotNum * yearSum)) * 100.0;
                        spotOperateRate = Double.parseDouble(df.format(spotOperateRate));

                    } else {
                        List<String> dateList = DateUtil.betweenYears(startTime, endTime);
                        int yearSum = 0;
                        for (String s : dateList) {
                            int i = new GregorianCalendar().isLeapYear(Integer.parseInt(s)) ? 366 : 365;
                            yearSum = yearSum + i;

                        }
                        spotOperateRate = (operateDay / (spotNum * yearSum)) * 100.0;
                        spotOperateRate = Double.parseDouble(df.format(spotOperateRate)) ;

                    }
                    Long robotCount = sysRobotMapper.getSpotIdByRobotCount(sysScenicSpot.getScenicSpotId());
                    Integer warehouseRobotCount = sysOrderMapper.getRobotWarehouseYearCount(sysScenicSpot.getScenicSpotId(), startTime, endTime);

                    if (warehouseRobotCount != 0 && warehouseRobotCount != null && robotCount != null && robotCount != 0) {

                        robotAttendance = (warehouseRobotCount / robotCount) * 100;
                        robotAttendance = Double.parseDouble(df.format(robotAttendance));

                    }


                    map.put("spotId", spotId);
                    map.put("spotName", sysScenicSpot.getScenicSpotName());
                    map.put("time", startTime + "~" + endTime);
                    map.put("spotOperateRate", spotOperateRate);
                    map.put("robotAttendance", robotAttendance);

                    list.add(map);

                } else if ("2".equals(type)) {

                    List<SysScenicSpot> scenicSpotOperated = sysScenicSpotMapper.getScenicSpotOperated("1,2,3");
                    SysScenicSpot sysScenicSpot = sysScenicSpotMapper.selectByScenicSpotId(Long.parseLong(spotId));


                    //园区数量
                    int spotNum = scenicSpotOperated.size();
                    //运营天数
                    double operateDay = 0;

                    //园区运营率
                    double spotOperateRate = 0;
                    double robotAttendance = 0;
                    map = new HashMap<>();
                    SysOrder firstOrder = sysOrderMapper.getSpotIdFirstOrder(sysScenicSpot.getScenicSpotId());

                    SysOrder lastOrder = sysOrderMapper.getSpotIdLastOrder(sysScenicSpot.getScenicSpotId());
                    if (StringUtils.isEmpty(firstOrder)){

                        map.put("spotId",sysScenicSpot.getScenicSpotId());
                        map.put("spotName",sysScenicSpot.getScenicSpotName());
                        map.put("time", startTime + "~" + endTime);
                        map.put("spotOperateRate", 0);
                        map.put("robotAttendance", 0);

                        list.add(map);
                    }

                    String firstDate = firstOrder.getCreateDate().substring(0, 10);
                    String lastDate = lastOrder.getCreateDate().substring(0, 10);
                    //运营天数
                    String dates = DateUtil.findDates(firstDate, lastDate);
                    operateDay = Double.parseDouble(dates);

                    if (startTime.equals(endTime)) {

                        int monthDay = DateUtil.getMonthDay(startTime);
                        spotOperateRate = (operateDay / (spotNum * monthDay)) * 100.0;
                        spotOperateRate = Double.parseDouble(df.format(spotOperateRate));

                    } else {
                        List<String> dateList = DateUtil.betweenMonths(startTime, endTime);
                        int monthSum = 0;
                        for (String s : dateList) {
                            int monthDay = DateUtil.getMonthDay(s);
                            monthSum = monthSum + monthDay;

                        }
                        spotOperateRate = (operateDay / (spotNum * monthSum)) * 100.0;
                        spotOperateRate = Double.parseDouble(df.format(spotOperateRate));
                    }
                    Long robotCount = sysRobotMapper.getSpotIdByRobotCount(sysScenicSpot.getScenicSpotId());
                    Integer warehouseRobotCount = sysOrderMapper.getRobotWarehouseMonthCount(sysScenicSpot.getScenicSpotId(), startTime, endTime);

                    if (warehouseRobotCount != 0 && warehouseRobotCount != null) {

                        robotAttendance = (warehouseRobotCount / robotCount) * 100;
                        robotAttendance = Double.parseDouble(df.format(robotAttendance));

                    }


                    map.put("spotId", spotId);
                    map.put("spotName", sysScenicSpot.getScenicSpotName());
                    map.put("time", startTime + "~" + endTime);
                    map.put("spotOperateRate", spotOperateRate);
                    map.put("robotAttendance", robotAttendance);
                    list.add(map);


                } else {

                    List<SysScenicSpot> scenicSpotOperated = sysScenicSpotMapper.getScenicSpotOperated("1,2,3");
                    SysScenicSpot sysScenicSpot = sysScenicSpotMapper.selectByScenicSpotId(Long.parseLong(spotId));

                    //园区数量
                    int spotNum = scenicSpotOperated.size();
                    //运营天数
                    double operateDay = 0;

                    //园区运营率
                    double spotOperateRate = 0;
                    double robotAttendance = 0;

                    map = new HashMap<>();
                    SysOrder firstOrder = sysOrderMapper.getSpotIdFirstOrder(sysScenicSpot.getScenicSpotId());

                    SysOrder lastOrder = sysOrderMapper.getSpotIdLastOrder(sysScenicSpot.getScenicSpotId());
                    String firstDate = firstOrder.getCreateDate().substring(0, 10);
                    String lastDate = lastOrder.getCreateDate().substring(0, 10);
                    //运营天数
                    String dates = DateUtil.findDates(firstDate, lastDate);
                    operateDay = Double.parseDouble(dates);

                    if (startTime.equals(endTime)) {

                        spotOperateRate = (operateDay / (spotNum * 1)) * 100.0;
                        spotOperateRate = Double.parseDouble(df.format(spotOperateRate)) ;

                    } else {

                        String dateS = DateUtil.findDates(startTime, endTime);
                        int dateC = Integer.parseInt(dateS);


                        spotOperateRate = (operateDay / (spotNum * dateC)) * 100.0;
                        spotOperateRate =  Double.parseDouble(df.format(spotOperateRate)) ;

                    }
                    Long robotCount = sysRobotMapper.getSpotIdByRobotCount(sysScenicSpot.getScenicSpotId());
                    Integer warehouseRobotCount = sysOrderMapper.getRobotWarehouseDayCount(sysScenicSpot.getScenicSpotId(), startTime, endTime);

                    if (warehouseRobotCount != 0 && warehouseRobotCount != null) {

                        robotAttendance = ((double)warehouseRobotCount / robotCount) * 100.0;
                        robotAttendance = Double.parseDouble(df.format(robotAttendance)) ;
                    }


                    map.put("spotId", spotId);
                    map.put("spotName", sysScenicSpot.getScenicSpotName());
                    map.put("time", startTime + "~" + endTime);
                    map.put("spotOperateRate", spotOperateRate);
                    map.put("robotAttendance", robotAttendance);
                    list.add(map);
                }


               }

        }catch (Exception e){

            e.printStackTrace();
        }

        String[] split = sort.split(",");
        if ("1".equals(split[0])){//设备出勤率排序
            if ("desc".equals(split[1])){
                Collections.sort(list, new Comparator<Map<String, Object>>() {
                    @Override
                    public int compare(Map<String, Object> o1, Map<String, Object> o2) {

                        Double aDouble = new Double(o1.get("robotAttendance").toString());
                        Double bDouble = new Double(o2.get("robotAttendance").toString());

                        return bDouble.compareTo(aDouble);
                    }
                });
            }else {
                Collections.sort(list, new Comparator<Map<String, Object>>() {
                    @Override
                    public int compare(Map<String, Object> o1, Map<String, Object> o2) {

                        Double aDouble = new Double(o1.get("robotAttendance").toString());
                        Double bDouble = new Double(o2.get("robotAttendance").toString());

                        return aDouble.compareTo(bDouble);
                    }
                });
            }
        }else{
            if ("desc".equals(split[1])){//根据园区运营率排序
                Collections.sort(list, new Comparator<Map<String, Object>>() {
                    @Override
                    public int compare(Map<String, Object> o1, Map<String, Object> o2) {

                        Double aDouble = new Double(o1.get("spotOperateRate").toString());
                        Double bDouble = new Double(o2.get("spotOperateRate").toString());

                        return bDouble.compareTo(aDouble);
                    }
                });



            }else {
                Collections.sort(list, new Comparator<Map<String, Object>>() {
                    @Override
                    public int compare(Map<String, Object> o1, Map<String, Object> o2) {

                        Double aDouble = new Double(o1.get("spotOperateRate").toString());
                        Double bDouble = new Double(o2.get("spotOperateRate").toString());

                        return aDouble.compareTo(bDouble);
                    }
                });
            }
        }

        return list ;

    }

    @Override
    public List<Map<String, Object>> histogramListNew(String type, String startTime, String endTime, String spotId, String sort) {
        List<Map<String,Object>> list = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();

        List<String> times = new ArrayList<>();
        List<String> spotIds = new ArrayList<>();
        List<String> spots = new ArrayList<>();
        List<String> spotOperateRates = new ArrayList<>();
        List<String> robotAttendances = new ArrayList<>();
        List<String> spotNames = new ArrayList<>();
        DecimalFormat df = new DecimalFormat("0.00");

        try{

            if(StringUtils.isEmpty(spotId)){
                if ("1".equals(type)){//年
                    List<String> dateList = DateUtil.betweenYears(startTime, endTime);
                    List<SysScenicSpot> scenicSpotOperated = sysScenicSpotMapper.getScenicSpotOperated("1");

                    for (String year : dateList) {
                        //园区数量
                        int spotNum = scenicSpotOperated.size();
                        //运营天数
                        double operateDay = 0;

                        //园区运营率
                        double spotOperateRate = 0;
                        //机器人出勤率
                        double robotAttendance = 0;
                        //运营景区数
                        int spotCount = 0;
                        for (SysScenicSpot sysScenicSpot : scenicSpotOperated) {
                            map = new HashMap<>();
                            SysOrder firstOrder = sysOrderMapper.getSpotIdFirstOrder(sysScenicSpot.getScenicSpotId());

                            SysOrder lastOrder = sysOrderMapper.getSpotIdLastOrder(sysScenicSpot.getScenicSpotId());
                            //判断是否运营天数为0
                            if (StringUtils.isEmpty(firstOrder)){

                                continue;
                            }

                            String firstDate = firstOrder.getCreateDate().substring(0, 10);
                            String lastDate = lastOrder.getCreateDate().substring(0, 10);
                            //运营天数
                            String dates = DateUtil.findDates(firstDate, lastDate);
                            operateDay = Double.parseDouble(dates);


                                int yearSum = new GregorianCalendar().isLeapYear(Integer.parseInt(year)) ? 366 : 365;

                                double operate = (operateDay / (spotNum * yearSum)) * 100;
                                spotOperateRate = spotOperateRate + Double.parseDouble(df.format(operate)) ;



                            Long robotCount = sysRobotMapper.getSpotIdByRobotCount(sysScenicSpot.getScenicSpotId());
//                            Integer warehouseRobotCount = sysOrderMapper.getRobotWarehouseYearCount(sysScenicSpot.getScenicSpotId(), startTime, endTime);
                            Integer warehouseRobotCount = sysOrderMapper.getRobotWarehouseYearCountNew(sysScenicSpot.getScenicSpotId(),year);

                            if ( warehouseRobotCount != null && warehouseRobotCount != 0 && robotCount != null && robotCount != 0) {

                                double attendance = (warehouseRobotCount / robotCount) ;
                                robotAttendance = robotAttendance + Double.parseDouble(df.format(attendance)) ;
                                spotCount ++ ;
                            }

                        }


                        times.add(year);
                        spots.add(String.valueOf(spotNum));
                        spotOperateRates.add(df.format(spotOperateRate));
                        robotAttendances.add(String.valueOf(robotAttendance));
//                        map.put("time",year);
//                        map.put("spotCount",spotNum);
//                        map.put("spotOperateRate",df.format(spotOperateRate) );
//                        map.put("robotAttendance",robotAttendance);
//
//                        list.add(map);

                    }
                    map.put("time",times);
                    map.put("spotCount",spots);
                    map.put("spotOperateRate",spotOperateRates );
                    map.put("robotAttendance",robotAttendances);

                    list.add(map);

                }else if ("2".equals(type)){//月

                    List<String> monthList = DateUtil.betweenMonths(startTime, endTime);

                    List<SysScenicSpot> scenicSpotOperated = sysScenicSpotMapper.getScenicSpotOperated("1");

                    for (String month : monthList) {

                        //园区数量
                        int spotNum = scenicSpotOperated.size();
                        //运营天数
                        double operateDay = 0;

                        //园区运营率
                        double spotOperateRate = 0;
                        double robotAttendance = 0;
                        int  spotCount = 0;
                        for (SysScenicSpot sysScenicSpot : scenicSpotOperated) {
                            map = new HashMap<>();
                            SysOrder firstOrder = sysOrderMapper.getSpotIdFirstOrder(sysScenicSpot.getScenicSpotId());

                            SysOrder lastOrder = sysOrderMapper.getSpotIdLastOrder(sysScenicSpot.getScenicSpotId());

                            //判断该景区是否有运营天数
                            if (StringUtils.isEmpty(firstOrder)){
                                continue;
                            }

                            String firstDate = firstOrder.getCreateDate().substring(0, 10);
                            String lastDate = lastOrder.getCreateDate().substring(0, 10);
                            //运营天数
                            String dates = DateUtil.findDates(firstDate, lastDate);
                            operateDay = Double.parseDouble(dates);


                            int monthDay = DateUtil.getMonthDay(month);
                             double operate = (operateDay / (spotNum * monthDay));
                             spotOperateRate = spotOperateRate + Double.parseDouble(df.format(operate)) ;

                            Long robotCount = sysRobotMapper.getSpotIdByRobotCount(sysScenicSpot.getScenicSpotId());

                            Integer warehouseRobotCount = sysOrderMapper.getRobotWarehouseMonthCountNew(sysScenicSpot.getScenicSpotId(),month);
                            if (warehouseRobotCount != 0 && warehouseRobotCount != null && robotCount != null && robotCount != 0) {

                                double attendance = (warehouseRobotCount / robotCount);
                                robotAttendance = robotAttendance + Double.parseDouble(df.format(attendance)) ;
                                spotCount++;
                            }


                        }

                        times.add(month);
                        spots.add(String.valueOf(spotNum));
                        spotOperateRates.add(df.format(spotOperateRate));
                        robotAttendances.add(String.valueOf(robotAttendance));
//                        map.put("time", month);
//                        map.put("spotCount",spotNum);
//                        map.put("spotOperateRate", df.format(spotOperateRate));
//                        map.put("robotAttendance", robotAttendance);
//
//                        list.add(map);

                    }

                    map.put("time", times);
                    map.put("spotCount",spots);
                    map.put("spotOperateRate", spotOperateRates);
                    map.put("robotAttendance", robotAttendances);

                    list.add(map);

                }else{//天

                    List<String> dayList = DateUtil.betweenDays(startTime, endTime);

                    for (String day : dayList) {

                        List<SysScenicSpot> scenicSpotOperated = sysScenicSpotMapper.getScenicSpotOperated("1");
                        //园区数量
                        int spotNum = scenicSpotOperated.size();
                        //运营天数
                        double operateDay = 0;

                        //园区运营率
                        double spotOperateRate = 0;
                        //机器人出勤率
                        double robotAttendance = 0;
                        int spotCount=0;
                        for (SysScenicSpot sysScenicSpot : scenicSpotOperated) {
                            map = new HashMap<>();
                            SysOrder firstOrder = sysOrderMapper.getSpotIdFirstOrder(sysScenicSpot.getScenicSpotId());

                            SysOrder lastOrder = sysOrderMapper.getSpotIdLastOrder(sysScenicSpot.getScenicSpotId());



                            if (StringUtils.isEmpty(firstOrder)){
                                continue;
                            }

                            String firstDate = firstOrder.getCreateDate().substring(0, 10);
                            String lastDate = lastOrder.getCreateDate().substring(0, 10);
                            //运营天数
                            String dates = DateUtil.findDates(firstDate, lastDate);
                            operateDay = Double.parseDouble(dates);

                                double operate = (operateDay / (spotNum * 1)) * 100.0;
                                spotOperateRate = spotOperateRate + Double.parseDouble(df.format(operate));

                            Long robotCount = sysRobotMapper.getSpotIdByRobotCount(sysScenicSpot.getScenicSpotId());
                            Integer warehouseRobotCount = sysOrderMapper.getRobotWarehouseDayCountNew(sysScenicSpot.getScenicSpotId(),day);

                            if (warehouseRobotCount != 0 && warehouseRobotCount != null && robotCount != null && robotCount != 0) {

                                double attendance = (warehouseRobotCount / robotCount) * 100;
                                robotAttendance = robotAttendance +  Double.parseDouble(df.format(attendance));
                                spotCount ++ ;
                            }



                        }


                        times.add(day);
                        spots.add(String.valueOf(spotCount));
                        spotOperateRates.add(df.format(spotOperateRate));
                        robotAttendances.add(String.valueOf(robotAttendance));

//                        map.put("time", day);
//                        map.put("spotCount",spotCount);
//                        map.put("spotOperateRate",df.format(spotOperateRate) );
//                        map.put("robotAttendance", robotAttendance);
//
//                        list.add(map);
                    }

                    map.put("time", times);
                    map.put("spotCount",spots);
                    map.put("spotOperateRate",spotOperateRates);
                    map.put("robotAttendance", robotAttendances);

                    list.add(map);

                }

            }else{//传景区id

                if ("1".equals(type)){//年

                    List<String> yearList = DateUtil.betweenYears(startTime, endTime);
                    SysScenicSpot sysScenicSpot = sysScenicSpotMapper.selectByScenicSpotId(Long.parseLong(spotId));

                    //园区数量
                    List<SysScenicSpot> scenicSpotOperated = sysScenicSpotMapper.getScenicSpotOperated("1");
                    int spotNum = scenicSpotOperated.size();
                    double spotOperateRate = 0;
                    double robotAttendance = 0;
                    for (String year : yearList) {

                      int yearSum = new GregorianCalendar().isLeapYear(Integer.parseInt(year)) ? 366 : 365;
                        //运营天数
                      Integer spotDayCount =  sysOrderMapper.getRobotWarehouseYearDateCount(sysScenicSpot.getScenicSpotId(),year);


                      if (spotDayCount != null && spotDayCount != 0){
                          double operate = ((double)spotDayCount / (spotNum * yearSum));
                          spotOperateRate =Double.parseDouble(df.format(operate));
                      }else{
                          spotDayCount = 0;
                      }

                        Long robotCount = sysRobotMapper.getSpotIdByRobotCount(sysScenicSpot.getScenicSpotId());
                        Integer warehouseRobotCount = sysOrderMapper.getRobotWarehouseDayCountNew(sysScenicSpot.getScenicSpotId(),year);

                        if (warehouseRobotCount != 0 && warehouseRobotCount != null && robotCount != null && robotCount != 0) {

                            double attendance = ((double)warehouseRobotCount / robotCount);
                            robotAttendance = robotAttendance +  Double.parseDouble(df.format(attendance));

                        }

//                        spotIds.add(String.valueOf(sysScenicSpot.getScenicSpotId()));
                        spotNames.add(sysScenicSpot.getScenicSpotName());
                        spots.add(String.valueOf(spotDayCount));
                        spotOperateRates.add(df.format(scenicSpotOperated));
                        robotAttendances.add(df.format(robotAttendance));

//                        map.put("spotId",sysScenicSpot.getScenicSpotId());
//                        map.put("spotName",sysScenicSpot.getScenicSpotName());
//                        map.put("spotDayCount",spotDayCount);
//                        map.put("spotOperateRate",spotOperateRate);
//                        map.put("robotAttendance",robotAttendance);
//                        list.add(map
                    }

//                    map.put("spotId",spotIds);
                    map.put("spotName",spotNames);
                    map.put("spotCount",spots);
                    map.put("spotOperateRate",spotOperateRates);
                    map.put("robotAttendance",robotAttendances);
                    list.add(map);


                }else if ("2".equals(type)){

                    List<String> monthList = DateUtil.betweenMonths(startTime, endTime);

                    SysScenicSpot sysScenicSpot = sysScenicSpotMapper.selectByScenicSpotId(Long.parseLong(spotId));

                    //园区数量
                    List<SysScenicSpot> scenicSpotOperated = sysScenicSpotMapper.getScenicSpotOperated("1");
                    int spotNum = scenicSpotOperated.size();
                    //景区运营率
                    double spotOperateRate = 0;
                    //机器人运营率
                    double robotAttendance = 0;
                    for (String month : monthList) {

                        int monthSum = DateUtil.getMonthDay(month);

                        Integer spotMonthCount =  sysOrderMapper.getRobotWarehouseMonthDateCount(sysScenicSpot.getScenicSpotId(),month);
                        if (spotMonthCount != null && spotMonthCount != 0){
                            double operate = ((double)spotMonthCount / (spotNum * monthSum));
                            spotOperateRate =Double.parseDouble(df.format(operate));
                        }else{
                            spotMonthCount = 0;
                        }

                        Long robotCount = sysRobotMapper.getSpotIdByRobotCount(sysScenicSpot.getScenicSpotId());
                        Integer warehouseRobotCount = sysOrderMapper.getRobotWarehouseMonthCountNew(sysScenicSpot.getScenicSpotId(),month);

                        if (warehouseRobotCount != 0 && warehouseRobotCount != null && robotCount != null && robotCount != 0) {

                            double attendance = ((double)warehouseRobotCount / robotCount);
                            robotAttendance = robotAttendance +  Double.parseDouble(df.format(attendance));

                        }

//                        spotIds.add(String.valueOf(sysScenicSpot.getScenicSpotId()));
                        spotNames.add(sysScenicSpot.getScenicSpotName());
                        spots.add(String.valueOf(spotMonthCount));
                        spotOperateRates.add(df.format(spotOperateRate));
                        robotAttendances.add(df.format(robotAttendance));
//                        map.put("spotId",sysScenicSpot.getScenicSpotId());
//                        map.put("spotName",sysScenicSpot.getScenicSpotName());
//                        map.put("spotDayCount",spotMonthCount);
//                        map.put("spotOperateRate",spotOperateRate);
//                        map.put("robotAttendance",robotAttendance);
//                        list.add(map);

                    }
//                    map.put("spotId",spotIds);
                    map.put("spotName",spotNames);
                    map.put("spotCount",spots);
                    map.put("spotOperateRate",spotOperateRates);
                    map.put("robotAttendance",robotAttendances);
                    list.add(map);


                }else {

                    List<String> dayList = DateUtil.betweenDays(startTime, endTime);
                    SysScenicSpot sysScenicSpot = sysScenicSpotMapper.selectByScenicSpotId(Long.parseLong(spotId));

                    //园区数量
                    List<SysScenicSpot> scenicSpotOperated = sysScenicSpotMapper.getScenicSpotOperated("1");
                    int spotNum = scenicSpotOperated.size();
                    //景区运营率
                    double spotOperateRate = 0;
                    //机器人运营率
                    double robotAttendance = 0;

                    for (String day : dayList) {

                        //运营天数
                        Integer spotDayCount =  sysOrderMapper.getRobotWarehouseDayDateCount(sysScenicSpot.getScenicSpotId(),day);

                        if (spotDayCount != null && spotDayCount != 0){
                            double operate = ((double)spotDayCount / (spotNum * 1));
                            spotOperateRate =Double.parseDouble(df.format(operate));

                        }else{
                            spotDayCount = 0;
                        }

                        Long robotCount = sysRobotMapper.getSpotIdByRobotCount(sysScenicSpot.getScenicSpotId());
                        Integer warehouseRobotCount = sysOrderMapper.getRobotWarehouseDayCountNew(sysScenicSpot.getScenicSpotId(),day);

                        if (warehouseRobotCount != 0 && warehouseRobotCount != null && robotCount != null && robotCount != 0) {

                            double attendance = ((double)warehouseRobotCount / robotCount);
                            robotAttendance = robotAttendance +  Double.parseDouble(df.format(attendance));

                        }
//
//                        spotIds.add(String.valueOf(sysScenicSpot.getScenicSpotId()));
                        spotNames.add(sysScenicSpot.getScenicSpotName());
                        spots.add(String.valueOf(spotDayCount));
                        spotOperateRates.add(df.format(spotOperateRate));
                        robotAttendances.add(df.format(robotAttendance));
//                        map.put("spotId",sysScenicSpot.getScenicSpotId());
//                        map.put("spotName",sysScenicSpot.getScenicSpotName());
//                        map.put("spotDayCount",spotDayCount);
//                        map.put("spotOperateRate",spotOperateRate);
//                        map.put("robotAttendance",robotAttendance);
//                        list.add(map);

                    }
//                    map.put("spotId",spotIds);
                    map.put("spotName",spotNames);
                    map.put("spotCount",spots);
                    map.put("spotOperateRate",spotOperateRates);
                    map.put("robotAttendance",robotAttendances);
                    list.add(map);
                }

            }


        }catch (Exception e){

            e.printStackTrace();

        }




        return list;

    }


    @Override
    public List<EquipmentAttendanceStatistics> listsN(EquipmentAttendanceStatistics equipmentAttendanceStatistics, Integer pageNum, Integer pageSize) {

        List<EquipmentAttendanceStatistics> lists = new ArrayList<>();
        DecimalFormat df = new DecimalFormat("0.00");
        try {

            //判断是否为按景区查询
            if ( "1".equals(equipmentAttendanceStatistics.getQueryType())){

                //判断按照年查询
                if ("1".equals(equipmentAttendanceStatistics.getDateType())) {

                    //判断是否有景区
                    if(!StringUtils.isEmpty(equipmentAttendanceStatistics.getScenicSpotId())){

                        List<String> list = DateUtil.betweenYears(equipmentAttendanceStatistics.getStartTime(), equipmentAttendanceStatistics.getEndTime());
                        //园区运营率
                        double spotOperateRate = 0;
                        //机器人运营率
                        double robotAttendance = 0;
                        SysOrder spotIdFirstOrder = sysOrderMapper.getSpotIdFirstOrder(Long.parseLong(equipmentAttendanceStatistics.getScenicSpotId()));
                        SysOrder spotIdLastOrder = sysOrderMapper.getSpotIdLastOrder(Long.parseLong(equipmentAttendanceStatistics.getScenicSpotId()));

                        //开业天数
                        Integer dates = 0;
                        if (!StringUtils.isEmpty(spotIdFirstOrder)){

                             dates =Integer.parseInt(DateUtil.findDates(spotIdFirstOrder.getCreateDate().substring(1, 10), spotIdLastOrder.getCreateDate().substring(1, 10))) ;

                        }

                        //运营天数
                        int  operationDays = 0;


                        //查询天数
                        int yearSum = 0;
                        for (String date : list) {
                           //营业天数
                            Integer robotWarehouseYearDateCount = sysOrderMapper.getRobotWarehouseYearDateCount(Long.parseLong(equipmentAttendanceStatistics.getScenicSpotId()), date);
                            operationDays += robotWarehouseYearDateCount;
                            yearSum += new GregorianCalendar().isLeapYear(Integer.parseInt(date)) ? 366 : 365;

                        }

                        double operat = (double) operationDays / (1*yearSum);
                        spotOperateRate =Double.parseDouble(df.format(operat));

                        Long robotCount = sysRobotMapper.getSpotIdByRobotCount(Long.parseLong(equipmentAttendanceStatistics.getScenicSpotId()));

                        Integer exWarehouseCount = sysOrderMapper.getRobotWarehouseYearCountNew(Long.parseLong(equipmentAttendanceStatistics.getScenicSpotId()), equipmentAttendanceStatistics.getStartTime());

                        if (robotCount != null && robotCount != 0 && exWarehouseCount != null && exWarehouseCount != 0){

                            double attendance = (double)exWarehouseCount / robotCount;
                            robotAttendance = Double.parseDouble(df.format(attendance));
                        }

                     EquipmentAttendanceStatistics equipmentAttendanceStatisticsN =  equipmentAttendanceStatisticsMapper.getSpotIdLastAttendance(equipmentAttendanceStatistics.getScenicSpotId());

                     equipmentAttendanceStatisticsN.setTodayDate(equipmentAttendanceStatistics.getStartTime() + "~" + equipmentAttendanceStatistics.getEndTime());

                    equipmentAttendanceStatisticsN.setOpeningDays(dates.toString());
                    equipmentAttendanceStatisticsN.setOperationDays(String.valueOf(operationDays));
                    equipmentAttendanceStatisticsN.setScenicSpotNameOperatingRate(spotOperateRate);
                    equipmentAttendanceStatisticsN.setEquipmentOperatingNum(String.valueOf(robotAttendance));
                    lists.add(equipmentAttendanceStatisticsN);

                    }else{

                        List<SysScenicSpot> scenicSpotOperated = sysScenicSpotMapper.getScenicSpotOperated("1");

                        List<String> list = DateUtil.betweenYears(equipmentAttendanceStatistics.getStartTime(), equipmentAttendanceStatistics.getEndTime());
                        double spotOperateRate = 0;
                        //机器人运营率
                        double robotAttendance = 0;

                        //运营天数
                        int  operationDays = 0;


                        //查询天数
                        int yearSum = 0;

                        for (SysScenicSpot sysScenicSpot : scenicSpotOperated) {

                            SysOrder spotIdFirstOrder = sysOrderMapper.getSpotIdFirstOrder(sysScenicSpot.getScenicSpotId());
                            SysOrder spotIdLastOrder = sysOrderMapper.getSpotIdLastOrder(sysScenicSpot.getScenicSpotId());

                            //开业天数
                            Integer dates = 0;
                            if (!StringUtils.isEmpty(spotIdFirstOrder)){

                                dates =Integer.parseInt(DateUtil.findDates(spotIdFirstOrder.getCreateDate().substring(1, 10), spotIdLastOrder.getCreateDate().substring(1, 10))) ;

                            }

                            Integer robotWarehouseYearDateCount = 0;

                            for (String date : list) {

                                robotWarehouseYearDateCount = sysOrderMapper.getRobotWarehouseYearDateCount(sysScenicSpot.getScenicSpotId(), date);
                                //运营天数
                                operationDays += robotWarehouseYearDateCount;

                                yearSum = new GregorianCalendar().isLeapYear(Integer.parseInt(date)) ? 366 : 365;

                                double operat = (double) robotWarehouseYearDateCount / (1*yearSum);
                                spotOperateRate += Double.parseDouble(df.format(operat));

                                Long robotCount = sysRobotMapper.getSpotIdByRobotCount(sysScenicSpot.getScenicSpotId());

                                Integer exWarehouseCount = sysOrderMapper.getRobotWarehouseYearCountNew(sysScenicSpot.getScenicSpotId(), equipmentAttendanceStatistics.getStartTime());

                                if (robotCount != null && robotCount != 0 && exWarehouseCount != null && exWarehouseCount != 0){

                                    double attendance = (double)exWarehouseCount / robotCount;
                                    robotAttendance += Double.parseDouble(df.format(attendance));
                                }


                            }

                            EquipmentAttendanceStatistics equipmentAttendanceStatisticsN =  equipmentAttendanceStatisticsMapper.getSpotIdLastAttendance(sysScenicSpot.getScenicSpotId().toString());


                            if(StringUtils.isEmpty(equipmentAttendanceStatisticsN)){


                            }else{
                                equipmentAttendanceStatisticsN.setTodayDate(equipmentAttendanceStatistics.getStartTime() + "~" + equipmentAttendanceStatistics.getEndTime());
                                equipmentAttendanceStatisticsN.setOpeningDays(dates.toString());
                                equipmentAttendanceStatisticsN.setOperationDays(String.valueOf(operationDays));
                                equipmentAttendanceStatisticsN.setScenicSpotNameOperatingRate(spotOperateRate/list.size());
                                equipmentAttendanceStatisticsN.setEquipmentOperatingNum(String.valueOf(robotAttendance/list.size()));

                            }


                            lists.add(equipmentAttendanceStatisticsN);
                        }
                    }

                }else if ("2".equals(equipmentAttendanceStatistics.getDateType())){//按月查询

                    //判断是否有景区
                    if(!StringUtils.isEmpty(equipmentAttendanceStatistics.getScenicSpotId())){

                        List<String> list = DateUtil.betweenMonths(equipmentAttendanceStatistics.getStartTime(), equipmentAttendanceStatistics.getEndTime());
                        //园区运营率
                        double spotOperateRate = 0;
                        //机器人运营率
                        double robotAttendance = 0;
                        SysOrder spotIdFirstOrder = sysOrderMapper.getSpotIdFirstOrder(Long.parseLong(equipmentAttendanceStatistics.getScenicSpotId()));
                        SysOrder spotIdLastOrder = sysOrderMapper.getSpotIdLastOrder(Long.parseLong(equipmentAttendanceStatistics.getScenicSpotId()));

                        //开业天数
                        Integer dates = 0;
                        if (!StringUtils.isEmpty(spotIdFirstOrder)){

                            dates =Integer.parseInt(DateUtil.findDates(spotIdFirstOrder.getCreateDate().substring(1, 10), spotIdLastOrder.getCreateDate().substring(1, 10))) ;

                        }

                        //运营天数
                        int  operationDays = 0;

                        //查询天数
                        int monthSum = 0;
                        for (String date : list) {
                            //营业天数
                            Integer robotWarehouseYearDateCount = sysOrderMapper.getRobotWarehouseMonthDateCount(Long.parseLong(equipmentAttendanceStatistics.getScenicSpotId()), date);
                            operationDays += robotWarehouseYearDateCount;
                            monthSum += DateUtil.getMonthDay(date);

                        }

                        double operat = (double) operationDays / (1*monthSum);
                        spotOperateRate =Double.parseDouble(df.format(operat));

                        Long robotCount = sysRobotMapper.getSpotIdByRobotCount(Long.parseLong(equipmentAttendanceStatistics.getScenicSpotId()));

                        Integer exWarehouseCount = sysOrderMapper.getRobotWarehouseYearCountNew(Long.parseLong(equipmentAttendanceStatistics.getScenicSpotId()), equipmentAttendanceStatistics.getStartTime());

                        if (robotCount != null && robotCount != 0 && exWarehouseCount != null && exWarehouseCount != 0){

                            double attendance = (double)exWarehouseCount / robotCount;
                            robotAttendance = Double.parseDouble(df.format(attendance));
                        }

                        EquipmentAttendanceStatistics equipmentAttendanceStatisticsN =  equipmentAttendanceStatisticsMapper.getSpotIdLastAttendance(equipmentAttendanceStatistics.getScenicSpotId());

                        equipmentAttendanceStatisticsN.setTodayDate(equipmentAttendanceStatistics.getStartTime() + "~" + equipmentAttendanceStatistics.getEndTime());

                        equipmentAttendanceStatisticsN.setOpeningDays(dates.toString());
                        equipmentAttendanceStatisticsN.setOperationDays(String.valueOf(operationDays));
                        equipmentAttendanceStatisticsN.setScenicSpotNameOperatingRate(spotOperateRate);
                        equipmentAttendanceStatisticsN.setEquipmentOperatingNum(String.valueOf(robotAttendance));

                        lists.add(equipmentAttendanceStatisticsN);

                    }else{

                        List<SysScenicSpot> scenicSpotOperated = sysScenicSpotMapper.getScenicSpotOperated("1");

                        List<String> list = DateUtil.betweenMonths(equipmentAttendanceStatistics.getStartTime(), equipmentAttendanceStatistics.getEndTime());
                        double spotOperateRate = 0;
                        //机器人运营率
                        double robotAttendance = 0;

                        //运营天数
                        int  operationDays = 0;


                        //查询天数
                        int monthSum = 0;

                        for (SysScenicSpot sysScenicSpot : scenicSpotOperated) {

                            SysOrder spotIdFirstOrder = sysOrderMapper.getSpotIdFirstOrder(sysScenicSpot.getScenicSpotId());
                            SysOrder spotIdLastOrder = sysOrderMapper.getSpotIdLastOrder(sysScenicSpot.getScenicSpotId());

                            //开业天数
                            Integer dates = 0;
                            if (!StringUtils.isEmpty(spotIdFirstOrder)){

                                dates =Integer.parseInt(DateUtil.findDates(spotIdFirstOrder.getCreateDate().substring(1, 10), spotIdLastOrder.getCreateDate().substring(1, 10))) ;

                            }

                            Integer robotWarehouseMonthDateCount = 0;

                            for (String date : list) {

                                robotWarehouseMonthDateCount = sysOrderMapper.getRobotWarehouseMonthDateCount(sysScenicSpot.getScenicSpotId(), date);
                                //运营天数
                                operationDays += robotWarehouseMonthDateCount;

                                monthSum = DateUtil.getMonthDay(date);

                                double operat = (double) robotWarehouseMonthDateCount / (1*monthSum);
                                spotOperateRate += Double.parseDouble(df.format(operat));

                                Long robotCount = sysRobotMapper.getSpotIdByRobotCount(sysScenicSpot.getScenicSpotId());

                                Integer exWarehouseCount = sysOrderMapper.getRobotWarehouseMonthCountNew(sysScenicSpot.getScenicSpotId(), equipmentAttendanceStatistics.getStartTime());

                                if (robotCount != null && robotCount != 0 && exWarehouseCount != null && exWarehouseCount != 0){

                                    double attendance = (double)exWarehouseCount / robotCount;
                                    robotAttendance += Double.parseDouble(df.format(attendance));
                                }


                            }

                            EquipmentAttendanceStatistics equipmentAttendanceStatisticsN =  equipmentAttendanceStatisticsMapper.getSpotIdLastAttendance(sysScenicSpot.getScenicSpotId().toString());

                            equipmentAttendanceStatisticsN.setTodayDate(equipmentAttendanceStatistics.getStartTime() + "~" + equipmentAttendanceStatistics.getEndTime());

                            equipmentAttendanceStatisticsN.setOpeningDays(dates.toString());
                            equipmentAttendanceStatisticsN.setOperationDays(String.valueOf(operationDays));
                            equipmentAttendanceStatisticsN.setScenicSpotNameOperatingRate(spotOperateRate/list.size());
                            equipmentAttendanceStatisticsN.setEquipmentOperatingNum(String.valueOf(robotAttendance/list.size()));

                            lists.add(equipmentAttendanceStatisticsN);
                        }
                    }

                }else if ("3".equals(equipmentAttendanceStatistics.getDateType())){//按照日查询
                    //判断是否有景区
                    if(!StringUtils.isEmpty(equipmentAttendanceStatistics.getScenicSpotId())){

                        List<String> list = DateUtil.betweenDays(equipmentAttendanceStatistics.getStartTime(), equipmentAttendanceStatistics.getEndTime());
                        //园区运营率
                        double spotOperateRate = 0;
                        //机器人运营率
                        double robotAttendance = 0;
                        SysOrder spotIdFirstOrder = sysOrderMapper.getSpotIdFirstOrder(Long.parseLong(equipmentAttendanceStatistics.getScenicSpotId()));
                        SysOrder spotIdLastOrder = sysOrderMapper.getSpotIdLastOrder(Long.parseLong(equipmentAttendanceStatistics.getScenicSpotId()));

                        //开业天数
                        Integer dates = 0;
                        if (!StringUtils.isEmpty(spotIdFirstOrder)){

                            dates =Integer.parseInt(DateUtil.findDates(spotIdFirstOrder.getCreateDate().substring(1, 10), spotIdLastOrder.getCreateDate().substring(1, 10))) ;

                        }

                        //运营天数
                        int  operationDays = 0;

                        //查询天数
                        int daySum = Integer.parseInt(DateUtil.findDates(equipmentAttendanceStatistics.getStartTime(),equipmentAttendanceStatistics.getEndTime())) ;
                        for (String date : list) {
                            //营业天数
                            Integer robotWarehouseYearDateCount = sysOrderMapper.getRobotWarehouseDayDateCount(Long.parseLong(equipmentAttendanceStatistics.getScenicSpotId()), date);
                            operationDays += robotWarehouseYearDateCount;
                        }


                        double operat = (double) operationDays / (1*daySum);
                        spotOperateRate =Double.parseDouble(df.format(operat));

                        Long robotCount = sysRobotMapper.getSpotIdByRobotCount(Long.parseLong(equipmentAttendanceStatistics.getScenicSpotId()));

                        Integer exWarehouseCount = sysOrderMapper.getRobotWarehouseDayCountNew(Long.parseLong(equipmentAttendanceStatistics.getScenicSpotId()), equipmentAttendanceStatistics.getStartTime());

                        if (robotCount != null && robotCount != 0 && exWarehouseCount != null && exWarehouseCount != 0){

                            double attendance = (double)exWarehouseCount / robotCount;
                            robotAttendance = Double.parseDouble(df.format(attendance));
                        }

                        EquipmentAttendanceStatistics equipmentAttendanceStatisticsN =  equipmentAttendanceStatisticsMapper.getSpotIdLastAttendance(equipmentAttendanceStatistics.getScenicSpotId());

                        if (StringUtils.isEmpty(equipmentAttendanceStatisticsN)){


                        }else{
                            equipmentAttendanceStatisticsN.setTodayDate(equipmentAttendanceStatistics.getStartTime() + "~" + equipmentAttendanceStatistics.getEndTime());
                            equipmentAttendanceStatisticsN.setOpeningDays(dates.toString());
                            equipmentAttendanceStatisticsN.setOperationDays(String.valueOf(operationDays));
                            equipmentAttendanceStatisticsN.setScenicSpotNameOperatingRate(spotOperateRate);
                            equipmentAttendanceStatisticsN.setEquipmentOperatingNum(String.valueOf(robotAttendance));

                            lists.add(equipmentAttendanceStatisticsN);
                        }



                    }else{

                        List<SysScenicSpot> scenicSpotOperated = sysScenicSpotMapper.getScenicSpotOperated("1");

                        List<String> list = DateUtil.betweenDays(equipmentAttendanceStatistics.getStartTime(), equipmentAttendanceStatistics.getEndTime());
                        double spotOperateRate = 0;
                        //机器人运营率
                        double robotAttendance = 0;

                        //运营天数
                        int  operationDays = 0;


                        //查询天数
                        int daySum = 0;

                        for (SysScenicSpot sysScenicSpot : scenicSpotOperated) {

                            SysOrder spotIdFirstOrder = sysOrderMapper.getSpotIdFirstOrder(sysScenicSpot.getScenicSpotId());
                            SysOrder spotIdLastOrder = sysOrderMapper.getSpotIdLastOrder(sysScenicSpot.getScenicSpotId());

                            //开业天数
                            Integer dates = 0;
                            if (!StringUtils.isEmpty(spotIdFirstOrder)){

                                dates =Integer.parseInt(DateUtil.findDates(spotIdFirstOrder.getCreateDate().substring(1, 10), spotIdLastOrder.getCreateDate().substring(1, 10))) ;

                            }

                            Integer robotWarehouseDayDateCount = 0;


                            operationDays =  sysOrderMapper.getTimeSlotOperateDay(sysScenicSpot.getScenicSpotId(),equipmentAttendanceStatistics.getStartTime(),equipmentAttendanceStatistics.getEndTime());

                            daySum = Integer.parseInt(DateUtil.findDates(equipmentAttendanceStatistics.getStartTime(), equipmentAttendanceStatistics.getEndTime()));

                            spotOperateRate = (double) operationDays / (1*daySum);

                            Long robotCount = sysRobotMapper.getSpotIdByRobotCount(sysScenicSpot.getScenicSpotId());

//                            Integer exWarehouseCount = sysOrderMapper.getRobotWarehouseMonthCountNew(sysScenicSpot.getScenicSpotId(), equipmentAttendanceStatistics.getStartTime());

                            Integer exWarehouseCount= sysOrderMapper.getTimeSlotOperateRobot(sysScenicSpot.getScenicSpotId(),equipmentAttendanceStatistics.getStartTime(),equipmentAttendanceStatistics.getEndTime());

                            if (robotCount != null && robotCount != 0 && exWarehouseCount != null && exWarehouseCount != 0){

                                double attendance = (double)exWarehouseCount / robotCount;
                                robotAttendance = Double.parseDouble(df.format(attendance));
                            }


                            EquipmentAttendanceStatistics equipmentAttendanceStatisticsN =  equipmentAttendanceStatisticsMapper.getSpotIdLastAttendance(sysScenicSpot.getScenicSpotId().toString());

                            if (StringUtils.isEmpty(equipmentAttendanceStatisticsN)){

                            }else{

                                equipmentAttendanceStatisticsN.setTodayDate(equipmentAttendanceStatistics.getStartTime() + "~" + equipmentAttendanceStatistics.getEndTime());
                                equipmentAttendanceStatisticsN.setOpeningDays(dates.toString());
                                equipmentAttendanceStatisticsN.setOperationDays(String.valueOf(operationDays));
                                equipmentAttendanceStatisticsN.setScenicSpotNameOperatingRate(Double.parseDouble(df.format(spotOperateRate/list.size())));
                                equipmentAttendanceStatisticsN.setEquipmentOperatingNum(df.format(robotAttendance/list.size()));

                                lists.add(equipmentAttendanceStatisticsN);

                            }


                        }
                    }

                }

            }else if ("2".equals(equipmentAttendanceStatistics.getQueryType())){{
                //判断按照年查询
                if ("1".equals(equipmentAttendanceStatistics.getDateType())) {

                    //判断是否有景区
                    if(!StringUtils.isEmpty(equipmentAttendanceStatistics.getScenicSpotId())){

                        List<String> list = DateUtil.betweenYears(equipmentAttendanceStatistics.getStartTime(), equipmentAttendanceStatistics.getEndTime());
                        //园区运营率
                        double spotOperateRate = 0;
                        //机器人运营率
                        double robotAttendance = 0;
                        SysOrder spotIdFirstOrder = sysOrderMapper.getSpotIdFirstOrder(Long.parseLong(equipmentAttendanceStatistics.getScenicSpotId()));
                        SysOrder spotIdLastOrder = sysOrderMapper.getSpotIdLastOrder(Long.parseLong(equipmentAttendanceStatistics.getScenicSpotId()));

                        //开业天数
                        Integer dates = 0;
                        if (!StringUtils.isEmpty(spotIdFirstOrder)){

                            dates =Integer.parseInt(DateUtil.findDates(spotIdFirstOrder.getCreateDate().substring(1, 10), spotIdLastOrder.getCreateDate().substring(1, 10))) ;

                        }

                        //运营天数
                        int  operationDays = 0;


                        //查询天数
                        int yearSum = 0;
                        for (String date : list) {
                            //营业天数
                            Integer robotWarehouseYearDateCount = sysOrderMapper.getRobotWarehouseYearDateCount(Long.parseLong(equipmentAttendanceStatistics.getScenicSpotId()), date);
                            operationDays = robotWarehouseYearDateCount;
                            yearSum = new GregorianCalendar().isLeapYear(Integer.parseInt(date)) ? 366 : 365;

                            double operat = (double) operationDays / (1*yearSum);
                            spotOperateRate =Double.parseDouble(df.format(operat));

                            Long robotCount = sysRobotMapper.getSpotIdByRobotCount(Long.parseLong(equipmentAttendanceStatistics.getScenicSpotId()));

                            Integer exWarehouseCount = sysOrderMapper.getRobotWarehouseYearCountNew(Long.parseLong(equipmentAttendanceStatistics.getScenicSpotId()), equipmentAttendanceStatistics.getStartTime());

                            if (robotCount != null && robotCount != 0 && exWarehouseCount != null && exWarehouseCount != 0){

                                double attendance = (double)exWarehouseCount / robotCount;
                                robotAttendance = Double.parseDouble(df.format(attendance));
                            }

                            EquipmentAttendanceStatistics equipmentAttendanceStatisticsN =  equipmentAttendanceStatisticsMapper.getSpotIdLastAttendance(equipmentAttendanceStatistics.getScenicSpotId());

                            if (StringUtils.isEmpty(equipmentAttendanceStatisticsN)){


                            }else{

                                equipmentAttendanceStatisticsN.setTodayDate(date);
                                equipmentAttendanceStatisticsN.setOpeningDays(dates.toString());
                                equipmentAttendanceStatisticsN.setOperationDays(String.valueOf(operationDays));
                                equipmentAttendanceStatisticsN.setScenicSpotNameOperatingRate(spotOperateRate);
                                equipmentAttendanceStatisticsN.setEquipmentOperatingNum(String.valueOf(robotAttendance));
                                lists.add(equipmentAttendanceStatisticsN);
                            }
                        }

                    }else{

                        List<SysScenicSpot> scenicSpotOperated = sysScenicSpotMapper.getScenicSpotOperated("1");

                        List<String> list = DateUtil.betweenYears(equipmentAttendanceStatistics.getStartTime(), equipmentAttendanceStatistics.getEndTime());
                        double spotOperateRate = 0;
                        //机器人运营率
                        double robotAttendance = 0;

                        //运营天数
                        int  operationDays = 0;


                        //查询天数
                        int yearSum = 0;

                        for (SysScenicSpot sysScenicSpot : scenicSpotOperated) {

                            SysOrder spotIdFirstOrder = sysOrderMapper.getSpotIdFirstOrder(sysScenicSpot.getScenicSpotId());
                            SysOrder spotIdLastOrder = sysOrderMapper.getSpotIdLastOrder(sysScenicSpot.getScenicSpotId());

                            //开业天数
                            Integer dates = 0;
                            if (!StringUtils.isEmpty(spotIdFirstOrder)){

                                dates =Integer.parseInt(DateUtil.findDates(spotIdFirstOrder.getCreateDate().substring(1, 10), spotIdLastOrder.getCreateDate().substring(1, 10))) ;

                            }

                            Integer robotWarehouseYearDateCount = 0;

                            for (String date : list) {

                                robotWarehouseYearDateCount = sysOrderMapper.getRobotWarehouseYearDateCount(sysScenicSpot.getScenicSpotId(), date);
                                //运营天数
                                operationDays = robotWarehouseYearDateCount;

                                yearSum = new GregorianCalendar().isLeapYear(Integer.parseInt(date)) ? 366 : 365;

                                double operat = (double) robotWarehouseYearDateCount / (1*yearSum);
                                spotOperateRate += Double.parseDouble(df.format(operat));

                                Long robotCount = sysRobotMapper.getSpotIdByRobotCount(sysScenicSpot.getScenicSpotId());

                                Integer exWarehouseCount = sysOrderMapper.getRobotWarehouseYearCountNew(sysScenicSpot.getScenicSpotId(), equipmentAttendanceStatistics.getStartTime());

                                if (robotCount != null && robotCount != 0 && exWarehouseCount != null && exWarehouseCount != 0){

                                    double attendance = (double)exWarehouseCount / robotCount;
                                    robotAttendance += Double.parseDouble(df.format(attendance));
                                }
                                EquipmentAttendanceStatistics equipmentAttendanceStatisticsN =  equipmentAttendanceStatisticsMapper.getSpotIdLastAttendance(sysScenicSpot.getScenicSpotId().toString());

                                if (StringUtils.isEmpty(equipmentAttendanceStatisticsN)) {


                                }else{
                                    equipmentAttendanceStatisticsN.setTodayDate(date);
                                    equipmentAttendanceStatisticsN.setOpeningDays(dates.toString());
                                    equipmentAttendanceStatisticsN.setOperationDays(String.valueOf(operationDays));
                                    equipmentAttendanceStatisticsN.setScenicSpotNameOperatingRate(spotOperateRate/list.size());
                                    equipmentAttendanceStatisticsN.setEquipmentOperatingNum(String.valueOf(robotAttendance/list.size()));
                                    lists.add(equipmentAttendanceStatisticsN);

                                }

                            }
                        }
                    }

                }else if ("2".equals(equipmentAttendanceStatistics.getDateType())){//按月查询

                    //判断是否有景区
                    if(!StringUtils.isEmpty(equipmentAttendanceStatistics.getScenicSpotId())){

                        List<String> list = DateUtil.betweenMonths(equipmentAttendanceStatistics.getStartTime(), equipmentAttendanceStatistics.getEndTime());
                        //园区运营率
                        double spotOperateRate = 0;
                        //机器人运营率
                        double robotAttendance = 0;
                        SysOrder spotIdFirstOrder = sysOrderMapper.getSpotIdFirstOrder(Long.parseLong(equipmentAttendanceStatistics.getScenicSpotId()));
                        SysOrder spotIdLastOrder = sysOrderMapper.getSpotIdLastOrder(Long.parseLong(equipmentAttendanceStatistics.getScenicSpotId()));

                        //开业天数
                        Integer dates = 0;
                        if (!StringUtils.isEmpty(spotIdFirstOrder)){

                            dates =Integer.parseInt(DateUtil.findDates(spotIdFirstOrder.getCreateDate().substring(1, 10), spotIdLastOrder.getCreateDate().substring(1, 10))) ;

                        }

                        //运营天数
                        int  operationDays = 0;

                        //查询天数
                        int monthSum = 0;
                        for (String date : list) {
                            //营业天数
                            Integer robotWarehouseYearDateCount = sysOrderMapper.getRobotWarehouseMonthDateCount(Long.parseLong(equipmentAttendanceStatistics.getScenicSpotId()), date);
                            operationDays = robotWarehouseYearDateCount;
                            monthSum = DateUtil.getMonthDay(date);

                            double operat = (double) operationDays / (1*monthSum);
                            spotOperateRate =Double.parseDouble(df.format(operat));

                            Long robotCount = sysRobotMapper.getSpotIdByRobotCount(Long.parseLong(equipmentAttendanceStatistics.getScenicSpotId()));

                            Integer exWarehouseCount = sysOrderMapper.getRobotWarehouseYearCountNew(Long.parseLong(equipmentAttendanceStatistics.getScenicSpotId()), equipmentAttendanceStatistics.getStartTime());

                            if (robotCount != null && robotCount != 0 && exWarehouseCount != null && exWarehouseCount != 0){

                                double attendance = (double)exWarehouseCount / robotCount;
                                robotAttendance = Double.parseDouble(df.format(attendance));
                            }

                            EquipmentAttendanceStatistics equipmentAttendanceStatisticsN =  equipmentAttendanceStatisticsMapper.getSpotIdLastAttendance(equipmentAttendanceStatistics.getScenicSpotId());

                            if (StringUtils.isEmpty(equipmentAttendanceStatisticsN)){

                            }else{

                                equipmentAttendanceStatisticsN.setTodayDate(date);
                                equipmentAttendanceStatisticsN.setOpeningDays(dates.toString());
                                equipmentAttendanceStatisticsN.setOperationDays(String.valueOf(operationDays));
                                equipmentAttendanceStatisticsN.setScenicSpotNameOperatingRate(spotOperateRate);
                                equipmentAttendanceStatisticsN.setEquipmentOperatingNum(String.valueOf(robotAttendance));
                                lists.add(equipmentAttendanceStatisticsN);

                            }
                        }

                    }else{

                        List<SysScenicSpot> scenicSpotOperated = sysScenicSpotMapper.getScenicSpotOperated("1");

                        List<String> list = DateUtil.betweenMonths(equipmentAttendanceStatistics.getStartTime(), equipmentAttendanceStatistics.getEndTime());
                        double spotOperateRate = 0;
                        //机器人运营率
                        double robotAttendance = 0;

                        //运营天数
                        int  operationDays = 0;


                        //查询天数
                        int monthSum = 0;

                        for (SysScenicSpot sysScenicSpot : scenicSpotOperated) {

                            SysOrder spotIdFirstOrder = sysOrderMapper.getSpotIdFirstOrder(sysScenicSpot.getScenicSpotId());
                            SysOrder spotIdLastOrder = sysOrderMapper.getSpotIdLastOrder(sysScenicSpot.getScenicSpotId());

                            //开业天数
                            Integer dates = 0;
                            if (!StringUtils.isEmpty(spotIdFirstOrder)){

                                dates =Integer.parseInt(DateUtil.findDates(spotIdFirstOrder.getCreateDate().substring(1, 10), spotIdLastOrder.getCreateDate().substring(1, 10))) ;

                            }

                            Integer robotWarehouseMonthDateCount = 0;

                            for (String date : list) {

                                robotWarehouseMonthDateCount = sysOrderMapper.getRobotWarehouseMonthDateCount(sysScenicSpot.getScenicSpotId(), date);
                                //运营天数
                                operationDays = robotWarehouseMonthDateCount;

                                monthSum = DateUtil.getMonthDay(date);

                                double operat = (double) robotWarehouseMonthDateCount / (1*monthSum);
                                spotOperateRate = Double.parseDouble(df.format(operat));

                                Long robotCount = sysRobotMapper.getSpotIdByRobotCount(sysScenicSpot.getScenicSpotId());

                                Integer exWarehouseCount = sysOrderMapper.getRobotWarehouseMonthCountNew(sysScenicSpot.getScenicSpotId(), equipmentAttendanceStatistics.getStartTime());

                                if (robotCount != null && robotCount != 0 && exWarehouseCount != null && exWarehouseCount != 0){

                                    double attendance = (double)exWarehouseCount / robotCount;
                                    robotAttendance = Double.parseDouble(df.format(attendance));
                                }


                                EquipmentAttendanceStatistics equipmentAttendanceStatisticsN =  equipmentAttendanceStatisticsMapper.getSpotIdLastAttendance(sysScenicSpot.getScenicSpotId().toString());

                                if (StringUtils.isEmpty(equipmentAttendanceStatisticsN)){


                                }else{

                                    equipmentAttendanceStatisticsN.setTodayDate(date);
                                    equipmentAttendanceStatisticsN.setOpeningDays(dates.toString());
                                    equipmentAttendanceStatisticsN.setOperationDays(String.valueOf(operationDays));
                                    equipmentAttendanceStatisticsN.setScenicSpotNameOperatingRate(spotOperateRate);
                                    equipmentAttendanceStatisticsN.setEquipmentOperatingNum(String.valueOf(robotAttendance));
                                    lists.add(equipmentAttendanceStatisticsN);

                                }
                            }
                        }
                    }

                }else if ("3".equals(equipmentAttendanceStatistics.getDateType())){//按照日查询
                    //判断是否有景区
                    if(!StringUtils.isEmpty(equipmentAttendanceStatistics.getScenicSpotId())){

                        List<String> list = DateUtil.betweenDays(equipmentAttendanceStatistics.getStartTime(), equipmentAttendanceStatistics.getEndTime());
                        //园区运营率
                        double spotOperateRate = 0;
                        //机器人运营率
                        double robotAttendance = 0;
                        SysOrder spotIdFirstOrder = sysOrderMapper.getSpotIdFirstOrder(Long.parseLong(equipmentAttendanceStatistics.getScenicSpotId()));
                        SysOrder spotIdLastOrder = sysOrderMapper.getSpotIdLastOrder(Long.parseLong(equipmentAttendanceStatistics.getScenicSpotId()));

                        //开业天数
                        Integer dates = 0;
                        if (!StringUtils.isEmpty(spotIdFirstOrder)){

                            dates =Integer.parseInt(DateUtil.findDates(spotIdFirstOrder.getCreateDate().substring(1, 10), spotIdLastOrder.getCreateDate().substring(1, 10))) ;

                        }

                        //运营天数
                        int  operationDays = 0;

                        //查询天数
                        int daySum = Integer.parseInt(DateUtil.findDates(equipmentAttendanceStatistics.getStartTime(),equipmentAttendanceStatistics.getEndTime())) ;
                        for (String date : list) {
                            //营业天数
                            Integer robotWarehouseYearDateCount = sysOrderMapper.getRobotWarehouseDayDateCount(Long.parseLong(equipmentAttendanceStatistics.getScenicSpotId()), date);
                            operationDays = robotWarehouseYearDateCount;

                            double operat = (double) operationDays / (1*daySum);
                            spotOperateRate =Double.parseDouble(df.format(operat));

                            Long robotCount = sysRobotMapper.getSpotIdByRobotCount(Long.parseLong(equipmentAttendanceStatistics.getScenicSpotId()));

                            Integer exWarehouseCount = sysOrderMapper.getRobotWarehouseDayCountNew(Long.parseLong(equipmentAttendanceStatistics.getScenicSpotId()), equipmentAttendanceStatistics.getStartTime());

                            if (robotCount != null && robotCount != 0 && exWarehouseCount != null && exWarehouseCount != 0){
                                double attendance = (double)exWarehouseCount / robotCount;
                                robotAttendance = Double.parseDouble(df.format(attendance));
                            }

                            EquipmentAttendanceStatistics equipmentAttendanceStatisticsN =  equipmentAttendanceStatisticsMapper.getSpotIdLastAttendance(equipmentAttendanceStatistics.getScenicSpotId());

                            if (StringUtils.isEmpty(equipmentAttendanceStatisticsN)){


                            }else{
                                equipmentAttendanceStatisticsN.setTodayDate(date);
                                equipmentAttendanceStatisticsN.setOpeningDays(dates.toString());
                                equipmentAttendanceStatisticsN.setOperationDays(String.valueOf(operationDays));
                                equipmentAttendanceStatisticsN.setScenicSpotNameOperatingRate(spotOperateRate);
                                equipmentAttendanceStatisticsN.setEquipmentOperatingNum(String.valueOf(robotAttendance));
                                lists.add(equipmentAttendanceStatisticsN);
                            }

                        }
                    }else{

                        List<SysScenicSpot> scenicSpotOperated = sysScenicSpotMapper.getScenicSpotOperated("1");

                        List<String> list = DateUtil.betweenDays(equipmentAttendanceStatistics.getStartTime(), equipmentAttendanceStatistics.getEndTime());
                        double spotOperateRate = 0;
                        //机器人运营率
                        double robotAttendance = 0;

                        //运营天数
                        int  operationDays = 0;


                        //查询天数
                        int daySum = 0;

                        for (SysScenicSpot sysScenicSpot : scenicSpotOperated) {

                            SysOrder spotIdFirstOrder = sysOrderMapper.getSpotIdFirstOrder(sysScenicSpot.getScenicSpotId());
                            SysOrder spotIdLastOrder = sysOrderMapper.getSpotIdLastOrder(sysScenicSpot.getScenicSpotId());

                            //开业天数
                            Integer dates = 0;
                            if (!StringUtils.isEmpty(spotIdFirstOrder)){

                                dates =Integer.parseInt(DateUtil.findDates(spotIdFirstOrder.getCreateDate().substring(1, 10), spotIdLastOrder.getCreateDate().substring(1, 10))) ;

                            }

                            Integer robotWarehouseDayDateCount = 0;

                            for (String date : list) {

                                robotWarehouseDayDateCount = sysOrderMapper.getRobotWarehouseDayDateCount(sysScenicSpot.getScenicSpotId(), date);
                                //运营天数
                                operationDays = robotWarehouseDayDateCount;


                                if(robotWarehouseDayDateCount != 0 && robotWarehouseDayDateCount != null){
                                    double operat = (double) robotWarehouseDayDateCount / (1*1);
                                    System.out.println(operat);
                                    spotOperateRate = Double.parseDouble(df.format(operat));

                                }

                                Long robotCount = sysRobotMapper.getSpotIdByRobotCount(sysScenicSpot.getScenicSpotId());
                                Integer exWarehouseCount = sysOrderMapper.getRobotWarehouseDayCountNew(sysScenicSpot.getScenicSpotId(), equipmentAttendanceStatistics.getStartTime());
                                if (robotCount != null && robotCount != 0 && exWarehouseCount != null && exWarehouseCount != 0){
                                    double attendance = (double)exWarehouseCount / robotCount;
                                    robotAttendance = Double.parseDouble(df.format(attendance));
                                }

                                EquipmentAttendanceStatistics equipmentAttendanceStatisticsN =  equipmentAttendanceStatisticsMapper.getSpotIdLastAttendance(sysScenicSpot.getScenicSpotId().toString());

                                if (StringUtils.isEmpty(equipmentAttendanceStatisticsN)){


                                }else{

                                    equipmentAttendanceStatisticsN.setTodayDate(date);
                                    equipmentAttendanceStatisticsN.setOpeningDays(dates.toString());
                                    equipmentAttendanceStatisticsN.setOperationDays(String.valueOf(operationDays));
                                    equipmentAttendanceStatisticsN.setScenicSpotNameOperatingRate(spotOperateRate);
                                    equipmentAttendanceStatisticsN.setEquipmentOperatingNum(String.valueOf(robotAttendance));
                                    lists.add(equipmentAttendanceStatisticsN);

                                }
                            }
                        }
                    }
                }
            }
            }


        }catch (Exception e){

            e.printStackTrace();

        }







        return lists;
    }
}
