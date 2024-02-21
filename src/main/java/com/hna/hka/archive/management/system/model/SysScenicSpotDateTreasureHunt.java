package com.hna.hka.archive.management.system.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author zhang
 * @Date 2022/3/10 9:20
 */
@Data
public class SysScenicSpotDateTreasureHunt {

    @ApiModelProperty(name = "treasureId" , value = "随机奖品主键id")
    private Long treasureId;
    @ApiModelProperty(name = "dateTreasureId" , value = "随机表id")
    private Long dateTreasureId;
    @ApiModelProperty(name = "treasureName" , value = "随机奖品名称")
    private String treasureName;
    @ApiModelProperty(name = "treasureType" , value = "随机奖品类型 1代金券 2 免单  3实物")
    private String treasureType;
    @ApiModelProperty(name = "couponAmount" , value = "代金卷金额")
    private String couponAmount;
    @ApiModelProperty(name = "inventory" , value = "数量")
    private String inventory;
    @ApiModelProperty(name = "endValidity" , value = "奖品开始有效期")
    private String endValidity;
    @ApiModelProperty(name = "startValidity" , value = "结束时间")
    private String startValidity;
    @ApiModelProperty(name = "picUrl" , value = "奖品图片")
    private String picUrl;
    @ApiModelProperty(name = "prizeWeight" , value = "权重")
    private String prizeWeight;
    @ApiModelProperty(name = "createDate" , value = "创建时间")
    private String createDate;
    @ApiModelProperty(name = "updateDate" , value = "修改时间")
    private String updateDate;
    @ApiModelProperty(name = "scenicSpotId" , value = "景区id")
    private Long    scenicSpotId;

    private String scenicSpotName;

    private String randomTime;


}
