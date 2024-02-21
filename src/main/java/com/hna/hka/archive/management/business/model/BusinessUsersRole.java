package com.hna.hka.archive.management.business.model;

import lombok.Data;

@Data
public class BusinessUsersRole {
    private Long id;

    private Long userId;

    private Long roleId;

    private String createDate;

}