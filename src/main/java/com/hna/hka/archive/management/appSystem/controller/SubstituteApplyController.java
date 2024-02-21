package com.hna.hka.archive.management.appSystem.controller;

import com.github.pagehelper.PageInfo;
import com.hna.hka.archive.management.appSystem.model.SubstituteApply;
import com.hna.hka.archive.management.appSystem.service.SubstituteApplyService;
import com.hna.hka.archive.management.system.util.Constant;
import com.hna.hka.archive.management.system.util.ReturnModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.HashMap;

/**
 * @program: rc
 * @description: 配件申请(大维修)
 * @author: zhaoxianglong
 * @create: 2021-11-03 09:59
 **/
@CrossOrigin
@RestController
@Api(tags = "(大维修)")
@RequestMapping("/api/substitute_apply")
public class SubstituteApplyController {

    @Autowired
    SubstituteApplyService service;


    @GetMapping("/list")
    @ApiOperation("列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "beginDate" , value = "起始时间"),
            @ApiImplicitParam(name = "endDate" , value = "截止时间"),
            @ApiImplicitParam(name = "spotId" , value = "景区ID"),
            @ApiImplicitParam(name = "userId" , value = "审请人ID"),
            @ApiImplicitParam(name = "stat" , value = "处理进度"),
            @ApiImplicitParam(name = "pageNum" , value = "起始条数"),
            @ApiImplicitParam(name = "pageSize" , value = "查询条数")
    })
    public ReturnModel list(String beginDate , String endDate , Long spotId , Long userId , Integer stat , @NotNull Integer pageNum , @NotNull Integer pageSize){
        try{
            PageInfo<SubstituteApply> info = service.list(beginDate , endDate , spotId , userId , stat, pageNum , pageSize);
            return new ReturnModel(info , "success" , Constant.STATE_SUCCESS , null);
        } catch (Exception e){
            e.printStackTrace();
            return new ReturnModel(e.getMessage() , "error" , "500" , null);
        }
    }

    @PostMapping("/add")
    @ApiOperation("新增")
    public ReturnModel add(@RequestBody SubstituteApply substituteApply){
        try{
            service.add(substituteApply);
            return new ReturnModel(null , "success" , Constant.STATE_SUCCESS , null);
        } catch (Exception e){
            e.printStackTrace();
            return new ReturnModel(e.getMessage() , "error" , "500" , null);
        }
    }

    @PostMapping("/edit")
    @ApiOperation("修改")

    public ReturnModel edit(@RequestBody SubstituteApply substituteApply){
        try{
            service.edit(substituteApply);
            return new ReturnModel(null , "success" , Constant.STATE_SUCCESS , null);
        } catch (Exception e){
            e.printStackTrace();
            return new ReturnModel(e.getMessage() , "error" , "500" , null);
        }
    }

    @PostMapping("/updateStat")
    @ApiOperation("修改审核状态")
    public ReturnModel updateStat(String applyNumber , Long stat , Long userid , String aggestion , Long factoryId){
        try{
            service.updateStat(applyNumber , stat , userid , aggestion , factoryId);
            return new ReturnModel(null , "success" , Constant.STATE_SUCCESS , null);
        } catch (Exception e){
            e.printStackTrace();
            return new ReturnModel(e.getMessage() , "error" , "500" , null);
        }
    }

}
