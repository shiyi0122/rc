package com.hna.hka.archive.management.assetsSystem.controller;

import com.hna.hka.archive.management.assetsSystem.model.*;
import com.hna.hka.archive.management.system.model.SysScenicSpotBinding;
import com.hna.hka.archive.management.system.service.SysOrderService;
import com.hna.hka.archive.management.system.service.SysRobotService;
import com.hna.hka.archive.management.system.service.SysScenicSpotBindingService;
import com.hna.hka.archive.management.system.util.*;
import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ProjectName: rc
 * @Package: com.hna.hka.archive.management.assetsSystem.controller
 * @ClassName: OperationBigDataController
 * @Author: 郭凯
 * @Description: 景区运营大数据控制层
 * @Date: 2021/5/31 16:16
 * @Version: 1.0
 */
@RequestMapping("/system/operationBigData")
@Controller
public class OperationBigDataController extends PublicUtil {

    @Autowired
    private SysScenicSpotBindingService scenicSpotBindingService;

    @Autowired
    private SysOrderService orderService;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private SysRobotService sysRobotService;

    /**
     * @Method getChinaMap
     * @Author 郭凯
     * @Version  1.0
     * @Description 获取景区运营数据，只获取本月数据
     * @Return com.hna.hka.archive.management.system.util.ReturnModel
     * @Date 2021/5/31 18:00
     */
    @RequestMapping("/getChinaMap")
    @ResponseBody
    public ReturnModel getChinaMap() {
        ReturnModel dataModel = new ReturnModel();
        try {
            List<ChinaMap> chinaMapList = scenicSpotBindingService.getChinaMapList();
            dataModel.setData(chinaMapList);
            dataModel.setMsg("运营数据获取成功！");
            dataModel.setState(Constant.STATE_SUCCESS);
            return dataModel;
        } catch (Exception e) {
            dataModel.setData("");
            dataModel.setMsg("运营数据获取失败！");
            dataModel.setState(Constant.STATE_FAILURE);
            logger.error("getChinaMap", e);
            return dataModel;
        }
    }

    /**
     * @Method getOrderAmountLine
     * @Author 郭凯
     * @Version  1.0
     * @Description 查询订单金额和年月
     * @Return com.hna.hka.archive.management.system.util.ReturnModel
     * @Date 2021/6/1 10:15
     */
    @RequestMapping("/getOrderAmountLine")
    @ResponseBody
    public ReturnModel getOrderAmountLine() {
        ReturnModel dataModel = new ReturnModel();
        try {
            List<OrderAmountLine> orderList = orderService.getOrderAmountLine();
            dataModel.setData(orderList);
            dataModel.setMsg("数据成功！");
            dataModel.setState(Constant.STATE_SUCCESS);
            return dataModel;
        } catch (Exception e) {
            dataModel.setData("");
            dataModel.setMsg("数据获取失败！");
            dataModel.setState(Constant.STATE_FAILURE);
            logger.error("getOrderAmountLine", e);
            return dataModel;
        }
    }

    /**
     * @Method getTradeEcharts
     * @Author 郭凯
     * @Version  1.0
     * @Description 获取最近七天的订单数和金额
     * @Return com.hna.hka.archive.management.system.util.ReturnModel
     * @Date 2021/6/1 14:48
     */
    @RequestMapping("/getTradeEcharts")
    @ResponseBody
    public ReturnModel getTradeEcharts() {
        ReturnModel dataModel = new ReturnModel();
        try {
            List<TradeEcharts> tradeEchartsList = orderService.getTradeEcharts();
            TradeEcharts tradeEcharts = orderService.getTrade();
            Map<String,Object> map = new HashMap<>();
            map.put("list",tradeEchartsList);
            map.put("tradeEcharts",tradeEcharts);
            dataModel.setData(map);
            dataModel.setMsg("数据成功！");
            dataModel.setState(Constant.STATE_SUCCESS);
            return dataModel;
        } catch (Exception e) {
            dataModel.setData("");
            dataModel.setMsg("数据获取失败！");
            dataModel.setState(Constant.STATE_FAILURE);
            logger.error("getTradeEcharts", e);
            return dataModel;
        }
    }



