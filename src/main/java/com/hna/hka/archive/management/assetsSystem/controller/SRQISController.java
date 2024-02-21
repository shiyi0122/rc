package com.hna.hka.archive.management.assetsSystem.controller;

import com.github.pagehelper.PageInfo;
import com.hna.hka.archive.management.assetsSystem.model.SysRobotQualityInspectionDetail;
import com.hna.hka.archive.management.assetsSystem.model.SysRobotQualityInspectionStandard;
import com.hna.hka.archive.management.assetsSystem.service.SRQISService;
import com.hna.hka.archive.management.system.util.Constant;
import com.hna.hka.archive.management.system.util.PublicUtil;
import com.hna.hka.archive.management.system.util.ReturnModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 * @program: rc
 * @description: 质检标准
 * @author: zhaoxianglong
 * @create: 2021-09-16 09:56
 **/
@Api(tags = "质检标准")
@RestController
@RequestMapping("/system/srqis")
public class SRQISController extends PublicUtil {

    @Autowired
    SRQISService service;

    @ApiOperation("列表信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name" , value = "检验项名称"),
            @ApiImplicitParam(name = "beginTime" , value = "起始时间"),
            @ApiImplicitParam(name = "endTime" , value = "截止时间"),
            @ApiImplicitParam(name = "pageNum" , value = "页码"),
            @ApiImplicitParam(name = "pageSize" , value = "每页条数"),
    })
    @CrossOrigin
    @GetMapping("/list")
    public ReturnModel list(String name , String beginTime , String endTime , Integer pageNum , Integer pageSize) {
        try {
            PageInfo<SysRobotQualityInspectionStandard> list = service.list(name , beginTime , endTime , pageNum , pageSize);
            return new ReturnModel(list , "获取列表信息成功" , Constant.STATE_SUCCESS , null);
        } catch (Exception e) {
            logger.error("获取列表信息失败", e);
            return new ReturnModel(e.getMessage(), "获取列表信息失败", "500", null);
        }
    }

    @ApiOperation("质检标准添加")
    @CrossOrigin
    @PostMapping("/add")
    public ReturnModel add(@RequestBody SysRobotQualityInspectionStandard standard){
        try {
            Integer result = service.add(standard);
            if (result > 0) {
                return new ReturnModel(result , "质检标准添加成功" , Constant.STATE_SUCCESS , null);
            } else {
                return new ReturnModel("原因未知" , "质检标准添加失败" , Constant.STATE_FAILURE , null);
            }
        } catch (Exception e){
            logger.error("质检标准添加失败" , e);
            return new ReturnModel(e.getMessage() , "质检标准添加失败" , Constant.STATE_FAILURE , null);
        }
    }

    @ApiOperation("质检标准修改")
    @CrossOrigin
    @PostMapping("/edit")
    public ReturnModel edit(@RequestBody SysRobotQualityInspectionStandard standard){
        try {
            Integer result = service.edit(standard);
            if (result > 0) {
                return new ReturnModel(result , "质检标准修改成功" , Constant.STATE_SUCCESS , null);
            } else {
                return new ReturnModel("当前数据不存在或已被删除" , "质检标准修改失败" , Constant.STATE_FAILURE , null);
            }
        } catch (Exception e){
            logger.error("质检标准修改失败" , e);
            return new ReturnModel(e.getMessage() , "质检标准修改失败" , Constant.STATE_FAILURE , null);
        }
    }

    @ApiOperation("质检标准删除")
    @ApiImplicitParam(name = "id" , value = "id")
    @CrossOrigin
    @DeleteMapping("/delete")
    public ReturnModel delete(Long id){
        try {
            Integer result = service.delete(id);
            if (result > 0) {
                return new ReturnModel(result , "质检标准删除成功" , Constant.STATE_SUCCESS , null);
            } else {
                return new ReturnModel("当前数据不存在或已被删除" , "质检标准删除失败" , Constant.STATE_FAILURE , null);
            }
        } catch (Exception e){
            logger.error("质检标准删除失败" , e);
            return new ReturnModel(e.getMessage() , "质检标准删除失败" , Constant.STATE_FAILURE , null);
        }
    }

    @ApiOperation("具体检测信息列表")
    @ApiImplicitParam(name = "id" , value = "id")
    @CrossOrigin
    @GetMapping("/showDetail")
    public ReturnModel showDetail(Long id) {
        try {
            List<SysRobotQualityInspectionDetail> list = service.showDetail(id);
            return new ReturnModel(list , "获取具体检测信息成功" , Constant.STATE_SUCCESS , null);
        } catch (Exception e) {
            logger.error("获取具体检测信信息失败", e);
            return new ReturnModel(e.getMessage(), "获取具体检测信信息失败", "500", null);
        }
    }

    @ApiOperation("具体检测信息添加")
    @CrossOrigin
    @PostMapping("/addDetail")
    public ReturnModel addDetail(@RequestBody SysRobotQualityInspectionDetail detail){
        try {
            Integer result = service.addDetail(detail);
            if (result > 0) {
                return new ReturnModel(result , "具体检测信息添加成功" , Constant.STATE_SUCCESS , null);
            } else {
                return new ReturnModel("原因未知" , "具体检测信息添加失败" , Constant.STATE_FAILURE , null);
            }
        } catch (Exception e){
            logger.error("具体检测信息添加失败" , e);
            return new ReturnModel(e.getMessage() , "具体检测信息添加失败" , Constant.STATE_FAILURE , null);
        }
    }

    @ApiOperation("具体检测信息修改")
    @CrossOrigin
    @PostMapping("/editDetail")
    public ReturnModel editDetail(@RequestBody SysRobotQualityInspectionDetail detail){
        try {
            Integer result = service.editDetail(detail);
            if (result > 0) {
                return new ReturnModel(result , "具体检测信息修改成功" , Constant.STATE_SUCCESS , null);
            } else {
                return new ReturnModel("当前数据不存在或已被删除" , "具体检测信息修改失败" , Constant.STATE_FAILURE , null);
            }
        } catch (Exception e){
            logger.error("具体检测信息修改失败" , e);
            return new ReturnModel(e.getMessage() , "具体检测信息修改失败" , Constant.STATE_FAILURE , null);
        }
    }

    @ApiOperation("具体检测信息删除")
    @ApiImplicitParam(name = "id" , value = "id")
    @CrossOrigin
    @DeleteMapping("/deleteDetail")
    public ReturnModel deleteDetail(Long id , Long standardId){
        try {
            Integer result = service.deleteDetail(id , standardId);
            if (result > 0) {
                return new ReturnModel(result , "具体检测信息删除成功" , Constant.STATE_SUCCESS , null);
            } else {
                return new ReturnModel("当前数据不存在或已被删除" , "具体检测信息删除失败" , Constant.STATE_FAILURE , null);
            }
        } catch (Exception e){
            logger.error("具体检测信息删除失败" , e);
            return new ReturnModel(e.getMessage() , "具体检测信息删除失败" , Constant.STATE_FAILURE , null);
        }
    }


    @ApiOperation("根据机器人编码获取质检标准")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "robotCode" , value = "机器人编码"),
            @ApiImplicitParam(name = "type" , value = "1:工厂质检;2:景区验收")
    })
    @GetMapping("/getMsgByRobotCode")
    public ReturnModel getMsgByRobotCode(String robotCode , Integer type){
        try {
            HashMap map = service.getMsgByRobotCode(robotCode ,type);
            return new ReturnModel(map , "success" , Constant.STATE_SUCCESS , null);
        } catch (Exception e){
            e.printStackTrace();
            return new ReturnModel(e.getMessage() , "error" , "500" , null);
        }
    }


    @ApiOperation("质检标准下拉项")
    @GetMapping("/getAll")
    public ReturnModel getAll(String type){
        try {
            List<SysRobotQualityInspectionStandard> list = service.getAll(type);
            return new ReturnModel(list , "获取列表信息成功" , Constant.STATE_SUCCESS , null);
        } catch (Exception e) {
            logger.error("获取列表信息失败", e);
            return new ReturnModel(e.getMessage(), "获取列表信息失败", "500", null);
        }
    }
}
