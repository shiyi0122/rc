package com.hna.hka.archive.management.assetsSystem.controller;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.hna.hka.archive.management.assetsSystem.model.OperatePartsStock;
import com.hna.hka.archive.management.assetsSystem.model.StatementOfAccessories;
import com.hna.hka.archive.management.assetsSystem.model.SysRobotPartsManagement;
import com.hna.hka.archive.management.assetsSystem.service.OperatePartsStockService;
import com.hna.hka.archive.management.assetsSystem.service.SysRobotPartsManagementService;
import com.hna.hka.archive.management.managerApp.model.SysAppUsers;
import com.hna.hka.archive.management.managerApp.service.SysAppUsersService;
import com.hna.hka.archive.management.system.model.SysScenicSpot;
import com.hna.hka.archive.management.system.service.SysScenicSpotService;
import com.hna.hka.archive.management.system.util.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2022/11/11 17:42
 */
@Api(tags = "运营库存管理")
@CrossOrigin
@RestController
@RequestMapping("/system/operatePartsStock")
public class OperatePartsStockController {

    @Autowired
    OperatePartsStockService operatePartsStockService;

    @Autowired
    SysScenicSpotService sysScenicSpotService;

    @Autowired
    SysRobotPartsManagementService sysRobotPartsManagementService;

    @Autowired
    SysAppUsersService sysAppUsersService ;

    @ApiOperation("运营库存管理列表")
    @GetMapping("/list")
    public PageDataResult list(OperatePartsStock operatePartsStock, @NotNull Integer pageNum, @NotNull Integer pageSize) {

        PageDataResult pageDataResult = new PageDataResult();
        Map<String, Object> search = new HashMap<>();

        search.put("spotId", operatePartsStock.getSpotId());
        search.put("type", operatePartsStock.getType());
        search.put("applyNumber",operatePartsStock.getApplyNumber());
        search.put("partsManagementId", operatePartsStock.getPartsManagementName());
        search.put("equipmentNo",operatePartsStock.getEquipmentNo());
        search.put("pageNum", pageNum);
        search.put("pageSize", pageSize);
        pageDataResult = operatePartsStockService.list(search);
        return pageDataResult;
    }

    @ApiOperation("运营库存管理添加")
    @PostMapping("/addOperatePartsStock")

