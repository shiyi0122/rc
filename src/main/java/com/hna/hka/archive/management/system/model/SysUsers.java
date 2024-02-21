package com.hna.hka.archive.management.system.model;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

@Data
public class SysUsers {

    private Long userId;

    private Long scenicSpotId;

    @Excel(name = "登陆名称",orderNum = "0")
    private String loginName;

    @Excel(name = "用户名称",orderNum = "1")
    private String userName;

    @Excel(name = "用户性别",replace = {"男_1", "女_2"}, orderNum = "2")
    private String userSex;

    @Excel(name = "入职日期/合作日期",orderNum = "3")
    private String userBirthday;

    @Excel(name = "办公地址/联系地址",orderNum = "4")
    private String userUnitAddress;

    private String userEmail;

    private String password;

    private String saltValue;

    @Excel(name = "用户分类",orderNum = "5",replace = {"内部员工_10", "外部员工_20" ,"合伙人_30" , "景区方_40"})
    private String userRoleState;

    private String userState;

    @Excel(name = "用户手机号",orderNum = "6")
    private String userPhone;

    private String longinTokenId;

    private String userClientGtId;

    private String userClient;

    private String createDate;

    private String updateDate;


    //审核字段
    private String examineState;
}
