package com.hna.hka.archive.management.system.model;

import lombok.Data;

@Data
public class SysCurrentUserUpdateTreasureLog {
    //主键
    private Long IntegralLogId;
    //后台管理账号
    private String userAccount;
    //修改前权重
    private String frontProbability;
    //修改后权重
    private String afterProbability;
    //修改前奖品名称
    private String frontTreasureName;
    //修改后奖品名称
    private String afterTreasureName;
    //修改前奖品类型 0普通奖品 1大奖
    private String frontTreasureType;
    //修改后奖品类型 0普通奖品 1大奖
    private String afterTreasureType;
    //修改前邮寄方式 1邮寄方式 2现场兑换方式
    private String frontWayType;
    //修改后邮寄方式 1邮寄方式 2现场兑换方式
    private String afterWayType;
    //修改前类型  1代金券 2 免单  3实物 4谢谢参与
    private String frontType;
    //修改后类型  1代金券 2 免单  3实物 4谢谢参与
    private String afterType;
    //修改前优惠卷额度  实物和免单券0.00
    private String frontCoupon;
    //修改后优惠卷额度  实物和免单券0.00
    private String afterCoupon;
    //创建时间
    private String createDate;
    //修改时间
    private String updateDate;

    private Integer pageNum;
    private Integer pageSize;
}
