package com.hna.hka.archive.management.system.service;

import com.hna.hka.archive.management.system.model.SysRobotSounds;
import com.hna.hka.archive.management.system.util.PageDataResult;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * @ProjectName: IDEA-CODE
 * @Package: com.hna.hka.archive.management.system.service
 * @ClassName: SysRobotSoundsService
 * @Author: 郭凯
 * @Description: 机器人提示音管理业务层（接口）
 * @Date: 2020/11/12 10:22
 * @Version: 1.0
 */
public interface SysRobotSoundsService {

    PageDataResult getRobotSoundsList(Integer pageNum, Integer pageSize, Map<String, String> search);

    int delRobotSounds(Long soundsId);

    int addRobotSoundsWrittenWords(SysRobotSounds sysRobotSounds);

    int addRobotSoundsVideo(MultipartFile file, SysRobotSounds sysRobotSounds);

    int addRobotSoundsAudio(MultipartFile file, SysRobotSounds sysRobotSounds);

    int editRobotSoundsWrittenWords(SysRobotSounds sysRobotSounds);

    int editRobotSoundsVideo(MultipartFile file,SysRobotSounds sysRobotSounds);

    int editRobotSoundsAudio(MultipartFile file, SysRobotSounds sysRobotSounds);
}
