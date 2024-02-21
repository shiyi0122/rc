package com.hna.hka.archive.management.assetsSystem.service.impl;

import com.github.pagehelper.PageHelper;
import com.hna.hka.archive.management.assetsSystem.dao.RobotEfficiencyAnalysisMapper;
import com.hna.hka.archive.management.assetsSystem.model.ChartReturnClass;
import com.hna.hka.archive.management.assetsSystem.model.SpotGrossProfitMargin;
import com.hna.hka.archive.management.assetsSystem.service.RobotEfficiencyAnalysisService;
import com.hna.hka.archive.management.system.dao.SysOrderMapper;
import com.hna.hka.archive.management.system.dao.SysRobotMapper;
import com.hna.hka.archive.management.system.dao.SysScenicSpotMapper;
import com.hna.hka.archive.management.system.model.SysScenicSpot;
import com.hna.hka.archive.management.system.util.*;
import io.swagger.annotations.ApiKeyAuthDefinition;
import net.bytebuddy.asm.Advice;
import org.apache.poi.ss.formula.functions.T;
import org.apache.shiro.crypto.hash.Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.keyvalue.core.SpelPropertyComparator;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.DecimalFormat;
import java.util.*;

/**
 * @Author zhang
 * @Date 2022/4/13 15:55
 * 机器人利用率
 */
@Service
public class RobotEfficiencyAnalysisServiceImpl implements RobotEfficiencyAnalysisService {


    @Autowired
    RobotEfficiencyAnalysisMapper robotEfficiencyAnalysisMapper;
    @Autowired
    SysOrderMapper sysOrderMapper;
    @Autowired
    SysRobotMapper sysRobotMapper;
    @Autowired
    SysScenicSpotMapper sysScenicSpotMapper;


