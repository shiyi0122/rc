package com.hna.hka.archive.management.system.service;

import com.hna.hka.archive.management.system.model.SysRobotCompanyAscription;
import com.hna.hka.archive.management.system.util.PageDataResult;

import java.util.Map;

/**
 * @Author zhang
 * @Date 2022/2/25 14:24
 */
public interface SysRobotCompanyAscriptionService {

    int addRobotCompanyAscription(SysRobotCompanyAscription robotCompanyAscription);


    int editAscriptionCompany(SysRobotCompanyAscription robotCompanyAscription);

    PageDataResult getAscriptionCompanyList(Integer pageNum, Integer pageSize, Map<String, Object> search);



}
