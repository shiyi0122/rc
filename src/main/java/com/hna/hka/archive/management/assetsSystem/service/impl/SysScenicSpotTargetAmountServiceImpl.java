package com.hna.hka.archive.management.assetsSystem.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hna.hka.archive.management.appSystem.dao.SysRobotErrorRecordsMapper;
import com.hna.hka.archive.management.assetsSystem.dao.SubscriptionInformationMapper;
import com.hna.hka.archive.management.assetsSystem.dao.SysRobotSoftAssetInformationMapper;
import com.hna.hka.archive.management.assetsSystem.dao.SysScenicSpotTargetAmountAscriptionMapper;
import com.hna.hka.archive.management.assetsSystem.dao.SysScenicSpotTargetAmountMapper;
import com.hna.hka.archive.management.assetsSystem.model.*;
import com.hna.hka.archive.management.assetsSystem.service.CooperativeCompanyService;
import com.hna.hka.archive.management.assetsSystem.service.SubscriptionInformationService;
import com.hna.hka.archive.management.assetsSystem.service.SysScenicSpotTargetAmountService;
import com.hna.hka.archive.management.system.dao.SysRobotCompanyAscriptionMapper;
import com.hna.hka.archive.management.system.dao.SysRobotMapper;
import com.hna.hka.archive.management.system.dao.SysScenicSpotAscriptionCompanyMapper;
import com.hna.hka.archive.management.system.model.SysRobot;
import com.hna.hka.archive.management.system.model.SysRobotCompanyAscription;
import com.hna.hka.archive.management.system.model.SysScenicSpotAscriptionCompany;
import com.hna.hka.archive.management.system.util.DateUtil;
import com.hna.hka.archive.management.system.util.IdUtils;
import com.hna.hka.archive.management.system.util.PageDataResult;
import com.hna.hka.archive.management.system.util.ToolUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ProjectName: rc
 * @Package: com.hna.hka.archive.management.assetsSystem.service.impl
 * @ClassName: SysScenicSpotTargetAmountServiceImpl
 * @Author: 郭凯
 * @Description: 景区目标金额设置业务层（实现）
 * @Date: 2021/7/19 14:17
 * @Version: 1.0
 */
@Service
@Transactional
public class SysScenicSpotTargetAmountServiceImpl implements SysScenicSpotTargetAmountService {

    @Autowired
    private SysScenicSpotTargetAmountMapper sysScenicSpotTargetAmountMapper;

    @Autowired
    private SysScenicSpotTargetAmountAscriptionMapper sysScenicSpotTargetAmountAscriptionMapper;

    @Autowired
    private SysRobotMapper sysRobotMapper;

    @Autowired
    private SubscriptionInformationMapper subscriptionInformationMapper;

    @Autowired
    private SysRobotSoftAssetInformationMapper sysRobotSoftAssetInformationMapper;

    @Autowired
    private SysRobotCompanyAscriptionMapper sysRobotCompanyAscriptionMapper;

    @Autowired
    private SysRobotErrorRecordsMapper sysRobotErrorRecordsMapper;

    @Autowired
    private SysScenicSpotAscriptionCompanyMapper sysScenicSpotAscriptionCompanyMapper;

    @Autowired
    private CooperativeCompanyService cooperativeCompanyService;




    @Override
    /**
     * @Method getTargetAmountList
     * @Author 郭凯
     * @Version  1.0
     * @Description 查询景区目标金额列表
     * @Return com.hna.hka.archive.management.system.util.PageDataResult
     * @Date 2021/7/19 14:36
     */
    public PageDataResult getTargetAmountList(Integer pageNum, Integer pageSize, Map<String, Object> search) {
        PageDataResult pageDataResult = new PageDataResult();
        PageHelper.startPage(pageNum, pageSize);
        List<SysScenicSpotTargetAmount> sysScenicSpotTargetAmountList = sysScenicSpotTargetAmountMapper.getTargetAmountList(search);
        for (SysScenicSpotTargetAmount sysScenicSpotTargetAmount : sysScenicSpotTargetAmountList) {
           sysScenicSpotTargetAmount.setRobotCostList(sysScenicSpotTargetAmountAscriptionMapper.getSpotTargetAmountAscriptionByType(sysScenicSpotTargetAmount.getTargetAmountId(),"1"));
           sysScenicSpotTargetAmount.setOperateCostList(sysScenicSpotTargetAmountAscriptionMapper.getSpotTargetAmountAscriptionByType(sysScenicSpotTargetAmount.getTargetAmountId(),"2"));
           sysScenicSpotTargetAmount.setStopMarketCostList(sysScenicSpotTargetAmountAscriptionMapper.getSpotTargetAmountAscriptionByType(sysScenicSpotTargetAmount.getTargetAmountId(),"3"));
           sysScenicSpotTargetAmount.setRentList(sysScenicSpotTargetAmountAscriptionMapper.getSpotTargetAmountAscriptionByType(sysScenicSpotTargetAmount.getTargetAmountId(),"4"));
           sysScenicSpotTargetAmount.setMaintainCostList(sysScenicSpotTargetAmountAscriptionMapper.getSpotTargetAmountAscriptionByType(sysScenicSpotTargetAmount.getTargetAmountId(),"5"));
           sysScenicSpotTargetAmount.setRobotExFactoryList(sysScenicSpotTargetAmountAscriptionMapper.getSpotTargetAmountAscriptionByType(sysScenicSpotTargetAmount.getTargetAmountId(),"6"));
        }
        if (sysScenicSpotTargetAmountList.size() > 0){
            PageInfo<SysScenicSpotTargetAmount> pageInfo = new PageInfo<>(sysScenicSpotTargetAmountList);
            pageDataResult.setList(pageInfo.getList());
            pageDataResult.setTotals((int) pageInfo.getTotal());
        }
        return pageDataResult;
    }

