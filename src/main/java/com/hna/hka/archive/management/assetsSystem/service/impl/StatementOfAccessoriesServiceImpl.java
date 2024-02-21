package com.hna.hka.archive.management.assetsSystem.service.impl;

import com.github.pagehelper.PageHelper;
import com.hna.hka.archive.management.appSystem.dao.SysRobotAccessoriesApplicationDetailMapper;
import com.hna.hka.archive.management.appSystem.dao.SysRobotErrorRecordsMapper;
import com.hna.hka.archive.management.assetsSystem.dao.*;
import com.hna.hka.archive.management.assetsSystem.model.*;
import com.hna.hka.archive.management.assetsSystem.service.StatementOfAccessoriesService;
import com.hna.hka.archive.management.assetsSystem.service.SysRobotAccessoriesApplicationService;
import com.hna.hka.archive.management.assetsSystem.service.SysRobotPartsManagementService;
import com.hna.hka.archive.management.system.model.SysUsers;
import com.hna.hka.archive.management.system.util.DateUtil;
import com.hna.hka.archive.management.system.util.IdUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2022/4/20 10:16
 * 配件对账单
 */
@Service
public class StatementOfAccessoriesServiceImpl implements StatementOfAccessoriesService {

    @Autowired
    StatementOfAccessoriesMapper statementOfAccessoriesMapper;
    @Autowired
    CooperativeCompanyMapper cooperativeCompanyMapper;
    @Autowired
    SubscriptionInformationMapper subscriptionInformationMapper;
    @Autowired
    SysRobotErrorRecordsMapper sysRobotErrorRecordsMapper;
    @Autowired
    AccountCloseMapper accountCloseMapper;
    @Autowired
    SysRobotAccessoriesApplicationMapper sysRobotAccessoriesApplicationMapper;
    @Autowired
    SysRobotAccessoriesApplicationDetailMapper sysRobotAccessoriesApplicationDetailMapper;

    /**
     * 配件对账单列表
     * @param company
     * @param spot
     * @param beginDate
     * @param endDate
     * @param paymentType
     * @param
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public List<StatementOfAccessories> list(Long company, Long spot, String beginDate, String endDate, String paymentType, Long errorRecordsUpkeepCost, Integer pageNum, Integer pageSize) {

        SysUsers user = (SysUsers) SecurityUtils.getSubject().getPrincipal();
        String[] split = (paymentType == null ? null : paymentType.split(","));
        Integer beginSize = (beginDate != null ? beginDate.length() : 0);
        Integer endSize = (endDate != null ? endDate.length() : 0);
        List<StatementOfAccessories> list =  statementOfAccessoriesMapper.list(company,spot,beginDate,endDate,paymentType,errorRecordsUpkeepCost,beginSize,endSize,pageNum,pageSize);

        return list ;
    }

    /**
     * 总条数和流水
     * @param company
     * @param spot
     * @param beginDate
     * @param endDate
     * @param paymentType
     * @param
     * @return
     */
    @Override
    public Map<String, Object> getCount(Long company, Long spot, String beginDate, String endDate, String paymentType, Long errorRecordsUpkeepCost, Integer pageNum, Integer pageSize) {

        SysUsers user = (SysUsers) SecurityUtils.getSubject().getPrincipal();
        String[] split = (paymentType == null ? null : paymentType.split(","));
        Integer beginSize = (beginDate != null ? beginDate.length() : 0);
        Integer endSize = (endDate != null ? endDate.length() : 0);
        Map<String, Object>  map =  statementOfAccessoriesMapper.getCount(company,spot,beginDate,endDate,paymentType,errorRecordsUpkeepCost,beginSize,endSize,pageNum,pageSize);
        return map;
    }

