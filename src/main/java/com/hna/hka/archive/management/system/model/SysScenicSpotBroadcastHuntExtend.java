package com.hna.hka.archive.management.system.model;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

@Data
public class SysScenicSpotBroadcastHuntExtend extends SysScenicSpotBroadcastHunt{
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
    
}