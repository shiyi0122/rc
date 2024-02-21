package com.hna.hka.archive.management.system.controller;

import com.hna.hka.archive.management.system.model.SysRobotCorpusWithBLOBs;
import com.hna.hka.archive.management.system.model.SysRobotProblemExtend;
import com.hna.hka.archive.management.system.service.SysRobotCorpusService;
import com.hna.hka.archive.management.system.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * @ProjectName: IDEA-CODE
 * @Package: com.hna.hka.archive.management.system.controller
 * @ClassName: SemanticsController
 * @Author: 郭凯
 * @Description: 语义交互管理控制层
 * @Date: 2020/6/3 17:33
 * @Version: 1.0
 */
@RequestMapping("/system/semantics")
@Controller
public class SemanticsController extends PublicUtil {

    @Autowired
    private HttpSession session;

    @Autowired
    private SysRobotCorpusService sysRobotCorpusService;

    /**
     * @Author 郭凯
     * @Description 语义交互管理列表查询
     * @Date 10:48 2020/6/4
     * @Param [pageNum, pageSize, sysRobotCorpusWithBLOBs]
     * @return com.hna.hka.archive.management.system.util.PageDataResult
    **/
    @RequestMapping("/getSemanticsList")
    @ResponseBody
    public PageDataResult getSemanticsList(@RequestParam("pageNum") Integer pageNum,
                                      @RequestParam("pageSize") Integer pageSize, SysRobotCorpusWithBLOBs sysRobotCorpusWithBLOBs) {
        PageDataResult pageDataResult = new PageDataResult();
        Map<String,String> search = new HashMap<>();
        try {
            if(null == pageNum) {
                pageNum = 1;
            }
            if(null == pageSize) {
                pageSize = 10;
            }
            //获取当前景区
            search.put("scenicSpotId",session.getAttribute("scenicSpotId").toString());
            if (!ToolUtil.isEmpty(sysRobotCorpusWithBLOBs.getCorpusProblem())){
                search.put("corpusProblem",sysRobotCorpusWithBLOBs.getCorpusProblem());
            }
            pageDataResult = sysRobotCorpusService.getSemanticsList(pageNum,pageSize,search);
        }catch (Exception e){
            logger.info("语义交互管理列表查询失败",e);
        }
        return pageDataResult;
    }

