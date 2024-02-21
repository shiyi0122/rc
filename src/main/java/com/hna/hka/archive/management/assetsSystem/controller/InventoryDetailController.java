package com.hna.hka.archive.management.assetsSystem.controller;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.github.pagehelper.PageInfo;
import com.hna.hka.archive.management.assetsSystem.model.Inventory;
import com.hna.hka.archive.management.assetsSystem.model.InventoryDetail;
import com.hna.hka.archive.management.assetsSystem.model.SysRobotPartsManagement;
import com.hna.hka.archive.management.assetsSystem.service.InventoryDetailService;
import com.hna.hka.archive.management.system.model.SysUsers;
import com.hna.hka.archive.management.system.util.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import javax.xml.transform.Result;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @program: rc
 * @description: 配件进销存
 * @author: zhaoxianglong
 * @create: 2021-10-21 14:37
 **/
@Api(tags = "配件进销存")
@CrossOrigin
@RestController
@RequestMapping("/system/inventory_detail")
public class InventoryDetailController extends PublicUtil {

    @Autowired
    InventoryDetailService service;


    @ApiOperation("库存列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", value = "类型：1、入库；2、出库"),
            @ApiImplicitParam(name = "goodId", value = "商品ID"),
            @ApiImplicitParam(name = "spotId", value = "单位id"),
            @ApiImplicitParam(name = "orderNumber", value = "订单编码"),
            @ApiImplicitParam(name = "inStockReason ", value = "原因"),
            @ApiImplicitParam(name = "pageNum", value = "起始条数"),
            @ApiImplicitParam(name = "pageSize", value = "每页条数"),
            @ApiImplicitParam(name = "startDate", value = "起始时间"),
            @ApiImplicitParam(name = "endDate", value = "结束时间")
    })
    @GetMapping("/list")
    public ReturnModel list(@NotNull Integer type, Long goodId, Long spotId, String orderNumber, String startDate, String endDate,String outStockReason ,String outStockType ,String signInState, @NotNull Integer pageNum, @NotNull Integer pageSize) {

        try {
            PageInfo<InventoryDetail> info = service.list(type, goodId, spotId, orderNumber, startDate, endDate,outStockReason,outStockType,signInState,pageNum, pageSize);
            return new ReturnModel(info, "success", Constant.STATE_SUCCESS, null);
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnModel(e.getMessage(), "error", "500", null);
        }
    }

    @ApiOperation("单号列表查询")
    @GetMapping("/listById")
    public ReturnModel listById(){
        try {
            List<InventoryDetail> inventoryDetails = service.listById();
            return new ReturnModel(inventoryDetails,"success",Constant.STATE_SUCCESS,null);
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnModel(e.getMessage(), "error", "500", null);
        }
    }

    @ApiOperation("跟据单号查询")
    @GetMapping("/getByOrderNumber")
    public ReturnModel getByOrderNumber(@NotNull String orderNumber ,String type){
        try {
            List<InventoryDetail> info = service.getByOrderNumberN(orderNumber,type);
            return new ReturnModel(info, "success", Constant.STATE_SUCCESS, null);
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnModel(e.getMessage(), "error", "500", null);
        }
    }

    @ApiOperation("新增")
    @PostMapping("/add")
    public ReturnModel add(@RequestBody Inventory inventory) {
        try {
            int i = service.add(inventory);
            if (i == 1) {
                return new ReturnModel(null, "添加成功", Constant.STATE_SUCCESS, null);
            } else if (i == 2){
                return new ReturnModel(null, "发货单位库存不足，无法发货", Constant.STATE_FAILURE, null);
            }else{
                return new ReturnModel(null, "添加失败", Constant.STATE_FAILURE, null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnModel(e.getMessage(), "添加失败", "500", null);
        }
    }

    @ApiOperation("修改")
    @PostMapping("/edit")
    public ReturnModel edit(@RequestBody Inventory inventory) {
        try {
            if (service.edit(inventory)) {
                return new ReturnModel(null, "success", Constant.STATE_SUCCESS, null);
            } else {
                return new ReturnModel(null, "fail", Constant.STATE_FAILURE, null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnModel(e.getMessage(), "error", "500", null);
        }
    }

    @ApiOperation("更改状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键id"),
            @ApiImplicitParam(name = "status", value = "状态：0、初始状态；1、确认；2、撤销")
    })
    @PostMapping("/updateStatus")
    public ReturnModel updateStatus(Long id, Integer status) {
        try {
            SysUsers sysUser = getSysUser();

            if (service.updateStatus(id, status,sysUser.getUserId())) {
                return new ReturnModel(null, "success", Constant.STATE_SUCCESS, null);
            } else {
                return new ReturnModel(null, "fail", Constant.STATE_FAILURE, null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnModel(e.getMessage(), "error", "500", null);
        }
    }

    @ApiOperation("更改签收状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键id"),
            @ApiImplicitParam(name = "status", value = "状态：0、未签收；1、签收")
    })
    @PostMapping("/updateSignInStatus")
    public ReturnModel updateSignInStatus(Long id, Integer status) {
        try {
            SysUsers sysUser = getSysUser();

            if (service.updateSignInStatus(id, status,sysUser.getUserId())) {
                return new ReturnModel(null, "success", Constant.STATE_SUCCESS, null);
            } else {
                return new ReturnModel(null, "fail", Constant.STATE_FAILURE, null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnModel(e.getMessage(), "error", "500", null);
        }



    }

    @ApiOperation("列表下载")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", value = "类型：1、入库；2、出库"),
            @ApiImplicitParam(name = "goodId", value = "商品ID"),
            @ApiImplicitParam(name = "spotId", value = "单位id"),
            @ApiImplicitParam(name = "orderNumber", value = "订单编码"),
            @ApiImplicitParam(name = "startDate", value = "起始时间"),
            @ApiImplicitParam(name = "endDate", value = "结束时间")
    })
    @GetMapping("/downloadExcel")
    public void downloadExcel(@NotNull Integer type, Long goodId, Long spotId, String orderNumber, String outStockType,String startDate, String endDate,String signInState,String outStockReason ,HttpServletResponse response) {
        List<InventoryDetail> detailList = service.detailList(type, goodId, spotId, orderNumber, outStockType,startDate, endDate,signInState,outStockReason);
        String dateTime = DateFormatUtils.format(new Date(), "yyyyMMddHHmm");
        if (type == 1) {
            FileUtil.exportExcel(FileUtil.getWorkbook("入库信息", "入库信息", InventoryDetail.class, detailList), "入库信息" + dateTime + ".xls", response);
        } else if (type == 2) {
            FileUtil.exportExcel(FileUtil.getWorkbook("出库信息", "出库信息", InventoryDetail.class, detailList), "出库库信息" + dateTime + ".xls", response);
        }
    }

    @ApiOperation("单据下载")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orderNumber", value = "单号")
    })
    @GetMapping("/downloadOrder")
    public void downloadOrder(Integer type, String orderNumber, HttpServletResponse response) {
        List<InventoryDetail> detailList = service.detailList(type, orderNumber);
        String dateTime = DateFormatUtils.format(new Date(), "yyyyMMddHHmm");
        if (type == 1) {
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFCellStyle center = workbook.createCellStyle();
            center.setAlignment(HorizontalAlignment.CENTER);
            center.setBorderLeft(BorderStyle.THIN);
            center.setBorderBottom(BorderStyle.THIN);
            center.setBorderTop(BorderStyle.THIN);
            center.setBorderRight(BorderStyle.THIN);
            XSSFCellStyle left = workbook.createCellStyle();
            left.setAlignment(HorizontalAlignment.LEFT);
            XSSFCellStyle right = workbook.createCellStyle();
            right.setAlignment(HorizontalAlignment.RIGHT);
            XSSFSheet sheet = workbook.createSheet("入库单");
            XSSFRow row0 = sheet.createRow(0);
            row0.createCell(0).setCellValue("入库单");
            row0.getCell(0).setCellStyle(center);
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 8));
            if (detailList.size() > 0) {
                InventoryDetail detail = detailList.get(0);

                XSSFRow row1 = sheet.createRow(1);
                row1.createCell(7).setCellValue("No.");
                row1.getCell(7).setCellStyle(right);
                row1.createCell(8).setCellValue(detail.getOrderNumber());

                XSSFRow row2 = sheet.createRow(2);
                row2.createCell(0).setCellValue("收货单位:");
                row2.getCell(0).setCellStyle(right);
                row2.createCell(1).setCellValue(detail.getReceivingName());
                row2.createCell(7).setCellValue("日期:");
                row2.getCell(7).setCellStyle(right);
                row2.createCell(8).setCellValue(detail.getOrderTime().substring(0, 10));

                XSSFRow row3 = sheet.createRow(3);
                row3.createCell(0).setCellValue("供货单位:");
                row3.getCell(0).setCellStyle(right);
                row3.createCell(1).setCellValue(detail.getSpotName());
//                row3.createCell(7).setCellValue("电话:");
//                row3.getCell(7).setCellStyle(right);
//                row3.createCell(8).setCellValue(detail.getPhone());

                XSSFRow row4 = sheet.createRow(4);
                row4.createCell(0).setCellValue("序号");
                row4.getCell(0).setCellStyle(center);
                row4.createCell(1).setCellValue("商品编码");
                row4.getCell(1).setCellStyle(center);
                row4.createCell(2).setCellValue("商品名称");
                row4.getCell(2).setCellStyle(center);
                row4.createCell(3).setCellValue("规格型号");
                row4.getCell(3).setCellStyle(center);
                row4.createCell(4).setCellValue("单位");
                row4.getCell(4).setCellStyle(center);
                row4.createCell(5).setCellValue("数量");
                row4.getCell(5).setCellStyle(center);
                row4.createCell(6).setCellValue("单价（元）");
                row4.getCell(6).setCellStyle(center);
                row4.createCell(7).setCellValue("金额（元）");
                row4.getCell(7).setCellStyle(center);
                row4.createCell(8).setCellValue("备注");
                row4.getCell(8).setCellStyle(center);

                Double totalAmount = 0d;
                for (int i = 0; i < detailList.size(); i++) {
                    XSSFRow row = sheet.createRow(i + 5);
                    row.createCell(0).setCellValue(i + 1);
                    row.getCell(0).setCellStyle(center);
                    row.createCell(1).setCellValue(detailList.get(i).getGoodsCode());
                    row.getCell(1).setCellStyle(center);
                    row.createCell(2).setCellValue(detailList.get(i).getGoodsName());
                    row.getCell(2).setCellStyle(center);
                    row.createCell(3).setCellValue(detailList.get(i).getModel());
                    row.getCell(3).setCellStyle(center);
                    row.createCell(4).setCellValue(detailList.get(i).getUnit());
                    row.getCell(4).setCellStyle(center);
                    row.createCell(5).setCellValue(detailList.get(i).getGoodsAmount());
                    row.getCell(5).setCellStyle(center);
                    row.createCell(6).setCellValue(detailList.get(i).getUnitPirce());
                    row.getCell(6).setCellStyle(center);
                    row.createCell(7).setCellValue(detailList.get(i).getTotalAmount());
                    row.getCell(7).setCellStyle(center);
                    row.createCell(8).setCellValue(detailList.get(i).getNotes());
                    row.getCell(8).setCellStyle(center);
                    totalAmount += detailList.get(i).getTotalAmount();
                }

                XSSFRow row5 = sheet.createRow(detailList.size() + 5);
                row5.createCell(0).setCellValue("合计(大写):");
                sheet.addMergedRegion(new CellRangeAddress(detailList.size() + 5, detailList.size() + 5, 0, 1));
                row5.getCell(0).setCellStyle(right);
                row5.createCell(2).setCellValue(MathUtil.cvt(String.valueOf(totalAmount), false) + "元");
                sheet.addMergedRegion(new CellRangeAddress(detailList.size() + 5, detailList.size() + 5, 2, 3));
                row5.createCell(7).setCellValue(totalAmount);
                row5.getCell(0).setCellStyle(center);

                XSSFRow row6 = sheet.createRow(detailList.size() + 6);
                row6.createCell(0).setCellValue("送货人:");
                row6.getCell(0).setCellStyle(right);
                row6.createCell(1).setCellValue(detail.getDeliveryMan());

                row6.createCell(7).setCellValue("收货人:");
                row6.getCell(7).setCellStyle(right);
                row6.createCell(8).setCellValue(detail.getReceiver());


                XSSFRow row7 = sheet.createRow(detailList.size() + 7);
                row7.createCell(0).setCellValue("送货人手机号:");
                row7.getCell(0).setCellStyle(right);
                row7.createCell(1).setCellValue(detail.getPhone());

                row7.createCell(7).setCellValue("收货人手机号:");
                row7.getCell(7).setCellStyle(right);
                row7.createCell(8).setCellValue(detail.getReceivingPhone());



                FileUtil.exportExcel(workbook, "入库单" + detail.getOrderNumber(), response);

            }
        } else if (type == 2) {
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFCellStyle center = workbook.createCellStyle();
            center.setAlignment(HorizontalAlignment.CENTER);
            center.setBorderLeft(BorderStyle.THIN);
            center.setBorderBottom(BorderStyle.THIN);
            center.setBorderTop(BorderStyle.THIN);
            center.setBorderRight(BorderStyle.THIN);
            XSSFCellStyle left = workbook.createCellStyle();
            left.setAlignment(HorizontalAlignment.LEFT);
            XSSFCellStyle right = workbook.createCellStyle();
            right.setAlignment(HorizontalAlignment.RIGHT);
            XSSFSheet sheet = workbook.createSheet("出库单");
            XSSFRow row0 = sheet.createRow(0);
            row0.createCell(0).setCellValue("出库单");
            row0.getCell(0).setCellStyle(center);
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 10));
            if (detailList.size() > 0) {
                InventoryDetail detail = detailList.get(0);

                XSSFRow row1 = sheet.createRow(1);
                row1.createCell(9).setCellValue("No.");
                row1.getCell(9).setCellStyle(right);
                row1.createCell(10).setCellValue(detail.getOrderNumber());

                XSSFRow row2 = sheet.createRow(2);
                row2.createCell(0).setCellValue("收货单位:");
                row2.getCell(0).setCellStyle(right);
                row2.createCell(1).setCellValue(detail.getReceivingName());
                row2.createCell(2).setCellValue("收货地址:");
                row2.getCell(2).setCellStyle(right);
                row2.createCell(3).setCellValue(detail.getReceivingAddress());
                row2.createCell(9).setCellValue("日期:");
                row2.getCell(9).setCellStyle(right);
                row2.createCell(10).setCellValue(detail.getOrderTime().substring(0, 10));

                XSSFRow row3 = sheet.createRow(3);
                row3.createCell(0).setCellValue("供货单位:");
                row3.getCell(0).setCellStyle(right);
                row3.createCell(1).setCellValue(detail.getSpotName());
//                row3.createCell(9).setCellValue("电话:");
//                row3.getCell(9).setCellStyle(right);
//                row3.createCell(10).setCellValue(detail.getPhone());

                XSSFRow row4 = sheet.createRow(4);
                row4.createCell(0).setCellValue("序号");
                row4.getCell(0).setCellStyle(center);
                row4.createCell(1).setCellValue("商品编码");
                row4.getCell(1).setCellStyle(center);
                row4.createCell(2).setCellValue("商品名称");
                row4.getCell(2).setCellStyle(center);
                row4.createCell(3).setCellValue("规格型号");
                row4.getCell(3).setCellStyle(center);
                row4.createCell(4).setCellValue("单位");
                row4.getCell(4).setCellStyle(center);
                row4.createCell(5).setCellValue("数量");
                row4.getCell(5).setCellStyle(center);
                row4.createCell(6).setCellValue("单价（元）");
                row4.getCell(6).setCellStyle(center);
                row4.createCell(7).setCellValue("金额（元）");
                row4.getCell(7).setCellStyle(center);
                row4.createCell(8).setCellValue("申请单号");
                row4.getCell(8).setCellStyle(center);
                row4.createCell(9).setCellValue("出库原因");
                row4.getCell(9).setCellStyle(center);
                row4.createCell(10).setCellValue("备注");
                row4.getCell(10).setCellStyle(center);

                Double totalAmount = 0d;
                for (int i = 0; i < detailList.size(); i++) {
                    XSSFRow row = sheet.createRow(i + 5);
                    row.createCell(0).setCellValue(i + 1);
                    row.getCell(0).setCellStyle(center);
                    row.createCell(1).setCellValue(detailList.get(i).getGoodsCode());
                    row.getCell(1).setCellStyle(center);
                    row.createCell(2).setCellValue(detailList.get(i).getGoodsName());
                    row.getCell(2).setCellStyle(center);
                    row.createCell(3).setCellValue(detailList.get(i).getModel());
                    row.getCell(3).setCellStyle(center);
                    row.createCell(4).setCellValue(detailList.get(i).getUnit());
                    row.getCell(4).setCellStyle(center);
                    row.createCell(5).setCellValue(detailList.get(i).getGoodsAmount());
                    row.getCell(5).setCellStyle(center);
                    row.createCell(6).setCellValue(detailList.get(i).getUnitPirce());
                    row.getCell(6).setCellStyle(center);
                    row.createCell(7).setCellValue(detailList.get(i).getTotalAmount());
                    row.getCell(7).setCellStyle(center);
                    row.createCell(8).setCellValue(detailList.get(i).getApplyNumber());
                    row.getCell(8).setCellStyle(center);
                    if ("1".equals(detailList.get(i).getInStockReason())){
                        row.createCell(9).setCellValue("生产");
                    }else if ("2".equals(detailList.get(i).getInStockReason())){
                        row.createCell(9).setCellValue("配件申请");
                    }else if ("3".equals(detailList.get(i).getInStockReason())){
                        row.createCell(9).setCellValue("故障上报");
                    }else if ("4".equals(detailList.get(i).getInStockReason())){
                        row.createCell(9).setCellValue("损坏上报");
                    }
                    row.getCell(9).setCellStyle(center);
                    row.createCell(10).setCellValue(detailList.get(i).getNotes());
                    row.getCell(10).setCellStyle(center);
                    totalAmount += detailList.get(i).getTotalAmount();
                }

                XSSFRow row5 = sheet.createRow(detailList.size() + 5);
                row5.createCell(0).setCellValue("合计(大写):");
                sheet.addMergedRegion(new CellRangeAddress(detailList.size() + 5, detailList.size() + 5, 0, 1));
                row5.getCell(0).setCellStyle(right);
                row5.createCell(2).setCellValue(MathUtil.cvt(String.valueOf(totalAmount), false) + "元");
                sheet.addMergedRegion(new CellRangeAddress(detailList.size() + 5, detailList.size() + 5, 2, 3));
                row5.createCell(7).setCellValue(totalAmount);
                row5.getCell(0).setCellStyle(center);

                XSSFRow row6 = sheet.createRow(detailList.size() + 6);
                row6.createCell(0).setCellValue("送货人:");
                row6.getCell(0).setCellStyle(right);
                row6.createCell(1).setCellValue(detail.getDeliveryMan());
                row6.createCell(9).setCellValue("收货人:");
                row6.getCell(9).setCellStyle(right);
                row6.createCell(10).setCellValue(detail.getReceiver());

                XSSFRow row7 = sheet.createRow(detailList.size() + 7);
                row7.createCell(0).setCellValue("送货人手机号:");
                row7.getCell(0).setCellStyle(right);
                row7.createCell(1).setCellValue(detail.getPhone());
                row7.createCell(9).setCellValue("收货人手机号:");
                row7.getCell(9).setCellStyle(right);
                row7.createCell(10).setCellValue(detail.getReceivingPhone());


                FileUtil.exportExcel(workbook, "出库单" + detail.getOrderNumber(), response);

            }
        }
    }

    /**
     * 导入入库数据
     * @param multipartFile
     * @return
     * @throws Exception
     */
    @ApiOperation("导入入库数据")
    @RequestMapping("/importExcel")
    @ResponseBody
    public ReturnModel importExcelEnter(@RequestPart("file") MultipartFile multipartFile) throws Exception {
        ReturnModel returnModel = new ReturnModel();

        try {
            ImportParams params = new ImportParams();
            params.setTitleRows(1);
            params.setHeadRows(1);
            List<InventoryDetail> result = ExcelImportUtil.importExcel(multipartFile.getInputStream(),InventoryDetail.class, params);
            for (InventoryDetail inventoryDetail:result){

                if ("采购".equals(inventoryDetail.getInStockType())){
                    inventoryDetail.setInStockType("1");
                }else if ("回收".equals(inventoryDetail.getInStockType())){
                    inventoryDetail.setInStockType("2");
                }
                service.importExcelEnter(inventoryDetail);
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


    /**
     * 导入入库数据
     * @param multipartFile
     * @return
     * @throws Exception
     */
    @ApiOperation("导入出库库数据")
    @RequestMapping("/importExcelOut")
    @ResponseBody
    public ReturnModel importExcelOut(@RequestPart("file") MultipartFile multipartFile) throws Exception {
        ReturnModel returnModel = new ReturnModel();

        try {
            ImportParams params = new ImportParams();
            params.setTitleRows(1);
            params.setHeadRows(1);
            List<InventoryDetail> result = ExcelImportUtil.importExcel(multipartFile.getInputStream(),InventoryDetail.class, params);
            for (InventoryDetail inventoryDetail:result){

                if ("生产".equals(inventoryDetail.getInStockType())){
                    inventoryDetail.setInStockType("1");
                }else if ("销售".equals(inventoryDetail.getInStockType())){
                    inventoryDetail.setInStockType("2");
                }else if ("售后".equals(inventoryDetail.getInStockType())){
                    inventoryDetail.setInStockType("3");
                }

                if ("生产".equals(inventoryDetail.getInStockReason())){
                    inventoryDetail.setInStockReason("1");
                }else if ("配件申请".equals(inventoryDetail.getInStockReason())){
                    inventoryDetail.setInStockReason("2");
                }else if ("故障上报".equals(inventoryDetail.getInStockReason())){
                    inventoryDetail.setInStockReason("3");
                }else if ("损坏上报".equals(inventoryDetail.getInStockReason())){
                    inventoryDetail.setInStockReason("4");
                }


                service.importExcelOut(inventoryDetail);
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


    @ApiOperation("合计")
    @GetMapping("/total")
    public ReturnModel total(@NotNull Integer type, Long goodId, Long spotId, String orderNumber, String startDate, String endDate,String outStockReason ,String outStockType,String signInState) {

        try {
            Map<String ,Object> info = service.total(type, goodId, spotId, orderNumber, startDate, endDate,outStockReason,outStockType,signInState);
            return new ReturnModel(info, "success", Constant.STATE_SUCCESS, null);
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnModel(e.getMessage(), "error", "500", null);
        }
    }





}
