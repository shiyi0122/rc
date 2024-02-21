package com.hna.hka.archive.management.business.model;

import lombok.Data;

@Data
public class BusinessDataMaintain {
    private Long id;

    private Long userId;

    private String content;

    private String type;

    private String state;

    private String createTime;

    private String updateTime;

    /**
     * 用户名称
     */
    private String userName;

    /**
     * 用户手机号
     */
    private String phone;

}