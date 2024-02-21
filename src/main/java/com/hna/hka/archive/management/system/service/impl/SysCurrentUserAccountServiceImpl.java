package com.hna.hka.archive.management.system.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hna.hka.archive.management.system.dao.SysCurrentUserAccountMapper;
import com.hna.hka.archive.management.system.dao.SysLogMapper;
import com.hna.hka.archive.management.system.model.SysCurrentUserAccount;
import com.hna.hka.archive.management.system.model.SysLogWithBLOBs;
import com.hna.hka.archive.management.system.service.SysCurrentUserAccountService;
import com.hna.hka.archive.management.system.util.DateUtil;
import com.hna.hka.archive.management.system.util.PageDataResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


/**
 * @ProjectName: IDEA-CODE
 * @Package: com.hna.hka.archive.management.system.service.impl
 * @ClassName: SysCurrentUserAccountServiceImpl
 * @Author: 郭凯
 * @Description: 储值管理业务层（接口）
 * @Date: 2020/6/23 16:29
 * @Version: 1.0
 */
@Service
@Transactional
public class SysCurrentUserAccountServiceImpl implements SysCurrentUserAccountService {

    @Autowired
    private SysCurrentUserAccountMapper sysCurrentUserAccountMapper;

    @Autowired
    private SysLogMapper sysLogMapper;


    /**
     * @Author 郭凯
     * @Description 储值管理列表查询
     * @Date 13:21 2020/6/24
     * @Param [pageNum, pageSize, search]
     * @return com.hna.hka.archive.management.system.util.PageDataResult
    **/
    @Override
    public PageDataResult getCurrentUserAccountList(Integer pageNum, Integer pageSize, Map<String, String> search) {
        PageDataResult pageDataResult = new PageDataResult();
        PageHelper.startPage(pageNum, pageSize);
        List<SysCurrentUserAccount> sysCurrentUserAccountList = sysCurrentUserAccountMapper.getCurrentUserAccountList(search);
        if (sysCurrentUserAccountList.size() > 0){
            PageInfo<SysCurrentUserAccount> pageInfo = new PageInfo<>(sysCurrentUserAccountList);
            pageDataResult.setList(pageInfo.getList());
            pageDataResult.setTotals((int) pageInfo.getTotal());
        }
        return pageDataResult;
    }

    /**
     * @Author 郭凯
     * @Description 查询下载Excel数据
     * @Date 13:48 2020/6/24
     * @Param [search]
     * @return java.util.List<com.hna.hka.archive.management.system.model.SysCurrentUserAccount>
    **/
    @Override
    public List<SysCurrentUserAccount> getUploadExcelCurrentUserAccount(Map<String, String> search) {
        return sysCurrentUserAccountMapper.getUploadExcelCurrentUserAccount(search);
    }

    /**
     * @Author 郭凯
     * @Description 修改储值信息
     * @Date 15:13 2020/6/24
     * @Param [sysCurrentUserAccount]
     * @return int
    **/
    @Override
    public int editCurrentUserAccount(SysCurrentUserAccount sysCurrentUserAccount) {
        sysCurrentUserAccount.setUpdateDate(DateUtil.currentDateTime());
        return sysCurrentUserAccountMapper.updateByPrimaryKeySelective(sysCurrentUserAccount);
    }

    /**
     * @Author 郭凯
     * @Description 查询用户储值账户信息
     * @Date 10:47 2020/8/19
     * @Param [userId]
     * @return com.hna.hka.archive.management.system.model.SysCurrentUserAccount
    **/
    @Override
    public SysCurrentUserAccount selectAccountByUserId(Long userId) {
        return sysCurrentUserAccountMapper.selectAccountByUserId(userId);
    }

    /**
     * @Author 郭凯
     * @Description 根据用户ID查询用户储值信息
     * @Date 17:28 2020/12/1
     * @Param [accountId]
     * @return com.hna.hka.archive.management.system.model.SysCurrentUserAccount
    **/
    @Override
    public SysCurrentUserAccount getSysCurrentUserAccountById(Long accountId) {
        return sysCurrentUserAccountMapper.getSysCurrentUserAccountById(accountId);
    }

    /**
     * @Author 郭凯
     * @Description 价格修改日志操作日志
     * @Date 17:35 2020/12/1
     * @Param [sysLog]
     * @return void
    **/
    @Override
    public void addSysLog(SysLogWithBLOBs sysLog) {
        sysLogMapper.insertSelective(sysLog);
    }
}
