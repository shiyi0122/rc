package com.hna.hka.archive.management.assetsSystem.controller;

import com.hna.hka.archive.management.assetsSystem.model.SysAppFlowPath;
import com.hna.hka.archive.management.assetsSystem.model.SysAppFlowPathDetails;
import com.hna.hka.archive.management.assetsSystem.service.SysAppFlowPathService;
import com.hna.hka.archive.management.managerApp.model.SysAppUsers;
import com.hna.hka.archive.management.managerApp.service.SysAppUsersService;
import com.hna.hka.archive.management.system.model.SysRobot;
import com.hna.hka.archive.management.system.model.SysRobotId;
import com.hna.hka.archive.management.system.model.SysScenicSpot;
import com.hna.hka.archive.management.system.model.SysUsers;
import com.hna.hka.archive.management.system.util.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2023/6/27 16:12
 */

@Slf4j
@Api(tags = "售后流程相关接口")
@RestController
@RequestMapping("/system/sys_app_flow_path")
public class SysAppFlowPathController extends PublicUtil {

    @Autowired
    SysAppFlowPathService sysAppFlowPathService;

    @Autowired
    SysAppUsersService sysAppUsersService;


    /**
     * @return com.hna.hka.archive.management.system.util.PageDataResult
     * @Author 郭凯
     * @Description 查询审批流程列表
     * @Date 9:24 2020/5/20
     * @Param [pageNum, pageSize, sysRobot]
     **/
    @ApiOperation("审批列表查询")
    @RequestMapping("/getSysAppFlowPathList")
    @ResponseBody
    public PageDataResult getSysAppFlowPathList(@RequestParam("pageNum") Integer pageNum,
                                                @RequestParam("pageSize") Integer pageSize, SysAppFlowPath sysAppFlowPath) {
        PageDataResult pageDataResult = new PageDataResult();
        SysUsers SysUsers = this.getSysUser();
        Map<String, Object> search = new HashMap<>();
        try {
            if (null == pageNum) {
                pageNum = 1;
            }
            if (null == pageSize) {
                pageSize = 10;
            }
            search.put("flowPathName", sysAppFlowPath.getFlowPathName());
            search.put("scenicSpotId", sysAppFlowPath.getScenicSpotIds());
            pageDataResult = sysAppFlowPathService.getSysAppFlowPathList(pageNum, pageSize, search);

        } catch (Exception e) {
            logger.info("审批列表查询成功", e);
        }
        return pageDataResult;
    }


