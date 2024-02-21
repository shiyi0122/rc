package com.hna.hka.archive.management.assetsSystem.service.impl;

import com.github.pagehelper.PageHelper;
import com.google.inject.internal.cglib.core.$ClassNameReader;
import com.google.inject.internal.cglib.core.$ObjectSwitchCallback;
import com.hna.hka.archive.management.assetsSystem.dao.*;
import com.hna.hka.archive.management.assetsSystem.model.*;
import com.hna.hka.archive.management.assetsSystem.service.RobotOperateGrossProfitService;
import com.hna.hka.archive.management.assetsSystem.service.SysScenicSpotOperationRulesService;
import com.hna.hka.archive.management.system.dao.SysOrderMapper;
import com.hna.hka.archive.management.system.dao.SysRobotMapper;
import com.hna.hka.archive.management.system.dao.SysScenicSpotMapper;
import com.hna.hka.archive.management.system.model.SysOrder;
import com.hna.hka.archive.management.system.model.SysRobot;
import com.hna.hka.archive.management.system.model.SysScenicSpot;
import com.hna.hka.archive.management.system.util.DateUtil;
import com.hna.hka.archive.management.system.util.ToolUtil;
import net.bytebuddy.asm.Advice;
import net.sf.jsqlparser.expression.DateTimeLiteralExpression;
import org.apache.shiro.crypto.hash.Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.tools.Tool;
import java.awt.*;
import java.nio.DoubleBuffer;
import java.sql.Struct;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author zhang
 * @Date 2022/3/17 18:01
 */
@Service
public class RobotOperateGrossProfitServiceImpl implements RobotOperateGrossProfitService {

    @Autowired
    RobotOperateGrossProfitMapper robotOperateGrossProfitMapper;

    @Autowired
    SysRobotMapper sysRobotMapper;

    @Autowired
    SysOrderMapper sysOrderMapper;
    @Autowired
    SysScenicSpotMapper sysScenicSpotMapper;

    @Autowired
    SysRobotSoftAssetInformationMapper sysRobotSoftAssetInformationMapper;

    @Autowired
    SysScenicSpotTargetAmountMapper sysScenicSpotTargetAmountMapper;
    @Autowired
    SubscriptionInformationMapper subscriptionInformationMapper;
    @Autowired
    SysScenicSpotOperationRulesMapper sysScenicSpotOperationRulesMapper;



