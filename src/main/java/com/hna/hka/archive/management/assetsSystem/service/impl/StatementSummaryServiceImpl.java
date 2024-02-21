package com.hna.hka.archive.management.assetsSystem.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hna.hka.archive.management.assetsSystem.dao.SubscriptionInformationMapper;
import com.hna.hka.archive.management.assetsSystem.model.OrderMoney;
import com.hna.hka.archive.management.assetsSystem.model.SubscriptionInformation;
import com.hna.hka.archive.management.assetsSystem.model.SysScenicSpotTargetAmount;
import com.hna.hka.archive.management.assetsSystem.service.StatementSummaryService;
import com.hna.hka.archive.management.system.dao.SysOrderMapper;
import com.hna.hka.archive.management.system.util.PageDataResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2023/2/1 14:50
 */
@Service
public class StatementSummaryServiceImpl implements StatementSummaryService {

    @Autowired
    SubscriptionInformationMapper subscriptionInformationMapper;
    @Autowired
    SysOrderMapper sysOrderMapper;

    /**
     * 对账单汇总列表查询
     * @param subjectId
     * @param companyId
     * @param spotId
     * @param startTime
     * @param endTime
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public PageDataResult getStatementSummaryList(String subjectId, String companyId, String spotId, String startTime, String endTime, Integer pageNum, Integer pageSize) {

        PageDataResult pageDataResult = new PageDataResult();
        DecimalFormat df = new DecimalFormat("#.00");

        PageHelper.startPage(pageNum,pageSize);
        List<OrderMoney> list = subscriptionInformationMapper.getStatementSummaryList(subjectId,companyId,spotId);

        Double  totalMoney = 0d;
        Double wechatMoney = 0d;
        Double savingMoney = 0d;
        Double savingWechatMoney = 0d;
        Double depositMoney = 0d;
        Double serviceCharge = 0d;
        Double taxMoney = 0d;
        Double shareAmount = 0d;
        for (OrderMoney orderMoney : list) {
            totalMoney = 0d;
            wechatMoney = 0d;
            savingMoney = 0d;
            savingWechatMoney = 0d;
            depositMoney = 0d;
            serviceCharge = 0d;
            taxMoney = 0d;
            shareAmount = 0d;

            //微信
             wechatMoney =  sysOrderMapper.getOrderStatementSummary(orderMoney.getSpotId(),1,startTime,endTime);
             if (wechatMoney == null){
                 wechatMoney = 0d;
             }
             if (orderMoney.getWechat() ==1){
                 totalMoney = totalMoney + wechatMoney;
             }
             //储值
             savingMoney = sysOrderMapper.getOrderStatementSummary(orderMoney.getSpotId(),3,startTime,endTime);
             if (savingMoney == null){
                 savingMoney = 0d;
             }
             if(orderMoney.getSaving() == 1){
                 totalMoney = totalMoney + savingMoney;
             }
             //储值+微信
             savingWechatMoney = sysOrderMapper.getOrderStatementSummary(orderMoney.getSpotId(),4,startTime,endTime);
             if (savingWechatMoney == null){
                 savingWechatMoney = 0d;
             }
             if (orderMoney.getSavingWechat() == 1){
                 totalMoney = totalMoney + savingWechatMoney;
             }
             //押金
             depositMoney = sysOrderMapper.getOrderStatementSummary(orderMoney.getSpotId(),5,startTime,endTime);
             if (depositMoney == null){
                 depositMoney = 0d;
             }
             if (orderMoney.getDeposit() == 1){
                 totalMoney = totalMoney + depositMoney;
             }
             //合计
//            totalMoney = Double.parseDouble(df.format(wechatMoney + savingMoney + savingWechatMoney + depositMoney));
            totalMoney = Double.parseDouble(df.format(totalMoney));

            if (!StringUtils.isEmpty(totalMoney) && totalMoney != 0){
                //手续费
                serviceCharge = Double.parseDouble(df.format(totalMoney * orderMoney.getCharge()));
                //税值
                Long taxRateMethod = orderMoney.getTaxRateMethod();

                if (!StringUtils.isEmpty(taxRateMethod)){

                    if (taxRateMethod == 1){

                        taxMoney =Double.parseDouble(df.format((totalMoney - serviceCharge) / (1+orderMoney.getTax()) * orderMoney.getTax())) ;
//                    taxMoney = Double.parseDouble(df.format((totalMoney - serviceCharge) *  orderMoney.getTax()));

                    }else if (taxRateMethod == 2){

                        taxMoney =Double.parseDouble(df.format((totalMoney - serviceCharge) * orderMoney.getTax()));

                    }else{

                        taxMoney =Double.parseDouble(df.format((totalMoney - serviceCharge) / 1.06 * orderMoney.getTax())) ;

                    }
                }else{

                    taxMoney = Double.parseDouble(df.format((totalMoney - serviceCharge) * orderMoney.getTax()));

                }


                //分成金额
                shareAmount =Double.parseDouble(df.format((totalMoney - serviceCharge -taxMoney) * orderMoney.getProportion()));
            }

            orderMoney.setWechatMoney(wechatMoney);
            orderMoney.setSavingMoney(savingMoney);
            orderMoney.setSavingWechatMoney(savingWechatMoney);
            orderMoney.setDepositMoney(depositMoney);
            orderMoney.setServiceCharge(serviceCharge);
            orderMoney.setTaxMoney(taxMoney);
            orderMoney.setTotalMoney(totalMoney);
            orderMoney.setShareAmount(shareAmount);
            orderMoney.setDate(startTime + "至" + endTime);
        }

        if (list.size()>0){
            PageInfo<OrderMoney> pageInfo = new PageInfo<>(list);
            pageDataResult.setData(list);
            pageDataResult.setTotals((int)pageInfo.getTotal());
            pageDataResult.setCode(200);
        }
        return pageDataResult;
    }

    /**
     * 对账单汇总列表导出
     * @param
     * @return
     */
    @Override
    public List<OrderMoney> getStatementSummaryExcel(String subjectId, String companyId, String spotId, String startTime, String endTime) {

        DecimalFormat df = new DecimalFormat("#.00");
        List<OrderMoney> list = subscriptionInformationMapper.getStatementSummaryList(subjectId,companyId,spotId);

        Double  totalMoney = 0d;
        Double wechatMoney = 0d;
        Double savingMoney = 0d;
        Double savingWechatMoney = 0d;
        Double depositMoney = 0d;
        Double serviceCharge = 0d;
        Double taxMoney = 0d;
        Double shareAmount = 0d;
//        Double shareableMoney = 0d;
        for (OrderMoney orderMoney : list) {

            orderMoney.setDate(startTime + "-" + endTime);

            totalMoney = 0d;
            wechatMoney = 0d;
            savingMoney = 0d;
            savingWechatMoney = 0d;
            depositMoney = 0d;
            serviceCharge = 0d;
            taxMoney = 0d;
            shareAmount = 0d;
//            shareableMoney = 0d;

            //微信
            wechatMoney =  sysOrderMapper.getOrderStatementSummary(orderMoney.getSpotId(),1,startTime,endTime);
            if (wechatMoney == null){
                wechatMoney = 0d;
            }
            if (orderMoney.getWechat() == 1){
                totalMoney = totalMoney + wechatMoney;
            }
            //储值
            savingMoney = sysOrderMapper.getOrderStatementSummary(orderMoney.getSpotId(),3,startTime,endTime);
            if (savingMoney == null){
                savingMoney = 0d;
            }
            if (orderMoney.getSaving() == 1){
                totalMoney = totalMoney + savingMoney;
            }
            //储值+微信
            savingWechatMoney = sysOrderMapper.getOrderStatementSummary(orderMoney.getSpotId(),4,startTime,endTime);
            if (savingWechatMoney == null){
                savingWechatMoney = 0d;
            }
            if (orderMoney.getSavingWechat() == 1){
                totalMoney = totalMoney + savingWechatMoney;
            }
            //押金
            depositMoney = sysOrderMapper.getOrderStatementSummary(orderMoney.getSpotId(),5,startTime,endTime);
            if (depositMoney == null){
                depositMoney = 0d;
            }
            if (orderMoney.getDeposit() == 1){
                totalMoney = totalMoney + depositMoney;
            }
            //合计
//            totalMoney = Double.parseDouble(df.format( wechatMoney + savingMoney + savingWechatMoney + depositMoney));
            totalMoney = Double.parseDouble(df.format(totalMoney));



            if (!StringUtils.isEmpty(totalMoney) && totalMoney != 0){
                //手续费
                serviceCharge = Double.parseDouble(df.format(totalMoney * orderMoney.getCharge()));
                //税值

                Long taxRateMethod = orderMoney.getTaxRateMethod();

                if (!StringUtils.isEmpty(taxRateMethod)){

                    if (taxRateMethod == 1){

                        taxMoney =Double.parseDouble(df.format((totalMoney - serviceCharge) / (1+orderMoney.getTax()) * orderMoney.getTax())) ;

                    }else if (taxRateMethod == 2){

                        taxMoney =Double.parseDouble(df.format((totalMoney - serviceCharge) * orderMoney.getTax()));

                    }else{

                        taxMoney =Double.parseDouble(df.format((totalMoney - serviceCharge) / 1.06 * orderMoney.getTax())) ;

                    }
                }else{

                    taxMoney = Double.parseDouble(df.format((totalMoney - serviceCharge) * orderMoney.getTax()));

                }

                //分成金额
                shareAmount =  Double.parseDouble(df.format( (totalMoney - serviceCharge - taxMoney) * orderMoney.getProportion()));
                //可分成金额
//                shareableMoney = totalMoney - serviceCharge - taxMoney;
            }

            orderMoney.setWechatMoney(wechatMoney);
            orderMoney.setSavingMoney(savingMoney);
            orderMoney.setSavingWechatMoney(savingWechatMoney);
            orderMoney.setDepositMoney(depositMoney);
            orderMoney.setTotalMoney(totalMoney);
            orderMoney.setServiceCharge(serviceCharge);
            orderMoney.setTaxMoney(taxMoney);
            orderMoney.setShareAmount(shareAmount);
            orderMoney.setDate(startTime + "至" + endTime);
        }

        return list;

    }

