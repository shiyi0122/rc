package com.hna.hka.archive.management.appSystem.model;

import lombok.Data;

/**
 * @Author zhang
 * @Date 2023/2/27 16:39
 * 配件申请详情表
 */
@Data
public class SysRobotAccessoriesApplicationDetail {

    private Long id;

    private Long  accessoriesApplicationId;

    private Long  accessoriesId;
    //商品名称
    private String   accessoriesName;

    private String accessoriesType;
    //合计金额
    private String  accessoryPrice;

    private String  accessoryModel;
    //数量
    private String  accessoryNumber;

    private String  warehouseId;
    //发货说明
    private String  shippingInstructions;

    private String  courierNumber;

    private String  expressFee;

    private String accessoriesReceivedType;

    private String  signInPicture;

    private String createDate;

    private String updateDate;

    private String  isSendOutGoods;

    private String type;

    //申请人
    private String  applicant;
    //申请人手机号
    private String  applicantPhone;
    //收件地址
    private String receivingAddress;
    //景区id
    private String  scenicSpotId;
    //景区name
    private String  scenicSpotName;
    //发货景区名称
    private String warehouseName;
    //发货库房id
    private String warehouseModelId;

    //配件id
    private Long goodsId;

}
