package com.hna.hka.archive.management.system.model;

import lombok.Data;

@Data
public class SysScenicSpotWarning {
    private Long warningId;

    private Long scenicSpotId;

    private String warningName;

    private String warningGps;

    private String warningGpsBaiDu;

    private String warningContent;

    private String warningPic;

    private String warningPriority;

    private String warningRadius;

    private String createDate;

    private String updateDate;

    /**
     * 景区名称
     */
    private String scenicSpotName;

}