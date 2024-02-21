package com.hna.hka.archive.management.appSystem.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("配件申请详情")
public class SubstituteApplyDetail {

  @ApiModelProperty("申请单号")
  private String applyNumber;
  @ApiModelProperty("配件id")
  private long goodsId;
  @ApiModelProperty("数量")
  private double quantity;
  @ApiModelProperty("金额")
  private double amount;
  @ApiModelProperty("出库单号")
  private String inventoryNumber;

}
