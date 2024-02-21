package com.hna.hka.archive.management.assetsSystem.controller;

import com.github.pagehelper.PageInfo;
import com.hna.hka.archive.management.assetsSystem.model.OperateState;
import com.hna.hka.archive.management.assetsSystem.service.SysOperateStateService;
import com.hna.hka.archive.management.system.util.Constant;
import com.hna.hka.archive.management.system.util.PageDataResult;
import com.hna.hka.archive.management.system.util.PublicUtil;
import com.hna.hka.archive.management.system.util.ReturnModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ProjectName: rc
 * @Package: com.hna.hka.archive.management.assetsSystem.controller
 * @ClassName: OperateStateController
 * @Author: 郭凯
 * @Description: 运营状态控制层
 * @Date: 2021/7/11 22:32
 * @Version: 1.0
 */
@Api(tags = "运营状态")
@RequestMapping("/system/operateState")
@Controller
public class OperateStateController extends PublicUtil {

    @Autowired
    private SysOperateStateService sysOperateStateService;

    /**
     * @Method getBugStatusList
     * @Author 郭凯
     * @Version 1.0
     * @Description 查询机器人运营状态
     * @Return com.hna.hka.archive.management.system.util.PageDataResult
     * @Date 2021/7/11 22:54
     */
    @GetMapping("/getOperateStateList")
    @ResponseBody
    public PageDataResult getBugStatusList(Integer currPage, Integer pageSize , OperateState operateState){
        PageDataResult pageDataResult = new PageDataResult();
        try {
            if(null == currPage) {
                currPage = 1;
            }
            if(null == pageSize) {
                pageSize = 10;
            }
            Map<String,String> search = new HashMap<>();
            search.put("startTime",operateState.getStartTime());
            search.put("endTime",operateState.getEndTime());
            search.put("scenicSpotId",operateState.getScenicSpotId());
            pageDataResult = sysOperateStateService.getOperateStateList(currPage, pageSize, search);
        }catch (Exception e){
            logger.info("getOperateStateList",e);
        }
        return pageDataResult;
    }

    /**
     * @Method getOperateStateSpotList
     * @Author 郭凯
     * @Version 1.0
     * @Description 机器人运营状态按景区统计
     * @Return com.hna.hka.archive.management.system.util.PageDataResult
     * @Date 2021/7/12 13:36
     */
    @ApiOperation("机器人运营状态按景区统计")
    @GetMapping("/getOperateStateSpotList")
    @ResponseBody
    public ReturnModel getOperateStateSpotList(Long spotId, @NotNull String beginDate, @NotNull String endDate,@NotNull Integer page,@NotNull Integer limit , String field) {
        try {
            PageInfo<HashMap> list = sysOperateStateService.getOperateStateSpotList(spotId, beginDate, endDate, page, limit , field);
//            List list = sysOperateStateService.getOperateStateSpotList(spotId , beginDate ,endDate);
            return new ReturnModel(list, "success", Constant.STATE_SUCCESS, null);
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnModel(e.getMessage(), "error", "500", null);
        }
    }

    @ApiOperation("机器人运营状态按机器人统计")
    @GetMapping("/getOperateStateRobotList")
    @ResponseBody
    public ReturnModel getOperateStateRobotList(Long spotId, @NotNull String beginDate, @NotNull String endDate,@NotNull Integer page,@NotNull Integer limit , Integer type) {
        try {
            PageInfo<HashMap> list = sysOperateStateService.getOperateStateRobotList(spotId, beginDate, endDate, page, limit , type);
//            List list = sysOperateStateService.getOperateStateSpotList(spotId , beginDate ,endDate);
            return new ReturnModel(list, "success", Constant.STATE_SUCCESS, null);
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnModel(e.getMessage(), "error", "500", null);
        }
    }

    @ApiOperation("根据景区获取详细信息")
    @GetMapping("/getDetailBySpot")
    @ResponseBody
    public ReturnModel getDetailBySpot(Long spotId , Integer type , String beginDate , String endDate){
        try {
            List list = sysOperateStateService.getDetailBySpot(spotId,type, beginDate, endDate);
            return new ReturnModel(list, "success", Constant.STATE_SUCCESS, null);
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnModel(e.getMessage(), "error", "500", null);
        }
    }

    @ApiOperation("导出数据")
    @GetMapping("/exportExcel")
    public void exportExcel(HttpServletResponse response , Long spotId , @NotNull Integer type , @NotNull String beginDate , @NotNull String endDate){
        sysOperateStateService.exportExcel(response , spotId , type , beginDate , endDate);
    }

    @ApiOperation("报表查询")
    @GetMapping("/getReport")
    @ResponseBody
    public ReturnModel getReport(Long spotId , String beginDate , String endDate){
        try {
//            PageInfo<HashMap> list = sysOperateStateService.getOperateStateSpotList(spotId, beginDate, endDate, page, limit);
            List list = sysOperateStateService.getOperateStateSpotList(spotId , beginDate ,endDate);
            return new ReturnModel(list, "success", Constant.STATE_SUCCESS, null);
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnModel(e.getMessage(), "error", "500", null);
        }
    }

}
