package com.hna.hka.archive.management.system.model;

import lombok.Data;

@Data
public class SysScenicSpotImg {
    private Long scneicSpotImgId;

    private Long scenicSpotId;

    private String scneicSpotImgUrl;

    private String createDate;

    private String updateDate;

    private String scenicSpotName;

}