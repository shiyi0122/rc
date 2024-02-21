package com.hna.hka.archive.management.assetsSystem.model;

import lombok.Data;

/**
 * @Author zhang
 * @Date 2022/1/24 14:01
 */
@Data
public class SearchRobot {

    private String startDate;

    private String endDate;

    private String type;

    private String robotCode;

    private String spotId;

    private String startTime;

    private String endTime;

    private Integer pageNum;

    private Integer pageSize;

    private String sort;



}
