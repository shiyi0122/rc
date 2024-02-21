package com.hna.hka.archive.management.system.model;

import lombok.Data;

@Data
public class SysScenicSpotGpsCoordinate {
    private Long coordinateId;

    private Long coordinateScenicSpotId;

    private String insideWarning;

    private String coordinateParkingType;

    private String createDate;

    private String updateDate;

    private String scenicSpotName;

}