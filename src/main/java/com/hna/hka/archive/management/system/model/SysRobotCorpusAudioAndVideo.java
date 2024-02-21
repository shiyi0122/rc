package com.hna.hka.archive.management.system.model;

import lombok.Data;

@Data
public class SysRobotCorpusAudioAndVideo {
    private Long mediaId;

    private Long scenicSpotId;

    private String mediaName;

    private String mediaNamePingYin;

    private String mediaAuthor;

    private String mediaAuthorPingYin;

    private String mediaType;

    private String mediaResourcesType;

    private String mediaUrl;

    private String createDate;

    private String updateDate;

    /**
     * 景区名称
     */
    private String scenicSpotName;

    /**
     * 通用类型
     */
    private String mediaCurrencyType;
}