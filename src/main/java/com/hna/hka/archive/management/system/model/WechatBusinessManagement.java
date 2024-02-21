package com.hna.hka.archive.management.system.model;

import lombok.Data;

@Data
public class WechatBusinessManagement {
    private Long merchantId;

    private String merchantName;

    private Long merchantScenicSpotId;

    private String certFileName;

    private String merchantSecret;

    private String merchantNumber;

    private String createDate;

    private String updateDate;

    /**
     * 文件名称
     */
    private String merchantFileName;
}