    /**
     * 查询机器人利用率
     * @param type
     * @param spotId
     * @param startTime
     * @param endTime
     * @param pageNum
     * @param pageSize
     * @param sort
     * @return
     */
    @Override
    public List<Map<String, Object>> getSpotGrossProfitMarginList(Long type, String spotId,String startTime, String endTime, Integer pageNum, Integer pageSize, String sort) {


        List<Map<String,Object>> listE = new ArrayList<>();
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        Map<String , Object> map = new HashMap<>();
        try{
            //判断景区是否为空
            if (StringUtils.isEmpty(spotId)){
                //景区
                if (type == 1){
//                    List<SysScenicSpot> scenicSpotList = sysScenicSpotMapper.getScenicSpotListPaging(pageNum*pageSize,pageSize);
                    List<SysScenicSpot> scenicSpotList = sysScenicSpotMapper.getScenicSpotListNew(spotId);
                    for (SysScenicSpot sysScenicSpot : scenicSpotList) {
                        String  startDate = startTime + "-01";
                        String startDateN = DateUtil.addYear(endTime, 1);
                        String endDate = startDateN + "-01";
                        String endYear = DateUtil.addYear(startTime,1);
                        //流水
                        Double spotIdAndTimeByIncome = sysOrderMapper.getSpotIdAndTimeByIncome( sysScenicSpot.getScenicSpotId() , startTime, endYear);
                        if (StringUtils.isEmpty(spotIdAndTimeByIncome)){
                            spotIdAndTimeByIncome = 0d;
                        }else{
                            String format = decimalFormat.format(spotIdAndTimeByIncome);
                            spotIdAndTimeByIncome =Double.parseDouble(format) ;
                        }
                        //运营时长
                        Double spotIdAndTimeByOperationDuration = sysOrderMapper.getSpotIdAndTimeByOperationDuration(sysScenicSpot.getScenicSpotId() ,startTime,endYear);
                        if (StringUtils.isEmpty(spotIdAndTimeByOperationDuration)){

                            spotIdAndTimeByOperationDuration = 0d;
                        }else {
                            spotIdAndTimeByOperationDuration = Double.parseDouble(decimalFormat.format(spotIdAndTimeByOperationDuration) ) ;

                        }

                        //获取时间段内中有订单的机器人数量
                        Long orderCount = sysOrderMapper.getOrderRobotCountBySpotId(startDate,endDate,sysScenicSpot.getScenicSpotId().toString());
                        //获取该景区机器人总数量
                        Long spotIdByRobotCount = sysRobotMapper.getSpotIdByRobotCount(sysScenicSpot.getScenicSpotId());
                        //机器人利用率

                        Double robotUtilizationRate = 0d;
                        if (StringUtils.isEmpty(spotIdByRobotCount) || spotIdByRobotCount == 0){
                            robotUtilizationRate = 0d;
                        }else{
                            robotUtilizationRate = orderCount.doubleValue() / spotIdByRobotCount * 100;
                            String format = decimalFormat.format(robotUtilizationRate);
                            robotUtilizationRate = Double.parseDouble(format);
                        }

                        //利用率同比
                        Double yearUtilization =0d;
                        //同期利用率
                        String lastYearCurrentDate   = DateUtil.getLastYearCurrentDateYear(startTime);
                        String lastYearCurrentDateEnd = DateUtil.getLastYearCurrentDateYear(endTime);
                        String addLastYearCurrentDateEnd = DateUtil.addYear(lastYearCurrentDateEnd,1);
                        Long orderCountUp = sysOrderMapper.getOrderRobotCountBySpotId(lastYearCurrentDate,addLastYearCurrentDateEnd,sysScenicSpot.getScenicSpotId().toString());

                        Double utilizationRateUp = 0d;
                        if (StringUtils.isEmpty(spotIdByRobotCount) || spotIdByRobotCount ==0){
                            utilizationRateUp = 0d;
                        }else{
                            utilizationRateUp =  orderCountUp.doubleValue() / spotIdByRobotCount * 100;
                            String format = decimalFormat.format(utilizationRateUp);
                            utilizationRateUp =  Double.parseDouble(format);
                        }
                        if (robotUtilizationRate != 0 && utilizationRateUp != 0){
                            yearUtilization = (robotUtilizationRate - utilizationRateUp)/utilizationRateUp * 100;
                            String format = decimalFormat.format(yearUtilization);
                            yearUtilization = Double.parseDouble(format);
                        }
                        //利用率环比
                        Double monthUtilization =0d;

//                        String monthUp = DateUtil.calcLastMonth(startDate);
//                        String monthUps = monthUp + "-01" ;
//                        String monthLower = DateUtil.calcLastMonth(endDate);
//                        String monthLowers = DateUtil.addMouth(monthLower, 1);
//                        monthLowers = monthLowers + "-01";

                        Long orderCountMonthUp = sysOrderMapper.getOrderRobotCountBySpotId(lastYearCurrentDate,addLastYearCurrentDateEnd,sysScenicSpot.getScenicSpotId().toString());

                        //环比上期
                        Double utilizationRateUpMonth =0d;
                        if (StringUtils.isEmpty(spotIdByRobotCount) || spotIdByRobotCount ==0){
                            utilizationRateUpMonth =0d;
                        }else{
                            utilizationRateUpMonth =  orderCountMonthUp.doubleValue() / spotIdByRobotCount * 100;
                            String format = decimalFormat.format(utilizationRateUpMonth);
                            utilizationRateUpMonth = Double.parseDouble(format);
                        }
                        if (utilizationRateUpMonth != 0 && robotUtilizationRate != 0){
                            monthUtilization = (robotUtilizationRate - utilizationRateUpMonth) / utilizationRateUpMonth  * 100;
                            String format = decimalFormat.format(monthUtilization);
                            monthUtilization = Double.parseDouble(format);
                        }
                        map = new HashMap<>();
                        map.put("time",startTime + "至" + endTime);//时间段
                        map.put("spotId",sysScenicSpot.getScenicSpotId());//景区id
                        map.put("spotName",sysScenicSpot.getScenicSpotName());//景区名称
                        map.put("spotIdAndTimeByIncome",spotIdAndTimeByIncome);//流水
                        map.put("spotIdAndTimeByOperationDuration",spotIdAndTimeByOperationDuration);//运营时长
                        map.put("utilizationRate",robotUtilizationRate);//利用率
                        map.put("yearOnYear",yearUtilization);//利用率同比
                        map.put("monthOnMonth",monthUtilization);//利用率环比
                        listE.add(map);
                    }
                    Integer scenicSpotCount = sysScenicSpotMapper.getScenicSpotCount();
                    map = new HashMap<>();
                    map.put("total",scenicSpotCount);
                    listE.add(map);

                }else if (type == 2 ){//时间
                    //景区
                    List<SysScenicSpot> scenicSpotList = sysScenicSpotMapper.getScenicSpotListNew(spotId);
                    //时间段
                    List<String> list = DateUtil.betweenMonths(startTime, endTime);
                    //景区列表
                    for (SysScenicSpot sysScenicSpot : scenicSpotList) {
                        for (String time : list) {
                            String startDate = time + "-01" ;
                            String s1 = DateUtil.addMouth(time, 1);
                            String endDate  = s1 + "-01";
                            String endDateT = DateUtil.addDay(endDate,-1);
                            //流水
                            Double spotIdAndTimeByIncome = sysOrderMapper.getSpotIdAndTimeByIncome( sysScenicSpot.getScenicSpotId() , startDate, endDate);
                            if (StringUtils.isEmpty(spotIdAndTimeByIncome)){
                                spotIdAndTimeByIncome = 0d;
                            }else{
                                String format = decimalFormat.format(spotIdAndTimeByIncome);
                                spotIdAndTimeByIncome =Double.parseDouble(format) ;
                            }

                            //运营时长
                            Double spotIdAndTimeByOperationDuration = sysOrderMapper.getSpotIdAndTimeByOperationDuration(sysScenicSpot.getScenicSpotId() ,startDate,endDateT);
                            if (StringUtils.isEmpty(spotIdAndTimeByOperationDuration)){

                                spotIdAndTimeByOperationDuration = 0d;
                            }else {
                                spotIdAndTimeByOperationDuration =Double.parseDouble(decimalFormat.format(spotIdAndTimeByOperationDuration))  ;

                            }
                            //获取时间段内中有订单的机器人数量
                            Long orderCount = sysOrderMapper.getOrderRobotCountBySpotId(startDate,endDate,sysScenicSpot.getScenicSpotId().toString());
                            //获取该景区机器人总数量
                            Long spotIdByRobotCount = sysRobotMapper.getSpotIdByRobotCount(sysScenicSpot.getScenicSpotId());
                            //机器人利用率
                            Double utilizationRate =0d;
                            if (StringUtils.isEmpty(spotIdByRobotCount) || spotIdByRobotCount == 0){
                                utilizationRate =0d;
                            }else {
                                 utilizationRate = orderCount.doubleValue() / spotIdByRobotCount * 100;
                                String format = decimalFormat.format(utilizationRate);
                                utilizationRate = Double.parseDouble(format);
                            }
                            //利用率同比
                            Double yearUtilization =0d;
                            //同期利用率
                            String lastYearCurrentDate = DateUtil.getLastYearCurrentDate(time);
                            String startDateUp = lastYearCurrentDate + "-01" ;
                            String dateUp = DateUtil.addMouth(lastYearCurrentDate, 1);
                            String endDateUp  = dateUp + "-01";
                            Long orderCountUp = sysOrderMapper.getOrderRobotCountBySpotId(startDateUp,endDateUp,sysScenicSpot.getScenicSpotId().toString());
                            Double utilizationRateUp = 0d;
                            if (StringUtils.isEmpty(spotIdByRobotCount) || spotIdByRobotCount ==0){
                                utilizationRateUp = 0d;
                            }else{
                                 utilizationRateUp =  orderCountUp.doubleValue() / spotIdByRobotCount *100;
                                String format = decimalFormat.format(utilizationRateUp);
                                utilizationRateUp = Double.parseDouble(format);
                            }

                            if (utilizationRate != 0 && utilizationRateUp != 0){
                                yearUtilization = (utilizationRate - utilizationRateUp)/utilizationRateUp * 100;
                                String format = decimalFormat.format(yearUtilization);
                                yearUtilization = Double.parseDouble(format);
                            }
                            //利用率环比
                            Double monthUtilization =0d;

                            String monthUp = DateUtil.calcLastMonth(time);
                            String monthUps = monthUp + "-01" ;

                            //上期利用率
                            Long orderCountMonthUp = sysOrderMapper.getOrderRobotCountBySpotId(monthUps,startDate,sysScenicSpot.getScenicSpotId().toString());

                            Double utilizationRateUpMonth =0d;
                            if (StringUtils.isEmpty(spotIdByRobotCount) || spotIdByRobotCount ==0){
                                utilizationRateUpMonth = 0d;
                            }else{
                                utilizationRateUpMonth =  orderCountMonthUp.doubleValue() / spotIdByRobotCount *100;
                                String format = decimalFormat.format(utilizationRateUpMonth);
                                utilizationRateUpMonth = Double.parseDouble(format);
                            }

                            if (utilizationRateUpMonth != 0 && utilizationRate != 0){
                                monthUtilization = (utilizationRate - utilizationRateUpMonth) / utilizationRateUpMonth  * 100;
                                String format = decimalFormat.format(monthUtilization);
                                monthUtilization = Double.parseDouble(format);
                            }

                            map = new HashMap<>();
                            map.put("time",time);//时间段
                            map.put("spotId",sysScenicSpot.getScenicSpotId());//景区id
                            map.put("spotName",sysScenicSpot.getScenicSpotName());//景区名称
                            map.put("spotIdAndTimeByIncome",spotIdAndTimeByIncome);//流水
                            map.put("spotIdAndTimeByOperationDuration",spotIdAndTimeByOperationDuration);//运营时长
                            map.put("utilizationRate",utilizationRate);//利用率
                            map.put("yearOnYear",yearUtilization);//利用率同比
                            map.put("monthOnMonth",monthUtilization);//利用率环比
                            listE.add(map);
                        }
                    }

                    //排序
                    Collections.sort(listE, new Comparator<Map<String, Object>>() {
                        @Override
                        public int compare(Map<String, Object> o1, Map<String, Object> o2) {
//                Object utilizationRate = o1.get("utilizationRate");
//                if (StringUtils.isEmpty(utilizationRate)){
//                    return -1;
//                }
//                Object utilizationRate1 = o2.get("utilizationRate");
//                if (StringUtils.isEmpty(utilizationRate1)){
//                    return -1;
//                }
//                return o1.get("utilizationRate").toString().compareTo(o2.get("utilizationRate").toString());
                            return o2.get("utilizationRate").toString().compareTo(o1.get("utilizationRate").toString());
                        }
                    });


                    if (listE.size()>0){
                        List<Map<String, Object>> maps = this.ListSplit(listE, pageNum, pageSize);

                        int size = listE.size();
                        HashMap<String, Object> lengthMap = new HashMap<>();
                        lengthMap.put("total",size);
                        maps.add(lengthMap);

                        return maps;
                    }else{
                        return listE;
                    }
                }
            }else{//景区不为空
                //景区查询
                if (type == 1){
                    PageHelper.startPage(pageNum,pageSize);
                    //景区名称
                    SysScenicSpot sysScenicSpot = sysScenicSpotMapper.selectByPrimaryKey(Long.valueOf(spotId));

                    String  startDate = startTime + "-01";
                    String startDateN = DateUtil.addYear(endTime, 1);
                    String endDate = startDateN + "-01";
                    String endYear = DateUtil.addYear(endTime,1);
                    //流水
                    Double spotIdAndTimeByIncome = sysOrderMapper.getSpotIdAndTimeByIncome( Long.parseLong(spotId) , startTime, endYear);
                    if (StringUtils.isEmpty(spotIdAndTimeByIncome)){
                        spotIdAndTimeByIncome = 0d;
                    }else{
                        String format = decimalFormat.format(spotIdAndTimeByIncome);
                        spotIdAndTimeByIncome =Double.parseDouble(format) ;
                    }
                    //运营时长
                    Double spotIdAndTimeByOperationDuration = sysOrderMapper.getSpotIdAndTimeByOperationDuration(Long.parseLong(spotId) ,startDate,endYear);
                    if (StringUtils.isEmpty(spotIdAndTimeByOperationDuration)){

                        spotIdAndTimeByOperationDuration = 0d;
                    }else {
                        spotIdAndTimeByOperationDuration =Double.parseDouble(decimalFormat.format(spotIdAndTimeByOperationDuration))  ;
                    }
                    //获取时间段内中有订单的机器人数量
                    Long orderCount = sysOrderMapper.getOrderRobotCountBySpotId(startDate,endDate,spotId);
                    //获取该景区机器人总数量
                    Long spotIdByRobotCount = sysRobotMapper.getSpotIdByRobotCount(Long.valueOf(spotId));
                    //机器人利用率

                    Double utilizationRate = 0d;
                    if (StringUtils.isEmpty(spotIdByRobotCount) || spotIdByRobotCount == 0){
                        utilizationRate = 0d;
                    }else {
                        utilizationRate = orderCount.doubleValue() / spotIdByRobotCount *100;
                        String format = decimalFormat.format(utilizationRate);
                        utilizationRate = Double.parseDouble(format);
                    }


                    //利用率同比
                    Double yearUtilization =0d;
                    //同期利用率
                    String lastYearCurrentDate   = DateUtil.getLastYearCurrentDateYear(startTime);
                    String lastYearCurrentDateEnd = DateUtil.getLastYearCurrentDateYear(endTime);
                    String addLastYearCurrentDateEnd = DateUtil.addYear(lastYearCurrentDateEnd,1);
                    Long orderCountUp = sysOrderMapper.getOrderRobotCountBySpotId(lastYearCurrentDate,addLastYearCurrentDateEnd,spotId);

                    Double utilizationRateUp = 0d;
                    if (StringUtils.isEmpty(spotIdByRobotCount) || spotIdByRobotCount ==0){
                        utilizationRateUp = 0d;
                    }else{
                         utilizationRateUp =  orderCountUp.doubleValue() / spotIdByRobotCount * 100;
                        String format = decimalFormat.format(utilizationRateUp);
                        utilizationRateUp = Double.parseDouble(format);
                    }

                    if (utilizationRate != 0 && utilizationRateUp != 0){
                        yearUtilization = (utilizationRate - utilizationRateUp)/utilizationRateUp * 100;
                        String format = decimalFormat.format(yearUtilization);
                        yearUtilization = Double.parseDouble(format);
                    }
                    //利用率环比
                    Double monthUtilization =0d;


                    Long orderCountMonthUp = sysOrderMapper.getOrderRobotCountBySpotId(lastYearCurrentDate,addLastYearCurrentDateEnd,spotId);

                    Double utilizationRateUpMonth =0d;
                    if (StringUtils.isEmpty(spotIdByRobotCount) || spotIdByRobotCount ==0){
                        utilizationRateUpMonth =0d;
                    }else{
                        utilizationRateUpMonth =  orderCountMonthUp.doubleValue() / spotIdByRobotCount * 100;
                        String format = decimalFormat.format(utilizationRateUpMonth);
                        utilizationRateUpMonth = Double.parseDouble(format);
                    }


                    if (utilizationRateUpMonth != 0 && utilizationRate != 0){
                        monthUtilization = (utilizationRate - utilizationRateUpMonth) / utilizationRateUpMonth  * 100;
                        String format = decimalFormat.format(monthUtilization);
                        monthUtilization = Double.parseDouble(format);
                    }


                    map = new HashMap<>();
                    map.put("time",startTime + "至" +endTime);//时间段
                    map.put("spotId",spotId);//景区id
                    map.put("spotName",sysScenicSpot.getScenicSpotName());//景区名称
                    map.put("spotIdAndTimeByIncome",spotIdAndTimeByIncome);//流水
                    map.put("spotIdAndTimeByOperationDuration",spotIdAndTimeByOperationDuration);//运营时长
                    map.put("utilizationRate",utilizationRate);//利用率
                    map.put("yearOnYear",yearUtilization);//利用率同比
                    map.put("monthOnMonth",monthUtilization);//利用率环比
                    listE.add(map);
                    //获取总条数

                    map = new HashMap<>();
                    map.put("total",listE.size());
                    listE.add(map);

                }else if (type == 2 ){//时间查询
                    //时间段
                    List<String> list = DateUtil.betweenMonths(startTime, endTime);
                    for (String s : list) {
                        //景区名称
                        SysScenicSpot sysScenicSpot = sysScenicSpotMapper.selectByPrimaryKey(Long.valueOf(spotId));
                        String startDate = s + "-01" ;
                        String s1 = DateUtil.addMouth(s, 1);
                        String endDate  = s1 + "-01";
                        String endDateT = DateUtil.addDay(endDate,-1);
                        //流水
                        Double spotIdAndTimeByIncome = sysOrderMapper.getSpotIdAndTimeByIncome( Long.parseLong(spotId) , startDate, endDate);
                        if (StringUtils.isEmpty(spotIdAndTimeByIncome)){
                            spotIdAndTimeByIncome = 0d;
                        }else{
                            String format = decimalFormat.format(spotIdAndTimeByIncome);
                            spotIdAndTimeByIncome =Double.parseDouble(format) ;
                        }
                        //运营时长
                        Double spotIdAndTimeByOperationDuration = sysOrderMapper.getSpotIdAndTimeByOperationDuration(Long.parseLong(spotId) ,startDate,endDateT);
                        if (StringUtils.isEmpty(spotIdAndTimeByOperationDuration)){

                            spotIdAndTimeByOperationDuration = 0d;
                        }else {
                            spotIdAndTimeByOperationDuration = Double.parseDouble(decimalFormat.format( spotIdAndTimeByOperationDuration));

                        }
                        //获取时间段内中有订单的机器人数量
                        Long orderCount = sysOrderMapper.getOrderRobotCountBySpotId(startDate,endDate,spotId);
                        //获取该景区机器人总数量
                        Long spotIdByRobotCount = sysRobotMapper.getSpotIdByRobotCount(Long.valueOf(spotId));
                        //机器人利用率
                        Double utilizationRate =0d;
                        if (StringUtils.isEmpty(spotIdByRobotCount) || spotIdByRobotCount == 0){

                        }else{
                             utilizationRate = orderCount.doubleValue() / spotIdByRobotCount * 100;
                             String format = decimalFormat.format(utilizationRate);
                             utilizationRate = Double.parseDouble(format);
                        }
                        //利用率同比
                        Double yearUtilization =0d;
                        //同期利用率
                        String lastYearCurrentDate = DateUtil.getLastYearCurrentDate(s);
                        String startDateUp = lastYearCurrentDate + "-01" ;
                        String dateUp = DateUtil.addMouth(lastYearCurrentDate, 1);
                        String endDateUp  = dateUp + "-01";
                        Long orderCountUp = sysOrderMapper.getOrderRobotCountBySpotId(startDateUp,endDateUp,spotId);

                        Double utilizationRateUp =0d;
                        if (StringUtils.isEmpty(spotIdByRobotCount) || spotIdByRobotCount == 0){

                        }else{
                             utilizationRateUp =  orderCountUp.doubleValue() / spotIdByRobotCount * 100;
                             String format = decimalFormat.format(utilizationRateUp);
                             utilizationRateUp = Double.parseDouble(format);
                        }

                        if (utilizationRate != 0 && utilizationRateUp != 0){
                            yearUtilization = (utilizationRate - utilizationRateUp)/utilizationRateUp * 100;
                            String format = decimalFormat.format(yearUtilization);
                            yearUtilization = Double.parseDouble(format);
                        }
                        //利用率环比
                        Double monthUtilization =0d;

                        String monthUp = DateUtil.calcLastMonth(s);
                        String monthUps = monthUp + "-01" ;

                        Long orderCountMonthUp = sysOrderMapper.getOrderRobotCountBySpotId(monthUps,startDate,spotId);
                        Double utilizationRateUpMonth =0d;
                        if (StringUtils.isEmpty(spotIdByRobotCount) ||  spotIdByRobotCount == 0){
                            utilizationRateUpMonth = 0d;
                        }else{
                             utilizationRateUpMonth =  orderCountMonthUp.doubleValue() / spotIdByRobotCount * 100;
                            String format = decimalFormat.format(utilizationRateUpMonth);
                            utilizationRateUpMonth = Double.parseDouble(format);
                        }


                        if (utilizationRateUpMonth != 0 && utilizationRate != 0){
                            monthUtilization = (utilizationRate - utilizationRateUpMonth) / utilizationRateUpMonth  * 100;
                            String format = decimalFormat.format(monthUtilization);
                            monthUtilization = Double.parseDouble(format);
                        }

                        map = new HashMap<>();
                        map.put("time",s);//时间段
                        map.put("spotId",spotId);//景区id
                        map.put("spotName",sysScenicSpot.getScenicSpotName());//景区名称
                        map.put("spotIdAndTimeByIncome",spotIdAndTimeByIncome);//流水
                        map.put("spotIdAndTimeByOperationDuration",spotIdAndTimeByOperationDuration);//运营时长
                        map.put("utilizationRate",utilizationRate);//利用率
                        map.put("yearOnYear",yearUtilization);//利用率同比
                        map.put("monthOnMonth",monthUtilization);//利用率环比
                        listE.add(map);
                    }




                    if (listE.size()>0){
                        List<Map<String, Object>> maps = this.ListSplit(listE, pageNum, pageSize);

                        int size = listE.size();
                        HashMap<String, Object> lengthMap = new HashMap<>();
                        lengthMap.put("total",size);
                        maps.add(lengthMap);

                        return maps;
                    }else{
                        return listE;
                    }
                }
            }

        }catch (Exception e){
         e.printStackTrace();
        }

        Map<String, Object> remove = listE.remove(listE.size() - 1);


        Collections.sort(listE, new Comparator<Map<String, Object>>() {
            @Override
            public int compare(Map<String, Object> o1, Map<String, Object> o2) {
//                Object utilizationRate = o1.get("utilizationRate");
//                if (StringUtils.isEmpty(utilizationRate)){
//                    return -1;
//                }
//                Object utilizationRate1 = o2.get("utilizationRate");
//                if (StringUtils.isEmpty(utilizationRate1)){
//                    return -1;
//                }
//                return o1.get("utilizationRate").toString().compareTo(o2.get("utilizationRate").toString());
                return o2.get("utilizationRate").toString().compareTo(o1.get("utilizationRate").toString());
            }
        });

        if (listE.size()>0){
            List<Map<String, Object>> maps = this.ListSplit(listE, pageNum, pageSize);

            int size = listE.size();
            HashMap<String, Object> lengthMap = new HashMap<>();
            lengthMap.put("total",size);
            maps.add(lengthMap);

            return maps;
        }else{
            return listE;
        }

//        listE.add(remove);


//        return listE;

    }

