package com.hna.hka.archive.management.Merchant.dao;


import com.hna.hka.archive.management.Merchant.model.SysShopWriteOffLog;

import java.util.List;

public interface SysShopWriteOffLogMapper {
    int deleteByPrimaryKey(Long writeOffId);

    int addShopWriteOffLog(SysShopWriteOffLog sysShopWriteOffLog);

    List<SysShopWriteOffLog> getShopWriteOffLogList(SysShopWriteOffLog sysShopWriteOffLog);

    int updateByPrimaryKeySelective(SysShopWriteOffLog record);

    int updateByPrimaryKey(SysShopWriteOffLog record);
}