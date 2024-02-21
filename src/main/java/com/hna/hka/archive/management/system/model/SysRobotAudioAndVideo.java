package com.hna.hka.archive.management.system.model;

import lombok.Data;

@Data
public class SysRobotAudioAndVideo {
    private Long mediaId;

    private String mediaName;

    private String mediaNamePingYin;

    private String mediaAuthor;

    private String mediaAuthorPingYin;

    private String mediaType;

    private String mediaResourcesType;

    private String mediaUrl;

    private String createDate;

    private String updateDate;

}