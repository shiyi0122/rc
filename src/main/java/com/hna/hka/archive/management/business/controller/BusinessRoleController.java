package com.hna.hka.archive.management.business.controller;

import com.hna.hka.archive.management.business.model.BusinessRole;
import com.hna.hka.archive.management.business.service.BusinessRoleService;
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
import java.util.Map;

/**
 * @ProjectName: IDEA-CODE
 * @Package: com.hna.hka.archive.management.business.controller
 * @ClassName: BusinessRoleController
 * @Author: 郭凯
 * @Description: 招商平台角色管理控制层
 * @Date: 2020/10/12 14:04
 * @Version: 1.0
 */
@RequestMapping("/business/role")
@Controller
public class BusinessRoleController extends PublicUtil {

    @Autowired
    private BusinessRoleService businessRoleService;


    /**
     * @Author 郭凯
     * @Description 角色管理列表查询
     * @Date 14:07 2020/10/12
     * @Param [pageNum, pageSize, businessRole]
     * @return com.hna.hka.archive.management.system.util.PageDataResult
    **/
    @RequestMapping("/getBusinessRoleList")
    @ResponseBody
    public PageDataResult getBusinessRoleList(@RequestParam("pageNum") Integer pageNum,
                                         @RequestParam("pageSize") Integer pageSize, BusinessRole businessRole) {
        PageDataResult pageDataResult = new PageDataResult();
        Map<String,String> search = new HashMap<>();
        try {
            if(null == pageNum) {
                pageNum = 1;
            }
            if(null == pageSize) {
                pageSize = 10;
            }
            pageDataResult = businessRoleService.getBusinessRoleList(pageNum,pageSize,search);
        }catch (Exception e){
            logger.info("角色管理列表查询失败",e);
        }
        return pageDataResult;
    }

    /**
     * @Author 郭凯
     * @Description 角色删除
     * @Date 16:25 2020/10/12
     * @Param [roleId]
     * @return com.hna.hka.archive.management.system.util.ReturnModel
    **/
    @RequestMapping("/delRole")
    @ResponseBody
    public ReturnModel delRole(@RequestParam("roleId") Long roleId){
        ReturnModel returnModel = new ReturnModel();
        try {
            int i = businessRoleService.delRole(roleId);
            if (i == 1){
                returnModel.setData("");
                returnModel.setMsg("角色删除成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            }else{
                returnModel.setData("");
                returnModel.setMsg("角色删除失败！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        }catch (Exception e){
            logger.info("delRole",e);
            returnModel.setData("");
            returnModel.setMsg("角色删除失败！(请联系管理员)");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

}
