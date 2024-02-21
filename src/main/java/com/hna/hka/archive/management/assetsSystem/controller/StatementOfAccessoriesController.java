package com.hna.hka.archive.management.assetsSystem.controller;

import com.hna.hka.archive.management.assetsSystem.model.AccountClose;
import com.hna.hka.archive.management.assetsSystem.model.AccountStatement;
import com.hna.hka.archive.management.assetsSystem.model.CooperativeCompany;
import com.hna.hka.archive.management.assetsSystem.model.StatementOfAccessories;
import com.hna.hka.archive.management.assetsSystem.service.StatementOfAccessoriesService;
import com.hna.hka.archive.management.system.model.SysUsers;
import com.hna.hka.archive.management.system.util.Constant;
import com.hna.hka.archive.management.system.util.FileUtil;
import com.hna.hka.archive.management.system.util.PublicUtil;
import com.hna.hka.archive.management.system.util.ReturnModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2022/4/20 9:53
 * 配件对账单
 */

@Api(tags = "配件对账单")
@RestController
@RequestMapping("/system/statementOfAccessories")
public class StatementOfAccessoriesController extends PublicUtil {

    @Autowired
    StatementOfAccessoriesService statementOfAccessoriesService;


    @ApiOperation("列表信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页码"),
            @ApiImplicitParam(name = "pageSize", value = "每页条数"),
            @ApiImplicitParam(name = "company", value = "公司ID"),
            @ApiImplicitParam(name = "spot", value = "景区ID"),
            @ApiImplicitParam(name = "beginDate", value = "开始时间"),
            @ApiImplicitParam(name = "endDate", value = "结束时间"),
            @ApiImplicitParam(name = "paymentType", value = "支付方式"),
            @ApiImplicitParam(name = "orderStatus", value = "支付状态")
    })
    @CrossOrigin
    @GetMapping("/list")
    public ReturnModel list(Integer pageNum, Integer pageSize, Long company, Long spot, String beginDate, String endDate, String paymentType, Long errorRecordsUpkeepCost,String warrantyState) {

        ReturnModel returnModel = new ReturnModel();
        try {
            List<StatementOfAccessories> list = statementOfAccessoriesService.listNew(company, spot, beginDate, paymentType, errorRecordsUpkeepCost,warrantyState, pageNum, pageSize);
            Map<String, Object> count = statementOfAccessoriesService.getCountNew(company, spot, beginDate, paymentType, errorRecordsUpkeepCost,warrantyState ,pageNum, pageSize);
            returnModel.setData(list);
            returnModel.setMsg("获取列表信息成功");
            returnModel.setState(Constant.STATE_SUCCESS);
            returnModel.setTotal(Integer.parseInt(count.get("count").toString()));
            return returnModel;
        } catch (Exception e) {
            logger.error("获取列表信息失败", e);
            return new ReturnModel(null, "获取列表信息失败", "500", null);
        }

    }


    @ApiOperation("预览")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "company", value = "公司ID"),
            @ApiImplicitParam(name = "spot", value = "景区ID"),
            @ApiImplicitParam(name = "beginDate", value = "开始时间"),
            @ApiImplicitParam(name = "endDate", value = "结束时间"),
            @ApiImplicitParam(name = "paymentType", value = "支付方式"),
            @ApiImplicitParam(name = "paymentStatus", value = "支付状态")
    })

    @CrossOrigin
    @GetMapping("/preview")
    public ReturnModel preview(Integer pageNum, Integer pageSize, Long company, Long spot, String beginDate, String paymentType, Long errorRecordsUpkeepCost) {
        List<HashMap<String, Object>> map = statementOfAccessoriesService.preview(company, spot, beginDate, paymentType, errorRecordsUpkeepCost, pageNum, pageSize);
        return new ReturnModel(map, "预览信息成功", Constant.STATE_SUCCESS, null);
    }

    @ApiOperation("计算合计")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页码"),
            @ApiImplicitParam(name = "pageSize", value = "每页条数"),
            @ApiImplicitParam(name = "company", value = "公司ID"),
            @ApiImplicitParam(name = "spot", value = "景区ID"),
            @ApiImplicitParam(name = "beginDate", value = "开始时间"),
            @ApiImplicitParam(name = "endDate", value = "结束时间"),
            @ApiImplicitParam(name = "paymentType", value = "支付方式"),
            @ApiImplicitParam(name = "orderStatus", value = "支付状态")
    })
    @CrossOrigin
    @GetMapping("/calculateTotal")
    public ReturnModel calculateTotal(Integer pageNum, Integer pageSize, Long company, Long spot, String beginDate, String endDate, String paymentType, Integer orderStatus) {

        ReturnModel returnModel = new ReturnModel();
        try {
            Map<String, Object> map = statementOfAccessoriesService.calculateTotal(company, spot, beginDate, endDate, paymentType, orderStatus, pageNum, pageSize);
//            Map<String, Object> count = statementOfAccessoriesService.getCount(company, spot, beginDate, endDate, paymentType, orderStatus, pageNum, pageSize);
            returnModel.setData(map);
            returnModel.setMsg("获取合计成功");
            returnModel.setState(Constant.STATE_SUCCESS);
//            returnModel.setTotal(Integer.parseInt(count.get("count").toString()));
            return returnModel;
        } catch (Exception e) {
            logger.error("获取合计失败", e);
            return new ReturnModel(null, "获取合计失败", "500", null);
        }

    }

    /**
     * 根据公司id，获取收款信息
     *
     * @return
     */
    @ApiOperation("根据公司id，获取收款相关信息")
    @CrossOrigin
    @GetMapping("/getCompanyId")
    public ReturnModel getCompanyId(Long companyId) {

        ReturnModel returnModel = new ReturnModel();

        CooperativeCompany cooperativeCompany = statementOfAccessoriesService.getCompanyId(companyId);

        returnModel.setData(cooperativeCompany);
        returnModel.setState(Constant.STATE_SUCCESS);
        returnModel.setMsg("获取成功");
        return returnModel;
    }

    @ApiOperation("导出")
    @CrossOrigin
    @GetMapping("/download")
    public void download(Integer pageNum, Integer pageSize, Long company, Long spot, String beginDate, String endDate, String paymentType, Long errorRecordsUpkeepCost, HttpServletResponse response) {
        try {
            List<StatementOfAccessories> list = statementOfAccessoriesService.download(company, spot, beginDate, endDate, paymentType, errorRecordsUpkeepCost, pageNum, pageSize);
            String dateTime = DateFormatUtils.format(new Date(), "yyyyMMddHHmm");
            FileUtil.exportExcel(FileUtil.getWorkbook("配件对账", "配件对账", StatementOfAccessories.class, list), "配件对账" + dateTime + ".xls", response);

        } catch (Exception e) {
            logger.error("获取配件列表失败", e);
        }
    }

    @ApiOperation("修改核实状态")
    @CrossOrigin
    @GetMapping("/exitSettlementState")
    public ReturnModel exitSettlementState(Long company, Long spot, String beginDate, String paymentType, Long errorRecordsUpkeepCost) {

        ReturnModel returnModel = new ReturnModel();
        SysUsers sysUser = getSysUser();
        int i = statementOfAccessoriesService.exitSettlementState(company, spot, beginDate, paymentType, errorRecordsUpkeepCost, sysUser.getUserId());
        if (i == 1) {
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

    @ApiOperation("核实按钮")
    @CrossOrigin
    @GetMapping("/settlementStateState")
    public ReturnModel settlementStateState(Long company, Long spot, String beginDate, String paymentType, Long errorRecordsUpkeepCost) {

        ReturnModel returnModel = new ReturnModel();

        SysUsers sysUser = getSysUser();
        int i = statementOfAccessoriesService.settlementStateState(company, spot, beginDate, paymentType, errorRecordsUpkeepCost, sysUser.getUserId(), sysUser.getUserName());

        returnModel.setData(i);
        returnModel.setState(Constant.STATE_SUCCESS);
        returnModel.setMsg("获取成功");
        return returnModel;

    }

//    @ApiOperation("测试定时统计接口")
//    @CrossOrigin
//    @GetMapping("/test")
//    public ReturnModel test() {
//
//
//
//
//
//
//    }
}