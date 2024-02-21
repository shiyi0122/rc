package com.hna.hka.archive.management.assetsSystem.model;

import lombok.Data;

/**
 * @ProjectName: rc
 * @Package: com.hna.hka.archive.management.assetsSystem.model
 * @ClassName: ReturnOperatingTime
 * @Author: 郭凯
 * @Description: 运营时长返回参数
 * @Date: 2021/6/23 15:10
 * @Version: 1.0
 */
@Data
public class ReturnOperatingTime {

    private String date;

    private String robotId;

    private String avgOperateTime;

    private String YoY;

    private String QoQ;

    private String robotUseRatio;

    private String robotNum;

}
