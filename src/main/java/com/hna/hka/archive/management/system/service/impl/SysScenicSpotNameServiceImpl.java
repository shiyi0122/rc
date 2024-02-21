package com.hna.hka.archive.management.system.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hna.hka.archive.management.system.dao.SysScenicSpotNameMapper;
import com.hna.hka.archive.management.system.model.SysScenicSpotName;
import com.hna.hka.archive.management.system.service.SysScenicSpotNameService;
import com.hna.hka.archive.management.system.util.PageDataResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @ProjectName: IDEA-CODE
 * @Package: com.hna.hka.archive.management.system.service.impl
 * @ClassName: SysScenicSpotNameServiceImpl
 * @Author: 郭凯
 * @Description: 景区名称管理业务层（实现）
 * @Date: 2020/12/3 14:00
 * @Version: 1.0
 */
@Service
@Transactional
public class SysScenicSpotNameServiceImpl implements SysScenicSpotNameService {

    @Autowired
    private SysScenicSpotNameMapper sysScenicSpotNameMapper;

    /**
     * @Author 郭凯
     * @Description 景区名称列表查询
     * @Date 14:01 2020/12/3
     * @Param [pageNum, pageSize, search]
     * @return com.hna.hka.archive.management.system.util.PageDataResult
    **/
    @Override
    public PageDataResult getScenicSpotNameList(Integer pageNum, Integer pageSize, Map<String, Object> search) {
        PageDataResult pageDataResult = new PageDataResult();
        PageHelper.startPage(pageNum, pageSize);
        List<SysScenicSpotName> sysScenicSpotNameList = sysScenicSpotNameMapper.getScenicSpotNameList(search);
        if (sysScenicSpotNameList.size() != 0){
            PageInfo<SysScenicSpotName> pageInfo = new PageInfo<>(sysScenicSpotNameList);
            pageDataResult.setList(pageInfo.getList());
            pageDataResult.setTotals((int) pageInfo.getTotal());
        }
        return pageDataResult;
    }
}
