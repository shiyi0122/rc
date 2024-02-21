package com.hna.hka.archive.management.system.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hna.hka.archive.management.system.dao.SysScenicSpotPriceModificationLogMapper;
import com.hna.hka.archive.management.system.model.SysScenicSpotPriceModificationLog;
import com.hna.hka.archive.management.system.model.SysScenicSpotPriceModificationLogVo;
import com.hna.hka.archive.management.system.service.SysScenicSpotPriceModificationLogService;
import com.hna.hka.archive.management.system.util.PageDataResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @ProjectName: IDEA-CODE
 * @Package: com.hna.hka.archive.management.system.service.impl
 * @ClassName: SysScenicSpotPriceModificationLogServiceImpl
 * @Author: 郭凯
 * @Description: 景区操作日志业务层（实现）
 * @Date: 2020/6/1 13:52
 * @Version: 1.0
 */
@Service
@Transactional
public class SysScenicSpotPriceModificationLogServiceImpl implements SysScenicSpotPriceModificationLogService{

    @Autowired
    private SysScenicSpotPriceModificationLogMapper sysScenicSpotPriceModificationLogMapper;

    /**
     * @Author 郭凯
     * @Description 景区操作日志列表查询
     * @Date 14:17 2020/6/1
     * @Param [pageNum, pageSize, search]
     * @return com.hna.hka.archive.management.system.util.PageDataResult
    **/
    @Override
    public PageDataResult getPriceModificationLogList(Integer pageNum, Integer pageSize, Map<String, Object> search) {
        PageDataResult pageDataResult = new PageDataResult();
        PageHelper.startPage(pageNum, pageSize);
        List<SysScenicSpotPriceModificationLog> priceModificationLogList = sysScenicSpotPriceModificationLogMapper.getPriceModificationLogList(search);
        if (priceModificationLogList.size() != 0){
            PageInfo<SysScenicSpotPriceModificationLog> pageInfo = new PageInfo<>(priceModificationLogList);
            pageDataResult.setList(pageInfo.getList());
            pageDataResult.setTotals((int) pageInfo.getTotal());
        }
        return pageDataResult;
    }

    /**
     * @Author 郭凯
     * @Description 下载EXCEl数据查询
     * @Date 15:12 2020/6/1
     * @Param [search]
     * @return java.util.List<com.hna.hka.archive.management.system.model.SysScenicSpotPriceModificationLog>
    **/
    @Override
    public List<SysScenicSpotPriceModificationLog> getSysScenicSpotPriceModificationLogExcel(Map<String, Object> search) {
        return sysScenicSpotPriceModificationLogMapper.getPriceModificationLogList(search);
    }

    /**
    * @Author 郭凯
    * @Description: 下载基础档案Excel表
    * @Title: getSysScenicSpotPriceModificationLogVoExcel
    * @date  2021年3月10日 下午2:23:47 
    * @param @param search
    * @param @return
    * @throws
     */
	@Override
	public List<SysScenicSpotPriceModificationLogVo> getSysScenicSpotPriceModificationLogVoExcel(
			Map<String, Object> search) {
		// TODO Auto-generated method stub
		return sysScenicSpotPriceModificationLogMapper.getSysScenicSpotPriceModificationLogVoExcel(search);
	}
}
