package com.hna.hka.archive.management.system.model;

import lombok.Data;

@Data
public class SysCurrentBlackList {
    private Long blackListId;

    private Long currentUserId;

    private String blackListType;

    private String createDate;

    private String updateDate;

}