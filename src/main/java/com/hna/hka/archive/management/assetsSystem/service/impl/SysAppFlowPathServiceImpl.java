package com.hna.hka.archive.management.assetsSystem.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hna.hka.archive.management.assetsSystem.dao.SysAppFlowPathDetailsMapper;
import com.hna.hka.archive.management.assetsSystem.dao.SysAppFlowPathMapper;
import com.hna.hka.archive.management.assetsSystem.dao.SysAppFlowPathSpotMapper;
import com.hna.hka.archive.management.assetsSystem.model.SysAppFlowPath;
import com.hna.hka.archive.management.assetsSystem.model.SysAppFlowPathDetails;
import com.hna.hka.archive.management.assetsSystem.model.SysAppFlowPathSpot;
import com.hna.hka.archive.management.assetsSystem.service.SysAppFlowPathService;
import com.hna.hka.archive.management.system.util.DateUtil;
import com.hna.hka.archive.management.system.util.IdUtils;
import com.hna.hka.archive.management.system.util.PageDataResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.util.StringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2023/6/27 16:22
 */

@Service
public class SysAppFlowPathServiceImpl implements SysAppFlowPathService {

    @Autowired
    SysAppFlowPathMapper sysAppFlowPathMapper;
    @Autowired
    SysAppFlowPathDetailsMapper sysAppFlowPathDetailsMapper;
    @Autowired
    SysAppFlowPathSpotMapper sysAppFlowPathSpotMapper;

    /**
     * 获取审核流程列表
     *
     * @param pageNum
     * @param pageSize
     * @param search
     * @return
     */
    @Override
    public PageDataResult getSysAppFlowPathList(Integer pageNum, Integer pageSize, Map<String, Object> search) {

        PageDataResult pageDataResult = new PageDataResult();
        List<SysAppFlowPathSpot> sysAppFlowPathSpots = new ArrayList<>();
        List<SysAppFlowPathDetails> sysAppFlowPathDetails = new ArrayList<>();
        List<SysAppFlowPathDetails> sysAppFlowPathDetailsCC = new ArrayList<>();

        PageHelper.startPage(pageNum, pageSize);
        List<SysAppFlowPath> list = sysAppFlowPathMapper.getSysAppFlowPathList(search);

        for (SysAppFlowPath sysAppFlowPath : list) {
            sysAppFlowPathDetails = sysAppFlowPathDetailsMapper.selectFlowPathIdByList(sysAppFlowPath.getId(), null, "1");
            if (!StringUtils.isEmpty(sysAppFlowPathDetails)) {
                for (SysAppFlowPathDetails sysAppFlowPathDetail : sysAppFlowPathDetails) {
                    sysAppFlowPathDetailsCC = sysAppFlowPathDetailsMapper.selectFlowPathIdByList(sysAppFlowPath.getId(), sysAppFlowPathDetail.getSort(), "2");
                    sysAppFlowPathDetail.setAppFlowPathDetailsList(sysAppFlowPathDetailsCC);
                }
            }

            sysAppFlowPath.setAppFlowPathDetailsList(sysAppFlowPathDetails);
            sysAppFlowPathSpots = sysAppFlowPathSpotMapper.selectFlowPathIdByList(sysAppFlowPath.getId());
            sysAppFlowPath.setAppFlowPathSpotList(sysAppFlowPathSpots);
        }

        if (list.size() > 0) {
            PageInfo<SysAppFlowPath> pageInfo = new PageInfo<>(list);
            pageDataResult.setData(list);
            pageDataResult.setCode(200);
            pageDataResult.setTotals((int) pageInfo.getTotal());
        }
        return pageDataResult;
    }

    /**
     * 添加审批流程
     *
     * @param sysAppFlowPath
     * @return
     */
    @Override
    public int addSysAppFlowPath(SysAppFlowPath sysAppFlowPath) {


        SysAppFlowPathSpot sysAppFlowPathSpot = new SysAppFlowPathSpot();
//        SysAppFlowPathDetails sysAppFlowPathDetails = new SysAppFlowPathDetails();
        sysAppFlowPath.setId(IdUtils.getSeqId());
        sysAppFlowPath.setCreateTime(DateUtil.currentDateTime());
        sysAppFlowPath.setUpdateTime(DateUtil.currentDateTime());

        int i = sysAppFlowPathMapper.insertSelective(sysAppFlowPath);

        if (i > 0) {

            String[] split = sysAppFlowPath.getScenicSpotIds().split(",");
            for (String spotId : split) {
                sysAppFlowPathSpot = new SysAppFlowPathSpot();
                sysAppFlowPathSpot.setId(IdUtils.getSeqId());
                sysAppFlowPathSpot.setFlowPathId(sysAppFlowPath.getId());
                sysAppFlowPathSpot.setScenicSpotId(Long.parseLong(spotId));
                sysAppFlowPathSpot.setCreateTime(DateUtil.currentDateTime());
                sysAppFlowPathSpot.setUpdateTime(DateUtil.currentDateTime());
                sysAppFlowPathSpotMapper.insertSelective(sysAppFlowPathSpot);
            }

            List<SysAppFlowPathDetails> appFlowPathDetailsList = sysAppFlowPath.getAppFlowPathDetailsList();
            for (SysAppFlowPathDetails sysAppFlowPathDetails : appFlowPathDetailsList) {

                sysAppFlowPathDetails.setId(IdUtils.getSeqId());
                sysAppFlowPathDetails.setFlowPathId(sysAppFlowPath.getId());
                sysAppFlowPathDetails.setCreateTime(DateUtil.currentDateTime());
                sysAppFlowPathDetails.setUpdateTime(DateUtil.currentDateTime());
                sysAppFlowPathDetailsMapper.insertSelective(sysAppFlowPathDetails);
                if (!StringUtils.isEmpty(sysAppFlowPathDetails.getAppFlowPathDetailsList())) {
                    if (sysAppFlowPathDetails.getAppFlowPathDetailsList().size() > 0) {
                        List<SysAppFlowPathDetails> appFlowPathDetailsListN = sysAppFlowPathDetails.getAppFlowPathDetailsList();
                        for (SysAppFlowPathDetails appFlowPathDetails : appFlowPathDetailsListN) {
                            appFlowPathDetails.setId(IdUtils.getSeqId());
                            appFlowPathDetails.setFlowPathId(sysAppFlowPath.getId());
                            appFlowPathDetails.setCreateTime(DateUtil.currentDateTime());
                            appFlowPathDetails.setUpdateTime(DateUtil.currentDateTime());
                            sysAppFlowPathDetailsMapper.insertSelective(appFlowPathDetails);
                        }
                    }
                }
            }
        }

        return i;

    }

