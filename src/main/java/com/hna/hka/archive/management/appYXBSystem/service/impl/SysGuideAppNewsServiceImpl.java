package com.hna.hka.archive.management.appYXBSystem.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hna.hka.archive.management.appYXBSystem.dao.SysGuideAppNewsMapper;
import com.hna.hka.archive.management.appYXBSystem.model.SysGuideAppNews;
import com.hna.hka.archive.management.appYXBSystem.model.SysGuideAppUsers;
import com.hna.hka.archive.management.appYXBSystem.service.SysGuideAppNewsService;
import com.hna.hka.archive.management.system.model.SysCurrentUserAccount;
import com.hna.hka.archive.management.system.util.DateUtil;
import com.hna.hka.archive.management.system.util.IdUtils;
import com.hna.hka.archive.management.system.util.PageDataResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class SysGuideAppNewsServiceImpl implements SysGuideAppNewsService {

    @Autowired
    SysGuideAppNewsMapper sysGuideAppNewsMapper;

    /**
     * 管理后台游小伴app消息列表
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public PageDataResult getGuideAppNewsList(Integer pageNum, Integer pageSize, Map<String, String> search) {
        PageDataResult pageDataResult = new PageDataResult();
        PageHelper.startPage(pageNum, pageSize);
        List<SysGuideAppNews> sysGuideAppNewsList = sysGuideAppNewsMapper.getGuideAppNewsList(search);
        if (sysGuideAppNewsList.size() > 0){
            PageInfo<SysGuideAppNews> pageInfo = new PageInfo<>(sysGuideAppNewsList);
            pageDataResult.setList(pageInfo.getList());
            pageDataResult.setTotals((int) pageInfo.getTotal());
        }

        return pageDataResult;
    }

    /**
     * 修改消息
     * @param sysGuideAppNews
     * @return
     */
    @Override
    public int editGuideAppNews(SysGuideAppNews sysGuideAppNews) {

        sysGuideAppNews.setUpdateDate(DateUtil.currentDateTime());
        int i = sysGuideAppNewsMapper.updateByPrimaryKeySelective(sysGuideAppNews);
        return i;
    }

    /**
     * 删除消息
     * @param guideId
     * @return
     */
    @Override
    public int delGuideAppNews(Long guideId) {

        int i = sysGuideAppNewsMapper.deleteByPrimaryKey(guideId);

        return i;
    }

    /**
     * 添加消息
     * @param sysGuideAppNews
     * @return
     */
    @Override
    public int addGuideAppNews(SysGuideAppNews sysGuideAppNews) {
        List<SysGuideAppUsers> list = sysGuideAppNewsMapper.getGuideAppNewsListAll();
        sysGuideAppNews.setGuideId(IdUtils.getSeqId());
        sysGuideAppNews.setGuideUserId(list.get(0).getUserId());
        sysGuideAppNews.setCreateDate(DateUtil.currentDateTime());
        sysGuideAppNews.setUpdateDate(DateUtil.currentDateTime());
        return sysGuideAppNewsMapper.insertSelective(sysGuideAppNews);
    }
}
