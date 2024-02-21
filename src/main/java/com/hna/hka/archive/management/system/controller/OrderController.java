package com.hna.hka.archive.management.system.controller;

import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.hna.hka.archive.management.system.model.*;
import com.hna.hka.archive.management.system.model.ExternalData.PayOrder;
import com.hna.hka.archive.management.system.model.ExternalData.RefundOrderList;
import com.hna.hka.archive.management.system.model.ExternalData.RentRecord;
import com.hna.hka.archive.management.system.model.ExternalData.User;
import com.hna.hka.archive.management.system.service.*;
import com.hna.hka.archive.management.system.service.SysCurrentUserAccountRefundLogService;
import com.hna.hka.archive.management.system.util.*;
import com.hna.hka.archive.management.system.util.Point;
import com.hna.hka.archive.management.wenYuRiverInterface.model.BaseQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.shiro.crypto.hash.Hash;
import org.jdom.JDOMException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.crypto.dsig.keyinfo.RetrievalMethod;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;
import java.util.List;

/**
 * @ProjectName: IDEA-CODE
 * @Package: com.hna.hka.archive.management.system.controller
 * @ClassName: OrderController
 * @Author: 郭凯
 * @Description: 订单管理控制层
 * @Date: 2020/5/23 14:50
 * @Version: 1.0
 */
@Api(tags = "订单")
@RequestMapping("/system/order")
@Controller
@Transactional
public class OrderController extends PublicUtil {

    @Autowired
    private SysOrderService sysOrderService;

    @Autowired
    private SysOrderLogStoredService sysOrderLogStoredService;

    @Autowired
    private SysCurrentUserService sysCurrentUserService;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private HttpSession session;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private SysRobotService sysRobotService;

    @Autowired
    private SysScenicSpotGpsCoordinateService sysScenicSpotGpsCoordinateService;

    @Autowired
    private SysScenicSpotService sysScenicSpotService;

    @Autowired
    private SysScenicSpotParkingService sysScenicSpotParkingService;

    @Autowired
    private SysScenicSpotCertificateSpotService sysScenicSpotCertificateSpotService;

    @Autowired
    private SysOrderLogService sysOrderLogService;

    @Autowired
    private SysCurrentUserAccountService sysCurrentUserAccountService;

    @Autowired
    private SysScenicSpotCapPriceService sysScenicSpotCapPriceService;

    @Autowired
    private SysCurrentUserAccountDeductionService sysCurrentUserAccountDeductionService;

    @Autowired
    private SysOrderCurrentLogService sysOrderCurrentLogService;

    @Autowired
    private SysCurrentUserAccountOrderService sysCurrentUserAccountOrderService;
    //    @Autowired
//    private SysCurrentUserAccountRefundLogService sysCurrentUserAccountRefundLogService;
//
    @Autowired
    private WeChatInfo weChatInfo;

    @Autowired
    private AlipayInfo alipayInfo;

    @Value("${wxpay.appid}")
    private String appid; //获取微信小程序APPID

    @Value("${wxpay.mchid}")
    private String mchid; //获取默认商户ID

    @Value("${wxpay.apiSecretkey}")
    private String apiSecretkey; //获取微信小程序唯一密钥32位

    @Value("${wxpay.depositCallbackInterface}")
    private String depositCallbackInterface; //获取回调接口

    @Value("${wxpay.wechatRefundRequest}")
    private String wechatRefundRequest; //获取微信支付链接

    @Value("${wxpay.fileCertPath}")
    private String fileCertPath; //获取默认文件路径

    @Value("${wxpay.certPath}")
    private String certPath; //获取文件路径
    @Value("${wxpay.closeOrder}")
    private String closeOrder;

    /**
     * @return com.hna.hka.archive.management.system.util.PageDataResult
     * @Author 郭凯
     * @Description 订单管理列表查询
     * @Date 15:25
     * @Param [pageNum, pageSize, sysOrder]
     **/
    @RequestMapping("/getOrderList")
    @ResponseBody
    public PageDataResult getOrderList(@RequestParam("pageNum") Integer pageNum,
                                       @RequestParam("pageSize") Integer pageSize, SysOrder sysOrder) {
        PageDataResult pageDataResult = new PageDataResult();
        Map<String, Object> search = new HashMap<>();
        Map<String, Object> priceSearch = new HashMap<>();
        SysUsers SysUsers = this.getSysUser();
        SysOrder sysOrderPrice = new SysOrder();
        try {
            if (null == pageNum) {
                pageNum = 1;
            }
            if (null == pageSize) {
                pageSize = 10;
            }
            String startTime = request.getParameter("startTime");
            String endTime = request.getParameter("endTime");


            if (org.springframework.util.StringUtils.isEmpty(sysOrder.getCurrentUserPhone()) && org.springframework.util.StringUtils.isEmpty(sysOrder.getOrderRobotCode()) && org.springframework.util.StringUtils.isEmpty(sysOrder.getOrderScenicSpotId()) && org.springframework.util.StringUtils.isEmpty(sysOrder.getOrderStatus())) {

                if (org.springframework.util.StringUtils.isEmpty(startTime) || org.springframework.util.StringUtils.isEmpty(endTime)) {
                    search.put("startTime", DateUtil.crutDate());
                    search.put("endTime", DateUtil.addDay(DateUtil.crutDate(), 1));
                } else {
                    search.put("startTime", startTime);
                    search.put("endTime", DateUtil.addDay(endTime, 1));
                }
            } else {
                if (org.springframework.util.StringUtils.isEmpty(startTime) || org.springframework.util.StringUtils.isEmpty(endTime)) {
                    search.put("startTime", DateUtil.addDay(DateUtil.crutDate(), -30));
                    search.put("endTime", DateUtil.addDay(DateUtil.crutDate(), 1));
                } else {
                    search.put("startTime", startTime);
                    search.put("endTime", DateUtil.addDay(endTime, 1));
                }
            }
            search.put("currentUserPhone", sysOrder.getCurrentUserPhone());
            search.put("paymentMethod", sysOrder.getPaymentMethod());
            search.put("subMethod", sysOrder.getSubMethod());
            search.put("orderScenicSpotId", sysOrder.getOrderScenicSpotId());
//            if (ToolUtil.isEmpty(sysOrder.getOrderStatus())){
//                sysOrder.setOrderStatus("10,20,30");
//            }
            search.put("orderStatus", sysOrder.getOrderStatus());
            search.put("orderRobotCode", sysOrder.getOrderRobotCode());
            search.put("userId", SysUsers.getUserId());
            search.put("paymentPort", sysOrder.getPaymentPort());
            search.put("orderParkingId", sysOrder.getOrderParkingId());
            search.put("type", sysOrder.getParkingType());
            search.put("huntsState", sysOrder.getHuntsState());
            pageDataResult = sysOrderService.getOrderList(pageNum, pageSize, search);

            if (org.springframework.util.StringUtils.isEmpty(startTime) || org.springframework.util.StringUtils.isEmpty(endTime)) {
                priceSearch.put("currentUserPhone", sysOrder.getCurrentUserPhone());
                priceSearch.put("paymentMethod", sysOrder.getPaymentMethod());
                priceSearch.put("subMethod", sysOrder.getSubMethod());
                priceSearch.put("orderScenicSpotId", sysOrder.getOrderScenicSpotId());
                priceSearch.put("orderStatus", sysOrder.getOrderStatus());
                priceSearch.put("orderRobotCode", sysOrder.getOrderRobotCode());
                priceSearch.put("userId", SysUsers.getUserId());
                priceSearch.put("startTime", DateUtil.crutDate());
                priceSearch.put("endTime", DateUtil.addDay(DateUtil.crutDate(), 1));
                priceSearch.put("paymentPort", sysOrder.getPaymentPort());
                priceSearch.put("orderParkingId", sysOrder.getOrderParkingId());
                priceSearch.put("type", sysOrder.getParkingType());
                priceSearch.put("huntsState", sysOrder.getHuntsState());
                sysOrderPrice = sysOrderService.getOrderAmount(priceSearch);
                pageDataResult.setRealIncome(sysOrderPrice.getRealIncomes());
                pageDataResult.setPaymentTotalAccount(sysOrderPrice.getPaymentTotalAccount());

            }

        } catch (Exception e) {
            logger.info("订单管理列表查询失败", e);
        }
        return pageDataResult;
    }

    /**
     * @return com.hna.hka.archive.management.system.util.ReturnModel
     * @Author 郭凯
     * @Description 查询订单状态
     * @Date 22:04 2020/5/23
     * @Param []
     **/
    @RequestMapping("/getOrderStateName")
    @ResponseBody
    public ReturnModel getOrderStateName() {
        ReturnModel returnModel = new ReturnModel();
        try {
            List<SysOrder> sysOrderList = new ArrayList<SysOrder>();
            for (String key : DictUtils.getOrderStateMap().keySet()) {
                String value = DictUtils.getOrderStateMap().get(key);
                SysOrder sysRobot = new SysOrder();
                sysRobot.setOrderStatus(key);
                sysRobot.setOrderStatusName(value);
                sysOrderList.add(sysRobot);
            }
            returnModel.setData(sysOrderList);
        } catch (Exception e) {
            logger.info("getOrderStateName", e);
        }
        return returnModel;
    }

