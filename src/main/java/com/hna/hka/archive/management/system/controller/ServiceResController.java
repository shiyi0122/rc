package com.hna.hka.archive.management.system.controller;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.hna.hka.archive.management.system.model.InnercricleExcel;
import com.hna.hka.archive.management.system.model.SysResExcel;
import com.hna.hka.archive.management.system.model.SysScenicSpotCustomType;
import com.hna.hka.archive.management.system.model.SysScenicSpotServiceRes;
import com.hna.hka.archive.management.system.service.SysScenicSpotServiceResService;
import com.hna.hka.archive.management.system.util.*;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.jetbrains.annotations.NotNull;
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
 * @ClassName: ServiceResController
 * @Author: 郭凯
 * @Description: 服务项控制层
 * @Date: 2020/7/25 10:19
 * @Version: 1.0
 */
@RequestMapping("/system/serviceRes")
@Controller
public class ServiceResController extends PublicUtil {

    @Autowired
    private SysScenicSpotServiceResService sysScenicSpotServiceResService;

    @Autowired
    private HttpSession session;

    /**
     * @Author 郭凯
     * @Description 服务项管理列表查询
     * @Date 10:24 2020/7/25
     * @Param [pageNum, pageSize, sysScenicSpotServiceRes]
     * @return com.hna.hka.archive.management.system.util.PageDataResult
    **/
    @RequestMapping("/getServiceResList")
    @ResponseBody
    public PageDataResult getServiceResList(@RequestParam("pageNum") Integer pageNum,
                                           @RequestParam("pageSize") Integer pageSize, SysScenicSpotServiceRes sysScenicSpotServiceRes) {
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
            pageDataResult = sysScenicSpotServiceResService.getServiceResList(pageNum,pageSize,search);
        }catch (Exception e){
            logger.info("服务项管理列表查询失败",e);
        }
        return pageDataResult;
    }

    /**
     * @Author 郭凯
     * @Description 服务项删除
     * @Date 11:03 2020/7/25
     * @Param [serviceId]
     * @return com.hna.hka.archive.management.system.util.ReturnModel
    **/
    @RequestMapping("/delServiceRes")
    @ResponseBody
    public ReturnModel delServiceRes(@NotBlank(message = "ID不能为空")Long serviceId){
        ReturnModel returnModel = new ReturnModel();
        try {
            int i = sysScenicSpotServiceResService.delServiceRes(serviceId);
            if (i == 1){
                returnModel.setData("");
                returnModel.setMsg("服务项删除成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            }else{
                returnModel.setData("");
                returnModel.setMsg("服务项删除失败！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        }catch (Exception e){
            returnModel.setData("");
            returnModel.setMsg("服务项删除失败，请联系管理员！");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    /**
     * @Author 郭凯
     * @Description 新增服务项
     * @Date 16:53 2020/8/1
     * @Param [file, sysScenicSpotServiceRes]
     * @return com.hna.hka.archive.management.system.util.ReturnModel
    **/
    @RequestMapping(value = "/addServiceRes")
    @ResponseBody
    public ReturnModel addServiceRes(@RequestParam MultipartFile file, SysScenicSpotServiceRes sysScenicSpotServiceRes){
        ReturnModel returnModel = new ReturnModel();
        try {
            sysScenicSpotServiceRes.setScenicSpotId(Long.parseLong(session.getAttribute("scenicSpotId").toString()));
            int i = sysScenicSpotServiceResService.addServiceRes(file,sysScenicSpotServiceRes);
            if (i == 1){
                returnModel.setData("");
                returnModel.setMsg("服务项新增成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            }else if(i == 2){
                returnModel.setData("");
                returnModel.setMsg("图片文件上传格式不正确！(支持:png，jpg格式)");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }else{
                returnModel.setData("");
                returnModel.setMsg("服务项新增失败！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        }catch (Exception e){
            returnModel.setData("");
            returnModel.setMsg("服务项新增失败，请联系管理员！");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    /**
     * @Author 郭凯
     * @Description 查询自定义类型
     * @Date 10:01 2020/11/2
     * @Param []
     * @return com.hna.hka.archive.management.system.util.ReturnModel
    **/
    @RequestMapping(value = "/selectCustomType")
    @ResponseBody
    public ReturnModel selectCustomType(){
        ReturnModel returnModel = new ReturnModel();
        try {
            List<SysScenicSpotCustomType> sysScenicSpotCustomType = sysScenicSpotServiceResService.selectCustomType();
            if (ToolUtil.isNotEmpty(sysScenicSpotCustomType)){
                returnModel.setData(sysScenicSpotCustomType);
                returnModel.setMsg("查询成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            }else{
                returnModel.setData("");
                returnModel.setMsg("查询失败！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        }catch (Exception e){
            returnModel.setData("");
            returnModel.setMsg("查询失败，请联系管理员！");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    /**
     * @Author 郭凯
     * @Description 修改服务项
     * @Date 10:28 2020/11/2
     * @Param [file, sysScenicSpotServiceRes]
     * @return com.hna.hka.archive.management.system.util.ReturnModel
    **/
    @RequestMapping(value = "/editServiceRes")
    @ResponseBody
    public ReturnModel editServiceRes(@RequestParam MultipartFile file, SysScenicSpotServiceRes sysScenicSpotServiceRes){
        ReturnModel returnModel = new ReturnModel();
        try {
            sysScenicSpotServiceRes.setScenicSpotId(Long.parseLong(session.getAttribute("scenicSpotId").toString()));
            int i = sysScenicSpotServiceResService.editServiceRes(file,sysScenicSpotServiceRes);
            if (i == 1){
                returnModel.setData("");
                returnModel.setMsg("服务项修改成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            }else if(i == 2){
                returnModel.setData("");
                returnModel.setMsg("图片文件上传格式不正确！(支持:png，jpg格式)");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }else{
                returnModel.setData("");
                returnModel.setMsg("服务项修改失败！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        }catch (Exception e){
            returnModel.setData("");
            returnModel.setMsg("服务项修改失败，请联系管理员！");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    //服务项导出
    @RequestMapping("/getSysResExcel")
    @ResponseBody
    private void getSysResExcel(HttpServletResponse response, SysResExcel resExcel){
        Map<String,Object> search=new  HashMap<>();
        search.put("scenicSpotId",(Long.parseLong(session.getAttribute("scenicSpotId").toString())));
        List<SysResExcel> sysResExcel = sysScenicSpotServiceResService.getSysResExcel(search);
        String dateTime = DateFormatUtils.format(new Date(), "yyyyMMddHHmm");
        FileUtil.exportExcel(FileUtil.getWorkbook("服务项列表","服务项列表",resExcel.getClass(),sysResExcel),"服务项列表"+dateTime+".xls",response);

    }


    @RequestMapping("/upSysResExcel")
    @ResponseBody
    public ReturnModel upSysResExcel(@RequestParam("file") MultipartFile multipartFile){
        ReturnModel returnModel=new ReturnModel();
        try {
            ImportParams params=new ImportParams();
            params.setTitleRows(1);
            params.setHeadRows(1);
            List<SysResExcel> result = ExcelImportUtil.importExcel(multipartFile.getInputStream(), SysResExcel.class, params);
            for (SysResExcel res:result){
                res.setScenicSpotId(Long.parseLong(session.getAttribute("scenicSpotId").toString()));
                SysResExcel sysResExcel = sysScenicSpotServiceResService.selSysRes(res);
                if (ToolUtil.isEmpty(sysResExcel)){
                    res.setCreateDate(DateUtil.currentDateTime());
                    res.setServiceId(IdUtils.getSeqId());
                    sysScenicSpotServiceResService.addSysRes(res);
                }else {
                    res.setServiceId(sysResExcel.getServiceId());
                    res.setUpdateDate(DateUtil.currentDateTime());
                    sysScenicSpotServiceResService.upSysRes(res);
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
