package com.hna.hka.archive.management.appSystem.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.hna.hka.archive.management.appSystem.model.SysRobotSoftAssetInformation;
import com.hna.hka.archive.management.appSystem.service.AppRobotSoftAssetInformationService;
import com.hna.hka.archive.management.assetsSystem.model.SysRobotBelarcAdvisor;
import com.hna.hka.archive.management.assetsSystem.service.SysRobotSoftwareVersionService;
import com.hna.hka.archive.management.system.util.AES;
import com.hna.hka.archive.management.system.util.Constant;
import com.hna.hka.archive.management.system.util.JsonUtils;
import com.hna.hka.archive.management.system.util.ReturnModel;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName：AppRobotSoftAssetInformationController
 * @Author: gouteng
 * @Date: 2022-12-29 13:37
 * @Description: 必须描述类做什么事情, 实现什么功能
 */
@Slf4j
@Controller
@RequestMapping("/appSystem/robotSoftAssetInformation")
public class AppRobotSoftAssetInformationController {

    @Autowired
    private AppRobotSoftAssetInformationService appRobotSoftAssetInformationService;
    private SysRobotSoftwareVersionService sysRobotSoftwareVersionService;
    /**
     * @Description: APP机器人资产信息,通过robotCode查询
     * @data:[content]
     * @return: java.lang.String
     * @Author: Sean
     * @Date: 2022-12-364 13:25:13
     */
    @RequestMapping("/getRobotSoftAssetInformationList")
    @ResponseBody
    public String getRobotSoftAssetInformationList(String content) {
        log.info("前端传回信息{}",content);
        ReturnModel dataModel = new ReturnModel();
        // 根据用户名查询当前登录用户
        try {
            if (content == null || "".equals(content)) {
                dataModel.setData("");
                dataModel.setMsg("加密参数不能为空！");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
            String decode = AES.decode(content);//参数加密
            //解密后进行JSON解析
            JSONObject jsonobject = JSON.parseObject(decode);
            //TokenId
            String longinTokenId = jsonobject.getString("longinTokenId");
            //判断longinTokenId是否为空，如果为空，直接return
            if (longinTokenId == "" || longinTokenId == null) {
                dataModel.setData("");
                dataModel.setMsg("TokenId为空，请传入TokenId!");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
//            机器人编号
            String robotCode = jsonobject.getString("robotCode");
            Map<String,Object> search = new HashMap<>();
            search.put("robotCode",robotCode);

            SysRobotSoftAssetInformation robotSoftAssetInformationList = appRobotSoftAssetInformationService.getRobotSoftAssetInformationList(search);

            dataModel.setData(robotSoftAssetInformationList);
            dataModel.setMsg("异常机器人列表查询成功!");
            dataModel.setState(Constant.STATE_SUCCESS);
            dataModel.setType(Constant.STATE_SUCCESS);
            String model = JSON.toJSONString(dataModel, SerializerFeature.WriteNullStringAsEmpty);
            return AES.encode(model);//加密返回
        } catch (AuthenticationException ae) {
            dataModel.setData("");
            dataModel.setMsg("未知原因,查询失败失败!");
            dataModel.setState(Constant.STATE_FAILURE);
            String model = JsonUtils.toString(dataModel);//对象转JSON
            return AES.encode(model);//加密返回
        }
    }

    /**
     * @Description: APP机器人软硬件信息,通过robotCode查询
     * @data:[content]
     * @return: java.lang.String
     * @Author: Sean
     * @Date: 2022-12-364 13:32:23
     */
    @RequestMapping("/getRobotBelarcAdvisorList")
    @ResponseBody
    public String getRobotSoftwareVersionList(String content) {
        ReturnModel dataModel = new ReturnModel();
        // 根据用户名查询当前登录用户
        try {
            if (content == null || "".equals(content)) {
                dataModel.setData("");
                dataModel.setMsg("加密参数不能为空！");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
            String decode = AES.decode(content);//参数加密
            //解密后进行JSON解析
            JSONObject jsonobject = JSON.parseObject(decode);

            //TokenId
            String longinTokenId = jsonobject.getString("longinTokenId");
            //判断longinTokenId是否为空，如果为空，直接return
            if (longinTokenId == "" || longinTokenId == null) {
                dataModel.setData("");
                dataModel.setMsg("TokenId为空，请传入TokenId!");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
//
            String robotCode = jsonobject.getString("robotCode");
//            景区id
            List<SysRobotBelarcAdvisor> sysRobotBelarcAdvisor = appRobotSoftAssetInformationService.getRobotSoftwareVersionList(robotCode);

            dataModel.setData(sysRobotBelarcAdvisor);
            dataModel.setMsg("列表查询成功!");
            dataModel.setState(Constant.STATE_SUCCESS);
            dataModel.setType(Constant.STATE_SUCCESS);
            String model = JSON.toJSONString(dataModel, SerializerFeature.WriteNullStringAsEmpty);
            return AES.encode(model);//加密返回
        } catch (AuthenticationException ae) {
            dataModel.setData("");
            dataModel.setMsg("未知原因,查询失败失败!");
            dataModel.setState(Constant.STATE_FAILURE);
            String model = JsonUtils.toString(dataModel);//对象转JSON
            return AES.encode(model);//加密返回
        }
    }


}
