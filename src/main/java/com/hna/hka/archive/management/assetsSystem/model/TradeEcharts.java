package com.hna.hka.archive.management.assetsSystem.model;

import lombok.Data;

/**
 * @ProjectName: rc
 * @Package: com.hna.hka.archive.management.assetsSystem.model
 * @ClassName: TradeEcharts
 * @Author: 郭凯
 * @Description: 最近七天的订单数和订单金额实体
 * @Date: 2021/6/1 14:52
 * @Version: 1.0
 */
@Data
public class TradeEcharts {

    private String time;

    private Integer orderTotal;

    private String orderAmount;

    private String sevenDays;

    private String today;

    private String yesterday;

    private String thisMonth;

}
