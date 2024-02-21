package com.hna.hka.archive.management.assetsSystem.model;

import lombok.Data;

/**
 * @ProjectName: rc
 * @Package: com.hna.hka.archive.management.assetsSystem.model
 * @ClassName: OperateStream
 * @Author: 郭凯
 * @Description: 运营流水实体类
 * @Date: 2021/7/4 19:02
 * @Version: 1.0
 */
@Data
public class OperateStream {

    private String date;

    private String amount;

    private String targetAmount;

    private String yoY;

    private String qoQ;

    private String scenicName;

    private String robotId;

    private String completeRatio;

    private String orderCount;

    private String robotTotalTimeSum;
}
