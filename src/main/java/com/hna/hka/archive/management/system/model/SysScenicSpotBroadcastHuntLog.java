package com.hna.hka.archive.management.system.model;

import lombok.Data;

/**
 * @Author zhang
 * @Date 2022/3/11 17:23
 * 寻宝活动日志
 */
@Data
public class SysScenicSpotBroadcastHuntLog {

    private Long huntLogId;

    //景区ID
    private Long scenicSpotId;

    //景区名称
    private String scenicSpotName;

    //订单编号
    private String orderNumber;

    //用户ID
    private Long userId;

    //用户手机号
    private String userPhone;

    //景点ID
    private Long broadcastId;

    //景点名称
    private String broadcastName;

    //获得的金币数额
    private Long integralNum;

    private String createDate;

    private String updateDate;

    private Integer pageNum;
    private Integer pageSize;
    private String userPhone1;

}
