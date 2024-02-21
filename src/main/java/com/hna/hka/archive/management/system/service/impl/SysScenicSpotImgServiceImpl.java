package com.hna.hka.archive.management.system.service.impl;

import com.hna.hka.archive.management.system.dao.SysScenicSpotImgMapper;
import com.hna.hka.archive.management.system.model.SysScenicSpotImg;
import com.hna.hka.archive.management.system.service.SysScenicSpotImgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class SysScenicSpotImgServiceImpl implements SysScenicSpotImgService {

    @Autowired
    private SysScenicSpotImgMapper sysScenicSpotImgMapper;

    /**
     * 根据用户ID查询景区名称和图片
     * @param search
     * @return
     */
    @Override
    public List<SysScenicSpotImg> getScenicSpotImgByUserId(Map<String, String> search) {
        return sysScenicSpotImgMapper.getScenicSpotImgByUserId(search);
    }
}
