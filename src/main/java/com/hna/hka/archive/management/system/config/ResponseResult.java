package com.hna.hka.archive.management.system.config;

import lombok.Data;

import java.io.Serializable;

/**
 *  前端请求响应结果,code:编码,message:描述,obj对象，可以是单个数据对象，数据列表或者PageInfo
 */

@Data
public class ResponseResult implements Serializable {

    private String code;
    private String message;
    private Object obj;

}
