package com.hna.hka.archive.management.Merchant.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SysShopCurrentUser {
    private Long shopUserId;

    @ApiModelProperty("用户手机号")
    private String userPhone;

    @ApiModelProperty("用户微信openId")
    private String openId;

    @ApiModelProperty("用户角色 0暂无角色 1商家 2店员")
    private String userRole;

    @ApiModelProperty("创建时间")
    private String createDate;

    @ApiModelProperty("更新时间")
    private String updateDate;

    @ApiModelProperty("商家id")
    private String shopId;

    @ApiModelProperty("商家名称")
    private String shopName;

    //
    private String thdSession;
    private String scenicSpotId;
    private String scenicSpotName;


}