    /**
     * 预览
     * @param company
     * @param spot
     * @param beginDate
     * @param paymentType
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public  List<HashMap<String, Object>> preview(Long company, Long spot, String beginDate, String paymentType, Long errorRecordsUpkeepCost, Integer pageNum, Integer pageSize) {

        SysUsers user = (SysUsers) SecurityUtils.getSubject().getPrincipal();
        String[] split = (paymentType == null ? null : paymentType.split(","));
        Integer beginSize = (beginDate != null ? beginDate.length() : 0);
//        Integer endSize = (endDate != null ? endDate.length() : 0);
        List<HashMap<String, Object>> map = statementOfAccessoriesMapper.preview(company,spot,beginDate,paymentType,errorRecordsUpkeepCost,beginSize,pageNum,pageSize);
        Map<String, Object> mapA = sysRobotAccessoriesApplicationMapper.calculateTotal(spot, beginDate);

        if (!StringUtils.isEmpty(map) && !StringUtils.isEmpty(mapA)){
            HashMap<String, Object> map1 = map.get(0);
            Double partsPrice = (Double) map1.get("配件费用");
            if (StringUtils.isEmpty(partsPrice)){
                partsPrice = 0d;
            }
            Double total = (Double) map1.get("合计");
            if (StringUtils.isEmpty(total)){
                total = 0d;
            }
            Double spotBearPartsCost = (Double)mapA.get("spotBearPartsCost");
            if (StringUtils.isEmpty(spotBearPartsCost)){
                spotBearPartsCost = 0d;
            }
            partsPrice = partsPrice + spotBearPartsCost;
            total = total + spotBearPartsCost;
            map1.put("配件费用",partsPrice);
            map1.put("合计",total);
            map.set(0,map1);

        }


        return map;
    }

    /**
     * 合计
     * @param company
     * @param spot
     * @param beginDate
     * @param endDate
     * @param paymentType
     * @param orderStatus
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public Map<String,Object> calculateTotal(Long company, Long spot, String beginDate, String endDate, String paymentType, Integer orderStatus, Integer pageNum, Integer pageSize) {

        SysUsers user = (SysUsers) SecurityUtils.getSubject().getPrincipal();
        String[] split = (paymentType == null ? null : paymentType.split(","));
        Integer beginSize = (beginDate != null ? beginDate.length() : 0);
        Integer endSize = (endDate != null ? endDate.length() : 0);
        Map<String,Object> map = statementOfAccessoriesMapper.calculateTotal(company,spot,beginDate,endDate,paymentType,orderStatus,beginSize,endSize,pageNum,pageSize);
        Map<String,Object> mapA = sysRobotAccessoriesApplicationMapper.calculateTotal(spot,beginDate);
        if (!StringUtils.isEmpty(mapA)){
            Double totalCostOfAccessories = (Double)map.get("totalCostOfAccessories");
            if (StringUtils.isEmpty(totalCostOfAccessories)){
                totalCostOfAccessories = 0d;
            }
            Double spotBearPartsCost = (Double)map.get("spotBearPartsCost");
            if (StringUtils.isEmpty(spotBearPartsCost)){
                spotBearPartsCost = 0d;
            }
            Double totalCostOfAccessoriesA = (Double)mapA.get("totalCostOfAccessories");
            if (StringUtils.isEmpty(totalCostOfAccessoriesA)){
                totalCostOfAccessoriesA = 0d;
            }
            Double spotBearPartsCostA = (Double)mapA.get("spotBearPartsCost");
            if (StringUtils.isEmpty(spotBearPartsCostA)){
                spotBearPartsCostA = 0d;
            }
            totalCostOfAccessories = totalCostOfAccessories +totalCostOfAccessoriesA;
            spotBearPartsCost = spotBearPartsCost + spotBearPartsCostA;
            map.put("totalCostOfAccessories",totalCostOfAccessories);
            map.put("spotBearPartsCost",spotBearPartsCost);


        }
        return map;
    }

    /**
     * 根据公司id获取收款相关信息
     * @param companyId
     * @return
     */
    @Override
    public CooperativeCompany getCompanyId(Long companyId) {


        CooperativeCompany cooperativeCompany = cooperativeCompanyMapper.selectByPrimaryKey(companyId);


        return cooperativeCompany;

    }

    @Override
    public List<StatementOfAccessories> download(Long company, Long spot, String beginDate, String endDate, String paymentType, Long  errorRecordsUpkeepCost, Integer pageNum, Integer pageSize) {

        List<StatementOfAccessories> statementOfAccessoriesList = new ArrayList<>();
        SysUsers user = (SysUsers) SecurityUtils.getSubject().getPrincipal();
        String[] split = (paymentType == null ? null : paymentType.split(","));
        Integer beginSize = (beginDate != null ? beginDate.length() : 0);
        Integer endSize = (endDate != null ? endDate.length() : 0);
        List<StatementOfAccessories> list =  statementOfAccessoriesMapper.list(company,spot,beginDate,endDate,paymentType,errorRecordsUpkeepCost,beginSize,endSize,pageNum,pageSize);
        List<StatementOfAccessories> listA = sysRobotAccessoriesApplicationMapper.getRobotAccessoriesApplicationVerifyList(spot,beginDate);
        if (StringUtils.isEmpty(listA) || listA.size()==0){
            return list;
        }else{
            statementOfAccessoriesList.addAll(list);
            statementOfAccessoriesList.addAll(listA);
            return statementOfAccessoriesList;
        }
    }



