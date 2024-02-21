package com.hna.hka.archive.management.system.service.impl;

import com.hna.hka.archive.management.system.dao.SysCurrentUserAccountRefundLogMapper;
import com.hna.hka.archive.management.system.model.SysCurrentUserAccountRefundLog;
import com.hna.hka.archive.management.system.service.SysCurrentUserAccountRefundLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author zhang
 * @Date 2022/8/8 15:17
 */
@Service
public class SysCurrentUserAccountRefundLogServiceImpl implements SysCurrentUserAccountRefundLogService {

    @Autowired
    SysCurrentUserAccountRefundLogMapper sysCurrentUserAccountRefundLogMapper;

    /**
     * 添加用户储值金额变化日志
     * @param sysCurrentUserAccountRefundLog
     * @return
     */
    @Override
    public int insertAccountRefundLog(SysCurrentUserAccountRefundLog sysCurrentUserAccountRefundLog) {

       int i =   sysCurrentUserAccountRefundLogMapper.insertAccountRefundLog(sysCurrentUserAccountRefundLog);
       return i;
    }
}
