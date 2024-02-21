package com.hna.hka.archive.management.system.service;

import com.hna.hka.archive.management.system.model.SysRobotCorpusWithBLOBs;
import com.hna.hka.archive.management.system.model.SysRobotProblemExtend;
import com.hna.hka.archive.management.system.util.PageDataResult;

import java.util.Map;

/**
 * @ProjectName: IDEA-CODE
 * @Package: com.hna.hka.archive.management.system.service
 * @ClassName: SysRobotCorpusService
 * @Author: 郭凯
 * @Description: 语义交互管理业务层（接口）
 * @Date: 2020/6/4 10:46
 * @Version: 1.0
 */
public interface SysRobotCorpusService {

    PageDataResult getSemanticsList(Integer pageNum, Integer pageSize, Map<String, String> search);

    int delSemantics(Long corpusId);

    int addSemantics(SysRobotCorpusWithBLOBs sysRobotCorpusWithBLOBs);

    int editSemantics(SysRobotCorpusWithBLOBs sysRobotCorpusWithBLOBs);

    PageDataResult getSemanticsDetailsList(Integer pageNum, Integer pageSize, Map<String, Object> search);

    int addSemanticsDetails(SysRobotProblemExtend sysRobotProblemExtend);

    int delSemanticsDetails(Long extendId);

    int editSemanticsDetails(SysRobotProblemExtend sysRobotProblemExtend);
}
