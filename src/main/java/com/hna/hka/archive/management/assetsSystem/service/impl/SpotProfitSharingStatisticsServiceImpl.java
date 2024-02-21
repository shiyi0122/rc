package com.hna.hka.archive.management.assetsSystem.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.inject.internal.cglib.core.$RejectModifierPredicate;
import com.hna.hka.archive.management.appSystem.dao.SysRobotErrorRecordsMapper;
import com.hna.hka.archive.management.assetsSystem.dao.*;
import com.hna.hka.archive.management.assetsSystem.model.*;
import com.hna.hka.archive.management.assetsSystem.service.SpotProfitSharingStatisticsService;
import com.hna.hka.archive.management.assetsSystem.service.SysRobotServiceRecordsService;
import com.hna.hka.archive.management.system.dao.SysOrderMapper;
import com.hna.hka.archive.management.system.dao.SysRobotMapper;
import com.hna.hka.archive.management.system.dao.SysScenicSpotMapper;
import com.hna.hka.archive.management.system.model.SysScenicSpot;
import com.hna.hka.archive.management.system.util.DateUtil;
import com.hna.hka.archive.management.system.util.IdUtils;
import com.hna.hka.archive.management.system.util.PageDataResult;
import com.hna.hka.archive.management.system.util.ToolUtil;
import io.swagger.models.auth.In;
import org.apache.shiro.crypto.hash.Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;

/**
 * @Author zhang
 * @Date 2022/4/23 17:56
 * 景区分润统计
 */
@Service
public class SpotProfitSharingStatisticsServiceImpl implements SpotProfitSharingStatisticsService {


    @Autowired
    SpotProfitSharingStatisticsMapper spotProfitSharingStatisticsMapper;
    @Autowired
    SubscriptionInformationMapper subscriptionInformationMapper;
    @Autowired
    SysScenicSpotTargetAmountMapper sysScenicSpotTargetAmountMapper;
    @Autowired
    CooperativeCompanyMapper cooperativeCompanyMapper;
    @Autowired
    SysScenicSpotMapper sysScenicSpotMapper;
    @Autowired
    SysRobotMapper sysRobotMapper;
    @Autowired
    SysOrderMapper sysOrderMapper;
    @Autowired
    SysRobotErrorRecordsMapper sysRobotErrorRecordsMapper;


    /**
     * 景区分润统计列表
     * @param
     * @param
     * @param
     * @param
     * @param
     */

