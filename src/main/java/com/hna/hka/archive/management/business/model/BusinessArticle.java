package com.hna.hka.archive.management.business.model;

import lombok.Data;

@Data
public class BusinessArticle {
    private Long id;

    private Long scenicSpotId;

    private Long coverId;

    private String title;

    private String briefDesc;

    private String type;

    private Integer recomSort;

    private String state;

    private String createTime;

    private String updateTime;

    private String details;

    private String typesOf;

    /**
     * 景区名称
     */
    private String scenicSpotName;
}