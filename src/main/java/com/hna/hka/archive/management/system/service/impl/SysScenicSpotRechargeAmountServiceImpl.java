package com.hna.hka.archive.management.system.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hna.hka.archive.management.system.dao.SysScenicSpotMapper;
import com.hna.hka.archive.management.system.dao.SysScenicSpotRechargeAmountMapper;
import com.hna.hka.archive.management.system.model.SysScenicSpot;
import com.hna.hka.archive.management.system.model.SysScenicSpotRechargeAmount;
import com.hna.hka.archive.management.system.service.SysScenicSpotRechargeAmountService;
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
 * @ClassName: SysScenicSpotRechargeAmountServiceImpl
 * @Author: 郭凯
 * @Description: 储值充值管理业务层（实现）
 * @Date: 2020/7/11 17:04
 * @Version: 1.0
 */
@Service
@Transactional
public class SysScenicSpotRechargeAmountServiceImpl implements SysScenicSpotRechargeAmountService {

    @Autowired
    private SysScenicSpotRechargeAmountMapper sysScenicSpotRechargeAmountMapper;

    @Autowired
    private SysScenicSpotMapper sysScenicSpotMapper;

    /**
     * @Author 郭凯
     * @Description 储值充值列表查询
     * @Date 17:07 2020/7/11
     * @Param [pageNum, pageSize, search]
     * @return com.hna.hka.archive.management.system.util.PageDataResult
    **/
    @Override
    public PageDataResult getRechargeAmountList(Integer pageNum, Integer pageSize, Map<String, String> search) {
        PageDataResult pageDataResult = new PageDataResult();
        PageHelper.startPage(pageNum, pageSize);
        List<SysScenicSpotRechargeAmount> sysScenicSpotRechargeAmountList = sysScenicSpotRechargeAmountMapper.getRechargeAmountList(search);
        if(sysScenicSpotRechargeAmountList.size() != 0){
            PageInfo<SysScenicSpotRechargeAmount> pageInfo = new PageInfo<>(sysScenicSpotRechargeAmountList);
            pageDataResult.setList(pageInfo.getList());
            pageDataResult.setTotals((int) pageInfo.getTotal());
        }
        return pageDataResult;
    }

    /**
     * @Author 郭凯
     * @Description 储值充值新增成功
     * @Date 15:16 2020/7/13
     * @Param [sysScenicSpotRechargeAmount]
     * @return int
    **/
    @Override
    public int addRechargeAmount(SysScenicSpotRechargeAmount sysScenicSpotRechargeAmount) {
        SysScenicSpot sysScenicSpot = sysScenicSpotMapper.selectByPrimaryKey(sysScenicSpotRechargeAmount.getRechargeScenicSpotId());
        sysScenicSpotRechargeAmount.setRechargeId(IdUtils.getSeqId());
        sysScenicSpotRechargeAmount.setRechargeScenicSpotName(sysScenicSpot.getScenicSpotName());
        sysScenicSpotRechargeAmount.setCreateDate(DateUtil.currentDateTime());
        sysScenicSpotRechargeAmount.setUpdateDate(DateUtil.currentDateTime());
        return sysScenicSpotRechargeAmountMapper.insertSelective(sysScenicSpotRechargeAmount);
    }

    /**
     * @Author 郭凯
     * @Description 删除储值充值
     * @Date 16:05 2020/7/13
     * @Param [rechargeId]
     * @return int
    **/
    @Override
    public int delRechargeAmount(Long rechargeId) {
        return sysScenicSpotRechargeAmountMapper.deleteByPrimaryKey(rechargeId);
    }

    /**
     * @Author 郭凯
     * @Description 修改储值充值
     * @Date 16:26 2020/7/13
     * @Param [sysScenicSpotRechargeAmount]
     * @return int
    **/
    @Override
    public int editRechargeAmount(SysScenicSpotRechargeAmount sysScenicSpotRechargeAmount) {
        SysScenicSpot sysScenicSpot = sysScenicSpotMapper.selectByPrimaryKey(sysScenicSpotRechargeAmount.getRechargeScenicSpotId());
        sysScenicSpotRechargeAmount.setRechargeScenicSpotName(sysScenicSpot.getScenicSpotName());
        sysScenicSpotRechargeAmount.setUpdateDate(DateUtil.currentDateTime());
        return sysScenicSpotRechargeAmountMapper.updateByPrimaryKeySelective(sysScenicSpotRechargeAmount);
    }
}
