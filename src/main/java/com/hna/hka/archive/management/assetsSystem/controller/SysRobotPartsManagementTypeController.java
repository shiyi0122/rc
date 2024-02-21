package com.hna.hka.archive.management.assetsSystem.controller;

import com.hna.hka.archive.management.assetsSystem.model.SysRobotPartsManagementType;
import com.hna.hka.archive.management.assetsSystem.model.SysRobotPeripheral;
import com.hna.hka.archive.management.assetsSystem.service.SysRobotPartsManagementTypeService;
import com.hna.hka.archive.management.system.util.Constant;
import com.hna.hka.archive.management.system.util.PageDataResult;
import com.hna.hka.archive.management.system.util.ReturnModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author zhang
 * @Date 2022/12/11 16:43
 */
@Slf4j
@Api(tags = "配件类型")
@RestController
@RequestMapping("/system/partsManagementType")
public class SysRobotPartsManagementTypeController {

    @Autowired
    SysRobotPartsManagementTypeService sysRobotPartsManagementTypeService;

    @RequestMapping("/addPartsManagementType")
    @ApiOperation("新增")
    public ReturnModel addRobotPartsManagementType(SysRobotPartsManagementType sysRobotPartsManagementType){

        int result= sysRobotPartsManagementTypeService.addRobotPartsManagementType(sysRobotPartsManagementType);
        if (result>0){
            return new ReturnModel(result,"添加成功", Constant.STATE_SUCCESS,null);
        }else{
            return new ReturnModel(result,"添加失败",Constant.STATE_FAILURE,null);
        }
    }

    @RequestMapping("/editPartsManagementType")
    @ApiOperation("修改")
    public ReturnModel editPartsManagementType(SysRobotPartsManagementType sysRobotPartsManagementType){

        int result= sysRobotPartsManagementTypeService.editRobotPartsManagementType(sysRobotPartsManagementType);
        if (result>0){
            return new ReturnModel(result,"修改成功", Constant.STATE_SUCCESS,null);
        }else{
            return new ReturnModel(result,"修改失败",Constant.STATE_FAILURE,null);
        }

    }

    @RequestMapping("/delPartsManagementType")
    @ApiOperation("删除")
    public ReturnModel delPartsManagementType(Long typeId){

        int result= sysRobotPartsManagementTypeService.delRobotPartsManagementType(typeId);
        if (result>0){
            return new ReturnModel(result,"删除成功", Constant.STATE_SUCCESS,null);
        }else{
            return new ReturnModel(result,"删除失败",Constant.STATE_FAILURE,null);
        }

    }

    @GetMapping("/getPartsManagementTypeList")
    @ApiOperation("列表")
    public PageDataResult getPartsManagementTypeList(SysRobotPartsManagementType sysRobotPartsManagementType,Integer pageNum,Integer pageSize){

        PageDataResult result= sysRobotPartsManagementTypeService.list(sysRobotPartsManagementType,pageNum,pageSize);
        return result;

    }

    @PostMapping("/getPartsManagementTypeDropDown")
    @ApiOperation("下拉选")
    public ReturnModel getPartsManagementTypeDropDown(){

        List<SysRobotPartsManagementType> list = sysRobotPartsManagementTypeService.getPartsManagementTypeDropDown();
        return new ReturnModel(list,"查询成功", Constant.STATE_SUCCESS,null);

    }




}
