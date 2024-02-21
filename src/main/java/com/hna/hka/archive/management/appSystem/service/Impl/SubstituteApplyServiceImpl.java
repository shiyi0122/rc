package com.hna.hka.archive.management.appSystem.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hna.hka.archive.management.appSystem.dao.SubstituteApplyMapper;
import com.hna.hka.archive.management.appSystem.model.SubstituteApply;
import com.hna.hka.archive.management.appSystem.model.SubstituteApplyDetail;
import com.hna.hka.archive.management.appSystem.service.SubstituteApplyService;
import com.hna.hka.archive.management.assetsSystem.model.Address;
import com.hna.hka.archive.management.assetsSystem.model.Inventory;
import com.hna.hka.archive.management.assetsSystem.service.AddressService;
import com.hna.hka.archive.management.assetsSystem.service.InventoryDetailService;
import com.hna.hka.archive.management.system.service.CommonService;
import com.hna.hka.archive.management.system.util.WeChatGtRobotAppPush;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @program: rc
 * @description:
 * @author: zhaoxianglong
 * @create: 2021-11-03 10:03
 **/
@Service
public class SubstituteApplyServiceImpl implements SubstituteApplyService {


    @Autowired
    SubstituteApplyMapper mapper;

    InventoryDetailService inventoryDetailService;

    CommonService commonService;

    AddressService addressService;

    @Override
    public PageInfo<SubstituteApply> list(String beginDate, String endDate, Long spotId, Long userId, Integer stat, Integer pageNum, Integer pageSize) {
        PageHelper.offsetPage(pageNum , pageSize);
        List<SubstituteApply> list = mapper.list(beginDate , endDate ,spotId , userId , stat);
        return new PageInfo(list);
    }

    @Override
    public void add(SubstituteApply substituteApply) throws Exception {
        mapper.add(substituteApply);

        StringBuilder builder = new StringBuilder();

        if (substituteApply.getType() == 1){
            builder.append("备件申请\n");
        } else if (substituteApply.getType() == 2){
            builder.append("上报故障\n");
        } else if (substituteApply.getType() == 3){
            builder.append("大维修\n");
        }

        builder.append("申请单号:").append(substituteApply.getApplyNumber()).append("\n");
        builder.append("景区名称:").append(substituteApply.getSpotName()).append("\n");
        builder.append("申请人:").append(substituteApply.getApplyUserName()).append("\n");
        builder.append("请及时审核!");

        WeChatGtRobotAppPush.singlePushApp(mapper.getGTIdBySpotId(substituteApply.getSpotId()), "审核申请", builder.toString());

    }

    @Override
    public void edit(SubstituteApply substituteApply) throws Exception {
        mapper.edit(substituteApply);
    }

    public SubstituteApply getByKey(String applyNumber){
        SubstituteApply substituteApply = mapper.getByKey(applyNumber);
        SubstituteApplyDetail[] array = mapper.getDetailsByKey(applyNumber);
        substituteApply.setDetails(array);
        return substituteApply;
    }

    @Override
    public void updateStat(String applyNumber, Long stat, Long userid, String aggestion, Long factoryId) throws Exception {
        SubstituteApply substituteApply = getByKey(applyNumber);

        if (stat == 2){
            substituteApply.setApplyStat(stat.toString());
            String userName = mapper.getUserNameById(userid);
            substituteApply.setExamineAggestion(substituteApply.getExamineAggestion() == null ? aggestion : substituteApply.getExamineAggestion() + ";" + userName + ":" + aggestion);
            edit(substituteApply);

            StringBuilder builder = new StringBuilder();

            if (substituteApply.getType() == 1){
                builder.append("备件申请\n");
            } else if (substituteApply.getType() == 2){
                builder.append("上报故障\n");
            } else if (substituteApply.getType() == 3){
                builder.append("大维修\n");
            }

            builder.append("申请单号:").append(substituteApply.getApplyNumber()).append("\n");
            builder.append("景区名称:").append(substituteApply.getSpotName()).append("\n");
            builder.append("申请人:").append(substituteApply.getApplyUserName()).append("\n");
            builder.append("请及时发货!");

            WeChatGtRobotAppPush.singlePushApp(mapper.getGTIdBySpotId(92611162370392L), "审核已通过,请及时发货", builder.toString());
        } else if (stat == 3){

            Address address = addressService.getByKey(factoryId,null);
            substituteApply.setApplyStat(stat.toString());
            edit(substituteApply);
            Inventory inventory = new Inventory();
            inventory.setOrderTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            inventory.setOrderNumber(commonService.getOrderNumber(2));
            inventory.setSpotId(factoryId);
            inventory.setReceivingId(substituteApply.getSpotId());
            inventory.setPhone(substituteApply.getReceivingPhone());
            inventory.setReceivingAddress(address.getAddress());
            inventory.setSpotName(address.getSpotName());
            int add = inventoryDetailService.add(inventory);

        }
    }


    /**
     * zhang
     * 大维修条件查询
     * @param beginDate
     * @param endDate
     * @param spotId
     * @param userId
     * @param robotId
     * @param stat
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public PageInfo<SubstituteApply> sysList(String beginDate, String endDate, Long spotId, Long userId, Long robotId, Integer stat, String faultNumber ,Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum , pageSize);
        List<SubstituteApply> list = mapper.sysList(beginDate , endDate ,spotId , userId, robotId, stat,faultNumber);
        return new PageInfo(list);

    }

    /**
     * zhang
     * 后台修改
     * @param substituteApply
     * @return
     */
    @Override
    public PageInfo<SubstituteApply> editSubstituteApply(SubstituteApply substituteApply) {

    return null;
    }

    /**
     * 导出大维修数据
     * @param search
     * @return
     */
    @Override
    public List<SubstituteApply> getSubstituteApplySystemExcel(Map<String, Object> search) {


       List<SubstituteApply> list =  mapper.getSubstituteApplySystemExcel(search);

        for (SubstituteApply substituteApply : list) {

            if (substituteApply.getApplyStat().equals("0")){
                substituteApply.setApplyStat("审批中");
            }else if (substituteApply.getApplyStat().equals("1")){
                substituteApply.setApplyStat("维修");
            }else if (substituteApply.getApplyStat().equals("2")){
                substituteApply.setApplyStat("回收");
            }

            if (substituteApply.getProcessingResults().equals("0")){
                substituteApply.setProcessingResults("未处理");
            }else if (substituteApply.getProcessingResults().equals("1")){
                substituteApply.setProcessingResults("已修好");
            }else if (substituteApply.getProcessingResults().equals("2")){
                substituteApply.setProcessingResults("已维修");
            }
        }

       return list;
    }
}
