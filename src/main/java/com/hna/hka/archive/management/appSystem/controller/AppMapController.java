package com.hna.hka.archive.management.appSystem.controller;

import com.hna.hka.archive.management.appSystem.service.AppUserService;
import com.hna.hka.archive.management.managerApp.model.SysAppUsers;
import com.hna.hka.archive.management.system.model.*;
import com.hna.hka.archive.management.system.service.*;
import com.hna.hka.archive.management.system.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * @ProjectName: IDEA-CODE
 * @Package: com.hna.hka.archive.management.appSystem.controller
 * @ClassName: AppMapController
 * @Author: 郭凯
 * @Description: 管理者APP地图管理控制层
 * @Date: 2020/11/23 14:56
 * @Version: 1.0
 */

@RequestMapping("/appSystem/appMap")
@Controller
public class AppMapController    extends PublicUtil {

    @Autowired
    private AppUserService appUserService;

    @Autowired
    private SysScenicSpotGpsCoordinateService sysScenicSpotGpsCoordinateService;

    @Autowired
    private SysScenicSpotBroadcastService sysScenicSpotBroadcastService;

    @Autowired
    private SysScenicSpotParkingService sysScenicSpotParkingService;

    @Autowired
    private SysScenicSpotInnercircleCoordinateGroupService innerCircleService;

    @Autowired
    private SysRobotMapResService sysRobotMapResService;


