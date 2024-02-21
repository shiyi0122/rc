package com.hna.hka.archive.management.system.model;

import lombok.Data;
import org.springframework.util.StringUtils;

/**
 * @Author zhang
 * @Date 2022/8/8 14:38
 */
@Data
public class SysCurrentUserAccountRefundLog {

    private  Long  accountLogId;

    private String accountLogUserPhone;

    private String accountLogLoginName;

    private String accountLogBefore;

    private String  accountLogAfter;

    private String createDate;

    private String updateDate;

    private String accountLogPrice;

}
