package com.hna.hka.archive.management.Merchant.service;

import com.github.pagehelper.PageInfo;
import com.hna.hka.archive.management.Merchant.model.SysShopWriteOffLog;

import java.util.List;

public interface SysShopWriteOffLogService {
    PageInfo<SysShopWriteOffLog> getShopWriteOffLogList(SysShopWriteOffLog sysShopWriteOffLog);

    List<SysShopWriteOffLog> exportShopWriteOffLog(SysShopWriteOffLog sysShopWriteOffLog);
}
