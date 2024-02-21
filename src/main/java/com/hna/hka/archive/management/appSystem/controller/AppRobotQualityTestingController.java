package com.hna.hka.archive.management.appSystem.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.inject.internal.cglib.core.$ClassInfo;
import com.hna.hka.archive.management.appSystem.service.AppRobotQualityTestingService;
import com.hna.hka.archive.management.appSystem.service.AppUserService;

import com.hna.hka.archive.management.assetsSystem.model.InspectionRecord;
import com.hna.hka.archive.management.appSystem.model.vo.SysAppRobotQualityTesting;
import com.hna.hka.archive.management.assetsSystem.model.ProductionInfo;
import com.hna.hka.archive.management.assetsSystem.model.SysRobotQualityInspectionStandard;
import com.hna.hka.archive.management.managerApp.model.SysAppUsers;
import com.hna.hka.archive.management.system.util.AES;
import com.hna.hka.archive.management.system.util.Constant;
import com.hna.hka.archive.management.system.util.JsonUtils;
import com.hna.hka.archive.management.system.util.ReturnModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @ClassName：AppRobotQualityTesting
 * @Author: gouteng
 * @Date: 2022-12-11 17:49
 * @Description: 实现质检/验收的新增和展示
 */
@RequestMapping("/system/appRobotQcOrAt")
@Controller
@Slf4j
public class AppRobotQualityTestingController {

    @Autowired
    private AppUserService appUserService;
    @Autowired
    private AppRobotQualityTestingService appRobotQualityTestingService;

