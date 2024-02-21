package com.hna.hka.archive.management.assetsSystem.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hna.hka.archive.management.assetsSystem.dao.SysOrderExceptionManagementMapper;
import com.hna.hka.archive.management.assetsSystem.model.SysOrderExceptionManagement;
import com.hna.hka.archive.management.assetsSystem.service.SysOrderExceptionManagementService;
import com.hna.hka.archive.management.system.util.DateUtil;
import com.hna.hka.archive.management.system.util.DictUtils;
import com.hna.hka.archive.management.system.util.IdUtils;
import com.hna.hka.archive.management.system.util.PageDataResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ProjectName: rc
 * @Package: com.hna.hka.archive.management.assetsSystem.service.impl
 * @ClassName: SysOrderExceptionManagementServiceImpl
 * @Author: 郭凯
 * @Description: 异常订单管理业务层（实现）
 * @Date: 2021/6/2 14:40
 * @Version: 1.0
 */
@Service
@Transactional
public class SysOrderExceptionManagementServiceImpl implements SysOrderExceptionManagementService {

    @Autowired
    private SysOrderExceptionManagementMapper sysOrderExceptionManagementMapper;


    /**
     * @Method getOrderExceptionManagementList
     * @Author 郭凯
     * @Version  1.0
     * @Description 异常订单管理列表查询
     * @Return com.hna.hka.archive.management.system.util.PageDataResult
     * @Date 2021/6/2 14:42
     */
    @Override
    public PageDataResult getOrderExceptionManagementList(Integer pageNum, Integer pageSize, Map<String, Object> search) {
        PageDataResult pageDataResult = new PageDataResult();
        PageHelper.startPage(pageNum, pageSize);
        List<SysOrderExceptionManagement> orderExceptionManagementList = sysOrderExceptionManagementMapper.getOrderExceptionManagementList(search);
        for (SysOrderExceptionManagement orderExceptionManagement : orderExceptionManagementList){
            orderExceptionManagement.setCausesName(DictUtils.getCausesMap().get(orderExceptionManagement.getCauses()));
        }
        if (orderExceptionManagementList.size() > 0){
            PageInfo<SysOrderExceptionManagement> pageInfo = new PageInfo<>(orderExceptionManagementList);
            pageDataResult.setList(pageInfo.getList());
            pageDataResult.setTotals((int) pageInfo.getTotal());
        }
        return pageDataResult;
    }

    /**
     * @Method addOrderExceptionManagement
     * @Author 郭凯
     * @Version  1.0
     * @Description 异常订单管理新增
     * @Return int
     * @Date 2021/6/3 9:16
     */
    @Override
    public int addOrderExceptionManagement(SysOrderExceptionManagement sysOrderExceptionManagement) {
        sysOrderExceptionManagement.setOrderExceptionManagementId(IdUtils.getSeqId());
        sysOrderExceptionManagement.setCreateTime(DateUtil.currentDateTime());
        sysOrderExceptionManagement.setUpdateTime(DateUtil.currentDateTime());
        return sysOrderExceptionManagementMapper.insertSelective(sysOrderExceptionManagement);
    }

    /**
     * @Method editOrderExceptionManagement
     * @Author 郭凯
     * @Version  1.0
     * @Description 异常订单管理编辑
     * @Return int
     * @Date 2021/6/3 9:29
     */
    @Override
    public int editOrderExceptionManagement(SysOrderExceptionManagement sysOrderExceptionManagement) {
        sysOrderExceptionManagement.setUpdateTime(DateUtil.currentDateTime());
        return sysOrderExceptionManagementMapper.updateByPrimaryKeySelective(sysOrderExceptionManagement);
    }

    /**
     * @Method delOrderExceptionManagement
     * @Author 郭凯
     * @Version  1.0
     * @Description 异常订单管理删除
     * @Return int
     * @Date 2021/6/3 9:36
     */
    @Override
    public int delOrderExceptionManagement(Long orderExceptionManagementId) {
        return sysOrderExceptionManagementMapper.deleteByPrimaryKey(orderExceptionManagementId);
    }

    /**
     * @Method getOderExceptionManagementExcel
     * @Author 郭凯
     * @Version  1.0
     * @Description 异常订单信息下载Excel数据查询
     * @Return java.util.List<com.hna.hka.archive.management.assetsSystem.model.SysOrderExceptionManagement>
     * @Date 2021/6/3 10:15
     */
    @Override
    public List<SysOrderExceptionManagement> getOderExceptionManagementExcel(Map<String, Object> search) {
        List<SysOrderExceptionManagement> orderExceptionManagementList = sysOrderExceptionManagementMapper.getOrderExceptionManagementList(search);
        for (SysOrderExceptionManagement orderExceptionManagement : orderExceptionManagementList){
            orderExceptionManagement.setCausesName(DictUtils.getCausesMap().get(orderExceptionManagement.getCauses()));
        }
        return orderExceptionManagementList;
    }

    /**
     * @Method getOrderExceptionManagementVoList
     * @Author 郭凯
     * @Version  1.0
     * @Description 查询异常订单
     * @Return java.util.List<com.hna.hka.archive.management.assetsSystem.model.SysOrderExceptionManagement>
     * @Date 2021/7/9 18:02
     */
    @Override
    public List<SysOrderExceptionManagement> getOrderExceptionManagementVoList() {
        Map<String,Object> search = new HashMap<>();
        List<SysOrderExceptionManagement> orderExceptionManagementList = sysOrderExceptionManagementMapper.getOrderExceptionManagementList(search);
        return orderExceptionManagementList;
    }
}
