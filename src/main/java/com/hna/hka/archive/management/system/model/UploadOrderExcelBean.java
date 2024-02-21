package com.hna.hka.archive.management.system.model;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

@Data
public class UploadOrderExcelBean {
	
	@Excel(name = "用户手机号",width = 20,orderNum = "0")
    private String currentUserPhone;
	
	@Excel(name = "机器人编号",width = 20,orderNum = "1")
    private String orderRobotCode;
	
	@Excel(name = "景区名称",width = 30,orderNum = "2")
    private String orderScenicSpotName;
	
	@Excel(name = "实际收入金额",width = 20,orderNum = "3")
    private Float realIncome;
	@Excel(name = "退款金额",width = 20,orderNum = "4")
	private Float orderRefundAmount;
	
	@Excel(name = "腾讯手续费费率",width = 20,orderNum = "5")
    private String tenCentCommission;
	
	@Excel(name = "最终入账金额" ,width = 20,orderNum = "6")
    private String paymentTotalAccount;
	
	@Excel(name = "支付状态",width = 20,orderNum = "7")
    private String orderStatusName;
	
	@Excel(name = "订单日期",width = 20,orderNum = "8")
	private String createDate;


	
	private String orderStatus;
	
	private String orderStartTime;
	
	

}
