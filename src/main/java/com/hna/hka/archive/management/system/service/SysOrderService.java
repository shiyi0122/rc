package com.hna.hka.archive.management.system.service;

import com.github.pagehelper.PageInfo;
import com.hna.hka.archive.management.appSystem.model.IsItEmpty;
import com.hna.hka.archive.management.appSystem.model.SysAppOrder;
import com.hna.hka.archive.management.assetsSystem.model.OrderAmountLine;
import com.hna.hka.archive.management.assetsSystem.model.ScenicSpotRanking;
import com.hna.hka.archive.management.assetsSystem.model.SearchRobot;
import com.hna.hka.archive.management.assetsSystem.model.TradeEcharts;
import com.hna.hka.archive.management.managerApp.model.SysAppUsers;
import com.hna.hka.archive.management.system.model.*;
import com.hna.hka.archive.management.system.util.PageDataResult;
import com.hna.hka.archive.management.wenYuRiverInterface.model.BaseQueryVo;
import com.hna.hka.archive.management.wenYuRiverInterface.model.RealTimeAccess;
import com.hna.hka.archive.management.wenYuRiverInterface.model.RobotUtilizationRate;
import com.hna.hka.archive.management.wenYuRiverInterface.model.WenYuRiverOrder;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ProjectName: IDEA-CODE
 * @Package: com.hna.hka.archive.management.system.service
 * @ClassName: SysOrderService
 * @Author: 郭凯
 * @Description: 订单管理业务层（接口）
 * @Date: 2020/5/23 15:24
 * @Version: 1.0
 */
public interface SysOrderService {

    PageDataResult getOrderList(Integer pageNum, Integer pageSize, Map<String,Object> search) throws ParseException;

    SysOrder getOrderState(Long orderId);

    int insertOrderOperationLog(SysOrderOperationLog operationLog);

    int updateOrderStatus(SysOrder order);

    List<SysOrder> getOrderVoExcel(Map<String, Object> search);

    SysOrder getOrderStateByRobotCode(String robotCode, String orderState);

    int updateOrderItemByOrderId(Long orderId, long timeConversionMoney, String total, String dispatchingFee, String coupon, String orderState,String giftTimeSetting);

    SysOrder getOrderByNumber(String orderNumber);

    int updateOrder(SysOrder order);

    WechatDeposit getSysDepositByUserId(Long currentUserId, String depositState);

    int saveSysDepositRefundLog(SysDepositRefundLog sysDepositRefundLog);

    int updateDepositStatus(WechatDeposit selectSysDepositByUSER_id, String depositStatus);

    PageDataResult getReconciliationOrderList(Integer pageNum, Integer pageSize, Map<String, Object> search);

    List<UploadOrderExcelBean> getReconciliationOrderVoExcel(Map<String,Object> search);

    List<SysOrder> getAppOrderVIPIndexList(SysOrder order, String searchstarttime, String searchendtime);

    PageDataResult getPassengerFlowList(Integer pageNum, Integer pageSize, Map<String, Object> search);

    SysCurrentBlackList getBlacklistByUserId(Long userId);

    int addBlacklist(Long userId);

    SysOrder getOrderById(Long orderId);

	SysOrder getOrderAmounts(String scenicSpotId);

	List<UploadOrderExcelVoBean> getReconciliationDeductionOrderExcel(Map<String, Object> search);

	WechatDeposit getWechatDepositByOutTradeNo(String outTradeNo);

    RealTimeAccess getRealTimeAccess();

    RealTimeAccess getTotalRevenue(Map<String, Object> search);

    List<RobotUtilizationRate> getRobotUtilizationRate();

    PageInfo<WenYuRiverOrder> getWenYuRiverOrderList(BaseQueryVo BaseQueryVo) throws ParseException;

    PageDataResult getFinanceOrderList(Integer pageNum, Integer pageSize, Map<String, Object> search) throws ParseException;

    PageInfo<WenYuRiverOrder> getOrderInterfaceList(BaseQueryVo baseQueryVo) throws ParseException;

    List<OrderAmountLine> getOrderAmountLine();

    List<TradeEcharts> getTradeEcharts();

    TradeEcharts getTrade();

    PageInfo<SysAppOrder> getAppOrderList(BaseQueryVo baseQueryVo);

    PageDataResult getScenicSpotRankingList(Integer pageNum,Integer pageSize,Map<String, Object> search) throws ParseException;

    PageDataResult getMonthOperateData(Integer pageNum, Integer pageSize, Map<String, Object> search) throws ParseException;

    List<ScenicSpotRanking> getScenicSpotRankingExcel(Map<String, Object> search) throws ParseException;

    PageDataResult getWholeCountryScenicSpotRankingList(Integer pageNum, Integer pageSize, Map<String, Object> search) throws ParseException;

    List<ScenicSpotRanking> getWholeCountryScenicSpotRankingExcel(Map<String, Object> search) throws ParseException;

    List<SysOrderExcel> getOrderVoExcelPoi(Map<String,Object> search);


    List<Map<String, String>> getOrderRunningTrack(Long  orderId);

    int refundOfDispatchingFee(HttpServletRequest request, Long orderId, SysAppUsers sysAppUsers);

    int reduceOfDispatchingFee(HttpServletRequest request, Long orderId);



    //修改订单退款状态
    int modifyRefundStatus(Long orderId, String type);

    int  refundOfDispatchingFeeStored(HttpServletRequest request, Long orderId, SysAppUsers appUsers);

    IsItEmpty isItEmpty();

    int exitIsItEmpty(String type);

    //后台订单管理中顶部收入数据
    SysOrder getOrderAmount(Map<String, Object> priceSearch);

    int updateOrderItemByOrderIdNew(Long orderId, long timeConversionMoney, String total, String dispatchingFee, String coupon, String orderState,String giftTimeSetting,String parkingName,Long orderParkingId);

    //管理者app储值抵扣退调度费
    int storedValueDeductionRefund(HttpServletRequest request, Long orderId, SysAppUsers appUsers);

    SysAppOrder getAppOrderDetails(String orderNumber);


}
