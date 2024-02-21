package com.hna.hka.archive.management.system.service;

import com.hna.hka.archive.management.system.model.SysScenicSpotDateTreasureHunt;
import com.hna.hka.archive.management.system.model.SysScenicSpotTreasureHunt;
import com.hna.hka.archive.management.system.util.PageDataResult;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * @Author zhang
 * @Date 2022/3/11 15:21
 */
public interface SysScenicSpotDateTreasureHuntService {


    PageDataResult getDateTreasureList(Integer pageNum, Integer pageSize, Map<String, String> search);

    int addDateTreasure(SysScenicSpotDateTreasureHunt dateTreasureHunt, MultipartFile file);

    int editDateTreasure(SysScenicSpotDateTreasureHunt dateTreasureHunt, MultipartFile file);

    int delDateTreasure(Long treasureId, Long scenicSpotId);

}