    //    新增质检/验收记录
    @RequestMapping("/insertQcOrAt")
    @ResponseBody
    public String insertQcOrAt(String content, InspectionRecord inspectionRecord) {
        log.info("content{}",content);
        log.info("sysAppRobotQualityTesting{}",inspectionRecord);
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
            int i = appRobotQualityTestingService.insertQcOrAt(userId, inspectionRecord);
            if (i == 1) {
                dataModel.setData(1);
                dataModel.setMsg("新增成功！");
                dataModel.setState(Constant.STATE_SUCCESS);
                dataModel.setType(Constant.STATE_SUCCESS);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            } else if (i == 2){
                dataModel.setData(2);
                dataModel.setMsg("请录入正确编号!");
                dataModel.setState(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }else if (i == 3){
                dataModel.setData(0);
                dataModel.setMsg("此编号已有记录,无法重复录入!");
                dataModel.setState(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }else if (i == 4){
                dataModel.setData(0);
                dataModel.setMsg("有未合格的质检项，无法验收!");
                dataModel.setState(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }else if (i==5){
                dataModel.setData(0);
                dataModel.setMsg("此编号没有质检,无法录入!");
                dataModel.setState(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
                {
                dataModel.setData(0);
                dataModel.setMsg("添加失败!");
                dataModel.setState(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            dataModel.setData("");
            dataModel.setMsg("新增失败！");
            dataModel.setState(Constant.STATE_FAILURE);
            dataModel.setType(Constant.STATE_FAILURE);
            String model = JsonUtils.toString(dataModel);//对象转JSON
            return AES.encode(model);//加密返回
        }
    }

    //    返回最新的检测/验收记录(详情)
    @RequestMapping("/seInspectionDetails")
    @ResponseBody
    public String seInspectionDetails(String content, Long id,String robotCode) {
        log.info("content{} id{}",content,id);
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
            InspectionRecord inspectionRecord = appRobotQualityTestingService.seInspectionDetails(id, robotCode);
            if (StringUtils.isEmpty(inspectionRecord)){
                dataModel.setData("");
                dataModel.setMsg("未查询到机器人!");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }else{
                dataModel.setData(inspectionRecord);
                dataModel.setMsg("检验记录详情查询成功");
                dataModel.setState(Constant.STATE_SUCCESS);
                dataModel.setType(Constant.STATE_SUCCESS);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            dataModel.setData("");
            dataModel.setMsg("检验记录详情查询失败");
            dataModel.setState(Constant.STATE_FAILURE);
            dataModel.setType(Constant.STATE_FAILURE);
            String model = JsonUtils.toString(dataModel);//对象转JSON
            return AES.encode(model);//加密返回
        }
    }

    //    查询最新的检测/验收记录
    @RequestMapping("/selectQcOrAt")
    @ResponseBody
    public String selectQcOrAts(String content, String robotCode, String startTime, String endTime, Integer type, Integer result) {
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
            List<InspectionRecord> inspectionRecord1 = appRobotQualityTestingService.selectQcOrAts(robotCode,startTime,endTime,type, result,userId);
            dataModel.setData(inspectionRecord1);
            dataModel.setMsg("检验记录查询成功");
            dataModel.setState(Constant.STATE_SUCCESS);
            dataModel.setType(Constant.STATE_SUCCESS);
            String model = JsonUtils.toString(dataModel);//对象转JSON
            return AES.encode(model);//加密返回
        } catch (Exception e) {
            // TODO: handle exception
            dataModel.setData("");
            dataModel.setMsg("检验记录查询失败");
            dataModel.setState(Constant.STATE_FAILURE);
            dataModel.setType(Constant.STATE_FAILURE);
            String model = JsonUtils.toString(dataModel);//对象转JSON
            return AES.encode(model);//加密返回
        }
    }

    /**
     * 质检标准列表
     * @param content
     * @param type

     * @return
     */
    @RequestMapping("/qualityStandard")
    @ResponseBody
    public String qualityStandard(String content, String type) {
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
            List<SysRobotQualityInspectionStandard> inspectionRecord1 = appRobotQualityTestingService.qualityStandard(type);
            dataModel.setData(inspectionRecord1);
            dataModel.setMsg("质检标准下拉选查询成功");
            dataModel.setState(Constant.STATE_SUCCESS);
            dataModel.setType(Constant.STATE_SUCCESS);
            String model = JsonUtils.toString(dataModel);//对象转JSON
            return AES.encode(model);//加密返回
        } catch (Exception e) {
            // TODO: handle exception
            dataModel.setData("");
            dataModel.setMsg("质检标准查询失败");
            dataModel.setState(Constant.STATE_FAILURE);
            dataModel.setType(Constant.STATE_FAILURE);
            String model = JsonUtils.toString(dataModel);//对象转JSON
            return AES.encode(model);//加密返回
        }
    }


    /**
     * 生产批次列表
     * @param content

     * @return
     */
    @RequestMapping("/productionBatch")
    @ResponseBody
    public String productionBatch(String content) {
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
            List<ProductionInfo> inspectionRecord1 = appRobotQualityTestingService.productionBatch();
            dataModel.setData(inspectionRecord1);
            dataModel.setMsg("质检标准下拉选查询成功");
            dataModel.setState(Constant.STATE_SUCCESS);
            dataModel.setType(Constant.STATE_SUCCESS);
            String model = JsonUtils.toString(dataModel);//对象转JSON
            return AES.encode(model);//加密返回
        } catch (Exception e) {
            // TODO: handle exception
            dataModel.setData("");
            dataModel.setMsg("质检标准查询失败");
            dataModel.setState(Constant.STATE_FAILURE);
            dataModel.setType(Constant.STATE_FAILURE);
            String model = JsonUtils.toString(dataModel);//对象转JSON
            return AES.encode(model);//加密返回
        }
    }

    /**
     * 复检接口
     * @param content
     * @param inspectionRecord
     * @return
     */
    @RequestMapping("/reInspection")
    @ResponseBody
    public String reInspection(String content, InspectionRecord inspectionRecord) {
        log.info("content{}",content);
        log.info("sysAppRobotQualityTesting{}",inspectionRecord);
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
            int i = appRobotQualityTestingService.reInspection(userId, inspectionRecord);
            if (i == 1) {
                dataModel.setData(1);
                dataModel.setMsg("新增成功！");
                dataModel.setState(Constant.STATE_SUCCESS);
                dataModel.setType(Constant.STATE_SUCCESS);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            } else if (i == 2){
                dataModel.setData(2);
                dataModel.setMsg("请录入正确编号!");
                dataModel.setState(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }else if (i == 3){
                dataModel.setData(0);
                dataModel.setMsg("此编号已有记录,无法重复录入!");
                dataModel.setState(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }else{
                dataModel.setData(0);
                dataModel.setMsg("添加失败!");
                dataModel.setState(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            dataModel.setData("");
            dataModel.setMsg("新增失败失败！");
            dataModel.setState(Constant.STATE_FAILURE);
            dataModel.setType(Constant.STATE_FAILURE);
            String model = JsonUtils.toString(dataModel);//对象转JSON
            return AES.encode(model);//加密返回
        }
    }


}
