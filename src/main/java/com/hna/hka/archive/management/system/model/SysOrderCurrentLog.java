package com.hna.hka.archive.management.system.model;

import lombok.Data;

/**
 * @Author zhang
 * @Date 2022/12/21 17:45
 */
@Data
public class SysOrderCurrentLog {

    private Long id;

    private Long orderId;

    private String orderNumber;

    private String  frontPhone;

    private String  afterPhone;

    private String  frontPrice;

    private String  afterPrice;

    private String createTime;

    private String updateTime;

    private String operatorId;

    private String userName;


}
