package com.hna.hka.archive.management.system.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author zhang
 * @Date 2022/3/10 9:14
 */
@Data
public class SysScenicSpotDateHunt {

    @ApiModelProperty(name = "dateTreasureId" , value = "主键id")
    private Long dateTreasureId;
    @ApiModelProperty(name = "spotId" , value = "景区id")
    private Long spotId;
    @ApiModelProperty(name = "randomTime" , value = "时间")
    private String randomTime;
    @ApiModelProperty(name = "sort" , value = "排序")
    private String sort;
    @ApiModelProperty(name = "createDate" , value = "创建时间")
    private String createDate;
    @ApiModelProperty(name = "updateDate" , value = "修改时间")
    private String updateDate;

    private String scenicSpotName;

}