    @Override
    public Integer spotProfitSharingStatistice() {

        int i =0;
        try{
            List<SubscriptionInformation> list = subscriptionInformationMapper.list(null, null, null, null, null, null, null, null);

            SysScenicSpotFenfun sysScenicSpotFenfun = new SysScenicSpotFenfun();
            Map<String, Object> search = new HashMap<>();
            DecimalFormat df = new DecimalFormat("0.00");

            for (SubscriptionInformation subscriptionInformation : list) {
                search = new HashMap<>();
                //合作公司
                Long companyId = subscriptionInformation.getCompanyId();
                String companyName = subscriptionInformation.getCompanyName();

                //合作主体公司
                long subjectIdN = subscriptionInformation.getSubjectId();
                String subjectName = subscriptionInformation.getSubjectName();
               //收款账户
                CooperativeCompany cooperativeCompany = cooperativeCompanyMapper.selectByPrimaryKey(companyId);
                String collectionAccount = cooperativeCompany.getCollectionAccount();


                //景区名称
                long spotIdN = subscriptionInformation.getSpotId();
                String spotName = subscriptionInformation.getSpotName();

                System.out.println(DateUtil.currentDateTime().substring(0,7));
                //月份
                String yearsDate = DateUtil.getYearsDate(DateUtil.currentDateTime().substring(0,7));
                yearsDate = DateUtil.addMouth(yearsDate, -1);
                search.put("spotId",spotIdN);
                search.put("paymentMethod",1);
                search.put("subMethod",0);
                search.put("yearsDate",yearsDate);
                //微信流水
                Double weChatOrder = sysOrderMapper.getPaymentOrderFlowingWater(search);
                if (ToolUtil.isEmpty(weChatOrder)){
                    weChatOrder = 0d;
                }
                //储值流水
                search.put("paymentMethod",3);
                search.put("subMethod",1);
                Double storedOrder = sysOrderMapper.getPaymentOrderFlowingWater(search);
                if (ToolUtil.isEmpty(storedOrder)){
                    storedOrder = 0d;
                }

                //储值抵扣流水
                search.put("paymentMethod",1);
                search.put("subMethod",2);
                Double weChatAndStoredOrder = sysOrderMapper.getPaymentOrderFlowingWater(search);
                if (ToolUtil.isEmpty(weChatAndStoredOrder)){
                    weChatAndStoredOrder = 0d;
                }
                //押金抵扣流水
                search.put("paymentMethod",5);
                search.put("subMethod",0);
                Double paymentOrderFlowingWater = sysOrderMapper.getPaymentOrderFlowingWater(search);
                if (ToolUtil.isEmpty(paymentOrderFlowingWater)){
                    paymentOrderFlowingWater = 0d;
                }
                //总订单流水
                Double totalNumber = weChatOrder + storedOrder + weChatAndStoredOrder + paymentOrderFlowingWater;

                if (ToolUtil.isEmpty(totalNumber)){
                    totalNumber = 0d;
                }
                //收支类型
                long revenueExpenditure = subscriptionInformation.getRevenueExpenditure();

                //手续费率
                double charge = subscriptionInformation.getCharge();

                //分润比例
                double proportion = subscriptionInformation.getProportion();

                //税点
                double tax = subscriptionInformation.getTax();

                //平台统计分润基数

                double statisticsFenrun = totalNumber ;

                //平台统计分润支出
                double statisticsFenrunExpenditure = 0;
                //平台统计分润收入
                double statisticsFenrunIncome = 0;

                if (revenueExpenditure == 1){//收入

                    statisticsFenrunIncome = statisticsFenrun * proportion;

                }else{//支出

                    statisticsFenrunIncome = statisticsFenrun * (1-proportion);
                    statisticsFenrunExpenditure = statisticsFenrun * proportion;

                }

                //平台统计配件维修金额
                String platformMaintenanceAmount = "0";

                search = new HashMap<>();
                search.put("scenicSpotId",spotIdN);
                search.put("date",yearsDate);

                List<SysScenicSpotTargetAmount> targetAmountList = sysScenicSpotTargetAmountMapper.getTargetAmountList(search);
                if (!StringUtils.isEmpty(targetAmountList) && targetAmountList.size()>0){
                    SysScenicSpotTargetAmount sysScenicSpotTargetAmount = targetAmountList.get(0);
                    //维养费用
                    String maintainCost = sysScenicSpotTargetAmount.getMaintainCost();
                    //景区维修费用承担比例
                    double spotRepairRatio = subscriptionInformation.getSpotRepairRatio();
                    double platformMaintainCost =Double.parseDouble(maintainCost) * (1-spotRepairRatio);
                    platformMaintenanceAmount = df.format(platformMaintainCost);

                }else{

                }

                //平台统计结算(支出)金额
                double platformSettlementExpenditure = statisticsFenrun - Double.parseDouble(platformMaintenanceAmount) ;

                //平台统计结算(收入)金额.

                double platformSettlementIncome  =  statisticsFenrun + Double.parseDouble(platformMaintenanceAmount);

                //实际分润基数金额
                double actualProfitSharing = 0;
                //实际扣税后分润(收入)金额
                double actualTaxProfitIncome = 0;
                //实际扣税后分润(支出)金额
                double actualTaxProfitExpenditure = 0;

                //判断是收入还是支出
                if (revenueExpenditure == 1 ){//收入
                    //实际分润基数金额
                    actualProfitSharing = totalNumber * proportion;
                    //实际扣税后分润(收入)金额
                    actualTaxProfitIncome = (totalNumber - (totalNumber * tax)) *  proportion;


                }else{//支出

                    //实际分润基数金额
                    actualProfitSharing = totalNumber * (1-proportion);
                    //实际扣税后分润(支出)金额
                    actualTaxProfitExpenditure =  (totalNumber - (totalNumber * tax)) * proportion;

                    //实际扣税后分润(收入)金额
                    actualTaxProfitIncome = (totalNumber - (totalNumber * tax)) *  (1-proportion);


                }

                String format = df.format(actualProfitSharing);


                //景区承担的配件维修金额
                String spotMaintenanceAmount;
                //配件维修费结算状态
                long spotSettlemenType = 0;

                if (!StringUtils.isEmpty(targetAmountList) && targetAmountList.size()>0){
                    SysScenicSpotTargetAmount sysScenicSpotTargetAmount = targetAmountList.get(0);
                    //维养费用
                    String maintainCost = sysScenicSpotTargetAmount.getMaintainCost();
                    //景区维修费用承担比例
                    double spotRepairRatio = subscriptionInformation.getSpotRepairRatio();
                    double spotMaintainCost =Double.parseDouble(maintainCost) * spotRepairRatio;
                    spotMaintenanceAmount = df.format(spotMaintainCost);
                    spotSettlemenType = 1;
                }else{
                    spotMaintenanceAmount = "0";
                }

                //实际结算（支出）金额


                //实际结算（收入）金额


               sysScenicSpotFenfun = new SysScenicSpotFenfun();
               sysScenicSpotFenfun.setFenrunScenicSpotId(IdUtils.getSeqId());
               sysScenicSpotFenfun.setSubjectId(subjectIdN);
               sysScenicSpotFenfun.setCompanyId(companyId);
               sysScenicSpotFenfun.setPayee(collectionAccount);
               sysScenicSpotFenfun.setScenicSpotId(spotIdN);
               sysScenicSpotFenfun.setProfitSharingMonth(yearsDate);
               sysScenicSpotFenfun.setWechatPaymentFlow(df.format(weChatOrder));
               sysScenicSpotFenfun.setStoredValuePaymentFlow(df.format(storedOrder));
               sysScenicSpotFenfun.setWechatStoredPaymentFlow(df.format(weChatAndStoredOrder));
               sysScenicSpotFenfun.setDepositDeductionPaymentFlow(df.format(paymentOrderFlowingWater));
               sysScenicSpotFenfun.setTotalAmount(df.format(totalNumber));
               sysScenicSpotFenfun.setPlatformFenrunBase(df.format(statisticsFenrun) );
               sysScenicSpotFenfun.setPlatformFenrunExpenditure(df.format(statisticsFenrunExpenditure));
               sysScenicSpotFenfun.setPlatformFenrunIncome(df.format(statisticsFenrunIncome));
               sysScenicSpotFenfun.setPlatformStatisticsPartsMaintenance(platformMaintenanceAmount);
               sysScenicSpotFenfun.setPlatformStatisticsSettlementExpenditure(df.format(platformSettlementExpenditure));
               sysScenicSpotFenfun.setPlatformStatisticsSettlementIncome(df.format( platformSettlementIncome));
               sysScenicSpotFenfun.setType(revenueExpenditure);
               sysScenicSpotFenfun.setCharge(Double.toString(charge) );
               sysScenicSpotFenfun.setProportion(Double.toString(proportion) );
               sysScenicSpotFenfun.setTaxPoint(Double.toString(tax));
               sysScenicSpotFenfun.setActualFenrunBase(df.format(actualProfitSharing));
               sysScenicSpotFenfun.setActualTaxFenrunExpenditure(df.format(actualTaxProfitIncome));
               sysScenicSpotFenfun.setActualTaxFenrunIncome(df.format(actualTaxProfitExpenditure));
               sysScenicSpotFenfun.setFenrunSettlemenType(spotSettlemenType);
               sysScenicSpotFenfun.setSpotPartsMaintenance(spotMaintenanceAmount);
               sysScenicSpotFenfun.setSpotSettlemenType(Double.toString(spotSettlemenType));
               sysScenicSpotFenfun.setActualSettlemenExpenditure(null);
               sysScenicSpotFenfun.setActualSettlemenIncome(null);
               sysScenicSpotFenfun.setCreateDate(DateUtil.currentDateTime());
               sysScenicSpotFenfun.setUpdateDate(DateUtil.currentDateTime());

                i = spotProfitSharingStatisticsMapper.insertSelective(sysScenicSpotFenfun);
            }

            return i;

        }catch (Exception e){
                e.printStackTrace();
        }

        return i;
    }

