package com.hna.hka.archive.management.appSystem.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.github.pagehelper.PageInfo;
import com.hna.hka.archive.management.appSystem.model.IsItEmpty;
import com.hna.hka.archive.management.appSystem.model.SysAppOrder;
import com.hna.hka.archive.management.appSystem.service.AppUserService;
import com.hna.hka.archive.management.managerApp.model.SysAppUsers;
import com.hna.hka.archive.management.system.model.*;
import com.hna.hka.archive.management.system.service.*;
import com.hna.hka.archive.management.system.util.*;
import com.hna.hka.archive.management.wenYuRiverInterface.model.BaseQueryVo;
import org.jdom.JDOMException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @ProjectName: IDEA-CODE
 * @Package: com.hna.hka.archive.management.appSystem.controller
 * @ClassName: AppOrderController
 * @Author: 郭凯
 * @Description:
 * @Date: 2020/11/23 17:00
 * @Version: 1.0
 */
@RequestMapping("/system/appOrderInterface")
@Controller
@CrossOrigin
public class AppOrderInterfaceController extends PublicUtil{

    @Autowired
    private AppUserService appUserService;

    @Autowired
    private SysRobotService sysRobotService;

    @Autowired
    private SysOrderService sysOrderService;

    @Autowired
    private SysCurrentUserService sysCurrentUserService;

    @Autowired
    private SysOrderLogService sysOrderLogService;



