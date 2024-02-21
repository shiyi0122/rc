package com.hna.hka.archive.management.system.model;

import lombok.Data;

@Data
public class SysCurrentUserAgreementLog {
    private Long agreementId;

    private Long userId;

    private String userPhone;

    private String agreementType;

    private Long scenicSpotId;

    private String createDate;

    private String updateDate;

    /**
     * 景区名称
     */
    private String scenicSpotName;

}