    /**
     * 景区分润统计列表
     * @param search
     * @return
     */
    @Override
    public PageDataResult spotProfitSharingStatisticeList(Map<String,Object> search) {

        PageDataResult pageDataResult = new PageDataResult();

       PageHelper.startPage((Integer) search.get("pageNum"),(Integer) search.get("pageSize"));

        List<SysScenicSpotFenfun> list = spotProfitSharingStatisticsMapper.list(search);

        //计算合计
        Map<String,Object> map =  spotProfitSharingStatisticsMapper.getAmountTo(search);

        List<Map> listMap = new ArrayList<>();
        listMap.add(map);

        if (list.size()>0){
            PageInfo<SysScenicSpotFenfun> pageInfo = new PageInfo<>(list);
            pageDataResult.setList(list);
            pageDataResult.setTotals((int) pageInfo.getTotal());
            pageDataResult.setData(listMap);
        }


        return pageDataResult;

    }

    /**
     * 修改分润统计状态信息
     * @param sysScenicSpotFenfun
     * @return
     */
    @Override
    public int editSpotProfitSharingStatistice(SysScenicSpotFenfun sysScenicSpotFenfun) {

        SysScenicSpotFenfun sysScenicSpotFenfunN = spotProfitSharingStatisticsMapper.selectByPrimaryKey(sysScenicSpotFenfun.getFenrunScenicSpotId());


        //微信
        sysScenicSpotFenfunN.setWechatPaymentFlow(sysScenicSpotFenfun.getWechatPaymentFlow());
        //储值
        sysScenicSpotFenfunN.setStoredValuePaymentFlow(sysScenicSpotFenfun.getStoredValuePaymentFlow());
        //储值+微信
        sysScenicSpotFenfunN.setWechatStoredPaymentFlow(sysScenicSpotFenfun.getWechatStoredPaymentFlow());
        //押金抵扣
        sysScenicSpotFenfunN.setDepositDeductionPaymentFlow(sysScenicSpotFenfun.getDepositDeductionPaymentFlow());
        //修改平台分润基数
        sysScenicSpotFenfunN.setPlatformFenrunBase(sysScenicSpotFenfun.getPlatformFenrunBase());
        //平台总订单
        sysScenicSpotFenfunN.setTotalAmount(sysScenicSpotFenfun.getTotalAmount());
        //修改平台分润支出金额
        sysScenicSpotFenfunN.setPlatformFenrunExpenditure(sysScenicSpotFenfun.getPlatformFenrunExpenditure());
        //修改平台分润收入金额
        sysScenicSpotFenfunN.setPlatformFenrunIncome(sysScenicSpotFenfun.getPlatformFenrunIncome());
        //修改分润金额结算状态
        sysScenicSpotFenfunN.setFenrunSettlemenType(sysScenicSpotFenfun.getFenrunSettlemenType());
        //修改景区配件维修费用结算状态
        sysScenicSpotFenfunN.setSpotSettlemenType(sysScenicSpotFenfun.getSpotSettlemenType());
        //修改平台统计配件维修金额
        sysScenicSpotFenfunN.setPlatformStatisticsPartsMaintenance(sysScenicSpotFenfun.getPlatformStatisticsPartsMaintenance());
        //修改分润备注
        sysScenicSpotFenfunN.setFenrunRemarks(sysScenicSpotFenfun.getFenrunRemarks());
        //修改对账备注
        sysScenicSpotFenfunN.setReconciliationRemarks(sysScenicSpotFenfun.getReconciliationRemarks());
        //修改平台统计结算支出金额
        sysScenicSpotFenfunN.setPlatformStatisticsSettlementExpenditure(sysScenicSpotFenfun.getPlatformStatisticsSettlementExpenditure());
        //修改平台统计结算收入金额
        sysScenicSpotFenfunN.setPlatformStatisticsSettlementIncome(sysScenicSpotFenfun.getPlatformStatisticsSettlementIncome());
        //分润备注
        sysScenicSpotFenfunN.setFenrunRemarks(sysScenicSpotFenfun.getFenrunRemarks());
        int i = spotProfitSharingStatisticsMapper.updateByPrimaryKeySelective(sysScenicSpotFenfunN);



        return i;

    }

