package com.hna.hka.archive.management.business.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hna.hka.archive.management.business.dao.BusinessDurationOptionMapper;
import com.hna.hka.archive.management.business.model.BusinessDurationOption;
import com.hna.hka.archive.management.business.service.BusinessDurationOptionService;
import com.hna.hka.archive.management.system.util.DateUtil;
import com.hna.hka.archive.management.system.util.IdUtils;
import com.hna.hka.archive.management.system.util.PageDataResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.crypto.Data;
import java.util.List;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2023/5/19 16:54
 * 机器人直播支付金额配置
 */

@Service
public class BusinessDurationOptionServiceImpl implements BusinessDurationOptionService {


    @Autowired
    BusinessDurationOptionMapper businessDurationOptionMapper;


    /**
     * 机器人支付金额配置列表
     * @param pageNum
     * @param pageSize
     * @param search
     * @return
     */
    @Override
    public PageDataResult getDurationOptionList(Integer pageNum, Integer pageSize, Map<String, String> search) {

        PageDataResult pageDataResult = new PageDataResult();

        PageHelper.startPage(pageNum,pageSize);
        List<BusinessDurationOption> list = businessDurationOptionMapper.getDurationOptionList(search);

        if (list.size() > 0){
            PageInfo<BusinessDurationOption> pageInfo = new PageInfo<>(list);
            pageDataResult.setList(list);
            pageDataResult.setCode(200);
            pageDataResult.setTotals( (int)pageInfo.getTotal());
        }
        return pageDataResult;
    }

    /**
     * 添加直播付费金额选项
     * @param durationOption
     * @return
     */
    @Override
    public int addDurationOption(BusinessDurationOption durationOption) {

        durationOption.setId(IdUtils.getSeqId());
        durationOption.setCreateTime(DateUtil.currentDateTime());
        durationOption.setUpdateTime(DateUtil.currentDateTime());

        int i = businessDurationOptionMapper.insertSelective(durationOption);
        return i;
    }

    /**
     * 修改直播金额选项
     * @param durationOption
     * @return
     */
    @Override
    public int editDurationOption(BusinessDurationOption durationOption) {

        durationOption.setUpdateTime(DateUtil.currentDateTime());

        int i = businessDurationOptionMapper.updateByPrimaryKeySelective(durationOption);
        return i;
    }

    @Override
    public int delDurationOption(BusinessDurationOption durationOption) {

        int i =  businessDurationOptionMapper.delDurationOption(durationOption.getId());
        return i;
    }
}
