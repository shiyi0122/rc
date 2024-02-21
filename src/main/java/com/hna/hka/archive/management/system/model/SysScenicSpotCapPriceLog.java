package com.hna.hka.archive.management.system.model;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

@Data
public class SysScenicSpotCapPriceLog {
    private Long capPriceId;

    private Long scenicSpotId;

    private String loginName;
    @Excel(name = "一档工作日封顶时间",orderNum = "1" ,width = 20)
    private String normalCapOneTime;
    @Excel(name = "一档工作日封顶价格",orderNum = "2" ,width = 20)
    private String normalCapOnePrice;
    @Excel(name = "一档工作日封顶单价",orderNum = "3" ,width = 20)
    private String normalCapOneUnitPrice;
    @Excel(name = "二档工作日封顶时间",orderNum = "4" ,width = 20)
    private String normalCapTwoTime;
    @Excel(name = "二档工作日封顶价格",orderNum = "5" ,width = 20)
    private String normalCapTwoPrice;
    @Excel(name = "二档工作日封顶单价",orderNum = "6" ,width = 20)
    private String normalCapTwoUnitPrice;
    @Excel(name = "一档周末封顶时间",orderNum = "7" ,width = 20)
    private String weekendCapOneTime;
    @Excel(name = "一档周末封顶价格",orderNum = "8" ,width = 20)
    private String weekendCapOnePrice;
    @Excel(name = "一档周末封顶单价",orderNum = "9" ,width = 20)
    private String weekendCapOneUnitPrice;
    @Excel(name = "二档周末封顶时间",orderNum = "10" ,width = 20)
    private String weekendCapTwoTime;
    @Excel(name = "二档周末封顶价格",orderNum = "11" ,width = 20)
    private String weekendCapTwoPrice;
    @Excel(name = "二档周末封顶单价",orderNum = "12" ,width = 20)
    private String weekendCapTwoUnitPrice;
    @Excel(name = "添加时间",orderNum = "13" ,width = 20)
    private String createDate;

    private String updateDate;

    /**
     * 景区名称
     */
    @Excel(name = "景区名称",orderNum = "0" ,width = 20)
    private String scenicSpotName;

}