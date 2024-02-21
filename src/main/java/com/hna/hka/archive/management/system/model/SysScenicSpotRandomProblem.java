package com.hna.hka.archive.management.system.model;

import lombok.Data;

@Data
public class SysScenicSpotRandomProblem {
    private Long problemId;

    private String randomProblem;

    private Long scenicSpotId;

    private String randomType;

    private String randomState;

    private String randomResRul;

    private String createDate;

    private String updateDate;

    private String weight;

    /**
     * 景区名称
     */
    private String scenicSpotName;
}