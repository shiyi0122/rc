package com.hna.hka.archive.management.system.service;

import com.hna.hka.archive.management.system.model.WechatSysDepositLog;
import com.hna.hka.archive.management.system.util.PageDataResult;

import java.util.List;
import java.util.Map;

/**
 * @ProjectName: IDEA-CODE
 * @Package: com.hna.hka.archive.management.system.service
 * @ClassName: WechatSysDepositLogService
 * @Author: 郭凯
 * @Description: 押金退款日志业务层（接口）
 * @Date: 2020/5/28 17:39
 * @Version: 1.0
 */
public interface WechatSysDepositLogService {

    /**
     * 押金退款日志列表查询
     * @param pageNum
     * @param pageSize
     * @param search
     * @return
     */
    PageDataResult getSysDepositLogList(Integer pageNum, Integer pageSize, Map<String, Object> search);

    /**
     * 退款日志Excel表下载
     * @param search
     * @return
     */
    List<WechatSysDepositLog> getUploadExcelPaybackLog(Map<String, Object> search);
}
