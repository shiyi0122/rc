package com.hna.hka.archive.management.system.service;

import com.hna.hka.archive.management.system.model.SysScenicSpotRandomProblemWithBLOBs;
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
public interface SysScenicSpotRandomProblemService {

    PageDataResult getRandomProblemList(Integer pageNum, Integer pageSize, Map<String, String> search);

    int delRandomProblem(Long problemId);

    int editRandomProblemValid(SysScenicSpotRandomProblemWithBLOBs sysScenicSpotRandomProblemWithBLOBs);

    int addRandomProblemImage(MultipartFile file, SysScenicSpotRandomProblemWithBLOBs sysScenicSpotRandomProblemWithBLOBs);

    int addRandomProblemVideo(MultipartFile file, SysScenicSpotRandomProblemWithBLOBs sysScenicSpotRandomProblemWithBLOBs);

    int addRandomProblemAudio(MultipartFile[] file, SysScenicSpotRandomProblemWithBLOBs randomProblem);

    int editRandomProblemImage(MultipartFile file, SysScenicSpotRandomProblemWithBLOBs sysScenicSpotRandomProblemWithBLOBs);

    int editRandomProblemVideo(MultipartFile file, SysScenicSpotRandomProblemWithBLOBs sysScenicSpotRandomProblemWithBLOBs);

    int editRandomProblemAudio(MultipartFile[] file, SysScenicSpotRandomProblemWithBLOBs randomProblem);
}
