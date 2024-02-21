package com.hna.hka.archive.management.system.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hna.hka.archive.management.system.dao.SysScenicSpotCapPriceLogMapper;
import com.hna.hka.archive.management.system.model.SysScenicSpotCapPriceLog;
import com.hna.hka.archive.management.system.service.SysScenicSpotCapPriceLogService;
import com.hna.hka.archive.management.system.util.PageDataResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @ProjectName: IDEA-CODE
 * @Package: com.hna.hka.archive.management.system.service.impl
 * @ClassName: SysScenicSpotCapPriceServiceImpl
 * @Author: 郭凯
 * @Description: 景区封顶价格修改日志业务层（实现）
 * @Date: 2020/9/10 9:49
 * @Version: 1.0
 */
@Service
@Transactional
public class SysScenicSpotCapPriceLogServiceImpl implements SysScenicSpotCapPriceLogService {

    @Autowired
    private SysScenicSpotCapPriceLogMapper sysScenicSpotCapPriceLogMapper;

    /**
     * @Author 郭凯
     * @Description 景区封顶价格修改日志列表查询
     * @Date 9:51 2020/9/10
     * @Param [pageNum, pageSize, search]
     * @return com.hna.hka.archive.management.system.util.PageDataResult
    **/
    @Override
    public PageDataResult getScenicSpotCapPriceLogList(Integer pageNum, Integer pageSize, Map<String, Object> search) {
        PageDataResult pageDataResult = new PageDataResult();
        PageHelper.startPage(pageNum, pageSize);
        List<SysScenicSpotCapPriceLog> sysScenicSpotCapPriceLogList = sysScenicSpotCapPriceLogMapper.getScenicSpotCapPriceLogList(search);
        if(sysScenicSpotCapPriceLogList.size() != 0){
            PageInfo<SysScenicSpotCapPriceLog> pageInfo = new PageInfo<>(sysScenicSpotCapPriceLogList);
            pageDataResult.setList(pageInfo.getList());
            pageDataResult.setTotals((int) pageInfo.getTotal());
        }
        return pageDataResult;
    }

//    /**
//     * @Author zhang
//     * @Description 景区封顶价格列表导出
//     * @Date 9:51 2020/9/10
//     * @Param [pageNum, pageSize, search]
//     * @return com.hna.hka.archive.management.system.util.PageDataResult
//     **/
//    @Override
//    public List<SysScenicSpotCapPriceLog> getScenicSpotCapPriceLogExcel( Map<String, String> search) {
//
//        // TODO Auto-generated method stub
//        List<SysScenicSpotCapPriceLog> sysScenicSpotCapPriceLogList = sysScenicSpotCapPriceLogMapper.getScenicSpotCapPriceLogExcel(search);
//        return sysScenicSpotCapPriceLogList;
//    }
}
