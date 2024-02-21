package com.hna.hka.archive.management.appYXBSystem.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hna.hka.archive.management.appYXBSystem.dao.SysGuideAppUsersHelpMapper;
import com.hna.hka.archive.management.appYXBSystem.model.SysGuideAppUsersHelp;
import com.hna.hka.archive.management.appYXBSystem.service.SysGuideAppUsersHelpService;
import com.hna.hka.archive.management.system.model.SysUsers;
import com.hna.hka.archive.management.system.util.DateUtil;
import com.hna.hka.archive.management.system.util.IdUtils;
import com.hna.hka.archive.management.system.util.PageDataResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysGuideAppUsersHelpServiceImpl implements SysGuideAppUsersHelpService {

    @Autowired
    SysGuideAppUsersHelpMapper sysGuideAppUsersHelpMapper;

    /**
     * 添加使用帮助
     * @param sysGuideAppUsersHelp
     * @return
     */
    @Override
    public int addGuideAppUsersHelp(SysGuideAppUsersHelp sysGuideAppUsersHelp) {
        sysGuideAppUsersHelp.setHelpId(IdUtils.getSeqId());
        sysGuideAppUsersHelp.setCreateDate(DateUtil.currentDateTime());
      return   sysGuideAppUsersHelpMapper.insertSelective(sysGuideAppUsersHelp);
    }

    /**
     * 修改使用帮助
     * @param sysGuideAppUsersHelp
     * @return
     */
    @Override
    public int editGuideAppUsersHelp(SysGuideAppUsersHelp sysGuideAppUsersHelp) {

        sysGuideAppUsersHelp.setUpdateDate(DateUtil.currentDateTime());
       return sysGuideAppUsersHelpMapper.updateByPrimaryKeySelective(sysGuideAppUsersHelp);

    }

    /**
     * 删除使用帮助
     * @param helpId
     * @return
     */
    @Override
    public int delGuideAppUsersHelp(Long helpId) {

        return sysGuideAppUsersHelpMapper.deleteByPrimaryKey(helpId);
    }

    /**
     * 使用帮助列表
     * @param pageNum
     * @param pageSize
     * @param sysGuideAppUsersHelp
     * @return
     */
    @Override
    public PageDataResult getGuideAppUsersHelpList(Integer pageNum, Integer pageSize, SysGuideAppUsersHelp sysGuideAppUsersHelp) {

        PageDataResult pageDataResult = new PageDataResult();
        PageHelper.startPage(pageNum, pageSize);
        List<SysGuideAppUsersHelp> sysUsersList = sysGuideAppUsersHelpMapper.getGuideAppUsersHelpList(sysGuideAppUsersHelp);
        if(sysUsersList.size() != 0){
            PageInfo<SysGuideAppUsersHelp> pageInfo = new PageInfo<>(sysUsersList);
            pageDataResult.setList(pageInfo.getList());
            pageDataResult.setTotals((int) pageInfo.getTotal());
        }
        return pageDataResult;

    }
}
