package com.hna.hka.archive.management.assetsSystem.controller;

import com.github.pagehelper.PageInfo;
import com.hna.hka.archive.management.assetsSystem.model.ProductionInfo;
import com.hna.hka.archive.management.assetsSystem.service.ProductionInfoService;
import com.hna.hka.archive.management.system.model.SysScenicSpotBinding;
import com.hna.hka.archive.management.system.util.Constant;
import com.hna.hka.archive.management.system.util.PublicUtil;
import com.hna.hka.archive.management.system.util.ReturnModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @program: rc
 * @description: 生产批次
 * @author: zhaoxianglong
 * @create: 2021-09-17 13:36
 **/
@Api(tags = "生产批次")
@RestController
@RequestMapping("/system/production_info")
public class ProductionInfoController extends PublicUtil {

    @Autowired
    ProductionInfoService service;

    @ApiOperation("生产批次列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name" , value = "生产批次"),
            @ApiImplicitParam(name = "factory" , value = "工厂名称"),
            @ApiImplicitParam(name = "pageNum" , value = "页码"),
            @ApiImplicitParam(name = "pageSize" , value = "每页条数")
    })
    @CrossOrigin
    @GetMapping("/list")
    public ReturnModel list(String name , String factory , Integer pageNum , Integer pageSize){
        try {
            PageInfo<ProductionInfo> info = service.list(name , factory , pageNum , pageSize);
            return new ReturnModel(info , "获取生产批次列表成功" , Constant.STATE_SUCCESS , null);
        } catch (Exception e){
            logger.error("获取生产批次列表失败" , e);
            return new ReturnModel(e.getMessage() , "获取生产批次列表失败" , "500" , null);
        }
    }


    @ApiOperation("添加")
    @CrossOrigin
    @PostMapping("/add")
    public ReturnModel add(@RequestBody ProductionInfo info){
        try {
            Integer result = service.add(info);
            if (result > 0) {
                return new ReturnModel(result , "生产批次添加成功" , Constant.STATE_SUCCESS , null);
            } else {
                return new ReturnModel("原因未知" , "生产批次添加失败" , Constant.STATE_FAILURE , null);
            }
        } catch (Exception e){
            logger.error("生产批次添加失败" , e);
            return new ReturnModel(e.getMessage() , "生产批次添加失败" , Constant.STATE_FAILURE , null);
        }
    }

    @ApiOperation("生产批次修改")
    @CrossOrigin
    @PostMapping("/edit")
    public ReturnModel edit(@RequestBody ProductionInfo info){
        try {
            Integer result = service.edit(info);
            if (result > 0) {
                return new ReturnModel(result , "生产批次修改成功" , Constant.STATE_SUCCESS , null);
            } else {
                return new ReturnModel("当前数据不存在或已被删除" , "生产批次修改失败" , Constant.STATE_FAILURE , null);
            }
        } catch (Exception e){
            logger.error("生产批次修改失败" , e);
            return new ReturnModel(e.getMessage() , "生产批次修改失败" , Constant.STATE_FAILURE , null);
        }
    }

    @ApiOperation("生产批次删除")
    @ApiImplicitParam(name = "id" , value = "id")
    @CrossOrigin
    @DeleteMapping("/delete")
    public ReturnModel delete(Long id){
        try {
            Integer result = service.delete(id);
            if (result > 0) {
                return new ReturnModel(result , "生产批次删除成功" , Constant.STATE_SUCCESS , null);
            } else {
                return new ReturnModel("当前数据不存在或已被删除" , "生产批次删除失败" , Constant.STATE_FAILURE , null);
            }
        } catch (Exception e){
            logger.error("生产批次删除失败" , e);
            return new ReturnModel(e.getMessage() , "生产批次删除失败" , Constant.STATE_FAILURE , null);
        }
    }


    @ApiOperation("发货工厂列表")
    @CrossOrigin
    @GetMapping("/spotFactoryList")
    public ReturnModel spotFactoryList(){
        try {
            PageInfo<SysScenicSpotBinding> info = service.spotFactoryList();
            return new ReturnModel(info , "获取发货工厂列表成功" , Constant.STATE_SUCCESS , null);
        } catch (Exception e){
            logger.error("获取发货工厂列表失败" , e);
            return new ReturnModel(e.getMessage() , "获取发货工厂列表失败" , "500" , null);
        }
    }


}