    /**
     * 修改之前的列表查询，时间模糊查询，不使用范围查询
     * @param company
     * @param spot
     * @param beginDate
     * @param paymentType
     * @param errorRecordsUpkeepCost
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public List<StatementOfAccessories> listNew(Long company, Long spot, String beginDate, String paymentType, Long errorRecordsUpkeepCost,String errorRecordsQuality ,Integer pageNum, Integer pageSize) {

        List<StatementOfAccessories> statementOfAccessoriesList = new ArrayList<>();
//        PageHelper.startPage(pageNum,pageSize);
        //损坏记录列表
        List<StatementOfAccessories> list =  statementOfAccessoriesMapper.listNew(company,spot,beginDate,paymentType,errorRecordsUpkeepCost,errorRecordsQuality,pageNum,pageSize);
        for (StatementOfAccessories statementOfAccessories : list) {
            statementOfAccessories.setType("2");
        }
        //配件申请列表
        List<StatementOfAccessories> listA = sysRobotAccessoriesApplicationMapper.getRobotAccessoriesApplicationVerifyList(spot,beginDate);
        for (StatementOfAccessories statementOfAccessories : listA) {
            statementOfAccessories.setType("1");
        }
        if (StringUtils.isEmpty(listA) || listA.size()==0){
            return statementOfAccessoriesList;
        }else{
            statementOfAccessoriesList.addAll(list);
            statementOfAccessoriesList.addAll(listA);

            List<StatementOfAccessories> statementOfAccessories = subList(statementOfAccessoriesList, pageNum, pageSize);
            return statementOfAccessories;
        }
    }

    /**
     * 修改之前的总条数和流水查询，时间模糊查询，不使用范围查询
     * @param company
     * @param spot
     * @param beginDate
     * @param paymentType
     * @param errorRecordsUpkeepCost
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public Map<String, Object> getCountNew(Long company, Long spot, String beginDate, String paymentType, Long errorRecordsUpkeepCost,String errorRecordsQuality ,Integer pageNum, Integer pageSize) {

        Map<String, Object>  map =  statementOfAccessoriesMapper.getCountNew(company,spot,beginDate,paymentType,errorRecordsUpkeepCost,errorRecordsQuality,pageNum,pageSize);
        Map<String, Object> mapA =  sysRobotAccessoriesApplicationMapper.getCountNew(spot,beginDate);
        Long count = (Long)map.get("count");
        if (StringUtils.isEmpty(count)){
            count = 0l;
        }
        Long countA = (Long)mapA.get("count");
        if(StringUtils.isEmpty(countA)){
            countA = 0l;
        }
        count =  count + countA  ;
        map.put("count",count);
        return map;
    }

    /**
     * 核实
     * @param company
     * @param spot
     * @param beginDate

     * @param paymentType
     * @param errorRecordsUpkeepCost
     * @return
     */
    @Override
    public int exitSettlementState(Long company, Long spot, String beginDate, String paymentType, Long errorRecordsUpkeepCost,Long sysUserId) {

        List<StatementOfAccessories> list =  statementOfAccessoriesMapper.listNewNoPage(company,spot,beginDate,paymentType,errorRecordsUpkeepCost);
        List<StatementOfAccessories> robotAccessoriesApplicationVerifyList = sysRobotAccessoriesApplicationMapper.getRobotAccessoriesApplicationVerifyList(spot, beginDate);

        int i = 1;
        int t =1;
        for (StatementOfAccessories statementOfAccessories : list) {

          i =  statementOfAccessoriesMapper.exitSettlementState(statementOfAccessories.getErrorRecordsId(),sysUserId);
        }
        for (StatementOfAccessories statementOfAccessories : robotAccessoriesApplicationVerifyList) {
          t =  sysRobotAccessoriesApplicationMapper.exitSettlementState(statementOfAccessories.getErrorRecordsId(),sysUserId);
        }
        return i;
    }

