package com.hna.hka.archive.management.appSystem.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.github.pagehelper.PageInfo;
import com.hna.hka.archive.management.appSystem.dao.AppRobotGpsDispatchLogMapper;
import com.hna.hka.archive.management.appSystem.model.AppRobotGpsDispatchLog;
import com.hna.hka.archive.management.appSystem.model.SysAppOrder;
import com.hna.hka.archive.management.appSystem.service.AppRobotGpsDispatchLogService;
import com.hna.hka.archive.management.appSystem.service.AppUserService;
import com.hna.hka.archive.management.managerApp.model.SysAppUsers;
import com.hna.hka.archive.management.system.util.*;
import com.hna.hka.archive.management.wenYuRiverInterface.model.BaseQueryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2023/5/25 17:40
 *
 * 机器人坐标更新时间接口
 */
@RequestMapping("/system/appRobotGpsDispatchLog")
@Controller
public class AppRobotGpsDispatchLogController extends PublicUtil {

    @Autowired
    AppRobotGpsDispatchLogService appRobotGpsDispatchLogService;

    @Autowired
    AppUserService appUserService;
    /**
     * @Method getAppOrderList
     * @Author 郭凯
     * @Version  1.0
     * @Description 管理者APP机器人更新坐标列表接口
     * @Return java.lang.String
     * @Date 2021/6/8 14:39
     */
    @RequestMapping(value = "/getAppRobotGpsDispatchLogList",method = RequestMethod.POST)
    @ResponseBody
    public String getAppRobotGpsDispatchLogList(BaseQueryVo BaseQueryVo, String content) {
        ReturnModel dataModel = new ReturnModel();
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
            if (ToolUtil.isEmpty(longinTokenId)) {
                dataModel.setData("");
                dataModel.setMsg("TokenId为空，请传入TokenId!");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }

            //查询longinTokenId，是否一致
            SysAppUsers appUsers = appUserService.getSysUserBylonginTokenId(longinTokenId);
            if (appUsers == null) {
                dataModel.setData("");
                dataModel.setMsg("TokenId已过期，请重新登陆!");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }

            String scenicSpotId = jsonobject.getString("scenicSpotId");
            String robotCode = jsonobject.getString("robotCode");

            Map<String, String> search = new HashMap<>();

            search.put("scenicSpotId",scenicSpotId);
            search.put("robotCode",robotCode);
            BaseQueryVo.setSearch(search);
            PageInfo<AppRobotGpsDispatchLog> page =  appRobotGpsDispatchLogService.getAppRobotGpsDispatchLogList(BaseQueryVo);
            Map<String,Object> dataMap = new HashMap<>();
            dataMap.put("list",page.getList());
            dataMap.put("pages",page.getPages());
            dataMap.put("pageNum",page.getPageNum());
            dataMap.put("total",page.getTotal());
            //查询订单接口
            dataModel.setData(dataMap);
            dataModel.setMsg("机器人更新坐标列表查询成功!");
            dataModel.setState(Constant.STATE_SUCCESS);
            dataModel.setType(Constant.STATE_SUCCESS);
            String model = JSON.toJSONString(dataModel, SerializerFeature.WriteNullStringAsEmpty);
            return AES.encode(model);//加密返回
        }
        catch (Exception e) {
            // TODO: handle exception
            logger.info("getAppOrderList",e);
            dataModel.setData("");
            dataModel.setMsg("订单查询失败!");
            dataModel.setState(Constant.STATE_FAILURE);
            dataModel.setType(Constant.STATE_FAILURE);
            String model = JSON.toJSONString(dataModel, SerializerFeature.WriteNullStringAsEmpty);
            return AES.encode(model);//加密返回
        }

    }




}
