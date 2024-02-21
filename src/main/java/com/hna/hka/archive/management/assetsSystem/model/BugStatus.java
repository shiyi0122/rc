package com.hna.hka.archive.management.assetsSystem.model;

import lombok.Data;

/**
 * @ProjectName: rc
 * @Package: com.hna.hka.archive.management.assetsSystem.model
 * @ClassName: BugStatus
 * @Author: 郭凯
 * @Description: 故障统计实体类
 * @Date: 2021/7/11 16:08
 * @Version: 1.0
 */
@Data
public class BugStatus {

    /* *****************************************************查询条件************************************************** */

    //开始时间
    private String startTime;

    //结束时间
    private String endTime;

    //景区查询方式
    private String checkScenicWay;

    //选择统计项
    private String statsOptionWay;

    /* *****************************************************接收参数************************************************** */

    //时间
    private String date;

    //上报原因大类
    private String reportReasonClass;

    //上报次数
    private String reportNum;

    //上报次数/上报总次数
    private String reportTotal;

    //上报次数/订单总数
    private String orderTotal;

    //上报大类
    private String causes;

    //景区名称
    private String scenicName;

    //该景区上报次数/该景区上报总次数
    private String repotTotal;

    //该景区上报次数/平台交易总笔数
    private String tradeTotal;

    //上报原因
    private String reportReason;


}
