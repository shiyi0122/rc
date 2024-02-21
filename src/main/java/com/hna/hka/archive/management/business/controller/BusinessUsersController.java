package com.hna.hka.archive.management.business.controller;

import com.hna.hka.archive.management.business.model.*;
import com.hna.hka.archive.management.business.service.BusinessUsersService;
import com.hna.hka.archive.management.system.util.Constant;
import com.hna.hka.archive.management.system.util.PageDataResult;
import com.hna.hka.archive.management.system.util.PublicUtil;
import com.hna.hka.archive.management.system.util.ReturnModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * @ProjectName: IDEA-CODE
 * @Package: com.hna.hka.archive.management.business.controller
 * @ClassName: BusinessUsersController
 * @Author: 郭凯
 * @Description: 招商系统用户管理控制层
 * @Date: 2020/6/19 13:02
 * @Version: 1.0
 */
@RequestMapping("/business/businessUsers")
@Controller
public class BusinessUsersController extends PublicUtil {

    @Autowired
    private BusinessUsersService businessUsersService;
    
    @Autowired
    private HttpServletRequest request;

    /**
     * @Author 郭凯
     * @Description 招商系统用户管理列表查询
     * @Date 13:07 2020/6/19
     * @Param [pageNum, pageSize, businessUsers]
     * @return com.hna.hka.archive.management.system.util.PageDataResult
    **/
    @RequestMapping("/getBusinessUsersList")
    @ResponseBody
    public PageDataResult getBusinessUsersList(@RequestParam("pageNum") Integer pageNum,
                                             @RequestParam("pageSize") Integer pageSize, BusinessUsers businessUsers) {
        PageDataResult pageDataResult = new PageDataResult();
        Map<String,String> search = new HashMap<>();
        try {
            if(null == pageNum) {
                pageNum = 1;
            }
            if(null == pageSize) {
                pageSize = 10;
            }
            search.put("phone",businessUsers.getPhone());
            search.put("userName",businessUsers.getUserName());
            search.put("userType",businessUsers.getUserType());
            search.put("priceMin",request.getParameter("priceMin"));
            search.put("priceMax",request.getParameter("priceMax"));
            pageDataResult = businessUsersService.getBusinessUsersList(pageNum,pageSize,search);
        }catch (Exception e){
            logger.info("招商系统用户管理列表查询失败",e);
        }
        return pageDataResult;
    }

