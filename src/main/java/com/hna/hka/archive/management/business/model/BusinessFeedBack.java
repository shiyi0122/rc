package com.hna.hka.archive.management.business.model;

import lombok.Data;

@Data
public class BusinessFeedBack {
    private Long id;

    private Long userId;

    private String imageIds;

    private String content;

    private String replyContent;

    private String contact;

    private String isRead;

    private String state;

    private String createTime;

    private String updateTime;

    /**
     * 用户名称
     */
    private String userName;

}