package com.hna.hka.archive.management.appSystem.model;

import lombok.Data;

import java.util.Date;

/**
 * @ProjectName: IDEA-CODE
 * @Package: com.hna.hka.archive.management.appSystem.model
 * @ClassName: SysAppLoginLog
 * @Author: 郭凯
 * @Description:
 * @Date: 2020/11/23 13:45
 * @Version: 1.0
 */
@Data
public class SysAppLoginLog {

    private Long loginLogId;

    private String operationPeople;

    private Date operationTime;

    private String operationAction;


}
