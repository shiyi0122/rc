package com.hna.hka.archive.management.system.model;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

@Data
public class SysScenicSpotParking {
    @Excel(name = "id",orderNum = "0" ,width = 20)
    private Long parkingId;
    @Excel(name = "名称",orderNum = "1" ,width = 20)
    private String parkingName;
    @Excel(name = "内容",orderNum = "2" ,width = 20)
    private String parkingContent;
    @Excel(name = "景区id",orderNum = "3" ,width = 20)
    private Long parkingScenicSpotId;
//    @Excel(name = "id",orderNum = "4" ,width = 20)
    private String parkingType;
//    @Excel(name = "id",orderNum = "5" ,width = 20)
    private String coordinateType;
    @Excel(name = "创建时间",orderNum = "6" ,width = 20)
    private String createDate;
    @Excel(name = "修改时间",orderNum = "7" ,width = 20)
    private String updateDate;

    /**
     * 景区名称
     */
    @Excel(name = "景区名称",orderNum = "8" ,width = 20)
    private String scenicSpotName;

}