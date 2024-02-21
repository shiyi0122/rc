package com.hna.hka.archive.management.business.model;

import lombok.Data;

@Data
public class BusinessBankCard {
    private Long id;

    private Long userId;
    
    private String bankName;

    private String bankCard;

    private String bankInfo;

    private String telephone;

    private String rejectInfo;

    private String state;

    private String createTime;

    private String updateTime;

    /**
     * 用户名称
     */
    private String userName;
    
    private String bankBranch;

    private String bankProvince;

}