    /**
     * 核实按钮,核实后保存到结算流水
     * @param company
     * @param spot
     * @param beginDate
     * @param paymentType
     * @param errorRecordsUpkeepCost
     * @return
     */
    @Override
    public int settlementStateState(Long company, Long spot, String beginDate, String paymentType, Long errorRecordsUpkeepCost,Long userId,String userName) {

        List<StatementOfAccessories> list =  statementOfAccessoriesMapper.listNewNoPage(company,spot,beginDate,paymentType,errorRecordsUpkeepCost);
        int i = 0;
        for (StatementOfAccessories statementOfAccessories : list) {
            if ("0".equals(statementOfAccessories.getIsSettlement())){
                i= 1;
                break;
            }
        }


        //配件价格
        Double sumPartPrice = sysRobotErrorRecordsMapper.getSumPartPrice(spot, DateUtil.getLast12Months(1));
        if (StringUtils.isEmpty(sumPartPrice)){
            sumPartPrice = 0d;
        }
        //维修价格
        Double sumUpkeepCost = sysRobotErrorRecordsMapper.getSumUpkeepCost(spot, DateUtil.getLast12Months(1));
        if (StringUtils.isEmpty(sumUpkeepCost)){
            sumUpkeepCost = 0d;
        }

        double repairRatio = 0;
        double accessoryRatio = 0;
        List<SubscriptionInformation> subscriptionInformationList = subscriptionInformationMapper.selectSpotIdByContract(spot);
        for (SubscriptionInformation subscriptionInformation : subscriptionInformationList) {
            //该公司承担维修费用
            if (!StringUtils.isEmpty(sumPartPrice) && sumPartPrice != 0){
                repairRatio =  sumUpkeepCost * subscriptionInformation.getSpotRepairRatio();
            }
            //该公司承担配件费用
            if (!StringUtils.isEmpty(sumUpkeepCost) && sumUpkeepCost != 0){
                accessoryRatio =  sumPartPrice * subscriptionInformation.getSpotAccessoryRatio();
            }
            AccountClose accountClose = accountCloseMapper.getSpotIdAndCompanyIdAndDateByAccountClose(spot, subscriptionInformation.getCompanyId(), DateUtil.getLast12Months(1));
            if (StringUtils.isEmpty(accountClose)){
                accountClose =  new AccountClose();
                //收款单位id
                accountClose.setCompanyId(subscriptionInformation.getCompanyId());
                //收款单位名称
                accountClose.setCompanyName(subscriptionInformation.getCompanyName());
                //景区id
                accountClose.setSpotId(subscriptionInformation.getSpotId());
                //景区名称
                accountClose.setSpotName(subscriptionInformation.getSpotName());
                //月份
                accountClose.setMonth(DateUtil.getLast12Months(1));
                //配件申请费用
                accountClose.setParts(sumPartPrice);
                //维修申请费用
                accountClose.setFault(sumUpkeepCost);
                //配件维修费用
                accountClose.setRepair(sumPartPrice + sumUpkeepCost);
                //景区承担配件维修费用
                accountClose.setSpotRepair(repairRatio + accessoryRatio);
                //配件维修核实人id
                accountClose.setRepairCheckId(userId);
                //配件维修核实人名称
                accountClose.setRepairCheckName(userName);
                accountClose.setId(IdUtils.getSeqId());
                accountClose.setCreateTime(DateUtil.currentDateTime());
                accountCloseMapper.add(accountClose);
            }else{
                //配件申请费用
                accountClose.setParts(sumPartPrice);
                //维修申请费用
                accountClose.setFault(sumUpkeepCost);
                //配件维修费用
                accountClose.setRepair(sumPartPrice + sumUpkeepCost);
                //景区承担配件维修费用
                accountClose.setSpotRepair(repairRatio + accessoryRatio);
                //配件维修核实人id
                accountClose.setRepairCheckId(userId);
                //配件维修核实人名称
                accountClose.setRepairCheckName(userName);

                accountCloseMapper.edit(accountClose);
            }


        }

        return i;
    }

