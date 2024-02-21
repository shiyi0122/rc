package com.hna.hka.archive.management.assetsSystem.controller;

import com.hna.hka.archive.management.assetsSystem.model.Cost;
import com.hna.hka.archive.management.assetsSystem.model.FixedAssetsResult;
import com.hna.hka.archive.management.assetsSystem.model.SearchRobot;
import com.hna.hka.archive.management.assetsSystem.service.FixedAssetsService;
import com.hna.hka.archive.management.system.util.PageDataResult;
import com.hna.hka.archive.management.system.util.PublicUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Api(tags = "固定资产投资率分析")
@Controller
@RequestMapping("/system/fixedAssets")
public class FixedAssetsController extends PublicUtil {

    @Autowired
    private FixedAssetsService fixedAssetsService;

    @ApiOperation("查询")
    @RequestMapping("/list")
    @ResponseBody
    public FixedAssetsResult list(String startDate,String endDate,String scenicSpotId,Integer pageNum, Integer pageSize){
        FixedAssetsResult pageDataResult=new FixedAssetsResult();
        try {
            if(null == pageNum) {
                pageNum = 1;
            }
            if(null == pageSize) {
                pageSize = 10;
            }
            pageDataResult = fixedAssetsService.list(startDate,endDate,scenicSpotId,pageNum,pageSize);
        }catch (Exception e){
            logger.info("列表查询失败",e);
        }
        return pageDataResult;
    }

    @ApiOperation("统计报表")
    @RequestMapping("/report")
    @ResponseBody
    public PageDataResult report(String startDate,String endDate,String scenicSpotId){
        PageDataResult pageDataResult=new PageDataResult();
        try {
            pageDataResult=fixedAssetsService.report(startDate, endDate, scenicSpotId);
        }catch (Exception e){
            logger.info("列表查询失败",e);
        }
        return pageDataResult;
    }
}