    /**
     * @Author 郭凯
     * @Description 删除语义交互
     * @Date 14:11 2020/6/4
     * @Param [corpusId]
     * @return com.hna.hka.archive.management.system.util.ReturnModel
     **/
    @RequestMapping("/delSemantics")
    @ResponseBody
    public ReturnModel delSemantics(@RequestParam("corpusId") Long corpusId){
        ReturnModel returnModel = new ReturnModel();
        try {
            int i = sysRobotCorpusService.delSemantics(corpusId);
            if (i == 1){
                returnModel.setData("");
                returnModel.setMsg("语义交互删除成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            }else{
                returnModel.setData("");
                returnModel.setMsg("语义交互删除失败！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        }catch (Exception e){
            logger.info("delSemantics",e);
            returnModel.setData("");
            returnModel.setMsg("语义交互删除失败！(请联系管理员)");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    /**
     * @Author 郭凯
     * @Description 新增语义交互
     * @Date 16:40 2020/7/31
     * @Param [sysRobotCorpusWithBLOBs]
     * @return com.hna.hka.archive.management.system.util.ReturnModel
     **/
    @RequestMapping("/addSemantics")
    @ResponseBody
    public ReturnModel addSemantics(SysRobotCorpusWithBLOBs sysRobotCorpusWithBLOBs) {
        ReturnModel returnModel = new ReturnModel();
        String scenicSpotId = session.getAttribute("scenicSpotId").toString();
        try {
            if ("1".equals(sysRobotCorpusWithBLOBs.getGenericType())) {
                sysRobotCorpusWithBLOBs.setGenericType(scenicSpotId);
            }else if ("2".equals(sysRobotCorpusWithBLOBs.getGenericType())) {
                sysRobotCorpusWithBLOBs.setGenericType("2019924");
            }
            int i = sysRobotCorpusService.addSemantics(sysRobotCorpusWithBLOBs);
            if (i == 1) {
                returnModel.setData("");
                returnModel.setMsg("语义交互新增成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            }else {
                returnModel.setData("");
                returnModel.setMsg("语义交互新增失败！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        } catch (Exception e) {
            logger.error("addSemantics", e);
            returnModel.setData("");
            returnModel.setMsg("语义交互新增失败！");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }
    /**
     * @Author 郭凯
     * @Description 编辑语义交互
     * @Date 10:31 2020/8/1
     * @Param [sysRobotCorpusWithBLOBs]
     * @return com.hna.hka.archive.management.system.util.ReturnModel
    **/
    @RequestMapping("/editSemantics")
    @ResponseBody
    public ReturnModel editSemantics(SysRobotCorpusWithBLOBs sysRobotCorpusWithBLOBs) {
        ReturnModel returnModel = new ReturnModel();
        String scenicSpotId = session.getAttribute("scenicSpotId").toString();
        try {
            if ("1".equals(sysRobotCorpusWithBLOBs.getGenericType())) {
                sysRobotCorpusWithBLOBs.setGenericType(scenicSpotId);
            }else if ("2".equals(sysRobotCorpusWithBLOBs.getGenericType())) {
                sysRobotCorpusWithBLOBs.setGenericType("2019924");
            }
            int i = sysRobotCorpusService.editSemantics(sysRobotCorpusWithBLOBs);
            if (i == 1) {
                returnModel.setData("");
                returnModel.setMsg("语义交互修改成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            }else {
                returnModel.setData("");
                returnModel.setMsg("语义交互修改失败！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        } catch (Exception e) {
            logger.error("editSemantics", e);
            returnModel.setData("");
            returnModel.setMsg("语义交互修改失败！");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    /**
     * @Author 郭凯
     * @Description 语义交互管理详情列表查询
     * @Date 14:58 2020/11/5
     * @Param [pageNum, pageSize, sysRobotProblemExtend]
     * @return com.hna.hka.archive.management.system.util.PageDataResult
    **/
    @RequestMapping("/getSemanticsDetailsList")
    @ResponseBody
    public PageDataResult getSemanticsDetailsList(@RequestParam("pageNum") Integer pageNum,
                                           @RequestParam("pageSize") Integer pageSize, SysRobotProblemExtend sysRobotProblemExtend) {
        PageDataResult pageDataResult = new PageDataResult();
        Map<String,Object> search = new HashMap<>();
        try {
            if(null == pageNum) {
                pageNum = 1;
            }
            if(null == pageSize) {
                pageSize = 10;
            }
            search.put("corpusId",sysRobotProblemExtend.getCorpusId());
            pageDataResult = sysRobotCorpusService.getSemanticsDetailsList(pageNum,pageSize,search);
        }catch (Exception e){
            logger.info("语义交互管理详情列表查询失败",e);
        }
        return pageDataResult;
    }

    /**
     * @Author 郭凯
     * @Description 资源详情新增
     * @Date 15:28 2020/11/5
     * @Param [sysRobotProblemExtend]
     * @return com.hna.hka.archive.management.system.util.ReturnModel
    **/
    @RequestMapping("/addSemanticsDetails")
    @ResponseBody
    public ReturnModel addSemanticsDetails(SysRobotProblemExtend sysRobotProblemExtend) {
        ReturnModel returnModel = new ReturnModel();
        try {
            int i = sysRobotCorpusService.addSemanticsDetails(sysRobotProblemExtend);
            if (i == 1) {
                returnModel.setData("");
                returnModel.setMsg("资源详情新增成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            }else {
                returnModel.setData("");
                returnModel.setMsg("资源详情新增失败！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        } catch (Exception e) {
            logger.error("addSemanticsDetails", e);
            returnModel.setData("");
            returnModel.setMsg("资源详情新增失败！");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    /**
     * @Author 郭凯
     * @Description 扩展问题删除
     * @Date 15:47 2020/11/5
     * @Param [extendId]
     * @return com.hna.hka.archive.management.system.util.ReturnModel
    **/
    @RequestMapping("/delSemanticsDetails")
    @ResponseBody
    public ReturnModel delSemanticsDetails(@RequestParam("extendId") Long extendId){
        ReturnModel returnModel = new ReturnModel();
        try {
            int i = sysRobotCorpusService.delSemanticsDetails(extendId);
            if (i == 1){
                returnModel.setData("");
                returnModel.setMsg("扩展问题删除成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            }else{
                returnModel.setData("");
                returnModel.setMsg("扩展问题删除失败！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        }catch (Exception e){
            logger.info("delSemanticsDetails",e);
            returnModel.setData("");
            returnModel.setMsg("扩展问题删除失败！(请联系管理员)");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    /**
     * @Author 郭凯
     * @Description 资源详情修改
     * @Date 16:04 2020/11/5
     * @Param [sysRobotProblemExtend]
     * @return com.hna.hka.archive.management.system.util.ReturnModel
    **/
    @RequestMapping("/editSemanticsDetails")
    @ResponseBody
    public ReturnModel editSemanticsDetails(SysRobotProblemExtend sysRobotProblemExtend) {
        ReturnModel returnModel = new ReturnModel();
        try {
            int i = sysRobotCorpusService.editSemanticsDetails(sysRobotProblemExtend);
            if (i == 1) {
                returnModel.setData("");
                returnModel.setMsg("资源详情修改成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            }else {
                returnModel.setData("");
                returnModel.setMsg("资源详情修改失败！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        } catch (Exception e) {
            logger.error("editSemanticsDetails", e);
            returnModel.setData("");
            returnModel.setMsg("资源详情修改失败！");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

}
