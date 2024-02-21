package com.hna.hka.archive.management.system.model;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

@Data
public class SysScenicSpotCertificateSpot {
    private Long certificateSpotId;

    private Long scenicSpotId;

    private Long certificateId;

    @Excel(name = "分配时间" , orderNum = "2", width = 20)
    private String createDate;

    private String updateDate;

    /**
     * 景区名称
     */
    @Excel(name = "景区名称" , orderNum = "0", width = 20)
    private String scenicSpotName;

    /**
     * 证书名称
     */
    @Excel(name = "证书名称" , orderNum = "1", width = 20)
    private String merchantName;

    private String certFileName;
    private String merchantSecret;
    private String merchantNumber;
}