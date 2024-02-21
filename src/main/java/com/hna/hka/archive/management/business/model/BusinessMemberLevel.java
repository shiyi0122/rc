package com.hna.hka.archive.management.business.model;

import lombok.Data;

@Data
public class BusinessMemberLevel {
    private Long id;

    private Long attachId;

    private String title;

    private Integer integral;

    private String state;

    private String createTime;

    private String updateTime;

}