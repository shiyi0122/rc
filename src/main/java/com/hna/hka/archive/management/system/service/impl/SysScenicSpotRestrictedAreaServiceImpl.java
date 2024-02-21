package com.hna.hka.archive.management.system.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hna.hka.archive.management.system.dao.SysScenicSpotRestrictedAreaMapper;
import com.hna.hka.archive.management.system.model.SysScenicSpotRestrictedAreaWithBLOBs;
import com.hna.hka.archive.management.system.service.SysScenicSpotRestrictedAreaService;
import com.hna.hka.archive.management.system.util.PageDataResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @ProjectName: IDEA-CODE
 * @Package: com.hna.hka.archive.management.system.service.impl
 * @ClassName: SysScenicSpotRestrictedAreaServiceImpl
 * @Author: 郭凯
 * @Description: 禁区告警日志业务层（实现）
 * @Date: 2020/5/31 16:33
 * @Version: 1.0
 */
@Service
@Transactional
public class SysScenicSpotRestrictedAreaServiceImpl implements SysScenicSpotRestrictedAreaService {

    @Autowired
    private SysScenicSpotRestrictedAreaMapper sysScenicSpotRestrictedAreaMapper;

    /**
     * @Author 郭凯
     * @Description 禁区告警日志列表查询
     * @Date 16:35 2020/5/31
     * @Param [pageNum, pageSize, search]
     * @return com.hna.hka.archive.management.system.util.PageDataResult
    **/
    @Override
    public PageDataResult getRestrictedAreatList(Integer pageNum, Integer pageSize, Map<String, Object> search) {
        PageDataResult pageDataResult = new PageDataResult();
        PageHelper.startPage(pageNum, pageSize);
        List<SysScenicSpotRestrictedAreaWithBLOBs> SysScenicSpotRestrictedAreaWithBLOBs = sysScenicSpotRestrictedAreaMapper.getRestrictedAreatList(search);
        if (SysScenicSpotRestrictedAreaWithBLOBs.size() != 0){
            PageInfo<SysScenicSpotRestrictedAreaWithBLOBs> pageInfo = new PageInfo<>(SysScenicSpotRestrictedAreaWithBLOBs);
            pageDataResult.setList(pageInfo.getList());
            pageDataResult.setTotals((int) pageInfo.getTotal());
        }
        return pageDataResult;
    }

    /**
     * @Author 郭凯
     * @Description 查询下载所需要的的数据
     * @Date 17:04 2020/5/31
     * @Param [search]
     * @return java.util.List<com.hna.hka.archive.management.system.model.SysScenicSpotRestrictedAreaWithBLOBs>
    **/
    @Override
    public List<SysScenicSpotRestrictedAreaWithBLOBs> getUploadExcelRestrictedArea(Map<String, Object> search) {
        return sysScenicSpotRestrictedAreaMapper.getRestrictedAreatList(search);
    }
}
