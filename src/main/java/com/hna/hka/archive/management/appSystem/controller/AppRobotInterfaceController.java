package com.hna.hka.archive.management.appSystem.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.github.pagehelper.PageInfo;
import com.hna.hka.archive.management.appSystem.model.SysAppRobot;
import com.hna.hka.archive.management.appSystem.model.SysAppRobotOperationTime;
import com.hna.hka.archive.management.appSystem.service.AppUserService;
import com.hna.hka.archive.management.assetsSystem.model.SysRobotBelarcAdvisor;
import com.hna.hka.archive.management.assetsSystem.model.SysRobotSoftAssetInformation;
import com.hna.hka.archive.management.assetsSystem.service.SysRobotBelarcAdvisorService;
import com.hna.hka.archive.management.assetsSystem.service.SysRobotSoftAssetInformationService;
import com.hna.hka.archive.management.managerApp.model.SysAppUsers;
import com.hna.hka.archive.management.system.service.SysRobotService;
import com.hna.hka.archive.management.system.util.*;
import com.hna.hka.archive.management.wenYuRiverInterface.model.BaseQueryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * @ProjectName: rc
 * @Package: com.hna.hka.archive.management.appSystem.controller
 * @ClassName: AppRobotInterfaceController
 * @Author: 郭凯
 * @Description: APP机器人管理控制层
 * @Date: 2021/6/8 13:15
 * @Version: 1.0
 */
@RequestMapping("/system/appRobotInterface")
@Controller
public class AppRobotInterfaceController extends PublicUtil {

    @Autowired
    private AppUserService appUserService;

    @Autowired
    private SysRobotService sysRobotService;

    @Autowired
    private SysRobotBelarcAdvisorService sysRobotBelarcAdvisorService;

