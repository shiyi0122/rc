package com.hna.hka.archive.management.assetsSystem.controller;

import com.hna.hka.archive.management.assetsSystem.model.SysOrderExceptionManagement;
import com.hna.hka.archive.management.assetsSystem.service.SysOrderExceptionManagementService;
import com.hna.hka.archive.management.system.util.*;
import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ProjectName: rc
 * @Package: com.hna.hka.archive.management.assetsSystem.controller
 * @ClassName: OrderExceptionManagementController
 * @Author: 郭凯
 * @Description: 异常订单管理控制层
 * @Date: 2021/6/2 14:36
 * @Version: 1.0
 */
@RequestMapping("/system/orderExceptionManagement")
@Controller
public class OrderExceptionManagementController extends PublicUtil {

    @Autowired
    private SysOrderExceptionManagementService sysOrderExceptionManagementService;


    /**
     * @Method getOrderExceptionManagementList
     * @Author 郭凯
     * @Version  1.0
     * @Description 异常订单管理列表查询
     * @Return com.hna.hka.archive.management.system.util.PageDataResult
     * @Date 2021/6/2 14:41
     */
    @RequestMapping("/getOrderExceptionManagementList")
    @ResponseBody
    public PageDataResult getOrderExceptionManagementList(@RequestParam("pageNum") Integer pageNum,
                                                           @RequestParam("pageSize") Integer pageSize, SysOrderExceptionManagement sysOrderExceptionManagement) {
        PageDataResult pageDataResult = new PageDataResult();
        Map<String,Object> search = new HashMap<>();
        try {
            if(null == pageNum) {
                pageNum = 1;
            }
            if(null == pageSize) {
                pageSize = 10;
            }
            search.put("causes",sysOrderExceptionManagement.getCauses());
            search.put("reason",sysOrderExceptionManagement.getReason());
            pageDataResult = sysOrderExceptionManagementService.getOrderExceptionManagementList(pageNum,pageSize,search);
        }catch (Exception e){
            logger.info("异常订单管理列表查询失败",e);
        }
        return pageDataResult;
    }

    /**
     * @Method addOrderExceptionManagement
     * @Author 郭凯
     * @Version  1.0
     * @Description 异常订单管理新增
     * @Return com.hna.hka.archive.management.system.util.ReturnModel
     * @Date 2021/6/3 9:14
     */
    @RequestMapping(value = "/addOrderExceptionManagement")
    @ResponseBody
    public ReturnModel addOrderExceptionManagement(SysOrderExceptionManagement sysOrderExceptionManagement) {
        ReturnModel dataModel = new ReturnModel();
        try {
            int i = sysOrderExceptionManagementService.addOrderExceptionManagement(sysOrderExceptionManagement);
            if (i == 1) {
                dataModel.setData("");
                dataModel.setMsg("异常订单管理新增成功！");
                dataModel.setState(Constant.STATE_SUCCESS);
                return dataModel;
            } else {
                dataModel.setData("");
                dataModel.setMsg("异常订单管理新增失败！");
                dataModel.setState(Constant.STATE_FAILURE);
                return dataModel;
            }
        } catch (Exception e) {
            dataModel.setData("");
            dataModel.setMsg("异常订单管理新增失败！");
            dataModel.setState(Constant.STATE_FAILURE);
            logger.error("addOrderExceptionManagement", e);
            return dataModel;
        }
    }

    /**
     * @Method editOrderExceptionManagement
     * @Author 郭凯
     * @Version  1.0
     * @Description 异常订单管理编辑
     * @Return com.hna.hka.archive.management.system.util.ReturnModel
     * @Date 2021/6/3 9:28
     */
    @RequestMapping(value = "/editOrderExceptionManagement")
    @ResponseBody
    public ReturnModel editOrderExceptionManagement(SysOrderExceptionManagement sysOrderExceptionManagement) {
        ReturnModel dataModel = new ReturnModel();
        try {
            int i = sysOrderExceptionManagementService.editOrderExceptionManagement(sysOrderExceptionManagement);
            if (i == 1) {
                dataModel.setData("");
                dataModel.setMsg("异常订单管理编辑成功！");
                dataModel.setState(Constant.STATE_SUCCESS);
                return dataModel;
            } else {
                dataModel.setData("");
                dataModel.setMsg("异常订单管理编辑失败！");
                dataModel.setState(Constant.STATE_FAILURE);
                return dataModel;
            }
        } catch (Exception e) {
            dataModel.setData("");
            dataModel.setMsg("异常订单管理编辑失败！");
            dataModel.setState(Constant.STATE_FAILURE);
            logger.error("editOrderExceptionManagement", e);
            return dataModel;
        }
    }


