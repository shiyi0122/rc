package com.hna.hka.archive.management.assetsSystem.controller;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.hna.hka.archive.management.assetsSystem.model.SysRobotPeripheral;
import com.hna.hka.archive.management.assetsSystem.model.SysRobotPeripheralExtendVo;
import com.hna.hka.archive.management.assetsSystem.service.SysRobotPeripheralService;
import com.hna.hka.archive.management.system.util.Constant;
import com.hna.hka.archive.management.system.util.FileUtil;
import com.hna.hka.archive.management.system.util.PublicUtil;
import com.hna.hka.archive.management.system.util.ReturnModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2022/3/1 15:32
 * 机器人外设管理
 */
@Api(tags = "机器人外设")
@RequestMapping("/system/robotPeripheral")
@RestController
public class RobotPeripheralController extends PublicUtil {

    @Autowired
    SysRobotPeripheralService sysRobotPeripheralService;

    @Autowired
    private HttpServletRequest request;

    @GetMapping("/getRobotPeripheral")
    @ApiOperation("列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "spotId", value = "景区ID"),
            @ApiImplicitParam(name = "peripheralName", value = "配件名称"),
            @ApiImplicitParam(name = "beginDate", value = "起始时间"),
            @ApiImplicitParam(name = "endDate", value = "截止时间"),
            @ApiImplicitParam(name = "pageNum", value = "页码"),
            @ApiImplicitParam(name = "pageSize", value = "每页条数"),
    })
    public ReturnModel getRobotPeripheralList(@NotNull Integer pageNum , @NotNull Integer pageSize,Long spotId,String peripheralName,String beginDate , String endDate ){
        Map<String, String> search = new HashMap<>();
        ReturnModel returnModel = new ReturnModel();
        if (!StringUtils.isEmpty(spotId)){
            search.put("spotId",spotId.toString());
        }
        if (!StringUtils.isEmpty(peripheralName)){
            search.put("peripheralName",peripheralName);
        }
        if (!StringUtils.isEmpty(beginDate) && !StringUtils.isEmpty(endDate)){
            search.put("beginDate",beginDate);
            search.put("endDate",endDate);
        }
        try {
            if(null == pageNum) {
                pageNum = 1;
            }
            if(null == pageSize) {
                pageSize = 10;
            }
             returnModel = sysRobotPeripheralService.getRobotPeripheralList(pageNum,pageSize,search);
            returnModel.setMsg("success");
            returnModel.setState(Constant.STATE_SUCCESS);
            returnModel.setType("");
        } catch (Exception e) {
            logger.info("景区拓展管理列表查询失败",e);
        }
        return returnModel;
    }

    @PostMapping("/addRobotPeripheral")
    @ApiOperation("新增")
    public  ReturnModel addRobotPeripheral(@RequestBody SysRobotPeripheral sysRobotPeripheral){

        int result= sysRobotPeripheralService.addRobotPeripheral(sysRobotPeripheral);
        if (result>0){
            return new ReturnModel(result,"添加成功",Constant.STATE_SUCCESS,null);
        }else{
            return new ReturnModel(result,"添加失败",Constant.STATE_FAILURE,null);
        }

    }

    @PostMapping("/editRobotPeripheral")
    @ApiOperation("修改")
    public  ReturnModel editRobotPeripheral(@RequestBody SysRobotPeripheral sysRobotPeripheral){

        int result= sysRobotPeripheralService.editRobotPeripheral(sysRobotPeripheral);
        if (result>0){
            return new ReturnModel(result,"修改成功",Constant.STATE_SUCCESS,null);
        }else{
            return new ReturnModel(result,"修改失败",Constant.STATE_FAILURE,null);
        }

    }


    @GetMapping("/delRobotPeripheral")
    @ApiOperation("删除")
    public  ReturnModel delRobotPeripheral(@NotNull Long robotPeripheralId){

        int result= sysRobotPeripheralService.delRobotPeripheral(robotPeripheralId);
        if (result>0){
            return new ReturnModel(result,"删除成功",Constant.STATE_SUCCESS,null);
        }else{
            return new ReturnModel(result,"删除失败",Constant.STATE_FAILURE,null);
        }
    }


    /**
     * 导出
     */

    @RequestMapping("/uploadExcelRobotPeripheral")
    @ApiOperation("导出")
    public void uploadExcelRobotPeripheral(HttpServletResponse response,  Long spotId,String peripheralName,String beginDate,String endDate ){

        Map<String, String> search = new HashMap<>();

        if (!StringUtils.isEmpty(spotId)){
//            search.put("spotId",sysRobotPeripheral.getScenicSpotId().toString());
            search.put("spotId",spotId.toString());
        }
        if (!StringUtils.isEmpty(peripheralName)){
            search.put("peripheralName",peripheralName);
        }
//        String startTime = request.getParameter("startTime");
//        String endTime = request.getParameter("endTime");

//        //判断开始时间和结束是否为空，全部满足空的条件才已当前时间为条件查询
//        if (ToolUtil.isEmpty(startTime) || ToolUtil.isEmpty(endTime)) {
//            if (ToolUtil.isEmpty(sysRobotPeripheral.getScenicSpotId()) && ToolUtil.isEmpty(sysRobotPeripheral.getPeripheralName())) {
//                startTime = DateUtil.crutDate();
//                endTime = DateUtil.crutDate();
//            }
//        }
        if (!StringUtils.isEmpty(beginDate)){
            search.put("beginDate",beginDate);
        }
        if (!StringUtils.isEmpty(endDate)){
            search.put("endDate",endDate);
        }
        List<SysRobotPeripheral> list = sysRobotPeripheralService.getRobotPeripheralExcelList(search);
        String dateTime = DateFormatUtils.format(new Date(), "yyyyMMddHHmm");
        FileUtil.exportExcel(FileUtil.getWorkbook("机器人外设信息", "外设信息", SysRobotPeripheral.class, list),"外设信息"+ dateTime,response);
    }

    /**
     * @Method importExcel
     * @Author zhang
     * @Version  1.0
     * @Description 导入Excel表
     * @Return com.hna.hka.archive.management.system.util.ReturnModel
     * @Date
     */
    @RequestMapping("/importExcel")
    @ResponseBody
