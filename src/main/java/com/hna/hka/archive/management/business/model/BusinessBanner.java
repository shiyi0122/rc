package com.hna.hka.archive.management.business.model;

import lombok.Data;

@Data
public class BusinessBanner {
    private Long id;

    private String title;

    private Long appImageId;

    private Long webImageId;

    private String url;

    private String state;

    private String createTime;

    private String updateTime;

    private String type;
}