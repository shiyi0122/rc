package com.hna.hka.archive.management.system.model;

import lombok.Data;

@Data
public class SysScenicSpotTimingProblem {
    private Long problemId;

    private String timingProblem;

    private Long scenicSpotId;

    private String timingType;

    private String timingState;

    private String timingResRul;

    private String createDate;

    private String updateDate;

    private String weight;

    /**
     * 景区名称
     */
    private String scenicSpotName;
}