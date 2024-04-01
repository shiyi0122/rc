package com.hna.hka.archive.management.Merchant.Controller;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.github.pagehelper.PageInfo;
import com.hna.hka.archive.management.Merchant.model.OrderVo;
import com.hna.hka.archive.management.Merchant.model.SysShop;
import com.hna.hka.archive.management.Merchant.service.SysShopService;
import com.hna.hka.archive.management.assetsSystem.model.ScenicSpot;
import com.hna.hka.archive.management.system.model.SysOrder;
import com.hna.hka.archive.management.system.model.SysScenicSpotBinding;
import com.hna.hka.archive.management.system.util.Constant;
import com.hna.hka.archive.management.system.util.FromCityToProvince;
import com.hna.hka.archive.management.system.util.PublicUtil;
import com.hna.hka.archive.management.system.util.ReturnModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@Api(tags = "商店相关")
@RestController
@CrossOrigin
@RequestMapping("/Merchant/sysShop")
public class SysShopController extends PublicUtil {

    @Autowired
    private SysShopService sysShopService;

    // 验证手机号的正则表达式
    private static final String PHONE_REGEX = "^1[3456789]\\d{9}$";
    //验证经纬度的正则表达式
    private static final String REGEX_REGEX = "^[-+]?\\d{1,3}\\.\\d+$";

    @ApiOperation("查询商店列表")
    @PostMapping("/getShopList")
    public ReturnModel getShopList(SysShop sysShop) {
        ReturnModel returnModel = new ReturnModel();
        PageInfo<SysShop> shopList = sysShopService.getShopList(sysShop);
        returnModel.setData(shopList);
        returnModel.setMsg("查询成功！");
        returnModel.setState(Constant.STATE_SUCCESS);
        return returnModel;
    }

    @ApiOperation("添加商店")
    @PostMapping("/addShop")
    public ReturnModel addShop(SysShop sysShop) {
        ReturnModel returnModel = new ReturnModel();
        //验证商店手机号
        Pattern pattern = Pattern.compile(PHONE_REGEX);
        if (!pattern.matcher(sysShop.getShopPhone()).matches()) {
            returnModel.setMsg("手机号格式错误！");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
        //验证商店GPS
        String[] coordinates = sysShop.getShopGps().split(",");
        if (coordinates.length != 2) {
            returnModel.setMsg("经纬度格式错误！");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
        // 去除空白字符
        String latitude = coordinates[0].trim();
        String longitude = coordinates[1].trim();
        // 经度范围为-180到180，纬度范围为-90到90
        Pattern pattern1 = Pattern.compile(REGEX_REGEX);
        if (!pattern1.matcher(latitude).matches() || !pattern1.matcher(longitude).matches()) {
            returnModel.setMsg("经纬度格式错误！");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }

        int result = sysShopService.addShop(sysShop);
        if (result > 0) {
            returnModel.setMsg("添加成功！");
            returnModel.setState(Constant.STATE_SUCCESS);
        } else {
            returnModel.setMsg("添加失败！");
            returnModel.setState(Constant.STATE_FAILURE);
        }
        return returnModel;
    }

    @ApiOperation("修改商店")
    @PostMapping("/updateShop")
    public ReturnModel updateShop(SysShop sysShop) {
        ReturnModel returnModel = new ReturnModel();
        int result = sysShopService.updateShop(sysShop);
        if (result > 0) {
            returnModel.setMsg("修改成功！");
            returnModel.setState(Constant.STATE_SUCCESS);
        } else {
            returnModel.setMsg("修改失败！");
            returnModel.setState(Constant.STATE_FAILURE);
        }
        return returnModel;
    }

    @ApiOperation("删除商店")
    @GetMapping("/deleteShop")
    public ReturnModel deleteShop(Long shopId) {
        ReturnModel returnModel = new ReturnModel();
        int result = sysShopService.deleteShop(shopId);
        if (result > 0) {
            returnModel.setMsg("删除成功！");
            returnModel.setState(Constant.STATE_SUCCESS);
        } else {
            returnModel.setMsg("删除失败！");
            returnModel.setState(Constant.STATE_FAILURE);
        }
        return returnModel;
    }

    @ApiOperation("查询景区")
    @GetMapping("/getScenicList")
    public ReturnModel getScenicList() {
        ReturnModel returnModel = new ReturnModel();
        List<ScenicSpot> scenicList = sysShopService.getScenicList();
        returnModel.setData(scenicList);
        returnModel.setMsg("查询成功！");
        returnModel.setState(Constant.STATE_SUCCESS);
        return returnModel;
    }

    @ApiOperation("根据城市和景区查询列表")
    @GetMapping("/queryScenicSpotLists")
    public ReturnModel queryScenicSpotLists(String scenicSpotName, String city) {
        ReturnModel returnModel = new ReturnModel();
        Map<String, Object> search = new HashMap<>();
        try {
            String objectProvinces = FromCityToProvince.findObjectProvinces(city);//截取“市”
            search.put("scenicSpotName", scenicSpotName);
            search.put("scenicSpotFname", objectProvinces);
            List<SysScenicSpotBinding> sysScenicSpot = sysShopService.queryScenicSpotList(search);
            returnModel.setData(sysScenicSpot);
            returnModel.setMsg("成功获取景区列表！");
            returnModel.setState(Constant.STATE_SUCCESS);
            return returnModel;
        } catch (Exception e) {
            logger.info("queryScenicSpotLists", e);
            returnModel.setData("");
            returnModel.setMsg("获取景区列表失败！");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    @PostMapping("/importExcel")
    public ReturnModel importExcel(@RequestPart("file") MultipartFile file) throws Exception {
        ReturnModel returnModel = new ReturnModel();
        try {
            ImportParams params = new ImportParams();
            params.setHeadRows(1);
            List<OrderVo> importExcel = ExcelImportUtil.importExcel(file.getInputStream(), OrderVo.class, params);
            for (OrderVo orderVo:importExcel){
                SysOrder sysOrder = new SysOrder();
                sysOrder.setOrderNumber(orderVo.getOrderNumber());
                sysOrder.setReasonsRefunds(orderVo.getReasonsRefunds());
                sysShopService.updateByOrderNumber(sysOrder);
            }
            returnModel.setMsg("导入成功！");
            returnModel.setState(Constant.STATE_SUCCESS);
        } catch (Exception e) {
            throw new RuntimeException("导入失败！", e);
        }
        return returnModel;
    }
}
