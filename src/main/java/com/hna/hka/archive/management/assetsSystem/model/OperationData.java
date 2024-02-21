package com.hna.hka.archive.management.assetsSystem.model;

import lombok.Data;

/**
 * @ProjectName: rc
 * @Package: com.hna.hka.archive.management.assetsSystem.model
 * @ClassName: OperationData
 * @Author: 郭凯
 * @Description: 本月运营数据实体类
 * @Date: 2021/6/21 14:50
 * @Version: 1.0
 */
@Data
public class OperationData {

    private String province;

    private String operationTime;

    private String orderAmount;

    private String completionRatio;

    private String robotUtilization;

    private String failureRate;

    private String unitPrice;

    private String robotOutputValue;


}
