package com.hna.hka.archive.management.assetsSystem.controller;

import com.github.pagehelper.PageInfo;
import com.hna.hka.archive.management.appSystem.model.SysRobotAccessoriesApplicationDetail;
import com.hna.hka.archive.management.assetsSystem.model.SysOrderExceptionManagement;
import com.hna.hka.archive.management.assetsSystem.model.SysRobotAccessoriesApplication;
import com.hna.hka.archive.management.assetsSystem.model.SysRobotPartsManagement;
import com.hna.hka.archive.management.assetsSystem.service.SysRobotAccessoriesApplicationService;
import com.hna.hka.archive.management.system.model.SysRobot;
import com.hna.hka.archive.management.system.util.*;
import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ProjectName: rc
 * @Package: com.hna.hka.archive.management.assetsSystem.controller
 * @ClassName: RobotAccessoriesApplicationController
 * @Author: 郭凯
 * @Description: 配件申请控制层
 * @Date: 2021/6/27 18:41
 * @Version: 1.0
 */
@RequestMapping("/system/robotAccessoriesApplication")
@Controller
public class RobotAccessoriesApplicationController extends PublicUtil {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private SysRobotAccessoriesApplicationService sysRobotAccessoriesApplicationService;

    /**
     * @Method getRobotAccessoriesApplicationList
     * @Author 郭凯
     * @Version  1.0
     * @Description 查询机器人配件申请列表
     * @Return com.hna.hka.archive.management.system.util.PageDataResult
     * @Date 2021/6/27 18:48
     */
    @RequestMapping("/getRobotAccessoriesApplicationList")
    @ResponseBody
    public PageDataResult getRobotAccessoriesApplicationList(@RequestParam("pageNum") Integer pageNum,
                                         @RequestParam("pageSize") Integer pageSize, SysRobotAccessoriesApplication sysRobotAccessoriesApplication){
        PageDataResult pageDataResult = new PageDataResult();
        Map<String,Object> search = new HashMap<>();
        try {
            if(null == pageNum) {
                pageNum = 1;
            }
            if(null == pageSize) {
                pageSize = 10;
            }
            String startTime = request.getParameter("startTime");
            String endTime = request.getParameter("endTime");
            search.put("scenicSpotId",sysRobotAccessoriesApplication.getScenicSpotId());
            search.put("accessoriesName",sysRobotAccessoriesApplication.getAccessoriesName());
            search.put("applicant",sysRobotAccessoriesApplication.getApplicant());
            search.put("processingProgress",sysRobotAccessoriesApplication.getProcessingProgress());
            //判断开始时间和结束是否为空，全部满足空的条件才已当前时间为条件查询
//            if (ToolUtil.isEmpty(startTime) || ToolUtil.isEmpty(endTime)) {
//                if (ToolUtil.isEmpty(sysRobotAccessoriesApplication.getScenicSpotId()) && ToolUtil.isEmpty(sysRobotAccessoriesApplication.getAccessoriesName()) && ToolUtil.isEmpty(sysRobotAccessoriesApplication.getApplicant()) && ToolUtil.isEmpty(sysRobotAccessoriesApplication.getProcessingProgress())) {
//                    startTime = DateUtil.crutDate();
//                    endTime = DateUtil.crutDate();
//                }
//            }
            search.put("startTime",startTime);
            search.put("endTime",endTime);
            pageDataResult = sysRobotAccessoriesApplicationService.getRobotAccessoriesApplicationVoList(pageNum,pageSize,search);
        }catch (Exception e){
            logger.info("getRobotAccessoriesApplicationList",e);
        }
        return pageDataResult;
    }

    /**
     * @Method uploadExcelRobotAccessoriesApplication
     * @Author 郭凯
     * @Version  1.0
     * @Description 下载Excel表
     * @Return void
     * @Date 2021/7/8 19:21
     */
    @RequestMapping(value = "/uploadExcelRobotAccessoriesApplication")
    public void  uploadExcelRobotAccessoriesApplication(HttpServletResponse response, SysRobotAccessoriesApplication sysRobotAccessoriesApplication){
        Map<String,Object> search = new HashMap<>();
        String startTime = request.getParameter("startTime");
        String endTime = request.getParameter("endTime");
        search.put("scenicSpotId",sysRobotAccessoriesApplication.getScenicSpotId());
        search.put("accessoriesName",sysRobotAccessoriesApplication.getAccessoriesName());
        search.put("applicant",sysRobotAccessoriesApplication.getApplicant());
        search.put("processingProgress",sysRobotAccessoriesApplication.getProcessingProgress());
        //判断开始时间和结束是否为空，全部满足空的条件才已当前时间为条件查询
        if (ToolUtil.isEmpty(startTime) || ToolUtil.isEmpty(endTime)) {
            if (ToolUtil.isEmpty(sysRobotAccessoriesApplication.getScenicSpotId()) && ToolUtil.isEmpty(sysRobotAccessoriesApplication.getAccessoriesName()) && ToolUtil.isEmpty(sysRobotAccessoriesApplication.getApplicant()) && ToolUtil.isEmpty(sysRobotAccessoriesApplication.getProcessingProgress())) {
                startTime = DateUtil.crutDate();
                endTime = DateUtil.crutDate();
            }
        }
        search.put("startTime",startTime);
        search.put("endTime",endTime);
        List<SysRobotAccessoriesApplication> robotAccessoriesApplicationList = sysRobotAccessoriesApplicationService.getRobotAccessoriesApplicationExcel(search);
        String dateTime = DateFormatUtils.format(new Date(), "yyyyMMddHHmm");
        FileUtil.exportExcel(FileUtil.getWorkbook("配件申请信息", "配件申请信息", SysRobotAccessoriesApplication.class, robotAccessoriesApplicationList),"配件申请信息"+ dateTime,response);
    }

