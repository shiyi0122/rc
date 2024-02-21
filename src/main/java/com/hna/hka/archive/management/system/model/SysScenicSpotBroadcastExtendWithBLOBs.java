package com.hna.hka.archive.management.system.model;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

@Data
public class SysScenicSpotBroadcastExtendWithBLOBs extends SysScenicSpotBroadcastExtend {

    @Excel(name = "景点内容" , width = 100, orderNum = "4")
    private String broadcastContent;

    private String pictureUrl;

}