package com.hna.hka.archive.management.system.model;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

@Data
public class SysParkingExcel {
    private Long parkingId;
    private Long parkingScenicSpotId;
    @Excel(name = "名称",orderNum = "2" ,width = 20)
    private String parkingName;
    @Excel(name = "内容",orderNum = "3" ,width = 20)
    private String parkingContent;
    @Excel(name = "WGS84坐标组",orderNum = "4")
    private String parkingCoordinateGroup;

    @Excel(name = "百度坐标组",orderNum = "5")
    private String parkingCoordinateGroupBaidu;

    @Excel(name = "状态",orderNum = "6" ,width = 20)
    private String parkingType;

    @Excel(name = "创建时间",orderNum = "7" ,width = 20)
    private String createDate;
    @Excel(name = "修改时间",orderNum = "8" ,width = 20)
    private String updateDate;

    @Excel(name = "景区名称",orderNum = "1" ,width = 20)
    private String scenicSpotName;
}
