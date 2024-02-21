package com.hna.hka.archive.management.system.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hna.hka.archive.management.appSystem.model.IsItEmpty;
import com.hna.hka.archive.management.appSystem.model.SysAppOrder;
import com.hna.hka.archive.management.assetsSystem.dao.SysScenicSpotOperationRulesMapper;
import com.hna.hka.archive.management.assetsSystem.model.*;
import com.hna.hka.archive.management.managerApp.model.SysAppUsers;
import com.hna.hka.archive.management.system.dao.*;
import com.hna.hka.archive.management.system.model.*;
import com.hna.hka.archive.management.system.service.SysOrderService;
import com.hna.hka.archive.management.system.util.*;
import com.hna.hka.archive.management.wenYuRiverInterface.model.BaseQueryVo;
import com.hna.hka.archive.management.wenYuRiverInterface.model.RealTimeAccess;
import com.hna.hka.archive.management.wenYuRiverInterface.model.RobotUtilizationRate;
import com.hna.hka.archive.management.wenYuRiverInterface.model.WenYuRiverOrder;
import com.itextpdf.text.log.SysoCounter;
import net.sf.json.JSONObject;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.text.ParseException;

import static java.lang.Math.PI;

/**
 * @ProjectName: IDEA-CODE
 * @Package: com.hna.hka.archive.management.system.service.impl
 * @ClassName: SysOrderServiceImpl
 * @Author: 郭凯
 * @Description: 订单管理业务层（实现）
 * @Date: 2020/5/23 15:24
 * @Version: 1.0
 */
@Service
@Transactional
public class SysOrderServiceImpl implements SysOrderService {


    @Autowired
    RedisUtil redisUtil;

    @Autowired
    private SysOrderMapper sysOrderMapper;

    @Autowired
    private SysOrderOperationLogMapper sysOrderOperationLogMapper;

    @Autowired
    private WechatDepositMapper wechatDepositMapper;

    @Autowired
    private SysDepositRefundLogMapper sysDepositRefundLogMapper;

    @Autowired
    private SysCurrentBlackListMapper sysCurrentBlackListMapper;

    @Autowired
    private SysScenicSpotMapper sysScenicSpotMapper;

    @Autowired
    private SysCurrentUserMapper sysCurrentUserMapper;

    @Autowired
    private SysScenicSpotOperationRulesMapper sysScenicSpotOperationRulesMapper;

    @Autowired
    private SysScenicSpotCertificateSpotMapper sysScenicSpotCertificateSpotMapper;

    @Autowired
    private WechatBusinessManagementMapper wechatBusinessManagementMapper;

    @Autowired
    private SysOrderLogMapper sysOrderLogMapper;

    @Autowired
    private SysCurrentUserAccountMapper sysCurrentUserAccountMapper;

    @Autowired
    private SysCurrentUserAccountDeductionMapper sysCurrentUserAccountDeductionMapper;




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

    /**
     * @Author 郭凯
     * @Description 订单管理列表查询
     * @Date 15:26 2020/5/23
     * @Param [pageNum, pageSize, search]
     * @return com.hna.hka.archive.management.system.util.PageDataResult
    **/
    @Override
    public PageDataResult getOrderList(Integer pageNum, Integer pageSize, Map<String,Object> search) throws ParseException {
        SysOrder sysOrder = sysOrderMapper.getOrderAmount(search);
        PageDataResult pageDataResult = new PageDataResult();
        PageHelper.startPage(pageNum, pageSize);
        List<SysOrder> sysOrderList = sysOrderMapper.getOrderList(search);
        for(SysOrder orderListVo:sysOrderList) {
            if (orderListVo.getHuntsState() == null){
                orderListVo.setHuntsState("0");
            }
            if (!"30".equals(orderListVo.getOrderStatus())) {
                orderListVo.setRealIncome(Float.parseFloat("0.00"));
            }
            if("1".equals(orderListVo.getSubStatus())) {
                orderListVo.setOrderAndDeductible("0");
                orderListVo.setActualAmount(orderListVo.getOrderAmount());
            }else {
                orderListVo.setOrderAndDeductible(orderListVo.getOrderAmount());
            }
            if ("2".equals(orderListVo.getPaymentMethod())){
                if (ToolUtil.isNotEmpty(orderListVo.getOrderEndTime()) && ToolUtil.isNotEmpty(orderListVo.getOrderStartTime())){
                    long timeConversionMoney = DateUtil.timeConversion(orderListVo.getOrderStartTime(),orderListVo.getOrderEndTime());
                    orderListVo.setTotalTime(String.valueOf(timeConversionMoney));
                }else{
                    orderListVo.setTotalTime(String.valueOf(0));
                }
            }
            if ("1".equals(orderListVo.getPaymentMethod()) && "2".equals(orderListVo.getSubMethod())){
                if (ToolUtil.isNotEmpty(orderListVo.getActualAmount()) && ToolUtil.isNotEmpty(orderListVo.getOrderDiscount())){
                    BigDecimal actualAmount = new BigDecimal(orderListVo.getActualAmount());
                    BigDecimal orderDiscount = new BigDecimal("0."+orderListVo.getOrderDiscount());
                    BigDecimal orderAndDeductible = actualAmount.multiply(orderDiscount);
                    orderAndDeductible = orderAndDeductible.setScale(2,BigDecimal.ROUND_HALF_UP);
                    orderListVo.setAmountAfterDiscount(orderAndDeductible.toString());
                    BigDecimal realIncome = new BigDecimal(orderListVo.getRealIncome());
                    BigDecimal deductibleAmount = new BigDecimal(Double.parseDouble(orderListVo.getDeductibleAmount()) - Double.parseDouble(orderListVo.getDeductibleRefundAmount()));
                    BigDecimal realIncomes = realIncome.add(deductibleAmount);
                    realIncomes = realIncomes.setScale(2,BigDecimal.ROUND_HALF_UP);
                    orderListVo.setRealIncome(realIncomes.floatValue());
                }
            }
        }
        if (sysOrderList.size() != 0){
            PageInfo<SysOrder> pageInfo = new PageInfo<>(sysOrderList);
            pageDataResult.setList(pageInfo.getList());
            pageDataResult.setTotals((int) pageInfo.getTotal());
            pageDataResult.setRealIncome(sysOrder.getRealIncomes());
            pageDataResult.setPaymentTotalAccount(sysOrder.getPaymentTotalAccount());
        }
        return pageDataResult;
    }

    /**
     * @Author 郭凯
     * @Description 根据ID查询数据
     * @Date 22:08 2020/5/23
     * @Param [orderId]
     * @return com.hna.hka.archive.management.system.model.SysOrder
    **/
    @Override
    public SysOrder getOrderState(Long orderId) {
        return sysOrderMapper.selectByPrimaryKey(orderId);
    }

    /**
     * @Author 郭凯
     * @Description 添加订单状态修改日志
     * @Date 22:28 2020/5/23
     * @Param [operationLog]
     * @return int
    **/
    @Override
    public int insertOrderOperationLog(SysOrderOperationLog operationLog) {
        return sysOrderOperationLogMapper.insertSelective(operationLog);
    }

    /**
     * @Author 郭凯
     * @Description 修改订单状态
     * @Date 22:41 2020/5/23
     * @Param [order]
     * @return int
    **/
    @Override
    public int updateOrderStatus(SysOrder order) {
        return sysOrderMapper.updateByPrimaryKeySelective(order);
    }

    /**
     * @Author 郭凯
     * @Description 查询下载订单列表Excel数据
     * @Date 16:52 2020/5/27
     * @Param [search]
     * @return java.util.List<com.hna.hka.archive.management.system.model.SysOrder>
    **/
    @Override
    public List<SysOrder> getOrderVoExcel(Map<String, Object> search) {
        List<SysOrder> orderExcelList = sysOrderMapper.getOrderList(search);
        for (SysOrder order : orderExcelList){
            order.setTenCentCommission("0.60%");
            if ("30".equals(order.getOrderStatus())){
                BigDecimal orderAmount = new BigDecimal((order.getOrderAmount()==null || order.getOrderAmount().equals(""))?"0.0":order.getOrderAmount());
                BigDecimal orderRefundAmount = new BigDecimal((order.getOrderRefundAmount()==null || order.getOrderRefundAmount().equals(""))?"0":order.getOrderRefundAmount());
                String realIncome = orderAmount.subtract(orderRefundAmount).toString();
                if (ToolUtil.isEmpty(realIncome)){
                    order.setPaymentTotalAccount("0");
                }else{
                    BigDecimal paymentTotalAccount = new BigDecimal((Double.parseDouble(realIncome) * 0.994));//解决小数精度问题
                    paymentTotalAccount = paymentTotalAccount.setScale(2,BigDecimal.ROUND_HALF_UP);
                    order.setPaymentTotalAccount(paymentTotalAccount.toString());
                }
            }else{
                order.setPaymentTotalAccount("0");
                order.setRealIncome((float) 0);
            }
            order.setOrderStatusName(DictUtils.getOrderStateMap().get(order.getOrderStatus()));
            if (ToolUtil.isEmpty(order.getTotalTime())){
                order.setTotalTime("0分钟");
            }else{
                order.setTotalTime(order.getTotalTime()+"分钟");
            }
        }
        return orderExcelList;
    }

    /**
     * @Author 郭凯
     * @Description 查询机器人是否有正在进行中订单
     * @Date 16:01 2020/6/18
     * @Param [robotCode, s]
     * @return com.hna.hka.archive.management.system.model.SysOrder
    **/
    @Override
    public SysOrder getOrderStateByRobotCode(String robotCode, String orderState) {
        Map<String, String> search = new HashMap<>();
        search.put("robotCode",robotCode);
        search.put("orderState",orderState);
        return sysOrderMapper.getOrderStateByRobotCode(search);
    }

