package com.hna.hka.archive.management.system.model;

import cn.afterturn.easypoi.excel.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SysRobotDispatchLog {
    private Long robotDispatchId;

    @ApiModelProperty("机器人调出景区名称")
    @Excel(name = "机器人调出景区名称" , width = 30 , orderNum = "1")
    private String robotDispatchCallOutName;

    @ApiModelProperty("机器人调入景区名称")
    @Excel(name = "机器人调入景区名称" , width = 30 , orderNum = "2")
    private String robotDispatchTransferInName;

    @ApiModelProperty("调度人名称")
    @Excel(name = "调度人名称" , width = 30 , orderNum = "3")
    private String robotDispatchUsersName;

    @ApiModelProperty("机器人编号")
    @Excel(name = "机器人编号" , width = 20 , orderNum = "0")
    private String robotDispatchCode;

    @ApiModelProperty("调度日期")
    @Excel(name = "调度日期" , width = 20 , orderNum = "4")
    private String createDate;

    private String updateDate;

    @ApiModelProperty("发车日期")
    @Excel(name = "发车日期" , width = 20 , orderNum = "4")
    private String departureTime;
    @Excel(name = "到达日期" , width = 20 , orderNum = "4")
    @ApiModelProperty("到达日期")
    private String arrivalTime;
    @ApiModelProperty("完成日期")
    @Excel(name = "完成日期" , width = 20 , orderNum = "4")
    private String completionTime;

}