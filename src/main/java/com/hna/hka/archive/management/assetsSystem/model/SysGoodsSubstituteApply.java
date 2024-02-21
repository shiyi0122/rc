package com.hna.hka.archive.management.assetsSystem.model;

import lombok.Data;

import java.util.Date;

@Data
public class SysGoodsSubstituteApply {

    private String applyNumber;

    private Long  spotId;

    private String applyUserId;

    private Date applyTime;

    private String applyReasion;

    private String picturePath;

    private Double  totalAmount;

    private Integer  applyStat;

    private String  examineAggestion;

    private String   receivingName;

    private String  receivingPhone;

    private String  inventoryNumber;

    private String  note;

    private Date  createTime;

    private Date  updateTime;

    private Integer  type;

    private String   faultNumber;

}
