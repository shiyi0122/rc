package com.hna.hka.archive.management.Merchant.Controller;


import com.github.pagehelper.PageInfo;
import com.hna.hka.archive.management.Merchant.model.SysShopCurrentUser;
import com.hna.hka.archive.management.Merchant.service.SysShopCurrentUserService;
import com.hna.hka.archive.management.system.util.Constant;
import com.hna.hka.archive.management.system.util.ReturnModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = "商家或店员用户信息接口")
@RestController
@CrossOrigin
@RequestMapping("/Merchant/shopUser")
public class SysShopCurrentUserController {

    @Autowired
    private SysShopCurrentUserService sysShopCurrentUserService;

    @ApiOperation("获取商家或店员用户信息")
    @PostMapping("/getShopUser")
    public ReturnModel getShopUser(SysShopCurrentUser sysShopCurrentUser) {
        ReturnModel returnModel = new ReturnModel();
        PageInfo<SysShopCurrentUser> shopUserList = sysShopCurrentUserService.getShopUserList(sysShopCurrentUser);
        returnModel.setData(shopUserList);
        returnModel.setMsg("查询成功！");
        returnModel.setState(Constant.STATE_SUCCESS);
        return returnModel;
    }

    @ApiOperation("新增商家或店员用户信息")
    @PostMapping("/addShopUser")
    public ReturnModel addShopUser(SysShopCurrentUser sysShopCurrentUser) {
        ReturnModel returnModel = new ReturnModel();
        int result = sysShopCurrentUserService.addShopUser(sysShopCurrentUser);
        if (result > 0) {
            returnModel.setMsg("新增成功！");
            returnModel.setState(Constant.STATE_SUCCESS);
        } else {
            returnModel.setMsg("新增失败！");
            returnModel.setState(Constant.STATE_FAILURE);
        }
        return returnModel;
    }

    @ApiOperation("修改商家或店员用户信息")
    @PostMapping("/updateShopUser")
    public ReturnModel updateShopUser(SysShopCurrentUser sysShopCurrentUser) {
        ReturnModel returnModel = new ReturnModel();
        int result = sysShopCurrentUserService.updateShopUser(sysShopCurrentUser);
        if (result > 0) {
            returnModel.setMsg("修改成功！");
            returnModel.setState(Constant.STATE_SUCCESS);
        } else {
            returnModel.setMsg("修改失败！");
            returnModel.setState(Constant.STATE_FAILURE);
        }
        return returnModel;
    }

    @ApiOperation("删除商家或店员用户信息")
    @GetMapping("/deleteShopUser")
    public ReturnModel deleteShopUser(Long shopUserId, String shopId) {
        ReturnModel returnModel = new ReturnModel();
        int result = sysShopCurrentUserService.deleteShopUser(shopUserId,shopId);
        if (result > 0) {
            returnModel.setMsg("删除成功！");
            returnModel.setState(Constant.STATE_SUCCESS);
        } else {
            returnModel.setMsg("删除失败！");
            returnModel.setState(Constant.STATE_FAILURE);
        }
        return returnModel;
    }
}
