package com.hna.hka.archive.management.system.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hna.hka.archive.management.system.dao.SysScenicSpotCustomTypeMapper;
import com.hna.hka.archive.management.system.model.SysScenicSpotCustomType;
import com.hna.hka.archive.management.system.service.SysScenicSpotCustomTypeService;
import com.hna.hka.archive.management.system.util.DateUtil;
import com.hna.hka.archive.management.system.util.IdUtils;
import com.hna.hka.archive.management.system.util.PageDataResult;
import com.hna.hka.archive.management.system.util.ToolUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @ProjectName: IDEA-CODE
 * @Package: com.hna.hka.archive.management.system.service.impl
 * @ClassName: SysScenicSpotCustomTypeServiceImpl
 * @Author: 郭凯
 * @Description: 自定义类型业务层（实现）
 * @Date: 2020/7/27 15:40
 * @Version: 1.0
 */
@Service
@Transactional
public class SysScenicSpotCustomTypeServiceImpl implements SysScenicSpotCustomTypeService {

    @Autowired
    private SysScenicSpotCustomTypeMapper sysScenicSpotCustomTypeMapper;

    /**
     * @Author 郭凯
     * @Description 自定义类型列表查询
     * @Date 15:42 2020/7/27
     * @Param [pageNum, pageSize, search]
     * @return com.hna.hka.archive.management.system.util.PageDataResult
    **/
    @Override
    public PageDataResult getCustomTypeList(Integer pageNum, Integer pageSize, Map<String, String> search) {
        PageDataResult pageDataResult = new PageDataResult();
        PageHelper.startPage(pageNum, pageSize);
        List<SysScenicSpotCustomType> sysScenicSpotCustomTypeList = sysScenicSpotCustomTypeMapper.getCustomTypeList(search);
        if(sysScenicSpotCustomTypeList.size() != 0){
            PageInfo<SysScenicSpotCustomType> pageInfo = new PageInfo<>(sysScenicSpotCustomTypeList);
            pageDataResult.setList(pageInfo.getList());
            pageDataResult.setTotals((int) pageInfo.getTotal());
        }
        return pageDataResult;
    }

    /**
     * @Author 郭凯
     * @Description 删除自定义类型
     * @Date 17:28 2020/7/27
     * @Param [typeId]
     * @return int
    **/
    @Override
    public int delCustomType(Long typeId) {
        return sysScenicSpotCustomTypeMapper.deleteByPrimaryKey(typeId);
    }

    /**
     * @Author 郭凯
     * @Description 添加自定义类型
     * @Date 9:34 2020/7/28
     * @Param [sysScenicSpotCustomType]
     * @return int
    **/
    @Override
    public int addCustomType(SysScenicSpotCustomType sysScenicSpotCustomType) {
        SysScenicSpotCustomType customType = sysScenicSpotCustomTypeMapper.getCustomTypeById(sysScenicSpotCustomType);
        if (ToolUtil.isNotEmpty(customType)){
            return 2;
        }
        sysScenicSpotCustomType.setTypeId(IdUtils.getSeqId());
        sysScenicSpotCustomType.setCreateDate(DateUtil.currentDateTime());
        sysScenicSpotCustomType.setUpdateDate(DateUtil.currentDateTime());
        return sysScenicSpotCustomTypeMapper.insertSelective(sysScenicSpotCustomType);
    }

    /**
     * @Author 郭凯
     * @Description 修改自定义类型
     * @Date 10:01 2020/7/28
     * @Param [sysScenicSpotCustomType]
     * @return int
    **/
    @Override
    public int editCustomType(SysScenicSpotCustomType sysScenicSpotCustomType) {
        sysScenicSpotCustomType.setUpdateDate(DateUtil.currentDateTime());
        return sysScenicSpotCustomTypeMapper.updateByPrimaryKeySelective(sysScenicSpotCustomType);
    }

    /**
     * @Author 郭凯
     * @Description 提示音类型列表查询
     * @Date 17:40 2020/9/10
     * @Param [pageNum, pageSize, search]
     * @return com.hna.hka.archive.management.system.util.PageDataResult
    **/
    @Override
    public PageDataResult getCueToneTypeList(Integer pageNum, Integer pageSize, Map<String, String> search) {
        PageDataResult pageDataResult = new PageDataResult();
        PageHelper.startPage(pageNum, pageSize);
        List<SysScenicSpotCustomType> sysScenicSpotCustomTypeList = sysScenicSpotCustomTypeMapper.getCueToneTypeList(search);
        if(sysScenicSpotCustomTypeList.size() != 0){
            PageInfo<SysScenicSpotCustomType> pageInfo = new PageInfo<>(sysScenicSpotCustomTypeList);
            pageDataResult.setList(pageInfo.getList());
            pageDataResult.setTotals((int) pageInfo.getTotal());
        }
        return pageDataResult;
    }

    /**
     * @Author 郭凯
     * @Description 提示音类型新增
     * @Date 10:59 2020/9/11
     * @Param [sysScenicSpotCustomType]
     * @return int
    **/
    @Override
    public int addCueToneType(SysScenicSpotCustomType sysScenicSpotCustomType) {
        //查询此状态是否唯一
        SysScenicSpotCustomType scenicSpotCustomType = sysScenicSpotCustomTypeMapper.getCustomTypeByType(sysScenicSpotCustomType.getTypeNameValue());
        if (scenicSpotCustomType == null) {
            sysScenicSpotCustomType.setTypeId(IdUtils.getSeqId());
            sysScenicSpotCustomType.setTypeResId("3");
            sysScenicSpotCustomType.setCreateDate(DateUtil.currentDateTime());
            sysScenicSpotCustomType.setUpdateDate(DateUtil.currentDateTime());
            return sysScenicSpotCustomTypeMapper.insertSelective(sysScenicSpotCustomType);
        }else {
            return 2;
        }
    }

    @Override
    /**
     * @Author 郭凯
     * @Description 查询机器人提示音类型
     * @Date 10:57 2020/11/16
     * @Param [search]
     * @return int
    **/
    public List<SysScenicSpotCustomType> getRobotSoundsCueToneTypeList(Map<String, String> search) {
        return sysScenicSpotCustomTypeMapper.getCueToneTypeList(search);
    }
}
