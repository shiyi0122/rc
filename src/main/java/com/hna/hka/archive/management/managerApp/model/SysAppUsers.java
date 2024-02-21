package com.hna.hka.archive.management.managerApp.model;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

@Data
public class SysAppUsers {
    private Long userId;

    private String loginName;

    @Excel(name = "用户名称" ,width = 20 , orderNum = "0")
    private String userName;

    private String password;

    private String saltValue;

    private String userState;

    private String longinTokenId;

    private String userClientGtId;

    private String userType;

    private String createDate;

    private String updateDate;
    
    private String userGps;
    
    @Excel(name = "景区名称" ,width = 20 , orderNum = "1")
    private String scenicSpotName;
    
    @Excel(name = "角色名称" ,width = 20 , orderNum = "2")
    private String roleName;

    private String userApproval;

}