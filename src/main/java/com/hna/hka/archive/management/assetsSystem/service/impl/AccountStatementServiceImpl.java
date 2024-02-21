package com.hna.hka.archive.management.assetsSystem.service.impl;

import com.hna.hka.archive.management.assetsSystem.dao.AccountCloseMapper;
import com.hna.hka.archive.management.assetsSystem.dao.AccountStatementMapper;
import com.hna.hka.archive.management.assetsSystem.dao.SubscriptionInformationMapper;
import com.hna.hka.archive.management.assetsSystem.model.AccountClose;
import com.hna.hka.archive.management.assetsSystem.model.AccountStatement;
import com.hna.hka.archive.management.assetsSystem.model.SubscriptionInformation;
import com.hna.hka.archive.management.assetsSystem.service.AccountStatementService;
import com.hna.hka.archive.management.system.dao.SysRobotMapper;
import com.hna.hka.archive.management.system.dao.SysScenicSpotMapper;
import com.hna.hka.archive.management.system.model.SysScenicSpot;
import com.hna.hka.archive.management.system.model.SysUsers;
import com.hna.hka.archive.management.system.util.DateUtil;
import com.hna.hka.archive.management.system.util.IdUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: rc
 * @description: 流水对账单
 * @author: zhaoxianglong
 * @create: 2021-09-09 10:26
 **/
@Service
public class AccountStatementServiceImpl implements AccountStatementService {

    @Autowired
    private AccountStatementMapper mapper;

    @Autowired
    private AccountCloseMapper accountCloseMapper;
    @Autowired
    private SubscriptionInformationMapper subscriptionInformationMapper;

    @Autowired
    private SysRobotMapper sysRobotMapper;
    @Autowired
    private SysScenicSpotMapper sysScenicSpotMapper;
    @Override
    public List<AccountStatement> list(Long company, Long spot, String beginDate, String endDate, String paymentType, Integer orderStatus, Integer pageNum, Integer pageSize) throws Exception {
        SysUsers user = (SysUsers) SecurityUtils.getSubject().getPrincipal();
        String[] split = (paymentType == null ? null : paymentType.split(","));
        Integer beginSize = (beginDate != null ? beginDate.length() : 0);
        Integer endSize = (endDate != null ? endDate.length() : 0);
        return mapper.list(user.getUserId(),company , spot , beginDate , endDate , split , orderStatus , pageNum , pageSize , beginSize , endSize);
    }

    @Override
    public Map<String, Object> getCount(Long company, Long spot, String beginDate, String endDate, String paymentType, Integer orderStatus) throws Exception {
        SysUsers user = (SysUsers) SecurityUtils.getSubject().getPrincipal();
        String[] split = (paymentType == null ? null : paymentType.split(","));
        Integer beginSize = (beginDate != null ? beginDate.length() : 0);
        Integer endSize = (endDate != null ? endDate.length() : 0);
        return mapper.getCount(user.getUserId(),company , spot , beginDate , endDate , split , orderStatus , beginSize , endSize);
    }

    @Override
    public List<AccountStatement> companyList(String spotId) throws Exception {
        SysUsers user = (SysUsers) SecurityUtils.getSubject().getPrincipal();
        String[] split = (spotId != null ? spotId.split(",") : null);
        return mapper.companyList(user.getUserId() , split);
    }

    @Override
    public List<AccountStatement> spotList(Long companyId) throws Exception {
        SysUsers user = (SysUsers) SecurityUtils.getSubject().getPrincipal();
        return mapper.spotList(user.getUserId() , companyId);
    }

