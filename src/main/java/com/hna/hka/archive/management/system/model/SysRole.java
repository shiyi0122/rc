package com.hna.hka.archive.management.system.model;

import lombok.Data;

@Data
public class SysRole {
    private Long roleId;

    private String roleName;

    private String roleDesc;

    private String roleStatus;

    private String createDate;

    private String updateDate;

    private  String userName;
}