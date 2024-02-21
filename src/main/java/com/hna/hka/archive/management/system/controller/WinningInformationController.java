package com.hna.hka.archive.management.system.controller;

import com.hna.hka.archive.management.system.model.WinningInformation;
import com.hna.hka.archive.management.system.service.WinningInformationService;
import com.hna.hka.archive.management.system.util.PageDataResult;
import com.hna.hka.archive.management.system.util.PublicUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Api(tags = "奖品中奖信息")
@Controller
@RequestMapping("/system/Winning")
public class WinningInformationController  extends PublicUtil {

    @Autowired
    private WinningInformationService winningInformationService;

    @ApiOperation("查询中奖信息")
    @RequestMapping("/getWinningInformation")
    @ResponseBody
    public PageDataResult getWinningInformation(@RequestParam("pageNum") Integer pageNum,
                                                          @RequestParam("pageSize") Integer pageSize,String currentUserPhone,String exchangeType){
            if(null == pageNum) {
                pageNum = 1;
            }
            if(null == pageSize) {
                pageSize = 10;
            }
        PageDataResult pageDataResult = winningInformationService.getWinningInformation(pageNum,pageSize,currentUserPhone,exchangeType);

        return pageDataResult;
    }
}
