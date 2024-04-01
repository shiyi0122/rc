package com.hna.hka.archive.management.system.model;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;

@Data
public class SysOrder extends BaseRowModel {
    private Long orderId;

    private Long userId;

    @Excel(name = "订单计费", width = 20, orderNum = "11")
    private String orderAmount;

    @Excel(name = "实际计费金额", width = 20, orderNum = "6")
    private String actualAmount;

    @Excel(name = "折扣", width = 10, orderNum = "7")
    private String orderDiscount;

    private String paymentMethod;

    private String orderStatus;

    private Long orderScenicSpotId;

    @Excel(name = "景区名称", width = 30, orderNum = "3")
    private String orderScenicSpotName;

    @Excel(name = "订单编号", width = 40, orderNum = "0")
    private String orderNumber;

    @Excel(name = "订单开始时间", width = 30, orderNum = "4")
    private String orderStartTime;

    @Excel(name = "订单结束时间", width = 30, orderNum = "5")
    private String orderEndTime;

    @Excel(name = "机器人编号", width = 20, orderNum = "2")
    private String orderRobotCode;

    @Excel(name = "调度费", width = 10, orderNum = "9")
    private String dispatchingFee;

    @Excel(name = "优惠劵", width = 20, orderNum = "8")
    private String coupon;

    @Excel(name = "退款原因", width = 20, orderNum = "17")
    private String reasonsRefunds;

    private String runningTrack;

    @Excel(name = "退款金额", width = 20, orderNum = "12")
    private String orderRefundAmount;

    private String deleteType;

    @Excel(name = "使用时长", width = 10, orderNum = "6")
    private String totalTime;

    @Excel(name = "用户手机号", width = 20, orderNum = "1")
    private String currentUserPhone;

    private String createDate;

    private String updateDate;

    private String orderGpsCoordinate;

    private String subStatus;

    @Excel(name = "账户抵扣", width = 10, orderNum = "10")
    private String deductibleAmount;

    private String subMethod;

    private Long accountId;

    private String paymentPort;

    private String outTradeNo;

    private String giftTime;

    /**
     * 实际收入
     */
    @Excel(name = "实际收入金额", width = 20, orderNum = "13")
    private Float realIncome;

    /**
     * 订单状态名称
     */
    @Excel(name = "支付状态", width = 20, orderNum = "16")
    private String orderStatusName;

    /**
     * 押金金额
     */
    private String depositPayAmount;

    private String orderAndDeductible;

    /**
     * 腾讯手续费
     */
    @Excel(name = "腾讯手续费费率", width = 20, orderNum = "14")
    private String tenCentCommission;

    /**
     * 最终入账金额
     */
    @Excel(name = "最终入账金额", width = 20, orderNum = "15")
    private String paymentTotalAccount;

    /**
     * 折扣后金额
     */
    private String amountAfterDiscount;

    /**
     * 今日收入
     */
    private String revenueAmountToday;

    /**
     * 显示金额
     */
    private String realIncomes;

    private String deductibleRefundAmount;

    private String distinguishType;

    private Long orderRobotId;

    private Long faultId;

    //是否退调度费
    private String isDispatchingFee;

    //当前订单的退款状态
    private String refundStatus;

    //扫码时停靠点名称
    private String startParkingName;
    //还车点名称
    private String parkingName;
    //还车点id
    private String orderParkingId;
    //停放点类型
    private String parkingType;
    //正在支付：0无法进行订单金额操作 默认值1可执行订单金额操作
    private String isPaying;

    //减免金额
    private String deductionAmount;

    //是否参加寻宝活动 0未参加 1参加 3抽奖
    private String huntsState;

    public Integer pageSize;
    public Integer pageNum;

    public String dateType;
    public String cycle;

    //所属公司ID
    public String companyId;

    //景区是否开启寻宝
    public String huntSwitch;

    @Excel(name = "真实退款原因", width = 20, orderNum = "18")
    public String reasonsRefundsTrue;
}