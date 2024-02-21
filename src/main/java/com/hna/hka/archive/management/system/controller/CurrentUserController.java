package com.hna.hka.archive.management.system.controller;

import com.hna.hka.archive.management.system.model.*;
import com.hna.hka.archive.management.system.service.SysCurrentUserAccountOrderService;
import com.hna.hka.archive.management.system.service.SysCurrentUserAccountService;
import com.hna.hka.archive.management.system.service.SysCurrentUserService;
import com.hna.hka.archive.management.system.service.SysOrderService;
import com.hna.hka.archive.management.system.util.*;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.jdom.JDOMException;
import org.springframework.beans.factory.annotation.Autowired;
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
 * @ClassName: SysCurrentUserController
 * @Author: 郭凯
 * @Description: 客户管理（控制层）
 * @Date: 2020/5/22 14:59
 * @Version: 1.0
 */
@RequestMapping("/system/payback")
@Controller
public class CurrentUserController extends PublicUtil {

    @Autowired
    private SysCurrentUserService sysCurrentUserService;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private SysOrderService sysOrderService;

    @Autowired
    private SysCurrentUserAccountOrderService sysCurrentUserAccountOrderService;

    @Autowired
    private SysCurrentUserAccountService sysCurrentUserAccountService;

    @Autowired
    private AlipayInfo alipayInfo;
    
    @Autowired
    private WeChatInfo weChatInfo;

    /**
     * @Author 郭凯
     * @Description 查询客户列表
     * @Date 15:04 2020/5/22
     * @Param [pageNum, pageSize, sysCurrentUser]
     * @return com.hna.hka.archive.management.system.util.PageDataResult
     **/
    @RequestMapping(value = "/getCurrentUserList", method = RequestMethod.GET)
    @ResponseBody
    public PageDataResult getCurrentUserList(@RequestParam("pageNum") Integer pageNum,
                                             @RequestParam("pageSize") Integer pageSize, SysCurrentUser sysCurrentUser) {
        PageDataResult pageDataResult = new PageDataResult();
        Map<String,Object> search = new HashMap<>();
        try {
            if(null == pageNum) {
                pageNum = 1;
            }
            if(null == pageSize) {
                pageSize = 10;
            }
            String startTime = request.getParameter("startTime");
            String endTime = request.getParameter("endTime");
            search.put("startTime", startTime);
            search.put("endTime", endTime);
            search.put("currentUserPhone", sysCurrentUser.getCurrentUserPhone());
            search.put("depositPayState", sysCurrentUser.getDepositPayState());
            search.put("creditArrearsState", sysCurrentUser.getCreditArrearsState());
            //判断开始时间和结束是否为空，全部满足空的条件才已当前时间为条件查询
            if (ToolUtil.isEmpty(startTime) || ToolUtil.isEmpty(endTime)) {
                if (ToolUtil.isEmpty(sysCurrentUser.getCurrentUserPhone()) && ToolUtil.isEmpty(sysCurrentUser.getDepositPayState()) && ToolUtil.isEmpty(sysCurrentUser.getCreditArrearsState())) {
                    search.put("time", DateUtil.crutDate());
                }
            }
            pageDataResult = sysCurrentUserService.getCurrentUserList(pageNum,pageSize,search);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("客户列表查询异常！", e);
        }
        return pageDataResult;
    }

    /**
     * @Author 郭凯
     * @Description 查询押金缴纳状态
     * @Date 15:21 2020/5/26
     * @Param []
     * @return com.hna.hka.archive.management.system.util.ReturnModel
    **/
    @RequestMapping("/getDepositPayStateName")
    @ResponseBody
    public ReturnModel getDepositPayStateName(){
        ReturnModel returnModel = new ReturnModel();
        try {
            List<SysCurrentUser> sysCurrentUserList = new ArrayList<SysCurrentUser>();
            for(String key : DictUtils.getDepositPayStateMap().keySet()){
                String value = DictUtils.getDepositPayStateMap().get(key);
                SysCurrentUser sysCurrentUser = new SysCurrentUser();
                sysCurrentUser.setDepositPayState(key);
                sysCurrentUser.setDepositPayStateName(value);
                sysCurrentUserList.add(sysCurrentUser);
            }
            returnModel.setData(sysCurrentUserList);
        }catch (Exception e){
            logger.info("getDepositPayStateName",e);
        }
        return returnModel;
    }

