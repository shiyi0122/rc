package com.hna.hka.archive.management.assetsSystem.model;

import io.swagger.models.auth.In;
import lombok.Data;

@Data
public class SysGoodsSubstituteApplyDetail {

    private String applyNumber;

    private Long goodsId;

    private Double quantity;

    private Double amount;

    private String inventoryNumber;
}
