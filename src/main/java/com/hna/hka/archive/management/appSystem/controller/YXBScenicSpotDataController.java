package com.hna.hka.archive.management.appSystem.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hna.hka.archive.management.system.model.*;
import com.hna.hka.archive.management.system.service.*;
import com.hna.hka.archive.management.system.util.JsonUtils;
import io.swagger.annotations.ApiOperation;
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
 * @Date 2022/12/6 15:44
 * 此controller为游小伴app代码中定时任务所需接口，和管理者app没有一点关系
 */
@RequestMapping("/appSystem/YXBScenicSpotData")
@Controller
public class YXBScenicSpotDataController {

    @Autowired
    SysScenicSpotService sysScenicSpotService;

    @Autowired
    SysScenicSpotBroadcastService sysScenicSpotBroadcastService;

    @Autowired
    SysScenicSpotRecommendedRouteService sysScenicSpotRecommendedRouteService;

    @Autowired
    SysScenicSpotGpsCoordinateService sysScenicSpotGpsCoordinateService;

    @Autowired
    SysScenicSpotBindingService sysScenicSpotBindingService;

    @Autowired
    SysScenicSpotParkingService sysScenicSpotParkingService;

    @Autowired
    SysScenicSpotServiceResService sysScenicSpotServiceResService;

    @ApiOperation("游小伴定时数据")
    @RequestMapping(value = "/getScenicSpotData.do",method = RequestMethod.POST)
    @ResponseBody
    public String getScenicSpotData() {
        Map<String, Object> map = new HashMap<>();

        //所有景区
        List<SysScenicSpot> sysScenicSpotList = sysScenicSpotService.getSysScenicSpotAll();
        map.put("scenicSpotList",sysScenicSpotList);
        //所有景点
        List<SysScenicSpotBroadcast> sysScenicSpotBroadcastList =  sysScenicSpotBroadcastService.getScenicSpotBroadcastAll();
        map.put("sysScenicSpotBroadcastList",sysScenicSpotBroadcastList);
        //所有线路
        List<SysScenicSpotRecommendedRoute> sysScenicSpotRecommendedRouteList = sysScenicSpotRecommendedRouteService.getScenicSpotRecommendedRouteAll();
        map.put("sysScenicSpotRecommendedRouteList",sysScenicSpotRecommendedRouteList);
        //景区归属地
        List<SysScenicSpotBinding> scenicSpotBindingList = sysScenicSpotService.getScenicSpotBindingAllList();
        map.put("scenicSpotBindingList",scenicSpotBindingList);
        //景区电子围栏
        List<SysScenicSpotGpsCoordinateWithBLOBs> scenicSpotGpsCoordinateList =  sysScenicSpotGpsCoordinateService.getScenicSpotGpsCoordinateAll();
        map.put("scenicSpotGpsCoordinateList",scenicSpotGpsCoordinateList);
        //卫生间
        List<SysScenicSpotServiceRes> sysScenicSpotServiceResList =  sysScenicSpotServiceResService.getScenicSpotServiceResAll();
        map.put("sysScenicSpotServiceResList",sysScenicSpotServiceResList);
        //停放点
        List<SysScenicSpotParkingWithBLOBs> sysScenicSpotParkingList = sysScenicSpotParkingService.getScenicSpotParkingAll();
        map.put("sysScenicSpotParkingList",sysScenicSpotParkingList);
        map.put("code","200");
        String s = JsonUtils.toString(map);
//        JSONObject jsonObject = JSON.parseObject(s);
//        Object code = jsonObject.get("code");
//        List<SysScenicSpot> scenicSpotList =(List<SysScenicSpot>) jsonObject.get("scenicSpotList");
//
//        System.out.println(code);

        return s;
    }

}
