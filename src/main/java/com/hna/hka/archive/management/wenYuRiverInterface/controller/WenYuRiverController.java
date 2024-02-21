package com.hna.hka.archive.management.wenYuRiverInterface.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.hna.hka.archive.management.system.dto.LoginDTO;
import com.hna.hka.archive.management.system.model.SysOrder;
import com.hna.hka.archive.management.system.model.SysUsers;
import com.hna.hka.archive.management.system.service.SysOrderService;
import com.hna.hka.archive.management.system.util.*;
import com.hna.hka.archive.management.wenYuRiverInterface.model.*;
import com.hna.hka.archive.management.wenYuRiverInterface.util.HttpClientToInterface;
import com.hna.hka.archive.management.wenYuRiverInterface.util.ReflectionUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @ProjectName: rc
 * @Package: com.hna.hka.archive.management.wenYuRiverInterface.controller
 * @ClassName: WenYuRiverController
 * @Author: 郭凯
 * @Description: 温榆河景区数据展示接口
 * @Date: 2021/5/13 15:22
 * @Version: 1.0
 */
@Api(tags = "温榆河景区数据展示接口")
@RequestMapping("/system/wenYuRiverInterface/")
@Controller
public class WenYuRiverController extends PublicUtil {

    @Autowired
    private SysOrderService sysOrderService;

    @Autowired
    private HttpServletRequest request;

