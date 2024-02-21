package com.hna.hka.archive.management.system.controller;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.hna.hka.archive.management.system.model.*;
import com.hna.hka.archive.management.system.service.SysRobotSimSupplierService;
import com.hna.hka.archive.management.system.util.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2023/6/2 10:58
 */
@Api(tags = "机器人sim卡相关接口")
@RequestMapping("/system/sysRobotSimSupplier")
@Controller
public class SysRobotSimSupplierController {

    @Autowired
    SysRobotSimSupplierService sysRobotSimSupplierService;

    /**
     * 添加sim卡信息
     * @param
     * @param
     * @return
     */
    @ApiOperation(value = "添加sim卡信息")
    @RequestMapping("/addSysRobotSimSupplier")
    @ResponseBody
    public ReturnModel addSysRobotSimSupplier(SysRobotSimSupplier sysRobotSimSupplier) {

        ReturnModel returnModel = new ReturnModel();

        int i  = sysRobotSimSupplierService.addSysRobotSimSupplier(sysRobotSimSupplier);

        if (i>0){
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_SUCCESS);
            returnModel.setMsg("添加成功");
        }else{
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("添加失败");
        }

        return returnModel;

    }


    @ApiOperation(value = "后台编辑sim卡信息")
    @RequestMapping("/editSysRobotSimSupplier")
    @ResponseBody
    public ReturnModel editSysRobotSimSupplier(SysRobotSimSupplier sysRobotSimSupplier) {

        ReturnModel returnModel = new ReturnModel();

        int i = sysRobotSimSupplierService.editSysRobotSimSupplier(sysRobotSimSupplier);
        if (i>0){
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_SUCCESS);
            returnModel.setMsg("修改成功");
        }else{
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("修改失败");
        }
        return returnModel;

    }


    @ApiOperation(value = "sim卡信息列表查询")
    @RequestMapping("getSysRobotSimSupplierList")
    @ResponseBody
    public PageDataResult getSysRobotSimSupplierList(Integer pageNum, Integer pageSize,SysRobotSimSupplier sysRobotSimSupplier) {

        PageDataResult pageDataResult = new PageDataResult();
        Map<String, Object> search = new HashMap<>();
        search.put("simCard",sysRobotSimSupplier.getSimCard());
        search.put("robotCode",sysRobotSimSupplier.getRobotCode());
        search.put("supplierName",sysRobotSimSupplier.getSupplierName());
        pageDataResult  = sysRobotSimSupplierService.getSysRobotSimSupplierList(pageNum,pageSize, search);
        return pageDataResult;

    }


    @ApiOperation(value = "sim卡信息删除")
    @RequestMapping("delSysRobotSimSupplier")
    @ResponseBody
    public ReturnModel delSysRobotSimSupplier(SysRobotSimSupplier sysRobotSimSupplier) {

        ReturnModel returnModel = new ReturnModel();

        int i   = sysRobotSimSupplierService.delSysRobotSimSupplier(sysRobotSimSupplier.getId());
        if (i>0){
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_SUCCESS);
            returnModel.setMsg("删除成功");
        }else{
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("删除失败");
        }
        return returnModel;

    }

    /**
     * @Author 郭凯
     * @Description sim卡号下载EXCEL表
     * @Date 14:00 2020/5/25
     * @Param [response, request, SysRobot]
     * @return void
     **/
    @RequestMapping(value = "/uploadExcelSim")
    public void  uploadExcelSim(HttpServletResponse response, @ModelAttribute SysRobotSimSupplier sysRobotSimSupplier) throws Exception {
        List<SysRobotSimSupplier> simListByExample = null;
        Map<String,Object> search = new HashMap<>();
//
            search.put("supplierName",sysRobotSimSupplier.getSupplierName());
            search.put("robotCode",sysRobotSimSupplier.getRobotCode());
            search.put("simCard",sysRobotSimSupplier.getSimCard());
            simListByExample = sysRobotSimSupplierService.getSimExcel(search);


        String dateTime = DateFormatUtils.format(new Date(), "yyyyMMddHHmm");
        FileUtil.exportExcel(FileUtil.getWorkbook("ICCID卡号列表", "ICCID卡号列表", SysRobotSimSupplier.class, simListByExample),"ICCID卡号列表"+ dateTime +".xls",response);
    }


    /**
     * @Author 郭凯
     * @Description: 批量导入ICCID
     * @Title: upload
     * @date  2021年2月24日 上午10:54:23
     * @param @param multipartFile
     * @param @return
     * @param @throws Exception
     * @return ReturnModel
     * @throws
     */
    @RequestMapping("/upload")
    @ResponseBody
    public ReturnModel upload(@RequestParam("file") MultipartFile multipartFile) throws Exception {
        ReturnModel returnModel = new ReturnModel();
        try {
            ImportParams params = new ImportParams();
            params.setTitleRows(1);
            params.setHeadRows(1);
            List<SysRobotSimSupplier> result = ExcelImportUtil.importExcel(multipartFile.getInputStream(),SysRobotSimSupplier.class, params);
            String robotCode = "";
            for (SysRobotSimSupplier sysRobotSimSupplier:result){
                //查询SIM卡是否重复
                SysRobotSimSupplier simById = sysRobotSimSupplierService.getSimById(sysRobotSimSupplier.getSimCard());
                if (!ToolUtil.isEmpty(simById)) {
                    robotCode += sysRobotSimSupplier.getSimCard() + "，";
                }else {
                    sysRobotSimSupplierService.addSysRobotSimSupplier(sysRobotSimSupplier);
                }
            }
            if (robotCode.length() > 0) {
                //sendDocNum.length() - 1 的原因：从零开始的，含头不含尾。
                robotCode = robotCode.substring(0, robotCode.length() - 1);
            }else {
                robotCode = "无";
            }
            returnModel.setData("");
            returnModel.setMsg("导入成功"+robotCode+"编号错误");
            returnModel.setState(Constant.STATE_SUCCESS);
            return returnModel;
        }catch (Exception e){
            e.printStackTrace();
            returnModel.setData("");
            returnModel.setMsg("导入失败！（请联系管理员）");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }


}
