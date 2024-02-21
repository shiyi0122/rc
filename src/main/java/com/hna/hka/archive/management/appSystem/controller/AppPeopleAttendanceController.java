package com.hna.hka.archive.management.appSystem.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hna.hka.archive.management.appSystem.model.IndividualAndAttendance;
import com.hna.hka.archive.management.appSystem.service.AppPeopleAttendanceService;
import com.hna.hka.archive.management.appSystem.service.AppUserService;
import com.hna.hka.archive.management.assetsSystem.model.Address;
import com.hna.hka.archive.management.assetsSystem.model.AttendanceTime;
import com.hna.hka.archive.management.assetsSystem.model.EmployeeAttendance;
import com.hna.hka.archive.management.assetsSystem.model.RobotAttendance;
import com.hna.hka.archive.management.managerApp.model.SysAppUsers;
import com.hna.hka.archive.management.system.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @ClassName：AppAttendanceRecordController
 * @Author: gouteng
 * @Date: 2022-11-23 17:45
 * @Description: APP人员考勤、机器人考勤 的功能实现
 */
@RequestMapping("/system/appAttendanceRecord")
@Controller
public class AppPeopleAttendanceController extends PublicUtil {

    @Autowired
    private AppPeopleAttendanceService appPeopleAttendanceService;
    @Autowired
    private AppUserService appUserService;

