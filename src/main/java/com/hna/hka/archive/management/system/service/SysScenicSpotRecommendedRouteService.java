package com.hna.hka.archive.management.system.service;

import com.hna.hka.archive.management.system.model.SysScenicSpotRecommendedRoute;
import com.hna.hka.archive.management.system.util.PageDataResult;

import java.util.List;
import java.util.Map;

/**
 * @ProjectName: IDEA-CODE
 * @Package: com.hna.hka.archive.management.system.service
 * @ClassName: SysScenicSpotRecommendedRouteService
 * @Author: 郭凯
 * @Description: 经典路线管理业务层（接口）
 * @Date: 2020/6/22 9:37
 * @Version: 1.0
 */
public interface SysScenicSpotRecommendedRouteService {

    PageDataResult getRecommendedRouteList(Integer pageNum, Integer pageSize, Map<String, String> search);

    int addRecommendedRoute(SysScenicSpotRecommendedRoute sysScenicSpotRecommendedRoute);

    int delRecommendedRoute(Long routeId);

    int editRecommendedRoute(SysScenicSpotRecommendedRoute sysScenicSpotRecommendedRoute);

    List<SysScenicSpotRecommendedRoute> getScenicSpotRecommendedRouteAll();


}
