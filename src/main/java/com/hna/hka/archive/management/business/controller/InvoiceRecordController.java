package com.hna.hka.archive.management.business.controller;

import com.hna.hka.archive.management.business.model.BusinessInvoiceRecord;
import com.hna.hka.archive.management.business.service.BusinessInvoiceRecordService;
import com.hna.hka.archive.management.system.util.PageDataResult;
import com.hna.hka.archive.management.system.util.PublicUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * @ProjectName: IDEA-CODE
 * @Package: com.hna.hka.archive.management.business.controller
 * @ClassName: InvoiceRecordController
 * @Author: 郭凯
 * @Description: 发票管理控制层
 * @Date: 2020/8/13 9:35
 * @Version: 1.0
 */
@RequestMapping("/business/invoiceRecord")
@Controller
public class InvoiceRecordController extends PublicUtil {

    @Autowired
    private BusinessInvoiceRecordService businessInvoiceRecordService;

    /**
     * @Author 郭凯
     * @Description 发票管理列表查询
     * @Date 9:39 2020/8/13
     * @Param [pageNum, pageSize, businessInvoiceRecord]
     * @return com.hna.hka.archive.management.system.util.PageDataResult
    **/
    @RequestMapping("/getInvoiceRecordList")
    @ResponseBody
    public PageDataResult getInvoiceRecordList(@RequestParam("pageNum") Integer pageNum,
                                              @RequestParam("pageSize") Integer pageSize, BusinessInvoiceRecord businessInvoiceRecord) {
        PageDataResult pageDataResult = new PageDataResult();
        Map<String,String> search = new HashMap<>();
        try {
            if(null == pageNum) {
                pageNum = 1;
            }
            if(null == pageSize) {
                pageSize = 10;
            }
            pageDataResult = businessInvoiceRecordService.getInvoiceRecordList(pageNum,pageSize,search);
        }catch (Exception e){
            logger.info("发票管理列表查询失败",e);
        }
        return pageDataResult;
    }

}