    /**
     * @Method addTargetAmount
     * @Author 郭凯
     * @Version  1.0
     * @Description 新增景区目标金额
     * @Return int
     * @Date 2021/7/19 15:52
     */
    @Override
    public int addTargetAmount(SysScenicSpotTargetAmount sysScenicSpotTargetAmount) {


        SysScenicSpotTargetAmount targetAmount = sysScenicSpotTargetAmountMapper.getTargetAmountById(sysScenicSpotTargetAmount);
        if (ToolUtil.isNotEmpty(targetAmount)){
            return 2;
        }
        int i1 = 0;
        Long seqId = IdUtils.getSeqId();
        sysScenicSpotTargetAmount.setTargetAmountId(seqId);
        sysScenicSpotTargetAmount.setCreateDate(DateUtil.currentDateTime());
        sysScenicSpotTargetAmount.setUpdateDate(DateUtil.currentDateTime());
        int i = sysScenicSpotTargetAmountMapper.insertSelective(sysScenicSpotTargetAmount);

        //机器人折旧成本承担方添加
        SysScenicSpotTargetAmountAscription[] robotCostList = sysScenicSpotTargetAmount.getRobotCostList();

        if (!StringUtils.isEmpty(robotCostList) && robotCostList.length>0){
            for (SysScenicSpotTargetAmountAscription spotTargetAmountAscription : robotCostList) {
                spotTargetAmountAscription.setTargetAmountAscriptionId(IdUtils.getSeqId());
                spotTargetAmountAscription.setType("1");
                spotTargetAmountAscription.setTargetAmountId(seqId);
                spotTargetAmountAscription.setCreateTime(DateUtil.currentDateTime());
                if (StringUtils.isEmpty( spotTargetAmountAscription.getCommitmentAmount())){
                    spotTargetAmountAscription.setCommitmentAmount("0") ;
                }
                i1 = sysScenicSpotTargetAmountAscriptionMapper.insertSelective(spotTargetAmountAscription);
            }
        }
        //运营成本承担方添加
        SysScenicSpotTargetAmountAscription[] operateCostList = sysScenicSpotTargetAmount.getOperateCostList();
        if (!StringUtils.isEmpty(operateCostList) && operateCostList.length>0){
            for (SysScenicSpotTargetAmountAscription spotTargetAmountAscription : operateCostList) {
                spotTargetAmountAscription.setTargetAmountAscriptionId(IdUtils.getSeqId());
                spotTargetAmountAscription.setType("2");
                spotTargetAmountAscription.setTargetAmountId(seqId);
                spotTargetAmountAscription.setCreateTime(DateUtil.currentDateTime());
                if (StringUtils.isEmpty( spotTargetAmountAscription.getCommitmentAmount())){
                    spotTargetAmountAscription.setCommitmentAmount("0");
                }
                i1 = sysScenicSpotTargetAmountAscriptionMapper.insertSelective(spotTargetAmountAscription);

            }
        }
        //景区营销成本承担方添加
        SysScenicSpotTargetAmountAscription[] stopMarketCostList = sysScenicSpotTargetAmount.getStopMarketCostList();
        if (!StringUtils.isEmpty(stopMarketCostList) && stopMarketCostList.length>0){
            for (SysScenicSpotTargetAmountAscription spotTargetAmountAscription : stopMarketCostList) {
                spotTargetAmountAscription.setTargetAmountAscriptionId(IdUtils.getSeqId());
                spotTargetAmountAscription.setType("3");
                spotTargetAmountAscription.setTargetAmountId(seqId);
                spotTargetAmountAscription.setCreateTime(DateUtil.currentDateTime());
                if (StringUtils.isEmpty( spotTargetAmountAscription.getCommitmentAmount())){
                    spotTargetAmountAscription.setCommitmentAmount("0");
                }
                i1 = sysScenicSpotTargetAmountAscriptionMapper.insertSelective(spotTargetAmountAscription);

            }
        }
        //租金成本承担方添加
        SysScenicSpotTargetAmountAscription[] rentList = sysScenicSpotTargetAmount.getRentList();
        if (!StringUtils.isEmpty(rentList) && rentList.length>0){
            for (SysScenicSpotTargetAmountAscription spotTargetAmountAscription : rentList) {
                spotTargetAmountAscription.setTargetAmountAscriptionId(IdUtils.getSeqId());
                spotTargetAmountAscription.setType("4");
                spotTargetAmountAscription.setTargetAmountId(seqId);
                spotTargetAmountAscription.setCreateTime(DateUtil.currentDateTime());
                if (StringUtils.isEmpty( spotTargetAmountAscription.getCommitmentAmount())){
                    spotTargetAmountAscription.setCommitmentAmount("0");
                }
                i1 = sysScenicSpotTargetAmountAscriptionMapper.insertSelective(spotTargetAmountAscription);
            }
        }
        //维养成本承担方添加
        SysScenicSpotTargetAmountAscription[] marketCostList = sysScenicSpotTargetAmount.getMaintainCostList();
        if (!StringUtils.isEmpty(marketCostList) && marketCostList.length>0){
            for (SysScenicSpotTargetAmountAscription spotTargetAmountAscription : marketCostList) {
                spotTargetAmountAscription.setTargetAmountAscriptionId(IdUtils.getSeqId());
                spotTargetAmountAscription.setType("5");
                spotTargetAmountAscription.setTargetAmountId(seqId);
                spotTargetAmountAscription.setCreateTime(DateUtil.currentDateTime());
                if (StringUtils.isEmpty( spotTargetAmountAscription.getCommitmentAmount())){
                    spotTargetAmountAscription.setCommitmentAmount("0");
                }
                i1 = sysScenicSpotTargetAmountAscriptionMapper.insertSelective(spotTargetAmountAscription);

            }
        }

        //机器人出厂成本添加
//        SysScenicSpotTargetAmountAscription[] robotExFactoryList = sysScenicSpotTargetAmount.getRobotExFactoryList();
//        if ( !StringUtils.isEmpty(robotExFactoryList[0].getUndertakerId()) && robotExFactoryList.length>0) {
//            for (SysScenicSpotTargetAmountAscription spotTargetAmountAscription : robotExFactoryList) {
//                spotTargetAmountAscription.setTargetAmountAscriptionId(IdUtils.getSeqId());
//                spotTargetAmountAscription.setType("6");
//                spotTargetAmountAscription.setTargetAmountId(seqId);
//                spotTargetAmountAscription.setCreateTime(DateUtil.currentDateTime());
//                if (StringUtils.isEmpty( spotTargetAmountAscription.getCommitmentAmount())){
//                    spotTargetAmountAscription.setCommitmentAmount("0");
//                }
//                i1 = sysScenicSpotTargetAmountAscriptionMapper.insertSelective(spotTargetAmountAscription);
//            }
//        }
            return i1;
    }

    /**
     * @Method delTargetAmount
     * @Author 郭凯
     * @Version  1.0
     * @Description 景区目标金额删除
     * @Return int
     * @Date 2021/7/19 16:40
     */
    @Override
    public int delTargetAmount(Long targetAmountId) {
        int i = sysScenicSpotTargetAmountMapper.deleteByPrimaryKey(targetAmountId);

        int i1= sysScenicSpotTargetAmountAscriptionMapper.deleteByTargetAmountId(targetAmountId);
        return i ;
    }

    /**
     * @Method editTargetAmount
     * @Author 郭凯
     * @Version  1.0
     * @Description 景区目标金额编辑
     * @Return int
     * @Date 2021/7/19 18:11
     */
    @Override
    public int editTargetAmount(SysScenicSpotTargetAmount sysScenicSpotTargetAmount) {
        sysScenicSpotTargetAmount.setUpdateDate(DateUtil.currentDateTime());
        int i1 = 0;
        //机器人成本承担方修改
        Double a = 0d;
        SysScenicSpotTargetAmountAscription[] robotCostList = sysScenicSpotTargetAmount.getRobotCostList();
        if (!StringUtils.isEmpty(robotCostList) && robotCostList.length>0){
            for (SysScenicSpotTargetAmountAscription spotTargetAmountAscription : robotCostList) {
                spotTargetAmountAscription.setType("1");
                if (!StringUtils.isEmpty(spotTargetAmountAscription.getCommitmentAmount())){
                    a = a + Double.parseDouble(spotTargetAmountAscription.getCommitmentAmount());
                }
                i1 = sysScenicSpotTargetAmountAscriptionMapper.updateByPrimaryKeySelective(spotTargetAmountAscription);
            }
        }
        sysScenicSpotTargetAmount.setRobotCost(a.toString());
        //运营成本承担方修改
        Double b = 0d;
        SysScenicSpotTargetAmountAscription[] operateCostList = sysScenicSpotTargetAmount.getOperateCostList();
        if ( !StringUtils.isEmpty(operateCostList) && operateCostList.length>0){
            for (SysScenicSpotTargetAmountAscription spotTargetAmountAscription : operateCostList) {
                spotTargetAmountAscription.setType("2");
                if (!StringUtils.isEmpty(spotTargetAmountAscription.getCommitmentAmount())) {
                    b = b + Double.parseDouble(spotTargetAmountAscription.getCommitmentAmount()) ;
                }
                i1 = sysScenicSpotTargetAmountAscriptionMapper.updateByPrimaryKeySelective(spotTargetAmountAscription);
            }
        }
        sysScenicSpotTargetAmount.setOperateCost(b.toString());
        //景区营销成本承担方修改
        Double c = 0d;
        SysScenicSpotTargetAmountAscription[] stopMarketCostList = sysScenicSpotTargetAmount.getStopMarketCostList();
        if (!StringUtils.isEmpty(stopMarketCostList)  && stopMarketCostList.length>0){
            for (SysScenicSpotTargetAmountAscription spotTargetAmountAscription : stopMarketCostList) {
                spotTargetAmountAscription.setType("3");
                if (!StringUtils.isEmpty(spotTargetAmountAscription.getCommitmentAmount())){
                    c = c + Double.parseDouble(spotTargetAmountAscription.getCommitmentAmount());
                }
                i1 = sysScenicSpotTargetAmountAscriptionMapper.updateByPrimaryKeySelective(spotTargetAmountAscription);
            }
        }
        sysScenicSpotTargetAmount.setSpotMarketCost(c.toString());
        //租金成本承担方修改
        Double d = 0d;
        SysScenicSpotTargetAmountAscription[] rentList = sysScenicSpotTargetAmount.getRentList();
        if (!StringUtils.isEmpty(rentList) && rentList.length>0){
            for (SysScenicSpotTargetAmountAscription spotTargetAmountAscription : rentList) {
                spotTargetAmountAscription.setType("4");
                if (!StringUtils.isEmpty(spotTargetAmountAscription.getCommitmentAmount())) {
                    d = d + Double.parseDouble(spotTargetAmountAscription.getCommitmentAmount());
                }
                i1 = sysScenicSpotTargetAmountAscriptionMapper.updateByPrimaryKeySelective(spotTargetAmountAscription);
            }
        }
        sysScenicSpotTargetAmount.setRent(d.toString());
        //维养成本承担方修改
        Double e = 0d;
        SysScenicSpotTargetAmountAscription[] maintainCostList = sysScenicSpotTargetAmount.getMaintainCostList();
        if (!StringUtils.isEmpty(maintainCostList) && maintainCostList.length>0){
            for (SysScenicSpotTargetAmountAscription spotTargetAmountAscription : maintainCostList) {
                spotTargetAmountAscription.setType("5");
                if (!StringUtils.isEmpty(spotTargetAmountAscription.getCommitmentAmount())) {
                    e = e + Double.parseDouble(spotTargetAmountAscription.getCommitmentAmount());
                }
                i1 = sysScenicSpotTargetAmountAscriptionMapper.updateByPrimaryKeySelective(spotTargetAmountAscription);
            }
        }
        sysScenicSpotTargetAmount.setMaintainCost(e.toString());
        //机器人出厂成本
        Double f = 0d;
        SysScenicSpotTargetAmountAscription[] robotExFactoryList = sysScenicSpotTargetAmount.getRobotExFactoryList();
        if (!StringUtils.isEmpty(robotExFactoryList) && robotExFactoryList.length>0){
            for (SysScenicSpotTargetAmountAscription sysScenicSpotTargetAmountAscription : robotExFactoryList) {
                sysScenicSpotTargetAmountAscription.setType("6");
                if (!StringUtils.isEmpty(sysScenicSpotTargetAmountAscription.getCommitmentAmount())){
                    f = f + Double.parseDouble(sysScenicSpotTargetAmountAscription.getCommitmentAmount());
                }
                i1 =  sysScenicSpotTargetAmountAscriptionMapper.updateByPrimaryKeySelective(sysScenicSpotTargetAmountAscription);
            }
        }
        sysScenicSpotTargetAmount.setRobotExFactory(f.toString());
        int i = sysScenicSpotTargetAmountMapper.updateByPrimaryKeySelective(sysScenicSpotTargetAmount);

        return i;
    }