    /**
     * @Method editRobotAccessoriesApplication
     * @Author 郭凯
     * @Version  1.0
     * @Description 编辑配件申请处理状态
     * @Return com.hna.hka.archive.management.system.util.ReturnModel
     * @Date 2021/7/9 16:30
     */
    @RequestMapping("/editRobotAccessoriesApplication")
    @ResponseBody
    public ReturnModel editRobotAccessoriesApplication(SysRobotAccessoriesApplication sysRobotAccessoriesApplication){
        ReturnModel returnModel = new ReturnModel();
        try {
            int i = sysRobotAccessoriesApplicationService.editRobotAccessoriesApplication(sysRobotAccessoriesApplication);
            if (i == 1){
                returnModel.setData("");
                returnModel.setMsg("配件申请处理状态修改成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            }else{
                returnModel.setData("");
                returnModel.setMsg("配件申请处理状态修改失败！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        }catch (Exception e){
            logger.info("editRobotAccessoriesApplication",e);
            returnModel.setData("");
            returnModel.setMsg("配件申请处理状态修改失败！");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    /**
     * @Method
     * @Author zhang
     * @Version  1.0
     * @Description 根据配件name查询配件详情
     * @Return com.hna.hka.archive.management.system.util.ReturnModel
     * @Date
     */
    @RequestMapping("/robotAccessoriesManagementApplication")
    @ResponseBody
    public  PageDataResult robotAccessoriesManagementApplication(String accessoriesName,String accessoriesCode){
        ReturnModel returnModel = new ReturnModel();
        PageDataResult pageDataResult = new PageDataResult();

        try {
            List<SysRobotPartsManagement> sysRobotPartsManagementList = sysRobotAccessoriesApplicationService.robotAccessoriesManagementApplication(accessoriesName,accessoriesCode);
            if (ToolUtil.isNotEmpty(sysRobotPartsManagementList)){
                pageDataResult.setList(sysRobotPartsManagementList);
                PageInfo<SysRobotPartsManagement> p = new PageInfo<>(sysRobotPartsManagementList);
                pageDataResult.setTotals((int)p.getTotal());
                pageDataResult.setCode(200);

//                returnModel.setData(sysRobotPartsManagementList);
//                returnModel.setMsg("配件查询成功！");
//                returnModel.setState(Constant.STATE_SUCCESS);
                return pageDataResult;
            }else{
                returnModel.setData("");
                returnModel.setMsg("配件查询失败！");
                returnModel.setState(Constant.STATE_FAILURE);
                return pageDataResult;
            }
        }catch (Exception e){
            logger.info("editRobotAccessoriesApplication",e);
            returnModel.setData("");
            returnModel.setMsg("配件查询失败！");
            returnModel.setState(Constant.STATE_FAILURE);
            return pageDataResult;
        }
    }



    /**
     * @Method
     * @Author zhang
     * @Version  1.0
     * @Description 根据配件申请id查询配件详情
     * @Return com.hna.hka.archive.management.system.util.ReturnModel
     * @Date
     */
    @RequestMapping("/robotAccessoriesIdByApplication")
    @ResponseBody
    public  PageDataResult robotAccessoriesIdByApplication(String accessoriesApplicationId){
        ReturnModel returnModel = new ReturnModel();
        PageDataResult pageDataResult = new PageDataResult();

        try {
            List<SysRobotAccessoriesApplicationDetail> SysRobotAccessoriesApplicationDetailList = sysRobotAccessoriesApplicationService.robotAccessoriesIdByApplication(accessoriesApplicationId);
            if (ToolUtil.isNotEmpty(SysRobotAccessoriesApplicationDetailList)){
                pageDataResult.setList(SysRobotAccessoriesApplicationDetailList);
                PageInfo<SysRobotAccessoriesApplicationDetail> p = new PageInfo<>(SysRobotAccessoriesApplicationDetailList);
                pageDataResult.setTotals((int)p.getTotal());
                pageDataResult.setCode(200);

//                returnModel.setData(sysRobotPartsManagementList);
//                returnModel.setMsg("配件查询成功！");
//                returnModel.setState(Constant.STATE_SUCCESS);
                return pageDataResult;
            }else{
                returnModel.setData("");
                returnModel.setMsg("配件查询失败！");
                returnModel.setState(Constant.STATE_FAILURE);
                return pageDataResult;
            }
        }catch (Exception e){
            logger.info("editRobotAccessoriesApplication",e);
            returnModel.setData("");
            returnModel.setMsg("配件查询失败！");
            returnModel.setState(Constant.STATE_FAILURE);
            return pageDataResult;
        }
    }

}
