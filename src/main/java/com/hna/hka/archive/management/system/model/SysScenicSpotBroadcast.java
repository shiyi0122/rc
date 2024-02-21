package com.hna.hka.archive.management.system.model;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

@Data
public class SysScenicSpotBroadcast {
    private Long broadcastId;

    private Long scenicSpotId;

    @Excel(name = "WGS84坐标",width = 20,orderNum = "2")
    private String broadcastGps;

    @Excel(name = "百度坐标" , width = 20,orderNum = "3")
    private String broadcastGpsBaiDu;

    private String scenicSpotRange;

    private String broadcastName;

    private String pinYinName;

    private String introductionTypes;

    private String navigationType;

    private String broadcastPriority;

    private String sortType;

    private String createDate;

    private String updateDate;

    /**
     * 景区名称
     */
    @Excel(name = "景区名称",width = 20,orderNum = "0")
    private String scenicSpotName;
}