    /**
     * @Method realTimeAccess
     * @Author 郭凯
     * @Version  1.0
     * @Description 实时获取数据
     * @Return com.hna.hka.archive.management.system.util.ReturnModel
     * @Date 2021/5/13 15:35
     */
    @RequestMapping("realTimeAccess")
    @ResponseBody
    public ReturnModel realTimeAccess(){
        ReturnModel returnModel = new ReturnModel();
        try {
            RealTimeAccess realTimeAccess = sysOrderService.getRealTimeAccess();
            Map<String,Object> dataMap = new HashMap<>();
            dataMap.put("timeAccess",realTimeAccess.getTimeAccess());
            dataMap.put("robotQuantityUsed",realTimeAccess.getRobotQuantityUsed());
            BigDecimal robotQuantityUsed =  new BigDecimal(realTimeAccess.getRobotQuantityUsed());
            BigDecimal robotRemainingQuantity =  new BigDecimal(realTimeAccess.getRobotRemainingQuantity());
            dataMap.put("robotRemainingQuantity" , robotRemainingQuantity.subtract(robotQuantityUsed));
            if (ToolUtil.isNotEmpty(dataMap)){
                returnModel.setData(dataMap);
                returnModel.setMsg("获取成功");
                returnModel.setState(Constant.STATE_SUCCESS);
                returnModel.setType(Constant.STATE_SUCCESS);
                return returnModel;
            }else{
                returnModel.setData("");
                returnModel.setMsg("接口超时");
                returnModel.setState(Constant.STATE_FAILURE);
                returnModel.setType(Constant.STATE_FAILURE);
                return returnModel;
            }
        }catch (Exception e){
            returnModel.setData("");
            returnModel.setMsg("接口超时");
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setType(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    /**
     * @Method totalRevenueData
     * @Author 郭凯
     * @Version  1.0
     * @Description 获取总营收数据
     * @Return com.hna.hka.archive.management.system.util.ReturnModel
     * @Date 2021/5/13 15:39
     */
    @RequestMapping("totalRevenueData")
    @ResponseBody
    public ReturnModel totalRevenueData(@NotNull(message = "开始日期为空") String startDate ,@NotNull(message = "结束日期为空") String endDate){
        ReturnModel returnModel = new ReturnModel();
        try {
            Map<String,Object> search = new HashMap<>();
            search.put("startDate",startDate);
            search.put("endDate",endDate);
            RealTimeAccess realTimeAccess = sysOrderService.getTotalRevenue(search);
            Map<String,Object> dataMap = new HashMap<>();
            dataMap.put("totalRevenue",realTimeAccess.getTotalRevenue());
            dataMap.put("finialRevenue",realTimeAccess.getFinialRevenue());
            if (ToolUtil.isNotEmpty(dataMap)){
                returnModel.setData(dataMap);
                returnModel.setMsg("获取成功");
                returnModel.setState(Constant.STATE_SUCCESS);
                returnModel.setType(Constant.STATE_SUCCESS);
                return returnModel;
            }else{
                returnModel.setData("");
                returnModel.setMsg("接口超时");
                returnModel.setState(Constant.STATE_FAILURE);
                returnModel.setType(Constant.STATE_FAILURE);
                return returnModel;
            }
        }catch (Exception e){
            returnModel.setData("");
            returnModel.setMsg("接口超时");
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setType(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    /**
     * @Method robotUtilizationRate
     * @Author 郭凯
     * @Version  1.0
     * @Description 机器人使用率
     * @Return com.hna.hka.archive.management.system.util.ReturnModel
     * @Date 2021/5/13 15:44
     */
    @RequestMapping("robotUtilizationRate")
    @ResponseBody
    public ReturnModel robotUtilizationRate(){
        ReturnModel returnModel = new ReturnModel();
        try {
            List<RobotUtilizationRate> robotUtilizationRateList = sysOrderService.getRobotUtilizationRate();

            for (RobotUtilizationRate robotUtilizationRate : robotUtilizationRateList){
                BigDecimal numberOfUsers = new BigDecimal(robotUtilizationRate.getNumberOfUsers());
                BigDecimal robotSize = new BigDecimal(robotUtilizationRateList.size());
                BigDecimal robotNum =  numberOfUsers.divide(robotSize);
                BigDecimal utilizationRate = robotNum.multiply(new BigDecimal("100"));
                robotUtilizationRate.setUtilizationRate(utilizationRate.setScale(2, BigDecimal.ROUND_HALF_UP).toString() + "%");
            }
            if (ToolUtil.isNotEmpty(robotUtilizationRateList)){
                returnModel.setData(robotUtilizationRateList);
                returnModel.setMsg("获取成功");
                returnModel.setState(Constant.STATE_SUCCESS);
                returnModel.setType(Constant.STATE_SUCCESS);
                return returnModel;
            }else{
                returnModel.setData("");
                returnModel.setMsg("接口超时");
                returnModel.setState(Constant.STATE_FAILURE);
                returnModel.setType(Constant.STATE_FAILURE);
                return returnModel;
            }
        }catch (Exception e){
            returnModel.setData("");
            returnModel.setMsg("接口超时");
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setType(Constant.STATE_FAILURE);
            return returnModel;
        }
    }
    
    
    /**
     * @Method orderHistoryRevenue
     * @Author 郭凯
     * @Version  1.0
     * @Description 历史数据查询跳转接口
     * @Return java.lang.String
     * @Date 2021/5/13 16:57
     */
    @ApiOperation("历史数据查询跳转接口")
    @RequestMapping("orderHistoryRevenue")
    @ResponseBody
    public ReturnModel orderHistoryRevenue(BaseQueryVo BaseQueryVo , @NotNull(message = "access_token为空") String access_token, @NotNull(message = "开始时间为空")String startDate ,@NotNull(message = "结束时间为空")String endDate, String orderRobotCode){
        ReturnModel returnModel = new ReturnModel();
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("access_token", access_token);
            WenYuRiver wenYuRiver = HttpClientToInterface.doPost("https://omp.wenyuriverpark.com/adminapi/login/getUserInfo?access_token=" + access_token,jsonObject);
            if (!"200".equals(wenYuRiver.getCode())){
                returnModel.setData("");
                returnModel.setMsg("access_token过期，请重新登录");
                returnModel.setState(Constant.STATE_FAILURE);
                returnModel.setType(Constant.STATE_FAILURE);
                return returnModel;
            }
            Map<String,String> search = new HashMap<>();

//            if (org.springframework.util.StringUtils.isEmpty(startDate) || org.springframework.util.StringUtils.isEmpty(endDate)){
//                search.put("startDate", DateUtil.crutDate());
//                search.put("endDate",DateUtil.addDay(DateUtil.crutDate(),1));
//            }else{
//                search.put("startDate", startDate);
//                search.put("endDate",DateUtil.addDay(endDate,1));
//            }

            search.put("startDate",startDate);
            search.put("endDate",endDate);
            search.put("orderRobotCode",orderRobotCode);
            BaseQueryVo.setSearch(search);
            PageInfo<WenYuRiverOrder> page = sysOrderService.getWenYuRiverOrderList(BaseQueryVo);
            List<Map<String,Object>> dataList = dealOrderData(page.getList());
            List<WenYuRiverOrder> sysOrderList = page.getList();
            Map<String,Object> dataMap = new HashMap<>();
            dataMap.put("list",dataList);
            dataMap.put("pages",page.getPages());
            dataMap.put("pageNum",page.getPageNum());
            returnModel.setData(dataMap);
            returnModel.setMsg("");
            returnModel.setState(Constant.STATE_SUCCESS);
            returnModel.setType(Constant.STATE_SUCCESS);
            return returnModel;
        }catch (Exception e){
            returnModel.setData("");
            returnModel.setMsg("接口超时");
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setType(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    /**
     * @Method tokenBeOverdue
     * @Author 郭凯
     * @Version  1.0
     * @Description tokenId过期
     * @Return com.hna.hka.archive.management.system.util.ReturnModel
     * @Date 2021/5/18 17:41
     */
    @RequestMapping("tokenBeOverdue")
    @ResponseBody
    public ReturnModel tokenBeOverdue(){
        ReturnModel returnModel = new ReturnModel();
        try {
            returnModel.setData("");
            returnModel.setMsg("access_token过期，请重新登录");
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setType(Constant.STATE_FAILURE);
            return returnModel;
        }catch (Exception e){
            returnModel.setData("");
            returnModel.setMsg("access_token过期，请重新登录");
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setType(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    /**
     * @Method timeIsEmpty
     * @Author 郭凯
     * @Version  1.0
     * @Description 时间字段为空返回数据
     * @Return com.hna.hka.archive.management.system.util.ReturnModel
     * @Date 2021/5/17 16:16
     */
    @RequestMapping("timeIsEmpty")
    @ResponseBody
    public ReturnModel timeIsEmpty(){
        ReturnModel returnModel = new ReturnModel();
        try {
            returnModel.setData("");
            returnModel.setMsg("参数不能为空");
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setType(Constant.STATE_FAILURE);
            return returnModel;
        }catch (Exception e){
            returnModel.setData("");
            returnModel.setMsg("参数不能为空");
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setType(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    @RequestMapping("failure")
    @ResponseBody
    public ReturnModel failure(){
        ReturnModel returnModel = new ReturnModel();
        try {
            returnModel.setData("");
            returnModel.setMsg("账号有误");
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setType(Constant.STATE_FAILURE);
            return returnModel;
        }catch (Exception e){
            returnModel.setData("");
            returnModel.setMsg("账号有误");
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setType(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    /**
     * @Method
     * @Author 郭凯
     * @Version  1.0
     * @Description 数据格式封装
     * @Return
     * @Date 2021/5/19 16:50
     */
    public List<Map<String,Object>> dealOrderData(List<WenYuRiverOrder> list){
        List<Map<String,Object>> dataList = list.stream().map(orders -> {
            Map<String,Object> map = ReflectionUtils.getDestJson(new String[]{"currentUserPhone:currentUserPhone","orderRobotCode:orderRobotCode","orderScenicSpotName:orderScenicSpotName",
                    "orderStartTime:orderStartTime","orderEndTime:orderEndTime","totalTime:totalTime","actualAmount:actualAmount","dispatchingFee:dispatchingFee",
                    "orderAndDeductible:orderAndDeductible","orderRefundAmount:orderRefundAmount","realIncome:realIncome","orderStatus:orderStatus","orderId:orderId"},orders);
            return map;
        }).collect(Collectors.toList());
        return dataList;
    }

    /**
     * @Method loginSystem
     * @Author 郭凯
     * @Version  1.0
     * @Description 单点登录
     * @Return java.lang.String
     * @Date 2021/5/19 17:17
     */
    @RequestMapping("singlePointLogin")
    public String loginSystem(HttpServletRequest request, HttpSession session, Model model ,String access_token){
        if (ToolUtil.isEmpty(access_token)){
            return "redirect:timeIsEmpty";
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("access_token", access_token);
        WenYuRiver wenYuRiver = HttpClientToInterface.doPost("https://omp.wenyuriverpark.com/adminapi/login/getUserInfo?access_token=" + access_token,jsonObject);
        if (!"200".equals(wenYuRiver.getCode())){
            return "redirect:tokenBeOverdue";
        }
        logger.info("进行登陆");
        // 使用 shiro 进行登录
        Subject subject = SecurityUtils.getSubject();

        String userName = "jianghexuan".trim();
        String password = "jianghexuan".trim();
        String host = request.getRemoteAddr();

        //获取token
        UsernamePasswordToken token = new UsernamePasswordToken(userName, password,host);
        try {
            subject.login(token);
            // 登录成功
            SysUsers user = (SysUsers) subject.getPrincipal();
            session.setAttribute("userName", user.getUserName());
            logger.info(user.getUserName()+"登陆成功");
            return "redirect:/loginSpot";
        }catch (UnknownAccountException e) {
            logger.error(userName+"账号不存在");
            return "redirect:failure";
        }catch (DisabledAccountException e){
            logger.error(userName+"账号异常");
            return "redirect:failure";
        }
        catch (AuthenticationException e){
            logger.error(userName+"密码错误");
            return "redirect:failure";
        }
    }

}