    /**
     * @return com.hna.hka.archive.management.system.util.ReturnModel
     * @Author 郭凯
     * @Description 查询订单状态回显数据
     * @Date 22:07 2020/5/23
     * @Param [orderId]
     **/
    @RequestMapping("/getOrderState")
    @ResponseBody
    public ReturnModel getOrderState(@RequestParam("orderId") Long orderId) {
        ReturnModel returnModel = new ReturnModel();
        try {
            SysOrder SysOrder = sysOrderService.getOrderState(orderId);
            if (!ToolUtil.isEmpty(SysOrder)) {
                returnModel.setData(SysOrder);
                returnModel.setMsg("成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            } else {
                returnModel.setData("");
                returnModel.setMsg("查询失败！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        } catch (Exception e) {
            logger.info("getOrderState", e);
        }
        return returnModel;
    }

    /**
     * @return com.hna.hka.archive.management.system.util.ReturnModel
     * @Author 郭凯
     * @Description 更改订单状态（旧）
     * @Date 22:11 2020/5/23
     * @Param [sysRobot]
     **/
    @RequestMapping("/updateOrderStates")
    @ResponseBody
    public ReturnModel updateOrderStates(SysOrder sysRobot) {
        ReturnModel dataModel = new ReturnModel();
        SysUsers currentUser = this.getSysUser();
        SysOrder order = null;
        try {
            order = sysOrderService.getOrderByNumber(sysRobot.getOrderNumber());
            String reason = request.getParameter("reason");
            String reasonsRefunds = request.getParameter("reasonsRefunds");
            if (ToolUtil.isEmpty(reason) && ToolUtil.isEmpty(reasonsRefunds)) {
                dataModel.setData("");
                dataModel.setMsg("请选择修改原因！");
                dataModel.setState(Constant.STATE_FAILURE);
                return dataModel;
            }
            if (ToolUtil.isNotEmpty(reasonsRefunds)) {
                reason = reason + reasonsRefunds;
            }
            if (order != null) {
                if ("10".equals(sysRobot.getOrderStatus())) {//进行中
                    dataModel.setData("");
                    dataModel.setMsg("当前订单不能修改为进行中状态！");
                    dataModel.setState(Constant.STATE_FAILURE);
                    return dataModel;
                } else if ("20".equals(sysRobot.getOrderStatus())) {//待付款
                    dataModel.setData("");
                    dataModel.setMsg("当前订单不能修改为待付款状态！");
                    dataModel.setState(Constant.STATE_FAILURE);
                    return dataModel;
                } else if ("30".equals(sysRobot.getOrderStatus())) {//已付款
                    if (order.getOrderStatus().equals("20")) {
                        //添加修改订单金额日志
                        SysOrderOperationLog operationLog = new SysOrderOperationLog();
                        operationLog.setOperationId(IdUtils.getSeqId());
                        operationLog.setOperationName(currentUser.getUserName());
                        operationLog.setOperationNumber(order.getOrderNumber());
                        operationLog.setOperationPhone(order.getCurrentUserPhone());
                        operationLog.setOperationScenicSpotName(order.getOrderScenicSpotName());
                        operationLog.setOperationRobotCode(order.getOrderRobotCode());
                        operationLog.setOperationTotalTime(order.getTotalTime());
                        operationLog.setOperationFront("未付款");
                        operationLog.setOperationAfter("已付款");
                        operationLog.setCreateDate(DateUtil.currentDateTime());
                        sysOrderService.insertOrderOperationLog(operationLog);
                        SysCurrentUser currenUser = new SysCurrentUser();
                        currenUser.setCurrentUserId(order.getUserId());
                        currenUser.setCreditArrearsState("10");
                        //修改用户是否欠款状态
                        sysCurrentUserService.updateCurrenUser(currenUser);
                        order.setOrderStatus("30");
                        order.setReasonsRefunds(reason);
                        order.setUpdateDate(DateUtil.currentDateTime());
                        int i = sysOrderService.updateOrderStatus(order);
                        if (i == 1) {
                            dataModel.setData("");
                            dataModel.setMsg("订单状态修改成功！");
                            dataModel.setState(Constant.STATE_SUCCESS);
                            return dataModel;
                        } else {
                            dataModel.setData("");
                            dataModel.setMsg("订单状态修改失败！");
                            dataModel.setState(Constant.STATE_FAILURE);
                            return dataModel;
                        }
                    } else {
                        dataModel.setData("");
                        dataModel.setMsg("当前订单不能修改为待付款状态！");
                        dataModel.setState(Constant.STATE_FAILURE);
                        return dataModel;
                    }
                } else if ("40".equals(sysRobot.getOrderStatus())) {//交易关闭
                    //修改订单状态
                    if (order.getOrderStatus().equals("20")) {
                        //查询用户账户信息
                        SysCurrentUserAccount currentUserAccount = sysCurrentUserAccountService.selectAccountByUserId(order.getUserId());
                        if (ToolUtil.isNotEmpty(currentUserAccount)) {
                            SysCurrentUserAccount userAccount = new SysCurrentUserAccount();
                            userAccount.setAccountId(currentUserAccount.getAccountId());
                            userAccount.setLoginStatus("0");
                            //修改用户账户登录状态
                            sysCurrentUserAccountService.editCurrentUserAccount(userAccount);
                        }
                        SysOrderOperationLog operationLog = new SysOrderOperationLog();
                        operationLog.setOperationId(IdUtils.getSeqId());
                        operationLog.setOperationName(currentUser.getUserName());
                        operationLog.setOperationNumber(order.getOrderNumber());
                        operationLog.setOperationPhone(order.getCurrentUserPhone());
                        operationLog.setOperationScenicSpotName(order.getOrderScenicSpotName());
                        operationLog.setOperationRobotCode(order.getOrderRobotCode());
                        operationLog.setOperationTotalTime(order.getTotalTime());
                        operationLog.setOperationFront("未付款");
                        operationLog.setOperationAfter("交易关闭");
                        operationLog.setCreateDate(DateUtil.currentDateTime());
                        sysOrderService.insertOrderOperationLog(operationLog);
                        SysCurrentUser currenUser = new SysCurrentUser();
                        currenUser.setCurrentUserId(order.getUserId());
                        currenUser.setCreditArrearsState("10");
                        //修改用户是否欠款状态
                        sysCurrentUserService.updateCurrenUser(currenUser);
                        order.setOrderStatus("40");
                        order.setReasonsRefunds(reason);
                        order.setUpdateDate(DateUtil.currentDateTime());
                        int i = sysOrderService.updateOrderStatus(order);
                        if (i == 1) {
                            dataModel.setData("");
                            dataModel.setMsg("订单状态修改成功！");
                            dataModel.setState(Constant.STATE_SUCCESS);
                            return dataModel;
                        } else {
                            dataModel.setData("");
                            dataModel.setMsg("订单状态修改失败！");
                            dataModel.setState(Constant.STATE_FAILURE);
                            return dataModel;
                        }
                    } else {
                        dataModel.setData("");
                        dataModel.setMsg("当前订单不能修改为交易关闭状态！");
                        dataModel.setState(Constant.STATE_FAILURE);
                        return dataModel;
                    }
                } else if ("50".equals(sysRobot.getOrderStatus())) {
                    dataModel.setData("");
                    dataModel.setMsg("当前订单不能修改为免单状态！");
                    dataModel.setState(Constant.STATE_FAILURE);
                    return dataModel;
                } else if ("60".equals(sysRobot.getOrderStatus())) {
                    dataModel.setData("");
                    dataModel.setMsg("当前订单不能修改为全额退款状态！");
                    dataModel.setState(Constant.STATE_FAILURE);
                    return dataModel;
                }
            } else {
                dataModel.setData("");
                dataModel.setMsg("订单查询失败！");
                dataModel.setState(Constant.STATE_FAILURE);
                return dataModel;
            }

        } catch (Exception e) {
            logger.error("updateOrderState", e.getMessage());
        }
        return dataModel;
    }

    /**
     * 迭代接口，修改订单状态
     *
     * @return
     * @Time:2024/1/4
     * @param:sysRobot
     */
    @RequestMapping("/updateOrderState")
    @ResponseBody
    public ReturnModel updateOrderState(SysOrder sysRobot) {
        ReturnModel dataModel = new ReturnModel();
        SysUsers currentUser = this.getSysUser();
        SysOrder order = null;
        try {
            order = sysOrderService.getOrderByNumber(sysRobot.getOrderNumber());
            String reason = request.getParameter("reason");
            String reasonsRefunds = request.getParameter("reasonsRefunds");
            if (ToolUtil.isEmpty(reason) && ToolUtil.isEmpty(reasonsRefunds)) {
                dataModel.setData("");
                dataModel.setMsg("请选择修改原因！");
                dataModel.setState(Constant.STATE_FAILURE);
                return dataModel;
            }
            if (ToolUtil.isNotEmpty(reasonsRefunds)) {
                reason = reason + reasonsRefunds;
            }
            if (order != null) {
                if ("10".equals(sysRobot.getOrderStatus())) {//进行中
                    dataModel.setData("");
                    dataModel.setMsg("当前订单不能修改为进行中状态！");
                    dataModel.setState(Constant.STATE_FAILURE);
                    return dataModel;
                } else if ("20".equals(sysRobot.getOrderStatus())) {//待付款
                    dataModel.setData("");
                    dataModel.setMsg("当前订单不能修改为待付款状态！");
                    dataModel.setState(Constant.STATE_FAILURE);
                    return dataModel;
                } else if ("30".equals(sysRobot.getOrderStatus())) {//已付款
                    if (order.getOrderStatus().equals("20")) {
                        //添加修改订单金额日志
                        SysOrderOperationLog operationLog = new SysOrderOperationLog();
                        operationLog.setOperationId(IdUtils.getSeqId());
                        operationLog.setOperationName(currentUser.getUserName());
                        operationLog.setOperationNumber(order.getOrderNumber());
                        operationLog.setOperationPhone(order.getCurrentUserPhone());
                        operationLog.setOperationScenicSpotName(order.getOrderScenicSpotName());
                        operationLog.setOperationRobotCode(order.getOrderRobotCode());
                        operationLog.setOperationTotalTime(order.getTotalTime());
                        operationLog.setOperationFront("未付款");
                        operationLog.setOperationAfter("已付款");
                        operationLog.setCreateDate(DateUtil.currentDateTime());
                        sysOrderService.insertOrderOperationLog(operationLog);
                        SysCurrentUser currenUser = new SysCurrentUser();
                        currenUser.setCurrentUserId(order.getUserId());
                        currenUser.setCreditArrearsState("10");
                        //修改用户是否欠款状态
                        sysCurrentUserService.updateCurrenUser(currenUser);
                        order.setOrderStatus("30");
                        order.setReasonsRefunds(reason);
                        order.setUpdateDate(DateUtil.currentDateTime());
                        int i = sysOrderService.updateOrderStatus(order);
                        if (i == 1) {
                            dataModel.setData("");
                            dataModel.setMsg("订单状态修改成功！");
                            dataModel.setState(Constant.STATE_SUCCESS);
                            return dataModel;
                        } else {
                            dataModel.setData("");
                            dataModel.setMsg("订单状态修改失败！");
                            dataModel.setState(Constant.STATE_FAILURE);
                            return dataModel;
                        }
                    } else {
                        dataModel.setData("");
                        dataModel.setMsg("当前订单不能修改为待付款状态！");
                        dataModel.setState(Constant.STATE_FAILURE);
                        return dataModel;
                    }
                } else if ("40".equals(sysRobot.getOrderStatus())) {//交易关闭
                    String merchantSecret = null;
                    String merchantNumber = null;
                    //修改订单状态
                    if (order.getOrderStatus().equals("20")) {
                        Map<String, Object> search = new HashMap<>();
                        search.put("scenicSpotId", order.getOrderScenicSpotId());
                        WechatBusinessManagement businessManagement = sysScenicSpotCertificateSpotService.selectById(search);
                        if (businessManagement != null) {
                            merchantSecret = businessManagement.getMerchantSecret();
                            merchantNumber = businessManagement.getMerchantNumber();
                        } else {
                            merchantSecret = apiSecretkey;// 获取微信小程序唯一密钥32位
                            merchantNumber = mchid; //获取默认商户ID
                        }
                        SortedMap<Object, Object> closeOrderStatus = OrderUtil.closeOrder(sysRobot.getOrderNumber(), appid, merchantNumber, merchantSecret, closeOrder);
                        // 处理每次迭代的结果
//                        if (closeOrderStatus != null && closeOrderStatus.get("return_code").toString().equals("SUCCESS") && closeOrderStatus.get("result_code").toString().equals("SUCCESS")) {

                        //查询用户账户信息
                        SysCurrentUserAccount currentUserAccount = sysCurrentUserAccountService.selectAccountByUserId(order.getUserId());
                        if (ToolUtil.isNotEmpty(currentUserAccount)) {
                            SysCurrentUserAccount userAccount = new SysCurrentUserAccount();
                            userAccount.setAccountId(currentUserAccount.getAccountId());
                            userAccount.setLoginStatus("0");
                            //修改用户账户登录状态
                            sysCurrentUserAccountService.editCurrentUserAccount(userAccount);
                        }
                        SysOrderOperationLog operationLog = new SysOrderOperationLog();
                        operationLog.setOperationId(IdUtils.getSeqId());
                        operationLog.setOperationName(currentUser.getUserName());
                        operationLog.setOperationNumber(order.getOrderNumber());
                        operationLog.setOperationPhone(order.getCurrentUserPhone());
                        operationLog.setOperationScenicSpotName(order.getOrderScenicSpotName());
                        operationLog.setOperationRobotCode(order.getOrderRobotCode());
                        operationLog.setOperationTotalTime(order.getTotalTime());
                        operationLog.setOperationFront("未付款");
                        operationLog.setOperationAfter("交易关闭");
                        operationLog.setCreateDate(DateUtil.currentDateTime());
                        sysOrderService.insertOrderOperationLog(operationLog);
                        SysCurrentUser currenUser = new SysCurrentUser();
                        currenUser.setCurrentUserId(order.getUserId());
                        currenUser.setCreditArrearsState("10");
                        //修改用户是否欠款状态
                        sysCurrentUserService.updateCurrenUser(currenUser);
                        order.setOrderStatus("40");
                        order.setReasonsRefunds(reason);
                        order.setUpdateDate(DateUtil.currentDateTime());
                        int i = sysOrderService.updateOrderStatus(order);
                        if (i == 1) {
                            dataModel.setData("");
                            dataModel.setMsg("订单状态修改成功！");
                            dataModel.setState(Constant.STATE_SUCCESS);
                            return dataModel;
                        } else {
                            dataModel.setData("");
                            dataModel.setMsg("订单状态修改失败！");
                            dataModel.setState(Constant.STATE_FAILURE);
                            return dataModel;
                        }
                    } else {
                        dataModel.setData("");
                        dataModel.setMsg("当前订单不能修改为交易关闭状态！");
                        dataModel.setState(Constant.STATE_FAILURE);
                        return dataModel;
                    }
                } else if ("50".equals(sysRobot.getOrderStatus())) {
                    dataModel.setData("");
                    dataModel.setMsg("当前订单不能修改为免单状态！");
                    dataModel.setState(Constant.STATE_FAILURE);
                    return dataModel;
                } else if ("60".equals(sysRobot.getOrderStatus())) {
                    dataModel.setData("");
                    dataModel.setMsg("当前订单不能修改为全额退款状态！");
                    dataModel.setState(Constant.STATE_FAILURE);
                    return dataModel;
                }
            } else {
                dataModel.setData("");
                dataModel.setMsg("订单查询失败！");
                dataModel.setState(Constant.STATE_FAILURE);
                return dataModel;
            }
//            }


        } catch (Exception e) {
            logger.error("updateOrderState", e.getMessage());
        }
        return dataModel;
    }

    /**
     * @return void
     * @Author 郭凯
     * @Description 订单下载Excel表
     * @Date 17:14 2020/5/27
     * @Param [response]
     **/
    @RequestMapping(value = "/uploadExcelOrder")
    public void uploadExcelOrder(HttpServletResponse response, SysOrder sysOrder, BaseQueryVo page) throws Exception {
        try {
            long start = System.currentTimeMillis() / 1000;//单位秒
            List<SysOrder> orderListByExample = null;
            Map<String, Object> search = new HashMap<>();
            SysUsers SysUsers = this.getSysUser();
//            search.put("currentUserPhone",sysOrder.getCurrentUserPhone());
            search.put("paymentMethod", sysOrder.getPaymentMethod());
            search.put("subMethod", sysOrder.getSubMethod());
            search.put("currentUserPhone", sysOrder.getCurrentUserPhone());
//            search.put("orderScenicSpotId",session.getAttribute("orderScenicSpotId"));
            search.put("orderScenicSpotId", sysOrder.getOrderScenicSpotId());
            search.put("orderStatus", sysOrder.getOrderStatus());
            search.put("orderRobotCode", sysOrder.getOrderRobotCode());
            search.put("huntsState", sysOrder.getHuntsState());
//            search.put("orderRobotCode",sysOrder.getOrderRobotCode());
            String startTime = request.getParameter("startTime");//获取开始时间
            String endTime = request.getParameter("endTime");//获取结束时间
            search.put("startTime", startTime);
            search.put("endTime", endTime);
            search.put("userId", SysUsers.getUserId().toString());
            search.put("orderParkingId", sysOrder.getOrderParkingId());
            search.put("type", sysOrder.getParkingType());
            //判断开始时间和结束是否为空，全部满足空的条件才已当前时间为条件查询
//            if (startTime == null || "".equals(endTime)) {
//                if (ToolUtil.isEmpty(sysOrder.getCurrentUserPhone()) && ToolUtil.isEmpty(sysOrder.getOrderScenicSpotId()) && ToolUtil.isEmpty(sysOrder.getOrderStatus()) && ToolUtil.isEmpty(sysOrder.getOrderRobotCode())) {
//                    search.put("time", DateUtil.crutDate());
//                }
//            }
            if (ToolUtil.isEmpty(startTime) || ToolUtil.isEmpty(endTime)) {
//                if (ToolUtil.isEmpty(sysOrder.getCurrentUserPhone()) && ToolUtil.isEmpty(sysOrder.getOrderScenicSpotId()) && ToolUtil.isEmpty(sysOrder.getOrderStatus()) && ToolUtil.isEmpty(sysOrder.getOrderRobotCode())) {
                search.put("time", DateUtil.crutDate());
//                }
            }
            OutputStream out = EasyExcel.getOutputStream(response, "景区订单" + DateFormatUtils.format(new Date(), "yyyyMMddHHmm"), ExcelTypeEnum.XLSX);
            ExcelWriter writer = new ExcelWriter(out, ExcelTypeEnum.XLSX, true);
            Sheet sheet = new Sheet(1, 0, SysOrderExcel.class);
            //设置自适应宽度
            sheet.setAutoWidth(Boolean.TRUE);
            //设置表格样式
            sheet.setTableStyle(EasyExcel.createTableStyle());
            //设置sheetName
            sheet.setSheetName("景区订单");
            List<SysOrderExcel> orderList1 = sysOrderService.getOrderVoExcelPoi(search);
            writer.write(orderList1, sheet);
            //关闭writer的输出流
            writer.finish();
            long end = System.currentTimeMillis() / 1000;
            logger.info("导出耗时：" + (end - start) + " 秒");
        } catch (Exception e) {
            logger.info("导出异常", e);
        }
    }

    /**
     * @return com.hna.hka.archive.management.system.util.ReturnModel
     * @Author 郭凯
     * @Description 结算订单
     * @Date 17:54 2020/6/22
     * @Param [orderId]
     **/
    @RequestMapping("/settlementOrder")
    @ResponseBody
    public ReturnModel settlementOrder(Long orderId) {
        ReturnModel returnModel = new ReturnModel();
        SysUsers SysUsers = this.getSysUser();
        //String source = "2";
        String dispatchingFee = "0";
        String coupon = "0";
        boolean flag = false;
        SysRobot robot = null;// 初始化机器人对象
        SysScenicSpot scenicSpots = null;
        String lon = null;
        String lat = null;
        SysScenicSpotGpsCoordinateWithBLOBs coordinate = null;// 初始化电子围栏对象
        try {
            if (ToolUtil.isEmpty(orderId)) {
                returnModel.setData("");
                returnModel.setMsg("订单ID为空！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
            //根据ID查询
            SysOrder SysOrder = sysOrderService.getOrderState(orderId);
            if (ToolUtil.isEmpty(SysOrder)) {
                returnModel.setData("");
                returnModel.setMsg("未查询到订单！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
            if (!"10".equals(SysOrder.getOrderStatus())) {
                returnModel.setData("");
                returnModel.setMsg("此订单已经结算！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
            // 根据机器人编号查询机器人数据
            robot = sysRobotService.getRobotCodeBy(SysOrder.getOrderRobotCode());
            //根据景区ID查询景区信息
            scenicSpots = sysScenicSpotService.getSysScenicSpotById(robot.getScenicSpotId());
            if (robot != null) {//&& ToolUtil.isNotEmpty(robot.getRobotGpsGpgga())
                // 当前机器人的GPS 小程序获取的GPS从数据库中查找
                SysRobotGPS sysRobotGPS = new SysRobotGPS();
                Point n1 = null;
                if (StringUtils.isNotBlank(lon) && StringUtils.isNotBlank(lat)) {
                    n1 = new Point(Double.valueOf(lon), Double.valueOf(lat));
                } else {
                    Object robotRedis = redisUtil.get(robot.getRobotCode());
                    JSONObject robots = JSONObject.fromObject(robotRedis);
                    Object sysRobot = JSONObject.toBean(robots, SysRobotGPS.class);
                    JSONObject objJson = JSONObject.fromObject(sysRobot);
                    sysRobotGPS = (SysRobotGPS) JSONObject.toBean(objJson, SysRobotGPS.class);
                    /**/
                    if (!ToolUtil.isEmpty(sysRobotGPS)) {
                        String[] split = sysRobotGPS.getRobotGpsGpgga().split(",");
                        n1 = new Point(Double.valueOf(split[0]), Double.valueOf(split[1]));//
                    } else if (!ToolUtil.isEmpty(robot.getRobotGpsGpgga())) {
                        String[] split = robot.getRobotGpsGpgga().split(",");// 获取机器人经纬度 并根据逗号截取
                        n1 = new Point(Double.valueOf(split[0]), Double.valueOf(split[1]));//
                    }

                    /**/
                }
                // 根据景区ID查询景区电子围栏
                coordinate = sysScenicSpotGpsCoordinateService.selectByPrimaryKey(robot.getScenicSpotId());
                if (coordinate != null) {// 判断是否在围栏内
                    String[] coordinateOuterring = coordinate.getCoordinateOuterring().split("!");// 获取WGS84围栏坐标组
                    if (coordinateOuterring != null && coordinateOuterring.length > 0) {
                        Point[] ps = new Point[coordinateOuterring.length];
                        for (int i = 0; i < coordinateOuterring.length; i++) {
                            String[] str = coordinateOuterring[i].split(",");
                            ps[i] = new Point(Double.valueOf(str[0]), Double.valueOf(str[1]));
                        }
                        flag = JudgingCoordinates.isPtInPoly(n1.getX(), n1.getY(), ps);
                    }
                    if (flag == false) {
                        dispatchingFee = scenicSpots.getScenicSpotBeyondPrice();
                        SysOrder.setParkingName("非停放点");
                        SysOrder.setOrderParkingId("0");
                    } else {// 在围栏圈内
                        //查询当前景区的停车点
                        List<SysScenicSpotParkingWithBLOBs> list = sysScenicSpotParkingService.getAllSportParkingByScenicSpotId(robot.getScenicSpotId(), "1");
                        if (list != null && list.size() > 0) {
                            for (SysScenicSpotParkingWithBLOBs sysScenicSpotParking : list) {
                                String[] split = sysScenicSpotParking.getParkingCoordinateGroup().split("!");
                                if (split != null && split.length > 0) {
                                    Point[] ps = new Point[split.length];
                                    for (int i = 0; i < split.length; i++) {
                                        String[] str = split[i].split(",");
                                        ps[i] = new Point(Double.valueOf(str[0]), Double.valueOf(str[1]));
                                    }
                                    flag = JudgingCoordinates.isPtInPoly(n1.getX(), n1.getY(), ps);
                                }
                                if (flag == true) {
                                    SysOrder.setParkingName(sysScenicSpotParking.getParkingName());
                                    SysOrder.setOrderParkingId(sysScenicSpotParking.getParkingId().toString());
                                    break;
                                }
                            }
                            if (flag == false) {
                                SysOrder.setParkingName("非停放点");
                                SysOrder.setOrderParkingId("0");
                                dispatchingFee = scenicSpots.getScenicSpotBeyondPrice();
                            }
                        } else {
                            SysOrder.setParkingName("非停放点");
                            SysOrder.setOrderParkingId("0");
                        }
                    }
                } else {
                    SysOrder.setParkingName("非停放点");
                    SysOrder.setOrderParkingId("0");
                }
            }
            SysScenicSpotCapPrice cap = sysScenicSpotCapPriceService.queryScenicSpotCapPrice(SysOrder.getOrderScenicSpotId());
//工作日第一档
            long normalCapOneTime = 10000;//一档工作日封顶时间
            float normalCapOnePrice = 0;//一档工作日封顶价格
            float normalCapOneUnitPrice = 0;//一档工作日封顶单价
//工作日第二档
            long normalCapTwoTime = 10000;//二档工作日封顶时间
            float normalCapTwoPrice = 0;//二档工作日封顶价格
            float normalCapTwoUnitPrice = 0;//二档工作日封顶单价
//周末第一档
            long weekendCapOneTime = 10000;//一档周末封顶时间
            float weekendCapOnePrice = 0;//一档周末封顶价格
            float weekendCapOneUnitPrice = 0;//一档周末封顶单价
//周末第二档
            long weekendCapTwoTime = 10000;//二档周末封顶时间
            float weekendCapTwoPrice = 0;//二档周末封顶价格
            float weekendCapTwoUnitPrice = 0;//二档周末封顶单价
            if (cap != null) {
                //工作日第一档
                normalCapOneTime = Long.parseLong(cap.getNormalCapOneTime());//一档工作日封顶时间
                normalCapOnePrice = Float.parseFloat(cap.getNormalCapOnePrice());//一档工作日封顶价格
                normalCapOneUnitPrice = Float.parseFloat(cap.getNormalCapOneUnitPrice());//一档工作日封顶单价
                //工作日第二档
                normalCapTwoTime = Long.parseLong(cap.getNormalCapTwoTime());//二档工作日封顶时间
                normalCapTwoPrice = Float.parseFloat(cap.getNormalCapTwoPrice());//二档工作日封顶价格
                normalCapTwoUnitPrice = Float.parseFloat(cap.getNormalCapTwoUnitPrice());//二档工作日封顶单价
                //周末第一档
                weekendCapOneTime = Long.parseLong(cap.getWeekendCapOneTime());//一档周末封顶时间
                weekendCapOnePrice = Float.parseFloat(cap.getWeekendCapOnePrice());//一档周末封顶价格
                weekendCapOneUnitPrice = Float.parseFloat(cap.getWeekendCapOneUnitPrice());//一档周末封顶单价
                //周末第二档
                weekendCapTwoTime = Long.parseLong(cap.getWeekendCapTwoTime());//二档周末封顶时间
                weekendCapTwoPrice = Float.parseFloat(cap.getWeekendCapTwoPrice());//二档周末封顶价格
                weekendCapTwoUnitPrice = Float.parseFloat(cap.getWeekendCapTwoUnitPrice());//二档周末封顶单价
            }

            String scenicSpotWeekendPrice = scenicSpots.getScenicSpotWeekendPrice();// 周末单价
            String scenicSpotNormalPrice = scenicSpots.getScenicSpotNormalPrice();// 工作日单价
            String scenicSpotWeekendRentPrice = null;// 周末起租价格 初始化
            String scenicSpotNormalRentPrice = null;// 工作日起租价格 初始化
            String scenicSpotRentTime = scenicSpots.getScenicSpotRentTime();// 起租时间 单位分钟
            String orderStartTime = SysOrder.getOrderStartTime();// 获取订单开启时间
            double totalAmount;// 总金额
//            double normalCappedAmount = Double.valueOf(scenicSpots.getNormalCappedPrice());// 工作日封顶价格
//            double weekendCappedAmount = Double.valueOf(scenicSpots.getWeekendCappedPrice());// 周末封顶价格
            long timeConversionMoney = DateUtil.timeConversionMoney(orderStartTime);// 当前时间和解锁机器人时间比较得到使用总时长（单位分钟）
            // 周末
            if (DateUtil.getWeekend() == true) {
                // 当前时间小于或等于起租时间
                if (timeConversionMoney <= Integer.valueOf(scenicSpotRentTime).intValue()) {
                    scenicSpotWeekendRentPrice = scenicSpots.getScenicSpotWeekendRentPrice();// 周末起租价格
                    // 是否存在优惠劵
                    if (StringUtils.isNotBlank(coupon)) {
                        scenicSpotWeekendRentPrice = String.valueOf(Double.valueOf(scenicSpotWeekendRentPrice) - Double.valueOf(coupon));// 是否存在优惠券
                    }

                    DecimalFormat df1 = new DecimalFormat("0.00");// 四舍五入保留两位小数
                    String total = df1.format(Double.parseDouble(scenicSpotWeekendRentPrice));// 转成字符串类型
//                    int i = sysOrderService.updateOrderItemByOrderId(orderId,timeConversionMoney, total, dispatchingFee, coupon,"20",scenicSpots.getGiftTimeSetting());// 周末更新订单状态以及产生的金额
//                    if (StringUtils.isEmpty(SysOrder.getOrderParkingId())){
//                    }

                    int i = sysOrderService.updateOrderItemByOrderIdNew(orderId, timeConversionMoney, total, dispatchingFee, coupon, "20", scenicSpots.getGiftTimeSetting(), SysOrder.getParkingName(), Long.parseLong(SysOrder.getOrderParkingId()));// 周末更新订单状态以及产生的金额

                    if (i == 1) {
                        SysOrder = sysOrderService.getOrderState(orderId);// 根据订单编号查询订单
                        SysOrderOperationLog operationLog = new SysOrderOperationLog();
                        operationLog.setOperationId(IdUtils.getSeqId());
                        operationLog.setOperationName(SysUsers.getUserName());
                        operationLog.setOperationNumber(SysOrder.getOrderNumber());
                        operationLog.setOperationPhone(SysOrder.getCurrentUserPhone());
                        operationLog.setOperationScenicSpotName(SysOrder.getOrderScenicSpotName());
                        operationLog.setOperationRobotCode(SysOrder.getOrderRobotCode());
                        operationLog.setOperationTotalTime(String.valueOf(timeConversionMoney));
                        operationLog.setOperationFront("结算订单");
                        operationLog.setOperationAfter("结算订单");
                        operationLog.setCreateDate(DateUtil.currentDateTime());
                        sysOrderService.insertOrderOperationLog(operationLog);
                        //删除缓存中的订单数据
//                        redisUtil.remove(SysOrder.getOrderNumber());
                        // 更改机器人状态为临时占用状态=============================================================>此处安卓主动调用后台接口修改机器人状态
                        // 为了模拟暂时由后台来更改
                        // 推送的实体对象到机器人端
                        returnModel.setData("");
                        returnModel.setMsg("结束行程！");
                        returnModel.setState(Constant.STATE_SUCCESS);
                        returnModel.setType(Constant.END_THE_JOURNEY);
                        // 转JSON格式发送到个推
                        String robotUnlock = JsonUtils.toString(returnModel);// 转JSON
                        String encode = AES.encode(robotUnlock);// 加密推送
                        // 个推推送消息到APP端
                        //判断是老版本pad还是新版本pad
                        if ("0".equals(robot.getRobotAppType())) {
                            WeChatGtRobotAppPush.singlePush(robot.getRobotCodeCid(), encode, "成功!");
                            returnModel.setData("");
                            returnModel.setMsg("当前费用：" + total);
                            returnModel.setState(Constant.STATE_SUCCESS);
                            return returnModel;
                        } else {
                            WeChatGtRobotAppPush.singlePushNew(robot.getNewRobotCodeCid(), encode, "成功!");
                            returnModel.setData("");
                            returnModel.setMsg("当前费用：" + total);
                            returnModel.setState(Constant.STATE_SUCCESS);
                            return returnModel;
                        }
                    }
                } else {// 超出起租时间后每分钟计费
                    scenicSpotWeekendRentPrice = scenicSpots.getScenicSpotWeekendRentPrice();// 周末起租价格
                    // 总金额
//                    totalAmount = (timeConversionMoney - Long.parseLong(scenicSpotRentTime))
//                            * Float.parseFloat(scenicSpotWeekendPrice) + Float.parseFloat(scenicSpotWeekendRentPrice);


                    //周末封顶价格结算
                    if (timeConversionMoney >= weekendCapOneTime) {//周末第一档封顶时间
                        if (timeConversionMoney >= weekendCapTwoTime) {//周末第二档封顶时间
                            //满足周末第二档封顶时间
                            Long beyond = timeConversionMoney - weekendCapTwoTime;//总时长 减去 第二档封顶时间 等于 超出封顶的第一档时间
                            float surplusTime = beyond * weekendCapTwoUnitPrice;//超出封顶时间的剩余时间进行单价计算费用
                            totalAmount = weekendCapTwoPrice + surplusTime;//第二档封顶价格+剩余超出封顶时间单价计算 等于 最终要支付的金额
                        } else {
                            //满足周末第一档封顶时间
                            Long beyond = timeConversionMoney - weekendCapOneTime;//总时长 减去 第一档封顶时间 等于 超出封顶的第一档时间
                            float surplusTime = beyond * weekendCapOneUnitPrice;//超出封顶时间的剩余时间进行单价计算费用
                            totalAmount = weekendCapOnePrice + surplusTime;//第一档封顶价格+剩余超出封顶时间单价计算 等于 最终要支付的金额
                        }
                        DecimalFormat df = new DecimalFormat("0.00");
                        String total = df.format(Double.parseDouble(String.valueOf(totalAmount)));
//                        int i = sysOrderService.updateOrderItemByOrderId(orderId, timeConversionMoney, total, dispatchingFee, coupon,"20",scenicSpots.getGiftTimeSetting());//修改订单状态和金额
                        int i = sysOrderService.updateOrderItemByOrderIdNew(orderId, timeConversionMoney, total, dispatchingFee, coupon, "20", scenicSpots.getGiftTimeSetting(), SysOrder.getParkingName(), Long.parseLong(SysOrder.getOrderParkingId()));// 更新订单状态以及产生的金额

                        if (i == 1) {
                            SysOrder = sysOrderService.getOrderState(orderId);// 根据订单编号查询订单
                            SysOrderOperationLog operationLog = new SysOrderOperationLog();
                            operationLog.setOperationId(IdUtils.getSeqId());
                            operationLog.setOperationName(SysUsers.getUserName());
                            operationLog.setOperationNumber(SysOrder.getOrderNumber());
                            operationLog.setOperationPhone(SysOrder.getCurrentUserPhone());
                            operationLog.setOperationScenicSpotName(SysOrder.getOrderScenicSpotName());
                            operationLog.setOperationRobotCode(SysOrder.getOrderRobotCode());
                            operationLog.setOperationTotalTime(String.valueOf(timeConversionMoney));
                            operationLog.setOperationFront("结算订单");
                            operationLog.setOperationAfter("结算订单");
                            operationLog.setCreateDate(DateUtil.currentDateTime());
                            sysOrderService.insertOrderOperationLog(operationLog);
                            //删除缓存中的订单数据
//                            redisUtil.remove(SysOrder.getOrderNumber());
                            // 推送的实体对象到机器人端
                            returnModel.setData("");
                            returnModel.setMsg("结束行程！");
                            returnModel.setState(Constant.STATE_SUCCESS);
                            returnModel.setType(Constant.END_THE_JOURNEY);
                            // 转JSON格式发送到个推
                            String robotUnlock = JsonUtils.toString(returnModel);// 转JSON
                            String encode = AES.encode(robotUnlock);// 加密推送
                            // 个推推送消息到APP端
                            // 判断pad是旧版本pad还是重构版pad
                            if ("0".equals(robot.getRobotAppType())) {
                                WeChatGtRobotAppPush.singlePush(robot.getRobotCodeCid(), encode, "成功!");
                                returnModel.setData("");
                                returnModel.setMsg("当前费用：" + total);
                                returnModel.setState(Constant.STATE_SUCCESS);
                                return returnModel;
                            } else {
                                WeChatGtRobotAppPush.singlePushNew(robot.getNewRobotCodeCid(), encode, "成功!");
                                returnModel.setData("");
                                returnModel.setMsg("当前费用：" + total);
                                returnModel.setState(Constant.STATE_SUCCESS);
                                return returnModel;
                            }
                        }
                    }

                    //唐山需求结算新增判断逻辑 0为通用结算 1为唐山结算
                    if (scenicSpots.getSettlementMethod().equals("1")) {
                        long runTime = timeConversionMoney - Long.parseLong(scenicSpotRentTime);
                        long weekendTime = Long.parseLong(scenicSpots.getScenicSpotWeekendTime());
                        long totalUserTimeType = (runTime % weekendTime == 0) ? runTime / weekendTime : (runTime / weekendTime + 1);//换算如果有小数位则进一位到整数位
                        String doubleValue = String.valueOf(totalUserTimeType);//转成字符串类型
                        totalAmount = Double.parseDouble(doubleValue) * Float.parseFloat(scenicSpotWeekendPrice) + Float.parseFloat(scenicSpotWeekendRentPrice);
                    } else {
                        double totalUserTimeType = (timeConversionMoney - Long.parseLong(scenicSpotRentTime)) / (double) Long.parseLong(scenicSpots.getScenicSpotWeekendTime());//换算用户使用的时常 （总时长 减 起租时间 除以 计费设置时间 等于 用户使用的时间）
                        DecimalFormat num = new DecimalFormat("0.00");//四舍五入保留两位小数
                        String doubleValue = num.format(totalUserTimeType);//转成字符串类型
                        totalAmount = Double.parseDouble(doubleValue) * Float.parseFloat(scenicSpotWeekendPrice) + Float.parseFloat(scenicSpotWeekendRentPrice);
                    }

                    // 是否存在优惠劵
                    if (StringUtils.isNotBlank(coupon)) {
                        totalAmount = totalAmount - Float.parseFloat(coupon);// 是否存在优惠券
                    }

                    //删除缓存中的订单数据
//                    redisUtil.remove(SysOrder.getOrderNumber());

                    DecimalFormat df1 = new DecimalFormat("0.00");// 四舍五入保留两位小数
                    String total = df1.format(totalAmount);// 转成字符串类型
//                    int i = sysOrderService.updateOrderItemByOrderId(orderId, timeConversionMoney, total, dispatchingFee, coupon,"20",scenicSpots.getGiftTimeSetting());//修改订单状态和金额
                    int i = sysOrderService.updateOrderItemByOrderIdNew(orderId, timeConversionMoney, total, dispatchingFee, coupon, "20", scenicSpots.getGiftTimeSetting(), SysOrder.getParkingName(), Long.parseLong(SysOrder.getOrderParkingId()));// 更新订单状态以及产生的金额

                    if (i == 1) {
                        SysOrder = sysOrderService.getOrderState(orderId);// 根据订单编号查询订单
                        SysOrderOperationLog operationLog = new SysOrderOperationLog();
                        operationLog.setOperationId(IdUtils.getSeqId());
                        operationLog.setOperationName(SysUsers.getUserName());
                        operationLog.setOperationNumber(SysOrder.getOrderNumber());
                        operationLog.setOperationPhone(SysOrder.getCurrentUserPhone());
                        operationLog.setOperationScenicSpotName(SysOrder.getOrderScenicSpotName());
                        operationLog.setOperationRobotCode(SysOrder.getOrderRobotCode());
                        operationLog.setOperationTotalTime(String.valueOf(timeConversionMoney));
                        operationLog.setOperationFront("结算订单");
                        operationLog.setOperationAfter("结算订单");
                        operationLog.setCreateDate(DateUtil.currentDateTime());
                        sysOrderService.insertOrderOperationLog(operationLog);
                        // 推送的实体对象到机器人端
                        returnModel.setData("");
                        returnModel.setMsg("结束行程！");
                        returnModel.setState(Constant.STATE_SUCCESS);
                        returnModel.setType(Constant.END_THE_JOURNEY);
                        // 转JSON格式发送到个推
                        String robotUnlock = JsonUtils.toString(returnModel);// 转JSON
                        String encode = AES.encode(robotUnlock);// 加密推送
                        // 个推推送消息到APP端
                        // 判断pad是旧版本pad还是重构版pad
                        if ("0".equals(robot.getRobotAppType())) {
                            WeChatGtRobotAppPush.singlePush(robot.getRobotCodeCid(), encode, "成功!");
                            returnModel.setData("");
                            returnModel.setMsg("当前费用：" + total);
                            returnModel.setState(Constant.STATE_SUCCESS);
                            return returnModel;
                        } else {
                            WeChatGtRobotAppPush.singlePushNew(robot.getNewRobotCodeCid(), encode, "成功!");
                            returnModel.setData("");
                            returnModel.setMsg("当前费用：" + total);
                            returnModel.setState(Constant.STATE_SUCCESS);
                            return returnModel;
                        }
                    }
                }
            } else if (DateUtil.getWeekend() == false) {//工作日
                if (timeConversionMoney <= Integer.valueOf(scenicSpotRentTime).intValue()) {// 当前时间小于或等于起租时间
                    scenicSpotNormalRentPrice = scenicSpots.getScenicSpotNormalRentPrice();// 工作日起租价格
                    // 是否存在优惠劵
                    if (StringUtils.isNotBlank(coupon)) {
                        scenicSpotNormalRentPrice = String.valueOf(Double.valueOf(scenicSpotNormalRentPrice) - Double.valueOf(coupon));// 是否存在优惠券
                    }

                    DecimalFormat df1 = new DecimalFormat("0.00");// 四舍五入保留两位小数
                    String total = df1.format(Double.parseDouble(scenicSpotNormalRentPrice));// 转成字符串类型
//                    int i = sysOrderService.updateOrderItemByOrderId(orderId, timeConversionMoney, total, dispatchingFee, coupon,"20",scenicSpots.getGiftTimeSetting());// 工作日更新订单状态以及产生的金额
                    int i = sysOrderService.updateOrderItemByOrderIdNew(orderId, timeConversionMoney, total, dispatchingFee, coupon, "20", scenicSpots.getGiftTimeSetting(), SysOrder.getParkingName(), Long.parseLong(SysOrder.getOrderParkingId()));// 更新订单状态以及产生的金额

                    if (i == 1) {
                        SysOrder = sysOrderService.getOrderState(orderId);// 根据订单编号查询订单
                        SysOrderOperationLog operationLog = new SysOrderOperationLog();
                        operationLog.setOperationId(IdUtils.getSeqId());
                        operationLog.setOperationName(SysUsers.getUserName());
                        operationLog.setOperationNumber(SysOrder.getOrderNumber());
                        operationLog.setOperationPhone(SysOrder.getCurrentUserPhone());
                        operationLog.setOperationScenicSpotName(SysOrder.getOrderScenicSpotName());
                        operationLog.setOperationRobotCode(SysOrder.getOrderRobotCode());
                        operationLog.setOperationTotalTime(String.valueOf(timeConversionMoney));
                        operationLog.setOperationFront("结算订单");
                        operationLog.setOperationAfter("结算订单");
                        operationLog.setCreateDate(DateUtil.currentDateTime());
                        sysOrderService.insertOrderOperationLog(operationLog);
                        //删除缓存中的订单数据
//                        redisUtil.remove(SysOrder.getOrderNumber());
                        // 更改机器人状态为临时占用状态=============================================================>此处安卓主动调用后台接口修改机器人状态
                        // 为了模拟暂时由后台来更改
                        // 推送的实体对象到机器人端
                        returnModel.setData("");
                        returnModel.setMsg("结束行程！");
                        returnModel.setState(Constant.STATE_SUCCESS);
                        returnModel.setType(Constant.END_THE_JOURNEY);
                        // 转JSON格式发送到个推
                        String robotUnlock = JsonUtils.toString(returnModel);// 转JSON
                        String encode = AES.encode(robotUnlock);// 加密推送
                        // 个推推送消息到APP端
                        // 判断pad是旧版本pad还是重构版pad
                        if ("0".equals(robot.getRobotAppType())) {
                            WeChatGtRobotAppPush.singlePush(robot.getRobotCodeCid(), encode, "成功!");
                            returnModel.setData("");
                            returnModel.setMsg("当前费用：" + total);
                            returnModel.setState(Constant.STATE_SUCCESS);
                            return returnModel;
                        } else {
                            WeChatGtRobotAppPush.singlePushNew(robot.getNewRobotCodeCid(), encode, "成功!");
                            returnModel.setData("");
                            returnModel.setMsg("当前费用：" + total);
                            returnModel.setState(Constant.STATE_SUCCESS);
                            return returnModel;
                        }
                    }
                } else {// 超出起租时间后每分钟计费
                    scenicSpotNormalRentPrice = scenicSpots.getScenicSpotNormalRentPrice();// 工作日起租价格
                    // 结算总金额
//                    totalAmount = (timeConversionMoney - Long.parseLong(scenicSpotRentTime))
//                            * Float.parseFloat(scenicSpotNormalPrice) + Float.parseFloat(scenicSpotNormalRentPrice);
//
                    //工作日封顶价格计算
                    if (timeConversionMoney >= normalCapOneTime) {//工作日第一档封顶时间
                        if (timeConversionMoney >= normalCapTwoTime) {//工作日第二档封顶时间
                            //满足工作日第二档封顶时间
                            Long beyond = timeConversionMoney - normalCapTwoTime;//总时长 减去 第二档封顶时间 等于 超出封顶的第一档时间
                            float surplusTime = beyond * normalCapTwoUnitPrice;//超出封顶时间的剩余时间进行单价计算费用
                            totalAmount = normalCapTwoPrice + surplusTime;//第二档封顶价格+剩余超出封顶时间单价计算 等于 最终要支付的金额
                        } else {
                            //满足工作日第一档封顶时间
                            Long beyond = timeConversionMoney - normalCapOneTime;//总时长 减去 第一档封顶时间 等于 超出封顶的第一档时间
                            float surplusTime = beyond * normalCapOneUnitPrice;//超出封顶时间的剩余时间进行单价计算费用
                            totalAmount = normalCapOnePrice + surplusTime;//第一档封顶价格+剩余超出封顶时间单价计算 等于 最终要支付的金额
                        }
                        DecimalFormat df = new DecimalFormat("0.00");
                        String total = df.format(Double.parseDouble(String.valueOf(totalAmount)));
//                        int i = sysOrderService.updateOrderItemByOrderId(orderId, timeConversionMoney, total, dispatchingFee, coupon,"20",scenicSpots.getGiftTimeSetting());
                        int i = sysOrderService.updateOrderItemByOrderIdNew(orderId, timeConversionMoney, total, dispatchingFee, coupon, "20", scenicSpots.getGiftTimeSetting(), SysOrder.getParkingName(), Long.parseLong(SysOrder.getOrderParkingId()));// 更新订单状态以及产生的金额

                        if (i == 1) {
                            SysOrder = sysOrderService.getOrderState(orderId);// 根据订单编号查询订单
                            SysOrderOperationLog operationLog = new SysOrderOperationLog();
                            operationLog.setOperationId(IdUtils.getSeqId());
                            operationLog.setOperationName(SysUsers.getUserName());
                            operationLog.setOperationNumber(SysOrder.getOrderNumber());
                            operationLog.setOperationPhone(SysOrder.getCurrentUserPhone());
                            operationLog.setOperationScenicSpotName(SysOrder.getOrderScenicSpotName());
                            operationLog.setOperationRobotCode(SysOrder.getOrderRobotCode());
                            operationLog.setOperationTotalTime(String.valueOf(timeConversionMoney));
                            operationLog.setOperationFront("结算订单");
                            operationLog.setOperationAfter("结算订单");
                            operationLog.setCreateDate(DateUtil.currentDateTime());
                            sysOrderService.insertOrderOperationLog(operationLog);

                            //删除缓存中的订单数据
//                            redisUtil.remove(SysOrder.getOrderNumber());
                            // 更改机器人状态为临时占用状态=============================================================>此处安卓主动调用后台接口修改机器人状态
                            // 为了模拟暂时由后台来更改
                            // 推送的实体对象到机器人端
                            returnModel.setData("");
                            returnModel.setMsg("结束行程！");
                            returnModel.setState(Constant.STATE_SUCCESS);
                            returnModel.setType(Constant.END_THE_JOURNEY);
                            // 转JSON格式发送到个推
                            String robotUnlock = JsonUtils.toString(returnModel);// 转JSON
                            String encode = AES.encode(robotUnlock);// 加密推送
                            // 个推推送消息到APP端
                            // 判断pad是旧版本pad还是重构版pad
                            if ("0".equals(robot.getRobotAppType())) {
                                WeChatGtRobotAppPush.singlePush(robot.getRobotCodeCid(), encode, "成功!");
                                returnModel.setData("");
                                returnModel.setMsg("当前费用：" + total);
                                returnModel.setState(Constant.STATE_SUCCESS);
                                return returnModel;
                            } else {
                                WeChatGtRobotAppPush.singlePushNew(robot.getNewRobotCodeCid(), encode, "成功!");
                                returnModel.setData("");
                                returnModel.setMsg("当前费用：" + total);
                                returnModel.setState(Constant.STATE_SUCCESS);
                                return returnModel;
                            }

                        }

                    }

                    // 唐山需求结算新增判断逻辑 0为通用结算 1为唐山结算
                    if (scenicSpots.getSettlementMethod().equals("1")) {
                        long runTime = timeConversionMoney - Long.parseLong(scenicSpotRentTime);
                        long weekendTime = Long.parseLong(scenicSpots.getScenicSpotNormalTime());
                        long totalUserTimeType = (runTime % weekendTime == 0) ? runTime / weekendTime : (runTime / weekendTime + 1);//换算如果有小数位则进一位到整数位
                        String doubleValue = String.valueOf(totalUserTimeType);//转成字符串类型

                        totalAmount = Double.parseDouble(doubleValue) * Float.parseFloat(scenicSpotNormalPrice) + Float.parseFloat(scenicSpotNormalRentPrice);
                    } else {
                        //总金额
                        //totalAmount = (timeConversionMoney - Long.parseLong(scenicSpotRentTime)) * Float.parseFloat(scenicSpotNormalPrice) + Float.parseFloat(scenicSpotNormalRentPrice);
                        double totalUserTimeType = (timeConversionMoney - Long.parseLong(scenicSpotRentTime)) / (double) Long.parseLong(scenicSpots.getScenicSpotNormalTime());//换算用户使用的时常 （总时长 减 起租时间 除以 计费设置时间 等于 用户使用的时间）
                        DecimalFormat num = new DecimalFormat("0.00");//四舍五入保留1位小数
                        String doubleValue = num.format(totalUserTimeType);//转成字符串类型
                        totalAmount = Double.parseDouble(doubleValue) * Float.parseFloat(scenicSpotNormalPrice) + Float.parseFloat(scenicSpotNormalRentPrice);
                    }


                    // 是否存在优惠劵
                    if (StringUtils.isNotBlank(coupon)) {
                        totalAmount = totalAmount - Float.parseFloat(coupon);// 是否存在优惠券
                    }

                    DecimalFormat df1 = new DecimalFormat("0.00");// 四舍五入保留两位小数
                    String total = df1.format(totalAmount);// 转成字符串类型
//                    int i = sysOrderService.updateOrderItemByOrderId(orderId, timeConversionMoney, total, dispatchingFee, coupon,"20",scenicSpots.getGiftTimeSetting());
                    int i = sysOrderService.updateOrderItemByOrderIdNew(orderId, timeConversionMoney, total, dispatchingFee, coupon, "20", scenicSpots.getGiftTimeSetting(), SysOrder.getParkingName(), Long.parseLong(SysOrder.getOrderParkingId()));// 更新订单状态以及产生的金额

                    if (i == 1) {
                        SysOrder = sysOrderService.getOrderState(orderId);// 根据订单编号查询订单
                        SysOrderOperationLog operationLog = new SysOrderOperationLog();
                        operationLog.setOperationId(IdUtils.getSeqId());
                        operationLog.setOperationName(SysUsers.getUserName());
                        operationLog.setOperationNumber(SysOrder.getOrderNumber());
                        operationLog.setOperationPhone(SysOrder.getCurrentUserPhone());
                        operationLog.setOperationScenicSpotName(SysOrder.getOrderScenicSpotName());
                        operationLog.setOperationRobotCode(SysOrder.getOrderRobotCode());
                        operationLog.setOperationTotalTime(String.valueOf(timeConversionMoney));
                        operationLog.setOperationFront("结算订单");
                        operationLog.setOperationAfter("结算订单");
                        operationLog.setCreateDate(DateUtil.currentDateTime());
                        sysOrderService.insertOrderOperationLog(operationLog);

                        //删除缓存中的订单数据
//                        redisUtil.remove(SysOrder.getOrderNumber());
                        // 更改机器人状态为临时占用状态=============================================================>此处安卓主动调用后台接口修改机器人状态
                        // 为了模拟暂时由后台来更改
                        // 推送的实体对象到机器人端
                        returnModel.setData("");
                        returnModel.setMsg("结束行程！");
                        returnModel.setState(Constant.STATE_SUCCESS);
                        returnModel.setType(Constant.END_THE_JOURNEY);
                        // 转JSON格式发送到个推
                        String robotUnlock = JsonUtils.toString(returnModel);// 转JSON
                        String encode = AES.encode(robotUnlock);// 加密推送
                        // 个推推送消息到APP端
                        // 判断pad是旧版本pad还是重构版pad
                        if ("0".equals(robot.getRobotAppType())) {
                            WeChatGtRobotAppPush.singlePush(robot.getRobotCodeCid(), encode, "成功!");
                            returnModel.setData("");
                            returnModel.setMsg("当前费用：" + total);
                            returnModel.setState(Constant.STATE_SUCCESS);
                            return returnModel;
                        } else {
                            WeChatGtRobotAppPush.singlePushNew(robot.getNewRobotCodeCid(), encode, "成功!");
                            returnModel.setData("");
                            returnModel.setMsg("当前费用：" + total);
                            returnModel.setState(Constant.STATE_SUCCESS);
                            return returnModel;
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.info("settlementOrder", e);
            returnModel.setData("");
            returnModel.setMsg("订单结算失败");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
        return returnModel;
    }

    /**
     * @return com.hna.hka.archive.management.system.util.ReturnModel
     * @Author 郭凯
     * @Description 订单退款
     * @Date 10:52 2020/8/19
     * @Param [request, sysOrder]
     **/
    @RequestMapping(value = "/doPayOrderBackDeposit", method = {RequestMethod.POST})
    @ResponseBody
    public ReturnModel doPayOrderBackDeposit(HttpServletRequest request, SysOrder sysOrder) throws IOException {
        ReturnModel weChatModel = new ReturnModel();
        SysUsers currentUser = this.getSysUser();
        String weChat_app_id = appid;// 获取微信小程序APPID
        String refund = wechatRefundRequest;// 获取微信支付链接
        String secret = apiSecretkey;// 获取微信小程序唯一密钥32位
        String mch_id = null;// 初始化
        String fileCert_Path = null;// 初始化
        SysOrder order = null;
        SortedMap<Object, Object> packageP = null;
        long jyh = 21809573909323L;
        HashMap<String, Object> modelMap = new HashMap<>();
        try {

            order = sysOrderService.getOrderByNumber(sysOrder.getOrderNumber());

            if (ToolUtil.isEmpty(order)) {
                weChatModel.setData("");
                weChatModel.setMsg("未查询此订单！");
                weChatModel.setState(Constant.STATE_FAILURE);
                return weChatModel;
            }
            //判断订单当前退款的状态
            String payMoney = request.getParameter("payMoney"); //获取用户可退金额
            SysCurrentUser currenuser = sysCurrentUserService.getCurrentUserById(order.getUserId());
            if (currenuser == null) {
                weChatModel.setData("");
                weChatModel.setMsg("未查询到此用户！");
                weChatModel.setState(Constant.STATE_FAILURE);
                return weChatModel;
            }
            String reason = request.getParameter("reason");
            String reasonsRefunds = request.getParameter("reasonsRefunds");
            if (ToolUtil.isEmpty(reason) && ToolUtil.isEmpty(reasonsRefunds)) {
                weChatModel.setData("");
                weChatModel.setMsg("请选择扣款原因！");
                weChatModel.setState(Constant.STATE_FAILURE);
                return weChatModel;
            }

            if ("1".equals(reason) || "3".equals(reason)) {
                if ("1".equals(order.getIsDispatchingFee())) {
                    weChatModel.setData("");
                    weChatModel.setMsg("该订单已退过调度费！");
                    weChatModel.setState(Constant.STATE_FAILURE);
                    return weChatModel;
                }
            }
            if ("1".equals(order.getRefundStatus())) {
                weChatModel.setData("");
                weChatModel.setMsg("该订单正在退款中！");
                weChatModel.setState(Constant.STATE_FAILURE);
                return weChatModel;
            }
//            else{
//                int i = sysOrderService.modifyRefundStatus(order.getOrderId(),"1");
//            }


            String reasonNew = "";
            if ("1".equals(reason)) {
                order.setIsDispatchingFee("1");
                reasonNew = "退调度费";
            } else if ("2".equals(reason)) {
                reasonNew = "内部测试";
            } else if ("3".equals(reason)) {
                order.setIsDispatchingFee("1");
                reasonNew = "退调度费及其他";
            } else if ("4".equals(reason)) {

            }

            if (ToolUtil.isNotEmpty(reasonsRefunds) && reason.equals("4")) {
                reasonNew = reasonNew + reasonsRefunds;
            }
            int fee = 0;// 初始化金额
            int refundFee = 0;// 退款金额
            // 转换金额
            fee = Integer.parseInt(new java.text.DecimalFormat("0").format(Double.parseDouble(order.getOrderAmount()) * 100));
            refundFee = Integer.parseInt(new java.text.DecimalFormat("0").format(Double.parseDouble(payMoney) * 100));
            // 获取用户openid
            String openid = currenuser.getCurrentOpenId();
            // 获取用户订单编号
            String outTradeNo = order.getOrderNumber();

            // 订单标题
            String body = "小程序退押金";
            // 时间戳
            final String nonce_str = System.currentTimeMillis() + "";
            // 获取商户ID
            mch_id = mchid;
            // 获取签名证书路径以及文件（此变量为默认变量）
            fileCert_Path = fileCertPath;
            WechatBusinessManagement Management = sysScenicSpotCertificateSpotService.getBusinessManagementByScenicSpotId(order.getOrderScenicSpotId());
            if (Management != null) {
                //覆盖默认商户号
                mch_id = Management.getMerchantNumber();
                //覆盖默认路径
                fileCert_Path = certPath + Management.getCertFileName();
                //覆盖微信小程序唯一密钥
                secret = Management.getMerchantSecret();
            }
            // 微信商户平台
            packageP = PayCommonUtil.sendWechatPayBackOrder(weChat_app_id, mch_id, fileCert_Path, secret, nonce_str, body, outTradeNo, fee, refundFee, openid, request.getRemoteAddr(), refund);
            BigDecimal bignum1 = new BigDecimal(order.getOrderRefundAmount());
            BigDecimal bignum2 = new BigDecimal(payMoney);
            if (order.getOrderRefundAmount() != null && order.getOrderRefundAmount().length() != 0 && payMoney != null && payMoney.length() != 0) {
                order.setOrderRefundAmount(bignum1.add(bignum2).toString());
            } else if (order.getOrderRefundAmount() == null && order.getOrderRefundAmount().length() == 0 && payMoney != null && payMoney.length() != 0) {
                order.setOrderRefundAmount(payMoney);
            } else if (order.getOrderRefundAmount() != null && order.getOrderRefundAmount().length() != 0 && payMoney == null && payMoney.length() == 0) {
                order.setOrderRefundAmount(order.getOrderRefundAmount());
            }
            //解析微信返回结果
            if (packageP != null && packageP.get("return_code").toString().equals("SUCCESS") && packageP.get("result_code").toString().equals("SUCCESS")) {
//                order.setFaultId(Long.parseLong(reason));
                //计算全额退款
                if (order.getOrderRefundAmount() != null && order.getOrderAmount() != null) {
                    Double orderAmount = Double.valueOf(order.getOrderRefundAmount());
                    Double orderRefundAmount = Double.valueOf(order.getOrderAmount());
                    if (String.format("%.2f", orderRefundAmount).equals(String.format("%.2f", orderAmount))) {
                        order.setOrderStatus("60");
                    }
                }
//                boolean ddf1 = reason.contains("调度费");
//                if (ddf1){
//                    order.setIsDispatchingFee("1");
//                }

                order.setReasonsRefunds(reasonNew);
                order.setRefundStatus("0");
                sysOrderService.updateOrder(order);
                //添加退款日志
                SysOrderLog orderLog = new SysOrderLog();
                orderLog.setOrderLogId(IdUtils.getSeqId());
                orderLog.setOrderLogLoginname(currentUser.getLoginName());
                orderLog.setOrderLogUsername(currentUser.getUserName());
                orderLog.setOrderLogPhone(order.getCurrentUserPhone());
                orderLog.setOrderLogNumber(order.getOrderNumber());
                orderLog.setDepositMoney("退款金额：" + payMoney);
                orderLog.setScenicSpotName(order.getOrderScenicSpotName());
                orderLog.setOrderLogReason("退款成功");
                orderLog.setReturnResultCode(packageP.get("result_code").toString());
                orderLog.setCreateDate(DateUtil.currentDateTime());
                orderLog.setUpdateDate(DateUtil.currentDateTime());
                sysOrderLogService.insertOrderLog(orderLog);

                if (jyh == order.getOrderScenicSpotId()) {
                    //订单信息
                    PayOrder payorder = new PayOrder();
                    payorder.setOrderId(order.getOrderId().toString());
                    payorder.setOrderNo(order.getOrderNumber());
                    payorder.setOpenTime(order.getOrderStartTime());
                    payorder.setCloseTime(order.getOrderEndTime());
                    payorder.setDeductTime(order.getUpdateDate());
                    payorder.setOrderStatus("1");
                    payorder.setAmountUse(order.getActualAmount());
                    payorder.setAmountPay(order.getOrderAmount());
                    payorder.setUserId(currenuser.getCurrentUserId().toString());
                    payorder.setDeviceId(order.getOrderRobotCode());
                    payorder.setPayStatus("1");
                    payorder.setPayType("WECHAT");
                    payorder.setOpenSiteName("");
                    payorder.setRevertSiteName("");
                    payorder.setCouponAmount(order.getCoupon());
                    modelMap.put("payOrder", payorder);

                    //退款记录
                    List<RefundOrderList> refundOrder = new ArrayList<RefundOrderList>();
                    refundOrder = sysOrderLogService.getOrderNumberLogList(order.getOrderNumber());
                    modelMap.put("refundOrderList", refundOrder);
                    //计时信息
                    RentRecord rentRecord = new RentRecord();
                    rentRecord.setOrderId(order.getOrderId().toString());
                    rentRecord.setUseMinute(order.getTotalTime());
                    rentRecord.setOpenTime(order.getOrderStartTime());
                    rentRecord.setCloseTime(order.getOrderEndTime());
                    rentRecord.setUserId(currenuser.getCurrentUserId().toString());
                    List<RentRecord> asList = Arrays.asList(rentRecord);
                    modelMap.put("rentRecord", asList);
                    //用户信息
                    User user = new User();
                    user.setUserId(currenuser.getCurrentUserId().toString());
                    user.setOpenId(currenuser.getCurrentOpenId());
                    user.setPhone(currenuser.getCurrentUserPhone());
                    user.setNickname("");
                    modelMap.put("user", user);
                    String httpURL = HttpsUtil.HttpURL(modelMap);
                    System.out.println("推送返回状态：" + httpURL);
                }
                weChatModel.setData("");
                weChatModel.setMsg("订单退款成功！");
                weChatModel.setState(Constant.STATE_SUCCESS);
                return weChatModel;
            } else {
                //添加退款日志信息
                SysOrderLog orderLog = new SysOrderLog();
                orderLog.setOrderLogId(IdUtils.getSeqId());
                orderLog.setOrderLogLoginname(currentUser.getLoginName());
                orderLog.setOrderLogUsername(currentUser.getUserName());
                orderLog.setOrderLogPhone(order.getCurrentUserPhone());
                orderLog.setOrderLogNumber(order.getOrderNumber());
                orderLog.setDepositMoney("退款金额：" + payMoney);
                orderLog.setScenicSpotName(order.getOrderScenicSpotName());
                orderLog.setOrderLogReason(packageP.get("err_code_des").toString());
                orderLog.setReturnResultCode(packageP.get("result_code").toString());
                orderLog.setCreateDate(DateUtil.currentDateTime());
                orderLog.setUpdateDate(DateUtil.currentDateTime());
                sysOrderLogService.insertOrderLog(orderLog);
                sysOrderService.modifyRefundStatus(order.getOrderId(), "0");
                weChatModel.setData("");
                weChatModel.setMsg("订单退款失败！");
                weChatModel.setState(Constant.STATE_FAILURE);
                return weChatModel;
            }
        } catch (Exception e) {
            weChatModel.setData("");
            weChatModel.setMsg("doPayBackDeposit退押金异常异常！");
            weChatModel.setState(Constant.STATE_FAILURE);
            logger.error("doPayOrderBackDeposit", e);
            return weChatModel;
        }
    }

    /**
     * @return com.hna.hka.archive.management.system.util.ReturnModel
     * @Author 郭凯
     * @Description 查询用户押金金额
     * @Date 17:24 2020/8/19
     * @Param [userId]
     **/
    @RequestMapping("/getDepositPayAmountByUserId")
    @ResponseBody
    public ReturnModel doPayOrderBackDeposit(Long orderId) {
        ReturnModel weChatModel = new ReturnModel();
        try {
            if (ToolUtil.isEmpty(orderId)) {
                weChatModel.setData("");
                weChatModel.setMsg("订单ID获取失败！");
                weChatModel.setState(Constant.STATE_FAILURE);
                return weChatModel;
            }
            SysOrder sysOrder = sysOrderService.getOrderById(orderId);
            if (ToolUtil.isNotEmpty(sysOrder)) {
                weChatModel.setData(sysOrder);
                weChatModel.setMsg("查询成功！");
                weChatModel.setState(Constant.STATE_SUCCESS);
                return weChatModel;
            } else {
                weChatModel.setData("");
                weChatModel.setMsg("查询失败！");
                weChatModel.setState(Constant.STATE_FAILURE);
                return weChatModel;
            }
        } catch (Exception e) {
            weChatModel.setData("");
            weChatModel.setMsg("查询失败！");
            weChatModel.setState(Constant.STATE_FAILURE);
            return weChatModel;
        }
    }

    /**
     * @return com.hna.hka.archive.management.system.util.ReturnModel
     * @Author 郭凯
     * @Description 押金扣款
     * @Date 10:40 2020/8/20
     * @Param [sysOrder]
     **/
    @RequestMapping("/updateEditDepositPay")
    @ResponseBody
    public ReturnModel updateEditDepositPay(SysOrder sysOrder) {
        ReturnModel weChatModel = new ReturnModel();
        SysUsers user = this.getSysUser();
        SysDepositRefundLog sysDepositRefundLog = new SysDepositRefundLog();
        try {
            SysOrder order = sysOrderService.getOrderState(sysOrder.getOrderId());
            if (ToolUtil.isEmpty(order)) {
                weChatModel.setData("");
                weChatModel.setMsg("订单信息查询失败！");
                weChatModel.setState(Constant.STATE_FAILURE);
                return weChatModel;
            }
            String reason = request.getParameter("reason");
            String reasonsRefunds = request.getParameter("reasonsRefunds");
            if (ToolUtil.isEmpty(reason) && ToolUtil.isEmpty(reasonsRefunds)) {
                weChatModel.setData("");
                weChatModel.setMsg("请选择扣款原因！");
                weChatModel.setState(Constant.STATE_FAILURE);
                return weChatModel;
            }
            if (ToolUtil.isNotEmpty(reasonsRefunds)) {
                reason = reason + reasonsRefunds;
            }
            if ("1".equals(order.getSubStatus())) {
                weChatModel.setData("");
                weChatModel.setMsg("此用户未提交订单，请提交后操作");
                weChatModel.setState(Constant.STATE_FAILURE);
                return weChatModel;
            }
            if (!"20".equals(order.getOrderStatus())) {
                weChatModel.setData("");
                weChatModel.setMsg("此订单已经操作！请刷新");
                weChatModel.setState(Constant.STATE_FAILURE);
                return weChatModel;
            }
            float parseInt = Float.parseFloat(sysOrder.getOrderAmount());
            float depositPayAmount = Float.parseFloat(sysOrder.getDepositPayAmount());
            if (parseInt > depositPayAmount) {
                weChatModel.setData("");
                weChatModel.setMsg("当前订单大于押金，请修改订单金额！");
                weChatModel.setState(Constant.STATE_FAILURE);
                return weChatModel;
            }
            SysCurrentUser currenuser = sysCurrentUserService.getCurrentUserById(order.getUserId());
            if (ToolUtil.isEmpty(currenuser)) {
                weChatModel.setData("");
                weChatModel.setMsg("用户信息获取失败");
                weChatModel.setState(Constant.STATE_FAILURE);
                return weChatModel;
            }
            //查询押金订单
            WechatDeposit selectSysDepositByUSER_ID = sysOrderService.getSysDepositByUserId(currenuser.getCurrentUserId(), "30");
            if (ToolUtil.isEmpty(selectSysDepositByUSER_ID)) {
                weChatModel.setData("");
                weChatModel.setMsg("押金订单查询失败");
                weChatModel.setState(Constant.STATE_FAILURE);
                return weChatModel;
            }
            BigDecimal bigDecimal = new BigDecimal(sysOrder.getDepositPayAmount());
            String orderAmount = sysOrder.getOrderAmount();
            BigDecimal bigDecimals = new BigDecimal(orderAmount);
            float amount = bigDecimal.subtract(bigDecimals).floatValue();
            String depositPay = null;//获取退款押金金额
            //扣款日志添加
            sysDepositRefundLog.setId(IdUtils.getSeqId());
            sysDepositRefundLog.setLoginName(user.getLoginName());
            sysDepositRefundLog.setOrderNumber(selectSysDepositByUSER_ID.getOutTradeNo());
            sysDepositRefundLog.setUserPhone(currenuser.getCurrentUserPhone());
            sysDepositRefundLog.setOrderMoney(currenuser.getDepositPayAmount());
            sysDepositRefundLog.setDepositMoney(bigDecimals.toString());
            sysDepositRefundLog.setBlackListType("");
            sysDepositRefundLog.setRobotCode(order.getOrderRobotCode());
            sysDepositRefundLog.setOrderStartTime(order.getOrderStartTime());
            sysDepositRefundLog.setOrderEndTime(order.getOrderEndTime());
            sysDepositRefundLog.setCreateDate(DateUtil.currentDateTime());
            if (bigDecimal.compareTo(bigDecimals) == 0) {
                //如果用户押金等于订单金额，则直接修改状态
                sysOrderService.saveSysDepositRefundLog(sysDepositRefundLog);
                sysOrderService.updateDepositStatus(selectSysDepositByUSER_ID, "60");//更新押金订单列表 60为已退款状态
                // 押金缴纳状态:  10 已缴纳 20 未缴纳
                currenuser.setDepositPayState("20");
                currenuser.setCreditArrearsState("10");
                currenuser.setReturnDepositPayTime(DateUtil.currentDateTime());
                currenuser.setScenicSpotId("");
                sysCurrentUserService.updateCurrenUser(currenuser);
                SysOrder orders = new SysOrder();
                orders.setOrderId(sysOrder.getOrderId());
                orders.setOrderStatus("30");
                orders.setPaymentMethod("5");
                orders.setReasonsRefunds(reason);
                orders.setOutTradeNo(selectSysDepositByUSER_ID.getOutTradeNo());
                orders.setUpdateDate(DateUtil.currentDateTime());
                sysOrderService.updateOrder(orders);
                weChatModel.setData("");
                weChatModel.setMsg("押金退款成功");
                weChatModel.setState(Constant.STATE_SUCCESS);
                return weChatModel;
            } else {
                depositPay = String.valueOf(amount);
            }
            int total_fee = 0;//初始化总金额
            int payback_fee = 0;//初始化退款金额
            //转换金额
            total_fee = Integer.parseInt(new java.text.DecimalFormat("0").format(Double.parseDouble(sysOrder.getDepositPayAmount()) * 100));
            payback_fee = Integer.parseInt(new java.text.DecimalFormat("0").format(Double.parseDouble(depositPay) * 100));
            // 获取用户openId
            String openid = currenuser.getCurrentOpenId();
            // 获取用户订单编号
            String outTradeNo = selectSysDepositByUSER_ID.getOutTradeNo();
            // 微信商户平台
            SortedMap<Object, Object> packageP = PayCommonUtil.weChatRefund(weChatInfo, openid, outTradeNo, total_fee, payback_fee);
            if (packageP != null && packageP.get("return_code").toString().equals("SUCCESS") && packageP.get("result_code").toString().equals("SUCCESS")) {
                sysOrderService.saveSysDepositRefundLog(sysDepositRefundLog);
                sysOrderService.updateDepositStatus(selectSysDepositByUSER_ID, "60");//更新押金订单列表 60为已退款状态 
                // 押金缴纳状态:  10 已缴纳 20 未缴纳
                currenuser.setDepositPayState("20");
                currenuser.setCreditArrearsState("10");
                currenuser.setReturnDepositPayTime(DateUtil.currentDateTime());
                currenuser.setScenicSpotId("");
                sysCurrentUserService.updateCurrenUser(currenuser);
                SysOrder orderss = new SysOrder();
                orderss.setOrderId(Long.parseLong(request.getParameter("orderId")));
                orderss.setOrderStatus("30");
                orderss.setPaymentMethod("5");
                orderss.setReasonsRefunds(reason);
                orderss.setOutTradeNo(selectSysDepositByUSER_ID.getOutTradeNo());
                orderss.setUpdateDate(DateUtil.currentDateTime());
                sysOrderService.updateOrder(orderss);
                weChatModel.setData("");
                weChatModel.setMsg("押金扣款成功！");
                weChatModel.setState(Constant.STATE_SUCCESS);
                return weChatModel;
            } else {
                weChatModel.setData("");
                weChatModel.setMsg("押金扣款失败------>" + packageP.get("err_code_des"));
                weChatModel.setState(Constant.STATE_FAILURE);
                return weChatModel;
            }
        } catch (Exception e) {
            logger.info("updateEditDepositPay", e);
            weChatModel.setData("");
            weChatModel.setMsg("押金扣款失败,（请联系管理员）");
            weChatModel.setState(Constant.STATE_FAILURE);
            return weChatModel;
        }
    }

    /**
     * @return com.hna.hka.archive.management.system.util.ReturnModel
     * @Author 郭凯
     * @Description 储值订单退款
     * @Date 10:08 2020/8/24
     * @Param [sysOrder]
     **/
    @RequestMapping("/doPayStoredValueOrderBackDeposit")
    @ResponseBody
    public ReturnModel doPayStoredValueOrderBackDeposit(SysOrder sysOrder) throws IOException, JDOMException {
        ReturnModel weChatModel = new ReturnModel();
        SysUsers currentUser = this.getSysUser();
        try {
            String payMoney = request.getParameter("payMoney"); //获取用户可退金额
            String reason = request.getParameter("reason");
            String reasonsRefunds = request.getParameter("reasonsRefunds");

            if (ToolUtil.isEmpty(reason) && ToolUtil.isEmpty(reasonsRefunds)) {
                weChatModel.setData("");
                weChatModel.setMsg("请选择扣款原因！");
                weChatModel.setState(Constant.STATE_FAILURE);
                return weChatModel;
            }

            SysOrder order = sysOrderService.getOrderByNumber(sysOrder.getOrderNumber());
            if (ToolUtil.isEmpty(order)) {
                weChatModel.setData("");
                weChatModel.setMsg("订单查询失败！");
                weChatModel.setState(Constant.STATE_FAILURE);
                return weChatModel;
            }

            SysCurrentUser sysCurrentUser = sysCurrentUserService.getCurrentUserById(order.getUserId());
            if (ToolUtil.isEmpty(sysCurrentUser)) {
                weChatModel.setData("");
                weChatModel.setMsg("用户信息查询失败！");
                weChatModel.setState(Constant.STATE_FAILURE);
                return weChatModel;
            }
            if ("1".equals(reason) || "3".equals(reason)) {
                if ("1".equals(order.getIsDispatchingFee())) {
                    weChatModel.setData("");
                    weChatModel.setMsg("该订单已退过调度费！");
                    weChatModel.setState(Constant.STATE_FAILURE);
                    return weChatModel;
                }
            }
            if ("1".equals(order.getRefundStatus())) {
                weChatModel.setData("");
                weChatModel.setMsg("该订单正在退款中！");
                weChatModel.setState(Constant.STATE_FAILURE);
                return weChatModel;
            } else {
                int i = sysOrderService.modifyRefundStatus(order.getOrderId(), "1");
            }


            String reasonNew = "";
            if ("1".equals(reason)) {
                order.setIsDispatchingFee("1");
                reasonNew = "退调度费";
            } else if ("2".equals(reason)) {
                reasonNew = "内部测试";
            } else if ("3".equals(reason)) {
                order.setIsDispatchingFee("1");
                reasonNew = "退调度费及其他";
            } else if ("4".equals(reason)) {

            }

            if (ToolUtil.isNotEmpty(reasonsRefunds)) {
                reasonNew = reasonNew + reasonsRefunds;
            }


            //判断是否全额退款
            BigDecimal bignum1 = new BigDecimal(order.getOrderRefundAmount());
            BigDecimal bignum2 = new BigDecimal(payMoney);
            if (order.getOrderRefundAmount() != null && order.getOrderRefundAmount().length() != 0 && payMoney != null && payMoney.length() != 0) {
                order.setOrderRefundAmount(bignum1.add(bignum2).toString());
            } else if (order.getOrderRefundAmount() == null && order.getOrderRefundAmount().length() == 0 && payMoney != null && payMoney.length() != 0) {
                order.setOrderRefundAmount(payMoney);
            } else if (order.getOrderRefundAmount() != null && order.getOrderRefundAmount().length() != 0 && payMoney == null && payMoney.length() == 0) {
                order.setOrderRefundAmount(order.getOrderRefundAmount());
            }
            //计算全额退款
            if (order.getOrderRefundAmount() != null && order.getOrderAmount() != null) {
                Double orderAmount = Double.valueOf(order.getOrderRefundAmount());
                Double orderRefundAmount = Double.valueOf(order.getOrderAmount());
                if (String.format("%.2f", orderRefundAmount).equals(String.format("%.2f", orderAmount))) {
                    order.setOrderStatus("60");
                }
            }
            order.setReasonsRefunds(reasonNew);
            int i = sysOrderService.updateOrder(order);
            if (i == 1) {
                SysCurrentUserAccount account = sysCurrentUserAccountService.selectAccountByUserId(sysCurrentUser.getCurrentUserId());
                if (ToolUtil.isEmpty(account)) {
                    weChatModel.setData("");
                    weChatModel.setMsg("未查询到此用户账户！");
                    weChatModel.setState(Constant.STATE_FAILURE);
                    return weChatModel;
                }
                SysCurrentUserAccount currentUserAccount = new SysCurrentUserAccount();

                currentUserAccount.setAccountId(account.getAccountId());
                Double orderAmount = Double.valueOf(account.getAccountAmount());
                Double orderRefundAmount = Double.valueOf(payMoney);
                BigDecimal bd1 = new BigDecimal(Double.toString(orderAmount));
                BigDecimal bd2 = new BigDecimal(Double.toString(orderRefundAmount));
                double doubleValue = bd1.add(bd2).doubleValue();
                currentUserAccount.setAccountAmount(String.format("%.2f", doubleValue));
                currentUserAccount.setUpdateDate(DateUtil.currentDateTime());
                int j = sysCurrentUserAccountService.editCurrentUserAccount(currentUserAccount);


                if (j == 1) {
                    SysOrderLog orderLog = new SysOrderLog();
                    orderLog.setOrderLogId(IdUtils.getSeqId());
                    orderLog.setOrderLogLoginname(currentUser.getLoginName());
                    orderLog.setOrderLogUsername(currentUser.getUserName());
                    orderLog.setOrderLogPhone(order.getCurrentUserPhone());
                    orderLog.setOrderLogNumber(order.getOrderNumber());
                    orderLog.setDepositMoney("退款金额：" + payMoney);
                    orderLog.setScenicSpotName(order.getOrderScenicSpotName());
                    orderLog.setOrderLogReason("退款成功");
                    orderLog.setReturnResultCode("");
                    orderLog.setCreateDate(DateUtil.currentDateTime());
                    orderLog.setUpdateDate(DateUtil.currentDateTime());
                    int i1 = sysOrderLogStoredService.insertOrderLog(orderLog);
//                    sysOrderLogService.insertOrderLog(orderLog);


                    //储值账户变动记录
                    SysCurrentUserAccountDeduction sysCurrentUserAccountDeduction = new SysCurrentUserAccountDeduction();
                    sysCurrentUserAccountDeduction.setDeductionId(IdUtils.getSeqId());
                    sysCurrentUserAccountDeduction.setScenicSpotId(order.getOrderScenicSpotId());
                    sysCurrentUserAccountDeduction.setScenicSpotName(order.getOrderScenicSpotName());
                    sysCurrentUserAccountDeduction.setOrderId(order.getOrderId());
                    sysCurrentUserAccountDeduction.setOrderNumber(order.getOrderNumber());
                    sysCurrentUserAccountDeduction.setUserPhone(sysCurrentUser.getCurrentUserPhone());
                    sysCurrentUserAccountDeduction.setUserId(sysCurrentUser.getCurrentUserId());
                    sysCurrentUserAccountDeduction.setAccountAmountFront(bd1.toString());
                    sysCurrentUserAccountDeduction.setDeductionAmount(bd2.toString());
                    sysCurrentUserAccountDeduction.setAccountAmountRear(String.format("%.2f", doubleValue));
                    sysCurrentUserAccountDeduction.setAccountType("2");
                    sysCurrentUserAccountDeduction.setCreateTime(DateUtil.currentDateTime());
                    sysCurrentUserAccountDeduction.setUpdateTime(DateUtil.currentDateTime());
                    int t = sysCurrentUserAccountDeductionService.add(sysCurrentUserAccountDeduction);

                    //用户储值金额日志
//                    SysCurrentUserAccountRefundLog sysCurrentUserAccountRefundLog = new SysCurrentUserAccountRefundLog();
//                    sysCurrentUserAccountRefundLog.setAccountLogId(IdUtils.getSeqId());
//                    sysCurrentUserAccountRefundLog.setAccountLogLoginName(currentUser.getLoginName());
//                    sysCurrentUserAccountRefundLog.setAccountLogUserPhone(order.getCurrentUserPhone());
//                    sysCurrentUserAccountRefundLog.setAccountLogBefore(orderAmount.toString());
//                    sysCurrentUserAccountRefundLog.setAccountLogAfter(String.valueOf(doubleValue));
//                    sysCurrentUserAccountRefundLog.setAccountLogPrice(orderRefundAmount.toString());
//                    sysCurrentUserAccountRefundLog.setCreateDate(DateUtil.currentDateTime());
//                    sysCurrentUserAccountRefundLog.setUpdateDate(DateUtil.currentDateTime());
//                    int i2 =  sysCurrentUserAccountRefundLogService.insertAccountRefundLog(sysCurrentUserAccountRefundLog);

                }
                weChatModel.setData(true);
                weChatModel.setMsg("订单退款成功！");
                weChatModel.setState(Constant.STATE_SUCCESS);
                return weChatModel;
            } else {
                SysOrderLog orderLog = new SysOrderLog();
                orderLog.setOrderLogId(IdUtils.getSeqId());
                orderLog.setOrderLogLoginname(currentUser.getLoginName());
                orderLog.setOrderLogUsername(currentUser.getUserName());
                orderLog.setOrderLogPhone(order.getCurrentUserPhone());
                orderLog.setOrderLogNumber(order.getOrderNumber());
                orderLog.setDepositMoney("退款金额：" + payMoney);
                orderLog.setScenicSpotName(order.getOrderScenicSpotName());
                orderLog.setOrderLogReason("退款失败");
                orderLog.setReturnResultCode("");
                orderLog.setCreateDate(DateUtil.currentDateTime());
                orderLog.setUpdateDate(DateUtil.currentDateTime());
                int i1 = sysOrderLogStoredService.insertOrderLog(orderLog);
//                sysOrderLogService.insertOrderLog(orderLog);


                weChatModel.setData("");
                weChatModel.setMsg("订单退款退款失败！");
                weChatModel.setState(Constant.STATE_FAILURE);
                return weChatModel;
            }
        } catch (Exception e) {
            weChatModel.setData("");
            weChatModel.setMsg("doPayBackDeposit退订单异常异常！");
            weChatModel.setState(Constant.STATE_FAILURE);
            logger.error("doPayOrderBackDeposit", e);
            return weChatModel;
        }
    }

    /**
     * @return com.hna.hka.archive.management.system.util.ReturnModel
     * @Author 郭凯
     * @Description 修改订单金额
     * @Date 14:50 2020/8/27
     * @Param [sysOrder]
     **/
    @RequestMapping("/editOrderAmount")
    @ResponseBody
    public ReturnModel editOrderAmount(SysOrder sysOrder) throws IOException, JDOMException {
        ReturnModel weChatModel = new ReturnModel();
        SysUsers currentUser = this.getSysUser();
        try {
            SysCurrentUser sysCurrentUser = sysCurrentUserService.getCurrentUserById(sysOrder.getUserId());
            if (ToolUtil.isNotEmpty(sysCurrentUser)) {
                String depositPayState = sysCurrentUser.getDepositPayState();
                if (ToolUtil.isNotEmpty(depositPayState)) {
                    SysOrder order = sysOrderService.getOrderByNumber(sysOrder.getOrderNumber());
                    if (ToolUtil.isEmpty(order)) {
                        weChatModel.setData("");
                        weChatModel.setMsg("订单查询失败！");
                        weChatModel.setState(Constant.STATE_FAILURE);
                        return weChatModel;
                    }
                    if ("1".equals(order.getSubStatus())) {
                        weChatModel.setData("");
                        weChatModel.setMsg("此订单用户未提交，用户提交后可修改！");
                        weChatModel.setState(Constant.STATE_FAILURE);
                        return weChatModel;
                    }
                    if (!"20".equals(order.getOrderStatus())) {
                        weChatModel.setData("");
                        weChatModel.setMsg("当前订不是未付款状态,不能修改金额！");
                        weChatModel.setState(Constant.STATE_FAILURE);
                        return weChatModel;
                    }

                    if ("0".equals(order.getIsPaying())) {
                        weChatModel.setData("");
                        weChatModel.setMsg("当前订单正在支付中,不能修改金额！");
                        weChatModel.setState(Constant.STATE_FAILURE);
                        return weChatModel;
                    }
                    //添加修改订单金额日志
                    SysOrderOperationLog operationLog = new SysOrderOperationLog();
                    operationLog.setOperationId(IdUtils.getSeqId());
                    operationLog.setOperationName(currentUser.getUserName());
                    operationLog.setOperationNumber(order.getOrderNumber());
                    operationLog.setOperationPhone(order.getCurrentUserPhone());
                    operationLog.setOperationScenicSpotName(order.getOrderScenicSpotName());
                    operationLog.setOperationRobotCode(order.getOrderRobotCode());
                    operationLog.setOperationTotalTime(order.getTotalTime());
                    operationLog.setOperationFront(sysOrder.getOrderAmount());
                    operationLog.setOperationAfter(sysOrder.getOrderAndDeductible());
                    operationLog.setCreateDate(DateUtil.currentDateTime());
                    sysOrderService.insertOrderOperationLog(operationLog);
                    SysOrder orders = new SysOrder();
                    orders.setOrderId(order.getOrderId());
                    orders.setOrderAmount(sysOrder.getOrderAndDeductible());
                    orders.setUpdateDate(DateUtil.currentDateTime());
                    int orderAmount = sysOrderService.updateOrder(orders);
                    if (orderAmount == 1) {
                        weChatModel.setData("");
                        weChatModel.setMsg("订单金额修改成功！");
                        weChatModel.setState(Constant.STATE_SUCCESS);
                        return weChatModel;
                    } else {
                        weChatModel.setData("");
                        weChatModel.setMsg("订单金额修改失败！");
                        weChatModel.setState(Constant.STATE_SUCCESS);
                        return weChatModel;
                    }
                }
            }
        } catch (Exception e) {
            weChatModel.setData("");
            weChatModel.setMsg("订单金额修改失败(请联系管理员)！");
            weChatModel.setState(Constant.STATE_FAILURE);
            logger.error("editOrderAmount", e);
            return weChatModel;
        }
        return weChatModel;
    }

    /**
     * @return com.hna.hka.archive.management.system.util.ReturnModel
     * @Author 郭凯
     * @Description 提交状态修改
     * @Date 15:38 2020/8/28
     * @Param [sysOrder]
     **/
    @RequestMapping("/updateSubMethod")
    @ResponseBody
    public ReturnModel updateSubMethod(SysOrder sysOrder) throws IOException, JDOMException {
        ReturnModel dataModel = new ReturnModel();
        SysUsers currentUser = this.getSysUser();
        try {
            SysOrder order = sysOrderService.getOrderState(sysOrder.getOrderId());
            SysCurrentUserAccount currentUserAccount = sysCurrentUserAccountService.selectAccountByUserId(order.getUserId());
            SysCurrentUser currenuser = sysCurrentUserService.getCurrentUserById(order.getUserId());
            if ("2".equals(order.getSubStatus())) {
                dataModel.setData("");
                dataModel.setMsg("此订单已提交！（请勿重复提交）");
                dataModel.setState(Constant.STATE_FAILURE);
                return dataModel;
            }
            SysOrder sysOrders = new SysOrder();
            sysOrders.setOrderId(order.getOrderId());
            sysOrders.setSubMethod(sysOrder.getSubMethod());
            sysOrders.setActualAmount(sysOrder.getOrderAmount());
            sysOrders.setOrderAmount(request.getParameter("orderAmounts"));
            sysOrders.setSubStatus("2");
            sysOrders.setUpdateDate(DateUtil.currentDateTime());
            //如果折扣不为空
            if (ToolUtil.isNotEmpty(sysOrder.getOrderDiscount())) {
                sysOrders.setOrderDiscount(sysOrder.getOrderDiscount());
            }
//            //记录用户储值金额日志用
//            String accountAmountString ="";
//            String orderAmountString ="";
//            String subtractString ="";
            //储值抵扣
            if ("2".equals(sysOrder.getSubMethod())) {
                sysOrders.setDeductibleAmount(sysOrder.getDeductibleAmount());
                currentUserAccount.setAccountAmount("0");
                currentUserAccount.setUpdateDate(DateUtil.currentDateTime());
                sysCurrentUserAccountService.editCurrentUserAccount(currentUserAccount);
                //记录用户储值金额变化日志会使用
//                accountAmountString = sysOrders.getActualAmount();
//                orderAmountString = sysOrders.getActualAmount();
//                subtractString = "0";
            }

            if ("1".equals(sysOrder.getSubMethod())) {
                sysOrders.setPaymentMethod("3");
                BigDecimal accountAmount = new BigDecimal(currentUserAccount.getAccountAmount());
                BigDecimal orderAmount = new BigDecimal(sysOrders.getOrderAmount());
                BigDecimal subtract = accountAmount.subtract(orderAmount);
                String orderAmounts = subtract.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
                currentUserAccount.setAccountAmount(orderAmounts);
                currentUserAccount.setUpdateDate(DateUtil.currentDateTime());
                sysCurrentUserAccountService.editCurrentUserAccount(currentUserAccount);
                sysOrders.setOrderStatus("30");
                currenuser.setCreditArrearsState("10");
                currenuser.setReturnDepositPayTime(DateUtil.currentDateTime());
                sysCurrentUserService.updateCurrenUser(currenuser);
                //记录用户储值金额变化日志会使用
//                accountAmountString = accountAmount.toString();
//                orderAmountString = orderAmount.toString();
//                subtractString = orderAmounts;
            }
            int i = sysOrderService.updateOrder(sysOrders);
            if (i == 1) {
                SysOrderOperationLog operationLog = new SysOrderOperationLog();
                operationLog.setOperationId(IdUtils.getSeqId());
                operationLog.setOperationName(currentUser.getUserName());
                operationLog.setOperationNumber(order.getOrderNumber());
                operationLog.setOperationPhone(order.getCurrentUserPhone());
                operationLog.setOperationScenicSpotName(order.getOrderScenicSpotName());
                operationLog.setOperationRobotCode(order.getOrderRobotCode());
                operationLog.setOperationTotalTime(order.getTotalTime());
                operationLog.setOperationFront(DictUtils.getPaymentMethodMap().get(sysOrder.getSubMethod()));
                operationLog.setOperationAfter(DictUtils.getPaymentMethodMap().get(sysOrder.getSubMethod()));
                operationLog.setCreateDate(DateUtil.currentDateTime());
                sysOrderService.insertOrderOperationLog(operationLog);

                //用户储值金额变化日志
//                SysCurrentUserAccountRefundLog sysCurrentUserAccountRefundLog = new SysCurrentUserAccountRefundLog();
//                sysCurrentUserAccountRefundLog.setAccountLogId(IdUtils.getSeqId());
//                sysCurrentUserAccountRefundLog.setAccountLogUserPhone(order.getCurrentUserPhone());
//                sysCurrentUserAccountRefundLog.setAccountLogLoginName(currentUser.getUserName());
//                sysCurrentUserAccountRefundLog.setAccountLogBefore(accountAmountString);
//                sysCurrentUserAccountRefundLog.setAccountLogAfter(subtractString);
//                sysCurrentUserAccountRefundLog.setAccountLogPrice(orderAmountString);
//                sysCurrentUserAccountRefundLog.setCreateDate(DateUtil.currentDateTime());
//                sysCurrentUserAccountRefundLog.setUpdateDate(DateUtil.currentDateTime());
//                sysCurrentUserAccountRefundLogService.insertAccountRefundLog(sysCurrentUserAccountRefundLog);

                dataModel.setData("");
                dataModel.setMsg("提交状态修改成功！");
                dataModel.setState(Constant.STATE_SUCCESS);
                return dataModel;
            } else {
                dataModel.setData("");
                dataModel.setMsg("提交状态修改失败！");
                dataModel.setState(Constant.STATE_FAILURE);
                return dataModel;
            }
        } catch (Exception e) {
            dataModel.setData("");
            dataModel.setMsg("提交状态修改失败！(请联系管理员)！");
            dataModel.setState(Constant.STATE_FAILURE);
            logger.error("updateSubMethod", e);
            return dataModel;
        }
    }

    /**
     * @return com.hna.hka.archive.management.system.util.ReturnModel
     * @Author 郭凯
     * @Description 设置黑名单
     * @Date 16:05 2020/12/10
     * @Param [id]
     **/
    @RequestMapping("/setBlacklist")
    @ResponseBody
    public ReturnModel setBlacklist(SysOrder sysOrder) {
        ReturnModel dataModel = new ReturnModel();
        try {
            SysCurrentBlackList currenUser = sysOrderService.getBlacklistByUserId(sysOrder.getUserId());
            if (ToolUtil.isNotEmpty(currenUser)) {
                dataModel.setData("");
                dataModel.setMsg("此用户已经是黑名单成员，请勿重复添加！");
                dataModel.setState(Constant.STATE_FAILURE);
                return dataModel;
            }
            int i = sysOrderService.addBlacklist(sysOrder.getUserId());
            if (i == 1) {
                dataModel.setData("");
                dataModel.setMsg("设置黑名单成功！");
                dataModel.setState(Constant.STATE_SUCCESS);
                return dataModel;
            } else {
                dataModel.setData("");
                dataModel.setMsg("设置黑名单失败！");
                dataModel.setState(Constant.STATE_FAILURE);
                return dataModel;
            }
        } catch (Exception e) {
            dataModel.setData("");
            dataModel.setMsg("设置黑名单失败！(请联系管理员)");
            dataModel.setState(Constant.STATE_FAILURE);
            return dataModel;
        }
    }

    /**
     * @param @param  request
     * @param @param  sysOrder
     * @param @return
     * @param @throws IOException
     * @return ReturnModel
     * @throws
     * @Author 郭凯
     * @Description: 押金扣款后退款
     * @Title: doPayDepositRefund
     * @date 2021年1月18日 下午2:10:12
     */
    @RequestMapping(value = "/doPayDepositRefund", method = {RequestMethod.POST})
    @ResponseBody
    public ReturnModel doPayDepositRefund(HttpServletRequest request, SysOrder sysOrder) throws IOException {
        ReturnModel weChatModel = new ReturnModel();
        SysUsers currentUser = this.getSysUser();
        SysOrder order = null;
        SortedMap<Object, Object> packageP = null;
        try {
            order = sysOrderService.getOrderByNumber(sysOrder.getOrderNumber());
            if (ToolUtil.isEmpty(order)) {
                weChatModel.setData("");
                weChatModel.setMsg("未查询此订单！");
                weChatModel.setState(Constant.STATE_FAILURE);
                return weChatModel;
            }
            String payMoney = request.getParameter("payMoney"); //获取用户可退金额
            SysCurrentUser currenuser = sysCurrentUserService.getCurrentUserById(order.getUserId());
            if (currenuser == null) {
                weChatModel.setData("");
                weChatModel.setMsg("未查询到此用户！");
                weChatModel.setState(Constant.STATE_FAILURE);
                return weChatModel;
            }
            String reason = request.getParameter("reason");
            String reasonsRefunds = request.getParameter("reasonsRefunds");
            if (ToolUtil.isEmpty(reason) && ToolUtil.isEmpty(reasonsRefunds)) {
                weChatModel.setData("");
                weChatModel.setMsg("请选择扣款原因！");
                weChatModel.setState(Constant.STATE_FAILURE);
                return weChatModel;
            }
            if (ToolUtil.isNotEmpty(reasonsRefunds)) {
                reason = reason + reasonsRefunds;
            }
            WechatDeposit deposit = sysOrderService.getWechatDepositByOutTradeNo(sysOrder.getOutTradeNo());
            if (ToolUtil.isEmpty(deposit)) {
                weChatModel.setData("");
                weChatModel.setMsg("未查询到押金信息！");
                weChatModel.setState(Constant.STATE_FAILURE);
                return weChatModel;
            }
            int fee = 0;// 初始化金额
            int refundFee = 0;// 退款金额
            // 转换金额
            fee = Integer.parseInt(new java.text.DecimalFormat("0").format(Double.parseDouble(deposit.getDepositMoney()) * 100));
            refundFee = Integer.parseInt(new java.text.DecimalFormat("0").format(Double.parseDouble(payMoney) * 100));
            // 获取用户openId
            String openid = currenuser.getCurrentOpenId();
            // 获取用户订单编号
            String outTradeNo = order.getOutTradeNo();
            // 微信商户平台
            packageP = PayCommonUtil.weChatRefund(weChatInfo, openid, outTradeNo, fee, refundFee);
            BigDecimal bignum1 = new BigDecimal(order.getOrderRefundAmount());
            BigDecimal bignum2 = new BigDecimal(payMoney);
            if (order.getOrderRefundAmount() != null && order.getOrderRefundAmount().length() != 0 && payMoney != null && payMoney.length() != 0) {
                order.setOrderRefundAmount(bignum1.add(bignum2).toString());
            } else if (order.getOrderRefundAmount() == null && order.getOrderRefundAmount().length() == 0 && payMoney != null && payMoney.length() != 0) {
                order.setOrderRefundAmount(payMoney);
            } else if (order.getOrderRefundAmount() != null && order.getOrderRefundAmount().length() != 0 && payMoney == null && payMoney.length() == 0) {
                order.setOrderRefundAmount(order.getOrderRefundAmount());
            }
            order.setOrderNumber(order.getOrderNumber());
            //解析微信返回结果
            if (packageP != null && packageP.get("return_code").toString().equals("SUCCESS") && packageP.get("result_code").toString().equals("SUCCESS")) {
                order.setReasonsRefunds(reason);
                //计算全额退款
                if (order.getOrderRefundAmount() != null && order.getOrderAmount() != null) {
                    Double orderAmount = Double.valueOf(order.getOrderRefundAmount());
                    Double orderRefundAmount = Double.valueOf(order.getOrderAmount());
                    if (String.format("%.2f", orderRefundAmount).equals(String.format("%.2f", orderAmount))) {
                        order.setOrderStatus("60");
                    }
                }
                sysOrderService.updateOrder(order);
                //添加退款日志
                SysOrderLog orderLog = new SysOrderLog();
                orderLog.setOrderLogId(IdUtils.getSeqId());
                orderLog.setOrderLogLoginname(currentUser.getLoginName());
                orderLog.setOrderLogUsername(currentUser.getUserName());
                orderLog.setOrderLogPhone(order.getCurrentUserPhone());
                orderLog.setOrderLogNumber(order.getOrderNumber());
                orderLog.setDepositMoney("退款金额：" + payMoney);
                orderLog.setScenicSpotName(order.getOrderScenicSpotName());
                orderLog.setOrderLogReason("退款成功");
                orderLog.setReturnResultCode(packageP.get("result_code").toString());
                orderLog.setCreateDate(DateUtil.currentDateTime());
                orderLog.setUpdateDate(DateUtil.currentDateTime());
                sysOrderLogService.insertOrderLog(orderLog);
                weChatModel.setData("");
                weChatModel.setMsg("订单退款成功！");
                weChatModel.setState(Constant.STATE_SUCCESS);
                return weChatModel;
            } else {
                //添加退款日志信息
                SysOrderLog orderLog = new SysOrderLog();
                orderLog.setOrderLogId(IdUtils.getSeqId());
                orderLog.setOrderLogLoginname(currentUser.getLoginName());
                orderLog.setOrderLogUsername(currentUser.getUserName());
                orderLog.setOrderLogPhone(order.getCurrentUserPhone());
                orderLog.setOrderLogNumber(order.getOrderNumber());
                orderLog.setDepositMoney("退款金额：" + payMoney);
                orderLog.setScenicSpotName(order.getOrderScenicSpotName());
                orderLog.setOrderLogReason(packageP.get("err_code_des").toString());
                orderLog.setReturnResultCode(packageP.get("result_code").toString());
                orderLog.setCreateDate(DateUtil.currentDateTime());
                orderLog.setUpdateDate(DateUtil.currentDateTime());
                sysOrderLogService.insertOrderLog(orderLog);
                weChatModel.setData("");
                weChatModel.setMsg("订单退款失败！");
                weChatModel.setState(Constant.STATE_FAILURE);
                return weChatModel;
            }
        } catch (Exception e) {
            weChatModel.setData("");
            weChatModel.setMsg("doPayBackDeposit退押金异常异常！");
            weChatModel.setState(Constant.STATE_FAILURE);
            logger.error("doPayOrderBackDeposit", e);
            return weChatModel;
        }
    }

    /**
     * @param @param  request
     * @param @param  sysOrder
     * @param @return
     * @return ReturnModel
     * @throws
     * @Author 郭凯
     * @Description: 支付宝订单退款
     * @Title: alipayOrderRefund
     * @date 2021年1月21日 上午11:25:41
     */
    @RequestMapping("/alipayOrderRefund")
    @ResponseBody
    public ReturnModel alipayOrderRefund(HttpServletRequest request, SysOrder sysOrder) {
        ReturnModel alipayModel = new ReturnModel();
        try {
            SysUsers user = this.getSysUser();
            SysOrder order = sysOrderService.getOrderByNumber(sysOrder.getOrderNumber());
            if (ToolUtil.isEmpty(order)) {
                alipayModel.setData("");
                alipayModel.setMsg("未查询此订单！");
                alipayModel.setState(Constant.STATE_FAILURE);
                return alipayModel;
            }
            String payMoney = request.getParameter("payMoney"); //获取用户可退金额
            SysCurrentUser currenuser = sysCurrentUserService.getCurrentUserById(order.getUserId());
            if (currenuser == null) {
                alipayModel.setData("");
                alipayModel.setMsg("未查询到此用户！");
                alipayModel.setState(Constant.STATE_FAILURE);
                return alipayModel;
            }
            String reason = request.getParameter("reason");//获取退款原因
            if (ToolUtil.isEmpty(reason) && ToolUtil.isEmpty(sysOrder.getReasonsRefunds())) {
                alipayModel.setData("");
                alipayModel.setMsg("请选择扣款原因！");
                alipayModel.setState(Constant.STATE_FAILURE);
                return alipayModel;
            }
            if (ToolUtil.isNotEmpty(sysOrder.getReasonsRefunds())) {
                reason = reason + sysOrder.getReasonsRefunds();
            }
            SortedMap<String, String> packageP = PayCommonUtil.alipayOrderRefund(alipayInfo, order.getOrderNumber(), payMoney);
            if (packageP != null && "Y".equals(packageP.get("FundChange")) && "10000".equals(packageP.get("code"))) {
                BigDecimal bignum1 = new BigDecimal(order.getOrderRefundAmount());
                BigDecimal bignum2 = new BigDecimal(payMoney);
                if (order.getOrderRefundAmount() != null && order.getOrderRefundAmount().length() != 0 && payMoney != null && payMoney.length() != 0) {
                    order.setOrderRefundAmount(bignum1.add(bignum2).toString());
                } else if (order.getOrderRefundAmount() == null && order.getOrderRefundAmount().length() == 0 && payMoney != null && payMoney.length() != 0) {
                    order.setOrderRefundAmount(payMoney);
                } else if (order.getOrderRefundAmount() != null && order.getOrderRefundAmount().length() != 0 && payMoney == null && payMoney.length() == 0) {
                    order.setOrderRefundAmount(order.getOrderRefundAmount());
                }
                order.setOrderNumber(order.getOrderNumber());
                order.setReasonsRefunds(reason);
                //计算全额退款
                if (order.getOrderRefundAmount() != null && order.getOrderAmount() != null) {
                    Double orderAmount = Double.valueOf(order.getOrderRefundAmount());
                    Double orderRefundAmount = Double.valueOf(order.getOrderAmount());
                    if (String.format("%.2f", orderRefundAmount).equals(String.format("%.2f", orderAmount))) {
                        order.setOrderStatus("60");
                    }
                }
                sysOrderService.updateOrder(order);
                //添加退款日志
                SysOrderLog orderLog = new SysOrderLog();
                orderLog.setOrderLogId(IdUtils.getSeqId());
                orderLog.setOrderLogLoginname(user.getLoginName());
                orderLog.setOrderLogUsername(user.getUserName());
                orderLog.setOrderLogPhone(order.getCurrentUserPhone());
                orderLog.setOrderLogNumber(order.getOrderNumber());
                orderLog.setDepositMoney("退款金额：" + payMoney);
                orderLog.setScenicSpotName(order.getOrderScenicSpotName());
                orderLog.setOrderLogReason("退款成功");
                orderLog.setReturnResultCode(packageP.get("msg").toString());
                orderLog.setCreateDate(DateUtil.currentDateTime());
                orderLog.setUpdateDate(DateUtil.currentDateTime());
                sysOrderLogService.insertOrderLog(orderLog);
                alipayModel.setData("");
                alipayModel.setMsg("订单退款成功！");
                alipayModel.setState(Constant.STATE_SUCCESS);
                return alipayModel;
            } else {
                //添加退款日志信息
                SysOrderLog orderLog = new SysOrderLog();
                orderLog.setOrderLogId(IdUtils.getSeqId());
                orderLog.setOrderLogLoginname(user.getLoginName());
                orderLog.setOrderLogUsername(user.getUserName());
                orderLog.setOrderLogPhone(order.getCurrentUserPhone());
                orderLog.setOrderLogNumber(order.getOrderNumber());
                orderLog.setDepositMoney("退款金额：" + payMoney);
                orderLog.setScenicSpotName(order.getOrderScenicSpotName());
                orderLog.setOrderLogReason("退款失败");
                orderLog.setReturnResultCode(packageP.get("subMsg").toString());
                orderLog.setCreateDate(DateUtil.currentDateTime());
                orderLog.setUpdateDate(DateUtil.currentDateTime());
                sysOrderLogService.insertOrderLog(orderLog);
                alipayModel.setData("");
                alipayModel.setMsg("订单退款失败！");
                alipayModel.setState(Constant.STATE_FAILURE);
                return alipayModel;
            }
        } catch (Exception e) {
            alipayModel.setData("");
            alipayModel.setMsg("alipayOrderRefund退押金异常异常！");
            alipayModel.setState(Constant.STATE_FAILURE);
            logger.error("alipayOrderRefund", e);
            return alipayModel;
        }
    }

    /**
     * @Method weChatRefund
     * @Author 郭凯
     * @Version 1.0
     * @Description 储值抵扣微信退款
     * @Return com.hna.hka.archive.management.system.util.ReturnModel
     * @Date 2021/4/19 15:35
     */
    @RequestMapping(value = "/weChatRefund", method = {RequestMethod.POST})
    @ResponseBody
    public ReturnModel weChatRefund(HttpServletRequest request, SysOrder sysOrder) throws IOException {
        ReturnModel weChatModel = new ReturnModel();
        SysUsers currentUser = this.getSysUser();
        String weChat_app_id = appid;// 获取微信小程序APPID
        String refund = wechatRefundRequest;// 获取微信支付链接
        String secret = apiSecretkey;// 获取微信小程序唯一密钥32位
        String mch_id = null;// 初始化
        String fileCert_Path = null;// 初始化
        SysOrder order = null;
        SortedMap<Object, Object> packageP = null;
        try {
            order = sysOrderService.getOrderByNumber(sysOrder.getOrderNumber());
            if (ToolUtil.isEmpty(order)) {
                weChatModel.setData("");
                weChatModel.setMsg("未查询此订单！");
                weChatModel.setState(Constant.STATE_FAILURE);
                return weChatModel;
            }
            String payMoney = request.getParameter("payMoney"); //获取用户可退金额
            SysCurrentUser currenuser = sysCurrentUserService.getCurrentUserById(order.getUserId());
            if (currenuser == null) {
                weChatModel.setData("");
                weChatModel.setMsg("未查询到此用户！");
                weChatModel.setState(Constant.STATE_FAILURE);
                return weChatModel;
            }
            String reason = request.getParameter("reason");
            String reasonsRefunds = request.getParameter("reasonsRefunds");
            if (ToolUtil.isEmpty(reason) && ToolUtil.isEmpty(reasonsRefunds)) {
                weChatModel.setData("");
                weChatModel.setMsg("请选择扣款原因！");
                weChatModel.setState(Constant.STATE_FAILURE);
                return weChatModel;
            }
            if (ToolUtil.isNotEmpty(reasonsRefunds)) {
                reason = reason + reasonsRefunds;
            }

            int fee = 0;// 初始化金额
            int refundFee = 0;// 退款金额
            // 转换金额
            fee = Integer.parseInt(new java.text.DecimalFormat("0").format(Double.parseDouble(order.getOrderAmount()) * 100));
            refundFee = Integer.parseInt(new java.text.DecimalFormat("0").format(Double.parseDouble(payMoney) * 100));
            // 获取用户openid
            String openid = currenuser.getCurrentOpenId();
            // 获取用户订单编号
            String outTradeNo = order.getOrderNumber();
            // 订单标题
            String body = "小程序退押金";
            // 时间戳
            final String nonce_str = System.currentTimeMillis() + "";
            // 获取商户ID
            mch_id = mchid;
            // 获取签名证书路径以及文件（此变量为默认变量）
            fileCert_Path = fileCertPath;
            WechatBusinessManagement Management = sysScenicSpotCertificateSpotService.getBusinessManagementByScenicSpotId(order.getOrderScenicSpotId());
            if (Management != null) {
                //覆盖默认商户号
                mch_id = Management.getMerchantNumber();
                //覆盖默认路径
                fileCert_Path = certPath + Management.getCertFileName();
                //覆盖微信小程序唯一密钥
                secret = Management.getMerchantSecret();
            }
            // 微信商户平台
            packageP = PayCommonUtil.sendWechatPayBackOrder(weChat_app_id, mch_id, fileCert_Path, secret, nonce_str, body, outTradeNo, fee, refundFee, openid, request.getRemoteAddr(), refund);
            BigDecimal bignum1 = new BigDecimal(order.getOrderRefundAmount());
            BigDecimal bignum2 = new BigDecimal(payMoney);
            if (order.getOrderRefundAmount() != null && order.getOrderRefundAmount().length() != 0 && payMoney != null && payMoney.length() != 0) {
                order.setOrderRefundAmount(bignum1.add(bignum2).toString());
            } else if (order.getOrderRefundAmount() == null && order.getOrderRefundAmount().length() == 0 && payMoney != null && payMoney.length() != 0) {
                order.setOrderRefundAmount(payMoney);
            } else if (order.getOrderRefundAmount() != null && order.getOrderRefundAmount().length() != 0 && payMoney == null && payMoney.length() == 0) {
                order.setOrderRefundAmount(order.getOrderRefundAmount());
            }
            //解析微信返回结果
            if (packageP != null && packageP.get("return_code").toString().equals("SUCCESS") && packageP.get("result_code").toString().equals("SUCCESS")) {
                order.setReasonsRefunds(reason);
                //计算全额退款
                if (ToolUtil.isNotEmpty(order.getOrderRefundAmount()) && ToolUtil.isNotEmpty(order.getOrderAmount())) {
                    Double orderRefundAmount = Double.valueOf(order.getOrderRefundAmount());
                    Double orderAmount = Double.valueOf(order.getOrderAmount());
                    if (String.format("%.2f", orderRefundAmount).equals(String.format("%.2f", orderAmount))) {
                        order.setOrderStatus("60");
                    }
                }
                sysOrderService.updateOrder(order);
                //添加退款日志
                SysOrderLog orderLog = new SysOrderLog();
                orderLog.setOrderLogId(IdUtils.getSeqId());
                orderLog.setOrderLogLoginname(currentUser.getLoginName());
                orderLog.setOrderLogUsername(currentUser.getUserName());
                orderLog.setOrderLogPhone(order.getCurrentUserPhone());
                orderLog.setOrderLogNumber(order.getOrderNumber());
                orderLog.setDepositMoney("微信抵扣退款：" + payMoney);
                orderLog.setScenicSpotName(order.getOrderScenicSpotName());
                orderLog.setOrderLogReason("退款成功");
                orderLog.setReturnResultCode(packageP.get("result_code").toString());
                orderLog.setCreateDate(DateUtil.currentDateTime());
                orderLog.setUpdateDate(DateUtil.currentDateTime());
                sysOrderLogService.insertOrderLog(orderLog);
                weChatModel.setData("");
                weChatModel.setMsg("订单退款成功！");
                weChatModel.setState(Constant.STATE_SUCCESS);
                return weChatModel;
            } else {
                //添加退款日志信息
                SysOrderLog orderLog = new SysOrderLog();
                orderLog.setOrderLogId(IdUtils.getSeqId());
                orderLog.setOrderLogLoginname(currentUser.getLoginName());
                orderLog.setOrderLogUsername(currentUser.getUserName());
                orderLog.setOrderLogPhone(order.getCurrentUserPhone());
                orderLog.setOrderLogNumber(order.getOrderNumber());
                orderLog.setDepositMoney("微信抵扣退款：" + payMoney);
                orderLog.setScenicSpotName(order.getOrderScenicSpotName());
                orderLog.setOrderLogReason(packageP.get("err_code_des").toString());
                orderLog.setReturnResultCode(packageP.get("result_code").toString());
                orderLog.setCreateDate(DateUtil.currentDateTime());
                orderLog.setUpdateDate(DateUtil.currentDateTime());
                sysOrderLogService.insertOrderLog(orderLog);
                weChatModel.setData("");
                weChatModel.setMsg("订单退款失败！");
                weChatModel.setState(Constant.STATE_FAILURE);
                return weChatModel;
            }
        } catch (Exception e) {
            weChatModel.setData("");
            weChatModel.setMsg("weChatRefund退押金异常异常！");
            weChatModel.setState(Constant.STATE_FAILURE);
            logger.error("weChatRefund", e);
            return weChatModel;
        }
    }

    /**
     * @Method deductionAndRefund
     * @Author 郭凯
     * @Version 1.0
     * @Description 储值抵扣订单退款
     * @Return com.hna.hka.archive.management.system.util.ReturnModel
     * @Date 2021/4/19 17:34
     */
    @RequestMapping("/deductionAndRefund")
    @ResponseBody
    public ReturnModel deductionAndRefund(SysOrder sysOrder) throws IOException, JDOMException {
        ReturnModel weChatModel = new ReturnModel();
        SysUsers currentUser = this.getSysUser();
        try {
            String payMoney = request.getParameter("payMoney"); //获取用户可退金额
            String reason = request.getParameter("reason");
            String reasonsRefunds = request.getParameter("reasonsRefunds");
            if (ToolUtil.isEmpty(reason) && ToolUtil.isEmpty(reasonsRefunds)) {
                weChatModel.setData("");
                weChatModel.setMsg("请选择扣款原因！");
                weChatModel.setState(Constant.STATE_FAILURE);
                return weChatModel;
            }
            if (ToolUtil.isNotEmpty(reasonsRefunds)) {
                reason = reason + reasonsRefunds;
            }
            SysOrder order = sysOrderService.getOrderByNumber(sysOrder.getOrderNumber());
            if (ToolUtil.isEmpty(order)) {
                weChatModel.setData("");
                weChatModel.setMsg("订单查询失败！");
                weChatModel.setState(Constant.STATE_FAILURE);
                return weChatModel;
            }
            SysCurrentUser sysCurrentUser = sysCurrentUserService.getCurrentUserById(order.getUserId());
            if (ToolUtil.isEmpty(sysCurrentUser)) {
                weChatModel.setData("");
                weChatModel.setMsg("用户信息查询失败！");
                weChatModel.setState(Constant.STATE_FAILURE);
                return weChatModel;
            }
            //判断是否全额退款
            BigDecimal bignum1 = new BigDecimal(order.getDeductibleRefundAmount());
            BigDecimal bignum2 = new BigDecimal(payMoney);
            if (order.getDeductibleRefundAmount() != null && order.getDeductibleRefundAmount().length() != 0 && payMoney != null && payMoney.length() != 0) {
                order.setDeductibleRefundAmount(bignum1.add(bignum2).toString());
            } else if (order.getDeductibleRefundAmount() == null && order.getDeductibleRefundAmount().length() == 0 && payMoney != null && payMoney.length() != 0) {
                order.setDeductibleRefundAmount(payMoney);
            } else if (order.getDeductibleRefundAmount() != null && order.getDeductibleRefundAmount().length() != 0 && payMoney == null && payMoney.length() == 0) {
                order.setDeductibleRefundAmount(order.getDeductibleRefundAmount());
            }
            order.setReasonsRefunds(reason);
            int i = sysOrderService.updateOrder(order);
            if (i == 1) {
                SysCurrentUserAccount account = sysCurrentUserAccountService.selectAccountByUserId(sysCurrentUser.getCurrentUserId());
                if (ToolUtil.isEmpty(account)) {
                    weChatModel.setData("");
                    weChatModel.setMsg("未查询到此用户账户！");
                    weChatModel.setState(Constant.STATE_FAILURE);
                    return weChatModel;
                }
                SysCurrentUserAccount currentUserAccount = new SysCurrentUserAccount();
                currentUserAccount.setAccountId(account.getAccountId());
                Double orderAmount = Double.valueOf(account.getAccountAmount());
                Double orderRefundAmount = Double.valueOf(payMoney);
                BigDecimal bd1 = new BigDecimal(Double.toString(orderAmount));
                BigDecimal bd2 = new BigDecimal(Double.toString(orderRefundAmount));
                double doubleValue = bd1.add(bd2).doubleValue();
                currentUserAccount.setAccountAmount(String.format("%.2f", doubleValue));
                currentUserAccount.setUpdateDate(DateUtil.currentDateTime());
                int j = sysCurrentUserAccountService.editCurrentUserAccount(currentUserAccount);
                if (j == 1) {
                    SysOrderLog orderLog = new SysOrderLog();
                    orderLog.setOrderLogId(IdUtils.getSeqId());
                    orderLog.setOrderLogLoginname(currentUser.getLoginName());
                    orderLog.setOrderLogUsername(currentUser.getUserName());
                    orderLog.setOrderLogPhone(order.getCurrentUserPhone());
                    orderLog.setOrderLogNumber(order.getOrderNumber());
                    orderLog.setDepositMoney("抵扣金额退款：" + payMoney);
                    orderLog.setScenicSpotName(order.getOrderScenicSpotName());
                    orderLog.setOrderLogReason("退款成功");
                    orderLog.setReturnResultCode("");
                    orderLog.setCreateDate(DateUtil.currentDateTime());
                    orderLog.setUpdateDate(DateUtil.currentDateTime());
                    sysOrderLogService.insertOrderLog(orderLog);

                    //储值账户记录
                    SysCurrentUserAccountDeduction sysCurrentUserAccountDeduction = new SysCurrentUserAccountDeduction();
                    sysCurrentUserAccountDeduction.setDeductionId(IdUtils.getSeqId());
                    sysCurrentUserAccountDeduction.setScenicSpotId(order.getOrderScenicSpotId());
                    sysCurrentUserAccountDeduction.setScenicSpotName(order.getOrderScenicSpotName());
                    sysCurrentUserAccountDeduction.setOrderId(order.getOrderId());
                    sysCurrentUserAccountDeduction.setOrderNumber(order.getOrderNumber());
                    sysCurrentUserAccountDeduction.setUserPhone(sysCurrentUser.getCurrentUserPhone());
                    sysCurrentUserAccountDeduction.setUserId(sysCurrentUser.getCurrentUserId());
                    sysCurrentUserAccountDeduction.setAccountAmountFront(bd1.toString());
                    sysCurrentUserAccountDeduction.setDeductionAmount(bd2.toString());
                    sysCurrentUserAccountDeduction.setAccountAmountRear(String.format("%.2f", doubleValue));
                    sysCurrentUserAccountDeduction.setAccountType("2");
                    sysCurrentUserAccountDeduction.setCreateTime(DateUtil.currentDateTime());
                    sysCurrentUserAccountDeduction.setUpdateTime(DateUtil.currentDateTime());
                    sysCurrentUserAccountDeductionService.add(sysCurrentUserAccountDeduction);


                    //用户储值金额变化日志
//                    SysCurrentUserAccountRefundLog sysCurrentUserAccountRefundLog = new SysCurrentUserAccountRefundLog();
//                    sysCurrentUserAccountRefundLog.setAccountLogId(IdUtils.getSeqId());
//                    sysCurrentUserAccountRefundLog.setAccountLogLoginName(currentUser.getLoginName());
//                    sysCurrentUserAccountRefundLog.setAccountLogUserPhone(order.getCurrentUserPhone());
//                    sysCurrentUserAccountRefundLog.setAccountLogBefore(orderAmount.toString());
//                    sysCurrentUserAccountRefundLog.setAccountLogAfter(String.valueOf(doubleValue));
//                    sysCurrentUserAccountRefundLog.setAccountLogPrice(orderRefundAmount.toString());
//                    sysCurrentUserAccountRefundLog.setCreateDate(DateUtil.currentDateTime());
//                    sysCurrentUserAccountRefundLog.setUpdateDate(DateUtil.currentDateTime());
//                    sysCurrentUserAccountRefundLogService.insertAccountRefundLog(sysCurrentUserAccountRefundLog);

                }
                weChatModel.setData(true);
                weChatModel.setMsg("订单退款成功！");
                weChatModel.setState(Constant.STATE_SUCCESS);
                return weChatModel;
            } else {
                SysOrderLog orderLog = new SysOrderLog();
                orderLog.setOrderLogId(IdUtils.getSeqId());
                orderLog.setOrderLogLoginname(currentUser.getLoginName());
                orderLog.setOrderLogUsername(currentUser.getUserName());
                orderLog.setOrderLogPhone(order.getCurrentUserPhone());
                orderLog.setOrderLogNumber(order.getOrderNumber());
                orderLog.setDepositMoney("抵扣金额金额：" + payMoney);
                orderLog.setScenicSpotName(order.getOrderScenicSpotName());
                orderLog.setOrderLogReason("退款失败");
                orderLog.setReturnResultCode("");
                orderLog.setCreateDate(DateUtil.currentDateTime());
                orderLog.setUpdateDate(DateUtil.currentDateTime());
                sysOrderLogService.insertOrderLog(orderLog);
                weChatModel.setData("");
                weChatModel.setMsg("订单退款退款失败！");
                weChatModel.setState(Constant.STATE_FAILURE);
                return weChatModel;
            }
        } catch (Exception e) {
            weChatModel.setData("");
            weChatModel.setMsg("deductionAndRefund退订单异常异常！");
            weChatModel.setState(Constant.STATE_FAILURE);
            logger.error("deductionAndRefund", e);
            return weChatModel;
        }
    }

    /**
     * 根据订单查询运行轨迹
     *
     * @param sysOrder
     * @return
     */
    @ApiOperation("根据订单查询运行轨迹")
    @RequestMapping("/getOrderRunningTrack")
    @ResponseBody
    public ReturnModel getOrderRunningTrack(Long orderId) {

        List<Map<String, String>> maps = sysOrderService.getOrderRunningTrack(orderId);

        ReturnModel returnModel = new ReturnModel();
        returnModel.setData(maps);
        returnModel.setMsg("success");
        returnModel.setState(Constant.STATE_SUCCESS);
        return returnModel;
    }

    @ApiOperation("未付款订单减调度费")
    @RequestMapping("/unpaidLessOrderFee")
    @ResponseBody
    public ReturnModel unpaidLessOrderFee(SysOrder sysOrder) {

        ReturnModel weChatModel = new ReturnModel();
        SysUsers currentUser = this.getSysUser();
        try {
            SysCurrentUser sysCurrentUser = sysCurrentUserService.getCurrentUserById(sysOrder.getUserId());
            if (ToolUtil.isNotEmpty(sysCurrentUser)) {
                SysOrder order = sysOrderService.getOrderByNumber(sysOrder.getOrderNumber());
                if (order == null) {
                    weChatModel.setData("");
                    weChatModel.setMsg("订单查询失败！");
                    weChatModel.setState(Constant.STATE_FAILURE);
                    String model = JsonUtils.toString(weChatModel);//对象转JSON
                    return weChatModel;
                }
                if ("0".equals(order.getDispatchingFee())) {
                    weChatModel.setData("");
                    weChatModel.setMsg("该订单没有调度费！");
                    weChatModel.setState(Constant.STATE_FAILURE);
                    String model = JsonUtils.toString(weChatModel);//对象转JSON
                    return weChatModel;
                }

                if ("0".equals(order.getIsPaying())) {
                    weChatModel.setData("");
                    weChatModel.setMsg("用户正在付款，无法进行订单金额修改操作！");
                    weChatModel.setState(Constant.STATE_FAILURE);
                    String model = JsonUtils.toString(weChatModel);//对象转JSON
                    return weChatModel;
                }

                SysCurrentUser currentUserById = sysCurrentUserService.getCurrentUserById(order.getUserId());
                if (org.springframework.util.StringUtils.isEmpty(currentUserById)) {
                    weChatModel.setData("");
                    weChatModel.setMsg("该订单没有用户！");
                    weChatModel.setState(Constant.STATE_FAILURE);
                    String model = JsonUtils.toString(weChatModel);//对象转JSON
                    return weChatModel;
                }

                SysRobot robotCode = sysRobotService.getRobotCodeBy(order.getOrderRobotCode());
                if (robotCode == null) {
                    weChatModel.setData("");
                    weChatModel.setMsg("机器人查询失败！");
                    weChatModel.setState(Constant.STATE_FAILURE);
                    String model = JsonUtils.toString(weChatModel);//对象转JSON
                    return weChatModel;
                }

                int i = sysOrderService.reduceOfDispatchingFee(request, order.getOrderId());

                if (1 == i) {
//                selectOrderByPrimaryKey.setOrderStatus("30");
//                selectOrderByPrimaryKey.setOrderEndTime(DateUtil.currentDateTime());
//                selectOrderByPrimaryKey.setUpdateDate(DateUtil.currentDateTime());
//                sysOrderService.updateOrder(selectOrderByPrimaryKey);
                    weChatModel.setData("1");
                    weChatModel.setMsg("减免成功");
                    weChatModel.setState(Constant.STATE_SUCCESS);
                    String model = JsonUtils.toString(weChatModel);//对象转JSON
                    return weChatModel;
                } else if (2 == i) {
                    weChatModel.setData("0");
                    weChatModel.setMsg("该笔订单已支付，无法在减免订单费，请联系客服进行退调度费。");
                    weChatModel.setState(Constant.STATE_FAILURE);
                    String model = JsonUtils.toString(weChatModel);//对象转JSON
                    return weChatModel;
                } else if (3 == i) {
                    weChatModel.setData("0");
                    weChatModel.setMsg("该笔订单已经退过订单费，无需减免调度费。");
                    weChatModel.setState(Constant.STATE_FAILURE);
                    String model = JsonUtils.toString(weChatModel);//对象转JSON
                    return weChatModel;
                } else {
                    weChatModel.setData("0");
                    weChatModel.setMsg("减免失败，请联系客服处理。");
                    weChatModel.setState(Constant.STATE_FAILURE);
                    String model = JsonUtils.toString(weChatModel);//对象转JSON
                    return weChatModel;
                }
            }
        } catch (Exception e) {
            weChatModel.setData("");
            weChatModel.setMsg("订单金额修改失败(请联系管理员)！");
            weChatModel.setState(Constant.STATE_FAILURE);
            logger.error("editOrderAmount", e);
            return weChatModel;

        }
        return weChatModel;
    }


    /**
     * 非储值订单，改为储值订单
     */

    @ApiOperation("非储值订单，改为储值订单")
    @RequestMapping("/orderTransformation")
    @ResponseBody
    public ReturnModel orderTransformation(SysOrder sysOrder, String storedPhone) {

        ReturnModel returnModel = new ReturnModel();
        Long orderId = sysOrder.getOrderId();
        SysOrder orderById = sysOrderService.getOrderById(orderId);
        String currentUserPhone = orderById.getCurrentUserPhone();
        SysCurrentUser currentUserByPhone1 = sysCurrentUserService.getCurrentUserByPhone(currentUserPhone);

        if (ToolUtil.isEmpty(orderById)) {
            returnModel.setData("");
            returnModel.setMsg("没有找到订单，无法修改!");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }

        SysCurrentUser currentUserByPhone = sysCurrentUserService.getCurrentUserByPhone(storedPhone);
        if (ToolUtil.isEmpty(currentUserByPhone)) {
            returnModel.setData("");
            returnModel.setMsg("储值手机号未找到，请确认后重新输入!");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
        SysCurrentUserAccount sysCurrentUserAccount = sysCurrentUserAccountService.selectAccountByUserId(currentUserByPhone.getCurrentUserId());

        //储值所剩金额
        BigDecimal currentPrice = new BigDecimal(sysCurrentUserAccount.getAccountAmount());
        //实际计费金额
        BigDecimal actualPrice = new BigDecimal(orderById.getOrderAmount());
        //折数
        Double d = Double.parseDouble(sysCurrentUserAccount.getDiscount()) / 10;
        BigDecimal discount = new BigDecimal(d.toString());
        //折扣后实际金额
        BigDecimal multiply = actualPrice.multiply(discount);
        if (currentPrice.compareTo(multiply) < 0) {
            returnModel.setData("");
            returnModel.setMsg("剩余储值金额小于实际计费金额，无法修改订单!");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
        if (!"20".equals(orderById.getOrderStatus())) {
            returnModel.setData("");
            returnModel.setMsg("订单不是未付款状态，无法修改订单!");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }

        orderById.setUserId(currentUserByPhone.getCurrentUserId());
        orderById.setCurrentUserPhone(storedPhone);
//        orderById.setActualAmount(multiply.toString());
        orderById.setOrderAmount(multiply.toString());
        orderById.setOrderDiscount(sysCurrentUserAccount.getDiscount());
        orderById.setPaymentMethod("3");
        orderById.setOrderStatus("30");
        orderById.setSubMethod("1");
        orderById.setSubStatus("2");
        orderById.setPaymentPort("1");
        int i = sysOrderService.updateOrder(orderById);

        if (i > 0) {
            //储值金额减折扣后金额
            BigDecimal subtract = currentPrice.subtract(multiply);
            sysCurrentUserAccount.setAccountAmount(subtract.toString());
            int j = sysCurrentUserAccountService.editCurrentUserAccount(sysCurrentUserAccount);
            //储值账户变动日志
            if (j > 0) {

                //将原订单手机号用户欠款状态改为无欠款
                currentUserByPhone1.setCreditArrearsState("10");
                sysCurrentUserService.updateCurrenUser(currentUserByPhone1);

                SysCurrentUserAccountDeduction sysCurrentUserAccountDeduction = new SysCurrentUserAccountDeduction();
                sysCurrentUserAccountDeduction.setDeductionId(IdUtils.getSeqId());
                sysCurrentUserAccountDeduction.setOrderId(orderById.getOrderId());
                sysCurrentUserAccountDeduction.setUserId(currentUserByPhone.getCurrentUserId());
                sysCurrentUserAccountDeduction.setUserPhone(currentUserByPhone.getCurrentUserPhone());
                sysCurrentUserAccountDeduction.setScenicSpotName(orderById.getOrderScenicSpotName());
                sysCurrentUserAccountDeduction.setScenicSpotId(orderById.getOrderScenicSpotId());
                sysCurrentUserAccountDeduction.setOrderNumber(orderById.getOrderNumber());
                sysCurrentUserAccountDeduction.setAccountAmountFront(currentPrice.toString());
                sysCurrentUserAccountDeduction.setDeductionAmount(multiply.toString());
                sysCurrentUserAccountDeduction.setAccountAmountRear(subtract.toString());
                sysCurrentUserAccountDeduction.setAccountType("1");
                sysCurrentUserAccountDeduction.setCreateTime(DateUtil.currentDateTime());
                sysCurrentUserAccountDeduction.setUpdateTime(DateUtil.currentDateTime());
                int add = sysCurrentUserAccountDeductionService.add(sysCurrentUserAccountDeduction);
            }

            SysOrderCurrentLog sysOrderCurrentLog = new SysOrderCurrentLog();
            sysOrderCurrentLog.setId(IdUtils.getSeqId());
            sysOrderCurrentLog.setOrderId(orderById.getOrderId());
            sysOrderCurrentLog.setOrderNumber(orderById.getOrderNumber());
            sysOrderCurrentLog.setFrontPhone(currentUserPhone);
            sysOrderCurrentLog.setAfterPhone(storedPhone);
            sysOrderCurrentLog.setFrontPrice(actualPrice.toString());
            sysOrderCurrentLog.setAfterPrice(multiply.toString());
            sysOrderCurrentLog.setCreateTime(DateUtil.currentDateTime());
            sysOrderCurrentLog.setUpdateTime(DateUtil.currentDateTime());
            sysOrderCurrentLog.setOperatorId(this.getSysUser().getUserId().toString());

            int t = sysOrderCurrentLogService.add(sysOrderCurrentLog);
            returnModel.setData("");
            returnModel.setMsg("修改成功");
            returnModel.setState(Constant.STATE_SUCCESS);
            return returnModel;
        } else {
            returnModel.setData("");
            returnModel.setMsg("修改失败，请联系开发人员处理!");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }

    }

    /**
     * 结算vip订单
     *
     * @param orderId
     * @return
     */
    @RequestMapping("/settlementVipOrder")
    @ResponseBody
    public ReturnModel settlementVipOrder(Long orderId) {

        ReturnModel returnModel = new ReturnModel();

        ReturnModel dataModel = new ReturnModel();
        SysUsers SysUsers = this.getSysUser();
        try {
            SysOrder orderById = sysOrderService.getOrderById(orderId);
            long timeConversionMoney = DateUtil.timeConversionMoney(orderById.getOrderStartTime());// 当前时间和解锁机器人时间比较得到使用总时长（单位分钟）

            orderById.setOrderStatus("30");
            orderById.setOrderEndTime(DateUtil.currentDateTime());
            orderById.setTotalTime(String.valueOf(timeConversionMoney));
            int i = sysOrderService.updateOrder(orderById);

            if (i > 0) {

                SysOrderOperationLog operationLog = new SysOrderOperationLog();
                operationLog.setOperationId(IdUtils.getSeqId());
                operationLog.setOperationName(SysUsers.getUserName());
                operationLog.setOperationNumber(orderById.getOrderNumber());
                operationLog.setOperationPhone(orderById.getCurrentUserPhone());
                operationLog.setOperationScenicSpotName(orderById.getOrderScenicSpotName());
                operationLog.setOperationRobotCode(orderById.getOrderRobotCode());
                operationLog.setOperationTotalTime(String.valueOf(timeConversionMoney));
                operationLog.setOperationFront("结算订单");
                operationLog.setOperationAfter("结算订单");
                operationLog.setCreateDate(DateUtil.currentDateTime());
                sysOrderService.insertOrderOperationLog(operationLog);


                SysRobot robot = sysRobotService.getRobotCodeBy(orderById.getOrderRobotCode());
                dataModel.setData("vip订单后台结算");
                dataModel.setMsg("管理系统推送机器人状态！");
                dataModel.setState(Constant.STATE_SUCCESS);
                dataModel.setType(Constant.ROBOT_IDLE_STATE);
                // 转JSON格式发送到个推
                String robotUnlock = JsonUtils.toString(dataModel);
                String encode = AES.encode(robotUnlock);// 加密推送

                // 个推推送消息到APP端
                // 判断pad是旧版本pad还是重构版pad
                if ("0".equals(robot.getRobotAppType())) {
                    WeChatGtRobotAppPush.singlePush(robot.getRobotCodeCid(), encode, "成功!");
                    returnModel.setData("");
                    returnModel.setMsg("结算成功");
                    returnModel.setState(Constant.STATE_SUCCESS);
                    return returnModel;
                } else {
                    WeChatGtRobotAppPush.singlePushNew(robot.getNewRobotCodeCid(), encode, "成功!");
                    returnModel.setData("");
                    returnModel.setMsg("结算成功");
                    returnModel.setState(Constant.STATE_SUCCESS);
                    return returnModel;
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnModel;

    }


}
