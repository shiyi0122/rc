package com.hna.hka.archive.management.appSystem.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.hna.hka.archive.management.appSystem.service.AppUserService;
import com.hna.hka.archive.management.assetsSystem.model.FactorySend;
import com.hna.hka.archive.management.assetsSystem.model.SysRobotAccessoriesApplication;
import com.hna.hka.archive.management.assetsSystem.service.FactorySendService;
import com.hna.hka.archive.management.managerApp.model.SysAppUsers;
import com.hna.hka.archive.management.system.model.SysRobot;
import com.hna.hka.archive.management.system.util.*;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @ClassName：AppFactorySend
 * @Author: gouteng
 * @Date: 2023-01-04 11:00
 * @Description: 必须描述类做什么事情, 实现什么功能
 */
@Slf4j
@Controller
@RequestMapping("/appSystem/factory_send")
public class AppFactorySendController {

    @Autowired
    private FactorySendService service;
    @Autowired
    private AppUserService appUserService;
    /**
     * @Description: 添加工厂发货
     * @data:[content]
     * @return: java.lang.String
     * @Author: 自定义作者名称
     * @Date: 2023-01-04 11:16:23
     */
    @PostMapping("/add")
    public String add(String content, FactorySend factorySend, HttpServletRequest request) {
        log.info("前端传回信息{}", content);
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
            String loginName = (String) request.getSession().getAttribute("loginName");
            Integer count = service.add(factorySend);
            if (count > 0) {
                if (factorySend.getForm() == 1) {
                    WeChatGtRobotAppPush.singlePushApp(service.getPId(factorySend.getConsignorId()), "工厂发货提醒", "工厂发货," + factorySend.getFactoryName() + "-" + factorySend.getReceivingName() + ",发出" + factorySend.getRobotCount() + "台,请尽快安排发货");
                } else if (factorySend.getForm() == 2) {
                    WeChatGtRobotAppPush.singlePushApp(service.getPId(factorySend.getConsignorId()), "设备调运提醒", "设备调运," + factorySend.getFactoryName() + "-" + factorySend.getReceivingName() + ",发出" + factorySend.getRobotCount() + "台,请尽快安排发货");
                }
            }
            dataModel.setData(count);
            dataModel.setMsg("添加发货工厂成功!");
            dataModel.setState(Constant.STATE_SUCCESS);
            dataModel.setType(Constant.STATE_SUCCESS);
            String model = JSON.toJSONString(dataModel, SerializerFeature.WriteNullStringAsEmpty);
            return AES.encode(model);//加密返回
        } catch (Exception ae) {
            dataModel.setData("");
            dataModel.setMsg("添加发货工厂失败!");
            dataModel.setState(Constant.STATE_FAILURE);
            String model = JsonUtils.toString(dataModel);//对象转JSON
            return AES.encode(model);//加密返回
        }
    }

    @ApiOperation("设备调运列表")
    @GetMapping("/sendList")
    @ResponseBody
