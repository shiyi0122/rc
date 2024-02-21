package com.hna.hka.archive.management.managerApp.controller;

import com.hna.hka.archive.management.managerApp.model.SysAppUsers;
import com.hna.hka.archive.management.managerApp.model.SysAppUsersSpotRole;
import com.hna.hka.archive.management.managerApp.service.SysAppUsersService;
import com.hna.hka.archive.management.system.model.SysScenicSpotBinding;
import com.hna.hka.archive.management.system.service.SysScenicSpotBindingService;
import com.hna.hka.archive.management.system.util.*;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ProjectName: IDEA-CODE
 * @Package: com.hna.hka.archive.management.managerApp.controller
 * @ClassName: AppUsersController
 * @Author: 郭凯
 * @Description: 管理者APP用户管理控制层
 * @Date: 2020/11/3 9:51
 * @Version: 1.0
 */
@RequestMapping("/system/appUsers")
@Controller
public class AppUsersController extends PublicUtil {

    @Autowired
    private SysAppUsersService sysAppUsersService;

    @Autowired
    private SysScenicSpotBindingService sysScenicSpotBindingService;

    /**
     * @Author 郭凯
     * @Description 管理者APP用户管理列表查询
     * @Date 10:03 2020/11/3
     * @Param [pageNum, pageSize, sysAppUsers]
     * @return com.hna.hka.archive.management.system.util.PageDataResult
    **/
    @RequestMapping("/getAppUsersList")
    @ResponseBody
    public PageDataResult getAppUsersList(@RequestParam("pageNum") Integer pageNum,
                                         @RequestParam("pageSize") Integer pageSize, SysAppUsers sysAppUsers) {
        PageDataResult pageDataResult = new PageDataResult();
        Map<String,String> search = new HashMap<>();
        try {
            if(null == pageNum) {
                pageNum = 1;
            }
            if(null == pageSize) {
                pageSize = 10;
            }
            search.put("userName",sysAppUsers.getUserName());
            search.put("loginName",sysAppUsers.getLoginName());
            pageDataResult = sysAppUsersService.getAppUsersList(pageNum,pageSize,search);
        }catch (Exception e){
            logger.info("管理者APP用户管理列表查询失败",e);
        }
        return pageDataResult;
    }

