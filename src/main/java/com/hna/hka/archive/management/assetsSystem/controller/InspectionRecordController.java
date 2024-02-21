package com.hna.hka.archive.management.assetsSystem.controller;

import com.github.pagehelper.PageInfo;
import com.hna.hka.archive.management.appSystem.model.vo.SysAppRobotQualityTesting;
import com.hna.hka.archive.management.assetsSystem.model.InspectionRecord;
import com.hna.hka.archive.management.assetsSystem.model.InspectionRecordDetail;
import com.hna.hka.archive.management.assetsSystem.model.InspectionRecordModel;
import com.hna.hka.archive.management.assetsSystem.model.SysRobotQualityInspectionDetail;
import com.hna.hka.archive.management.assetsSystem.service.InspectionRecordService;
import com.hna.hka.archive.management.assetsSystem.service.SRQISService;
import com.hna.hka.archive.management.system.util.Constant;
import com.hna.hka.archive.management.system.util.PublicUtil;
import com.hna.hka.archive.management.system.util.ReturnModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.TemplateEngine;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * @program: rc
 * @description: 机器人检验记录
 * @author: zhaoxianglong
 * @create: 2021-09-18 10:31
 **/
@Api(tags = "机器人检验记录")
@RestController
@RequestMapping("/inspection_record")
public class InspectionRecordController extends PublicUtil {

    @Autowired
    InspectionRecordService service;

    @Autowired
    private TemplateEngine engine;


    @ApiOperation("校验列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "robotCode" , value = "机器人编码"),
            @ApiImplicitParam(name = "startTime" , value = "起始时间"),
            @ApiImplicitParam(name = "endTime" , value = "截止时间"),
            @ApiImplicitParam(name = "type" , value = "校验类型:1-工厂质检;2-景区验收"),
            @ApiImplicitParam(name = "result" , value = "检验结果：0、不合格；1、合格"),
            @ApiImplicitParam(name = "pageNum" , value = "页码"),
            @ApiImplicitParam(name = "pageSize" , value = "每页条数"),
    })
    @CrossOrigin
    @GetMapping("/list")
    public ReturnModel list(String robotCode , String startTime , String endTime , Integer type , Integer result , Integer pageSize , Integer pageNum){
        try {
            PageInfo<InspectionRecord> records = service.list(robotCode , startTime , endTime , type , result , pageNum , pageSize);
            return new ReturnModel(records , "获取校验列表成功" , Constant.STATE_SUCCESS , null);
        } catch (Exception e) {
            logger.error("获取校验列表失败" , e);
            return new ReturnModel(e.getMessage() , "获取校验列表失败" , "500" , null);
        }
    }

    @ApiOperation("校验添加")
    @CrossOrigin
    @PostMapping("/add")
    public ReturnModel add(@RequestBody SysAppRobotQualityTesting record){
        try {
            Integer result = service.add(record);
            if (result > 0 ){
                return new ReturnModel(result , "校验添加成功" , Constant.STATE_SUCCESS , null);
            } else {
                return new ReturnModel("未知错误" , "校验添加失败" , Constant.STATE_FAILURE , null);
            }
        } catch (Exception e){
            logger.error("校验添加失败" , e);
            return new ReturnModel(e.getMessage() , "校验添加失败" , "500" , null);
        }
    }

    @ApiOperation("预览")
    @CrossOrigin
    @GetMapping("/preview")
    public ReturnModel preview(Long id){
        try {
            InspectionRecord record = service.preview(id);
            List<InspectionRecordDetail> details = service.showDetail(id);
            HashMap map = new HashMap();
            map.put("info" , record);
            map.put("details" , details);
            return new ReturnModel(map , "获取预览信息成功" , Constant.STATE_SUCCESS , null);
        } catch (Exception e){
            logger.error("获取预览信息失败" , e);
            return new ReturnModel(e.getMessage() , "获取预览信息失败" , "500" , null);
        }
    }

    @ApiOperation("导出")
    @CrossOrigin
    @GetMapping("/export")
    public void export(Long id  , HttpServletResponse response){
        InspectionRecordModel model = new InspectionRecordModel(engine, "pdf/index");
        InspectionRecord record = service.preview(id);
        List<InspectionRecordDetail> details = service.showDetail(id);
        model.setRecord(record);
        model.setDetails(details);
        try {
            model.downloadPdf(record.getRobotCode()+"质检报告",response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @ApiOperation("校验删除")
    @GetMapping("/delect")
    public ReturnModel delect(@RequestBody Long id){
        try {
            Integer delect = service.delect(id);
                return new ReturnModel(delect,"删除成功",Constant.STATE_SUCCESS,null);
        }catch (Exception e){
            logger.error("校验列表删除失败" , e);
            return new ReturnModel(e.getMessage() , "校验列表删除失败" , "500" , null);
        }
    }
}
