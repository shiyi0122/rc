package com.hna.hka.archive.management.wenYuRiverInterface.model;

import lombok.Data;

/**
 * @ProjectName: rc
 * @Package: com.hna.hka.archive.management.wenYuRiverInterface.model
 * @ClassName: RealTimeAccess
 * @Author: 郭凯
 * @Description: 实时获取数据对象
 * @Date: 2021/5/14 10:35
 * @Version: 1.0
 */
@Data
public class RealTimeAccess {

    //实时营收
    private String timeAccess;

    //机器人正在使用数量
    private String robotQuantityUsed;

    //机器人剩余数量
    private String robotRemainingQuantity;

    //总收入
    private String totalRevenue;
    //最终入账
    private String finialRevenue;



}
