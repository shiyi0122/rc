package com.hna.hka.archive.management.system.controller;

import com.github.pagehelper.PageInfo;
import com.hna.hka.archive.management.system.model.SysResource;
import com.hna.hka.archive.management.system.model.SysRole;
import com.hna.hka.archive.management.system.model.SysUsers;
import com.hna.hka.archive.management.system.model.SysUsersRoleSpot;
import com.hna.hka.archive.management.system.service.SysRoleService;
import com.hna.hka.archive.management.system.util.Constant;
import com.hna.hka.archive.management.system.util.PageDataResult;
import com.hna.hka.archive.management.system.util.PublicUtil;
import com.hna.hka.archive.management.system.util.ReturnModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * 角色管理控制层
 * @ProjectName: IDEA-CODE
 * @Package: com.hna.hka.archive.management.system.controller
 * @ClassName: RoleController
 * @Author: 郭凯
 * @Description:
 * @Date: 2020/5/12 14:33
 * @Version: 1.0
 */
@RequestMapping("/system/role")
@Controller
public class RoleController extends PublicUtil {

    @Autowired
    private SysRoleService sysRoleService;

    /**
     * 角色列表查询
     * @Author 郭凯
     * @Description
     * @Date 14:37 2020/5/12
     * @Param [pageNum, pageSize, sysRole]
     * @return com.hna.hka.archive.management.system.util.PageDataResult
     **/
    @RequestMapping(value = "/getRoleList", method = RequestMethod.GET)
    @ResponseBody
    public PageDataResult getRoleList(@RequestParam("pageNum") Integer pageNum,
                                   @RequestParam("pageSize") Integer pageSize, SysRole sysRole) {
        PageDataResult pageDataResult = new PageDataResult();
        try {
            if(null == pageNum) {
                pageNum = 1;
            }
            if(null == pageSize) {
                pageSize = 10;
            }
            pageDataResult = sysRoleService.getRoleList(pageNum,pageSize,sysRole);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("角色列表查询异常！", e);
        }
        return pageDataResult;
    }

