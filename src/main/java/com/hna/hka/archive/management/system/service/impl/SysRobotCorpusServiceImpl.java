package com.hna.hka.archive.management.system.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hna.hka.archive.management.system.dao.SysRobotCorpusMapper;
import com.hna.hka.archive.management.system.dao.SysRobotProblemExtendMapper;
import com.hna.hka.archive.management.system.dao.SysScenicSpotResourceVersionMapper;
import com.hna.hka.archive.management.system.model.*;
import com.hna.hka.archive.management.system.service.SysRobotCorpusService;
import com.hna.hka.archive.management.system.util.DateUtil;
import com.hna.hka.archive.management.system.util.IdUtils;
import com.hna.hka.archive.management.system.util.PageDataResult;
import com.hna.hka.archive.management.system.util.Tinypinyin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @ProjectName: IDEA-CODE
 * @Package: com.hna.hka.archive.management.system.service.impl
 * @ClassName: SysRobotCorpusServiceImpl
 * @Author: 郭凯
 * @Description: 语义交互管理业务层（实现）
 * @Date: 2020/6/4 10:47
 * @Version: 1.0
 */
@Service
@Transactional
public class SysRobotCorpusServiceImpl implements SysRobotCorpusService {

    @Autowired
    private SysRobotCorpusMapper sysRobotCorpusMapper;

    @Autowired
    private SysScenicSpotResourceVersionMapper sysScenicSpotResourceVersionMapper;

    @Autowired
    private SysRobotProblemExtendMapper sysRobotProblemExtendMapper;

    /**
     * @Author 郭凯
     * @Description 语义交互管理列表查询
     * @Date 10:49 2020/6/4
     * @Param [pageNum, pageSize, search]
     * @return com.hna.hka.archive.management.system.util.PageDataResult
    **/
    @Override
    public PageDataResult getSemanticsList(Integer pageNum, Integer pageSize, Map<String, String> search) {
        PageDataResult pageDataResult = new PageDataResult();
        PageHelper.startPage(pageNum, pageSize);
        List<SysRobotCorpus> sysRobotCorpusList = sysRobotCorpusMapper.getSemanticsList(search);
        for(SysRobotCorpus sysRobotCorpus : sysRobotCorpusList) {
            if ("2019924".equals(String.valueOf(sysRobotCorpus.getGenericType()))) {
                sysRobotCorpus.setScenicSpotName("通用类型");
                sysRobotCorpus.setGenericTypeTwo("2");
            }else{
                sysRobotCorpus.setGenericTypeTwo("1");
            }
        }
        if (sysRobotCorpusList.size() != 0){
            PageInfo<SysRobotCorpus> pageInfo = new PageInfo<>(sysRobotCorpusList);
            pageDataResult.setList(pageInfo.getList());
            pageDataResult.setTotals((int) pageInfo.getTotal());
        }
        return pageDataResult;
    }

    /**
     * @Author 郭凯
     * @Description 删除语义交互
     * @Date 14:12 2020/6/4
     * @Param [corpusId]
     * @return int
    **/
    @Override
    public int delSemantics(Long corpusId) {
        SysRobotCorpus SysRobotCorpus = sysRobotCorpusMapper.selectByPrimaryKey(corpusId);
        int i = sysRobotCorpusMapper.deleteByPrimaryKey(corpusId);
        if (!"2019924".equals(SysRobotCorpus.getGenericType())){
            if (i == 1) {
                SysScenicSpotResourceVersion sysScenicSpotResourceVersion = new SysScenicSpotResourceVersion();
                sysScenicSpotResourceVersion.setScenicSpotId(Long.parseLong(SysRobotCorpus.getGenericType()));
                sysScenicSpotResourceVersion.setResType("1");
                SysScenicSpotResourceVersion resourceVersion = sysScenicSpotResourceVersionMapper.selectResourceVersion(sysScenicSpotResourceVersion);
                double resVersion = Double.parseDouble(resourceVersion.getResVersion());
                double s = 0.1;
                BigDecimal p1 = new BigDecimal(Double.toString(resVersion));
                BigDecimal p2 = new BigDecimal(Double.toString(s));
                double a = p1.add(p2).doubleValue();
                SysScenicSpotResourceVersion sysScenicSpotResourceVersion2 = new SysScenicSpotResourceVersion();
                sysScenicSpotResourceVersion2.setScenicSpotId(Long.parseLong(SysRobotCorpus.getGenericType()));
                sysScenicSpotResourceVersion2.setResType("1");
                sysScenicSpotResourceVersion2.setResVersion(String.valueOf(a));
                sysScenicSpotResourceVersion2.setUpdateDate(DateUtil.currentDateTime());
                sysScenicSpotResourceVersionMapper.updateResourceVersion(sysScenicSpotResourceVersion2);
            }
        }
        return i;
    }

