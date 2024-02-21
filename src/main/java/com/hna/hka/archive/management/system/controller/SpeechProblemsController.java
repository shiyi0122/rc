package com.hna.hka.archive.management.system.controller;

import com.hna.hka.archive.management.system.model.SysRobotSpeechProblems;
import com.hna.hka.archive.management.system.service.SysRobotSpeechProblemsService;
import com.hna.hka.archive.management.system.util.*;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ProjectName: IDEA-CODE
 * @Package: com.hna.hka.archive.management.system.controller
 * @ClassName: SpeechProblemsController
 * @Author: 郭凯
 * @Description: 问题汇总控制层
 * @Date: 2020/7/24 16:25
 * @Version: 1.0
 */
@RequestMapping("/system/speechProblems")
@Controller
public class SpeechProblemsController extends PublicUtil {

    @Autowired
    private SysRobotSpeechProblemsService sysRobotSpeechProblemsService;

    @Autowired
    private HttpSession session;

    /**
     * @Author 郭凯
     * @Description 问题汇总列表查询
     * @Date 16:31 2020/7/24
     * @Param [pageNum, pageSize, sysRobotSpeechProblems]
     * @return com.hna.hka.archive.management.system.util.PageDataResult
    **/
    @RequestMapping("/getSpeechProblemsList")
    @ResponseBody
    public PageDataResult getSpeechProblemsList(@RequestParam("pageNum") Integer pageNum,
                                           @RequestParam("pageSize") Integer pageSize, SysRobotSpeechProblems sysRobotSpeechProblems) {
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
            pageDataResult = sysRobotSpeechProblemsService.getSpeechProblemsList(pageNum,pageSize,search);
        }catch (Exception e){
            logger.info("问题汇总列表查询失败",e);
        }
        return pageDataResult;
    }

    /**
     * @Author 郭凯
     * @Description 问题汇总Excel下载
     * @Date 17:33 2020/7/24
     * @Param [response, request, sysRobotSpeechProblems]
     * @return void
    **/
    @RequestMapping(value = "/uploadExcelSpeechProblems")
    public void  uploadExcelOrder(HttpServletResponse response, HttpServletRequest request, @ModelAttribute SysRobotSpeechProblems sysRobotSpeechProblems) throws Exception {
        List<SysRobotSpeechProblems> speechProblemsListByExample = null;
        Map<String,String> search = new HashMap<>();
        search.put("scenicSpotId",session.getAttribute("scenicSpotId").toString());
        speechProblemsListByExample = sysRobotSpeechProblemsService.getSpeechProblemsExcel(search);
        for(SysRobotSpeechProblems problems : speechProblemsListByExample){
        	problems.setHandleStateName(DictUtils.getHandleStateMap().get(problems.getHandleState()));
        	problems.setProblemStatusName(DictUtils.getProblemStatusMap().get(problems.getProblemStatus()));
        }
        String dateTime = DateFormatUtils.format(new Date(), "yyyyMMddHHmm");
        FileUtil.exportExcel(FileUtil.getWorkbook("问题汇总", "问题汇总", SysRobotSpeechProblems.class, speechProblemsListByExample),"问题汇总"+ dateTime +".xls",response);
    }

    /**
     * @Author 郭凯
     * @Description 修改状态为已处理
     * @Date 9:49 2020/7/25
     * @Param [sysRobotSpeechProblems]
     * @return com.hna.hka.archive.management.system.util.ReturnModel
    **/
    @RequestMapping(value = "/editSpeechProblemsHandleState")
    @ResponseBody
    public ReturnModel editSpeechProblemsHandleState(SysRobotSpeechProblems sysRobotSpeechProblems) {
        ReturnModel dataModel = new ReturnModel();
        try {
            int i = sysRobotSpeechProblemsService.editSpeechProblemsHandleState(sysRobotSpeechProblems);
            if (i == 1){
                dataModel.setData("");
                dataModel.setMsg("处理状态修改成功！");
                dataModel.setState(Constant.STATE_SUCCESS);
                return dataModel;
            }else{
                dataModel.setData("");
                dataModel.setMsg("处理状态修改失败！");
                dataModel.setState(Constant.STATE_FAILURE);
                return dataModel;
            }
        } catch (Exception e) {
            logger.error("editSpeechProblemsHandleState", e);
            dataModel.setData("");
            dataModel.setMsg("处理状态修改失败！(请联系管理员)");
            dataModel.setState(Constant.STATE_FAILURE);
            return dataModel;
        }
    }

}