    /**
     * 定时计算景区承担配件费和景区承担维修费(当月1号凌晨执行)
     */
    @Override
    public void timingCalculationSpotPartsMaintenance() {

        double pricePartsAll = 0d;
        double priceRepairAll = 0d;

        double spotPriceRepairAll = 0d;
        double spotPricePartsAll = 0d;
        String date = DateUtil.getLast12Months(1);
        List<SysRobotErrorRecords> list = statementOfAccessoriesMapper.getDateLikeByRobotErrorRecordList(date);
        for (SysRobotErrorRecords sysRobotErrorRecords : list) {

            //查询故障单中申请发配件的配件总金额
            Double  partsPrice =  sysRobotAccessoriesApplicationDetailMapper.getSumAccessoryPrice(sysRobotErrorRecords.getErrorRecordsId());

            pricePartsAll = 0d;
            //签约公司
            List<SubscriptionInformation> subscriptionInformationList = subscriptionInformationMapper.selectSpotIdByContract(sysRobotErrorRecords.getScenicSpotId());
            if (subscriptionInformationList.size()>0){
                for (SubscriptionInformation subscriptionInformation : subscriptionInformationList) {
                    //判断景区维修承担比例
                    if (!StringUtils.isEmpty(subscriptionInformation.getSpotRepairRatio())){

                        if(!StringUtils.isEmpty(sysRobotErrorRecords.getErrorRecordsUpkeepCost()) && !"0".equals(sysRobotErrorRecords.getErrorRecordsUpkeepCost())){
                            priceRepairAll = priceRepairAll + Double.parseDouble(sysRobotErrorRecords.getErrorRecordsUpkeepCost());
                        }
                    }
                    //判断景区配件承担比例
                    if (!StringUtils.isEmpty(subscriptionInformation.getSpotAccessoryRatio())){
                        if (!StringUtils.isEmpty(partsPrice) && !"0".equals(partsPrice)){
                            pricePartsAll = pricePartsAll + (partsPrice )  * subscriptionInformation.getSpotRepairRatio();
                        }
                    }
                }
                //计算景区承担配件金额
                if (!StringUtils.isEmpty(partsPrice) && !"0".equals(partsPrice)){
                    spotPricePartsAll = partsPrice   -  pricePartsAll;
                    sysRobotErrorRecords.setSpotPartsCost(String.valueOf(spotPricePartsAll));
                }else{
                    sysRobotErrorRecords.setSpotPartsCost("0");
                }
                //景区承担维修金额
                if (!StringUtils.isEmpty(sysRobotErrorRecords.getErrorRecordsUpkeepCost()) && !"0".equals(sysRobotErrorRecords.getErrorRecordsUpkeepCost())){
                    spotPriceRepairAll = Double.parseDouble(sysRobotErrorRecords.getErrorRecordsUpkeepCost()) - priceRepairAll;
                    sysRobotErrorRecords.setSpotRepairCost(String.valueOf(spotPriceRepairAll));
                }else{
                    sysRobotErrorRecords.setSpotRepairCost("0");
                }

            }else{

               Double a = partsPrice ;
               Double b = Double.parseDouble(sysRobotErrorRecords.getErrorRecordsUpkeepCost());
               sysRobotErrorRecords.setSpotPartsCost(a.toString());
               sysRobotErrorRecords.setSpotRepairCost(b.toString());

            }
        }
        for (SysRobotErrorRecords sysRobotErrorRecords : list) {
            sysRobotErrorRecordsMapper.updateByPrimaryKeySelective(sysRobotErrorRecords);
        }
        //配件申请表计算景区承担配件费用
        List<SysRobotAccessoriesApplication> accessoriesApplicationList = sysRobotAccessoriesApplicationMapper.getDateLikeByAccessoriesApplicationList(date);

        for (SysRobotAccessoriesApplication sysRobotAccessoriesApplication : accessoriesApplicationList) {

            Double accessoryPrice =Double.parseDouble(sysRobotAccessoriesApplication.getAccessoryPrice());
            Double accessoryNumber = Double.parseDouble(sysRobotAccessoriesApplication.getAccessoryNumber());
            Double all = accessoryPrice * accessoryNumber ;
            List<SubscriptionInformation> subscriptionInformationList = subscriptionInformationMapper.selectSpotIdByContract(sysRobotAccessoriesApplication.getScenicSpotId());
            if (subscriptionInformationList.size()>0){
                for (SubscriptionInformation subscriptionInformation : subscriptionInformationList) {
                    //判断景区配件承担比例
                    if (!StringUtils.isEmpty(subscriptionInformation.getSpotAccessoryRatio())){
                        Double t =  all * subscriptionInformation.getSpotAccessoryRatio();
                        sysRobotAccessoriesApplication.setSpotPartsCost(t.toString());
                    }
                }

            }else{
                sysRobotAccessoriesApplication.setSpotPartsCost(all.toString());
            }

        }

    }

    //subList手动分页，page为第几页，rows为每页个数
    public static List<StatementOfAccessories> subList(List<StatementOfAccessories> list, int pageNum, int pageSize){
        List<StatementOfAccessories> listSort  = new ArrayList<>();
        int size=list.size();
        int pageStart=pageNum==1?0:(pageNum-1)*pageSize;//截取的开始位置
        int pageEnd=size<pageNum*pageSize?size:pageNum*pageSize;//截取的结束位置
        if(size>pageStart){
            listSort =list.subList(pageStart, pageEnd);
        }
        //总页数
        int totalPage=list.size()/pageSize;
        return listSort;
    }


}
