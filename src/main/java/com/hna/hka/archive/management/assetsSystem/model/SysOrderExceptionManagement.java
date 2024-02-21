package com.hna.hka.archive.management.assetsSystem.model;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

@Data
public class SysOrderExceptionManagement {
    private Long orderExceptionManagementId;

    private String causes;

    @Excel(name = "上报原因" , orderNum = "1" , width = 10)
    private String reason;

    @Excel(name = "备注" , orderNum = "2" , width = 10)
    private String remarks;

    private String createTime;

    private String updateTime;

    @Excel(name = "上报原因大类" , orderNum = "0" , width = 10)
    private String causesName;

}