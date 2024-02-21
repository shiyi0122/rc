package com.hna.hka.archive.management.system.dao;

import com.hna.hka.archive.management.system.model.SysLog;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2022/2/25 9:41
 */
@Mapper
public interface SysRobotDownLoadLogMapper {

    List<SysLog> getSysRobotDownloadLogList(Map<String, String> search);

}
