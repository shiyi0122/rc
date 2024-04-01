package com.hna.hka.archive.management.Merchant.model;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

@Data
public class OrderVo {

    @Excel(name = "订单编号")
    private String orderNumber;

    @Excel(name ="退款原因")
    private String reasonsRefunds;
}
