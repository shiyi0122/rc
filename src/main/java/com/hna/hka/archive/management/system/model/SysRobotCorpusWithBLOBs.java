package com.hna.hka.archive.management.system.model;

import lombok.Data;

@Data
public class SysRobotCorpusWithBLOBs extends SysRobotCorpus {
    private String corpusAnswer;

    private String corpusResUrlPic;
}