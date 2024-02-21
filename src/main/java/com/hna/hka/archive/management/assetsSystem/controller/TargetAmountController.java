package com.hna.hka.archive.management.assetsSystem.controller;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.hna.hka.archive.management.appSystem.model.SubstituteApply;
import com.hna.hka.archive.management.assetsSystem.model.CooperativeCompany;
import com.hna.hka.archive.management.assetsSystem.model.SysRobotSoftAssetInformation;
import com.hna.hka.archive.management.assetsSystem.model.SysRobotSoftAssetInformationExtendVo;
import com.hna.hka.archive.management.assetsSystem.model.SysScenicSpotTargetAmount;
import com.hna.hka.archive.management.assetsSystem.service.CooperativeCompanyService;
import com.hna.hka.archive.management.assetsSystem.service.SysScenicSpotTargetAmountService;
import com.hna.hka.archive.management.system.model.SysRobot;
import com.hna.hka.archive.management.system.model.SysScenicSpot;
import com.hna.hka.archive.management.system.service.SysScenicSpotService;
import com.hna.hka.archive.management.system.util.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ProjectName: rc
 * @Package: com.hna.hka.archive.management.assetsSystem.controller
 * @ClassName: TargetAmountController
 * @Author: 郭凯
 * @Description: 景区目标金额设置控制层
 * @Date: 2021/7/19 14:15
 * @Version: 1.0
 */
@Api( tags = "运营绩效")
@RequestMapping("/system/targetAmount")
@Controller
public class TargetAmountController extends PublicUtil {

    @Autowired
    private SysScenicSpotTargetAmountService sysScenicSpotTargetAmountService;

    @Autowired
    private SysScenicSpotService sysScenicSpotService;
    @Autowired
    private CooperativeCompanyService cooperativeCompanyService;

    /**
     *
     * @Method getTargetAmountList
     * @Author 郭凯
     * @Version  1.0
     * @Description 查询景区目标金额列表
     * @Return com.hna.hka.archive.management.system.util.PageDataResult
     * @Date 2021/7/19 14:36
     */
    @ApiOperation("列表")
    @RequestMapping("/getTargetAmountList")
    @ResponseBody
    public PageDataResult getTargetAmountList(@RequestParam("pageNum") Integer pageNum,
                                              @RequestParam("pageSize") Integer pageSize, SysScenicSpotTargetAmount sysScenicSpotTargetAmount){
        PageDataResult pageDataResult = new PageDataResult();
        Map<String,Object> search = new HashMap<>();
        try {
            if(null == pageNum) {
                pageNum = 1;
            }
            if(null == pageSize) {
                pageSize = 10;
            }
            search.put("date",sysScenicSpotTargetAmount.getDate());
            search.put("scenicSpotId",sysScenicSpotTargetAmount.getScenicSpotId());
            search.put("targetAmount",sysScenicSpotTargetAmount.getTargetAmount());
            search.put("companyId",sysScenicSpotTargetAmount.getCompanyId());
            pageDataResult = sysScenicSpotTargetAmountService.getTargetAmountList(pageNum,pageSize,search);
        }catch (Exception e){
            logger.info("getTargetAmountList",e);
        }
        return pageDataResult;
    }

