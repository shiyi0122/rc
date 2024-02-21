package com.hna.hka.archive.management.system.controller;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.hna.hka.archive.management.system.model.SysScenicSpotWarning;
import com.hna.hka.archive.management.system.model.WarningExcel;
import com.hna.hka.archive.management.system.service.SysScenicSpotWarningService;
import com.hna.hka.archive.management.system.util.*;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ProjectName: IDEA-CODE
 * @Package: com.hna.hka.archive.management.system.controller
 * @ClassName: WarningController
 * @Author: 郭凯
 * @Description: 警告管理控制层
 * @Date: 2020/7/25 13:05
 * @Version: 1.0
 */
@RequestMapping("/system/warning")
@Controller
public class WarningController extends PublicUtil {

    @Autowired
    private SysScenicSpotWarningService sysScenicSpotWarningService;

    @Autowired
    private HttpSession session;

    /**
     * @Author 郭凯
     * @Description 警告管理列表查询
     * @Date 13:10 2020/7/25
     * @Param [pageNum, pageSize, sysScenicSpotWarning]
     * @return com.hna.hka.archive.management.system.util.PageDataResult
    **/
    @RequestMapping("/getWarningList")
    @ResponseBody
    public PageDataResult getWarningList(@RequestParam("pageNum") Integer pageNum,
                                            @RequestParam("pageSize") Integer pageSize, SysScenicSpotWarning sysScenicSpotWarning) {
        PageDataResult pageDataResult = new PageDataResult();
        Map<String,String> search = new HashMap<>();
        try {
            if(null == pageNum) {
                pageNum = 1;
            }
            if(null == pageSize) {
                pageSize = 10;
            }
            search.put("scenicSpotId",session.getAttribute("scenicSpotId").toString());
            pageDataResult = sysScenicSpotWarningService.getWarningList(pageNum,pageSize,search);
        }catch (Exception e){
            logger.info("警告管理列表查询失败",e);
        }
        return pageDataResult;
    }

    /**
     * @Author 郭凯
     * @Description 警告信息新增
     * @Date 14:12 2020/7/25
     * @Param [file, sysScenicSpotWarning]
     * @return com.hna.hka.archive.management.system.util.ReturnModel
    **/
    @RequestMapping(value = "/addWarning")
    @ResponseBody
    public ReturnModel addWarning(@RequestParam("file") MultipartFile file, SysScenicSpotWarning sysScenicSpotWarning) {
        ReturnModel dataModel = new ReturnModel();
        try {
            if(!file.isEmpty()){
                int a = sysScenicSpotWarningService.addWarning(file,sysScenicSpotWarning);
                if (a == 1) {
                    dataModel.setData("");
                    dataModel.setMsg("警告信息新增成功！");
                    dataModel.setState(Constant.STATE_SUCCESS);
                    return dataModel;
                }
                if (a == 2) {
                    dataModel.setData("");
                    dataModel.setMsg("图片格式不正确！（只支持png，jpg文件）");
                    dataModel.setState(Constant.STATE_FAILURE);
                    return dataModel;
                }
                if (a == 0) {
                    dataModel.setData("");
                    dataModel.setMsg("警告信息新增失败！");
                    dataModel.setState(Constant.STATE_FAILURE);
                    return dataModel;
                }
            }else{
                dataModel.setData("");
                dataModel.setMsg("请选择要上传的图片！");
                dataModel.setState(Constant.STATE_FAILURE);
                return dataModel;
            }
        } catch (Exception e) {
            dataModel.setData("");
            dataModel.setMsg("警告信息新增失败！");
            dataModel.setState(Constant.STATE_FAILURE);
            logger.error("addWarning", e);
            return dataModel;
        }
        return dataModel;
    }

