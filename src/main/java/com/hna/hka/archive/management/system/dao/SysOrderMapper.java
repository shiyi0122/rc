package com.hna.hka.archive.management.system.dao;

import com.hna.hka.archive.management.appSystem.model.IsItEmpty;
import com.hna.hka.archive.management.appSystem.model.SysAppOrder;
import com.hna.hka.archive.management.assetsSystem.model.*;
import com.hna.hka.archive.management.system.model.SysOrder;
import com.hna.hka.archive.management.system.model.SysOrderExcel;
import com.hna.hka.archive.management.system.model.UploadOrderExcelBean;
import com.hna.hka.archive.management.system.model.UploadOrderExcelVoBean;
import com.hna.hka.archive.management.wenYuRiverInterface.model.RealTimeAccess;
import com.hna.hka.archive.management.wenYuRiverInterface.model.RobotUtilizationRate;
import com.hna.hka.archive.management.wenYuRiverInterface.model.WenYuRiverOrder;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mapper
public interface SysOrderMapper {
    int deleteByPrimaryKey(Long orderId);

    int insert(SysOrder record);

    int insertSelective(SysOrder record);

    SysOrder selectByPrimaryKey(Long orderId);

    int updateByPrimaryKeySelective(SysOrder record);

    int updateByPrimaryKey(SysOrder record);

    List<SysOrder> getOrderList(Map<String,Object> search);

    SysOrder getOrderStateByRobotCode(Map<String, String> search);

    SysOrder getOrderByNumber(@Param("orderNumber") String orderNumber);

    List<UploadOrderExcelBean> getReconciliationOrderExcel(Map<String,Object> search);

    List<SysOrder> getReconciliationOrderList(Map<String, Object> search);

    SysOrder getReconciliationOrderAmountList(Map<String, Object> search);

    SysOrder getOrderAmount(Map<String, Object> search);

    SysOrder getOrderById(Long orderId);

	SysOrder getOrderAmounts(String scenicSpotId);

	List<SysOrder> getVipOrderList(Map<String, Object> search);

	List<UploadOrderExcelVoBean> getReconciliationDeductionOrderExcel(Map<String, Object> search);

    RealTimeAccess getRealTimeAccess();

    RealTimeAccess getTotalRevenue(Map<String, Object> search);

    List<RobotUtilizationRate> getRobotUtilizationRate();

    List<WenYuRiverOrder> getWenYuRiverOrderList(Map<String, String> search);

    List<SysOrder> getFinanceOrderList(Map<String, Object> search);

    List<WenYuRiverOrder> getOrderInterfaceList(Map<String, String> search);

    List<OrderAmountLine> getOrderAmountLine();

    List<TradeEcharts> getTradeEcharts();

    TradeEcharts getTrade();

    List<SysAppOrder> getAppOrderList(Map<String, String> search);

    List<ScenicSpotRanking> getScenicSpotRankingList(Map<String, Object> search);

    List<OperationData> getMonthOperateData(Map<String, Object> search);

    List<OperatingTime> getOperatingTimeList(Map<String, String> search);

    List<OperatingTime> getOperatingTimeListShowQoQ(Map<String, String> search);

    List<OperatingTime> getRobotOperatingTimeListShowQoQ(Map<String, String> search);

    List<OperatingTime> getRobotOperatingTimeList(Map<String, String> search);

    List<OperateStream> getOperateStreamListShowQoQ(Map<String, String> search);

    List<OperateStream> getRobotOperateStreamShowQoQ(Map<String, String> search);

    List<OperateStream> getOperateStreamList(Map<String, String> search);

    List<OperateStream> getRobotOperateStreamList(Map<String, String> search);

    List<ReportForm> getQueryReportShowQoQ(ReportForm reportForm);

    List<ReportForm> getQueryReportStreamShowQoQ(ReportForm reportForm);

    List<ReportForm> getQueryReportNotShowQoQ(ReportForm reportForm);

    List<ReportForm> getQueryReportStreamNotShowQoQ(ReportForm reportForm);

    List<BugStatus> getBugStatusCausesList(Map<String, String> search);

    List<BugStatus> getBugStatusCausesSpotList(Map<String, String> search);

    List<BugStatus> getBugStatusReasonsList(Map<String, String> search);

    List<BugStatus> getBugStatusReasonsSpotList(Map<String, String> search);

    List<ScenicSpotRanking> getWholeCountryScenicSpotRankingList(Map<String, Object> search);

    ScenicSpotRanking getWholeCountryScenicSpotRankingSum(Map<String, Object> search);

    List<SysOrderExcel> getOrderListPoi(Map<String, Object> search);


    //获取机器人当天运行时间
    Integer getRobotCodeByDayTime(String robotCode);
    //获取机器人当月运行时间
    Integer getRobotCodeByMonthTime(String robotCode);

