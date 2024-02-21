package com.hna.hka.archive.management.system.model;

import lombok.Data;

@Data
public class SysRobotCorpusMediaExtend {
    private Long mediaExtendId;

    private Long mediaId;

    private String mediaExtendName;

    private String mediaExtendNamePingYin;

    private String mediaExtendAuthor;

    private String mediaExtendAuthorPingYin;

    private String createDate;

    private String updateDate;
}