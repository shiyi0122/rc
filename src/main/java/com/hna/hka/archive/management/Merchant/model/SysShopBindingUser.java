package com.hna.hka.archive.management.Merchant.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SysShopBindingUser {
    private Long bindingId;

    @ApiModelProperty("绑定 商家或者店员ID")
    private Long shopUserId;

    @ApiModelProperty("绑定 商店ID")
    private Long shopId;

    @ApiModelProperty("创建时间")
    private String createDate;

    @ApiModelProperty("更新时间")
    private String updateDate;

}