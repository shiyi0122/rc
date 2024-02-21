package com.hna.hka.archive.management.system.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hna.hka.archive.management.system.dao.SysCurrentBlackListMapper;
import com.hna.hka.archive.management.system.dao.SysCurrentUserMapper;
import com.hna.hka.archive.management.system.dao.SysLogMapper;
import com.hna.hka.archive.management.system.dao.WechatDepositMapper;
import com.hna.hka.archive.management.system.dao.WechatSysDepositLogMapper;
import com.hna.hka.archive.management.system.model.SysCurrentUser;
import com.hna.hka.archive.management.system.model.SysLogWithBLOBs;
import com.hna.hka.archive.management.system.model.SysUsers;
import com.hna.hka.archive.management.system.model.WechatDeposit;
import com.hna.hka.archive.management.system.model.WechatSysDepositLog;
import com.hna.hka.archive.management.system.service.SysCurrentUserService;
import com.hna.hka.archive.management.system.util.DateUtil;
import com.hna.hka.archive.management.system.util.DictUtils;
import com.hna.hka.archive.management.system.util.IdUtils;
import com.hna.hka.archive.management.system.util.PageDataResult;
import com.hna.hka.archive.management.system.util.ToolUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @ProjectName: IDEA-CODE
 * @Package: com.hna.hka.archive.management.system.service.impl
 * @ClassName: SysCurrentUserServiceImpl
 * @Author: 郭凯
 * @Description: 客户管理业务层（实现）
 * @Date: 2020/5/22 15:01
 * @Version: 1.0
 */
@Service
@Transactional
public class SysCurrentUserServiceImpl implements SysCurrentUserService {

    @Autowired
    private SysCurrentUserMapper sysCurrentUserMapper;

    @Autowired
    private WechatSysDepositLogMapper wechatSysDepositLogMapper;

    @Autowired
    private SysCurrentBlackListMapper sysCurrentBlackListMapper;
    
    @Autowired
    private WechatDepositMapper wechatDepositMapper;
    
    @Autowired
    private SysLogMapper sysLogMapper;


    /**
     * @Author 郭凯
     * @Description 查询客户列表
     * @Date 15:05 2020/5/22
     * @Param [pageNum, pageSize, search]
     * @return com.hna.hka.archive.management.system.util.PageDataResult
    **/
    @Override
    public PageDataResult getCurrentUserList(Integer pageNum, Integer pageSize, Map<String,Object> search) {
        PageDataResult pageDataResult = new PageDataResult();
        PageHelper.startPage(pageNum, pageSize);
        List<SysCurrentUser> sysCurrentUserList = sysCurrentUserMapper.getCurrentUserList(search);
        for(SysCurrentUser currentUser : sysCurrentUserList) {
        	WechatDeposit wechatDeposit = wechatDepositMapper.getDepositLogByUserId(currentUser.getCurrentUserId());
        	if (ToolUtil.isNotEmpty(wechatDeposit)) {
        		currentUser.setOutTradeNo(wechatDeposit.getOutTradeNo());
			}else{
				currentUser.setOutTradeNo("无");
			}
        }
        if(sysCurrentUserList.size() != 0){
            PageInfo<SysCurrentUser> pageInfo = new PageInfo<>(sysCurrentUserList);
            pageDataResult.setList(pageInfo.getList());
            pageDataResult.setTotals((int) pageInfo.getTotal());
        }
        return pageDataResult;
    }

    /**
     * 修改用户是否欠款状态
     * @param currenUser
     * @return
     */
    @Override
    public int updateCurrenUser(SysCurrentUser currenUser) {
        return sysCurrentUserMapper.updateByPrimaryKeySelective(currenUser);
    }

    /**
     * @Author 郭凯
     * @Description 查询回显状态
     * @Date 15:28 2020/5/26
     * @Param [currentUserId]
     * @return com.hna.hka.archive.management.system.model.SysCurrentUser
    **/
    @Override
    public SysCurrentUser getCurrentUserById(Long currentUserId) {
        return sysCurrentUserMapper.selectByPrimaryKey(currentUserId);
    }