    /**
     * @Author 郭凯
     * @Description 查询拓展景区列表
     * @Date 15:16 2020/11/26
     * @Param []
     * @return com.hna.hka.archive.management.system.util.ReturnModel
    **/
    @RequestMapping("/getScenicSpotExpandList")
    @ResponseBody
    public ReturnModel getScenicSpotExpandList(Long userId){
        ReturnModel returnModel = new ReturnModel();
        try {
            List<BusinessScenicSpotExpand> businessScenicSpotExpandList = businessUsersService.getScenicSpotExpandList(userId);
            returnModel.setData(businessScenicSpotExpandList);
            returnModel.setMsg("查询成功！");
            returnModel.setState(Constant.STATE_SUCCESS);
            return returnModel;
        }catch (Exception e){
            returnModel.setData("");
            returnModel.setMsg("查询失败！");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    /**
     * @Author 郭凯
     * @Description 分配景区
     * @Date 15:32 2020/11/26
     * @Param [businessUsers]
     * @return com.hna.hka.archive.management.system.util.ReturnModel
    **/
    @RequestMapping("/addAllocateScenicSpot")
    @ResponseBody
    public ReturnModel addAllocateScenicSpot(BusinessUsersScenicSpot BusinessUsersScenicSpot){
        ReturnModel returnModel = new ReturnModel();
        try {
            int i = businessUsersService.addAllocateScenicSpot(BusinessUsersScenicSpot);
            if (i == 1){
                returnModel.setData("");
                returnModel.setMsg("用户景区分配成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            }else{
                returnModel.setData("");
                returnModel.setMsg("用户景区分配失败！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        }catch (Exception e){
            logger.info("addAllocateScenicSpot",e);
            returnModel.setData("");
            returnModel.setMsg("用户景区分配失败！（请联系管理员）");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }


    /**
     * @Author 郭凯
     * @Description 分配景区
     * @Date 15:32 2020/11/26
     * @Param [businessUsers]
     * @return com.hna.hka.archive.management.system.util.ReturnModel
     **/
    @RequestMapping("/addAllocateScenicSpotList")
    @ResponseBody
    public ReturnModel addAllocateScenicSpotList(BusinessUsersScenicSpot BusinessUsersScenicSpot){
        ReturnModel returnModel = new ReturnModel();
        try {
            int i = businessUsersService.addAllocateScenicSpotList(BusinessUsersScenicSpot);
            if (i == 1){
                returnModel.setData("");
                returnModel.setMsg("用户景区分配成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            }else{
                returnModel.setData("");
                returnModel.setMsg("用户景区分配失败！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        }catch (Exception e){
            logger.info("addAllocateScenicSpot",e);
            returnModel.setData("");
            returnModel.setMsg("用户景区分配失败！（请联系管理员）");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }


    /**
     * @Author 郭凯
     * @Description 查询角色
     * @Date 9:37 2020/11/27
     * @Param []
     * @return com.hna.hka.archive.management.system.util.ReturnModel
    **/
    @RequestMapping("/getRoleList")
    @ResponseBody
    public ReturnModel getRoleList(){
        ReturnModel returnModel = new ReturnModel();
        try {
            List<BusinessRole> businessRoleList = businessUsersService.getRoleList();
            returnModel.setData(businessRoleList);
            returnModel.setMsg("查询成功！");
            returnModel.setState(Constant.STATE_SUCCESS);
            return returnModel;
        }catch (Exception e){
            returnModel.setData("");
            returnModel.setMsg("查询失败！");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    /**
     * @Author 郭凯
     * @Description 查询用户拥有的角色
     * @Date 9:53 2020/11/27
     * @Param [businessUsersRole]
     * @return com.hna.hka.archive.management.system.util.ReturnModel
    **/
    @RequestMapping("/getBusinessUsersByUserId")
    @ResponseBody
    public ReturnModel getBusinessUsersByUserId(BusinessUsersRole businessUsersRole){
        ReturnModel returnModel = new ReturnModel();
        try {
            BusinessUsersRole usersRole = businessUsersService.getBusinessUsersByUserId(businessUsersRole.getUserId());
            returnModel.setData(usersRole);
            returnModel.setMsg("查询成功！");
            returnModel.setState(Constant.STATE_SUCCESS);
            return returnModel;
        }catch (Exception e){
            returnModel.setData("");
            returnModel.setMsg("查询失败！");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    /**
     * @Author 郭凯
     * @Description 用户角色分配
     * @Date 10:16 2020/11/27
     * @Param [businessUsersRole]
     * @return com.hna.hka.archive.management.system.util.ReturnModel
    **/
    @RequestMapping("/addAllocateRole")
    @ResponseBody
    public ReturnModel addAllocateRole(BusinessUsersRole businessUsersRole){
        ReturnModel returnModel = new ReturnModel();
        try {
            int i = businessUsersService.addAllocateRole(businessUsersRole);
            if (i == 1){
                returnModel.setData("");
                returnModel.setMsg("用户角色分配成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            }else{
                returnModel.setData("");
                returnModel.setMsg("用户角色分配失败！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        }catch (Exception e){
            logger.info("addAllocateRole",e);
            returnModel.setData("");
            returnModel.setMsg("用户角色分配失败！（请联系管理员）");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    /**
     * @Author 郭凯
     * @Description 查询用户绑定景区列表
     * @Date 9:59 2020/12/1
     * @Param [pageNum, pageSize, businessUsersScenicSpot]
     * @return com.hna.hka.archive.management.system.util.PageDataResult
    **/
    @RequestMapping("/getBusinessUsersScenicSpotsList")
    @ResponseBody
    public PageDataResult getBusinessUsersScenicSpotsList(@RequestParam("pageNum") Integer pageNum,
                                               @RequestParam("pageSize") Integer pageSize, BusinessUsersScenicSpot businessUsersScenicSpot) {
        PageDataResult pageDataResult = new PageDataResult();
        Map<String,Object> search = new HashMap<>();
        try {
            if(null == pageNum) {
                pageNum = 1;
            }
            if(null == pageSize) {
                pageSize = 10;
            }
            search.put("userId",businessUsersScenicSpot.getUserId());
            search.put("scenicSpotName",businessUsersScenicSpot.getScenicSpotName());
            pageDataResult = businessUsersService.getBusinessUsersScenicSpotsList(pageNum,pageSize,search);
        }catch (Exception e){
            logger.info("用户绑定景区列表查询失败",e);
        }
        return pageDataResult;
    }

    /**
     * @Author 郭凯
     * @Description 修改用户景区
     * @Date 11:13 2020/12/1
     * @Param [BusinessUsersScenicSpot]
     * @return com.hna.hka.archive.management.system.util.ReturnModel
    **/
    @RequestMapping("/editAllocateScenicSpot")
    @ResponseBody
    public ReturnModel editAllocateScenicSpot(BusinessUsersScenicSpot BusinessUsersScenicSpot){
        ReturnModel returnModel = new ReturnModel();
        try {
            int i = businessUsersService.editAllocateScenicSpot(BusinessUsersScenicSpot);
            if (i == 1){
                returnModel.setData("");
                returnModel.setMsg("用户景区修改成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            }else{
                returnModel.setData("");
                returnModel.setMsg("用户景区修改失败！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        }catch (Exception e){
            logger.info("editAllocateScenicSpot",e);
            returnModel.setData("");
            returnModel.setMsg("用户景区修改失败！（请联系管理员）");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    /**
     * @Author 郭凯
     * @Description 用户解绑景区
     * @Date 11:18 2020/12/1
     * @Param [id]
     * @return com.hna.hka.archive.management.system.util.ReturnModel
    **/
    @RequestMapping("/delBusinessUsersScenicSpot")
    @ResponseBody
    public ReturnModel delBusinessUsersScenicSpot(@RequestParam Long id){
        ReturnModel returnModel = new ReturnModel();
        try {
            int i = businessUsersService.delBusinessUsersScenicSpot(id);
            if (i == 1){
                returnModel.setData("");
                returnModel.setMsg("用户景区解绑成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            }else{
                returnModel.setData("");
                returnModel.setMsg("用户景区解绑失败！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        }catch (Exception e){
            logger.info("delBusinessUsersScenicSpot",e);
            returnModel.setData("");
            returnModel.setMsg("用户景区解绑失败！（请联系管理员）");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    /**
     * 修改用户报备审核权限
     * @param
     * @return
     */
    @RequestMapping("/editBusinessUsersFilling")
    @ResponseBody
    public ReturnModel editBusinessUsersFilling(BusinessUsersRole businessUsersRole){

        Long userId = businessUsersRole.getUserId();
        Long roleId = businessUsersRole.getRoleId();

        ReturnModel returnModel = new ReturnModel();
        int i = businessUsersService.editBusinessUsersFilling(userId,roleId);
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


    }

    @RequestMapping("/getBusinessUsersFilingMessage")
    @ResponseBody
    public ReturnModel getBusinessUsersFilingMessage(String userId) {

        ReturnModel returnModel = new ReturnModel();

        BusinessUsers businessUsers =  businessUsersService.getBusinessUsersFilingMessage(userId);

        returnModel.setData(businessUsers);
        returnModel.setState(Constant.STATE_SUCCESS);
        returnModel.setMsg("获取成功");

        return returnModel;
    }


    /**
     * 修改用户押金查询权限
     * @param
     * @return
     */
    @RequestMapping("/editBusinessUsersDeposit")
    @ResponseBody
    public ReturnModel editBusinessUsersDeposit(BusinessUsersRole businessUsersRole){

        Long userId = businessUsersRole.getUserId();
        Long roleId = businessUsersRole.getRoleId();

        ReturnModel returnModel = new ReturnModel();
        int i = businessUsersService.editBusinessUsersDeposit(userId,roleId);
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


    }









}
