package com.hna.hka.archive.management.assetsSystem.controller;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.hna.hka.archive.management.assetsSystem.model.SysRobotErrorRecords;
import com.hna.hka.archive.management.assetsSystem.model.SysRobotPartsManagement;
import com.hna.hka.archive.management.assetsSystem.model.SysRobotPartsManagementType;
import com.hna.hka.archive.management.assetsSystem.service.SysRobotPartsManagementService;
import com.hna.hka.archive.management.assetsSystem.service.SysRobotPartsManagementTypeService;
import com.hna.hka.archive.management.system.util.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ProjectName: rc
 * @Package: com.hna.hka.archive.management.assetsSystem.controller
 * @ClassName: RobotPartsManagementController
 * @Author: 郭凯
 * @Description: 机器人配件管理控制层
 * @Date: 2021/5/28 18:03
 * @Version: 1.0
 */
@RequestMapping("/system/robotPartsManagement")
@Controller
public class RobotPartsManagementController extends PublicUtil {

    @Autowired
    private SysRobotPartsManagementService sysRobotPartsManagementService;

    @Autowired
    private SysRobotPartsManagementTypeService sysRobotPartsManagementTypeService;

    /**
     * @Method getRobotPartsManagementList
     * @Author 郭凯
     * @Version  1.0
     * @Description 机器人配件管理列表查询
     * @Return com.hna.hka.archive.management.system.util.PageDataResult
     * @Date 2021/5/31 9:49
     */
    @RequestMapping("/getRobotPartsManagementList")
    @ResponseBody
    public PageDataResult getRobotPartsManagementList(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize,SysRobotPartsManagement robotPartsManagement) {
        PageDataResult pageDataResult = new PageDataResult();
        Map<String,Object> search = new HashMap<>();
        try {
            if(null == pageNum) {
                pageNum = 1;
            }
            if(null == pageSize) {
                pageSize = 10;
            }
            search.put("accessoriesTypeId",robotPartsManagement.getAccessoriesTypeId());
            search.put("faultName",robotPartsManagement.getFaultName());
            search.put("accessoryName",robotPartsManagement.getAccessoryName());
            search.put("accessoryModel",robotPartsManagement.getAccessoryModel());
            pageDataResult = sysRobotPartsManagementService.getRobotPartsManagementList(pageNum,pageSize,search);
        }catch (Exception e){
            logger.info("机器人配件管理列表查询失败",e);
        }
        return pageDataResult;
    }

    /**
     * @Method addRobotPartsManagement
     * @Author 郭凯
     * @Version  1.0
     * @Description 机器人配件信息新增
     * @Return com.hna.hka.archive.management.system.util.ReturnModel
     * @Date 2021/5/31 11:01
     */
    @RequestMapping(value = "/addRobotPartsManagement")
    @ResponseBody
    public ReturnModel addRobotPartsManagement(SysRobotPartsManagement SysRobotPartsManagement) {
        ReturnModel dataModel = new ReturnModel();
        try {
            int i = sysRobotPartsManagementService.addRobotPartsManagement(SysRobotPartsManagement);
            if (i == 1) {
                dataModel.setData("");
                dataModel.setMsg("机器人配件信息新增成功！");
                dataModel.setState(Constant.STATE_SUCCESS);
                return dataModel;
            }else{
                dataModel.setData("");
                dataModel.setMsg("机器人配件信息新增失败！");
                dataModel.setState(Constant.STATE_FAILURE);
                return dataModel;
            }
        } catch (Exception e) {
            dataModel.setData("");
            dataModel.setMsg("机器人配件信息新增失败！(请联系管理员)");
            dataModel.setState(Constant.STATE_FAILURE);
            logger.error("addRobotPartsManagement", e);
            return dataModel;
        }
    }

    /**
     * @Method editRobotPartsManagement
     * @Author 郭凯
     * @Version  1.0
     * @Description 机器人配件信息编辑
     * @Return com.hna.hka.archive.management.system.util.ReturnModel
     * @Date 2021/5/31 11:14
     */
    @RequestMapping(value = "/editRobotPartsManagement")
    @ResponseBody
    public ReturnModel editRobotPartsManagement(SysRobotPartsManagement SysRobotPartsManagement) {
        ReturnModel dataModel = new ReturnModel();
        try {
            int i = sysRobotPartsManagementService.editRobotPartsManagement(SysRobotPartsManagement);
            if (i == 1) {
                dataModel.setData("");
                dataModel.setMsg("机器人配件信息编辑成功！");
                dataModel.setState(Constant.STATE_SUCCESS);
                return dataModel;
            }else{
                dataModel.setData("");
                dataModel.setMsg("机器人配件信息编辑失败！");
                dataModel.setState(Constant.STATE_FAILURE);
                return dataModel;
            }
        } catch (Exception e) {
            dataModel.setData("");
            dataModel.setMsg("机器人配件信息编辑失败！(请联系管理员)");
            dataModel.setState(Constant.STATE_FAILURE);
            logger.error("editRobotPartsManagement", e);
            return dataModel;
        }
    }

