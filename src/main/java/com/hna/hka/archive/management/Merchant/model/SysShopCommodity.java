package com.hna.hka.archive.management.Merchant.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SysShopCommodity {
    private Long commodityId;

    @ApiModelProperty("商店ID")
    private Long shopId;

    @ApiModelProperty("商店名称")
    private String commodityName;

    @ApiModelProperty("商品介绍")
    private String commodityContent;

    @ApiModelProperty("商品类型1冰淇淋 2饮料 3面包 4火腿肠 5面巾纸 （以此类推）")
    private String commodityType;

    @ApiModelProperty("商品价格")
    private String commodityPrice;

    @ApiModelProperty("商品数量")
    private Long commodityNum;

    @ApiModelProperty("创建时间")
    private String createDate;

    @ApiModelProperty("更新时间")
    private String updateDate;

    @ApiModelProperty("商店名称")
    private String shopName;
}