package com.hna.hka.archive.management.business.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hna.hka.archive.management.business.dao.BusinessInvoiceRecordMapper;
import com.hna.hka.archive.management.business.model.BusinessInvoiceRecord;
import com.hna.hka.archive.management.business.service.BusinessInvoiceRecordService;
import com.hna.hka.archive.management.system.util.PageDataResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @ProjectName: IDEA-CODE
 * @Package: com.hna.hka.archive.management.business.service.impl
 * @ClassName: BusinessInvoiceRecordServiceImpl
 * @Author: 郭凯
 * @Description: 发票管理业务层（实现）
 * @Date: 2020/8/13 9:38
 * @Version: 1.0
 */
@Service
public class BusinessInvoiceRecordServiceImpl implements BusinessInvoiceRecordService {

    @Autowired
    private BusinessInvoiceRecordMapper businessInvoiceRecordMapper;

    /**
     * @Author 郭凯
     * @Description 发票管理列表查询
     * @Date 9:40 2020/8/13
     * @Param [pageNum, pageSize, search]
     * @return com.hna.hka.archive.management.system.util.PageDataResult
    **/
    @Override
    public PageDataResult getInvoiceRecordList(Integer pageNum, Integer pageSize, Map<String, String> search) {
        PageDataResult pageDataResult = new PageDataResult();
        PageHelper.startPage(pageNum, pageSize);
        List<BusinessInvoiceRecord> businessInvoiceRecordList = businessInvoiceRecordMapper.getInvoiceRecordList(search);
        if (businessInvoiceRecordList.size() != 0){
            PageInfo<BusinessInvoiceRecord> pageInfo = new PageInfo<>(businessInvoiceRecordList);
            pageDataResult.setList(pageInfo.getList());
            pageDataResult.setTotals((int) pageInfo.getTotal());
        }
        return pageDataResult;
    }
}
