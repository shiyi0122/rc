package com.hna.hka.archive.management.system.controller;

import com.hna.hka.archive.management.system.model.*;
import com.hna.hka.archive.management.system.service.*;
import com.hna.hka.archive.management.system.util.Constant;
import com.hna.hka.archive.management.system.util.PublicUtil;
import com.hna.hka.archive.management.system.util.ReturnModel;
import com.hna.hka.archive.management.system.util.ToolUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @ProjectName: IDEA-CODE
 * @Package: com.hna.hka.archive.management.system.controller
 * @ClassName: MapController
 * @Author: 郭凯
 * @Description: 地图服务控制层
 * @Date: 2020/7/24 13:50
 * @Version: 1.0
 */
@RequestMapping("/system/map")
@Controller
public class MapController extends PublicUtil {

    @Autowired
    private SysScenicSpotGpsCoordinateService sysScenicSpotGpsCoordinateService;

    @Autowired
    private SysScenicSpotBroadcastService sysScenicSpotBroadcastService;

    @Autowired
    private SysScenicSpotParkingService sysScenicSpotParkingService;

    @Autowired
    private SysScenicSpotInnercircleCoordinateGroupService innercircleService;

    @Autowired
    private SysRobotService sysRobotService;

    @Autowired
    private HttpSession session;

