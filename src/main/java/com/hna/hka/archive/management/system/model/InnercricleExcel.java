package com.hna.hka.archive.management.system.model;


import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;



@Data
public class InnercricleExcel {


    private Long coordinateInnercircleId;

    private Long coordinateId;


    @Excel(name = "景区名称", width = 20)
    private String scenicSpotName;

    @Excel(name = "禁区名称",width = 20)
    private String coordinateInnercircleName;

    @Excel(name = "禁区内容",width = 20)
    private String coordinateInnercircleContent;

    @Excel(name = "内圈WGS84坐标组",width = 20)
    private String coordinateInnercircle;

    @Excel(name = "内圈百度坐标组",width = 20)
    private String coordinateInnercircleBaiDu;

    @Excel(name = "警告缓冲圈(米)",width = 20)
    private String bufferRadius;

    @Excel(name = "状态",width = 20)
    private String innercircleType;

    @Excel(name = "添加时间",width = 20,databaseFormat = "yyyyMMdd",format = "yyyy-MM-dd")
    private String createDate;

    @Excel(name = "修改时间",width = 20,databaseFormat = "yyyyMMdd",format = "yyyy-MM-dd")
    private String updateDate;


}
