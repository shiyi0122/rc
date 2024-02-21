package com.hna.hka.archive.management.assetsSystem.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.inject.internal.util.$Nullable;
import com.hna.hka.archive.management.appSystem.dao.SysRobotErrorRecordsMapper;
import com.hna.hka.archive.management.assetsSystem.dao.AccountCloseMapper;
import com.hna.hka.archive.management.assetsSystem.dao.SpotProfitSharingStatisticsMapper;
import com.hna.hka.archive.management.assetsSystem.dao.SubscriptionInformationMapper;
import com.hna.hka.archive.management.assetsSystem.model.*;
import com.hna.hka.archive.management.assetsSystem.service.AccountCloseService;
import com.hna.hka.archive.management.system.dao.SysRobotMapper;
import com.hna.hka.archive.management.system.model.SysUsers;
import com.hna.hka.archive.management.system.util.DateUtil;
import com.hna.hka.archive.management.system.util.IdUtils;
import io.swagger.annotations.OAuth2Definition;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

/**
 * @program: rc
 * @description: 结账流水
 * @author: zhaoxianglong
 * @create: 2021-09-13 14:15
 **/
@Service
public class AccountCloseServiceImpl implements AccountCloseService {

    @Autowired
    private AccountCloseMapper mapper;
    @Autowired
    private SubscriptionInformationMapper subscriptionInformationMapper;
    @Autowired
    private SysRobotMapper sysRobotMapper;
    @Autowired
    private SysRobotErrorRecordsMapper sysRobotErrorRecordsMapper;
    @Autowired
    private SpotProfitSharingStatisticsMapper spotProfitSharingStatisticsMapper;

    @Override
    public List<AccountClose> list(String companyId, String spotId, String startDate, String endDate, Integer type, Integer pageNum, Integer pageSize) {
        String[] split = (spotId != null ? spotId.split(",") : null);
        List<AccountClose> list = mapper.list(companyId , split , startDate , endDate , type , pageNum , pageSize);
        return list;
    }

    @Override
    public Integer getCount(String companyId, String spotId, String startDate, String endDate, Integer type) {
        String[] split = (spotId != null ? spotId.split(",") : null);
        return mapper.getCount(companyId , split , startDate , endDate , type);
    }

    @Override
    public Integer update(AccountClose accountClose) {
        SysUsers user = (SysUsers) SecurityUtils.getSubject().getPrincipal();
        accountClose.setPaymentName(user.getUserName());
        Integer update = mapper.update(accountClose);

        long type = accountClose.getType();
        if (type == 1 ){//收入

            Double total = accountClose.getTotal();
//            Double totalCharge = accountClose.getTotalCharge();
            Double totalTax = accountClose.getTotalTax();

            SysScenicSpotFenfun sysScenicSpotFenfun = spotProfitSharingStatisticsMapper.getSpotIdAndDateByOne(accountClose.getCompanyId(),accountClose.getSpotId(),accountClose.getMonth());
            if (!StringUtils.isEmpty(sysScenicSpotFenfun)){
                sysScenicSpotFenfun.setActualFenrunBase(total.toString());
                sysScenicSpotFenfun.setActualTaxFenrunIncome( totalTax.toString());
                sysScenicSpotFenfun.setFenrunSettlemenType(2l);
                spotProfitSharingStatisticsMapper.updateByPrimaryKeySelective(sysScenicSpotFenfun);
            }

        }else{//支出
            SysScenicSpotFenfun sysScenicSpotFenfun = spotProfitSharingStatisticsMapper.getSpotIdAndDateByOne(accountClose.getCompanyId(),accountClose.getSpotId(),accountClose.getMonth());

            Double total = accountClose.getTotal();
            Double totalCharge = accountClose.getTotalCharge();
            Double totalTax = accountClose.getTotalTaxEx();
            if (!StringUtils.isEmpty(sysScenicSpotFenfun)){
                sysScenicSpotFenfun.setActualFenrunBase(total.toString());
                sysScenicSpotFenfun.setActualTaxFenrunExpenditure( totalTax.toString());
                sysScenicSpotFenfun.setFenrunSettlemenType(2l);
                spotProfitSharingStatisticsMapper.updateByPrimaryKeySelective(sysScenicSpotFenfun);
            }

        }

        return mapper.update(accountClose);
    }