    @Override
    public HashMap<String, Object> preview(Long company, Long spot, String beginDate, String endDate, String paymentType,String paymentStatus) {
        DecimalFormat df = new DecimalFormat("0.00");
        SysUsers user = (SysUsers) SecurityUtils.getSubject().getPrincipal();
        String[] split = (paymentType == null ? null : paymentType.split(","));
        Integer beginSize = (beginDate != null ? beginDate.length() : 0);
        Integer endSize = (endDate != null ? endDate.length() : 0);
        HashMap<String, Object> preview = mapper.preview(user.getUserId(), company, spot, beginDate, endDate, split, beginSize, endSize, paymentStatus);
        if (StringUtils.isEmpty(preview)){
            return preview;
        }else{

            String cooperation = (String) preview.get("收款账户");
            String CHARGE =(String) preview.get("手续费率");
            String[] split1 = CHARGE.split("%");
            String PROPORTION = (String) preview.get("分润比例");
            String[] split2 = PROPORTION.split("%");
            Double  a = Double.parseDouble(split1[0]) / 100;
            BigDecimal A = new BigDecimal(a.toString());

            Double  b = Double.parseDouble(split2[0]) / 100;
            BigDecimal B = new BigDecimal(b.toString());

//            Double c = (Double) preview.get("可分润金额");
//            BigDecimal C = new BigDecimal(c);
//
//            Double d = (Double) preview.get("分润金额");
//            BigDecimal D = new BigDecimal(d);

            Double e = (Double) preview.get("流水");
            BigDecimal E = new BigDecimal(e.toString());




            if (StringUtils.isEmpty(CHARGE) || StringUtils.isEmpty(e)){
                return preview;
            }else{

                SubscriptionInformation subscriptionInformation = subscriptionInformationMapper.selectCompanyNameById(cooperation);

                //手续费
                BigDecimal multiply = E.multiply(A);
                multiply = new BigDecimal(df.format(multiply));
                //税
                BigDecimal divide = null   ;
                if (subscriptionInformation.getTaxRateMethod() == 1){

                     divide = E.subtract(multiply).divide(new BigDecimal((1 + subscriptionInformation.getTax()) ),2,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal( subscriptionInformation.getTax()));


                }else if (subscriptionInformation.getTaxRateMethod() == 2){

                    divide = E.subtract(multiply).multiply(new BigDecimal(subscriptionInformation.getTax()));

                }else if (subscriptionInformation.getTaxRateMethod() == 3){
                    divide =  E.subtract(multiply).divide(new BigDecimal(1.06 ),2,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(subscriptionInformation.getTax()));
                }

                preview.put("税",Double.parseDouble(df.format(divide.doubleValue())));

                //可分润金额
                BigDecimal subtract = E.subtract(multiply).subtract(divide);

                preview.put("可分润金额",Double.parseDouble(df.format(subtract)));

                //分润金额
                BigDecimal multiply1 = subtract.multiply(B);
                preview.put("分润金额",Double.parseDouble(df.format(multiply1)));

                return preview;
            }
        }
    }

