package com.hna.hka.archive.management.assetsSystem.controller;

import com.hna.hka.archive.management.assetsSystem.model.SysRobotDamages;
import com.hna.hka.archive.management.assetsSystem.service.SysRobotDamagesService;
import com.hna.hka.archive.management.system.util.*;
import org.apache.commons.lang3.time.DateFormatUtils;
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
 * @ClassName: RobotDamagesController
 * @Author: 郭凯
 * @Description: 损坏赔偿单控制层
 * @Date: 2021/6/27 21:31
 * @Version: 1.0
 */
@RequestMapping("/system/robotDamages")
@Controller
public class RobotDamagesController extends PublicUtil {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private SysRobotDamagesService sysRobotDamagesService;


    /**
     * @Method getRobotDamagesList
     * @Author 郭凯
     * @Version  1.0
     * @Description 查询损坏赔偿单列表
     * @Return com.hna.hka.archive.management.system.util.PageDataResult
     * @Date 2021/6/27 21:37
     */
    @RequestMapping("/getRobotDamagesList")
    @ResponseBody
    public PageDataResult getRobotDamagesList(@RequestParam("pageNum") Integer pageNum,
                                                             @RequestParam("pageSize") Integer pageSize, SysRobotDamages sysRobotDamages){
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
            search.put("scenicSpotId",sysRobotDamages.getScenicSpotId());
            search.put("robotCode",sysRobotDamages.getRobotCode());
            search.put("damagesType",sysRobotDamages.getDamagesType());
            search.put("paymentPlatform",sysRobotDamages.getPaymentPlatform());
            //判断开始时间和结束是否为空，全部满足空的条件才已当前时间为条件查询
            if (ToolUtil.isEmpty(startTime) || ToolUtil.isEmpty(endTime)) {
                if (ToolUtil.isEmpty(sysRobotDamages.getScenicSpotId()) && ToolUtil.isEmpty(sysRobotDamages.getRobotCode()) && ToolUtil.isEmpty(sysRobotDamages.getDamagesType()) && ToolUtil.isEmpty(sysRobotDamages.getPaymentPlatform())) {
                    startTime = DateUtil.crutDate();
                    endTime = DateUtil.crutDate();
                }
            }
            search.put("startTime",startTime);
            search.put("endTime",endTime);
            pageDataResult = sysRobotDamagesService.getRobotDamagesList(pageNum,pageSize,search);
        }catch (Exception e){
            logger.info("getRobotDamagesList",e);
        }
        return pageDataResult;
    }

    /**
     * @Method closeRobotDamages
     * @Author 郭凯
     * @Version  1.0
     * @Description 关闭损坏赔偿
     * @Return com.hna.hka.archive.management.system.util.ReturnModel
     * @Date 2021/7/9 17:01
     */
    @RequestMapping("/closeRobotDamages")
    @ResponseBody
    public ReturnModel closeRobotDamages(SysRobotDamages sysRobotDamages){
        ReturnModel returnModel = new ReturnModel();
        try {
            int i = sysRobotDamagesService.editCloseRobotDamages(sysRobotDamages);
            if(i == 1){
                returnModel.setData("");
                returnModel.setMsg("关闭成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            }else{
                returnModel.setData("");
                returnModel.setMsg("关闭失败！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        }catch (Exception e){
            logger.info("closeRobotDamages",e);
            returnModel.setData("");
            returnModel.setMsg("系统错误！");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    /**
     * @Method uploadExcelRobotDamages
     * @Author 郭凯
     * @Version  1.0
     * @Description 导出损坏赔偿Excel表
     * @Return void
     * @Date 2021/7/19 18:19
     */
    @RequestMapping(value = "/uploadExcelRobotDamages")
    public void  uploadExcelRobotDamages(HttpServletResponse response, SysRobotDamages sysRobotDamages) throws Exception {
        List<SysRobotDamages> sysRobotDamagesList = null;
        Map<String,Object> search = new HashMap<>();
        search.put("scenicSpotId",sysRobotDamages.getScenicSpotId());
        search.put("robotCode",sysRobotDamages.getRobotCode());
        search.put("paymentPlatform",sysRobotDamages.getPaymentPlatform());
        search.put("damagesType",sysRobotDamages.getDamagesType());
        search.put("startTime",sysRobotDamages.getStartTime());
        search.put("endTime",sysRobotDamages.getEndTime());
        sysRobotDamagesList = sysRobotDamagesService.getRobotDamagesExcel(search);
        String dateTime = DateFormatUtils.format(new Date(), "yyyyMMddHHmm");
        FileUtil.exportExcel(FileUtil.getWorkbook("损坏赔偿信息", "损坏赔偿信息", SysRobotDamages.class, sysRobotDamagesList),"损坏赔偿信息"+ dateTime,response);
    }

    /**
     * @Method editfixedLossAmount
     * @Author 郭凯
     * @Version  1.0
     * @Description 修改定损金额
     * @Return com.hna.hka.archive.management.system.util.ReturnModel
     * @Date 2021/7/20 9:27
     */
    @RequestMapping("/editfixedLossAmount")
    @ResponseBody
    public ReturnModel editfixedLossAmount(SysRobotDamages sysRobotDamages){
        ReturnModel returnModel = new ReturnModel();
        try {
            int i = sysRobotDamagesService.editCloseRobotDamages(sysRobotDamages);
            if(i == 1){
                returnModel.setData("");
                returnModel.setMsg("定损金额修改成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            }else{
                returnModel.setData("");
                returnModel.setMsg("定损金额修改失败！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        }catch (Exception e){
            logger.info("editfixedLossAmount",e);
            returnModel.setData("");
            returnModel.setMsg("系统错误！");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

}
