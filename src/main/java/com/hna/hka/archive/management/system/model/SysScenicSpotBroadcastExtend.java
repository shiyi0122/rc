package com.hna.hka.archive.management.system.model;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

@Data
public class SysScenicSpotBroadcastExtend extends SysScenicSpotBroadcast{
    private Long broadcastResId;

    private Long broadcastId;

    private String mediaResourceUrl;

    private String mediaType;

    private String createDate;

    private String updateDate;

    /**
     * 景点名称
     */
    @Excel(name = "景点名称",width = 20,orderNum = "1")
    private String broadcastName;

    //0默认为无宝箱类型 1为玲龙宝箱  2为锦龙宝箱 3为祥龙宝箱
    @Excel(name = "宝箱类型",width = 20,orderNum = "6")
    private String treasureType;

    //宝箱里的金币积分
    @Excel(name = "金币积分数量",width = 20,orderNum = "7")
    private Long integralNum;

    //坐标半径默认值为5米
    @Excel(name = "坐标缓冲半径",width = 20,orderNum = "8")
    private String scenicSpotRange;

    private Long scenicSpotId;
    
}