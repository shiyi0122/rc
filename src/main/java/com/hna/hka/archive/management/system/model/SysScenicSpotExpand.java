package com.hna.hka.archive.management.system.model;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class SysScenicSpotExpand extends SysScenicSpot {

    private Long imageId;

    private String scenicSpotArea;

    private BigDecimal revenueYear;

    private Integer rewardRate;

    private String  realData;

    private String scenicSpotIntroduce;

    /**
     * 省份ID
     */
    private String provinceId;

    /**
     * 城市ID
     */
    private String cityId;

    /**
     * 区/县ID
     */
    private String regionId;

}