    /**
     * @Author 郭凯
     * @Description 地图资源查询
     * @Date 15:30 2020/11/23
     * @Param [content]
     * @return java.lang.String
    **/
    @RequestMapping("/AppGpsCoordinate.do")
    @ResponseBody
    public String AppGpsCoordinate(String content) {
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
            if (longinTokenId == "" || longinTokenId == null) {
                dataModel.setData("");
                dataModel.setMsg("TokenId为空，请传入TokenId!");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
            //景区ID
            String scenicSpotId = jsonobject.getString("scenicSpotId");
            //判断scenicSpotId是否为空，如果为空，直接return
            if (scenicSpotId == "" || scenicSpotId == null) {
                dataModel.setData("");
                dataModel.setMsg("景区ID为空，请传入景区ID!");
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
            SysScenicSpotGpsCoordinateWithBLOBs scenicSpot = null;
            scenicSpot = sysScenicSpotGpsCoordinateService.selectByPrimaryKey(Long.parseLong(scenicSpotId));
            dataModel.setData(scenicSpot);
            dataModel.setMsg("电子围栏查询成功！");
            dataModel.setState(Constant.STATE_SUCCESS);
            dataModel.setType(Constant.STATE_SUCCESS);
            String model = JsonUtils.toString(dataModel);//对象转JSON
            return AES.encode(model);//加密返回
        } catch (Exception e) {
            // TODO: handle exception
            dataModel.setData("");
            dataModel.setMsg("电子围栏查询失败！");
            dataModel.setState(Constant.STATE_FAILURE);
            dataModel.setType(Constant.STATE_FAILURE);
            String model = JsonUtils.toString(dataModel);//对象转JSON
            return AES.encode(model);//加密返回
        }
    }

    /**
     * @Author 郭凯
     * @Description 景点列表查询
     * @Date 15:30 2020/11/23
     * @Param [content]
     * @return java.lang.String
    **/
    @RequestMapping("/AppBroadcast.do")
    @ResponseBody
    public String AppBroadcast(String content) {
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
            if (longinTokenId == "" || longinTokenId == null) {
                dataModel.setData("");
                dataModel.setMsg("TokenId为空，请传入TokenId!");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
            //景区ID
            String scenicSpotId = jsonobject.getString("scenicSpotId");
            //判断scenicSpotId是否为空，如果为空，直接return
            if (scenicSpotId == "" || scenicSpotId == null) {
                dataModel.setData("");
                dataModel.setMsg("景区ID为空，请传入景区ID!");
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
            List<SysScenicSpotBroadcast> broadcastsList = sysScenicSpotBroadcastService.getBroadcastByScenicSpotId(scenicSpotId);
            dataModel.setData(broadcastsList);
            dataModel.setMsg("景点坐标查询成功！");
            dataModel.setState(Constant.STATE_SUCCESS);
            dataModel.setType(Constant.STATE_SUCCESS);
            String model = JsonUtils.toString(dataModel);//对象转JSON
            return AES.encode(model);//加密返回
        } catch (Exception e) {
            // TODO: handle exception
            dataModel.setData("");
            dataModel.setMsg("景点坐标查询失败！");
            dataModel.setState(Constant.STATE_FAILURE);
            dataModel.setType(Constant.STATE_FAILURE);
            String model = JsonUtils.toString(dataModel);//对象转JSON
            return AES.encode(model);//加密返回
        }
    }

    /**
     * @Author 郭凯
     * @Description 停放点列表查询
     * @Date 15:30 2020/11/23
     * @Param [content]
     * @return java.lang.String
    **/
    @RequestMapping("/AppParking.do")
    @ResponseBody
    public String AppParking(String content) {
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
            if (longinTokenId == "" || longinTokenId == null) {
                dataModel.setData("");
                dataModel.setMsg("TokenId为空，请传入TokenId!");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
            //景区ID
            String scenicSpotId = jsonobject.getString("scenicSpotId");
            //判断scenicSpotId是否为空，如果为空，直接return
            if (scenicSpotId == "" || scenicSpotId == null) {
                dataModel.setData("");
                dataModel.setMsg("景区ID为空，请传入景区ID!");
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
            List<SysScenicSpotParkingWithBLOBs> spotParkings = sysScenicSpotParkingService.getParkingListBy(Long.parseLong(scenicSpotId));
            dataModel.setData(spotParkings);
            dataModel.setMsg("停车点坐标查询成功！");
            dataModel.setState(Constant.STATE_SUCCESS);
            dataModel.setType(Constant.STATE_SUCCESS);
            String model = JsonUtils.toString(dataModel);//对象转JSON
            return AES.encode(model);//加密返回
        } catch (Exception e) {
            // TODO: handle exception
            dataModel.setData("");
            dataModel.setMsg("停车点坐标查询失败！");
            dataModel.setState(Constant.STATE_FAILURE);
            dataModel.setType(Constant.STATE_FAILURE);
            String model = JsonUtils.toString(dataModel);//对象转JSON
            return AES.encode(model);//加密返回
        }
    }


    /**
     * @Author 郭凯
     * @Description 禁区列表查询
     * @Date 15:31 2020/11/23
     * @Param [content]
     * @return java.lang.String
    **/
    @RequestMapping("/AppCoordinateGroup.do")
    @ResponseBody
    public String AppCoordinateGroup(String content) {
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
            if (longinTokenId == "" || longinTokenId == null) {
                dataModel.setData("");
                dataModel.setMsg("TokenId为空，请传入TokenId!");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
            //景区ID
            String scenicSpotId = jsonobject.getString("scenicSpotId");
            //判断scenicSpotId是否为空，如果为空，直接return
            if (scenicSpotId == "" || scenicSpotId == null) {
                dataModel.setData("");
                dataModel.setMsg("景区ID为空，请传入景区ID!");
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
            List<SysScenicSpotInnercircleCoordinateGroupWithBLOBs> coordinateGroups = innerCircleService.getCoordinateGroupListByScenicSpotId(Long.parseLong(scenicSpotId));
            dataModel.setData(coordinateGroups);
            dataModel.setMsg("禁区坐标查询成功！");
            dataModel.setState(Constant.STATE_SUCCESS);
            dataModel.setType(Constant.STATE_SUCCESS);
            String model = JsonUtils.toString(dataModel);//对象转JSON
            return AES.encode(model);//加密返回
        } catch (Exception e) {
            // TODO: handle exception
            dataModel.setData("");
            dataModel.setMsg("禁区坐标查询失败！");
            dataModel.setState(Constant.STATE_FAILURE);
            dataModel.setType(Constant.STATE_FAILURE);
            String model = JsonUtils.toString(dataModel);//对象转JSON
            return AES.encode(model);//加密返回
        }
    }

    /**
     * @Author 郭凯
     * @Description 地图资源查询
     * @Date 15:33 2020/11/23
     * @Param [content]
     * @return java.lang.String
    **/
    @RequestMapping("/AppResMap.do")
    @ResponseBody
    public String AppResMap(String content) {
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
            if (longinTokenId == "" || longinTokenId == null) {
                dataModel.setData("");
                dataModel.setMsg("TokenId为空，请传入TokenId!");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
            //景区ID
            String scenicSpotId = jsonobject.getString("scenicSpotId");
            //判断scenicSpotId是否为空，如果为空，直接return
            if (scenicSpotId == "" || scenicSpotId == null) {
                dataModel.setData("");
                dataModel.setMsg("景区ID为空，请传入景区ID!");
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
            SysRobotMapRes mapRes = sysRobotMapResService.getSysRobotMapResByScenicSpotId(scenicSpotId);
            dataModel.setData(mapRes);
            dataModel.setMsg("地图资源查询成功！");
            dataModel.setState(Constant.STATE_SUCCESS);
            dataModel.setType(Constant.STATE_SUCCESS);
            String model = JsonUtils.toString(dataModel);//对象转JSON
            return AES.encode(model);//加密返回
        } catch (Exception e) {
            // TODO: handle exception
            dataModel.setData("");
            dataModel.setMsg("地图资源查询失败！");
            dataModel.setState(Constant.STATE_FAILURE);
            dataModel.setType(Constant.STATE_FAILURE);
            String model = JsonUtils.toString(dataModel);//对象转JSON
            return AES.encode(model);//加密返回
        }
    }


}
