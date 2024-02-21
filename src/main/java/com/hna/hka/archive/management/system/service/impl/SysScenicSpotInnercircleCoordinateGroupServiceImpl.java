package com.hna.hka.archive.management.system.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hna.hka.archive.management.system.dao.SysScenicSpotInnercircleCoordinateGroupMapper;
import com.hna.hka.archive.management.system.model.InnercricleExcel;
import com.hna.hka.archive.management.system.model.SysScenicSpotInnercircleCoordinateGroupWithBLOBs;
import com.hna.hka.archive.management.system.service.SysScenicSpotInnercircleCoordinateGroupService;
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
 * @ClassName: SysScenicSpotInnercircleCoordinateGroupServiceImpl
 * @Author: 郭凯
 * @Description: 内圈禁区管理业务层（实现）
 * @Date: 2020/6/8 15:27
 * @Version: 1.0
 */
@Service
@Transactional
public class SysScenicSpotInnercircleCoordinateGroupServiceImpl implements SysScenicSpotInnercircleCoordinateGroupService {

    @Autowired
    private SysScenicSpotInnercircleCoordinateGroupMapper sysScenicSpotInnercircleCoordinateGroupMapper;

    /**
     * @Author 郭凯
     * @Description 内圈禁区列表查询
     * @Date 15:27 2020/6/8
     * @Param [pageNum, pageSize, search]
     * @return com.hna.hka.archive.management.system.util.PageDataResult
    **/
    @Override
    public PageDataResult getInnercircleList(Integer pageNum, Integer pageSize, Map<String, String> search) {
        PageDataResult pageDataResult = new PageDataResult();
        PageHelper.startPage(pageNum, pageSize);
        List<SysScenicSpotInnercircleCoordinateGroupWithBLOBs> sysScenicSpotInnercircleCoordinateGroupWithBLOBsList = sysScenicSpotInnercircleCoordinateGroupMapper.getInnercircleList(search);
        if (sysScenicSpotInnercircleCoordinateGroupWithBLOBsList.size() != 0){
            PageInfo<SysScenicSpotInnercircleCoordinateGroupWithBLOBs> pageInfo = new PageInfo<>(sysScenicSpotInnercircleCoordinateGroupWithBLOBsList);
            pageDataResult.setList(pageInfo.getList());
            pageDataResult.setTotals((int) pageInfo.getTotal());
        }
        return pageDataResult;
    }

    /**
     * @Author 郭凯
     * @Description 添加内圈禁区
     * @Date 16:43 2020/6/8
     * @Param [sysScenicSpotInnercircleCoordinateGroupWithBLOBs]
     * @return int
    **/
    @Override
    public int addInnercircle(SysScenicSpotInnercircleCoordinateGroupWithBLOBs sysScenicSpotInnercircleCoordinateGroupWithBLOBs) {
        sysScenicSpotInnercircleCoordinateGroupWithBLOBs.setCoordinateInnercircleId(IdUtils.getSeqId());
        sysScenicSpotInnercircleCoordinateGroupWithBLOBs.setCreateDate(DateUtil.currentDateTime());
        sysScenicSpotInnercircleCoordinateGroupWithBLOBs.setUpdateDate(DateUtil.currentDateTime());
        return sysScenicSpotInnercircleCoordinateGroupMapper.insertSelective(sysScenicSpotInnercircleCoordinateGroupWithBLOBs);
    }

    /**
     * @Author 郭凯
     * @Description 删除内圈禁区
     * @Date 17:16 2020/6/8
     * @Param [coordinateInnercircleId]
     * @return int
    **/
    @Override
    public int delInnercircle(Long coordinateInnercircleId) {
        return sysScenicSpotInnercircleCoordinateGroupMapper.deleteByPrimaryKey(coordinateInnercircleId);
    }

    /**
     * @Author 郭凯
     * @Description 编辑内圈禁区
     * @Date 17:26 2020/6/8
     * @Param [sysScenicSpotInnercircleCoordinateGroupWithBLOBs]
     * @return int
     **/
    @Override
    public int editInnercircle(SysScenicSpotInnercircleCoordinateGroupWithBLOBs sysScenicSpotInnercircleCoordinateGroupWithBLOBs) {
        sysScenicSpotInnercircleCoordinateGroupWithBLOBs.setUpdateDate(DateUtil.currentDateTime());
        return sysScenicSpotInnercircleCoordinateGroupMapper.updateByPrimaryKeySelective(sysScenicSpotInnercircleCoordinateGroupWithBLOBs);
    }

    /**
     * @Author 郭凯
     * @Description 禁区坐标查询接口
     * @Date 14:44 2020/7/24
     * @Param [scenicSpotId]
     * @return java.util.List<com.hna.hka.archive.management.system.model.SysScenicSpotInnercircleCoordinateGroupWithBLOBs>
    **/
    @Override
    public List<SysScenicSpotInnercircleCoordinateGroupWithBLOBs> getcoordinateGroupListBy(Long scenicSpotId) {
        return sysScenicSpotInnercircleCoordinateGroupMapper.getcoordinateGroupListBy(scenicSpotId);
    }

    //导出禁区excel
    @Override
    public List<InnercricleExcel> getInnercricleExcel(Map<String, Object> search) {
        return sysScenicSpotInnercircleCoordinateGroupMapper.getInnercricleExcel(search);


    }

    //导入禁区excel
    @Override
    public InnercricleExcel getcoordinateInnercircleIdByName(InnercricleExcel inn) {
        return sysScenicSpotInnercircleCoordinateGroupMapper.getcoordinateInnercircleIdByName(inn);
    }

    @Override
    public int addInnercricleExcel(InnercricleExcel inn) {
        return sysScenicSpotInnercircleCoordinateGroupMapper.setInnercricleExcel(inn);
    }

    @Override
    public int upInnercricleExcel(InnercricleExcel inn) {
        inn.setUpdateDate(DateUtil.currentDateTime());

        return sysScenicSpotInnercircleCoordinateGroupMapper.upInnercricleExcel(inn);
    }

    /**
     * @Method getCoordinateGroupListByScenicSpotId
     * @Author 郭凯
     * @Version  1.0
     * @Description 根据景区ID查询禁区信息
     * @Return java.util.List<com.hna.hka.archive.management.system.model.SysScenicSpotInnercircleCoordinateGroupWithBLOBs>
     * @Date 2021/3/29 13:16
     */
    @Override
    public List<SysScenicSpotInnercircleCoordinateGroupWithBLOBs> getCoordinateGroupListByScenicSpotId(Long scenicSpotId) {
        return sysScenicSpotInnercircleCoordinateGroupMapper.getCoordinateGroupListByScenicSpotId(scenicSpotId);
    }
}
