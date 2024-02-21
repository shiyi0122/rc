package com.hna.hka.archive.management.system.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hna.hka.archive.management.system.dao.SysScenicSpotRecommendedRouteMapper;
import com.hna.hka.archive.management.system.model.SysScenicSpotRecommendedRoute;
import com.hna.hka.archive.management.system.service.SysScenicSpotRecommendedRouteService;
import com.hna.hka.archive.management.system.util.DateUtil;
import com.hna.hka.archive.management.system.util.IdUtils;
import com.hna.hka.archive.management.system.util.PageDataResult;
import com.hna.hka.archive.management.system.util.Tinypinyin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @ProjectName: IDEA-CODE
 * @Package: com.hna.hka.archive.management.system.service.impl
 * @ClassName: SysScenicSpotRecommendedRouteServiceImpl
 * @Author: 郭凯
 * @Description: 经典路线管理业务层（实现）
 * @Date: 2020/6/22 9:37
 * @Version: 1.0
 */
@Service
@Transactional
public class SysScenicSpotRecommendedRouteServiceImpl implements SysScenicSpotRecommendedRouteService {

    @Autowired
    private SysScenicSpotRecommendedRouteMapper sysScenicSpotRecommendedRouteMapper;

    /**
     * @Author 郭凯
     * @Description 经典路线列表查询
     * @Date 9:39 2020/6/22
     * @Param [pageNum, pageSize, search]
     * @return com.hna.hka.archive.management.system.util.PageDataResult
    **/
    @Override
    public PageDataResult getRecommendedRouteList(Integer pageNum, Integer pageSize, Map<String, String> search) {
        PageDataResult pageDataResult = new PageDataResult();
        PageHelper.startPage(pageNum, pageSize);
        List<SysScenicSpotRecommendedRoute> sysScenicSpotRecommendedRouteList = sysScenicSpotRecommendedRouteMapper.getRecommendedRouteList(search);
        if (sysScenicSpotRecommendedRouteList.size() != 0){
            PageInfo<SysScenicSpotRecommendedRoute> pageInfo = new PageInfo<>(sysScenicSpotRecommendedRouteList);
            pageDataResult.setList(pageInfo.getList());
            pageDataResult.setTotals((int) pageInfo.getTotal());
        }
        return pageDataResult;
    }

    /**
     * @Author 郭凯
     * @Description 经典路线新增
     * @Date 10:24 2020/6/22
     * @Param [sysScenicSpotRecommendedRoute]
     * @return int
    **/
    @Override
    public int addRecommendedRoute(SysScenicSpotRecommendedRoute sysScenicSpotRecommendedRoute) {
        sysScenicSpotRecommendedRoute.setRouteId(IdUtils.getSeqId());
        sysScenicSpotRecommendedRoute.setRouteNamePinYin(Tinypinyin.tinypinyin(sysScenicSpotRecommendedRoute.getRouteName()));
        sysScenicSpotRecommendedRoute.setCreateDate(DateUtil.currentDateTime());
        sysScenicSpotRecommendedRoute.setUpdateDate(DateUtil.currentDateTime());
        return sysScenicSpotRecommendedRouteMapper.insertSelective(sysScenicSpotRecommendedRoute);
    }

    /**
     * @Author 郭凯
     * @Description 删除景点路线
     * @Date 10:31 2020/6/22
     * @Param [routeId]
     * @return int
    **/
    @Override
    public int delRecommendedRoute(Long routeId) {
        return sysScenicSpotRecommendedRouteMapper.deleteByPrimaryKey(routeId);
    }

    /**
     * @Author 郭凯
     * @Description 修改经典路线
     * @Date 10:38 2020/6/22
     * @Param [sysScenicSpotRecommendedRoute]
     * @return int
    **/
    @Override
    public int editRecommendedRoute(SysScenicSpotRecommendedRoute sysScenicSpotRecommendedRoute) {
        sysScenicSpotRecommendedRoute.setRouteNamePinYin(Tinypinyin.tinypinyin(sysScenicSpotRecommendedRoute.getRouteName()));
        sysScenicSpotRecommendedRoute.setUpdateDate(DateUtil.currentDateTime());
        return sysScenicSpotRecommendedRouteMapper.updateByPrimaryKeySelective(sysScenicSpotRecommendedRoute);
    }

    /**
     * 获取全部线路
     * @return
     */
    @Override
    public List<SysScenicSpotRecommendedRoute> getScenicSpotRecommendedRouteAll() {

        List<SysScenicSpotRecommendedRoute> list =  sysScenicSpotRecommendedRouteMapper.getScenicSpotRecommendedRouteAll();

        return list;
    }
}
