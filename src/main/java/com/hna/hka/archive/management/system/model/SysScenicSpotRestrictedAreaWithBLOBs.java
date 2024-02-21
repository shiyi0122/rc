package com.hna.hka.archive.management.system.model;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

@Data
public class SysScenicSpotRestrictedAreaWithBLOBs extends SysScenicSpotRestrictedArea {
	
	@Excel(name = "84坐标", width = 60 , orderNum = "5")
    private String restrictedWarningGps;

	@Excel(name = "百度坐标", width = 60 , orderNum = "6")
    private String restrictedWarningBaiduGps;
}