    /**
     * @Method getScenicSpotRankingList
     * @Author 郭凯
     * @Version  1.0
     * @Description 机器人运营排名列表查询
     * @Return com.hna.hka.archive.management.system.util.ReturnModel
     * @Date 2021/6/17 16:43
     */
    @RequestMapping("/getScenicSpotRankingList")
    @ResponseBody
    public PageDataResult getScenicSpotRankingList(@RequestParam("pageNum") Integer pageNum,
                                                   @RequestParam("pageSize") Integer pageSize, String id) {
        PageDataResult pageDataResult = new PageDataResult();
        try {
            Map<String, Object> search = new HashMap<>();
            String startTime = request.getParameter("startTime");
            String endTime = request.getParameter("endTime");
            String dataType = request.getParameter("dataType");
            String field = request.getParameter("field");
            String type = request.getParameter("type");
            if (ToolUtil.isEmpty(startTime)){
                startTime = DateUtil.crutDate();
            }
            if (ToolUtil.isEmpty(endTime)){
                endTime = DateUtil.crutDate();
            }
            if (ToolUtil.isEmpty(dataType)){
                dataType = "2";
            }
            if (ToolUtil.isEmpty(field) && ToolUtil.isEmpty(type)){
                field = "orderAmount";
                type = "desc";
            }
            search.put("id",id);
            search.put("scenicSpotId",request.getParameter("scenicSpotId"));
            search.put("companyId",request.getParameter("companyId"));
            search.put("dataType",dataType);
            search.put("startTime",startTime);
            search.put("endTime",endTime);
            search.put("field",field);
            search.put("type",type);
            pageDataResult = orderService.getScenicSpotRankingList(pageNum, pageSize, search);
            return pageDataResult;
        } catch (Exception e) {
            logger.info("getScenicSpotRankingList",e);
        }
        return pageDataResult;
    }

    
    /**
     * @Method getScenicSpotById
     * @Author 郭凯
     * @Version  1.0
     * @Description 根据景区Fid查询景区
     * @Return com.hna.hka.archive.management.system.util.ReturnModel
     * @Date 2021/6/18 14:02
     */
    @RequestMapping("/getScenicSpotById")
    @ResponseBody
    public ReturnModel getScenicSpotById(String id) {
        ReturnModel dataModel = new ReturnModel();
        try {
            List<SysScenicSpotBinding> scenicSpotRankingList = scenicSpotBindingService.getScenicSpotById(id);
            dataModel.setData(scenicSpotRankingList);
            dataModel.setMsg("数据获取成功！");
            dataModel.setState(Constant.STATE_SUCCESS);
            return dataModel;
        } catch (Exception e) {
            dataModel.setData("");
            dataModel.setMsg("数据获取失败！");
            dataModel.setState(Constant.STATE_FAILURE);
            logger.error("getTradeEcharts", e);
            return dataModel;
        }
    }


    /**
     * @Method getMonthOperateData
     * @Author 郭凯
     * @Version  1.0
     * @Description 获取本月运营数据
     * @Return com.hna.hka.archive.management.system.util.PageDataResult
     * @Date 2021/6/21 14:38
     */
    @RequestMapping("/getMonthOperateData")
    @ResponseBody
    public PageDataResult getMonthOperateData(@RequestParam("pageNum") Integer pageNum,
                                                   @RequestParam("pageSize") Integer pageSize) {
        PageDataResult pageDataResult = new PageDataResult();
        try {
            Map<String, Object> search = new HashMap<>();
            String field = request.getParameter("field");
            String type = request.getParameter("type");
            if (ToolUtil.isEmpty(field) && ToolUtil.isEmpty(type)){
                field = "orderAmount";
                type = "desc";
            }
            search.put("field",field);
            search.put("type",type);
            pageDataResult = orderService.getMonthOperateData(pageNum, pageSize, search);
            return pageDataResult;
        } catch (Exception e) {
            logger.info("getMonthOperateData",e);
        }
        return pageDataResult;
    }

    /**
     * @Method uploadScenicSpotRankingExcel
     * @Author 郭凯
     * @Version  1.0
     * @Description 下载Excel表
     * @Return void
     * @Date 2021/7/9 12:49
     */
    @RequestMapping("/uploadScenicSpotRankingExcel")
    public void  uploadScenicSpotRankingExcel(HttpServletResponse response, String id) throws ParseException {
        Map<String, Object> search = new HashMap<>();
        String startTime = request.getParameter("startTime");
        String endTime = request.getParameter("endTime");
        String dataType = request.getParameter("dataType");
        String field = request.getParameter("field");
        String type = request.getParameter("type");
        if (ToolUtil.isEmpty(startTime)){
            startTime = DateUtil.crutDate();
        }
        if (ToolUtil.isEmpty(endTime)){
            endTime = DateUtil.crutDate();
        }
        if (ToolUtil.isEmpty(dataType)){
            dataType = "2";
        }
        search.put("id",id);
        search.put("scenicSpotId",request.getParameter("scenicSpotId"));
        search.put("companyId",request.getParameter("companyId"));
        search.put("dataType",dataType);
        search.put("startTime",startTime);
        search.put("endTime",endTime);
        search.put("field",field);
        search.put("type",type);
        List<ScenicSpotRanking> scenicSpotRankingList = orderService.getScenicSpotRankingExcel(search);
        String dateTime = DateFormatUtils.format(new Date(), "yyyyMMddHHmm");
        FileUtil.exportExcel(FileUtil.getWorkbook("景区运营排名", "景区运营排名", ScenicSpotRanking.class, scenicSpotRankingList),"景区运营排名"+ dateTime,response);
    }