    @Override
    public   Map<String, Object> getSpotGrossProfitMarginList(Long type, Long spotId, String startTime,String endTime,Integer pageNum, Integer pageSize,String sort) {
//        PageHelper.startPage(pageNum,pageSize);
        List<Map<String,Object>> list = new ArrayList<>();
        DecimalFormat df = new DecimalFormat("#.00");

        try {
            if (StringUtils.isEmpty(spotId)){
                List<SysScenicSpot> scenicSpotList = sysScenicSpotMapper.getScenicSpotListWords();
                for (SysScenicSpot sysScenicSpot : scenicSpotList) {

                    List<SysRobot> robotList = sysRobotMapper.getRobotListByScenicSpotId(sysScenicSpot.getScenicSpotId());
                    if (robotList.size()<=0){
                        //运营成本(运营人员成本、景区营销成本、租金、维养成本(均显示总值))
                        Double sumPrice = sysScenicSpotTargetAmountMapper.getTargetAmountGpmList(sysScenicSpot.getScenicSpotId(),startTime,endTime);

                            HashMap<String, Object> map = new HashMap<>();
                            map.put("spotId",sysScenicSpot.getScenicSpotId());
                            map.put("spotName",sysScenicSpot.getScenicSpotName());
                            map.put("cycleTime",startTime +"-"+endTime);
                            map.put("transactionprice","0");
                            map.put("operationDuration","0");
                            map.put("averagePrice","0");
                            map.put("averageTime","0");
                            map.put("cost","0");
                            map.put("sumPrice",StringUtils.isEmpty(sumPrice) ? 0:sumPrice);//综合成本
//
                            map.put("comprehensiveCost","0");
                            map.put("divide","0");
                            map.put("robotPeopleRatio","0");
                            map.put("grossProfit","0");
                            map.put("netProfit","0");
                            map.put("robotGrossProfit","0");
                            map.put("yearOnYear",null);
                            map.put("monthOnMonth",null);
                            map.put("grossProfitMargin","0");
                            map.put("netInterestRate","0");
                            list.add(map);
                            continue;
                    }
                    if (type ==1){//年
                        //交易金额
                        //需将结束时间向后加一年（按年查询的）
                        Long endTimeN = Long.parseLong(endTime)+1;
                        Double transactionprice =  sysOrderMapper.getSpotIdAndTimeByIncome(sysScenicSpot.getScenicSpotId(),startTime,endTimeN.toString());
                        if (StringUtils.isEmpty(transactionprice)){
                            transactionprice = 0d;
                        }else{
                            transactionprice = Double.parseDouble(df.format(transactionprice));
                        }
                        //运营时长
                        Double operationDuration =  sysOrderMapper.getSpotIdAndTimeByOperationDuration(sysScenicSpot.getScenicSpotId(),startTime,endTimeN.toString());
                        if (!StringUtils.isEmpty(operationDuration)){
                            operationDuration = Double.parseDouble(df.format(operationDuration));
                        }
                        //平均单台交易金额
                        Double  averagePrice =0d;
                        Long count = 0l;
                        if (!StringUtils.isEmpty(operationDuration) && operationDuration != 0){
                            count = sysRobotMapper.getSpotIdByRobotCount(sysScenicSpot.getScenicSpotId());
                            averagePrice = Double.parseDouble(df.format(transactionprice / count) );
                        }
                        //平均运营时长
                        Double averageTime = 0d;
                        if (!StringUtils.isEmpty(operationDuration) && operationDuration != 0){
                            averageTime = operationDuration / count;
                            averageTime = Double.parseDouble(df.format(averageTime));
                        }

                        //获取景区机器人，获取折旧金额
//                      List<SysRobot> robotList = sysRobotMapper.getRobotList(search);
                        Double sum = 0d;

                        for (SysRobot sysRobot : robotList) {
                            SysRobotSoftAssetInformation robotSoftAssetInformationByRobot = sysRobotSoftAssetInformationMapper.getRobotSoftAssetInformationByRobotId(sysRobot.getRobotId());
                            if (StringUtils.isEmpty(robotSoftAssetInformationByRobot)){
                                //暂时跳过
                                continue;
//                            throw new Exception(sysRobot.getRobotCode()+ "机器人无软资产信息");
                            }
                            if (StringUtils.isEmpty(robotSoftAssetInformationByRobot.getNetPrice()) || robotSoftAssetInformationByRobot.getNetPrice() <= 0){
                                continue;
                            }
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                            Calendar cal = Calendar.getInstance();
                            cal.setTime(sdf.parse(robotSoftAssetInformationByRobot.getDateProduction()));
                            cal.add(Calendar.YEAR,3);
                            String format = sdf.format(cal.getTime());
                            String year= format.substring(0,4);
                            System.out.println(format);
//
                            if (StringUtils.isEmpty(startTime) && StringUtils.isEmpty(endTime)){
                                sum += robotSoftAssetInformationByRobot.getAccumulate();
                            }else{
                                String dateProduction = robotSoftAssetInformationByRobot.getDateProduction();
                                //
                                if (0 < startTime.compareTo(year) || 0 > endTime.compareTo(dateProduction.substring(0,4))){
                                    //不在
                                    continue;
                                }else if(0 >= endTime.compareTo(year) && 0 >= dateProduction.compareTo(startTime) ){
                                    //在成本时间内
//                                    String months = DateUtil.findYears(startTime, endTime);
                                    Long  months = (Long.parseLong(endTime) - Long.parseLong(startTime))+1;
                                    Double price =  (Double.parseDouble(robotSoftAssetInformationByRobot.getFactoryCost()) / (Double.parseDouble(robotSoftAssetInformationByRobot.getServiceLife()) * 12)) * (months * 12);
                                    sum += price;
                                }else if ( 0 >= startTime.compareTo(dateProduction) && 0 >= endTime.compareTo(year)){
                                    //少于生产时间的时间段
//                                    String months =  DateUtil.findYears(dateProduction, endTime);
                                    String substring = dateProduction.substring(0, 7);

                                    String endTimeEnd = format.substring(0,7);
                                    String months = DateUtil.findMonths(substring, endTimeEnd);
                                    if ("1".equals(months)){
                                        months = "2";
                                    }
                                    Double price =  (Double.parseDouble(robotSoftAssetInformationByRobot.getFactoryCost()) / (Double.parseDouble(robotSoftAssetInformationByRobot.getServiceLife()) * 12)) * (Double.parseDouble(months)-1);
                                    sum += price;
                                }else if ( 0 <= startTime.compareTo(dateProduction) && 0 <= endTime.compareTo(year)){


                                    startTime = startTime + "-01";

                                    String substring = format.substring(0, 7);
                                    String months = DateUtil.findMonths(substring, endTime);
                                    if ("1".equals(months)){
                                        months = "2";
                                    }
                                    Double price =  (Double.parseDouble(robotSoftAssetInformationByRobot.getFactoryCost()) / (Double.parseDouble(robotSoftAssetInformationByRobot.getServiceLife()) * 12)) * (Double.parseDouble(months)-1);
                                    sum += price;
                                }

                            }
                        }

                        //运营成本(运营人员成本、景区营销成本、租金、维养成本(均显示总值))
                        Double sumPrice = sysScenicSpotTargetAmountMapper.getTargetAmountGpmList(sysScenicSpot.getScenicSpotId(),startTime,endTimeN.toString());
                        if (StringUtils.isEmpty(sumPrice)){
                            sumPrice = 0d;
                        }

                        //台均和成本
                        Double comprehensiveCost = (sum + sumPrice) / robotList.size();
                        if (StringUtils.isEmpty(comprehensiveCost)){
                            comprehensiveCost = 0d;
                        }
                        //公司所得分润金额
                        Double divide = 0d;
                        Map jxzyCompanyId =  subscriptionInformationMapper.selectJxzyCompanyId();
                        Object companyd = jxzyCompanyId.get("COMPANY_ID");
                        List<SubscriptionInformation> subscriptionInformations = subscriptionInformationMapper.selectSpotIdByContract(sysScenicSpot.getScenicSpotId());
                        if (subscriptionInformations.size()>1){
                            //分成
                            for (SubscriptionInformation subscriptionInformation : subscriptionInformations) {
                                if (1 == subscriptionInformation.getRevenueExpenditure()){
                                    divide = divide + transactionprice * subscriptionInformation.getProportion();
                                }else if (2 == subscriptionInformation.getRevenueExpenditure()){
                                    divide = divide + transactionprice -  transactionprice * subscriptionInformations.get(0).getProportion();
                                }
                            }
                        }else if (subscriptionInformations.size()==1){
                            //流水-分成
                            if (1 == subscriptionInformations.get(0).getRevenueExpenditure()){
                                divide = divide + transactionprice * subscriptionInformations.get(0).getProportion();
                            }else{
                                divide =divide + transactionprice -  transactionprice * subscriptionInformations.get(0).getProportion();
                            }
                        }else{
                            divide = transactionprice;
                        }
                        //机-人配比
                        Double robotPeopleRatio=0d;
                        List<SysScenicSpotOperationRules> sysScenicSpotOperationRules = sysScenicSpotOperationRulesMapper.getOperationRulesBySpotId(sysScenicSpot.getScenicSpotId());
                        if (robotList.size()!=0){
                            robotPeopleRatio = robotList.size() / Double.parseDouble(sysScenicSpotOperationRules.get(0).getOperatePeople()) ;
                        }

                        //毛利 同公司所得分润金额

                        //净利润
                        Double netProfit = divide - sum - sumPrice;

                        //台均毛利
                        Double robotGrossProfit = divide / robotList.size();
                        if (!StringUtils.isEmpty(robotGrossProfit) && robotGrossProfit != 0){
                            robotGrossProfit = Double.parseDouble(df.format(robotGrossProfit));
                        }
                        //毛利同比
                        //去年毛利
                        Double aDouble = this.grossProfitForTheSamePeriod(sysScenicSpot.getScenicSpotId(), startTime, endTime,type);
                        Double yearOnYear = 0d;
                        if (aDouble != 0){
                             yearOnYear = (divide-aDouble)/Math.abs(aDouble)*1.0;
                             yearOnYear = Double.parseDouble(df.format(yearOnYear));
                        }


                        //毛利环比
                        //上期收入
                        Double aDouble1 = this.currentGrossProfit(sysScenicSpot.getScenicSpotId(), startTime, endTime, type);
                        Double monthOnMonth = 0d;
                        if (aDouble1 != 0){
                            monthOnMonth = (divide-aDouble1)/Math.abs(aDouble1)*1.0;
                            monthOnMonth =Double.parseDouble(df.format(monthOnMonth));
                        }

                        //毛利率
                        Double grossProfitMargin = 0d;
                        if (divide == 0 || transactionprice == 0){
                            grossProfitMargin = 0d;
                        }else{
                            grossProfitMargin =  divide / transactionprice ;
                            grossProfitMargin = Double.parseDouble(df.format(grossProfitMargin));
                        }


                        //净利率
                        Double netInterestRate = 0d;
                        if (netProfit == 0 || transactionprice == 0){
                            netInterestRate = 0d;
                        }else{
                            netInterestRate =  netProfit / transactionprice ;
                            netInterestRate = Double.parseDouble(df.format(netInterestRate));
                        }

                        HashMap<String, Object> map = new HashMap<>();
                        map.put("spotId",sysScenicSpot.getScenicSpotId());
                        map.put("spotName",sysScenicSpot.getScenicSpotName());
                        map.put("cycleTime",startTime +"-"+endTime);
                        map.put("transactionprice",transactionprice);
                        map.put("operationDuration",operationDuration);
                        map.put("averagePrice",averagePrice);
                        map.put("averageTime",averageTime);
                        map.put("cost",sum);
                        map.put("sumPrice",sumPrice);

                        map.put("comprehensiveCost",comprehensiveCost);
                        map.put("divide",divide);
                        map.put("robotPeopleRatio",robotPeopleRatio);
                        map.put("grossProfit",divide);//毛利
                        map.put("netProfit",netProfit);
                        map.put("robotGrossProfit",robotGrossProfit);
                        map.put("yearOnYear",yearOnYear);
                        map.put("monthOnMonth",monthOnMonth);
                        map.put("grossProfitMargin",grossProfitMargin);
                        map.put("netInterestRate",netInterestRate);
                        list.add(map);


                    }else if (type == 2){//按照月
                        //交易金额
                        String endTimeN = DateUtil.addMouth(endTime, 1);
                        Double transactionprice =  sysOrderMapper.getSpotIdAndTimeByIncome(sysScenicSpot.getScenicSpotId(),startTime,endTimeN);
                        if (StringUtils.isEmpty(transactionprice)){
                            transactionprice = 0d;
                        }else{
                            transactionprice = Double.parseDouble(df.format(transactionprice));
                        }
                        //运营时长
                        Double operationDuration =  sysOrderMapper.getSpotIdAndTimeByOperationDuration(sysScenicSpot.getScenicSpotId(),startTime,endTimeN);
                        if (!StringUtils.isEmpty(operationDuration) && operationDuration != 0){
                            operationDuration = Double.parseDouble(df.format(operationDuration));
                        }
                        //平均单台交易金额
                        Double  averagePrice =0d;
                        Long count = 0l;
                        if (!StringUtils.isEmpty(operationDuration) && operationDuration != 0){
                            count = sysRobotMapper.getSpotIdByRobotCount(sysScenicSpot.getScenicSpotId());
                            averagePrice = transactionprice / count;
                            averagePrice = Double.parseDouble(df.format(averagePrice));
                        }
                        //平均运营时长
                        Double averageTime = 0d;
                        if (!StringUtils.isEmpty(operationDuration) && operationDuration != 0){
                            averageTime = operationDuration / count;
                            averageTime = Double.parseDouble(df.format(averageTime));
                        }

                        //获取景区机器人，获取折旧金额
//                      List<SysRobot> robotList = sysRobotMapper.getRobotList(search);
                        Double sum = 0d;


                        for (SysRobot sysRobot : robotList) {
                            SysRobotSoftAssetInformation robotSoftAssetInformationByRobot = sysRobotSoftAssetInformationMapper.getRobotSoftAssetInformationByRobotId(sysRobot.getRobotId());
                            if (StringUtils.isEmpty(robotSoftAssetInformationByRobot)){
                                //暂时跳过
                                continue;
//                            throw new Exception(sysRobot.getRobotCode()+ "机器人无软资产信息");
                            }
                            if (StringUtils.isEmpty(robotSoftAssetInformationByRobot.getNetPrice()) || robotSoftAssetInformationByRobot.getNetPrice() <= 0){
                                continue;
                            }
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                            Calendar cal = Calendar.getInstance();
                            cal.setTime(sdf.parse(robotSoftAssetInformationByRobot.getDateProduction()));
                            cal.add(Calendar.YEAR,3);
                            String format = sdf.format(cal.getTime());
                            System.out.println(format);
//
                            if (StringUtils.isEmpty(startTime) && StringUtils.isEmpty(endTime)){
                                sum += robotSoftAssetInformationByRobot.getAccumulate();
                            }else{
                                String dateProduction = robotSoftAssetInformationByRobot.getDateProduction();

                                if (0 >= endTime.compareTo(format) && 0 >= dateProduction.compareTo(startTime)){
                                    //在成本时间内
                                    String months = DateUtil.findMonths(startTime, endTime);
                                    if ("1".equals(months)){
                                        months = "2";
                                    }
                                    Double price =  (Double.parseDouble(robotSoftAssetInformationByRobot.getFactoryCost()) / (Double.parseDouble(robotSoftAssetInformationByRobot.getServiceLife()) * 12)) * (Double.parseDouble(months)-1);
                                    sum += price;

                                }else if( 0 < startTime.compareTo(format) || 0 > endTime.compareTo(dateProduction)){
                                    //不在
                                    continue;
                                }else if ( 0 <= endTime.compareTo(dateProduction) && 0 >= endTime.compareTo(format)){
                                    //
                                    String months =  DateUtil.findMonths(dateProduction, endTime);
                                    if ("1".equals(months)){
                                        months = "2";
                                    }
                                    Double price =  (Double.parseDouble(robotSoftAssetInformationByRobot.getFactoryCost()) / (Double.parseDouble(robotSoftAssetInformationByRobot.getServiceLife()) * 12)) * (Double.parseDouble(months)-1);
                                    sum += price;
                                }else if ( 0 <= startTime.compareTo(dateProduction) && 0 >= startTime.compareTo(format)){

                                    String months =  DateUtil.findMonths(startTime, format);
                                    if ("1".equals(months)){
                                        months = "2";
                                    }
                                    Double price =  (Double.parseDouble(robotSoftAssetInformationByRobot.getFactoryCost()) / (Double.parseDouble(robotSoftAssetInformationByRobot.getServiceLife()) * 12)) * (Double.parseDouble(months)-1);
                                    sum += price;
                                }else  {
                                    String months =  DateUtil.findMonths(dateProduction, format);
                                    if ("1".equals(months)){
                                        months = "2";
                                    }
                                    Double price =  (Double.parseDouble(robotSoftAssetInformationByRobot.getFactoryCost()) / (Double.parseDouble(robotSoftAssetInformationByRobot.getServiceLife()) * 12)) * (Double.parseDouble(months)-1);
                                    sum += price;
                                }
                            }
                        }
                        //运营成本(运营人员成本、景区营销成本、租金、维养成本(均显示总值))
                        Double sumPrice = sysScenicSpotTargetAmountMapper.getTargetAmountGpmList(sysScenicSpot.getScenicSpotId(),startTime,endTimeN);
                        if (StringUtils.isEmpty(sumPrice)){
                            sumPrice = 0d;
                        }


                        //台均和成本
                        Double comprehensiveCost = (sum + sumPrice) / robotList.size();
                        if (StringUtils.isEmpty(comprehensiveCost)){
                            comprehensiveCost = 0d;
                        }
                        //公司所得分润金额
                        Double divide = 0d;
                        Map jxzyCompanyId =  subscriptionInformationMapper.selectJxzyCompanyId();
                        Object companyd = jxzyCompanyId.get("COMPANY_ID");
                        List<SubscriptionInformation> subscriptionInformations = subscriptionInformationMapper.selectSpotIdByContract(sysScenicSpot.getScenicSpotId());
                        if (subscriptionInformations.size()>1){
                            //分成
//                        for (SubscriptionInformation subscriptionInformation : subscriptionInformations) {
//                            if (subscriptionInformation.getCompanyId().equals(companyd)){
//                                divide =  transactionprice * subscriptionInformation.getProportion();
//                            }
//                        }
                            for (SubscriptionInformation subscriptionInformation : subscriptionInformations) {
                                if (subscriptionInformation.getRevenueExpenditure() == 1){
                                    divide = divide +  transactionprice * subscriptionInformation.getProportion();
                                }else{
                                    divide = divide + (transactionprice - (transactionprice * subscriptionInformation.getProportion()));
                                }

                            }

                        }else if (subscriptionInformations.size()==1){
                            //流水-分成
//                        divide = transactionprice -  transactionprice * subscriptionInformations.get(0).getProportion();
                            if(subscriptionInformations.get(0).getRevenueExpenditure() == 1){
                                divide = transactionprice * subscriptionInformations.get(0).getProportion();
                            }else{
                                divide =  transactionprice - (transactionprice * subscriptionInformations.get(0).getProportion());
                            }

                        }else{
                            divide = transactionprice;
                        }
                        //机-人配比
                        Double robotPeopleRatio=0d;
                        List<SysScenicSpotOperationRules> sysScenicSpotOperationRules = sysScenicSpotOperationRulesMapper.getOperationRulesBySpotId(sysScenicSpot.getScenicSpotId());
                        if (robotList.size()!=0){
                            robotPeopleRatio = robotList.size() / Double.parseDouble(sysScenicSpotOperationRules.get(0).getOperatePeople()) ;
                        }

                        //毛利 同公司所得分润金额

                        //净利润
                        Double netProfit = divide - sum - sumPrice;

                        //台均毛利
                        Double robotGrossProfit = divide / robotList.size();
                        if (!StringUtils.isEmpty(robotGrossProfit) && robotGrossProfit !=0){
                            robotGrossProfit = Double.parseDouble(df.format(robotGrossProfit));
                        }
                        //毛利同比
                        //去年毛利

                        Double aDouble = this.grossProfitForTheSamePeriod(sysScenicSpot.getScenicSpotId(), startTime, endTime,type);
                        Double yearOnYear = 0d;
                        if (aDouble != 0){
                             yearOnYear = (divide-aDouble)/Math.abs(aDouble)*1.0;
                             yearOnYear = Double.parseDouble(df.format(yearOnYear));
                        }


                        //毛利环比
                        //上期收入
                        Double aDouble1 = this.currentGrossProfit(sysScenicSpot.getScenicSpotId(), startTime, endTime, type);
                        Double monthOnMonth =0d;
                        if (aDouble1 != 0){
                             monthOnMonth = (divide-aDouble1)/Math.abs(aDouble1)*1.0;
                             monthOnMonth = Double.parseDouble(df.format(monthOnMonth));
                        }

                        //毛利率
                        Double grossProfitMargin = 0d;
                        if (divide == 0 || transactionprice == 0){
                            grossProfitMargin = 0d;
                        }else{
                            grossProfitMargin =  divide / transactionprice ;
                            grossProfitMargin = Double.parseDouble(df.format(grossProfitMargin));
                        }
                        //净利率
                        Double netInterestRate = 0d;

                        if (netProfit == 0 || transactionprice == 0){
                            netInterestRate = 0d;
                        }else{
                            netInterestRate =  netProfit / transactionprice ;
                            netInterestRate = Double.parseDouble(df.format(netInterestRate));
                        }


                        HashMap<String, Object> map = new HashMap<>();
                        map.put("spotId",sysScenicSpot.getScenicSpotId());
                        map.put("spotName",sysScenicSpot.getScenicSpotName());
                        map.put("cycleTime",startTime +"-"+endTime);
                        map.put("transactionprice",transactionprice); //交易金额
                        map.put("operationDuration",operationDuration); //运营时长
                        map.put("averagePrice",averagePrice);//平均单台交易金额
                        map.put("averageTime",averageTime);//平均运营时长
                        map.put("cost",sum);//机器人折旧金额
                        map.put("sumPrice",sumPrice);//综合成本
                        map.put("comprehensiveCost",comprehensiveCost);//台均和成本
                        map.put("divide",divide);//公司所得分润金额
                        map.put("robotPeopleRatio",robotPeopleRatio);//机-人配比
                        map.put("grossProfit",divide);  //公司所得分润金额
                        map.put("netProfit",netProfit); //净利润
                        map.put("robotGrossProfit",robotGrossProfit);//台均毛利
                        map.put("yearOnYear",yearOnYear);//同比
                        map.put("monthOnMonth",monthOnMonth);//环比
                        map.put("grossProfitMargin",grossProfitMargin);//毛利率
                        map.put("netInterestRate",netInterestRate);//净利率
                        list.add(map);

                    }
                }
            }else{//根据景区查询
                SysScenicSpot sysScenicSpot = sysScenicSpotMapper.selectByScenicSpotId(spotId);
                List<SysRobot> robotList = sysRobotMapper.getRobotListByScenicSpotId(sysScenicSpot.getScenicSpotId());
                if (robotList.size()<=0){
                    //运营成本(运营人员成本、景区营销成本、租金、维养成本(均显示总值))
                    Double sumPrice = sysScenicSpotTargetAmountMapper.getTargetAmountGpmList(sysScenicSpot.getScenicSpotId(),startTime,endTime);

                        HashMap<String, Object> map = new HashMap<>();
                        map.put("spotId",sysScenicSpot.getScenicSpotId());
                        map.put("spotName",sysScenicSpot.getScenicSpotName());
                        map.put("cycleTime",startTime +"-"+endTime);
                        map.put("transactionprice","0");
                        map.put("operationDuration","0");
                        map.put("averagePrice","0");
                        map.put("averageTime","0");
                        map.put("cost","0");
                        map.put("sumPrice",StringUtils.isEmpty(sumPrice) ? 0:sumPrice);//综合成本

                        map.put("comprehensiveCost","0");
                        map.put("divide","0");
                        map.put("robotPeopleRatio","0");
                        map.put("grossProfit","0");
                        map.put("netProfit","0");
                        map.put("robotGrossProfit","0");
                        map.put("yearOnYear",null);
                        map.put("monthOnMonth",null);
                        map.put("grossProfitMargin","0");
                        map.put("netInterestRate","0");
                        list.add(map);

                    HashMap<String, Object> map1 = new HashMap<>();
                    map.put("list",map1);
                    map.put("total",list.size());
                    return map1;
                }

                if (type ==1 ){ //年查询
                    //交易金额
                    //需将结束时间向后加一年（按年查询的）
                    Long endTimeN = Long.parseLong(endTime)+1;
                    Double transactionprice =  sysOrderMapper.getSpotIdAndTimeByIncome(sysScenicSpot.getScenicSpotId(),startTime,endTimeN.toString());
                    if (StringUtils.isEmpty(transactionprice)){
                        transactionprice = 0d;
                    }else{
                        transactionprice = Double.parseDouble(df.format(transactionprice));
                    }
                    //运营时长
                    Double operationDuration =  sysOrderMapper.getSpotIdAndTimeByOperationDuration(sysScenicSpot.getScenicSpotId(),startTime,endTimeN.toString());
                    if (!StringUtils.isEmpty(operationDuration) && operationDuration != 0){
                        operationDuration = Double.parseDouble(df.format(operationDuration)) ;
                    }
                    //平均单台交易金额
                    Double  averagePrice =0d;
                    Long count = 0l;
                    if (!StringUtils.isEmpty(operationDuration) && operationDuration != 0){
                        count = sysRobotMapper.getSpotIdByRobotCount(sysScenicSpot.getScenicSpotId());
                        averagePrice = transactionprice / count;
                        averagePrice = Double.parseDouble(df.format(averagePrice));
                    }
                    //平均运营时长
                    Double averageTime = 0d;
                    if (!StringUtils.isEmpty(operationDuration) && operationDuration != 0){
                        averageTime = operationDuration / count;
                        averageTime = Double.parseDouble(df.format(averageTime));
                    }



                    //获取景区机器人，获取折旧金额
//                    List<SysRobot> robotList = sysRobotMapper.getRobotList(search);
                    Double sum = 0d;


                    for (SysRobot sysRobot : robotList) {
                        SysRobotSoftAssetInformation robotSoftAssetInformationByRobot = sysRobotSoftAssetInformationMapper.getRobotSoftAssetInformationByRobotId(sysRobot.getRobotId());
                        if (StringUtils.isEmpty(robotSoftAssetInformationByRobot)){
                            //暂时跳过
                            continue;
//                            throw new Exception(sysRobot.getRobotCode()+ "机器人无软资产信息");
                        }
                        if (StringUtils.isEmpty(robotSoftAssetInformationByRobot.getNetPrice()) || robotSoftAssetInformationByRobot.getNetPrice() <= 0){
                            continue;
                        }
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        Calendar cal = Calendar.getInstance();
                        cal.setTime(sdf.parse(robotSoftAssetInformationByRobot.getDateProduction()));
                        cal.add(Calendar.YEAR,3);
                        String format = sdf.format(cal.getTime());
                        String year = format.substring(0, 4);
                        System.out.println(format);
//
                        if (StringUtils.isEmpty(startTime) && StringUtils.isEmpty(endTime)){
                            sum += robotSoftAssetInformationByRobot.getAccumulate();
                        }else{
                            String dateProduction = robotSoftAssetInformationByRobot.getDateProduction();

                            //
                            if (0 < startTime.compareTo(year) || 0 > endTime.compareTo(dateProduction.substring(0,4))){
                                //不在
                                continue;
                            }else if(0 >= endTime.compareTo(year) && 0 >= dateProduction.compareTo(startTime) ){
                                //在成本时间内
//                                    String months = DateUtil.findYears(startTime, endTime);
                                Long  months = (Long.parseLong(endTime) - Long.parseLong(startTime))+1;

                                Double price =  (Double.parseDouble(robotSoftAssetInformationByRobot.getFactoryCost()) / (Double.parseDouble(robotSoftAssetInformationByRobot.getServiceLife()) * 12)) * (months * 12);
                                sum += price;
                            }else if ( 0 >= startTime.compareTo(dateProduction) && 0 >= endTime.compareTo(year)){
                                //少于生产时间的时间段
//                                    String months =  DateUtil.findYears(dateProduction, endTime);
                                String substring = dateProduction.substring(0, 7);

                                String endTimeEnd = format.substring(0,7);
                                String months = DateUtil.findMonths(substring, endTimeEnd);
                                if ("1".equals(months)){
                                    months = "2";
                                }
                                Double price =  (Double.parseDouble(robotSoftAssetInformationByRobot.getFactoryCost()) / (Double.parseDouble(robotSoftAssetInformationByRobot.getServiceLife()) * 12)) * (Double.parseDouble(months)-1);
                                sum += price;
                            }else if ( 0 <= startTime.compareTo(dateProduction) && 0 <= endTime.compareTo(year)){


                                startTime = startTime + "-01";

                                String substring = format.substring(0, 7);
                                String months = DateUtil.findMonths(substring, endTime);
                                if ("1".equals(months)){
                                    months = "2";
                                }
                                Double price =  (Double.parseDouble(robotSoftAssetInformationByRobot.getFactoryCost()) / (Double.parseDouble(robotSoftAssetInformationByRobot.getServiceLife()) * 12)) * (Double.parseDouble(months)-1);
                                sum += price;
                            }

                        }
                    }

                    //运营成本(运营人员成本、景区营销成本、租金、维养成本(均显示总值))
                    Double sumPrice = sysScenicSpotTargetAmountMapper.getTargetAmountGpmList(sysScenicSpot.getScenicSpotId(),startTime,endTimeN.toString());
                    if (StringUtils.isEmpty(sumPrice)){
                        sumPrice = 0d;
                    }

                    //台均和成本
                    Double comprehensiveCost = (sum + sumPrice) / robotList.size();
                    if (StringUtils.isEmpty(comprehensiveCost)){
                        comprehensiveCost = 0d;
                    }
                    //公司所得分润金额
                    Double divide = 0d;
                    Map jxzyCompanyId =  subscriptionInformationMapper.selectJxzyCompanyId();
                    Object companyd = jxzyCompanyId.get("COMPANY_ID");
                    List<SubscriptionInformation> subscriptionInformations = subscriptionInformationMapper.selectSpotIdByContract(sysScenicSpot.getScenicSpotId());
                    if (subscriptionInformations.size()>1){
                        //分成
//                        for (SubscriptionInformation subscriptionInformation : subscriptionInformations) {
//                            if (subscriptionInformation.getCompanyId().equals(companyd)){
//                                divide =  transactionprice * subscriptionInformation.getProportion();
//                            }
//                        }
                        for (SubscriptionInformation subscriptionInformation : subscriptionInformations) {
                            if (subscriptionInformation.getRevenueExpenditure() == 1){
                                divide = divide +  transactionprice * subscriptionInformation.getProportion();
                            }else{
                                divide = divide + (transactionprice - (transactionprice * subscriptionInformation.getProportion()));
                            }

                        }

                    }else if (subscriptionInformations.size()==1){
                        //流水-分成
//                        divide = transactionprice -  transactionprice * subscriptionInformations.get(0).getProportion();
                        if(subscriptionInformations.get(0).getRevenueExpenditure() == 1){
                            divide = transactionprice * subscriptionInformations.get(0).getProportion();
                        }else{
                            divide =  transactionprice - (transactionprice * subscriptionInformations.get(0).getProportion());
                        }

                    }else{
                        divide = transactionprice;
                    }
                    //机-人配比
                    Double robotPeopleRatio=0d;
                    List<SysScenicSpotOperationRules> sysScenicSpotOperationRules = sysScenicSpotOperationRulesMapper.getOperationRulesBySpotId(sysScenicSpot.getScenicSpotId());
                    if (robotList.size()!=0){
                        robotPeopleRatio = robotList.size() / Double.parseDouble(sysScenicSpotOperationRules.get(0).getOperatePeople()) ;
                    }

                    //毛利 同公司所得分润金额

                    //净利润
                    Double netProfit = divide - sum - sumPrice;

                    //台均毛利
                    Double robotGrossProfit = divide / robotList.size();
                    if (StringUtils.isEmpty(robotGrossProfit) && robotGrossProfit != 0){
                        robotGrossProfit = Double.parseDouble(df.format(robotGrossProfit));
                    }
                    //毛利同比
                    Double aDouble = this.grossProfitForTheSamePeriod(sysScenicSpot.getScenicSpotId(), startTime, endTime,type);
                    Double yearOnYear = 0d;
                    if (aDouble != 0){
                        yearOnYear  = Double.parseDouble(df.format ((divide-aDouble) / Math.abs(aDouble)*1.0));

                    }


                    //毛利环比
                    Double aDouble1 = this.currentGrossProfit(sysScenicSpot.getScenicSpotId(), startTime, endTime, type);
                    Double monthOnMonth = 0d;
                    if (aDouble1 != 0){
                         monthOnMonth =Double.parseDouble(df.format((divide-aDouble1)/Math.abs(aDouble1)*1.0));
                    }


                    //毛利率
                    Double grossProfitMargin = 0d ;
                    if (divide == 0 || transactionprice == 0){
                        grossProfitMargin = 0d;
                    }else{
                        grossProfitMargin = Double.parseDouble(df.format( divide / transactionprice )) ;
                    }


                    //净利率
                    Double netInterestRate = 0d;
                    if (netProfit == 0 || transactionprice == 0){
                        netInterestRate = 0d;
                    }else{
                        netInterestRate = Double.parseDouble(df.format(netProfit / transactionprice))  ;

                    }


                    HashMap<String, Object> map = new HashMap<>();
                    map.put("spotId",sysScenicSpot.getScenicSpotId());
                    map.put("spotName",sysScenicSpot.getScenicSpotName());
                    map.put("cycleTime",startTime +"-"+endTime);
                    map.put("transactionprice",transactionprice);
                    map.put("operationDuration",operationDuration);
                    map.put("averagePrice",averagePrice);
                    map.put("averageTime",averageTime);
                    map.put("cost",sum);
                    map.put("sumPrice",sumPrice);//综合成本

                    map.put("comprehensiveCost",comprehensiveCost);
                    map.put("divide",divide);
                    map.put("robotPeopleRatio",robotPeopleRatio);
                    map.put("grossProfit",divide);
                    map.put("netProfit",netProfit);
                    map.put("robotGrossProfit",robotGrossProfit);
                    map.put("yearOnYear",yearOnYear);
                    map.put("monthOnMonth",monthOnMonth);
                    map.put("grossProfitMargin",grossProfitMargin);
                    map.put("netInterestRate",netInterestRate);
                    list.add(map);


                }else if (type == 2){//按月查询
                    //交易金额
                    String endTimeN = DateUtil.addMouth(endTime, 1);
                    Double transactionprice =  sysOrderMapper.getSpotIdAndTimeByIncome(sysScenicSpot.getScenicSpotId(),startTime,endTimeN);
                    if (StringUtils.isEmpty(transactionprice)){
                        transactionprice = 0d;
                    }else{
                        transactionprice = Double.parseDouble(df.format(transactionprice));
                    }
                    //运营时长
                    Double operationDuration =  sysOrderMapper.getSpotIdAndTimeByOperationDuration(sysScenicSpot.getScenicSpotId(),startTime,endTimeN);
                    if (!StringUtils.isEmpty(operationDuration) && operationDuration != 0){
                        operationDuration = Double.parseDouble(df.format(operationDuration)) ;
                    }
                    //平均单台交易金额
                    Double  averagePrice =0d;
                    Long count = 0l;
                    if (!StringUtils.isEmpty(operationDuration) && operationDuration != 0){
                        count = sysRobotMapper.getSpotIdByRobotCount(sysScenicSpot.getScenicSpotId());
                        averagePrice =Double.parseDouble( df.format(transactionprice / count)) ;
                    }
                    //平均运营时长
                    Double averageTime = 0d;
                    if (!StringUtils.isEmpty(operationDuration) && operationDuration != 0){
                        averageTime = Double.parseDouble(df.format(operationDuration / count)) ;

                    }


                    //获取景区机器人，获取折旧金额
//                    List<SysRobot> robotList = sysRobotMapper.getRobotList(search);
                    Double sum = 0d;


                    for (SysRobot sysRobot : robotList) {
                        SysRobotSoftAssetInformation robotSoftAssetInformationByRobot = sysRobotSoftAssetInformationMapper.getRobotSoftAssetInformationByRobotId(sysRobot.getRobotId());
                        if (StringUtils.isEmpty(robotSoftAssetInformationByRobot)){
                            //暂时跳过
                            continue;
//                            throw new Exception(sysRobot.getRobotCode()+ "机器人无软资产信息");
                        }
                        if (StringUtils.isEmpty(robotSoftAssetInformationByRobot.getNetPrice()) || robotSoftAssetInformationByRobot.getNetPrice() <= 0){
                            continue;
                        }
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        Calendar cal = Calendar.getInstance();
                        cal.setTime(sdf.parse(robotSoftAssetInformationByRobot.getDateProduction()));
                        cal.add(Calendar.YEAR,3);
                        String format = sdf.format(cal.getTime());
                        System.out.println(format);
//
                        if (StringUtils.isEmpty(startTime) && StringUtils.isEmpty(endTime)){
                            sum += robotSoftAssetInformationByRobot.getAccumulate();
                        }else{
                            String dateProduction = robotSoftAssetInformationByRobot.getDateProduction();

                            if (0 >= endTime.compareTo(format) && 0 >= dateProduction.compareTo(startTime)){
                                //在成本时间内
                                String months = DateUtil.findMonths(startTime, endTime);
                                if ("1".equals(months)){
                                    months = "2";
                                }
                                Double price =  (Double.parseDouble(robotSoftAssetInformationByRobot.getFactoryCost()) / (Double.parseDouble(robotSoftAssetInformationByRobot.getServiceLife()) * 12)) * (Double.parseDouble(months)-1);
                                sum += price;

                            }else if( 0 < startTime.compareTo(format) || 0 > endTime.compareTo(dateProduction)){
                                //不在
                                continue;
                            }else if ( 0 <= endTime.compareTo(dateProduction) && 0 >= endTime.compareTo(format)){
                                //
                                String months =  DateUtil.findMonths(dateProduction, endTime);
                                if ("1".equals(months)){
                                    months = "2";
                                }
                                Double price =  (Double.parseDouble(robotSoftAssetInformationByRobot.getFactoryCost()) / (Double.parseDouble(robotSoftAssetInformationByRobot.getServiceLife()) * 12)) * (Double.parseDouble(months)-1);
                                sum += price;
                            }else if ( 0 <= startTime.compareTo(dateProduction) && 0 >= startTime.compareTo(format)){

                                String months =  DateUtil.findMonths(startTime, format);
                                if ("1".equals(months)){
                                    months = "2";
                                }
                                Double price =  (Double.parseDouble(robotSoftAssetInformationByRobot.getFactoryCost()) / (Double.parseDouble(robotSoftAssetInformationByRobot.getServiceLife()) * 12)) * (Double.parseDouble(months)-1);
                                sum += price;
                            }else  {

                                String months =  DateUtil.findMonths(dateProduction, format);
                                if ("1".equals(months)){
                                    months = "2";
                                }
                                Double price =  (Double.parseDouble(robotSoftAssetInformationByRobot.getFactoryCost()) / (Double.parseDouble(robotSoftAssetInformationByRobot.getServiceLife()) * 12)) * (Double.parseDouble(months)-1);
                                sum += price;
                            }
                        }
                    }
                    //运营成本(运营人员成本、景区营销成本、租金、维养成本(均显示总值))
                    Double sumPrice = sysScenicSpotTargetAmountMapper.getTargetAmountGpmList(sysScenicSpot.getScenicSpotId(),startTime,endTimeN);
                    if (StringUtils.isEmpty(sumPrice)){
                        sumPrice = 0d;
                    }

                    //台均和成本
                    Double comprehensiveCost = (sum + sumPrice) / robotList.size();
                    if (StringUtils.isEmpty(comprehensiveCost)){
                        comprehensiveCost = 0d;
                    }
                    //公司所得分润金额
                    Double divide = 0d;
                    Map jxzyCompanyId =  subscriptionInformationMapper.selectJxzyCompanyId();
                    Object companyd = jxzyCompanyId.get("COMPANY_ID");
                    List<SubscriptionInformation> subscriptionInformations = subscriptionInformationMapper.selectSpotIdByContract(sysScenicSpot.getScenicSpotId());
                    if (subscriptionInformations.size()>1){
                        //分成
//                        for (SubscriptionInformation subscriptionInformation : subscriptionInformations) {
//                            if (subscriptionInformation.getCompanyId().equals(companyd)){
//                                divide =  transactionprice * subscriptionInformation.getProportion();
//                            }
//                        }
                        for (SubscriptionInformation subscriptionInformation : subscriptionInformations) {
                            if (subscriptionInformation.getRevenueExpenditure() == 1){
                                divide = divide +  transactionprice * subscriptionInformation.getProportion();
                            }else{
                                divide = divide + (transactionprice - (transactionprice * subscriptionInformation.getProportion()));
                            }

                        }

                    }else if (subscriptionInformations.size()==1){
                        //流水-分成
//                        divide = transactionprice -  transactionprice * subscriptionInformations.get(0).getProportion();
                        if(subscriptionInformations.get(0).getRevenueExpenditure() == 1){
                            divide = transactionprice * subscriptionInformations.get(0).getProportion();
                        }else{
                            divide =  transactionprice - (transactionprice * subscriptionInformations.get(0).getProportion());
                        }

                    }else{
                        divide = transactionprice;
                    }
                    divide = Double.parseDouble(df.format(divide));
                    //机-人配比
                    Double robotPeopleRatio=0d;
                    List<SysScenicSpotOperationRules> sysScenicSpotOperationRules = sysScenicSpotOperationRulesMapper.getOperationRulesBySpotId(sysScenicSpot.getScenicSpotId());
                    if (robotList.size()!=0){
                        robotPeopleRatio = robotList.size() / Double.parseDouble(sysScenicSpotOperationRules.get(0).getOperatePeople()) ;
                    }

                    //毛利 同公司所得分润金额
                    //净利润
                    Double netProfit = Double.parseDouble(df.format(divide - sum - sumPrice))  ;


                    //台均毛利
                    Double robotGrossProfit = divide / robotList.size();
                    if (!StringUtils.isEmpty(robotGrossProfit) && robotGrossProfit != 0){
                        robotGrossProfit = Double.parseDouble(df.format(robotGrossProfit));
                    }
                    //毛利同比
                    Double aDouble = this.grossProfitForTheSamePeriod(sysScenicSpot.getScenicSpotId(), startTime, endTime,type);
                    Double yearOnYear =0d;
                    if (aDouble !=0){
                         yearOnYear = Double.parseDouble(df.format((divide-aDouble) / Math.abs(aDouble)*1.0))  ;
                    }

                    //毛利环比
                    Double aDouble1 = this.currentGrossProfit(sysScenicSpot.getScenicSpotId(), startTime, endTime, type);
                    Double monthOnMonth = 0d;
                    if (aDouble1 != 0){
                         monthOnMonth =Double.parseDouble(df.format((divide-aDouble1)/Math.abs(aDouble1)*1.0));
                    }


                    //毛利率
                    Double grossProfitMargin = 0d ;
                    if (divide == 0 || transactionprice == 0){
                        grossProfitMargin = 0d;
                    }else{
                        grossProfitMargin =Double.parseDouble(df.format(divide / transactionprice))   ;
                    }
                    //净利率
                    Double netInterestRate = 0d;
                    if (netProfit == 0 || transactionprice == 0){
                        netInterestRate = 0d;
                    }else{
                        netInterestRate = Double.parseDouble(df.format( netProfit / transactionprice))  ;
                    }



                    HashMap<String, Object> map = new HashMap<>();
                    map.put("spotId",sysScenicSpot.getScenicSpotId());
                    map.put("spotName",sysScenicSpot.getScenicSpotName());
                    map.put("cycleTime",startTime +"-"+endTime);
                    map.put("transactionprice",transactionprice);
                    map.put("operationDuration",operationDuration);
                    map.put("averagePrice",averagePrice);
                    map.put("averageTime",averageTime);
                    map.put("cost",sum);
                    map.put("sumPrice",sumPrice);

                    map.put("comprehensiveCost",comprehensiveCost);
                    map.put("divide",divide);
                    map.put("robotPeopleRatio",robotPeopleRatio);
                    map.put("grossProfit",divide);
                    map.put("netProfit",netProfit);
                    map.put("robotGrossProfit",robotGrossProfit);
                    map.put("yearOnYear",yearOnYear);
                    map.put("monthOnMonth",monthOnMonth);
                    map.put("grossProfitMargin",grossProfitMargin);
                    map.put("netInterestRate",netInterestRate);
                    list.add(map);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        if (StringUtils.isEmpty(spotId)){
            if (!StringUtils.isEmpty(sort)){
                String[] split = sort.split(",");
                String sortType = split[0];
                String sortN = split[1];
                if (sortN.equals("desc")){
                    if (sortType.equals("1")){
                        //按毛利降序序排列
                        Collections.sort(list, new Comparator<Map<String, Object>>() {
                            public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                                Double aDouble = new Double(o1.get("divide").toString()) ;
                                Double bDouble = new Double(o2.get("divide").toString()) ;
                                return bDouble.compareTo(aDouble);
                            }
                        });
                    }else if (sortType.equals("2")){
                        //按流水降序序排列
                        Collections.sort(list, new Comparator<Map<String, Object>>() {
                            public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                                Double aDouble = new Double(o1.get("transactionprice").toString()) ;
                                Double bDouble = new Double(o2.get("transactionprice").toString()) ;
                                return bDouble.compareTo(aDouble);
                            }
                        });
                    }else if (sortType.equals("3")){
                        //台均综合成本
                        Collections.sort(list, new Comparator<Map<String, Object>>() {
                            public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                                Double aDouble = new Double(o1.get("comprehensiveCost").toString()) ;
                                Double bDouble = new Double(o2.get("comprehensiveCost").toString()) ;
                                return bDouble.compareTo(aDouble);
                            }
                        });
                    }else if (sortType.equals("4")){
                        //台均毛利
                        Collections.sort(list, new Comparator<Map<String, Object>>() {
                            public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                                Double aDouble = new Double(o1.get("robotGrossProfit").toString()) ;
                                Double bDouble = new Double(o2.get("robotGrossProfit").toString()) ;
                                return bDouble.compareTo(aDouble);
                            }
                        });
                    }else if (sortType.equals("5")){
                        //净利润
                        Collections.sort(list, new Comparator<Map<String, Object>>() {
                            public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                                Double aDouble = new Double(o1.get("netProfit").toString()) ;
                                Double bDouble = new Double(o2.get("netProfit").toString()) ;
                                return bDouble.compareTo(aDouble);
                            }
                        });
                    }
                }else if (sortN.equals("asc")){
                    if (sortType.equals("1")){
                        //按毛利降序序排列
                        Collections.sort(list, new Comparator<Map<String, Object>>() {
                            public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                                Double aDouble = new Double(o1.get("divide").toString()) ;
                                Double bDouble = new Double(o2.get("divide").toString()) ;
                                return aDouble.compareTo(bDouble);
                            }
                        });
                    }else if (sortType.equals("2")){
                        //按流水降序序排列
                        Collections.sort(list, new Comparator<Map<String, Object>>() {
                            public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                                Double aDouble = new Double(o1.get("transactionprice").toString()) ;
                                Double bDouble = new Double(o2.get("transactionprice").toString()) ;
                                return aDouble.compareTo(bDouble);
                            }
                        });
                    }else if (sortType.equals("3")){
                        //台均综合成本
                        Collections.sort(list, new Comparator<Map<String, Object>>() {
                            public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                                Double aDouble = new Double(o1.get("comprehensiveCost").toString()) ;
                                Double bDouble = new Double(o2.get("comprehensiveCost").toString()) ;
                                return aDouble.compareTo(bDouble);
                            }
                        });
                    }else if (sortType.equals("4")){
                        //台均毛利
                        Collections.sort(list, new Comparator<Map<String, Object>>() {
                            public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                                Double aDouble = new Double(o1.get("robotGrossProfit").toString()) ;
                                Double bDouble = new Double(o2.get("robotGrossProfit").toString()) ;
                                return aDouble.compareTo(bDouble);
                            }
                        });
                    }else if (sortType.equals("5")){
                        //净利润
                        Collections.sort(list, new Comparator<Map<String, Object>>() {
                            public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                                Double aDouble = new Double(o1.get("netProfit").toString()) ;
                                Double bDouble = new Double(o2.get("netProfit").toString()) ;
                                return aDouble.compareTo(bDouble);
                            }
                        });
                    }
                }
            }

        }

        //总数
        int total = list.size();
        //总页数
        int pageSum = total % pageSize == 0 ? total / pageSize : total / pageSize + 1;

        //分页
        List<Map<String,Object>> subList = list.stream().skip((pageNum - 1) * pageSize).limit(pageSize).
                collect(Collectors.toList());

        Map<String, Object> map = new HashMap<>();

        map.put("list",subList);
        map.put("total",list.size());
        return map;

