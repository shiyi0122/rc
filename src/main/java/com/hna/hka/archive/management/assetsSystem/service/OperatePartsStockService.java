package com.hna.hka.archive.management.assetsSystem.service;

import com.hna.hka.archive.management.assetsSystem.model.OperatePartsStock;
import com.hna.hka.archive.management.assetsSystem.model.StatementOfAccessories;
import com.hna.hka.archive.management.system.util.PageDataResult;

import java.util.List;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2022/11/11 17:43
 */
public interface OperatePartsStockService {
    PageDataResult list(Map<String, Object> search);

    int addOperatePartsStock(OperatePartsStock operatePartsStock);


    int editOperatePartsStock(OperatePartsStock operatePartsStock);


    int delOperatePartsStock(Long id);

    List<OperatePartsStock> download(Map<String, Object> search);


    OperatePartsStock selectByApplyNumber(Long applyNumber);


}
