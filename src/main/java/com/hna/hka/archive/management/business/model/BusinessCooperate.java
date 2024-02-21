package com.hna.hka.archive.management.business.model;

import lombok.Data;

@Data
public class BusinessCooperate {
    private Long id;

    private Integer provinceId;

    private Integer cityId;

    private Integer regionId;

    private String name;

    private String phone;

    private String content;

    private String address;

    private String type;

    private String state;

    private String createTime;

    private String updateTime;

    /**
     * 地区名称
     */
    private String mergerName;
}