package com.hna.hka.archive.management.system.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hna.hka.archive.management.system.dao.SysCurrentUserAccountDeductionMapper;
import com.hna.hka.archive.management.system.model.SysCurrentUserAccountDeduction;
import com.hna.hka.archive.management.system.service.SysCurrentUserAccountDeductionService;
import com.hna.hka.archive.management.system.util.PageDataResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2022/12/1 16:05
 */
@Service
public class SysCurrentUserAccountDeductionServiceImpl implements SysCurrentUserAccountDeductionService {

    @Autowired
    SysCurrentUserAccountDeductionMapper sysCurrentUserAccountDeductionMapper;

    @Override
    public int add(SysCurrentUserAccountDeduction sysCurrentUserAccountDeduction) {

       int i = sysCurrentUserAccountDeductionMapper.add(sysCurrentUserAccountDeduction);

       return i;
    }

    /**
     * 后台页面查询
     * @param pageNum
     * @param pageSize
     * @param search
     * @return
     */
    @Override
    public PageDataResult getCurrentUserAccountDeductionList(Integer pageNum, Integer pageSize, Map<String, Object> search) {

        PageDataResult pageDataResult = new PageDataResult();
        PageHelper.startPage(pageNum,pageSize);
        List<SysCurrentUserAccountDeduction> list = sysCurrentUserAccountDeductionMapper.list(search);
        if (list.size()>0){
            PageInfo<SysCurrentUserAccountDeduction> pageInfo = new PageInfo<>(list);
            pageDataResult.setList(list);
            pageDataResult.setTotals((int)pageInfo.getTotal());
        }
        return pageDataResult;

    }

    /**
     * 下载
     * @param search
     * @return
     */
    @Override
    public List<SysCurrentUserAccountDeduction> uploadExceluserPhoneLog(Map<String, Object> search) {

        List<SysCurrentUserAccountDeduction> list = sysCurrentUserAccountDeductionMapper.list(search);
        return list;
    }
}