    /**
     * 导出
     * @return
     */
    @Override
    public List<SysScenicSpotTargetAmount> getTargetAmountExel(Map<String, Object> search) {

        List<SysScenicSpotTargetAmount> targetAmountList = sysScenicSpotTargetAmountMapper.getTargetAmountListExel(search);

        for (SysScenicSpotTargetAmount sysScenicSpotTargetAmount : targetAmountList) {
            //运营人员
            SysScenicSpotTargetAmountAscription[] spotTargetAmountAscriptionByType = sysScenicSpotTargetAmountAscriptionMapper.getSpotTargetAmountAscriptionByType(sysScenicSpotTargetAmount.getTargetAmountId(), "2");
            //营销
            SysScenicSpotTargetAmountAscription[] spotTargetAmountAscriptionByType1 = sysScenicSpotTargetAmountAscriptionMapper.getSpotTargetAmountAscriptionByType(sysScenicSpotTargetAmount.getTargetAmountId(), "3");
            //租金
            SysScenicSpotTargetAmountAscription[] spotTargetAmountAscriptionByType2 = sysScenicSpotTargetAmountAscriptionMapper.getSpotTargetAmountAscriptionByType(sysScenicSpotTargetAmount.getTargetAmountId(), "4");
            //维养
            SysScenicSpotTargetAmountAscription[] spotTargetAmountAscriptionByType3 = sysScenicSpotTargetAmountAscriptionMapper.getSpotTargetAmountAscriptionByType(sysScenicSpotTargetAmount.getTargetAmountId(), "5");
            //机器人折旧
            SysScenicSpotTargetAmountAscription[] spotTargetAmountAscriptionByType4 = sysScenicSpotTargetAmountAscriptionMapper.getSpotTargetAmountAscriptionByType(sysScenicSpotTargetAmount.getTargetAmountId(), "1");

            String name = "" ;
            String name1 = "";
            String name2 = "";
            String name3 = "";
            String name4 = "";
            for (int i = 0; i < spotTargetAmountAscriptionByType.length; i++) {

                if (StringUtils.isEmpty(spotTargetAmountAscriptionByType[i].getUndertakerId())){
                    name = "无";
                    break;
                }


                if (name == ""){
                    if(!StringUtils.isEmpty(spotTargetAmountAscriptionByType[i].getUndertakerName())){
                        name = spotTargetAmountAscriptionByType[i].getUndertakerName() +":"+ spotTargetAmountAscriptionByType[i].getCommitmentAmount();
                    }else {
                        name = spotTargetAmountAscriptionByType[i].getCompanyNameSubject() +":"+ spotTargetAmountAscriptionByType[i].getCommitmentAmount();
                    }
                }else{
                    if(!StringUtils.isEmpty(spotTargetAmountAscriptionByType[i].getUndertakerName())){
                        name= name+","+spotTargetAmountAscriptionByType[i].getUndertakerName() +":"+ spotTargetAmountAscriptionByType[i].getCommitmentAmount();
                    }else{
                        name= name+","+spotTargetAmountAscriptionByType[i].getCompanyNameSubject() +":"+ spotTargetAmountAscriptionByType[i].getCommitmentAmount();
                    }
                }
            }
            sysScenicSpotTargetAmount.setUndertakerName(name);
            for (int i = 0; i < spotTargetAmountAscriptionByType1.length; i++) {

                if (StringUtils.isEmpty(spotTargetAmountAscriptionByType1[i].getUndertakerId())){
                    name1 = "无";
                    break;
                }

                if (name1 == ""){
                    if (!StringUtils.isEmpty(spotTargetAmountAscriptionByType1[i].getUndertakerName())){
                        name1 = spotTargetAmountAscriptionByType1[i].getUndertakerName() +":"+ spotTargetAmountAscriptionByType1[i].getCommitmentAmount();

                    }else{
                        name1 = spotTargetAmountAscriptionByType1[i].getCompanyNameSubject() +":"+ spotTargetAmountAscriptionByType1[i].getCommitmentAmount();
                    }
                }else{
                    if (!StringUtils.isEmpty(spotTargetAmountAscriptionByType1[i].getUndertakerName())){
                        name1= name1+","+spotTargetAmountAscriptionByType1[i].getUndertakerName()+":"+spotTargetAmountAscriptionByType1[i].getCommitmentAmount();
                    }else{
                        name1= name1+","+spotTargetAmountAscriptionByType1[i].getCompanyNameSubject()+":"+spotTargetAmountAscriptionByType1[i].getCommitmentAmount();
                    }
                }
            }
            sysScenicSpotTargetAmount.setSpotMarketName(name1);
            for (int i = 0; i < spotTargetAmountAscriptionByType2.length; i++) {

                if (StringUtils.isEmpty(spotTargetAmountAscriptionByType2[i].getUndertakerId())){
                    name2 = "无";
                    break;
                }

                if (name2 == ""){
                    if (!StringUtils.isEmpty(spotTargetAmountAscriptionByType2[i].getUndertakerName())){
                        name2 = spotTargetAmountAscriptionByType2[i].getUndertakerName()+":"+spotTargetAmountAscriptionByType2[i].getCommitmentAmount();
                    }else{
                        name2 = spotTargetAmountAscriptionByType2[i].getCompanyNameSubject()+":"+spotTargetAmountAscriptionByType2[i].getCommitmentAmount();
                    }
                }else{
                    if (!StringUtils.isEmpty(spotTargetAmountAscriptionByType2[i].getUndertakerName())){
                        name2 = name2 +","+spotTargetAmountAscriptionByType2[i].getUndertakerName()+":"+spotTargetAmountAscriptionByType2[i].getCommitmentAmount();
                    }else{
                        name2 = name2 +","+spotTargetAmountAscriptionByType2[i].getCompanyNameSubject()+":"+spotTargetAmountAscriptionByType2[i].getCommitmentAmount();
                    }
                }
            }
            sysScenicSpotTargetAmount.setRentName(name2);
            for (int i = 0; i < spotTargetAmountAscriptionByType3.length; i++) {

                if (StringUtils.isEmpty(spotTargetAmountAscriptionByType3[i].getUndertakerId())){
                    name3 = "无";
                    break;
                }

                if (name3== ""){
                    if (!StringUtils.isEmpty(spotTargetAmountAscriptionByType3[i].getUndertakerName())){
                        name3 = spotTargetAmountAscriptionByType3[i].getUndertakerName()+":"+spotTargetAmountAscriptionByType3[i].getCommitmentAmount();
                    }else{
                        name3 = spotTargetAmountAscriptionByType3[i].getCompanyNameSubject()+":"+spotTargetAmountAscriptionByType3[i].getCommitmentAmount();
                    }
                }else{
                    if (!StringUtils.isEmpty(spotTargetAmountAscriptionByType3[i].getUndertakerName())){
                        name3 = name3 +","+spotTargetAmountAscriptionByType3[i].getUndertakerName()+":"+spotTargetAmountAscriptionByType3[i].getCommitmentAmount();
                    }else{
                        name3 = name3 +","+spotTargetAmountAscriptionByType3[i].getCompanyNameSubject()+":"+spotTargetAmountAscriptionByType3[i].getCommitmentAmount();
                    }

                }
            }
            sysScenicSpotTargetAmount.setMaintainCostName(name3);

            for (int i = 0; i < spotTargetAmountAscriptionByType4.length; i++) {

                if (StringUtils.isEmpty(spotTargetAmountAscriptionByType4[i].getUndertakerId())){
                    name4 = "无";
                    break;
                }

                if (name4== ""){
                    if (!StringUtils.isEmpty(spotTargetAmountAscriptionByType4[i].getUndertakerName())){
                        name4 = spotTargetAmountAscriptionByType4[i].getUndertakerName()+":"+spotTargetAmountAscriptionByType4[i].getCommitmentAmount();
                    }else{
                        name4 = spotTargetAmountAscriptionByType4[i].getCompanyNameSubject()+":"+spotTargetAmountAscriptionByType4[i].getCommitmentAmount();
                    }
                }else{
                    if (!StringUtils.isEmpty(spotTargetAmountAscriptionByType4[i].getUndertakerName())){
                        name4 = name4 +","+spotTargetAmountAscriptionByType4[i].getUndertakerName()+":"+spotTargetAmountAscriptionByType4[i].getCommitmentAmount();
                    }else{
                        name4 = name4 +","+spotTargetAmountAscriptionByType4[i].getCompanyNameSubject()+":"+spotTargetAmountAscriptionByType4[i].getCommitmentAmount();
                    }
                }
            }
            sysScenicSpotTargetAmount.setRobotCostName(name4);

        }

        return targetAmountList;
    }