    /**
     * @Author 郭凯
     * @Description 根据ID查询回显状态
     * @Date 15:27 2020/5/26
     * @Param [currentUserId]
     * @return com.hna.hka.archive.management.system.util.ReturnModel
    **/
    @RequestMapping("/getDepositPayState")
    @ResponseBody
    public ReturnModel getDepositPayState(@RequestParam("currentUserId") Long currentUserId){
        ReturnModel returnModel = new ReturnModel();
        try {
            SysCurrentUser SysCurrentUser = sysCurrentUserService.getCurrentUserById(currentUserId);
            if (!ToolUtil.isEmpty(SysCurrentUser)){
                returnModel.setData(SysCurrentUser);
                returnModel.setMsg("查询成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            }else {
                returnModel.setData("");
                returnModel.setMsg("查询失败！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        }catch (Exception e){
            returnModel.setData("");
            returnModel.setMsg("查询失败！");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    /**
     * @Author 郭凯
     * @Description 修改押金缴纳状态
     * @Date 15:36 2020/5/26
     * @Param [SysCurrentUser]
     * @return com.hna.hka.archive.management.system.util.ReturnModel
    **/
    @RequestMapping("/updateDepositPayState")
    @ResponseBody
    public ReturnModel updateDepositPayState(SysCurrentUser SysCurrentUser) {
        ReturnModel returnModel = new ReturnModel();
        SysUsers sysUsers = this.getSysUser();
        try {
            if ("10".equals(SysCurrentUser.getDepositPayState())){
                returnModel.setData("");
                returnModel.setMsg("不能修改为已缴纳押金状态！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
            int i = sysCurrentUserService.updateDepositPayState(SysCurrentUser,sysUsers);
            if (i == 1) {
                returnModel.setData("");
                returnModel.setMsg("押金缴纳状态修改成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            } else if(i == 2){
                returnModel.setData("");
                returnModel.setMsg("用户信息获取失败！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }else {
            	returnModel.setData("");
                returnModel.setMsg("押金缴纳状态修改失败！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        } catch (Exception e) {
            returnModel.setData("");
            returnModel.setMsg("押金缴纳状态修改失败！");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    /**
     * @Author 郭凯
     * @Description 押金退款
     * @Date 13:46 2020/8/20
     * @Param [currentUserId]
     * @return com.hna.hka.archive.management.system.util.ReturnModel
    **/
    @RequestMapping("/doPayBackDeposit")
    @ResponseBody
    public ReturnModel doPayBackDeposit(Long currentUserId) throws IOException, JDOMException {
        ReturnModel returnModel = new ReturnModel();
        SysUsers user = this.getSysUser();
        String depositPay = request.getParameter("depositPay");// 获取退款押金金额
        String depositPayAmount = request.getParameter("depositPayAmount");// 获取退款押金总金额
        WechatSysDepositLog chatSysDepositLog = new WechatSysDepositLog();
        try {
            SysCurrentUser currenuser = sysCurrentUserService.getCurrentUserById(currentUserId);
            if (ToolUtil.isEmpty(currenuser)){
                returnModel.setData("");
                returnModel.setMsg("用户信息查询失败！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
            if (currenuser != null && "10".equals(currenuser.getCreditArrearsState())) {//判断是未完成订单
                String depositPayState = currenuser.getDepositPayState();// 获取押金状态
                // 押金缴纳状态: 10 已缴纳 20 未缴纳
                if (ToolUtil.isNotEmpty(depositPayState) && depositPayState.equals("10")) {
                	//支付宝退款
                	if ("2".equals(currenuser.getPaymentChannels())) {
                		WechatDeposit selectSysDepositByUSER_ID = sysOrderService.getSysDepositByUserId(currenuser.getCurrentUserId(), "30");
                		if (ToolUtil.isNotEmpty(selectSysDepositByUSER_ID)) {
                			SortedMap<String, String> packageP = PayCommonUtil.alipayRefund(alipayInfo,selectSysDepositByUSER_ID.getTradeNo(),depositPay);
                			if(packageP != null && "Y".equals(packageP.get("FundChange")) && "10000".equals(packageP.get("code"))) {
                				chatSysDepositLog.setDepositLogId(IdUtils.getSeqId());
	                            chatSysDepositLog.setDepositId(selectSysDepositByUSER_ID.getDepositId());
	                            chatSysDepositLog.setDepositPhone(currenuser.getCurrentUserPhone());
	                            chatSysDepositLog.setDepositMoney(selectSysDepositByUSER_ID.getDepositMoney());
	                            chatSysDepositLog.setOutTradeNo(selectSysDepositByUSER_ID.getOutTradeNo());
	                            chatSysDepositLog.setRefundClient(user.getLoginName());
	                            chatSysDepositLog.setReturnResultCode(packageP.get("msg"));
	                            chatSysDepositLog.setCreateDate(DateUtil.currentDateTime());
	                            chatSysDepositLog.setUpdateDate(DateUtil.currentDateTime());
	                            sysCurrentUserService.saveDeposLog(chatSysDepositLog);// 插入押金日志
	                            sysOrderService.updateDepositStatus(selectSysDepositByUSER_ID, "60");//更新押金订单列表 60为已退款状态
	                            // 60为已退款状态
	                            // 押金缴纳状态: 10 已缴纳 20 未缴纳
	                            currenuser.setDepositPayState("20");
	                            currenuser.setReturnDepositPayTime(DateUtil.currentDateTime());
	                            currenuser.setScenicSpotId("");
	                            sysCurrentUserService.updateCurrenUser(currenuser);
	                            returnModel.setData("");
	                            returnModel.setMsg("押金退款成功！");
	                            returnModel.setState(Constant.STATE_SUCCESS);
	                            return returnModel;
                			}else {
                				chatSysDepositLog.setDepositLogId(IdUtils.getSeqId());
	                            chatSysDepositLog.setDepositId(selectSysDepositByUSER_ID.getDepositId());
	                            chatSysDepositLog.setDepositPhone(currenuser.getCurrentUserPhone());
	                            chatSysDepositLog.setDepositMoney(selectSysDepositByUSER_ID.getDepositMoney());
	                            chatSysDepositLog.setOutTradeNo(selectSysDepositByUSER_ID.getOutTradeNo());
	                            chatSysDepositLog.setRefundClient(user.getLoginName());
	                            chatSysDepositLog.setReturnResultCode(packageP.get("subMsg"));
	                            chatSysDepositLog.setCreateDate(DateUtil.currentDateTime());
	                            chatSysDepositLog.setUpdateDate(DateUtil.currentDateTime());
	                            sysCurrentUserService.saveDeposLog(chatSysDepositLog);// 插入押金日志
	                            returnModel.setData("");
	                            returnModel.setMsg("押金退款失败,请与管理员联系！失败原因："+packageP.get("subMsg"));
	                            returnModel.setState(Constant.STATE_FAILURE);
	                            return returnModel;
                			}
                		}else {
                			returnModel.setData("");
	                        returnModel.setMsg("未查询到押金订单，请确认是否交押金！");
	                        returnModel.setState(Constant.STATE_FAILURE);
	                        return returnModel;
                		}
					}else if("1".equals(currenuser.getPaymentChannels())) {//微信退款
	                    int total_fee = 0;// 初始化总金额
	                    int payback_fee = 0;// 初始化退款金额
	                    //查询押金订单
	                    WechatDeposit selectSysDepositByUSER_ID = sysOrderService.getSysDepositByUserId(currenuser.getCurrentUserId(), "30");
	                    if (ToolUtil.isNotEmpty(selectSysDepositByUSER_ID)) {
	                        // 转换金额
	                        total_fee = Integer.parseInt(new java.text.DecimalFormat("0").format(Double.parseDouble(depositPayAmount) * 100));
	                        payback_fee = Integer.parseInt(new java.text.DecimalFormat("0").format(Double.parseDouble(depositPay) * 100));
	                        // 获取用户openId
	                        String openid = currenuser.getCurrentOpenId();
	                        // 获取用户订单编号
	                        String outTradeNo = selectSysDepositByUSER_ID.getOutTradeNo();
	                        // 微信商户平台
	                        SortedMap<Object, Object> packageP = PayCommonUtil.weChatRefund(weChatInfo,openid,outTradeNo,total_fee,payback_fee);
	                        // 将packageP数据返回给小程序
	                        if (packageP != null && packageP.get("return_code").toString().equals("SUCCESS") && packageP.get("result_code").toString().equals("SUCCESS")) {
	                            chatSysDepositLog.setDepositLogId(IdUtils.getSeqId());
	                            chatSysDepositLog.setDepositId(selectSysDepositByUSER_ID.getDepositId());
	                            chatSysDepositLog.setDepositPhone(currenuser.getCurrentUserPhone());
	                            chatSysDepositLog.setDepositMoney(selectSysDepositByUSER_ID.getDepositMoney());
	                            chatSysDepositLog.setOutTradeNo(selectSysDepositByUSER_ID.getOutTradeNo());
	                            chatSysDepositLog.setRefundClient(user.getLoginName());
	                            chatSysDepositLog.setReturnResultCode((String) packageP.get("return_code"));
	                            chatSysDepositLog.setCreateDate(DateUtil.currentDateTime());
	                            chatSysDepositLog.setUpdateDate(DateUtil.currentDateTime());
	                            sysCurrentUserService.saveDeposLog(chatSysDepositLog);// 插入押金日志
	                            sysOrderService.updateDepositStatus(selectSysDepositByUSER_ID, "60");//更新押金订单列表 60为已退款状态
	                            // 60为已退款状态
	                            // 押金缴纳状态: 10 已缴纳 20 未缴纳
	                            currenuser.setDepositPayState("20");
	                            currenuser.setReturnDepositPayTime(DateUtil.currentDateTime());
	                            currenuser.setScenicSpotId("");
	                            sysCurrentUserService.updateCurrenUser(currenuser);
	                            returnModel.setData("");
	                            returnModel.setMsg("押金退款成功！");
	                            returnModel.setState(Constant.STATE_SUCCESS);
	                            return returnModel;
	                        }else {
	                            chatSysDepositLog.setDepositLogId(IdUtils.getSeqId());
	                            chatSysDepositLog.setDepositId(selectSysDepositByUSER_ID.getDepositId());
	                            chatSysDepositLog.setDepositPhone(currenuser.getCurrentUserPhone());
	                            chatSysDepositLog.setDepositMoney(selectSysDepositByUSER_ID.getDepositMoney());
	                            chatSysDepositLog.setOutTradeNo(selectSysDepositByUSER_ID.getOutTradeNo());
	                            chatSysDepositLog.setRefundClient(user.getLoginName());
	                            chatSysDepositLog.setReturnResultCode(packageP.get("err_code_des").toString());
	                            chatSysDepositLog.setCreateDate(DateUtil.currentDateTime());
	                            chatSysDepositLog.setUpdateDate(DateUtil.currentDateTime());
	                            sysCurrentUserService.saveDeposLog(chatSysDepositLog);// 插入押金日志
	                            returnModel.setData("");
	                            returnModel.setMsg("押金退款失败,请与管理员联系！失败原因："+packageP.get("err_code_des").toString());
	                            returnModel.setState(Constant.STATE_FAILURE);
	                            return returnModel;
	                        }
	                    }else{
	                        returnModel.setData("");
	                        returnModel.setMsg("未查询到押金订单，请确认是否交押金！");
	                        returnModel.setState(Constant.STATE_FAILURE);
	                        return returnModel;
	                    }
					}else {
						returnModel.setData("");
	                    returnModel.setMsg("用户未缴纳押金，无法退款！");
	                    returnModel.setState(Constant.STATE_FAILURE);
	                    return returnModel;
					}
                }else {
                    returnModel.setData("");
                    returnModel.setMsg("用户未缴纳押金，无法退款！");
                    returnModel.setState(Constant.STATE_FAILURE);
                    return returnModel;
                }
            }else{
                returnModel.setData("");
                returnModel.setMsg("用户有未完成订单！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        }catch (Exception e){
            logger.info("doPayBackDeposit",e);
            returnModel.setData("");
            returnModel.setMsg("doPayBackDeposit退押金异常异常！");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    /**
     * @Author 郭凯
     * @Description 下载客户Excel表
     * @Date 11:39 2020/9/2
     * @Param [response, sysCurrentUser]
     * @return void
    **/
    @RequestMapping(value = "/uploadExcelCurrentUser")
    public void  uploadExcelCurrentUser(HttpServletResponse response, SysCurrentUser sysCurrentUser) throws Exception {
        List<SysCurrentUser> currentUserListByExample = null;
        Map<String,Object> search = new HashMap<>();
        search.put("currentUserPhone",sysCurrentUser.getCurrentUserPhone());
        search.put("depositPayState",sysCurrentUser.getDepositPayState());
        search.put("creditArrearsState",sysCurrentUser.getCreditArrearsState());
        String startTime = request.getParameter("startTime");//获取开始时间
        String endTime = request.getParameter("endTime");//获取结束时间
        search.put("startTime",startTime);
        search.put("endTime",endTime);
        //判断开始时间和结束是否为空，全部满足空的条件才已当前时间为条件查询
        if (ToolUtil.isEmpty(startTime) || ToolUtil.isEmpty(endTime)) {
            if (ToolUtil.isEmpty(sysCurrentUser.getCurrentUserPhone()) && ToolUtil.isEmpty(sysCurrentUser.getDepositPayState()) && ToolUtil.isEmpty(sysCurrentUser.getCreditArrearsState())) {
                search.put("time", DateUtil.crutDate());
            }
        }
        currentUserListByExample = sysCurrentUserService.getCurrentUserExcel(search);
        String dateTime = DateFormatUtils.format(new Date(), "yyyyMMddHHmm");
        FileUtil.exportExcel(FileUtil.getWorkbook("客户管理", "客户管理", SysCurrentUser.class, currentUserListByExample),"客户管理"+ dateTime +".xls",response);
    }

    /**
     * @Author 郭凯
     * @Description 设置白名单
     * @Date 9:08 2020/12/11
     * @Param [sysOrder]
     * @return com.hna.hka.archive.management.system.util.ReturnModel
    **/
    @RequestMapping("/setWhitelist")
    @ResponseBody
    public ReturnModel setWhitelist(SysOrder sysOrder) {
        ReturnModel dataModel = new ReturnModel();
        try {
            SysCurrentBlackList currenUser = sysOrderService.getBlacklistByUserId(sysOrder.getUserId());
            if (ToolUtil.isEmpty(currenUser)) {
                dataModel.setData("");
                dataModel.setMsg("此用户已经是白名单成员，请勿重复添加！");
                dataModel.setState(Constant.STATE_FAILURE);
                return dataModel;
            }
            int i = sysCurrentUserService.delBlacklist(currenUser.getBlackListId());
            if (i == 1) {
                dataModel.setData("");
                dataModel.setMsg("设置白名单成功！");
                dataModel.setState(Constant.STATE_SUCCESS);
                return dataModel;
            }else {
                dataModel.setData("");
                dataModel.setMsg("设置白名单失败！");
                dataModel.setState(Constant.STATE_FAILURE);
                return dataModel;
            }
        } catch (Exception e) {
        	logger.info("setWhitelist", e);
            dataModel.setData("");
            dataModel.setMsg("设置白名单失败！(请联系管理员)");
            dataModel.setState(Constant.STATE_FAILURE);
            return dataModel;
        }
    }


}
