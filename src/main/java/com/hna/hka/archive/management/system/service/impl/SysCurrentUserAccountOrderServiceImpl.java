package com.hna.hka.archive.management.system.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hna.hka.archive.management.system.dao.SysCurrentUserAccountOrderMapper;
import com.hna.hka.archive.management.system.model.SysCurrentUserAccountOrder;
import com.hna.hka.archive.management.system.service.SysCurrentUserAccountOrderService;
import com.hna.hka.archive.management.system.util.PageDataResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @ProjectName: IDEA-CODE
 * @Package: com.hna.hka.archive.management.system.service.impl
 * @ClassName: SysCurrentUserAccountOrderServiceImpl
 * @Author: 郭凯
 * @Description: 储值记录管理业务层（实现）
 * @Date: 2020/11/5 11:42
 * @Version: 1.0
 */
@Service
@Transactional
public class SysCurrentUserAccountOrderServiceImpl implements SysCurrentUserAccountOrderService {

    @Autowired
    private SysCurrentUserAccountOrderMapper sysCurrentUserAccountOrderMapper;

    /**
     * @Author 郭凯
     * @Description 储值记录管理列表查询
     * @Date 13:22 2020/11/5
     * @Param [pageNum, pageSize, search]
     * @return com.hna.hka.archive.management.system.util.PageDataResult
    **/
    @Override
    public PageDataResult getCurrentUserAccountOrderList(Integer pageNum, Integer pageSize, Map<String, Object> search) {
        PageDataResult pageDataResult = new PageDataResult();
        PageHelper.startPage(pageNum, pageSize);
        List<SysCurrentUserAccountOrder> sysCurrentUserAccountOrderList = sysCurrentUserAccountOrderMapper.getCurrentUserAccountOrderList(search);
        if (sysCurrentUserAccountOrderList.size() > 0){
            PageInfo<SysCurrentUserAccountOrder> pageInfo = new PageInfo<>(sysCurrentUserAccountOrderList);
            pageDataResult.setList(pageInfo.getList());
            pageDataResult.setTotals((int) pageInfo.getTotal());
        }
        return pageDataResult;
    }

    /**
     * @Author 郭凯
     * @Description 下载储值记录Excel表
     * @Date 13:45 2020/11/5
     * @Param [search]
     * @return java.util.List<com.hna.hka.archive.management.system.model.SysCurrentUserAccountOrder>
    **/
    @Override
    public List<SysCurrentUserAccountOrder> getCurrentUserAccountOrderExcel(Map<String, Object> search) {
        return sysCurrentUserAccountOrderMapper.getCurrentUserAccountOrderList(search);
    }

    /**
     * @Description 根据用户id查询用户的储值记录
     * @param userId
     * @return
     */
    @Override
    public List<SysCurrentUserAccountOrder> getCurrentUserAccountOrderListByUserId(Long userId) {



        return sysCurrentUserAccountOrderMapper.getCurrentUserAccountOrderListByUserId(userId);
    }

    /**
     * 修改储值记录
     * @param order
     * @return
     */
    @Override
    public int updateCurrentUserAccountOrder(SysCurrentUserAccountOrder order) {

        return sysCurrentUserAccountOrderMapper.updateByPrimaryKeySelective(order);

    }
}