    /**
     * 定时统计结算流水
     */
    @Override
    public void spotAccountCloseTimedStatistics() {

        //获取签约公司列表信息
        List<SubscriptionInformation> subscriptionInformationList = subscriptionInformationMapper.selectAll();
        AccountClose accountClose = new AccountClose();
        for (SubscriptionInformation subscriptionInformation : subscriptionInformationList) {
           accountClose = new AccountClose();
           //公司id
           accountClose.setCompanyId(subscriptionInformation.getCompanyId());
           //公司名称
           accountClose.setCompanyName(subscriptionInformation.getCompanyName());
           //景区id
            accountClose.setSpotId(subscriptionInformation.getSpotId());
            //景区名称
            accountClose.setSpotName(subscriptionInformation.getSpotName());
            //月份
            accountClose.setMonth(DateUtil.getLast12Months(1));
            //微信支付金额
            accountClose.setWechat(0);
            //储值支付金额
            accountClose.setSaving(0);
            //储值抵扣+微信支付金额
            accountClose.setSavingWechat(0);
            //押金抵扣支付金额
            accountClose.setDeposit(0);
            //总分润基数金额
            accountClose.setTotal(0);
            //手续费
            accountClose.setCharge(subscriptionInformation.getCharge());
            //可分润金额
            accountClose.setTotalCharge(0);
            //分润比例
            accountClose.setProportion(subscriptionInformation.getProportion());
            //分润金额
            accountClose.setTotalProportion(0);
            //税率
            accountClose.setTax(subscriptionInformation.getTax());
            //税后收入金额
            accountClose.setTotalTax(0);
            //税后支出金额
            accountClose.setTotalTaxEx(0);
            //机器人总数量
            Long robotCount = sysRobotMapper.getSpotIdByRobotCount(subscriptionInformation.getSpotId());
            accountClose.setRobotCount(robotCount);
            //景区承担配件申请费用
            Double sumPartPrice =0d;
//            Double sumPartPrice = sysRobotErrorRecordsMapper.getSumPartPrice(subscriptionInformation.getSpotId());
//            if (StringUtils.isEmpty(sumPartPrice)){
//                sumPartPrice = 0d;
//            }
            accountClose.setParts(sumPartPrice);
            //景区承担上报故障维修费用
            Double sumUpkeepCost = 0d;
//            Double sumUpkeepCost = sysRobotErrorRecordsMapper.getSumUpkeepCost(subscriptionInformation.getSpotId());
//            if (StringUtils.isEmpty(sumUpkeepCost)){
//                sumUpkeepCost = 0d;
//            }
            accountClose.setFault(sumUpkeepCost);
            //总配件维修金额
            Double spotIdAndTimeLikeBySpotRepairMoney = 0d;
//            Double spotIdAndTimeLikeBySpotRepairMoney = sysRobotErrorRecordsMapper.getSpotIdAndTimeLikeBySpotRepairMoney(subscriptionInformation.getSpotId(), accountClose.getMonth());
//            if (StringUtils.isEmpty(spotIdAndTimeLikeBySpotRepairMoney)){
//                spotIdAndTimeLikeBySpotRepairMoney = 0d;
//            }
            accountClose.setRepair(spotIdAndTimeLikeBySpotRepairMoney);
            //付款金额
            accountClose.setMoneyIn(0);
            //收款金额
            accountClose.setMoneyOut(0);
            //类型
            accountClose.setType(subscriptionInformation.getRevenueExpenditure());
            //创建时间
            accountClose.setCreateTime(DateUtil.currentDateTime());

            AccountClose accountCloseOle =  mapper.getSpotIdAndCompanyIdAndDateByAccountClose(subscriptionInformation.getSpotId(),subscriptionInformation.getCompanyId(),accountClose.getMonth());
            if (StringUtils.isEmpty(accountCloseOle)){
                accountClose.setId(IdUtils.getSeqId());
                mapper.add(accountClose);
            }else{
                continue;
            }
        }

    }

    /**
     * 定时统计结算流水(新)
     */
    @Override
    public void spotAccountCloseTimedStatisticsNew(){

//        String last12Months = DateUtil.getLast12Months(1);
//
//        List<AccountClose> accountCloseList = mapper.getDateByAccountClose(last12Months);
//
//        for (AccountClose accountClose : accountCloseList) {
//
//
//
//
//
//        }




    }


}
