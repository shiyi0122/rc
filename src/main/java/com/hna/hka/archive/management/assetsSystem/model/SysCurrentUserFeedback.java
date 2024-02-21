package com.hna.hka.archive.management.assetsSystem.model;

import lombok.Data;

@Data
public class SysCurrentUserFeedback {
    private Long feedbackId;

    private Long currentUserId;

    private Long scenicSpotId;

    private String feedbackType;

    private String robotCode;

    private String feedback;

    private String describes;

    private String createDate;

    private String updateDate;

    private String phone;

    private String scenicSpotName;
}