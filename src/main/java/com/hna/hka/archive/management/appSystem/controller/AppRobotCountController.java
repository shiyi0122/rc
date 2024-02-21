package com.hna.hka.archive.management.appSystem.controller;

import com.hna.hka.archive.management.appSystem.model.BigPadSpot;
import com.hna.hka.archive.management.system.model.SysCurrentUser;
import com.hna.hka.archive.management.system.model.SysCurrentUserCoupons;
import com.hna.hka.archive.management.system.model.SysScenicSpot;
import com.hna.hka.archive.management.system.model.SysScenicSpotParking;
import com.hna.hka.archive.management.system.service.*;
import com.hna.hka.archive.management.system.util.DateUtil;
import com.hna.hka.archive.management.system.util.IdUtils;
import com.hna.hka.archive.management.system.util.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2022/5/27 13:49
 * 大屏app中需要的数据，此Controller和管理者app没有一点关系
 */
@RequestMapping("/appSystem/bigPidRobot")
@Controller
public class AppRobotCountController {

    @Autowired
    SysRobotService sysRobotService;

    @Autowired
    SysScenicSpotWifiDataService scenicSpotWifiDataService;

    @Autowired
    SysCurrentUserService sysCurrentUserService;

    @Autowired
    SysCurrentUserCouponsService sysCurrentUserCouponsService;

    @Autowired
    SysScenicSpotService sysScenicSpotService;

    @Autowired
    SysScenicSpotParkingService sysScenicSpotParkingService;

    /**
     * 大屏中获取机器人总数和闲置数量
     * @param spotId
     * @return
     */
    @RequestMapping(value = "/getBigPidRobotCount.do",method = RequestMethod.POST)
    @ResponseBody
    public String getRobotCount(String spotId) {
        Map<String, Object> map = new HashMap<>();

        map = sysRobotService.getBigPidRobotCount(spotId);


//        map.put("robotCount","50");
//        map.put("freeRobotCount","10");
//        map.put("code","200");


        String s = JsonUtils.toString(map);
        return s;

    }


    /**
     * 大屏获取运行中机器人数量
     * @param spotId
     * @return
     */
    @RequestMapping(value = "/getPeopleCounting.do",method = RequestMethod.POST)
    @ResponseBody
    public String getPeopleCounting(String spotId) {
        Map<String, Object> map = new HashMap<>();

        map = scenicSpotWifiDataService.getPeopleCounting(spotId);


//        map.put("robotCount","50");
//        map.put("freeRobotCount","10");
//        map.put("code","200");

        String s = JsonUtils.toString(map);
        return s;

    }


    /**
     * 大屏获取总机器人数量
     * @return
     */
    @RequestMapping(value = "/getRobotCount.do",method = RequestMethod.POST)
    @ResponseBody
    public String getRobotCount() {
        Map<String, Object> map = new HashMap<>();

        map = sysRobotService.getRobotCount();


//        map.put("robotCount","50");
//        map.put("freeRobotCount","10");
//        map.put("code","200");

        String s = JsonUtils.toString(map);
        return s;

    }

    /**
     * 大屏中抽奖获取到的优惠券，保存到用户账号中
     * @param spotId
     * @param phone
     * @param prizeAmount
     * @param type
     * @param expirationTime
     * @return
     */
    @RequestMapping(value = "/addUserPrize.do",method = RequestMethod.POST)
    @ResponseBody
    public String addUserPrize(String spotId,String phone,String prizeAmount,String type,String expirationTime) {
        Map<String, Object> map = new HashMap<>();

       SysCurrentUser currentUser = sysCurrentUserService.getCurrentUserByPhone(phone);

        SysScenicSpot sysScenicSpot = sysScenicSpotService.getSysScenicSpotById(Long.parseLong(spotId));

        SysCurrentUserCoupons sysCurrentUserCoupons = new SysCurrentUserCoupons();

        sysCurrentUserCoupons.setUserCouponsId(IdUtils.getSeqId());
        sysCurrentUserCoupons.setUserId(currentUser.getCurrentUserId());
        sysCurrentUserCoupons.setCouponsScenicSpotId(sysScenicSpot.getScenicSpotId());
        sysCurrentUserCoupons.setCouponsScenicSpotName(sysScenicSpot.getScenicSpotName());
        sysCurrentUserCoupons.setCouponsAmount(prizeAmount);
        sysCurrentUserCoupons.setCouponsStandard("0");
        sysCurrentUserCoupons.setCouponsStartTime(DateUtil.currentDateTime());
        sysCurrentUserCoupons.setCouponsEndTime(expirationTime);
        sysCurrentUserCoupons.setCouponsType("2");//大屏抽奖
        if ("1".equals(type)){
            sysCurrentUserCoupons.setType("2");
        }else if ("2".equals(type)){
            sysCurrentUserCoupons.setType("3");
        }
        sysCurrentUserCoupons.setCreateDate(DateUtil.currentDateTime());
        sysCurrentUserCoupons.setUpdateDate(DateUtil.currentDateTime());

        int i = sysCurrentUserCouponsService.insertCurrentUserCoupons(sysCurrentUserCoupons);


//        map.put("robotCount","50");
//        map.put("freeRobotCount","10");
        if (i>0){
            map.put("code","200");
            map.put("phone",phone);
        }else{
            map.put("code","400");
            map.put("phone",phone);
        }


        String s = JsonUtils.toString(map);
        return s;

    }

    /**
     * 大屏获取景区列表
     * @return
     */
    @RequestMapping(value = "/getSpotIdList.do",method = RequestMethod.POST)
    @ResponseBody
    public String getSpotIdList() {

        List<BigPadSpot> list = sysScenicSpotService.getSpotIdList();

//        HashMap<String, Object> map = new HashMap<>();
//
////        map.put("list",list);
////        map.put("code","200");
        String s = JsonUtils.toString(list);
        return s;
    }

    /**
     * 大屏获取景区计费规则
     * @return
     */
    @RequestMapping(value = "/getSpotIdPrice.do",method = RequestMethod.POST)
    @ResponseBody
    public String getSpotIdPrice(String spotId) {

       Map map = sysScenicSpotService.getSpotIdPrice(spotId);
       map.put("code","200");
       String s = JsonUtils.toString(map);
       return s  ;

    }

    /**
     * 大屏根据景区获取景区停放点
     * @return
     */
    @RequestMapping(value = "/getSpotIdParkingList.do",method = RequestMethod.POST)
    @ResponseBody
    public String getSpotIdParkingList(String spotId) {
        HashMap<String, Object>  map = new HashMap();
        List<SysScenicSpotParking> list  = sysScenicSpotParkingService.getSpotIdParkingList(Long.parseLong(spotId));
//        map.put("code","200");
//        map.put("list",list);
        String s = JsonUtils.toString(list);
        return s  ;

    }

}