    @Autowired
    private SysRobotSoftAssetInformationService sysRobotSoftAssetInformationService;
    /**
     * @Method getAppRobotList
     * @Author 郭凯
     * @Version  1.0
     * @Description APP机器人列表查询
     * @Return java.lang.String
     * @Date 2021/6/8 13:18
     */
    @RequestMapping("/getAppRobotList")
    @ResponseBody
    public String getAppRobotList(BaseQueryVo BaseQueryVo , String content){
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
            //景区ID
            String scenicSpotId = jsonobject.getString("scenicSpotId");
            if (ToolUtil.isEmpty(scenicSpotId)) {
                dataModel.setData("");
                dataModel.setMsg("景区ID为空，请传入景区ID!");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
            //机器人运行状态
            String robotRunState = jsonobject.getString("robotRunState");
            if (ToolUtil.isEmpty(robotRunState)) {
                dataModel.setData("");
                dataModel.setMsg("运行状态为空，请传入运行状态!");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
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
            if (ToolUtil.isEmpty(appUsers)) {
                dataModel.setData("");
                dataModel.setMsg("TokenId已过期，请重新登陆!");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
            Map<String,String> search = new HashMap<>();
            search.put("scenicSpotId", scenicSpotId);
            search.put("robotRunState", robotRunState);
            BaseQueryVo.setSearch(search);
            PageInfo<SysAppRobot> page = sysRobotService.getAppRobotListVo(BaseQueryVo);
            Map<String,Object> dataMap = new HashMap<>();
            dataMap.put("list",page.getList());
            dataMap.put("pages",page.getPages());
            dataMap.put("pageNum",page.getPageNum());
            dataModel.setData(dataMap);
            dataModel.setMsg("");
            dataModel.setState(Constant.STATE_SUCCESS);
            dataModel.setType(Constant.STATE_SUCCESS);
            String model = JsonUtils.toString(dataModel);//对象转JSON
            return AES.encode(model);//加密返回
        }catch (Exception e){
            dataModel.setData("");
            dataModel.setMsg("接口超时");
            dataModel.setState(Constant.STATE_FAILURE);
            dataModel.setType(Constant.STATE_FAILURE);
            String model = JsonUtils.toString(dataModel);//对象转JSON
            return AES.encode(model);//加密返回
        }
    }

    /**
     * @Method getRobotSoftwareAndHardwareInformation
     * @Author 郭凯
     * @Version  1.0
     * @Description 根据机器人编号查询软硬件信息（APP接口）
     * @Return java.lang.String
     * @Date 2021/6/10 13:18
     */
    @RequestMapping("/getRobotSoftwareAndHardwareInformation")
    @ResponseBody
    public String getRobotSoftwareAndHardwareInformation(String content){
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
            //机器人编号
            String robotCode = jsonobject.getString("robotCode");
            if (ToolUtil.isEmpty(robotCode)) {
                dataModel.setData("");
                dataModel.setMsg("机器人编号为空，请传入机器人编号!");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
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
            if (ToolUtil.isEmpty(appUsers)) {
                dataModel.setData("");
                dataModel.setMsg("TokenId已过期，请重新登陆!");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
            Map<String,Object> search = new HashMap<>();
            search.put("robotCode", robotCode);
            SysRobotBelarcAdvisor robotBelarcAdvisor = sysRobotBelarcAdvisorService.getRobotSoftwareAndHardwareByCode(search);
            dataModel.setData(robotBelarcAdvisor);
            dataModel.setMsg("");
            dataModel.setState(Constant.STATE_SUCCESS);
            dataModel.setType(Constant.STATE_SUCCESS);
            String model = JSON.toJSONString(dataModel, SerializerFeature.WriteNullStringAsEmpty);//对象转JSON
            return AES.encode(model);//加密返回
        }catch (Exception e){
            dataModel.setData("");
            dataModel.setMsg("接口超时");
            dataModel.setState(Constant.STATE_FAILURE);
            dataModel.setType(Constant.STATE_FAILURE);
            String model = JsonUtils.toString(dataModel);//对象转JSON
            return AES.encode(model);//加密返回
        }
    }

    /**
     * @Method getAppRobotSoftAssetInformation
     * @Author 郭凯
     * @Version  1.0
     * @Description 根据机器人编号查询机器人软资产
     * @Return java.lang.String
     * @Date 2021/6/10 14:44
     */
    @RequestMapping("/getAppRobotSoftAssetInformation")
    @ResponseBody
    public String getAppRobotSoftAssetInformation(String content){
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
            //机器人编号
            String robotCode = jsonobject.getString("robotCode");
            if (ToolUtil.isEmpty(robotCode)) {
                dataModel.setData("");
                dataModel.setMsg("机器人编号为空，请传入机器人编号!");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
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
            if (ToolUtil.isEmpty(appUsers)) {
                dataModel.setData("");
                dataModel.setMsg("TokenId已过期，请重新登陆!");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
            Map<String,Object> search = new HashMap<>();
            search.put("robotCode", robotCode);
            SysRobotSoftAssetInformation robotSoftAssetInformation = sysRobotSoftAssetInformationService.getAppRobotSoftAssetInformation(search);
            dataModel.setData(robotSoftAssetInformation);
            dataModel.setMsg("");
            dataModel.setState(Constant.STATE_SUCCESS);
            dataModel.setType(Constant.STATE_SUCCESS);
            String model = JSON.toJSONString(dataModel, SerializerFeature.WriteNullStringAsEmpty);
            return AES.encode(model);//加密返回
        }catch (Exception e){
            dataModel.setData("");
            dataModel.setMsg("接口超时");
            dataModel.setState(Constant.STATE_FAILURE);
            dataModel.setType(Constant.STATE_FAILURE);
            String model = JsonUtils.toString(dataModel);//对象转JSON
            return AES.encode(model);//加密返回
        }
    }

    /**
     * @Method getAppRobotOperationTime
     * @Author 郭凯
     * @Version  1.0
     * @Description 机器人运营时长列表查询
     * @Return java.lang.String
     * @Date 2021/6/10 15:57
     */
    @RequestMapping("/getAppRobotOperationTime")
    @ResponseBody
    public String getAppRobotOperationTime(BaseQueryVo BaseQueryVo , String content){
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
            //机器人编号
            String robotCode = jsonobject.getString("robotCode");
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
            //景区ID
            String scenicSpotId = jsonobject.getString("scenicSpotId");
            //景区ID，如果为空，直接return
            if (ToolUtil.isEmpty(scenicSpotId)) {
                dataModel.setData("");
                dataModel.setMsg("景区ID为空，请传入景区ID!");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
            //查询longinTokenId，是否一致
            SysAppUsers appUsers = appUserService.getSysUserBylonginTokenId(longinTokenId);
            if (ToolUtil.isEmpty(appUsers)) {
                dataModel.setData("");
                dataModel.setMsg("TokenId已过期，请重新登陆!");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
            //开始时间
            String startTime = jsonobject.getString("startTime");
            //结束时间
            String endTime = jsonobject.getString("endTime");
            if (ToolUtil.isEmpty(startTime) || ToolUtil.isEmpty(endTime)){
                startTime = DateUtil.crutDate();
                endTime = DateUtil.crutDate();
            }
            Map<String,String> search = new HashMap<>();
            search.put("robotCode", robotCode);
            search.put("startTime",startTime);
            search.put("endTime",endTime);
            search.put("scenicSpotId",scenicSpotId);
            BaseQueryVo.setSearch(search);
            PageInfo<SysAppRobotOperationTime> page = sysRobotService.getAppRobotOperationTime(BaseQueryVo);
            Map<String,Object> dataMap = new HashMap<>();
            dataMap.put("list",page.getList());
            dataMap.put("pages",page.getPages());
            dataMap.put("pageNum",page.getPageNum());
            dataMap.put("total",page.getTotal());
            dataModel.setData(dataMap);
            dataModel.setMsg("");
            dataModel.setState(Constant.STATE_SUCCESS);
            dataModel.setType(Constant.STATE_SUCCESS);
            String model = JsonUtils.toString(dataModel);//对象转JSON
            return AES.encode(model);//加密返回
        }catch (Exception e){
            dataModel.setData("");
            dataModel.setMsg("接口超时");
            dataModel.setState(Constant.STATE_FAILURE);
            dataModel.setType(Constant.STATE_FAILURE);
            String model = JsonUtils.toString(dataModel);//对象转JSON
            return AES.encode(model);//加密返回
        }
    }

    /**
     * 管理者app查询机器人列表
     * zhang
     */

    @RequestMapping("/getAppRobotListNew")
    @ResponseBody
    public String getAppRobotListNew(BaseQueryVo BaseQueryVo , String content){
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
            //景区ID
            String scenicSpotId = jsonobject.getString("scenicSpotId");
            if (ToolUtil.isEmpty(scenicSpotId)) {
                dataModel.setData("");
                dataModel.setMsg("景区ID为空，请传入景区ID!");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
            //机器人运行状态
            String robotRunState = jsonobject.getString("robotRunState");
            if (ToolUtil.isEmpty(robotRunState)) {
                dataModel.setData("");
                dataModel.setMsg("运行状态为空，请传入运行状态!");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
            //排序方式
            String sort = jsonobject.getString("sort");
            if (ToolUtil.isEmpty(sort)){
                dataModel.setData("");
                dataModel.setMsg("排序方式为空，请传入排序方式");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }

            String sortField = jsonobject.getString("sortField");
            if (ToolUtil.isEmpty(sortField)){
                dataModel.setData("");
                dataModel.setMsg("排序字段为空，请传入排序字段");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }

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
            if (ToolUtil.isEmpty(appUsers)) {
                dataModel.setData("");
                dataModel.setMsg("TokenId已过期，请重新登陆!");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
            Map<String,String> search = new HashMap<>();
            search.put("scenicSpotId", scenicSpotId);
            search.put("robotRunState", robotRunState);
            if ("1".equals(sort)){
                search.put("sort","1");
            }else{
                search.put("sort","0");
            }

            if ("1".equals(sortField)){
                search.put("sortField","1");
            }else{
                search.put("sortField","0");
            }
            BaseQueryVo.setSearch(search);
             PageInfo<SysAppRobot> page = sysRobotService.getAppRobotListVoNew(BaseQueryVo);
            Map<String,Object> dataMap = new HashMap<>();
            dataMap.put("list",page.getList());
            dataMap.put("pages",page.getPages());
            dataMap.put("pageNum",page.getPageNum());
            dataModel.setData(dataMap);
            dataModel.setMsg("");
            dataModel.setState(Constant.STATE_SUCCESS);
            dataModel.setType(Constant.STATE_SUCCESS);
            String model = JsonUtils.toString(dataModel);//对象转JSON
            return AES.encode(model);//加密返回
        }catch (Exception e){
            e.printStackTrace();
            dataModel.setData("");
            dataModel.setMsg("接口超时");
            dataModel.setState(Constant.STATE_FAILURE);
            dataModel.setType(Constant.STATE_FAILURE);
            String model = JsonUtils.toString(dataModel);//对象转JSON
            return AES.encode(model);//加密返回
        }
    }






}
