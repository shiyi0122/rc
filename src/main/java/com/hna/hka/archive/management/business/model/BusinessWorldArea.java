package com.hna.hka.archive.management.business.model;

import lombok.Data;

@Data
public class BusinessWorldArea {
    private Integer id;

    private Integer pid;

    private String shortName;

    private String name;

    private String mergerName;

    private String level;

    private String pinyin;

    private String phoneCode;

    private String zipCode;

    private String first;

    private String lng;

    private String lat;

    private String areaCode;
}