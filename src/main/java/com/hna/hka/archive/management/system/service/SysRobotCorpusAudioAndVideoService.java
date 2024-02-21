package com.hna.hka.archive.management.system.service;

import com.hna.hka.archive.management.system.model.SysRobotCorpusAudioAndVideo;
import com.hna.hka.archive.management.system.model.SysRobotCorpusMediaExtend;
import com.hna.hka.archive.management.system.util.PageDataResult;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * @ProjectName: IDEA-CODE
 * @Package: com.hna.hka.archive.management.system.service
 * @ClassName: SysRobotCorpusAudioAndVideoService
 * @Author: 郭凯
 * @Description: 语音媒体资源业务层(接口)
 * @Date: 2020/6/15 13:30
 * @Version: 1.0
 */
public interface SysRobotCorpusAudioAndVideoService {

    PageDataResult getCorpusAudioAndVideoList(Integer pageNum, Integer pageSize, Map<String, String> search);

    PageDataResult getViewDetailsList(Integer pageNum, Integer pageSize, Map<String, String> search);

    int delCorpusAudioAndVideo(Long mediaId);

    int addCorpusAudio(MultipartFile file, SysRobotCorpusAudioAndVideo sysRobotCorpusAudioAndVideo);

    int addCorpusVideo(MultipartFile file, SysRobotCorpusAudioAndVideo sysRobotCorpusAudioAndVideo);

    SysRobotCorpusAudioAndVideo getCorpusAudioAndVideoById(Long mediaId);

    int editCorpusAudio(MultipartFile file, SysRobotCorpusAudioAndVideo sysRobotCorpusAudioAndVideo);

    int editCorpusVideo(MultipartFile file, SysRobotCorpusAudioAndVideo sysRobotCorpusAudioAndVideo);

    int addCorpusMediaExtend(SysRobotCorpusMediaExtend sysRobotCorpusMediaExtend);

    int delCorpusMediaExtend(Long mediaExtendId);

    int editCorpusMediaExtend(SysRobotCorpusMediaExtend sysRobotCorpusMediaExtend);
}
