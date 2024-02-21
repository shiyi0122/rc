package com.hna.hka.archive.management.system.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.coyote.ajp.AjpProcessor;

/**
 * @Author zhang
 * @Date 2022/2/25 14:26
 */
@Data
public class SysRobotCompanyAscription {

    @ApiModelProperty("id")
    private Long  robotCompanyAscriptionId ;
    @ApiModelProperty("机器人编号")
    private String robotCode;
    @ApiModelProperty("公司id")
    private String companyId;
    @ApiModelProperty("机器人编号")
    private String robotId;

    @ApiModelProperty("创建时间")
    private String createDate;
    @ApiModelProperty("修改时间")
    private String  updateDate;

    private String  companyName;
}
