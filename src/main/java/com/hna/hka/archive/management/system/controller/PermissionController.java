package com.hna.hka.archive.management.system.controller;

import com.hna.hka.archive.management.system.util.Constant;
import com.hna.hka.archive.management.system.util.ReturnModel;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *  判断操作权限控制层
 * @Author 郭凯
 * @Description
 * @Date 11:40 2020/5/12
 * @Param
 * @return
**/
@Controller
@RequestMapping("/system/permissionId")
public class PermissionController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 用户操作权限查询
     * @Author 郭凯
     * @Description
     * @Date 11:45 2020/5/12
     * @Param [permissionId]
     * @return com.hna.hka.archive.management.system.util.ReturnModel
    **/
    @RequestMapping("/getPermissionId")
    @ResponseBody
    public ReturnModel getPermissionId(String permissionId){
        ReturnModel returnModel = new ReturnModel();
        try {
            Subject subject = SecurityUtils.getSubject();
            if(subject.isPermitted(permissionId)){
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            }else{
                returnModel.setData("");
                returnModel.setMsg("对不起，您没有该操作权限！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        } catch (Exception e){
            logger.info("getPermissionId",e);
        }
        return returnModel;
    }

}
