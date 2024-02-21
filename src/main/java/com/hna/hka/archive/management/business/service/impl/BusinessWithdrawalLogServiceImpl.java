package com.hna.hka.archive.management.business.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hna.hka.archive.management.business.dao.BusinessUsersMapper;
import com.hna.hka.archive.management.business.dao.BusinessWithdrawalLogMapper;
import com.hna.hka.archive.management.business.model.BusinessUsers;
import com.hna.hka.archive.management.business.model.BusinessWithdrawalLog;
import com.hna.hka.archive.management.business.service.BusinessWithdrawalLogService;
import com.hna.hka.archive.management.system.util.DateUtil;
import com.hna.hka.archive.management.system.util.DictUtils;
import com.hna.hka.archive.management.system.util.PageDataResult;
import com.hna.hka.archive.management.system.util.ToolUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @ProjectName: IDEA-CODE
 * @Package: com.hna.hka.archive.management.business.service.impl
 * @ClassName: BusinessWithdrawalLogServiceImpl
 * @Author: 郭凯
 * @Description: 提现申请管理业务层（实现）
 * @Date: 2020/8/13 10:42
 * @Version: 1.0
 */
@Service
public class BusinessWithdrawalLogServiceImpl implements BusinessWithdrawalLogService {

    @Autowired
    private BusinessWithdrawalLogMapper businessWithdrawalLogMapper;

    @Autowired
    private BusinessUsersMapper businessUsersMapper;

    /**
     * @Author 郭凯
     * @Description 提现申请管理列表查询
     * @Date 10:45 2020/8/13
     * @Param [pageNum, pageSize, search]
     * @return com.hna.hka.archive.management.system.util.PageDataResult
    **/
    @Override
    public PageDataResult getWithdrawalLogList(Integer pageNum, Integer pageSize, Map<String, String> search) {
        PageDataResult pageDataResult = new PageDataResult();
        PageHelper.startPage(pageNum, pageSize);
        List<BusinessWithdrawalLog> businessWithdrawalLogList = businessWithdrawalLogMapper.getWithdrawalLogList(search);
        if (businessWithdrawalLogList.size() != 0){
            PageInfo<BusinessWithdrawalLog> pageInfo = new PageInfo<>(businessWithdrawalLogList);
            pageDataResult.setList(pageInfo.getList());
            pageDataResult.setTotals((int) pageInfo.getTotal());
        }
        return pageDataResult;
    }

    /**
     * @Author 郭凯
     * @Description 通过或者驳回状态修改
     * @Date 13:31 2020/8/13
     * @Param [businessWithdrawalLog]
     * @return int
    **/
    @Override
    public int editAdopt(BusinessWithdrawalLog businessWithdrawalLog) {
        if ("1".equals(businessWithdrawalLog.getState())){
            if (ToolUtil.isEmpty(businessWithdrawalLog.getUserId())){
                return 2;
            }
            BusinessUsers users = businessUsersMapper.selectByPrimaryKey(businessWithdrawalLog.getUserId());
            if (users.getAccountBalance().compareTo(businessWithdrawalLog.getMoney()) == -1){
                return 3;
            }
            if (ToolUtil.isEmpty(users.getAccountBalance()) || ToolUtil.isEmpty(businessWithdrawalLog.getMoney())){
                return 4;
            }
            BusinessUsers businessUsers = new BusinessUsers();
            businessUsers.setId(businessWithdrawalLog.getUserId());
            businessUsers.setAccountBalance(users.getAccountBalance().subtract(businessWithdrawalLog.getMoney()));
            businessUsers.setUpdateTime(DateUtil.currentDateTime());
            businessUsersMapper.updateByPrimaryKeySelective(businessUsers);
        }
        businessWithdrawalLog.setUpdateTime(DateUtil.currentDateTime());
        return businessWithdrawalLogMapper.updateByPrimaryKeySelective(businessWithdrawalLog);
    }

    /**
     * @Author 郭凯
     * @Description 下载提现申请Excel表数据查询
     * @Date 13:37 2020/12/10
     * @Param [search]
     * @return java.util.List<com.hna.hka.archive.management.business.model.BusinessWithdrawalLog>
    **/
    @Override
    public List<BusinessWithdrawalLog> getWithdrawalLogExcel(Map<String, String> search) {
        List<BusinessWithdrawalLog> businessWithdrawalLogList = businessWithdrawalLogMapper.getWithdrawalLogList(search);
        for (BusinessWithdrawalLog withdrawalLog : businessWithdrawalLogList){
            withdrawalLog.setStateName(DictUtils.getWithdrawalLogStateMap().get(withdrawalLog.getState()));
        }
        return businessWithdrawalLogList;
    }
}
