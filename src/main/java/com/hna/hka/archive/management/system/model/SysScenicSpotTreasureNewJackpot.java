package com.hna.hka.archive.management.system.model;

import lombok.Data;

@Data
public class SysScenicSpotTreasureNewJackpot {

    private Long jackpotId;

    //关联景区ID
    private Long scenicSpotId;

    //景区名称
    private String scenicSpotName;

    //金币积分条件（多少积分抽一次奖品）
    private Long integralNum;

    //奖池类型 1定点寻宝 2定时寻宝
    private String jackpotType;

    private String createDate;

    private String updateDate;

    private Integer pageNum;
    private Integer pageSize;

    //奖池名称
    private String jackpotName;
    //排序
    private String jackpotSort;
    //判断奖池内的奖品数量是否够用
    private String ExchangeNum;
}
