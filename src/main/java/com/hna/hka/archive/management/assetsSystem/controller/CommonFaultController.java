package com.hna.hka.archive.management.assetsSystem.controller;

import com.github.pagehelper.PageInfo;
import com.hna.hka.archive.management.assetsSystem.model.CommonFault;
import com.hna.hka.archive.management.assetsSystem.service.CommonFaultService;
import com.hna.hka.archive.management.system.util.Constant;
import com.hna.hka.archive.management.system.util.PublicUtil;
import com.hna.hka.archive.management.system.util.ReturnModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @program: rc
 * @description: 常见故障
 * @author: zhaoxianglong
 * @create: 2021-09-16 16:30
 **/
@Api(tags = "常见故障")
@RestController
@RequestMapping("/system/common_fault")
public class CommonFaultController extends PublicUtil {

    @Autowired
    private CommonFaultService service;

    @ApiOperation("常见故障列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "title", value = "问题标题"),
            @ApiImplicitParam(name = "applicableModel", value = "机器型号"),
            @ApiImplicitParam(name = "pageNum", value = "页码"),
            @ApiImplicitParam(name = "pageSize", value = "每页条数")
    })
    @CrossOrigin
    @GetMapping("/list")
    public ReturnModel list(String title, String applicableModel, Integer pageNum, Integer pageSize) {
        try {
            PageInfo<CommonFault> list = service.list(title, applicableModel, pageNum, pageSize);
            return new ReturnModel(list, "获取常见故障列表故障成功", Constant.STATE_SUCCESS, null);
        } catch (Exception e) {
            logger.error("获取常见故障列表故障失败", e);
            return new ReturnModel(e.getMessage(), "获取常见故障列表故障失败", "500", null);
        }
    }

    @ApiOperation("详情")
    @ApiImplicitParam(name = "id", value = "主键id")
    @GetMapping("/detail")
    public ReturnModel detail(Long id) {
        try {
            CommonFault commonFault = service.detail(id);
            return new ReturnModel(commonFault, "获取常见故障详情故障成功", Constant.STATE_SUCCESS, null);
        } catch (Exception e) {
            logger.error("获取常见故障列表故障失败", e);
            return new ReturnModel(e.getMessage(), "获取常见故障列表故障失败", "500", null);
        }
    }

    @ApiOperation("常见故障添加")
    @CrossOrigin
    @PostMapping("/add")
    public ReturnModel add(@RequestBody CommonFault fault) {
        try {
            Integer result = service.add(fault);
            if (result > 0) {
                return new ReturnModel(result, "常见故障添加成功", Constant.STATE_SUCCESS, null);
            } else {
                return new ReturnModel("原因未知", "常见故障添加失败", Constant.STATE_FAILURE, null);
            }
        } catch (Exception e) {
            logger.error("常见故障添加失败", e);
            return new ReturnModel(e.getMessage(), "常见故障添加失败", "500", null);
        }
    }

    @ApiOperation("常见故障修改")
    @CrossOrigin
    @PostMapping("/edit")
    public ReturnModel edit(@RequestBody CommonFault fault) {
        try {
            Integer result = service.edit(fault);
            if (result > 0) {
                return new ReturnModel(result, "常见故障修改成功", Constant.STATE_SUCCESS, null);
            } else {
                return new ReturnModel("当前数据不存在或已被删除", "常见故障修改失败", Constant.STATE_FAILURE, null);
            }
        } catch (Exception e) {
            logger.error("常见故障修改失败", e);
            return new ReturnModel(e.getMessage(), "常见故障修改失败", "500", null);
        }
    }

    @ApiOperation("常见故障删除")
    @CrossOrigin
    @PostMapping("/delete")
    public ReturnModel delete(Long id) {
        try {
            Integer result = service.delete(id);
            if (result > 0) {
                return new ReturnModel(result, "常见故障删除成功", Constant.STATE_SUCCESS, null);
            } else {
                return new ReturnModel("当前数据不存在或已被删除", "常见故障删除失败", Constant.STATE_FAILURE, null);
            }
        } catch (Exception e) {
            logger.error("常见故障删除失败", e);
            return new ReturnModel(e.getMessage(), "常见故障删除失败", "500", null);
        }
    }

    @ApiOperation("常见故障下拉选")
    @GetMapping("commonFaultList")
    @ResponseBody
    public ReturnModel commonFaultList() {
        ReturnModel returnModel = new ReturnModel();
        List<CommonFault> list = service.commonFaultList();
        returnModel.setData(list);
        returnModel.setMsg("success");
        returnModel.setState(Constant.STATE_SUCCESS);
        return returnModel;
    }
}