    /**
     * @Author 郭凯
     * @Description 修改订单状态和金额
     * @Date 14:41 2020/6/23
     * @Param [orderId, timeConversionMoney, total, dispatchingFee, coupon, s]
     * @return int
    **/
    @Override
    public int updateOrderItemByOrderId(Long orderId, long timeConversionMoney, String total, String dispatchingFee, String coupon, String orderState,String giftTimeSetting) {
        SysOrder sysOrder = new SysOrder();
        sysOrder.setOrderId(orderId);
        sysOrder.setTotalTime(String.valueOf(timeConversionMoney));
        sysOrder.setOrderAmount(total);
        sysOrder.setDispatchingFee(dispatchingFee);
        sysOrder.setCoupon(coupon);
        sysOrder.setGiftTime(giftTimeSetting);
        sysOrder.setOrderStatus(orderState);
        sysOrder.setOrderEndTime(DateUtil.currentDateTime());
        sysOrder.setUpdateDate(DateUtil.currentDateTime());
        return sysOrderMapper.updateByPrimaryKeySelective(sysOrder);
    }

    /**
     *  修改订单状态和金额(修改)
     * @param orderId
     * @param timeConversionMoney
     * @param total
     * @param dispatchingFee
     * @param coupon
     * @param orderState
     * @param giftTimeSetting
     * @return
     */
    @Override
    public int updateOrderItemByOrderIdNew(Long orderId, long timeConversionMoney, String total, String dispatchingFee, String coupon, String orderState,String giftTimeSetting,String parkingName,Long orderParkingId) {
        SysOrder sysOrder = new SysOrder();
        sysOrder.setOrderId(orderId);
        sysOrder.setTotalTime(String.valueOf(timeConversionMoney));
        sysOrder.setOrderAmount(total);
        sysOrder.setDispatchingFee(dispatchingFee);
        sysOrder.setCoupon(coupon);
        sysOrder.setGiftTime(giftTimeSetting);
        sysOrder.setParkingName(parkingName);
        sysOrder.setOrderParkingId(orderParkingId.toString());
        sysOrder.setOrderStatus(orderState);
        sysOrder.setOrderEndTime(DateUtil.currentDateTime());
        sysOrder.setUpdateDate(DateUtil.currentDateTime());
        return sysOrderMapper.updateByPrimaryKeySelective(sysOrder);
    }




    /**
     * @Author 郭凯
     * @Description 根据订单编号查询订单
     * @Date 13:30 2020/8/18
     * @Param [orderNumber]
     * @return com.hna.hka.archive.management.system.model.SysOrder
    **/
    @Override
    public SysOrder getOrderByNumber(String orderNumber) {
        return sysOrderMapper.getOrderByNumber(orderNumber);
    }

    /**
     * @Author 郭凯
     * @Description 修改订单信息
     * @Date 10:39 2020/8/19
     * @Param [order]
     * @return int
     **/
    @Override
    public int updateOrder(SysOrder order) {
        order.setUpdateDate(DateUtil.currentDateTime());
        return sysOrderMapper.updateByPrimaryKeySelective(order);
    }

    /**
     * @Author 郭凯
     * @Description 查询押金订单
     * @Date 10:17 2020/8/20
     * @Param [currentUserId, s]
     * @return com.hna.hka.archive.management.system.model.WechatDeposit
    **/
    @Override
    public WechatDeposit getSysDepositByUserId(Long currentUserId, String depositState) {
        return wechatDepositMapper.getSysDepositByUserId(currentUserId,depositState);
    }

    /**
     * @Author 郭凯
     * @Description 新增押金扣款日志
     * @Date 10:23 2020/8/20
     * @Param [sysDepositRefundLog]
     * @return int
    **/
    @Override
    public int saveSysDepositRefundLog(SysDepositRefundLog sysDepositRefundLog) {
        return sysDepositRefundLogMapper.insertSelective(sysDepositRefundLog);
    }

    /**
     * @Author 郭凯
     * @Description 更新押金订单列表
     * @Date 10:28 2020/8/20
     * @Param [selectSysDepositByUSER_id, depositStatus]
     * @return void
    **/
    @Override
    public int updateDepositStatus(WechatDeposit selectSysDepositByUSER_id, String depositStatus) {
        selectSysDepositByUSER_id.setDepositState(depositStatus);
        return wechatDepositMapper.updateByPrimaryKeySelective(selectSysDepositByUSER_id);
    }

