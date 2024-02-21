package com.hna.hka.archive.management.assetsSystem.controller;

import com.gexin.fastjson.JSON;
import com.hna.hka.archive.management.assetsSystem.model.FactorySend;
import com.hna.hka.archive.management.assetsSystem.service.FactorySendService;
import com.hna.hka.archive.management.system.model.SysUsers;
import com.hna.hka.archive.management.system.util.Constant;
import com.hna.hka.archive.management.system.util.PublicUtil;
import com.hna.hka.archive.management.system.util.ReturnModel;
import com.hna.hka.archive.management.system.util.WeChatGtRobotAppPush;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @program: rc
 * @description: 工厂发货
 * @author: zhaoxianglong
 * @create: 2021-09-29 10:43
 **/
@CrossOrigin
@RestController
@Api(tags = "发货管理")
@RequestMapping("/system/factory_send")
public class FactorySendController extends PublicUtil {

    @Autowired
    FactorySendService service;


    @ApiOperation("工厂发货列表信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "spotId", value = "景区ID"),
            @ApiImplicitParam(name = "beginDate", value = "起始时间"),
            @ApiImplicitParam(name = "endDate", value = "截止时间"),
            @ApiImplicitParam(name = "pageNum", value = "页码"),
            @ApiImplicitParam(name = "pageSize", value = "每页条数"),
    })
    @GetMapping("/sendList")
    public ReturnModel sendList(Long spotId, String beginDate, String endDate, Long userId, Integer type , Integer pageNum, Integer pageSize) {
        try {
            List<FactorySend> list = service.list(spotId, beginDate, endDate, userId, pageNum, pageSize,type , 1);
            Integer count = service.getCount(spotId, beginDate, endDate, userId, type, 1);
            return new ReturnModel(list, "success", Constant.STATE_SUCCESS, null, count);
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnModel(e.getMessage(), "error", "500", null);
        }
    }

    @ApiOperation("添加")
    @PostMapping("/add")
    public ReturnModel add(@RequestBody FactorySend factorySend) {
        try {
            if (StringUtils.isEmpty(factorySend.getApplicantId()) && StringUtils.isEmpty(factorySend.getApplicantName())){
                SysUsers sysUser = this.getSysUser();
                factorySend.setApplicantId(sysUser.getUserId());
                factorySend.setApplicantName(sysUser.getUserName());
            }
            Integer count = service.add(factorySend);
            if (count > 0) {
                if (factorySend.getForm() == 1) {
                    WeChatGtRobotAppPush.singlePushApp(service.getPId(factorySend.getConsignorId()), "工厂发货提醒", "工厂发货," + factorySend.getFactoryName() + "-" + factorySend.getReceivingName() + ",发出" + factorySend.getRobotCount() + "台,请尽快安排发货");
                } else if (factorySend.getForm() == 2) {
                    WeChatGtRobotAppPush.singlePushApp(service.getPId(factorySend.getConsignorId()), "设备调运提醒", "设备调运," + factorySend.getFactoryName() + "-" + factorySend.getReceivingName() + ",发出" + factorySend.getRobotCount() + "台,请尽快安排发货");
                }
                return new ReturnModel(count, "success", Constant.STATE_SUCCESS, null);
            } else {
                return new ReturnModel(null, "fail", Constant.STATE_FAILURE, null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnModel(e.getMessage(), "error", "500", null);
        }
    }


    @ApiOperation("修改")
    @PostMapping("/edit")
    public ReturnModel edit(@RequestBody FactorySend factorySend) {
        try {
            Integer count = service.edit(factorySend);
            if (count > 0) {
                if (factorySend.getType() == 2) {
                    if (factorySend.getForm() == 1) {
                        WeChatGtRobotAppPush.singlePushApp(service.getPId(factorySend.getConsigneeId()), "工厂发货提醒", "工厂发货," + factorySend.getFactoryName() + "-" + factorySend.getReceivingName() + ",发出" + factorySend.getRobotCount() + "台,收货后请注意签收");
                    } else if (factorySend.getForm() == 2) {
                        WeChatGtRobotAppPush.singlePushApp(service.getPId(factorySend.getConsigneeId()), "设备调运提醒", "设备调运," + factorySend.getFactoryName() + "-" + factorySend.getReceivingName() + ",发出" + factorySend.getRobotCount() + "台,收货后请注意签收");
                    }
                }
                return new ReturnModel(count, "success", Constant.STATE_SUCCESS, null);
            } else {
                return new ReturnModel("数据不存在或已被删除", "fail", Constant.STATE_FAILURE, null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnModel(e.getMessage(), "error", "500", null);
        }
    }

    @ApiOperation("详情")
    @GetMapping("/detail")
    public ReturnModel detail(Long id) {
        try {
            FactorySend factorySend = service.detail(id);
            return new ReturnModel(factorySend, "fail", Constant.STATE_FAILURE, null);
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnModel(e.getMessage(), "error", "500", null);
        }
    }

    @ApiOperation("工厂列表")
    @GetMapping("/factory_list")
    public String factoryList() {


        return JSON.toJSONString(service.factoryList());
    }

    @ApiOperation("景区列表")
    @GetMapping("/spot_list")
    public String spotList() {
        return JSON.toJSONString(service.spotList());
    }

    @ApiOperation("全部收货景区列表")
    @GetMapping("/spot_list_all")
    public String spot_list_all() {
        return JSON.toJSONString(service.spotAllList());
    }

    @ApiOperation("员工列表")
    @GetMapping("/user_list")
    public String userList() {
        return JSON.toJSONString(service.userList());
    }

    @ApiOperation("设备调运列表信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "spotId", value = "景区ID"),
            @ApiImplicitParam(name = "beginDate", value = "起始时间"),
            @ApiImplicitParam(name = "endDate", value = "截止时间"),
            @ApiImplicitParam(name = "pageNum", value = "页码"),
            @ApiImplicitParam(name = "pageSize", value = "每页条数"),
    })
    @GetMapping("/dispatchList")
    public ReturnModel dispatchList(Long spotId, String beginDate, String endDate, Long userId, Integer type , Integer pageNum, Integer pageSize) {
        try {
            List<FactorySend> list = service.list(spotId, beginDate, endDate, userId, pageNum, pageSize, type, 2);
            Integer count = service.getCount(spotId, beginDate, endDate, userId, type, 2);
            return new ReturnModel(list, "success", Constant.STATE_SUCCESS, null, count);
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnModel(e.getMessage(), "error", "500", null);
        }
    }


    @ApiOperation("调运轨迹列表信息")
    @GetMapping("/trailList")
    public ReturnModel trailList(String robotCode) {
        try {
            List list = service.trailList(robotCode);
            return new ReturnModel(list, "success", Constant.STATE_SUCCESS, null);
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnModel(e.getMessage(), "error", "500", null);
        }
    }



}
