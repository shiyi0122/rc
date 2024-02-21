package com.hna.hka.archive.management.assetsSystem.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hna.hka.archive.management.assetsSystem.dao.OperatePartsStockMapper;
import com.hna.hka.archive.management.assetsSystem.model.OperatePartsStock;
import com.hna.hka.archive.management.assetsSystem.model.StatementOfAccessories;
import com.hna.hka.archive.management.assetsSystem.service.OperatePartsStockService;
import com.hna.hka.archive.management.system.util.DateUtil;
import com.hna.hka.archive.management.system.util.IdUtils;
import com.hna.hka.archive.management.system.util.PageDataResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2022/11/11 17:43
 * 运营库存
 */
@Service
public class OperatePartsStockServiceImpl implements OperatePartsStockService {

    @Autowired
    OperatePartsStockMapper operatePartsStockMapper;


    /**
     * 获取列表
     * @param search
     * @return
     */
    @Override
    public PageDataResult list(Map<String, Object> search) {

        PageDataResult pageDataResult = new PageDataResult();

        PageHelper.startPage((int)search.get("pageNum"),(int)search.get("pageSize"));
        List<OperatePartsStock> list =  operatePartsStockMapper.list(search);

        if (list.size()>0){
            PageInfo<OperatePartsStock> pageInfo = new PageInfo<>(list);
            pageDataResult.setData(list);
            pageDataResult.setCode(200);
            pageDataResult.setTotals((int)pageInfo.getTotal());
        }

        return pageDataResult;
    }
    /**
     * 添加运营库存
     * @param operatePartsStock
     * @return
     */
    @Override
    public int addOperatePartsStock(OperatePartsStock operatePartsStock) {

        operatePartsStock.setId(IdUtils.getSeqId());
        operatePartsStock.setCreateTime(DateUtil.currentDateTime());
        operatePartsStock.setUpdateTime(DateUtil.currentDateTime());
       int i =  operatePartsStockMapper.insertSelective(operatePartsStock);

       return i;

    }

    /**
     * 编辑
     * @param operatePartsStock
     * @return
     */
    @Override
    public int editOperatePartsStock(OperatePartsStock operatePartsStock) {

        operatePartsStock.setUpdateTime(DateUtil.currentDateTime());
        int i = operatePartsStockMapper.updateByPrimaryKeySelective(operatePartsStock);
        return i;

    }

    /**
     * 删除
     * @param id
     * @return
     */
    @Override
    public int delOperatePartsStock(Long id) {

        int i = operatePartsStockMapper.deleteByPrimaryKey(id);

        return i;
    }

    /**
     * 导出
     * @param
     * @return
     */
    @Override
    public List<OperatePartsStock> download(Map<String, Object> search) {

        List<OperatePartsStock> list =  operatePartsStockMapper.list(search);

        return list;

    }

    /**
     * 根据申请单号查询数据
     * @param applyNumber
     * @return
     */
    @Override
    public OperatePartsStock selectByApplyNumber(Long applyNumber) {

        OperatePartsStock operatePartsStock = operatePartsStockMapper.selectByApplyNumber(applyNumber);

        return operatePartsStock;
    }
}