    /**
     * @Author 郭凯
     * @Description 电子围栏坐标查询接口
     * @Date 13:54 2020/7/24
     * @Param [scenicSpotId]
     * @return com.hna.hka.archive.management.system.util.ReturnModel
    **/
    @RequestMapping(value = "/GpsCoordinate", method={RequestMethod.GET})
    @ResponseBody
    public ReturnModel GpsCoordinate() {
        ReturnModel returnModel = new ReturnModel();
        try {
            SysScenicSpotGpsCoordinateWithBLOBs scenicSpot = sysScenicSpotGpsCoordinateService.selectByPrimaryKey(Long.parseLong(session.getAttribute("scenicSpotId").toString()));
            if (ToolUtil.isNotEmpty(scenicSpot)) {
                returnModel.setData(scenicSpot);
                returnModel.setMsg("电子围栏查询成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            }else {
                returnModel.setData("");
                returnModel.setMsg("电子围栏查询失败！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        } catch (Exception e) {
            // TODO: handle exception
            logger.info("GpsCoordinate",e);
            returnModel.setData("");
            returnModel.setMsg("电子围栏查询失败！(请联系管理员)");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    /**
     * @Author 郭凯
     * @Description 景点坐标查询接口
     * @Date 13:57 2020/7/24
     * @Param [scenicSpotId]
     * @return com.hna.hka.archive.management.system.util.ReturnModel
    **/
    @RequestMapping(value = "/Broadcast", method={RequestMethod.GET})
    @ResponseBody
    public ReturnModel Broadcast() {
        ReturnModel dataModel = new ReturnModel();
        try {
            List<SysScenicSpotBroadcast> broadcastsList = sysScenicSpotBroadcastService.getBroadcastListByScenicSpotId(Long.parseLong(session.getAttribute("scenicSpotId").toString()));
            if (ToolUtil.isNotEmpty(broadcastsList)) {
                dataModel.setData(broadcastsList);
                dataModel.setMsg("景点坐标查询成功！");
                dataModel.setState(Constant.STATE_SUCCESS);
                return dataModel;
            }else {
                dataModel.setData("");
                dataModel.setMsg("景点坐标查询失败！");
                dataModel.setState(Constant.STATE_FAILURE);
                return dataModel;
            }
        } catch (Exception e) {
            // TODO: handle exception
            logger.info("Broadcast",e);
            dataModel.setData("");
            dataModel.setMsg("景点坐标查询失败！(请联系管理员)");
            dataModel.setState(Constant.STATE_FAILURE);
            return dataModel;
        }
    }

    /**
     * @Author 郭凯
     * @Description 停车点坐标查询接口
     * @Date 14:05 2020/7/24
     * @Param [scenicSpotId]
     * @return com.hna.hka.archive.management.system.util.ReturnModel
    **/
    @RequestMapping(value = "/parking", method={RequestMethod.GET})
    @ResponseBody
    public ReturnModel Parking() {
        ReturnModel dataModel = new ReturnModel();
        try {
            List<SysScenicSpotParkingWithBLOBs> spotParking = sysScenicSpotParkingService.getParkingListBy(Long.parseLong(session.getAttribute("scenicSpotId").toString()));
            if (ToolUtil.isNotEmpty(spotParking)) {
                dataModel.setData(spotParking);
                dataModel.setMsg("停车点坐标查询成功！");
                dataModel.setState(Constant.STATE_SUCCESS);
                return dataModel;
            }else {
                dataModel.setData("");
                dataModel.setMsg("停车点坐标查询失败！");
                dataModel.setState(Constant.STATE_FAILURE);
                return dataModel;
            }
        } catch (Exception e) {
            // TODO: handle exception
            logger.info("parking",e);
            dataModel.setData("");
            dataModel.setMsg("停车点坐标查询失败！(请联系管理员)");
            dataModel.setState(Constant.STATE_FAILURE);
            return dataModel;
        }
    }

    /**
     * @Author 郭凯
     * @Description 禁区坐标查询接口
     * @Date 14:40 2020/7/24
     * @Param [scenicSpotId]
     * @return com.hna.hka.archive.management.system.util.ReturnModel
    **/
    @RequestMapping(value = "/coordinateGroup", method={RequestMethod.GET})
    @ResponseBody
    public ReturnModel coordinateGroup() {
        ReturnModel dataModel = new ReturnModel();
        try {
            List<SysScenicSpotInnercircleCoordinateGroupWithBLOBs> coordinateGroups = innercircleService.getCoordinateGroupListByScenicSpotId(Long.parseLong(session.getAttribute("scenicSpotId").toString()));
            if (ToolUtil.isNotEmpty(coordinateGroups)) {
                dataModel.setData(coordinateGroups);
                dataModel.setMsg("禁区坐标查询成功！");
                dataModel.setState(Constant.STATE_SUCCESS);
                return dataModel;
            }else {
                dataModel.setData("");
                dataModel.setMsg("禁区坐标查询失败！");
                dataModel.setState(Constant.STATE_FAILURE);
                return dataModel;
            }
        } catch (Exception e) {
            // TODO: handle exception
            logger.info("coordinateGroup",e);
            dataModel.setData("");
            dataModel.setMsg("禁区坐标查询失败！(请联系管理员)");
            dataModel.setState(Constant.STATE_FAILURE);
            return dataModel;
        }
    }

    /**
     * @Author 郭凯
     * @Description 机器人坐标查询接口
     * @Date 14:47 2020/7/24
     * @Param [robotCode, scenicSpotId]
     * @return com.hna.hka.archive.management.system.util.ReturnModel
    **/
    @RequestMapping(value = "/Robot", method={RequestMethod.GET})
    @ResponseBody
    public ReturnModel Robot(String robotCode) {
        ReturnModel dataModel = new ReturnModel();
        try {
            List<SysRobot> robots = sysRobotService.getRobotListBy(Long.parseLong(session.getAttribute("scenicSpotId").toString()),robotCode);
            System.out.println(robots);
            if (ToolUtil.isNotEmpty(robots)) {
                dataModel.setData(robots);
                dataModel.setMsg("机器人坐标查询成功！");
                dataModel.setState(Constant.STATE_SUCCESS);
                return dataModel;
            }else {
                dataModel.setData("");
                dataModel.setMsg("机器人坐标查询失败！");
                dataModel.setState(Constant.STATE_FAILURE);
                return dataModel;
            }
        } catch (Exception e) {
            // TODO: handle exception
            logger.info("Robot",e);
            dataModel.setData("");
            dataModel.setMsg("机器人坐标查询失败！(请联系管理员)");
            dataModel.setState(Constant.STATE_FAILURE);
            return dataModel;
        }
    }


    /**
     * @Author 郭凯
     * @Description 库房点坐标查询接口
     * @Date 14:05 2020/7/24
     * @Param [scenicSpotId]
     * @return com.hna.hka.archive.management.system.util.ReturnModel
     **/
    @RequestMapping(value = "/storageRoom", method={RequestMethod.GET})
    @ResponseBody
    public ReturnModel StorageRoom() {
        ReturnModel dataModel = new ReturnModel();
        try {
            List<SysScenicSpotParkingWithBLOBs> spotParking = sysScenicSpotParkingService.getStorageRoomListBy(Long.parseLong(session.getAttribute("scenicSpotId").toString()));
            if (ToolUtil.isNotEmpty(spotParking)) {
                dataModel.setData(spotParking);
                dataModel.setMsg("库房点坐标查询成功！");
                dataModel.setState(Constant.STATE_SUCCESS);
                return dataModel;
            }else {
                dataModel.setData("");
                dataModel.setMsg("库房点坐标查询失败！");
                dataModel.setState(Constant.STATE_FAILURE);
                return dataModel;
            }
        } catch (Exception e) {
            // TODO: handle exception
            logger.info("parking",e);
            dataModel.setData("");
            dataModel.setMsg("库房点坐标查询失败！(请联系管理员)");
            dataModel.setState(Constant.STATE_FAILURE);
            return dataModel;
        }
    }

}
