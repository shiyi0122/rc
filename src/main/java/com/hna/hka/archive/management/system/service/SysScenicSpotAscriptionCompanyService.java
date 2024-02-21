package com.hna.hka.archive.management.system.service;

import com.hna.hka.archive.management.system.model.SysScenicSpotAscriptionCompany;
import com.hna.hka.archive.management.system.util.PageDataResult;

import java.util.List;
import java.util.Map;

/**
 * @ProjectName: rc
 * @Package: com.hna.hka.archive.management.system.service
 * @ClassName: SysScenicSpotAscriptionCompanyService
 * @Author: 郭凯
 * @Description: 景区归属公司业务层（接口）
 * @Date: 2021/5/20 16:03
 * @Version: 1.0
 */
public interface SysScenicSpotAscriptionCompanyService {

    PageDataResult getAscriptionCompanyList(Integer pageNum, Integer pageSize, Map<String, Object> search);

    int addAscriptionCompany(SysScenicSpotAscriptionCompany ascriptionCompany);

    int editAscriptionCompany(SysScenicSpotAscriptionCompany ascriptionCompany);

    int delAscriptionCompany(Long companyId);

    List<SysScenicSpotAscriptionCompany> getAscriptionCompany();
}
