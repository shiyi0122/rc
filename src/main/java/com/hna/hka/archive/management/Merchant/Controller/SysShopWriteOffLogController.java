package com.hna.hka.archive.management.Merchant.Controller;


import com.github.pagehelper.PageInfo;
import com.hna.hka.archive.management.Merchant.model.SysShopWriteOffLog;
import com.hna.hka.archive.management.Merchant.service.SysShopWriteOffLogService;
import com.hna.hka.archive.management.system.util.Constant;
import com.hna.hka.archive.management.system.util.FileUtil;
import com.hna.hka.archive.management.system.util.ReturnModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

@Api(tags = "店员核销日志")
@RestController
@CrossOrigin
@RequestMapping("/Merchant/shopWriteOffLog")
public class SysShopWriteOffLogController {

    @Autowired
    private SysShopWriteOffLogService sysShopWriteOffLogService;

    @ApiOperation("查询核销日志列表")
    @PostMapping("/getShopWriteOffLogList")
    public ReturnModel getShopWriteOffLogList(SysShopWriteOffLog sysShopWriteOffLog) {
        ReturnModel returnModel = new ReturnModel();
        PageInfo<SysShopWriteOffLog> shopWriteOffLogList = sysShopWriteOffLogService.getShopWriteOffLogList(sysShopWriteOffLog);
        returnModel.setData(shopWriteOffLogList);
        returnModel.setMsg("查询成功！");
        returnModel.setState(Constant.STATE_SUCCESS);
        return returnModel;
    }

    @ApiOperation("导出核销日志表")
    @PostMapping("/exportShopWriteOffLog")
    public void exportShopWriteOffLog(HttpServletResponse response,SysShopWriteOffLog sysShopWriteOffLog) {
        List<SysShopWriteOffLog> sysShopWriteOffLogs = null;
        sysShopWriteOffLogs = sysShopWriteOffLogService.exportShopWriteOffLog(sysShopWriteOffLog);
        String dateTime = DateFormatUtils.format(new Date(), "yyyyMMddHHmm");
        FileUtil.exportExcel(FileUtil.getWorkbook("核销日志列表", "核销列表", SysShopWriteOffLog.class, sysShopWriteOffLogs), "核销日志列表" + dateTime + ".xls", response);
    }


}
