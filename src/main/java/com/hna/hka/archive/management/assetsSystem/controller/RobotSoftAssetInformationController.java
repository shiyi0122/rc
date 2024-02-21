package com.hna.hka.archive.management.assetsSystem.controller;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.alibaba.fastjson.JSONObject;
import com.hna.hka.archive.management.appSystem.service.SysRobotErrorRecordsService;
import com.hna.hka.archive.management.assetsSystem.model.SysRobotErrorRecords;
import com.hna.hka.archive.management.assetsSystem.model.SysRobotServiceRecords;
import com.hna.hka.archive.management.assetsSystem.model.SysRobotSoftAssetInformation;
import com.hna.hka.archive.management.assetsSystem.model.SysRobotSoftAssetInformationExtendVo;
import com.hna.hka.archive.management.assetsSystem.service.SysRobotServiceRecordsService;
import com.hna.hka.archive.management.assetsSystem.service.SysRobotSoftAssetInformationService;
import com.hna.hka.archive.management.system.model.*;
import com.hna.hka.archive.management.system.service.SysRobotDispatchLogService;
import com.hna.hka.archive.management.system.service.SysRobotService;
import com.hna.hka.archive.management.system.util.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @ProjectName: rc
 * @Package: com.hna.hka.archive.management.assetsSystem.controller
 * @ClassName: RobotSoftAssetInformationController
 * @Author: 郭凯
 * @Description: 机器人资产信息控制层
 * @Date: 2021/5/28 14:15
 * @Version: 1.0
 */
@Api(tags = "机器人资产信息相关")
@RequestMapping("/system/robotSoftAssetInformation")
@Controller
public class RobotSoftAssetInformationController extends PublicUtil {

    @Autowired
    private SysRobotSoftAssetInformationService robotSoftAssetInformationService;

    @Autowired
    private SysRobotService sysRobotService;

    @Autowired
    private SysRobotDispatchLogService sysRobotDispatchLogService;

    @Autowired
    private SysRobotErrorRecordsService sysRobotErrorRecordsService;

    @Autowired
    private SysRobotServiceRecordsService sysRobotServiceRecordsService;

    @Autowired
    private HttpSession session;


    /**
     * @Method getRobotSoftAssetInformationList
     * @Author 郭凯
     * @Version  1.0
     * @Description 机器人软资产信息列表查询
     * @Return com.hna.hka.archive.management.system.util.PageDataResult
     * @Date 2021/5/28 14:20
     */
    @ApiOperation("机器人软资产信息列表查询")
    @RequestMapping("/getRobotSoftAssetInformationList")
    @ResponseBody
    public PageDataResult getRobotSoftAssetInformationList(@RequestParam("pageNum") Integer pageNum,
                                                              @RequestParam("pageSize") Integer pageSize,
//                                                                @RequestParam("type")Integer type,
                                                                    SysRobotSoftAssetInformation robotSoftAssetInformation) {
        PageDataResult pageDataResult = new PageDataResult();
        SysUsers SysUsers = this.getSysUser();
        Map<String,Object> search = new HashMap<>();


        try {
            if(null == pageNum) {
                pageNum = 1;
            }
            if(null == pageSize) {
                pageSize = 10;
            }
            search.put("userId",SysUsers.getUserId().toString());
            search.put("robotCode",robotSoftAssetInformation.getRobotCode());
            search.put("robotModel",robotSoftAssetInformation.getRobotModel());
            search.put("scenicSpotId",robotSoftAssetInformation.getScenicSpotId());
            search.put("ascriptionCompanyId",robotSoftAssetInformation.getAscriptionCompanyId());
            search.put("equipmentStatus",robotSoftAssetInformation.getEquipmentStatus());
            pageDataResult = robotSoftAssetInformationService.getRobotSoftAssetInformationList(pageNum,pageSize,search);
        }catch (Exception e){
            logger.info("机器人软资产信息列表查询失败",e);
        }
        return pageDataResult;
    }

