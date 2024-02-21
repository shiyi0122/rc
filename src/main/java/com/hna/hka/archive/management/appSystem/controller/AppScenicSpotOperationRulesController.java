package com.hna.hka.archive.management.appSystem.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.github.pagehelper.PageInfo;
import com.hna.hka.archive.management.appSystem.service.AppUserService;
import com.hna.hka.archive.management.assetsSystem.model.SysScenicSpotOperationRules;
import com.hna.hka.archive.management.assetsSystem.service.SysScenicSpotOperationRulesService;
import com.hna.hka.archive.management.managerApp.model.SysAppUsers;
import com.hna.hka.archive.management.system.model.SysRobotFaule;
import com.hna.hka.archive.management.system.util.Constant;
import com.hna.hka.archive.management.system.util.PublicUtil;
import com.hna.hka.archive.management.system.util.ReturnModel;
import com.hna.hka.archive.management.system.util.ToolUtil;
import com.hna.hka.archive.management.wenYuRiverInterface.model.BaseQueryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ProjectName: rc
 * @Package: com.hna.hka.archive.management.appSystem.controller
 * @ClassName: AppScenicSpotOperationRulesController
 * @Author: 郭凯
 * @Description: APP景区运营规则控制层
 * @Date: 2021/6/27 15:43
 * @Version: 1.0
 */
@RequestMapping("/system/appScenicSpotOperationRules")
@Controller
public class AppScenicSpotOperationRulesController extends PublicUtil {

    @Autowired
    private AppUserService appUserService;

    @Autowired
    private SysScenicSpotOperationRulesService sysScenicSpotOperationRulesService;


    /**
     * @Method getOperationRulesByScenicSpotId
     * @Author 郭凯
     * @Version  1.0
     * @Description 景区运营规则列表查询
     * @Return java.lang.String
     * @Date 2021/6/27 16:01
     */
    @RequestMapping("/getOperationRulesList")
    @ResponseBody
    public String getOperationRulesList( @RequestParam("longinTokenId") String longinTokenId, SysScenicSpotOperationRules scenicSpotOperationRules){
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
            search.put("scenicSpotId",String.valueOf(scenicSpotOperationRules.getScenicSpotId()));
            search.put("time",scenicSpotOperationRules.getDate());
            List<SysScenicSpotOperationRules> page = sysScenicSpotOperationRulesService.getOperationRulesList(search);
            returnModel.setData(page);
            returnModel.setMsg("查询成功");
            returnModel.setState(Constant.STATE_SUCCESS);
            returnModel.setType(Constant.STATE_SUCCESS);
            String model = JSON.toJSONString(returnModel, SerializerFeature.WriteNullStringAsEmpty);
            return model;
        }catch (Exception e){
            logger.info("getOperationRulesList",e);
            returnModel.setData("");
            returnModel.setMsg("系统错误");
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setType(Constant.STATE_FAILURE);
            String model = JSON.toJSONString(returnModel, SerializerFeature.WriteNullStringAsEmpty);
            return model;
        }
    }

    /**
     * @Method editOperationRules
     * @Author 郭凯
     * @Version  1.0
     * @Description 编辑景区规则
     * @Return com.hna.hka.archive.management.system.util.ReturnModel
     * @Date 2021/6/27 16:28
     */
    @RequestMapping("/editOperationRules")
    @ResponseBody
    public ReturnModel editOperationRules(@RequestParam("longinTokenId") String longinTokenId, SysScenicSpotOperationRules scenicSpotOperationRules){
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
            //编辑
            int i = sysScenicSpotOperationRulesService.editOperationRules(scenicSpotOperationRules);
            if (i == 1){
                returnModel.setData("");
                returnModel.setMsg("修改成功!");
                returnModel.setState(Constant.STATE_SUCCESS);
                returnModel.setType(Constant.STATE_SUCCESS);
                return returnModel;
            }else{
                returnModel.setData("");
                returnModel.setMsg("修改失败!");
                returnModel.setState(Constant.STATE_FAILURE);
                returnModel.setType(Constant.STATE_FAILURE);
                return returnModel;
            }
        }catch (Exception e){
            logger.info("editOperationRules",e);
            returnModel.setData("");
            returnModel.setMsg("修改失败!");
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setType(Constant.STATE_FAILURE);
            return returnModel;
        }
    }


}