    /**
     * 预览
     * @param subjectId
     * @param companyId
     * @param spotId
     * @param startTime
     * @param endTime
     * @return
     */
    @Override
    public   HashMap<String, Object>preview(String subjectId, String companyId, String spotId, String startTime, String endTime) {

        HashMap<String, Object> returnMap = new HashMap<>();
        List<Map<String, Object>> mapList = new ArrayList<>();
        Map<String, Object> map1 = new HashMap<>();

        Map<String, Object> map = new HashMap<>();
        DecimalFormat df = new DecimalFormat("#.00");
        List<OrderMoney> list = subscriptionInformationMapper.getStatementSummaryList(subjectId,companyId,spotId);

        Double  totalMoney = 0d;
        Double wechatMoney = 0d;
        Double savingMoney = 0d;
        Double savingWechatMoney = 0d;
        Double depositMoney = 0d;
        Double serviceCharge = 0d;
        Double taxMoney = 0d;
        Double shareAmount = 0d;
        Double shareableMoney = 0d;
        //小计
        Double subtotalMoney = 0d;
        Double subtotalCharge = 0d;
        Double subtotalTax = 0d;
        Double subtotalShareable = 0d;
        for (OrderMoney orderMoney : list) {
            map = new HashMap<>();
            orderMoney.setDate(startTime + "至" + endTime);
            totalMoney = 0d;
            wechatMoney = 0d;
            savingMoney = 0d;
            savingWechatMoney = 0d;
            depositMoney = 0d;
            serviceCharge = 0d;
            taxMoney = 0d;
            shareAmount = 0d;
//            shareableMoney = 0d;

            //微信
            wechatMoney =  sysOrderMapper.getOrderStatementSummary(orderMoney.getSpotId(),1,startTime,endTime);
            if (wechatMoney == null){
                wechatMoney = 0d;
            }
            if (orderMoney.getWechat() == 1){
                totalMoney = totalMoney + wechatMoney;
            }
            //储值
            savingMoney = sysOrderMapper.getOrderStatementSummary(orderMoney.getSpotId(),3,startTime,endTime);
            if (savingMoney == null){
                savingMoney = 0d;
            }
            if (orderMoney.getSaving() == 1){
                totalMoney = totalMoney + savingMoney;
            }
            //储值+微信
            savingWechatMoney = sysOrderMapper.getOrderStatementSummary(orderMoney.getSpotId(),4,startTime,endTime);
            if (savingWechatMoney == null){
                savingWechatMoney = 0d;
            }
            if (orderMoney.getSavingWechat() == 1){
                totalMoney = totalMoney + savingWechatMoney;
            }
            //押金
            depositMoney = sysOrderMapper.getOrderStatementSummary(orderMoney.getSpotId(),5,startTime,endTime);
            if (depositMoney == null){
                depositMoney = 0d;
            }
            if (orderMoney.getDeposit() == 1){
                totalMoney = totalMoney  + depositMoney;
            }
            //合计
//            totalMoney = wechatMoney + savingMoney + savingWechatMoney + depositMoney;
            totalMoney = Double.parseDouble(df.format(totalMoney));

            if (!StringUtils.isEmpty(totalMoney) && totalMoney != 0){
                //手续费
                serviceCharge = Double.parseDouble(df.format(totalMoney * orderMoney.getCharge()));
                if (serviceCharge == null){
                    serviceCharge = 0d;
                }
                //税值
                Long taxRateMethod = orderMoney.getTaxRateMethod();

                if (!StringUtils.isEmpty(taxRateMethod)){

                    if (taxRateMethod == 1){

                        taxMoney =Double.parseDouble(df.format((totalMoney - serviceCharge) / (1+orderMoney.getTax()) * orderMoney.getTax())) ;
//                    taxMoney = Double.parseDouble(df.format((totalMoney - serviceCharge) *  orderMoney.getTax()));

                    }else if (taxRateMethod == 2){

                        taxMoney =Double.parseDouble(df.format((totalMoney - serviceCharge) * orderMoney.getTax()));

                    }else{

                        taxMoney =Double.parseDouble(df.format((totalMoney - serviceCharge) / 1.06 * orderMoney.getTax())) ;

                    }
                }else{

                    taxMoney = Double.parseDouble(df.format((totalMoney - serviceCharge) * orderMoney.getTax()));

                }
                //可分成金额
                shareableMoney = Double.parseDouble(df.format(totalMoney - serviceCharge - taxMoney));
                //分成金额
                shareAmount =  Double.parseDouble(df.format( totalMoney - serviceCharge - taxMoney));
                if (shareAmount == null){
                    shareAmount = 0d;
                }
                //累计小计
                subtotalMoney = subtotalMoney + totalMoney ;
                subtotalCharge = subtotalCharge + serviceCharge;
                subtotalTax = subtotalTax + taxMoney;
                subtotalShareable = subtotalShareable + shareableMoney;

            }

            map.put("我方公司",orderMoney.getSubjectName());
            map.put("合作公司",orderMoney.getCompanyName());
            map.put("收款单位",orderMoney.getCompanyName());
            map.put("收款账号",orderMoney.getBank());
            map.put("开户行",orderMoney.getCollectionAccountNumber());
            map.put("景区名称",orderMoney.getSpotName());
            map.put("月份",orderMoney.getDate());
            map.put("流水",totalMoney);
            map.put("手续费",serviceCharge);
            map.put("税",taxMoney);
            map.put("可分成金额",shareableMoney);
//            map.put("分成金额",shareAmount);
            map.put("分成比例",orderMoney.getProportion());
            mapList.add(map);
        }
        map1.put("流水小计",subtotalMoney);
        map1.put("手续费小计",subtotalCharge);
        map1.put("税小计",subtotalTax);
        map1.put("可分成金额小计",subtotalShareable);
//        map2.put("分成比例",list.get(0).getProportion());


        returnMap.put("list",mapList);
        returnMap.put("subtotal",map1);
        returnMap.put("shareProportion",list.get(0).getProportion());
        returnMap.put("shareAmount",Double.parseDouble(df.format( subtotalShareable * list.get(0).getProportion())));
        return returnMap;
    }


}
