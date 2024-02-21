package com.hna.hka.archive.management.assetsSystem.service;

import com.github.pagehelper.PageInfo;
import com.hna.hka.archive.management.assetsSystem.model.CommonFault;

import java.util.List;

/**
 * @program: rc
 * @description:
 * @author: zhaoxianglong
 * @create: 2021-09-16 16:51
 **/
public interface CommonFaultService {
    PageInfo<CommonFault> list(String title, String applicableModel, Integer pageNum, Integer pageSize) throws Exception;

    Integer add(CommonFault fault) throws Exception;

    Integer edit(CommonFault fault) throws Exception;

    Integer delete(Long id) throws Exception;

    CommonFault detail(Long id) throws Exception;

    List<CommonFault> commonFaultList();


}
