package com.hna.hka.archive.management.system.service;

import com.hna.hka.archive.management.system.model.SysScenicSpotInfraredBroadcast;
import com.hna.hka.archive.management.system.util.PageDataResult;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * @ProjectName: IDEA-CODE
 * @Package: com.hna.hka.archive.management.system.service
 * @ClassName: SysScenicSpotInfraredBroadcastService
 * @Author: 郭凯
 * @Description: 红外播报管理业务层（接口）
 * @Date: 2020/11/12 9:28
 * @Version: 1.0
 */
public interface SysScenicSpotInfraredBroadcastService {

    PageDataResult getInfraredBroadcastList(Integer pageNum, Integer pageSize, Map<String, String> search);

    int addInfraredBroadcastWrittenWords(SysScenicSpotInfraredBroadcast sysScenicSpotInfraredBroadcast);

    int addInfraredBroadcastAudio(MultipartFile file,SysScenicSpotInfraredBroadcast sysScenicSpotInfraredBroadcast);

    int editInfraredBroadcastWrittenWords(SysScenicSpotInfraredBroadcast sysScenicSpotInfraredBroadcast);

    int editInfraredBroadcastAudio(MultipartFile file, SysScenicSpotInfraredBroadcast sysScenicSpotInfraredBroadcast);

    int delInfraredBroadcast(Long infraredId);
}