    /**
     * 修改审批流程
     *
     * @param sysAppFlowPath
     * @return
     */
    @Transactional
    @Override
    public int editSysAppFlowPath(SysAppFlowPath sysAppFlowPath) {

        SysAppFlowPathSpot sysAppFlowPathSpot = new SysAppFlowPathSpot();

        List<SysAppFlowPathDetails> appFlowPathDetailsList = sysAppFlowPath.getAppFlowPathDetailsList();

        String[] split = sysAppFlowPath.getScenicSpotIds().split(",");

        int i2 = sysAppFlowPathSpotMapper.deleteByFlowPathId(sysAppFlowPath.getId());
        int i1 = sysAppFlowPathDetailsMapper.deleteByFlowPathId(sysAppFlowPath.getId());

        for (SysAppFlowPathDetails sysAppFlowPathDetails : appFlowPathDetailsList) {
            sysAppFlowPathDetails.setId(IdUtils.getSeqId());
            sysAppFlowPathDetails.setFlowPathId(sysAppFlowPath.getId());
            sysAppFlowPathDetails.setCreateTime(DateUtil.currentDateTime());
            sysAppFlowPathDetails.setUpdateTime(DateUtil.currentDateTime());
            sysAppFlowPathDetails.setUpdateTime(DateUtil.currentDateTime());
            sysAppFlowPathDetailsMapper.insertSelective(sysAppFlowPathDetails);

            if (sysAppFlowPathDetails.getAppFlowPathDetailsList().size() > 0) {
                List<SysAppFlowPathDetails> appFlowPathDetailsListN = sysAppFlowPathDetails.getAppFlowPathDetailsList();
                for (SysAppFlowPathDetails appFlowPathDetails : appFlowPathDetailsListN) {
                    appFlowPathDetails.setId(IdUtils.getSeqId());
                    appFlowPathDetails.setFlowPathId(sysAppFlowPath.getId());
                    appFlowPathDetails.setCreateTime(DateUtil.currentDateTime());
                    appFlowPathDetails.setUpdateTime(DateUtil.currentDateTime());
                    sysAppFlowPathDetailsMapper.insertSelective(appFlowPathDetails);
                }
            }
        }
        for (String spotId : split) {
            sysAppFlowPathSpot = new SysAppFlowPathSpot();
            sysAppFlowPathSpot.setId(IdUtils.getSeqId());
            sysAppFlowPathSpot.setFlowPathId(sysAppFlowPath.getId());
            sysAppFlowPathSpot.setScenicSpotId(Long.parseLong(spotId));
            sysAppFlowPathSpot.setCreateTime(DateUtil.currentDateTime());
            sysAppFlowPathSpot.setUpdateTime(DateUtil.currentDateTime());
            sysAppFlowPathSpotMapper.insertSelective(sysAppFlowPathSpot);
        }

        sysAppFlowPath.setUpdateTime(DateUtil.currentDateTime());
        int i = sysAppFlowPathMapper.updateByPrimaryKeySelective(sysAppFlowPath);

        return i;
    }

    /**
     * 删除审批流程
     *
     * @param id
     * @return
     */
    @Override
    public int delSysAppFlowPath(Long id) {

//       List<SysAppFlowPathDetails> list =  sysAppFlowPathDetailsMapper.selectById(id);
        int j = sysAppFlowPathDetailsMapper.deleteByFlowPathId(id);
        int t = sysAppFlowPathSpotMapper.deleteByFlowPathId(id);
        int i = sysAppFlowPathMapper.deleteByPrimaryKey(id);

        return i;
    }

    /**
     * 管理者app获取审批流程
     *
     * @return
     */
    @Override
    public List<SysAppFlowPath> getSysAppFlowPathDrop(String scenicSpotId) {

        List<SysAppFlowPath> list = sysAppFlowPathMapper.getSysAppFlowPathDrop(scenicSpotId);

        return list;
    }

    /**
     * 根据审批流程id，获取审批流程人员
     *
     * @param id
     * @return
     */
    @Override
    public List<SysAppFlowPathDetails> getAppFlowPathIdByPeople(Long id) {

        List<SysAppFlowPathDetails> list = new ArrayList<>();

        list = sysAppFlowPathDetailsMapper.getAppFlowPathIdByPeople(id);


        return list;
    }
}
