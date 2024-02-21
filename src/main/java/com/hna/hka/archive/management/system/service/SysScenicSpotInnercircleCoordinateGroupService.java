package com.hna.hka.archive.management.system.service;

import com.hna.hka.archive.management.system.model.InnercricleExcel;
import com.hna.hka.archive.management.system.model.SysScenicSpotInnercircleCoordinateGroupWithBLOBs;
import com.hna.hka.archive.management.system.util.PageDataResult;

import java.util.List;
import java.util.Map;

/**
 * @ProjectName: IDEA-CODE
 * @Package: com.hna.hka.archive.management.system.service
 * @ClassName: SysScenicSpotInnercircleCoordinateGroupService
 * @Author: 郭凯
 * @Description: 内圈禁区管理业务层（接口）
 * @Date: 2020/6/8 15:24
 * @Version: 1.0
 */
public interface SysScenicSpotInnercircleCoordinateGroupService {

    PageDataResult getInnercircleList(Integer pageNum, Integer pageSize, Map<String, String> search);

    int addInnercircle(SysScenicSpotInnercircleCoordinateGroupWithBLOBs sysScenicSpotInnercircleCoordinateGroupWithBLOBs);

    int delInnercircle(Long coordinateInnercircleId);

    int editInnercircle(SysScenicSpotInnercircleCoordinateGroupWithBLOBs sysScenicSpotInnercircleCoordinateGroupWithBLOBs);

    List<SysScenicSpotInnercircleCoordinateGroupWithBLOBs> getcoordinateGroupListBy(Long scenicSpotId);

    List<SysScenicSpotInnercircleCoordinateGroupWithBLOBs> getCoordinateGroupListByScenicSpotId(Long parseLong);

    List<InnercricleExcel> getInnercricleExcel(Map<String, Object> search);

    InnercricleExcel getcoordinateInnercircleIdByName(InnercricleExcel inn);

    int upInnercricleExcel(InnercricleExcel inn);

    int addInnercricleExcel(InnercricleExcel inn);
}
