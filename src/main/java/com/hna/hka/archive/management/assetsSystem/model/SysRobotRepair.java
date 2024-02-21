package com.hna.hka.archive.management.assetsSystem.model;

import lombok.Data;

/**
 * @ProjectName: rc
 * @Package: com.hna.hka.archive.management.assetsSystem.model
 * @ClassName: SysRobotRepair
 * @Author: 郭凯
 * @Description: 机器人维修
 * @Date: 2021/6/24 18:36
 * @Version: 1.0
 */
@Data
public class SysRobotRepair {

    //机器人故障ID
    private Long errorRecordsId;

    //景区ID
    private Long scenicSpotId;

    //机器人编号
    private String robotCode;

    //故障单号
    private String errorRecordsModel;

    //故障名称
    private String errorRecordsName;

    //维修结果
    private String serviceRecordsResult;

    //维修详情
    private String serviceRecordsDetails;

    //维修人员
    private String serviceRecordsPersonnel;

    //维修人员电话
    private String serviceRecordsTel;

    //维修评价级别
    private String serviceRecordsLevel;

    //配件是否收到: 0未收到； 1已收到
    private String ErrorRecordsReceive;

    //已更换配件:0未更换配件；1已更换配件
    private String errorRecordsReplace;

    //配件来源:1新收到的配件;2园区库房配件
    private String errorRecordsSource;

    //维修ID
    private Long serviceRecordsId;

    //维修金额
    private String errorRecordsUpkeepCost;

    private String improperOperation;//是否运营不当造成0是，1不是

    //故障状态
    private String faultStatus;

    //库存详情
    private String inventoryDetailList;

    private String unit;

    private String goodsCode;
    //配件详情新
    private String detailsN;

    //出库原因
    private String inStockReason;
}
