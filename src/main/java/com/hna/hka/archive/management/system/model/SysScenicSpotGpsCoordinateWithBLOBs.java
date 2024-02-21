package com.hna.hka.archive.management.system.model;

import lombok.Data;

@Data
public class SysScenicSpotGpsCoordinateWithBLOBs extends SysScenicSpotGpsCoordinate {
    private String coordinateOuterring;

    private String coordinateOuterringBaiDu;

    private String warningLoopCoordinateGroup;

}