    //获取机器人当月运行时间
    Integer getRobotCodeByYearTime(String robotCode);

    //获取机器人累积运行时长
    Integer getRobotCodeByAccumulateTime(String robotCode);

    List<SysOrder> getRobotIdList(String robotCode);

    Double getSpotIdAndTimeByIncome(Long scenicSpotId, String startTime, String endTime);

    Double getSpotIdAndTimeByOperationDuration(Long scenicSpotId, String startTime, String endTime);

    Double getSpotIdAndTimeByRunningAmount(Long scenicSpotId, String startTime, String endTime);

    Double getSpotIdByRevenue(String scenicSpotId, String startDate);

    Long getOrderRobotCountBySpotId(String startDate, String endDate, String spotId);

    int modifyRefundStatus(Long orderId, String type);

    int getOrderRobotCount(Map<String,Object> search);

    int getOrderSpotRobotCount(HashMap<String, Object> search);

    Double getPaymentOrderFlowingWater(Map<String, Object> search);


    //测试使用
    IsItEmpty isItEmpty();
    //测试使用
    int exitIsItEmpty(String type);

    //根据机器人编号查询当天订单
    SysOrder getCurrentDateOrder(String robotCode,String date);

    int getSpotIdByOrder(Long scenicSpotId, String startDate, String endDate);

    SysOrder getSpotIdFirstOrder(Long scenicSpotId);

    SysOrder getSpotIdLastOrder(Long scenicSpotId);

    Integer getRobotWarehouseYearCount(Long scenicSpotId, String startTime, String endTime);


    Integer getRobotWarehouseMonthCount(Long scenicSpotId, String startTime, String endTime);

    Integer getRobotWarehouseDayCount(Long scenicSpotId, String startTime, String endTime);

    Integer getRobotWarehouseYearCountNew(Long scenicSpotId, String year);

    Integer getRobotWarehouseMonthCountNew(Long scenicSpotId, String month);

    Integer getRobotWarehouseDayCountNew(Long scenicSpotId, String day);

    Integer getRobotWarehouseYearDateCount(Long scenicSpotId, String year);

    Integer getRobotWarehouseMonthDateCount(Long scenicSpotId, String month);

    Integer getRobotWarehouseDayDateCount(Long scenicSpotId, String day);

    Integer getTimeSlotOperateDay(Long scenicSpotId, String startTime, String endTime);

    Integer getTimeSlotOperateRobot(Long scenicSpotId, String startTime, String endTime);


    Integer getSysRobotOrderCount( Map<String, Object> searchN);

    Integer getSysRobotTotalTime( Map<String, Object> searchN);

    /**
     * 根据景区名称和年月，计算收入
     * @param scenicSpotId
     * @param
     * @return
     */
    Double getMonthByMoney(Long scenicSpotId, String data);

    /**
     * 根据景区id，和年月，根据类型计算收入流水
     * @param spotId
     * @param
     * @param
     * @return
     */
    Double getSpotIdAndTimeLikeByFlowingWater(long spotId, String date, Integer type);


    /**
     * 根据景区id，和时间段，根据类型计算收入流水
     * @param spotId
     * @param type
     * @param startTime
     * @param endTime
     * @return
     */
    Double getOrderStatementSummary(@Param("spotId") long spotId,@Param("type") int type,@Param("startTime") String startTime,@Param("endTime") String endTime);

    /**
     * 根据景区id和停放点id以及时间，查询订单数量
     * @param spotId
     * @param parkingId
     * @param date
     * @return
     */
    Integer getOrderNumberBySpotIdAndParkIdAndDate(Long spotId, Long parkingId, String date);


    /**
     * 根据景区id和停放点id以及时间，查询订单金额
     * @param spotId
     * @param parkingId
     * @param date
     * @return
     */
    Double getOrderMoneyBySpotIdAndParkIdAndDate(Long spotId, Long parkingId, String date);

    /**
     * 根据机器人编号和日期，查询日期内最后一条已支付订单
     * @param robotCode
     * @param
     * @return
     */
     List<SysOrder> getRobotAndDateByOrder(@Param("robotCode") String robotCode);

    /**
     * 获取机器人最后一笔订单数据
     * @param robotCode
     * @return
     */
    List<SysOrder> getRobotCodeByOneOrder(String robotCode);


    Integer getJXZOrder(Long scenicSpotId,String date);

    Double getOrderPrice(Long scenicSpotId, String date);


    SysOrder getOrderAmountNotDeposit(Map<String, Object> search);


    SysAppOrder getAppOrderDetails(String orderNumber);


    List<SysOrder> getSpotIdAndStatusByList(Long scenicSpotId,String orderAbnormalTime);

    int getTotle(Map<String, Object> search);
}