    /**
     * @return com.hna.hka.archive.management.system.util.ReturnModel
     * @Author
     * @Description 添加审批流程
     * @Date 16:58 2020/5/20
     * @Param [sysRobot]
     **/
    @ApiOperation("添加审批流程")
    @RequestMapping("/addSysAppFlowPath")
    @ResponseBody
    public ReturnModel addSysAppFlowPath(@RequestBody SysAppFlowPath sysAppFlowPath) {
        ReturnModel returnModel = new ReturnModel();
        try {
            int i = sysAppFlowPathService.addSysAppFlowPath(sysAppFlowPath);

            if (i == 1) {
                returnModel.setData("");
                returnModel.setMsg("添加审批流程成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            } else {
                returnModel.setData("");
                returnModel.setMsg("添加审批流程失败！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }

        } catch (Exception e) {

            e.printStackTrace();
            returnModel.setData("");
            returnModel.setMsg("审批流程添加失败！(请联系管理员)");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }


    /**
     * 修改审批流程
     *
     * @param
     * @return
     */
    @ApiOperation("修改审批审批")
    @RequestMapping("/editSysAppFlowPath")
    @ResponseBody
    public ReturnModel editSysAppFlowPath(@RequestBody SysAppFlowPath sysAppFlowPath) {
        ReturnModel returnModel = new ReturnModel();
        try {
            int i = sysAppFlowPathService.editSysAppFlowPath(sysAppFlowPath);
            if (i == 1) {
                returnModel.setData("");
                returnModel.setMsg("修改审批流程成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            } else {
                returnModel.setData("");
                returnModel.setMsg("修改审批流程失败！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }

        } catch (Exception e) {
            logger.error("editSysAppFlowPath", e);
            returnModel.setData("");
            returnModel.setMsg("修改审批流程失败！（请联系管理员！）");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    /**
     * @return com.hna.hka.archive.management.system.util.PageDataResult
     * @Author 郭凯
     * @Description 删除审批流程
     * @Date 9:24 2020/5/20
     * @Param [pageNum, pageSize, sysRobot]
     **/
    @ApiOperation("删除审批流程")
    @RequestMapping("/delSysAppFlowPath")
    @ResponseBody
    public ReturnModel delSysAppFlowPath(SysAppFlowPath sysAppFlowPath) {

        ReturnModel returnModel = new ReturnModel();
        try {
            int i = sysAppFlowPathService.delSysAppFlowPath(sysAppFlowPath.getId());
            if (i == 1) {
                returnModel.setData(i);
                returnModel.setMsg("删除审批流程成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            } else {
                returnModel.setData(i);
                returnModel.setMsg("删除审批流程失败！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }

        } catch (Exception e) {
            logger.info("删除审批流程失败", e);
            returnModel.setData("");
            returnModel.setMsg("删除审批流程失败！");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }

    }

    /**
     * @return com.hna.hka.archive.management.system.util.PageDataResult
     * @Author
     * @Description 根据审批流程id，获取审批流程人员
     * @Date 9:24 2020/5/20
     * @Param [pageNum, pageSize, sysRobot]
     **/
    @ApiOperation("根据审批流程id，获取审批流程人员")
    @RequestMapping("/getAppFlowPathIdByPeople")
    @ResponseBody
    public ReturnModel getAppFlowPathIdByPeople(SysAppFlowPath sysAppFlowPath) {

        ReturnModel returnModel = new ReturnModel();
        try {
            List<SysAppFlowPathDetails> list = sysAppFlowPathService.getAppFlowPathIdByPeople(sysAppFlowPath.getId());

            returnModel.setData(list);
            returnModel.setMsg("列表查询成功！");
            returnModel.setState(Constant.STATE_SUCCESS);
            return returnModel;


        } catch (Exception e) {
            logger.info("查询失败失败", e);
            returnModel.setData("");
            returnModel.setMsg("查询失败！");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }

    }


    @ApiOperation("获取审核人下拉选")
    @RequestMapping("/reviewedDrop")
    @ResponseBody
    public ReturnModel reviewedDrop() {

        ReturnModel returnModel = new ReturnModel();

        List<SysAppUsers> list = sysAppUsersService.getSysAppUsers();

        returnModel.setData(list);
        returnModel.setMsg("列表查询成功！");
        returnModel.setState(Constant.STATE_SUCCESS);
        return returnModel;


    }


//    /**
//     * 审批流程下拉选
//     * @param sysAppFlowPath
//     * @return
//     */
//    @RequestMapping("/sysAppFlowPathDrop")
//    @ResponseBody
//    public ReturnModel sysAppFlowPathDrop( SysAppFlowPath sysAppFlowPath) {
//
//        ReturnModel returnModel = new ReturnModel();
//        try {
//
//            int i  = sysAppFlowPathService.delSysAppFlowPath(sysAppFlowPath.getId());
//            if (i == 1){
//                returnModel.setData(i);
//                returnModel.setMsg("删除审批流程成功！");
//                returnModel.setState(Constant.STATE_SUCCESS);
//                return returnModel;
//            }else{
//                returnModel.setData(i);
//                returnModel.setMsg("删除审批流程失败！");
//                returnModel.setState(Constant.STATE_FAILURE);
//                return returnModel;
//            }
//
//        }catch (Exception e){
//            logger.info("删除审批流程失败",e);
//            returnModel.setData("");
//            returnModel.setMsg("删除审批流程失败！");
//            returnModel.setState(Constant.STATE_FAILURE);
//            return returnModel;
//        }
//
//    }

}
