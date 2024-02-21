package com.hna.hka.archive.management.system.service;

import com.hna.hka.archive.management.system.util.PageDataResult;

import java.util.Map;

/**
 * @Author zhang
 * @Date 2022/2/25 9:32
 * 流量问题
 */
public interface SysRobotDownloadLogServer {


    PageDataResult getSysRobotDownloadLogList(Integer pageNum, Integer pageSize, Map<String, String> search);


}
