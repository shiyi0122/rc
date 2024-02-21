package com.hna.hka.archive.management.system.model;

import lombok.Data;

@Data
public class SysScenicSpotTimingProblemWithBLOBs extends SysScenicSpotTimingProblem {
    private String timingAnswers;

    private String timingResRulPic;
}