    @Override
    public PageDataResult spotProfitSharingStatisticeExcel(Map<String, Object> search) {
        PageDataResult pageDataResult = new PageDataResult();

//        PageHelper.startPage((Integer) search.get("pageNum"),(Integer) search.get("pageSize"));

        List<SysScenicSpotFenfun> list = spotProfitSharingStatisticsMapper.list(search);

        //计算合计
        Map<String,Object> map =  spotProfitSharingStatisticsMapper.getAmountTo(search);

        List<Map> listMap = new ArrayList<>();
        listMap.add(map);

        if (list.size()>0){
            PageInfo<SysScenicSpotFenfun> pageInfo = new PageInfo<>(list);
            pageDataResult.setList(list);
            pageDataResult.setTotals((int) pageInfo.getTotal());
            pageDataResult.setData(listMap);
        }


        return pageDataResult;
    }


    @Override
    public Map<Object, Object> spotProfitSharingStatisticeChart(Long subjectId, Long spotId, String startDate, String endDate, Long type, Long companyId) {

        HashMap<Object, Object> map = new HashMap<>();

        DecimalFormat df = new DecimalFormat("#.00");
        try {
            List<String> list = DateUtil.betweenMonths(startDate, endDate);
            ProfitSharingStatisticeTemporary profitSharingStatisticeTemporary = new ProfitSharingStatisticeTemporary();
            List<ProfitSharingStatisticeTemporary> profitSharingStatisticeTemporaries = new ArrayList<>();
            double robotCost = 0;
            double operateCost = 0;
            double spotMarketCost = 0;
            double rent = 0;
            double maintainCost = 0;
            //毛利
            double moneys = 0 ;
            //毛利率
            double  moneyRate = 0;
            double  robotMoneyRate = 0;
            if (StringUtils.isEmpty(spotId)){
                List<SysScenicSpot> scenicSpotList = sysScenicSpotMapper.getScenicSpotList();
                for (SysScenicSpot sysScenicSpot : scenicSpotList) {
                    profitSharingStatisticeTemporary =  new ProfitSharingStatisticeTemporary();
                    robotCost = 0;
                    operateCost = 0;
                    spotMarketCost = 0;
                    rent = 0;
                    maintainCost = 0;
                    //毛利
                    moneys = 0 ;
                    //毛利率
                    moneyRate = 0;
                    robotMoneyRate = 0;
                    profitSharingStatisticeTemporary.setSpotName(sysScenicSpot.getScenicSpotName());
                    Long robotCount = sysRobotMapper.getSpotIdByRobotCount(sysScenicSpot.getScenicSpotId());
                    for (String s : list) {
                        //毛利
                        Double money =  sysOrderMapper.getMonthByMoney(sysScenicSpot.getScenicSpotId(),s);
                        SysScenicSpotTargetAmount spotIdAndDateByTagret = sysScenicSpotTargetAmountMapper.getSpotIdAndDateByTagret(sysScenicSpot.getScenicSpotId(), s);

                        if (!StringUtils.isEmpty(spotIdAndDateByTagret)){
                            if (!StringUtils.isEmpty(spotIdAndDateByTagret.getRobotCost())){
                                robotCost = Double.parseDouble(spotIdAndDateByTagret.getRobotCost());
                            }
                            if (!StringUtils.isEmpty(spotIdAndDateByTagret.getOperateCost())){
                                operateCost = Double.parseDouble(spotIdAndDateByTagret.getOperateCost());
                            }

                            if (!StringUtils.isEmpty(spotIdAndDateByTagret.getSpotMarketCost())){
                                spotMarketCost = Double.parseDouble(spotIdAndDateByTagret.getSpotMarketCost());
                            }
                            if (!StringUtils.isEmpty(spotIdAndDateByTagret.getRent())){
                                rent = Double.parseDouble(spotIdAndDateByTagret.getRent());
                            }
                            if (!StringUtils.isEmpty(spotIdAndDateByTagret.getMaintainCost())){
                                maintainCost = Double.parseDouble(spotIdAndDateByTagret.getMaintainCost());
                            }

                        }
                        if (money == null){
                           money = 0d;
                        }
                        moneys = moneys + money;
                        //毛利率
                        if (money == 0){
                            moneyRate  = 0;
                        }else{
                            moneyRate  =  moneyRate + (((money - (robotCost + operateCost + spotMarketCost + rent + maintainCost)) / money) * 100);
                        }

                        if (money == 0){
                            robotMoneyRate = 0;
                        }else{
                            robotMoneyRate = robotMoneyRate +  (money / robotCount);
                        }
                    }
                    if (moneys != 0){
                        profitSharingStatisticeTemporary.setGrossProfit( String.valueOf(df.format(moneys/list.size())));
                    }else{
                        profitSharingStatisticeTemporary.setGrossProfit( String.valueOf(moneys));
                    }
                    if (moneyRate != 0){
                        profitSharingStatisticeTemporary.setGrossProfitMargin(String.valueOf(df.format(moneyRate/list.size())));
                    }else{
                        profitSharingStatisticeTemporary.setGrossProfitMargin(String.valueOf(moneyRate));
                    }
                    if (robotMoneyRate != 0){
                        profitSharingStatisticeTemporary.setRobotGrossProfit(String.valueOf(df.format(robotMoneyRate/list.size())));
                    }else{
                        profitSharingStatisticeTemporary.setRobotGrossProfit(String.valueOf(robotMoneyRate));
                    }
                    profitSharingStatisticeTemporaries.add(profitSharingStatisticeTemporary);
                }
            }else{

                List<SysScenicSpot> scenicSpotList = sysScenicSpotMapper.getScenicSpotListNew(spotId.toString());

                for (SysScenicSpot sysScenicSpot : scenicSpotList) {
                    profitSharingStatisticeTemporary =  new ProfitSharingStatisticeTemporary();
                    robotCost = 0;
                    operateCost = 0;
                    spotMarketCost = 0;
                    rent = 0;
                    maintainCost = 0;
                    //毛利
                    moneys = 0 ;
                    //毛利率
                    moneyRate = 0;
                    robotMoneyRate = 0;
                    profitSharingStatisticeTemporary.setSpotName(sysScenicSpot.getScenicSpotName());
                    Long robotCount = sysRobotMapper.getSpotIdByRobotCount(sysScenicSpot.getScenicSpotId());
                    for (String s : list) {
                        //毛利
                        Double money =  sysOrderMapper.getMonthByMoney(sysScenicSpot.getScenicSpotId(),s);
                        SysScenicSpotTargetAmount spotIdAndDateByTagret = sysScenicSpotTargetAmountMapper.getSpotIdAndDateByTagret(sysScenicSpot.getScenicSpotId(), s);

                        if (!StringUtils.isEmpty(spotIdAndDateByTagret)){
                            if (!StringUtils.isEmpty(spotIdAndDateByTagret.getRobotCost())){
                                robotCost = Double.parseDouble(spotIdAndDateByTagret.getRobotCost());
                            }
                            if (!StringUtils.isEmpty(spotIdAndDateByTagret.getOperateCost())){
                                operateCost = Double.parseDouble(spotIdAndDateByTagret.getOperateCost());
                            }

                            if (!StringUtils.isEmpty(spotIdAndDateByTagret.getSpotMarketCost())){
                                spotMarketCost = Double.parseDouble(spotIdAndDateByTagret.getSpotMarketCost());
                            }
                            if (!StringUtils.isEmpty(spotIdAndDateByTagret.getRent())){
                                rent = Double.parseDouble(spotIdAndDateByTagret.getRent());
                            }
                            if (!StringUtils.isEmpty(spotIdAndDateByTagret.getMaintainCost())){
                                maintainCost = Double.parseDouble(spotIdAndDateByTagret.getMaintainCost());
                            }

                        }
                        if (money == null){
                            money = 0d;
                        }
                        moneys = moneys + money;
                        //毛利率
                        if (money == 0){
                            moneyRate  = 0;
                        }else{
                            moneyRate  =  moneyRate + (((money - (robotCost + operateCost + spotMarketCost + rent + maintainCost)) / money) * 100);
                        }

                        if (money == 0){
                            robotMoneyRate = 0;
                        }else{
                            robotMoneyRate = robotMoneyRate +  (money / robotCount);
                        }
                    }
                    if (moneys != 0){
                        profitSharingStatisticeTemporary.setGrossProfit( String.valueOf(df.format(moneys/list.size())));
                    }else{
                        profitSharingStatisticeTemporary.setGrossProfit( String.valueOf(moneys));
                    }
                    if (moneyRate != 0){
                        profitSharingStatisticeTemporary.setGrossProfitMargin(String.valueOf(df.format(moneyRate/list.size())));
                    }else{
                        profitSharingStatisticeTemporary.setGrossProfitMargin(String.valueOf(moneyRate));
                    }
                    if (robotMoneyRate != 0){
                        profitSharingStatisticeTemporary.setRobotGrossProfit(String.valueOf(df.format(robotMoneyRate/list.size())));
                    }else{
                        profitSharingStatisticeTemporary.setRobotGrossProfit(String.valueOf(robotMoneyRate));
                    }

                    profitSharingStatisticeTemporaries.add(profitSharingStatisticeTemporary);
                }
            }

            Collections.sort(profitSharingStatisticeTemporaries, new Comparator<ProfitSharingStatisticeTemporary>() {
                public int compare(ProfitSharingStatisticeTemporary o1, ProfitSharingStatisticeTemporary o2) {
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

            for (ProfitSharingStatisticeTemporary sharingStatisticeTemporary : profitSharingStatisticeTemporaries) {

                oneList.add(sharingStatisticeTemporary.getSpotName());
                twoList.add(sharingStatisticeTemporary.getGrossProfit());
                threeList.add(sharingStatisticeTemporary.getGrossProfitMargin());
                fourList.add(sharingStatisticeTemporary.getRobotGrossProfit());

            }
            map.put("spotNameList",oneList);
            map.put("grossProfitList",twoList);
            map.put("grossProfitMargin",threeList);
            map.put("robotGrossProfit",fourList);
        }catch (Exception e){
            e.printStackTrace();
        }
        return map ;
    }

    /**
     * 每月定时统计分润相关数据的方法
     */
    @Override
    public void spotProfitSharingStatisticeTimedStatistics() {

        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        List<SubscriptionInformation> subscriptionInformationList = subscriptionInformationMapper.selectAll();

        //平台统计分润基数
        double priceA = 0d;
        //实际分润基数
        double priceB = 0d;
        SysScenicSpotFenfun sysScenicSpotFenfun = new SysScenicSpotFenfun();
        for (SubscriptionInformation subscriptionInformation : subscriptionInformationList) {
            priceA = 0d;
            priceB = 0d;
            sysScenicSpotFenfun = new SysScenicSpotFenfun();
            //分润表主键id
            sysScenicSpotFenfun.setFenrunScenicSpotId(IdUtils.getSeqId());
            //分润表合作主体公司id
            sysScenicSpotFenfun.setSubjectId(subscriptionInformation.getSubjectId());
            //分润表合作公司
            sysScenicSpotFenfun.setCompanyId(subscriptionInformation.getCompanyId());
            //分润表收款单位
            CooperativeCompany cooperativeCompany = cooperativeCompanyMapper.selectByPrimaryKey(subscriptionInformation.getCompanyId());
            sysScenicSpotFenfun.setPayee(cooperativeCompany.getCollectionAccount());
            //分润表景区id
            sysScenicSpotFenfun.setScenicSpotId(subscriptionInformation.getSpotId());
            //分润表月份
            String last12Months = DateUtil.getLast12Months(1);
            sysScenicSpotFenfun.setProfitSharingMonth(last12Months);
            //微信订单支付流水
            Double weCharMoney = sysOrderMapper.getSpotIdAndTimeLikeByFlowingWater(subscriptionInformation.getSpotId(), last12Months, 1);
            if (StringUtils.isEmpty(weCharMoney)) {
                weCharMoney = 0d;
            }
            sysScenicSpotFenfun.setWechatPaymentFlow(weCharMoney.toString());
            //储值订单支付流水
            Double accountMoney = sysOrderMapper.getSpotIdAndTimeLikeByFlowingWater(subscriptionInformation.getSpotId(), last12Months, 3);
            if (StringUtils.isEmpty(accountMoney)) {
                accountMoney = 0d;
            }
            sysScenicSpotFenfun.setStoredValuePaymentFlow(accountMoney.toString());
            //微信加储值订单支付流水（）
            Double wechatAccountMoney = sysOrderMapper.getSpotIdAndTimeLikeByFlowingWater(subscriptionInformation.getSpotId(), last12Months, 4);
            if (StringUtils.isEmpty(wechatAccountMoney)) {
                wechatAccountMoney = 0d;
            }

            sysScenicSpotFenfun.setWechatStoredPaymentFlow(wechatAccountMoney.toString());
            //押金抵扣支付流水
            Double depositMoney = sysOrderMapper.getSpotIdAndTimeLikeByFlowingWater(subscriptionInformation.getSpotId(), last12Months, 5);
            if (StringUtils.isEmpty(depositMoney)) {
                depositMoney = 0d;
            }
            sysScenicSpotFenfun.setDepositDeductionPaymentFlow(depositMoney.toString());
            //总流水
            Double totalMoney = sysOrderMapper.getSpotIdAndTimeLikeByFlowingWater(subscriptionInformation.getSpotId(), last12Months, null);
            if (StringUtils.isEmpty(totalMoney)) {
                totalMoney = 0d;
            }
            sysScenicSpotFenfun.setTotalAmount(totalMoney.toString());
            //平台分润基数
            //判断是否计算微信流水
            if (subscriptionInformation.getWechat() == 1){
                priceA = priceA + depositMoney;
            }
            //判断是否计算储值流水
            if (subscriptionInformation.getSaving() == 1){
                priceA = priceA + accountMoney;
            }
            //判断是否计算微信+储值
            if (subscriptionInformation.getSavingWechat() ==1){
                priceA = priceA + wechatAccountMoney;
            }
            //判断是否计算押金抵扣流水
            if (subscriptionInformation.getDeposit() == 1){
                priceA = priceA + depositMoney;
            }
            sysScenicSpotFenfun.setPlatformFenrunBase(String.valueOf(priceA));

            //平台分润收入以及支出
            //减手续费和税
            priceA = priceA - (priceA * subscriptionInformation.getCharge());
            if (1 == subscriptionInformation.getTaxRateMethod()){

                priceA =  priceA / (1 + subscriptionInformation.getTax()) * subscriptionInformation.getTax();

            }else if (2 == subscriptionInformation.getTaxRateMethod()){

                priceA =  priceA * subscriptionInformation.getTax();

            }else if (3 == subscriptionInformation.getTaxRateMethod()){

                priceA = priceA / 1.06 * subscriptionInformation.getTax();
            }else {
                priceA =  priceA * subscriptionInformation.getTax();

            }

            priceA = Double.parseDouble(decimalFormat.format(priceA));
            double rae  =  priceA * subscriptionInformation.getProportion();
            rae = Double.parseDouble(decimalFormat.format(rae));
            if (subscriptionInformation.getRevenueExpenditure() == 1 ){//平台分润收入
                //类型为收入，则添加到平台分润收入；平台分润支出则为0
                sysScenicSpotFenfun.setPlatformFenrunIncome(String.valueOf(rae));
                sysScenicSpotFenfun.setPlatformFenrunExpenditure("0");
            }else{//平台分润支出
                sysScenicSpotFenfun.setPlatformFenrunExpenditure(String.valueOf(rae));
                sysScenicSpotFenfun.setPlatformFenrunIncome("0");
            }

            //
            //平台统计配件维修金额
            Double partsMaintenanceMoney = sysRobotErrorRecordsMapper.getSpotIdAndTimeLikeByRepairMoney(subscriptionInformation.getSpotId(), last12Months);
            if (StringUtils.isEmpty(partsMaintenanceMoney)) {
                partsMaintenanceMoney = 0d;
            }
            sysScenicSpotFenfun.setPlatformStatisticsPartsMaintenance(partsMaintenanceMoney.toString());

            //平台统计结算支出或者收入金额
            if (subscriptionInformation.getRevenueExpenditure() == 1){//统计结算收入
                double raes =  rae + partsMaintenanceMoney;
                sysScenicSpotFenfun.setPlatformStatisticsSettlementExpenditure("0");
                sysScenicSpotFenfun.setPlatformStatisticsSettlementIncome(String.valueOf(raes));
            }else{//统计结算支出
                double raes = rae - partsMaintenanceMoney;
                sysScenicSpotFenfun.setPlatformStatisticsSettlementIncome("0");
                sysScenicSpotFenfun.setPlatformStatisticsSettlementExpenditure(String.valueOf(raes));
            }
            //收支类型1收入，2支出
            sysScenicSpotFenfun.setType(subscriptionInformation.getRevenueExpenditure());
            //手续费费率
            sysScenicSpotFenfun.setCharge(String.valueOf(subscriptionInformation.getCharge()));
            //分润比例
            sysScenicSpotFenfun.setProportion(String.valueOf(subscriptionInformation.getProportion()));
            //税点
            sysScenicSpotFenfun.setTaxPoint(String.valueOf(subscriptionInformation.getTax()));

            priceB =  weCharMoney + accountMoney + depositMoney + wechatAccountMoney ;
            //减手续费
            priceB = priceB - (priceB * subscriptionInformation.getCharge());
            priceB = Double.parseDouble(decimalFormat.format(priceB));
            //实际分润基数
            sysScenicSpotFenfun.setActualFenrunBase(String.valueOf(priceB));
            //实际扣税分润支出或收入金额

            //计算税
            if (1 == subscriptionInformation.getTaxRateMethod()){

                priceB =  priceB / (1 + subscriptionInformation.getTax()) * subscriptionInformation.getTax();

            }else if (2 == subscriptionInformation.getTaxRateMethod()){

                priceB =  priceB * subscriptionInformation.getTax();

            }else if (3 == subscriptionInformation.getTaxRateMethod()){

                priceB = priceB / 1.06 * subscriptionInformation.getTax();
            }else {
                priceB =  priceB * subscriptionInformation.getTax();

            }

           double raeB = priceB  * subscriptionInformation.getProportion();
            raeB = Double.parseDouble(decimalFormat.format(raeB));
            if (subscriptionInformation.getRevenueExpenditure() == 1){//统计结算收入
                sysScenicSpotFenfun.setActualTaxFenrunExpenditure("0");
                sysScenicSpotFenfun.setActualTaxFenrunIncome(String.valueOf(raeB));
            }else{//统计结算支出
                sysScenicSpotFenfun.setActualTaxFenrunExpenditure(String.valueOf(raeB));
                sysScenicSpotFenfun.setActualTaxFenrunIncome("0");
            }

            //景区承担配件维修金额（总合计）
            Double spotPartsMaintenanceMoney = sysRobotErrorRecordsMapper.getSpotIdAndTimeLikeBySpotRepairMoney(subscriptionInformation.getSpotId(), last12Months);
            if (StringUtils.isEmpty(spotPartsMaintenanceMoney)) {
                spotPartsMaintenanceMoney = 0d;
            }
            sysScenicSpotFenfun.setSpotPartsMaintenance(spotPartsMaintenanceMoney.toString());
            //实际结算支出或收入金额
            if (subscriptionInformation.getRevenueExpenditure() == 1){//实际结算收入
                double raes =  raeB + partsMaintenanceMoney;
                sysScenicSpotFenfun.setActualSettlemenExpenditure("0");
                sysScenicSpotFenfun.setActualSettlemenIncome(String.valueOf(raes));
            }else{//实际结算支出
                double raes = raeB - partsMaintenanceMoney;
                sysScenicSpotFenfun.setActualSettlemenExpenditure(String.valueOf(raes));
                sysScenicSpotFenfun.setActualSettlemenIncome("0");
            }
            //创建时间
            sysScenicSpotFenfun.setCreateDate(DateUtil.currentDateTime());
            //修改时间
            sysScenicSpotFenfun.setUpdateDate(DateUtil.currentDateTime());
            int i = spotProfitSharingStatisticsMapper.insertSelective(sysScenicSpotFenfun);
        }
    }
}
