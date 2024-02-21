package com.hna.hka.archive.management.system.service;

import com.hna.hka.archive.management.system.model.SysScenicSpotRandomProblemWithBLOBs;
import com.hna.hka.archive.management.system.model.SysScenicSpotTimingProblemWithBLOBs;
import com.hna.hka.archive.management.system.util.PageDataResult;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * @ProjectName: IDEA-CODE
 * @Package: com.hna.hka.archive.management.system.service
 * @ClassName: SysScenicSpotRandomProblemService
 * @Author: 郭凯
 * @Description: 随机播报管理业务层（接口）
 * @Date: 2020/6/20 10:23
 * @Version: 1.0
 */
public interface SysScenicSpotTimingProblemService {

    PageDataResult getTimingProblemList(Integer pageNum, Integer pageSize, Map<String, String> search);

    int delTimingProblem(Long problemId);

    int editTimingProblemValid(SysScenicSpotTimingProblemWithBLOBs sysScenicSpotTimingProblemWithBLOBs);

    int addTimingProblemImage(MultipartFile file, SysScenicSpotTimingProblemWithBLOBs sysScenicSpotTimingProblemWithBLOBs);

    int addTimingProblemVideo(MultipartFile file, SysScenicSpotTimingProblemWithBLOBs sysScenicSpotTimingProblemWithBLOBs);

    int addTimingProblemAudio(MultipartFile[] file, SysScenicSpotTimingProblemWithBLOBs timingProblem);

    int editTimingProblemImage(MultipartFile file, SysScenicSpotTimingProblemWithBLOBs sysScenicSpotTimingProblemWithBLOBs);

    int editTimingProblemVideo(MultipartFile file, SysScenicSpotTimingProblemWithBLOBs sysScenicSpotTimingProblemWithBLOBs);

    int editTimingProblemAudio(MultipartFile[] file, SysScenicSpotTimingProblemWithBLOBs timingProblem);
}
