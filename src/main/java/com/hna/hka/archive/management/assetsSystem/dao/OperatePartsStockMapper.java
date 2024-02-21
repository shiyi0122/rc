package com.hna.hka.archive.management.assetsSystem.dao;

import com.hna.hka.archive.management.assetsSystem.model.OperatePartsStock;

import java.util.List;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2022/11/11 17:46
 */
public interface OperatePartsStockMapper {


    List<OperatePartsStock> list(Map<String, Object> search);


    int insertSelective(OperatePartsStock operatePartsStock);


    int updateByPrimaryKeySelective(OperatePartsStock operatePartsStock);


    int deleteByPrimaryKey(Long id);

    OperatePartsStock selectByApplyNumber(Long applyNumber);

}
