package com.hna.hka.archive.management.assetsSystem.controller;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.github.pagehelper.PageInfo;
import com.hna.hka.archive.management.assetsSystem.model.GoodsStock;
import com.hna.hka.archive.management.assetsSystem.model.InventoryDetail;
import com.hna.hka.archive.management.assetsSystem.service.GoodsStockService;
import com.hna.hka.archive.management.system.util.Constant;
import com.hna.hka.archive.management.system.util.FileUtil;
import com.hna.hka.archive.management.system.util.PublicUtil;
import com.hna.hka.archive.management.system.util.ReturnModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * @program: rc
 * @description: 库存管理
 * @author: zhaoxianglong
 * @create: 2021-10-20 15:33
 **/
@Api(tags = "配件库存管理")
@CrossOrigin
@RestController
@RequestMapping("/system/goods_stock")
public class GoodsStockController extends PublicUtil {

    @Autowired
    GoodsStockService service;


    @ApiOperation("库存列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "managementId" , value = "配件Id"),
            @ApiImplicitParam(name = "spotId" , value = "仓库Id"),
            @ApiImplicitParam(name = "isDanger" , value = "是否超过阈值"),
            @ApiImplicitParam(name = "pageNum" , value = "页码"),
            @ApiImplicitParam(name = "pageSize" , value = "每页条数")
    })
    @GetMapping("/list")
    public ReturnModel list(Long managementId , Long spotId , Integer isDanger , Integer pageNum , Integer pageSize){
        try{
            PageInfo<GoodsStock> info = service.list(managementId , spotId , isDanger , pageNum , pageSize);
            return new ReturnModel(info , "success" , Constant.STATE_SUCCESS , null);
        }catch (Exception e){
            e.printStackTrace();
            return new ReturnModel(e.getMessage() , "error" , "500" , null);
        }
    }

    @ApiOperation("新增")
    @PostMapping("/add")
    public ReturnModel add(@RequestBody GoodsStock stock){
        try{
            Integer result = service.add(stock);
            if (result > 0){
                return new ReturnModel(result , "success" , Constant.STATE_SUCCESS , null);
            } else {
                return new ReturnModel(result , "fail" , Constant.STATE_FAILURE , null);
            }
        } catch (Exception e){
            e.printStackTrace();
            return new ReturnModel(e.getMessage() , "error" , "500" , null);
        }
    }

    @ApiOperation("修改")
    @PostMapping("/edit")
    public ReturnModel edit(@RequestBody GoodsStock stock){
        try{
            Integer result = service.edit(stock);
            if (result > 0){
                return new ReturnModel(result , "success" , Constant.STATE_SUCCESS , null);
            } else {
                return new ReturnModel(result , "fail" , Constant.STATE_FAILURE , null);
            }
        } catch (Exception e){
            e.printStackTrace();
            return new ReturnModel(e.getMessage() , "error" , "500" , null);
        }
    }

    @ApiOperation("删除")
    @DeleteMapping("/delete")
    public ReturnModel delete(Long id){
        try{
            Integer result = service.delete(id);
            if (result > 0){
                return new ReturnModel(result , "success" , Constant.STATE_SUCCESS , null);
            } else {
                return new ReturnModel(result , "fail" , Constant.STATE_FAILURE , null);
            }
        } catch (Exception e){
            e.printStackTrace();
            return new ReturnModel(e.getMessage() , "error" , "500" , null);
        }
    }

    @ApiOperation("仓库信息")
    @GetMapping("/getSpot")
    public ReturnModel getSpot(){
        try{
            List list = service.getSpot();
            return new ReturnModel(list , "success" , Constant.STATE_SUCCESS , null);
        }catch (Exception e){
            e.printStackTrace();
            return new ReturnModel(e.getMessage() , "error" , "500" , null);
        }
    }

    @ApiOperation("配件信息")
    @GetMapping("/getGoods")
    public ReturnModel getGoods(){
        try{
            List list = service.getGoods();
            return new ReturnModel(list , "success" , Constant.STATE_SUCCESS , null);
        }catch (Exception e){
            e.printStackTrace();
            return new ReturnModel(e.getMessage() , "error" , "500" , null);
        }
    }

    @ApiOperation("根据仓库和配件id获取仓库是否有这个配件")
    @GetMapping("/isGoodsIdSpotId")
    public ReturnModel isGoodsIdSpotId(Long spotId, Long  managementId){

        ReturnModel returnModel = new ReturnModel();

        int i = service.isGoodsIdSpotId(spotId,managementId);

        returnModel.setData(i);
        returnModel.setState(Constant.STATE_SUCCESS);
        returnModel.setMsg("获取成功");

        return returnModel;
    }


    @ApiOperation("列表下载")
    @GetMapping("/downloadExcel")
    public void downloadExcel(Long managementId , Long spotId , Integer isDanger , HttpServletResponse response) {
        List<GoodsStock> detailList = service.detailList(managementId, spotId, isDanger);
        String dateTime = DateFormatUtils.format(new Date(), "yyyyMMddHHmm");
            FileUtil.exportExcel(FileUtil.getWorkbook("配件库存信息", "库存信息", GoodsStock.class, detailList), "库存信息" + dateTime + ".xls", response);
    }


    @ApiOperation("导入配件库存数据")
    @RequestMapping("/importExcel")
    @ResponseBody
    public ReturnModel importExcelEnter(@RequestPart("file") MultipartFile multipartFile) throws Exception {
        ReturnModel returnModel = new ReturnModel();

        try {
            ImportParams params = new ImportParams();
            params.setTitleRows(1);
            params.setHeadRows(1);
            List<GoodsStock> result = ExcelImportUtil.importExcel(multipartFile.getInputStream(),GoodsStock.class, params);
            for (GoodsStock goodsStock:result){

                service.importExcelEnter(goodsStock);
            }
            returnModel.setData("");
            returnModel.setMsg("导入成功");
            returnModel.setState(Constant.STATE_SUCCESS);
            return returnModel;
        }catch (Exception e){
            logger.info("importExcel", e);
            returnModel.setData("");
            returnModel.setMsg("导入失败！（请联系管理员）");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }



}
