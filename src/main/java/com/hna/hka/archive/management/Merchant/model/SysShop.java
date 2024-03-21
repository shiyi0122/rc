package com.hna.hka.archive.management.Merchant.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SysShop {
    private Long shopId;

    @ApiModelProperty("商店名称")
    private String shopName;

    @ApiModelProperty("商店地址")
    private String shopAddress;

    @ApiModelProperty("商店电话")
    private String shopPhone;

    @ApiModelProperty("景区Id")
    private Long scenicSpotId;

    @ApiModelProperty("景区名称")
    private String scenicSpotName;

    @ApiModelProperty("商店GPS定位")
    private String shopGps;

    @ApiModelProperty("创建时间")
    private String createDate;

    @ApiModelProperty("更新时间")
    private String updateDate;

}