    /**
     * @Author 郭凯
     * @Description 新增语义交互
     * @Date 16:43 2020/7/31
     * @Param [sysRobotCorpusWithBLOBs]
     * @return int
    **/
    @Override
    public int addSemantics(SysRobotCorpusWithBLOBs sysRobotCorpusWithBLOBs) {
        sysRobotCorpusWithBLOBs.setCorpusId(IdUtils.getSeqId());
        sysRobotCorpusWithBLOBs.setPinYinProblem(Tinypinyin.tinypinyin(sysRobotCorpusWithBLOBs.getCorpusProblem()));
        sysRobotCorpusWithBLOBs.setCorpusType("1");
        sysRobotCorpusWithBLOBs.setUpdateDate(DateUtil.currentDateTime());
        sysRobotCorpusWithBLOBs.setCreateDate(DateUtil.currentDateTime());
        int i = sysRobotCorpusMapper.insertSelective(sysRobotCorpusWithBLOBs);
        if (!"2019924".equals(sysRobotCorpusWithBLOBs.getGenericType())) {
            if (i == 1){
                SysScenicSpotResourceVersion sysScenicSpotResourceVersion = new SysScenicSpotResourceVersion();
                sysScenicSpotResourceVersion.setScenicSpotId(Long.parseLong(sysRobotCorpusWithBLOBs.getGenericType()));
                sysScenicSpotResourceVersion.setResType("1");
                SysScenicSpotResourceVersion resourceVersion = sysScenicSpotResourceVersionMapper.selectResourceVersion(sysScenicSpotResourceVersion);
                double resVersion = Double.parseDouble(resourceVersion.getResVersion());
                double s = 0.1;
                BigDecimal p1 = new BigDecimal(Double.toString(resVersion));
                BigDecimal p2 = new BigDecimal(Double.toString(s));
                double a = p1.add(p2).doubleValue();
                SysScenicSpotResourceVersion sysScenicSpotResourceVersion2 = new SysScenicSpotResourceVersion();
                sysScenicSpotResourceVersion2.setScenicSpotId(Long.parseLong(sysRobotCorpusWithBLOBs.getGenericType()));
                sysScenicSpotResourceVersion2.setResType("1");
                sysScenicSpotResourceVersion2.setResVersion(String.valueOf(a));
                sysScenicSpotResourceVersion2.setUpdateDate(DateUtil.currentDateTime());
                sysScenicSpotResourceVersionMapper.updateResourceVersion(sysScenicSpotResourceVersion2);
            }
        }
        return i;
    }

    /**
     * @Author 郭凯
     * @Description 编辑语义交互
     * @Date 10:33 2020/8/1
     * @Param [sysRobotCorpusWithBLOBs]
     * @return int
    **/
    @Override
    public int editSemantics(SysRobotCorpusWithBLOBs sysRobotCorpusWithBLOBs) {
        sysRobotCorpusWithBLOBs.setPinYinProblem(Tinypinyin.tinypinyin(sysRobotCorpusWithBLOBs.getCorpusProblem()));
        sysRobotCorpusWithBLOBs.setCorpusResUrl("");
        sysRobotCorpusWithBLOBs.setCorpusType("1");
        sysRobotCorpusWithBLOBs.setUpdateDate(DateUtil.currentDateTime());
        int i = sysRobotCorpusMapper.updateByPrimaryKeySelective(sysRobotCorpusWithBLOBs);
        if (!"2019924".equals(sysRobotCorpusWithBLOBs.getGenericType())) {
            SysScenicSpotResourceVersion sysScenicSpotResourceVersion = new SysScenicSpotResourceVersion();
            sysScenicSpotResourceVersion.setScenicSpotId(Long.parseLong(sysRobotCorpusWithBLOBs.getGenericType()));
            sysScenicSpotResourceVersion.setResType("1");
            SysScenicSpotResourceVersion resourceVersion = sysScenicSpotResourceVersionMapper
                    .selectResourceVersion(sysScenicSpotResourceVersion);
            double resVersion = Double.parseDouble(resourceVersion.getResVersion());
            double s = 0.1;
            BigDecimal p1 = new BigDecimal(Double.toString(resVersion));
            BigDecimal p2 = new BigDecimal(Double.toString(s));
            double a = p1.add(p2).doubleValue();
            SysScenicSpotResourceVersion sysScenicSpotResourceVersion2 = new SysScenicSpotResourceVersion();
            sysScenicSpotResourceVersion2.setScenicSpotId(Long.parseLong(sysRobotCorpusWithBLOBs.getGenericType()));
            sysScenicSpotResourceVersion2.setResType("1");
            sysScenicSpotResourceVersion2.setResVersion(String.valueOf(a));
            sysScenicSpotResourceVersion2.setUpdateDate(DateUtil.currentDateTime());
            sysScenicSpotResourceVersionMapper.updateResourceVersion(sysScenicSpotResourceVersion2);
        }
        return i;
    }

