package com.hna.hka.archive.management.appSystem.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.hna.hka.archive.management.appSystem.service.AppUserService;
import com.hna.hka.archive.management.assetsSystem.model.FactorySend;
import com.hna.hka.archive.management.assetsSystem.model.GoodsStock;
import com.hna.hka.archive.management.assetsSystem.service.FactorySendService;
import com.hna.hka.archive.management.assetsSystem.service.GoodsStockService;
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
 *
 * 管理者app，景区配件库存
 * @Description: 必须描述类做什么事情, 实现什么功能
 */
@Slf4j
@Controller
@RequestMapping("/appSystem/goods_stock")
public class AppSysGoodsStockController {

    @Autowired
    GoodsStockService goodsStockService;
    @Autowired
    private AppUserService appUserService;


    @ApiOperation("景区配件列表")
    @GetMapping("/getGoodsStockList")
    @ResponseBody
//    Long spotId, String beginDate, String endDate, Long userId, Integer type
    public String getGoodsStockList(String content) {
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
            String pageNum = jsonobject.getString("pageNum");
            String pageSize = jsonobject.getString("pageSize");
////            Long userId = appUsers.getUserId();
//            Long userId = jsonobject.getLong("userId");
//            Integer type = Integer.valueOf(jsonobject.getString("type"));
//            Integer form = jsonobject.getInteger("form");

            PageDataResult pageDataResult =  goodsStockService.getGoodsStockList(spotId,Integer.parseInt(pageNum),Integer.parseInt(pageSize));

            dataModel.setData(pageDataResult);
            dataModel.setMsg("success");
            dataModel.setState(Constant.STATE_SUCCESS);
            dataModel.setType(Constant.STATE_SUCCESS);
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
//            FactorySend list = goodsStockService.detail(id);
//            dataModel.setData(list);
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
