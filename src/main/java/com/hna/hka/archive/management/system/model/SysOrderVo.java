package com.hna.hka.archive.management.system.model;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

/**
 * @ProjectName: IDEA-CODE
 * @Package: com.hna.hka.archive.management.system.model
 * @ClassName: SysOrderVo
 * @Author: 郭凯
 * @Description: 客流量管理Excel下载实体
 * @Date: 2020/11/10 11:35
 * @Version: 1.0
 */
@Data
public class SysOrderVo {

    @Excel(name = "用户手机号",width = 20,orderNum = "0")
    private String currentUserPhone;

    @Excel(name = "景区名称",width = 30,orderNum = "1")
    private String orderScenicSpotName;

    @Excel(name = "时间",width = 30,orderNum = "2")
    private String orderStartTime;

}
