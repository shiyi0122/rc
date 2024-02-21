package com.hna.hka.archive.management.assetsSystem.model;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SysScenicSpotTargetAmountAscription {

    @ApiModelProperty("绩效归属主键id")
    private Long targetAmountAscriptionId;

    @ApiModelProperty("绩效表主键id")
    private Long targetAmountId;

    @ApiModelProperty("承担方")
    private String undertakerId;

    @ApiModelProperty("承担金额")
    private String commitmentAmount;

    @ApiModelProperty("类型，1机器折旧，2运营人员成本，3景区营销成本，4维养成本，5租金")
    private String type;

    private String createTime;

    private String undertakerName;

    //主体公司
    private String companyNameSubject;

}