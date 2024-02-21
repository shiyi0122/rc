package com.hna.hka.archive.management.assetsSystem.service.impl;

import com.hna.hka.archive.management.assetsSystem.dao.CooperativeCompanyMapper;
import com.hna.hka.archive.management.assetsSystem.model.CooperativeCompany;
import com.hna.hka.archive.management.assetsSystem.service.CooperativeCompanyService;
import com.hna.hka.archive.management.system.util.IdUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: rc
 * @description: 合作公司ServiceImpl
 * @author: zhaoxianglong
 * @create: 2021-09-08 14:24
 **/
@Service
public class CooperativeCompanyServiceImpl implements CooperativeCompanyService {

    @Autowired
    CooperativeCompanyMapper mapper;

    @Override
    public List<CooperativeCompany> list(Integer pageNum, Integer pageSize, String name) throws Exception{
        return mapper.list(pageNum , pageSize , name);
    }

    @Override
    public Integer add(CooperativeCompany cooperativeCompany) throws Exception {
        cooperativeCompany.setCompanyId(IdUtils.getSeqId());
        return mapper.add(cooperativeCompany);
    }

    @Override
    public Integer edit(CooperativeCompany cooperativeCompany) throws Exception {
        return mapper.edit(cooperativeCompany);
    }

    @Override
    public Integer delete(Long id) throws Exception {
        return mapper.delete(id);
    }

    @Override
    public Integer findCount(String name) {
        return mapper.findCount(name);
    }

    @Override
    public CooperativeCompany getNameByCompanyId(String companyName) {

        CooperativeCompany cooperativeCompany  =  mapper.getNameByCompanyId(companyName);
        return cooperativeCompany;
    }
}
