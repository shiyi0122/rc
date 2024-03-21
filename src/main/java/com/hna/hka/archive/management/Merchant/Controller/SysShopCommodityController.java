package com.hna.hka.archive.management.Merchant.Controller;

import com.github.pagehelper.PageInfo;
import com.hna.hka.archive.management.Merchant.model.SysShopCommodity;
import com.hna.hka.archive.management.Merchant.service.SysShopCommodityService;
import com.hna.hka.archive.management.system.util.Constant;
import com.hna.hka.archive.management.system.util.ReturnModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = "商品管理")
@RestController
@CrossOrigin
@RequestMapping("/Merchant/shopCommodity")
public class SysShopCommodityController {

    @Autowired
    private SysShopCommodityService sysShopCommodityService;

    @ApiOperation("查询商品列表")
    @PostMapping("/getShopCommodityList")
    public ReturnModel getShopCommodityList(SysShopCommodity sysShopCommodity) {
        ReturnModel returnModel = new ReturnModel();
        PageInfo<SysShopCommodity> shopCommodityList = sysShopCommodityService.getShopCommodityList(sysShopCommodity);
        returnModel.setData(shopCommodityList);
        returnModel.setMsg("查询成功！");
        returnModel.setState(Constant.STATE_SUCCESS);
        return returnModel;
    }

    @ApiOperation("新增商品")
    @PostMapping("/addShopCommodity")
    public ReturnModel addShopCommodity(SysShopCommodity sysShopCommodity) {
        ReturnModel returnModel = new ReturnModel();
        int result = sysShopCommodityService.addShopCommodity(sysShopCommodity);
        if (result > 0) {
            returnModel.setMsg("新增成功！");
            returnModel.setState(Constant.STATE_SUCCESS);
        } else {
            returnModel.setMsg("新增失败！");
            returnModel.setState(Constant.STATE_FAILURE);
        }
        return returnModel;
    }

    @ApiOperation("删除商品")
    @GetMapping("/deleteShopCommodity")
    public ReturnModel deleteShopCommodity(Long commodityId) {
        ReturnModel returnModel = new ReturnModel();
        int result = sysShopCommodityService.deleteShopCommodity(commodityId);
        if (result > 0) {
            returnModel.setMsg("删除成功！");
            returnModel.setState(Constant.STATE_SUCCESS);
        } else {
            returnModel.setMsg("删除失败！");
            returnModel.setState(Constant.STATE_FAILURE);
        }
        return returnModel;
    }

    @ApiOperation("修改商品")
    @PostMapping("/updateShopCommodity")
    public ReturnModel updateShopCommodity(SysShopCommodity sysShopCommodity) {
        ReturnModel returnModel = new ReturnModel();
        int result = sysShopCommodityService.updateShopCommodity(sysShopCommodity);
        if (result > 0) {
            returnModel.setMsg("修改成功！");
            returnModel.setState(Constant.STATE_SUCCESS);
        } else {
            returnModel.setMsg("修改失败！");
            returnModel.setState(Constant.STATE_FAILURE);
        }
        return returnModel;
    }
}