    /**
     * @Author 郭凯
     * @Description APP用户修改
     * @Date 10:56 2020/11/3
     * @Param [sysAppUsers]
     * @return com.hna.hka.archive.management.system.util.ReturnModel
    **/
    @RequestMapping("/editAppUsers")
    @ResponseBody
    public ReturnModel editAppUsers(SysAppUsers sysAppUsers){
        ReturnModel returnModel = new ReturnModel();
        try {
            int i = sysAppUsersService.editAppUsers(sysAppUsers);
            if (i == 1){
                returnModel.setData("");
                returnModel.setMsg("APP用户修改成功");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            }else{
                returnModel.setData("");
                returnModel.setMsg("APP用户修改失败");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        }catch (Exception e){
            logger.info("editAppUsers",e);
            returnModel.setData("");
            returnModel.setMsg("APP用户修改失败！（请联系管理员）");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    /**
     * @Author 郭凯
     * @Description APP用户新增
     * @Date 13:46 2020/11/3
     * @Param [sysAppUsers]
     * @return com.hna.hka.archive.management.system.util.ReturnModel
    **/
    @RequestMapping("/addAppUser")
    @ResponseBody
    public ReturnModel addAppUser(SysAppUsers sysAppUsers){
        ReturnModel returnModel = new ReturnModel();
        try {
            int i = sysAppUsersService.addAppUser(sysAppUsers);
            if (i == 1){
                returnModel.setData("");
                returnModel.setMsg("APP用户新增成功");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            }else{
                returnModel.setData("");
                returnModel.setMsg("APP用户新增失败");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        }catch (Exception e){
            logger.info("addAppUser",e);
            returnModel.setData("");
            returnModel.setMsg("APP用户新增失败！（请联系管理员）");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    /**
     * @Author 郭凯
     * @Description APP用户删除
     * @Date 14:01 2020/11/3
     * @Param [userId]
     * @return com.hna.hka.archive.management.system.util.ReturnModel
    **/
    @RequestMapping("/delAppUsers")
    @ResponseBody
    public ReturnModel delAppUsers(@RequestParam("userId") Long userId){
        ReturnModel returnModel = new ReturnModel();
        try {
            int i = sysAppUsersService.delAppUsers(userId);
            if (i == 1){
                returnModel.setData("");
                returnModel.setMsg("APP用户删除成功");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            }else{
                returnModel.setData("");
                returnModel.setMsg("APP用户删除失败");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        }catch (Exception e){
            logger.info("delAppUsers",e);
            returnModel.setData("");
            returnModel.setMsg("APP用户删除失败！（请联系管理员）");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    /**
     * @Author 郭凯
     * @Description 查询所有景区
     * @Date 14:55 2020/11/3
     * @Param []
     * @return com.hna.hka.archive.management.system.util.ReturnModel
    **/
    @RequestMapping("/getScenicSpotList")
    @ResponseBody
    public ReturnModel getScenicSpotList(){
        ReturnModel returnModel = new ReturnModel();
        try {
            List<SysScenicSpotBinding> scenicSpotList = sysScenicSpotBindingService.getScenicSpotBinding();
            if (ToolUtil.isNotEmpty(scenicSpotList)){
                returnModel.setData(scenicSpotList);
                returnModel.setMsg("查询成功");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            }else{
                returnModel.setData("");
                returnModel.setMsg("查询失败");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        }catch (Exception e){
            logger.info("getScenicSpotList",e);
            returnModel.setData("");
            returnModel.setMsg("查询失败！（请联系管理员）");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    /**
     * @Author 郭凯
     * @Description 查询回显景区
     * @Date 17:38 2020/11/3
     * @Param [userId]
     * @return com.hna.hka.archive.management.system.util.ReturnModel
    **/
    @RequestMapping("/getScenicSpotzTree")
    @ResponseBody
    public ReturnModel getScenicSpotzTree(Long userId){
        ReturnModel returnModel = new ReturnModel();
        try {
            List<SysAppUsersSpotRole> appUsersSpotRoleList = sysAppUsersService.getScenicSpotzTree(userId);
            returnModel.setData(appUsersSpotRoleList);
            returnModel.setMsg("查询成功");
            returnModel.setState(Constant.STATE_SUCCESS);
            return returnModel;
        }catch (Exception e){
            logger.info("getScenicSpotList",e);
            returnModel.setData("");
            returnModel.setMsg("查询失败！（请联系管理员）");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    /**
     * @Author 郭凯
     * @Description 新增APP账号景区角色权限
     * @Date 17:47 2020/11/3
     * @Param [roleId, scenicSpots, userId]
     * @return com.hna.hka.archive.management.system.util.ReturnModel
    **/
    @RequestMapping("/addRoleScenicSpot")
    @ResponseBody
    public ReturnModel addRoleScenicSpot(@NotBlank(message = "角色ID不能为空")Long roleId, @NotBlank(message = "景区ID不能为空")String scenicSpotId, @NotBlank(message = "用户ID不能为空")String userId){
        ReturnModel returnModel = new ReturnModel();
        try {
            int i = sysAppUsersService.addRoleScenicSpot(roleId,scenicSpotId,userId);
            if (i == 2){
                returnModel.setData("");
                returnModel.setMsg("此景区已经分配，请重新分配。");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            } if (i > 0){
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
            logger.info("addRoleScenicSpot",e);
            returnModel.setData("");
            returnModel.setMsg("权限设置失败！（请联系管理员）");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    /**
     * @Author 郭凯
     * @Description 管理者APP重置密码
     * @Date 11:43 2020/11/4
     * @Param [sysAppUsers]
     * @return com.hna.hka.archive.management.system.util.ReturnModel
    **/
    @RequestMapping("/resetPassword")
    @ResponseBody
    public ReturnModel resetPassword(SysAppUsers sysAppUsers){
        ReturnModel returnModel = new ReturnModel();
        try {
            int i = sysAppUsersService.resetPassword(sysAppUsers);
            if (i == 1){
                returnModel.setData("");
                returnModel.setMsg("密码重置成功");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            }else{
                returnModel.setData("");
                returnModel.setMsg("密码重置失败");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        }catch (Exception e){
            logger.info("resetPassword",e);
            returnModel.setData("");
            returnModel.setMsg("密码重置失败！（请联系管理员）");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    /**
    * @Author 郭凯
    * @Description: 下载Excel表
    * @Title: uploadUserExcel
    * @date  2021年3月3日 下午2:04:19
    * @param @param response
    * @param @param sysUsers
    * @param @throws Exception
    * @return void
    * @throws
     */
    @RequestMapping(value = "/uploadUserExcel")
    public void  uploadUserExcel(HttpServletResponse response, SysAppUsers sysUsers) throws Exception {
        List<SysAppUsers> usersListByExample = null;
        Map<String,Object> search = new HashMap<>();
        search.put("userName",sysUsers.getUserName());
        usersListByExample = sysAppUsersService.getUsersVoExcel(search);
        String dateTime = DateFormatUtils.format(new Date(), "yyyyMMddHHmm");
        FileUtil.exportExcel(FileUtil.getWorkbook("用户列表", "用户列表", SysAppUsers.class, usersListByExample),"用户列表"+ dateTime +".xls",response);
    }

    /**
     * @Method getSysAppUsers
     * @Author 郭凯
     * @Version  1.0
     * @Description 查询所有员工
     * @Return com.hna.hka.archive.management.system.util.ReturnModel
     * @Date 2021/6/25 14:56
     */
    @RequestMapping("/getSysAppUsers")
    @ResponseBody
    public ReturnModel getSysAppUsers(){
        ReturnModel returnModel = new ReturnModel();
        try {
            List<SysAppUsers> sysAppUsersList = sysAppUsersService.getSysAppUsers();
            returnModel.setData(sysAppUsersList);
            returnModel.setMsg("查询成功");
            returnModel.setState(Constant.STATE_SUCCESS);
            return returnModel;
        }catch (Exception e){
            logger.info("getSysAppUsers",e);
            returnModel.setData("");
            returnModel.setMsg("查询失败！（请联系管理员）");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

}
