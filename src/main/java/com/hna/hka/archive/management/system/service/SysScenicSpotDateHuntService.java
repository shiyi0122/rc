package com.hna.hka.archive.management.system.service;

import com.hna.hka.archive.management.system.model.SysScenicSpotDateHunt;
import com.hna.hka.archive.management.system.util.PageDataResult;

import java.util.Map;

/**
 * @Author zhang
 * @Date 2022/3/10 10:10
 */

public interface SysScenicSpotDateHuntService {
    int addSpotDateHunt(SysScenicSpotDateHunt sysScenicSpotDateHunt);

    int editSpotDateHunt(SysScenicSpotDateHunt sysScenicSpotDateHunt);

    int delSpotDateHunt(Long dateTreasureId);

    PageDataResult getSpotDateHuntList(Integer pageNum, Integer pageSize, Map<String, String> search);


    PageDataResult getSpotDateHuntIdList( Map<String,Object>  search);

}
