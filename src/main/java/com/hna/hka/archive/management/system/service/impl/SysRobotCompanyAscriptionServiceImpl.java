package com.hna.hka.archive.management.system.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hna.hka.archive.management.system.dao.SysRobotCompanyAscriptionMapper;
import com.hna.hka.archive.management.system.model.SysRobotCompanyAscription;
import com.hna.hka.archive.management.system.model.SysScenicSpotAscriptionCompany;
import com.hna.hka.archive.management.system.service.SysRobotCompanyAscriptionService;
import com.hna.hka.archive.management.system.util.DateUtil;
import com.hna.hka.archive.management.system.util.IdUtils;
import com.hna.hka.archive.management.system.util.PageDataResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2022/2/25 14:24
 * 机器人景区归属
 */
@Service
public class SysRobotCompanyAscriptionServiceImpl  implements SysRobotCompanyAscriptionService {

    @Autowired
    SysRobotCompanyAscriptionMapper sysRobotCompanyAscriptionMapper;

    /**
     * 新增
     * @param robotCompanyAscription
     * @return
     */
    @Override
    public int addRobotCompanyAscription(SysRobotCompanyAscription robotCompanyAscription) {

        robotCompanyAscription.setRobotCompanyAscriptionId(IdUtils.getSeqId());
        robotCompanyAscription.setCreateDate(DateUtil.currentDateTime());
        robotCompanyAscription.setUpdateDate(DateUtil.currentDateTime());
        SysRobotCompanyAscription  robotCompanyAscriptionId =  sysRobotCompanyAscriptionMapper.getAscriptionCompanyId(robotCompanyAscription.getRobotCode());
        if (StringUtils.isEmpty(robotCompanyAscriptionId)){
            return sysRobotCompanyAscriptionMapper.insertSelective(robotCompanyAscription);
        }else{

            return  2;
        }
    }

    /**
     * 修改
     * @param robotCompanyAscription
     * @return
     */
    @Override
    public int editAscriptionCompany(SysRobotCompanyAscription robotCompanyAscription) {
        robotCompanyAscription.setUpdateDate(DateUtil.currentDateTime());
        return sysRobotCompanyAscriptionMapper.updateByPrimaryKeySelective(robotCompanyAscription);

    }

    /**
     * 机器人归属公司列表查询
     * @param pageNum
     * @param pageSize
     * @param search
     * @return
     */
    @Override
    public PageDataResult getAscriptionCompanyList(Integer pageNum, Integer pageSize, Map<String, Object> search) {

        PageDataResult pageDataResult = new PageDataResult();
        PageHelper.startPage(pageNum, pageSize);
        List<SysScenicSpotAscriptionCompany> ascriptionCompanyList = sysRobotCompanyAscriptionMapper.getAscriptionCompanyList(search);
        if(ascriptionCompanyList.size() != 0){
            PageInfo<SysScenicSpotAscriptionCompany> pageInfo = new PageInfo<>(ascriptionCompanyList);
            pageDataResult.setList(pageInfo.getList());
            pageDataResult.setTotals((int) pageInfo.getTotal());
        }
        return pageDataResult;


    }

}
