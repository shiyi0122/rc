package com.hna.hka.archive.management.system.model;

import lombok.Data;

@Data
public class SysScenicSpotTreasureNewHunt {

    //寻宝奖品ID
    private Long treasureId;

    //关联奖池ID
    private Long jackpotId;

    //奖品名称（例如：代金券、实体物品）
    private String treasureName;

    //奖品类型  1代金券 2 免单  3实物
    private String treasureType;

    //代金券金额 （免单和实物赋值0.00）
    private String couponAmount;

    //库存数量
    private Long inventory;

    //有效期
    private String endValidity;

    //结束时间
    private String startValidity;

    //奖品图片
    private String picUrl;

    //0普通奖品 1大奖
    private String prizeSize;

    //权重
    private Double probability;
    //临时权重
    private String probability1;

    private String createDate;

    private String updateDate;

    private Integer pageNum;
    private Integer pageSize;

    //兑奖方式0未选择兑换方式  1邮寄方式 2现场兑换方式
    private String wayType;

    //用户中奖后有效期（单位天）
    private Long userExpiration;

}
