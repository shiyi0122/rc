package com.hna.hka.archive.management.assetsSystem.controller;

import com.github.pagehelper.PageInfo;
import com.hna.hka.archive.management.appSystem.model.SubstituteApply;
import com.hna.hka.archive.management.appSystem.service.SubstituteApplyService;
import com.hna.hka.archive.management.assetsSystem.model.SysRobotSoftAssetInformation;
import com.hna.hka.archive.management.system.util.Constant;
import com.hna.hka.archive.management.system.util.FileUtil;
import com.hna.hka.archive.management.system.util.PublicUtil;
import com.hna.hka.archive.management.system.util.ReturnModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2022/2/26 17:26
 * 大维修
 */
@Slf4j
@Api(tags = "大维修")
@RestController
@RequestMapping("/system/substitute_apply_system")
public class SubstituteApplySystemController extends PublicUtil {

    @Autowired
    SubstituteApplyService substituteApplyService;

    @GetMapping("/list")
    @ApiOperation("列表")
    public ReturnModel sysList(String beginDate,String endDate,Long spotId,Long userId,Long robotCode,String faultNumber , Integer stat , @NotNull Integer pageNum , @NotNull Integer pageSize){
        try{
            PageInfo<SubstituteApply> info = substituteApplyService.sysList(beginDate , endDate , spotId , userId , robotCode, stat, faultNumber,pageNum , pageSize);
            return new ReturnModel(info , "success" , Constant.STATE_SUCCESS , null);
        } catch (Exception e){
            e.printStackTrace();
            return new ReturnModel(e.getMessage() , "error" , "500" , null);
        }
    }


    /**
     * @Method uploadExcelRobotSoftAssetInformation
     * @Author 郭凯
     * @Version  1.0
     * @Description 下载机器人大维修信息Excel表
     * @Return void
     * @Date 2021/5/28 16:37
     */
    @ApiOperation("大维修下载")
    @RequestMapping(value = "/uploadExcelSubstituteApplySystem")
    public void  uploadExcelSubstituteApplySystem(HttpServletResponse response, SubstituteApply substituteApply) throws Exception {
        List<SubstituteApply> substituteApplies = null;
        Map<String,Object> search = new HashMap<>();
//        search.put("userId",this.getSysUser().getUserId().toString());
        search.put("robotCode",substituteApply.getRobotCode());
        search.put("spotId",substituteApply.getSpotId());
        search.put("faultNumber",substituteApply.getFaultNumber());

        substituteApplies = substituteApplyService.getSubstituteApplySystemExcel(search);
        String dateTime = DateFormatUtils.format(new Date(), "yyyyMMddHHmm");
        FileUtil.exportExcel(FileUtil.getWorkbook("机器人大维修及报废", "机器人大维修及报废", SubstituteApply.class, substituteApplies),"机器人大维修及报废"+ dateTime,response);
    }

    @PostMapping("/add")
    @ApiOperation("新增")
    public ReturnModel add(@RequestBody SubstituteApply substituteApply){
        try{
            substituteApplyService.add(substituteApply);
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
            substituteApplyService.edit(substituteApply);
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
            substituteApplyService.updateStat(applyNumber , stat , userid , aggestion , factoryId);
            return new ReturnModel(null , "success" , Constant.STATE_SUCCESS , null);
        } catch (Exception e){
            e.printStackTrace();
            return new ReturnModel(e.getMessage() , "error" , "500" , null);
        }
    }




    /**
     * 不使用
     * @param substituteApply
     * @return
     */
    @PostMapping("/sysEdit")
    public ReturnModel editSubstituteApply(@RequestBody SubstituteApply substituteApply){
        try{
            PageInfo<SubstituteApply> info = substituteApplyService.editSubstituteApply(substituteApply);
            return new ReturnModel(null , "success" , Constant.STATE_SUCCESS , null);
        } catch (Exception e){
            e.printStackTrace();
            return new ReturnModel(e.getMessage() , "error" , "500" , null);
        }
    }





}