    public ReturnModel addOperatePartsStock(@RequestBody OperatePartsStock operatePartsStock) {

        ReturnModel returnModel = new ReturnModel();

        int i = operatePartsStockService.addOperatePartsStock(operatePartsStock);

        if (i > 0) {
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_SUCCESS);
            returnModel.setMsg("添加成功");
        } else {
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("添加失败");
        }
        return returnModel;

    }

    @ApiOperation("运营库存管理修改")
    @PostMapping("/editOperatePartsStock")
    public ReturnModel editOperatePartsStock(@RequestBody OperatePartsStock operatePartsStock) {

        ReturnModel returnModel = new ReturnModel();

        int i = operatePartsStockService.editOperatePartsStock(operatePartsStock);

        if (i > 0) {
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_SUCCESS);
            returnModel.setMsg("修改成功");
        } else {
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("修改失败");
        }

        return returnModel;
    }

    @ApiOperation("运营库存管理删除")
    @PostMapping("/delOperatePartsStock")
    public ReturnModel delOperatePartsStock(Long id) {

        ReturnModel returnModel = new ReturnModel();

        int i = operatePartsStockService.delOperatePartsStock(id);

        if (i > 0) {
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_SUCCESS);
            returnModel.setMsg("删除成功");
        } else {
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("删除失败");
        }

        return returnModel;

    }


    @ApiOperation("导出")
    @CrossOrigin
    @GetMapping("/download")
    public void download(OperatePartsStock operatePartsStock, HttpServletResponse response) {


        Map<String, Object> search = new HashMap<>();

        search.put("spotId", operatePartsStock.getSpotId());
        search.put("type", operatePartsStock.getType());
        search.put("partsManagementId",operatePartsStock.getPartsManagementName());
        search.put("applyNumber",operatePartsStock.getApplyNumber());

        try {
            List<OperatePartsStock> list = operatePartsStockService.download(search);
            String dateTime = DateFormatUtils.format(new Date(), "yyyyMMddHHmm");
            FileUtil.exportExcel(FileUtil.getWorkbook("运营库存", "运营库存", OperatePartsStock.class, list), "运营库存" + dateTime + ".xls", response);

        }catch (Exception e) {
//            logger.error("获取配件列表失败" , e);
            e.printStackTrace();
        }
    }

    /**
     * 导入
     * @param
     * @param
     * @param
     * @throws Exception
     */
    @ApiOperation("导入")
    @RequestMapping("/import")
    @ResponseBody
    public ReturnModel importPartsStock(@RequestPart("file") MultipartFile multipartFile) {
        ReturnModel returnModel = new ReturnModel();
        try {
            ImportParams params = new ImportParams();
            params.setTitleRows(1);
            params.setHeadRows(1);
            params.setSheetNum(1);
            List<OperatePartsStock> result = ExcelImportUtil.importExcel(multipartFile.getInputStream(),OperatePartsStock.class, params);
            OperatePartsStock sysScenicSpot = new OperatePartsStock();
            for (OperatePartsStock operatePartsStock:result) {
                //查询景区id是否存在
//                sysScenicSpot = sysScenicSpotService.selectById(sysScenicSpotExcel.getScenicSpotId());
                OperatePartsStock operatePartsStockNew = operatePartsStockService.selectByApplyNumber(operatePartsStock.getApplyNumber());

                if (StringUtils.isEmpty(operatePartsStockNew)) {

                    SysScenicSpot spotNameById = sysScenicSpotService.getSpotNameById(operatePartsStock.getSpotName());
                    SysRobotPartsManagement sysRobotPartsManagement = sysRobotPartsManagementService.getAccessoryNameByParts(operatePartsStock.getPartsManagementName());

                    operatePartsStock.setId(IdUtils.getSeqId());
                    operatePartsStock.setPartsManagementId(sysRobotPartsManagement.getPartsManagementId());
                    operatePartsStock.setSpotId(spotNameById.getScenicSpotId());
                    operatePartsStock.setCreateTime(DateUtil.currentDateTime());
                    operatePartsStock.setUpdateTime(DateUtil.currentDateTime());
                    operatePartsStockService.addOperatePartsStock(operatePartsStock);

                } else {
                    SysScenicSpot spotNameById1 = sysScenicSpotService.getSpotNameById(operatePartsStock.getSpotName());
                    operatePartsStockNew.setSpotId(spotNameById1.getScenicSpotId());
                    operatePartsStockNew.setAccessoryModel(operatePartsStock.getApplyNumber().toString());
                    operatePartsStockNew.setPartsManagementName(operatePartsStockNew.getPartsManagementName());
                    operatePartsStockNew.setAccessoryModel(operatePartsStockNew.getAccessoryModel());
                    operatePartsStockNew.setType(operatePartsStock.getType());
                    operatePartsStockNew.setNumber(operatePartsStock.getNumber());
                    operatePartsStockNew.setReason(operatePartsStock.getReason());
                    operatePartsStockNew.setEquipmentNo(operatePartsStock.getEquipmentNo());
                    operatePartsStockNew.setInventoryBalance(operatePartsStock.getInventoryBalance());
                    operatePartsStockNew.setOverWarranty(operatePartsStock.getOverWarranty());
                    operatePartsStockNew.setThreshold(operatePartsStock.getThreshold());
                    operatePartsStockNew.setRemarks(operatePartsStock.getRemarks());

                    operatePartsStockService.editOperatePartsStock(operatePartsStockNew);
                }

            }
            returnModel.setData("");
            returnModel.setMsg("导入成功");
            returnModel.setState(Constant.STATE_SUCCESS);
            return returnModel;
        }catch (Exception e){
//            logger.info("importBatchNumber", e);
            e.printStackTrace();
            returnModel.setData("");
            returnModel.setMsg("导入失败！（请联系管理员）");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    @ApiOperation("领用或者入库人员下拉选")
    @GetMapping("/operatorUserDrop")
    public ReturnModel operatorUserDrop() {
        ReturnModel returnModel = new ReturnModel();

        List<SysAppUsers> sysAppUsers = sysAppUsersService.getSysAppUsers();

        returnModel.setData(sysAppUsers);
        returnModel.setState(Constant.STATE_SUCCESS);
        returnModel.setMsg("获取成功");

        return returnModel;
    }


}




