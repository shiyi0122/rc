package com.hna.hka.archive.management.business.model;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

/**
 * @Author zhang
 * @Date 2022/10/26 16:26
 */
@Data
public class BusinessFilingMessageLog {

    private Long id;

    private Long  filingId;
    @Excel(name = "报备人名称" , orderNum = "1" ,width = 20)
    private String  filingName;
    @Excel(name = "报备日期" , orderNum = "2" ,width = 20)
    private String  filingTime;

    private String  examineId;
    @Excel(name = "审核人名称" , orderNum = "3" ,width = 20)
    private String  examineName;
    @Excel(name = "审核日期" , orderNum = "4" ,width = 20)
    private String  auditDate;
    @Excel(name = "审核结果" ,replace = {"通过_2" , "驳回_3"},orderNum = "5" ,width = 20)
    private String  findingsOfAudit;
    @Excel(name = "原因" ,orderNum = "6" ,width = 20)
    private String  reson;
    @Excel(name = "创建时间" ,orderNum = "7" ,width = 20)
    private String  createTime;

    private String  messageId;


}