    /**
     * @Method delRobotPartsManagement
     * @Author 郭凯
     * @Version  1.0
     * @Description 机器人配件信息删除
     * @Return com.hna.hka.archive.management.system.util.ReturnModel
     * @Date 2021/5/31 11:21
     */
    @RequestMapping(value = "/delRobotPartsManagement")
    @ResponseBody
    public ReturnModel delRobotPartsManagement(Long partsManagementId) {
        ReturnModel dataModel = new ReturnModel();
        try {
            int i = sysRobotPartsManagementService.delRobotPartsManagement(partsManagementId);
            if (i == 1) {
                dataModel.setData("");
                dataModel.setMsg("机器人配件信息删除成功！");
                dataModel.setState(Constant.STATE_SUCCESS);
                return dataModel;
            }else{
                dataModel.setData("");
                dataModel.setMsg("机器人配件信息删除失败！");
                dataModel.setState(Constant.STATE_FAILURE);
                return dataModel;
            }
        } catch (Exception e) {
            dataModel.setData("");
            dataModel.setMsg("机器人配件信息删除失败！(请联系管理员)");
            dataModel.setState(Constant.STATE_FAILURE);
            logger.error("delRobotPartsManagement", e);
            return dataModel;
        }
    }

    /**
     * @Method uploadExcelRobotPartsManagement
     * @Author 郭凯
     * @Version  1.0
     * @Description 下载机器人配件信息Excel表
     * @Return void
     * @Date 2021/5/31 13:33
     */
    @RequestMapping(value = "/uploadExcelRobotPartsManagement")
    public void  uploadExcelRobotPartsManagement(HttpServletResponse response, @ModelAttribute SysRobotPartsManagement robotPartsManagement){
        Map<String,Object> search = new HashMap<>();
        search.put("accessoriesTypeId",robotPartsManagement.getAccessoriesTypeId());
//        search.put("faultName",robotPartsManagement.getFaultName());
        search.put("accessoryName",robotPartsManagement.getAccessoryName());
        search.put("accessoryModel",robotPartsManagement.getAccessoryModel());
        List<SysRobotPartsManagement> partsManagementExcelList = sysRobotPartsManagementService.getRobotPartsManagementExcel(search);
        String dateTime = DateFormatUtils.format(new Date(), "yyyyMMddHHmm");
        FileUtil.exportExcel(FileUtil.getWorkbook("机器人配件信息", "机器人配件信息", SysRobotPartsManagement.class, partsManagementExcelList),"机器人配件信息"+ dateTime +".xls",response);
    }

    /**
     * @Method importExcel
     * @Author 郭凯
     * @Version  1.0
     * @Description 导入Excel表
     * @Return com.hna.hka.archive.management.system.util.ReturnModel
     * @Date 2021/7/5 17:56
     */
    @RequestMapping("/importExcel")
    @ResponseBody
    public ReturnModel importExcel(@RequestParam("file") MultipartFile multipartFile) throws Exception {
        ReturnModel returnModel = new ReturnModel();
        SysRobotPartsManagementType sysRobotPartsManagementType = new SysRobotPartsManagementType();
        try {
            ImportParams params = new ImportParams();
            params.setTitleRows(1);
            params.setHeadRows(1);
            List<SysRobotPartsManagement> result = ExcelImportUtil.importExcel(multipartFile.getInputStream(),SysRobotPartsManagement.class, params);
            for (SysRobotPartsManagement sysRobotPartsManagement:result){
//                sysRobotPartsManagement.setAccessoriesType(sysRobotPartsManagement.getAccessoriesTypeName());
                if (!StringUtils.isEmpty(sysRobotPartsManagement.getAccessoriesTypeName())){
                    String accessoriesTypeName = sysRobotPartsManagement.getAccessoriesTypeName();
                    sysRobotPartsManagementType = sysRobotPartsManagementTypeService.getPartsManagementTypeByOne(accessoriesTypeName);
                    sysRobotPartsManagement.setAccessoriesTypeId(sysRobotPartsManagementType.getId().toString());
                    sysRobotPartsManagementService.addRobotPartsManagement(sysRobotPartsManagement);
                }else{
                    continue;
                }

            }
            returnModel.setData("");
            returnModel.setMsg("导入成功");
            returnModel.setState(Constant.STATE_SUCCESS);
            return returnModel;
        }catch (Exception e){
            logger.info("importExcel", e);
            returnModel.setData("");
            returnModel.setMsg("导入失败！（请联系管理员）");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }



}
