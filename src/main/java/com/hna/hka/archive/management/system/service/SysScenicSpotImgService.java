package com.hna.hka.archive.management.system.service;

import com.hna.hka.archive.management.system.model.SysScenicSpotImg;

import java.util.List;
import java.util.Map;

public interface SysScenicSpotImgService {

    /**
     * 根据用户ID查询景区名称和图片
     * @param search
     * @return
     */
    List<SysScenicSpotImg> getScenicSpotImgByUserId(Map<String, String> search);
}