    /**
     * @Author 郭凯
     * @Description 修改押金缴纳状态
     * @Date 15:38 2020/5/26
     * @Param [sysCurrentUser]
     * @return int
    **/
    @Override
    public int updateDepositPayState(SysCurrentUser sysCurrentUser,SysUsers sysUsers) {
    	//查询押金缴纳日志
    	List<WechatDeposit> wechatDepositsList = wechatDepositMapper.getWechatSysDepositLogByUserId(sysCurrentUser.getCurrentUserId());
    	for(WechatDeposit wechatDeposit:wechatDepositsList) {
    		if ("30".equals(wechatDeposit.getDepositState())) {
    			wechatDeposit.setDepositState("60");
    			wechatDeposit.setUpdateDate(DateUtil.currentDateTime());
				wechatDepositMapper.updateByPrimaryKeySelective(wechatDeposit);
			}
    	}
    	SysCurrentUser currentUser = sysCurrentUserMapper.selectByPrimaryKey(sysCurrentUser.getCurrentUserId());
    	if (ToolUtil.isEmpty(currentUser)) {
			return 2;
		}
    	//添加修改押金缴纳状态日志
    	SysLogWithBLOBs log = new SysLogWithBLOBs();
    	log.setLogId(IdUtils.getSeqId());
    	log.setUserName(sysUsers.getUserName());
    	log.setLogUserPhone(currentUser.getCurrentUserPhone());
    	log.setLogAmount("修改前："+DictUtils.getDepositPayStateMap().get(currentUser.getDepositPayState())+"；修改后："+DictUtils.getDepositPayStateMap().get(sysCurrentUser.getDepositPayState()));
    	log.setLogType("3");
    	log.setCreateDate(DateUtil.currentDateTime());
    	sysLogMapper.insertSelective(log);
        return sysCurrentUserMapper.updateByPrimaryKeySelective(sysCurrentUser);
    }

    /**
     * @Author 郭凯
     * @Description 插入押金日志
     * @Date 16:39 2020/8/20
     * @Param [chatSysDepositLog]
     * @return int
    **/
    @Override
    public int saveDeposLog(WechatSysDepositLog chatSysDepositLog) {
        return wechatSysDepositLogMapper.insertSelective(chatSysDepositLog);
    }

    /**
     * @Author 郭凯
     * @Description 下载客户Excel表
     * @Date 11:40 2020/9/2
     * @Param [search]
     * @return java.util.List<com.hna.hka.archive.management.system.model.SysCurrentUser>
    **/
    @Override
    public List<SysCurrentUser> getCurrentUserExcel(Map<String, Object> search) {
        List<SysCurrentUser> currentUserList = sysCurrentUserMapper.getCurrentUserList(search);
        for (SysCurrentUser currentUser : currentUserList){
            currentUser.setDepositPayStateName(DictUtils.getDepositPayStateMap().get(currentUser.getDepositPayState()));
            currentUser.setCreditArrearsStateName(DictUtils.getCreditArrearsStateMap().get(currentUser.getCreditArrearsState()));
            currentUser.setBlackListTypeName(DictUtils.getBlackListTypeMap().get(currentUser.getBlackListType()));
        }
        return currentUserList;
    }

    /**
     * @Author 郭凯
     * @Description 设置白名单
     * @Date 9:10 2020/12/11
     * @Param [userId]
     * @return int
    **/
    @Override
    public int delBlacklist(Long blackListId) {
        return sysCurrentBlackListMapper.deleteByPrimaryKey(blackListId);
    }

    /**
     * 根据手机号查询用户
     * @param phone
     * @return
     */
    @Override
    public SysCurrentUser getCurrentUserByPhone(String phone) {

       SysCurrentUser sysCurrentUser = sysCurrentUserMapper.getCurrenUserByPhone(phone);

        return sysCurrentUser;

    }
}
