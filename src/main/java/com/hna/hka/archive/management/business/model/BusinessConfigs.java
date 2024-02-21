package com.hna.hka.archive.management.business.model;

import lombok.Data;

/**
 * @Author zhang
 * @Date 2023/7/11 13:34
 */
@Data
public class BusinessConfigs {

    private Long id ;

    private String title;

    private String values;

    private String type;

    private String state;

    private String createTime;

    private String updateTime;


}
