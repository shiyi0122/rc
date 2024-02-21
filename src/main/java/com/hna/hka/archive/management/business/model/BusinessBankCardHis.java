package com.hna.hka.archive.management.business.model;

import lombok.Data;

@Data
public class BusinessBankCardHis {
    private Long id;

    private Long userId;

    private Long bankCardId;

    private String bankCard;

    private String bankName;

    private String bankInfo;

    private String telephone;

    private Boolean state;

    private String createTime;

    private String updateTime;
    
    private String bankProvince;

    private String bankBranch;

}