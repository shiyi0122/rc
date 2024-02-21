package com.hna.hka.archive.management.system.controller;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.hna.hka.archive.management.system.model.SysScenicSpotBinding;
import com.hna.hka.archive.management.system.model.SysUsers;
import com.hna.hka.archive.management.system.model.SysUsersRoleSpot;
import com.hna.hka.archive.management.system.service.SysUserService;
import com.hna.hka.archive.management.system.util.*;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ProjectName: IDEA-CODE
 * @Package: com.hna.hka.archive.management.system.controller
 * @ClassName: UserController
 * @Author: 郭凯
 * @Description: 用户管理控制层
 * @Date: 2020/5/16 14:03
 * @Version: 1.0
 */

@RequestMapping("/system/user")
@Controller
public class UserController extends PublicUtil {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private HttpServletRequest request;

    /**
     * @Author 郭凯
     * @Description 查询用户列表
     * @Date 14:07 2020/5/16
     * @Param [pageNum, pageSize, sysUsers]
     * @return com.hna.hka.archive.management.system.util.PageDataResult
     **/
    @RequestMapping(value = "/getUserList", method = RequestMethod.GET)
    @ResponseBody
    public PageDataResult getUserList(@RequestParam("pageNum") Integer pageNum,
                                      @RequestParam("pageSize") Integer pageSize, SysUsers sysUsers) {
        PageDataResult pageDataResult = new PageDataResult();
        try {
            if(null == pageNum) {
                pageNum = 1;
            }
            if(null == pageSize) {
                pageSize = 10;
            }
            pageDataResult = sysUserService.getUserList(pageNum,pageSize,sysUsers);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("用户列表查询异常！", e);
        }
        return pageDataResult;
    }
    /**
     * @Author 郭凯
     * @Description 新增用户
     * @Date 9:13 2020/5/18
     * @Param [sysUsers]
     * @return com.hna.hka.archive.management.system.util.ReturnModel
    **/
    @RequestMapping("/addUser")
    @ResponseBody
    public ReturnModel addUser(SysUsers sysUsers){
        ReturnModel returnModel = new ReturnModel();
        try {
            int i = sysUserService.addUser(sysUsers);
            if (i == 1){
                returnModel.setData("");
                returnModel.setMsg("用户新增成功");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            }else if (i == 2){
                returnModel.setData("");
                returnModel.setMsg("此账号已存在！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }else if (i != 1 && i != 2){
                returnModel.setData("");
                returnModel.setMsg("用户新增失败");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        }catch (Exception e){
            logger.info("addUser",e);
            returnModel.setData("");
            returnModel.setMsg("用户新增失败！（请联系管理员）");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
        return returnModel;
    }

    /**
     * @Author 郭凯
     * @Description 修改用户信息
     * @Date 13:31 2020/5/18
     * @Param [sysUsers]
     * @return com.hna.hka.archive.management.system.util.ReturnModel
     **/
    @RequestMapping("/editUser")
    @ResponseBody
    public ReturnModel editUser(SysUsers sysUsers){
        ReturnModel returnModel = new ReturnModel();
        try {
            int i = sysUserService.editUser(sysUsers);
            if (i == 1){
                returnModel.setData("");
                returnModel.setMsg("用户修改成功");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            }else{
                returnModel.setData("");
                returnModel.setMsg("用户修改失败");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        }catch (Exception e){
            logger.info("addUser",e);
            returnModel.setData("");
            returnModel.setMsg("用户修改失败！（请联系管理员）");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    /**
     * @Author 郭凯
     * @Description 删除用户
     * @Date 13:37 2020/5/18
     * @Param [sysUsers]
     * @return com.hna.hka.archive.management.system.util.ReturnModel
     **/
    @RequestMapping("/delUser")
    @ResponseBody
    public ReturnModel delUser(@NotBlank(message = "用户ID不能为空")Long userId){
        ReturnModel returnModel = new ReturnModel();
        try {
            int i = sysUserService.delUser(userId);
            if (i == 1){
                returnModel.setData("");
                returnModel.setMsg("用户删除成功");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            }else{
                returnModel.setData("");
                returnModel.setMsg("用户删除失败");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        }catch (Exception e){
            logger.info("delUser",e);
            returnModel.setData("");
            returnModel.setMsg("用户删除失败！（请联系管理员）");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    /**
     * @Author 郭凯
     * @Description 查询角色景区
     * @Date 9:24 2020/5/19
     * @Param []
     * @return com.hna.hka.archive.management.system.util.ReturnModel
     **/
    @RequestMapping("/getScenicSpotRole")
    @ResponseBody
    public ReturnModel getScenicSpotRole(@NotBlank(message = "用户ID不能为空")String userId){
        ReturnModel returnModel = new ReturnModel();
        try {
            Map<String,String> search = new HashMap<>();
            search.put("userId",userId);
            List<SysScenicSpotBinding> sysScenicSpotBindingList = sysUserService.getScenicSpotRole(search);
            if (sysScenicSpotBindingList.size() > 0){
                returnModel.setData(sysScenicSpotBindingList);
                returnModel.setMsg("查询成功");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            }else if(ToolUtil.isEmpty(sysScenicSpotBindingList)){
                returnModel.setData("");
                returnModel.setMsg("景区已经全部分配完毕");
                returnModel.setState(Constant.STATE_NORMAL);
                return returnModel;
            }else{
                returnModel.setData("");
                returnModel.setMsg("查询失败");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        }catch (Exception e){
            logger.info("getScenicSpotRole",e);
            returnModel.setData("");
            returnModel.setMsg("查询失败！（请联系管理员）");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    /**
     * @Author 郭凯
     * @Description 新增账号景区角色权限
     * @Date 11:30 2020/5/19
     * @Param [roleId, scenicSpots, userId]
     * @return com.hna.hka.archive.management.system.util.ReturnModel
     **/
    @RequestMapping("/addScenicSpotRole")
    @ResponseBody
    public ReturnModel addScenicSpotRole(@NotBlank(message = "角色ID不能为空")Long roleId,@NotBlank(message = "景区ID不能为空")String scenicSpots,@NotBlank(message = "用户ID不能为空")String userId){
        ReturnModel returnModel = new ReturnModel();
        try {
            int i = sysUserService.addScenicSpotRoleNew(roleId,scenicSpots,userId);
            if (i > 0){
                returnModel.setData("");
                returnModel.setMsg("权限设置成功,审核通过后即可使用");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            }else{
                returnModel.setData("");
                returnModel.setMsg("权限设置失败");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        }catch (Exception e){
            logger.info("addScenicSpotRole",e);
            returnModel.setData("");
            returnModel.setMsg("权限设置失败！（请联系管理员）");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    /**
     * @Author 郭凯
     * @Description 用户密码重置
     * @Date 16:10 2020/11/19
     * @Param [sysAppUsers]
     * @return com.hna.hka.archive.management.system.util.ReturnModel
    **/
    @RequestMapping("/resetPassword")
    @ResponseBody
    public ReturnModel resetPassword(SysUsers sysUsers){
        ReturnModel returnModel = new ReturnModel();
        //SysUsers user = (SysUsers) SecurityUtils.getSubject().getPrincipal();
        try {
            if (ToolUtil.isEmpty(sysUsers.getUserId())){
                sysUsers.setUserId(sysUsers.getUserId());
            }
            int i = sysUserService.resetPassword(sysUsers);
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
     * @Description 查看权限详情
     * @Date 11:01 2020/11/20
     * @Param [sysUsersRoleSpot]
     * @return com.hna.hka.archive.management.system.util.ReturnModel
    **/
    @RequestMapping("/getUserRoleSpotList")
    @ResponseBody
    private PageDataResult getUserRoleSpotList(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize,SysUsersRoleSpot sysUsersRoleSpot){
        PageDataResult pageDataResult = new PageDataResult();
        Map<String,Object> search = new HashMap<>();
        try {
            if(null == pageNum) {
                pageNum = 1;
            }
            if(null == pageSize) {
                pageSize = 10;
            }
            search.put("userId",sysUsersRoleSpot.getUserId());
            search.put("scenicSpotName",sysUsersRoleSpot.getScenicSpotName());
            //查询权限详情
            pageDataResult = sysUserService.getUserRoleSpotList(pageNum,pageSize,search);
        }catch (Exception e){
            e.printStackTrace();
            logger.error("用户列表查询异常！", e);
        }
        return pageDataResult;
    }

    /**
     * @Author 郭凯
     * @Description 删除用户权限
     * @Date 11:24 2020/11/20
     * @Param [id]
     * @return com.hna.hka.archive.management.system.util.ReturnModel
    **/
    @RequestMapping("/delUserRoleSpot")
    @ResponseBody
    public ReturnModel delUserRoleSpot(@NotBlank(message = "ID不能为空")Long id){
        ReturnModel returnModel = new ReturnModel();
        try {
            int i = sysUserService.delUserRoleSpot(id);
            if (i == 1){
                returnModel.setData("");
                returnModel.setMsg("用户权限删除成功");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            }else{
                returnModel.setData("");
                returnModel.setMsg("用户权限删除失败");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        }catch (Exception e){
            logger.info("delUserRoleSpot",e);
            returnModel.setData("");
            returnModel.setMsg("用户权限删除失败！（请联系管理员）");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    /**
     * @Author 郭凯
     * @Description 导出用户权限Excel表
     * @Date 9:53 2020/12/4
     * @Param [response, sysOrder]
     * @return void
    **/
    @RequestMapping(value = "/uploadExcel")
    public void  uploadExcel(HttpServletResponse response, SysUsersRoleSpot sysUsersRoleSpot) throws Exception {
        List<SysUsersRoleSpot> usersRoleSpotListByExample = null;
        Map<String,Object> search = new HashMap<>();
        String scenicSpotName = request.getParameter("scenicSpotName");//获取景区名称
        search.put("scenicSpotName",scenicSpotName);
        search.put("userId",sysUsersRoleSpot.getUserId());
        search.put("userName",sysUsersRoleSpot.getUserName());
        usersRoleSpotListByExample = sysUserService.getUsersRoleSpotVoExcel(search);
        String dateTime = DateFormatUtils.format(new Date(), "yyyyMMddHHmm");
        FileUtil.exportExcel(FileUtil.getWorkbook("用户权限", "用户权限", SysUsersRoleSpot.class, usersRoleSpotListByExample),"用户权限"+ dateTime +".xls",response);
    }

    /**
     * @Author 郭凯
     * @Description 修改禁用启用状态
     * @Date 13:43 2020/12/8
     * @Param [sysUsers]
     * @return com.hna.hka.archive.management.system.util.ReturnModel
    **/
    @RequestMapping("/editUserState")
    @ResponseBody
    public ReturnModel editUserState(SysUsers sysUsers){
        ReturnModel returnModel = new ReturnModel();
        try {
            int i = sysUserService.editUser(sysUsers);
            if (i == 1){
                returnModel.setData("");
                returnModel.setMsg("修改成功");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            }else{
                returnModel.setData("");
                returnModel.setMsg("修改失败");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        }catch (Exception e){
            logger.info("editUserState",e);
            returnModel.setData("");
            returnModel.setMsg("修改失败！（请联系管理员）");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    /**
     * @Author 郭凯
     * @Description 获取当前用户的所有信息
     * @Date 14:22 2020/12/8
     * @Param [sysUsers]
     * @return com.hna.hka.archive.management.system.util.ReturnModel
    **/
    @RequestMapping("/getUser")
    @ResponseBody
    public ReturnModel getUser(){
        ReturnModel returnModel = new ReturnModel();
        try {
            SysUsers SysUsers = sysUserService.getUsersById(getSysUser().getUserId());
            if (ToolUtil.isNotEmpty(SysUsers)){
                returnModel.setData(SysUsers);
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
            logger.info("getUser",e);
            returnModel.setData("");
            returnModel.setMsg("查询失败！（请联系管理员）");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    /**
     * @Author 郭凯
     * @Description 导入用户信息
     * @Date 16:52 2020/12/9
     * @Param [multipartFile]
     * @return com.hna.hka.archive.management.system.util.ReturnModel
    **/
    @RequestMapping("/upload")
    @ResponseBody
    public ReturnModel upload(@RequestParam("file") MultipartFile multipartFile) throws Exception {
        ReturnModel returnModel = new ReturnModel();
        try {
            ImportParams params = new ImportParams();
            params.setTitleRows(1);
            params.setHeadRows(1);
            List<SysUsers> result = ExcelImportUtil.importExcel(multipartFile.getInputStream(),SysUsers.class, params);
            for (SysUsers sysUsers:result){
                SysUsers user = sysUserService.getSysUsersByLoginName(sysUsers.getLoginName());
                if (ToolUtil.isNotEmpty(user)){
                    sysUsers.setUserId(user.getUserId());
                    sysUserService.editUser(sysUsers);
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

    /**
     * @Author 郭凯
     * @Description 导出用户Excel表
     * @Date 9:19 2020/12/10
     * @Param [response, sysUsers]
     * @return void
    **/
    @RequestMapping(value = "/uploadUserExcel")
    public void  uploadUserExcel(HttpServletResponse response, SysUsers sysUsers) throws Exception {
        List<SysUsers> usersListByExample = null;
        Map<String,Object> search = new HashMap<>();
        search.put("userName",sysUsers.getUserName());
        search.put("loginName",sysUsers.getLoginName());
        usersListByExample = sysUserService.getUsersVoExcel(search);
        String dateTime = DateFormatUtils.format(new Date(), "yyyyMMddHHmm");
        FileUtil.exportExcel(FileUtil.getWorkbook("用户列表", "用户列表", SysUsers.class, usersListByExample),"用户列表"+ dateTime +".xls",response);
    }

    /**
     * 审核接口
     * @param sysUsers
     * @return
     */
    @RequestMapping("/userExamine")
    @ResponseBody
    public ReturnModel userExamine(SysUsers sysUsers){
        ReturnModel returnModel = new ReturnModel();
        try {
            int i = sysUserService.userExamine(sysUsers);
            if (i == 1){
                returnModel.setData("");
                returnModel.setMsg("审核成功");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            }else if (i==2){
                returnModel.setData("");
                returnModel.setMsg("驳回成功");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            }else{
                returnModel.setData("");
                returnModel.setMsg("审核失败");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        }catch (Exception e){
            logger.info("editUserState",e);
            returnModel.setData("");
            returnModel.setMsg("修改失败！（请联系管理员）");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    /**
     * @Author zhang
     * @Description 查询审核用户列表
     * @Date 14:07 2022/3/10
     * @Param [pageNum, pageSize, sysUsers]
     * @return com.hna.hka.archive.management.system.util.PageDataResult
     **/
    @RequestMapping(value = "/getUserExamineList", method = RequestMethod.GET)
    @ResponseBody
    public PageDataResult getUserExamineList(@RequestParam("pageNum") Integer pageNum,
                                      @RequestParam("pageSize") Integer pageSize, SysUsers sysUsers) {
        PageDataResult pageDataResult = new PageDataResult();
        try {
            if(null == pageNum) {
                pageNum = 1;
            }
            if(null == pageSize) {
                pageSize = 10;
            }
            pageDataResult = sysUserService.getUserExamineList(pageNum,pageSize,sysUsers);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("用户列表查询异常！", e);
        }
        return pageDataResult;
    }




}
