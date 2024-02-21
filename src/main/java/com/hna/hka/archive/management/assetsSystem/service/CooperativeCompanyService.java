package com.hna.hka.archive.management.assetsSystem.service;

import com.hna.hka.archive.management.assetsSystem.model.CooperativeCompany;

import java.util.List;

/**
 * @program: rc
 * @description: 合作公司Service
 * @author: zhaoxianglong
 * @create: 2021-09-08 14:23
 **/
public interface CooperativeCompanyService {

    List<CooperativeCompany> list(Integer pageNum, Integer pageSize, String name) throws Exception;

    Integer add(CooperativeCompany cooperativeCompany) throws Exception;

    Integer edit(CooperativeCompany cooperativeCompany) throws Exception;

    Integer delete(Long id) throws Exception;

    Integer findCount(String name);

    CooperativeCompany getNameByCompanyId(String companyName);

}
