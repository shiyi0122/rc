package com.hna.hka.archive.management.system.model;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

@Data
public class SysScenicSpotPriceModificationLogVo {
	
	@Excel(name = "修改人名称",orderNum = "0",width = 20)
    private String priceModificationUserName;

    @Excel(name = "景区名称",orderNum = "1",width = 30)
    private String priceModificationSpotName;
	
    @Excel(name = "景区联系人",orderNum = "2",width = 30)
    private String scenicSpotContact;

    @Excel(name = "景区联系人电话",orderNum = "3",width = 30)
    private String scenicSpotPhone;

    @Excel(name = "景区归属地",orderNum = "4",width = 30)
    private String scenicSpotProvince;

    @Excel(name = "测试开始时间",orderNum = "5",width = 30)
    private String testStartTime;

    @Excel(name = "试运营开始时间",orderNum = "6",width = 30)
    private String trialOperationsTime;

    @Excel(name = "正式运营时间",orderNum = "7",width = 30)
    private String formalOperationTime;

    @Excel(name = "停止运营时间",orderNum = "8",width = 30)
    private String stopOperationTime;
    
    @Excel(name = "修改时间",orderNum = "9",width = 20)
    private String createDate;

}
