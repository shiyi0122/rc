package com.hna.hka.archive.management.system.controller;

import com.alibaba.excel.util.StringUtils;
import com.hna.hka.archive.management.system.model.SysRobotUnusualTime;
import com.hna.hka.archive.management.system.model.SysScenicSpotTimingProblemWithBLOBs;
import com.hna.hka.archive.management.system.service.SysRobotUnusualTimeService;
import com.hna.hka.archive.management.system.util.Constant;
import com.hna.hka.archive.management.system.util.PageDataResult;
import com.hna.hka.archive.management.system.util.ReturnModel;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import tk.mybatis.mapper.util.StringUtil;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2023/3/2 15:04
 * 机器人异常状态时间配置
 */
@Api(tags = "机器人异常状态时间配置")
@RequestMapping("/system/sysRobotUnusualTime")
@Controller
public class SysRobotUnusualTimeController {

    @Autowired
    SysRobotUnusualTimeService sysRobotUnusualTimeService;

    @Autowired
    private HttpSession session;
    /**
     * @Author 郭凯
     * @Description 机器人异常状态时间配置列表
     * @Date 10:25 2020/6/20
     * @Param [pageNum, pageSize, sysScenicSpotRandomProblemWithBLOBs]
     * @return com.hna.hka.archive.management.system.util.PageDataResult
     **/
    @RequestMapping("/getSysRobotUnusualTimeList")
    @ResponseBody
    public PageDataResult getSysRobotUnusualTimeList(@RequestParam("pageNum") Integer pageNum,
                                               @RequestParam("pageSize") Integer pageSize,SysRobotUnusualTime sysRobotUnusualTime ) {
        PageDataResult pageDataResult = new PageDataResult();
        Map<String,String> search = new HashMap<>();
        try {
            if(null == pageNum) {
                pageNum = 1;
            }
            if(null == pageSize) {
                pageSize = 10;
            }

            if  (!StringUtils.isEmpty(sysRobotUnusualTime.getSysScenicSpotId())){

                search.put("scenicSpotId",sysRobotUnusualTime.getSysScenicSpotId().toString());
            }
//            search.put("timingProblem",sysScenicSpotTimingProblemWithBLOBs.getTimingProblem());
//            search.put("scenicSpotId",session.getAttribute("scenicSpotId").toString());
            pageDataResult = sysRobotUnusualTimeService.getSysRobotUnusualTimeList(pageNum,pageSize,search);
        }catch (Exception e){
            e.printStackTrace();
//            logger.info("随机播报管理列表查询失败",e);
        }
        return pageDataResult;
    }

    /**
     * @Author 郭凯
     * @Description 添加机器人异常状态时间配置
     * @Date 9:19 2020/6/22
     * @Param [sysScenicSpotRandomProblemWithBLOBs]
     * @return com.hna.hka.archive.management.system.util.ReturnModel
     **/
    @RequestMapping("/addSysRobotUnusualTime")
    @ResponseBody
    public ReturnModel addSysRobotUnusualTime(SysRobotUnusualTime sysRobotUnusualTime){
        ReturnModel returnModel = new ReturnModel();
        try {
            int i = sysRobotUnusualTimeService.addSysRobotUnusualTime(sysRobotUnusualTime);
            if (i == 1){
                returnModel.setData("");
                returnModel.setMsg("添加成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            }else if ( i == 2){
                returnModel.setData("");
                returnModel.setMsg("同一个景区无法重复添加！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }else{
                returnModel.setData("");
                returnModel.setMsg("添加失败！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        }catch (Exception e){
//            logger.info("editRandomProblemValid",e);
            e.printStackTrace();
            returnModel.setData("");
            returnModel.setMsg("添加失败！(请联系管理员)");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }


    /**
     * @Author 郭凯
     * @Description 修改机器人异常状态时间配置
     * @Date 9:19 2020/6/22
     * @Param [sysScenicSpotRandomProblemWithBLOBs]
     * @return com.hna.hka.archive.management.system.util.ReturnModel
     **/
    @RequestMapping("/editSysRobotUnusualTime")
    @ResponseBody
    public ReturnModel editSysRobotUnusualTime(SysRobotUnusualTime sysRobotUnusualTime){
        ReturnModel returnModel = new ReturnModel();
        try {
            int i = sysRobotUnusualTimeService.editSysRobotUnusualTime(sysRobotUnusualTime);
            if (i == 1){
                returnModel.setData("");
                returnModel.setMsg("修改成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            }else{
                returnModel.setData("");
                returnModel.setMsg("修改失败！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        }catch (Exception e){
//            logger.info("editRandomProblemValid",e);
            e.printStackTrace();
            returnModel.setData("");
            returnModel.setMsg("随机播报状态修改失败！(请联系管理员)");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    /**
     * @Author 郭凯
     * @Description 修改机器人异常状态时间配置
     * @Date 9:19 2020/6/22
     * @Param [sysScenicSpotRandomProblemWithBLOBs]
     * @return com.hna.hka.archive.management.system.util.ReturnModel
     **/
    @RequestMapping("/delSysRobotUnusualTime")
    @ResponseBody
    public ReturnModel delSysRobotUnusualTime(SysRobotUnusualTime sysRobotUnusualTime){
        ReturnModel returnModel = new ReturnModel();
        try {
            int i = sysRobotUnusualTimeService.delSysRobotUnusualTime(sysRobotUnusualTime);
            if (i == 1){
                returnModel.setData("");
                returnModel.setMsg("删除成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            }else{
                returnModel.setData("");
                returnModel.setMsg("删除失败！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        }catch (Exception e){
//            logger.info("editRandomProblemValid",e);
            e.printStackTrace();
            returnModel.setData("");
            returnModel.setMsg("删除机器人异常配置失败！(请联系管理员)");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }




}
