package com.hna.hka.archive.management.system.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hna.hka.archive.management.system.dao.SysScenicSpotCertificateSpotMapper;
import com.hna.hka.archive.management.system.dao.WechatBusinessManagementMapper;
import com.hna.hka.archive.management.system.model.SysScenicSpotCertificateSpot;
import com.hna.hka.archive.management.system.model.WechatBusinessManagement;
import com.hna.hka.archive.management.system.service.SysScenicSpotCertificateSpotService;
import com.hna.hka.archive.management.system.util.DateUtil;
import com.hna.hka.archive.management.system.util.IdUtils;
import com.hna.hka.archive.management.system.util.PageDataResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @ProjectName: IDEA-CODE
 * @Package: com.hna.hka.archive.management.system.service.impl
 * @ClassName: SysScenicSpotCertificateSpotServiceImpl
 * @Author: 郭凯
 * @Description: 景区证书管理业务层（实现）
 * @Date: 2020/5/28 9:43
 * @Version: 1.0
 */
@Service
@Transactional
public class SysScenicSpotCertificateSpotServiceImpl implements SysScenicSpotCertificateSpotService {

    @Autowired
    private SysScenicSpotCertificateSpotMapper sysScenicSpotCertificateSpotMapper;

    @Autowired
    private WechatBusinessManagementMapper wechatBusinessManagementMapper;


    /**
     * @Author 郭凯
     * @Description 景区证书列表查询
     * @Date 9:46 2020/5/28
     * @Param [pageNum, pageSize, search]
     * @return com.hna.hka.archive.management.system.util.PageDataResult
    **/
    @Override
    public PageDataResult getCertificateSpotList(Integer pageNum, Integer pageSize, Map<String, Object> search) {
        PageDataResult pageDataResult = new PageDataResult();
        PageHelper.startPage(pageNum, pageSize);
        List<SysScenicSpotCertificateSpot> scenicSpotCertificateSpotList = sysScenicSpotCertificateSpotMapper.getCertificateSpotList(search);
        if(scenicSpotCertificateSpotList.size() != 0){
            PageInfo<SysScenicSpotCertificateSpot> pageInfo = new PageInfo<>(scenicSpotCertificateSpotList);
            pageDataResult.setList(pageInfo.getList());
            pageDataResult.setTotals((int) pageInfo.getTotal());
        }
        return pageDataResult;
    }

    /**
     * @Author 郭凯
     * @Description 新增景区证书
     * @Date 14:59 2020/5/28
     * @Param [sysScenicSpotCertificateSpot]
     * @return int
    **/
    @Override
    public int addCertificateSpot(SysScenicSpotCertificateSpot sysScenicSpotCertificateSpot) {
        sysScenicSpotCertificateSpot.setCertificateSpotId(IdUtils.getSeqId());
        sysScenicSpotCertificateSpot.setCreateDate(DateUtil.currentDateTime());
        sysScenicSpotCertificateSpot.setUpdateDate(DateUtil.currentDateTime());
        return sysScenicSpotCertificateSpotMapper.insertSelective(sysScenicSpotCertificateSpot);
    }

    /**
     * @Author 郭凯
     * @Description 查询此景区是否存在证书
     * @Date 15:04 2020/5/28
     * @Param [scenicSpotId]
     * @return com.hna.hka.archive.management.system.model.SysScenicSpotCertificateSpot
    **/
    @Override
    public SysScenicSpotCertificateSpot selectCertificateSpotById(Long scenicSpotId) {
        return sysScenicSpotCertificateSpotMapper.selectByScenicSpotId(scenicSpotId);
    }

    /**
     * @Author 郭凯
     * @Description 修改景区证书
     * @Date 15:49 2020/5/28
     * @Param [sysScenicSpotCertificateSpot]
     * @return int
    **/
    @Override
    public int editCertificateSpot(SysScenicSpotCertificateSpot sysScenicSpotCertificateSpot) {
        sysScenicSpotCertificateSpot.setUpdateDate(DateUtil.currentDateTime());
        return sysScenicSpotCertificateSpotMapper.updateByPrimaryKeySelective(sysScenicSpotCertificateSpot);
    }

    /**
     * @Author 郭凯
     * @Description 删除景区证书
     * @Date 15:52 2020/5/28
     * @Param [certificateSpotId]
     * @return int
    **/
    @Override
    public int delCertificateSpot(Long certificateSpotId) {
        return sysScenicSpotCertificateSpotMapper.deleteByPrimaryKey(certificateSpotId);
    }

    /**
     * @Author 郭凯
     * @Description 查询景区是否存在证书
     * @Date 14:19 2020/8/18
     * @Param [orderScenicSpotId]
     * @return com.hna.hka.archive.management.system.model.WechatBusinessManagement
    **/
    @Override
    public WechatBusinessManagement getBusinessManagementByScenicSpotId(Long orderScenicSpotId) {
        return wechatBusinessManagementMapper.getBusinessManagementByScenicSpotId(orderScenicSpotId);
    }

    /**
     * @Method getUploadExcelCertificateSpot
     * @Author 郭凯
     * @Version  1.0
     * @Description 景区证书下载Excel数据查询
     * @Return java.util.List<com.hna.hka.archive.management.system.model.SysScenicSpotCertificateSpot>
     * @Date 2021/4/7 13:56
     */
    @Override
    public List<SysScenicSpotCertificateSpot> getUploadExcelCertificateSpot(Map<String, Object> search) {
        return sysScenicSpotCertificateSpotMapper.getCertificateSpotList(search);
    }

    @Override
    public WechatBusinessManagement selectById(Map<String, Object> search) {
        return sysScenicSpotCertificateSpotMapper.selectById(search);
    }
}
