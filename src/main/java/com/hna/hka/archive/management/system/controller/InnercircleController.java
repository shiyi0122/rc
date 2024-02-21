package com.hna.hka.archive.management.system.controller;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.hna.hka.archive.management.system.model.InnercricleExcel;
import com.hna.hka.archive.management.system.model.SysScenicSpotInnercircleCoordinateGroupWithBLOBs;
import com.hna.hka.archive.management.system.service.SysScenicSpotInnercircleCoordinateGroupService;
import com.hna.hka.archive.management.system.util.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ProjectName: IDEA-CODE
 * @Package: com.hna.hka.archive.management.system.controller
 * @ClassName: InnercircleController
 * @Author: 郭凯
 * @Description: 内圈禁区管理控制层
 * @Date: 2020/6/8 14:52
 * @Version: 1.0
 */
@Api(tags = "禁区管理")
@RequestMapping("/system/innercircle")
@Controller
public class InnercircleController extends PublicUtil {

    @Autowired
    private HttpSession session;

    @Autowired
    private SysScenicSpotInnercircleCoordinateGroupService sysScenicSpotInnercircleCoordinateGroupService;

    /**
     * @Author 郭凯
     * @Description 内圈禁区列表查询
     * @Date 15:25 2020/6/8
     * @Param [pageNum, pageSize, sysScenicSpotInnercircleCoordinateGroupWithBLOBs]
     * @return com.hna.hka.archive.management.system.util.PageDataResult
    **/
    @ApiOperation("禁区管理")
    @RequestMapping("/getInnercircleList")
    @ResponseBody
    public PageDataResult getInnercircleList(@RequestParam("pageNum") Integer pageNum,
                                                         @RequestParam("pageSize") Integer pageSize, SysScenicSpotInnercircleCoordinateGroupWithBLOBs sysScenicSpotInnercircleCoordinateGroupWithBLOBs) {
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
            search.put("coordinateInnercircleName",sysScenicSpotInnercircleCoordinateGroupWithBLOBs.getCoordinateInnercircleName());
            pageDataResult = sysScenicSpotInnercircleCoordinateGroupService.getInnercircleList(pageNum,pageSize,search);
        }catch (Exception e){
            logger.info("内圈禁区列表查询失败",e);
        }
        return pageDataResult;
    }

    /**
     * @Author 郭凯
     * @Description 添加内圈禁区
     * @Date 16:42 2020/6/8
     * @Param [sysScenicSpotInnercircleCoordinateGroupWithBLOBs]
     * @return com.hna.hka.archive.management.system.util.ReturnModel
    **/
    @RequestMapping("addInnercircle")
    @ResponseBody
    public ReturnModel addInnercircle(SysScenicSpotInnercircleCoordinateGroupWithBLOBs sysScenicSpotInnercircleCoordinateGroupWithBLOBs) {
        ReturnModel returnModel = new ReturnModel();
        try {
            int i = sysScenicSpotInnercircleCoordinateGroupService.addInnercircle(sysScenicSpotInnercircleCoordinateGroupWithBLOBs);
            if (i == 1) {
                returnModel.setData("");
                returnModel.setMsg("内圈禁区新增成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            }else {
                returnModel.setData("");
                returnModel.setMsg("内圈禁区新增失败！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        } catch (Exception e) {
            logger.error("addInnercircle", e);
            returnModel.setData("");
            returnModel.setMsg("内圈禁区新增失败！");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    /**
     * @Author 郭凯
     * @Description 删除内圈禁区
     * @Date 17:15 2020/6/8
     * @Param [coordinateInnercircleId]
     * @return com.hna.hka.archive.management.system.util.ReturnModel
    **/
    @RequestMapping("/delInnercircle")
    @ResponseBody
    public ReturnModel delInnercircle(@RequestParam("coordinateInnercircleId") Long coordinateInnercircleId){
        ReturnModel returnModel = new ReturnModel();
        try {
            int i = sysScenicSpotInnercircleCoordinateGroupService.delInnercircle(coordinateInnercircleId);
            if (i == 1){
                returnModel.setData("");
                returnModel.setMsg("内圈禁区删除成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            }else{
                returnModel.setData("");
                returnModel.setMsg("内圈禁区删除失败！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        }catch (Exception e){
            logger.info("delInnercircle",e);
            returnModel.setData("");
            returnModel.setMsg("内圈禁区删除失败！(请联系管理员)");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    /**
     * @Author 郭凯
     * @Description 编辑内圈禁区
     * @Date 17:25 2020/6/8
     * @Param [sysScenicSpotInnercircleCoordinateGroupWithBLOBs]
     * @return com.hna.hka.archive.management.system.util.ReturnModel
     **/
    @RequestMapping("editInnercircle")
    @ResponseBody
    public ReturnModel editInnercircle(SysScenicSpotInnercircleCoordinateGroupWithBLOBs sysScenicSpotInnercircleCoordinateGroupWithBLOBs) {
        ReturnModel returnModel = new ReturnModel();
        try {
            int i = sysScenicSpotInnercircleCoordinateGroupService.editInnercircle(sysScenicSpotInnercircleCoordinateGroupWithBLOBs);
            if (i == 1) {
                returnModel.setData("");
                returnModel.setMsg("内圈禁区编辑成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            }else {
                returnModel.setData("");
                returnModel.setMsg("内圈禁区编辑失败！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        } catch (Exception e) {
            logger.error("editInnercircle", e);
            returnModel.setData("");
            returnModel.setMsg("内圈禁区编辑失败！");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    /**
     * @Author 郭凯
     * @Description 内圈禁区状态修改
     * @Date 17:37 2020/6/8
     * @Param [sysScenicSpotInnercircleCoordinateGroupWithBLOBs]
     * @Param [sysScenicSpotInnercircleCoordinateGroupWithBLOBs]
     * @return com.hna.hka.archive.management.system.util.ReturnModel
    **/
    @RequestMapping(value = "/editValid")
    @ResponseBody
    public ReturnModel editValid(SysScenicSpotInnercircleCoordinateGroupWithBLOBs sysScenicSpotInnercircleCoordinateGroupWithBLOBs) {
        ReturnModel dataModel = new ReturnModel();
        try {
            int a = sysScenicSpotInnercircleCoordinateGroupService.editInnercircle(sysScenicSpotInnercircleCoordinateGroupWithBLOBs);
            if (a == 1) {
                dataModel.setData("");
                dataModel.setMsg("内圈禁区状态修改成功！");
                dataModel.setState(Constant.STATE_SUCCESS);
                return dataModel;
            }else{
                dataModel.setData("");
                dataModel.setMsg("内圈禁区状态修改失败！");
                dataModel.setState(Constant.STATE_FAILURE);
                return dataModel;
            }
        } catch (Exception e) {
            dataModel.setData("");
            dataModel.setMsg("内圈禁区状态修改失败！");
            dataModel.setState(Constant.STATE_FAILURE);
            logger.error("editValid", e);
            return dataModel;
        }
    }


    /*
    *禁区列表导出
    * */
    @RequestMapping("/getInnercricleExcel")
    @ResponseBody
    public void getInnercricleExcel(HttpServletResponse Response, InnercricleExcel innercricleExcel){
        Map<String,Object> search = new HashMap<>();
        search.put("scenicSpotId",session.getAttribute("scenicSpotId"));
        List<InnercricleExcel> Inn=sysScenicSpotInnercircleCoordinateGroupService.getInnercricleExcel(search);
        String dateTime = DateFormatUtils.format(new Date(), "yyyyMMddHHmm");
        FileUtil.exportExcel(FileUtil.getWorkbook("禁区列表","禁区列表",innercricleExcel.getClass(),Inn),"禁区列表"+ dateTime +".xls",Response);
    }


    /*
    * 禁区列表导入
    * */
    @RequestMapping("/upInnercricleExcel")
    @ResponseBody
    public ReturnModel upInnercricleExcel(@RequestParam("file") MultipartFile multipartFile){
        ReturnModel returnModel=new ReturnModel();
        try {
            ImportParams params=new ImportParams();
            params.setTitleRows(1);
            params.setHeadRows(1);
            List<InnercricleExcel> result = ExcelImportUtil.importExcel(multipartFile.getInputStream(), InnercricleExcel.class, params);
            for (InnercricleExcel inn:result){
                inn.setCoordinateId(Long.parseLong(session.getAttribute("scenicSpotId").toString()));
                InnercricleExcel innercricleExcel = sysScenicSpotInnercircleCoordinateGroupService.getcoordinateInnercircleIdByName(inn);
                if (ToolUtil.isEmpty(innercricleExcel)){
                    inn.setCreateDate(DateUtil.currentDateTime());
                    sysScenicSpotInnercircleCoordinateGroupService.addInnercricleExcel(inn);
                }else {
                    inn.setCoordinateInnercircleId(innercricleExcel.getCoordinateInnercircleId());
                    sysScenicSpotInnercircleCoordinateGroupService.upInnercricleExcel(inn);
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