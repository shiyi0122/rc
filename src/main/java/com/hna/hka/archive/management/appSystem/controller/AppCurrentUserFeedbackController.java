package com.hna.hka.archive.management.appSystem.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.github.pagehelper.PageInfo;
import com.hna.hka.archive.management.appSystem.service.AppUserService;
import com.hna.hka.archive.management.assetsSystem.model.SysCurrentUserFeedback;
import com.hna.hka.archive.management.assetsSystem.service.SysCurrentUserFeedbackService;
import com.hna.hka.archive.management.managerApp.model.SysAppUsers;
import com.hna.hka.archive.management.system.util.Constant;
import com.hna.hka.archive.management.system.util.PublicUtil;
import com.hna.hka.archive.management.system.util.ReturnModel;
import com.hna.hka.archive.management.wenYuRiverInterface.model.BaseQueryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * @ProjectName: rc
 * @Package: com.hna.hka.archive.management.appSystem.controller
 * @ClassName: AppCurrentUserFeedbackController
 * @Author: 郭凯
 * @Description: 小程序上报故障记录控制层
 * @Date: 2021/6/27 17:37
 * @Version: 1.0
 */
@RequestMapping("/system/appCurrentUserFeedback")
@Controller
public class AppCurrentUserFeedbackController extends PublicUtil {

    @Autowired
    private AppUserService appUserService;

    @Autowired
    private SysCurrentUserFeedbackService sysCurrentUserFeedbackService;

    /**
     * @Method getCurrentUserFeedbackAppList
     * @Author 郭凯
     * @Version  1.0
     * @Description 查询小程序上报故障列表
     * @Return java.lang.String
     * @Date 2021/6/27 17:40
     */
    @RequestMapping("/getCurrentUserFeedbackAppList")
    @ResponseBody
    public String getCurrentUserFeedbackAppList(@RequestParam("longinTokenId") String longinTokenId, SysCurrentUserFeedback sysCurrentUserFeedback, BaseQueryVo BaseQueryVo){
        ReturnModel returnModel = new ReturnModel();
        try {
            //查询longinTokenId，是否一致
            SysAppUsers appUsers = appUserService.getSysUserBylonginTokenId(longinTokenId);
            if (appUsers == null) {
                returnModel.setData("");
                returnModel.setMsg("TokenId已过期，请重新登陆!");
                returnModel.setState(Constant.STATE_FAILURE);
                returnModel.setType(Constant.STATE_FAILURE);
                String model = JSON.toJSONString(returnModel, SerializerFeature.WriteNullStringAsEmpty);
                return model;
            }
            Map<String,String> search = new HashMap<>();
            search.put("robotCode",sysCurrentUserFeedback.getRobotCode());
            search.put("scenicSpotId",String.valueOf(sysCurrentUserFeedback.getScenicSpotId()));
            search.put("phone",sysCurrentUserFeedback.getPhone());
            BaseQueryVo.setSearch(search);
            PageInfo<SysCurrentUserFeedback> page = sysCurrentUserFeedbackService.getRobotDamagesAppList(BaseQueryVo);
            returnModel.setData(page);
            returnModel.setMsg("查询成功！");
            returnModel.setState(Constant.STATE_SUCCESS);
            returnModel.setType(Constant.STATE_SUCCESS);
            String model = JSON.toJSONString(returnModel, SerializerFeature.WriteNullStringAsEmpty);
            return model;
        }catch (Exception e){
            logger.info("getCurrentUserFeedbackAppList",e);
            returnModel.setData("");
            returnModel.setMsg("系统错误！");
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setType(Constant.STATE_FAILURE);
            String model = JSON.toJSONString(returnModel, SerializerFeature.WriteNullStringAsEmpty);
            return model;
        }
    }

    /**
     * @Method editCurrentUserFeedbackApp
     * @Author 郭凯
     * @Version  1.0
     * @Description 编辑小程序上报故障
     * @Return com.hna.hka.archive.management.system.util.ReturnModel
     * @Date 2021/6/27 18:07
     */
    @RequestMapping("/editCurrentUserFeedbackApp")
    @ResponseBody
    public ReturnModel editCurrentUserFeedbackApp(@RequestParam("longinTokenId") String longinTokenId, SysCurrentUserFeedback sysCurrentUserFeedback){
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
            int i = sysCurrentUserFeedbackService.editCurrentUserFeedbackApp(sysCurrentUserFeedback);
            if (i == 1){
                returnModel.setData("");
                returnModel.setMsg("修改成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
                returnModel.setType(Constant.STATE_SUCCESS);
                return returnModel;
            }else{
                returnModel.setData("");
                returnModel.setMsg("修改失败！");
                returnModel.setState(Constant.STATE_FAILURE);
                returnModel.setType(Constant.STATE_FAILURE);
                return returnModel;
            }
        }catch (Exception e){
            logger.info("editCurrentUserFeedbackApp",e);
            returnModel.setData("");
            returnModel.setMsg("系统错误！");
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setType(Constant.STATE_FAILURE);
            return returnModel;
        }
    }
}