    /**
     * @Author 郭凯
     * @Description 删除警告
     * @Date 9:47 2020/7/26
     * @Param [warningId]
     * @return com.hna.hka.archive.management.system.util.ReturnModel
    **/
    @RequestMapping("/delWarning")
    @ResponseBody
    public ReturnModel delWarning(@NotBlank(message = "ID不能为空")Long warningId){
        ReturnModel dataModel = new ReturnModel();
        try {
            int i = sysScenicSpotWarningService.delWarning(warningId);
            if (i == 1){
                dataModel.setData("");
                dataModel.setMsg("警告信息删除成功！");
                dataModel.setState(Constant.STATE_SUCCESS);
                return dataModel;
            }else{
                dataModel.setData("");
                dataModel.setMsg("警告信息删除失败！");
                dataModel.setState(Constant.STATE_FAILURE);
                return dataModel;
            }
        }catch (Exception e){
            dataModel.setData("");
            dataModel.setMsg("警告信息删除失败！");
            dataModel.setState(Constant.STATE_FAILURE);
            return dataModel;
        }
    }

    /**
     * @Author 郭凯
     * @Description 警告信息修改
     * @Date 14:09 2020/7/26
     * @Param [file, sysScenicSpotWarning]
     * @return com.hna.hka.archive.management.system.util.ReturnModel
    **/
    @RequestMapping(value = "/editWarning")
    @ResponseBody
    public ReturnModel editWarning(@RequestParam("file") MultipartFile file, SysScenicSpotWarning sysScenicSpotWarning) {
        ReturnModel dataModel = new ReturnModel();
        try {
            int a = sysScenicSpotWarningService.editWarning(file,sysScenicSpotWarning);
            if (a == 1) {
                dataModel.setData("");
                dataModel.setMsg("警告信息修改成功！");
                dataModel.setState(Constant.STATE_SUCCESS);
                return dataModel;
            }
            if (a == 2) {
                dataModel.setData("");
                dataModel.setMsg("图片格式不正确！（只支持png，jpg文件）");
                dataModel.setState(Constant.STATE_FAILURE);
                return dataModel;
            }
            if (a == 0) {
                dataModel.setData("");
                dataModel.setMsg("警告信息修改失败！");
                dataModel.setState(Constant.STATE_FAILURE);
                return dataModel;
            }
        } catch (Exception e) {
            dataModel.setData("");
            dataModel.setMsg("警告信息修改失败！");
            dataModel.setState(Constant.STATE_FAILURE);
            logger.error("editWarning", e);
            return dataModel;
        }
        return dataModel;
    }

    @RequestMapping("/getWarningExcel")
    @ResponseBody
    private void getWarningExcel(HttpServletResponse response, WarningExcel warningExcel){
        Map<String,Object> search=new HashMap<>();
        search.put("scenicSpotId",(Long.parseLong(session.getAttribute("scenicSpotId").toString())));
        List<WarningExcel> warning = sysScenicSpotWarningService.getWarningExcel(search);
        String dateTime = DateFormatUtils.format(new Date(), "yyyyMMddHHmm");
        FileUtil.exportExcel(FileUtil.getWorkbook("警告点列表","警告点列表",warningExcel.getClass(),warning),"警告点列表"+dateTime+".xls",response);
    }

    @RequestMapping("/upWarningExcel")
    @ResponseBody
    public ReturnModel upSysResExcel(@RequestParam("file") MultipartFile multipartFile){
        ReturnModel returnModel=new ReturnModel();
        try {
            ImportParams params=new ImportParams();
            params.setTitleRows(1);
            params.setHeadRows(1);
            List<WarningExcel> result = ExcelImportUtil.importExcel(multipartFile.getInputStream(), WarningExcel.class, params);
            for (WarningExcel warn:result){
                warn.setScenicSpotId(Long.parseLong(session.getAttribute("scenicSpotId").toString()));
                WarningExcel warning = sysScenicSpotWarningService.seLWarning(warn);
                if (ToolUtil.isEmpty(warning)){
                    warn.setCreateDate(DateUtil.currentDateTime());
                    warn.setWarningId(IdUtils.getSeqId());
                    sysScenicSpotWarningService.addWarningExcel(warn);
                }else {
                    warn.setWarningId(warning.getWarningId());
                    warn.setCreateDate(warning.getCreateDate());
                    warn.setUpdateDate(DateUtil.currentDateTime());
                    sysScenicSpotWarningService.upWarningExcel(warn);
                }
            }
            returnModel.setData("");
            returnModel.setMsg("导入成功");
            returnModel.setState(Constant.STATE_SUCCESS);
            return returnModel;
        }catch (Exception e){
            returnModel.setData("");
            returnModel.setMsg("导入失败！（请联系管理员）");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

}