    /**
     * 添加角色
     * @Author 郭凯
     * @Description
     * @Date 16:23 2020/5/12
     * @Param [sysRole]
     * @return com.hna.hka.archive.management.system.util.ReturnModel
    **/
    @RequestMapping("/addRole")
    @ResponseBody
    public ReturnModel addRole(SysRole sysRole){
        ReturnModel returnModel = null;
        try {
            returnModel = new ReturnModel();
            int i = sysRoleService.addRole(sysRole);
            if (i == 1){
                returnModel.setData("");
                returnModel.setMsg("角色新增成功");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            }else{
                returnModel.setData("");
                returnModel.setMsg("角色新增失败");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        }catch (Exception e){
            logger.info("addRole",e);
            returnModel.setData("");
            returnModel.setMsg("角色新增失败！（请联系管理员）");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    /**
     * @Author 郭凯
     * @Description 删除角色
     * @Date 15:58 2020/5/13
     * @Param [resId]
     * @return com.hna.hka.archive.management.system.util.ReturnModel
     **/
    @RequestMapping("/delRole")
    @ResponseBody
    public ReturnModel delRole(@NotBlank(message = "角色ID不能为空")Long roleId){
        ReturnModel returnModel = new ReturnModel();
        try {
            int i = sysRoleService.delRole(roleId);
            if (i == 1){
                returnModel.setData("");
                returnModel.setMsg("角色删除成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            }else{
                returnModel.setData("");
                returnModel.setMsg("角色删除错误，请联系管理员！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        }catch (Exception e){
            returnModel.setData("");
            returnModel.setMsg("角色删除错误，请联系管理员！");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    /**
     * 修改角色信息
     * @Author 郭凯
     * @Description
     * @Date 10:41 2020/5/15
     * @Param [sysRole]
     * @return com.hna.hka.archive.management.system.util.ReturnModel
    **/
    @RequestMapping("/editRole")
    @ResponseBody
    public ReturnModel editRole(SysRole sysRole){
        ReturnModel returnModel = null;
        try {
            returnModel = new ReturnModel();
            int i = sysRoleService.editRole(sysRole);
            if (i == 1){
                returnModel.setData("");
                returnModel.setMsg("角色修改成功");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            }else{
                returnModel.setData("");
                returnModel.setMsg("角色修改失败");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        }catch (Exception e){
            logger.info("addRole",e);
            returnModel.setData("");
            returnModel.setMsg("角色修改失败！（请联系管理员）");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    /**
     * @Author 郭凯
     * @Description 查询权限设置回显数据
     * @Date 15:22 2020/5/15
     * @Param [sysRole]
     * @return com.hna.hka.archive.management.system.util.ReturnModel
     **/
    @RequestMapping("/getEchoZtree")
    @ResponseBody
    public ReturnModel getEchoZtree(@NotBlank(message = "角色ID不能为空")Long roleId){
        ReturnModel returnModel = new ReturnModel();
        try {
            List<SysResource> sysResource = sysRoleService.getEchoZtree(roleId);
            returnModel.setData(sysResource);
            returnModel.setMsg("角色权限查询成功！");
            returnModel.setState(Constant.STATE_SUCCESS);
            return returnModel;
        }catch (Exception e){
            logger.info("getEchoZtree",e);
            returnModel.setData("");
            returnModel.setMsg("角色权限查询失败！（请联系管理员）");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    /**
     * @Author 郭凯
     * @Description 权限设置提交
     * @Date 16:13 2020/5/15
     * @Param [roleId, resId]
     * @return com.hna.hka.archive.management.system.util.ReturnModel
     **/
    @RequestMapping("/addRoleResource")
    @ResponseBody
    public ReturnModel addRoleResource(@NotBlank(message = "角色ID不能为空")Long roleId,@NotBlank(message = "资源不能为空")String resIds){
        ReturnModel returnModel = null;
        try {
            returnModel = new ReturnModel();
            int i = sysRoleService.addRoleResource(roleId,resIds);
            if (i == 1){
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
            logger.info("addRoleResource",e);
            returnModel.setData("");
            returnModel.setMsg("权限设置失败！（请联系管理员）");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    /**
     * @Author 郭凯
     * @Description 获取权限分配用户
     * @Date 10:49 2020/5/19
     * @Param []
     * @return com.hna.hka.archive.management.system.util.ReturnModel
     **/
    @RequestMapping("/getScenicSpotRole")
    @ResponseBody
    public ReturnModel getScenicSpotRole(){
        ReturnModel returnModel = null;
        try {
            returnModel = new ReturnModel();
            List<SysRole> scenicSpotRoleList = sysRoleService.getScenicSpotRole();
            if (scenicSpotRoleList.size() > 0){
                returnModel.setData(scenicSpotRoleList);
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
            logger.info("getScenicSpotRole",e);
            returnModel.setData("");
            returnModel.setMsg("查询失败！（请联系管理员）");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }


    /**
     * 根据用户id获取用户所有角色
     * zhang
     * @param sysUsers
     * @return
     */
    @RequestMapping("/getUserRole")
    @ResponseBody
    public PageDataResult getUserRole(SysUsers sysUsers){
        PageDataResult pageDataResult = new PageDataResult();
        List<SysRole> roleList = sysRoleService.getUserRole(sysUsers.getUserId());
        if(roleList.size() != 0){
            PageInfo<SysRole> pageInfo = new PageInfo<>(roleList);
            pageDataResult.setList(pageInfo.getList());
            pageDataResult.setTotals((int) pageInfo.getTotal());
        }
        return pageDataResult;
    }

    /**
     * 根据用户id获取用户所有角色
     * zhang
     * @param sysUsers
     * @return
     */
    @RequestMapping("/getUserExamineRole")
    @ResponseBody
    public PageDataResult getUserExamineRole(SysUsers sysUsers){
        PageDataResult pageDataResult = new PageDataResult();
        List<SysUsersRoleSpot> usersRoleSpots = sysRoleService.getUserExamineRole(sysUsers.getUserId());
        if(usersRoleSpots.size() != 0){
            PageInfo<SysUsersRoleSpot> pageInfo = new PageInfo<>(usersRoleSpots);
            pageDataResult.setList(pageInfo.getList());
            pageDataResult.setTotals((int) pageInfo.getTotal());
        }
        return pageDataResult;
    }

//    /**
//     * 根据角色id获取所有权限
//     * zhang
//     * @param roleId
//     * @return
//     */
//    @RequestMapping("/getRoleRes")
//    @ResponseBody
//    public PageDataResult getRoleRes(Long roleId){
//        PageDataResult pageDataResult = new PageDataResult();
//        List<SysResource> roleList = sysRoleService.getRoleRes(roleId);
//        if(roleList.size() != 0){
//            PageInfo<SysResource> pageInfo = new PageInfo<>(roleList);
//            pageDataResult.setList(pageInfo.getList());
//            pageDataResult.setTotals((int) pageInfo.getTotal());
//        }
//        return pageDataResult;
//    }

}
