package com.hna.hka.archive.management.assetsSystem.model;

import com.hna.hka.archive.management.appSystem.model.SysRobotAccessoriesApplicationDetail;
import lombok.Data;

import java.util.List;

@Data
public class SysRobotErrorRepairUser {
    private Long errorRepairUserId;

    private Long errorId;//故障id

    private Long repairId;//维修id

    private Long userId;//分配维修人员id

    private String createDate;//创建时间

    private String updateDate;//修改时间

    private String robotCode;//机器人编号

    private String errorRecordsModel;//机器人型号

    private String errorRecordsNo;//故障单号

    private String scenicSpotName;

    private String errorRecordsName;//故障名称

    private String faultStatus;//故障状态

    private String forwardingId;//发货单位id

    private String shippingInstructions;//发货说明

    private String detailsN ;//配件列表


}