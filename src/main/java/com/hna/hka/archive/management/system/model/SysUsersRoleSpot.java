package com.hna.hka.archive.management.system.model;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

@Data
public class SysUsersRoleSpot {
    private Long id;

    private Long userId;

    private Long scenicSpotId;

    private Long roleId;

    @Excel(name = "添加时间" ,width = 20,orderNum = "3")
    private String createDate;

    /**
     * 用户名称
     */
    @Excel(name = "用户名称" ,width = 20,orderNum = "0")
    private String userName;

    /**
     * 景区名称
     */
    @Excel(name = "景区名称" ,width = 20,orderNum = "1")
    private String scenicSpotName;

    /**
     * 角色名称
     */
    @Excel(name = "角色名称" ,width = 20,orderNum = "2")
    private String roleName;

}