    /**
     * 获取机器人总折旧相关信息
     * @param spotId
     * @param date
     * @return
     */
    @Override
    public SysScenicSpotTargetAmount robotTotalDepreciation(Long spotId, String date) {

        DecimalFormat df = new DecimalFormat("0.00");
        SysScenicSpotTargetAmount sysScenicSpotTargetAmount = new SysScenicSpotTargetAmount();

        //景区合作签约的公司
        List<SubscriptionInformation> subscriptionInformations = subscriptionInformationMapper.selectSpotIdByContract(spotId);

        //获取景区中的所有机器人
        List<SysRobot> robotList = sysRobotMapper.getRobotListByScenicSpotId(spotId);

        SysScenicSpotTargetAmountAscription[] robotCosts = new SysScenicSpotTargetAmountAscription[subscriptionInformations.size()+1];
        SysScenicSpotTargetAmountAscription[] maintainCosts = new SysScenicSpotTargetAmountAscription[subscriptionInformations.size()+1];
        List<SysScenicSpotTargetAmountAscription> robotCostList = new ArrayList<>();
        List<SysScenicSpotTargetAmountAscription> maintainCostList = new ArrayList<>();
        //
//
//        for (int i = 0; i < subscriptionInformations.size(); i++) {
//            SubscriptionInformation ll = subscriptionInformations.get(i);
//            if (StringUtils.isEmpty(ll)){
//                continue;
//            }
//            SysScenicSpotTargetAmountAscription sstaa = new SysScenicSpotTargetAmountAscription();
//            sstaa.setUndertakerId(subscriptionInformations.get(i).getCompanyId().toString());
//            sstaa.setUndertakerName(subscriptionInformations.get(i).getCompanyName());
//            robotCosts[i] = sstaa;
//            if (i+1 == subscriptionInformations.size()){
//                //合作主体公司id
//                sstaa = new SysScenicSpotTargetAmountAscription();
//                long subjectId = subscriptionInformations.get(0).getSubjectId();
//                SysScenicSpotAscriptionCompany sysScenicSpotAscriptionCompany = sysScenicSpotAscriptionCompanyMapper.selectByPrimaryKey(subjectId);
//                sstaa.setUndertakerId(sysScenicSpotAscriptionCompany.getCompanyId().toString());
//                sstaa.setUndertakerName(sysScenicSpotAscriptionCompany.getCompanyName());
//                robotCosts[i+1] = sstaa;
//            }
////            maintainCosts[i].setUndertakerId(companyList.get(i).getCompanyId());
//        }
//
        SysScenicSpotTargetAmountAscription sstaa = new SysScenicSpotTargetAmountAscription();

        //总折旧
        Double accumulate = 0d;
        //总维养
        Double maintainSum = 0d;

        try{
            for (SysRobot sysRobot : robotList) {
                sstaa = new SysScenicSpotTargetAmountAscription();
                SysRobotSoftAssetInformation robotSoftAssetInformation = sysRobotSoftAssetInformationMapper.getRobotSoftAssetInformationByRobotId(sysRobot.getRobotId());
                SysRobotCompanyAscription companyId = sysRobotCompanyAscriptionMapper.getAscriptionCompanyId(sysRobot.getRobotId().toString());
                if (robotCostList.size()==0){
                    String dateProduction = robotSoftAssetInformation.getDateProduction();
                    String months = DateUtil.findMonths(dateProduction, date);
                    if (Long.valueOf(months)<=36){
                        //出厂成本
                        Double cost = Double.valueOf(robotSoftAssetInformation.getFactoryCost());
                        //使用年限
                        Double serviceLife = Double.valueOf(robotSoftAssetInformation.getServiceLife())*12;
                        //每月折旧金额
                        Double mouthPiece = cost / serviceLife;
                        mouthPiece = Double.parseDouble(df.format(mouthPiece));
                        //总折旧额相加
                        accumulate = accumulate + mouthPiece;
                        sstaa.setUndertakerId(companyId.getCompanyId());
                        sstaa.setUndertakerName(companyId.getCompanyName());
                        sstaa.setCommitmentAmount(mouthPiece.toString());
                        robotCostList.add(sstaa);
//                        for (int i = 0; i < robotCosts.length; i++) {
//                            if (companyId.getCompanyId().equals(robotCosts[i].getUndertakerId())){
//                                if (StringUtils.isEmpty(robotCosts[i].getCommitmentAmount())){
//                                    robotCosts[i].setCommitmentAmount("0");
//                                }
//                                robotCosts[i].setCommitmentAmount(Double.toString(Double.valueOf(robotCosts[i].getCommitmentAmount())+mouthPiece));
//                                if (StringUtils.isEmpty(robotCosts[i].getCommitmentAmount())){
//                                    robotCosts[i].setCommitmentAmount("0");
//                                }
//                            }
//                        }
                    }
                }else{
                    String dateProduction = robotSoftAssetInformation.getDateProduction();
                    String months = DateUtil.findMonths(dateProduction, date);
                    if (Long.valueOf(months)<=36) {
                        //出厂成本
                        Double cost = Double.valueOf(robotSoftAssetInformation.getFactoryCost());
                        //使用年限
                        Double serviceLife = Double.valueOf(robotSoftAssetInformation.getServiceLife()) * 12;
                        //每月折旧金额
                        Double mouthPiece = cost / serviceLife;
                        mouthPiece = Double.parseDouble(df.format(mouthPiece));
                        //总折旧额相加
                        accumulate = accumulate + mouthPiece;

                        double robotPrice = 0;
                        for (SysScenicSpotTargetAmountAscription sysScenicSpotTargetAmountAscription : robotCostList) {
                            if (sysScenicSpotTargetAmountAscription.getUndertakerId().equals(companyId.getCompanyId())){
                                String commitmentAmount = sysScenicSpotTargetAmountAscription.getCommitmentAmount();
                                robotPrice = Double.parseDouble(commitmentAmount) + mouthPiece;
                                sysScenicSpotTargetAmountAscription.setCommitmentAmount(String.valueOf(df.format(robotPrice)));
                            }else{
                                continue;
                            }
                        }
                        if (robotPrice == 0){
                            sstaa.setUndertakerId(companyId.getCompanyId());
                            sstaa.setUndertakerName(companyId.getCompanyName());
                            sstaa.setCommitmentAmount(df.format(mouthPiece));
                            robotCostList.add(sstaa);
                        }
                    }
                }
            }
            //计算维养成本
            //配件费用
            Double partSum = sysRobotErrorRecordsMapper.getSumPartPrice(spotId,date);
            //维修费用
            Double upkeepSum = sysRobotErrorRecordsMapper.getSumUpkeepCost(spotId,date);

//            List<SubscriptionInformation> subscriptionInformationList = subscriptionInformationMapper.selectSpotIdByContract(spotId);

            if (StringUtils.isEmpty(partSum) && StringUtils.isEmpty(upkeepSum)){
                //此景区没有配件和维修费用
                for (SubscriptionInformation subscriptionInformation : subscriptionInformations) {
                    sstaa = new SysScenicSpotTargetAmountAscription();
                    sstaa.setCommitmentAmount("0");
                    sstaa.setUndertakerName(subscriptionInformation.getCompanyName());
                    sstaa.setUndertakerId(subscriptionInformation.getCompanyId().toString());
                    maintainCostList.add(sstaa);
                }
            }else if (!StringUtils.isEmpty(partSum) && !StringUtils.isEmpty(upkeepSum)){
                //配件费和维修费都有
                    for (SubscriptionInformation subscriptionInformation : subscriptionInformations) {
                        //配件比例
                        double spotManagementScale = subscriptionInformation.getSpotAccessoryRatio();
                        //维修比例
                        double spotServiceScale = subscriptionInformation.getSpotRepairRatio();

                        //景区承担的配件和维修费用
                        double spotPartSum =  partSum * (spotManagementScale/100);
                        double spotUpkeepSum = upkeepSum * (spotServiceScale/100);
                        //总费用
                        Double comSum = spotPartSum + spotUpkeepSum ;
                        sstaa = new SysScenicSpotTargetAmountAscription();
                        sstaa.setCommitmentAmount(comSum.toString());
                        sstaa.setUndertakerName(subscriptionInformation.getCompanyName());
                        sstaa.setUndertakerId(subscriptionInformation.getCompanyId().toString());
                        maintainCostList.add(sstaa);
                        maintainSum = maintainSum + comSum;
                    }

//                        SysScenicSpotTargetAmountAscription ssstaa = new SysScenicSpotTargetAmountAscription();
//                        ssstaa.setCommitmentAmount(comSum.toString());
//                        ssstaa.setUndertakerName(subscriptionInformation.getCompanyName());
//                        ssstaa.setUndertakerId(subscriptionInformation.getCompanyId().toString());
//                        maintainSum = maintainSum + comSum;
//
//                    if (i+2 == robotCosts.length){
//                        ssstaa = new SysScenicSpotTargetAmountAscription();
//
//                        double subjectSpotPartSum =  partSum - spotPartSum ;
//                        double subjectSpotUpkeepSum = upkeepSum - spotUpkeepSum;
//                        double sum = subjectSpotPartSum + subjectSpotUpkeepSum;
//                        ssstaa.setCommitmentAmount(String.valueOf(sum));
//                        ssstaa.setUndertakerId(robotCosts[i+1].getUndertakerId());
//                        ssstaa.setUndertakerName(robotCosts[i+1].getUndertakerName());
//                        maintainCosts[i+1] = ssstaa;
//                        maintainSum = maintainSum + subjectSpotPartSum + subjectSpotPartSum;
//
//                    }
            }else if (!StringUtils.isEmpty(partSum) && StringUtils.isEmpty(upkeepSum)){
                //只有配件费没有维修费
//                for (int i = 0; i < robotCosts.length-1; i++) {

                    for (SubscriptionInformation subscriptionInformation : subscriptionInformations) {
                        //配件比例
                        double spotManagementScale = subscriptionInformation.getSpotAccessoryRatio();
                        //景区承担的配件和维修费用
                        double spotPartSum =  partSum * (spotManagementScale/100);
                        sstaa = new SysScenicSpotTargetAmountAscription();

                        sstaa.setUndertakerId(subscriptionInformation.getCompanyId().toString());
                        sstaa.setUndertakerName(subscriptionInformation.getCompanyName());
                        sstaa.setCommitmentAmount(String.valueOf(df.format(spotPartSum)));
                        maintainSum = maintainSum + spotPartSum;
                        maintainCostList.add(sstaa);
                    }
//                    SubscriptionInformation subscriptionInformation = subscriptionInformations.get(i);
//                    //配件比例
//                    double spotManagementScale = subscriptionInformation.getSpotAccessoryRatio();
////                    //维修比例
////                    double spotServiceScale = subscriptionInformation.getSpotRepairRatio();
//
//                    //景区承担的配件和维修费用
//                    double spotPartSum =  partSum * (spotManagementScale/100);
//                    double spotUpkeepSum = upkeepSum * spotServiceScale;

//                    Double comSum = spotPartSum;
//                    SysScenicSpotTargetAmountAscription ssstaa = new SysScenicSpotTargetAmountAscription();
//                    ssstaa.setCommitmentAmount(comSum.toString());
//                    ssstaa.setUndertakerName(subscriptionInformation.getCompanyName());
//                    ssstaa.setUndertakerId(subscriptionInformation.getCompanyId().toString());
//                    maintainCosts[i] = ssstaa;
//                    maintainSum = maintainSum + spotPartSum;
//                    if (i+2 == robotCosts.length){
//                        ssstaa = new SysScenicSpotTargetAmountAscription();
//
//                        double subjectSpotPartSum =  partSum - comSum ;
//                        ssstaa.setCommitmentAmount(String.valueOf(subjectSpotPartSum));
//                        ssstaa.setUndertakerId(robotCosts[i+1].getUndertakerId());
//                        ssstaa.setUndertakerName(robotCosts[i+1].getUndertakerName());
//                        maintainCosts[i+1] = ssstaa;
//                        maintainSum = maintainSum + subjectSpotPartSum ;
//                    }

//                }
            }else {
               //只有维修费，没有配件费
//                for (int i = 0; i < robotCosts.length-1; i++) {

                    for (SubscriptionInformation subscriptionInformation : subscriptionInformations) {
                        //维修比例
                        double spotServiceScale = subscriptionInformation.getSpotRepairRatio();
                        //景区承担的配件和维修费用
                        double spotUpkeepSum =Double.parseDouble(df.format(upkeepSum * (spotServiceScale/100)));

                        sstaa = new SysScenicSpotTargetAmountAscription();
                        sstaa.setCommitmentAmount(String.valueOf(spotUpkeepSum));
                        sstaa.setUndertakerName(subscriptionInformation.getCompanyName());
                        sstaa.setUndertakerId(subscriptionInformation.getCompanyId().toString());
                        maintainCostList.add(sstaa);
                        maintainSum = maintainSum +  spotUpkeepSum;
                    }

//                    SubscriptionInformation subscriptionInformation = subscriptionInformations.get(i);
//                    //配件比例
////                    double spotManagementScale = subscriptionInformation.getSpotAccessoryRatio();
//                    //维修比例
//                    double spotServiceScale = subscriptionInformation.getSpotRepairRatio();
//
//                    //景区承担的配件和维修费用
////                    double spotPartSum =  partSum * spotManagementScale;
//                     double spotUpkeepSum = upkeepSum * (spotServiceScale/100);


//                        Double sum = spotUpkeepSum ;
//                        SysScenicSpotTargetAmountAscription ssstaa = new SysScenicSpotTargetAmountAscription();
//                        ssstaa.setCommitmentAmount(sum.toString());
//                        ssstaa.setUndertakerName(subscriptionInformation.getCompanyName());
//                        ssstaa.setUndertakerId(subscriptionInformation.getCompanyId().toString());
//                        maintainCosts[i] = ssstaa;
//                        maintainSum = maintainSum +  spotUpkeepSum;
//
//                    if (i+2 == robotCosts.length){
//                        ssstaa = new SysScenicSpotTargetAmountAscription();
//
//                        double subjectSpotUpkeepSum =  upkeepSum - sum ;
//                        ssstaa.setCommitmentAmount(String.valueOf(subjectSpotUpkeepSum));
//                        ssstaa.setUndertakerId(robotCosts[i+1].getUndertakerId());
//                        ssstaa.setUndertakerName(robotCosts[i+1].getUndertakerName());
//                        maintainCosts[i+1] = ssstaa;
//                        maintainSum = maintainSum + subjectSpotUpkeepSum ;
//
//                    }
//                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
         robotCosts = robotCostList.toArray(new SysScenicSpotTargetAmountAscription[robotCostList.size()]);
         maintainCosts = maintainCostList.toArray(new SysScenicSpotTargetAmountAscription[maintainCostList.size()]);

        sysScenicSpotTargetAmount.setRobotCost(df.format(accumulate));
        sysScenicSpotTargetAmount.setMaintainCost(df.format(maintainSum));
        sysScenicSpotTargetAmount.setRobotCostList(robotCosts);
        sysScenicSpotTargetAmount.setMaintainCostList(maintainCosts);

        return sysScenicSpotTargetAmount;
    }

    @Override
    public SysScenicSpotTargetAmount getSpotIdAndDateByTagret(Long scenicSpotId, String date) {

        SysScenicSpotTargetAmount sysScenicSpotTargetAmount = sysScenicSpotTargetAmountMapper.getSpotIdAndDateByTagret(scenicSpotId,date);

        return sysScenicSpotTargetAmount;
    }


    /**
     * 导入时用到的修改
     * @param spotIdAndDateByTagret
     * @return
     */
    @Override
    public int editTargetAmountExcel(SysScenicSpotTargetAmount spotIdAndDateByTagret) {

        spotIdAndDateByTagret.setUpdateDate(DateUtil.currentDateTime());
        int i1 = 0;
        //机器人成本承担方修改
//        SysScenicSpotTargetAmountAscription sysScenicSpotTargetAmountAscription = new SysScenicSpotTargetAmountAscription();
//        double accumulate = 0;
//        DecimalFormat df = new DecimalFormat("0.00");
//        SysRobotSoftAssetInformation robotSoftAssetInformationByRobotId = new SysRobotSoftAssetInformation();
//        List<SysRobotCompanyAscription> list = new ArrayList<>();
////        SysRobotCompanyAscription companyId = new SysRobotCompanyAscription();
//        String date = spotIdAndDateByTagret.getDate();
//        Long scenicSpotId = spotIdAndDateByTagret.getScenicSpotId();
//        List<SysRobot> robotList = sysRobotMapper.getRobotListByScenicSpotId(scenicSpotId);
//        try {
//            for (SysRobot sysRobot : robotList) {
//                robotSoftAssetInformationByRobotId = sysRobotSoftAssetInformationMapper.getRobotSoftAssetInformationByRobotId(sysRobot.getRobotId());
//                SysRobotCompanyAscription companyId = sysRobotCompanyAscriptionMapper.getAscriptionCompanyId(sysRobot.getRobotId().toString());
//                if(list.stream()
//                        .filter(item->item.getCompanyId()
//                                .equals(companyId.getCompanyId()))
//                        .findAny()
//                        .isPresent())
//                {//存在则代码块执行业务逻辑代码
//
//                }else{//不存在
//                    list.add(companyId);
//                }
//                String dateProduction = robotSoftAssetInformationByRobotId.getDateProduction();
//                String months = DateUtil.findMonths(dateProduction.substring(7), date);
//                if (Long.valueOf(months) <= 36) {
//                    //出厂成本
//                    Double cost = Double.valueOf(robotSoftAssetInformationByRobotId.getFactoryCost());
//                    //使用年限
//                    Double serviceLife = Double.valueOf(robotSoftAssetInformationByRobotId.getServiceLife()) * 12;
//                    //每月折旧金额
//                    Double mouthPiece = cost / serviceLife;
//                    mouthPiece = Double.parseDouble(df.format(mouthPiece));
//                    //总折旧额相加
//                    accumulate = accumulate + mouthPiece;
//                }
//            }
//        }catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        spotIdAndDateByTagret.setRobotCost(String.valueOf(accumulate));
////        CooperativeCompany nameByCompanyId = cooperativeCompanyService.getNameByCompanyId(split1[0]);
//        sysScenicSpotTargetAmountAscription = new SysScenicSpotTargetAmountAscription();
//        sysScenicSpotTargetAmountAscription.setTargetAmountAscriptionId(IdUtils.getSeqId());
//        sysScenicSpotTargetAmountAscription.setTargetAmountId(spotIdAndDateByTagret.getTargetAmountId());
//        sysScenicSpotTargetAmountAscription.setUndertakerId(list.get(0).getCompanyId());
//        sysScenicSpotTargetAmountAscription.setCommitmentAmount(String.valueOf(accumulate));
//        sysScenicSpotTargetAmountAscription.setType("1");
//        sysScenicSpotTargetAmountAscriptionMapper.insertSelective(sysScenicSpotTargetAmountAscription);


        //运营成本承担方修改
        String undertakerName = spotIdAndDateByTagret.getUndertakerName();
        if (!StringUtils.isEmpty(undertakerName)){
            String[] undertakerNameSplit = undertakerName.split(",");
            for (String s : undertakerNameSplit) {
                String[] split1 = s.split(":");
                if ("null".equals(split1[0]) || "无".equals(split1[0])){
                    continue;
                }else{
                    CooperativeCompany nameByCompanyId = cooperativeCompanyService.getNameByCompanyId(split1[0]);
                    SysScenicSpotTargetAmountAscription[] spotTargetAmountAscriptionByType = sysScenicSpotTargetAmountAscriptionMapper.getSpotTargetAmountAscriptionByType(spotIdAndDateByTagret.getTargetAmountId(), "2");
                    if (spotTargetAmountAscriptionByType.length>0){
                        for (SysScenicSpotTargetAmountAscription sysScenicSpotTargetAmountAscription : spotTargetAmountAscriptionByType) {
                            if (Double.parseDouble(sysScenicSpotTargetAmountAscription.getUndertakerId()) == nameByCompanyId.getCompanyId()){
//                            SysScenicSpotTargetAmountAscription sysScenicSpotTargetAmountAscription = spotTargetAmountAscriptionByType[0];
                                sysScenicSpotTargetAmountAscription.setUndertakerId(nameByCompanyId.getCompanyId().toString());
                                sysScenicSpotTargetAmountAscription.setCommitmentAmount(split1[1]);
                                i1 = sysScenicSpotTargetAmountAscriptionMapper.updateByPrimaryKeySelective(sysScenicSpotTargetAmountAscription);
                            }
                        }
                    }
                }
            }
        }


        //景区营销成本承担方修改
        String spotMarketName = spotIdAndDateByTagret.getSpotMarketName();
        if (!StringUtils.isEmpty(spotMarketName)){
            String[] spotMarketNameSplit = spotMarketName.split(",");
            for (String s : spotMarketNameSplit) {
                String[] split1 = s.split(":");
                if ("null".equals(split1[0]) || "无".equals(split1[0])){
                    continue;
                }else{
                    CooperativeCompany nameByCompanyId = cooperativeCompanyService.getNameByCompanyId(split1[0]);
                    SysScenicSpotTargetAmountAscription[] spotTargetAmountAscriptionByType = sysScenicSpotTargetAmountAscriptionMapper.getSpotTargetAmountAscriptionByType(spotIdAndDateByTagret.getTargetAmountId(), "3");
                    if (spotTargetAmountAscriptionByType.length>0){
                        for (SysScenicSpotTargetAmountAscription sysScenicSpotTargetAmountAscription : spotTargetAmountAscriptionByType) {
                            if (Double.parseDouble(sysScenicSpotTargetAmountAscription.getUndertakerId()) == nameByCompanyId.getCompanyId()){
//                            SysScenicSpotTargetAmountAscription sysScenicSpotTargetAmountAscription = spotTargetAmountAscriptionByType[0];
                                sysScenicSpotTargetAmountAscription.setUndertakerId(nameByCompanyId.getCompanyId().toString());
                                sysScenicSpotTargetAmountAscription.setCommitmentAmount(split1[1]);
                                i1 = sysScenicSpotTargetAmountAscriptionMapper.updateByPrimaryKeySelective(sysScenicSpotTargetAmountAscription);
                            }
                        }
                    }
                }
            }
        }



        //租金成本承担方修改
        String rentName = spotIdAndDateByTagret.getRentName();
        if (!StringUtils.isEmpty(rentName)){
            String[] rentNameSplit1 = rentName.split(",");
            for (String s : rentNameSplit1) {
                String[] split1 = s.split(":");
                if ("null".equals(split1[0]) || "无".equals(split1[0])){
                    continue;
                }else{
                    CooperativeCompany nameByCompanyId = cooperativeCompanyService.getNameByCompanyId(split1[0]);
                    SysScenicSpotTargetAmountAscription[] spotTargetAmountAscriptionByType = sysScenicSpotTargetAmountAscriptionMapper.getSpotTargetAmountAscriptionByType(spotIdAndDateByTagret.getTargetAmountId(), "4");
                    if (spotTargetAmountAscriptionByType.length>0){
                        for (SysScenicSpotTargetAmountAscription sysScenicSpotTargetAmountAscription : spotTargetAmountAscriptionByType) {
                            if (Double.parseDouble(sysScenicSpotTargetAmountAscription.getUndertakerId()) == nameByCompanyId.getCompanyId()){
//                            SysScenicSpotTargetAmountAscription sysScenicSpotTargetAmountAscription = spotTargetAmountAscriptionByType[0];
                                sysScenicSpotTargetAmountAscription.setUndertakerId(nameByCompanyId.getCompanyId().toString());
                                sysScenicSpotTargetAmountAscription.setCommitmentAmount(split1[1]);
                                i1 = sysScenicSpotTargetAmountAscriptionMapper.updateByPrimaryKeySelective(sysScenicSpotTargetAmountAscription);
                            }
                        }
                    }
                }
            }
        }
        //维养成本承担方修改
        String maintainCostName = spotIdAndDateByTagret.getMaintainCostName();
        if (!StringUtils.isEmpty(maintainCostName)){
            String[] maintainCostNameSplit1 = maintainCostName.split(",");
            for (String s : maintainCostNameSplit1) {
                String[] split1 = s.split(":");
                if ("null".equals(split1[0]) || "无".equals(split1[0])){
                    continue;
                }else{
                    CooperativeCompany nameByCompanyId = cooperativeCompanyService.getNameByCompanyId(split1[0]);
                    SysScenicSpotTargetAmountAscription[] spotTargetAmountAscriptionByType = sysScenicSpotTargetAmountAscriptionMapper.getSpotTargetAmountAscriptionByType(spotIdAndDateByTagret.getTargetAmountId(), "5");
                    if (spotTargetAmountAscriptionByType.length>0){
                        for (SysScenicSpotTargetAmountAscription sysScenicSpotTargetAmountAscription : spotTargetAmountAscriptionByType) {
                            if (Double.parseDouble(sysScenicSpotTargetAmountAscription.getUndertakerId()) == nameByCompanyId.getCompanyId()){
//                            SysScenicSpotTargetAmountAscription sysScenicSpotTargetAmountAscription = spotTargetAmountAscriptionByType[0];
                                sysScenicSpotTargetAmountAscription.setUndertakerId(nameByCompanyId.getCompanyId().toString());
                                sysScenicSpotTargetAmountAscription.setCommitmentAmount(split1[1]);
                                i1 = sysScenicSpotTargetAmountAscriptionMapper.updateByPrimaryKeySelective(sysScenicSpotTargetAmountAscription);
                            }
                        }
                    }
                }
            }
        }
//        //机器人出厂成本
//        Double f = 0d;
//        SysScenicSpotTargetAmountAscription[] robotExFactoryList = spotIdAndDateByTagret.getRobotExFactoryList();
//        if (!StringUtils.isEmpty(robotExFactoryList) && robotExFactoryList.length>0){
//            for (SysScenicSpotTargetAmountAscription sysScenicSpotTargetAmountAscription : robotExFactoryList) {
//                sysScenicSpotTargetAmountAscription.setType("6");
//                if (!StringUtils.isEmpty(sysScenicSpotTargetAmountAscription.getCommitmentAmount())){
//                    f = f + Double.parseDouble(sysScenicSpotTargetAmountAscription.getCommitmentAmount());
//                }
//                i1 =  sysScenicSpotTargetAmountAscriptionMapper.updateByPrimaryKeySelective(sysScenicSpotTargetAmountAscription);
//            }
//        }
//        spotIdAndDateByTagret.setRobotExFactory(f.toString());
        int i = sysScenicSpotTargetAmountMapper.updateByPrimaryKeySelective(spotIdAndDateByTagret);

        return i;


    }

    /**
     * 导入时用到的添加
     * @param sysScenicSpotTargetAmount
     * @return
     */
    @Override
    public int addTargetAmountExcel(SysScenicSpotTargetAmount sysScenicSpotTargetAmount) {
//        sysScenicSpotTargetAmount.setUpdateDate(DateUtil.currentDateTime());
        int i1 = 0;
        SysScenicSpotTargetAmountAscription sysScenicSpotTargetAmountAscription = new SysScenicSpotTargetAmountAscription();
        //机器人成本承担方修改
        double accumulate = 0;
        DecimalFormat df = new DecimalFormat("0.00");
        SysRobotSoftAssetInformation robotSoftAssetInformationByRobotId = new SysRobotSoftAssetInformation();
        List<SysRobotCompanyAscription> list = new ArrayList<>();
//        SysRobotCompanyAscription companyId = new SysRobotCompanyAscription();
        String date = sysScenicSpotTargetAmount.getDate();
        Long scenicSpotId = sysScenicSpotTargetAmount.getScenicSpotId();
        List<SysRobot> robotList = sysRobotMapper.getRobotListByScenicSpotId(scenicSpotId);
        try {
            for (SysRobot sysRobot : robotList) {
                robotSoftAssetInformationByRobotId = sysRobotSoftAssetInformationMapper.getRobotSoftAssetInformationByRobotId(sysRobot.getRobotId());
                SysRobotCompanyAscription companyId = sysRobotCompanyAscriptionMapper.getAscriptionCompanyId(sysRobot.getRobotId().toString());
                //判断list中是否有重复值
                if(list.stream()
                        .filter(item->item.getCompanyId()
                                .equals(companyId.getCompanyId()))
                        .findAny()
                        .isPresent())
                {//存在则代码块执行业务逻辑代码

                }else{//不存在
                    list.add(companyId);
                }
                String dateProduction = robotSoftAssetInformationByRobotId.getDateProduction();
                String months = DateUtil.findMonths(dateProduction.substring(0,7), date);
                if (Long.valueOf(months) <= (Long.parseLong(robotSoftAssetInformationByRobotId.getServiceLife()) *12)) {
                    //出厂成本
                    Double cost = Double.valueOf(robotSoftAssetInformationByRobotId.getFactoryCost());
                    //使用年限
                    Double serviceLife = Double.valueOf(robotSoftAssetInformationByRobotId.getServiceLife()) * 12;
                    //每月折旧金额
                    Double mouthPiece = cost / serviceLife;
                    mouthPiece = Double.parseDouble(df.format(mouthPiece));
                    //总折旧额相加
                    accumulate = accumulate + mouthPiece;
                }
            }
          }catch (Exception e) {
            e.printStackTrace();
        }

        sysScenicSpotTargetAmount.setRobotCost(String.valueOf(accumulate));
//        CooperativeCompany nameByCompanyId = cooperativeCompanyService.getNameByCompanyId(split1[0]);
        sysScenicSpotTargetAmountAscription = new SysScenicSpotTargetAmountAscription();
        sysScenicSpotTargetAmountAscription.setTargetAmountAscriptionId(IdUtils.getSeqId());
        sysScenicSpotTargetAmountAscription.setTargetAmountId(sysScenicSpotTargetAmount.getTargetAmountId());
        sysScenicSpotTargetAmountAscription.setUndertakerId(list.get(0).getCompanyId());
        sysScenicSpotTargetAmountAscription.setCommitmentAmount(String.valueOf(accumulate));
        sysScenicSpotTargetAmountAscription.setType("1");
        sysScenicSpotTargetAmountAscriptionMapper.insertSelective(sysScenicSpotTargetAmountAscription);






//        if (!StringUtils.isEmpty(robotCostName)){
//            String[] split = robotCostName.split(",");
//            for (String s : split) {
//                String[] split1 = s.split(":");
//                if ("null".equals(split1[0]) || "无".equals(split1[0])){
//                    continue;
//                }else{
//                    CooperativeCompany nameByCompanyId = cooperativeCompanyService.getNameByCompanyId(split1[0]);
//                    sysScenicSpotTargetAmountAscription = new SysScenicSpotTargetAmountAscription();
//                    sysScenicSpotTargetAmountAscription.setTargetAmountAscriptionId(IdUtils.getSeqId());
//                    sysScenicSpotTargetAmountAscription.setTargetAmountId(sysScenicSpotTargetAmount.getTargetAmountId());
//                    sysScenicSpotTargetAmountAscription.setUndertakerId(nameByCompanyId.getCompanyId().toString());
//                    sysScenicSpotTargetAmountAscription.setCommitmentAmount(split1[1]);
//                    sysScenicSpotTargetAmountAscription.setType("1");
//                    sysScenicSpotTargetAmountAscriptionMapper.insertSelective(sysScenicSpotTargetAmountAscription);
//                }
//            }
//        }

        //运营成本承担方修改
        String undertakerName = sysScenicSpotTargetAmount.getUndertakerName();
        if (!StringUtils.isEmpty(undertakerName)){
            String[] undertakerNameSplit = undertakerName.split(",");
            for (String s : undertakerNameSplit) {
                String[] split1 = s.split(":");
                if ("null".equals(split1[0]) || "无".equals(split1[0])){
                    continue;
                }else{
                    CooperativeCompany nameByCompanyId = cooperativeCompanyService.getNameByCompanyId(split1[0]);
                    sysScenicSpotTargetAmountAscription = new SysScenicSpotTargetAmountAscription();
                    sysScenicSpotTargetAmountAscription.setTargetAmountAscriptionId(IdUtils.getSeqId());
                    sysScenicSpotTargetAmountAscription.setTargetAmountId(sysScenicSpotTargetAmount.getTargetAmountId());
                    sysScenicSpotTargetAmountAscription.setUndertakerId(nameByCompanyId.getCompanyId().toString());
                    sysScenicSpotTargetAmountAscription.setCommitmentAmount(split1[1]);
                    sysScenicSpotTargetAmountAscription.setType("2");
                    sysScenicSpotTargetAmountAscriptionMapper.insertSelective(sysScenicSpotTargetAmountAscription);
                }
            }
        }


        //景区营销成本承担方修改
        String spotMarketName = sysScenicSpotTargetAmount.getSpotMarketName();
        if (!StringUtils.isEmpty(spotMarketName)){
            String[] spotMarketNameSplit = spotMarketName.split(",");
            for (String s : spotMarketNameSplit) {
                String[] split1 = s.split(":");
                if ("null".equals(split1[0]) || "无".equals(split1[0])){
                    continue;
                }else{
                    CooperativeCompany nameByCompanyId = cooperativeCompanyService.getNameByCompanyId(split1[0]);
                    sysScenicSpotTargetAmountAscription = new SysScenicSpotTargetAmountAscription();
                    sysScenicSpotTargetAmountAscription.setTargetAmountAscriptionId(IdUtils.getSeqId());
                    sysScenicSpotTargetAmountAscription.setTargetAmountId(sysScenicSpotTargetAmount.getTargetAmountId());
                    sysScenicSpotTargetAmountAscription.setUndertakerId(nameByCompanyId.getCompanyId().toString());
                    sysScenicSpotTargetAmountAscription.setCommitmentAmount(split1[1]);
                    sysScenicSpotTargetAmountAscription.setType("3");
                    sysScenicSpotTargetAmountAscriptionMapper.insertSelective(sysScenicSpotTargetAmountAscription);
                }
            }
        }



        //租金成本承担方修改
        String rentName = sysScenicSpotTargetAmount.getRentName();
        if (!StringUtils.isEmpty(rentName)){
            String[] rentNameSplit1 = rentName.split(",");
            for (String s : rentNameSplit1) {
                String[] split1 = s.split(":");
                if ("null".equals(split1[0])|| "无".equals(split1[0])){
                    continue;
                }else{
                    CooperativeCompany nameByCompanyId = cooperativeCompanyService.getNameByCompanyId(split1[0]);
                    sysScenicSpotTargetAmountAscription = new SysScenicSpotTargetAmountAscription();
                    sysScenicSpotTargetAmountAscription.setTargetAmountAscriptionId(IdUtils.getSeqId());
                    sysScenicSpotTargetAmountAscription.setTargetAmountId(sysScenicSpotTargetAmount.getTargetAmountId());
                    sysScenicSpotTargetAmountAscription.setUndertakerId(nameByCompanyId.getCompanyId().toString());
                    sysScenicSpotTargetAmountAscription.setCommitmentAmount(split1[1]);
                    sysScenicSpotTargetAmountAscription.setType("4");
                    sysScenicSpotTargetAmountAscriptionMapper.insertSelective(sysScenicSpotTargetAmountAscription);
                }
            }
        }
        //维养成本承担方修改
        String maintainCostName = sysScenicSpotTargetAmount.getMaintainCostName();
        if (!StringUtils.isEmpty(maintainCostName)){
            String[] maintainCostNameSplit1 = maintainCostName.split(",");
            for (String s : maintainCostNameSplit1) {
                String[] split1 = s.split(":");
                if ("null".equals(split1[0]) || "无".equals(split1[0])){
                    continue;
                }else{
                    CooperativeCompany nameByCompanyId = cooperativeCompanyService.getNameByCompanyId(split1[0]);
                    sysScenicSpotTargetAmountAscription = new SysScenicSpotTargetAmountAscription();
                    sysScenicSpotTargetAmountAscription.setTargetAmountAscriptionId(IdUtils.getSeqId());
                    sysScenicSpotTargetAmountAscription.setTargetAmountId(sysScenicSpotTargetAmount.getTargetAmountId());
                    sysScenicSpotTargetAmountAscription.setUndertakerId(nameByCompanyId.getCompanyId().toString());
                    sysScenicSpotTargetAmountAscription.setCommitmentAmount(split1[1]);
                    sysScenicSpotTargetAmountAscription.setType("5");
                    sysScenicSpotTargetAmountAscriptionMapper.insertSelective(sysScenicSpotTargetAmountAscription);
                }
            }
        }
//        //机器人出厂成本
//        Double f = 0d;
//        SysScenicSpotTargetAmountAscription[] robotExFactoryList = spotIdAndDateByTagret.getRobotExFactoryList();
//        if (!StringUtils.isEmpty(robotExFactoryList) && robotExFactoryList.length>0){
//            for (SysScenicSpotTargetAmountAscription sysScenicSpotTargetAmountAscription : robotExFactoryList) {
//                sysScenicSpotTargetAmountAscription.setType("6");
//                if (!StringUtils.isEmpty(sysScenicSpotTargetAmountAscription.getCommitmentAmount())){
//                    f = f + Double.parseDouble(sysScenicSpotTargetAmountAscription.getCommitmentAmount());
//                }
//                i1 =  sysScenicSpotTargetAmountAscriptionMapper.updateByPrimaryKeySelective(sysScenicSpotTargetAmountAscription);
//            }
//        }
//        spotIdAndDateByTagret.setRobotExFactory(f.toString());
        int i = sysScenicSpotTargetAmountMapper.insertSelective(sysScenicSpotTargetAmount);

        return i;

    }
}
