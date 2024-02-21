package com.hna.hka.archive.management.system.model;

import lombok.Data;

@Data
public class SysCurrentUserAddress {

    private Long addressId;

    private Long userId;

    private String mailingAddress;

    private String phone;

    private String fullName;

    private String createDate;

    private String updateDate;
    private String userPhone1;
    private Integer pageNum;
    private Integer pageSize;
}
