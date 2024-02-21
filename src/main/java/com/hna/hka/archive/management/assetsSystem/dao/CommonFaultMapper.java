package com.hna.hka.archive.management.assetsSystem.dao;

import com.hna.hka.archive.management.assetsSystem.model.CommonFault;

import java.util.List;

/**
 * @program: rc
 * @description:
 * @author: zhaoxianglong
 * @create: 2021-09-16 16:51
 **/
public interface CommonFaultMapper {
    List<CommonFault> list(String title, String applicableModel) throws Exception;

    Integer add(CommonFault fault) throws Exception;

    Integer edit(CommonFault fault) throws Exception;

    Integer delete(Long id) throws Exception;

    CommonFault findByKey(Long id) throws Exception;

    List<CommonFault> commonFaultList();

}