    /**
     * 机器人利用率图表
     *
     * @param type
     * @param spotId
     * @param startTime
     * @param endTime
     * @param pageNum
     * @param pageSize
     * @param sort
     * @return
     */

    @Override
    public ReturnModel getSpotGrossProfitMarginChartList(Long type, String spotId, String startTime, String endTime, Integer pageNum, Integer pageSize, String sort) {

        Map<String, Object> mapL = new HashMap<>();
        List<Map<String,Object>> list = new ArrayList<>();
        List<String> dateL = new ArrayList<>();
        List<String> utilizationRateL = new ArrayList<>();
        List<String> yearOnYearL = new ArrayList<>();
        List<String> monthOnMonthL = new ArrayList<>();
        ReturnModel returnModel = new ReturnModel();

        try {
            if (StringUtils.isEmpty(spotId)){//没有景区id，查询所有景区，
                Map<String, Object> map = new HashMap<>();
                if (type == 1) {//景区
                    HashMap<String, Object> search = new HashMap<>();
                    DecimalFormat df = new DecimalFormat("0.00");//格式化小数
//                    PageHelper.startPage(pageSize,pageNum);
                    List<SysScenicSpot> scenicSpotList = sysScenicSpotMapper.getScenicSpotListNew(spotId);

                    for (SysScenicSpot sysScenicSpot : scenicSpotList) {
                        map = new HashMap<>();
                        search = new HashMap<>();
                        //景区总机器人
                        long spotIdByRobotCount = sysRobotMapper.getSpotIdByRobotCount(sysScenicSpot.getScenicSpotId());

//                        String startTimeN = startTime+"-01";

                        String endTimeN = DateUtil.addMouth(endTime, 1);
                        search.put("spotId",sysScenicSpot.getScenicSpotId());
                        search.put("startTime",startTime);
                        search.put("endTime",endTimeN);


                        //有订单机器人数量
                        int robotCount =  sysOrderMapper.getOrderSpotRobotCount(search);

                        //机器人利用率
                        double utilizationRate = 0;
                        if (spotIdByRobotCount != 0){
                            utilizationRate =  (double) robotCount / spotIdByRobotCount *100 ;
                        }
                        String format = df.format(utilizationRate);
                        utilizationRate = Double.parseDouble(format);

                        //计算利用率同比
                        String lastYearCurrentStartDate = DateUtil.getLastYearCurrentDate(startTime);
                        String lastYearCurrentEndDate = DateUtil.getLastYearCurrentDate(endTimeN);
                        search = new HashMap<>();
                        search.put("spotId",sysScenicSpot.getScenicSpotId());
                        search.put("startTime",lastYearCurrentStartDate);
                        search.put("endTime",lastYearCurrentEndDate);
                        //同期机器人数量
                        int lastYearRobotCount =  sysOrderMapper.getOrderSpotRobotCount(search);
                        //同期机器人利用率
                        double lastYearUtilizationRate = 0;
                        if (spotIdByRobotCount != 0){
                           lastYearUtilizationRate =   (double) lastYearRobotCount / spotIdByRobotCount * 100;
                        }
                        String lastYearFormat = df.format(lastYearRobotCount);
                        lastYearUtilizationRate = Double.parseDouble(lastYearFormat);
                        //同比
                        double yearOnYear = 0;
                        if (utilizationRate != 0 && lastYearUtilizationRate != 0){
                             yearOnYear =(utilizationRate - lastYearUtilizationRate) / lastYearUtilizationRate *100 ;
                        }

                        String yearOnYearFormat = df.format(yearOnYear);
                        yearOnYear = Double.parseDouble(yearOnYearFormat);

                        //计算利用率环比
                        //相差天数
                        String count = DateUtil.findMonths(startTime, endTime);
                        String countN = null;
                        if (Long.parseLong(count)  > 0){
                            countN  = MathUtil.positiveNumberChangeNegative(Long.parseLong(count));
                        }

                        String lastMonthStartTime = DateUtil.addMouth(startTime, Integer.parseInt(countN));
                        String lastMonthEndTime = DateUtil.addMouth(endTime, Integer.parseInt(countN)+1);
                        search = new HashMap<>();
                        search.put("spotId",sysScenicSpot.getScenicSpotId());
                        search.put("startTime",lastMonthStartTime);
                        search.put("endTime",lastMonthEndTime);
                        //获取环比有订单机器人数量
                        int lastMonthRobotCount =  sysOrderMapper.getOrderSpotRobotCount(search);

                        //环比机器人出勤率
                        double lastMonthUtilizationRate = 0;
                        if (spotIdByRobotCount != 0){
                            lastMonthUtilizationRate = (double) lastMonthRobotCount / spotIdByRobotCount * 100;

                        }
                        String lastMonthFormat = df.format(lastMonthUtilizationRate);

                        lastMonthUtilizationRate = Double.parseDouble(lastMonthFormat);
                        //计算环比
                        double monthOrMonth = 0;
                        if (utilizationRate != 0 && lastMonthUtilizationRate != 0){
                            monthOrMonth =(utilizationRate - lastMonthUtilizationRate) / lastMonthUtilizationRate * 100 ;
                        }

                        String monthOrMonthFormat = df.format(monthOrMonth);
                        monthOrMonth = Double.parseDouble(monthOrMonthFormat);

                        map.put("spotId",sysScenicSpot.getScenicSpotId());
                        map.put("spotName",sysScenicSpot.getScenicSpotName());
                        map.put("utilizationRate",utilizationRate);
                        map.put("yearOnYear",lastYearUtilizationRate);
                        map.put("date",startTime+"至"+endTime);
                        map.put("monthOnMonth",lastMonthUtilizationRate);
                        list.add(map);
                        dateL.add(startTime+"至"+endTime);
                        utilizationRateL.add(String.valueOf(utilizationRate) );
                        yearOnYearL.add(String.valueOf(yearOnYear));
                        monthOnMonthL.add(String.valueOf(monthOrMonth));
                    }
                    mapL.put("realAmout",list);
                    mapL.put("dateTime",dateL);
                    mapL.put("yearOnYear",yearOnYearL);
                    mapL.put("monthOnMonth",monthOnMonthL);
                    mapL.put("utilizationRate",utilizationRateL);
//                    List<ChartReturnClass> page = this.page(list, pageSize, pageNum);

                    returnModel.setData(mapL);
                    returnModel.setTotal(sysScenicSpotMapper.getScenicSpotCount());

//                    pageDataResult.setTotals(sysScenicSpotMapper.getScenicSpotCount());
                    return returnModel;

                }else{//日期
                //两个时间之间的日期
                List<String> dateList = DateUtil.betweenMonths(startTime, endTime);

                Map<String, Object> search = new HashMap<>();
                DecimalFormat df = new DecimalFormat("0.00");//格式化小数
                 Map<String, Object> hashMap = new HashMap<>();
                //获取可以使用的机器人数量
                int robotCount = sysRobotMapper.getOperateRobotCount();
                for (String date : dateList) {
                    hashMap = new HashMap<>();
                    search = new HashMap<>();
                    search.put("date",date);
                    //获取该时间中有订单的机器人数量
                    int orderRobotCount = sysOrderMapper.getOrderRobotCount(search);

                    //机器人利用率
                    double utilizationRate =(double) orderRobotCount / robotCount * 100;
                    String format = df.format(utilizationRate);
                    utilizationRate = Double.parseDouble(format);

                    hashMap.put("date",date);

                    hashMap.put("utilizationRate",utilizationRate);
                    //计算利用率同比
                    String lastYearCurrentDate = DateUtil.getLastYearCurrentDate(date);
                    search = new HashMap<>();
                    search.put("date",lastYearCurrentDate);
                    int lastYearOrderRobotCount = sysOrderMapper.getOrderRobotCount(search);
                    double lastYearUtilizationRate = (double) lastYearOrderRobotCount / robotCount *100;
                    String format1 = df.format(lastYearUtilizationRate);
                    lastYearUtilizationRate = Double.parseDouble(format1);
                    //计算同比
                    double yearOnYear =(utilizationRate - lastYearUtilizationRate) / lastYearUtilizationRate * 100 ;
                    String format2 = df.format(yearOnYear);
                    yearOnYear = Double.parseDouble(format2);

                    hashMap.put("yearOnYear",yearOnYear);
                    //计算利用率环比
                    String lastMonthDate = DateUtil.addMouth(date, -1);
                    search = new HashMap<>();
                    search.put("date",lastMonthDate);
                    int lastMonthOrderRobotCount = sysOrderMapper.getOrderRobotCount(search);
                    double lastMonthUtilizationRate =  (double)lastMonthOrderRobotCount / robotCount * 100;
                    String format3 = df.format(lastMonthUtilizationRate);
                    lastMonthUtilizationRate = Double.parseDouble(format3);
                    double monthOnMonth = (utilizationRate - lastMonthUtilizationRate)/ lastMonthUtilizationRate * 100;
                    String format4 = df.format(monthOnMonth);
                    monthOnMonth = Double.parseDouble(format4);

                    hashMap.put("monthOnMonth",monthOnMonth);
                    list.add(hashMap);
                    dateL.add(date);
                    utilizationRateL.add(String.valueOf(utilizationRate) );
                    yearOnYearL.add(String.valueOf(yearOnYear));
                    monthOnMonthL.add(String.valueOf(monthOnMonth));

                    }

                }

            }else{//单个景区(传入了景区id)
                if (type == 1){//按景区
                    SysScenicSpot sysScenicSpot = sysScenicSpotMapper.selectByScenicSpotId(Long.parseLong(spotId));
                    HashMap<String,Object> search = new HashMap<>();
                    Map map  = new HashMap<>();
                    //景区总机器人
                    long spotIdByRobotCount = sysRobotMapper.getSpotIdByRobotCount(Long.parseLong(spotId));

//                        String startTimeN = startTime+"-01";

                    String endYear = DateUtil.addYear(endTime, 1);
                    search.put("spotId",spotId);
                    search.put("startTime",startTime);
                    search.put("endTime",endYear);

                    //有订单机器人数量
                    int robotCount =  sysOrderMapper.getOrderSpotRobotCount(search);

                    //机器人利用率
                    double utilizationRate = 0;
                    if (spotIdByRobotCount != 0){
                        utilizationRate =  (double) robotCount / spotIdByRobotCount * 100;
                    }

                    DecimalFormat df = new DecimalFormat("0.00");//格式化小数

                    String utilizationRateFormat = df.format(utilizationRate);
                    utilizationRate =  Double.parseDouble(utilizationRateFormat);

                    //计算利用率同比
                    String lastYearCurrentStartDate = DateUtil.getLastYearCurrentDateYear(startTime);
                    String lastYearCurrentEndDate = DateUtil.getLastYearCurrentDateYear(endTime);
                    String addLastYearCurrentEndDate = DateUtil.addYear(endTime,1);
                    search = new HashMap<>();
                    search.put("spotId",spotId);
                    search.put("startTime",lastYearCurrentStartDate);
                    search.put("endTime",addLastYearCurrentEndDate);
                    //同期机器人数量
                    int lastYearRobotCount =  sysOrderMapper.getOrderSpotRobotCount(search);
                    //同期机器人利用率
                    double lastYearUtilizationRate = 0;
                    if (spotIdByRobotCount != 0){
                        lastYearUtilizationRate =   (double) lastYearRobotCount / spotIdByRobotCount * 100;
                    }

                    String lastYearUtilizationRateFormat = df.format(lastYearUtilizationRate);
                    lastYearUtilizationRate =  Double.parseDouble(lastYearUtilizationRateFormat);
                    //同比
                    double yearOnYear = 0;
                    if (utilizationRate != 0 && lastYearUtilizationRate != 0){
                        yearOnYear =(utilizationRate - lastYearUtilizationRate) / lastYearUtilizationRate * 100 ;
                    }

                    String yearOnYearFormat = df.format(yearOnYear);
                    yearOnYear = Double.parseDouble(yearOnYearFormat);
                    //计算利用率环比
//                    //相差天数
//                    String count = DateUtil.findMonths(startTime, endTime);
//                    String countN = null;
//                    if (Long.parseLong(count)  > 0){
//                        countN  = MathUtil.positiveNumberChangeNegative(Long.parseLong(count));
//                    }
//                    String lastMonthStartTime = DateUtil.addMouth(startTime, Integer.parseInt(countN));
//                    String lastMonthEndTime = DateUtil.addMouth(endTime, Integer.parseInt(countN)+1);
                    search = new HashMap<>();
                    search.put("spotId",spotId);
                    search.put("startTime",lastYearCurrentStartDate);
                    search.put("endTime",addLastYearCurrentEndDate);
                    //获取环比有订单机器人数量
                    int lastMonthRobotCount =  sysOrderMapper.getOrderSpotRobotCount(search);
                    //环比机器人出勤率
                    double lastMonthUtilizationRate = 0;
                    if (spotIdByRobotCount != 0){
                        lastMonthUtilizationRate = (double) lastMonthRobotCount / spotIdByRobotCount * 100;

                    }
                    //计算环比
                    double monthOrMonth = 0;
                    if (utilizationRate != 0 && lastMonthUtilizationRate != 0){
                        monthOrMonth = Double.parseDouble(df.format((utilizationRate - lastMonthUtilizationRate) / lastMonthUtilizationRate * 100)) ;
                    }

                    map.put("spotId",sysScenicSpot.getScenicSpotId());
                    map.put("spotName",sysScenicSpot.getScenicSpotName());
                    map.put("utilizationRate",utilizationRate);
                    map.put("yearOnYear",lastYearUtilizationRate);
                    map.put("date",startTime +"至"+endTime);
                    map.put("monthOnMonth",monthOrMonth);

                    list.add(map);
                    dateL.add(startTime +"至"+endTime);
                    utilizationRateL.add(String.valueOf(utilizationRate));
                    yearOnYearL.add(String.valueOf(yearOnYear));
                    monthOnMonthL.add(String.valueOf(monthOrMonth));
                } else {//按照时间
                    //两个时间之间的日期
                    List<String> dateList = DateUtil.betweenMonths(startTime, endTime);
                    SysScenicSpot sysScenicSpot = sysScenicSpotMapper.selectByScenicSpotId(Long.parseLong(spotId));
                    long spotIdByRobotCount = sysRobotMapper.getSpotIdByRobotCount(Long.parseLong(spotId));
                    Map<String, Object> search = new HashMap<>();
                    DecimalFormat df = new DecimalFormat("0.00");//格式化小数
                    double utilizationRate = 0;
                    for (String date : dateList) {

                        Map map = new HashMap<>();
                        search = new HashMap<>();
                        search.put("date", date);
                        search.put("spotId", spotId);
                        int orderRobotCount = sysOrderMapper.getOrderRobotCount(search);
                        if (spotIdByRobotCount != 0) {

                            utilizationRate = (double) orderRobotCount / spotIdByRobotCount * 100;

                            String format = df.format(utilizationRate);
                            utilizationRate = Double.parseDouble(format);
                        }
                        map.put("date",date);
                        map.put("spotId",spotId);
                        map.put("spotName",sysScenicSpot.getScenicSpotName());
                        map.put("utilizationRate",utilizationRate);

                        //计算利用率同比
                        String lastYearCurrentDate = DateUtil.getLastYearCurrentDate(date);
                        search = new HashMap<>();
                        search.put("date", lastYearCurrentDate);
                        search.put("spotId", spotId);
                        int lastYearOrderRobotCount = sysOrderMapper.getOrderRobotCount(search);
                        double lastYearUtilizationRate =0d;
                        if (lastYearOrderRobotCount != 0){
                             lastYearUtilizationRate = (double) lastYearOrderRobotCount / spotIdByRobotCount * 100;
                        }
                        if (lastYearUtilizationRate !=0 ){
                            String format = df.format(lastYearUtilizationRate);
                            lastYearUtilizationRate = Double.parseDouble(format);
                        }

                        //计算同比
                        double yearOnYear = 0d;
                        if (utilizationRate != 0 && lastYearUtilizationRate != 0){
                             yearOnYear = (utilizationRate - lastYearUtilizationRate) / lastYearUtilizationRate * 100;
                            String format1 = df.format(yearOnYear);
                            yearOnYear = Double.parseDouble(format1);
                            map.put("yearOnYear",yearOnYear);
                        }

                        //计算利用率环比
                        String lastMonthDate = DateUtil.addMouth(date, -1);
                        search = new HashMap<>();
                        search.put("date", lastMonthDate);
                        search.put("spotId", spotId);
                        int lastMonthOrderRobotCount = sysOrderMapper.getOrderRobotCount(search);
                        double lastMonthUtilizationRate = (double) lastMonthOrderRobotCount / spotIdByRobotCount *100;
                        double monthOnMonth = 0d;
                        if (utilizationRate != 0 && lastMonthUtilizationRate != 0){
                            String format2 = df.format(lastMonthUtilizationRate);
                            lastMonthUtilizationRate = Double.parseDouble(format2);
                            monthOnMonth = (utilizationRate - lastMonthUtilizationRate) / lastMonthUtilizationRate * 100;
                            String format3 = df.format(monthOnMonth);
                            monthOnMonth = Double.parseDouble(format3);

                            map.put("monthOnMonth",monthOnMonth);
                        }
                        list.add(map);
                        dateL.add(date);
                        utilizationRateL.add(String.valueOf(utilizationRate));
                        yearOnYearL.add(String.valueOf(yearOnYear));
                        monthOnMonthL.add(String.valueOf(monthOnMonth));
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }




        if (list.size()>0){
//            List<Map<String,Object>> page = this.page(list, pageSize, pageNum);
            mapL.put("dateTime",dateL);
            mapL.put("realAmout",list);
            mapL.put("utilizationRate",utilizationRateL);
            mapL.put("yearOnYear",yearOnYearL);
            mapL.put("monthOnMonth",monthOnMonthL);

            returnModel.setData(mapL);
            returnModel.setTotal(list.size());
//            pageDataResult.setData(mapL);
//            pageDataResult.setTotals(list.size());

        }
        return returnModel;
//        return pageDataResult;
    }

    //导出
    @Override
    public List<SpotGrossProfitMargin> getSpotGrossProfitMarginExcel(Long type, String spotId, String startTime, String endTime, Integer pageNum, Integer pageSize, String sort) {
        List<SpotGrossProfitMargin> listE = new ArrayList<>();
        SpotGrossProfitMargin spotGrossProfitMargin = new SpotGrossProfitMargin();
        Map<String , Object> map = new HashMap<>();
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        try{
            //判断景区是否为空
            if (StringUtils.isEmpty(spotId)){
                //景区
                if (type == 1){

                    List<SysScenicSpot> scenicSpotList = sysScenicSpotMapper.getScenicSpotListNew(spotId);
                    for (SysScenicSpot sysScenicSpot : scenicSpotList) {

                        String  startDate = startTime + "-01";
                        String startDateN = DateUtil.addYear(endTime, 1);
                        String endDate = startDateN + "-01";
                        String endYear = DateUtil.addYear(endTime,1);

                        //流水
                        Double spotIdAndTimeByIncome = sysOrderMapper.getSpotIdAndTimeByIncome( sysScenicSpot.getScenicSpotId() , startTime, endYear);
                        if (StringUtils.isEmpty(spotIdAndTimeByIncome)){
                            spotIdAndTimeByIncome = 0d;
                        }else{
                            String format = decimalFormat.format(spotIdAndTimeByIncome);
                            spotIdAndTimeByIncome =Double.parseDouble(format) ;
                        }

                        //运营时长
                        Double spotIdAndTimeByOperationDuration = sysOrderMapper.getSpotIdAndTimeByOperationDuration(sysScenicSpot.getScenicSpotId() ,startTime,endYear);
                        if (StringUtils.isEmpty(spotIdAndTimeByOperationDuration)){

                            spotIdAndTimeByOperationDuration = 0d;
                        }else {
                            spotIdAndTimeByOperationDuration = Double.parseDouble(decimalFormat.format(spotIdAndTimeByOperationDuration / 60)) ;

                        }


                        //获取时间段内中有订单的机器人数量
                        Long orderCount = sysOrderMapper.getOrderRobotCountBySpotId(startDate,endDate,sysScenicSpot.getScenicSpotId().toString());
                        //获取该景区机器人总数量
                        Long spotIdByRobotCount = sysRobotMapper.getSpotIdByRobotCount(sysScenicSpot.getScenicSpotId());
                        //机器人利用率

                        Double robotUtilizationRate = 0d;
                        if (StringUtils.isEmpty(spotIdByRobotCount) || spotIdByRobotCount == 0){
                            robotUtilizationRate = 0d;
                        }else{
                            robotUtilizationRate = orderCount.doubleValue() / spotIdByRobotCount * 100;
                            String format = decimalFormat.format(robotUtilizationRate);
                            robotUtilizationRate = Double.parseDouble(format);
                        }

                        //利用率同比
                        Double yearUtilization =0d;
                        //同期利用率
                        String lastYearCurrentDate   = DateUtil.getLastYearCurrentDateYear(startTime);
                        String lastYearCurrentDateEnd = DateUtil.getLastYearCurrentDateYear(endTime);
                        String addLastYearCurrentDateEnd = DateUtil.addYear(lastYearCurrentDateEnd,1);
                        Long orderCountUp = sysOrderMapper.getOrderRobotCountBySpotId(lastYearCurrentDate,addLastYearCurrentDateEnd,sysScenicSpot.getScenicSpotId().toString());

                        Double utilizationRateUp = 0d;
                        if (StringUtils.isEmpty(spotIdByRobotCount) || spotIdByRobotCount ==0){
                            utilizationRateUp = 0d;
                        }else{
                            utilizationRateUp =  orderCountUp.doubleValue() / spotIdByRobotCount * 100;
                            String format = decimalFormat.format(utilizationRateUp);
                            utilizationRateUp = Double.parseDouble(format);
                        }
                        if (robotUtilizationRate != 0 && utilizationRateUp != 0){
                            yearUtilization = (robotUtilizationRate - utilizationRateUp)/utilizationRateUp * 100;
                            String format = decimalFormat.format(yearUtilization);
                            yearUtilization = Double.parseDouble(format);
                        }
                        //利用率环比
                        Double monthUtilization =0d;



                        Long orderCountMonthUp = sysOrderMapper.getOrderRobotCountBySpotId(lastYearCurrentDate,addLastYearCurrentDateEnd,sysScenicSpot.getScenicSpotId().toString());

                        //环比上期
                        Double utilizationRateUpMonth =0d;
                        if (StringUtils.isEmpty(spotIdByRobotCount) || spotIdByRobotCount ==0){
                            utilizationRateUpMonth =0d;
                        }else{
                            utilizationRateUpMonth =  orderCountMonthUp.doubleValue() / spotIdByRobotCount * 100;
                            String format = decimalFormat.format(utilizationRateUpMonth);
                            utilizationRateUpMonth = Double.parseDouble(format);
                        }
                        if (utilizationRateUpMonth != 0 && robotUtilizationRate != 0){
                            monthUtilization = (robotUtilizationRate - utilizationRateUpMonth) / utilizationRateUpMonth  * 100;
                            String format = decimalFormat.format(monthUtilization);
                            monthUtilization = Double.parseDouble(format);
                        }
                        map = new HashMap<>();
                        spotGrossProfitMargin = new SpotGrossProfitMargin();

//                        map.put("time",startTime + "至" + endTime);//时间段
//                        map.put("spotId",sysScenicSpot.getScenicSpotId());//景区id
//                        map.put("spotName",sysScenicSpot.getScenicSpotName());//景区名称
//                        map.put("spotIdAndTimeByIncome",spotIdAndTimeByIncome);//流水
//                        map.put("spotIdAndTimeByOperationDuration",spotIdAndTimeByOperationDuration);//运营时长
//                        map.put("utilizationRate",robotUtilizationRate);//利用率
//                        map.put("yearOnYear",yearUtilization);//利用率同比
//                        map.put("monthOnMonth",monthUtilization);//利用率环比
                        spotGrossProfitMargin.setTime(startTime + "至" + endTime);
                        spotGrossProfitMargin.setSpotId(sysScenicSpot.getScenicSpotId().toString());
                        spotGrossProfitMargin.setSpotName(sysScenicSpot.getScenicSpotName());
                        spotGrossProfitMargin.setSpotIdAndTimeByIncome(ToolUtil.isEmpty(spotIdAndTimeByIncome) ? 0 : spotIdAndTimeByIncome);
                        spotGrossProfitMargin.setSpotIdAndTimeByOperationDuration(ToolUtil.isEmpty(spotIdAndTimeByOperationDuration) ? 0 : spotIdAndTimeByOperationDuration );
                        spotGrossProfitMargin.setUtilizationRate(ToolUtil.isEmpty(robotUtilizationRate) ? 0 : robotUtilizationRate );
                        spotGrossProfitMargin.setYearOnYear(yearUtilization.toString());
                        spotGrossProfitMargin.setMonthOnMonth(monthUtilization.toString());
//


                        listE.add(spotGrossProfitMargin);
                    }
//                    Integer scenicSpotCount = sysScenicSpotMapper.getScenicSpotCount();
//                    map = new HashMap<>();
//                    map.put("total",scenicSpotCount);
//                    listE.add(map);

                }else if (type == 2 ){//时间
                    //景区
                    List<SysScenicSpot> scenicSpotList = sysScenicSpotMapper.getScenicSpotListNew(spotId);
                    //时间段
                    List<String> list = DateUtil.betweenMonths(startTime, endTime);
                    //景区列表
                    for (SysScenicSpot sysScenicSpot : scenicSpotList) {
                        for (String time : list) {
                            String startDate = time + "-01" ;
                            String s1 = DateUtil.addMouth(time, 1);
                            String endDate  = s1 + "-01";
                            String endDateT = DateUtil.addDay(endDate,-1);
                            //流水
                            Double spotIdAndTimeByIncome = sysOrderMapper.getSpotIdAndTimeByIncome( sysScenicSpot.getScenicSpotId() , startDate, endDate);
                            if (StringUtils.isEmpty(spotIdAndTimeByIncome)){

                                spotIdAndTimeByIncome = 0d;

                            }else{
                                String format = decimalFormat.format(spotIdAndTimeByIncome);
                                spotIdAndTimeByIncome =Double.parseDouble(format) ;
                            }
                            //运营时长
                            Double spotIdAndTimeByOperationDuration = sysOrderMapper.getSpotIdAndTimeByOperationDuration(sysScenicSpot.getScenicSpotId() ,startDate,endDateT);
                            if (StringUtils.isEmpty(spotIdAndTimeByOperationDuration)){

                                spotIdAndTimeByOperationDuration = 0d;
                            }else {
                                spotIdAndTimeByOperationDuration =Double.parseDouble(decimalFormat.format(spotIdAndTimeByOperationDuration / 60));

                            }
                            //获取时间段内中有订单的机器人数量
                            Long orderCount = sysOrderMapper.getOrderRobotCountBySpotId(startDate,endDate,sysScenicSpot.getScenicSpotId().toString());
                            if (StringUtils.isEmpty(orderCount)){
                                orderCount = 0l;
                            }

                            //获取该景区机器人总数量
                            Long spotIdByRobotCount = sysRobotMapper.getSpotIdByRobotCount(sysScenicSpot.getScenicSpotId());
                            if (StringUtils.isEmpty(spotIdByRobotCount)){
                                spotIdByRobotCount = 0l;
                            }

                            //机器人利用率
                            Double utilizationRate =0d;
                            if (StringUtils.isEmpty(spotIdByRobotCount) || spotIdByRobotCount == 0){
                                utilizationRate =0d;
                            }else {
                                utilizationRate = orderCount.doubleValue() / spotIdByRobotCount * 100;
                                String format = decimalFormat.format(utilizationRate);
                                utilizationRate = Double.parseDouble(format);
                            }
                            //利用率同比
                            Double yearUtilization =0d;
                            //同期利用率
                            String lastYearCurrentDate = DateUtil.getLastYearCurrentDate(time);
                            String startDateUp = lastYearCurrentDate + "-01" ;
                            String dateUp = DateUtil.addMouth(lastYearCurrentDate, 1);
                            String endDateUp  = dateUp + "-01";
                            Long orderCountUp = sysOrderMapper.getOrderRobotCountBySpotId(startDateUp,endDateUp,sysScenicSpot.getScenicSpotId().toString());
                            Double utilizationRateUp = 0d;
                            if (StringUtils.isEmpty(spotIdByRobotCount) || spotIdByRobotCount ==0){
                                utilizationRateUp = 0d;
                            }else{
                                utilizationRateUp =  orderCountUp.doubleValue() / spotIdByRobotCount * 100;
                                String format = decimalFormat.format(utilizationRateUp);
                                utilizationRateUp = Double.parseDouble(format);
                            }

                            if (utilizationRate != 0 && utilizationRateUp != 0){
                                yearUtilization = (utilizationRate - utilizationRateUp)/utilizationRateUp * 100;
                                String format = decimalFormat.format(yearUtilization);
                                yearUtilization = Double.parseDouble(format);
                            }
                            //利用率环比
                            Double monthUtilization =0d;

                            String monthUp = DateUtil.calcLastMonth(time);
                            String monthUps = monthUp + "-01" ;

                            //上期利用率
                            Long orderCountMonthUp = sysOrderMapper.getOrderRobotCountBySpotId(monthUps,startDate,sysScenicSpot.getScenicSpotId().toString());

                            Double utilizationRateUpMonth =0d;
                            if (StringUtils.isEmpty(spotIdByRobotCount) || spotIdByRobotCount ==0){
                                utilizationRateUpMonth = 0d;
                            }else{
                                utilizationRateUpMonth =  orderCountMonthUp.doubleValue() / spotIdByRobotCount * 100;
                                String format = decimalFormat.format(utilizationRateUpMonth);
                                utilizationRateUpMonth = Double.parseDouble(format);

                            }

                            if (utilizationRateUpMonth != 0 && utilizationRate != 0){
                                monthUtilization = (utilizationRate - utilizationRateUpMonth) / utilizationRateUpMonth  * 100;
                                String format = decimalFormat.format(monthUtilization);
                                monthUtilization = Double.parseDouble(format);
                            }

                            map = new HashMap<>();
                            spotGrossProfitMargin = new SpotGrossProfitMargin();
                            spotGrossProfitMargin.setTime(time);
                            spotGrossProfitMargin.setSpotId(sysScenicSpot.getScenicSpotId().toString());
                            spotGrossProfitMargin.setSpotName(sysScenicSpot.getScenicSpotName());
                            spotGrossProfitMargin.setSpotIdAndTimeByIncome(spotIdAndTimeByIncome);
                            spotGrossProfitMargin.setSpotIdAndTimeByOperationDuration(spotIdAndTimeByOperationDuration );
                            spotGrossProfitMargin.setUtilizationRate(utilizationRate);
                            spotGrossProfitMargin.setYearOnYear(yearUtilization.toString());
                            spotGrossProfitMargin.setMonthOnMonth(monthUtilization.toString());
//                            map.put("time",time);//时间段
//                            map.put("spotId",sysScenicSpot.getScenicSpotId());//景区id
//                            map.put("spotName",sysScenicSpot.getScenicSpotName());//景区名称
//                            map.put("spotIdAndTimeByIncome",spotIdAndTimeByIncome);//流水
//                            map.put("spotIdAndTimeByOperationDuration",spotIdAndTimeByOperationDuration);//运营时长
//                            map.put("utilizationRate",utilizationRate);//利用率
//                            map.put("yearOnYear",yearUtilization);//利用率同比
//                            map.put("monthOnMonth",monthUtilization);//利用率环比
                            listE.add(spotGrossProfitMargin);
                        }
                    }

//                    if (listE.size()>0){
//                        List<Map<String, Object>> maps = this.ListSplit(listE, pageNum, pageSize);
//
//                        int size = listE.size();
//                        HashMap<String, Object> lengthMap = new HashMap<>();
//                        lengthMap.put("total",size);
//                        maps.add(lengthMap);
//
//                        return maps;
//                    }else{
//
//                        return listE;
//                    }
                }
            }else{//景区不为空
                //景区查询
                if (type == 1){
//                    PageHelper.startPage(pageNum,pageSize);
                    //景区名称
                    SysScenicSpot sysScenicSpot = sysScenicSpotMapper.selectByPrimaryKey(Long.valueOf(spotId));

                    String  startDate = startTime + "-01";
                    String startDateN = DateUtil.addYear(endTime, 1);
                    String endDate = startDateN + "-01";
                    String endYear = DateUtil.addYear(endTime,1);
                    //流水
                    Double spotIdAndTimeByIncome = sysOrderMapper.getSpotIdAndTimeByIncome( Long.parseLong(spotId) , startTime, endYear);
                    if (StringUtils.isEmpty(spotIdAndTimeByIncome)){

                        spotIdAndTimeByIncome = 0d;

                    }else{
                        String format = decimalFormat.format(spotIdAndTimeByIncome);
                        spotIdAndTimeByIncome =Double.parseDouble(format) ;
                    }

                    //运营时长
                    Double spotIdAndTimeByOperationDuration = sysOrderMapper.getSpotIdAndTimeByOperationDuration(Long.parseLong(spotId) ,startTime,endYear);
                    if (StringUtils.isEmpty(spotIdAndTimeByOperationDuration)){

                        spotIdAndTimeByOperationDuration = 0d;
                    }else {
                        spotIdAndTimeByOperationDuration = spotIdAndTimeByOperationDuration ;
                        String format = decimalFormat.format(spotIdAndTimeByOperationDuration);
                        spotIdAndTimeByOperationDuration =Double.parseDouble(format);
                    }

                    //获取时间段内中有订单的机器人数量
                    Long orderCount = sysOrderMapper.getOrderRobotCountBySpotId(startTime,endYear,spotId);
                    //获取该景区机器人总数量
                    Long spotIdByRobotCount = sysRobotMapper.getSpotIdByRobotCount(Long.valueOf(spotId));
                    //机器人利用率

                    Double utilizationRate = 0d;
                    if (StringUtils.isEmpty(spotIdByRobotCount) || spotIdByRobotCount == 0){
                        utilizationRate = 0d;
                    }else {
                        utilizationRate = orderCount.doubleValue() / spotIdByRobotCount * 100;
                        String format = decimalFormat.format(utilizationRate);
                        utilizationRate = Double.parseDouble(format);
                    }


                    //利用率同比
                    Double yearUtilization =0d;
                    //同期利用率
                    String lastYearCurrentDate   = DateUtil.getLastYearCurrentDateYear(startTime);
                    String lastYearCurrentDateEnd = DateUtil.getLastYearCurrentDateYear(endTime);
                    String addLastYearCurrentDateEnd = DateUtil.addYear(lastYearCurrentDateEnd,1);
                    Long orderCountUp = sysOrderMapper.getOrderRobotCountBySpotId(lastYearCurrentDate,addLastYearCurrentDateEnd,spotId);

                    Double utilizationRateUp = 0d;
                    if (StringUtils.isEmpty(spotIdByRobotCount) || spotIdByRobotCount ==0){
                        utilizationRateUp = 0d;
                    }else{
                        utilizationRateUp =  orderCountUp.doubleValue() / spotIdByRobotCount * 100;
                        String format = decimalFormat.format(utilizationRateUp);
                        utilizationRateUp = Double.parseDouble(format);

                    }

                    if (utilizationRate != 0 && utilizationRateUp != 0){
                        yearUtilization = (utilizationRate - utilizationRateUp)/utilizationRateUp * 100;
                        String format = decimalFormat.format(yearUtilization);
                        yearUtilization = Double.parseDouble(format);
                    }
                    //利用率环比
                    Double monthUtilization =0d;



                    Long orderCountMonthUp = sysOrderMapper.getOrderRobotCountBySpotId(lastYearCurrentDate,addLastYearCurrentDateEnd,spotId);

                    Double utilizationRateUpMonth =0d;
                    if (StringUtils.isEmpty(spotIdByRobotCount) || spotIdByRobotCount ==0){
                        utilizationRateUpMonth =0d;
                    }else{
                        utilizationRateUpMonth =  orderCountMonthUp.doubleValue() / spotIdByRobotCount * 100 ;
                        String format = decimalFormat.format(utilizationRateUpMonth);
                        utilizationRateUpMonth = Double.parseDouble(format);
                    }


                    if (utilizationRateUpMonth != 0 && utilizationRate != 0){
                        monthUtilization = (utilizationRate - utilizationRateUpMonth) / utilizationRateUpMonth  * 100;
                        String format = decimalFormat.format(monthUtilization);
                        monthUtilization = Double.parseDouble(format);
                    }


                    map = new HashMap<>();
//                    map.put("time",startTime + "至" +endTime);//时间段
//                    map.put("spotId",spotId);//景区id
//                    map.put("spotName",sysScenicSpot.getScenicSpotName());//景区名称
//                    map.put("spotIdAndTimeByIncome",spotIdAndTimeByIncome);//流水
//                    map.put("spotIdAndTimeByOperationDuration",spotIdAndTimeByOperationDuration);//运营时长
//                    map.put("utilizationRate",utilizationRate);//利用率
//                    map.put("yearOnYear",yearUtilization);//利用率同比
//                    map.put("monthOnMonth",monthUtilization);//利用率环比
                    spotGrossProfitMargin = new SpotGrossProfitMargin();
                    spotGrossProfitMargin.setTime(startTime + "至" +endTime);
                    spotGrossProfitMargin.setSpotId(sysScenicSpot.getScenicSpotId().toString());
                    spotGrossProfitMargin.setSpotName(sysScenicSpot.getScenicSpotName());
                    spotGrossProfitMargin.setSpotIdAndTimeByIncome(spotIdAndTimeByIncome);
                    spotGrossProfitMargin.setSpotIdAndTimeByOperationDuration(spotIdAndTimeByOperationDuration);
                    spotGrossProfitMargin.setUtilizationRate(utilizationRate);
                    spotGrossProfitMargin.setYearOnYear(yearUtilization.toString());
                    spotGrossProfitMargin.setMonthOnMonth(monthUtilization.toString());
                    listE.add(spotGrossProfitMargin);
                    //获取总条数


//                    map = new HashMap<>();
//                    map.put("total",listE.size());
//                    listE.add(map);


                }else if (type == 2 ){//时间查询
                    //时间段
                    List<String> list = DateUtil.betweenMonths(startTime, endTime);
                    for (String s : list) {
                        //景区名称
                        SysScenicSpot sysScenicSpot = sysScenicSpotMapper.selectByPrimaryKey(Long.valueOf(spotId));
                        String startDate = s + "-01" ;
                        String s1 = DateUtil.addMouth(s, 1);
                        String endDate  = s1 + "-01";
                        String endDateT = DateUtil.addDay(endDate,-1);
                        //流水
                        Double spotIdAndTimeByIncome = sysOrderMapper.getSpotIdAndTimeByIncome( Long.parseLong(spotId) , startDate, endDate);
                        //运营时长
                        Double spotIdAndTimeByOperationDuration = sysOrderMapper.getSpotIdAndTimeByOperationDuration(Long.parseLong(spotId) ,startDate,endDateT);
                        if (StringUtils.isEmpty(spotIdAndTimeByOperationDuration)){
                            spotIdAndTimeByOperationDuration = 0d;
                        }else{
                            spotIdAndTimeByOperationDuration = Double.parseDouble(decimalFormat.format(spotIdAndTimeByOperationDuration));
                        }
                        //获取时间段内中有订单的机器人数量
                        Long orderCount = sysOrderMapper.getOrderRobotCountBySpotId(startDate,endDate,spotId);
                        //获取该景区机器人总数量
                        Long spotIdByRobotCount = sysRobotMapper.getSpotIdByRobotCount(Long.valueOf(spotId));
                        //机器人利用率
                        Double utilizationRate =0d;
                        if (StringUtils.isEmpty(spotIdByRobotCount) || spotIdByRobotCount == 0){

                        }else{
                            utilizationRate = orderCount.doubleValue() / spotIdByRobotCount * 100;
                            utilizationRate = Double.parseDouble(decimalFormat.format(utilizationRate));
                        }
                        //利用率同比
                        Double yearUtilization =0d;
                        //同期利用率
                        String lastYearCurrentDate = DateUtil.getLastYearCurrentDate(s);
                        String startDateUp = lastYearCurrentDate + "-01" ;
                        String dateUp = DateUtil.addMouth(lastYearCurrentDate, 1);
                        String endDateUp  = dateUp + "-01";
                        Long orderCountUp = sysOrderMapper.getOrderRobotCountBySpotId(startDateUp,endDateUp,spotId);

                        Double utilizationRateUp =0d;
                        if (StringUtils.isEmpty(spotIdByRobotCount) || spotIdByRobotCount == 0){

                        }else{
                            utilizationRateUp = Double.parseDouble(decimalFormat.format(orderCountUp.doubleValue() / spotIdByRobotCount * 100));
                        }

                        if (utilizationRate != 0 && utilizationRateUp != 0){
                            yearUtilization = Double.parseDouble(decimalFormat.format((utilizationRate - utilizationRateUp)/utilizationRateUp * 100))  ;
                        }
                        //利用率环比
                        Double monthUtilization =0d;

                        String monthUp = DateUtil.calcLastMonth(s);
                        String monthUps = monthUp + "-01" ;

                        Long orderCountMonthUp = sysOrderMapper.getOrderRobotCountBySpotId(monthUps,startDate,spotId);
                        Double utilizationRateUpMonth =0d;
                        if (StringUtils.isEmpty(spotIdByRobotCount) ||  spotIdByRobotCount == 0){
                            utilizationRateUpMonth = 0d;
                        }else{
                            utilizationRateUpMonth =Double.parseDouble(decimalFormat.format(orderCountMonthUp.doubleValue() / spotIdByRobotCount * 100)) ;
                        }

                        if (utilizationRateUpMonth != 0 && utilizationRate != 0){
                            monthUtilization =Double.parseDouble (decimalFormat.format((utilizationRate - utilizationRateUpMonth) / utilizationRateUpMonth  * 100));
                        }

                        map = new HashMap<>();
//                        map.put("time",s);//时间段
//                        map.put("spotId",spotId);//景区id
//                        map.put("spotName",sysScenicSpot.getScenicSpotName());//景区名称
//                        map.put("spotIdAndTimeByIncome",spotIdAndTimeByIncome);//流水
//                        map.put("spotIdAndTimeByOperationDuration",spotIdAndTimeByOperationDuration);//运营时长
//                        map.put("utilizationRate",utilizationRate);//利用率
//                        map.put("yearOnYear",yearUtilization);//利用率同比
//                        map.put("monthOnMonth",monthUtilization);//利用率环比

                        spotGrossProfitMargin = new SpotGrossProfitMargin();
                        spotGrossProfitMargin.setTime(startTime + "至" +endTime);
                        spotGrossProfitMargin.setSpotId(sysScenicSpot.getScenicSpotId().toString());
                        spotGrossProfitMargin.setSpotName(sysScenicSpot.getScenicSpotName());
                        spotGrossProfitMargin.setSpotIdAndTimeByIncome(spotIdAndTimeByIncome);
                        spotGrossProfitMargin.setSpotIdAndTimeByOperationDuration(spotIdAndTimeByOperationDuration);
                        spotGrossProfitMargin.setUtilizationRate(utilizationRate);
                        spotGrossProfitMargin.setYearOnYear(yearUtilization.toString());
                        spotGrossProfitMargin.setMonthOnMonth(monthUtilization.toString());
                        listE.add(spotGrossProfitMargin);
                    }

                }
            }



        }catch (Exception e){
            e.printStackTrace();
        }

        Collections.sort(listE, new Comparator<SpotGrossProfitMargin>() {
            @Override
            public int compare(SpotGrossProfitMargin o1, SpotGrossProfitMargin o2) {

                return (int) Math.round(o2.getUtilizationRate()-o1.getUtilizationRate());
            }
        });



        return listE;
    }









    private List<Map<String,Object>> ListSplit(List<Map<String,Object>> list,int page,int rows) {
        List<Map<String,Object>> newList=null;
        int  total=list.size();
        newList=list.subList(rows*(page-1), ((rows*page)>total?total:(rows*page))); return newList;
    }
    //list分页
    public List<Map<String,Object>> page(List<Map<String,Object>> dataList, int pageSize, int currentPage) {
        List<Map<String,Object>> currentPageList = new ArrayList<>();
        if (dataList != null && dataList.size() > 0) {
            int currIdx = (currentPage > 1 ? (currentPage - 1) * pageSize : 0);
            for (int i = 0; i < pageSize && i < dataList.size() - currIdx; i++) {
                Map data = dataList.get(currIdx + i);
                currentPageList.add(data);
            }
        }
        return currentPageList;
    }


}
