package com.hna.hka.archive.management.assetsSystem.controller;

import com.hna.hka.archive.management.assetsSystem.model.CooperativeCompany;
import com.hna.hka.archive.management.assetsSystem.service.CooperativeCompanyService;
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
 * @description: 合作公司
 * @author: zhaoxianglong
 * @create: 2021-09-08 13:57
 **/
@Api(tags="合作公司")
@RequestMapping("/system/cooperative_company")
@RestController
public class CooperativeCompanyController extends PublicUtil {

    @Autowired
    CooperativeCompanyService service;

    /**
     * 查询列表
     * @param pageNum 页码
     * @param pageSize 每页查询数据
     * @param name 公司名称
     * @return com.hna.hka.archive.management.system.util.ReturnModel
     */
    @ApiOperation("查询列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum" , value = "页码"),
            @ApiImplicitParam(name = "pageSize" , value = "每页条数"),
            @ApiImplicitParam(name = "name" , value = "公司名称(模糊查询)"),
    })
    @GetMapping(value = "/list")
    @CrossOrigin
    public ReturnModel list(Integer pageNum , Integer pageSize , String name){
        try {
            List<CooperativeCompany> companyList =  service.list(pageNum , pageSize , name);
            Integer count = service.findCount(name);
            return new ReturnModel(companyList , "请求合作公司列表成功" , Constant.STATE_SUCCESS , null , count);
        } catch (Exception e) {
            logger.error("请求合作公司列表失败" , e);
            return new ReturnModel(e.getMessage() , "请求合作公司列表失败" , Constant.STATE_FAILURE , null );
        }
    }


    /**
     * 合作公司新增
     * @param cooperativeCompany 新增实体类
     * @return com.hna.hka.archive.management.system.util.ReturnModel
     */
    @ApiOperation("新增")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bank" , value = "开户行"),
            @ApiImplicitParam(name = "collectionAccount" , value = "收款账户(必填)"),
            @ApiImplicitParam(name = "collectionAccountNumber" , value = "收款账号"),
            @ApiImplicitParam(name = "companyId" , value = "合作公司id(不填)"),
            @ApiImplicitParam(name = "companyName" , value = "合作公司名称(必填)"),
            @ApiImplicitParam(name = "createTime" , value = "创建时间(不填)"),
            @ApiImplicitParam(name = "notes" , value = "备注"),
    })
    @CrossOrigin
    @PostMapping(value = "/add")
    public ReturnModel add(@RequestBody CooperativeCompany cooperativeCompany){
        try {
            Integer result = service.add(cooperativeCompany);
            if (result > 0) {
                return new ReturnModel(result, "合作公司新增成功", Constant.STATE_SUCCESS, null);
            } else {
                return new ReturnModel(result, "合作公司新增失败,原因未知", Constant.STATE_FAILURE, null);
            }
        } catch (Exception e) {
            logger.error("合作公司新增失败" , e);
            return new ReturnModel(e.getMessage() , "合作公司新增失败" , Constant.STATE_FAILURE , null);
        }
    }

    /**
     * 合作公司修改
     * @param cooperativeCompany 修改实体类
     * @return com.hna.hka.archive.management.system.util.ReturnModel
     */
    @ApiOperation("修改")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bank" , value = "开户行"),
            @ApiImplicitParam(name = "collectionAccount" , value = "收款账户(必填)"),
            @ApiImplicitParam(name = "collectionAccountNumber" , value = "收款账号"),
            @ApiImplicitParam(name = "companyId" , value = "合作公司id(必填)"),
            @ApiImplicitParam(name = "companyName" , value = "合作公司名称(必填)"),
            @ApiImplicitParam(name = "createTime" , value = "创建时间(不填)"),
            @ApiImplicitParam(name = "notes" , value = "备注"),
    })
    @CrossOrigin
    @PostMapping(value = "/edit")
    public ReturnModel edit(@RequestBody CooperativeCompany cooperativeCompany){
        try {
            Integer result = service.edit(cooperativeCompany);
            if (result > 0) {
                return new ReturnModel(result , "合作公司修改成功" , Constant.STATE_SUCCESS , null);
            } else {
                return new ReturnModel("合作公司不存在或已经被删除" , "合作公司修改失败" , Constant.STATE_SUCCESS , null);
            }
        } catch (Exception e) {
            logger.error("合作公司修改失败" , e);
            return new ReturnModel(e.getMessage() , "合作公司修改失败" , Constant.STATE_FAILURE , null);
        }
    }

    /**
     * 合作公司删除
     * @param id 合作公司id
     * @return com.hna.hka.archive.management.system.util.ReturnModel
     */
    @ApiOperation("删除")
    @ApiImplicitParam(name = "id" , value = "合作公司id(必填)")
    @CrossOrigin
    @DeleteMapping("/delete")
    public ReturnModel delete(Long id){
        try {
            Integer result = service.delete(id);
            if (result > 0) {
                return new ReturnModel(result , "合作公司删除成功" , Constant.STATE_SUCCESS , null);
            } else {
                return new ReturnModel("合作公司不存在或已经被删除" , "合作公司删除失败" , Constant.STATE_SUCCESS , null);
            }
        } catch (Exception e) {
            logger.error("合作公司删除失败" , e);
            return new ReturnModel(e.getMessage() , "合作公司删除失败" , Constant.STATE_FAILURE , null);
        }
    }

}
