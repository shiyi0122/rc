package com.hna.hka.archive.management.system.model;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

@Data
public class WarningExcel {

    private Long warningId;

    private Long  scenicSpotId;

    @Excel(name = "景点名称",width = 20,orderNum = "1")
    private String scenicSpotName;

    @Excel(name = "警告名称",width = 20,orderNum = "2")
    private String warningName;

    @Excel(name = "WGS84坐标",width = 20,orderNum = "3")
    private String warningGps;

    @Excel(name = "百度坐标",width = 20,orderNum = "4")
    private String warningGpsBaiDu;

    @Excel(name = "警告内容",width = 20,orderNum = "5")
    private String warningContent;

    @Excel(name = "警告图片",width = 20,orderNum = "6")
    private String warningPic;

    @Excel(name = "警告点半径",width = 20,orderNum = "7")
    private String warningRadius;

    @Excel(name = "更新时间",width = 20,orderNum = "8")
    private String createDate;

    @Excel(name = "修改时间",width = 20,orderNum = "9")
    private String updateDate;
}
