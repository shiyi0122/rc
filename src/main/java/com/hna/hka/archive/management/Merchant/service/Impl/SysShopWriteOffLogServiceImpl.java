package com.hna.hka.archive.management.Merchant.service.Impl;

import com.github.pagehelper.PageInfo;
import com.hna.hka.archive.management.Merchant.dao.SysShopWriteOffLogMapper;
import com.hna.hka.archive.management.Merchant.model.SysShopWriteOffLog;
import com.hna.hka.archive.management.Merchant.service.SysShopWriteOffLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("SysShopWriteOffLogService")
@Transactional
public class SysShopWriteOffLogServiceImpl implements SysShopWriteOffLogService {

    @Autowired
    private SysShopWriteOffLogMapper sysShopWriteOffLogMapper;

    @Override
    public PageInfo<SysShopWriteOffLog> getShopWriteOffLogList(SysShopWriteOffLog sysShopWriteOffLog) {
        List<SysShopWriteOffLog> shopWriteOffLogList = sysShopWriteOffLogMapper.getShopWriteOffLogList(sysShopWriteOffLog);
        return new PageInfo<>(shopWriteOffLogList);
    }

    @Override
    public List<SysShopWriteOffLog> exportShopWriteOffLog(SysShopWriteOffLog sysShopWriteOffLog) {
        return sysShopWriteOffLogMapper.getShopWriteOffLogList(sysShopWriteOffLog);
    }
}
