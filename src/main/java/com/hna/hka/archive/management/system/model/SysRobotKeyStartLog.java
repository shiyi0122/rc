package com.hna.hka.archive.management.system.model;

import cn.afterturn.easypoi.excel.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author zhang
 * @Date 2022/6/14 10:35
 */
@Data
public class SysRobotKeyStartLog {


    private Long robotKeyId;

    @ApiModelProperty("机器人编号")
    @Excel(name = "机器人编号" , width = 30 , orderNum = "1")
    private String robotCode;



    private Long scenicSpotId;
    @ApiModelProperty("景区名称")
    @Excel(name = "景区名称" , width = 30 , orderNum = "3")
    private String scenicSpotName;

    @ApiModelProperty("启动类型")
    @Excel(name = "启动类型" , width = 30 , orderNum = "4")
    private String robotKeyType;

    @ApiModelProperty("启动时间")
    @Excel(name = "启动时间" , width = 30 , orderNum = "5")
    private String robotKeyStart;

    @ApiModelProperty("停止时间")
    @Excel(name = "停止时间" , width = 30 , orderNum = "6")
    private String robotKeyStop;


    @ApiModelProperty("总时长")
    private String robotTotalTime;

    @ApiModelProperty("创建时间")
    @Excel(name = "创建时间" , width = 30 , orderNum = "7")
    private String createDate;
    @ApiModelProperty("修改时间")
    private String updateDate;




}