    //    添加人员考勤
    @RequestMapping(value = "/inAttendanceRecord", method = RequestMethod.POST)
    @ResponseBody
    public String inAttendanceRecord(MultipartFile file, String content, EmployeeAttendance employeeAttendance,AttendanceTime attendanceTime) {
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
            String userId = jsonobject.getString("userId");
            String scenicSpotId = jsonobject.getString("scenicSpotId");
            employeeAttendance.setUserId(Long.valueOf(userId));
            employeeAttendance.setScenicSpotId(Long.valueOf(scenicSpotId));
            int i = appPeopleAttendanceService.inAttendanceRecord(file, employeeAttendance,userId,attendanceTime);
            if (i == 1) {
                IndividualAndAttendance individualAndAttendance = appPeopleAttendanceService.selectUser(employeeAttendance);
                dataModel.setData(individualAndAttendance);
                dataModel.setMsg("记录新增成功！");
                dataModel.setState(Constant.STATE_SUCCESS);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            } else if (i == 2) {
                dataModel.setData("");
                dataModel.setMsg("图片格式不正确！（只支持png，jpg文件）！");
                dataModel.setState(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            } else if (i == 3) {
                dataModel.setData("");
                dataModel.setMsg("超出打卡次数3次");
                dataModel.setState(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
        } catch (Exception e) {
            dataModel.setData("");
            dataModel.setMsg("记录新增失败！");
            dataModel.setState(Constant.STATE_FAILURE);
            dataModel.setType(Constant.STATE_FAILURE);
            String model = JsonUtils.toString(dataModel);//对象转JSON
            return AES.encode(model);//加密返回
        }
        String model = JsonUtils.toString(dataModel);//对象转JSON
        return AES.encode(model);//加密返回
    }

    //   查询人员考勤
    @RequestMapping(value = "/selAttendanceRecord", method = RequestMethod.POST)
    @ResponseBody
    public String selAttendanceRecord(String content, EmployeeAttendance employeeAttendance, AttendanceTime attendanceTime) {
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
            String userId = jsonobject.getString("userId");
            //判断scenicSpotId是否为空，如果为空，直接return
            if (userId == "" || userId == null) {
                dataModel.setData("");
                dataModel.setMsg("userId不可为空");
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
            IndividualAndAttendance individualAndAttendance = appPeopleAttendanceService.selAttendanceRecord(userId, employeeAttendance, attendanceTime);
            dataModel.setData(individualAndAttendance);
            dataModel.setMsg("个人考勤查询成功！");
            dataModel.setState(Constant.STATE_SUCCESS);
            dataModel.setType(Constant.STATE_SUCCESS);
            String model = JsonUtils.toString(dataModel);//对象转JSON
            return AES.encode(model);//加密返回
        } catch (Exception e) {
            // TODO: handle exception
            dataModel.setData("");
            dataModel.setMsg("个人考勤查询失败！");
            dataModel.setState(Constant.STATE_FAILURE);
            dataModel.setType(Constant.STATE_FAILURE);
            String model = JsonUtils.toString(dataModel);//对象转JSON
            return AES.encode(model);//加密返回
        }
    }

    //   人员考勤统计(未打卡,已打卡)
    @RequestMapping(value = "/selNoClocking", method = RequestMethod.POST)
    @ResponseBody
    public String selNoClocking(String content, EmployeeAttendance employeeAttendance) {
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
            dataModel = appPeopleAttendanceService.selNoClocking(employeeAttendance);
            dataModel.setMsg("人员考勤查询成功！");
            dataModel.setState(Constant.STATE_SUCCESS);
            dataModel.setType(Constant.STATE_SUCCESS);
            String model = JsonUtils.toString(dataModel);//对象转JSON
            return AES.encode(model);//加密返回
        } catch (Exception e) {
            // TODO: handle exception
            dataModel.setData("");
            dataModel.setMsg("人员考勤查询失败！");
            dataModel.setState(Constant.STATE_FAILURE);
            dataModel.setType(Constant.STATE_FAILURE);
            String model = JsonUtils.toString(dataModel);//对象转JSON
            return AES.encode(model);//加密返回
        }
    }

    //   机器人考勤统计
    @RequestMapping(value = "/selRobotClocking", method = RequestMethod.POST)
    @ResponseBody
    public String selRobotClocking(String content, RobotAttendance robotAttendance) {
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
            String userId = jsonobject.getString("userId");
            if (userId == "" || userId == null) {
                dataModel.setData("");
                dataModel.setMsg("userId不可为空");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
            IndividualAndAttendance individualAndAttendance = appPeopleAttendanceService.selRobotClocking(userId,robotAttendance);
            dataModel.setData(individualAndAttendance);
            dataModel.setMsg("机器人考勤查询成功！");
            dataModel.setState(Constant.STATE_SUCCESS);
            dataModel.setType(Constant.STATE_SUCCESS);
            String model = JsonUtils.toString(dataModel);//对象转JSON
            return AES.encode(model);//加密返回
        } catch (Exception e) {
            // TODO: handle exception
            dataModel.setData("");
            dataModel.setMsg("机器人考勤查询失败！");
            dataModel.setState(Constant.STATE_FAILURE);
            dataModel.setType(Constant.STATE_FAILURE);
            String model = JsonUtils.toString(dataModel);//对象转JSON
            return AES.encode(model);//加密返回
        }
    }

    @RequestMapping(value = "/upUserInformation", method = RequestMethod.POST)
    @ResponseBody
    public String upUserInformation(String content, EmployeeAttendance employeeAttendance) {
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
            String userId = jsonobject.getString("userId");
            if (userId == "" || userId == null) {
                dataModel.setData("");
                dataModel.setMsg("userId不可为空");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
            int i = appPeopleAttendanceService.upUserInformation(userId,employeeAttendance);
            dataModel.setMsg("个人信息修改成功！");
            dataModel.setState(Constant.STATE_SUCCESS);
            dataModel.setType(Constant.STATE_SUCCESS);
            String model = JsonUtils.toString(dataModel);//对象转JSON
            return AES.encode(model);//加密返回
        } catch (Exception e) {
            // TODO: handle exception
            dataModel.setData("");
            dataModel.setMsg("个人信息修改失败！");
            dataModel.setState(Constant.STATE_FAILURE);
            dataModel.setType(Constant.STATE_FAILURE);
            String model = JsonUtils.toString(dataModel);//对象转JSON
            return AES.encode(model);//加密返回
        }
    }
    
    @RequestMapping(value = "/selScenicSpot", method = RequestMethod.POST)
    @ResponseBody
    public String selScenicSpot(String content,String scenicSpotName) {
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
            List<Address> employeeAttendanceList = appPeopleAttendanceService.selScenicSpot(scenicSpotName);
            dataModel.setData(employeeAttendanceList);
            dataModel.setMsg("人员考勤查询成功！");
            dataModel.setState(Constant.STATE_SUCCESS);
            dataModel.setType(Constant.STATE_SUCCESS);
            String model = JsonUtils.toString(dataModel);//对象转JSON
            return AES.encode(model);//加密返回
        } catch (Exception e) {
            // TODO: handle exception
            dataModel.setData("");
            dataModel.setMsg("人员考勤查询失败！");
            dataModel.setState(Constant.STATE_FAILURE);
            dataModel.setType(Constant.STATE_FAILURE);
            String model = JsonUtils.toString(dataModel);//对象转JSON
            return AES.encode(model);//加密返回
        }
    }





}
