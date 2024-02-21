package com.hna.hka.archive.management.system.controller;

import com.hna.hka.archive.management.system.model.SysScenicSpotBinding;
import com.hna.hka.archive.management.system.model.SysScenicSpotBroadcastHuntLog;
import com.hna.hka.archive.management.system.service.SysScenicSpotBroadcastHuntLogService;
import com.hna.hka.archive.management.system.util.PageDataResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author zhang
 * @Date 2022/3/11 17:18
 * 寻宝日志
 */

@RequestMapping("/system/scenicSpotBroadcastHuntLog")
@Controller
public class ScenicSpotBroadcastHuntLogController {


    @Autowired
    SysScenicSpotBroadcastHuntLogService sysScenicSpotBroadcastHuntLogService;

    @RequestMapping(value = "/getScenicSpotBroadcastHuntLogList", method = RequestMethod.GET)
    @ResponseBody
    public PageDataResult getScenicSpotBroadcastHuntLogList(@RequestParam("pageNum") Integer pageNum,
                                                   @RequestParam("pageSize") Integer pageSize, SysScenicSpotBroadcastHuntLog sysScenicSpotBroadcastHuntLog) {
        PageDataResult pageDataResult = new PageDataResult();
        try {
            if(null == pageNum) {
                pageNum = 1;
            }
            if(null == pageSize) {
                pageSize = 10;
            }
            pageDataResult = sysScenicSpotBroadcastHuntLogService.getScenicSpotBroadcastHuntLogList(pageNum,pageSize,sysScenicSpotBroadcastHuntLog);
        } catch (Exception e) {
            e.printStackTrace();
//            logger.error("归属地列表查询异常！", e);
        }
        return pageDataResult;
    }



}
