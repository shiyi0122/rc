package com.hna.hka.archive.management.assetsSystem.model;

import lombok.Data;

/**
 * @ProjectName: rc
 * @Package: com.hna.hka.archive.management.assetsSystem.model
 * @ClassName: OperateState
 * @Author: 郭凯
 * @Description: 运营状态实体类
 * @Date: 2021/7/11 22:49
 * @Version: 1.0
 */
@Data
public class OperateState {

    /* *****************************************************查询条件************************************************** */

    //景区ID
    private String scenicSpotId;

    //开始时间
    private String startTime;

    //结束时间
    private String endTime;

    /* *****************************************************接收参数************************************************** */

    //机器人编号
    private String robotId;

    //景区名称
    private String scenicName;

    //机器人型号
    private String robotModel;

    //时间
    private String date;

    //运营状态
    private String opeState;

    //使用中数量
    private String inUse;

    //机器人总数量
    private String robotNumber;

    //数量
    private String robotNum;

    //占比
    private String ratio;

}

