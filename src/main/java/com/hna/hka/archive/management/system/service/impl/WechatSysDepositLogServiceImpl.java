package com.hna.hka.archive.management.system.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hna.hka.archive.management.system.dao.WechatSysDepositLogMapper;
import com.hna.hka.archive.management.system.model.WechatSysDepositLog;
import com.hna.hka.archive.management.system.service.WechatSysDepositLogService;
import com.hna.hka.archive.management.system.util.PageDataResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @ProjectName: IDEA-CODE
 * @Package: com.hna.hka.archive.management.system.service.impl
 * @ClassName: WechatSysDepositLogServiceImpl
 * @Author: 郭凯
 * @Description: 押金退款日志业务层（实现）
 * @Date: 2020/5/28 17:40
 * @Version: 1.0
 */
@Service
@Transactional
public class WechatSysDepositLogServiceImpl implements WechatSysDepositLogService {

    @Autowired
    private WechatSysDepositLogMapper wechatSysDepositLogMapper;

    /**
     * @Author 郭凯
     * @Description 押金退款日志列表查询
     * @Date 17:42 2020/5/28
     * @Param [pageNum, pageSize, search]
     * @return com.hna.hka.archive.management.system.util.PageDataResult
    **/
    @Override
    public PageDataResult getSysDepositLogList(Integer pageNum, Integer pageSize, Map<String, Object> search) {
        PageDataResult pageDataResult = new PageDataResult();
        PageHelper.startPage(pageNum, pageSize);
        List<WechatSysDepositLog> sysDepositLogList = wechatSysDepositLogMapper.getSysDepositLogList(search);
        if (sysDepositLogList.size() != 0){
            PageInfo<WechatSysDepositLog> pageInfo = new PageInfo<>(sysDepositLogList);
            pageDataResult.setList(pageInfo.getList());
            pageDataResult.setTotals((int) pageInfo.getTotal());
        }
        return pageDataResult;
    }

    /**
     * @Author 郭凯
     * @Description 退款日志Excel表下载
     * @Date 14:28 2020/5/29
     * @Param [search]
     * @return java.util.List<com.hna.hka.archive.management.system.model.WechatSysDepositLog>
    **/
    @Override
    public List<WechatSysDepositLog> getUploadExcelPaybackLog(Map<String, Object> search) {
        return wechatSysDepositLogMapper.getSysDepositLogList(search);
    }
}
