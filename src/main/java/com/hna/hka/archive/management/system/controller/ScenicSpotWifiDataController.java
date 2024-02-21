package com.hna.hka.archive.management.system.controller;

import com.hna.hka.archive.management.system.model.SysScenicSpotRobotOperate;
import com.hna.hka.archive.management.system.model.SysScenicSpotWifiData;
import com.hna.hka.archive.management.system.service.SysScenicSpotWifiDataService;
import com.hna.hka.archive.management.system.util.PageDataResult;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;

/**
 * @Author zhang
 * @Date 2022/3/19 11:22
 * WIFI探针数据统计
 */
@RequestMapping("/system/scenicSpotWifiData")
@Controller
public class ScenicSpotWifiDataController {


    @Autowired
    SysScenicSpotWifiDataService scenicSpotWifiDataService;


    @RequestMapping(value = "/getScenicSpotWifiDataList")
    @ResponseBody()
    public PageDataResult getSpotRobotOperateList(@RequestParam("pageNum") Integer pageNum,
                                                  @RequestParam("pageSize") Integer pageSize, SysScenicSpotWifiData sysScenicSpotWifiData) {

        HashMap<String, String> search = new HashMap<>();
        search.put("router",sysScenicSpotWifiData.getRouter());
        PageDataResult pageDataResult = scenicSpotWifiDataService.getScenicSpotWifiData(pageNum,pageSize,search);

       return pageDataResult;

    }




    }
