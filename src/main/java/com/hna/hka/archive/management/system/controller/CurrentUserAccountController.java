package com.hna.hka.archive.management.system.controller;

import com.hna.hka.archive.management.system.model.*;
import com.hna.hka.archive.management.system.service.SysCurrentUserAccountOrderService;
import com.hna.hka.archive.management.system.service.SysCurrentUserAccountService;
import com.hna.hka.archive.management.system.service.SysCurrentUserService;
import com.hna.hka.archive.management.system.service.SysScenicSpotCertificateSpotService;
import com.hna.hka.archive.management.system.util.*;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

/**
 * @ProjectName: IDEA-CODE
 * @Package: com.hna.hka.archive.management.system.controller
 * @ClassName: CurrentUserAccountController
 * @Author: 郭凯
 * @Description: 储值管理控制层
 * @Date: 2020/6/23 16:26
 * @Version: 1.0
 */
@RequestMapping("/system/currentUserAccount")
@Controller
public class CurrentUserAccountController extends PublicUtil {

    @Autowired
    private SysCurrentUserAccountService sysCurrentUserAccountService;
    @Autowired
    private SysCurrentUserService sysCurrentUserService;
    @Autowired
    private SysCurrentUserAccountOrderService sysCurrentUserAccountOrderService;
    @Autowired
    private SysScenicSpotCertificateSpotService sysScenicSpotCertificateSpotService;


    @Value("${wxpay.fileCertPath}")
    private String fileCertPath; //获取默认文件路径

    @Value("${wxpay.certPath}")
    private String certPath; //获取文件路径


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


    @Autowired
    private HttpServletRequest request;

    /**
     * @Author 郭凯
     * @Description 储值管理列表查询
     * @Date 16:28 2020/6/23
     * @Param [pageNum, pageSize, sysCurrentUserAccount]
     * @return com.hna.hka.archive.management.system.util.PageDataResult
    **/
    @RequestMapping(value = "/getCurrentUserAccountList", method = RequestMethod.GET)
    @ResponseBody
    public PageDataResult getCurrentUserAccountList(@RequestParam("pageNum") Integer pageNum,
                                             @RequestParam("pageSize") Integer pageSize, SysCurrentUserAccount sysCurrentUserAccount) {
        PageDataResult pageDataResult = new PageDataResult();
        Map<String,String> search = new HashMap<>();
        try {
            if(null == pageNum) {
                pageNum = 1;
            }
            if(null == pageSize) {
                pageSize = 10;
            }
            String startTime = request.getParameter("startTime");
            String endTime = request.getParameter("endTime");
            search.put("currentUserPhone",sysCurrentUserAccount.getCurrentUserPhone());
            search.put("startTime",startTime);
            search.put("endTime",endTime);
            pageDataResult = sysCurrentUserAccountService.getCurrentUserAccountList(pageNum,pageSize,search);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("储值管理列表查询异常！", e);
        }
        return pageDataResult;
    }

    /**
     * @Author 郭凯
     * @Description 下载用户储值管理Excel表
     * @Date 13:48 2020/6/24
     * @Param [response, sysCurrentUserAccount]
     * @return void
    **/
    @RequestMapping("/uploadExcelCurrentUserAccount")
    public void uploadExcelCurrentUserAccount(HttpServletResponse response, SysCurrentUserAccount sysCurrentUserAccount) throws Exception {
        List<SysCurrentUserAccount> uploadExcelCurrentUserAccount = null;
        Map<String,String> search = new HashMap<>();
        String startTime = request.getParameter("startTime");
        String endTime = request.getParameter("endTime");
        search.put("startTime",startTime);
        search.put("endTime",endTime);
        search.put("currentUserPhone",sysCurrentUserAccount.getCurrentUserPhone());
        uploadExcelCurrentUserAccount = sysCurrentUserAccountService.getUploadExcelCurrentUserAccount(search);
        String dateTime = DateFormatUtils.format(new Date(), "yyyyMMddHHmm");
        FileUtil.exportExcel(FileUtil.getWorkbook("储值信息", "储值信息", SysCurrentUserAccount.class, uploadExcelCurrentUserAccount),"储值信息"+ dateTime +".xls",response);
    }