    /**
     * @Author 郭凯
     * @Description 语义交互管理详情列表查询
     * @Date 14:58 2020/11/5
     * @Param [pageNum, pageSize, search]
     * @return com.hna.hka.archive.management.system.util.PageDataResult
    **/
    @Override
    public PageDataResult getSemanticsDetailsList(Integer pageNum, Integer pageSize, Map<String, Object> search) {
        PageDataResult pageDataResult = new PageDataResult();
        PageHelper.startPage(pageNum, pageSize);
        List<SysRobotProblemExtend> problemExtendList = sysRobotProblemExtendMapper.getSemanticsDetailsList(search);
        if (problemExtendList.size() != 0){
            PageInfo<SysRobotProblemExtend> pageInfo = new PageInfo<>(problemExtendList);
            pageDataResult.setList(pageInfo.getList());
            pageDataResult.setTotals((int) pageInfo.getTotal());
        }
        return pageDataResult;
    }

    /**
     * @Author 郭凯
     * @Description 资源详情新增
     * @Date 15:28 2020/11/5
     * @Param [sysRobotProblemExtend]
     * @return int
    **/
    @Override
    public int addSemanticsDetails(SysRobotProblemExtend sysRobotProblemExtend) {
        SysRobotCorpusWithBLOBs SysRobotProblemExtend = sysRobotCorpusMapper.selectByPrimaryKey(sysRobotProblemExtend.getCorpusId());
        sysRobotProblemExtend.setExtendId(IdUtils.getSeqId());
        sysRobotProblemExtend.setScenicSpotId(Long.parseLong(SysRobotProblemExtend.getGenericType()));
        sysRobotProblemExtend.setExtendCorpusPinyin(Tinypinyin.tinypinyin(sysRobotProblemExtend.getExtendCorpusProblem()));
        sysRobotProblemExtend.setExtendType("1");
        sysRobotProblemExtend.setCreateDate(DateUtil.currentDateTime());
        sysRobotProblemExtend.setUpdateDate(DateUtil.currentDateTime());
        return sysRobotProblemExtendMapper.insertSelective(sysRobotProblemExtend);
    }

    /**
     * @Author 郭凯
     * @Description 扩展问题删除
     * @Date 15:47 2020/11/5
     * @Param [extendId]
     * @return int
    **/
    @Override
    public int delSemanticsDetails(Long extendId) {
        return sysRobotProblemExtendMapper.deleteByPrimaryKey(extendId);
    }

    /**
     * @Author 郭凯
     * @Description 资源详情修改
     * @Date 16:04 2020/11/5
     * @Param [sysRobotProblemExtend]
     * @return int
    **/
    @Override
    public int editSemanticsDetails(SysRobotProblemExtend sysRobotProblemExtend) {
        sysRobotProblemExtend.setExtendCorpusPinyin(Tinypinyin.tinypinyin(sysRobotProblemExtend.getExtendCorpusProblem()));
        sysRobotProblemExtend.setUpdateDate(DateUtil.currentDateTime());
        return sysRobotProblemExtendMapper.updateByPrimaryKeySelective(sysRobotProblemExtend);
    }
}
