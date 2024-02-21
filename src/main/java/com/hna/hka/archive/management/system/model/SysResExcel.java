package com.hna.hka.archive.management.system.model;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

@Data
public class SysResExcel {

    private Long serviceId;

    private Long  scenicSpotId;

    @Excel(name = "景区名称", width = 20,orderNum = "1")
    private String scenicSpotName;

    @Excel(name = "服务项名称",width = 20,orderNum = "2")
    private String serviceName;

    @Excel(name = "WGS84坐标",width = 20,orderNum = "3")
    private String serviceGps;

    @Excel(name="百度坐标",width = 20,orderNum = "4")
    private String serviceGpsBaiDu;

    @Excel(name = "服务站介绍",width = 20,orderNum = "5")
    private String serviceIntroduce;

    @Excel(name = "服务站图片",width = 20,orderNum = "6")
    private String servicePic;

    @Excel(name = "更新时间",width = 20,orderNum = "7")
    private String createDate;

    @Excel(name = "修改时间",width = 20,orderNum = "8")
    private String updateDate;

}