    /**
     * @Method delOrderExceptionManagement
     * @Author 郭凯
     * @Version  1.0
     * @Description 异常订单管理删除
     * @Return com.hna.hka.archive.management.system.util.ReturnModel
     * @Date 2021/6/3 9:35
     */
    @RequestMapping(value = "/delOrderExceptionManagement")
    @ResponseBody
    public ReturnModel delOrderExceptionManagement(Long orderExceptionManagementId) {
        ReturnModel dataModel = new ReturnModel();
        try {
            int i = sysOrderExceptionManagementService.delOrderExceptionManagement(orderExceptionManagementId);
            if (i == 1) {
                dataModel.setData("");
                dataModel.setMsg("异常订单管理删除成功！");
                dataModel.setState(Constant.STATE_SUCCESS);
                return dataModel;
            } else {
                dataModel.setData("");
                dataModel.setMsg("异常订单管理删除失败！");
                dataModel.setState(Constant.STATE_FAILURE);
                return dataModel;
            }
        } catch (Exception e) {
            dataModel.setData("");
            dataModel.setMsg("异常订单管理删除失败！");
            dataModel.setState(Constant.STATE_FAILURE);
            logger.error("delOrderExceptionManagement", e);
            return dataModel;
        }
    }

    /**
     * @Method uploadExcelOderExceptionManagement
     * @Author 郭凯
     * @Version  1.0
     * @Description 异常订单信息下载Excel
     * @Return void
     * @Date 2021/6/3 10:13
     */
    @RequestMapping(value = "/uploadExcelOderExceptionManagement")
    public void uploadExcelOderExceptionManagement(HttpServletResponse response,SysOrderExceptionManagement sysOrderExceptionManagement){
        Map<String,Object> search = new HashMap<>();
        search.put("reason",sysOrderExceptionManagement.getReason());
        search.put("causes",sysOrderExceptionManagement.getCauses());
        List<SysOrderExceptionManagement> orderExceptionManagementExcelList = sysOrderExceptionManagementService.getOderExceptionManagementExcel(search);
        String dateTime = DateFormatUtils.format(new Date(), "yyyyMMddHHmm");
        FileUtil.exportExcel(FileUtil.getWorkbook("异常订单信息", "异常订单信息", SysOrderExceptionManagement.class, orderExceptionManagementExcelList),"异常订单信息"+ dateTime,response);
    }

    /**
     * @Method getOrderExceptionManagementVoList
     * @Author 郭凯
     * @Version  1.0
     * @Description 查询异常订单
     * @Return com.hna.hka.archive.management.system.util.ReturnModel
     * @Date 2021/7/9 18:03
     */
    @RequestMapping("/getOrderExceptionManagementVoList")
    @ResponseBody
    public ReturnModel getOrderExceptionManagementVoList() {
        ReturnModel dataModel = new ReturnModel();
        try {
            List<SysOrderExceptionManagement> orderExceptionManagementList = sysOrderExceptionManagementService.getOrderExceptionManagementVoList();
            if (ToolUtil.isNotEmpty(orderExceptionManagementList)) {
                dataModel.setData(orderExceptionManagementList);
                dataModel.setMsg("查询成功！");
                dataModel.setState(Constant.STATE_SUCCESS);
                return dataModel;
            } else {
                dataModel.setData("");
                dataModel.setMsg("查询失败！");
                dataModel.setState(Constant.STATE_FAILURE);
                return dataModel;
            }
        } catch (Exception e) {
            dataModel.setData("");
            dataModel.setMsg("查询失败！");
            dataModel.setState(Constant.STATE_FAILURE);
            logger.error("getOrderExceptionManagementVoList", e);
            return dataModel;
        }
    }


}
