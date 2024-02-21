package com.hna.hka.archive.management.assetsSystem.model;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

/**
 * @ProjectName: rc
 * @Package: com.hna.hka.archive.management.assetsSystem.model
 * @ClassName: ScenicSpotRanking
 * @Author: 郭凯
 * @Description: 景区运营排名实体类
 * @Date: 2021/6/17 16:40
 * @Version: 1.0
 */
@Data
public class ScenicSpotRanking {

    private String time;

    @Excel(name = "景区名称",orderNum = "0",width = 20)
    private String scenicSpotName;

    @Excel(name = "运营时长/小时",orderNum = "1",width = 20)
    private String operationTime;

    @Excel(name = "订单数量",orderNum = "2",width = 20)
    private String orderNumber;

    @Excel(name = "订单金额/元",orderNum = "3",width = 20)
    private String orderAmount;

    @Excel(name = "目标完成比例/%",orderNum = "4",width = 20)
    private String completionRatio;

    @Excel(name = "单台机器人接单量",orderNum = "5",width = 20)
    private String robotReceivingOrder;

    @Excel(name = "单台机器人产值/元",orderNum = "6",width = 20)
    private String robotOutputValue;

    @Excel(name = "单台机器人运营时长/小时",orderNum = "7",width = 20)
    private String robotOperationTime;

    @Excel(name = "单运营人员接单量",orderNum = "8",width = 20)
    private String operatorsReceivingOrder;

    @Excel(name = "机器人利用率/%",orderNum = "9",width = 20)
    private String robotUtilization;

    @Excel(name = "客单价/元",orderNum = "10",width = 20)
    private String unitPricePerCustomer;

    @Excel(name = "订单故障率/%",orderNum = "11",width = 20)
    private String failureRate;

    @Excel(name = "机器人投放数量",orderNum = "12",width = 20)
    private String robotLaunchQuantity;

    private String targetAmount;

    private String operateStartTime;

    private String operateEndTime;

    private String operatePeople;

    @Excel(name = "在线订单数量",orderNum = "13",width = 20)
    private String jxz;

    @Excel(name = "未付款订单数量",orderNum = "14",width = 20)
    private String wzf;

    @Excel(name = "未付款金额/元",orderNum = "15",width = 20)
    private String wzfje;

}
