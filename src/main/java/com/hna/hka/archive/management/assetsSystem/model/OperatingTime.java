package com.hna.hka.archive.management.assetsSystem.model;

import lombok.Data;

/**
 * @ProjectName: rc
 * @Package: com.hna.hka.archive.management.assetsSystem.model
 * @ClassName: OperatingTime
 * @Author: 郭凯
 * @Description: 运营时长实体类
 * @Date: 2021/6/22 16:14
 * @Version: 1.0
 */
@Data
public class OperatingTime {

    /* *****************************************************查询条件************************************************** */

    //景区ID
    private String scenicSpotId;

    //统计状态方式(按景区统计1、按机器人统计0)
    private String newStateWay;

    //运营时长方式(平均运营时长1、运营时长0)
    private String operateTimeWay;

    //开始时间
    private String startTime;

    //结束时间
    private String endTime;

    //是否显示环比
    private Boolean showQoQ;

    //运营时长输入框文字内容
    private String opeTimeInputValue;

    //选择的日期(1月份/2日份/3年份)
    private String chooseDate;

    //同比排序
    private String YoYOrderBy;

    //环比排序
    private String QoQOrderBy;

    //机器人利用率排序
    private String robotUseRatioOrderBy;

    //目标完成比例
    private String completeRInputValue;

    private String amountWay;

    /* *****************************************************返回参数************************************************** */

    //日期
    private String date;

    //景区名称
    private String scenicName;

    //运营时长
    private String avgOperateTime;

    //同比
    private String yoY;

    //环比
    private String qoQ;

    private String robotUseRatio;

    private String robotId;

    private String robotModel;
}