    /**
     * @Method addRobotSoftAssetInformation
     * @Author 郭凯
     * @Version  1.0
     * @Description 机器人软资产信息新增
     * @Return com.hna.hka.archive.management.system.util.ReturnModel
     * @Date 2021/5/28 14:22
     */
    @ApiOperation("机器人软资产信息新增")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "dateProduction", value = "出厂日期"),
            @ApiImplicitParam(name = "batteryWarrantyDeadline", value = "电池质保截止日期"),
            @ApiImplicitParam(name = "robotWarrantyDeadline", value = "机器人质保截止日期"),
            @ApiImplicitParam(name = "updateDate", value = "更新时间"),
            @ApiImplicitParam(name = "cost", value = "成本"),
            @ApiImplicitParam(name = "serviceLife", value = "使用年限"),
            @ApiImplicitParam(name = "factoryCost", value = "出厂成本"),
            @ApiImplicitParam(name = "accumulate", value = "累积折旧"),
            @ApiImplicitParam(name = "netPrice", value = "净值"),
            @ApiImplicitParam(name = "robotCode", value = "机器人编号"),
            @ApiImplicitParam(name = "robotId", value = "机器人id"),
            @ApiImplicitParam(name = "robotBatchNumber", value = "生产批次"),
            @ApiImplicitParam(name = "equipmentStatus", value = "设备状态"),
    })
    @RequestMapping("/addRobotSoftAssetInformation")
    @ResponseBody
    public ReturnModel addRobotSoftAssetInformation(@RequestBody SysRobotSoftAssetInformation robotSoftAssetInformation){
        ReturnModel returnModel = new ReturnModel();
        try {
            if (StringUtils.isEmpty(robotSoftAssetInformation.getRobotId())){
                returnModel.setData("");
                returnModel.setMsg("robotId为空");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
            SysRobotSoftAssetInformation robotSoftAssetInformationByRobotId = robotSoftAssetInformationService.getRobotSoftAssetInformationByRobotId(robotSoftAssetInformation.getRobotId());
            if (!StringUtils.isEmpty(robotSoftAssetInformationByRobotId)){
                returnModel.setData("");
                returnModel.setMsg("该机器人已存在软资产信息");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
            int i = robotSoftAssetInformationService.addRobotSoftAssetInformation(robotSoftAssetInformation);
            if (i == 1){
                returnModel.setData("");
                returnModel.setMsg("机器人软资产信息新增成功");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            }else{
                returnModel.setData("");
                returnModel.setMsg("机器人软资产信息新增失败");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        }catch (Exception e){
            logger.info("addRobotSoftAssetInformation",e);
            returnModel.setData("");
            returnModel.setMsg("机器人软资产信息新增失败，请联系管理员");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    /**
     * @Method editRobotSoftAssetInformation
     * @Author 郭凯
     * @Version  1.0
     * @Description 机器人软资产信息编辑
     * @Return com.hna.hka.archive.management.system.util.ReturnModel
     * @Date 2021/5/28 14:23
     */
    @ApiOperation("机器人软资产信息编辑")
    @RequestMapping("/editRobotSoftAssetInformation")
    @ResponseBody
    public ReturnModel editRobotSoftAssetInformation( @RequestBody SysRobotSoftAssetInformation robotSoftAssetInformation){
        ReturnModel returnModel = new ReturnModel();
        try {

            //根据机器人编号查询数据是否存在
            SysRobotSoftAssetInformation sysRobotSoftAssetInformation = robotSoftAssetInformationService.getRobotSoftAssetInformationByRobotId(robotSoftAssetInformation.getRobotId());
            if (ToolUtil.isNotEmpty(sysRobotSoftAssetInformation)){
                int i = robotSoftAssetInformationService.editRobotSoftAssetInformation(robotSoftAssetInformation);
                if (i == 1){
                    returnModel.setData("");
                    returnModel.setMsg("机器人软资产信息编辑成功");
                    returnModel.setState(Constant.STATE_SUCCESS);
                    return returnModel;
                }else{
                    returnModel.setData("");
                    returnModel.setMsg("机器人软资产信息编辑失败");
                    returnModel.setState(Constant.STATE_FAILURE);
                    return returnModel;
                }
            }else{
                int i = robotSoftAssetInformationService.addRobotSoftAssetInformation(robotSoftAssetInformation);
                if (i == 1){
                    returnModel.setData("");
                    returnModel.setMsg("机器人软资产信息添加成功");
                    returnModel.setState(Constant.STATE_SUCCESS);
                    return returnModel;
                }else{
                    returnModel.setData("");
                    returnModel.setMsg("机器人软资产信息添加失败");
                    returnModel.setState(Constant.STATE_FAILURE);
                    return returnModel;
                }
            }
        }catch (Exception e){
            logger.info("editRobotSoftAssetInformation",e);
            returnModel.setData("");
            returnModel.setMsg("机器人软资产信息编辑失败，请联系管理员");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    /**
     * @Method delRobotSoftAssetInformation
     * @Author 郭凯
     * @Version  1.0
     * @Description 机器人软资产信息删除
     * @Return com.hna.hka.archive.management.system.util.ReturnModel
     * @Date 2021/5/28 14:24
     */
    @ApiOperation("机器人软资产信息删除")
    @RequestMapping("/delRobotSoftAssetInformation")
    @ResponseBody
    public ReturnModel delRobotSoftAssetInformation(Long softAssetInformationId){
        ReturnModel returnModel = new ReturnModel();
        try {
            int i = robotSoftAssetInformationService.delRobotSoftAssetInformation(softAssetInformationId);
            if (i == 1){
                returnModel.setData("");
                returnModel.setMsg("机器人软资产信息删除成功");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            }else{
                returnModel.setData("");
                returnModel.setMsg("机器人软资产信息删除失败");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        }catch (Exception e){
            logger.info("delRobotSoftAssetInformation",e);
            returnModel.setData("");
            returnModel.setMsg("机器人软资产信息删除失败，请联系管理员");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    /**
     * @Method uploadExcelRobotSoftAssetInformation
     * @Author 郭凯
     * @Version  1.0
     * @Description 下载机器人软资产信息Excel表
     * @Return void
     * @Date 2021/5/28 16:37
     */
    @ApiOperation("下载")
    @RequestMapping(value = "/uploadExcelRobotSoftAssetInformation")
    public void  uploadExcelRobotSoftAssetInformation(HttpServletResponse response, @RequestBody SysRobotSoftAssetInformation robotSoftAssetInformation) throws Exception {
        List<SysRobotSoftAssetInformation> robotSoftAssetInformationExcelList = null;
        SysUsers SysUsers = this.getSysUser();
        Map<String,Object> search = new HashMap<>();
        search.put("userId",SysUsers.getUserId().toString());
        search.put("robotCode",robotSoftAssetInformation.getRobotCode());
        search.put("robotModel",robotSoftAssetInformation.getRobotModel());
        search.put("scenicSpotId",robotSoftAssetInformation.getScenicSpotId());
        search.put("ascriptionCompanyId",robotSoftAssetInformation.getAscriptionCompanyId());
        search.put("equipmentStatus",robotSoftAssetInformation.getEquipmentStatus());
        robotSoftAssetInformationExcelList = robotSoftAssetInformationService.getRobotSoftAssetInformationExcel(search);
        String dateTime = DateFormatUtils.format(new Date(), "yyyyMMddHHmm");
        FileUtil.exportExcel(FileUtil.getWorkbook("机器人资产信息", "机器人资产信息", SysRobotSoftAssetInformation.class, robotSoftAssetInformationExcelList),"机器人资产信息"+ dateTime,response);
    }

    /**
     * @Method importExcel
     * @Author 郭凯
     * @Version  1.0
     * @Description 导入Excel表
     * @Return com.hna.hka.archive.management.system.util.ReturnModel
     * @Date 2021/7/5 16:56
     */
    @ApiOperation("导入")
    @RequestMapping("/importExcel")
    @ResponseBody
    public ReturnModel importExcel(@RequestPart("file") MultipartFile multipartFile) throws Exception {
        ReturnModel returnModel = new ReturnModel();
        try {
            ImportParams params = new ImportParams();
            params.setTitleRows(1);
            params.setHeadRows(1);
            List<SysRobotSoftAssetInformationExtendVo> sysRobotSoftAssetInformationExtendVos = ExcelImportUtil.importExcel(multipartFile.getInputStream(), SysRobotSoftAssetInformationExtendVo.class, params);
            SysRobotSoftAssetInformation robotSoftAsset = new SysRobotSoftAssetInformation();
            SysRobotSoftAssetInformation robotSoftAssetInformation = new SysRobotSoftAssetInformation();
            for (SysRobotSoftAssetInformationExtendVo robotSoftAssetInformationExtendVo : sysRobotSoftAssetInformationExtendVos){
                 robotSoftAsset = new SysRobotSoftAssetInformation();
                 robotSoftAssetInformation = robotSoftAssetInformationService.selectRobotSoftByRobotCode(robotSoftAssetInformationExtendVo.getRobotCode());
                  if (StringUtils.isEmpty(robotSoftAssetInformation)){

                      SysRobot robotCodeBy = sysRobotService.getRobotCodeBy(robotSoftAssetInformationExtendVo.getRobotCode());
                      robotSoftAsset.setRobotId(robotCodeBy.getRobotId());
                      robotSoftAsset.setSoftAssetInformationId(IdUtils.getSeqId());
                      robotSoftAsset.setRobotCode(robotSoftAssetInformationExtendVo.getRobotCode());
                      robotSoftAsset.setDateProduction(robotSoftAssetInformationExtendVo.getDateProduction());
                      robotSoftAsset.setBatteryWarrantyDeadline(robotSoftAssetInformationExtendVo.getBatteryWarrantyDeadline());
                      robotSoftAsset.setRobotWarrantyDeadline(robotSoftAssetInformationExtendVo.getBatteryWarrantyDeadline());
                      robotSoftAsset.setServiceLife(robotSoftAssetInformationExtendVo.getServiceLife());
                      robotSoftAsset.setFactoryCost(robotSoftAssetInformationExtendVo.getFactoryCost());
                      robotSoftAsset.setAccumulate(robotSoftAssetInformationExtendVo.getAccumulate());
                      robotSoftAsset.setNetPrice(robotSoftAssetInformationExtendVo.getNetPrice());
                      robotSoftAsset.setAscriptionCompanyName(robotSoftAssetInformationExtendVo.getAscriptionCompanyName());
                      robotSoftAsset.setCreateDate(DateUtil.currentDateTime());
                      robotSoftAsset.setUpdateDate(DateUtil.currentDateTime());
                      int i = robotSoftAssetInformationService.addRobotSoftAssetInformationImport(robotSoftAsset);
                  }else{
                      robotSoftAssetInformation.setDateProduction(robotSoftAssetInformationExtendVo.getDateProduction());
                      robotSoftAssetInformation.setBatteryWarrantyDeadline(robotSoftAssetInformationExtendVo.getBatteryWarrantyDeadline());
                      robotSoftAssetInformation.setRobotWarrantyDeadline(robotSoftAssetInformationExtendVo.getBatteryWarrantyDeadline());
                      robotSoftAssetInformation.setServiceLife(robotSoftAssetInformationExtendVo.getServiceLife());
                      robotSoftAssetInformation.setFactoryCost(robotSoftAssetInformationExtendVo.getFactoryCost());
                      robotSoftAssetInformation.setAccumulate(robotSoftAssetInformationExtendVo.getAccumulate());
                      robotSoftAssetInformation.setNetPrice(robotSoftAssetInformationExtendVo.getNetPrice());
                      robotSoftAssetInformation.setAscriptionCompanyName(robotSoftAssetInformationExtendVo.getAscriptionCompanyName());
                     int i = robotSoftAssetInformationService.editRobotSoftAssetInformationImport(robotSoftAssetInformation);
                  }

            }
            returnModel.setData("");
            returnModel.setMsg("导入成功");
            returnModel.setState(Constant.STATE_SUCCESS);
            return returnModel;
        }catch (Exception e){
            logger.info("景点列表导入Excel",e);
            returnModel.setData("");
            returnModel.setMsg("导入失败！（请联系管理员）");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }


    /**
     * 机器人调运记录
     * zhang
     */
    @ApiOperation("调用记录")
    @RequestMapping("/getSysDepositLogList")
    @ResponseBody
    public PageDataResult getSysDepositLogList(@RequestParam("pageNum") Integer pageNum,
                                               @RequestParam("pageSize") Integer pageSize, String robotCode) {
        PageDataResult pageDataResult = new PageDataResult();
        SysUsers SysUsers = this.getSysUser();
        Map<String,Object> search = new HashMap<>();
        try {
            if(null == pageNum) {
                pageNum = 1;
            }
            if(null == pageSize) {
                pageSize = 10;
            }
//            search.put("userId",SysUsers.getUserId().toString());
            search.put("robotDispatchCode",robotCode);
//            search.put("robotDispatchCallOutName",sysRobotDispatchLog.getRobotDispatchCallOutName());

            pageDataResult = sysRobotDispatchLogService.getSysDepositLogList(pageNum,pageSize,search);
        }catch (Exception e){
            logger.info("机器人调度日志列表查询失败",e);
        }
        return pageDataResult;
    }

    /**
     * 机器人故障记录
     * zhang
     * @param pageNum
     * @param pageSize
     * @param robotCode
     * @return
     */
    @ApiOperation("故障记录")
    @RequestMapping("/getRobotErrorRecordsList")
    @ResponseBody
    public PageDataResult getRobotErrorRecordsList(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize, String robotCode){
        PageDataResult pageDataResult = new PageDataResult();
        Map<String,Object> search = new HashMap<>();
        try {
            if(null == pageNum) {
                pageNum = 1;
            }
            if(null == pageSize) {
                pageSize = 10;
            }

            search.put("robotCode",robotCode);
//            search.put("scenicSpotId",sysRobotErrorRecords.getScenicSpotId());
//            search.put("errorRecordsName",sysRobotErrorRecords.getErrorRecordsName());
//            search.put("errorRecordsStatus",sysRobotErrorRecords.getErrorRecordsStatus());
//            search.put("errorRecordsQuality",sysRobotErrorRecords.getErrorRecordsQuality());
//            search.put("errorRecordsReceive",sysRobotErrorRecords.getErrorRecordsReceive());
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.DAY_OF_MONTH, 1);
            String startFormat = simpleDateFormat.format(calendar.getTime());
//            System.out.println(format);
            search.put("startTime",startFormat);
            calendar.add(Calendar.MONTH,1);
            calendar.set(Calendar.DAY_OF_MONTH,0);
            String endFormat = simpleDateFormat.format(calendar.getTime());
            search.put("endTime",endFormat);
            pageDataResult = sysRobotErrorRecordsService.getRobotErrorRecordsList(pageNum,pageSize,search);

        }catch (Exception e){
            logger.info("getRobotErrorRecordsList",e);
        }
        return pageDataResult;
    }

    /**
     * 机器人维修记录
     * zhang
     * @param pageNum
     * @param pageSize
     * @param robotCode
     * @return
     */
    @ApiOperation("维修记录")
    @RequestMapping("/getRobotServiceRecordsList")
    @ResponseBody
    public PageDataResult getRobotServiceRecordsList(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize, String robotCode){
        PageDataResult pageDataResult = new PageDataResult();
        Map<String,Object> search = new HashMap<>();
        try {
            if(null == pageNum) {
                pageNum = 1;
            }
            if(null == pageSize) {
                pageSize = 10;
            }

            search.put("robotCode",robotCode);
//            search.put("scenicSpotId",sysRobotServiceRecords.getScenicSpotId());
//            search.put("errorRecordsName",sysRobotServiceRecords.getErrorRecordsName());
//            search.put("errorRecordsModel",sysRobotServiceRecords.getErrorRecordsModel());
//            search.put("serviceRecordsPersonnel",sysRobotServiceRecords.getServiceRecordsPersonnel());

            pageDataResult = sysRobotServiceRecordsService.getRobotServiceRecordsList(pageNum,pageSize,search);
        }catch (Exception e){
            logger.info("getRobotServiceRecordsList",e);
        }
        return pageDataResult;
    }


    /**
     * 饼图统计设备状态
     */
    @ApiOperation("饼图统计设备状态")
    @RequestMapping("/StatisticsState")
    @ResponseBody
    public ReturnModel getRobotStatisticsState(){

        List<Map<String,String>> map = sysRobotService.getRobotStatisticsState();
        return new ReturnModel(map,"success",Constant.STATE_SUCCESS,null);

    }

    @ApiOperation("测试定时")
    @RequestMapping("/testTime")
    @ResponseBody
    public void testTime() {

        robotSoftAssetInformationService.updateRobotSoftAssetInformation();
        
        
    }
    
    








}