//        List<Map<String,Object>> memberArticleBeanPage = new ArrayList<Map<String,Object>>();
//        int currIdx = (pageSize > 1 ? (pageSize -1) * pageSize : 0);
//        for (int i = 0; i < pageSize && i < list.size() - currIdx; i++) {
//            Map<String, Object> map = list.get(currIdx + i);
//            memberArticleBeanPage.add(map);
//        }


    }

    /**
     * 查询机器人毛利率统计图(景区)
     * @param
     */

    @Override
    public Map<Object,Object> getSpotGrossProfitMarginListStatistical(String spotId,String startDate,String endDate,String type) {


        List<SysScenicSpot> scenicSpotList = sysScenicSpotMapper.getScenicSpotListNew(spotId);

        DecimalFormat df = new DecimalFormat("0.00");

        List<RobotOperateGrossProfit> robotOperateGrossProfits = new ArrayList<>();

        RobotOperateGrossProfit robotOperateGrossProfit = new RobotOperateGrossProfit();

//        List<Object> list = new  ArrayList<>();
//        List<Object> revenueList = new ArrayList<>();
//        List<Object> yearList = new ArrayList<>();
//        List<Object> monthList = new ArrayList<>();
        Map<Object,Object> map = new HashMap<>();
//        HashMap<Object, Object> revenueMap = new HashMap<>();

        if ("1".equals(type)){//年

            for (SysScenicSpot sysScenicSpot : scenicSpotList) {
                 robotOperateGrossProfit = new RobotOperateGrossProfit();
                //景区
//                spotMap.put(sysScenicSpot.getScenicSpotId(),sysScenicSpot.getScenicSpotName());


                robotOperateGrossProfit.setSpotName(sysScenicSpot.getScenicSpotName());


//                Double revenue =  sysOrderMapper.getSpotIdByRevenue(sysScenicSpot.getScenicSpotId().toString(),startDate);
//                //                revenueMap.put(sysScenicSpot.getScenicSpotId(),revenue);
//                //营收额
//                if (StringUtils.isEmpty(revenue)){
////                    revenueList.add(0l);
//                    robotOperateGrossProfit.setGrossProfit("0");
//                }else{
//                    robotOperateGrossProfit.setGrossProfit(revenue.toString());
////                    revenueList.add(revenue);
//                }
                //毛利同比

                Long endDateN = Long.parseLong(endDate)+1;
                Double transactionprice =  sysOrderMapper.getSpotIdAndTimeByIncome(sysScenicSpot.getScenicSpotId(),startDate,endDateN.toString());
                if (StringUtils.isEmpty(transactionprice)){
                    transactionprice = 0d;
                }
                Double divide = 0d;
                Map jxzyCompanyId =  subscriptionInformationMapper.selectJxzyCompanyId();
                Object companyd = jxzyCompanyId.get("COMPANY_ID");
                List<SubscriptionInformation> subscriptionInformations = subscriptionInformationMapper.selectSpotIdByContract(sysScenicSpot.getScenicSpotId());
                if (subscriptionInformations.size()>1){
                    //分成
//                        for (SubscriptionInformation subscriptionInformation : subscriptionInformations) {
//                            if (subscriptionInformation.getCompanyId().equals(companyd)){
//                                divide =  transactionprice * subscriptionInformation.getProportion();
//                            }
//                        }
                    for (SubscriptionInformation subscriptionInformation : subscriptionInformations) {
                        if (subscriptionInformation.getRevenueExpenditure() == 1){
                            divide = divide +  transactionprice * subscriptionInformation.getProportion();
                        }else{
                            divide = divide + (transactionprice - (transactionprice * subscriptionInformation.getProportion()));
                        }

                    }

                }else if (subscriptionInformations.size()==1){
                    //流水-分成
//                        divide = transactionprice -  transactionprice * subscriptionInformations.get(0).getProportion();
                    if(subscriptionInformations.get(0).getRevenueExpenditure() == 1){
                        divide = transactionprice * subscriptionInformations.get(0).getProportion();
                    }else{
                        divide =  transactionprice - (transactionprice * subscriptionInformations.get(0).getProportion());
                    }

                }else{
                    divide = transactionprice;
                }

                divide =Double.parseDouble(df.format(divide));
                Double aDouble = this.grossProfitForTheSamePeriod(sysScenicSpot.getScenicSpotId(), startDate, endDate,Long.parseLong(type));
                Double yearOnYear =0d;
                if (aDouble !=0){
                    yearOnYear = Double.parseDouble(df.format((divide-aDouble) / Math.abs(aDouble)*1.0)) ;
                }
                if (StringUtils.isEmpty(yearOnYear)){
                    robotOperateGrossProfit.setYearOnYear("0");
                }else{
                    robotOperateGrossProfit.setYearOnYear(yearOnYear.toString());
                }
                //毛利环比
                Double aDouble1 = this.currentGrossProfit(sysScenicSpot.getScenicSpotId(), startDate, endDate, Long.parseLong(type));
                Double monthOnMonth = 0d;
                if (aDouble1 != 0){
                    monthOnMonth = Double.parseDouble(df.format((divide-aDouble1)/Math.abs(aDouble1)*1.0)) ;
                }
                if (StringUtils.isEmpty(monthOnMonth)){
                    robotOperateGrossProfit.setMonthOnMonth("0");
                }else{
                    robotOperateGrossProfit.setMonthOnMonth(monthOnMonth.toString());
                }

                robotOperateGrossProfit.setGrossProfit(divide.toString());
                robotOperateGrossProfit.setYearOnYear(yearOnYear.toString());
                robotOperateGrossProfit.setMonthOnMonth(monthOnMonth.toString());
                robotOperateGrossProfits.add(robotOperateGrossProfit);

            }

        }else if ("2".equals(type)){//月

            for (SysScenicSpot sysScenicSpot : scenicSpotList) {
                robotOperateGrossProfit = new RobotOperateGrossProfit();
                //景区
//                spotMap.put(sysScenicSpot.getScenicSpotId(),sysScenicSpot.getScenicSpotName());
                robotOperateGrossProfit.setSpotName(sysScenicSpot.getScenicSpotName());

//                Double revenue =  sysOrderMapper.getSpotIdByRevenue(sysScenicSpot.getScenicSpotId().toString(),startDate);
//                //营收额
////                revenueMap.put(sysScenicSpot.getScenicSpotId(),revenue);
//                if (StringUtils.isEmpty(revenue)){
//                    revenueList.add(0l);
//                }else{
//                    revenueList.add(revenue);
//                }

                //毛利同比
                String endDateN = DateUtil.addMouth(endDate, 1);
                Double transactionprice =  sysOrderMapper.getSpotIdAndTimeByIncome(sysScenicSpot.getScenicSpotId(),startDate,endDateN);
                if (StringUtils.isEmpty(transactionprice)){
                    transactionprice = 0d;
                }
                Double divide = 0d;
                Map jxzyCompanyId =  subscriptionInformationMapper.selectJxzyCompanyId();
                Object companyd = jxzyCompanyId.get("COMPANY_ID");
                List<SubscriptionInformation> subscriptionInformations = subscriptionInformationMapper.selectSpotIdByContract(sysScenicSpot.getScenicSpotId());
                if (subscriptionInformations.size()>1){
                    //分成
//                        for (SubscriptionInformation subscriptionInformation : subscriptionInformations) {
//                            if (subscriptionInformation.getCompanyId().equals(companyd)){
//                                divide =  transactionprice * subscriptionInformation.getProportion();
//                            }
//                        }
                    for (SubscriptionInformation subscriptionInformation : subscriptionInformations) {
                        if (subscriptionInformation.getRevenueExpenditure() == 1){
                            divide = divide +  transactionprice * subscriptionInformation.getProportion();
                        }else{
                            divide = divide + (transactionprice - (transactionprice * subscriptionInformation.getProportion()));
                        }

                    }

                }else if (subscriptionInformations.size()==1){
                    //流水-分成
//                        divide = transactionprice -  transactionprice * subscriptionInformations.get(0).getProportion();
                    if(subscriptionInformations.get(0).getRevenueExpenditure() == 1){
                        divide = transactionprice * subscriptionInformations.get(0).getProportion();
                    }else{
                        divide =  transactionprice - (transactionprice * subscriptionInformations.get(0).getProportion());
                    }

                }else{
                    divide = transactionprice;
                }
                Double aDouble = this.grossProfitForTheSamePeriod(sysScenicSpot.getScenicSpotId(), startDate, endDate,Long.parseLong(type));
                Double yearOnYear =0d;
                if (aDouble !=0){
                    yearOnYear = Double.parseDouble(df.format((divide-aDouble) / Math.abs(aDouble)*1.0)) ;
                }
                if (StringUtils.isEmpty(yearOnYear)){
                    robotOperateGrossProfit.setYearOnYear("0");
                }else{
                    robotOperateGrossProfit.setYearOnYear(yearOnYear.toString());
                }
                //毛利环比
                Double aDouble1 = this.currentGrossProfit(sysScenicSpot.getScenicSpotId(), startDate, endDate, Long.parseLong(type));
                Double monthOnMonth = 0d;
                if (aDouble1 != 0){
                    monthOnMonth = Double.parseDouble(df.format((divide-aDouble1)/Math.abs(aDouble1)*1.0)) ;
                }
                if (StringUtils.isEmpty(monthOnMonth)){
                    robotOperateGrossProfit.setMonthOnMonth("0");
                }else{
                    robotOperateGrossProfit.setMonthOnMonth(monthOnMonth.toString());
                }
                robotOperateGrossProfit.setGrossProfit(divide.toString());
                robotOperateGrossProfit.setYearOnYear(yearOnYear.toString());
                robotOperateGrossProfit.setMonthOnMonth(monthOnMonth.toString());
                robotOperateGrossProfits.add(robotOperateGrossProfit);
            }



        }else {//日

            for (SysScenicSpot sysScenicSpot : scenicSpotList) {
                robotOperateGrossProfit = new RobotOperateGrossProfit();
                //景区
//                spotMap.put(sysScenicSpot.getScenicSpotId(),sysScenicSpot.getScenicSpotName());
                robotOperateGrossProfit.setSpotName(sysScenicSpot.getScenicSpotName());

//                Double revenue =  sysOrderMapper.getSpotIdByRevenue(sysScenicSpot.getScenicSpotId().toString(),startDate);
//                //营收额
//                revenueList.add(revenue);
//                revenueMap.put(sysScenicSpot.getScenicSpotId(),revenue);
                //毛利同比
                String endDateN = DateUtil.addMouth(endDate, 1);

                Double transactionprice =  sysOrderMapper.getSpotIdAndTimeByIncome(sysScenicSpot.getScenicSpotId(),startDate,endDateN);
                if (StringUtils.isEmpty(transactionprice)){
                    transactionprice = 0d;
                }
                Double divide = 0d;
                Map jxzyCompanyId =  subscriptionInformationMapper.selectJxzyCompanyId();
                Object companyd = jxzyCompanyId.get("COMPANY_ID");
                List<SubscriptionInformation> subscriptionInformations = subscriptionInformationMapper.selectSpotIdByContract(sysScenicSpot.getScenicSpotId());
                if (subscriptionInformations.size()>1){
                    //分成
//                        for (SubscriptionInformation subscriptionInformation : subscriptionInformations) {
//                            if (subscriptionInformation.getCompanyId().equals(companyd)){
//                                divide =  transactionprice * subscriptionInformation.getProportion();
//                            }
//                        }
                    for (SubscriptionInformation subscriptionInformation : subscriptionInformations) {
                        if (subscriptionInformation.getRevenueExpenditure() == 1){
                            divide = divide +  transactionprice * subscriptionInformation.getProportion();
                        }else{
                            divide = divide + (transactionprice - (transactionprice * subscriptionInformation.getProportion()));
                        }

                    }

                }else if (subscriptionInformations.size()==1){
                    //流水-分成
//                        divide = transactionprice -  transactionprice * subscriptionInformations.get(0).getProportion();
                    if(subscriptionInformations.get(0).getRevenueExpenditure() == 1){
                        divide = transactionprice * subscriptionInformations.get(0).getProportion();
                    }else{
                        divide =  transactionprice - (transactionprice * subscriptionInformations.get(0).getProportion());
                    }

                }else{
                    divide = transactionprice;
                }

                Double aDouble = this.grossProfitForTheSamePeriod(sysScenicSpot.getScenicSpotId(), startDate, endDate,Long.parseLong(type));
                Double yearOnYear =0d;
                if (aDouble !=0){
                    yearOnYear = Double.parseDouble(df.format((divide-aDouble) / Math.abs(aDouble)*1.0)) ;
                }
                if (StringUtils.isEmpty(yearOnYear)){
                    robotOperateGrossProfit.setYearOnYear("0");
                }else{
                    robotOperateGrossProfit.setYearOnYear(yearOnYear.toString());
                }
                //毛利环比
                Double aDouble1 = this.currentGrossProfit(sysScenicSpot.getScenicSpotId(), startDate, endDate, Long.parseLong(type));
                Double monthOnMonth = 0d;
                if (aDouble1 != 0){
                    monthOnMonth = Double.parseDouble(df.format((divide-aDouble1)/Math.abs(aDouble1)*1.0));
                }
                if (StringUtils.isEmpty(monthOnMonth)){
                    robotOperateGrossProfit.setMonthOnMonth("0");
                }else{
                    robotOperateGrossProfit.setMonthOnMonth(monthOnMonth.toString());
                }
                robotOperateGrossProfit.setGrossProfit(divide.toString());
                robotOperateGrossProfit.setMonthOnMonth(monthOnMonth.toString());
                robotOperateGrossProfit.setYearOnYear(yearOnYear.toString());
                robotOperateGrossProfits.add(robotOperateGrossProfit);

            }
        }

        Collections.sort(robotOperateGrossProfits, new Comparator<RobotOperateGrossProfit>() {
            public int compare(RobotOperateGrossProfit o1, RobotOperateGrossProfit o2) {
//                Double aDouble = new Double(o1.get("runningAmount").toString()) ;
//                Double bDouble = new Double(o2.get("runningAmount").toString()) ;
                Double aDouble = new Double(o1.getGrossProfit()) ;
                Double bDouble = new Double(o2.getGrossProfit()) ;
                return bDouble.compareTo(aDouble);
            }
        });

        List<Object> oneList = new ArrayList<>();
        List<Object> twoList = new ArrayList<>();
        List<Object> threeList = new ArrayList<>();
        List<Object> fourList = new ArrayList<>();

        for (RobotOperateGrossProfit operateGrossProfit : robotOperateGrossProfits) {
            oneList.add(operateGrossProfit.getSpotName());
            twoList.add(operateGrossProfit.getGrossProfit());
            threeList.add(operateGrossProfit.getYearOnYear());
            fourList.add(operateGrossProfit.getMonthOnMonth());
        }

        map.put("spotNameList",oneList);
        map.put("grossProfitList",twoList);
        map.put("yearOnYearList",threeList);
        map.put("monthOnMonthList",fourList);

        return map;

    }
    /**
     * 查询机器人毛利率统计图(时间)
     * @param
     */

    @Override
    public  HashMap<Object, Object> getSpotGrossProfitMarginListStatisticalDate(String spotId, String startDate, String endDate, String type) {

        ArrayList<Object> list = new ArrayList<>();

//        HashMap<Object, Object> dateMap = new HashMap<>();
//        HashMap<Object, Object> revenueMap = new HashMap<>();

        DecimalFormat df = new DecimalFormat("0.00");
        List<RobotOperateGrossProfit> robotOperateGrossProfits = new ArrayList<>();

        RobotOperateGrossProfit robotOperateGrossProfit = new RobotOperateGrossProfit();

        try {

            if ("1".equals(type)){//年

                List<SysScenicSpot> scenicSpotList = sysScenicSpotMapper.getScenicSpotListNew(spotId);
                for (SysScenicSpot sysScenicSpot : scenicSpotList) {
                    robotOperateGrossProfit =  new RobotOperateGrossProfit();
                    Long endDateN = Long.parseLong(endDate)+1;
                    Double transactionprice =  sysOrderMapper.getSpotIdAndTimeByIncome(sysScenicSpot.getScenicSpotId(),startDate,endDateN.toString());
                    if (StringUtils.isEmpty(transactionprice)){
                        transactionprice = 0d;
                    }
                    Double divide = 0d;
                    Map jxzyCompanyId =  subscriptionInformationMapper.selectJxzyCompanyId();
                    Object companyd = jxzyCompanyId.get("COMPANY_ID");
                    List<SubscriptionInformation> subscriptionInformations = subscriptionInformationMapper.selectSpotIdByContract(sysScenicSpot.getScenicSpotId());
                    if (subscriptionInformations.size()>1){
                        //分成
//                        for (SubscriptionInformation subscriptionInformation : subscriptionInformations) {
//                            if (subscriptionInformation.getCompanyId().equals(companyd)){
//                                divide =  transactionprice * subscriptionInformation.getProportion();
//                            }
//                        }
                        for (SubscriptionInformation subscriptionInformation : subscriptionInformations) {
                             if (subscriptionInformation.getRevenueExpenditure() == 1){
                                 divide = divide +  transactionprice * subscriptionInformation.getProportion();
                             }else{
                                 divide = divide + (transactionprice - (transactionprice * subscriptionInformation.getProportion()));
                             }

                        }

                    }else if (subscriptionInformations.size()==1){
                        //流水-分成
//                        divide = transactionprice -  transactionprice * subscriptionInformations.get(0).getProportion();
                        if(subscriptionInformations.get(0).getRevenueExpenditure() == 1){
                            divide = transactionprice * subscriptionInformations.get(0).getProportion();
                        }else{
                            divide =  transactionprice - (transactionprice * subscriptionInformations.get(0).getProportion());
                        }

                    }else{
                        divide = transactionprice;
                    }

                    //毛利同比
                    Double aDouble = this.grossProfitForTheSamePeriod(sysScenicSpot.getScenicSpotId(), startDate, endDate,Long.parseLong(type));
                    Double yearOnYear =0d;
                    if (aDouble !=0){
                        yearOnYear = (divide-aDouble) / Math.abs(aDouble)*1.0;
                    }
                    if (StringUtils.isEmpty(yearOnYear)){
                        robotOperateGrossProfit.setYearOnYear("0");
                    }else{
                        robotOperateGrossProfit.setYearOnYear(yearOnYear.toString());
                    }
                    //毛利环比
                    Double aDouble1 = this.currentGrossProfit(sysScenicSpot.getScenicSpotId(), startDate, endDate, Long.parseLong(type));
                    Double monthOnMonth = 0d;
                    if (aDouble1 != 0){
                        monthOnMonth = (divide-aDouble1)/Math.abs(aDouble1)*1.0;
                    }
                    if (StringUtils.isEmpty(monthOnMonth)){
                        robotOperateGrossProfit.setMonthOnMonth("0");
                    }else{
                        robotOperateGrossProfit.setMonthOnMonth(monthOnMonth.toString());
                    }
                    robotOperateGrossProfit.setSpotName(sysScenicSpot.getScenicSpotName());
                    robotOperateGrossProfit.setGrossProfit(divide.toString());


                    robotOperateGrossProfits.add(robotOperateGrossProfit);
//                    List<String> dateList = DateUtil.betweenYears(startDate, endDate);
//                    for (int i = 0; i < dateList.size(); i++) {
//                        dateMap.put(i+1,dateList.get(i));
//                        Double spotIdByRevenue = sysOrderMapper.getSpotIdByRevenue(sysScenicSpot.getScenicSpotId().toString(), dateList.get(i));
//                        revenueMap.put(dateList.get(i),spotIdByRevenue);
//
                }


            }else if ("2".equals(type)){//月

                List<SysScenicSpot> scenicSpotList = sysScenicSpotMapper.getScenicSpotListNew(spotId);

                for (SysScenicSpot sysScenicSpot : scenicSpotList) {
                    robotOperateGrossProfit =  new RobotOperateGrossProfit();
                    //毛利同比
                    String endDateN = DateUtil.addMouth(endDate, 1);
                    Double transactionprice =  sysOrderMapper.getSpotIdAndTimeByIncome(sysScenicSpot.getScenicSpotId(),startDate,endDateN);
                    if (StringUtils.isEmpty(transactionprice)){
                        transactionprice = 0d;
                    }
                    Double divide = 0d;
                    Map jxzyCompanyId =  subscriptionInformationMapper.selectJxzyCompanyId();
                    Object companyd = jxzyCompanyId.get("COMPANY_ID");
                    List<SubscriptionInformation> subscriptionInformations = subscriptionInformationMapper.selectSpotIdByContract(sysScenicSpot.getScenicSpotId());
                    if (subscriptionInformations.size()>1){
                        //分成
//                        for (SubscriptionInformation subscriptionInformation : subscriptionInformations) {
//                            if (subscriptionInformation.getCompanyId().equals(companyd)){
//                                divide =  transactionprice * subscriptionInformation.getProportion();
//                            }
//                        }
                        for (SubscriptionInformation subscriptionInformation : subscriptionInformations) {
                            if (subscriptionInformation.getRevenueExpenditure() == 1){
                                divide = divide +  transactionprice * subscriptionInformation.getProportion();
                            }else{
                                divide = divide + (transactionprice - (transactionprice * subscriptionInformation.getProportion()));
                            }

                        }

                    }else if (subscriptionInformations.size()==1){
                        //流水-分成
//                        divide = transactionprice -  transactionprice * subscriptionInformations.get(0).getProportion();
                        if(subscriptionInformations.get(0).getRevenueExpenditure() == 1){
                            divide = transactionprice * subscriptionInformations.get(0).getProportion();
                        }else{
                            divide =  transactionprice - (transactionprice * subscriptionInformations.get(0).getProportion());
                        }

                    }else{
                        divide = transactionprice;
                    }
                    divide = Double.parseDouble(df.format(divide));

                    Double aDouble = this.grossProfitForTheSamePeriod(sysScenicSpot.getScenicSpotId(), startDate, endDate,Long.parseLong(type));
                    Double yearOnYear =0d;
                    if (aDouble !=0){
                        yearOnYear = Double.parseDouble(df.format((divide-aDouble) / Math.abs(aDouble)*1.0)) ;
                    }
                    if (StringUtils.isEmpty(yearOnYear)){
                        robotOperateGrossProfit.setYearOnYear("0");
                    }else{
                        robotOperateGrossProfit.setYearOnYear(yearOnYear.toString());
                    }
                    //毛利环比
                    Double aDouble1 = this.currentGrossProfit(sysScenicSpot.getScenicSpotId(), startDate, endDate, Long.parseLong(type));
                    Double monthOnMonth = 0d;
                    if (aDouble1 != 0){
                        monthOnMonth =Double.parseDouble(df.format((divide-aDouble1)/Math.abs(aDouble1)*1.0));
                    }
                    if (StringUtils.isEmpty(monthOnMonth)){
                        robotOperateGrossProfit.setMonthOnMonth("0");
                    }else{
                        robotOperateGrossProfit.setMonthOnMonth(monthOnMonth.toString());
                    }

                    robotOperateGrossProfit.setSpotName(sysScenicSpot.getScenicSpotName());
                    robotOperateGrossProfit.setGrossProfit(divide.toString());

                    robotOperateGrossProfits.add(robotOperateGrossProfit);

                }


            }else if ("3".equals(type)){

                List<SysScenicSpot> scenicSpotList = sysScenicSpotMapper.getScenicSpotListNew(spotId);

                for (SysScenicSpot sysScenicSpot : scenicSpotList) {
                    robotOperateGrossProfit =  new RobotOperateGrossProfit();
                    //毛利同比
                    String endDateN = DateUtil.addMouth(endDate, 1);
                    Double transactionprice =  sysOrderMapper.getSpotIdAndTimeByIncome(sysScenicSpot.getScenicSpotId(),startDate,endDateN);
                    if (StringUtils.isEmpty(transactionprice)){
                        transactionprice = 0d;
                    }
                    Double divide = 0d;
                    Map jxzyCompanyId =  subscriptionInformationMapper.selectJxzyCompanyId();
                    Object companyd = jxzyCompanyId.get("COMPANY_ID");
                    List<SubscriptionInformation> subscriptionInformations = subscriptionInformationMapper.selectSpotIdByContract(sysScenicSpot.getScenicSpotId());
                    if (subscriptionInformations.size()>1){
                        //分成
//                        for (SubscriptionInformation subscriptionInformation : subscriptionInformations) {
//                            if (subscriptionInformation.getCompanyId().equals(companyd)){
//                                divide =  transactionprice * subscriptionInformation.getProportion();
//                            }
//                        }
                        for (SubscriptionInformation subscriptionInformation : subscriptionInformations) {
                            if (subscriptionInformation.getRevenueExpenditure() == 1){
                                divide = divide +  transactionprice * subscriptionInformation.getProportion();
                            }else{
                                divide = divide + (transactionprice - (transactionprice * subscriptionInformation.getProportion()));
                            }

                        }

                    }else if (subscriptionInformations.size()==1){
                        //流水-分成
//                        divide = transactionprice -  transactionprice * subscriptionInformations.get(0).getProportion();
                        if(subscriptionInformations.get(0).getRevenueExpenditure() == 1){
                            divide = transactionprice * subscriptionInformations.get(0).getProportion();
                        }else{
                            divide =  transactionprice - (transactionprice * subscriptionInformations.get(0).getProportion());
                        }

                    }else{
                        divide = transactionprice;
                    }

                    Double aDouble = this.grossProfitForTheSamePeriod(sysScenicSpot.getScenicSpotId(), startDate, endDate,Long.parseLong(type));
                    Double yearOnYear =0d;
                    if (aDouble !=0){
                        yearOnYear =Double.parseDouble(df.format((divide-aDouble) / Math.abs(aDouble)*1.0)) ;
                    }
                    if (StringUtils.isEmpty(yearOnYear)){
                        robotOperateGrossProfit.setYearOnYear("0");
                    }else{
                        robotOperateGrossProfit.setYearOnYear(yearOnYear.toString());
                    }
                    //毛利环比
                    Double aDouble1 = this.currentGrossProfit(sysScenicSpot.getScenicSpotId(), startDate, endDate, Long.parseLong(type));
                    Double monthOnMonth = 0d;
                    if (aDouble1 != 0){
                        monthOnMonth =Double.parseDouble(df.format((divide-aDouble1)/Math.abs(aDouble1)*1.0));
                    }
                    if (StringUtils.isEmpty(monthOnMonth)){
                        robotOperateGrossProfit.setMonthOnMonth("0");
                    }else{
                        robotOperateGrossProfit.setMonthOnMonth(monthOnMonth.toString());
                    }

                    robotOperateGrossProfit.setSpotName(sysScenicSpot.getScenicSpotName());
                    robotOperateGrossProfit.setGrossProfit(divide.toString());
                    robotOperateGrossProfits.add(robotOperateGrossProfit);

                }


            }
        } catch (Exception parseException) {
            parseException.printStackTrace();
        }

        //按毛利降序序排列
        Collections.sort(robotOperateGrossProfits, new Comparator<RobotOperateGrossProfit>() {
            public int compare(RobotOperateGrossProfit o1, RobotOperateGrossProfit o2) {
//                Double aDouble = new Double(o1.get("runningAmount").toString()) ;
//                Double bDouble = new Double(o2.get("runningAmount").toString()) ;
                Double aDouble = new Double(o1.getGrossProfit()) ;
                Double bDouble = new Double(o2.getGrossProfit()) ;
                return bDouble.compareTo(aDouble);
            }
        });

        List<Object> oneList = new ArrayList<>();
        List<Object> twoList = new ArrayList<>();
        List<Object> threeList = new ArrayList<>();
        List<Object> fourList = new ArrayList<>();

        for (RobotOperateGrossProfit operateGrossProfit : robotOperateGrossProfits) {
            oneList.add(operateGrossProfit.getSpotName());
            twoList.add(operateGrossProfit.getGrossProfit());
            threeList.add(operateGrossProfit.getYearOnYear());
            fourList.add(operateGrossProfit.getMonthOnMonth());
        }

        HashMap<Object, Object> map = new HashMap<>();

        map.put("spotNameList",oneList);
        map.put("grossProfitList",twoList);
        map.put("yearOnYearList",threeList);
        map.put("monthOnMonthList",fourList);
        return map;
    }

    /**
     * 计算合计
     * @param spotId
     * @param
     * @param type
     * @return
     */
    @Override
    public Map<String, Object> getSpotGrossProfitMarginListStatisticalTotal(Long spotId, String startTime, String endTime, Long type) {

        List<Map<String,Object>> list = new ArrayList<>();
        DecimalFormat df = new DecimalFormat("#.00");

        //合计毛利
        Double grossProfitAll = 0d;
        //合计净利润
        Double netProfitAll = 0d;
        try {
            if (StringUtils.isEmpty(spotId)){
                List<SysScenicSpot> scenicSpotList = sysScenicSpotMapper.getScenicSpotListWords();
                for (SysScenicSpot sysScenicSpot : scenicSpotList) {

                    List<SysRobot> robotList = sysRobotMapper.getRobotListByScenicSpotId(sysScenicSpot.getScenicSpotId());
                    if (robotList.size()<=0){
                        //运营成本(运营人员成本、景区营销成本、租金、维养成本(均显示总值))
//                        Double sumPrice = sysScenicSpotTargetAmountMapper.getTargetAmountGpmList(sysScenicSpot.getScenicSpotId(),startTime,endTime);

                        continue;
                    }
                    if (type ==1){//年
                        //交易金额
                        //需将结束时间向后加一年（按年查询的）
                        Long endTimeN = Long.parseLong(endTime)+1;
                        Double transactionprice =  sysOrderMapper.getSpotIdAndTimeByIncome(sysScenicSpot.getScenicSpotId(),startTime,endTimeN.toString());
                        if (StringUtils.isEmpty(transactionprice)){
                            transactionprice = 0d;
                        }else{
                            transactionprice = Double.parseDouble(df.format(transactionprice));
                        }
//                        //运营时长
//                        Double operationDuration =  sysOrderMapper.getSpotIdAndTimeByOperationDuration(sysScenicSpot.getScenicSpotId(),startTime,endTimeN.toString());
//                        if (!StringUtils.isEmpty(operationDuration)){
//                            operationDuration = Double.parseDouble(df.format(operationDuration));
//                        }
//                        //平均单台交易金额
//                        Double  averagePrice =0d;
//                        Long count = 0l;
//                        if (!StringUtils.isEmpty(operationDuration) && operationDuration != 0){
//                            count = sysRobotMapper.getSpotIdByRobotCount(sysScenicSpot.getScenicSpotId());
//                            averagePrice = Double.parseDouble(df.format(transactionprice / count) );
//                        }
//                        //平均运营时长
//                        Double averageTime = 0d;
//                        if (!StringUtils.isEmpty(operationDuration) && operationDuration != 0){
//                            averageTime = operationDuration / count;
//                            averageTime = Double.parseDouble(df.format(averageTime));
//                        }

                        //获取景区机器人，获取折旧金额
//                      List<SysRobot> robotList = sysRobotMapper.getRobotList(search);
                        Double sum = 0d;
                        for (SysRobot sysRobot : robotList) {
                            SysRobotSoftAssetInformation robotSoftAssetInformationByRobot = sysRobotSoftAssetInformationMapper.getRobotSoftAssetInformationByRobotId(sysRobot.getRobotId());
                            if (StringUtils.isEmpty(robotSoftAssetInformationByRobot)){
                                //暂时跳过
                                continue;
//                            throw new Exception(sysRobot.getRobotCode()+ "机器人无软资产信息");
                            }
                            if (StringUtils.isEmpty(robotSoftAssetInformationByRobot.getNetPrice()) || robotSoftAssetInformationByRobot.getNetPrice() <= 0){
                                continue;
                            }
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                            Calendar cal = Calendar.getInstance();
                            cal.setTime(sdf.parse(robotSoftAssetInformationByRobot.getDateProduction()));
                            cal.add(Calendar.YEAR,3);
                            String format = sdf.format(cal.getTime());
                            String year= format.substring(0,4);
                            System.out.println(format);
//
                            if (StringUtils.isEmpty(startTime) && StringUtils.isEmpty(endTime)){
                                sum += robotSoftAssetInformationByRobot.getAccumulate();
                            }else{
                                String dateProduction = robotSoftAssetInformationByRobot.getDateProduction();
                                //
                                if (0 < startTime.compareTo(year) || 0 > endTime.compareTo(dateProduction.substring(0,4))){
                                    //不在
                                    continue;
                                }else if(0 >= endTime.compareTo(year) && 0 >= dateProduction.compareTo(startTime) ){
                                    //在成本时间内
//                                    String months = DateUtil.findYears(startTime, endTime);
                                    Long  months = (Long.parseLong(endTime) - Long.parseLong(startTime))+1;
                                    Double price =  (Double.parseDouble(robotSoftAssetInformationByRobot.getFactoryCost()) / (Double.parseDouble(robotSoftAssetInformationByRobot.getServiceLife()) * 12)) * (months * 12);
                                    sum += price;
                                }else if ( 0 >= startTime.compareTo(dateProduction) && 0 >= endTime.compareTo(year)){
                                    //少于生产时间的时间段
//                                    String months =  DateUtil.findYears(dateProduction, endTime);
                                    String substring = dateProduction.substring(0, 7);

                                    String endTimeEnd = format.substring(0,7);
                                    String months = DateUtil.findMonths(substring, endTimeEnd);
                                    if ("1".equals(months)){
                                        months = "2";
                                    }
                                    Double price =  (Double.parseDouble(robotSoftAssetInformationByRobot.getFactoryCost()) / (Double.parseDouble(robotSoftAssetInformationByRobot.getServiceLife()) * 12)) * (Double.parseDouble(months)-1);
                                    sum += price;
                                }else if ( 0 <= startTime.compareTo(dateProduction) && 0 <= endTime.compareTo(year)){


                                    startTime = startTime + "-01";

                                    String substring = format.substring(0, 7);
                                    String months = DateUtil.findMonths(substring, endTime);
                                    if ("1".equals(months)){
                                        months = "2";
                                    }
                                    Double price =  (Double.parseDouble(robotSoftAssetInformationByRobot.getFactoryCost()) / (Double.parseDouble(robotSoftAssetInformationByRobot.getServiceLife()) * 12)) * (Double.parseDouble(months)-1);
                                    sum += price;
                                }

                            }
                        }
                        //运营成本(运营人员成本、景区营销成本、租金、维养成本(均显示总值))
                        Double sumPrice = sysScenicSpotTargetAmountMapper.getTargetAmountGpmList(sysScenicSpot.getScenicSpotId(),startTime,endTimeN.toString());
                        if (StringUtils.isEmpty(sumPrice)){
                            sumPrice = 0d;
                        }

                        //台均和成本
//                        Double comprehensiveCost = (sum + sumPrice) / robotList.size();
//                        if (StringUtils.isEmpty(comprehensiveCost)){
//                            comprehensiveCost = 0d;
//                        }
                        //公司所得分润金额
                        Double divide = 0d;
                        Map jxzyCompanyId =  subscriptionInformationMapper.selectJxzyCompanyId();
                        Object companyd = jxzyCompanyId.get("COMPANY_ID");
                        List<SubscriptionInformation> subscriptionInformations = subscriptionInformationMapper.selectSpotIdByContract(sysScenicSpot.getScenicSpotId());
                        if (subscriptionInformations.size()>1){
                            //分成
//                        for (SubscriptionInformation subscriptionInformation : subscriptionInformations) {
//                            if (subscriptionInformation.getCompanyId().equals(companyd)){
//                                divide =  transactionprice * subscriptionInformation.getProportion();
//                            }
//                        }
                            for (SubscriptionInformation subscriptionInformation : subscriptionInformations) {
                                if (subscriptionInformation.getRevenueExpenditure() == 1){
                                    divide = divide +  transactionprice * subscriptionInformation.getProportion();
                                }else{
                                    divide = divide + (transactionprice - (transactionprice * subscriptionInformation.getProportion()));
                                }

                            }

                        }else if (subscriptionInformations.size()==1){
                            //流水-分成
//                        divide = transactionprice -  transactionprice * subscriptionInformations.get(0).getProportion();
                            if(subscriptionInformations.get(0).getRevenueExpenditure() == 1){
                                divide = transactionprice * subscriptionInformations.get(0).getProportion();
                            }else{
                                divide =  transactionprice - (transactionprice * subscriptionInformations.get(0).getProportion());
                            }

                        }else{
                            divide = transactionprice;
                        }
                        //机-人配比
//                        Double robotPeopleRatio=0d;
//                        List<SysScenicSpotOperationRules> sysScenicSpotOperationRules = sysScenicSpotOperationRulesMapper.getOperationRulesBySpotId(sysScenicSpot.getScenicSpotId());
//                        if (robotList.size()!=0){
//                            robotPeopleRatio = robotList.size() / Double.parseDouble(sysScenicSpotOperationRules.get(0).getOperatePeople()) ;
//                        }

                        //毛利 同公司所得分润金额

                        //净利润
                       Double netProfit = divide - sum - sumPrice;

                        //台均毛利
//                        Double robotGrossProfit = divide / robotList.size();
//                        if (!StringUtils.isEmpty(robotGrossProfit) && robotGrossProfit != 0){
//                            robotGrossProfit = Double.parseDouble(df.format(robotGrossProfit));
//                        }
                        //毛利同比
                        //去年毛利
//                        Double aDouble = this.grossProfitForTheSamePeriod(sysScenicSpot.getScenicSpotId(), startTime, endTime,type);
//                        Double yearOnYear = 0d;
//                        if (aDouble != 0){
//                            yearOnYear = (divide-aDouble)/Math.abs(aDouble)*1.0;
//                        }


                        //毛利环比
                        //上期收入
//                        Double aDouble1 = this.currentGrossProfit(sysScenicSpot.getScenicSpotId(), startTime, endTime, type);
//                        Double monthOnMonth = 0d;
//                        if (aDouble1 != 0){
//                            monthOnMonth = (divide-aDouble1)/Math.abs(aDouble1)*1.0;
//                        }

                        //毛利率
//                        Double grossProfitMargin = 0d;
//                        if (divide == 0 || transactionprice == 0){
//                            grossProfitMargin = 0d;
//                        }else{
//                            grossProfitMargin =  divide / transactionprice ;
//                        }


                        //净利率
//                        Double netInterestRate = 0d;
//                        if (netProfit == 0 || transactionprice == 0){
//                            netInterestRate = 0d;
//                        }else{
//                            netInterestRate =  netProfit / transactionprice ;
//                        }
                        grossProfitAll = grossProfitAll + divide;
                        netProfitAll = netProfitAll + netProfit;


                    }else if (type == 2){//按照月

                        //交易金额
                        String endTimeN = DateUtil.addMouth(endTime, 1);

                        Double transactionprice =  sysOrderMapper.getSpotIdAndTimeByIncome(sysScenicSpot.getScenicSpotId(),startTime,endTimeN);
                        if (StringUtils.isEmpty(transactionprice)){
                            transactionprice = 0d;
                        }else{
                            transactionprice = Double.parseDouble(df.format(transactionprice));
                        }
                        //运营时长
//                        Double operationDuration =  sysOrderMapper.getSpotIdAndTimeByOperationDuration(sysScenicSpot.getScenicSpotId(),startTime,endTimeN);
//                        if (!StringUtils.isEmpty(operationDuration) && operationDuration != 0){
//                            operationDuration = Double.parseDouble(df.format(operationDuration));
//                        }
                        //平均单台交易金额
//                        Double  averagePrice =0d;
//                        Long count = 0l;
//                        if (!StringUtils.isEmpty(operationDuration) && operationDuration != 0){
//                            count = sysRobotMapper.getSpotIdByRobotCount(sysScenicSpot.getScenicSpotId());
//                            averagePrice = transactionprice / count;
//                            averagePrice = Double.parseDouble(df.format(averagePrice));
//                        }
                        //平均运营时长
//                        Double averageTime = 0d;
//                        if (!StringUtils.isEmpty(operationDuration) && operationDuration != 0){
//                            averageTime = operationDuration / count;
//                            averageTime = Double.parseDouble(df.format(averageTime));
//                        }

                        //获取景区机器人，获取折旧金额
//                      List<SysRobot> robotList = sysRobotMapper.getRobotList(search);
                        Double sum = 0d;


                        for (SysRobot sysRobot : robotList) {
                            SysRobotSoftAssetInformation robotSoftAssetInformationByRobot = sysRobotSoftAssetInformationMapper.getRobotSoftAssetInformationByRobotId(sysRobot.getRobotId());
                            if (StringUtils.isEmpty(robotSoftAssetInformationByRobot)){
                                //暂时跳过
                                continue;
//                            throw new Exception(sysRobot.getRobotCode()+ "机器人无软资产信息");
                            }
                            if (StringUtils.isEmpty(robotSoftAssetInformationByRobot.getNetPrice()) || robotSoftAssetInformationByRobot.getNetPrice() <= 0){
                                continue;
                            }
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                            Calendar cal = Calendar.getInstance();
                            cal.setTime(sdf.parse(robotSoftAssetInformationByRobot.getDateProduction()));
                            cal.add(Calendar.YEAR,3);
                            String format = sdf.format(cal.getTime());
                            System.out.println(format);
//
                            if (StringUtils.isEmpty(startTime) && StringUtils.isEmpty(endTime)){
                                sum += robotSoftAssetInformationByRobot.getAccumulate();
                            }else{
                                String dateProduction = robotSoftAssetInformationByRobot.getDateProduction();

                                if (0 >= endTime.compareTo(format) && 0 >= dateProduction.compareTo(startTime)){
                                    //在成本时间内
                                    String months = DateUtil.findMonths(startTime, endTime);
                                    if ("1".equals(months)){
                                        months = "2";
                                    }
                                    Double price =  (Double.parseDouble(robotSoftAssetInformationByRobot.getFactoryCost()) / (Double.parseDouble(robotSoftAssetInformationByRobot.getServiceLife()) * 12)) * (Double.parseDouble(months)-1);
                                    sum += price;

                                }else if( 0 < startTime.compareTo(format) || 0 > endTime.compareTo(dateProduction)){
                                    //不在
                                    continue;
                                }else if ( 0 <= endTime.compareTo(dateProduction) && 0 >= endTime.compareTo(format)){
                                    //
                                    String months =  DateUtil.findMonths(dateProduction, endTime);
                                    if ("1".equals(months)){
                                        months = "2";
                                    }
                                    Double price =  (Double.parseDouble(robotSoftAssetInformationByRobot.getFactoryCost()) / (Double.parseDouble(robotSoftAssetInformationByRobot.getServiceLife()) * 12)) * (Double.parseDouble(months)-1);
                                    sum += price;
                                }else if ( 0 <= startTime.compareTo(dateProduction) && 0 >= startTime.compareTo(format)){

                                    String months =  DateUtil.findMonths(startTime, format);
                                    if ("1".equals(months)){
                                        months = "2";
                                    }
                                    Double price =  (Double.parseDouble(robotSoftAssetInformationByRobot.getFactoryCost()) / (Double.parseDouble(robotSoftAssetInformationByRobot.getServiceLife()) * 12)) * (Double.parseDouble(months)-1);
                                    sum += price;
                                }else  {
                                    String months =  DateUtil.findMonths(dateProduction, format);
                                    if ("1".equals(months)){
                                        months = "2";
                                    }
                                    Double price =  (Double.parseDouble(robotSoftAssetInformationByRobot.getFactoryCost()) / (Double.parseDouble(robotSoftAssetInformationByRobot.getServiceLife()) * 12)) * (Double.parseDouble(months)-1);
                                    sum += price;
                                }
                            }
                        }
                        //运营成本(运营人员成本、景区营销成本、租金、维养成本(均显示总值))
                        Double sumPrice = sysScenicSpotTargetAmountMapper.getTargetAmountGpmList(sysScenicSpot.getScenicSpotId(),startTime,endTimeN);
                        if (StringUtils.isEmpty(sumPrice)){
                            sumPrice = 0d;
                        }


                        //台均和成本
//                        Double comprehensiveCost = (sum + sumPrice) / robotList.size();
//                        if (StringUtils.isEmpty(comprehensiveCost)){
//                            comprehensiveCost = 0d;
//                        }
                        //公司所得分润金额
                        Double divide = 0d;
                        Map jxzyCompanyId =  subscriptionInformationMapper.selectJxzyCompanyId();
                        Object companyd = jxzyCompanyId.get("COMPANY_ID");
                        List<SubscriptionInformation> subscriptionInformations = subscriptionInformationMapper.selectSpotIdByContract(sysScenicSpot.getScenicSpotId());
                        if (subscriptionInformations.size()>1){
                            //分成
//                        for (SubscriptionInformation subscriptionInformation : subscriptionInformations) {
//                            if (subscriptionInformation.getCompanyId().equals(companyd)){
//                                divide =  transactionprice * subscriptionInformation.getProportion();
//                            }
//                        }
                            for (SubscriptionInformation subscriptionInformation : subscriptionInformations) {
                                if (subscriptionInformation.getRevenueExpenditure() == 1){
                                    divide = divide +  transactionprice * subscriptionInformation.getProportion();
                                }else{
                                    divide = divide + (transactionprice - (transactionprice * subscriptionInformation.getProportion()));
                                }

                            }

                        }else if (subscriptionInformations.size()==1){
                            //流水-分成
//                        divide = transactionprice -  transactionprice * subscriptionInformations.get(0).getProportion();
                            if(subscriptionInformations.get(0).getRevenueExpenditure() == 1){
                                divide = transactionprice * subscriptionInformations.get(0).getProportion();
                            }else{
                                divide =  transactionprice - (transactionprice * subscriptionInformations.get(0).getProportion());
                            }

                        }else{
                            divide = transactionprice;
                        }
                        //机-人配比
//                        Double robotPeopleRatio=0d;
//                        List<SysScenicSpotOperationRules> sysScenicSpotOperationRules = sysScenicSpotOperationRulesMapper.getOperationRulesBySpotId(sysScenicSpot.getScenicSpotId());
//                        if (robotList.size()!=0){
//                            robotPeopleRatio = robotList.size() / Double.parseDouble(sysScenicSpotOperationRules.get(0).getOperatePeople()) ;
//                        }

                        //毛利 同公司所得分润金额

                        //净利润
                        Double netProfit = divide - sum - sumPrice;

                        //台均毛利
//                        Double robotGrossProfit = divide / robotList.size();
//                        if (!StringUtils.isEmpty(robotGrossProfit) && robotGrossProfit !=0){
//                            robotGrossProfit = Double.parseDouble(df.format(robotGrossProfit));
//                        }
                        //毛利同比
                        //去年毛利

//                        Double aDouble = this.grossProfitForTheSamePeriod(sysScenicSpot.getScenicSpotId(), startTime, endTime,type);
//                        Double yearOnYear = 0d;
//                        if (aDouble != 0){
//                            yearOnYear = (divide-aDouble)/Math.abs(aDouble)*1.0;
//                        }


                        //毛利环比
                        //上期收入
//                        Double aDouble1 = this.currentGrossProfit(sysScenicSpot.getScenicSpotId(), startTime, endTime, type);
//                        Double monthOnMonth =0d;
//                        if (aDouble1 != 0){
//                            monthOnMonth = (divide-aDouble1)/Math.abs(aDouble1)*1.0;
//                        }

                        //毛利率
//                        Double grossProfitMargin = 0d;
//                        if (divide == 0 || transactionprice == 0){
//                            grossProfitMargin = 0d;
//                        }else{
//                            grossProfitMargin =  divide / transactionprice ;
//                        }
                        //净利率
//                        Double netInterestRate = 0d;
//
//                        if (netProfit == 0 || transactionprice == 0){
//                            netInterestRate = 0d;
//                        }else{
//                            netInterestRate =  netProfit / transactionprice ;
//                        }

                        grossProfitAll = grossProfitAll + divide;
                        netProfitAll = netProfitAll + netProfit;

                    }
                }
            }else{//根据景区查询
                SysScenicSpot sysScenicSpot = sysScenicSpotMapper.selectByScenicSpotId(spotId);
                List<SysRobot> robotList = sysRobotMapper.getRobotListByScenicSpotId(sysScenicSpot.getScenicSpotId());
                if (robotList.size()<=0){

                    Map<String, Object> map = new HashMap<>();

                    map.put("grossProfitAll",grossProfitAll);
                    map.put("netProfitAll",netProfitAll);
                    return map;
                }

                if (type ==1 ){ //年查询
                    //交易金额
                    //需将结束时间向后加一年（按年查询的）
                    Long endTimeN = Long.parseLong(endTime)+1;
                    Double transactionprice =  sysOrderMapper.getSpotIdAndTimeByIncome(sysScenicSpot.getScenicSpotId(),startTime,endTimeN.toString());
                    if (StringUtils.isEmpty(transactionprice)){
                        transactionprice = 0d;
                    }else{
                        transactionprice = Double.parseDouble(df.format(transactionprice));
                    }
                    //运营时长
//                    Double operationDuration =  sysOrderMapper.getSpotIdAndTimeByOperationDuration(sysScenicSpot.getScenicSpotId(),startTime,endTimeN.toString());
//                    if (!StringUtils.isEmpty(operationDuration) && operationDuration != 0){
//                        operationDuration = Double.parseDouble(df.format(operationDuration)) ;
//                    }
                    //平均单台交易金额
//                    Double  averagePrice =0d;
//                    Long count = 0l;
//                    if (!StringUtils.isEmpty(operationDuration) && operationDuration != 0){
//                        count = sysRobotMapper.getSpotIdByRobotCount(sysScenicSpot.getScenicSpotId());
//                        averagePrice = transactionprice / count;
//                        averagePrice = Double.parseDouble(df.format(averagePrice));
//                    }
                    //平均运营时长
//                    Double averageTime = 0d;
//                    if (!StringUtils.isEmpty(operationDuration) && operationDuration != 0){
//                        averageTime = operationDuration / count;
//                        averageTime = Double.parseDouble(df.format(averageTime));
//                    }



                    //获取景区机器人，获取折旧金额
//                    List<SysRobot> robotList = sysRobotMapper.getRobotList(search);
                    Double sum = 0d;
                    for (SysRobot sysRobot : robotList) {
                        SysRobotSoftAssetInformation robotSoftAssetInformationByRobot = sysRobotSoftAssetInformationMapper.getRobotSoftAssetInformationByRobotId(sysRobot.getRobotId());
                        if (StringUtils.isEmpty(robotSoftAssetInformationByRobot)){
                            //暂时跳过
                            continue;
//                            throw new Exception(sysRobot.getRobotCode()+ "机器人无软资产信息");
                        }
                        if (StringUtils.isEmpty(robotSoftAssetInformationByRobot.getNetPrice()) || robotSoftAssetInformationByRobot.getNetPrice() <= 0){
                            continue;
                        }
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        Calendar cal = Calendar.getInstance();
                        cal.setTime(sdf.parse(robotSoftAssetInformationByRobot.getDateProduction()));
                        cal.add(Calendar.YEAR,3);
                        String format = sdf.format(cal.getTime());
                        String year = format.substring(0, 4);
                        System.out.println(format);
//
                        if (StringUtils.isEmpty(startTime) && StringUtils.isEmpty(endTime)){
                            sum += robotSoftAssetInformationByRobot.getAccumulate();
                        }else{
                            String dateProduction = robotSoftAssetInformationByRobot.getDateProduction();

                            //
                            if (0 < startTime.compareTo(year) || 0 > endTime.compareTo(dateProduction.substring(0,4))){
                                //不在
                                continue;
                            }else if(0 >= endTime.compareTo(year) && 0 >= dateProduction.compareTo(startTime) ){
                                //在成本时间内
//                                    String months = DateUtil.findYears(startTime, endTime);
                                Long  months = (Long.parseLong(endTime) - Long.parseLong(startTime))+1;

                                Double price =  (Double.parseDouble(robotSoftAssetInformationByRobot.getFactoryCost()) / (Double.parseDouble(robotSoftAssetInformationByRobot.getServiceLife()) * 12)) * (months * 12);
                                sum += price;
                            }else if ( 0 >= startTime.compareTo(dateProduction) && 0 >= endTime.compareTo(year)){
                                //少于生产时间的时间段
//                                    String months =  DateUtil.findYears(dateProduction, endTime);
                                String substring = dateProduction.substring(0, 7);

                                String endTimeEnd = format.substring(0,7);
                                String months = DateUtil.findMonths(substring, endTimeEnd);
                                if ("1".equals(months)){
                                    months = "2";
                                }
                                Double price =  (Double.parseDouble(robotSoftAssetInformationByRobot.getFactoryCost()) / (Double.parseDouble(robotSoftAssetInformationByRobot.getServiceLife()) * 12)) * (Double.parseDouble(months)-1);
                                sum += price;
                            }else if ( 0 <= startTime.compareTo(dateProduction) && 0 <= endTime.compareTo(year)){


                                startTime = startTime + "-01";

                                String substring = format.substring(0, 7);
                                String months = DateUtil.findMonths(substring, endTime);
                                if ("1".equals(months)){
                                    months = "2";
                                }
                                Double price =  (Double.parseDouble(robotSoftAssetInformationByRobot.getFactoryCost()) / (Double.parseDouble(robotSoftAssetInformationByRobot.getServiceLife()) * 12)) * (Double.parseDouble(months)-1);
                                sum += price;
                            }

                        }
                    }

                    //运营成本(运营人员成本、景区营销成本、租金、维养成本(均显示总值))
                    Double sumPrice = sysScenicSpotTargetAmountMapper.getTargetAmountGpmList(sysScenicSpot.getScenicSpotId(),startTime,endTimeN.toString());
                    if (StringUtils.isEmpty(sumPrice)){
                        sumPrice = 0d;
                    }

                    //台均和成本
//                    Double comprehensiveCost = (sum + sumPrice) / robotList.size();
//                    if (StringUtils.isEmpty(comprehensiveCost)){
//                        comprehensiveCost = 0d;
//                    }
                    //公司所得分润金额
                    Double divide = 0d;
                    Map jxzyCompanyId =  subscriptionInformationMapper.selectJxzyCompanyId();
                    Object companyd = jxzyCompanyId.get("COMPANY_ID");
                    List<SubscriptionInformation> subscriptionInformations = subscriptionInformationMapper.selectSpotIdByContract(sysScenicSpot.getScenicSpotId());
                    if (subscriptionInformations.size()>1){
                        //分成
//                        for (SubscriptionInformation subscriptionInformation : subscriptionInformations) {
//                            if (subscriptionInformation.getCompanyId().equals(companyd)){
//                                divide =  transactionprice * subscriptionInformation.getProportion();
//                            }
//                        }
                        for (SubscriptionInformation subscriptionInformation : subscriptionInformations) {
                            if (subscriptionInformation.getRevenueExpenditure() == 1){
                                divide = divide +  transactionprice * subscriptionInformation.getProportion();
                            }else{
                                divide = divide + (transactionprice - (transactionprice * subscriptionInformation.getProportion()));
                            }

                        }

                    }else if (subscriptionInformations.size()==1){
                        //流水-分成
//                        divide = transactionprice -  transactionprice * subscriptionInformations.get(0).getProportion();
                        if(subscriptionInformations.get(0).getRevenueExpenditure() == 1){
                            divide = transactionprice * subscriptionInformations.get(0).getProportion();
                        }else{
                            divide =  transactionprice - (transactionprice * subscriptionInformations.get(0).getProportion());
                        }

                    }else{
                        divide = transactionprice;
                    }
                    //机-人配比
//                    Double robotPeopleRatio=0d;
//                    List<SysScenicSpotOperationRules> sysScenicSpotOperationRules = sysScenicSpotOperationRulesMapper.getOperationRulesBySpotId(sysScenicSpot.getScenicSpotId());
//                    if (robotList.size()!=0){
//                        robotPeopleRatio = robotList.size() / Double.parseDouble(sysScenicSpotOperationRules.get(0).getOperatePeople()) ;
//                    }

                    //毛利 同公司所得分润金额

                    //净利润
                    Double netProfit = divide - sum - sumPrice;

                    //台均毛利
//                    Double robotGrossProfit = divide / robotList.size();
//                    if (StringUtils.isEmpty(robotGrossProfit) && robotGrossProfit != 0){
//                        robotGrossProfit = Double.parseDouble(df.format(robotGrossProfit));
//                    }
                    //毛利同比
//                    Double aDouble = this.grossProfitForTheSamePeriod(sysScenicSpot.getScenicSpotId(), startTime, endTime,type);
//                    Double yearOnYear = 0d;
//                    if (aDouble != 0){
//                        yearOnYear = (divide-aDouble) / Math.abs(aDouble)*1.0;
//                    }


                    //毛利环比
//                    Double aDouble1 = this.currentGrossProfit(sysScenicSpot.getScenicSpotId(), startTime, endTime, type);
//                    Double monthOnMonth = 0d;
//                    if (aDouble1 != 0){
//                        monthOnMonth = (divide-aDouble1)/Math.abs(aDouble1)*1.0;
//                    }


                    //毛利率
//                    Double grossProfitMargin = 0d ;
//                    if (divide == 0 || transactionprice == 0){
//                        grossProfitMargin = 0d;
//                    }else{
//                        grossProfitMargin =  divide / transactionprice ;
//                    }


                    //净利率
//                    Double netInterestRate = 0d;
//                    if (netProfit == 0 || transactionprice == 0){
//                        netInterestRate = 0d;
//                    }else{
//                        netInterestRate =  netProfit / transactionprice ;
//                    }
                    grossProfitAll = grossProfitAll + divide;
                    netProfitAll = netProfitAll + netProfit;

                }else if (type == 2){//按月查询
                    //交易金额
                    String endTimeN = DateUtil.addMouth(endTime, 1);
                    Double transactionprice =  sysOrderMapper.getSpotIdAndTimeByIncome(sysScenicSpot.getScenicSpotId(),startTime,endTimeN);
                    if (StringUtils.isEmpty(transactionprice)){
                        transactionprice = 0d;
                    }else{
                        transactionprice = Double.parseDouble(df.format(transactionprice));
                    }
                    //运营时长
//                    Double operationDuration =  sysOrderMapper.getSpotIdAndTimeByOperationDuration(sysScenicSpot.getScenicSpotId(),startTime,endTimeN);
//                    if (!StringUtils.isEmpty(operationDuration) && operationDuration != 0){
//                        operationDuration = Double.parseDouble(df.format(operationDuration)) ;
//                    }
                    //平均单台交易金额
//                    Double  averagePrice =0d;
//                    Long count = 0l;
//                    if (!StringUtils.isEmpty(operationDuration) && operationDuration != 0){
//                        count = sysRobotMapper.getSpotIdByRobotCount(sysScenicSpot.getScenicSpotId());
//                        averagePrice = transactionprice / count;
//                        averagePrice = Double.parseDouble(df.format(averagePrice));
//                    }
                    //平均运营时长
//                    Double averageTime = 0d;
//                    if (!StringUtils.isEmpty(operationDuration) && operationDuration != 0){
//                        averageTime = operationDuration / count;
//                        averageTime = Double.parseDouble(df.format(averageTime));
//                    }


                    //获取景区机器人，获取折旧金额
//                    List<SysRobot> robotList = sysRobotMapper.getRobotList(search);
                    Double sum = 0d;
                    for (SysRobot sysRobot : robotList) {
                        SysRobotSoftAssetInformation robotSoftAssetInformationByRobot = sysRobotSoftAssetInformationMapper.getRobotSoftAssetInformationByRobotId(sysRobot.getRobotId());
                        if (StringUtils.isEmpty(robotSoftAssetInformationByRobot)){
                            //暂时跳过
                            continue;
//                            throw new Exception(sysRobot.getRobotCode()+ "机器人无软资产信息");
                        }
                        if (StringUtils.isEmpty(robotSoftAssetInformationByRobot.getNetPrice()) || robotSoftAssetInformationByRobot.getNetPrice() <= 0){
                            continue;
                        }
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        Calendar cal = Calendar.getInstance();
                        cal.setTime(sdf.parse(robotSoftAssetInformationByRobot.getDateProduction()));
                        cal.add(Calendar.YEAR,3);
                        String format = sdf.format(cal.getTime());
                        System.out.println(format);
//
                        if (StringUtils.isEmpty(startTime) && StringUtils.isEmpty(endTime)){
                            sum += robotSoftAssetInformationByRobot.getAccumulate();
                        }else{
                            String dateProduction = robotSoftAssetInformationByRobot.getDateProduction();

                            if (0 >= endTime.compareTo(format) && 0 >= dateProduction.compareTo(startTime)){
                                //在成本时间内
                                String months = DateUtil.findMonths(startTime, endTime);
                                if ("1".equals(months)){
                                    months = "2";
                                }
                                Double price =  (Double.parseDouble(robotSoftAssetInformationByRobot.getFactoryCost()) / (Double.parseDouble(robotSoftAssetInformationByRobot.getServiceLife()) * 12)) * (Double.parseDouble(months)-1);
                                sum += price;

                            }else if( 0 < startTime.compareTo(format) || 0 > endTime.compareTo(dateProduction)){
                                //不在
                                continue;
                            }else if ( 0 <= endTime.compareTo(dateProduction) && 0 >= endTime.compareTo(format)){
                                //
                                String months =  DateUtil.findMonths(dateProduction, endTime);
                                if ("1".equals(months)){
                                    months = "2";
                                }
                                Double price =  (Double.parseDouble(robotSoftAssetInformationByRobot.getFactoryCost()) / (Double.parseDouble(robotSoftAssetInformationByRobot.getServiceLife()) * 12)) * (Double.parseDouble(months)-1);
                                sum += price;
                            }else if ( 0 <= startTime.compareTo(dateProduction) && 0 >= startTime.compareTo(format)){

                                String months =  DateUtil.findMonths(startTime, format);
                                if ("1".equals(months)){
                                    months = "2";
                                }
                                Double price =  (Double.parseDouble(robotSoftAssetInformationByRobot.getFactoryCost()) / (Double.parseDouble(robotSoftAssetInformationByRobot.getServiceLife()) * 12)) * (Double.parseDouble(months)-1);
                                sum += price;
                            }else  {

                                String months =  DateUtil.findMonths(dateProduction, format);
                                if ("1".equals(months)){
                                    months = "2";
                                }
                                Double price =  (Double.parseDouble(robotSoftAssetInformationByRobot.getFactoryCost()) / (Double.parseDouble(robotSoftAssetInformationByRobot.getServiceLife()) * 12)) * (Double.parseDouble(months)-1);
                                sum += price;
                            }
                        }
                    }
                    //运营成本(运营人员成本、景区营销成本、租金、维养成本(均显示总值))
                    Double sumPrice = sysScenicSpotTargetAmountMapper.getTargetAmountGpmList(sysScenicSpot.getScenicSpotId(),startTime,endTimeN);
                    if (StringUtils.isEmpty(sumPrice)){
                        sumPrice = 0d;
                    }

                    //台均和成本
//                    Double comprehensiveCost = (sum + sumPrice) / robotList.size();
//                    if (StringUtils.isEmpty(comprehensiveCost)){
//                        comprehensiveCost = 0d;
//                    }
                    //公司所得分润金额
                    Double divide = 0d;
                    Map jxzyCompanyId =  subscriptionInformationMapper.selectJxzyCompanyId();
                    Object companyd = jxzyCompanyId.get("COMPANY_ID");
                    List<SubscriptionInformation> subscriptionInformations = subscriptionInformationMapper.selectSpotIdByContract(sysScenicSpot.getScenicSpotId());
                    if (subscriptionInformations.size()>1){
                        //分成
//                        for (SubscriptionInformation subscriptionInformation : subscriptionInformations) {
//                            if (subscriptionInformation.getCompanyId().equals(companyd)){
//                                divide =  transactionprice * subscriptionInformation.getProportion();
//                            }
//                        }
                        for (SubscriptionInformation subscriptionInformation : subscriptionInformations) {
                            if (subscriptionInformation.getRevenueExpenditure() == 1){
                                divide = divide +  transactionprice * subscriptionInformation.getProportion();
                            }else{
                                divide = divide + (transactionprice - (transactionprice * subscriptionInformation.getProportion()));
                            }

                        }

                    }else if (subscriptionInformations.size()==1){
                        //流水-分成
//                        divide = transactionprice -  transactionprice * subscriptionInformations.get(0).getProportion();
                        if(subscriptionInformations.get(0).getRevenueExpenditure() == 1){
                            divide = transactionprice * subscriptionInformations.get(0).getProportion();
                        }else{
                            divide =  transactionprice - (transactionprice * subscriptionInformations.get(0).getProportion());
                        }

                    }else{
                        divide = transactionprice;
                    }
                    //机-人配比
//                    Double robotPeopleRatio=0d;
//                    List<SysScenicSpotOperationRules> sysScenicSpotOperationRules = sysScenicSpotOperationRulesMapper.getOperationRulesBySpotId(sysScenicSpot.getScenicSpotId());
//                    if (robotList.size()!=0){
//                        robotPeopleRatio = robotList.size() / Double.parseDouble(sysScenicSpotOperationRules.get(0).getOperatePeople()) ;
//                    }

                    //毛利 同公司所得分润金额
                    //净利润
                    Double netProfit = divide - sum - sumPrice;

                    //台均毛利
//                    Double robotGrossProfit = divide / robotList.size();
//                    if (!StringUtils.isEmpty(robotGrossProfit) && robotGrossProfit != 0){
//                        robotGrossProfit = Double.parseDouble(df.format(robotGrossProfit));
//                    }
                    //毛利同比
//                    Double aDouble = this.grossProfitForTheSamePeriod(sysScenicSpot.getScenicSpotId(), startTime, endTime,type);
//                    Double yearOnYear =0d;
//                    if (aDouble !=0){
//                        yearOnYear = (divide-aDouble) / Math.abs(aDouble)*1.0;
//                    }

                    //毛利环比
//                    Double aDouble1 = this.currentGrossProfit(sysScenicSpot.getScenicSpotId(), startTime, endTime, type);
//                    Double monthOnMonth = 0d;
//                    if (aDouble1 != 0){
//                        monthOnMonth = (divide-aDouble1)/Math.abs(aDouble1)*1.0;
//                    }


                    //毛利率
//                    Double grossProfitMargin = 0d ;
//                    if (divide == 0 || transactionprice == 0){
//                        grossProfitMargin = 0d;
//                    }else{
//                        grossProfitMargin =  divide / transactionprice ;
//                    }
//                    //净利率
//                    Double netInterestRate = 0d;
//                    if (netProfit == 0 || transactionprice == 0){
//                        netInterestRate = 0d;
//                    }else{
//                        netInterestRate =  netProfit / transactionprice ;
//                    }
                    grossProfitAll = grossProfitAll + divide;
                    netProfitAll = netProfitAll + netProfit;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        Map<String, Object> map = new HashMap<>();
        map.put("grossProfitAll",df.format(grossProfitAll) );
        map.put("netProfitAll",df.format(netProfitAll));
        return map;

    }

    /**
     * 导出
     * @param type
     * @param spotId
     * @param startTime
     * @param endTime
     * @param sort
     * @return
     */
    @Override
    public List<RobotOperateGrossProfit> getSpotGrossProfitMarginExcel(Long type, Long spotId, String startTime, String endTime, String sort) {
        List<RobotOperateGrossProfit> list = new ArrayList<>();
        DecimalFormat df = new DecimalFormat("#.00");

        RobotOperateGrossProfit robotOperateGrossProfit = new RobotOperateGrossProfit();
        try {
            if (StringUtils.isEmpty(spotId)){
                List<SysScenicSpot> scenicSpotList = sysScenicSpotMapper.getScenicSpotListWords();
                for (SysScenicSpot sysScenicSpot : scenicSpotList) {

                    List<SysRobot> robotList = sysRobotMapper.getRobotListByScenicSpotId(sysScenicSpot.getScenicSpotId());
                    if (robotList.size()<=0){
                        //运营成本(运营人员成本、景区营销成本、租金、维养成本(均显示总值))
                        Double sumPrice = sysScenicSpotTargetAmountMapper.getTargetAmountGpmList(sysScenicSpot.getScenicSpotId(),startTime,endTime);
                        robotOperateGrossProfit = new RobotOperateGrossProfit();

                        robotOperateGrossProfit.setSpotId(sysScenicSpot.getScenicSpotId());
                        robotOperateGrossProfit.setSpotName(sysScenicSpot.getScenicSpotName());
                        robotOperateGrossProfit.setCycleTime(startTime +"-"+endTime);
                        robotOperateGrossProfit.setTransactionprice("0");
                        robotOperateGrossProfit.setOperationDuration("0");
                        robotOperateGrossProfit.setAveragePrice("0");
                        robotOperateGrossProfit.setAverageTime("0");
                        robotOperateGrossProfit.setCost("0");
                        robotOperateGrossProfit.setSumPrice(StringUtils.isEmpty(sumPrice) ? 0:sumPrice);

                        robotOperateGrossProfit.setComprehensiveCost("0");

                        robotOperateGrossProfit.setDivide("0");

                        robotOperateGrossProfit.setRobotPeopleRatio("0");

                        robotOperateGrossProfit.setGrossProfit("0");

                        robotOperateGrossProfit.setNetProfit("0");

                        robotOperateGrossProfit.setRobotGrossProfit("0");

                        robotOperateGrossProfit.setYearOnYear(null);

                        robotOperateGrossProfit.setMonthOnMonth(null);

                        robotOperateGrossProfit.setGrossProfitMargin("0");

                        robotOperateGrossProfit.setNetInterestRate("0");
                        list.add(robotOperateGrossProfit);
                        continue;
                    }
                    if (type ==1){//年
                        //交易金额
                        //需将结束时间向后加一年（按年查询的）
                        Long endTimeN = Long.parseLong(endTime)+1;
                        Double transactionprice =  sysOrderMapper.getSpotIdAndTimeByIncome(sysScenicSpot.getScenicSpotId(),startTime,endTimeN.toString());
                        if (StringUtils.isEmpty(transactionprice)){
                            transactionprice = 0d;
                        }else{
                            transactionprice = Double.parseDouble(df.format(transactionprice));
                        }
                        //运营时长
                        Double operationDuration =  sysOrderMapper.getSpotIdAndTimeByOperationDuration(sysScenicSpot.getScenicSpotId(),startTime,endTimeN.toString());
                        if (!StringUtils.isEmpty(operationDuration)){
                            operationDuration = Double.parseDouble(df.format(operationDuration));
                        }
                        //平均单台交易金额
                        Double  averagePrice =0d;
                        Long count = 0l;
                        if (!StringUtils.isEmpty(operationDuration) && operationDuration != 0){
                            count = sysRobotMapper.getSpotIdByRobotCount(sysScenicSpot.getScenicSpotId());
                            averagePrice = Double.parseDouble(df.format(transactionprice / count) );
                        }
                        //平均运营时长
                        Double averageTime = 0d;
                        if (!StringUtils.isEmpty(operationDuration) && operationDuration != 0){
                            averageTime = operationDuration / count;
                            averageTime = Double.parseDouble(df.format(averageTime));
                        }

                        //获取景区机器人，获取折旧金额
//                      List<SysRobot> robotList = sysRobotMapper.getRobotList(search);
                        Double sum = 0d;

                        for (SysRobot sysRobot : robotList) {
                            SysRobotSoftAssetInformation robotSoftAssetInformationByRobot = sysRobotSoftAssetInformationMapper.getRobotSoftAssetInformationByRobotId(sysRobot.getRobotId());
                            if (StringUtils.isEmpty(robotSoftAssetInformationByRobot)){
                                //暂时跳过
                                continue;
//                            throw new Exception(sysRobot.getRobotCode()+ "机器人无软资产信息");
                            }
                            if (StringUtils.isEmpty(robotSoftAssetInformationByRobot.getNetPrice()) || robotSoftAssetInformationByRobot.getNetPrice() <= 0){
                                continue;
                            }
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                            Calendar cal = Calendar.getInstance();
                            cal.setTime(sdf.parse(robotSoftAssetInformationByRobot.getDateProduction()));
                            cal.add(Calendar.YEAR,3);
                            String format = sdf.format(cal.getTime());
                            String year= format.substring(0,4);
                            System.out.println(format);
//
                            if (StringUtils.isEmpty(startTime) && StringUtils.isEmpty(endTime)){
                                sum += robotSoftAssetInformationByRobot.getAccumulate();
                            }else{
                                String dateProduction = robotSoftAssetInformationByRobot.getDateProduction();
                                //
                                if (0 < startTime.compareTo(year) || 0 > endTime.compareTo(dateProduction.substring(0,4))){
                                    //不在
                                    continue;
                                }else if(0 >= endTime.compareTo(year) && 0 >= dateProduction.compareTo(startTime) ){
                                    //在成本时间内
//                                    String months = DateUtil.findYears(startTime, endTime);
                                    Long  months = (Long.parseLong(endTime) - Long.parseLong(startTime))+1;
                                    Double price =  (Double.parseDouble(robotSoftAssetInformationByRobot.getFactoryCost()) / (Double.parseDouble(robotSoftAssetInformationByRobot.getServiceLife()) * 12)) * (months * 12);
                                    sum += price;
                                }else if ( 0 >= startTime.compareTo(dateProduction) && 0 >= endTime.compareTo(year)){
                                    //少于生产时间的时间段
//                                    String months =  DateUtil.findYears(dateProduction, endTime);
                                    String substring = dateProduction.substring(0, 7);

                                    String endTimeEnd = format.substring(0,7);
                                    String months = DateUtil.findMonths(substring, endTimeEnd);
                                    if ("1".equals(months)){
                                        months = "2";
                                    }
                                    Double price =  (Double.parseDouble(robotSoftAssetInformationByRobot.getFactoryCost()) / (Double.parseDouble(robotSoftAssetInformationByRobot.getServiceLife()) * 12)) * (Double.parseDouble(months)-1);
                                    sum += price;
                                }else if ( 0 <= startTime.compareTo(dateProduction) && 0 <= endTime.compareTo(year)){


                                    startTime = startTime + "-01";

                                    String substring = format.substring(0, 7);
                                    String months = DateUtil.findMonths(substring, endTime);
                                    if ("1".equals(months)){
                                        months = "2";
                                    }
                                    Double price =  (Double.parseDouble(robotSoftAssetInformationByRobot.getFactoryCost()) / (Double.parseDouble(robotSoftAssetInformationByRobot.getServiceLife()) * 12)) * (Double.parseDouble(months)-1);
                                    sum += price;
                                }

                            }
                        }

                        //运营成本(运营人员成本、景区营销成本、租金、维养成本(均显示总值))
                        Double sumPrice = sysScenicSpotTargetAmountMapper.getTargetAmountGpmList(sysScenicSpot.getScenicSpotId(),startTime,endTimeN.toString());
                        if (StringUtils.isEmpty(sumPrice)){
                            sumPrice = 0d;
                        }

                        //台均和成本
                        Double comprehensiveCost = (sum + sumPrice) / robotList.size();
                        if (StringUtils.isEmpty(comprehensiveCost)){
                            comprehensiveCost = 0d;
                        }
                        //公司所得分润金额
                        Double divide = 0d;
                        Map jxzyCompanyId =  subscriptionInformationMapper.selectJxzyCompanyId();
                        Object companyd = jxzyCompanyId.get("COMPANY_ID");
                        List<SubscriptionInformation> subscriptionInformations = subscriptionInformationMapper.selectSpotIdByContract(sysScenicSpot.getScenicSpotId());
                        if (subscriptionInformations.size()>1){
                            //分成
//                        for (SubscriptionInformation subscriptionInformation : subscriptionInformations) {
//                            if (subscriptionInformation.getCompanyId().equals(companyd)){
//                                divide =  transactionprice * subscriptionInformation.getProportion();
//                            }
//                        }
                            for (SubscriptionInformation subscriptionInformation : subscriptionInformations) {
                                if (subscriptionInformation.getRevenueExpenditure() == 1){
                                    divide = divide +  transactionprice * subscriptionInformation.getProportion();
                                }else{
                                    divide = divide + (transactionprice - (transactionprice * subscriptionInformation.getProportion()));
                                }

                            }

                        }else if (subscriptionInformations.size()==1){
                            //流水-分成
//                        divide = transactionprice -  transactionprice * subscriptionInformations.get(0).getProportion();
                            if(subscriptionInformations.get(0).getRevenueExpenditure() == 1){
                                divide = transactionprice * subscriptionInformations.get(0).getProportion();
                            }else{
                                divide =  transactionprice - (transactionprice * subscriptionInformations.get(0).getProportion());
                            }

                        }else{
                            divide = transactionprice;
                        }
                        //机-人配比
                        Double robotPeopleRatio=0d;
                        List<SysScenicSpotOperationRules> sysScenicSpotOperationRules = sysScenicSpotOperationRulesMapper.getOperationRulesBySpotId(sysScenicSpot.getScenicSpotId());
                        if (robotList.size()!=0){
                            robotPeopleRatio = robotList.size() / Double.parseDouble(sysScenicSpotOperationRules.get(0).getOperatePeople()) ;
                        }

                        //毛利 同公司所得分润金额

                        //净利润
                        Double netProfit = divide - sum - sumPrice;

                        //台均毛利
                        Double robotGrossProfit = divide / robotList.size();
                        if (!StringUtils.isEmpty(robotGrossProfit) && robotGrossProfit != 0){
                            robotGrossProfit = Double.parseDouble(df.format(robotGrossProfit));
                        }
                        //毛利同比
                        //去年毛利
                        Double aDouble = this.grossProfitForTheSamePeriod(sysScenicSpot.getScenicSpotId(), startTime, endTime,type);
                        Double yearOnYear = 0d;
                        if (aDouble != 0){
                            yearOnYear = Double.parseDouble(df.format((divide-aDouble)/Math.abs(aDouble)*1.0)) ;
                        }


                        //毛利环比
                        //上期收入
                        Double aDouble1 = this.currentGrossProfit(sysScenicSpot.getScenicSpotId(), startTime, endTime, type);
                        Double monthOnMonth = 0d;
                        if (aDouble1 != 0){
                            monthOnMonth =Double.parseDouble(df.format((divide-aDouble1)/Math.abs(aDouble1)*1.0)) ;
                        }

                        //毛利率
                        Double grossProfitMargin = 0d;
                        if (divide == 0 || transactionprice == 0){
                            grossProfitMargin = 0d;
                        }else{
                            grossProfitMargin =Double.parseDouble(df.format(divide / transactionprice))   ;
                        }


                        //净利率
                        Double netInterestRate = 0d;
                        if (netProfit == 0 || transactionprice == 0){
                            netInterestRate = 0d;
                        }else{
                            netInterestRate =Double.parseDouble(df.format(netProfit / transactionprice))   ;
                        }

                        robotOperateGrossProfit = new RobotOperateGrossProfit();
                        robotOperateGrossProfit.setSpotId(sysScenicSpot.getScenicSpotId());
                        robotOperateGrossProfit.setSpotName(sysScenicSpot.getScenicSpotName());
                        robotOperateGrossProfit.setCycleTime(startTime +"-"+endTime);
                        robotOperateGrossProfit.setTransactionprice(transactionprice.toString());
                        robotOperateGrossProfit.setOperationDuration(operationDuration.toString());
                        robotOperateGrossProfit.setAveragePrice(averagePrice.toString());
                        robotOperateGrossProfit.setAverageTime(averageTime.toString());
                        robotOperateGrossProfit.setCost(sum.toString());
                        robotOperateGrossProfit.setSumPrice(sumPrice);
                        robotOperateGrossProfit.setComprehensiveCost(comprehensiveCost.toString());
                        robotOperateGrossProfit.setDivide(divide.toString());
                        robotOperateGrossProfit.setRobotPeopleRatio(robotPeopleRatio.toString());
                        robotOperateGrossProfit.setGrossProfit(divide.toString());
                        robotOperateGrossProfit.setNetProfit(netProfit.toString());
                        robotOperateGrossProfit.setRobotGrossProfit(robotGrossProfit.toString());
                        robotOperateGrossProfit.setYearOnYear(yearOnYear.toString());
                        robotOperateGrossProfit.setMonthOnMonth(monthOnMonth.toString());
                        robotOperateGrossProfit.setGrossProfitMargin(grossProfitMargin.toString());
                        robotOperateGrossProfit.setNetInterestRate(netInterestRate.toString());
                        list.add(robotOperateGrossProfit);


                    }else if (type == 2){//按照月

                        //交易金额
                        String endTimeN = DateUtil.addMouth(endTime, 1);

                        Double transactionprice =  sysOrderMapper.getSpotIdAndTimeByIncome(sysScenicSpot.getScenicSpotId(),startTime,endTimeN);
                        if (StringUtils.isEmpty(transactionprice)){
                            transactionprice = 0d;
                        }else{
                            transactionprice = Double.parseDouble(df.format(transactionprice));
                        }
                        //运营时长
                        Double operationDuration =  sysOrderMapper.getSpotIdAndTimeByOperationDuration(sysScenicSpot.getScenicSpotId(),startTime,endTimeN);
                        if (!StringUtils.isEmpty(operationDuration) && operationDuration != 0){
                            operationDuration = Double.parseDouble(df.format(operationDuration));
                        }else{
                            operationDuration = 0d;
                        }
                        //平均单台交易金额
                        Double  averagePrice =0d;
                        Long count = 0l;
                        if (!StringUtils.isEmpty(operationDuration) && operationDuration != 0){
                            count = sysRobotMapper.getSpotIdByRobotCount(sysScenicSpot.getScenicSpotId());
                            averagePrice = Double.parseDouble(df.format(transactionprice / count)) ;

                        }
                        //平均运营时长
                        Double averageTime = 0d;
                        if (!StringUtils.isEmpty(operationDuration) && operationDuration != 0){
                            averageTime = Double.parseDouble(df.format(operationDuration / count)) ;

                        }

                        //获取景区机器人，获取折旧金额
//                      List<SysRobot> robotList = sysRobotMapper.getRobotList(search);
                        Double sum = 0d;


                        for (SysRobot sysRobot : robotList) {
                            SysRobotSoftAssetInformation robotSoftAssetInformationByRobot = sysRobotSoftAssetInformationMapper.getRobotSoftAssetInformationByRobotId(sysRobot.getRobotId());
                            if (StringUtils.isEmpty(robotSoftAssetInformationByRobot)){
                                //暂时跳过
                                continue;
//                            throw new Exception(sysRobot.getRobotCode()+ "机器人无软资产信息");
                            }
                            if (StringUtils.isEmpty(robotSoftAssetInformationByRobot.getNetPrice()) || robotSoftAssetInformationByRobot.getNetPrice() <= 0){
                                continue;
                            }
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                            Calendar cal = Calendar.getInstance();
                            cal.setTime(sdf.parse(robotSoftAssetInformationByRobot.getDateProduction()));
                            cal.add(Calendar.YEAR,3);
                            String format = sdf.format(cal.getTime());
                            System.out.println(format);
//
                            if (StringUtils.isEmpty(startTime) && StringUtils.isEmpty(endTime)){
                                sum += robotSoftAssetInformationByRobot.getAccumulate();
                            }else{
                                String dateProduction = robotSoftAssetInformationByRobot.getDateProduction();

                                if (0 >= endTime.compareTo(format) && 0 >= dateProduction.compareTo(startTime)){
                                    //在成本时间内
                                    String months = DateUtil.findMonths(startTime, endTime);
                                    if ("1".equals(months)){
                                        months = "2";
                                    }
                                    Double price =  (Double.parseDouble(robotSoftAssetInformationByRobot.getFactoryCost()) / (Double.parseDouble(robotSoftAssetInformationByRobot.getServiceLife()) * 12)) * (Double.parseDouble(months)-1);
                                    sum += price;

                                }else if( 0 < startTime.compareTo(format) || 0 > endTime.compareTo(dateProduction)){
                                    //不在
                                    continue;
                                }else if ( 0 <= endTime.compareTo(dateProduction) && 0 >= endTime.compareTo(format)){
                                    //
                                    String months =  DateUtil.findMonths(dateProduction, endTime);
                                    if ("1".equals(months)){
                                        months = "2";
                                    }
                                    Double price =  (Double.parseDouble(robotSoftAssetInformationByRobot.getFactoryCost()) / (Double.parseDouble(robotSoftAssetInformationByRobot.getServiceLife()) * 12)) * (Double.parseDouble(months)-1);
                                    sum += price;
                                }else if ( 0 <= startTime.compareTo(dateProduction) && 0 >= startTime.compareTo(format)){

                                    String months =  DateUtil.findMonths(startTime, format);
                                    if ("1".equals(months)){
                                        months = "2";
                                    }
                                    Double price =  (Double.parseDouble(robotSoftAssetInformationByRobot.getFactoryCost()) / (Double.parseDouble(robotSoftAssetInformationByRobot.getServiceLife()) * 12)) * (Double.parseDouble(months)-1);
                                    sum += price;
                                }else  {
                                    String months =  DateUtil.findMonths(dateProduction, format);
                                    if ("1".equals(months)){
                                        months = "2";
                                    }
                                    Double price =  (Double.parseDouble(robotSoftAssetInformationByRobot.getFactoryCost()) / (Double.parseDouble(robotSoftAssetInformationByRobot.getServiceLife()) * 12)) * (Double.parseDouble(months)-1);
                                    sum += price;
                                }
                            }
                        }
                        //运营成本(运营人员成本、景区营销成本、租金、维养成本(均显示总值))
                        Double sumPrice = sysScenicSpotTargetAmountMapper.getTargetAmountGpmList(sysScenicSpot.getScenicSpotId(),startTime,endTimeN);
                        if (StringUtils.isEmpty(sumPrice)){
                            sumPrice = 0d;
                        }


                        //台均和成本
                        Double comprehensiveCost = (sum + sumPrice) / robotList.size();
                        if (StringUtils.isEmpty(comprehensiveCost)){
                            comprehensiveCost = 0d;
                        }
                        //公司所得分润金额
                        Double divide = 0d;
                        Map jxzyCompanyId =  subscriptionInformationMapper.selectJxzyCompanyId();
                        Object companyd = jxzyCompanyId.get("COMPANY_ID");
                        List<SubscriptionInformation> subscriptionInformations = subscriptionInformationMapper.selectSpotIdByContract(sysScenicSpot.getScenicSpotId());
                        if (subscriptionInformations.size()>1){
                            //分成
//                        for (SubscriptionInformation subscriptionInformation : subscriptionInformations) {
//                            if (subscriptionInformation.getCompanyId().equals(companyd)){
//                                divide =  transactionprice * subscriptionInformation.getProportion();
//                            }
//                        }
                            for (SubscriptionInformation subscriptionInformation : subscriptionInformations) {
                                if (subscriptionInformation.getRevenueExpenditure() == 1){
                                    divide = divide +  transactionprice * subscriptionInformation.getProportion();
                                }else{
                                    divide = divide + (transactionprice - (transactionprice * subscriptionInformation.getProportion()));
                                }

                            }

                        }else if (subscriptionInformations.size()==1){
                            //流水-分成
//                        divide = transactionprice -  transactionprice * subscriptionInformations.get(0).getProportion();
                            if(subscriptionInformations.get(0).getRevenueExpenditure() == 1){
                                divide = transactionprice * subscriptionInformations.get(0).getProportion();
                            }else{
                                divide =  transactionprice - (transactionprice * subscriptionInformations.get(0).getProportion());
                            }

                        }else{
                            divide = transactionprice;
                        }
                        //机-人配比
                        Double robotPeopleRatio=0d;
                        List<SysScenicSpotOperationRules> sysScenicSpotOperationRules = sysScenicSpotOperationRulesMapper.getOperationRulesBySpotId(sysScenicSpot.getScenicSpotId());
                        if (robotList.size()!=0){
                            robotPeopleRatio = robotList.size() / Double.parseDouble(sysScenicSpotOperationRules.get(0).getOperatePeople()) ;
                        }

                        //毛利 同公司所得分润金额

                        //净利润
                        Double netProfit =Double.parseDouble(df.format(divide - sum - sumPrice)) ;

                        //台均毛利
                        Double robotGrossProfit = divide / robotList.size();
                        if (!StringUtils.isEmpty(robotGrossProfit) && robotGrossProfit !=0){
                            robotGrossProfit = Double.parseDouble(df.format(robotGrossProfit));
                        }
                        //毛利同比
                        //去年毛利

                        Double aDouble = this.grossProfitForTheSamePeriod(sysScenicSpot.getScenicSpotId(), startTime, endTime,type);
                        Double yearOnYear = 0d;
                        if (aDouble != 0){
                            yearOnYear = (divide-aDouble)/Math.abs(aDouble)*1.0;
                        }


                        //毛利环比
                        //上期收入
                        Double aDouble1 = this.currentGrossProfit(sysScenicSpot.getScenicSpotId(), startTime, endTime, type);
                        Double monthOnMonth =0d;
                        if (aDouble1 != 0){
                            monthOnMonth = (divide-aDouble1)/Math.abs(aDouble1)*1.0;
                        }

                        //毛利率
                        Double grossProfitMargin = 0d;
                        if (divide == 0 || transactionprice == 0){
                            grossProfitMargin = 0d;
                        }else{
                            grossProfitMargin =Double.parseDouble(df.format(divide / transactionprice ))  ;
                        }
                        //净利率
                        Double netInterestRate = 0d;

                        if (netProfit == 0 || transactionprice == 0){
                            netInterestRate = 0d;
                        }else{
                            netInterestRate = Double.parseDouble(df.format(netProfit / transactionprice))   ;
                        }


                        robotOperateGrossProfit = new RobotOperateGrossProfit();
                        robotOperateGrossProfit.setSpotId(sysScenicSpot.getScenicSpotId());
                        robotOperateGrossProfit.setSpotName(sysScenicSpot.getScenicSpotName());
                        robotOperateGrossProfit.setCycleTime(startTime +"-"+endTime);
                        robotOperateGrossProfit.setTransactionprice(transactionprice.toString());//交易金额
                        robotOperateGrossProfit.setOperationDuration(operationDuration.toString());//运营时长
                        robotOperateGrossProfit.setAveragePrice(averagePrice.toString());//平均单台交易金额
                        robotOperateGrossProfit.setAverageTime(averageTime.toString());//平均运营时长
                        robotOperateGrossProfit.setCost(sum.toString());//机器人折旧金额
                        robotOperateGrossProfit.setSumPrice(sumPrice);//综合成本
                        robotOperateGrossProfit.setComprehensiveCost(comprehensiveCost.toString());//台均和成
                        robotOperateGrossProfit.setDivide(divide.toString());//公司所得分润金额
                        robotOperateGrossProfit.setRobotPeopleRatio(robotPeopleRatio.toString());//机-人配比
                        robotOperateGrossProfit.setGrossProfit(divide.toString()); //公司所得分润金额
                        robotOperateGrossProfit.setNetProfit(netProfit.toString());//净利润
                        robotOperateGrossProfit.setRobotGrossProfit(robotGrossProfit.toString());//台均毛利
                        robotOperateGrossProfit.setYearOnYear(yearOnYear.toString());//同比
                        robotOperateGrossProfit.setMonthOnMonth(monthOnMonth.toString());//环比
                        robotOperateGrossProfit.setGrossProfitMargin(grossProfitMargin.toString());//毛利率
                        robotOperateGrossProfit.setNetInterestRate(netInterestRate.toString());//净利率
                        list.add(robotOperateGrossProfit);

                    }
                }
            }else{//根据景区查询
                SysScenicSpot sysScenicSpot = sysScenicSpotMapper.selectByScenicSpotId(spotId);
                List<SysRobot> robotList = sysRobotMapper.getRobotListByScenicSpotId(sysScenicSpot.getScenicSpotId());
                if (robotList.size()<=0){
                    //运营成本(运营人员成本、景区营销成本、租金、维养成本(均显示总值))
                    Double sumPrice = sysScenicSpotTargetAmountMapper.getTargetAmountGpmList(sysScenicSpot.getScenicSpotId(),startTime,endTime);
                    robotOperateGrossProfit = new RobotOperateGrossProfit();

                    robotOperateGrossProfit.setSpotId(sysScenicSpot.getScenicSpotId());
                    robotOperateGrossProfit.setSpotName(sysScenicSpot.getScenicSpotName());
                    robotOperateGrossProfit.setCycleTime(startTime +"-"+endTime);
                    robotOperateGrossProfit.setTransactionprice("0");
                    robotOperateGrossProfit.setOperationDuration("0");
                    robotOperateGrossProfit.setAveragePrice("0");
                    robotOperateGrossProfit.setAverageTime("0");
                    robotOperateGrossProfit.setCost("0");
                    robotOperateGrossProfit.setSumPrice(StringUtils.isEmpty(sumPrice) ? 0:sumPrice);

                    robotOperateGrossProfit.setComprehensiveCost("0");

                    robotOperateGrossProfit.setDivide("0");

                    robotOperateGrossProfit.setRobotPeopleRatio("0");

                    robotOperateGrossProfit.setGrossProfit("0");

                    robotOperateGrossProfit.setNetProfit("0");

                    robotOperateGrossProfit.setRobotGrossProfit("0");

                    robotOperateGrossProfit.setYearOnYear(null);

                    robotOperateGrossProfit.setMonthOnMonth(null);

                    robotOperateGrossProfit.setGrossProfitMargin("0");

                    robotOperateGrossProfit.setNetInterestRate("0");
                    list.add(robotOperateGrossProfit);

                    return list;
                }

                if (type ==1 ){ //年查询
                    //交易金额
                    //需将结束时间向后加一年（按年查询的）
                    Long endTimeN = Long.parseLong(endTime)+1;
                    Double transactionprice =  sysOrderMapper.getSpotIdAndTimeByIncome(sysScenicSpot.getScenicSpotId(),startTime,endTimeN.toString());
                    if (StringUtils.isEmpty(transactionprice)){
                        transactionprice = 0d;
                    }else{
                        transactionprice = Double.parseDouble(df.format(transactionprice));
                    }
                    //运营时长
                    Double operationDuration =  sysOrderMapper.getSpotIdAndTimeByOperationDuration(sysScenicSpot.getScenicSpotId(),startTime,endTimeN.toString());
                    if (!StringUtils.isEmpty(operationDuration) && operationDuration != 0){
                        operationDuration = Double.parseDouble(df.format(operationDuration)) ;
                    }
                    //平均单台交易金额
                    Double  averagePrice =0d;
                    Long count = 0l;
                    if (!StringUtils.isEmpty(operationDuration) && operationDuration != 0){
                        count = sysRobotMapper.getSpotIdByRobotCount(sysScenicSpot.getScenicSpotId());
                        averagePrice = Double.parseDouble(df.format(transactionprice / count)) ;

                    }
                    //平均运营时长
                    Double averageTime = 0d;
                    if (!StringUtils.isEmpty(operationDuration) && operationDuration != 0){
                        averageTime = Double.parseDouble(df.format(operationDuration / count)) ;

                    }



                    //获取景区机器人，获取折旧金额
//                    List<SysRobot> robotList = sysRobotMapper.getRobotList(search);
                    Double sum = 0d;


                    for (SysRobot sysRobot : robotList) {
                        SysRobotSoftAssetInformation robotSoftAssetInformationByRobot = sysRobotSoftAssetInformationMapper.getRobotSoftAssetInformationByRobotId(sysRobot.getRobotId());
                        if (StringUtils.isEmpty(robotSoftAssetInformationByRobot)){
                            //暂时跳过
                            continue;
//                            throw new Exception(sysRobot.getRobotCode()+ "机器人无软资产信息");
                        }
                        if (StringUtils.isEmpty(robotSoftAssetInformationByRobot.getNetPrice()) || robotSoftAssetInformationByRobot.getNetPrice() <= 0){
                            continue;
                        }
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        Calendar cal = Calendar.getInstance();
                        cal.setTime(sdf.parse(robotSoftAssetInformationByRobot.getDateProduction()));
                        cal.add(Calendar.YEAR,3);
                        String format = sdf.format(cal.getTime());
                        String year = format.substring(0, 4);
                        System.out.println(format);
//
                        if (StringUtils.isEmpty(startTime) && StringUtils.isEmpty(endTime)){
                            sum += robotSoftAssetInformationByRobot.getAccumulate();
                        }else{
                            String dateProduction = robotSoftAssetInformationByRobot.getDateProduction();

                            //
                            if (0 < startTime.compareTo(year) || 0 > endTime.compareTo(dateProduction.substring(0,4))){
                                //不在
                                continue;
                            }else if(0 >= endTime.compareTo(year) && 0 >= dateProduction.compareTo(startTime) ){
                                //在成本时间内
//                                    String months = DateUtil.findYears(startTime, endTime);
                                Long  months = (Long.parseLong(endTime) - Long.parseLong(startTime))+1;

                                Double price =  (Double.parseDouble(robotSoftAssetInformationByRobot.getFactoryCost()) / (Double.parseDouble(robotSoftAssetInformationByRobot.getServiceLife()) * 12)) * (months * 12);
                                sum += price;
                            }else if ( 0 >= startTime.compareTo(dateProduction) && 0 >= endTime.compareTo(year)){
                                //少于生产时间的时间段
//                                    String months =  DateUtil.findYears(dateProduction, endTime);
                                String substring = dateProduction.substring(0, 7);

                                String endTimeEnd = format.substring(0,7);
                                String months = DateUtil.findMonths(substring, endTimeEnd);
                                if ("1".equals(months)){
                                    months = "2";
                                }
                                Double price =  (Double.parseDouble(robotSoftAssetInformationByRobot.getFactoryCost()) / (Double.parseDouble(robotSoftAssetInformationByRobot.getServiceLife()) * 12)) * (Double.parseDouble(months)-1);
                                sum += price;
                            }else if ( 0 <= startTime.compareTo(dateProduction) && 0 <= endTime.compareTo(year)){


                                startTime = startTime + "-01";

                                String substring = format.substring(0, 7);
                                String months = DateUtil.findMonths(substring, endTime);
                                if ("1".equals(months)){
                                    months = "2";
                                }
                                Double price =  (Double.parseDouble(robotSoftAssetInformationByRobot.getFactoryCost()) / (Double.parseDouble(robotSoftAssetInformationByRobot.getServiceLife()) * 12)) * (Double.parseDouble(months)-1);
                                sum += price;
                            }

                        }
                    }
                    //运营成本(运营人员成本、景区营销成本、租金、维养成本(均显示总值))
                    Double sumPrice = sysScenicSpotTargetAmountMapper.getTargetAmountGpmList(sysScenicSpot.getScenicSpotId(),startTime,endTimeN.toString());
                    if (StringUtils.isEmpty(sumPrice)){
                        sumPrice = 0d;
                    }
                    //台均和成本
                    Double comprehensiveCost = Double.parseDouble(df.format((sum + sumPrice) / robotList.size())) ;
                    if (StringUtils.isEmpty(comprehensiveCost)){
                        comprehensiveCost = 0d;
                    }
                    //公司所得分润金额
                    Double divide = 0d;
                    Map jxzyCompanyId =  subscriptionInformationMapper.selectJxzyCompanyId();
                    Object companyd = jxzyCompanyId.get("COMPANY_ID");
                    List<SubscriptionInformation> subscriptionInformations = subscriptionInformationMapper.selectSpotIdByContract(sysScenicSpot.getScenicSpotId());
                    if (subscriptionInformations.size()>1){
                        //分成
//                        for (SubscriptionInformation subscriptionInformation : subscriptionInformations) {
//                            if (subscriptionInformation.getCompanyId().equals(companyd)){
//                                divide =  transactionprice * subscriptionInformation.getProportion();
//                            }
//                        }
                        for (SubscriptionInformation subscriptionInformation : subscriptionInformations) {
                            if (subscriptionInformation.getRevenueExpenditure() == 1){
                                divide = divide +  transactionprice * subscriptionInformation.getProportion();
                            }else{
                                divide = divide + (transactionprice - (transactionprice * subscriptionInformation.getProportion()));
                            }

                        }

                    }else if (subscriptionInformations.size()==1){
                        //流水-分成
//                        divide = transactionprice -  transactionprice * subscriptionInformations.get(0).getProportion();
                        if(subscriptionInformations.get(0).getRevenueExpenditure() == 1){
                            divide = transactionprice * subscriptionInformations.get(0).getProportion();
                        }else{
                            divide =  transactionprice - (transactionprice * subscriptionInformations.get(0).getProportion());
                        }

                    }else{
                        divide = transactionprice;
                    }
                    //机-人配比
                    Double robotPeopleRatio=0d;
                    List<SysScenicSpotOperationRules> sysScenicSpotOperationRules = sysScenicSpotOperationRulesMapper.getOperationRulesBySpotId(sysScenicSpot.getScenicSpotId());
                    if (robotList.size()!=0){
                        robotPeopleRatio = robotList.size() / Double.parseDouble(sysScenicSpotOperationRules.get(0).getOperatePeople()) ;
                    }

                    //毛利 同公司所得分润金额

                    //净利润
                    Double netProfit = divide - sum - sumPrice;

                    //台均毛利
                    Double robotGrossProfit = divide / robotList.size();
                    if (StringUtils.isEmpty(robotGrossProfit) && robotGrossProfit != 0){
                        robotGrossProfit = Double.parseDouble(df.format(robotGrossProfit));
                    }
                    //毛利同比
                    Double aDouble = this.grossProfitForTheSamePeriod(sysScenicSpot.getScenicSpotId(), startTime, endTime,type);
                    Double yearOnYear = 0d;
                    if (aDouble != 0){
                        yearOnYear = Double.parseDouble(df.format((divide-aDouble) / Math.abs(aDouble)*1.0)) ;
                    }


                    //毛利环比
                    Double aDouble1 = this.currentGrossProfit(sysScenicSpot.getScenicSpotId(), startTime, endTime, type);
                    Double monthOnMonth = 0d;
                    if (aDouble1 != 0){
                        monthOnMonth =Double.parseDouble(df.format((divide-aDouble1)/Math.abs(aDouble1)*1.0)) ;
                    }


                    //毛利率
                    Double grossProfitMargin = 0d ;
                    if (divide == 0 || transactionprice == 0){
                        grossProfitMargin = 0d;
                    }else{
                        grossProfitMargin = Double.parseDouble(df.format(divide / transactionprice))   ;
                    }
                    //净利率
                    Double netInterestRate = 0d;
                    if (netProfit == 0 || transactionprice == 0){
                        netInterestRate = 0d;
                    }else{
                        netInterestRate =Double.parseDouble(df.format(netProfit / transactionprice))   ;
                    }
                    robotOperateGrossProfit = new RobotOperateGrossProfit();
                    robotOperateGrossProfit.setSpotId(sysScenicSpot.getScenicSpotId());
                    robotOperateGrossProfit.setSpotName(sysScenicSpot.getScenicSpotName());
                    robotOperateGrossProfit.setCycleTime(startTime +"-"+endTime);
                    robotOperateGrossProfit.setTransactionprice(transactionprice.toString());//交易金额
                    robotOperateGrossProfit.setOperationDuration(operationDuration.toString());//运营时长
                    robotOperateGrossProfit.setAveragePrice(averagePrice.toString());//平均单台交易金额
                    robotOperateGrossProfit.setAverageTime(averageTime.toString());//平均运营时长
                    robotOperateGrossProfit.setCost(sum.toString());//机器人折旧金额
                    robotOperateGrossProfit.setSumPrice(sumPrice);//综合成本
                    robotOperateGrossProfit.setComprehensiveCost(comprehensiveCost.toString());//台均和成
                    robotOperateGrossProfit.setDivide(divide.toString());//公司所得分润金额
                    robotOperateGrossProfit.setRobotPeopleRatio(robotPeopleRatio.toString());//机-人配比
                    robotOperateGrossProfit.setGrossProfit(divide.toString()); //公司所得分润金额
                    robotOperateGrossProfit.setNetProfit(netProfit.toString());//净利润
                    robotOperateGrossProfit.setRobotGrossProfit(robotGrossProfit.toString());//台均毛利
                    robotOperateGrossProfit.setYearOnYear(yearOnYear.toString());//同比
                    robotOperateGrossProfit.setMonthOnMonth(monthOnMonth.toString());//环比
                    robotOperateGrossProfit.setGrossProfitMargin(grossProfitMargin.toString());//毛利率
                    robotOperateGrossProfit.setNetInterestRate(netInterestRate.toString());//净利率
                    list.add(robotOperateGrossProfit);

                }else if (type == 2){//按月查询
                    //交易金额
                    String endTimeN = DateUtil.addMouth(endTime, 1);
                    Double transactionprice =  sysOrderMapper.getSpotIdAndTimeByIncome(sysScenicSpot.getScenicSpotId(),startTime,endTimeN);
                    if (StringUtils.isEmpty(transactionprice)){
                        transactionprice = 0d;
                    }else{
                        transactionprice = Double.parseDouble(df.format(transactionprice));
                    }
                    //运营时长
                    Double operationDuration =  sysOrderMapper.getSpotIdAndTimeByOperationDuration(sysScenicSpot.getScenicSpotId(),startTime,endTimeN);
                    if (!StringUtils.isEmpty(operationDuration) && operationDuration != 0){
                        operationDuration = Double.parseDouble(df.format(operationDuration)) ;
                    }
                    //平均单台交易金额
                    Double  averagePrice =0d;
                    Long count = 0l;
                    if (!StringUtils.isEmpty(operationDuration) && operationDuration != 0){
                        count = sysRobotMapper.getSpotIdByRobotCount(sysScenicSpot.getScenicSpotId());
                        averagePrice = Double.parseDouble(df.format(transactionprice / count)) ;

                    }
                    //平均运营时长
                    Double averageTime = 0d;
                    if (!StringUtils.isEmpty(operationDuration) && operationDuration != 0){
                        averageTime = Double.parseDouble(df.format(operationDuration / count)) ;

                    }
                    //获取景区机器人，获取折旧金额
//                    List<SysRobot> robotList = sysRobotMapper.getRobotList(search);
                    Double sum = 0d;
                    for (SysRobot sysRobot : robotList) {
                        SysRobotSoftAssetInformation robotSoftAssetInformationByRobot = sysRobotSoftAssetInformationMapper.getRobotSoftAssetInformationByRobotId(sysRobot.getRobotId());
                        if (StringUtils.isEmpty(robotSoftAssetInformationByRobot)){
                            //暂时跳过
                            continue;
//                            throw new Exception(sysRobot.getRobotCode()+ "机器人无软资产信息");
                        }
                        if (StringUtils.isEmpty(robotSoftAssetInformationByRobot.getNetPrice()) || robotSoftAssetInformationByRobot.getNetPrice() <= 0){
                            continue;
                        }
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        Calendar cal = Calendar.getInstance();
                        cal.setTime(sdf.parse(robotSoftAssetInformationByRobot.getDateProduction()));
                        cal.add(Calendar.YEAR,3);
                        String format = sdf.format(cal.getTime());
                        System.out.println(format);
//
                        if (StringUtils.isEmpty(startTime) && StringUtils.isEmpty(endTime)){
                            sum += robotSoftAssetInformationByRobot.getAccumulate();
                        }else{
                            String dateProduction = robotSoftAssetInformationByRobot.getDateProduction();

                            if (0 >= endTime.compareTo(format) && 0 >= dateProduction.compareTo(startTime)){
                                //在成本时间内
                                String months = DateUtil.findMonths(startTime, endTime);
                                if ("1".equals(months)){
                                    months = "2";
                                }
                                Double price =  (Double.parseDouble(robotSoftAssetInformationByRobot.getFactoryCost()) / (Double.parseDouble(robotSoftAssetInformationByRobot.getServiceLife()) * 12)) * (Double.parseDouble(months)-1);
                                sum += price;

                            }else if( 0 < startTime.compareTo(format) || 0 > endTime.compareTo(dateProduction)){
                                //不在
                                continue;
                            }else if ( 0 <= endTime.compareTo(dateProduction) && 0 >= endTime.compareTo(format)){
                                //
                                String months =  DateUtil.findMonths(dateProduction, endTime);
                                if ("1".equals(months)){
                                    months = "2";
                                }
                                Double price =  (Double.parseDouble(robotSoftAssetInformationByRobot.getFactoryCost()) / (Double.parseDouble(robotSoftAssetInformationByRobot.getServiceLife()) * 12)) * (Double.parseDouble(months)-1);
                                sum += price;
                            }else if ( 0 <= startTime.compareTo(dateProduction) && 0 >= startTime.compareTo(format)){

                                String months =  DateUtil.findMonths(startTime, format);
                                if ("1".equals(months)){
                                    months = "2";
                                }
                                Double price =  (Double.parseDouble(robotSoftAssetInformationByRobot.getFactoryCost()) / (Double.parseDouble(robotSoftAssetInformationByRobot.getServiceLife()) * 12)) * (Double.parseDouble(months)-1);
                                sum += price;
                            }else  {

                                String months =  DateUtil.findMonths(dateProduction, format);
                                if ("1".equals(months)){
                                    months = "2";
                                }
                                Double price =  (Double.parseDouble(robotSoftAssetInformationByRobot.getFactoryCost()) / (Double.parseDouble(robotSoftAssetInformationByRobot.getServiceLife()) * 12)) * (Double.parseDouble(months)-1);
                                sum += price;
                            }
                        }
                    }
                    //运营成本(运营人员成本、景区营销成本、租金、维养成本(均显示总值))
                    Double sumPrice = sysScenicSpotTargetAmountMapper.getTargetAmountGpmList(sysScenicSpot.getScenicSpotId(),startTime,endTimeN);
                    if (StringUtils.isEmpty(sumPrice)){
                        sumPrice = 0d;
                    }

                    //台均和成本
                    Double comprehensiveCost = Double.parseDouble(df.format((sum + sumPrice) / robotList.size())) ;
                    if (StringUtils.isEmpty(comprehensiveCost)){
                        comprehensiveCost = 0d;
                    }
                    //公司所得分润金额
                    Double divide = 0d;
                    Map jxzyCompanyId =  subscriptionInformationMapper.selectJxzyCompanyId();
                    Object companyd = jxzyCompanyId.get("COMPANY_ID");
                    List<SubscriptionInformation> subscriptionInformations = subscriptionInformationMapper.selectSpotIdByContract(sysScenicSpot.getScenicSpotId());
                    if (subscriptionInformations.size()>1){
                        //分成
//                        for (SubscriptionInformation subscriptionInformation : subscriptionInformations) {
//                            if (subscriptionInformation.getCompanyId().equals(companyd)){
//                                divide =  transactionprice * subscriptionInformation.getProportion();
//                            }
//                        }
                        for (SubscriptionInformation subscriptionInformation : subscriptionInformations) {
                            if (subscriptionInformation.getRevenueExpenditure() == 1){
                                divide = divide +  transactionprice * subscriptionInformation.getProportion();
                            }else{
                                divide = divide + (transactionprice - (transactionprice * subscriptionInformation.getProportion()));
                            }

                        }

                    }else if (subscriptionInformations.size()==1){
                        //流水-分成
//                        divide = transactionprice -  transactionprice * subscriptionInformations.get(0).getProportion();
                        if(subscriptionInformations.get(0).getRevenueExpenditure() == 1){
                            divide = transactionprice * subscriptionInformations.get(0).getProportion();
                        }else{
                            divide =  transactionprice - (transactionprice * subscriptionInformations.get(0).getProportion());
                        }

                    }else{
                        divide = transactionprice;
                    }
                    //机-人配比
                    Double robotPeopleRatio=0d;
                    List<SysScenicSpotOperationRules> sysScenicSpotOperationRules = sysScenicSpotOperationRulesMapper.getOperationRulesBySpotId(sysScenicSpot.getScenicSpotId());
                    if (robotList.size()!=0){
                        robotPeopleRatio = robotList.size() / Double.parseDouble(sysScenicSpotOperationRules.get(0).getOperatePeople()) ;
                    }

                    //毛利 同公司所得分润金额
                    //净利润
                    Double netProfit = Double.parseDouble(df.format(divide - sum - sumPrice)) ;

                    //台均毛利
                    Double robotGrossProfit = Double.parseDouble(df.format( divide / robotList.size()));
                    if (!StringUtils.isEmpty(robotGrossProfit) && robotGrossProfit != 0){
                        robotGrossProfit = Double.parseDouble(df.format(robotGrossProfit));
                    }
                    //毛利同比
                    Double aDouble = this.grossProfitForTheSamePeriod(sysScenicSpot.getScenicSpotId(), startTime, endTime,type);
                    Double yearOnYear =0d;
                    if (aDouble !=0){
                        yearOnYear =Double.parseDouble(df.format((divide-aDouble) / Math.abs(aDouble)*1.0)) ;
                    }

                    //毛利环比
                    Double aDouble1 = this.currentGrossProfit(sysScenicSpot.getScenicSpotId(), startTime, endTime, type);
                    Double monthOnMonth = 0d;
                    if (aDouble1 != 0){
                        monthOnMonth =Double.parseDouble(df.format((divide-aDouble1)/Math.abs(aDouble1)*1.0)) ;
                    }


                    //毛利率
                    Double grossProfitMargin = 0d ;
                    if (divide == 0 || transactionprice == 0){
                        grossProfitMargin = 0d;
                    }else{
                        grossProfitMargin =Double.parseDouble(df.format(divide / transactionprice))   ;
                    }
                    //净利率
                    Double netInterestRate = 0d;
                    if (netProfit == 0 || transactionprice == 0){
                        netInterestRate = 0d;
                    }else{
                        netInterestRate =Double.parseDouble(df.format(netProfit / transactionprice))   ;
                    }

                    robotOperateGrossProfit = new RobotOperateGrossProfit();
                    robotOperateGrossProfit.setSpotId(sysScenicSpot.getScenicSpotId());
                    robotOperateGrossProfit.setSpotName(sysScenicSpot.getScenicSpotName());
                    robotOperateGrossProfit.setCycleTime(startTime +"-"+endTime);
                    robotOperateGrossProfit.setTransactionprice(transactionprice.toString());//交易金额
                    robotOperateGrossProfit.setOperationDuration(operationDuration.toString());//运营时长
                    robotOperateGrossProfit.setAveragePrice(averagePrice.toString());//平均单台交易金额
                    robotOperateGrossProfit.setAverageTime(averageTime.toString());//平均运营时长
                    robotOperateGrossProfit.setCost(sum.toString());//机器人折旧金额
                    robotOperateGrossProfit.setSumPrice(sumPrice);//综合成本
                    robotOperateGrossProfit.setComprehensiveCost(comprehensiveCost.toString());//台均和成
                    robotOperateGrossProfit.setDivide(divide.toString());//公司所得分润金额
                    robotOperateGrossProfit.setRobotPeopleRatio(robotPeopleRatio.toString());//机-人配比
                    robotOperateGrossProfit.setGrossProfit(divide.toString()); //公司所得分润金额
                    robotOperateGrossProfit.setNetProfit(netProfit.toString());//净利润
                    robotOperateGrossProfit.setRobotGrossProfit(robotGrossProfit.toString());//台均毛利
                    robotOperateGrossProfit.setYearOnYear(yearOnYear.toString());//同比
                    robotOperateGrossProfit.setMonthOnMonth(monthOnMonth.toString());//环比
                    robotOperateGrossProfit.setGrossProfitMargin(grossProfitMargin.toString());//毛利率
                    robotOperateGrossProfit.setNetInterestRate(netInterestRate.toString());//净利率
                    list.add(robotOperateGrossProfit);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        if (StringUtils.isEmpty(spotId)){
            if (!StringUtils.isEmpty(sort)){
                String[] split = sort.split(",");
                String sortType = split[0];
                String sortN = split[1];
                if (sortN.equals("desc")){
                    if (sortType.equals("1")){
                        //按毛利降序序排列
                        Collections.sort(list, new Comparator<RobotOperateGrossProfit>() {
                            public int compare(RobotOperateGrossProfit o1, RobotOperateGrossProfit o2) {
                                Double aDouble = new Double(o1.getDivide()) ;
                                Double bDouble = new Double(o2.getDivide()) ;
                                return bDouble.compareTo(aDouble);
                            }
                        });
                    }else if (sortType.equals("2")){
                        //按流水降序序排列
                        Collections.sort(list, new Comparator<RobotOperateGrossProfit>() {
                            public int compare(RobotOperateGrossProfit o1, RobotOperateGrossProfit o2) {
                                Double aDouble = new Double(o1.getTransactionprice()) ;
                                Double bDouble = new Double(o2.getTransactionprice()) ;
                                return bDouble.compareTo(aDouble);
                            }
                        });
                    }else if (sortType.equals("3")){
                        //台均综合成本
                        Collections.sort(list, new Comparator<RobotOperateGrossProfit>() {
                            public int compare(RobotOperateGrossProfit o1, RobotOperateGrossProfit o2) {
                                Double aDouble = new Double(o1.getComprehensiveCost()) ;
                                Double bDouble = new Double(o2.getComprehensiveCost()) ;
                                return bDouble.compareTo(aDouble);
                            }
                        });
                    }else if (sortType.equals("4")){
                        //台均毛利
                        Collections.sort(list, new Comparator<RobotOperateGrossProfit>() {
                            public int compare(RobotOperateGrossProfit o1, RobotOperateGrossProfit o2) {
                                Double aDouble = new Double(o1.getRobotGrossProfit()) ;
                                Double bDouble = new Double(o2.getRobotGrossProfit()) ;
                                return bDouble.compareTo(aDouble);
                            }
                        });
                    }else if (sortType.equals("5")){
                        //净利润
                        Collections.sort(list, new Comparator<RobotOperateGrossProfit>() {
                            public int compare(RobotOperateGrossProfit o1, RobotOperateGrossProfit o2) {
                                Double aDouble = new Double(o1.getNetProfit()) ;
                                Double bDouble = new Double(o2.getNetProfit()) ;
                                return bDouble.compareTo(aDouble);
                            }
                        });
                    }
                }else if (sortN.equals("asc")){
                    if (sortType.equals("1")){
                        //按毛利降序序排列
                        Collections.sort(list, new Comparator<RobotOperateGrossProfit>() {
                            public int compare(RobotOperateGrossProfit o1, RobotOperateGrossProfit o2) {
                                Double aDouble = new Double(o1.getDivide()) ;
                                Double bDouble = new Double(o2.getDivide()) ;
                                return aDouble.compareTo(bDouble);
                            }
                        });
                    }else if (sortType.equals("2")){
                        //按流水降序序排列
                        Collections.sort(list, new Comparator<RobotOperateGrossProfit>() {
                            public int compare(RobotOperateGrossProfit o1, RobotOperateGrossProfit o2) {
                                Double aDouble = new Double(o1.getTransactionprice()) ;
                                Double bDouble = new Double(o2.getTransactionprice()) ;
                                return aDouble.compareTo(bDouble);
                            }
                        });
                    }else if (sortType.equals("3")){
                        //台均综合成本
                        Collections.sort(list, new Comparator<RobotOperateGrossProfit>() {
                            public int compare(RobotOperateGrossProfit o1, RobotOperateGrossProfit o2) {
                                Double aDouble = new Double(o1.getComprehensiveCost()) ;
                                Double bDouble = new Double(o2.getComprehensiveCost()) ;
                                return aDouble.compareTo(bDouble);
                            }
                        });
                    }else if (sortType.equals("4")){
                        //台均毛利
                        Collections.sort(list, new Comparator<RobotOperateGrossProfit>() {
                            public int compare(RobotOperateGrossProfit o1, RobotOperateGrossProfit o2) {
                                Double aDouble = new Double(o1.getRobotGrossProfit()) ;
                                Double bDouble = new Double(o2.getRobotGrossProfit()) ;
                                return aDouble.compareTo(bDouble);
                            }
                        });
                    }else if (sortType.equals("5")){
                        //净利润
                        Collections.sort(list, new Comparator<RobotOperateGrossProfit>() {
                            public int compare(RobotOperateGrossProfit o1, RobotOperateGrossProfit o2) {
                                Double aDouble = new Double(o1.getNetProfit()) ;
                                Double bDouble = new Double(o2.getNetProfit()) ;
                                return aDouble.compareTo(bDouble);
                            }
                        });
                    }
                }
            }

        }

        return list;

    }


    //同期毛利  同比
    protected Double grossProfitForTheSamePeriod(Long scenicSpotId,String startTime,String endTime,Long type){
        Double divide = 0d;
        try {
            if (type == 1){//年


                startTime = DateUtil.getLastYearCurrentDateYear(startTime);

                //流水金额
                Double runningAmount =  sysOrderMapper.getSpotIdAndTimeByIncome(scenicSpotId,startTime,endTime);
                if (StringUtils.isEmpty(runningAmount)){
                    runningAmount=0d;
                }


                Map jxzyCompanyId =  subscriptionInformationMapper.selectJxzyCompanyId();
                Object companyd = jxzyCompanyId.get("COMPANY_ID");
                List<SubscriptionInformation> subscriptionInformations = subscriptionInformationMapper.selectSpotIdByContract(scenicSpotId);
                if (subscriptionInformations.size()>1){
                    //分成
//                        for (SubscriptionInformation subscriptionInformation : subscriptionInformations) {
//                            if (subscriptionInformation.getCompanyId().equals(companyd)){
//                                divide =  transactionprice * subscriptionInformation.getProportion();
//                            }
//                        }
                    for (SubscriptionInformation subscriptionInformation : subscriptionInformations) {
                        if (subscriptionInformation.getRevenueExpenditure() == 1){
                            divide = divide +  runningAmount * subscriptionInformation.getProportion();
                        }else{
                            divide = divide + (runningAmount - (runningAmount * subscriptionInformation.getProportion()));
                        }

                    }

                }else if (subscriptionInformations.size()==1){
                    //流水-分成
//                        divide = transactionprice -  transactionprice * subscriptionInformations.get(0).getProportion();
                    if(subscriptionInformations.get(0).getRevenueExpenditure() == 1){
                        divide = runningAmount * subscriptionInformations.get(0).getProportion();
                    }else{
                        divide =  runningAmount - (runningAmount * subscriptionInformations.get(0).getProportion());
                    }

                }else{
                    divide = runningAmount;
                }
            }else if (type ==2){//月

                endTime = DateUtil.addMouth(endTime,1);
                String lastYearCurrentStartDate = DateUtil.getLastYearCurrentDate(startTime);
                String lastYearCurrentEndDate =   DateUtil.getLastYearCurrentDate(endTime);
                //流水金额
                Double runningAmount =  sysOrderMapper.getSpotIdAndTimeByIncome(scenicSpotId,lastYearCurrentStartDate,lastYearCurrentEndDate);
                if (StringUtils.isEmpty(runningAmount)){
                    runningAmount=0d;
                }
                Map jxzyCompanyId =  subscriptionInformationMapper.selectJxzyCompanyId();
                Object companyd = jxzyCompanyId.get("COMPANY_ID");
                List<SubscriptionInformation> subscriptionInformations = subscriptionInformationMapper.selectSpotIdByContract(scenicSpotId);
                if (subscriptionInformations.size()>1){
                    //分成
//                        for (SubscriptionInformation subscriptionInformation : subscriptionInformations) {
//                            if (subscriptionInformation.getCompanyId().equals(companyd)){
//                                divide =  transactionprice * subscriptionInformation.getProportion();
//                            }
//                        }
                    for (SubscriptionInformation subscriptionInformation : subscriptionInformations) {
                        if (subscriptionInformation.getRevenueExpenditure() == 1){
                            divide = divide +  runningAmount * subscriptionInformation.getProportion();
                        }else{
                            divide = divide + (runningAmount - (runningAmount * subscriptionInformation.getProportion()));
                        }

                    }
                }else if (subscriptionInformations.size()==1){
                    //流水-分成
//                        divide = transactionprice -  transactionprice * subscriptionInformations.get(0).getProportion();
                    if(subscriptionInformations.get(0).getRevenueExpenditure() == 1){
                        divide = runningAmount * subscriptionInformations.get(0).getProportion();
                    }else{
                        divide =  runningAmount - (runningAmount * subscriptionInformations.get(0).getProportion());
                    }
                }else{
                    divide = runningAmount;
                }
            }else if (type == 3){
                String lastYearCurrentStartDate = DateUtil.getLastYearCurrentDateDay(startTime);
                String lastYearCurrentEndDate = DateUtil.getLastYearCurrentDateDay(endTime);

                //流水金额
                Double runningAmount =  sysOrderMapper.getSpotIdAndTimeByIncome(scenicSpotId,lastYearCurrentStartDate,lastYearCurrentEndDate);
                if (StringUtils.isEmpty(runningAmount)){
                    runningAmount=0d;
                }

                Map jxzyCompanyId =  subscriptionInformationMapper.selectJxzyCompanyId();
                Object companyd = jxzyCompanyId.get("COMPANY_ID");
                List<SubscriptionInformation> subscriptionInformations = subscriptionInformationMapper.selectSpotIdByContract(scenicSpotId);
                if (subscriptionInformations.size()>1){
                    //分成
//                        for (SubscriptionInformation subscriptionInformation : subscriptionInformations) {
//                            if (subscriptionInformation.getCompanyId().equals(companyd)){
//                                divide =  transactionprice * subscriptionInformation.getProportion();
//                            }
//                        }
                    for (SubscriptionInformation subscriptionInformation : subscriptionInformations) {
                        if (subscriptionInformation.getRevenueExpenditure() == 1){
                            divide = divide +  runningAmount * subscriptionInformation.getProportion();
                        }else{
                            divide = divide + (runningAmount - (runningAmount * subscriptionInformation.getProportion()));
                        }

                    }

                }else if (subscriptionInformations.size()==1){
                    //流水-分成
//                        divide = transactionprice -  transactionprice * subscriptionInformations.get(0).getProportion();
                    if(subscriptionInformations.get(0).getRevenueExpenditure() == 1){
                        divide = runningAmount * subscriptionInformations.get(0).getProportion();
                    }else{
                        divide =  runningAmount - (runningAmount * subscriptionInformations.get(0).getProportion());
                    }

                }else{
                    divide = runningAmount;
                }

            }

        }catch (Exception e){
            e.printStackTrace();
        }

        return divide;

    }

    //本期毛利 环比
    protected Double currentGrossProfit(Long scenicSpotId,String startTime,String endTime, Long type){
        Double divide = 0d;
        try {
            if (type==1){//年
                Long i = Long.parseLong( endTime) - Long.parseLong(startTime) ;
                startTime =  String.valueOf((Long.parseLong(startTime) - (i+1)));
                endTime  =    String.valueOf((Long.parseLong(endTime) - (i)));

                startTime = startTime + "-01";
                endTime = endTime + "-01";

                //流水金额
                Double runningAmount =  sysOrderMapper.getSpotIdAndTimeByIncome(scenicSpotId,startTime,endTime);
                if (StringUtils.isEmpty(runningAmount)){
                    runningAmount=0d;
                }

                Map jxzyCompanyId =  subscriptionInformationMapper.selectJxzyCompanyId();
                Object companyd = jxzyCompanyId.get("COMPANY_ID");
                List<SubscriptionInformation> subscriptionInformations = subscriptionInformationMapper.selectSpotIdByContract(scenicSpotId);
                if (subscriptionInformations.size()>1){
                    //分成
//                        for (SubscriptionInformation subscriptionInformation : subscriptionInformations) {
//                            if (subscriptionInformation.getCompanyId().equals(companyd)){
//                                divide =  transactionprice * subscriptionInformation.getProportion();
//                            }
//                        }
                    for (SubscriptionInformation subscriptionInformation : subscriptionInformations) {
                        if (subscriptionInformation.getRevenueExpenditure() == 1){
                            divide = divide +  runningAmount * subscriptionInformation.getProportion();
                        }else{
                            divide = divide + (runningAmount - (runningAmount * subscriptionInformation.getProportion()));
                        }

                    }

                }else if (subscriptionInformations.size()==1){
                    //流水-分成
//                        divide = transactionprice -  transactionprice * subscriptionInformations.get(0).getProportion();
                    if(subscriptionInformations.get(0).getRevenueExpenditure() == 1){
                        divide = runningAmount * subscriptionInformations.get(0).getProportion();
                    }else{
                        divide =  runningAmount - (runningAmount * subscriptionInformations.get(0).getProportion());
                    }

                }else{
                    divide = runningAmount;
                }

            }else if (type==2){//月

//                endTime = DateUtil.addMouth(endTime,1);
                String months = DateUtil.findMonths(startTime, endTime);
                endTime =  startTime;
                startTime = DateUtil.getMonth(startTime,Integer.parseInt(months));


                //流水金额
                Double runningAmount =  sysOrderMapper.getSpotIdAndTimeByIncome(scenicSpotId,startTime,endTime);
                if (StringUtils.isEmpty(runningAmount)){
                    runningAmount=0d;
                }

                Map jxzyCompanyId =  subscriptionInformationMapper.selectJxzyCompanyId();
                Object companyd = jxzyCompanyId.get("COMPANY_ID");
                List<SubscriptionInformation> subscriptionInformations = subscriptionInformationMapper.selectSpotIdByContract(scenicSpotId);
                if (subscriptionInformations.size()>1){
                    //分成
//                        for (SubscriptionInformation subscriptionInformation : subscriptionInformations) {
//                            if (subscriptionInformation.getCompanyId().equals(companyd)){
//                                divide =  transactionprice * subscriptionInformation.getProportion();
//                            }
//                        }
                    for (SubscriptionInformation subscriptionInformation : subscriptionInformations) {
                        if (subscriptionInformation.getRevenueExpenditure() == 1){
                            divide = divide +  runningAmount * subscriptionInformation.getProportion();
                        }else{
                            divide = divide + (runningAmount - (runningAmount * subscriptionInformation.getProportion()));
                        }

                    }

                }else if (subscriptionInformations.size()==1){
                    //流水-分成
//                        divide = transactionprice -  transactionprice * subscriptionInformations.get(0).getProportion();
                    if(subscriptionInformations.get(0).getRevenueExpenditure() == 1){
                        divide = runningAmount * subscriptionInformations.get(0).getProportion();
                    }else{
                        divide =  runningAmount - (runningAmount * subscriptionInformations.get(0).getProportion());
                    }

                }else{
                    divide = runningAmount;
                }

            }else if (type == 3){//日
                String dates = DateUtil.findDates(startTime, endTime);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");//格式化一下
                Date startT =sdf.parse(startTime);
                Calendar calendar = Calendar.getInstance();//获取对日期操作的类对象
                calendar.setTime(startT);
                calendar.add(Calendar.DATE, -Integer.parseInt(dates)+1);
                String start = sdf.format(calendar.getTime());

                Date endT =sdf.parse(endTime);
                calendar.setTime(endT);
                calendar.add(Calendar.DATE, -Integer.parseInt(dates));
                String end = sdf.format(calendar.getTime());
                //流水金额
                Double runningAmount =  sysOrderMapper.getSpotIdAndTimeByRunningAmount(scenicSpotId,start,end);
                if (StringUtils.isEmpty(runningAmount)){
                    runningAmount=0d;
                }

                Map jxzyCompanyId =  subscriptionInformationMapper.selectJxzyCompanyId();
                Object companyd = jxzyCompanyId.get("COMPANY_ID");
                List<SubscriptionInformation> subscriptionInformations = subscriptionInformationMapper.selectSpotIdByContract(scenicSpotId);
                if (subscriptionInformations.size()>1){
                    //分成
//                        for (SubscriptionInformation subscriptionInformation : subscriptionInformations) {
//                            if (subscriptionInformation.getCompanyId().equals(companyd)){
//                                divide =  transactionprice * subscriptionInformation.getProportion();
//                            }
//                        }
                    for (SubscriptionInformation subscriptionInformation : subscriptionInformations) {
                        if (subscriptionInformation.getRevenueExpenditure() == 1){
                            divide = divide +  runningAmount * subscriptionInformation.getProportion();
                        }else{
                            divide = divide + (runningAmount - (runningAmount * subscriptionInformation.getProportion()));
                        }

                    }

                }else if (subscriptionInformations.size()==1){
                    //流水-分成
//                        divide = transactionprice -  transactionprice * subscriptionInformations.get(0).getProportion();
                    if(subscriptionInformations.get(0).getRevenueExpenditure() == 1){
                        divide = runningAmount * subscriptionInformations.get(0).getProportion();
                    }else{
                        divide =  runningAmount - (runningAmount * subscriptionInformations.get(0).getProportion());
                    }

                }else{
                    divide = runningAmount;
                }

            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return divide;

    }

}


