package com.hna.hka.archive.management.assetsSystem.controller;

import com.hna.hka.archive.management.assetsSystem.model.SysScenicSpotTargetAmount;
import com.hna.hka.archive.management.assetsSystem.service.TreasureHuntOrderService;
import com.hna.hka.archive.management.system.model.SysOrder;
import com.hna.hka.archive.management.system.util.PageDataResult;
import com.hna.hka.archive.management.system.util.PublicUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2022/7/19 13:26
 * 寻宝活动订单相关数据
 */
public class TreasureHuntOrderController extends PublicUtil {


    @Autowired
    TreasureHuntOrderService treasureHuntOrderService;

    @ApiOperation("寻宝订单列表")
    @RequestMapping("/getTargetAmountList")
    @ResponseBody
    public PageDataResult getTreasureHuntList(@RequestParam("pageNum") Integer pageNum,
                                              @RequestParam("pageSize") Integer pageSize, SysOrder sysOrder){
        PageDataResult pageDataResult = new PageDataResult();
        Map<String,Object> search = new HashMap<>();
        try {
            if(null == pageNum) {
                pageNum = 1;
            }
            if(null == pageSize) {
                pageSize = 10;
            }

            search.put("startTime",sysOrder.getOrderStartTime());
            search.put("endTime",sysOrder.getOrderEndTime());
            if (!StringUtils.isEmpty(sysOrder.getOrderScenicSpotId())){
                search.put("spotId",sysOrder.getOrderScenicSpotId());
            }

           pageDataResult  =  treasureHuntOrderService.getTreasureHuntList(pageNum,pageSize,search);



        }catch (Exception e){
            logger.info("getTargetAmountList",e);
        }
        return pageDataResult;
    }




}
