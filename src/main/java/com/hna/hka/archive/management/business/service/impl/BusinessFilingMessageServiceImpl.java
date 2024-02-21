package com.hna.hka.archive.management.business.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hna.hka.archive.management.business.dao.BusinessFilingMessageLogMapper;
import com.hna.hka.archive.management.business.dao.BusinessFilingMessageMapper;
import com.hna.hka.archive.management.business.dao.BusinessUsersMapper;
import com.hna.hka.archive.management.business.dao.FilingCycleMapper;
import com.hna.hka.archive.management.business.model.BusinessFilingCycle;
import com.hna.hka.archive.management.business.model.BusinessFilingMessage;
import com.hna.hka.archive.management.business.model.BusinessFilingMessageLog;
import com.hna.hka.archive.management.business.model.BusinessUsers;
import com.hna.hka.archive.management.business.service.BusinessFilingMessageService;
import com.hna.hka.archive.management.system.util.DateUtil;
import com.hna.hka.archive.management.system.util.IdUtils;
import com.hna.hka.archive.management.system.util.PageDataResult;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2022/9/21 16:21
 */
@Service
public class BusinessFilingMessageServiceImpl implements BusinessFilingMessageService {

    @Autowired
    BusinessFilingMessageMapper businessFilingMessageMapper;

    @Autowired
    BusinessFilingMessageLogMapper businessFilingMessageLogMapper;

    @Autowired
    BusinessUsersMapper businessUsersMapper;

    @Autowired
    FilingCycleMapper filingCycleMapper;



    /**
     * 查询报备景区列表
     * @param pageNum
     * @param pageSize
     * @param search
     * @return
     */
    @Override
    public PageDataResult getFilingMessageList(Integer pageNum, Integer pageSize, Map<String, Object> search) {

        PageDataResult pageDataResult = new PageDataResult();

        PageHelper.startPage(pageNum,pageSize);

        List<BusinessFilingMessage> list = businessFilingMessageMapper.getFilingMessageList(search);

        if (list.size()>0){
            PageInfo<BusinessFilingMessage> pageInfo = new PageInfo<>(list);
            pageDataResult.setData(list);
            pageDataResult.setCode(200);
            pageDataResult.setTotals((int)pageInfo.getTotal());
        }
        return pageDataResult;
    }

    /**
     * 导出报备景区列表
     * @param search
     * @return
     */
    @Override
    public List<BusinessFilingMessage> uploadExcelFilingMessage(Map<String, Object> search) {
        List<BusinessFilingMessage> list = businessFilingMessageMapper.getFilingMessageList(search);
        return list;
    }

    /**
     * 修改审核结果
     * @param businessFilingMessage
     * @return
     */
    @Override
    public int editFilingMessageResult(BusinessFilingMessage businessFilingMessage) {

        businessFilingMessage.setUpdateTime(DateUtil.currentDateTime());
        BusinessFilingMessage businessFilingMessageNew = businessFilingMessageMapper.selectByPrimaryKey(businessFilingMessage.getId());
        businessFilingMessageNew.setUpdateTime(DateUtil.currentDateTime());
        businessFilingMessageNew.setFindingsOfAudit(businessFilingMessage.getFindingsOfAudit());
        businessFilingMessageNew.setSigningTimeDetermine(DateUtil.currentDateTime());
        businessFilingMessageNew.setApprovalId(businessFilingMessage.getApprovalId());
        businessFilingMessageNew.setApprovalName(businessFilingMessage.getApprovalName());
        int i =  businessFilingMessageMapper.updateByPrimaryKeySelective(businessFilingMessageNew);
        return i;


    }

    /**
     * 添加报备信息
     * @param businessFilingMessage
     * @return
     */
    @Override
    public int addFilingMessage(BusinessFilingMessage businessFilingMessage) {


        businessFilingMessage.setId(IdUtils.getSeqId());
        String filingTime = businessFilingMessage.getFilingTime();
        String signingTime = DateUtil.addDay(filingTime, 30);
        businessFilingMessage.setSigningTime(signingTime);
        businessFilingMessage.setCreateTime(DateUtil.currentDateTime());
        businessFilingMessage.setUpdateTime(DateUtil.currentDateTime());
//        String filingRegion = businessFilingMessage.getProvince() + "-" + businessFilingMessage.getCity() + "-" + businessFilingMessage.getCounty();
//        businessFilingMessage.setFilingRegion(filingRegion);
        businessFilingMessage.setFilingState("1");
        businessFilingMessage.setFilingCycle("30");

        int i = businessFilingMessageMapper.insertSelective(businessFilingMessage);
        return i;
    }

    /**
     * 查询日志审核列表
     * @param pageNum
     * @param pageSize
     * @param id
     * @return
     */
    @Override
    public PageDataResult getFilingMessageLogList(Integer pageNum, Integer pageSize, Long id) {

        PageDataResult pageDataResult = new PageDataResult();
        Map<String, Object> search = new HashMap<>();
        search.put("messageId",id);
        PageHelper.startPage(pageNum,pageSize);
        List<BusinessFilingMessageLog> businessFilingMessageLogs = businessFilingMessageLogMapper.getFilingMessageLogList(search);

        if (businessFilingMessageLogs.size()>0){
            PageInfo<BusinessFilingMessageLog> pageInfo = new PageInfo<>(businessFilingMessageLogs);
            pageDataResult.setData(businessFilingMessageLogs);
            pageDataResult.setTotals((int)pageInfo.getTotal());
        }
        return pageDataResult;
    }

    //导出日志审核列表
    @Override
    public List<BusinessFilingMessageLog> uploadExcelFilingMessageAuditLog(Map<String, Object> search) {

        List<BusinessFilingMessageLog> businessFilingMessageLogs = businessFilingMessageLogMapper.getFilingMessageLogList(search);

        return  businessFilingMessageLogs;
    }

    //导入添加
    @Override
    public int importExcelEnter(BusinessFilingMessage businessFilingMessage) {

        Map<String, String> search = new HashMap<>();
        search.put("phone",businessFilingMessage.getFilingPhone());
        businessFilingMessage.setId(IdUtils.getSeqId());
        BusinessFilingCycle cycle = filingCycleMapper.getCycle();

        List<BusinessUsers> businessUsersList = businessUsersMapper.getBusinessUsersList(search);
        if (businessUsersList.size()>0){
            businessFilingMessage.setFilingId(businessUsersList.get(0).getId());
            businessFilingMessage.setSubmitId(businessUsersList.get(0).getId());
        }
        businessFilingMessage.setFilingState("1");
        businessFilingMessage.setFilingTime(DateUtil.currentDateTime());
        businessFilingMessage.setCreateTime(DateUtil.currentDateTime());
        businessFilingMessage.setUpdateTime(DateUtil.currentDateTime());
        businessFilingMessage.setSigningTime(DateUtil.addDay(DateUtil.currentDateTime(),Integer.parseInt(cycle.getFilingCycle())));

        List<BusinessFilingMessage> list = businessFilingMessageMapper.getSpotNameFilingMessage(businessFilingMessage.getFilingSpotName());

        if (list.size()>0){
            businessFilingMessage.setFindingsOfAudit("7");
        }else{
            businessFilingMessage.setFindingsOfAudit("4");
        }

        int i = businessFilingMessageMapper.insertSelective(businessFilingMessage);

        return i;
    }
}