//    Long spotId, String beginDate, String endDate, Long userId, Integer type
    public String sendList(String content) {
        log.info("前端传回信息{}", content);
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
            String longinTokenId = jsonobject.getString("longinTokenId");
            //判断longinTokenId是否为空，如果为空，直接return
            if (ToolUtil.isEmpty(longinTokenId)) {
                dataModel.setData("");
                dataModel.setMsg("TokenId为空，请传入TokenId!");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
            //TokenId
            SysAppUsers appUsers = appUserService.getSysUserBylonginTokenId(longinTokenId);
            if (ToolUtil.isEmpty(appUsers)) {
                dataModel.setData("");
                dataModel.setMsg("TokenId已过期，请重新登陆!");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
            Long spotId = jsonobject.getLong("spotId");
            String beginDate = jsonobject.getString("beginDate");
            String endDate = jsonobject.getString("endDate");
//            Long userId = appUsers.getUserId();
            Long userId = jsonobject.getLong("userId");
            Integer type = Integer.valueOf(jsonobject.getString("type"));
            Integer form = jsonobject.getInteger("form");

            List<FactorySend> list = service.list(spotId, beginDate, endDate, userId, type, form);
            Integer count = service.getCount(spotId, beginDate, endDate, userId, type, form);

            dataModel.setData(list);
            dataModel.setMsg("success");
            dataModel.setState(Constant.STATE_SUCCESS);
            dataModel.setType(Constant.STATE_SUCCESS);
            dataModel.setTotal(count);
            String model = JSON.toJSONString(dataModel, SerializerFeature.WriteNullStringAsEmpty);
            return AES.encode(model);//加密返回
        } catch (AuthenticationException ae) {
            dataModel.setData("");
            dataModel.setMsg("未知原因,查询失败!");
            dataModel.setState(Constant.STATE_FAILURE);
            String model = JsonUtils.toString(dataModel);//对象转JSON
            return AES.encode(model);//加密返回
        }
    }

    @ApiOperation("设备调运列表详情")
    @GetMapping("/detail")
    @ResponseBody
//    传值 主键id
    public String detail(String content) {
        log.info("前端传回信息{}", content);
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
            Long id = jsonobject.getLong("id");
            FactorySend list = service.detail(id);
            dataModel.setData(list);
            dataModel.setMsg("success");
            dataModel.setState(Constant.STATE_SUCCESS);
            dataModel.setType(Constant.STATE_SUCCESS);
//            String s = JsonUtils.toString(dataModel);
            String model = JSON.toJSONString(dataModel, SerializerFeature.WriteNullStringAsEmpty);
            return AES.encode(model );//加密返回
        } catch (AuthenticationException ae) {
            dataModel.setData("");
            dataModel.setMsg("未知原因,查询失败!");
            dataModel.setState(Constant.STATE_FAILURE);
            String model = JsonUtils.toString(dataModel);//对象转JSON
            return AES.encode(model);//加密返回
        }
    }

    @ApiOperation("修改设备调运状态")
    @GetMapping("/upType")
    @ResponseBody
//    这里接收前端传的主键id和要修改的状态type
    public String upType(String content,FactorySend factorySend) {
        log.info("前端传回信息{}", content);
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
//            Long id = jsonobject.getLong("id");
//            Long type = jsonobject.getLong("type");
            int i = service.upType(factorySend);
            if (i==1){
                dataModel.setData(i);
                dataModel.setMsg("success");
                dataModel.setState(Constant.STATE_SUCCESS);
                dataModel.setType(Constant.STATE_SUCCESS);
                String model = JSON.toJSONString(dataModel, SerializerFeature.WriteNullStringAsEmpty);
                return AES.encode(model);//加密返回
            }else {
                dataModel.setData(i);
                dataModel.setMsg("error");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JSON.toJSONString(dataModel, SerializerFeature.WriteNullStringAsEmpty);
                return AES.encode(model);//加密返回
            }

        } catch (Exception ae) {
            ae.printStackTrace();
            dataModel.setData("");
            dataModel.setMsg("未知原因,查询失败!");
            dataModel.setState(Constant.STATE_FAILURE);
            String model = JsonUtils.toString(dataModel);//对象转JSON
            return AES.encode(model);//加密返回
        }
    }



    @ApiOperation("根据景区id获取机器人列表")
    @GetMapping("/getSpotIdByRobotList")
    @ResponseBody
//    传值 主键id
    public String getSpotIdByRobotList(String content,String spotId) {
        log.info("前端传回信息{}", content);
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
//            Long id = jsonobject.getLong("id");
            List<SysRobot> list = service.getSpotIdByRobotList(spotId);
            dataModel.setData(list);
            dataModel.setMsg("success");
            dataModel.setState(Constant.STATE_SUCCESS);
            dataModel.setType(Constant.STATE_SUCCESS);
//            String s = JsonUtils.toString(dataModel);
            String model = JSON.toJSONString(dataModel, SerializerFeature.WriteNullStringAsEmpty);
            return AES.encode(model );//加密返回
        } catch (AuthenticationException ae) {
            dataModel.setData("");
            dataModel.setMsg("未知原因,查询失败!");
            dataModel.setState(Constant.STATE_FAILURE);
            String model = JsonUtils.toString(dataModel);//对象转JSON
            return AES.encode(model);//加密返回
        }
    }






}
