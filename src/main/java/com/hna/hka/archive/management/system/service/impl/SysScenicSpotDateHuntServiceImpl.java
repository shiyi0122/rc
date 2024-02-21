package com.hna.hka.archive.management.system.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hna.hka.archive.management.system.dao.SysScenicSpotDateHuntMapper;
import com.hna.hka.archive.management.system.model.SysScenicSpotDateHunt;
import com.hna.hka.archive.management.system.model.SysScenicSpotExpand;
import com.hna.hka.archive.management.system.model.SysScenicSpotName;
import com.hna.hka.archive.management.system.service.SysScenicSpotDateHuntService;
import com.hna.hka.archive.management.system.util.DateUtil;
import com.hna.hka.archive.management.system.util.IdUtils;
import com.hna.hka.archive.management.system.util.PageDataResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2022/3/10 10:10
 */
@Service
@Transactional
public class SysScenicSpotDateHuntServiceImpl implements SysScenicSpotDateHuntService {

    @Autowired
    SysScenicSpotDateHuntMapper sysScenicSpotDateHuntMapper;


    /**
     * 添加
     * @param sysScenicSpotDateHunt
     * @return
     */
    @Override
    public int addSpotDateHunt(SysScenicSpotDateHunt sysScenicSpotDateHunt) {

        sysScenicSpotDateHunt.setDateTreasureId(IdUtils.getSeqId());
        sysScenicSpotDateHunt.setCreateDate(DateUtil.currentDateTime());
        sysScenicSpotDateHunt.setUpdateDate(DateUtil.currentDateTime());

        int i = sysScenicSpotDateHuntMapper.insertSelective(sysScenicSpotDateHunt);

        return i;
    }


    /**
     * 修改
     * @param sysScenicSpotDateHunt
     * @return
     */
    @Override
    public int editSpotDateHunt(SysScenicSpotDateHunt sysScenicSpotDateHunt) {

        sysScenicSpotDateHunt.setUpdateDate(DateUtil.currentDateTime());

        int i = sysScenicSpotDateHuntMapper.updateByPrimaryKeySelective(sysScenicSpotDateHunt);

        return i;
    }

    /**
     * 删除
     * @param dateTreasureId
     * @return
     */
    @Override
    public int delSpotDateHunt(Long dateTreasureId) {

        return sysScenicSpotDateHuntMapper.deleteByPrimaryKey(dateTreasureId);

    }

    /**
     * 查询
     * @param pageNum
     * @param pageSize
     * @param search
     * @return
     */
    @Override
    public PageDataResult getSpotDateHuntList(Integer pageNum, Integer pageSize, Map<String, String> search) {

        PageDataResult pageDataResult = new PageDataResult();
        PageHelper.startPage(pageNum,pageSize);

        List<SysScenicSpotDateHunt> list = sysScenicSpotDateHuntMapper.getSpotDateHuntList(search);

        if (list.size()>0){
            PageInfo<SysScenicSpotDateHunt> pageInfo = new PageInfo<>(list);
            pageDataResult.setList(pageInfo.getList());
            pageDataResult.setTotals((int) pageInfo.getTotal());
        }

        return pageDataResult;

    }
    /**
     * 获取当前景区所有的时间段id和对应时间段
     *
     * zhang
     *
     * @return
     */
    @Override
    public PageDataResult getSpotDateHuntIdList( Map<String,Object> search) {
        PageDataResult pageDataResult = new PageDataResult();
        PageHelper.startPage(1, 10);
        List<SysScenicSpotDateHunt> list  =  sysScenicSpotDateHuntMapper.getSpotDateHuntIdList(search);
        if (list.size() != 0){
            PageInfo<SysScenicSpotDateHunt> pageInfo = new PageInfo<>(list);
            pageDataResult.setList(pageInfo.getList());
            pageDataResult.setTotals((int) pageInfo.getTotal());
        }
       return pageDataResult;
    }
}
