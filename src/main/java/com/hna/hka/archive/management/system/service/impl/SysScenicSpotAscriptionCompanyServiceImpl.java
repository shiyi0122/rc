package com.hna.hka.archive.management.system.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hna.hka.archive.management.system.dao.SysScenicSpotAscriptionCompanyMapper;
import com.hna.hka.archive.management.system.model.SysScenicSpotAscriptionCompany;
import com.hna.hka.archive.management.system.service.SysScenicSpotAscriptionCompanyService;
import com.hna.hka.archive.management.system.util.DateUtil;
import com.hna.hka.archive.management.system.util.IdUtils;
import com.hna.hka.archive.management.system.util.PageDataResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ProjectName: rc
 * @Package: com.hna.hka.archive.management.system.service.impl
 * @ClassName: SysScenicSpotAscriptionCompanyServiceImpl
 * @Author: 郭凯
 * @Description: 景区归属公司业务层（实现）
 * @Date: 2021/5/20 16:03
 * @Version: 1.0
 */
@Service
public class SysScenicSpotAscriptionCompanyServiceImpl implements SysScenicSpotAscriptionCompanyService {

    @Autowired
    private SysScenicSpotAscriptionCompanyMapper sysScenicSpotAscriptionCompanyMapper;


    /**
     * @Method getAscriptionCompanyList
     * @Author 郭凯
     * @Version  1.0
     * @Description 景区归属公司列表查询
     * @Return com.hna.hka.archive.management.system.util.PageDataResult
     * @Date 2021/5/20 16:06
     */
    @Override
    public PageDataResult getAscriptionCompanyList(Integer pageNum, Integer pageSize, Map<String, Object> search) {
        PageDataResult pageDataResult = new PageDataResult();
        PageHelper.startPage(pageNum, pageSize);
        List<SysScenicSpotAscriptionCompany> ascriptionCompanyList = sysScenicSpotAscriptionCompanyMapper.getScenicSpotBindingList(search);
        if(ascriptionCompanyList.size() != 0){
            PageInfo<SysScenicSpotAscriptionCompany> pageInfo = new PageInfo<>(ascriptionCompanyList);
            pageDataResult.setList(pageInfo.getList());
            pageDataResult.setTotals((int) pageInfo.getTotal());
        }
        return pageDataResult;
    }

    /**
     * @Method addAscriptionCompany
     * @Author 郭凯
     * @Version  1.0
     * @Description 景区归属公司新增
     * @Return int
     * @Date 2021/5/20 16:42
     */
    @Override
    public int addAscriptionCompany(SysScenicSpotAscriptionCompany ascriptionCompany) {
        ascriptionCompany.setCompanyId(IdUtils.getSeqId());
        ascriptionCompany.setCreateDate(DateUtil.currentDateTime());
        ascriptionCompany.setUpdateDate(DateUtil.currentDateTime());
        return sysScenicSpotAscriptionCompanyMapper.insertSelective(ascriptionCompany);
    }

    /**
     * @Method editAscriptionCompany
     * @Author 郭凯
     * @Version  1.0
     * @Description 景区归属公司修改
     * @Return int
     * @Date 2021/5/20 16:56
     */
    @Override
    public int editAscriptionCompany(SysScenicSpotAscriptionCompany ascriptionCompany) {
        ascriptionCompany.setUpdateDate(DateUtil.currentDateTime());
        return sysScenicSpotAscriptionCompanyMapper.updateByPrimaryKeySelective(ascriptionCompany);
    }

    /**
     * @Method delAscriptionCompany
     * @Author 郭凯
     * @Version  1.0
     * @Description 景区归属公司删除
     * @Return int
     * @Date 2021/5/20 16:58
     */
    @Override
    public int delAscriptionCompany(Long companyId) {
        return sysScenicSpotAscriptionCompanyMapper.deleteByPrimaryKey(companyId);
    }

    @Override
    public List<SysScenicSpotAscriptionCompany> getAscriptionCompany() {
        Map<String, Object> search = new HashMap<>();
        List<SysScenicSpotAscriptionCompany> ascriptionCompanyList = sysScenicSpotAscriptionCompanyMapper.getScenicSpotBindingList(search);
        return ascriptionCompanyList;
    }
}