    /**
     * 核实
     * 张
     * @param company
     * @param spot
     * @param beginDate
     * @param endDate
     * @param paymentType
     * @param orderStatus
     * @return
     */
    @Override
    public int verify(Long company, Long spot, String beginDate, String endDate, String paymentType, Integer orderStatus, Long userId, String userName) {

        DecimalFormat df = new DecimalFormat("#.00");
        SysUsers user = (SysUsers) SecurityUtils.getSubject().getPrincipal();
        String[] split = (paymentType == null ? null : paymentType.split(","));
        Integer beginSize = (beginDate != null ? beginDate.length() : 0);
        Integer endSize = (endDate != null ? endDate.length() : 0);

        String[] splitType = paymentType.split(",");

        AccountClose accountClose = new AccountClose();
        //微信支付金额
        double weChatPayment = 0;
        //储值支付金额
        double saving = 0;
        //储值抵扣支付金额
        double savingWechatPayment = 0;
        //押金抵扣支付金额
        double depositPayment = 0;

        //循环支付方式
        for (String s : splitType) {
            Double money = mapper.verifyN(user.getUserId(), company, spot, beginDate, endDate, split, orderStatus,s, beginSize , endSize);
            if (StringUtils.isEmpty(money)){
                money = 0d;
            }
            if ("WECHAT".equals(s)){
                weChatPayment = money;
            }else if("SAVING".equals(s)){
                saving = money;
            }else if("SAVING_WECHAT".equals(s)){
                savingWechatPayment = money;
            }else if("DEPOSIT".equals(s)){
                depositPayment = money;
            }
        }
        List<SubscriptionInformation> subscriptionInformationList = subscriptionInformationMapper.selectSpotIdByContract(spot);

        //景区获取机器人数量
        Long robotCount = sysRobotMapper.getSpotIdByRobotCount(spot);
        //获取景区名称
        SysScenicSpot sysScenicSpot = sysScenicSpotMapper.selectByPrimaryKey(spot);

        //总分润基数
        double priceA = 0 ;

        for (SubscriptionInformation subscriptionInformation : subscriptionInformationList) {
            accountClose = new AccountClose();
            accountClose.setId(IdUtils.getSeqId());
            //收款公司id
            accountClose.setCompanyId(subscriptionInformation.getCompanyId());
            //收款单位名称
            accountClose.setCompanyName(subscriptionInformation.getCompanyName());
            //景区id
            accountClose.setSpotId(spot);
            //景区名称
            accountClose.setSpotName(sysScenicSpot.getScenicSpotName());
            //月份
//            accountClose.setMonth(DateUtil.getLast12Months(1));
            accountClose.setMonth(beginDate);
            //微信支付金额
            accountClose.setWechat(weChatPayment);
            //储值支付金额
            accountClose.setSaving(saving);
            //微信+储值支付金额
            accountClose.setSavingWechat(savingWechatPayment);
            //押金抵扣金额
            accountClose.setDeposit(depositPayment);
            //总分润基数金额
            if (subscriptionInformation.getWechat() == 1){
                priceA = priceA + weChatPayment;
            }
            if (subscriptionInformation.getSaving() == 1){
                priceA = priceA + saving;
            }
            if (subscriptionInformation.getDeposit() == 1){
                priceA = priceA + depositPayment;
            }
            if (subscriptionInformation.getSavingWechat() == 1){
                priceA = priceA + savingWechatPayment;
            }
            priceA = Double.parseDouble(df.format(priceA));
            accountClose.setTotal(priceA);
            //手续费
            accountClose.setCharge(subscriptionInformation.getCharge());
            //可分润金额(手续费已扣)
            priceA = priceA - (priceA * subscriptionInformation.getCharge());
            priceA = Double.parseDouble(df.format(priceA));
            accountClose.setTotalCharge(priceA);
            //分润比例
            accountClose.setProportion(subscriptionInformation.getProportion());
            //分润金额,手续费已扣、未扣税点
             priceA = priceA * subscriptionInformation.getProportion();
            priceA = Double.parseDouble(df.format(priceA));
            accountClose.setTotalProportion(priceA);
            //税率
            accountClose.setTax(subscriptionInformation.getTax());
            //税后分润金额
            if (subscriptionInformation.getRevenueExpenditure() == 1){
                priceA = priceA - (priceA * subscriptionInformation.getTax());
                accountClose.setTotalTax(priceA);
                accountClose.setTotalTaxEx(0);
            }else if (subscriptionInformation.getRevenueExpenditure() == 2){
                priceA = priceA - (priceA * subscriptionInformation.getTax());
                accountClose.setTotalTaxEx(priceA);
                accountClose.setTotalTax(0);
            }
            accountClose.setPsStatus(1);
            //机器人总数
            accountClose.setRobotCount(robotCount);
            accountClose.setType(subscriptionInformation.getRevenueExpenditure());
            //流水核实人id
            accountClose.setAccountCheckId(userId);
            accountClose.setAccountCheckName(userName);
            AccountClose accountCloseO =  mapper.getSpotIdAndCompanyIdAndDateByRunningData(spot,subscriptionInformation.getCompanyId(),beginDate);
             if (StringUtils.isEmpty(accountCloseO)){
                 accountCloseMapper.add(accountClose);
             }else{
                 accountClose.setId(accountCloseO.getId());
                 accountCloseMapper.edit(accountClose);
             }
        }
        return 1;
    }

    /**
     * 是否核实
     * @param company
     * @param spot
     * @param beginDate
     * @param endDate
     * @param paymentType
     * @param orderStatus
     * @return
     */
    @Override
    public int isVerify(Long company, Long spot, String beginDate, String endDate, String paymentType, Integer orderStatus) {

        AccountClose accountClose = accountCloseMapper.selectSpotAndCompanyAndDate(spot,company,beginDate);

        if (StringUtils.isEmpty(accountClose)){
            return 0; //未核实
        }else{
            return 1; //已核实
        }
    }

//    @Override
//    public List<HashMap<String, Object>> previewNew(Long company, Long spot, String beginDate, String endDate, String paymentType, String paymentStatus) {
//        SysUsers user = (SysUsers) SecurityUtils.getSubject().getPrincipal();
//        String[] split = (paymentType == null ? null : paymentType.split(","));
//        Integer beginSize = (beginDate != null ? beginDate.length() : 0);
//        Integer endSize = (endDate != null ? endDate.length() : 0);
//        return mapper.previewNew(user.getUserId() , company , spot , beginDate , endDate , split , beginSize , endSize,paymentStatus);
//
//
//    }
}