    /**
     * @Author 郭凯
     * @Description 对账订单管理列表查询
     * @Date 16:03 2020/11/4
     * @Param [pageNum, pageSize, search]
     * @return com.hna.hka.archive.management.system.util.PageDataResult
    **/
    @Override
    public PageDataResult getReconciliationOrderList(Integer pageNum, Integer pageSize, Map<String, Object> search) {
        SysOrder sysOrder = sysOrderMapper.getReconciliationOrderAmountList(search);
        PageDataResult pageDataResult = new PageDataResult();
        PageHelper.startPage(pageNum, pageSize);
        List<SysOrder> sysOrderList = sysOrderMapper.getReconciliationOrderList(search);
        for(SysOrder orderListVo:sysOrderList) {
            orderListVo.setCurrentUserPhone(orderListVo.getCurrentUserPhone().replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2"));
            orderListVo.setOrderRobotCode(orderListVo.getOrderRobotCode().replaceAll("(\\d{2})\\d{3}(\\d{2})", "$1***$2"));
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDate date = LocalDate.parse(orderListVo.getOrderStartTime(), formatter);
            Date localDate2Date = DateUtil.localDate2Date(date);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            orderListVo.setOrderStartTime(sdf.format(localDate2Date));
            orderListVo.setTenCentCommission("0.60%");
            BigDecimal deductibleAmount = new BigDecimal(orderListVo.getDeductibleAmount());//解决小数精度问题
            if ("30".equals(orderListVo.getOrderStatus()) && ToolUtil.isNotEmpty(orderListVo.getRealIncome())) {
                BigDecimal b3 = new BigDecimal((orderListVo.getRealIncome() * 0.994));//解决小数精度问题
                BigDecimal deductible = new BigDecimal(String.valueOf(deductibleAmount.multiply(BigDecimal.valueOf(0.994))));//解决小数精度问题
                b3 = b3.add(deductible);
                b3 = b3.setScale(2,BigDecimal.ROUND_HALF_UP);
                orderListVo.setPaymentTotalAccount(b3.toString());
            }else {
                orderListVo.setPaymentTotalAccount("0");
            }
        }
        if (sysOrderList.size() != 0){
            PageInfo<SysOrder> pageInfo = new PageInfo<>(sysOrderList);
            pageDataResult.setList(pageInfo.getList());
            pageDataResult.setTotals((int) pageInfo.getTotal());
            pageDataResult.setRealIncome(String.valueOf(sysOrder.getRealIncome()));
            pageDataResult.setPaymentTotalAccount(sysOrder.getPaymentTotalAccount());
        }
        return pageDataResult;
    }

    /**
     * @Author 郭凯
     * @Description 下载对账订单Excel表
     * @Date 17:31 2020/11/4
     * @Param [sysOrder]
     * @return java.util.List<com.hna.hka.archive.management.system.model.SysOrder>
    **/
    @Override
    public List<UploadOrderExcelBean> getReconciliationOrderVoExcel(Map<String,Object> search) {
        List<UploadOrderExcelBean> reconciliationOrderExcel = sysOrderMapper.getReconciliationOrderExcel(search);
        for(UploadOrderExcelBean orderExcelBean:reconciliationOrderExcel) {
        	orderExcelBean.setOrderStatusName(DictUtils.getOrderStateMap().get(orderExcelBean.getOrderStatus()));
        	orderExcelBean.setCurrentUserPhone(orderExcelBean.getCurrentUserPhone().replaceAll("(\\d{3})\\d{4}(\\d{4})","$1****$2"));
        	orderExcelBean.setOrderRobotCode(orderExcelBean.getOrderRobotCode().replaceAll("(\\d{2})\\d{3}(\\d{2})","$1***$2"));
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDate date = LocalDate.parse(orderExcelBean.getOrderStartTime(), formatter);
            Date localDate2Date = DateUtil.localDate2Date(date);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            orderExcelBean.setOrderStartTime(sdf.format(localDate2Date));
            orderExcelBean.setTenCentCommission("0.60%");
            if (ToolUtil.isNotEmpty(orderExcelBean.getRealIncome()) && "30".equals(orderExcelBean.getOrderStatus())) {
            	orderExcelBean.setRealIncome(orderExcelBean.getRealIncome());
                BigDecimal b3 = new BigDecimal(String.valueOf(BigDecimal.valueOf(orderExcelBean.getRealIncome()).multiply(BigDecimal.valueOf(0.994))));//解决小数精度问题
                b3 = b3.setScale(2,BigDecimal.ROUND_HALF_UP);
                orderExcelBean.setPaymentTotalAccount(b3.toString());
            }else {
            	orderExcelBean.setRealIncome(Float.parseFloat("0"));
            	orderExcelBean.setPaymentTotalAccount("0");
            }
        }
        return reconciliationOrderExcel;
    }

    /**
     * @Author 郭凯
     * @Description 查询管理者APPVIP订单列表页面
     * @Date 17:24 2020/11/23
     * @Param [order, searchstarttime, searchendtime]
     * @return java.util.List<com.hna.hka.archive.management.system.model.SysOrder>
    **/
    @Override
    public List<SysOrder> getAppOrderVIPIndexList(SysOrder order, String searchstarttime, String searchendtime) {
        Map<String,Object> search = new HashMap<>();
        search.put("startTime", searchstarttime);
        search.put("endTime", searchendtime);
        search.put("time", order.getCreateDate());
        search.put("paymentMethod", order.getPaymentMethod());
        List<SysOrder> sysOrderList = sysOrderMapper.getVipOrderList(search);
        return sysOrderList;
    }

    /**
     * @Author 郭凯
     * @Description 客流量管理列表查询
     * @Date 16:20 2020/12/1
     * @Param [pageNum, pageSize, search]
     * @return com.hna.hka.archive.management.system.util.PageDataResult
    **/
    @Override
    public PageDataResult getPassengerFlowList(Integer pageNum, Integer pageSize, Map<String, Object> search) {
        PageDataResult pageDataResult = new PageDataResult();
        PageHelper.startPage(pageNum, pageSize);
        List<SysOrder> sysOrderList = sysOrderMapper.getOrderList(search);
        for(SysOrder orderListVo:sysOrderList) {
            orderListVo.setCurrentUserPhone(orderListVo.getCurrentUserPhone().replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2"));
        }
        if (sysOrderList.size() != 0){
            PageInfo<SysOrder> pageInfo = new PageInfo<>(sysOrderList);
            pageDataResult.setList(pageInfo.getList());
            pageDataResult.setTotals((int) pageInfo.getTotal());
        }
        return pageDataResult;
    }

    /**
     * @Author 郭凯
     * @Description 查询此用户是否有黑名单
     * @Date 16:07 2020/12/10
     * @Param [userId]
     * @return com.hna.hka.archive.management.system.model.SysCurrentBlackList
    **/
    @Override
    public SysCurrentBlackList getBlacklistByUserId(Long userId) {
        return sysCurrentBlackListMapper.getBlacklistByUserId(userId);
    }

    /**
     * @Author 郭凯
     * @Description 添加黑名单
     * @Date 16:16 2020/12/10
     * @Param [userId]
     * @return int
    **/
    @Override
    public int addBlacklist(Long userId) {
        SysCurrentBlackList sysCurrentBlackList = new SysCurrentBlackList();
        sysCurrentBlackList.setBlackListId(IdUtils.getSeqId());
        sysCurrentBlackList.setBlackListType("1");
        sysCurrentBlackList.setCurrentUserId(userId);
        sysCurrentBlackList.setCreateDate(DateUtil.currentDateTime());
        sysCurrentBlackList.setUpdateDate(DateUtil.currentDateTime());
        return sysCurrentBlackListMapper.insertSelective(sysCurrentBlackList);
    }

    /**
     * @Author 郭凯
     * @Description 根据ID查询订单数据
     * @Date 9:57 2020/12/14
     * @Param [orderId]
     * @return com.hna.hka.archive.management.system.model.SysOrder
    **/
    @Override
    public SysOrder getOrderById(Long orderId) {
        return sysOrderMapper.getOrderById(orderId);
    }

	/**
	* @Author 郭凯
	* @Description: 查询景区收入数据
	* @Title: getOrderAmount
	* @date  2020年12月18日 下午2:09:27
	* @param @param scenicSpotId
	* @param @return
	* @throws
	*/
	@Override
	public SysOrder getOrderAmounts(String scenicSpotId) {
		// TODO Auto-generated method stub
		return sysOrderMapper.getOrderAmounts(scenicSpotId);
	}

	/**
	* @Author 郭凯
	* @Description: 查询下载Excel表订单数据
	* @Title: getReconciliationDeductionOrderExcel
	* @date  2021年1月12日 下午3:55:02
	* @param @param search
	* @param @return
	* @throws
	 */
	@Override
	public List<UploadOrderExcelVoBean> getReconciliationDeductionOrderExcel(Map<String, Object> search) {
		// TODO Auto-generated method stub
		List<UploadOrderExcelVoBean> reconciliationOrderExcel = sysOrderMapper.getReconciliationDeductionOrderExcel(search);
        for(UploadOrderExcelVoBean orderExcelBean:reconciliationOrderExcel) {
        	orderExcelBean.setOrderStatusName(DictUtils.getOrderStateMap().get(orderExcelBean.getOrderStatus()));
        	orderExcelBean.setCurrentUserPhone(orderExcelBean.getCurrentUserPhone().replaceAll("(\\d{3})\\d{4}(\\d{4})","$1****$2"));
        	orderExcelBean.setOrderRobotCode(orderExcelBean.getOrderRobotCode().replaceAll("(\\d{2})\\d{3}(\\d{2})","$1***$2"));
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDate date = LocalDate.parse(orderExcelBean.getOrderStartTime(), formatter);
            Date localDate2Date = DateUtil.localDate2Date(date);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            orderExcelBean.setOrderStartTime(sdf.format(localDate2Date));
            orderExcelBean.setTenCentCommission("0.60%");
            BigDecimal deductibleAmount = new BigDecimal(orderExcelBean.getDeductibleAmount());//解决小数精度问题
            if (ToolUtil.isNotEmpty(orderExcelBean.getRealIncome()) && "30".equals(orderExcelBean.getOrderStatus())) {
            	orderExcelBean.setRealIncome(orderExcelBean.getRealIncome());
                BigDecimal b3 = new BigDecimal(String.valueOf(BigDecimal.valueOf(orderExcelBean.getRealIncome()).multiply(BigDecimal.valueOf(0.994))));//解决小数精度问题
                BigDecimal deductible = new BigDecimal(String.valueOf(deductibleAmount.multiply(BigDecimal.valueOf(0.994))));//解决小数精度问题
                b3 = b3.add(deductible);
                b3 = b3.setScale(2,BigDecimal.ROUND_HALF_UP);
                orderExcelBean.setPaymentTotalAccount(b3.toString());
            }else {
            	orderExcelBean.setRealIncome(Float.parseFloat("0"));
            	orderExcelBean.setPaymentTotalAccount("0");
            }
        }
        return reconciliationOrderExcel;
	}

	/**
	* @Author 郭凯
	* @Description: 根据押金编号查询押金记录
	* @Title: getWechatDepositByOutTradeNo
	* @date  2021年1月18日 下午2:57:19
	* @param @param outTradeNo
	* @param @return
	* @throws
	 */
	@Override
	public WechatDeposit getWechatDepositByOutTradeNo(String outTradeNo) {
		// TODO Auto-generated method stub
		return wechatDepositMapper.getWechatDepositByOutTradeNo(outTradeNo);
	}

    /**
     * @Method getRealTimeAccess
     * @Author 郭凯
     * @Version  1.0
     * @Description 实时获取数据
     * @Return com.hna.hka.archive.management.wenYuRiverInterface.model.RealTimeAccess
     * @Date 2021/5/14 10:42
     */
    @Override
    public RealTimeAccess getRealTimeAccess() {
        return sysOrderMapper.getRealTimeAccess();
    }

    /**
     * @Method getTotalRevenue
     * @Author 郭凯
     * @Version  1.0
     * @Description 获取总营收数据
     * @Return com.hna.hka.archive.management.wenYuRiverInterface.model.RealTimeAccess
     * @Date 2021/5/14 13:27
     */
    @Override
    public RealTimeAccess getTotalRevenue(Map<String, Object> search) {
        return sysOrderMapper.getTotalRevenue(search);
    }

    /**
     * @Method getRobotUtilizationRate
     * @Author 郭凯
     * @Version  1.0
     * @Description 机器人使用率
     * @Return java.util.List<com.hna.hka.archive.management.wenYuRiverInterface.model.RobotUtilizationRate>
     * @Date 2021/5/14 15:37
     */
    @Override
    public List<RobotUtilizationRate> getRobotUtilizationRate() {
        return sysOrderMapper.getRobotUtilizationRate();
    }

    /**
     * @Method getWenYuRiverOrderList
     * @Author 郭凯
     * @Version  1.0
     * @Description 温榆河订单列表
     * @Return com.hna.hka.archive.management.system.util.PageDataResult
     * @Date 2021/5/17 17:23
     */
    @Override
    public PageInfo<WenYuRiverOrder> getWenYuRiverOrderList(BaseQueryVo BaseQueryVo) throws ParseException {
        PageHelper.startPage(BaseQueryVo.getPageNum(), BaseQueryVo.getPageSize());
        List<WenYuRiverOrder> sysOrderList = sysOrderMapper.getWenYuRiverOrderList(BaseQueryVo.getSearch());
        for(WenYuRiverOrder orderListVo:sysOrderList) {
            if (!"30".equals(orderListVo.getOrderStatus())) {
                orderListVo.setRealIncome("0.00");
            }
            if("1".equals(orderListVo.getSubStatus())) {
                orderListVo.setOrderAndDeductible("0");
                orderListVo.setActualAmount(orderListVo.getOrderAmount());
            }else {
                orderListVo.setOrderAndDeductible(orderListVo.getOrderAmount());
            }
            if ("2".equals(orderListVo.getPaymentMethod())){
                if (ToolUtil.isNotEmpty(orderListVo.getOrderEndTime()) && ToolUtil.isNotEmpty(orderListVo.getOrderStartTime())){
                    long timeConversionMoney = DateUtil.timeConversion(orderListVo.getOrderStartTime(),orderListVo.getOrderEndTime());
                    orderListVo.setTotalTime(String.valueOf(timeConversionMoney));
                }else{
                    orderListVo.setTotalTime(String.valueOf(0));
                }
            }
            if ("1".equals(orderListVo.getPaymentMethod()) && "2".equals(orderListVo.getSubMethod())){
                if (ToolUtil.isNotEmpty(orderListVo.getActualAmount()) && ToolUtil.isNotEmpty(orderListVo.getOrderDiscount())){
                    BigDecimal actualAmount = new BigDecimal(orderListVo.getActualAmount());
                    BigDecimal orderDiscount = new BigDecimal("0."+orderListVo.getOrderDiscount());
                    BigDecimal orderAndDeductible = actualAmount.multiply(orderDiscount);
                    orderAndDeductible = orderAndDeductible.setScale(2,BigDecimal.ROUND_HALF_UP);
                    orderListVo.setAmountAfterDiscount(orderAndDeductible.toString());
                    BigDecimal realIncome = new BigDecimal(orderListVo.getRealIncome());
                    BigDecimal deductibleAmount = new BigDecimal(orderListVo.getDeductibleAmount());
                    BigDecimal realIncomes = realIncome.add(deductibleAmount);
                    realIncomes = realIncomes.setScale(2,BigDecimal.ROUND_HALF_UP);
                    orderListVo.setRealIncome(realIncomes.toString());
                }
            }
        }
        PageInfo<WenYuRiverOrder> page = new PageInfo<>(sysOrderList);
        return page;
    }

    /**
     * @Method getFinanceOrderList
     * @Author 郭凯
     * @Version  1.0
     * @Description 财务订单查看列表查询
     * @Return com.hna.hka.archive.management.system.util.PageDataResult
     * @Date 2021/5/21 15:49
     */
    @Override
    public PageDataResult getFinanceOrderList(Integer pageNum, Integer pageSize, Map<String, Object> search) throws ParseException {
//        SysOrder sysOrder = sysOrderMapper.getOrderAmount(search);
        SysOrder sysOrder = sysOrderMapper.getOrderAmountNotDeposit(search);
        PageDataResult pageDataResult = new PageDataResult();
        PageHelper.startPage(pageNum, pageSize);
        List<SysOrder> sysOrderList = sysOrderMapper.getFinanceOrderList(search);
        for(SysOrder orderListVo:sysOrderList) {
            if (!"30".equals(orderListVo.getOrderStatus())) {
                orderListVo.setRealIncome(Float.parseFloat("0.00"));
            }
            if("1".equals(orderListVo.getSubStatus())) {
                orderListVo.setOrderAndDeductible("0");
                orderListVo.setActualAmount(orderListVo.getOrderAmount());
            }else {
                orderListVo.setOrderAndDeductible(orderListVo.getOrderAmount());
            }
            if ("2".equals(orderListVo.getPaymentMethod())){
                if (ToolUtil.isNotEmpty(orderListVo.getOrderEndTime()) && ToolUtil.isNotEmpty(orderListVo.getOrderStartTime())){
                    long timeConversionMoney = DateUtil.timeConversion(orderListVo.getOrderStartTime(),orderListVo.getOrderEndTime());
                    orderListVo.setTotalTime(String.valueOf(timeConversionMoney));
                }else{
                    orderListVo.setTotalTime(String.valueOf(0));
                }
            }
            if ("1".equals(orderListVo.getPaymentMethod()) && "2".equals(orderListVo.getSubMethod())){
                if (ToolUtil.isNotEmpty(orderListVo.getActualAmount()) && ToolUtil.isNotEmpty(orderListVo.getOrderDiscount())){
                    BigDecimal actualAmount = new BigDecimal(orderListVo.getActualAmount());
                    BigDecimal orderDiscount = new BigDecimal("0."+orderListVo.getOrderDiscount());
                    BigDecimal orderAndDeductible = actualAmount.multiply(orderDiscount);
                    orderAndDeductible = orderAndDeductible.setScale(2,BigDecimal.ROUND_HALF_UP);
                    orderListVo.setAmountAfterDiscount(orderAndDeductible.toString());
                    BigDecimal realIncome = new BigDecimal(orderListVo.getRealIncome());
                    BigDecimal deductibleAmount = new BigDecimal(orderListVo.getDeductibleAmount());
                    BigDecimal realIncomes = realIncome.add(deductibleAmount);
                    realIncomes = realIncomes.setScale(2,BigDecimal.ROUND_HALF_UP);
                    orderListVo.setRealIncome(realIncomes.floatValue());
                }
            }
        }
        if (sysOrderList.size() != 0){
            PageInfo<SysOrder> pageInfo = new PageInfo<>(sysOrderList);
            pageDataResult.setList(pageInfo.getList());
            pageDataResult.setTotals((int) pageInfo.getTotal());
            pageDataResult.setRealIncome(sysOrder.getRealIncomes());
            pageDataResult.setPaymentTotalAccount(sysOrder.getPaymentTotalAccount());
        }
        return pageDataResult;
    }

    /**
     * @Method getOrderInterfaceList
     * @Author 郭凯
     * @Version  1.0
     * @Description 外部订单接口数据查询
     * @Return com.github.pagehelper.PageInfo<com.hna.hka.archive.management.wenYuRiverInterface.model.WenYuRiverOrder>
     * @Date 2021/5/27 17:10
     */
    @Override
    public PageInfo<WenYuRiverOrder> getOrderInterfaceList(BaseQueryVo baseQueryVo) throws ParseException {
        PageHelper.startPage(baseQueryVo.getPageNum(), baseQueryVo.getPageSize());
        List<WenYuRiverOrder> sysOrderList = sysOrderMapper.getOrderInterfaceList(baseQueryVo.getSearch());
        for(WenYuRiverOrder orderListVo:sysOrderList) {
            if (!"30".equals(orderListVo.getOrderStatus())) {
                orderListVo.setRealIncome("0.00");
            }
            if("1".equals(orderListVo.getSubStatus())) {
                orderListVo.setOrderAndDeductible("0");
                orderListVo.setActualAmount(orderListVo.getOrderAmount());
            }else {
                orderListVo.setOrderAndDeductible(orderListVo.getOrderAmount());
            }
            if ("2".equals(orderListVo.getPaymentMethod())){
                if (ToolUtil.isNotEmpty(orderListVo.getOrderEndTime()) && ToolUtil.isNotEmpty(orderListVo.getOrderStartTime())){
                    long timeConversionMoney = DateUtil.timeConversion(orderListVo.getOrderStartTime(),orderListVo.getOrderEndTime());
                    orderListVo.setTotalTime(String.valueOf(timeConversionMoney));
                }else{
                    orderListVo.setTotalTime(String.valueOf(0));
                }
            }
            if ("1".equals(orderListVo.getPaymentMethod()) && "2".equals(orderListVo.getSubMethod())){
                if (ToolUtil.isNotEmpty(orderListVo.getActualAmount()) && ToolUtil.isNotEmpty(orderListVo.getOrderDiscount())){
                    BigDecimal actualAmount = new BigDecimal(orderListVo.getActualAmount());
                    BigDecimal orderDiscount = new BigDecimal("0."+orderListVo.getOrderDiscount());
                    BigDecimal orderAndDeductible = actualAmount.multiply(orderDiscount);
                    orderAndDeductible = orderAndDeductible.setScale(2,BigDecimal.ROUND_HALF_UP);
                    orderListVo.setAmountAfterDiscount(orderAndDeductible.toString());
                    BigDecimal realIncome = new BigDecimal(orderListVo.getRealIncome());
                    BigDecimal deductibleAmount = new BigDecimal(orderListVo.getDeductibleAmount());
                    BigDecimal realIncomes = realIncome.add(deductibleAmount);
                    realIncomes = realIncomes.setScale(2,BigDecimal.ROUND_HALF_UP);
                    orderListVo.setRealIncome(realIncomes.toString());
                }
            }
        }
        PageInfo<WenYuRiverOrder> page = new PageInfo<>(sysOrderList);
        return page;
    }

    /**
     * @Method getOrderAmountLine
     * @Author 郭凯
     * @Version  1.0
     * @Description 查询订单金额和年月
     * @Return java.util.List<com.hna.hka.archive.management.system.model.SysOrder>
     * @Date 2021/6/1 10:13
     */
    @Override
    public List<OrderAmountLine> getOrderAmountLine() {
        return sysOrderMapper.getOrderAmountLine();
    }

    /**
     * @Method getTradeEcharts
     * @Author 郭凯
     * @Version  1.0
     * @Description 获取最近七天的订单数和金额
     * @Return java.util.List<com.hna.hka.archive.management.assetsSystem.model.TradeEcharts>
     * @Date 2021/6/1 15:18
     */
    @Override
    public List<TradeEcharts> getTradeEcharts() {
        return sysOrderMapper.getTradeEcharts();
    }

    /**
     * @Method trade
     * @Author 郭凯
     * @Version  1.0
     * @Description 查询订单总金额
     * @Return java.util.List<com.hna.hka.archive.management.assetsSystem.model.TradeEcharts>
     * @Date 2021/6/2 9:46
     */
    @Override
    public TradeEcharts getTrade() {
        return sysOrderMapper.getTrade();
    }

    /**
     * @Method getAppOrderList
     * @Author 郭凯
     * @Version  1.0
     * @Description 管理者APP订单管理列表查询
     * @Return com.github.pagehelper.PageInfo<com.hna.hka.archive.management.appSystem.model.SysAppOrder>
     * @Date 2021/6/8 14:40
     */
    @Override
    public PageInfo<SysAppOrder> getAppOrderList(BaseQueryVo baseQueryVo) {
        PageHelper.startPage(baseQueryVo.getPageNum(), baseQueryVo.getPageSize());
        List<SysAppOrder> appOrderList = sysOrderMapper.getAppOrderList(baseQueryVo.getSearch());
        for (SysAppOrder sysAppOrder : appOrderList){
//            SysOrderLog sysOrderLog = sysOrderLogMapper.selectByOrderLogNumber(sysAppOrder.getOrderNumber());
//
//            if (StringUtils.isEmpty(sysOrderLog)){
//                sysAppOrder.setIsDispatchingFee("0");
//            }else {
//                sysAppOrder.setIsDispatchingFee("1");
//            }
            sysAppOrder.setSubStatusName(DictUtils.getSubStatusMap().get(sysAppOrder.getSubStatus()));
            sysAppOrder.setOrderStatusName(DictUtils.getOrderStateMap().get(sysAppOrder.getOrderStatus()));
            sysAppOrder.setPaymentMethodName(DictUtils.getPaymentMethodMap().get(sysAppOrder.getPaymentMethod()));

        }
        PageInfo<SysAppOrder> page = new PageInfo<>(appOrderList);
        return page;
    }

    /**
     * @Method getScenicSpotRankingList
     * @Author 郭凯
     * @Version  1.0
     * @Description 机器人运营排名列表查询
     * @Return java.util.List<com.hna.hka.archive.management.assetsSystem.model.ScenicSpotRanking>
     * @Date 2021/6/17 16:43
     */
    @Override
    public PageDataResult getScenicSpotRankingList(Integer pageNum,Integer pageSize,Map<String, Object> search) throws ParseException {
        PageDataResult pageDataResult = new PageDataResult();
        PageHelper.startPage(pageNum, pageSize);
        if (ToolUtil.isNotEmpty(search.get("startTime"))){
            if ("1".equals(search.get("dataType"))){

            }
            if ("2".equals(search.get("dataType"))){
                search.put("startTime",DateUtil.getYearsDate(search.get("startTime").toString()));
            }
            if ("3".equals(search.get("dataType"))){
                search.put("startTime",DateUtil.getYearDate(search.get("startTime").toString()));
            }
        }
        if (ToolUtil.isNotEmpty(search.get("dataType"))){
            if ("1".equals(search.get("endTime"))){

            }
            if ("2".equals(search.get("dataType"))){
                search.put("endTime",DateUtil.getYearsDate(search.get("endTime").toString()));
            }
            if ("3".equals(search.get("dataType"))){
                search.put("endTime",DateUtil.getYearDate(search.get("endTime").toString()));
            }
        }
        if ("1".equals(search.get("dataType"))){
            search.put("cycle",DateUtil.findDates(search.get("startTime").toString(),search.get("endTime").toString()));
        }
        if ("2".equals(search.get("dataType"))){
            search.put("cycle",DateUtil.findMonths(search.get("startTime").toString(),search.get("endTime").toString()));
        }
        if ("3".equals(search.get("dataType"))){
            search.put("cycle",DateUtil.findYears(search.get("startTime").toString(),search.get("endTime").toString()));
        }
        List<ScenicSpotRanking> scenicSpotRankingList = sysOrderMapper.getScenicSpotRankingList(search);
        if (scenicSpotRankingList.size() != 0){
            PageInfo<ScenicSpotRanking> pageInfo = new PageInfo<>(scenicSpotRankingList);
            pageDataResult.setList(pageInfo.getList());
            pageDataResult.setTotals((int) pageInfo.getTotal());
        }
        return pageDataResult;
    }

    /**
     * @Method getMonthOperateData
     * @Author 郭凯
     * @Version  1.0
     * @Description 获取本月运营数据
     * @Return com.hna.hka.archive.management.system.util.PageDataResult
     * @Date 2021/6/21 14:39
     */
    @Override
    public PageDataResult getMonthOperateData(Integer pageNum, Integer pageSize, Map<String, Object> search) throws ParseException {
        PageDataResult pageDataResult = new PageDataResult();
        PageHelper.startPage(pageNum, pageSize);
        List<OperationData> operationDataList = sysOrderMapper.getMonthOperateData(search);
        if (operationDataList.size() != 0){
            PageInfo<OperationData> pageInfo = new PageInfo<>(operationDataList);
            pageDataResult.setList(pageInfo.getList());
            pageDataResult.setTotals((int) pageInfo.getTotal());
        }
        return pageDataResult;
    }

    /**
     * @Method getScenicSpotRankingExcel
     * @Author 郭凯
     * @Version  1.0
     * @Description 景区排名下载Excel表
     * @Return java.util.List<com.hna.hka.archive.management.assetsSystem.model.ScenicSpotRanking>
     * @Date 2021/7/9 12:52
     */
    @Override
    public List<ScenicSpotRanking> getScenicSpotRankingExcel(Map<String, Object> search) throws ParseException {
        if (ToolUtil.isNotEmpty(search.get("startTime"))){
            if ("1".equals(search.get("dataType"))){

            }
            if ("2".equals(search.get("dataType"))){
                search.put("startTime",DateUtil.getYearsDate(search.get("startTime").toString()));
            }
            if ("3".equals(search.get("dataType"))){
                search.put("startTime",DateUtil.getYearDate(search.get("startTime").toString()));
            }
        }
        if (ToolUtil.isNotEmpty(search.get("dataType"))){
            if ("1".equals(search.get("endTime"))){

            }
            if ("2".equals(search.get("dataType"))){
                search.put("endTime",DateUtil.getYearsDate(search.get("endTime").toString()));
            }
            if ("3".equals(search.get("dataType"))){
                search.put("endTime",DateUtil.getYearDate(search.get("endTime").toString()));
            }
        }
        List<ScenicSpotRanking> scenicSpotRankingList = sysOrderMapper.getWholeCountryScenicSpotRankingList(search);
        if (scenicSpotRankingList.size() != 0){
            scenicSpotRankingList.add(sysOrderMapper.getWholeCountryScenicSpotRankingSum(search));
        }
        return scenicSpotRankingList;
    }

    /**
     * @Method getWholeCountryScenicSpotRankingList
     * @Author 郭凯
     * @Version  1.0
     * @Description 全国景区排名列表查询
     * @Return com.hna.hka.archive.management.system.util.PageDataResult
     * @Date 2021/7/13 22:06
     */
    @Override
    public PageDataResult getWholeCountryScenicSpotRankingList(Integer pageNum, Integer pageSize, Map<String, Object> search) throws ParseException {
        PageDataResult pageDataResult = new PageDataResult();
        PageHelper.startPage(pageNum, pageSize);
        if (ToolUtil.isNotEmpty(search.get("startTime"))){
            if ("1".equals(search.get("dataType"))){

            }
            if ("2".equals(search.get("dataType"))){
                search.put("startTime",DateUtil.getYearsDate(search.get("startTime").toString()));
            }
            if ("3".equals(search.get("dataType"))){
                search.put("startTime",DateUtil.getYearDate(search.get("startTime").toString()));
            }
        }
        if (ToolUtil.isNotEmpty(search.get("dataType"))){
            if ("1".equals(search.get("endTime"))){

            }
            if ("2".equals(search.get("dataType"))){
                search.put("endTime",DateUtil.getYearsDate(search.get("endTime").toString()));
            }
            if ("3".equals(search.get("dataType"))){
                search.put("endTime",DateUtil.getYearDate(search.get("endTime").toString()));
            }
        }
        if ("1".equals(search.get("dataType"))){
            search.put("cycle",DateUtil.findDates(search.get("startTime").toString(),search.get("endTime").toString()));
        }
        if ("2".equals(search.get("dataType"))){
            search.put("cycle",DateUtil.findMonths(search.get("startTime").toString(),search.get("endTime").toString()));
        }
        if ("3".equals(search.get("dataType"))){
            search.put("cycle",DateUtil.findYears(search.get("startTime").toString(),search.get("endTime").toString()));
        }
        List<ScenicSpotRanking> scenicSpotRankingList = sysOrderMapper.getWholeCountryScenicSpotRankingList(search);
        if (scenicSpotRankingList.size() != 0){
            PageInfo<ScenicSpotRanking> pageInfo = new PageInfo<>(scenicSpotRankingList);
            pageInfo.getList().add(sysOrderMapper.getWholeCountryScenicSpotRankingSum(search));
            pageDataResult.setList(pageInfo.getList());
            pageDataResult.setTotals((int) pageInfo.getTotal());
        }
        return pageDataResult;
    }

    /**
     * @Method getWholeCountryScenicSpotRankingExcel
     * @Author 郭凯
     * @Version  1.0
     * @Description 下载全国景区排名excel表
     * @Return java.util.List<com.hna.hka.archive.management.assetsSystem.model.ScenicSpotRanking>
     * @Date 2021/7/13 22:17
     */
    @Override
    public List<ScenicSpotRanking> getWholeCountryScenicSpotRankingExcel(Map<String, Object> search) throws ParseException {
        if (ToolUtil.isNotEmpty(search.get("startTime"))){
            if ("1".equals(search.get("dataType"))){

            }
            if ("2".equals(search.get("dataType"))){
                search.put("startTime",DateUtil.getYearsDate(search.get("startTime").toString()));
            }
            if ("3".equals(search.get("dataType"))){
                search.put("startTime",DateUtil.getYearDate(search.get("startTime").toString()));
            }
        }
        if (ToolUtil.isNotEmpty(search.get("dataType"))){
            if ("1".equals(search.get("endTime"))){

            }
            if ("2".equals(search.get("dataType"))){
                search.put("endTime",DateUtil.getYearsDate(search.get("endTime").toString()));
            }
            if ("3".equals(search.get("dataType"))){
                search.put("endTime",DateUtil.getYearDate(search.get("endTime").toString()));
            }
        }
        List<ScenicSpotRanking> scenicSpotRankingList = sysOrderMapper.getWholeCountryScenicSpotRankingList(search);
        return scenicSpotRankingList;
    }

    @Override
    public List<SysOrderExcel> getOrderVoExcelPoi( Map<String,Object> search) {
        List<SysOrderExcel> orderExcelList = sysOrderMapper.getOrderListPoi(search);
        for (SysOrderExcel order : orderExcelList){
            if (("0").equals(order.getHuntsState())){
                order.setHuntsStateName("未参与");
            }else if (("1").equals(order.getHuntsState())){
                order.setHuntsStateName("已参与");
            }else if (("3").equals(order.getHuntsState())){
                order.setHuntsStateName("已抽奖");
            }else {
                order.setHuntsStateName("未参与");
            }
            order.setTenCentCommission("0.60%");
            if ("30".equals(order.getOrderStatus())){
                BigDecimal orderAmount = new BigDecimal((order.getOrderAmount()==null || order.getOrderAmount().equals(""))?"0.0":order.getOrderAmount());
                BigDecimal orderRefundAmount = new BigDecimal((order.getOrderRefundAmount()==null || order.getOrderRefundAmount().equals(""))?"0":order.getOrderRefundAmount());
                String realIncome = orderAmount.subtract(orderRefundAmount).toString();
                if (ToolUtil.isEmpty(realIncome)){
                    order.setPaymentTotalAccount("0");
                }else{
                    BigDecimal paymentTotalAccount = new BigDecimal((Double.parseDouble(realIncome) * 0.994));//解决小数精度问题
                    paymentTotalAccount = paymentTotalAccount.setScale(2,BigDecimal.ROUND_HALF_UP);
                    order.setPaymentTotalAccount(paymentTotalAccount.toString());
                }
            }else{
                order.setPaymentTotalAccount("0");
                order.setRealIncome((float) 0);
            }
            order.setOrderStatusName(DictUtils.getOrderStateMap().get(order.getOrderStatus()));
            if (ToolUtil.isEmpty(order.getTotalTime())){
                order.setTotalTime("0分钟");
            }else{
                order.setTotalTime(order.getTotalTime()+"分钟");
            }
        }
        return orderExcelList;
    }


    /**
     * 根据订单查询运行轨迹
     * @param sysOrder
     * @return
     */
    @Override
    public List<Map<String, String>> getOrderRunningTrack(Long orderId) {

        SysOrder orderById = sysOrderMapper.getOrderById(orderId);
        List<Map<String, String>> maps = new ArrayList<>();
        String runningTrack = orderById.getRunningTrack();
        if (!StringUtils.isEmpty(runningTrack)){

            Map<String, String> map = new HashMap<>();
            //获取点坐标
            String[] split = runningTrack.split("!");
            for (String s : split) {
                String[] spot = s.split(",");
//                double[] doubles = LngLonUtil.gcj02_To_Bd09(Double.valueOf(spot[0]), Double.valueOf(spot[1]));
//                map  = new HashMap<>();
//                String s1 = String.valueOf(doubles[0]);
//                String s2 = String.valueOf(doubles[1]);
//                map.put(s1,s2);
//                maps.add(map);
                //第三种
                map  = new HashMap<>();
                Double[] doubles = map_tx2bd(Double.valueOf(spot[0]), Double.valueOf(spot[1]));
                map.put(doubles[0].toString(),doubles[1].toString());
                maps.add(map);
            }
        }

        return maps;
    }

    /**
     * 手机app退微信支付调度费
     * @param orderId
     * @return
     */
    @Override
    public int refundOfDispatchingFee(HttpServletRequest request, Long orderId, SysAppUsers sysAppUsers) {
//        SysUsers currentUser = this.getSysUser();
        String weChat_app_id = appid;// 获取微信小程序APPID
        String refund = wechatRefundRequest;// 获取微信支付链接
        String secret = apiSecretkey;// 获取微信小程序唯一密钥32位
        String mch_id = null;// 初始化
        String fileCert_Path = null;// 初始化
        SortedMap<Object, Object> packageP = null;

        SysOrder order = sysOrderMapper.getOrderById(orderId);
        SysScenicSpot sysScenicSpot = sysScenicSpotMapper.selectByPrimaryKey(order.getOrderScenicSpotId());
        //景区调度费
        String scenicSpotBeyondPrice = sysScenicSpot.getScenicSpotBeyondPrice();
        Double spotPrice = Double.parseDouble(scenicSpotBeyondPrice);

        int i = 0;
        try {
            SysUsers currentUser = (SysUsers) SecurityUtils.getSubject().getPrincipal();

            SysCurrentUser currenuser = sysCurrentUserMapper.selectByPrimaryKey(order.getUserId());
            int fee = 0;// 初始化金额
            int refundFee = 0;// 退款金额
            //可退款金额
            Double payMoneys =  Double.parseDouble(order.getOrderAmount())  - Double.parseDouble(order.getOrderRefundAmount());
            String payMoney = null;
            if (payMoneys >= spotPrice){
                 payMoney = scenicSpotBeyondPrice;
            }else{
                sysOrderMapper.modifyRefundStatus(orderId, "0");
                return 0;
            }

//            String payMoney = payMoneys.toString();
            // 转换金额
            //付款金额
            fee = Integer.parseInt(new java.text.DecimalFormat("0").format(Double.parseDouble(order.getOrderAmount()) * 100));
            //退款金额
            refundFee = Integer.parseInt(new java.text.DecimalFormat("0").format(Double.parseDouble(payMoney) * 100));
            // 获取用户openid
            String openid = currenuser.getCurrentOpenId();
            // 获取用户订单编号
            String outTradeNo = order.getOrderNumber();
            // 订单标题
            String body = "退调度费";
            // 时间戳
            final String nonce_str = System.currentTimeMillis() + "";
            // 获取商户ID
            mch_id = mchid;
            // 获取签名证书路径以及文件（此变量为默认变量）
            fileCert_Path = fileCertPath;
            WechatBusinessManagement Management = wechatBusinessManagementMapper.getBusinessManagementByScenicSpotId(order.getOrderScenicSpotId());
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
            if (order.getOrderRefundAmount() != null && order.getOrderRefundAmount().length() != 0 &&  payMoney != null && payMoney.length() != 0){
                order.setOrderRefundAmount(bignum1.add(bignum2).toString());
            }else if (order.getOrderRefundAmount() == null && order.getOrderRefundAmount().length() == 0 &&  payMoney != null && payMoney.length() != 0){
                order.setOrderRefundAmount(payMoney.toString());
            }else if (order.getOrderRefundAmount() != null && order.getOrderRefundAmount().length() != 0 &&  payMoney == null && payMoney.length() == 0){
                order.setOrderRefundAmount(order.getOrderRefundAmount());
            }
            //解析微信返回结果
            if (packageP != null && packageP.get("return_code").toString().equals("SUCCESS")&& packageP.get("result_code").toString().equals("SUCCESS")) {
//                order.setFaultId(Long.parseLong(reason));
                //计算全额退款
                if (order.getOrderRefundAmount() != null && order.getOrderAmount() != null) {
                    Double orderAmount = Double.valueOf(order.getOrderRefundAmount());
                    Double orderRefundAmount = Double.valueOf(order.getOrderAmount());
                    if (String.format("%.2f", orderRefundAmount).equals(String.format("%.2f", orderAmount))) {
                        order.setOrderStatus("60");
                    }
                }
                order.setReasonsRefunds("退调度费");
                order.setIsDispatchingFee("1");
                order.setRefundStatus("0");
                i = this.updateOrder(order);
                //添加退款日志
                sysOrderMapper.modifyRefundStatus(order.getOrderId(),"0");
                SysOrderLog orderLog = new SysOrderLog();
                orderLog.setOrderLogId(IdUtils.getSeqId());
                orderLog.setOrderLogLoginname(sysAppUsers.getLoginName());
                orderLog.setOrderLogUsername(sysAppUsers.getUserName());
                orderLog.setOrderLogPhone(order.getCurrentUserPhone());
                orderLog.setOrderLogNumber(order.getOrderNumber());
                orderLog.setDepositMoney("退调度费金额："+payMoney);
                orderLog.setScenicSpotName(order.getOrderScenicSpotName());
                orderLog.setOrderLogReason("退调度费成功");
                orderLog.setReturnResultCode(packageP.get("result_code").toString());
                orderLog.setCreateDate(DateUtil.currentDateTime());
                orderLog.setUpdateDate(DateUtil.currentDateTime());
                sysOrderLogMapper.insertSelective(orderLog);

            }else{
                //添加退款日志信息
                sysOrderMapper.modifyRefundStatus(order.getOrderId(),"0");
                SysOrderLog orderLog = new SysOrderLog();
                orderLog.setOrderLogId(IdUtils.getSeqId());
                orderLog.setOrderLogLoginname(sysAppUsers.getLoginName());
                orderLog.setOrderLogUsername(sysAppUsers.getUserName());
                orderLog.setOrderLogPhone(order.getCurrentUserPhone());
                orderLog.setOrderLogNumber(order.getOrderNumber());
                orderLog.setDepositMoney("退调度金额："+payMoney);
                orderLog.setScenicSpotName(order.getOrderScenicSpotName());
                orderLog.setOrderLogReason(packageP.get("err_code_des").toString());
                orderLog.setReturnResultCode(packageP.get("result_code").toString());
                orderLog.setCreateDate(DateUtil.currentDateTime());
                orderLog.setUpdateDate(DateUtil.currentDateTime());
                sysOrderLogMapper.insertSelective(orderLog);

            }
        }catch (Exception e){
            e.printStackTrace();

        }

        return i;
    }

    /**
     * 减调度费
     * @param request
     * @param orderId
     * @return
     */
    @Override
    public int reduceOfDispatchingFee(HttpServletRequest request, Long orderId) {
        SysOrderRedis sysOrderRedis = new SysOrderRedis();
        SysOrder selectOrderByPrimaryKey = sysOrderMapper.selectByPrimaryKey( orderId);
        if ("30".equals(selectOrderByPrimaryKey.getOrderStatus())){
            return 2;
        }

       if ("1".equals(selectOrderByPrimaryKey.getIsDispatchingFee())){
           return 3;
       }
        if ("1".equals(selectOrderByPrimaryKey.getSubStatus())){//未提交
            String orderAmount = selectOrderByPrimaryKey.getOrderAmount();
            String dispatchingFee = selectOrderByPrimaryKey.getDispatchingFee();

            selectOrderByPrimaryKey.setDispatchingFee("0");

            int i = sysOrderMapper.updateByPrimaryKeySelective(selectOrderByPrimaryKey);

            if (i>0){
                boolean exists = redisUtil.exists(selectOrderByPrimaryKey.getOrderNumber());
                if (exists){
                    Object object = redisUtil.get(selectOrderByPrimaryKey.getOrderNumber());
                    JSONObject robot = JSONObject.fromObject(object);
                    Object order = JSONObject.toBean(robot, SysOrderRedis.class);
                    JSONObject objJson = JSONObject.fromObject(order);
                    sysOrderRedis = (SysOrderRedis) JSONObject.toBean(objJson,SysOrderRedis.class);
                    String lampClosingTime = sysOrderRedis.getLampClosingTime();
                    String lampLightingTime = sysOrderRedis.getLampLightingTime();
                    String lampOpeningTime = sysOrderRedis.getLampOpeningTime();
                    if ("\"null\"".equals(lampClosingTime)){
                        sysOrderRedis.setLampClosingTime("");
                    }
                    if ("\"null\"".equals(lampLightingTime)){
                        sysOrderRedis.setLampLightingTime("");
                    }
                    if ("\"null\"".equals(lampOpeningTime)){
                        sysOrderRedis.setLampOpeningTime("");
                    }
                    sysOrderRedis.setModifys("1");

                    String s = JsonUtils.toString(sysOrderRedis);
                    boolean set = redisUtil.set(selectOrderByPrimaryKey.getOrderNumber(), s);
                    if (set){
                        System.out.println(set);
                    }
                }
            }
            return i;

        }else{//已提交
            String dispatchingFee = selectOrderByPrimaryKey.getDispatchingFee();
            String actualAmount = selectOrderByPrimaryKey.getActualAmount();
            String orderAmount = selectOrderByPrimaryKey.getOrderAmount();
            //实际计费金额
            BigDecimal bignum0 = new BigDecimal(orderAmount);
            //调度费
            BigDecimal bignum1 = new BigDecimal(dispatchingFee);
            //实际支付金额
            BigDecimal bignum2 = new BigDecimal(actualAmount);
            //去除调度费的实际支付金额
//            String s = bignum2.subtract(bignum1).toString();
            //去除调度费的实际计费金额
            String s1 = bignum0.subtract(bignum1).toString();

//            selectOrderByPrimaryKey.setActualAmount(s);
            selectOrderByPrimaryKey.setOrderAmount(s1);
            selectOrderByPrimaryKey.setDispatchingFee("0");

            int i = sysOrderMapper.updateByPrimaryKeySelective(selectOrderByPrimaryKey);

            if (i>0){
                boolean exists = redisUtil.exists(selectOrderByPrimaryKey.getOrderNumber());
                if (exists){
                    Object object = redisUtil.get(selectOrderByPrimaryKey.getOrderNumber());
                    JSONObject robot = JSONObject.fromObject(object);
                    Object order = JSONObject.toBean(robot, SysOrderRedis.class);
                    JSONObject objJson = JSONObject.fromObject(order);
                    sysOrderRedis = (SysOrderRedis) JSONObject.toBean(objJson,SysOrderRedis.class);
                    String lampClosingTime = sysOrderRedis.getLampClosingTime();
                    String lampLightingTime = sysOrderRedis.getLampLightingTime();
                    String lampOpeningTime = sysOrderRedis.getLampOpeningTime();
                    if ("\"null\"".equals(lampClosingTime)){
                        sysOrderRedis.setLampClosingTime("");
                    }
                    if ("\"null\"".equals(lampLightingTime)){
                        sysOrderRedis.setLampLightingTime("");
                    }
                    if ("\"null\"".equals(lampOpeningTime)){
                        sysOrderRedis.setLampOpeningTime("");
                    }
                    sysOrderRedis.setModifys("1");

                    String s = JsonUtils.toString(sysOrderRedis);
                    boolean set = redisUtil.set(selectOrderByPrimaryKey.getOrderNumber(), s);
                    if (set){
                        System.out.println(set);
                    }
                }

            }
            return i;

        }

    }


    /**
     * 修改订单退款状态
     * @param orderId
     * @param s
     * @return
     */
    @Override
    public int modifyRefundStatus(Long orderId, String type) {

        int i = sysOrderMapper.modifyRefundStatus(orderId,type);


        return i;
    }
    /**
     * 手机app退储值支付调度费
     * @param orderId
     * @return
     */
    @Override
    public int refundOfDispatchingFeeStored(HttpServletRequest request, Long orderId, SysAppUsers appUsers) {

        SysOrder order = sysOrderMapper.selectByPrimaryKey(orderId);

        SysScenicSpot sysScenicSpot = sysScenicSpotMapper.selectByPrimaryKey(order.getOrderScenicSpotId());
        //景区调度费
        String scenicSpotBeyondPrice = sysScenicSpot.getScenicSpotBeyondPrice();
//        Double spotPrice = Double.parseDouble(scenicSpotBeyondPrice);


        //判断是否全额退款
        BigDecimal bignum1 = new BigDecimal(order.getOrderRefundAmount());
        BigDecimal bignum2 = new BigDecimal(scenicSpotBeyondPrice);
        if (order.getOrderRefundAmount() != null && order.getOrderRefundAmount().length() != 0 &&  scenicSpotBeyondPrice != null && scenicSpotBeyondPrice.length() != 0){
            order.setOrderRefundAmount(bignum1.add(bignum2).toString());
        }else if (order.getOrderRefundAmount() == null && order.getOrderRefundAmount().length() == 0 &&  scenicSpotBeyondPrice != null && scenicSpotBeyondPrice.length() != 0){
            order.setOrderRefundAmount(scenicSpotBeyondPrice);
        }else if (order.getOrderRefundAmount() != null && order.getOrderRefundAmount().length() != 0 &&  scenicSpotBeyondPrice == null && scenicSpotBeyondPrice.length() == 0){
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
        order.setIsDispatchingFee("1");
        order.setRefundStatus("0");
        order.setReasonsRefunds("退调度费");
        int i = this.updateOrder(order);
        //解除订单锁定状态
        sysOrderMapper.modifyRefundStatus(order.getOrderId(),"0");
        if (i == 1) {
            SysCurrentUserAccount account = sysCurrentUserAccountMapper.selectAccountByUserId(order.getUserId());
            SysCurrentUserAccount currentUserAccount = new SysCurrentUserAccount();
            currentUserAccount.setAccountId(account.getAccountId());
            Double orderAmount = Double.valueOf(account.getAccountAmount());
            Double orderRefundAmount = Double.valueOf(scenicSpotBeyondPrice);
            BigDecimal bd1 = new BigDecimal(Double.toString(orderAmount));
            BigDecimal bd2 = new BigDecimal(Double.toString(orderRefundAmount));
            double doubleValue = bd1.add(bd2).doubleValue();
            currentUserAccount.setAccountAmount(String.format("%.2f", doubleValue));
            currentUserAccount.setUpdateDate(DateUtil.currentDateTime());
            int j = sysCurrentUserAccountMapper.updateByPrimaryKeySelective(currentUserAccount);
            if (j == 1){
                SysOrderLog orderLog = new SysOrderLog();
                orderLog.setOrderLogId(IdUtils.getSeqId());
                orderLog.setOrderLogLoginname(appUsers.getLoginName());
                orderLog.setOrderLogUsername(appUsers.getUserName());
                orderLog.setOrderLogPhone(order.getCurrentUserPhone());
                orderLog.setOrderLogNumber(order.getOrderNumber());
                orderLog.setDepositMoney("退调度费金额："+scenicSpotBeyondPrice);
                orderLog.setScenicSpotName(order.getOrderScenicSpotName());
                orderLog.setOrderLogReason("退调度费成功");
                orderLog.setReturnResultCode("");
                orderLog.setCreateDate(DateUtil.currentDateTime());
                orderLog.setUpdateDate(DateUtil.currentDateTime());
//                sysOrderLogService.insertOrderLog(orderLog);
                sysOrderLogMapper.insertSelective(orderLog);
            }

        }else{
            sysOrderMapper.modifyRefundStatus(order.getOrderId(),"0");
            SysOrderLog orderLog = new SysOrderLog();
            orderLog.setOrderLogId(IdUtils.getSeqId());
            orderLog.setOrderLogLoginname(appUsers.getLoginName());
            orderLog.setOrderLogUsername(appUsers.getUserName());
            orderLog.setOrderLogPhone(order.getCurrentUserPhone());
            orderLog.setOrderLogNumber(order.getOrderNumber());
            orderLog.setDepositMoney("退款金额："+scenicSpotBeyondPrice);
            orderLog.setScenicSpotName(order.getOrderScenicSpotName());
            orderLog.setOrderLogReason("退款失败");
            orderLog.setReturnResultCode("");
            orderLog.setCreateDate(DateUtil.currentDateTime());
            orderLog.setUpdateDate(DateUtil.currentDateTime());
//            sysOrderLogService.insertOrderLog(orderLog);
            sysOrderLogMapper.insertSelective(orderLog);
        }

        return i;
    }

    /**
     * 管理者app储值抵扣退调度费
     * @param request
     * @param orderId
     * @param appUsers
     * @return
     */
    @Override
    public int storedValueDeductionRefund(HttpServletRequest request, Long orderId, SysAppUsers appUsers) {

        String weChat_app_id = appid;// 获取微信小程序APPID
        String refund = wechatRefundRequest;// 获取微信支付链接
        String secret = apiSecretkey;// 获取微信小程序唯一密钥32位
        String mch_id = null;// 初始化
        String fileCert_Path = null;// 初始化
        SortedMap<Object, Object> packageP = null;
        int i = 0;
        SysOrder order = sysOrderMapper.getOrderById(orderId);
        SysScenicSpot sysScenicSpot = sysScenicSpotMapper.selectByPrimaryKey(order.getOrderScenicSpotId());
        //景区调度费
        String scenicSpotBeyondPrice = sysScenicSpot.getScenicSpotBeyondPrice();
        Double spotPrice = Double.parseDouble(scenicSpotBeyondPrice);

        //用户信息
        SysCurrentUser currenuser = sysCurrentUserMapper.selectByPrimaryKey(order.getUserId());

        String orderAmount1 = order.getOrderAmount();
        String deductibleAmount = order.getDeductibleAmount();
        BigDecimal bignumAmount = new BigDecimal(orderAmount1);
        BigDecimal bignumDedAmount= new BigDecimal(deductibleAmount);
        BigDecimal bignumSpot = new BigDecimal(spotPrice);

        try {
            if (bignumAmount.compareTo(bignumSpot) >= 0) {//微信支付金额大于景区调度费
                //退款金额
                Double payMoneys = Double.parseDouble(order.getOrderAmount()) - Double.parseDouble(order.getOrderRefundAmount());
                String payMoney = null;
                if (payMoneys >= spotPrice) {
                    payMoney = scenicSpotBeyondPrice;
                } else {
                    sysOrderMapper.modifyRefundStatus(orderId, "0");
                    return 0;
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
                WechatBusinessManagement Management = wechatBusinessManagementMapper.getBusinessManagementByScenicSpotId(order.getOrderScenicSpotId());
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
                    order.setIsDispatchingFee("1");
                    order.setRefundStatus("0");
                    order.setReasonsRefunds("退调度费");

                    //计算全额退款
                    if (ToolUtil.isNotEmpty(order.getOrderRefundAmount()) && ToolUtil.isNotEmpty(order.getOrderAmount())) {
                        Double orderRefundAmount = Double.valueOf(order.getOrderRefundAmount());
                        Double orderAmount = Double.valueOf(order.getOrderAmount());
                        if (String.format("%.2f", orderRefundAmount).equals(String.format("%.2f", orderAmount))) {
                            order.setOrderStatus("60");
                        }
                    }
                    i = this.updateOrder(order);
                    //添加退款日志
                    SysOrderLog orderLog = new SysOrderLog();
                    orderLog.setOrderLogId(IdUtils.getSeqId());
                    orderLog.setOrderLogLoginname(appUsers.getLoginName());
                    orderLog.setOrderLogUsername(appUsers.getUserName());
                    orderLog.setOrderLogPhone(order.getCurrentUserPhone());
                    orderLog.setOrderLogNumber(order.getOrderNumber());
                    orderLog.setDepositMoney("退调度费金额：" + payMoney);
                    orderLog.setScenicSpotName(order.getOrderScenicSpotName());
                    orderLog.setOrderLogReason("退调度费成功");
                    orderLog.setReturnResultCode(packageP.get("result_code").toString());
                    orderLog.setCreateDate(DateUtil.currentDateTime());
                    orderLog.setUpdateDate(DateUtil.currentDateTime());
                    sysOrderLogMapper.insertSelective(orderLog);

                } else {
                    //添加退款日志信息
                    SysOrderLog orderLog = new SysOrderLog();
                    orderLog.setOrderLogId(IdUtils.getSeqId());
                    orderLog.setOrderLogLoginname(appUsers.getLoginName());
                    orderLog.setOrderLogUsername(appUsers.getUserName());
                    orderLog.setOrderLogPhone(order.getCurrentUserPhone());
                    orderLog.setOrderLogNumber(order.getOrderNumber());
                    orderLog.setDepositMoney("退调度费金额：" + payMoney);
                    orderLog.setScenicSpotName(order.getOrderScenicSpotName());
                    orderLog.setOrderLogReason(packageP.get("err_code_des").toString());
                    orderLog.setReturnResultCode(packageP.get("result_code").toString());
                    orderLog.setCreateDate(DateUtil.currentDateTime());
                    orderLog.setUpdateDate(DateUtil.currentDateTime());
                    sysOrderLogMapper.insertSelective(orderLog);
                }

            } else {//微信支付金额小于景区调度费

                int j = 0;
                //退款金额
                Double payMoneys = Double.parseDouble(order.getOrderAmount()) - Double.parseDouble(order.getOrderRefundAmount());
                String payMoney = null;

                if (payMoneys > 0){
                    payMoney = payMoneys.toString();

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
                    WechatBusinessManagement Management = wechatBusinessManagementMapper.getBusinessManagementByScenicSpotId(order.getOrderScenicSpotId());
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
                        order.setIsDispatchingFee("1");
                        order.setRefundStatus("0");
                        order.setReasonsRefunds("退调度费");

                        //计算全额退款
                        if (ToolUtil.isNotEmpty(order.getOrderRefundAmount()) && ToolUtil.isNotEmpty(order.getOrderAmount())) {
                            Double orderRefundAmount = Double.valueOf(order.getOrderRefundAmount());
                            Double orderAmount = Double.valueOf(order.getOrderAmount());
                            if (String.format("%.2f", orderRefundAmount).equals(String.format("%.2f", orderAmount))) {
                                order.setOrderStatus("60");
                            }
                        }
//                        i = this.updateOrder(order);

                        //添加退款日志
                        SysOrderLog orderLog = new SysOrderLog();
                        orderLog.setOrderLogId(IdUtils.getSeqId());
                        orderLog.setOrderLogLoginname(appUsers.getLoginName());
                        orderLog.setOrderLogUsername(appUsers.getUserName());
                        orderLog.setOrderLogPhone(order.getCurrentUserPhone());
                        orderLog.setOrderLogNumber(order.getOrderNumber());
                        orderLog.setDepositMoney("退调度费金额："+payMoney);
                        orderLog.setScenicSpotName(order.getOrderScenicSpotName());
                        orderLog.setOrderLogReason("退调度费成功");
                        orderLog.setReturnResultCode(packageP.get("result_code").toString());
                        orderLog.setCreateDate(DateUtil.currentDateTime());
                        orderLog.setUpdateDate(DateUtil.currentDateTime());
                        sysOrderLogMapper.insertSelective(orderLog);
                        j = 1;
                    }else{
                        //添加退款日志信息
                        SysOrderLog orderLog = new SysOrderLog();
                        orderLog.setOrderLogId(IdUtils.getSeqId());
                        orderLog.setOrderLogLoginname(appUsers.getLoginName());
                        orderLog.setOrderLogUsername(appUsers.getUserName());
                        orderLog.setOrderLogPhone(order.getCurrentUserPhone());
                        orderLog.setOrderLogNumber(order.getOrderNumber());
                        orderLog.setDepositMoney("退调度费金额：" + payMoney);
                        orderLog.setScenicSpotName(order.getOrderScenicSpotName());
                        orderLog.setOrderLogReason(packageP.get("err_code_des").toString());
                        orderLog.setReturnResultCode(packageP.get("result_code").toString());
                        orderLog.setCreateDate(DateUtil.currentDateTime());
                        orderLog.setUpdateDate(DateUtil.currentDateTime());
                        sysOrderLogMapper.insertSelective(orderLog);
                        j = 2;
                    }
                }

                if (j != 2){

                    SysCurrentUserAccount account = sysCurrentUserAccountMapper.selectAccountByUserId(currenuser.getCurrentUserId());

                    BigDecimal bignumWECHAT = new BigDecimal(payMoneys);
                    //多扣的储值部分调度费
                    Double subtract = bignumSpot.subtract(bignumWECHAT).doubleValue();

                    SysCurrentUserAccount currentUserAccount = new SysCurrentUserAccount();
                    currentUserAccount.setAccountId(account.getAccountId());
                    Double orderAmount = Double.valueOf(account.getAccountAmount());
                    Double orderRefundAmount = Double.valueOf(subtract);
                    BigDecimal bd1 = new BigDecimal(Double.toString(orderAmount));
                    BigDecimal bd2 = new BigDecimal(Double.toString(orderRefundAmount));
                    double doubleValue = bd1.add(bd2).doubleValue();
                    currentUserAccount.setAccountAmount(String.format("%.2f", doubleValue));
                    currentUserAccount.setUpdateDate(DateUtil.currentDateTime());
                    int t = sysCurrentUserAccountMapper.updateByPrimaryKeySelective(currentUserAccount);
                    if (t == 1) {
                        SysOrderLog orderLog = new SysOrderLog();
                        orderLog.setOrderLogId(IdUtils.getSeqId());
                        orderLog.setOrderLogLoginname(appUsers.getLoginName());
                        orderLog.setOrderLogUsername(appUsers.getUserName());
                        orderLog.setOrderLogPhone(order.getCurrentUserPhone());
                        orderLog.setOrderLogNumber(order.getOrderNumber());
                        orderLog.setDepositMoney("抵扣调度费金额退款：" + payMoney);
                        orderLog.setScenicSpotName(order.getOrderScenicSpotName());
                        orderLog.setOrderLogReason("退调度费成功");
                        orderLog.setReturnResultCode("");
                        orderLog.setCreateDate(DateUtil.currentDateTime());
                        orderLog.setUpdateDate(DateUtil.currentDateTime());
                        sysOrderLogMapper.insertSelective(orderLog);

                        //储值账户记录
                        SysCurrentUserAccountDeduction sysCurrentUserAccountDeduction = new SysCurrentUserAccountDeduction();
                        sysCurrentUserAccountDeduction.setDeductionId(IdUtils.getSeqId());
                        sysCurrentUserAccountDeduction.setScenicSpotId(order.getOrderScenicSpotId());
                        sysCurrentUserAccountDeduction.setScenicSpotName(order.getOrderScenicSpotName());
                        sysCurrentUserAccountDeduction.setOrderId(order.getOrderId());
                        sysCurrentUserAccountDeduction.setOrderNumber(order.getOrderNumber());
                        sysCurrentUserAccountDeduction.setUserPhone(currenuser.getCurrentUserPhone());
                        sysCurrentUserAccountDeduction.setUserId(currenuser.getCurrentUserId());
                        sysCurrentUserAccountDeduction.setAccountAmountFront(bd1.toString());
                        sysCurrentUserAccountDeduction.setDeductionAmount(bd2.toString());
                        sysCurrentUserAccountDeduction.setAccountAmountRear(String.format("%.2f", doubleValue));
                        sysCurrentUserAccountDeduction.setAccountType("2");
                        sysCurrentUserAccountDeduction.setCreateTime(DateUtil.currentDateTime());
                        sysCurrentUserAccountDeduction.setUpdateTime(DateUtil.currentDateTime());
                        sysCurrentUserAccountDeductionMapper.add(sysCurrentUserAccountDeduction);
                    }
                    order.setDeductibleRefundAmount(subtract.toString());
                }


                i = this.updateOrder(order);

            }

        }catch(Exception e){
                e.printStackTrace();
        }

        return i;
    }


    /**
     * 管理者app查询订单详情
     * @param orderNumber
     * @return
     */
    @Override
    public SysAppOrder getAppOrderDetails(String orderNumber) {

        SysAppOrder sysAppOrder = sysOrderMapper.getAppOrderDetails(orderNumber);
        sysAppOrder.setSubStatusName(DictUtils.getSubStatusMap().get(sysAppOrder.getSubStatus()));
        sysAppOrder.setOrderStatusName(DictUtils.getOrderStateMap().get(sysAppOrder.getOrderStatus()));
        sysAppOrder.setPaymentMethodName(DictUtils.getPaymentMethodMap().get(sysAppOrder.getPaymentMethod()));


        return sysAppOrder;

    }


    /**
     * 坐标转换，腾讯地图转换成百度地图坐标 (第三种)
     *
     * @param lat 腾讯纬度
     * @param lon 腾讯经度
     * @return 返回结果：经度,纬度
     */
    public  Double[] map_tx2bd(double lat, double lon) {
        double bd_lat;
        double bd_lon;
        double x_pi = 3.14159265358979324;
        double x = lon, y = lat;
        double z = Math.sqrt(x * x + y * y) + 0.00002 * Math.sin(y * x_pi);
        double theta = Math.atan2(y, x) + 0.000003 * Math.cos(x * x_pi);
        bd_lon = z * Math.cos(theta) + 0.0065;
        bd_lat = z * Math.sin(theta) + 0.006;

        System.out.println("bd_lat:" + bd_lat);
        System.out.println("bd_lon:" + bd_lon);
//        return bd_lon + "," + bd_lat;
        return   new Double[]{bd_lat, bd_lon};
    }


    //测试app 查询订单

    @Override
    public IsItEmpty isItEmpty() {

        IsItEmpty isItEmpty =  sysOrderMapper.isItEmpty();
        return isItEmpty;
    }

    //修改app查询列表状态
    @Override
    public int exitIsItEmpty(String type) {
        int i = sysOrderMapper.exitIsItEmpty(type);
        return i;
    }

    @Override
    public SysOrder getOrderAmount(Map<String, Object> priceSearch) {

        SysOrder sysOrder = sysOrderMapper.getOrderAmount(priceSearch);

        return sysOrder;
    }


}
