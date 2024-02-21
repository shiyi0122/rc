package com.hna.hka.archive.management.system.model;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

/**
 * @Author zhang
 * @Date 2022/12/1 14:33
 */
@Data
public class SysCurrentUserAccountDeduction {

    private Long  deductionId;

    private Long orderId ;
    @Excel(name = "用户手机号", width = 20, orderNum = "0")
    private String userPhone;

    private Long  userId;
    @Excel(name = "景区名称", width = 20, orderNum = "1")
    private String scenicSpotName;

    private Long  scenicSpotId;
    @Excel(name = "订单编号", width = 20, orderNum = "2")
    private String orderNumber ;
    @Excel(name = "账户原金额", width = 20, orderNum = "3")
    private String  accountAmountFront ;
    @Excel(name = "账户操作金额", width = 20, orderNum = "4")
    private String  deductionAmount;
    @Excel(name = "账户操作后金额", width = 20, orderNum = "5")
    private String  accountAmountRear;

    private String  accountType;
    @Excel(name = "创建时间", width = 20, orderNum = "6")
    private String  createTime;
    @Excel(name = "修改时间", width = 20, orderNum = "7")
    private String  updateTime;





}