//    MultipartFile 实现文件的快速上传
    public ReturnModel importExcel(@RequestParam("file") MultipartFile multipartFile) throws Exception {
//        创建ReturnModel用于返回归前端数据
        ReturnModel returnModel = new ReturnModel();
//        异常抛出
        try {
//            标题行设置为一行,默认是0,可以不设置;依实际情况设置
            ImportParams params = new ImportParams();
            params.setTitleRows(1);
//            表头设置为1行
            params.setHeadRows(1);
//            读取excel
            List<SysRobotPeripheralExtendVo> result = ExcelImportUtil.importExcel(multipartFile.getInputStream(), SysRobotPeripheralExtendVo.class, params);
            for (SysRobotPeripheralExtendVo sysRobotPeripheralExtendVo : result) {
                SysRobotPeripheral sysRobotPeripheral = new SysRobotPeripheral();
                sysRobotPeripheral.setRobotPeripheralId(sysRobotPeripheralExtendVo.getRobotPeripheralId());
                sysRobotPeripheral.setPeripheralName(sysRobotPeripheralExtendVo.getPeripheralName());
                sysRobotPeripheral.setPeripheralPrice(sysRobotPeripheralExtendVo.getPeripheralPrice());
                sysRobotPeripheral.setPeripheralQuantity(sysRobotPeripheralExtendVo.getPeripheralQuantity());
                sysRobotPeripheral.setPeripheralCompany(sysRobotPeripheralExtendVo.getPeripheralCompany());
                sysRobotPeripheral.setRemarks(sysRobotPeripheralExtendVo.getRemarks());
                sysRobotPeripheral.setScenicSpotName(sysRobotPeripheralExtendVo.getScenicSpotName());
                int i = sysRobotPeripheralService.importExcel(sysRobotPeripheral);
            }

            returnModel.setData("");
            returnModel.setMsg("导入成功");
            returnModel.setState(Constant.STATE_SUCCESS);
            return returnModel;
        }catch (Exception e){
//            logger.info("importExcel", e);
            returnModel.setData("");
            returnModel.setMsg("导入失败！（请联系管理员）");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }
}