    /**
     * @Method addTargetAmount
     * @Author 郭凯
     * @Version  1.0
     * @Description 新增景区目标金额
     * @Return com.hna.hka.archive.management.system.util.ReturnModel
     * @Date 2021/7/19 15:49
     */
    @ApiOperation("新增")
    @RequestMapping("/addTargetAmount")
    @ResponseBody
    public ReturnModel addTargetAmount( @RequestBody SysScenicSpotTargetAmount sysScenicSpotTargetAmount){
        ReturnModel returnModel = new ReturnModel();
        try {
            int i = sysScenicSpotTargetAmountService.addTargetAmount(sysScenicSpotTargetAmount);
            if (i == 1){
                returnModel.setData("");
                returnModel.setMsg("景区目标金额新增成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            }else if(i == 2){
                returnModel.setData("");
                returnModel.setMsg("此状态已存在，请修改！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }else{
                returnModel.setData("");
                returnModel.setMsg("景区目标金额新增失败！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        }catch (Exception e){
            logger.info("addTargetAmount",e);
            returnModel.setData("");
            returnModel.setMsg("系统错误！");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    /**
     * @Method delTargetAmount
     * @Author 郭凯
     * @Version  1.0
     * @Description 景区目标金额删除
     * @Return com.hna.hka.archive.management.system.util.ReturnModel
     * @Date 2021/7/19 16:39
     */
    @ApiOperation("删除")
    @RequestMapping("/delTargetAmount")
    @ResponseBody
    public ReturnModel delTargetAmount(Long targetAmountId){
        ReturnModel returnModel = new ReturnModel();
        try {
            int i = sysScenicSpotTargetAmountService.delTargetAmount(targetAmountId);
            if (i == 1){
                returnModel.setData("");
                returnModel.setMsg("景区目标金额删除成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            }else{
                returnModel.setData("");
                returnModel.setMsg("景区目标金额删除失败！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        }catch (Exception e){
            logger.info("delTargetAmount",e);
            returnModel.setData("");
            returnModel.setMsg("系统错误！");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    /**
     * @Method editTargetAmount
     * @Author 郭凯
     * @Version  1.0
     * @Description 景区目标金额编辑
     * @Return com.hna.hka.archive.management.system.util.ReturnModel
     * @Date 2021/7/19 18:11
     */
    @ApiOperation("编辑")
    @RequestMapping("/editTargetAmount")
    @ResponseBody
    public ReturnModel editTargetAmount(@RequestBody SysScenicSpotTargetAmount sysScenicSpotTargetAmount){
        ReturnModel returnModel = new ReturnModel();
        try {
            int i = sysScenicSpotTargetAmountService.editTargetAmount(sysScenicSpotTargetAmount);
            if (i == 1){
                returnModel.setData("");
                returnModel.setMsg("景区目标金额编辑成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            }else{
                returnModel.setData("");
                returnModel.setMsg("景区目标金额编辑失败！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        }catch (Exception e){
            logger.info("editTargetAmount",e);
            returnModel.setData("");
            returnModel.setMsg("系统错误！");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    /**
     * 导出
     *
     */
    @ApiOperation("导出")
    @RequestMapping("/getTargetAmountExcel")
    @ResponseBody
    public PageDataResult getTargetAmountExcel(HttpServletResponse response, Long spotId,String date,String targetAmount,String companyId){
        PageDataResult pageDataResult = new PageDataResult();
        Map<String,Object> search = new HashMap<>();
        try {
            search.put("scenicSpotId",spotId);
            search.put("date",date);
            search.put("targetAmount",targetAmount);
            search.put("companyId",companyId);
             List<SysScenicSpotTargetAmount> list = sysScenicSpotTargetAmountService.getTargetAmountExel(search);
            String dateTime = DateFormatUtils.format(new Date(), "yyyyMMddHHmm");
            FileUtil.exportExcel(FileUtil.getWorkbook("运营绩效管理", "运营绩效管理", SysScenicSpotTargetAmount.class, list),"运营绩效管理"+ dateTime,response);

        }catch (Exception e){
            logger.info("getTargetAmountList",e);
        }
        return pageDataResult;
    }

    /**
     * 导入
     *
     */

    /**
     * @Method importExcel
     * @Author 郭凯
     * @Version  1.0
     * @Description 导入Excel表
     * @Return com.hna.hka.archive.management.system.util.ReturnModel
     * @Date 2021/7/5 16:56
     */
    @ApiOperation("导入")
    @RequestMapping("/importExcel")
    @ResponseBody
    public ReturnModel importExcel(@RequestPart("file") MultipartFile multipartFile) throws Exception {
        ReturnModel returnModel = new ReturnModel();
        try {
            ImportParams params = new ImportParams();
            params.setTitleRows(1);
            params.setHeadRows(1);
            List<SysScenicSpotTargetAmount> sysScenicSpotTargetAmounts = ExcelImportUtil.importExcel(multipartFile.getInputStream(), SysScenicSpotTargetAmount.class, params);
            CooperativeCompany cooperativeCompany = new CooperativeCompany();
            for (SysScenicSpotTargetAmount sysScenicSpotTargetAmount : sysScenicSpotTargetAmounts) {
                 cooperativeCompany = cooperativeCompanyService.getNameByCompanyId(sysScenicSpotTargetAmount.getCompanyName());
                if (StringUtils.isEmpty(sysScenicSpotTargetAmount.getScenicSpotName())){
                    continue;
                }

                SysScenicSpot sysScenicSpot = sysScenicSpotService.getSpotNameById(sysScenicSpotTargetAmount.getScenicSpotName());
                SysScenicSpotTargetAmount  spotIdAndDateByTagret = sysScenicSpotTargetAmountService.getSpotIdAndDateByTagret(sysScenicSpot.getScenicSpotId(),sysScenicSpotTargetAmount.getDate());

                if (StringUtils.isEmpty(spotIdAndDateByTagret)){
                    sysScenicSpotTargetAmount.setCompanyId(cooperativeCompany.getCompanyId().toString());
                    sysScenicSpotTargetAmount.setTargetAmountId(IdUtils.getSeqId());
                    sysScenicSpotTargetAmount.setScenicSpotId(sysScenicSpot.getScenicSpotId());
                    sysScenicSpotTargetAmount.setCreateDate(DateUtil.currentDateTime());
                    sysScenicSpotTargetAmount.setUpdateDate(DateUtil.currentDateTime());
                    sysScenicSpotTargetAmount.setRobotCostName(sysScenicSpotTargetAmount.getRobotCostName());
                    sysScenicSpotTargetAmount.setUndertakerName(sysScenicSpotTargetAmount.getUndertakerName());
                    sysScenicSpotTargetAmount.setRentName(sysScenicSpotTargetAmount.getRentName());
                    sysScenicSpotTargetAmount.setMaintainCostName(sysScenicSpotTargetAmount.getMaintainCostName());
                    sysScenicSpotTargetAmount.setSpotMarketName(sysScenicSpotTargetAmount.getSpotMarketName());
                    int i = sysScenicSpotTargetAmountService.addTargetAmountExcel(sysScenicSpotTargetAmount);
                }else{
                    spotIdAndDateByTagret.setCompanyId(cooperativeCompany.getCompanyId().toString());
                    spotIdAndDateByTagret.setTargetAmount(sysScenicSpotTargetAmount.getTargetAmount());
                    spotIdAndDateByTagret.setRobotTargetAmount(sysScenicSpotTargetAmount.getRobotTargetAmount());
                    spotIdAndDateByTagret.setTargetState(sysScenicSpotTargetAmount.getTargetState());
                    spotIdAndDateByTagret.setDate(sysScenicSpotTargetAmount.getDate());
                    spotIdAndDateByTagret.setRobotCost(sysScenicSpotTargetAmount.getRobotCost());
                    spotIdAndDateByTagret.setOperateCost(sysScenicSpotTargetAmount.getOperateCost());
                    spotIdAndDateByTagret.setSpotMarketCost(sysScenicSpotTargetAmount.getSpotMarketCost());
                    spotIdAndDateByTagret.setRent(sysScenicSpotTargetAmount.getRent());
                    spotIdAndDateByTagret.setMaintainCost(sysScenicSpotTargetAmount.getMaintainCost());
                    spotIdAndDateByTagret.setRobotCostName(sysScenicSpotTargetAmount.getRobotCostName());
                    spotIdAndDateByTagret.setUndertakerName(sysScenicSpotTargetAmount.getUndertakerName());
                    spotIdAndDateByTagret.setRentName(sysScenicSpotTargetAmount.getRentName());
                    spotIdAndDateByTagret.setMaintainCostName(sysScenicSpotTargetAmount.getMaintainCostName());
                    spotIdAndDateByTagret.setSpotMarketName(sysScenicSpotTargetAmount.getSpotMarketName());
                    sysScenicSpotTargetAmountService.editTargetAmountExcel(spotIdAndDateByTagret);
                }
            }

            returnModel.setData("");
            returnModel.setMsg("导入成功");
            returnModel.setState(Constant.STATE_SUCCESS);
            return returnModel;
        }catch (Exception e){
            logger.info("景点列表导入Excel",e);
            returnModel.setData("");
            returnModel.setMsg("导入失败！（请联系管理员）");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }







    /**
     *获取机器人总折旧相关信息
     * @param spotId
     * @return
     */
    @ApiOperation("获取机器人总折旧相关信息")
    @RequestMapping("/robotTotalDepreciation")
    @ResponseBody
    public ReturnModel robotTotalDepreciation(Long spotId,String date){

       SysScenicSpotTargetAmount list= sysScenicSpotTargetAmountService.robotTotalDepreciation(spotId,date);

        return new ReturnModel(list,"success",Constant.STATE_SUCCESS,null);

    }




}
