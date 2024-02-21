package com.hna.hka.archive.management.system.model;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

@Data
public class SysRobotExcel {
	
	@Excel(name = "机器人编号",width = 10,orderNum = "0")
	private String robotCode;
	
	@Excel(name = "景区名称",width = 10,orderNum = "1")
	private String scenicSpotName;
	
	@Excel(name = "机器人ICCID",width = 30,orderNum = "2")
	private String robotCodeSim;

	@Excel(name = "蓝牙编号",width = 30,orderNum = "3")
	private String robotBluetooth;

	@Excel(name = "机器人版本号",width = 10,orderNum = "3")
	private String robotVersionNumber;

	@Excel(name = "机器人设备状态" ,width = 10,orderNum = "4")
	private String robotRunState;

	@Excel(name = "机器人备注",width = 20,orderNum = "5")
	private String robotRemarks;

	@Excel(name = "机器人PAD版本号",width = 20,orderNum = "6")
	private String  clientVersion;

	@Excel(name = "机器人批次号",width = 20,orderNum = "6")
	private String  robotBatchNumber;
	@Excel(name = "创建时间",width = 10,orderNum = "7")
	private String createDate;



}
