package com.hna.hka.archive.management.system.model;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;

/**
 * @ProjectName: rc
 * @Package: com.hna.hka.archive.management.system.model
 * @ClassName: SysOrderExcel
 * @Author: 郭凯
 * @Description:
 * @Date: 2021/7/28 17:17
 * @Version: 1.0
 */
@Data
public class SysOrderExcel extends BaseRowModel {

    @ExcelProperty(value = "订单计费",index = 12)
    private String orderAmount;

    @ExcelProperty(value = "实际计费金额",index = 6)
    private String actualAmount;

    @ExcelProperty(value = "折扣",index = 8)
    private String orderDiscount;

    @ExcelProperty(value = "景区名称",index = 3)
    private String orderScenicSpotName;

    @ExcelProperty(value = "订单编号",index = 0)
    private String orderNumber;

    @ExcelProperty(value = "订单开始时间",index = 4)
    private String orderStartTime;

    @ExcelProperty(value = "订单结束时间",index = 5)
    private String orderEndTime;

    @ExcelProperty(value = "机器人编号",index = 2)
    private String orderRobotCode;

    @ExcelProperty(value = "调度费",index = 10)
    private String dispatchingFee;

    @ExcelProperty(value = "优惠劵",index = 9)
    private String coupon;

    @ExcelProperty(value = "退款原因",index = 18)
    private String reasonsRefunds;
    @ExcelProperty(value = "真实退款原因",index = 19)
    private String reasonsRefundsTrue;

    @ExcelProperty(value = "退款金额",index = 13)
    private String orderRefundAmount;

    @ExcelProperty(value = "使用时长",index = 7)
    private String totalTime;

    @ExcelProperty(value = "用户手机号",index = 1)
    private String currentUserPhone;

    @ExcelProperty(value = "账户抵扣",index = 11)
    private String deductibleAmount;

    /**
     * 实际收入
     */
    @ExcelProperty(value = "实际收入金额",index = 14)
    private Float realIncome;

    /**
     * 订单状态名称
     */
    @ExcelProperty(value = "支付状态",index = 17)
    private String orderStatusName;

    /**
     * 腾讯手续费
     */
    @ExcelProperty(value = "腾讯手续费费率",index = 15)
    private String tenCentCommission;

    /**
     * 最终入账金额
     */
    @ExcelProperty(value = "最终入账金额",index = 16)
    private String paymentTotalAccount;

    @ExcelIgnore
    private String orderStatus;

    @ExcelIgnore
    private String huntsState;

    @ExcelProperty(value = "是否参加寻宝活动",index = 20)
    private String huntsStateName;
}
