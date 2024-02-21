package com.hna.hka.archive.management.business.model;

import lombok.Data;

@Data
public class BusinessRole {
    private Long roleId;

    private String roleIdentity;

    private String roleName;

    private Long roleParentId;

    private String roleDesc;

    private String roleStatus;

    private String createDate;

    private String updateDate;

    private String roleType;

}