package com.hna.hka.archive.management.appSystem.controller;

import com.hna.hka.archive.management.appSystem.service.AppUserService;
import com.hna.hka.archive.management.assetsSystem.model.SysInvoice;
import com.hna.hka.archive.management.assetsSystem.service.SysInvoiceService;
import com.hna.hka.archive.management.managerApp.model.SysAppUsers;
import com.hna.hka.archive.management.system.util.Constant;
import com.hna.hka.archive.management.system.util.PublicUtil;
import com.hna.hka.archive.management.system.util.ReturnModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @ProjectName: rc
 * @Package: com.hna.hka.archive.management.appSystem.controller
 * @ClassName: AppRobotInvoiceController
 * @Author: 郭凯
 * @Description: APP发票申请控制层
 * @Date: 2021/6/25 19:21
 * @Version: 1.0
 */
@RequestMapping("/system/appRobotInvoice")
@Controller
public class AppRobotInvoiceController extends PublicUtil {

    @Autowired
    private AppUserService appUserService;

    @Autowired
    private SysInvoiceService sysInvoiceService;

    /**
     * @Method robotErrorRecordApproval
     * @Author 郭凯
     * @Version  1.0
     * @Description 上传发票申请
     * @Return com.hna.hka.archive.management.system.util.ReturnModel
     * @Date 2021/6/25 19:25
     */
    @RequestMapping("/addInvoice")
    @ResponseBody
    public ReturnModel addInvoice(@RequestParam("longinTokenId") String longinTokenId, SysInvoice sysInvoice){
        ReturnModel returnModel = new ReturnModel();
        try {
            //查询longinTokenId，是否一致
            SysAppUsers appUsers = appUserService.getSysUserBylonginTokenId(longinTokenId);
            if (appUsers == null) {
                returnModel.setData("");
                returnModel.setMsg("TokenId已过期，请重新登陆!");
                returnModel.setState(Constant.STATE_FAILURE);
                returnModel.setType(Constant.STATE_FAILURE);
                return returnModel;
            }
            int i = sysInvoiceService.addInvoice(sysInvoice);
            if (i == 1){
                returnModel.setData("");
                returnModel.setMsg("新增成功!");
                returnModel.setState(Constant.STATE_SUCCESS);
                returnModel.setType(Constant.STATE_SUCCESS);
                return returnModel;
            } else if(i == 2){
                returnModel.setData("");
                returnModel.setMsg("重复提交!");
                returnModel.setState(Constant.STATE_FAILURE);
                returnModel.setType(Constant.STATE_FAILURE);
                return returnModel;
            }else{
                returnModel.setData("");
                returnModel.setMsg("新增失败!");
                returnModel.setState(Constant.STATE_FAILURE);
                returnModel.setType(Constant.STATE_FAILURE);
                return returnModel;
            }
        }catch (Exception e){
            logger.info("addInvoice",e);
            returnModel.setData("");
            returnModel.setMsg("系统错误!");
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setType(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

}
