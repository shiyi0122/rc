package com.hna.hka.archive.management.managerApp.controller;

import com.hna.hka.archive.management.appSystem.model.SysAppRole;
import com.hna.hka.archive.management.managerApp.model.SysManagerAppRes;
import com.hna.hka.archive.management.managerApp.service.SysManagerAppResService;
import com.hna.hka.archive.management.managerApp.service.SysManagerAppRoleService;
import com.hna.hka.archive.management.system.model.SysResource;
import com.hna.hka.archive.management.system.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.constraints.NotBlank;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ProjectName: rc
 * @Package: com.hna.hka.archive.management.managerApp.controller
 * @ClassName: AppRoleController
 * @Author: 郭凯
 * @Description: APP角色管理控制层
 * @Date: 2021/6/4 15:14
 * @Version: 1.0
 */
@RequestMapping("/system/AppRole")
@Controller
public class AppRoleController extends PublicUtil {

    @Autowired
    private SysManagerAppRoleService sysAppRoleService;

    @Autowired
    private SysManagerAppResService sysManagerAppResService;

    /**
     * @Method getAppRoleList
     * @Author 郭凯
     * @Version  1.0
     * @Description APP角色管理列表查询
     * @Return com.hna.hka.archive.management.system.util.PageDataResult
     * @Date 2021/6/4 15:18
     */
    @RequestMapping("/getAppRoleList")
    @ResponseBody
    public PageDataResult getAppRoleList(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize, SysAppRole sysAppRole) {
        PageDataResult pageDataResult = new PageDataResult();
        Map<String,Object> search = new HashMap<>();
        try {
            if(null == pageNum) {
                pageNum = 1;
            }
            if(null == pageSize) {
                pageSize = 10;
            }
            pageDataResult = sysAppRoleService.getAppRoleList(pageNum,pageSize,search);
        }catch (Exception e){
            logger.info("APP角色管理列表查询失败",e);
        }
        return pageDataResult;
    }

    /**
     * @Method addAppRole
     * @Author 郭凯
     * @Version  1.0
     * @Description 新增管理者APP角色
     * @Return com.hna.hka.archive.management.system.util.ReturnModel
     * @Date 2021/6/4 16:35
     */
    @RequestMapping(value = "/addAppRole")
    @ResponseBody
    public ReturnModel addAppRole(SysAppRole sysAppRole) {
        ReturnModel dataModel = new ReturnModel();
        try {

            int i = sysAppRoleService.addAppRole(sysAppRole);
            if (i == 1) {
                dataModel.setData("");
                dataModel.setMsg("APP角色新增成功！");
                dataModel.setState(Constant.STATE_SUCCESS);
                return dataModel;
            } else {
                dataModel.setData("");
                dataModel.setMsg("APP角色新增失败！");
                dataModel.setState(Constant.STATE_FAILURE);
                return dataModel;
            }
        } catch (Exception e) {
            dataModel.setData("");
            dataModel.setMsg("APP角色新增失败！");
            dataModel.setState(Constant.STATE_FAILURE);
            logger.error("addAppRole", e);
            return dataModel;
        }
    }

    /**
     * @Method getEchoAppZtree
     * @Author 郭凯
     * @Version  1.0
     * @Description 查询回显数据
     * @Return com.hna.hka.archive.management.system.util.ReturnModel
     * @Date 2021/6/4 17:09
     */
    @RequestMapping("/getEchoAppZtree")
    @ResponseBody
    public ReturnModel getEchoAppZtree(@NotBlank(message = "角色ID不能为空")Long roleId){
        ReturnModel returnModel = new ReturnModel();
        try {
            List<SysManagerAppRes> managerAppRes = sysManagerAppResService.getEchoAppZtree(roleId);
            returnModel.setData(managerAppRes);
            returnModel.setMsg("角色权限查询成功！");
            returnModel.setState(Constant.STATE_SUCCESS);
            return returnModel;
        }catch (Exception e){
            logger.info("getEchoAppZtree",e);
            returnModel.setData("");
            returnModel.setMsg("角色权限查询失败！（请联系管理员）");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    /**
     * @Method addAppRoleResource
     * @Author 郭凯
     * @Version  1.0
     * @Description 设置APP权限
     * @Return com.hna.hka.archive.management.system.util.ReturnModel
     * @Date 2021/6/4 17:22
     */
    @RequestMapping("/addAppRoleResource")
    @ResponseBody
    public ReturnModel addAppRoleResource(@NotBlank(message = "角色ID不能为空")Long roleId,@NotBlank(message = "资源不能为空")String resIds){
        ReturnModel returnModel = new ReturnModel();
        try {
            int i = sysAppRoleService.addRoleResource(roleId,resIds);
            if (i > 0){
                returnModel.setData("");
                returnModel.setMsg("权限设置成功");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            }else{
                returnModel.setData("");
                returnModel.setMsg("权限设置失败");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        }catch (Exception e){
            logger.info("addAppRoleResource",e);
            returnModel.setData("");
            returnModel.setMsg("权限设置失败！（请联系管理员）");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    /**
     * @Method getAppRoleList
     * @Author 郭凯
     * @Version  1.0
     * @Description 查询角色下拉框
     * @Return com.hna.hka.archive.management.system.util.ReturnModel
     * @Date 2021/6/4 17:46
     */
    @RequestMapping(value = "/getAppRoleLists")
    @ResponseBody
    public ReturnModel getAppRoleLists() {
        ReturnModel dataModel = new ReturnModel();
        try {
            List<SysAppRole> appRoleList = sysAppRoleService.getAppRoleLists();
            if (ToolUtil.isNotEmpty(appRoleList)) {
                dataModel.setData(appRoleList);
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
            logger.error("getAppRoleLists", e);
            return dataModel;
        }
    }

}