    /**
     * @Author 郭凯
     * @Description 修改储值信息
     * @Date 15:13 2020/6/24
     * @Param [sysCurrentUserAccount]
     * @return com.hna.hka.archive.management.system.util.ReturnModel
     **/
    @RequestMapping("/editCurrentUserAccount")
    @ResponseBody
    public ReturnModel editCurrentUserAccount(SysCurrentUserAccount sysCurrentUserAccount){
        ReturnModel returnModel = new ReturnModel();
        try {
            //根据用户ID查询用户储值信息
            SysCurrentUserAccount currentUserAccount = sysCurrentUserAccountService.getSysCurrentUserAccountById(sysCurrentUserAccount.getAccountId());
            String reasonsRefunds = request.getParameter("reasonsRefunds");
            int i = sysCurrentUserAccountService.editCurrentUserAccount(sysCurrentUserAccount);
            if (i == 1){
                SysLogWithBLOBs sysLog = new SysLogWithBLOBs();
                sysLog.setLogId(IdUtils.getSeqId());
                sysLog.setUserName(getSysUser().getUserName());
                sysLog.setLogUserPhone(currentUserAccount.getCurrentUserPhone());
                sysLog.setLogType("1");
                sysLog.setLogAmount("修改前："+currentUserAccount.getAccountAmount() +"元！"+ "修改后：" +sysCurrentUserAccount.getAccountAmount()+"元");
                sysLog.setLogDiscount("修改前："+currentUserAccount.getDiscount() +"折！" + "修改后：" +sysCurrentUserAccount.getDiscount() +"折");
                sysLog.setLogReasonsRefunds(reasonsRefunds);
                sysLog.setCreateDate(DateUtil.currentDateTime());
                //添加操作日志
                sysCurrentUserAccountService.addSysLog(sysLog);
                returnModel.setData("");
                returnModel.setMsg("储值信息修改成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            }else {
                returnModel.setData("");
                returnModel.setMsg("储值信息修改失败！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        }catch (Exception e){
            returnModel.setData("");
            returnModel.setMsg("储值信息修改失败！（请联系管理员）");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    /**
     * @Author 郭凯
     * @Description 储值信息查询
     * @Date 10:50 2020/8/28
     * @Param [accountUserId]
     * @return com.hna.hka.archive.management.system.util.ReturnModel
    **/
    @RequestMapping("/getUserAccountByUserId")
    @ResponseBody
    public ReturnModel getUserAccountByUserId(Long accountUserId){
        ReturnModel returnModel = new ReturnModel();
        try {
            SysCurrentUserAccount sysCurrentUserAccount = sysCurrentUserAccountService.selectAccountByUserId(accountUserId);
            if (ToolUtil.isNotEmpty(sysCurrentUserAccount)){
                returnModel.setData(sysCurrentUserAccount);
                returnModel.setMsg("储值信息查询成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            }else {
                returnModel.setData("");
                returnModel.setMsg("储值信息查询失败（此用户没有储值）！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        }catch (Exception e){
            returnModel.setData("");
            returnModel.setMsg("储值信息查询失败！（请联系管理员）");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    /**
     * @Author 张
     * @Description 储值余额退款
     * @Date
     * @Param
     * @return com.hna.hka.archive.management.system.util.ReturnModel
     **/
    @RequestMapping(value = "/userAccountOrderRefund", method = { RequestMethod.POST })
    @ResponseBody
    public ReturnModel userAccountOrderRefund(HttpServletRequest request,SysCurrentUser sysCurrentUser) throws IOException {
        ReturnModel weChatModel = new ReturnModel();
        SysUsers currentUser = this.getSysUser();
        String weChat_app_id = appid;// 获取微信小程序APPID
        String refund = wechatRefundRequest;// 获取微信支付链接
        String secret = apiSecretkey;// 获取微信小程序唯一密钥32位
        String mch_id = null;// 初始化
        String fileCert_Path = null;// 初始化
        SysCurrentUserAccountOrder order = null;
        SortedMap<Object, Object> packageP = null;
        SortedMap<Object, Object> packageJ = null;

        try {
            SysCurrentUser currentUserN = sysCurrentUserService.getCurrentUserById(sysCurrentUser.getCurrentUserId());
            if (ToolUtil.isEmpty(sysCurrentUser)){
                weChatModel.setData("");
                weChatModel.setMsg("未查询此客户！");
                weChatModel.setState(Constant.STATE_FAILURE);
                return weChatModel;
            }

            //用户的储值信息
            SysCurrentUserAccount sysCurrentUserAccount = sysCurrentUserAccountService.selectAccountByUserId(currentUserN.getCurrentUserId());

            if (Double.parseDouble(sysCurrentUserAccount.getAccountAmount())<=0){
                weChatModel.setData("");
                weChatModel.setMsg("客户储值余额为空！");
                weChatModel.setState(Constant.STATE_FAILURE);
                return weChatModel;
            }

            List<SysCurrentUserAccountOrder> currentUserAccountOrderList= sysCurrentUserAccountOrderService.getCurrentUserAccountOrderListByUserId(currentUserN.getCurrentUserId());
            if (ToolUtil.isEmpty(currentUserAccountOrderList)){
                weChatModel.setData("");
                weChatModel.setMsg("客户无储值记录！");
                weChatModel.setState(Constant.STATE_FAILURE);
                return weChatModel;
            }

            order = currentUserAccountOrderList.get(0);
            SysCurrentUserAccountOrder order1 = currentUserAccountOrderList.get(1);
            if (Double.parseDouble(order.getRechargeAmount()) >= Double.parseDouble(sysCurrentUserAccount.getAccountAmount())){

                //扣款原因
                //String reason = request.getParameter("reason");
                String reasonsRefunds = request.getParameter("reasonsRefunds");
                if (ToolUtil.isEmpty(reasonsRefunds)){
                    weChatModel.setData("");
                    weChatModel.setMsg("请选择退款原因！");
                    weChatModel.setState(Constant.STATE_FAILURE);
                    return weChatModel;
                }
//                if (ToolUtil.isNotEmpty(reasonsRefunds)){
//                    reason = reason+reasonsRefunds;
//                }
                int fee = 0;// 初始化金额
                int refundFee = 0;// 退款金额
                // 转换金额
                fee = Integer.parseInt(new java.text.DecimalFormat("0").format(Double.parseDouble(order.getRechargeAmount()) * 100));
                refundFee = Integer.parseInt(new java.text.DecimalFormat("0").format(Double.parseDouble(sysCurrentUserAccount.getAccountAmount()) * 100));
                // 获取用户openid
                String openid = sysCurrentUser.getCurrentOpenId();
                // 获取用户订单编号
                String outTradeNo = order.getAccountOrderNumber();
                // 订单标题
                String body = "储值余额退款";
                // 时间戳
                final String nonce_str = System.currentTimeMillis() + "";
                // 获取商户ID
                mch_id = mchid;
                // 获取签名证书路径以及文件（此变量为默认变量）
                fileCert_Path = fileCertPath;
                WechatBusinessManagement Management = sysScenicSpotCertificateSpotService.getBusinessManagementByScenicSpotId(order.getScenicSpotId());
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
                BigDecimal bignum1 = new BigDecimal(order.getRechargeAmount());
                BigDecimal bignum2 = new BigDecimal(sysCurrentUserAccount.getAccountAmount());
                if (order.getRechargeAmount() != null && order.getRechargeAmount().length() != 0 &&  sysCurrentUserAccount.getAccountAmount() != null && sysCurrentUserAccount.getAccountAmount().length() != 0){
                        order.setRefundAmount(bignum2.toString());
                        order.setRevenueAmount(bignum1.subtract(bignum2).toString());
                        order.setOrderStart("50");
                }
                //解析微信返回结果
                if (packageP != null && packageP.get("return_code").toString().equals("SUCCESS")&& packageP.get("result_code").toString().equals("SUCCESS")) {
                //    order.setFaultId(Long.parseLong(reason));
                    //计算全额退款
//                    if (order.getOrderRefundAmount() != null && order.getOrderAmount() != null) {
//                        Double orderAmount = Double.valueOf(order.getOrderRefundAmount());
//                        Double orderRefundAmount = Double.valueOf(order.getOrderAmount());
//                        if (String.format("%.2f", orderRefundAmount).equals(String.format("%.2f", orderAmount))) {
//                            order.setOrderStatus("60");
//                        }
//                    }
                    int i = sysCurrentUserAccountOrderService.updateCurrentUserAccountOrder(order);

                    //添加退款日志
//                    SysOrderLog orderLog = new SysOrderLog();
//                    orderLog.setOrderLogId(IdUtils.getSeqId());
//                    orderLog.setOrderLogLoginname(currentUser.getLoginName());
//                    orderLog.setOrderLogUsername(currentUser.getUserName());
//                    orderLog.setOrderLogPhone(order.getCurrentUserPhone());
//                    orderLog.setOrderLogNumber(order.getOrderNumber());
//                    orderLog.setDepositMoney("退款金额："+payMoney);
//                    orderLog.setScenicSpotName(order.getOrderScenicSpotName());
//                    orderLog.setOrderLogReason("退款成功");
//                    orderLog.setReturnResultCode(packageP.get("result_code").toString());
//                    orderLog.setCreateDate(DateUtil.currentDateTime());
//                    orderLog.setUpdateDate(DateUtil.currentDateTime());
//                    sysOrderLogService.insertOrderLog(orderLog);
                    weChatModel.setData("");
                    weChatModel.setMsg("订单退款成功！");
                    weChatModel.setState(Constant.STATE_SUCCESS);
                    return weChatModel;
                }else{
                    //添加退款日志信息
//                    SysOrderLog orderLog = new SysOrderLog();
//                    orderLog.setOrderLogId(IdUtils.getSeqId());
//                    orderLog.setOrderLogLoginname(currentUser.getLoginName());
//                    orderLog.setOrderLogUsername(currentUser.getUserName());
//                    orderLog.setOrderLogPhone(order.getCurrentUserPhone());
//                    orderLog.setOrderLogNumber(order.getOrderNumber());
//                    orderLog.setDepositMoney("退款金额："+payMoney);
//                    orderLog.setScenicSpotName(order.getOrderScenicSpotName());
//                    orderLog.setOrderLogReason(packageP.get("err_code_des").toString());
//                    orderLog.setReturnResultCode(packageP.get("result_code").toString());
//                    orderLog.setCreateDate(DateUtil.currentDateTime());
//                    orderLog.setUpdateDate(DateUtil.currentDateTime());
//                    sysOrderLogService.insertOrderLog(orderLog);
                    weChatModel.setData("");
                    weChatModel.setMsg("订单退款失败！");
                    weChatModel.setState(Constant.STATE_FAILURE);
                    return weChatModel;
                }

            }else{//储值余额大于第一个储值订单
                if ((Double.parseDouble(order.getRechargeAmount())+Double.parseDouble(order1.getRechargeAmount())) >= Double.parseDouble(sysCurrentUserAccount.getAccountAmount())){
                    //扣款原因
                   // String reason = request.getParameter("reason");
                    String reasonsRefunds = request.getParameter("reasonsRefunds");
                    if ( ToolUtil.isEmpty(reasonsRefunds)){
                        weChatModel.setData("");
                        weChatModel.setMsg("请选择扣款原因！");
                        weChatModel.setState(Constant.STATE_FAILURE);
                        return weChatModel;
                    }
//                    if (ToolUtil.isNotEmpty(reasonsRefunds)){
//                        reason = reason+reasonsRefunds;
//                    }
                    int fee = 0;// 初始化金额
                    int refundFee = 0;// 退款金额

                    // 转换金额
                    fee = Integer.parseInt(new java.text.DecimalFormat("0").format(Double.parseDouble(order.getRechargeAmount()) * 100));
                    refundFee = Integer.parseInt(new java.text.DecimalFormat("0").format(Double.parseDouble(order.getRechargeAmount()) * 100));
                    // 获取用户openid
                    String openid = sysCurrentUser.getCurrentOpenId();
                    // 获取用户订单编号
                    String outTradeNo = order.getAccountOrderNumber();
                    // 订单标题
                    String body = "储值余额退款";
                    // 时间戳
                    final String nonce_str = System.currentTimeMillis() + "";
                    // 获取商户ID
                    mch_id = mchid;
                    // 获取签名证书路径以及文件（此变量为默认变量）
                    fileCert_Path = fileCertPath;
                    WechatBusinessManagement Management = sysScenicSpotCertificateSpotService.getBusinessManagementByScenicSpotId(order.getScenicSpotId());
                    if (Management != null) {
                        //覆盖默认商户号
                        mch_id = Management.getMerchantNumber();
                        //覆盖默认路径
                        fileCert_Path = certPath + Management.getCertFileName();
                        //覆盖微信小程序唯一密钥
                        secret = Management.getMerchantSecret();
                    }

                    // 微信商户平台
                    //第一个订单
                    packageP = PayCommonUtil.sendWechatPayBackOrder(weChat_app_id, mch_id, fileCert_Path, secret, nonce_str, body, outTradeNo, fee, refundFee, openid, request.getRemoteAddr(), refund);

                    // 转换金额
                    fee = Integer.parseInt(new java.text.DecimalFormat("0").format(Double.parseDouble(order1.getRechargeAmount()) * 100));
                    refundFee = Integer.parseInt(new java.text.DecimalFormat("0").format((Double.parseDouble(sysCurrentUserAccount.getAccountAmount())-Double.parseDouble(order.getRechargeAmount())) * 100));
                    // 获取用户openid
//                    String openid = sysCurrentUser.getCurrentOpenId();
                    // 获取用户订单编号
                    String outTradeNo1 = order.getAccountOrderNumber();
                    // 订单标题
                     body = "储值余额退款";
                    // 时间戳
                    final String nonce_str1 = System.currentTimeMillis() + "";
                    // 获取商户ID
                    mch_id = mchid;
                    // 获取签名证书路径以及文件（此变量为默认变量）
                    fileCert_Path = fileCertPath;
                     Management = sysScenicSpotCertificateSpotService.getBusinessManagementByScenicSpotId(order.getScenicSpotId());
                    if (Management != null) {
                        //覆盖默认商户号
                        mch_id = Management.getMerchantNumber();
                        //覆盖默认路径
                        fileCert_Path = certPath + Management.getCertFileName();
                        //覆盖微信小程序唯一密钥
                        secret = Management.getMerchantSecret();
                    }

                    //第二个订单
                    packageJ = PayCommonUtil.sendWechatPayBackOrder(weChat_app_id, mch_id, fileCert_Path, secret, nonce_str, body, outTradeNo, fee, refundFee, openid, request.getRemoteAddr(), refund);

                    BigDecimal bignum1 = new BigDecimal(order.getRechargeAmount());
                    BigDecimal bignum2 = new BigDecimal(sysCurrentUserAccount.getAccountAmount());
                    if (order.getRechargeAmount() != null && order.getRechargeAmount().length() != 0 &&  sysCurrentUserAccount.getAccountAmount() != null && sysCurrentUserAccount.getAccountAmount().length() != 0){
                        if (Double.parseDouble(order.getRechargeAmount()) < Double.parseDouble(sysCurrentUserAccount.getAccountAmount())){
                            order.setRefundAmount(order.getRechargeAmount());
                            order.setRevenueAmount("0");
                            order.setOrderStart("50");
                        }
                        Double refundAmount = Double.parseDouble(sysCurrentUserAccount.getAccountAmount()) - (Double.parseDouble(order.getRechargeAmount()));
                        order1.setRefundAmount(refundAmount.toString());
                        Double revenueAmount = Double.parseDouble(order1.getRechargeAmount()) - refundAmount;
                        order1.setRevenueAmount(revenueAmount.toString());
                        order1.setOrderStart("50");

                    }
                    //解析微信返回结果
                    if ((packageP != null && packageP.get("return_code").toString().equals("SUCCESS") ) && (packageP.get("result_code").toString().equals("SUCCESS") && (packageP != null && packageJ.get("return_code").toString().equals("SUCCESS")) && packageJ.get("result_code").toString().equals("SUCCESS"))) {
                        //    order.setFaultId(Long.parseLong(reason));
                        //计算全额退款
//                    if (order.getOrderRefundAmount() != null && order.getOrderAmount() != null) {
//                        Double orderAmount = Double.valueOf(order.getOrderRefundAmount());
//                        Double orderRefundAmount = Double.valueOf(order.getOrderAmount());
//                        if (String.format("%.2f", orderRefundAmount).equals(String.format("%.2f", orderAmount))) {
//                            order.setOrderStatus("60");
//                        }
//                    }
                        int i = sysCurrentUserAccountOrderService.updateCurrentUserAccountOrder(order);
                        int j = sysCurrentUserAccountOrderService.updateCurrentUserAccountOrder(order1);

                        //添加退款日志
//                    SysOrderLog orderLog = new SysOrderLog();
//                    orderLog.setOrderLogId(IdUtils.getSeqId());
//                    orderLog.setOrderLogLoginname(currentUser.getLoginName());
//                    orderLog.setOrderLogUsername(currentUser.getUserName());
//                    orderLog.setOrderLogPhone(order.getCurrentUserPhone());
//                    orderLog.setOrderLogNumber(order.getOrderNumber());
//                    orderLog.setDepositMoney("退款金额："+payMoney);
//                    orderLog.setScenicSpotName(order.getOrderScenicSpotName());
//                    orderLog.setOrderLogReason("退款成功");
//                    orderLog.setReturnResultCode(packageP.get("result_code").toString());
//                    orderLog.setCreateDate(DateUtil.currentDateTime());
//                    orderLog.setUpdateDate(DateUtil.currentDateTime());
//                    sysOrderLogService.insertOrderLog(orderLog);
                        weChatModel.setData("");
                        weChatModel.setMsg("订单退款成功！");
                        weChatModel.setState(Constant.STATE_SUCCESS);
                        return weChatModel;
                    }else{
                        //添加退款日志信息
//                    SysOrderLog orderLog = new SysOrderLog();
//                    orderLog.setOrderLogId(IdUtils.getSeqId());
//                    orderLog.setOrderLogLoginname(currentUser.getLoginName());
//                    orderLog.setOrderLogUsername(currentUser.getUserName());
//                    orderLog.setOrderLogPhone(order.getCurrentUserPhone());
//                    orderLog.setOrderLogNumber(order.getOrderNumber());
//                    orderLog.setDepositMoney("退款金额："+payMoney);
//                    orderLog.setScenicSpotName(order.getOrderScenicSpotName());
//                    orderLog.setOrderLogReason(packageP.get("err_code_des").toString());
//                    orderLog.setReturnResultCode(packageP.get("result_code").toString());
//                    orderLog.setCreateDate(DateUtil.currentDateTime());
//                    orderLog.setUpdateDate(DateUtil.currentDateTime());
//                    sysOrderLogService.insertOrderLog(orderLog);
                        weChatModel.setData("");
                        weChatModel.setMsg("订单退款失败！");
                        weChatModel.setState(Constant.STATE_FAILURE);
                        return weChatModel;
                    }

                }
                weChatModel.setData("");
                weChatModel.setMsg("订单退款失败！");
                weChatModel.setState(Constant.STATE_FAILURE);
                return weChatModel;
            }
        }catch (Exception e){
            weChatModel.setData("");
            weChatModel.setMsg("退储值异常异常！");
            weChatModel.setState(Constant.STATE_FAILURE);
            logger.error("doPayOrderBackDeposit", e);
            return weChatModel;
        }
    }





}
