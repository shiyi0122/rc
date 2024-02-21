package com.hna.hka.archive.management.assetsSystem.controller;

import com.github.pagehelper.PageInfo;
import com.hna.hka.archive.management.assetsSystem.model.Address;
import com.hna.hka.archive.management.assetsSystem.model.SysRobotQualityInspectionStandard;
import com.hna.hka.archive.management.assetsSystem.service.AddressService;
import com.hna.hka.archive.management.managerApp.model.SysAppUsers;
import com.hna.hka.archive.management.system.model.SysRobotGPS;
import com.hna.hka.archive.management.system.util.Constant;
import com.hna.hka.archive.management.system.util.PublicUtil;
import com.hna.hka.archive.management.system.util.RedisUtil;
import com.hna.hka.archive.management.system.util.ReturnModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @program: rc
 * @description: 地址管理
 * @author: zhaoxianglong
 * @create: 2021-09-22 16:38
 **/
@Api(tags = "地址管理")
@RestController
@RequestMapping("/system/address")
public class AddressController extends PublicUtil {

    @Autowired
    AddressService service;

    @Autowired
    RedisUtil redisUtil;

    @ApiOperation("地址列表")
    @CrossOrigin
    @GetMapping("/list")
    public ReturnModel list(String spotId , String name , String phone , Integer type , @NotNull Integer pageNum , @NotNull Integer pageSize){
        try {
            PageInfo<Address> info = service.list(spotId , name , phone , type , pageNum , pageSize);
            return new ReturnModel(info , "获取地址列表成功" , Constant.STATE_SUCCESS , null);
        } catch (Exception e){
            logger.error("获取地址列表失败" , e);
            return new ReturnModel(e.getMessage() , "获取地址列表失败" , Constant.STATE_SUCCESS , null);
        }
    }

    @ApiOperation("地址添加")
    @CrossOrigin
    @PostMapping("/add")
    public ReturnModel add(@RequestBody Address address){
        try {
            Integer result = service.add(address);
            if (result > 0) {
                return new ReturnModel(result , "地址添加成功" , Constant.STATE_SUCCESS , null);
            } else {
                return new ReturnModel("原因未知" , "地址添加失败" , Constant.STATE_FAILURE , null);
            }
        } catch (Exception e){
            logger.error("地址添加失败" , e);
            return new ReturnModel(e.getMessage() , "地址添加失败" , Constant.STATE_FAILURE , null);
        }
    }

    @ApiOperation("地址修改")
    @CrossOrigin
    @PostMapping("/edit")
    public ReturnModel edit(@RequestBody Address address){
        try {
            Integer result = service.edit(address);
            if (result > 0) {
                return new ReturnModel(result , "地址修改成功" , Constant.STATE_SUCCESS , null);
            } else {
                return new ReturnModel("当前数据不存在或已被删除" , "地址修改失败" , Constant.STATE_FAILURE , null);
            }
        } catch (Exception e){
            logger.error("地址修改失败" , e);
            return new ReturnModel(e.getMessage() , "地址修改失败" , Constant.STATE_FAILURE , null);
        }
    }

    @ApiOperation("地址删除")
    @ApiImplicitParam(name = "id" , value = "id")
    @CrossOrigin
    @DeleteMapping("/delete")
    public ReturnModel delete(@NotNull Long id){
        try {
            Integer result = service.delete(id);
            if (result > 0) {
                return new ReturnModel(result , "地址删除成功" , Constant.STATE_SUCCESS , null);
            } else {
                return new ReturnModel("当前数据不存在或已被删除" , "地址删除失败" , Constant.STATE_FAILURE , null);
            }
        } catch (Exception e){
            logger.error("地址删除失败" , e);
            return new ReturnModel(e.getMessage() , "地址删除失败" , Constant.STATE_FAILURE , null);
        }
    }

    @ApiOperation("地址管理列表下拉项")
    @GetMapping("/getAll")
    public ReturnModel getAll(){
        try {
            List<Address> info = service.getAll();
            return new ReturnModel(info , "获取地址列表成功" , Constant.STATE_SUCCESS , null);
        } catch (Exception e){
            logger.error("获取地址列表失败" , e);
            return new ReturnModel(e.getMessage() , "获取地址列表失败" , Constant.STATE_FAILURE , null);
        }
    }

    @ApiOperation("app用户下拉")
    @GetMapping("/getAppUserBySpot")
    public ReturnModel getAppUserBySpot(Long spotId){
        try {
            List<SysAppUsers> info = service.getAppUserBySpot(spotId);
            return new ReturnModel(info , "success" , Constant.STATE_SUCCESS , null);
        } catch (Exception e){
            logger.error("error" , e);
            return new ReturnModel(e.getMessage() , "error" , Constant.STATE_FAILURE , null);
        }
    }

    @ApiOperation("地址导入")
    @PostMapping("/upload")
    public ReturnModel upload(MultipartFile file){
        try {
            service.upload(file);
            return new ReturnModel(null , "success" , Constant.STATE_SUCCESS , null);
        } catch (Exception e){
            logger.error("error" , e);
            return new ReturnModel(e.getMessage() , "error" , "500" , null);
        }
    }
    @ApiOperation("地址管理列表下拉项(新)")
    @GetMapping("/getFactoryAll")
    public ReturnModel getFactoryAll(String type){
        try {
            List<Address> info = service.getFactoryAll(type);
            return new ReturnModel(info , "获取工厂地址列表成功" , Constant.STATE_SUCCESS , null);
        } catch (Exception e){
            logger.error("获取工厂地址列表失败" , e);
            return new ReturnModel(e.getMessage() , "获取工厂地址列表失败" , Constant.STATE_FAILURE , null);
        }
    }


    @ApiOperation("地址下拉选(四级联动)")
    @GetMapping("/getFactoryFour")
    public ReturnModel getFactoryFour(){
        try {
            List<Address> info = service.getFactoryFour();
            return new ReturnModel(info , "查询下拉选成功" , Constant.STATE_SUCCESS , null);
        } catch (Exception e){
            logger.error("查询下拉选失败" , e);
            return new ReturnModel(e.getMessage() , "查询下拉选失败" , Constant.STATE_FAILURE , null);
        }
    }



    @ApiOperation("redisTest")
    @PostMapping("/redisTest")
    public ReturnModel redisTest(String robotCode){
        try {
            SysRobotGPS sysRobotGPS = new SysRobotGPS();
            Object result = redisUtil.get(robotCode);
            JSONObject robot = JSONObject.fromObject(result);
            Object sysRobot = JSONObject.toBean(robot, SysRobotGPS.class);
            JSONObject objJson = JSONObject.fromObject(sysRobot);
            sysRobotGPS = (SysRobotGPS) JSONObject.toBean(objJson,SysRobotGPS.class);
            return new ReturnModel(null , "success" , Constant.STATE_SUCCESS , null);
        } catch (Exception e){

            return new ReturnModel(e.getMessage() , "error" , "500" , null);
        }
    }

}