    /**
     * @Method getRobotState
     * @Author 郭凯
     * @Version  1.0
     * @Description 查询机器人状态
     * @Return com.hna.hka.archive.management.system.util.ReturnModel
     * @Date 2021/7/9 15:26
     */
    @RequestMapping("/getRobotState")
    @ResponseBody
    public ReturnModel getRobotState(){
        ReturnModel dataModel = new ReturnModel();
        try {
            Map<String,Object> map = new HashMap<>();
            Map<String,Object> search = new HashMap<>();
            search.put("robotRunState","20,30,100");
            search.put("robotFaultState","20");
            int i = sysRobotService.getRobotState(search);
            map.put("使用中",i);
            search.clear();
            search.put("robotRunState","10,40,50,60,70,80,90");
            search.put("robotFaultState","20");
            int a = sysRobotService.getRobotState(search);
            map.put("闲置",a);
            search.clear();
            search.put("robotFaultState","30");
            int b = sysRobotService.getRobotState(search);
            map.put("故障",b);
            dataModel.setData(map);
            dataModel.setMsg("成功！");
            dataModel.setState(Constant.STATE_SUCCESS);
            return dataModel;
        }catch (Exception e){
            dataModel.setData("");
            dataModel.setMsg("失败！");
            dataModel.setState(Constant.STATE_FAILURE);
            logger.error("getRobotState", e);
            return dataModel;
        }
    }

    /**
     * @Method getWholeCountryScenicSpotRankingList
     * @Author 郭凯
     * @Version  1.0
     * @Description 全国景区排名列表查询
     * @Return com.hna.hka.archive.management.system.util.PageDataResult
     * @Date 2021/7/13 22:05
     */
    @RequestMapping("/getWholeCountryScenicSpotRankingList")
    @ResponseBody
    public PageDataResult getWholeCountryScenicSpotRankingList(@RequestParam("pageNum") Integer pageNum,
                                                   @RequestParam("pageSize") Integer pageSize) {
        PageDataResult pageDataResult = new PageDataResult();
        try {
            Map<String, Object> search = new HashMap<>();
            String startTime = request.getParameter("startTime");
            String endTime = request.getParameter("endTime");
            String dataType = request.getParameter("dataType");
            String field = request.getParameter("field");
            String type = request.getParameter("type");
            if (ToolUtil.isEmpty(startTime)){
                startTime = DateUtil.crutDate();
            }
            if (ToolUtil.isEmpty(endTime)){
                endTime = DateUtil.crutDate();
            }
            if (ToolUtil.isEmpty(dataType)){
                dataType = "2";
            }
            if (ToolUtil.isEmpty(field) && ToolUtil.isEmpty(type)){
                field = "orderAmount";
                type = "desc";
            }
            search.put("scenicSpotId",request.getParameter("scenicSpotId"));
            search.put("companyId",request.getParameter("companyId"));
            search.put("dataType",dataType);
            search.put("startTime",startTime);
            search.put("endTime",endTime);
            search.put("field",field);
            search.put("type",type);
            pageDataResult = orderService.getWholeCountryScenicSpotRankingList(pageNum, pageSize, search);
            return pageDataResult;
        } catch (Exception e) {
            logger.info("getWholeCountryScenicSpotRankingList",e);
        }
        return pageDataResult;
    }

    /**
     * @Method uploadScenicSpotRankingExcel
     * @Author 郭凯
     * @Version  1.0
     * @Description 下载Excel表
     * @Return void
     * @Date 2021/7/9 12:49
     */
    @RequestMapping("/uploadWholeCountryScenicSpotRankingExcel")
    public void  uploadWholeCountryScenicSpotRankingExcel(HttpServletResponse response, String id) throws ParseException {
        Map<String, Object> search = new HashMap<>();
        String startTime = request.getParameter("startTime");
        String endTime = request.getParameter("endTime");
        String dataType = request.getParameter("dataType");
        String field = request.getParameter("field");
        String type = request.getParameter("type");
        if (ToolUtil.isEmpty(startTime)){
            startTime = DateUtil.crutDate();
        }
        if (ToolUtil.isEmpty(endTime)){
            endTime = DateUtil.crutDate();
        }
        if (ToolUtil.isEmpty(dataType)){
            dataType = "1";
        }
        search.put("id",id);
        search.put("scenicSpotId",request.getParameter("scenicSpotId"));
        search.put("companyId",request.getParameter("companyId"));
        search.put("dataType",dataType);
        search.put("startTime",startTime);
        search.put("endTime",endTime);
        search.put("field",field);
        search.put("type",type);
        List<ScenicSpotRanking> scenicSpotRankingList = orderService.getWholeCountryScenicSpotRankingExcel(search);
        String dateTime = DateFormatUtils.format(new Date(), "yyyyMMddHHmm");
        FileUtil.exportExcel(FileUtil.getWorkbook("全国景区运营排名", "全国景区运营排名", ScenicSpotRanking.class, scenicSpotRankingList),"全国景区运营排名"+ dateTime,response);
    }

}
