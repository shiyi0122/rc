package com.hna.hka.archive.management.assetsSystem.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hna.hka.archive.management.assetsSystem.dao.SysInvoiceMapper;
import com.hna.hka.archive.management.assetsSystem.model.SysInvoice;
import com.hna.hka.archive.management.assetsSystem.service.SysInvoiceService;
import com.hna.hka.archive.management.system.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @ProjectName: rc
 * @Package: com.hna.hka.archive.management.assetsSystem.service.impl
 * @ClassName: SysInvoiceServiceImpl
 * @Author: 郭凯
 * @Description: 发票申请记录管理业务层（实现）
 * @Date: 2021/6/25 18:42
 * @Version: 1.0
 */
@Service
@Transactional
public class SysInvoiceServiceImpl implements SysInvoiceService {

    @Autowired
    private SysInvoiceMapper sysInvoiceMapper;


    /**
     * @Method getInvoiceList
     * @Author 郭凯
     * @Version  1.0
     * @Description 发票申请管理列表查询
     * @Return com.hna.hka.archive.management.system.util.PageDataResult
     * @Date 2021/6/25 18:47
     */
    @Override
    public PageDataResult getInvoiceList(Integer pageNum, Integer pageSize, Map<String, Object> search) {
        PageDataResult pageDataResult = new PageDataResult();
        PageHelper.startPage(pageNum, pageSize);
        List<SysInvoice> invoiceList = sysInvoiceMapper.getInvoiceList(search);
        if (invoiceList.size() > 0){
            PageInfo<SysInvoice> pageInfo = new PageInfo<>(invoiceList);
            pageDataResult.setList(pageInfo.getList());
            pageDataResult.setTotals((int) pageInfo.getTotal());
        }
        return pageDataResult;
    }

    /**
     * @Method addInvoice
     * @Author 郭凯
     * @Version  1.0
     * @Description 上传发票申请
     * @Return int
     * @Date 2021/6/25 20:02
     */
    @Override
    public int addInvoice(SysInvoice sysInvoice) {
        SysInvoice sysInvoices = sysInvoiceMapper.getInvoiceByNumber(sysInvoice.getOrderNumber());
        if (ToolUtil.isNotEmpty(sysInvoices)){
            return 2;
        }
        sysInvoice.setInvoiceId(IdUtils.getSeqId());
        sysInvoice.setCreateTime(DateUtil.currentDateTime());
        sysInvoice.setUpdateTime(DateUtil.currentDateTime());
        return sysInvoiceMapper.insertSelective(sysInvoice);
    }

    @Override
    /**
     * @Method editBilling
     * @Author 郭凯
     * @Version  1.0
     * @Description 修改为已开票状态
     * @Return int
     * @Date 2021/6/26 12:05
     */
    public int editBilling(SysInvoice sysInvoice) {
        sysInvoice.setUpdateTime(DateUtil.currentDateTime());
        return sysInvoiceMapper.updateByPrimaryKeySelective(sysInvoice);
    }

    /**
     * @Method getInvoiceExcelList
     * @Author 郭凯
     * @Version  1.0
     * @Description 开票管理下载Excel表
     * @Return java.util.List<com.hna.hka.archive.management.assetsSystem.model.SysInvoice>
     * @Date 2021/6/26 15:17
     */
    @Override
    public List<SysInvoice> getInvoiceExcelList(Map<String, Object> search) {
        List<SysInvoice> invoiceList = sysInvoiceMapper.getInvoiceList(search);
        for (SysInvoice invoice : invoiceList){
            invoice.setInvoiceTypeName(DictUtils.getInvoiceTypeMap().get(invoice.getInvoiceType()));
            invoice.setRiseTypeName(DictUtils.getRiseTypeMapMap().get(invoice.getRiseType()));
            invoice.setProcessingProgressName(DictUtils.getProcessingProgressMap().get(invoice.getProcessingProgress()));
        }
        return invoiceList;
    }
}
