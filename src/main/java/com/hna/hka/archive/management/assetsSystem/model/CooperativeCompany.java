package com.hna.hka.archive.management.assetsSystem.model;

import lombok.Data;
import lombok.Getter;

/**
 * @program: rc
 * @description: 合作公司实体类
 * @author: zhaoxianglong
 * @create: 2021-09-08 14:26
 **/
@Data
public class CooperativeCompany {

    /**
     * id
     */
    private Long companyId;

    /**
     * 名称
     */
    private String companyName;

    /**
     * 收款账户
     */
    private String collectionAccount;

    /**
     * 收款账号
     */
    private String collectionAccountNumber;

    /**
     * 开户行
     */
    private String bank;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 备注
     */
    private String notes;
}