    /**
     * @Method getAppOrderList
     * @Author 郭凯
     * @Version  1.0
     * @Description 管理者APP订单管理页面查询
     * @Return java.lang.String
     * @Date 2021/6/8 14:39
     */
    @RequestMapping(value = "/getAppOrderList",method = RequestMethod.POST)
    @ResponseBody
    public String getAppOrderList(BaseQueryVo BaseQueryVo,String content) {
        ReturnModel dataModel = new ReturnModel();
        try {

            IsItEmpty isItEmpty = sysOrderService.isItEmpty();
            if (isItEmpty.getIsItEmpty().equals("0")){

                dataModel.setData("");
                dataModel.setMsg("订单查询成功!");
                dataModel.setState(Constant.STATE_SUCCESS);
                dataModel.setType(Constant.STATE_SUCCESS);
                String model = JSON.toJSONString(dataModel, SerializerFeature.WriteNullStringAsEmpty);
                return AES.encode(model);//加密返回

            }

            if (content == null || "".equals(content)) {
                dataModel.setData("");
                dataModel.setMsg("加密参数不能为空！");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
            String decode = AES.decode(content);//参数加密
            //解密后进行JSON解析
            JSONObject jsonobject = JSON.parseObject(decode);
            //TokenId
            String longinTokenId = jsonobject.getString("longinTokenId");
            //判断longinTokenId是否为空，如果为空，直接return
            if (ToolUtil.isEmpty(longinTokenId)) {
                dataModel.setData("");
                dataModel.setMsg("TokenId为空，请传入TokenId!");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
            //景区ID
            String scenicSpotId = jsonobject.getString("scenicSpotId");
            //判断scenicSpotId是否为空，如果为空，直接return
            if (ToolUtil.isEmpty(scenicSpotId)) {
                dataModel.setData("");
                dataModel.setMsg("景区ID为空，请传入景区ID!");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
            //订单状态
            String orderStatus = jsonobject.getString("orderStatus");
            //用户手机号
            String currentUserPhone = jsonobject.getString("currentUserPhone");
            //开始时间
            String startTime = jsonobject.getString("startTime");
            //结束时间
            String endTime = jsonobject.getString("endTime");
            String robotCode = jsonobject.getString("robotCode");
            //排序字段
            String type = jsonobject.getString("type");
            //升序降序
            String sort = jsonobject.getString("sort");
            //查询longinTokenId，是否一致
            SysAppUsers appUsers = appUserService.getSysUserBylonginTokenId(longinTokenId);
            if (appUsers == null) {
                dataModel.setData("");
                dataModel.setMsg("TokenId已过期，请重新登陆!");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
            if (ToolUtil.isEmpty(startTime) || ToolUtil.isEmpty(endTime)){
                if (ToolUtil.isEmpty(currentUserPhone)){
                    startTime = DateUtil.crutDate();
                    endTime = DateUtil.crutDate();
                }
            }



            Map<String,String> search = new HashMap<>();
            search.put("scenicSpotId",scenicSpotId);
            search.put("currentUserPhone",currentUserPhone);

            if(StringUtils.isEmpty(startTime) || StringUtils.isEmpty(endTime)){
                search.put("startTime",DateUtil.crutDate());
                search.put("endTime",DateUtil.addDay(DateUtil.crutDate(), 1));
            }else{
                search.put("startTime",startTime);
                search.put("endTime",DateUtil.addDay(endTime, 1));
            }

//            search.put("startTime",startTime);
//            search.put("endTime",endTime);

            search.put("orderStatus",orderStatus);
            search.put("robotCode",robotCode);
            search.put("type",type);
            search.put("sort",sort);
            BaseQueryVo.setSearch(search);
            PageInfo<SysAppOrder> page = sysOrderService.getAppOrderList(BaseQueryVo);
            Map<String,Object> dataMap = new HashMap<>();
            dataMap.put("list",page.getList());
            dataMap.put("pages",page.getPages());
            dataMap.put("pageNum",page.getPageNum());
            dataMap.put("total",page.getTotal());

            //查询订单接口
            dataModel.setData(dataMap);
            dataModel.setMsg("订单查询成功!");
            dataModel.setState(Constant.STATE_SUCCESS);
            dataModel.setType(Constant.STATE_SUCCESS);
            String model = JSON.toJSONString(dataModel, SerializerFeature.WriteNullStringAsEmpty);
            return AES.encode(model);//加密返回
        }
        catch (Exception e) {
            // TODO: handle exception
            logger.info("getAppOrderList",e);
            dataModel.setData("");
            dataModel.setMsg("订单查询失败!");
            dataModel.setState(Constant.STATE_FAILURE);
            dataModel.setType(Constant.STATE_FAILURE);
            String model = JSON.toJSONString(dataModel, SerializerFeature.WriteNullStringAsEmpty);
            return AES.encode(model);//加密返回
        }

    }

    /**
     * @Method getAppOrderList
     * @Author 郭凯
     * @Version  1.0
     * @Description 管理者APP订单管理页面查询
     * @Return java.lang.String
     * @Date 2021/6/8 14:39
     */
    @RequestMapping(value = "/getAppOrderDetails",method = RequestMethod.POST)
    @ResponseBody
    public String getAppOrderDetails(BaseQueryVo BaseQueryVo,String content) {
        ReturnModel dataModel = new ReturnModel();
        try {
//            IsItEmpty isItEmpty = sysOrderService.isItEmpty();
//            if (isItEmpty.getIsItEmpty().equals("0")){
//
//                dataModel.setData("");
//                dataModel.setMsg("订单查询成功!");
//                dataModel.setState(Constant.STATE_SUCCESS);
//                dataModel.setType(Constant.STATE_SUCCESS);
//                String model = JSON.toJSONString(dataModel, SerializerFeature.WriteNullStringAsEmpty);
//                return AES.encode(model);//加密返回
//
//            }

            if (content == null || "".equals(content)) {
                dataModel.setData("");
                dataModel.setMsg("加密参数不能为空！");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
            String decode = AES.decode(content);//参数加密
            //解密后进行JSON解析
            JSONObject jsonobject = JSON.parseObject(decode);
            //TokenId
            String longinTokenId = jsonobject.getString("longinTokenId");
            //判断longinTokenId是否为空，如果为空，直接return
            if (ToolUtil.isEmpty(longinTokenId)) {
                dataModel.setData("");
                dataModel.setMsg("TokenId为空，请传入TokenId!");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }


            String orderNumber = jsonobject.getString("orderNumber");

            //查询longinTokenId，是否一致
            SysAppUsers appUsers = appUserService.getSysUserBylonginTokenId(longinTokenId);
            if (appUsers == null) {
                dataModel.setData("");
                dataModel.setMsg("TokenId已过期，请重新登陆!");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }

            SysAppOrder sysAppOrder = sysOrderService.getAppOrderDetails(orderNumber);
            Map<String,Object> dataMap = new HashMap<>();

            dataMap.put("detail",sysAppOrder);
            //查询订单接口
            dataModel.setData(dataMap);
            dataModel.setMsg("订单查询成功!");
            dataModel.setState(Constant.STATE_SUCCESS);
            dataModel.setType(Constant.STATE_SUCCESS);
            String model = JSON.toJSONString(dataModel, SerializerFeature.WriteNullStringAsEmpty);
            return AES.encode(model);//加密返回
        }
        catch (Exception e) {
            // TODO: handle exception
            logger.info("getAppOrderList",e);
            dataModel.setData("");
            dataModel.setMsg("订单查询失败!");
            dataModel.setState(Constant.STATE_FAILURE);
            dataModel.setType(Constant.STATE_FAILURE);
            String model = JSON.toJSONString(dataModel, SerializerFeature.WriteNullStringAsEmpty);
            return AES.encode(model);//加密返回
        }

    }



    /**
     * @Author 郭凯
     * @Description VIP订单查询
     * @Date 17:22 2020/11/23
     * @Param [content]
     * @return java.lang.String
    **/
    @RequestMapping(value = "/getAppVipOrderList",method = RequestMethod.POST)
    @ResponseBody
    public String getAppVipOrderList(BaseQueryVo BaseQueryVo, String content) {
        ReturnModel dataModel = new ReturnModel();
        try {
            if (content == null || "".equals(content)) {
                dataModel.setData("");
                dataModel.setMsg("加密参数不能为空！");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
            String decode = AES.decode(content);//参数加密
            //解密后进行JSON解析
            JSONObject jsonobject = JSON.parseObject(decode);
            //景区ID
            String scenicSpotId = jsonobject.getString("scenicSpotId");
            //景区ID，如果为空，直接return
            if (ToolUtil.isEmpty(scenicSpotId)) {
                dataModel.setData("");
                dataModel.setMsg("景区ID为空，请传入景区ID!");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
            //TokenId
            String longinTokenId = jsonobject.getString("longinTokenId");
            //判断longinTokenId是否为空，如果为空，直接return
            if (ToolUtil.isEmpty(longinTokenId)) {
                dataModel.setData("");
                dataModel.setMsg("TokenId为空，请传入TokenId!");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
            //开始时间
            String startTime = jsonobject.getString("startTime");
            //结束时间
            String endTime = jsonobject.getString("endTime");
            //订单状态
            String orderStatus = jsonobject.getString("orderStatus");
            //排序字段
            String type = jsonobject.getString("type");
            //排序字段
            String sort = jsonobject.getString("sort");

            //查询longinTokenId，是否一致
            SysAppUsers appUsers = appUserService.getSysUserBylonginTokenId(longinTokenId);
            if (appUsers == null) {
                dataModel.setData("");
                dataModel.setMsg("TokenId已过期，请重新登陆!");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
            if (ToolUtil.isEmpty(startTime) || ToolUtil.isEmpty(endTime)){
                startTime = DateUtil.crutDate();
                endTime = DateUtil.crutDate();
            }
            Map<String,String> search = new HashMap<>();
            search.put("scenicSpotId",scenicSpotId);
            if(StringUtils.isEmpty(startTime) || StringUtils.isEmpty(endTime)){
                search.put("startTime",DateUtil.crutDate());
                search.put("endTime",DateUtil.addDay(DateUtil.crutDate(), 1));
            }else{
                search.put("startTime",startTime);
                search.put("endTime",DateUtil.addDay(endTime, 1));
            }

            search.put("orderStatus",orderStatus);
            search.put("paymentMethod","2");
            search.put("type",type);
            search.put("sort",sort);
            BaseQueryVo.setSearch(search);
            PageInfo<SysAppOrder> page = sysOrderService.getAppOrderList(BaseQueryVo);
            Map<String,Object> dataMap = new HashMap<>();
            dataMap.put("list",page.getList());
            dataMap.put("pages",page.getPages());
            dataMap.put("pageNum",page.getPageNum());
            dataMap.put("total",page.getTotal());
            //查询订单接口
            dataModel.setData(dataMap);
            dataModel.setMsg("VIP订单查询成功!");
            dataModel.setState(Constant.STATE_SUCCESS);
            dataModel.setType(Constant.STATE_SUCCESS);
            String model = JsonUtils.toString(dataModel);//对象转JSON
            return AES.encode(model);//加密返回
        }
        catch (Exception e) {
            // TODO: handle exception
            dataModel.setData("");
            dataModel.setMsg("订单查询失败!");
            dataModel.setState(Constant.STATE_FAILURE);
            dataModel.setType(Constant.STATE_FAILURE);
            String model = JsonUtils.toString(dataModel);//对象转JSON
            return AES.encode(model);//加密返回
        }

    }

    /**
     * @Author 郭凯
     * @Description 结算VIP订单
     * @Date 17:26 2020/11/23
     * @Param [content]
     * @return java.lang.String
    **/
    @RequestMapping(value = "/settleVipOrder",method = RequestMethod.POST)
    @ResponseBody
    public String settleVipOrder(String content) {
        ReturnModel dataModel = new ReturnModel();
        try {
            if (content == null || "".equals(content)) {
                dataModel.setData("");
                dataModel.setMsg("加密参数不能为空！");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
            String decode = AES.decode(content);//参数加密
            //解密后进行JSON解析
            JSONObject jsonobject = JSON.parseObject(decode);
            //TokenId
            String longinTokenId = jsonobject.getString("longinTokenId");
            //判断longinTokenId是否为空，如果为空，直接return
            if (longinTokenId == "" || longinTokenId == null) {
                dataModel.setData("");
                dataModel.setMsg("TokenId为空，请传入TokenId!");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
            //orderId
            String orderId = jsonobject.getString("orderId");
            //判断orderId是否为空，如果为空，直接return
            if (orderId == "" || orderId == null) {
                dataModel.setData("");
                dataModel.setMsg("orderId为空，请传入orderId!");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
            //查询longinTokenId，是否一致
            SysAppUsers appUsers = appUserService.getSysUserBylonginTokenId(longinTokenId);
            if (appUsers == null) {
                dataModel.setData("");
                dataModel.setMsg("TokenId已过期，请重新登陆!");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
            SysOrder selectOrderByPrimaryKey = sysOrderService.getOrderState(Long.parseLong(orderId));
            if (selectOrderByPrimaryKey == null) {
                dataModel.setData("");
                dataModel.setMsg("订单查询失败！");
                dataModel.setState(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
            SysRobot robotCode = sysRobotService.getRobotCodeBy(selectOrderByPrimaryKey.getOrderRobotCode());
            if (robotCode == null) {
                dataModel.setData("");
                dataModel.setMsg("机器人查询失败！");
                dataModel.setState(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
            dataModel.setData("");
            dataModel.setMsg("管理系统推送机器人状态！");
            dataModel.setState(Constant.STATE_SUCCESS);
            dataModel.setType(Constant.END_THE_JOURNEY);
            // 转JSON格式发送到个推
            String robotUnlock = JsonUtils.toString(dataModel);
            String encode = AES.encode(robotUnlock);// 加密推送
            // 个推推送消息到APP端
            String isSuccess = WeChatGtRobotAppPush.singlePush(robotCode.getRobotCodeCid(), encode, "成功!");
            if ("1".equals(isSuccess)) {
                selectOrderByPrimaryKey.setOrderStatus("30");
                selectOrderByPrimaryKey.setOrderEndTime(DateUtil.currentDateTime());
                selectOrderByPrimaryKey.setUpdateDate(DateUtil.currentDateTime());
                sysOrderService.updateOrder(selectOrderByPrimaryKey);
                dataModel.setData("");
                dataModel.setMsg("发送成功！");
                dataModel.setState(Constant.STATE_SUCCESS);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            } else {
                dataModel.setData("");
                dataModel.setMsg("发送失败！");
                dataModel.setState(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
        }
        catch (Exception e) {
            // TODO: handle exception
            logger.info("结算VIP订单",e);
            dataModel.setData("");
            dataModel.setMsg("订单查询失败!");
            dataModel.setState(Constant.STATE_FAILURE);
            dataModel.setType(Constant.STATE_FAILURE);
            String model = JsonUtils.toString(dataModel);//对象转JSON
            return AES.encode(model);//加密返回
        }

    }
    /**
     * @Author zhang
     * @Description 退调度费
     * @Date 17:26 2022/3/21
     * @Param [content]
     * @return java.lang.String
     **/

    @RequestMapping(value = "/refundOfDispatchingFee",method = RequestMethod.POST)
    @ResponseBody
    public String refundOfDispatchingFee(HttpServletRequest request,String content) {

        ReturnModel dataModel = new ReturnModel();
        try {
            Thread.currentThread().sleep(1000);
            if (content == null || "".equals(content)) {
                dataModel.setData("");
                dataModel.setMsg("加密参数不能为空！");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
            String decode = AES.decode(content);//参数加密
            //解密后进行JSON解析
            JSONObject jsonobject = JSON.parseObject(decode);
            //TokenId
            String longinTokenId = jsonobject.getString("longinTokenId");
            //判断longinTokenId是否为空，如果为空，直接return
            if (longinTokenId == "" || longinTokenId == null) {
                dataModel.setData("");
                dataModel.setMsg("TokenId为空，请传入TokenId!");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
            //orderId
            String orderId = jsonobject.getString("orderId");
            //判断orderId是否为空，如果为空，直接return
            if (orderId == "" || orderId == null) {
                dataModel.setData("");
                dataModel.setMsg("orderId为空，请传入orderId!");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
            //查询longinTokenId，是否一致
            SysAppUsers appUsers = appUserService.getSysUserBylonginTokenId(longinTokenId);
            if (appUsers == null) {
                dataModel.setData("");
                dataModel.setMsg("TokenId已过期，请重新登陆!");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
            SysOrder selectOrderByPrimaryKey = sysOrderService.getOrderState(Long.parseLong(orderId));
            if (selectOrderByPrimaryKey == null) {
                dataModel.setData("");
                dataModel.setMsg("订单查询失败！");
                dataModel.setState(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
            if ("1".equals(selectOrderByPrimaryKey.getIsDispatchingFee())){
                dataModel.setData("");
                dataModel.setMsg("该订单已经退过调度费！");
                dataModel.setState(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }


            if ("0".equals(selectOrderByPrimaryKey.getDispatchingFee())){
                dataModel.setData("");
                dataModel.setMsg("该订单没有调度费！");
                dataModel.setState(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }


            SysCurrentUser currentUserById = sysCurrentUserService.getCurrentUserById(selectOrderByPrimaryKey.getUserId());
            if (StringUtils.isEmpty(currentUserById)){
                dataModel.setData("");
                dataModel.setMsg("该订单没有用户！");
                dataModel.setState(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }

            SysRobot robotCode = sysRobotService.getRobotCodeBy(selectOrderByPrimaryKey.getOrderRobotCode());
            if (robotCode == null) {
                dataModel.setData("");
                dataModel.setMsg("机器人查询失败！");
                dataModel.setState(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
//            if (selectOrderByPrimaryKey.getOrderScenicSpotId() != 83102483267695l){
//                dataModel.setData("");
//                dataModel.setMsg("暂不支持退调度费！");
//                dataModel.setState(Constant.STATE_FAILURE);
//                String model = JsonUtils.toString(dataModel);//对象转JSON
//                return AES.encode(model);
//            }

            if ("1".equals(selectOrderByPrimaryKey.getRefundStatus())){
                dataModel.setData("");
                dataModel.setMsg("该订单客服正在处理中，请勿重复操作！");
                dataModel.setState(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }else{
                int i = sysOrderService.modifyRefundStatus(selectOrderByPrimaryKey.getOrderId(), "1");
            }

//            if (true){
//                dataModel.setData("");
//                dataModel.setMsg("暂不支持退调度费！");
//                dataModel.setState(Constant.STATE_FAILURE);
//                String model = JsonUtils.toString(dataModel);//对象转JSON
//                return AES.encode(model);//加密返回
//            }
            //退调度费
            //微信
            int i = 0;
            if ("1".equals(selectOrderByPrimaryKey.getPaymentMethod()) && "0".equals(selectOrderByPrimaryKey.getSubMethod())){
                 i = sysOrderService.refundOfDispatchingFee(request,selectOrderByPrimaryKey.getOrderId(),appUsers);
            }else if ("1".equals(selectOrderByPrimaryKey.getPaymentMethod()) && "2".equals(selectOrderByPrimaryKey.getSubMethod())) {
            //储值抵扣支付
                i = sysOrderService.storedValueDeductionRefund(request,selectOrderByPrimaryKey.getOrderId(),appUsers);
            }else if ("3".equals(selectOrderByPrimaryKey.getPaymentMethod())){
                //储值余额
                i =  sysOrderService.refundOfDispatchingFeeStored(request,selectOrderByPrimaryKey.getOrderId(),appUsers);
            }else if("5".equals(selectOrderByPrimaryKey.getPaymentMethod())) {
                //押金支付
                dataModel.setData("0");
                dataModel.setMsg("APP暂不支持押金抵扣订单退调度费，请联系客服处理");
                dataModel.setState(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }

            if (1==i) {
                dataModel.setData("1");
                dataModel.setMsg("退款成功");
                dataModel.setState(Constant.STATE_SUCCESS);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            } else {
                dataModel.setData("0");
                dataModel.setMsg("退款失败！");
                dataModel.setState(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
        }
        catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            logger.info("退调度费订单",e);
            dataModel.setData("");
            dataModel.setMsg("订单查询失败!");
            dataModel.setState(Constant.STATE_FAILURE);
            dataModel.setType(Constant.STATE_FAILURE);
            String model = JsonUtils.toString(dataModel);//对象转JSON
            return AES.encode(model);//加密返回
        }

    }




    /**
     * @Author zhang
     * @Description 减调度费
     * @Date 17:26 2022/3/21
     * @Param [content]
     * @return java.lang.String
     **/
    @RequestMapping(value = "/reduceOfDispatchingFee",method = RequestMethod.POST)
    @ResponseBody
    public String reduceOfDispatchingFee(HttpServletRequest request,String content) {

        ReturnModel dataModel = new ReturnModel();
        try {
            if (content == null || "".equals(content)) {
                dataModel.setData("");
                dataModel.setMsg("加密参数不能为空！");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
            String decode = AES.decode(content);//参数加密
            //解密后进行JSON解析
            JSONObject jsonobject = JSON.parseObject(decode);
            //TokenId
            String longinTokenId = jsonobject.getString("longinTokenId");
            //判断longinTokenId是否为空，如果为空，直接return
            if (longinTokenId == "" || longinTokenId == null) {
                dataModel.setData("");
                dataModel.setMsg("TokenId为空，请传入TokenId!");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
            //orderId
            String orderId = jsonobject.getString("orderId");
            //判断orderId是否为空，如果为空，直接return
            if (orderId == "" || orderId == null) {
                dataModel.setData("");
                dataModel.setMsg("orderId为空，请传入orderId!");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
            //查询longinTokenId，是否一致
            SysAppUsers appUsers = appUserService.getSysUserBylonginTokenId(longinTokenId);
            if (appUsers == null) {
                dataModel.setData("");
                dataModel.setMsg("TokenId已过期，请重新登陆!");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
            SysOrder selectOrderByPrimaryKey = sysOrderService.getOrderState(Long.parseLong(orderId));
            if (selectOrderByPrimaryKey == null) {
                dataModel.setData("");
                dataModel.setMsg("订单查询失败！");
                dataModel.setState(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
            if ("0".equals(selectOrderByPrimaryKey.getDispatchingFee())){
                dataModel.setData("");
                dataModel.setMsg("该订单没有调度费！");
                dataModel.setState(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }

            if ("0".equals(selectOrderByPrimaryKey.getIsPaying())){
                dataModel.setData("");
                dataModel.setMsg("用户正在付款，无法进行订单金额修改操作！");
                dataModel.setState(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }

            SysCurrentUser currentUserById = sysCurrentUserService.getCurrentUserById(selectOrderByPrimaryKey.getUserId());
            if (StringUtils.isEmpty(currentUserById)){
                dataModel.setData("");
                dataModel.setMsg("该订单没有用户！");
                dataModel.setState(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }

            SysRobot robotCode = sysRobotService.getRobotCodeBy(selectOrderByPrimaryKey.getOrderRobotCode());
            if (robotCode == null) {
                dataModel.setData("");
                dataModel.setMsg("机器人查询失败！");
                dataModel.setState(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }

            if (selectOrderByPrimaryKey.getOrderScenicSpotId() != 83102483267695l){
                dataModel.setData("");
                dataModel.setMsg("暂不支持退调度费！");
                dataModel.setState(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);
            }

            //减调度费
            int i = sysOrderService.reduceOfDispatchingFee(request,selectOrderByPrimaryKey.getOrderId());

//            dataModel.setData("");
//            dataModel.setMsg("管理系统推送机器人状态！");
//            dataModel.setState(Constant.STATE_SUCCESS);
//            dataModel.setType(Constant.END_THE_JOURNEY);
//            // 转JSON格式发送到个推
//            String robotUnlock = JsonUtils.toString(dataModel);
//            String encode = AES.encode(robotUnlock);// 加密推送
//            // 个推推送消息到APP端
//            String isSuccess = WeChatGtRobotAppPush.singlePush(robotCode.getRobotCodeCid(), encode, "成功!");
            if (1==i) {


                dataModel.setData("1");
                dataModel.setMsg("减免成功");
                dataModel.setState(Constant.STATE_SUCCESS);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            } else if (2==i){
                dataModel.setData("0");
                dataModel.setMsg("该笔订单已支付，无法在减免订单费，请联系客服进行退调度费。");
                dataModel.setState(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }else if (3 == i){
                dataModel.setData("0");
                dataModel.setMsg("该笔订单已经退过订单费，无需减免调度费。");
                dataModel.setState(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }else{
                dataModel.setData("0");
                dataModel.setMsg("减免失败，请联系客服处理。");
                dataModel.setState(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
        }
        catch (Exception e) {
            // TODO: handle exception
            logger.info("减调度费订单",e);
            dataModel.setData("");
            dataModel.setMsg("减调度费失败!");
            dataModel.setState(Constant.STATE_FAILURE);
            dataModel.setType(Constant.STATE_FAILURE);
            String model = JsonUtils.toString(dataModel);//对象转JSON
            return AES.encode(model);//加密返回
        }

    }

    @RequestMapping(value = "/isItEmpty",method = RequestMethod.POST)
    @ResponseBody
    public ReturnModel isItEmpty(HttpServletRequest request,String type) {
        ReturnModel dataModel = new ReturnModel();

        int i = sysOrderService.exitIsItEmpty(type);
        if (i== 1){
            dataModel.setData("1");
            dataModel.setMsg("修改成功");
            dataModel.setState(Constant.STATE_SUCCESS);

        }else{
            dataModel.setData("0");
            dataModel.setMsg("修改失败");
            dataModel.setState(Constant.STATE_SUCCESS);
        }


        return dataModel;
    }
}
